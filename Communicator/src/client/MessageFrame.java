package client;

import javax.swing.*;

import base.Message;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Class containing the window(swing frame) of the client GUI message window, extends the JFrame class
 * Its used to show the messages sent to an user
 * It has a set, non-resizable dimension based on screen resolution
 * The constructor takes an reference to the Message class object that contains the message
 * 
 * @author      Marcin Gorczyñski
 * @see         JFrame
 * @version     1.0
 */

public final class MessageFrame extends JFrame 
{
	/** 
     * The constructor of the MessageFrame class. 
     * Takes the reference to the object containing the message
     * Creates and sets the properietes of the elements of the frame (one label, one text field, one text area, one panel)
     * Sets the proper layout of the panel components
     *
     * @param	message reference to the object containing the message
     * @see				Message
     * @see				JFrame
     * @see             JLabel
     * @see             JTextField
     * @since           1.0
     */
	
	public MessageFrame(Message message)
	{
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		Dimension screenSize = kit.getScreenSize();
		
		setSize(screenSize.width/5, screenSize.height/5);
		
		setLocationByPlatform(true);
		
		setTitle("Message");
		
		setLayout(new BorderLayout());
		
		fromField.setBorder(BorderFactory.createCompoundBorder());
		
		fromField.setText(message.getFromUsername());
		
		contentArea.setText(message.getMessage());
		
		contentArea.setEditable(false);
		
		fromField.setEditable(false);
		
		topPanel.add(fromLabel);
		
		topPanel.add(fromField);
		
		add(topPanel, BorderLayout.NORTH);
		
		add(contentArea, BorderLayout.CENTER);
		
		setVisible(true);
		
		setEnabled(true);
		
		setResizable(false);
	}
	
	private final JPanel topPanel = new JPanel();
	
	private final JLabel fromLabel = new JLabel("From: ");
	
	private final JTextField fromField = new JTextField();
	
	private final JTextArea contentArea = new JTextArea(10, 20);
}
