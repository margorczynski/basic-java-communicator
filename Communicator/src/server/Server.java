package server;

/**
 * The main class of the server application containing the main method entry-point
 * It establishes a basic TCP/IP server listening on the port specified as the argument of the program
 * For every accepted connection it spawns a new ServerThread to handle and process it
 * 
 * @author      Marcin Gorczyñski
 * @version     1.0
 */

import java.io.*;
import java.net.*;

public final class Server 
{
	/** 
     * The entry point method. Takes the port number as an argument, creates a {@link ServerSocket} object to listen on it 
     * and creates {@link ServerThread} objects to handle incoming connections on that socket.
     * Checks and creates if needed the database file and structure
     * 
     * @param args      the arguments of the program. There must be only 
     * 					one argument that specifies the port to listen on
     * @see				ServerSocket
     * @since           1.0
     */
	
	public static void main(String[] args) throws IOException
	{	
		boolean running = true;
		
		ServerSocket serverSocket = null;
		
		DatabaseCommunicator.init();
		
		new ServerFrame();
		
		if(args.length != 1)
		{
			System.exit(1);
		}
		
		try 
		{
		    serverSocket = new ServerSocket(Integer.parseInt(args[0]));
		} 
		catch (IOException e) 
		{
		    System.out.println("Could not listen on port: " + Integer.parseInt(args[0]));
		    System.exit(-1);
		}
		
		while(running)
		{
			new ServerThread(serverSocket.accept()).start();
		}
		
		serverSocket.close();

	}

}
