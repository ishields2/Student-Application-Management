/**
 * This class represents the Add Enquiry view for staff. 
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




public class AddEnquiry extends JPanel {

	// field variables
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private final JLabel lblNewLabel = new JLabel("New label");
	private User user;
	
	JLabel warningLabel = new JLabel("Please complete all fields.  ");
	private JTextField phoneField;
	private JTextField emailField;
	private JTextField noWeeksField;
	
	
	// constructor
	public AddEnquiry(User user) {
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
		
		JLabel lblAgent = new JLabel("Agent: ");
		lblAgent.setBounds(42, 183, 131, 18);
		roundedPanel.add(lblAgent);
		lblAgent.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAgent.setForeground(new Color(142, 39, 148));
		
		JComboBox agentCombo = new JComboBox();
		agentCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ArrayList<String> agentsList = new ArrayList<String>();
		agentsList.add("Select");
		for(Agent a : user.getAgentsList()){
			agentsList.add(a.getCompanyName());
		}
		String[]agents = new String[agentsList.size()];
		for(int i = 0; i<agentsList.size(); i++){
			agents[i] = agentsList.get(i);
		}	
		user.getAgentsList().get(0).getCompanyName();
		agentCombo.setModel(new DefaultComboBoxModel(agents));
		agentCombo.setBounds(160, 183, 179, 20);
		roundedPanel.add(agentCombo);
		
		JLabel lblWeeklyPrice = new JLabel("Weekly Price:      \u00A3");
		lblWeeklyPrice.setBounds(42, 324, 151, 25);
		roundedPanel.add(lblWeeklyPrice);
		lblWeeklyPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWeeklyPrice.setForeground(new Color(142, 39, 148));
		
		
		
		JLabel lblLastContacted = new JLabel("Last Contacted: ");
		lblLastContacted.setBounds(368, 257, 151, 14);
		roundedPanel.add(lblLastContacted);
		lblLastContacted.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastContacted.setForeground(new Color(142, 39, 148));
		
		JComboBox countryCombo = new JComboBox();
		countryCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		countryCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		countryCombo.setBounds(160, 148, 179, 20);
		roundedPanel.add(countryCombo);
		
		JComboBox statusCombo = new JComboBox();
		statusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "A", "B", "C", "D", "E"}));
		statusCombo.setBounds(160, 220, 179, 20);
		roundedPanel.add(statusCombo);
		
		JLabel lblCountry = new JLabel("Country: ");
		lblCountry.setBounds(42, 148, 131, 20);
		roundedPanel.add(lblCountry);
		lblCountry.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCountry.setForeground(new Color(142, 39, 148));
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(41, 70, 131, 15);
		roundedPanel.add(lblFirstName);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFirstName.setForeground(new Color(142, 39, 148));
		
		JLabel weeklyPriceLabel = new JLabel("");
		weeklyPriceLabel.setForeground(new Color(142, 39, 148));
		weeklyPriceLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		weeklyPriceLabel.setBounds(177, 324, 151, 25);
		roundedPanel.add(weeklyPriceLabel);
		
		JTextField firstNameField = new JTextField();
		firstNameField.setBounds(159, 65, 180, 25);
		roundedPanel.add(firstNameField);
		firstNameField.setForeground(new Color(142, 39, 148));
		firstNameField.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name: ");
		lblLastName.setBounds(369, 70, 150, 14);
		roundedPanel.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastName.setForeground(new Color(142, 39, 148));
		
		JTextField lastNameField = new JTextField();
		lastNameField.setBounds(498, 65, 178, 25);
		roundedPanel.add(lastNameField);
		lastNameField.setForeground(new Color(142, 39, 148));
		lastNameField.setColumns(10);
		
		JLabel lblTotalPrice = new JLabel("Total Price: ");
		lblTotalPrice.setBounds(41, 109, 131, 14);
		roundedPanel.add(lblTotalPrice);
		lblTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotalPrice.setForeground(new Color(142, 39, 148));
		
		
		
		JLabel lblSource = new JLabel("Source: ");
		lblSource.setBounds(368, 173, 150, 14);
		roundedPanel.add(lblSource);
		lblSource.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSource.setForeground(new Color(142, 39, 148));
		
		JTextField sourceField = new JTextField();
		sourceField.setBounds(498, 166, 178, 25);
		roundedPanel.add(sourceField);
		sourceField.setForeground(new Color(142, 39, 148));
		sourceField.setColumns(10);
		
		JLabel lblAddNewEnquiry = new JLabel("Add New Enquiry:");
		lblAddNewEnquiry.setForeground(new Color(142, 39, 148));
		lblAddNewEnquiry.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAddNewEnquiry.setBounds(42, 11, 181, 31);
		roundedPanel.add(lblAddNewEnquiry);
		
		warningLabel.setForeground(Color.RED);
		warningLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel.setBounds(43, 436, 612, 33);
		roundedPanel.add(warningLabel);
		warningLabel.setVisible(false);
		
		JDateChooser lastContactedCombo = new JDateChooser();
		lastContactedCombo.setBounds(497, 251, 179, 25);
		roundedPanel.add(lastContactedCombo);
		
		JComboBox salesAdvisorCombo = new JComboBox();
		salesAdvisorCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		salesAdvisorCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
		salesAdvisorCombo.setBounds(160, 251, 179, 20);
		roundedPanel.add(salesAdvisorCombo);
		
		JComboBox campusCombo = new JComboBox();
		campusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		campusCombo.setModel(new DefaultComboBoxModel(user.getCampuses()));
		campusCombo.setBounds(160, 291, 179, 20);
		roundedPanel.add(campusCombo);
		
		JLabel lblPrice = new JLabel("");
		lblPrice.setForeground(new Color(142, 39, 148));
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrice.setBounds(665, 430, 95, 14);
		lblPrice.setVisible(true);
		roundedPanel.add(lblPrice);
		
		JLabel lblStatus_1 = new JLabel("Status:");
		lblStatus_1.setForeground(new Color(142, 39, 148));
		lblStatus_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStatus_1.setBounds(41, 222, 133, 14);
		roundedPanel.add(lblStatus_1);
		
		JLabel lblCampus = new JLabel("Campus: ");
		lblCampus.setForeground(new Color(142, 39, 148));
		lblCampus.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCampus.setBounds(43, 288, 150, 25);
		roundedPanel.add(lblCampus);
		
		JLabel lblSalesAdvisor = new JLabel("Sales Advisor: ");
		lblSalesAdvisor.setForeground(new Color(142, 39, 148));
		lblSalesAdvisor.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSalesAdvisor.setBounds(42, 252, 132, 14);
		roundedPanel.add(lblSalesAdvisor);
		
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
		
		JLabel courseLabel = new JLabel("Course:");
		courseLabel.setForeground(new Color(142, 39, 148));
		courseLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		courseLabel.setBounds(368, 295, 150, 14);
		roundedPanel.add(courseLabel);
		
		JComboBox courseCombo = new JComboBox();
		courseCombo.setModel(new DefaultComboBoxModel(user.getCourses()));
		courseCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseCombo.setBounds(496, 291, 180, 20);
		roundedPanel.add(courseCombo);
		
		phoneField = new JTextField();
		phoneField.setForeground(new Color(142, 39, 148));
		phoneField.setColumns(10);
		phoneField.setBounds(498, 98, 178, 25);
		roundedPanel.add(phoneField);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		phoneLabel.setForeground(new Color(142, 39, 148));
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		phoneLabel.setBounds(368, 106, 150, 14);
		roundedPanel.add(phoneLabel);
		
		emailField = new JTextField();
		emailField.setForeground(new Color(142, 39, 148));
		emailField.setColumns(10);
		emailField.setBounds(498, 134, 178, 24);
		roundedPanel.add(emailField);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(142, 39, 148));
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(368, 139, 150, 14);
		roundedPanel.add(lblEmail);
		
		
		
		JLabel noWeeksLabel = new JLabel("No. Weeks");
		noWeeksLabel.setForeground(new Color(142, 39, 148));
		noWeeksLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		noWeeksLabel.setBounds(368, 220, 150, 14);
		roundedPanel.add(noWeeksLabel);
		
		JTextField totalPriceField = new JTextField();
		
		totalPriceField.setBounds(159, 101, 180, 25);
		roundedPanel.add(totalPriceField);
		totalPriceField.setForeground(new Color(142, 39, 148));
		totalPriceField.setColumns(10);
		
		
		noWeeksField = new JTextField();
		noWeeksField.setForeground(new Color(142, 39, 148));
		noWeeksField.setColumns(10);
		noWeeksField.setBounds(498, 213, 178, 25);
		roundedPanel.add(noWeeksField);
		
		Controller.addWeeklyPriceCalculatorListeners(totalPriceField, noWeeksField, weeklyPriceLabel);
	

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Controller.addEnquiry(frame, firstNameField, warningLabel, 
						 totalPriceField, lastNameField,  countryCombo,
						 agentCombo,  statusCombo, salesAdvisorCombo,
						 sourceField,  lastContactedCombo,  campusCombo,
						noWeeksField,  user,  courseCombo,  emailField,
						 weeklyPriceLabel,  phoneField);
				
				
			}
		});
		btnConfirm.setBackground(new Color(142, 39, 148));
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnConfirm.setBounds(588, 396, 108, 31);
		roundedPanel.add(btnConfirm);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(142, 39, 148));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancel.setBounds(470, 396, 108, 31);
		roundedPanel.add(btnCancel);
		
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