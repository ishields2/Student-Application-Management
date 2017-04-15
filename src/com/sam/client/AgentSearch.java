/**
 * This class represents the Agent Search view for staff. 
 * 
 * The various swing components for the view along with their
 * characteristics and attributes. Relevant listeners are added
 * to buttons and their action performed methods defined.
 * 
 * @author Ian Shields, 1506416
 * @version 07/09/2015
 */

package com.sam.client;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

import com.sam.server.Agent;
import com.sam.server.Application;
import com.sam.server.Enquiry;
import com.sam.server.User;


public class AgentSearch extends JFrame {

	// field variables
	private static final long serialVersioncompanyName = 1L;
	private final JLabel lblNewLabel = new JLabel("New label");
	
	private RoundedPanel roundedPanel = new RoundedPanel();
	
	
	// JLabels for each of the 5 application buttons to display key application info
	JLabel lblcontactName = new JLabel ("Contact Name:" + " " );
	JLabel lblcompanyName = new JLabel("Company Name:" );
	JLabel stats1 = new JLabel("Apps/Payments: " );
	JLabel lblAccountManager = new JLabel("Account Manager:" + " " );
	JLabel lblStatus = new JLabel("Status:" + " ");
	JLabel lblLastContacted = new JLabel("Last Contacted:" + " " );
	JLabel contactName_2 = new JLabel("Contact Name: ");
	JLabel companyName2 = new JLabel("Company Name:");
	JLabel lastContacted2 = new JLabel("Last Contacted: ");
	JLabel stats2 = new JLabel("Apps/Payments: ");
	JLabel AccountManager2 = new JLabel("Account Manager: ");
	JLabel status2 = new JLabel("Status: ");
	JLabel contactName3 = new JLabel("Contact Name: ");
	JLabel companyName3 = new JLabel("Company Name:");
	JLabel lastContacted3 = new JLabel("Last Contacted: ");
	JLabel stats3 = new JLabel("Apps/Payments: ");
	JLabel AccountManager3 = new JLabel("Account Manager: ");
	JLabel status3 = new JLabel("Status: ");
	JLabel contactName4 = new JLabel("Contact Name: ");
	JLabel lastContacted4 = new JLabel("Last Contacted: ");
	JLabel stats4 = new JLabel("Apps/Payments: ");
	JLabel status4 = new JLabel("Status: ");
	JLabel lastContacted5 = new JLabel("Last Contacted: ");
	JLabel AccountManager5 = new JLabel("Account Manager: ");
	JLabel status5 = new JLabel("Status: ");
	JLabel stats5 = new JLabel("Stats:");
	JLabel companyName5 = new JLabel("Company Name:");
	JLabel contactName5 = new JLabel("Contact Name: ");
	JLabel AccountManager4 = new JLabel("Account Manager: ");
	JLabel companyName4 = new JLabel("Company Name:");
	ArrayList<Agent>signedAgents = new ArrayList<Agent>();
	
	// JButtons to display each application
	JButton agent1Button = new JButton("");
	JButton agent2Button = new JButton("");
	JButton agent3Button = new JButton("");
	JButton agent4Button = new JButton("");
	JButton agent5Button = new JButton("");
	
	JLabel lblOf = new JLabel(" ");
	
	// this keeps track of the number of applications that have been displayed (initially it is 0)
	int i = 0;
	int j = 1;
	
	// constructor
	public AgentSearch(User user) {
		
		// set JFrame properties
		this.setVisible(true);
		this.getContentPane().setBackground(Color.WHITE);
		this.setBounds(100, 100, 1050, 630);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		// set panel properties
		roundedPanel.setForeground(new Color(142, 39, 148));
		roundedPanel.setBackground(Color.WHITE);
		roundedPanel.setBounds(284, 104, 732, 474);
		this.getContentPane().add(roundedPanel);
		roundedPanel.setLayout(null);
		
		lblNewLabel.setBounds(40, -30, 200, 31);
		this.getContentPane().add(lblNewLabel);
		
	    this.setVisible(true);
		
	   // label for displaying logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(10, 0, 240, 100);
		this.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// title for JFrame
		this.setTitle("Student Application System");
		
		// logout button and action performed method to close GUI
		JButton logoutButton = new JButton("Back");
		logoutButton.setForeground(Color.WHITE);
		logoutButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		logoutButton.setBackground(new Color(142, 39, 148));
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
						dispose();
				}
			});
		logoutButton.setBounds(775, 52, 234, 41);
		this.getContentPane().add(logoutButton);
			
		// welcome user label displays welcome message plus users contactName
		JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername());
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		welcomeLabel.setForeground(new Color(142, 39, 148));
		welcomeLabel.setBounds(379, 11, 326, 25);
		this.getContentPane().add(welcomeLabel);
		
		// search text field
				JTextField searchBox = new JTextField();
				searchBox.setBounds(284, 59, 286, 31);
				searchBox.setBorder(new RoundedCornerBorder());
				this.getContentPane().add(searchBox);
				searchBox.setColumns(10);
				
				JComboBox searchCombo = new JComboBox();
				searchCombo.setModel(new DefaultComboBoxModel(new String[] {"Applications", "Enquiries", "Agents"}));
				searchCombo.setForeground(Color.WHITE);
				searchCombo.setFont(new Font("Tahoma", Font.BOLD, 11));
				searchCombo.setBackground(new Color(142, 39, 148));
				searchCombo.setBounds(663, 63, 102, 25);
				this.getContentPane().add(searchCombo);
				
				JLabel warningLabelSearch = new JLabel("Please enter a search term");
				warningLabelSearch.setForeground(Color.RED);
				warningLabelSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
				warningLabelSearch.setBounds(302, 35, 211, 20);
				warningLabelSearch.setVisible(false);
				this.getContentPane().add(warningLabelSearch);
				
				JButton searchButton = new JButton("Search");
				searchButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(searchBox.getText().equals("") ){
							
							warningLabelSearch.setForeground(Color.RED);
							warningLabelSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
							warningLabelSearch.setBounds(369, 64, 211, 20);
							warningLabelSearch.setVisible(true);
							
						}
						
						else{
							if(searchCombo.getSelectedItem().equals("Applications")){
								user.setCommand(searchBox.getText());
								user.setSearchApps(new ArrayList<Application>());
								ApplicationSearch as = new ApplicationSearch(user);
								dispose();
							}
							
							else if(searchCombo.getSelectedItem().equals("Enquiries")){
								user.setCommand(searchBox.getText());
								user.setSearchEnqs(new ArrayList<Enquiry>());
								EnquirySearch es = new EnquirySearch(user);
								dispose();
							}
							else{
								user.setCommand(searchBox.getText());
								user.setSearchAgents(new ArrayList<Agent>());
								AgentSearch as = new AgentSearch(user); 
								dispose();
							}
						}
					}
				});
				searchButton.setForeground(Color.WHITE);
				searchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
				searchButton.setBackground(new Color(142, 39, 148));
				searchButton.setBounds(580, 59, 73, 31);
				this.getContentPane().add(searchButton);
		
		// creation of JLabel	
		JLabel lblViewApplications = new JLabel("Search Results:");
		lblViewApplications.setForeground(new Color(142, 39, 148));
		lblViewApplications.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblViewApplications.setBounds(10, 11, 265, 17);
		roundedPanel.add(lblViewApplications);
		
		// creation of JButton
		JButton button_pre = new JButton("<< Previous");
		button_pre.setBackground(new Color(142, 39, 148));
		button_pre.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_pre.setForeground(Color.WHITE);
		button_pre.setBounds(153, 440, 114, 23);
		roundedPanel.add(button_pre);
		
		// creation of JButton
	 	JButton btnNext = new JButton();
	 	btnNext.setText("Next >>");
	 	btnNext.setBackground(new Color(142, 39, 148));
		btnNext.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNext.setForeground(Color.WHITE);
		btnNext.setBounds(419, 440, 105, 23);
		roundedPanel.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				i = i+5;
				
				// if i is less than appsList.size it means there are more applications to display
				if(i < user.getUserApplications().size());
				
				// call method to display the next five applications	
					j = j + 5;
				
					Controller.nextAgents(signedAgents, agent1Button, agent2Button,agent3Button,  agent4Button,
							 agent5Button,  lblcontactName,  lblcompanyName,  stats1,  lblAccountManager,  lblStatus, lblLastContacted,
							 contactName_2, companyName2,  stats2,  AccountManager2,  status2,  lastContacted2, 
							 contactName3,  companyName3,  stats3,  AccountManager3, status3,  lastContacted3,
							 contactName4,  companyName4, stats4,  AccountManager4,  status4,  lastContacted4,
							 contactName5,  companyName5,  stats5,  AccountManager5,  status5,  lastContacted5,  lblOf, i, j);
			}
		});
		
		// creation of JLabel
		lblOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblOf.setForeground(new Color(142, 39, 148));
		lblOf.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOf.setBounds(269, 444, 150, 19);
		roundedPanel.add(lblOf);
		
		// set parameters of JLabel
		lblcontactName.setText("contactName:" + " " );
		lblcontactName.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblcontactName.setForeground(Color.WHITE);
		lblcontactName.setBounds(20, 45, 211, 14);
		roundedPanel.add(lblcontactName);
		
		// set parameters of JLabel
		lblcompanyName.setFont(new Font("Tahoma", Font.BOLD, 10));			
		lblcompanyName.setForeground(Color.WHITE);
		lblcompanyName.setBounds(251, 45, 188, 14);
		roundedPanel.add(lblcompanyName);
		
		// set parameters of JLabel
		stats1.setFont(new Font("Tahoma", Font.BOLD, 10));
		stats1.setForeground(Color.WHITE);
		stats1.setBounds(20, 75, 220, 14);
		roundedPanel.add(stats1);
		
		// set parameters of JLabel
		lblAccountManager.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblAccountManager.setForeground(Color.WHITE);
		lblAccountManager.setBounds(250, 75, 220, 14);
		roundedPanel.add(lblAccountManager);
		
		// set parameters of JLabel
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setBounds(521, 75, 220, 14);
		roundedPanel.add(lblStatus);
		
		// set parameters of JLabel
		lblLastContacted.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblLastContacted.setForeground(Color.WHITE);
		lblLastContacted.setBounds(521, 45, 220, 14);
		roundedPanel.add(lblLastContacted);
		
		// set parameters of JLabel
		contactName_2.setForeground(Color.WHITE);
		contactName_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		contactName_2.setBounds(20, 127, 211, 14);
		roundedPanel.add(contactName_2);
		
		// set parameters of JLabel
		companyName2.setForeground(Color.WHITE);
		companyName2.setFont(new Font("Tahoma", Font.BOLD, 10));
		companyName2.setBounds(252, 127, 188, 14);
		roundedPanel.add(companyName2);
		
		// set parameters of JLabel
		lastContacted2.setForeground(Color.WHITE);
		lastContacted2.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastContacted2.setBounds(521, 127, 220, 14);
		roundedPanel.add(lastContacted2);
		
		// set parameters of JLabel
		stats2.setForeground(Color.WHITE);
		stats2.setFont(new Font("Tahoma", Font.BOLD, 10));
		stats2.setBounds(20, 157, 220, 14);
		roundedPanel.add(stats2);
		
		// set parameters of JLabel
		AccountManager2.setForeground(Color.WHITE);
		AccountManager2.setFont(new Font("Tahoma", Font.BOLD, 10));
		AccountManager2.setBounds(251, 157, 220, 14);
		roundedPanel.add(AccountManager2);
		
		// set parameters of JLabel
		status2.setForeground(Color.WHITE);
		status2.setFont(new Font("Tahoma", Font.BOLD, 10));
		status2.setBounds(521, 157, 220, 14);
		roundedPanel.add(status2);
		
		// set parameters of JLabel
		contactName3.setForeground(Color.WHITE);
		contactName3.setFont(new Font("Tahoma", Font.BOLD, 10));
		contactName3.setBounds(20, 210, 211, 14);
		roundedPanel.add(contactName3);
		
		// set parameters of JLabel
		companyName3.setForeground(Color.WHITE);
		companyName3.setFont(new Font("Tahoma", Font.BOLD, 10));
		companyName3.setBounds(252, 210, 188, 14);
		roundedPanel.add(companyName3);
		
		// set parameters of JLabel
		lastContacted3.setForeground(Color.WHITE);
		lastContacted3.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastContacted3.setBounds(524, 210, 220, 14);
		roundedPanel.add(lastContacted3);
		
		// set parameters of JLabel
		stats3.setForeground(Color.WHITE);
		stats3.setFont(new Font("Tahoma", Font.BOLD, 10));
		stats3.setBounds(20, 240, 220, 14);
		roundedPanel.add(stats3);
		
		// set parameters of JLabel
		AccountManager3.setForeground(Color.WHITE);
		AccountManager3.setFont(new Font("Tahoma", Font.BOLD, 10));
		AccountManager3.setBounds(251, 240, 220, 14);
		roundedPanel.add(AccountManager3);
		
		// set parameters of JLabel
		status3.setForeground(Color.WHITE);
		status3.setFont(new Font("Tahoma", Font.BOLD, 10));
		status3.setBounds(524, 240, 220, 14);
		roundedPanel.add(status3);
		
		// set parameters of JLabel
		contactName4.setForeground(Color.WHITE);
		contactName4.setFont(new Font("Tahoma", Font.BOLD, 10));
		contactName4.setBounds(21, 290, 211, 14);
		roundedPanel.add(contactName4);
		
		// set parameters of JLabel
		companyName4.setForeground(Color.WHITE);
		companyName4.setFont(new Font("Tahoma", Font.BOLD, 10));
		companyName4.setBounds(253, 290, 188, 14);
		roundedPanel.add(companyName4);
		
		// set parameters of JLabel
		lastContacted4.setForeground(Color.WHITE);
		lastContacted4.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastContacted4.setBounds(525, 290, 220, 14);
		roundedPanel.add(lastContacted4);
		
		// set parameters of JLabel
		stats4.setForeground(Color.WHITE);
		stats4.setFont(new Font("Tahoma", Font.BOLD, 10));
		stats4.setBounds(21, 320, 220, 14);
		roundedPanel.add(stats4);
		
		// set parameters of JLabel
		AccountManager4.setForeground(Color.WHITE);
		AccountManager4.setFont(new Font("Tahoma", Font.BOLD, 10));
		AccountManager4.setBounds(252, 320, 220, 14);
		roundedPanel.add(AccountManager4);
		
		// set parameters of JLabel
		status4.setForeground(Color.WHITE);
		status4.setFont(new Font("Tahoma", Font.BOLD, 10));
		status4.setBounds(525, 320, 220, 14);
		roundedPanel.add(status4);
		
		// set parameters of JLabel
		contactName5.setForeground(Color.WHITE);
		contactName5.setFont(new Font("Tahoma", Font.BOLD, 10));
		contactName5.setBounds(21, 371, 211, 14);
		roundedPanel.add(contactName5);
		
		// set parameters of JLabel
		companyName5.setForeground(Color.WHITE);
		companyName5.setFont(new Font("Tahoma", Font.BOLD, 10));
		companyName5.setBounds(253, 371, 188, 14);
		roundedPanel.add(companyName5);
		
		// set parameters of JLabel
		lastContacted5.setForeground(Color.WHITE);
		lastContacted5.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastContacted5.setBounds(525, 371, 220, 14);
		roundedPanel.add(lastContacted5);
		
		// set parameters of JLabel
		stats5.setForeground(Color.WHITE);
		stats5.setFont(new Font("Tahoma", Font.BOLD, 10));
		stats5.setBounds(21, 401, 220, 14);
		roundedPanel.add(stats5);
		
		// set parameters of JLabel
		AccountManager5.setForeground(Color.WHITE);
		AccountManager5.setFont(new Font("Tahoma", Font.BOLD, 10));
		AccountManager5.setBounds(252, 401, 220, 14);
		roundedPanel.add(AccountManager5);
		
		// set parameters of JLabel
		status5.setForeground(Color.WHITE);
		status5.setFont(new Font("Tahoma", Font.BOLD, 10));
		status5.setBounds(525, 401, 220, 14);
		roundedPanel.add(status5);
		agent1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		// define attributes of app1Button
		agent1Button.setForeground(Color.WHITE);
		agent1Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		agent1Button.setBackground(new Color(142, 39, 148));
		agent1Button.setBounds(10, 35, 704, 70);
		roundedPanel.add(agent1Button);
		agent1Button.setVisible(false);
		agent2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		

		// define attributes of app2Button
		agent2Button.setForeground(Color.WHITE);
		agent2Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		agent2Button.setBackground(new Color(142, 39, 148));
		agent2Button.setBounds(10, 116, 704, 70);
		roundedPanel.add(agent2Button);
		agent2Button.setVisible(false);
		agent3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		

		
		// define attributes of app3Button
		agent3Button.setForeground(Color.WHITE);
		agent3Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		agent3Button.setBackground(new Color(142, 39, 148));
		agent3Button.setBounds(10, 197, 704, 70);
		roundedPanel.add(agent3Button);
		agent3Button.setVisible(false);
		agent4Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		

		
		// define attributes of app4Button
		agent4Button.setForeground(Color.WHITE);
		agent4Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		agent4Button.setBackground(new Color(142, 39, 148));
		agent4Button.setBounds(10, 278, 704, 70);
		roundedPanel.add(agent4Button);
		agent4Button.setVisible(false);
		agent5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		

		// define attributes of app5Button
		agent5Button.setForeground(Color.WHITE);
		agent5Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		agent5Button.setBackground(new Color(142, 39, 148));
		agent5Button.setBounds(10, 359, 704, 70);
		roundedPanel.add(agent5Button);
		
		Controller.addViewAgentButtonListeners(user, agent1Button, agent2Button, agent3Button, agent4Button, agent5Button, lblcompanyName, companyName2, companyName3, companyName4, companyName5);
		agent5Button.setVisible(false);

		
		Controller.searchAgents(user);
		
		
		
		Controller.nextAgents(user.getSearchAgents(), agent1Button, agent2Button,agent3Button,  agent4Button,
				 agent5Button,  lblcontactName,  lblcompanyName,  stats1,  lblAccountManager,  lblStatus, lblLastContacted,
				 contactName_2, companyName2,  stats2,  AccountManager2,  status2,  lastContacted2, 
				 contactName3,  companyName3,  stats3,  AccountManager3, status3,  lastContacted3,
				 contactName4,  companyName4, stats4,  AccountManager4,  status4,  lastContacted4,
				 contactName5,  companyName5,  stats5,  AccountManager5,  status5,  lastContacted5,  lblOf, i, j);
		
		JButton btnAddNewAgent = new JButton();
		btnAddNewAgent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNewAgent ana = new AddNewAgent(user);
				dispose();
			}
		});
		btnAddNewAgent.setText("Add New Agent");
		btnAddNewAgent.setForeground(Color.WHITE);
		btnAddNewAgent.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAddNewAgent.setBackground(new Color(142, 39, 148));
		btnAddNewAgent.setBounds(550, 7, 164, 25);
		roundedPanel.add(btnAddNewAgent);
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setLayout(null);
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(10, 104, 245, 474);
		getContentPane().add(roundedPanel_1);
		
		JLabel label = new JLabel("Dashboard");
		label.setForeground(new Color(142, 39, 148));
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(76, 11, 98, 17);
		roundedPanel_1.add(label);
		
		JButton button = new JButton("Unsigned Agents");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		button.setBackground(new Color(142, 39, 148));
		button.setBounds(10, 383, 220, 32);
		roundedPanel_1.add(button);
		
		JButton button_1 = new JButton("Agents");
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_1.setBackground(new Color(142, 39, 148));
		button_1.setBounds(10, 340, 220, 32);
		roundedPanel_1.add(button_1);
		
		JButton button_2 = new JButton("Enrolled Students");
		button_2.setForeground(Color.WHITE);
		button_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_2.setBackground(new Color(142, 39, 148));
		button_2.setBounds(10, 297, 220, 32);
		roundedPanel_1.add(button_2);
		
		JButton button_3 = new JButton("View Applications");
		button_3.setForeground(Color.WHITE);
		button_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_3.setBackground(new Color(142, 39, 148));
		button_3.setBounds(10, 254, 220, 32);
		roundedPanel_1.add(button_3);
		
		JButton button_4 = new JButton("Add Group Application");
		button_4.setForeground(Color.WHITE);
		button_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_4.setBackground(new Color(142, 39, 148));
		button_4.setBounds(10, 211, 220, 32);
		roundedPanel_1.add(button_4);
		
		JButton button_5 = new JButton("Add Application");
		button_5.setForeground(Color.WHITE);
		button_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_5.setBackground(new Color(142, 39, 148));
		button_5.setBounds(10, 168, 220, 32);
		roundedPanel_1.add(button_5);
		
		JButton button_6 = new JButton("View Enquiries");
		button_6.setForeground(Color.WHITE);
		button_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_6.setBackground(new Color(142, 39, 148));
		button_6.setBounds(10, 125, 220, 32);
		roundedPanel_1.add(button_6);
		
		JButton button_7 = new JButton("Add Enquiry");
		button_7.setForeground(Color.WHITE);
		button_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_7.setBackground(new Color(142, 39, 148));
		button_7.setBounds(10, 82, 220, 32);
		roundedPanel_1.add(button_7);
		
		JButton button_8 = new JButton("Home");
		button_8.setForeground(Color.WHITE);
		button_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_8.setBackground(new Color(142, 39, 148));
		button_8.setBounds(10, 39, 220, 32);
		roundedPanel_1.add(button_8);
		
		JButton button_9 = new JButton("Documents");
		button_9.setForeground(Color.WHITE);
		button_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_9.setBackground(new Color(142, 39, 148));
		button_9.setBounds(10, 426, 220, 32);
		roundedPanel_1.add(button_9);
		
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			dispose();
			}
		});

button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddEnquiry ae = new AddEnquiry(user);
				dispose();
			}
		});

button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewEnquiries ve = new ViewEnquiries(user);
				dispose();
			}
		});
		

		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddApplicationStaff aas = new AddApplicationStaff(user);
				dispose();
			}
		});

button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddGroupApplicationStaff a = new AddGroupApplicationStaff(user);
					dispose();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewApplications va = new ViewApplications(user);
				dispose();
			}
		});
		
button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EnrolledStudentsViewStaff esv = new EnrolledStudentsViewStaff(user);
				dispose();
			}
		});
		
button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignedAgentsView sav = new SignedAgentsView(user);
				dispose();
			}
		});
		
button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnsignedAgentsView usv = new UnsignedAgentsView(user);
				dispose();
			}
		});
		
button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageDocumentsView mdv = new ManageDocumentsView(user);
				dispose();
			}
		});
				
	}
}
