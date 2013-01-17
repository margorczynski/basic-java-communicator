package client;

import java.awt.BorderLayout;

import base.Message;
import base.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import server.ServerFrame;
import server.ServerPanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.PrintWriter;

import java.util.List;

/**
 * Class containing the panel of the {@link ClientFrame} class, extends the JPanel class
 * It contains a text area for the message content, a text field for the user username to send it to and two button "Send message" and "Get messages"
 * Also contains a simple text label "To: " near the text field with the username
 * The buttons functions are depicted by their respective names
 * If the message was sent succesfully a information popup is shown and the receiver text field and message area are cleared 
 * If the message was sent unsuccesfully a error popup is shown and the receiver text field is cleared 
 * Contains four panels for layout purposes
 * The constructor takes in three refrences,  one for the parent frame, and two for calling methods communicating with the server
 * 
 * @author      Marcin Gorczyñski
 * @see         JPanel
 * @see         ClientFrame
 * @version     1.0
 */

public final class ClientPanel extends JPanel 
{
	/** 
     * The constructor of the ClientPanel class. 
     * Takes three references to objects, ref is for the parent frame, in and out for calling methods communicating with the server
     * Creates and sets the properietes of the elements of the panel (two buttons, one label, one text field, one text area and four panels)
     * Sets the proper layout of the panel components
     *
     * @param	ref		reference to the ClientFrame frame that contains the panel
     * @param   in      reference to the BufferedReader class object for server communication
     * @param   out     reference to the PrintWriter class object for server communication
     * @see				ServerPanel
     * @see				JFrame
     * @see             JButton
     * @see             JLabel
     * @see             JTextField
     * @see				JTextArea
     * @see             BufferedReader
     * @see             PrintWriter
     * @since           1.0
     */
	
	public ClientPanel(ClientFrame ref, BufferedReader in, PrintWriter out)
	{	
		this.out = out;
		
		this.in = in;
		
		this.ref = ref;
		
		setLayout(new BorderLayout());
		
		new RegisterLoginFrame(ref, in, out);
		
		
		addButton("Send message", new SendMessageAction(), buttonPanel);
		
		addButton("Get messages", new GetMessagesAction(), buttonPanel);
		
		messageArea.setLineWrap(true);
		
		messageArea.setBorder(BorderFactory.createLoweredBevelBorder());
		
		textPanel.add(messageArea);
		
		bottomPanel.setLayout(new GridLayout(2,1));
		
		fieldPanel.setLayout(new BorderLayout());
		
		fieldPanel.add(receiverLabel, BorderLayout.WEST);
		
		fieldPanel.add(receiverField, BorderLayout.CENTER);
		
		bottomPanel.add(fieldPanel);
		
		bottomPanel.add(buttonPanel);
		
		
		
		
		add(textPanel, BorderLayout.CENTER);
		
		add(bottomPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	/** 
     * A simple method used to create a button with an action listener and adding it to a panel
     *
     * @param	label		the label on the button
     * @param   listener	the action listener linked to the button
     * @param   panel  		the reference to the panel that should contain the button
     * @see					ActionListener
     * @see					JButton
     * @see					JPanel
     * @since          		1.0
     */
	
	private void addButton(String label, ActionListener listener, JPanel panel)
	{
		JButton button = new JButton(label);
		
		button.addActionListener(listener);
		
		panel.add(button);
	}
	
	/** 
     * The inner class for the action listener that listens for actions on the "Get messages" button
     * Implements the ActionListener interface
     * Pressing the button gets all messages for the user from the database using the getMessages(...) method from the {@link ServerCommunicator} class
     * Then for every message it spawns a {@link MessageFrame} class object with the message reference as the constructor paremeter
     *
     * @see				ActionListener
     * @see				Message
     * @see             List
     * @see             ServerCommunicator
     * @see             MessageFrame
     * @since           1.0
     */
	
	private class GetMessagesAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			List<Message> messages = ServerCommunicator.getMessages(in, out);
			
			for(Message m : messages)
			{
				new MessageFrame(m);
			}
		}
	}
	
	/** 
     * The inner class for the action listener that listens for actions on the "Send message" button
     * Implements the ActionListener interface
     * Pressing the button calls and checks the result of the sendMessage(...) method from the {@link ServerCommunicator} class
     * If the result is true it sends the message, clears the text components and spawns a information popup
     * If the result is false it clears the receiver text field and spawns an error popup
     *
     * @see				ActionListener
     * @see             ServerCommunicator
     * @see             JOptionPane
     * @since           1.0
     */
	
	private class SendMessageAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(ServerCommunicator.sendMessage(receiverField.getText(), messageArea.getText(), in, out))
			{
				receiverField.setText("");
				
				messageArea.setText("");
				
				JOptionPane.showMessageDialog(ref,
					    "Message sent!");
			}
			else
			{
				receiverField.setText("");
				
				JOptionPane.showMessageDialog(ref,
					    "User doesn't exist or empty field",
					    "Sending Error",
					    JOptionPane.ERROR_MESSAGE);
			}
			
			
		}
	}
	
	private ClientFrame ref;
	
	private BufferedReader in;
	
	private PrintWriter out;
	
	private final JPanel textPanel = new JPanel();
	
	private final JPanel buttonPanel = new JPanel();
	
	private final JPanel fieldPanel = new JPanel();
	
	private final JPanel bottomPanel = new JPanel();
	
	private final JTextArea messageArea = new JTextArea(25, 24);
	
	private final JTextField receiverField = new JTextField();
	
	private final JLabel receiverLabel = new JLabel("To: ");
}
