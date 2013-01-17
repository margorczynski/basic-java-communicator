package client;

import java.io.*;
import java.net.*;

/**
 * The main class of the client application containing the main method entry-point
 * It establishes a basic TCP/IP  client connection to a server with a specified adress and port
 * 
 * @author      Marcin Gorczyñski
 * @version     1.0
 */

public final class Client 
{
	/** 
     * The entry point method. Takes the ip adress and port number as arguments establishing a connection using a socket
     * It handles communication with the server using the {@link ServerCommunicator} class
     * 
     * @param args      the arguments of the program. There must be only 
     * 					two arguments that specifiy the adress and port to connect
     * @see				ServerSocket
     * @since           1.0
     */
	
	public static void main(String[] args) throws IOException
	{
		Socket clientSocket = null;
		
		PrintWriter out = null;
		
		BufferedReader in = null;
		
		
		
		
		if(args.length != 2)
		{
			System.out.println("Wrong argument number, specify host and port...");
			
			System.exit(1);
		}
		
		try
		{
			clientSocket = new Socket(args[0], Integer.parseInt(args[1]));
			
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			
            in = new BufferedReader(new InputStreamReader(
                                        clientSocket.getInputStream()));
		}
		catch(UnknownHostException e)
		{
			System.err.println("Can't resolve host: " + args[0]);
                    									
			System.exit(1);
		}
		catch (IOException e) 
		{
            System.err.println("Couldn't get I/O for " + "the connection to: " + args[0] + " on port " + args[1]);
            System.exit(1);
        }
		
		new ClientFrame(in, out);
		
		//ServerCommunicator.logout(out);
		
		//clientSocket.close();
		

	}

}
