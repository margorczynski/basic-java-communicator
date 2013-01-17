package client;

import java.awt.Dimension;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JFrame;

/**
 * Class containing the window(swing frame) of the client GUI register/login window, extends the JFrame class
 * Its closed after succesfull loging in and the main client frame is shown
 * It has a set, non-resizable dimension based on screen resolution
 * All of the active elements are contained in the panel object
 * The constructor takes three references for server communication purposes and the reference to the client frame
 * It exits the application on closing
 * 
 * @author      Marcin Gorczyñski
 * @see         JFrame
 * @see         RegisterLoginPanel
 * @version     1.0
 */

public final class RegisterLoginFrame extends JFrame 
{
	/** 
     * The constructor of the ServerFrame class. 
     * Sets the size of the frame, sets basic properietes and adds a {@link RegisterLoginPanel} class object
     * Creates an CloseAction listener class object linking it to the frame
     *
     * @param	ref		reference to the main client frame
     * @param	in		reference to the BufferedReader object listening on the client port
     * @param   out		reference to the PrintWriter object listening on the client port
     * @see				BufferedReader
     * @see             PrintWriter
     * @see				Toolkit
     * @see				RegisterLoginPanel
     * @since           1.0
     */
	
	public RegisterLoginFrame(ClientFrame ref, BufferedReader in, PrintWriter out)
	{
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		RegisterLoginPanel panel = new RegisterLoginPanel(ref, this, in, out);
		
		
		Dimension screenSize = kit.getScreenSize();
		
		setSize(screenSize.width/5, screenSize.height/10);
		
		setLocationByPlatform(true);
		
		setTitle("Login/Register");
		
		setResizable(false);
		
		
		add(panel);
		
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
