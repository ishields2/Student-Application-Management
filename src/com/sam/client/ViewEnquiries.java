/**
 * This class represents the View Enquiries view for staff members. 
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


public class ViewEnquiries extends JFrame {
	

	// field variables
	private static final long serialVersioneid = 1L;
	private final JLabel lblNewLabel = new JLabel("New label");
	private RoundedPanel roundedPanel = new RoundedPanel();
	
	
	// JLabels for each of the 5 Enquiry buttons to display key Enquiry info
	JLabel lblName = new JLabel ("Name:" + " " );
	JLabel lbleid = new JLabel("EID:" );
	JLabel lblAgent = new JLabel("Agent:" );
	JLabel lblStaff = new JLabel("Staff:" + " " );
	JLabel lblStatus = new JLabel("Status:" + " ");
	JLabel lblLastUpdated = new JLabel("Last Updated:" + " " );
	JLabel name_2 = new JLabel("Name: ");
	JLabel eid2 = new JLabel("EID:");
	JLabel lastUpdated2 = new JLabel("Last Updated: ");
	JLabel agent2 = new JLabel("Agent:");
	JLabel staff2 = new JLabel("Staff: ");
	JLabel status2 = new JLabel("Status: ");
	JLabel name3 = new JLabel("Name: ");
	JLabel eid3 = new JLabel("EID:");
	JLabel lastUpdated3 = new JLabel("Last Updated: ");
	JLabel agent3 = new JLabel("Agent:");
	JLabel staff3 = new JLabel("Staff: ");
	JLabel status3 = new JLabel("Status: ");
	JLabel name4 = new JLabel("Name: ");
	JLabel lastUpdated4 = new JLabel("Last Updated: ");
	JLabel agent4 = new JLabel("Agent:");
	JLabel status4 = new JLabel("Status: ");
	JLabel lastUpdated5 = new JLabel("Last Updated: ");
	JLabel staff5 = new JLabel("Staff: ");
	JLabel status5 = new JLabel("Status: ");
	JLabel agent5 = new JLabel("Agent:");
	JLabel eid5 = new JLabel("EID:");
	JLabel name5 = new JLabel("Name: ");
	JLabel staff4 = new JLabel("Staff: ");
	JLabel eid4 = new JLabel("EID:");
	
	// JButtons to display each Enquiry
	JButton enq1Button = new JButton("");
	JButton enq2Button = new JButton("");
	JButton enq3Button = new JButton("");
	JButton enq4Button = new JButton("");
	JButton enq5Button = new JButton("");
	
	JLabel lblOf = new JLabel(" ");
	
	// this keeps track of the number of Enquirys that have been displayed (initially it is 0)
	int i = 0;
	int j = 1;
	
	// constructor
	public ViewEnquiries(User user) {
		
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
		roundedPanel.setBounds(265, 101, 744, 474);
		this.getContentPane().add(roundedPanel);
		roundedPanel.setLayout(null);
		
		lblNewLabel.setBounds(40, -30, 200, 31);
		this.getContentPane().add(lblNewLabel);
	    this.setVisible(true);
		
	   // label for displaying logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(10, 0, 245, 93);
		this.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// title for JFrame
		this.setTitle("Student Application Management");
		
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
					}
					
					else if(searchCombo.getSelectedItem().equals("Enquiries")){
						user.setCommand(searchBox.getText());
						user.setSearchEnqs(new ArrayList<Enquiry>());
						EnquirySearch es = new EnquirySearch(user);
					}
					else{
						user.setCommand(searchBox.getText());
						user.setSearchAgents(new ArrayList<Agent>());
						AgentSearch as = new AgentSearch(user); 
					}
				}
			}
		});
		searchButton.setForeground(Color.WHITE);
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		searchButton.setBackground(new Color(142, 39, 148));
		searchButton.setBounds(580, 59, 73, 31);
		this.getContentPane().add(searchButton);
		
		// logout button and action performed method to close GUI
		JButton logoutButton = new JButton("Back");
		logoutButton.setBackground(new Color(142, 39, 148));
		logoutButton.setForeground(Color.WHITE);
		logoutButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						dispose();
				}
			});
		logoutButton.setBounds(775, 49, 234, 41);
		this.getContentPane().add(logoutButton);
		
		// welcome user label displays welcome message plus users name
		JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername());
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		welcomeLabel.setForeground(new Color(142, 39, 148));
		welcomeLabel.setBounds(337, 11, 326, 25);
		this.getContentPane().add(welcomeLabel);
		
		// creation of JLabel	
		JLabel lblViewEnquirys = new JLabel("View Enquirys:");
		lblViewEnquirys.setForeground(new Color(142, 39, 148));
		lblViewEnquirys.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblViewEnquirys.setBounds(56, 11, 165, 17);
		roundedPanel.add(lblViewEnquirys);
		
		// creation of JButton
		JButton button_pre = new JButton("<< Previous");
		button_pre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				i = i -5;
				j = j - 5;
				Controller.displayNextEnqs(user.getEnquiries(), enq1Button,enq2Button,
						 enq3Button, enq4Button, enq5Button,  lblName,
						 lbleid,  lblAgent,  lblStaff,  lblStatus,  lblLastUpdated,  lblOf,
						name_2, eid2,  agent2,  staff2,  status2,  lastUpdated2,
						name3,  eid3,  agent3,  staff3,  status3,  lastUpdated3,
						name4,  eid4,  agent4,  staff4,  status4,  lastUpdated4,
						 name5,  eid5,  agent5,  staff5,  status5,  lastUpdated5,  i,  j);
			}
		});
		button_pre.setBackground(new Color(142, 39, 148));
		button_pre.setFont(new Font("Tahoma", Font.BOLD, 11));
		button_pre.setForeground(Color.WHITE);
		button_pre.setBounds(183, 438, 114, 23);
		button_pre.setEnabled(false);
		roundedPanel.add(button_pre);
		
		// creation of JButton
	 	JButton btnNext = new JButton();
	 	btnNext.setText("Next >>");
	 	btnNext.setBackground(new Color(142, 39, 148));
		btnNext.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNext.setForeground(Color.WHITE);
		btnNext.setBounds(442, 438, 105, 23);
		roundedPanel.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				// if i is less than enqsList.size it means there are more enquiries to display
				if(!user.getEnquiries().isEmpty()){
				
					i = i + 5;
				// call method to display the next five enquiries	
					j = j + 5;
					
					button_pre.setEnabled(true);	
				Controller.displayNextEnqs(user.getEnquiries(), enq1Button,enq2Button,
						 enq3Button, enq4Button, enq5Button,  lblName,
						 lbleid,  lblAgent,  lblStaff,  lblStatus,  lblLastUpdated,  lblOf,
						name_2, eid2,  agent2,  staff2,  status2,  lastUpdated2,
						name3,  eid3,  agent3,  staff3,  status3,  lastUpdated3,
						name4,  eid4,  agent4,  staff4,  status4,  lastUpdated4,
						 name5,  eid5,  agent5,  staff5,  status5,  lastUpdated5,  i,  j);
				}
				}
				
		});
		
		// creation of JLabel
		lblOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblOf.setForeground(new Color(142, 39, 148));
		lblOf.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOf.setBounds(308, 443, 130, 17);
		roundedPanel.add(lblOf);
		
		// set parameters of JLabel
		lblName.setText("Name:" + " " );
		lblName.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(41, 48, 211, 14);
		roundedPanel.add(lblName);
		
		// set parameters of JLabel
		lbleid.setFont(new Font("Tahoma", Font.BOLD, 10));			
		lbleid.setForeground(Color.WHITE);
		lbleid.setBounds(280, 48, 188, 14);
		roundedPanel.add(lbleid);
		
		// set parameters of JLabel
		lblAgent.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblAgent.setForeground(Color.WHITE);
		lblAgent.setBounds(41, 78, 220, 14);
		roundedPanel.add(lblAgent);
		
		// set parameters of JLabel
		lblStaff.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblStaff.setForeground(Color.WHITE);
		lblStaff.setBounds(279, 78, 220, 14);
		roundedPanel.add(lblStaff);
		
		// set parameters of JLabel
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setBounds(524, 78, 220, 14);
		roundedPanel.add(lblStatus);
		
		// set parameters of JLabel
		lblLastUpdated.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblLastUpdated.setForeground(Color.WHITE);
		lblLastUpdated.setBounds(524, 48, 220, 14);
		roundedPanel.add(lblLastUpdated);
		
		// set parameters of JLabel
		name_2.setForeground(Color.WHITE);
		name_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		name_2.setBounds(41, 132, 211, 14);
		roundedPanel.add(name_2);
		
		// set parameters of JLabel
		eid2.setForeground(Color.WHITE);
		eid2.setFont(new Font("Tahoma", Font.BOLD, 10));
		eid2.setBounds(280, 132, 188, 14);
		roundedPanel.add(eid2);
		
		// set parameters of JLabel
		lastUpdated2.setForeground(Color.WHITE);
		lastUpdated2.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastUpdated2.setBounds(524, 132, 220, 14);
		roundedPanel.add(lastUpdated2);
		
		// set parameters of JLabel
		agent2.setForeground(Color.WHITE);
		agent2.setFont(new Font("Tahoma", Font.BOLD, 10));
		agent2.setBounds(41, 162, 220, 14);
		roundedPanel.add(agent2);
		
		// set parameters of JLabel
		staff2.setForeground(Color.WHITE);
		staff2.setFont(new Font("Tahoma", Font.BOLD, 10));
		staff2.setBounds(279, 162, 220, 14);
		roundedPanel.add(staff2);
		
		// set parameters of JLabel
		status2.setForeground(Color.WHITE);
		status2.setFont(new Font("Tahoma", Font.BOLD, 10));
		status2.setBounds(524, 162, 220, 14);
		roundedPanel.add(status2);
		
		// set parameters of JLabel
		name3.setForeground(Color.WHITE);
		name3.setFont(new Font("Tahoma", Font.BOLD, 10));
		name3.setBounds(40, 214, 211, 14);
		roundedPanel.add(name3);
		
		// set parameters of JLabel
		eid3.setForeground(Color.WHITE);
		eid3.setFont(new Font("Tahoma", Font.BOLD, 10));
		eid3.setBounds(279, 214, 188, 14);
		roundedPanel.add(eid3);
		
		// set parameters of JLabel
		lastUpdated3.setForeground(Color.WHITE);
		lastUpdated3.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastUpdated3.setBounds(523, 214, 220, 14);
		roundedPanel.add(lastUpdated3);
		
		// set parameters of JLabel
		agent3.setForeground(Color.WHITE);
		agent3.setFont(new Font("Tahoma", Font.BOLD, 10));
		agent3.setBounds(40, 244, 220, 14);
		roundedPanel.add(agent3);
		
		// set parameters of JLabel
		staff3.setForeground(Color.WHITE);
		staff3.setFont(new Font("Tahoma", Font.BOLD, 10));
		staff3.setBounds(278, 244, 220, 14);
		roundedPanel.add(staff3);
		
		// set parameters of JLabel
		status3.setForeground(Color.WHITE);
		status3.setFont(new Font("Tahoma", Font.BOLD, 10));
		status3.setBounds(523, 244, 220, 14);
		roundedPanel.add(status3);
		
		// set parameters of JLabel
		name4.setForeground(Color.WHITE);
		name4.setFont(new Font("Tahoma", Font.BOLD, 10));
		name4.setBounds(41, 294, 211, 14);
		roundedPanel.add(name4);
		
		// set parameters of JLabel
		eid4.setForeground(Color.WHITE);
		eid4.setFont(new Font("Tahoma", Font.BOLD, 10));
		eid4.setBounds(280, 294, 188, 14);
		roundedPanel.add(eid4);
		
		// set parameters of JLabel
		lastUpdated4.setForeground(Color.WHITE);
		lastUpdated4.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastUpdated4.setBounds(524, 294, 220, 14);
		roundedPanel.add(lastUpdated4);
		
		// set parameters of JLabel
		agent4.setForeground(Color.WHITE);
		agent4.setFont(new Font("Tahoma", Font.BOLD, 10));
		agent4.setBounds(41, 324, 220, 14);
		roundedPanel.add(agent4);
		
		// set parameters of JLabel
		staff4.setForeground(Color.WHITE);
		staff4.setFont(new Font("Tahoma", Font.BOLD, 10));
		staff4.setBounds(279, 324, 220, 14);
		roundedPanel.add(staff4);
		
		// set parameters of JLabel
		status4.setForeground(Color.WHITE);
		status4.setFont(new Font("Tahoma", Font.BOLD, 10));
		status4.setBounds(524, 324, 220, 14);
		roundedPanel.add(status4);
		
		// set parameters of JLabel
		name5.setForeground(Color.WHITE);
		name5.setFont(new Font("Tahoma", Font.BOLD, 10));
		name5.setBounds(41, 375, 211, 14);
		roundedPanel.add(name5);
		
		// set parameters of JLabel
		eid5.setForeground(Color.WHITE);
		eid5.setFont(new Font("Tahoma", Font.BOLD, 10));
		eid5.setBounds(280, 375, 188, 14);
		roundedPanel.add(eid5);
		
		// set parameters of JLabel
		lastUpdated5.setForeground(Color.WHITE);
		lastUpdated5.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastUpdated5.setBounds(524, 375, 220, 14);
		roundedPanel.add(lastUpdated5);
		
		// set parameters of JLabel
		agent5.setForeground(Color.WHITE);
		agent5.setFont(new Font("Tahoma", Font.BOLD, 10));
		agent5.setBounds(41, 405, 220, 14);
		roundedPanel.add(agent5);
		
		// set parameters of JLabel
		staff5.setForeground(Color.WHITE);
		staff5.setFont(new Font("Tahoma", Font.BOLD, 10));
		staff5.setBounds(279, 405, 220, 14);
		roundedPanel.add(staff5);
		
		// set parameters of JLabel
		status5.setForeground(Color.WHITE);
		status5.setFont(new Font("Tahoma", Font.BOLD, 10));
		status5.setBounds(524, 405, 220, 14);
		roundedPanel.add(status5);
		enq1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
		
		// define attributes of app1Button
		enq1Button.setForeground(Color.WHITE);
		enq1Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		enq1Button.setBackground(new Color(142, 39, 148));
		enq1Button.setBounds(30, 39, 685, 70);
		roundedPanel.add(enq1Button);
		enq1Button.setVisible(false);
		enq2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		// define attributes of app2Button
		enq2Button.setForeground(Color.WHITE);
		enq2Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		enq2Button.setBackground(new Color(142, 39, 148));
		enq2Button.setBounds(30, 120, 685, 70);
		roundedPanel.add(enq2Button);
		enq2Button.setVisible(false);
		enq3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		
		// define attributes of app3Button
		enq3Button.setForeground(Color.WHITE);
		enq3Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		enq3Button.setBackground(new Color(142, 39, 148));
		enq3Button.setBounds(30, 201, 685, 70);
		roundedPanel.add(enq3Button);
		enq3Button.setVisible(false);
		enq4Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		
		// define attributes of app4Button
		enq4Button.setForeground(Color.WHITE);
		enq4Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		enq4Button.setBackground(new Color(142, 39, 148));
		enq4Button.setBounds(30, 282, 685, 70);
		roundedPanel.add(enq4Button);
		enq4Button.setVisible(false);
		enq5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
		// define attributes of app5Button
		enq5Button.setForeground(Color.WHITE);
		enq5Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		enq5Button.setBackground(new Color(142, 39, 148));
		enq5Button.setBounds(30, 363, 685, 70);
		roundedPanel.add(enq5Button);
		enq5Button.setVisible(false);
		
		Controller.addViewEnqButtonListeners(user, enq1Button, enq2Button, enq3Button,
				enq4Button, enq5Button, lbleid,
				eid2, eid3,eid4, eid5);
		
		

		Controller.displayNextEnqs(user.getEnquiries(), enq1Button,enq2Button,
				 enq3Button, enq4Button, enq5Button,  lblName,
				 lbleid,  lblAgent,  lblStaff,  lblStatus,  lblLastUpdated,  lblOf,
				name_2, eid2,  agent2,  staff2,  status2,  lastUpdated2,
				name3,  eid3,  agent3,  staff3,  status3,  lastUpdated3,
				name4,  eid4,  agent4,  staff4,  status4,  lastUpdated4,
				 name5,  eid5,  agent5,  staff5,  status5,  lastUpdated5,  i,  j);
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setLayout(null);
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(10, 101, 245, 480);
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


