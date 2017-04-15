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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sam.server.Agent;
import com.sam.server.Application;
import com.sam.server.Enquiry;
import com.sam.server.User;
import com.toedter.calendar.JDateChooser;

import javax.swing.JTextArea;




public class ManageUsers extends JPanel {

	// field variables
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private final JLabel lblNewLabel = new JLabel("New label");
	private User user;
	
	JLabel warningLabel = new JLabel("Please complete all fields.  ");
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField textField;
	
	
	// constructor
	public ManageUsers(User user) {
		this.user = user;	
		initialize(user);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	}

	// initialize method adds frame components and sets paramaters
	public void initialize(User user) {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1050, 630);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		lblNewLabel.setBounds(40, -30, 200, 31);
		frame.getContentPane().add(lblNewLabel);
		
		
	    frame.setVisible(true);
		
		// label for displaying logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(10, 0, 240, 111);
		frame.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// need to set this to NOT CONNECTED when the client/server connection ends
		frame.setTitle("Student Application Management");
		
		
		
		RoundedPanel roundedPanel = new RoundedPanel();
		
		roundedPanel.setForeground(new Color(142, 39, 148));
		roundedPanel.setBackground(Color.WHITE);
		roundedPanel.setBounds(284, 107, 725, 474);
		frame.getContentPane().add(roundedPanel);
		roundedPanel.setLayout(null);
		
		String[]agents = new String[user.getAgentsList().size()+1];
		agents[0] = "Select";
		for(int i = 1; i < user.getAgentsList().size(); i++){
			agents[i] = user.getAgentsList().get(i).getCompanyName();
		}
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(42, 135, 131, 15);
		roundedPanel.add(lblFirstName);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFirstName.setForeground(new Color(142, 39, 148));
		
		JTextField firstNameField = new JTextField();
		firstNameField.setBounds(160, 130, 180, 25);
		roundedPanel.add(firstNameField);
		firstNameField.setForeground(new Color(142, 39, 148));
		firstNameField.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name: ");
		lblLastName.setBounds(42, 166, 150, 14);
		roundedPanel.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastName.setForeground(new Color(142, 39, 148));
		
		JTextField lastNameField = new JTextField();
		lastNameField.setBounds(160, 163, 180, 25);
		roundedPanel.add(lastNameField);
		lastNameField.setForeground(new Color(142, 39, 148));
		lastNameField.setColumns(10);
		
		JLabel lblAddNewEnquiry = new JLabel("Manage Users:");
		lblAddNewEnquiry.setForeground(new Color(142, 39, 148));
		lblAddNewEnquiry.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAddNewEnquiry.setBounds(42, 26, 181, 31);
		roundedPanel.add(lblAddNewEnquiry);
		
		warningLabel.setForeground(Color.RED);
		warningLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel.setBounds(43, 436, 612, 33);
		roundedPanel.add(warningLabel);
		warningLabel.setVisible(false);
		
		JComboBox typeCombo = new JComboBox();
		typeCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		typeCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "staff", "admin"}));
		typeCombo.setSelectedItem("Select");
		typeCombo.setBounds(160, 270, 179, 20);
		roundedPanel.add(typeCombo);
		
		JLabel typeComboLabel = new JLabel("User Type:");
		typeComboLabel.setForeground(new Color(142, 39, 148));
		typeComboLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		typeComboLabel.setBounds(42, 271, 132, 19);
		roundedPanel.add(typeComboLabel);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnBack.setForeground(Color.WHITE);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBackground(new Color(142, 39, 148));
		btnBack.setBounds(775, 49, 234, 41);
		frame.getContentPane().add(btnBack);
		
		// search text field
				JTextField searchBox = new JTextField();
				searchBox.setBounds(284, 59, 286, 31);
				searchBox.setBorder(new RoundedCornerBorder());
				frame.getContentPane().add(searchBox);
				searchBox.setColumns(10);
				
				JComboBox searchCombo = new JComboBox();
				searchCombo.setModel(new DefaultComboBoxModel(new String[] {"Applications", "Enquiries", "Agents"}));
				searchCombo.setForeground(Color.WHITE);
				searchCombo.setFont(new Font("Tahoma", Font.BOLD, 11));
				searchCombo.setBackground(new Color(142, 39, 148));
				searchCombo.setBounds(663, 63, 102, 25);
				frame.getContentPane().add(searchCombo);
				
				JLabel warningLabelSearch = new JLabel("Please enter a search term");
				warningLabelSearch.setForeground(Color.RED);
				warningLabelSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
				warningLabelSearch.setBounds(302, 35, 211, 20);
				warningLabelSearch.setVisible(false);
				frame.getContentPane().add(warningLabelSearch);
				
				JButton searchButton = new JButton("Search");
				searchButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(searchBox.getText().equals("") ){
							
							warningLabelSearch.setForeground(Color.RED);
							warningLabelSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
							warningLabelSearch.setBounds(369, 64, 211, 20);
							warningLabelSearch.setVisible(true);
							frame.getContentPane().add(warningLabelSearch);
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
				frame.getContentPane().add(searchButton);
		
		usernameField = new JTextField();
		usernameField.setForeground(new Color(142, 39, 148));
		usernameField.setColumns(10);
		usernameField.setBounds(160, 199, 180, 25);
		roundedPanel.add(usernameField);
		
		JLabel phoneLabel = new JLabel("Username: ");
		phoneLabel.setForeground(new Color(142, 39, 148));
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		phoneLabel.setBounds(42, 202, 150, 14);
		roundedPanel.add(phoneLabel);
		
		passwordField = new JTextField();
		passwordField.setForeground(new Color(142, 39, 148));
		passwordField.setColumns(10);
		passwordField.setBounds(160, 235, 180, 24);
		roundedPanel.add(passwordField);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setForeground(new Color(142, 39, 148));
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordLabel.setBounds(42, 238, 150, 14);
		roundedPanel.add(passwordLabel);
	

		JButton addUserButton = new JButton("Add User");
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			Controller.addUser(firstNameField, lastNameField, usernameField, passwordField, typeCombo, warningLabel, user);
			}
		});
		addUserButton.setBackground(new Color(142, 39, 148));
		addUserButton.setForeground(Color.WHITE);
		addUserButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		addUserButton.setBounds(198, 316, 108, 31);
		roundedPanel.add(addUserButton);
		
		JLabel lblAddNewUser = new JLabel("Add New User:");
		lblAddNewUser.setForeground(new Color(142, 39, 148));
		lblAddNewUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddNewUser.setBounds(42, 68, 181, 31);
		roundedPanel.add(lblAddNewUser);
		
		JLabel lblRemoveUser = new JLabel("Remove User Access:");
		lblRemoveUser.setForeground(new Color(142, 39, 148));
		lblRemoveUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRemoveUser.setBounds(389, 68, 181, 31);
		roundedPanel.add(lblRemoveUser);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
		comboBox.setSelectedItem("Select");
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		comboBox.setBounds(441, 211, 208, 25);
		roundedPanel.add(comboBox);
		
		JLabel lblSelectUser = new JLabel("Select User:");
		lblSelectUser.setForeground(new Color(142, 39, 148));
		lblSelectUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectUser.setBounds(389, 130, 181, 31);
		roundedPanel.add(lblSelectUser);
		
		JButton removeUserButton = new JButton("Remove User");
		removeUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem().equals("Select")){
					warningLabel.setText("Please select a user to remove");
				}
				
				else{
					user.setCommand2(comboBox.getSelectedItem().toString());
					Controller.removeUser(user, warningLabel);
				}
			}
		});
		removeUserButton.setForeground(Color.WHITE);
		removeUserButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		removeUserButton.setBackground(new Color(142, 39, 148));
		removeUserButton.setBounds(475, 316, 136, 31);
		roundedPanel.add(removeUserButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		backButton.setForeground(Color.WHITE);
		backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		backButton.setBackground(new Color(142, 39, 148));
		backButton.setBounds(629, 439, 86, 25);
		roundedPanel.add(backButton);
		
		JLabel lblUpdateUserPassword = new JLabel("Update User Password:");
		lblUpdateUserPassword.setForeground(new Color(142, 39, 148));
		lblUpdateUserPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUpdateUserPassword.setBounds(42, 358, 181, 31);
		roundedPanel.add(lblUpdateUserPassword);
		
		JLabel lblNewPassword = new JLabel("New Password:");
		lblNewPassword.setForeground(new Color(142, 39, 148));
		lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewPassword.setBounds(309, 396, 150, 14);
		roundedPanel.add(lblNewPassword);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		comboBox_1.setBounds(91, 392, 208, 25);
		roundedPanel.add(comboBox_1);
		
		textField = new JTextField();
		textField.setForeground(new Color(142, 39, 148));
		textField.setColumns(10);
		textField.setBounds(422, 393, 150, 24);
		roundedPanel.add(textField);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setForeground(new Color(142, 39, 148));
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUser.setBounds(42, 398, 131, 15);
		roundedPanel.add(lblUser);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().isEmpty()){
					warningLabel.setText("Please enter a new password");
				}
				else if(comboBox_1.getSelectedItem().equals("Select")){
					warningLabel.setText("Please select a user to update their password");
				}
				
				else{
					Controller.updatePassword(user, warningLabel, textField, comboBox_1);
				}
			}
		});
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBackground(new Color(142, 39, 148));
		btnUpdate.setBounds(589, 392, 108, 25);
		roundedPanel.add(btnUpdate);
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setLayout(null);
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(10, 107, 245, 474);
		frame.getContentPane().add(roundedPanel_1);
		
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
			frame.dispose();
			}
		});

button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddEnquiry ae = new AddEnquiry(user);
				frame.dispose();
			}
		});

button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewEnquiries ve = new ViewEnquiries(user);
				frame.dispose();
			}
		});
		

		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddApplicationStaff aas = new AddApplicationStaff(user);
				frame.dispose();
			}
		});

button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddGroupApplicationStaff a = new AddGroupApplicationStaff(user);
					frame.dispose();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewApplications va = new ViewApplications(user);
				frame.dispose();
			}
		});
		
button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EnrolledStudentsViewStaff esv = new EnrolledStudentsViewStaff(user);
				frame.dispose();
			}
		});
		
button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignedAgentsView sav = new SignedAgentsView(user);
				frame.dispose();
			}
		});
		
button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnsignedAgentsView usv = new UnsignedAgentsView(user);
				frame.dispose();
			}
		});
		
button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageDocumentsView mdv = new ManageDocumentsView(user);
				frame.dispose();
			}
		});
		
	}
}
