package server;

import java.io.File;
import java.io.IOException;
import java.sql.*;

import java.util.LinkedList;
import java.util.List;

import base.*;

/**
 * Abstract class containing static methods used for database communication
 * The methods are synchronized so that two or more threads won't acces the database at the same time ensuring data integrity
 * It uses the SQLite RDBMS as the database backend (needs the tables to be already created) and the SQLite JDBC connector
 * 
 * @author      Marcin Gorczyñski
 * @see			http://en.wikibooks.org/wiki/Java_JDBC_using_SQLite
 * @see			SQL
 * @see			http://www.sqlite.org/
 * @version     1.0
 */

public abstract class DatabaseCommunicator 
{
	/** 
     * Checks if the database file exists, if not then it creates one and creates the tables using SQL updates
     * The method isn't <code>synchronized</code> because it only is ran one within the main thread
     *
     * @see				Connection
     * @see				Statement
     * @since           1.0
     */
	
	public static void init()
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		
		File db = new File("komunikator.sqlite");
		
		try
		{
			db.createNewFile();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
				
			sqlConnection = DriverManager.getConnection(
			                    "jdbc:sqlite:komunikator.sqlite");
				
			sqlStatement = sqlConnection.createStatement();
				
			sqlStatement.executeUpdate(
				         "CREATE TABLE  IF NOT EXISTS 'Message' ('messageid' INTEGER PRIMARY KEY  NOT NULL ,'from' VARCHAR,'to' VARCHAR,'message' TEXT);");
			
			sqlStatement.executeUpdate(
			         "CREATE TABLE  IF NOT EXISTS 'User' ('username' VARCHAR PRIMARY KEY  NOT NULL , 'password' VARCHAR NOT NULL , 'online' BOOL NOT NULL  DEFAULT FALSE, 'ip' VARCHAR);");

			sqlConnection.close();	
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
		}
	}
	
	/** 
     * Checks if the user exists in the database returning true or false as the answer
     *
     * @param username	the users username in the database
     * @return			<code>true</code> if the user exists, <code>false</code> otherwise
     * @see				Connection
     * @see				Statement
     * @since           1.0
     */
	
	public synchronized static boolean exists(String username)
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
			
			sqlConnection = DriverManager.getConnection(
		                    "jdbc:sqlite:komunikator.sqlite");
			
			sqlStatement = sqlConnection.createStatement();
			
			ResultSet sqlResult = sqlStatement.executeQuery(
			         "SELECT username FROM User WHERE username = " + "'" + username + "'");
			

			if(sqlResult.next())
			{
				sqlResult.close();
				
				sqlConnection.close();
				
				return true;
			}
			else
			{
				sqlResult.close();
				
				sqlConnection.close();
				
				return false;
			}
			
			
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
			
			return false;
		}
	}
	
	/** 
     * Gets all users from the database and returns a reference to a list of {@link User} objects
     *
     * @return			reference to the list of users
     * @see				Connection
     * @see				Statement
     * @see				List
     * @see				LinkedList
     * @since           1.0
     */
	
	public synchronized static List<User> getAllUsers()
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		List<User> users = new LinkedList<User>();
		
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
			
			sqlConnection = DriverManager.getConnection(
		                    "jdbc:sqlite:komunikator.sqlite");
			
			sqlStatement = sqlConnection.createStatement();
			
			ResultSet sqlResult = sqlStatement.executeQuery(
			         "SELECT * FROM User");
			

			while(sqlResult.next())
			{
				User user = new User(sqlResult.getString("username"), sqlResult.getString("password"), sqlResult.getString("online"),sqlResult.getString("ip"));
				
				users.add(user);
			}
			
			sqlResult.close();
			
			sqlConnection.close();
			
			return users;
			
			
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
			
			return users;
		}
	}
	
	
	/** 
     * Creates/registers a user in the database returning if it succeded
     *
     * @param username	the users username
     * @param			the users password
     * @return			<code>true</code> if the user was created, <code>false</code> otherwise
     * @see				Connection
     * @see				Statement
     * @since           1.0
     */
	
	public synchronized static boolean create(String username, String password)
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		try
		{
			if(DatabaseCommunicator.exists(username))
			{
				return false;
			}
			else
			{
				Class.forName("org.sqlite.JDBC").newInstance();
				
				sqlConnection = DriverManager.getConnection(
			                    "jdbc:sqlite:komunikator.sqlite");
				
				sqlStatement = sqlConnection.createStatement();
				
				sqlStatement.executeUpdate("INSERT INTO User VALUES ('" + username + "', '" + password + "', 'FALSE' , '0');");
				

				sqlConnection.close();
				
				return true;
			}
			
			
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
			
			return false;
		}
	}
	
	/** 
     * Checks if the user is online at the moment returning the result
     *
     * @param username	the users username in the database
     * @return			<code>true</code> if the user is online, <code>false</code> otherwise
     * @see				Connection
     * @see				Statement
     * @since           1.0
     */
	
	public synchronized static boolean online(String username)
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
			
			sqlConnection = DriverManager.getConnection(
		                    "jdbc:sqlite:komunikator.sqlite");
			
			sqlStatement = sqlConnection.createStatement();
			
			ResultSet sqlResult = sqlStatement.executeQuery(
			         "SELECT username FROM User WHERE username = " + "'" + username + "' AND online = 'TRUE'");
			

			if(sqlResult.next())
			{
				sqlResult.close();
				
				sqlConnection.close();
				
				return true;
			}
			else
			{
				sqlResult.close();
				
				sqlConnection.close();
				
				return false;
			}
			
			
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
			
			return false;
		}
	}
	
	/** 
     * Checks how many users are online returning the number
     *
     * @return			the number of users online
     * @see				Connection
     * @see				Statement
     * @since           1.0
     */
	
	public synchronized static int howManyOnline()
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		int number = 0;
		
		
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
			
			sqlConnection = DriverManager.getConnection(
		                    "jdbc:sqlite:komunikator.sqlite");
			
			sqlStatement = sqlConnection.createStatement();
			
			ResultSet sqlResult = sqlStatement.executeQuery(
			         "SELECT * FROM User WHERE online = 'TRUE'");
			

			while(sqlResult.next()) number++;
			
			sqlResult.close();
				
			sqlConnection.close();
			
			return number+1;
			
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
			
			return number;
		}
	}
	
	/** 
     * Login the user if he exists returning if the login was succesful
     *
     * @param username	the users username in the database
     * @param password  the users password in the database
     * @param ip        the users ip (the accepted connection ip)
     * @return			<code>true</code> if the user was logged in, <code>false</code> otherwise
     * @see				Connection
     * @see				Statement
     * @since           1.0
     */
	
	public synchronized static boolean login(String username, String password, String ip)
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
			
			sqlConnection = DriverManager.getConnection(
		                    "jdbc:sqlite:komunikator.sqlite");
			
			sqlStatement = sqlConnection.createStatement();
			
			ResultSet sqlResult = sqlStatement.executeQuery(
			         "SELECT username FROM User WHERE username = " + "'" + username + "' AND password = '" + password + "';");
			

			if(sqlResult.next())
			{
				sqlStatement.executeUpdate("UPDATE User SET online = 'TRUE', ip = '" + ip + "' WHERE username = '" + username + "';");
				
				sqlResult.close();
				
				sqlConnection.close();
				
				return true;
			}
			else
			{
				sqlResult.close();
				
				sqlConnection.close();
				
				return false;
			}
			
			
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
			
			return false;
		}
	}
	
	/** 
     * Logs out the user
     *
     * @param username	the users username in the database
     * @param password  the users password in the database
     * @see				Connection
     * @see				Statement
     * @since           1.0
     */
	
	public synchronized static void logout(String username, String password)
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
			
			sqlConnection = DriverManager.getConnection(
		                    "jdbc:sqlite:komunikator.sqlite");
			
			sqlStatement = sqlConnection.createStatement();
			
			
			sqlStatement.executeUpdate("UPDATE User SET online = 'FALSE', ip = '' WHERE username = '" + username + "' AND password = '" + password + "';");
			
			
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
		}
	}
	
	/** 
     * Sends a message from one user to another creating a message in the database
     *
     * @param fromUsername	the username of the sender
     * @param toUsername	the username of the receiver
     * @param message		the content of the message
     * @return			<code>true</code> if sending was succesful, <code>false</code> otherwise
     * @see				Connection
     * @see				Statement
     * @since           1.0
     */
	
	public synchronized static boolean sendMessage(String fromUsername,  String toUsername, String message)
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		if(!DatabaseCommunicator.exists(toUsername) || message.length() == 0) return false;
	
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
			
			sqlConnection = DriverManager.getConnection(
		                    "jdbc:sqlite:komunikator.sqlite");
			
			sqlStatement = sqlConnection.createStatement();
			
			ResultSet sqlResult = sqlStatement.executeQuery(
			         "SELECT max(messageid) FROM Message;");
			
			sqlResult.next();
			
			sqlStatement.executeUpdate("INSERT INTO Message VALUES ((SELECT max(messageid) from Message) + 1,'" + fromUsername + "', '" + toUsername + "', '" + message.replace("\n", " ") + "');");
			

			sqlResult.close();
			
			sqlConnection.close();
			
			return true;
			
			
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
			
			return false;
		}
	}
	
	/** 
     * Gets all the messages to an user from the database and putting them in a LinkedList of {@link Message} objects
     *
     * @param username	the users username in the database
     * @return			the reference to the <code>LinkedList</code> with the messages
     * @see				Connection
     * @see				Statement
     * @see				List
     * @see				LinkedList
     * @since           1.0
     */
	
	public synchronized static List<Message> getMessages(String username)
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		List<Message> result = new LinkedList<Message>();
		
		
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
			
			sqlConnection = DriverManager.getConnection(
		                    "jdbc:sqlite:komunikator.sqlite");
			
			sqlStatement = sqlConnection.createStatement();
			
			ResultSet sqlResult = sqlStatement.executeQuery(
			         "SELECT * FROM Message");
			

			while(sqlResult.next())
			{
				if(sqlResult.getString("to").equals(username))
				{
					Message message = new Message(sqlResult.getString("from"), sqlResult.getString("message"));
					
					result.add(message);
				}
			}
			
			sqlResult.close();
			
			sqlConnection.close();
			
			return result;
			
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
			
			return result;
		}
	}
	
	/** 
     * Gets all the messagesfrom the database and putting them in a LinkedList of {@link Message} objects
     *
     * @return			the reference to the <code>LinkedList</code> with the messages
     * @see				Connection
     * @see				Statement
     * @see				List
     * @see				LinkedList
     * @since           1.0
     */
	
	public synchronized static List<Message> getAllMessages()
	{
		Connection sqlConnection;
		
		Statement sqlStatement;
		
		List<Message> result = new LinkedList<Message>();
		
		
		try
		{
			Class.forName("org.sqlite.JDBC").newInstance();
			
			sqlConnection = DriverManager.getConnection(
		                    "jdbc:sqlite:komunikator.sqlite");
			
			sqlStatement = sqlConnection.createStatement();
			
			ResultSet sqlResult = sqlStatement.executeQuery(
			         "SELECT * FROM Message");
			

			while(sqlResult.next())
			{
				
				Message message = new Message(sqlResult.getString("from"), sqlResult.getString("message"));
					
				result.add(message);
				
			}
			
			sqlResult.close();
			
			sqlConnection.close();
			
			return result;
			
		}
		catch(Exception sqlException)
		{
			System.err.println(sqlException);
			
			return result;
		}
	}
}
