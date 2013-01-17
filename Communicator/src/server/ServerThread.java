package server;

import java.util.List;

import java.io.*;
import java.net.*;

import base.Message;

/**
 * ServerThread is the thread class used for handling requests from multiple clients at once
 * It extends the basic class Thread
 * Contains one field that contains the reference to the server socket used
 * 
 * 
 * @author      Marcin Gorczyñski
 * @see			Thread
 * @version     1.0
 */

public final class ServerThread extends Thread
{
	/** 
     * The constructor of the ServerThread class. 
     * Calls the constructor of thread creating a thread called "Server Thread" and sets the reference to the socket
     *
     * @param socket	reference to the socket used by the server
     * @see				Socket
     * @see				Thread
     * @since           1.0
     */
	
	public ServerThread(Socket socket)
	{
		super("Server thread");
		
		this.socket = socket;
	}
	
	/** 
     * The main method of the {@link ServerThread} class. 
     * It handles all communication between the client and server using the static methods from the {@link DatabaseCommunicator} class
     * The protocol/communication is based on command strings with a format "!command!", for example "!Online!", "!Message!", etc.
     * 
     * The algorithm of handling is as follows:
     * 
     * <ul>
     * <li>Check if the client wants to register, if yes register
     * <li>Login
     * <li>Loop until you get logout command from client
     * <li>Handle online check, message send and receiving command from client
     * <li>Get next command and go back to point 3 if you don't get logout command
     * <li>Logout
     * </ul>
     * 
     * After loging out close all IO and the socket
     * 
     * If theres a exception thrown by the IO handling method it is catched and the stack trace is printed on the console
     * 
     *
     * @see				Socket
     * @see				PrintWriter
     * @see				BufferedReader
     * @since           1.0
     */
	
	public void run()
	{
		
		try
		{
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			BufferedReader in = new BufferedReader(
				    new InputStreamReader(
				    socket.getInputStream()));
			
			String inputLine;
			
			String username, password, ip;
			
			String toUsername, message;
			
			ip = socket.getInetAddress().getHostAddress();
			
			inputLine = in.readLine();
	
			
			username = "";
			
			password = "";
			
			while(inputLine.equals("!Register!") || inputLine.equals("!Login!"))
			{
				
				if(inputLine.equals("!Register!"))
				{
					username = in.readLine();
					
					password = in.readLine();
						
					if(!DatabaseCommunicator.exists(username) && !username.equals(""))
					{
						DatabaseCommunicator.create(username, password);
							
						out.println("Created");
						
						inputLine = in.readLine();
					}
					else
					{
						out.println("User already exists");
						
						inputLine = in.readLine();
					}
				}
				
				if(inputLine.equals("!Login!"))
				{	
					username = in.readLine();
						
					password = in.readLine();
							
					if(DatabaseCommunicator.login(username, password, ip))
					{
						out.println("Ok");
						
						inputLine = in.readLine();
						
						break;
					}
					else
					{
						out.println("Error");
						
						inputLine = in.readLine();
					}
				}
			}
			
			while(!inputLine.equals("!Logout!"))
			{
				if(inputLine.equals("!Message!"))
				{
					toUsername = in.readLine();
					
					message = in.readLine();
					
					if(DatabaseCommunicator.sendMessage(username, toUsername, message))
					{
						out.println("Sent");
					}
					else
					{
						out.println("Error");
					}
					
					inputLine = in.readLine();
				}
				
				if(inputLine.equals("!Online!"))
				{
					String onlineUsername;
					
					onlineUsername = in.readLine();
						
					out.println(DatabaseCommunicator.online(onlineUsername));
					
					inputLine = in.readLine();
					
				}
				
				if(inputLine.equals("!GetMessages!"))
				{
					List<Message> messages = DatabaseCommunicator.getMessages(username);
					
					out.println(Integer.toString(messages.size()));
					
					for(Message m : messages)
					{
						out.println(m.getFromUsername());
						out.println(m.getMessage());
					}
					
					inputLine = in.readLine();
				}
			}
			
			DatabaseCommunicator.logout(username, password);
			
			in.close();
			
			out.close();
			
			socket.close();
			
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private Socket socket = null;
}
