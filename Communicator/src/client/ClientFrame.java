package client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.PrintWriter;

import java.awt.event.*;

import javax.swing.JFrame;

import server.ServerPanel;

/**
 * Class containing the window(swing frame) of the client GUI main window, extends the JFrame class
 * It has a set, non-resizable dimension based on screen resolution
 * All of the active elements are contained in the panel object
 * The constructor takes two references for server communication purposes
 * It logs out the user on closing
 * 
 * @author      Marcin Gorczyñski
 * @see         JFrame
 * @see         ClientPanel
 * @version     1.0
 */

public final class ClientFrame extends JFrame 
{
	/** 
     * The constructor of the ServerFrame class. 
     * Sets the size of the frame, sets basic properietes and adds a {@link ClientPanel} class object
     * Creates an CloseAction listener class object linking it to the frame
     *
     * @param	in		reference to the BufferedReader object listening on the client port
     * @param   out		reference to the PrintWriter object listening on the client port
     * @see				BufferedReader
     * @see             PrintWriter
     * @see				Toolkit
     * @see				ClientPanel
     * @since           1.0
     */
	
	public ClientFrame(BufferedReader in, PrintWriter out)
	{
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		CloseAction closeAction = new CloseAction();
		
		ClientPanel panel = new ClientPanel(this, in, out);
		
		this.out = out;
		
		Dimension screenSize = kit.getScreenSize();
		
		setSize(screenSize.width/6, screenSize.height/2);
		
		setLocationByPlatform(true);
		
		setTitle("Client");
		
		setResizable(false);
		
		this.addWindowListener(closeAction);
		
		add(panel);
		
		
		setVisible(false);
		
		setEnabled(false);
	}
	
	/** 
     * The inner class for the action listener that listens for actions on the window
     * Implements the ActionListener interface
     * Closing the window (pressing "x") calls the logout(...) method from the {@link ServerCommunicator} class and exits the application
     *
     * @see				ActionListener
     * @see				ServerCommunicator
     * @since           1.0
     */
	
	private class CloseAction implements WindowListener, ActionListener
	{
		public void windowClosing(WindowEvent e) 
		{
			ServerCommunicator.logout(out);
            System.exit(0);
		}
		
		public void actionPerformed(ActionEvent e) {}
		
		public void windowOpened(WindowEvent e) {}
        public void windowActivated(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}
	}
	
	
	private PrintWriter out;
}
