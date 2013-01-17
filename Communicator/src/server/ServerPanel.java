package server;

import javax.swing.*;
import javax.swing.table.*;

import base.*;

import java.awt.*;
import java.awt.event.*;

import java.util.List;

/**
 * Class containing the panel of the {@link ServerFrame} class, extends the JPanel class
 * It contains two tables that show information about the users and messages in the database
 * Contains one "Refresh" button that call the updateData() method that updates the information contained in the tables with the data from the database
 * The top panel contains the JLabels with the table names
 * The bottom panel contains the refresh button
 * The userTable and messageTable panels contain the tables respective to their names
 * The main thing that stands out is the passing of the ServerFrame reference to the constructor and not using the getParen() method
 * 
 * @author      Marcin Gorczyñski
 * @see         JPanel
 * @see         ServerFrame
 * @see 		Message
 * @see         User
 * @see         List
 * @version     1.0
 */

public final class ServerPanel extends JPanel 
{
	/** 
     * The constructor of the ServerPanel class. 
     * Initiates the data contained in userData and messageData creating using it two tables
     * Creates a action listener refreshAction tied to the "Refresh" button added later using the addButton(...) method
     * Sets the proper layout of the panel components
     *
     * @param	ref		the reference to the ServerFrame frame that contains the panel
     * @see				ServerPanel
     * @see				JFrame
     * @see             JButton
     * @see             JLabel
     * @see             JTable
     * @see             Message
     * @see             User
     * @since           1.0
     */
	
	public ServerPanel(ServerFrame ref)
	{
		setLayout(new BorderLayout());
		
		RefreshAction refreshAction = new RefreshAction();
		
		String[] columnNamesUser = {"Username", "Password", "Online", "IP Adress"}; 
		
		String[] columnNamesMessage = {"Sender username", "Message content"};
		
		List<User> users = DatabaseCommunicator.getAllUsers();
		
		List<Message> messages = DatabaseCommunicator.getAllMessages();
		
		userData = new String[30][4];
		
		messageData = new String[30][2];
		
		int i = 0;
		
		for(User u : users)
		{
			userData[i] = u.getAll();
			
			i++;
			
			if(i >= 30) break;
		}
		
		i = 0;
		
		for(Message m : messages)
		{
			messageData[i] = m.getAll();
			
			i++;
			
			if(i >= 30) break;
		}
		
		JLabel userLabel = new JLabel("User data");
		
		JLabel messageLabel = new JLabel("Message data");
		
		topPanel.setLayout(new BorderLayout());
		
		topPanel.add(userLabel, BorderLayout.WEST);
		
		topPanel.add(messageLabel, BorderLayout.EAST);
		
		
		
		messageTable = new JTable(messageData, columnNamesMessage);
		
		userTable = new JTable(userData, columnNamesUser);
		
		
		userTable.setEnabled(false);

		messageTable.setEnabled(false);
		
		
		userTablePanel.add(userTable);
		
		messageTablePanel.add(messageTable);
		
		
		userTable.setFillsViewportHeight(true);
		
		messageTable.setFillsViewportHeight(true);
		
		addButton("Refresh", refreshAction, bottomPanel);
		
		
		add(topPanel, BorderLayout.NORTH);
		
		add(bottomPanel, BorderLayout.SOUTH);
		
		add(userTablePanel, BorderLayout.WEST);
		
		add(messageTablePanel, BorderLayout.EAST);
		
		
		
		
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
     * Updates the data contained in userData and serverData with actual data from the database
     * Uses the getAllUsers() and getAllMessages() methods from {@link DatabaseCommunicator} for geting the data
     *
     * @see				DatabaseCommunicator
     * @see				JTable
     * @since           1.0
     */
	
	private void updateData()
	{
		String[] columnNamesUser = {"Username", "Password", "Online", "IP Adress"}; 
		
		String[] columnNamesMessage = {"Sender username", "Message content"};
		
		List<User> users = DatabaseCommunicator.getAllUsers();
		
		List<Message> messages = DatabaseCommunicator.getAllMessages();
		
		int i = 0;
		
		for(User u : users)
		{		
			userData[i] = u.getAll();
		
			
			i++;
			
			if(i >= 30) break;
		}
		
		i = 0;
		
		for(Message m : messages)
		{
			messageData[i] = m.getAll();
			
			i++;
			
			if(i >= 30) break;
		}
		
		userTable.setModel(new DefaultTableModel(userData, columnNamesUser));
		
		messageTable.setModel(new DefaultTableModel(messageData, columnNamesMessage));
	}
	
	/** 
     * The inner class for the action listener that listens for actions on the "Refresh" button
     * Implements the ActionListener interface
     * Pressing the button executes a call of the updateData() method
     *
     * @see				ActionListener
     * @see				updateData()
     * @since           1.0
     */
	
	private class RefreshAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			updateData();
		}
	}
	
	
	
	private String[][] userData;
	
	private String[][] messageData;
	
	private final JPanel userTablePanel = new JPanel();
	
	private final JPanel messageTablePanel = new JPanel();
	
	private final JPanel topPanel = new JPanel();
	
	private final JPanel bottomPanel = new JPanel();
	
	private JTable userTable;
	
	private JTable messageTable;
}
