package server;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.Socket;

import javax.swing.*;

/**
 * Class containing the window(swing frame) of the server GUI extending the JFrame class
 * It has a set, non-resizable dimension based on screen resolution
 * All of the active elements are contained in the panel object
 * 
 * @author      Marcin Gorczyñski
 * @see         JFrame
 * @see         ServerPanel
 * @version     1.0
 */

public final class ServerFrame extends JFrame 
{
	/** 
     * The constructor of the ServerFrame class. 
     * Sets the size of the frame, sets basic properietes and adds a {@link ServerPanel} class object
     *
     * @see				ServerPanel
     * @see				JFrame
     * @since           1.0
     */
	
	public ServerFrame()
	{
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		ServerPanel panel = new ServerPanel(this);
		
		
		Dimension screenSize = kit.getScreenSize();
		
		setSize(screenSize.width/3, screenSize.height/2);
		
		setLocationByPlatform(true);
		
		setTitle("Server");
		
		setResizable(false);
		
		
		add(panel);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}
}
