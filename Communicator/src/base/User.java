package base;

/**
 * Class containing user information. Contains four fields with the username, 
 * password, online status and current ip adress if connected
 * 
 * @author      Marcin Gorczyñski
 * @version     1.0
 */

public final class User
{
	/** 
     * The constructor of the User class.
     *
     * @param username	the username of the user
     * @param password	the users password
     * @param online	if the user is online
     * @param ip		the current ip adress of the users client
     * @since           1.0
     */
	
	public User(String username, String password, String online, String ip)
	{
		this.username = username;
		
		this.password = password;
		
		this.online = online;
		
		this.ip = ip;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getOnline()
	{
		return online;
	}
	
	public String getIP()
	{
		return ip;
	}
	
	public String[] getAll()
	{
		String[] all = new String[4];
		
		all[0] = username;
		all[1] = password;
		all[2] = online;
		all[3] = ip;
		
		return all;
	}
	
	private String username;
	
	private String password;
	
	private String online;
	
	private String ip;
}
