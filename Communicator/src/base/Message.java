package base;

/**
 * Class containing message information. Contains two fields with the content and
 * the username from whom it was received
 * 
 * @author      Marcin Gorczyñski
 * @version     1.0
 */

public final class Message 
{
	/** 
     * The constructor of the Message class.
     *
     * @param fromUsername	the username of the user who sended the message
     * @param message		the content of the message
     * @since           1.0
     */
	
	public Message(String fromUsername, String message)
	{
		this.fromUsername = fromUsername;
		
		this.message = message;
	}
	
	public String getFromUsername()
	{
		return fromUsername;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String[] getAll()
	{
		String[] all = new String[2];
		
		all[0] = fromUsername;
		all[1] = message;
		
		return all;
	}
	
	private String fromUsername;
	
	private String message;
}