/**
 * This class represents the View Applications view for users. 
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
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

import com.sam.server.Agent;
import com.sam.server.Application;
import com.sam.server.Enquiry;
import com.sam.server.User;


public class ViewApplications extends JFrame {

	// field variables
	private static final long serialVersionUID = 1L;
	private final JLabel lblNewLabel = new JLabel("New label");
	private RoundedPanel roundedPanel = new RoundedPanel();
	private ArrayList<Application>nonEnrolled = new ArrayList<Application>();
	
	
	// JLabels for each of the 5 application buttons to display key application info
	JLabel lblName = new JLabel ("Name:" + " " );
	JLabel lblUid = new JLabel("UID:" );
	JLabel lblAgent = new JLabel("Agent:" );
	JLabel lblStaff = new JLabel("Staff:" + " " );
	JLabel lblStatus = new JLabel("Status:" + " ");
	JLabel lblLastUpdated = new JLabel("Last Updated:" + " " );
	JLabel name_2 = new JLabel("Name: ");
	JLabel uid2 = new JLabel("UID:");
	JLabel lastUpdated2 = new JLabel("Last Updated: ");
	JLabel agent2 = new JLabel("Agent:");
	JLabel staff2 = new JLabel("Staff: ");
	JLabel status2 = new JLabel("Status: ");
	JLabel name3 = new JLabel("Name: ");
	JLabel uid3 = new JLabel("UID:");
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
	JLabel uid5 = new JLabel("UID:");
	JLabel name5 = new JLabel("Name: ");
	JLabel staff4 = new JLabel("Staff: ");
	JLabel uid4 = new JLabel("UID:");
	
	// JButtons to display each application
	JButton app1Button = new JButton("");
	JButton app2Button = new JButton("");
	JButton app3Button = new JButton("");
	JButton app4Button = new JButton("");
	JButton app5Button = new JButton("");
	
	JLabel lblOf = new JLabel(" ");
	
	// this keeps track of the number of applications that have been displayed (initially it is 0)
	int i = 0;
	int j = 1;
	
	// constructor
	public ViewApplications(User user) {
		
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
		roundedPanel.setBounds(284, 104, 725, 474);
		this.getContentPane().add(roundedPanel);
		roundedPanel.setLayout(null);
		
		lblNewLabel.setBounds(40, -30, 200, 31);
		this.getContentPane().add(lblNewLabel);
	    this.setVisible(true);
		
	   // label for displaying logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(10, 0, 242, 93);
		this.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// title for JFrame
		this.setTitle("Student Application System");
		
		JTextField searchBox = new JTextField();
		searchBox.setBounds(284, 59, 286, 31);
		searchBox.setBorder(new RoundedCornerBorder());
		this.getContentPane().add(searchBox);
		searchBox.setColumns(10);
		
		JLabel warningLabelSearch = new JLabel("Please enter a search term");
		warningLabelSearch.setForeground(Color.RED);
		warningLabelSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabelSearch.setBounds(302, 35, 211, 20);
		warningLabelSearch.setVisible(false);
		this.getContentPane().add(warningLabelSearch);
		
		JButton searchButton = new JButton("Search");
		
		
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
			public void actionPerformed(ActionEvent arg0) {
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
		JLabel lblViewApplications = new JLabel("View Applications:");
		lblViewApplications.setForeground(new Color(142, 39, 148));
		lblViewApplications.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblViewApplications.setBounds(28, 11, 165, 17);
		roundedPanel.add(lblViewApplications);
		
		// creation of JButton
		JButton button_1 = new JButton("<< Previous");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				i = i-5;
				j = j-5;
				
				Controller.nextApps(nonEnrolled, app1Button, app2Button,app3Button,  app4Button,
						 app5Button,  lblName,  lblUid,  lblAgent,  lblStaff,  lblStatus, lblLastUpdated,
						 name_2, uid2,  agent2,  staff2,  status2,  lastUpdated2, 
						 name3,  uid3,  agent3,  staff3, status3,  lastUpdated3,
						 name4,  uid4, agent4,  staff4,  status4,  lastUpdated4,
						 name5,  uid5,  agent5,  staff5,  status5,  lastUpdated5,  lblOf, i, j);
			}
		});
		button_1.setBackground(new Color(142, 39, 148));
		button_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		button_1.setForeground(Color.WHITE);
		button_1.setBounds(166, 439, 114, 23);
		roundedPanel.add(button_1);
		
		// creation of JButton
	 	JButton btnNext = new JButton();
	 	btnNext.setText("Next >>");
	 	btnNext.setBackground(new Color(142, 39, 148));
		btnNext.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNext.setForeground(Color.WHITE);
		btnNext.setBounds(440, 439, 105, 23);
		roundedPanel.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				i = i+5;
				
				// if i is less than appsList.size it means there are more applications to display
				if(i < user.getUserApplications().size()){
				
				// call method to display the next five applications	
					j = j + 5;
				//nextApps(user);
					Controller.nextApps(nonEnrolled, app1Button, app2Button,app3Button,  app4Button,
							 app5Button,  lblName,  lblUid,  lblAgent,  lblStaff,  lblStatus, lblLastUpdated,
							 name_2, uid2,  agent2,  staff2,  status2,  lastUpdated2, 
							 name3,  uid3,  agent3,  staff3, status3,  lastUpdated3,
							 name4,  uid4, agent4,  staff4,  status4,  lastUpdated4,
							 name5,  uid5,  agent5,  staff5,  status5,  lastUpdated5,  lblOf, i, j);
				}
			}
		});
		
		// creation of JLabel
		lblOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblOf.setForeground(new Color(142, 39, 148));
		lblOf.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOf.setBounds(300, 448, 130, 17);
		roundedPanel.add(lblOf);
		
		// set parameters of JLabel
		lblName.setText("Name:" + " " );
		lblName.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(43, 48, 211, 14);
		roundedPanel.add(lblName);
		
		// set parameters of JLabel
		lblUid.setFont(new Font("Tahoma", Font.BOLD, 10));			
		lblUid.setForeground(Color.WHITE);
		lblUid.setBounds(315, 48, 188, 14);
		roundedPanel.add(lblUid);
		
		// set parameters of JLabel
		lblAgent.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblAgent.setForeground(Color.WHITE);
		lblAgent.setBounds(43, 78, 220, 14);
		roundedPanel.add(lblAgent);
		
		// set parameters of JLabel
		lblStaff.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblStaff.setForeground(Color.WHITE);
		lblStaff.setBounds(314, 78, 220, 14);
		roundedPanel.add(lblStaff);
		
		// set parameters of JLabel
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setBounds(522, 78, 220, 14);
		roundedPanel.add(lblStatus);
		
		// set parameters of JLabel
		lblLastUpdated.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblLastUpdated.setForeground(Color.WHITE);
		lblLastUpdated.setBounds(522, 48, 220, 14);
		roundedPanel.add(lblLastUpdated);
		
		// set parameters of JLabel
		name_2.setForeground(Color.WHITE);
		name_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		name_2.setBounds(44, 132, 211, 14);
		roundedPanel.add(name_2);
		
		// set parameters of JLabel
		uid2.setForeground(Color.WHITE);
		uid2.setFont(new Font("Tahoma", Font.BOLD, 10));
		uid2.setBounds(315, 132, 188, 14);
		roundedPanel.add(uid2);
		
		// set parameters of JLabel
		lastUpdated2.setForeground(Color.WHITE);
		lastUpdated2.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastUpdated2.setBounds(522, 132, 220, 14);
		roundedPanel.add(lastUpdated2);
		
		// set parameters of JLabel
		agent2.setForeground(Color.WHITE);
		agent2.setFont(new Font("Tahoma", Font.BOLD, 10));
		agent2.setBounds(44, 162, 220, 14);
		roundedPanel.add(agent2);
		
		// set parameters of JLabel
		staff2.setForeground(Color.WHITE);
		staff2.setFont(new Font("Tahoma", Font.BOLD, 10));
		staff2.setBounds(314, 162, 220, 14);
		roundedPanel.add(staff2);
		
		// set parameters of JLabel
		status2.setForeground(Color.WHITE);
		status2.setFont(new Font("Tahoma", Font.BOLD, 10));
		status2.setBounds(522, 162, 220, 14);
		roundedPanel.add(status2);
		
		// set parameters of JLabel
		name3.setForeground(Color.WHITE);
		name3.setFont(new Font("Tahoma", Font.BOLD, 10));
		name3.setBounds(43, 214, 211, 14);
		roundedPanel.add(name3);
		
		// set parameters of JLabel
		uid3.setForeground(Color.WHITE);
		uid3.setFont(new Font("Tahoma", Font.BOLD, 10));
		uid3.setBounds(314, 214, 188, 14);
		roundedPanel.add(uid3);
		
		// set parameters of JLabel
		lastUpdated3.setForeground(Color.WHITE);
		lastUpdated3.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastUpdated3.setBounds(521, 214, 220, 14);
		roundedPanel.add(lastUpdated3);
		
		// set parameters of JLabel
		agent3.setForeground(Color.WHITE);
		agent3.setFont(new Font("Tahoma", Font.BOLD, 10));
		agent3.setBounds(43, 244, 220, 14);
		roundedPanel.add(agent3);
		
		// set parameters of JLabel
		staff3.setForeground(Color.WHITE);
		staff3.setFont(new Font("Tahoma", Font.BOLD, 10));
		staff3.setBounds(313, 244, 220, 14);
		roundedPanel.add(staff3);
		
		// set parameters of JLabel
		status3.setForeground(Color.WHITE);
		status3.setFont(new Font("Tahoma", Font.BOLD, 10));
		status3.setBounds(521, 244, 220, 14);
		roundedPanel.add(status3);
		
		// set parameters of JLabel
		name4.setForeground(Color.WHITE);
		name4.setFont(new Font("Tahoma", Font.BOLD, 10));
		name4.setBounds(44, 294, 211, 14);
		roundedPanel.add(name4);
		
		// set parameters of JLabel
		uid4.setForeground(Color.WHITE);
		uid4.setFont(new Font("Tahoma", Font.BOLD, 10));
		uid4.setBounds(315, 294, 188, 14);
		roundedPanel.add(uid4);
		
		// set parameters of JLabel
		lastUpdated4.setForeground(Color.WHITE);
		lastUpdated4.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastUpdated4.setBounds(522, 294, 220, 14);
		roundedPanel.add(lastUpdated4);
		
		// set parameters of JLabel
		agent4.setForeground(Color.WHITE);
		agent4.setFont(new Font("Tahoma", Font.BOLD, 10));
		agent4.setBounds(44, 324, 220, 14);
		roundedPanel.add(agent4);
		
		// set parameters of JLabel
		staff4.setForeground(Color.WHITE);
		staff4.setFont(new Font("Tahoma", Font.BOLD, 10));
		staff4.setBounds(314, 324, 220, 14);
		roundedPanel.add(staff4);
		
		// set parameters of JLabel
		status4.setForeground(Color.WHITE);
		status4.setFont(new Font("Tahoma", Font.BOLD, 10));
		status4.setBounds(522, 324, 220, 14);
		roundedPanel.add(status4);
		
		// set parameters of JLabel
		name5.setForeground(Color.WHITE);
		name5.setFont(new Font("Tahoma", Font.BOLD, 10));
		name5.setBounds(44, 375, 211, 14);
		roundedPanel.add(name5);
		
		// set parameters of JLabel
		uid5.setForeground(Color.WHITE);
		uid5.setFont(new Font("Tahoma", Font.BOLD, 10));
		uid5.setBounds(315, 375, 188, 14);
		roundedPanel.add(uid5);
		
		// set parameters of JLabel
		lastUpdated5.setForeground(Color.WHITE);
		lastUpdated5.setFont(new Font("Tahoma", Font.BOLD, 10));
		lastUpdated5.setBounds(522, 375, 220, 14);
		roundedPanel.add(lastUpdated5);
		
		// set parameters of JLabel
		agent5.setForeground(Color.WHITE);
		agent5.setFont(new Font("Tahoma", Font.BOLD, 10));
		agent5.setBounds(44, 405, 220, 14);
		roundedPanel.add(agent5);
		
		// set parameters of JLabel
		staff5.setForeground(Color.WHITE);
		staff5.setFont(new Font("Tahoma", Font.BOLD, 10));
		staff5.setBounds(314, 405, 220, 14);
		roundedPanel.add(staff5);
		
		// set parameters of JLabel
		status5.setForeground(Color.WHITE);
		status5.setFont(new Font("Tahoma", Font.BOLD, 10));
		status5.setBounds(522, 405, 220, 14);
		roundedPanel.add(status5);
		app1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
		// define attributes of app1Button
		app1Button.setForeground(Color.WHITE);
		app1Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		app1Button.setBackground(new Color(142, 39, 148));
		app1Button.setBounds(28, 39, 665, 70);
		roundedPanel.add(app1Button);
		app1Button.setVisible(false);
		app2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		

		// define attributes of app2Button
		app2Button.setForeground(Color.WHITE);
		app2Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		app2Button.setBackground(new Color(142, 39, 148));
		app2Button.setBounds(28, 120, 665, 70);
		roundedPanel.add(app2Button);
		app2Button.setVisible(false);
		app3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		

		
		// define attributes of app3Button
		app3Button.setForeground(Color.WHITE);
		app3Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		app3Button.setBackground(new Color(142, 39, 148));
		app3Button.setBounds(28, 201, 665, 70);
		roundedPanel.add(app3Button);
		app3Button.setVisible(false);
		app4Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		

		
		// define attributes of app4Button
		app4Button.setForeground(Color.WHITE);
		app4Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		app4Button.setBackground(new Color(142, 39, 148));
		app4Button.setBounds(28, 282, 665, 70);
		roundedPanel.add(app4Button);
		app4Button.setVisible(false);
		app5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		

		// define attributes of app5Button
		app5Button.setForeground(Color.WHITE);
		app5Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		app5Button.setBackground(new Color(142, 39, 148));
		app5Button.setBounds(28, 363, 665, 70);
		roundedPanel.add(app5Button);
		
		Controller.addButtonListeners(user,lblUid, uid2, uid3, uid4, uid5,
				app1Button, app2Button, app3Button, app4Button, app5Button);
		app5Button.setVisible(false);
		
		if(user.getType().equals("admin")){
			Controller.createAdminView(this, warningLabelSearch, searchBox, user);
		}
		
		else if(user.getType().equals("staff")){
			Controller.createStaffView(this, warningLabelSearch, searchBox, user);
		}
		
		else if(user.getType().equals("agent")){
			Controller.createAgentView(this, searchBox, warningLabelSearch, user);
		}

		Controller.updateApps(user);
		
		for(Application a : user.getUserApplications()){
			if(!a.getAppStatus().equals("Enrolled")){
				nonEnrolled.add(a);
			}
		}
		
		Controller.nextApps(nonEnrolled, app1Button, app2Button,app3Button,  app4Button,
				 app5Button,  lblName,  lblUid,  lblAgent,  lblStaff,  lblStatus, lblLastUpdated,
				 name_2, uid2,  agent2,  staff2,  status2,  lastUpdated2, 
				 name3,  uid3,  agent3,  staff3, status3,  lastUpdated3,
				 name4,  uid4, agent4,  staff4,  status4,  lastUpdated4,
				 name5,  uid5,  agent5,  staff5,  status5,  lastUpdated5,  lblOf, i, j);
				
	}
	
}


