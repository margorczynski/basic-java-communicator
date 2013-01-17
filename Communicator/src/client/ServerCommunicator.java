package client;

import java.util.LinkedList;

import java.util.List;

import java.io.*;

import server.ServerThread;

import base.Message;

/**
 * Abstract class containing static methods used for communication with the server
 * It uses a method of communication explained in the {@link ServerThread} class
 * Uses the BufferedReader and PrintWriter classes for IO with the server
 * 
 * @author      Marcin Gorczyñski
 * @see			BufferedReader
 * @see         PrintWriter
 * @version     1.0
 */

public abstract class ServerCommunicator 
{	
	/** 
     * Attempts to login the user, returns if it succeded
     *
     * @param	username the username to login
     * @param	password the password of the user logging in
     * @param   in		 reference to the input of the socket
     * @param   out      reference to the output of the socket
     * @return 			<code>true</code> if logged in succefully, <code>false</code> otherwise
     * @see				BufferedReader
     * @see				PrintWriter
     * @since           1.0
     */
	
	public static boolean login(String username, String password, BufferedReader in, PrintWriter out)
	{
		out.println("!Login!");
		
		out.println(username);
		
		out.println(password);
		
		try
		{
			if(in.readLine().equals("Error"))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	/** 
     * Attempts to create a user, returns if it succeded
     *
     * @param	username the username to login
     * @param	password the password of the user logging in
     * @param   in		 reference to the input of the socket
     * @param   out      reference to the output of the socket
     * @return 			<code>true</code> if the user was created, <code>false</code> otherwise
     * @see				BufferedReader
     * @see				PrintWriter
     * @since           1.0
     */
	
	public static boolean register(String username, String password, BufferedReader in, PrintWriter out)
	{
		out.println("!Register!");
		
		out.println(username);
		
		out.println(password);
		
		try
		{
			if(in.readLine().equals("User already exists")) return false;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
	
	/** 
     * Attempts to send a message from the user to another
     *
     * @param	username the username to login
     * @param	password the password of the user logging in
     * @param   in		 reference to the input of the socket
     * @param   out      reference to the output of the socket
     * @return 			<code>true</code> if the message was sent, <code>false</code> otherwise
     * @see				BufferedReader
     * @see				PrintWriter
     * @since           1.0
     */
	
	public static boolean sendMessage(String toUsername, String message, BufferedReader in, PrintWriter out)
	{
		out.println("!Message!");
		
		out.println(toUsername);
		
		out.println(message);
		
		try
		{
			if(in.readLine().equals("Sent"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	/** 
     * Logs out the user
     *
     * @param   out      reference to the output of the socket
     * @see				PrintWriter
     * @since           1.0
     */
	
	public static void logout(PrintWriter out)
	{
		out.println("!Logout!");
	}
	
	/** 
     * Checks if the user is online returning the result
     *
     * @param	username the username to check if online
     * @param   in		 reference to the input of the socket
     * @param   out      reference to the output of the socket
     * @return 			<code>true</code> if the user is online, <code>false</code> otherwise
     * @see				BufferedReader
     * @see				PrintWriter
     * @since           1.0
     */
	
	public static boolean online(String username, BufferedReader in, PrintWriter out)
	{
		out.println("!Online!");
		
		out.println(username);
		
		try
		{
			if(in.readLine().equals("true"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	/** 
     * Gets all messages from the server return a reference to the list containing {@link Message} objects
     *
     * @param   in		 reference to the input of the socket
     * @param   out      reference to the output of the socket
     * @return 			<code>true</code> if the user exists, <code>false</code> otherwise
     * @see				BufferedReader
     * @see				PrintWriter
     * @see             List
     * @see             LinkedList
     * @see             Message
     * @since           1.0
     */
	
	public static List<Message> getMessages(BufferedReader in, PrintWriter out)
	{
		int number = 0;
		
		List<Message> messages = new LinkedList<Message>();
		
		out.println("!GetMessages!");
		
		try
		{
			number = Integer.parseInt(in.readLine());
			
			for(int i = 0; i < number; i++)
			{
				String fromUsername = in.readLine();
				
				String message = in.readLine();
				
				messages.add(new Message(fromUsername, message));
			}
		}
		catch(IOException e)
		{
			e.getMessage();
		}
		
		
		
		return messages;
	}
}
