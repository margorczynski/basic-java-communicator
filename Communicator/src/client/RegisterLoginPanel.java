package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import base.Message;

import server.ServerPanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class containing the panel of the {@link RegisterLoginFrame} class, extends the JPanel class
 * It contains a text field and password field for username and password, two buttons "Login" and "Register" and two labels with information about the text/password field
 * The buttons functions are depicted by their respective names
 * After a succesfull login the register/login frame is closed and the main client frame is enabled/shown
 * Contains two panels for layout purposes
 * The constructor takes in four refrences,  one for the clients main frame object, one for the parent frame, and two for calling methods communicating with the server
 * 
 * @author      Marcin Gorczyñski
 * @see         JPanel
 * @see         ClientFrame
 * @see         RegisterLoginFrame
 * @version     1.0
 */

public final class RegisterLoginPanel extends JPanel 
{
	/** 
     * The constructor of the RegisterLoginPanel class. 
     * Takes four references to objects, pref for the main frame of the client, sref is for the parent frame, in and out for calling methods communicating with the server
     * Creates and sets the properietes of the elements of the panel (two buttons, two labels, one text field, one password field and two panels)
     * Creates two action listener objects from the inner classes and ties them to the buttons using the addButton(...) method
     * Sets the proper layout of the panel components
     *
     * @param	pref	reference to the ClientFrame that is the main frame of the client
     * @param   sref    reference to the RegisterLoginFrame parent frame
     * @param   in      reference to the BufferedReader class object for server communication
     * @param   out     reference to the PrintWriter class object for server communication
     * @see				ClientFrame
     * @see             RegisterLoginFrame
     * @see				JFrame
     * @see             JButton
     * @see             JLabel
     * @see             JTextField
     * @see             JPasswordField
     * @see             BufferedReader
     * @see             PrintWriter
     * @since           1.0
     */
	
	public RegisterLoginPanel(ClientFrame pref, RegisterLoginFrame sref, BufferedReader in, PrintWriter out)
	{
		this.pref = pref;
		
		this.sref = sref;
		
		this. in = in;
		
		this.out = out;
		
		LoginAction loginAction = new LoginAction();
		
		RegisterAction registerAction = new RegisterAction();
		
		setLayout(new BorderLayout());
		
		topPanel.setLayout(new GridLayout(2, 2));
		
		usernameField.setText("");
		
		passwordField.setText("");
		
		topPanel.add(userLabel);
		
		topPanel.add(usernameField);
		
		topPanel.add(passwordLabel);
		
		topPanel.add(passwordField);
		
		
		addButton("Login", loginAction, midPanel);
		
		addButton("Register", registerAction, midPanel);
		
		
		add(topPanel, BorderLayout.NORTH);
		
		add(midPanel, BorderLayout.CENTER);
		
		
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
     * The inner class for the action listener that listens for actions on the "Login" button
     * Implements the ActionListener interface
     * Pressing the button calls and checks the result of the login(...) method from the {@link ServerCommunicator} class
     * If the result is true it logs in the user, enables and shows the main client frame, hides and disposes of the register/login frame
     * If the result is false it clears the fields and creates an error popup
     *
     * @see				ActionListener
     * @see				ServerCommunicator
     * @see             JOptionPane
     * @since           1.0
     */
	
	private class LoginAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(!ServerCommunicator.login(usernameField.getText(), passwordField.getText(), in, out))
			{
				usernameField.setText("");
				
				passwordField.setText("");
				
				JOptionPane.showMessageDialog(sref,
					    "User doesn't exist or already logged",
					    "Login Error",
					    JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				pref.setVisible(true);
				
				pref.setEnabled(true);
				
				sref.setVisible(false);
				
				sref.dispose();
			}
		}
	}
	
	/** 
     * The inner class for the action listener that listens for actions on the "Register" button
     * Implements the ActionListener interface
     * Pressing the button calls and checks the result of the register(...) method from the {@link ServerCommunicator} class
     * If the result is true it creates the user, creates an information popup, logs in the user, destroyes the frame and shows the main client frame
     * If the result is false it creates an error popup
     *
     * @see				ActionListener
     * @see				ServerCommunicator
     * @see             JOptionPane
     * @since           1.0
     */
	
	private class RegisterAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(!ServerCommunicator.register(usernameField.getText(), passwordField.getText(), in, out))
			{
				JOptionPane.showMessageDialog(sref,
					    "User exists or wrong username",
					    "Register Error",
					    JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(pref,
					    "User created and logged in succesfully");
				
				ServerCommunicator.login(usernameField.getText(), passwordField.getText(), in, out);
				
				pref.setVisible(true);
				
				pref.setEnabled(true);
				
				sref.setVisible(false);
				
				sref.dispose();
			}
		}
	}
	
	private ClientFrame pref;
	
	private RegisterLoginFrame sref;
	
	private BufferedReader in;
	
	private PrintWriter out;
	
	private final JLabel userLabel = new JLabel("Username: ");
	
	private final JLabel passwordLabel = new JLabel("Password: ");
	
	private final JTextField usernameField = new JTextField();
	
	private final JPasswordField passwordField = new JPasswordField();
	
	private final JPanel topPanel = new JPanel();
	
	private final JPanel midPanel = new JPanel();
}
