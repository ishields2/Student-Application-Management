/**
 * This class represents the Add Application view for staff. 
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
import javax.swing.JFileChooser;
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
import java.io.FileNotFoundException;
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

import com.sam.server.Agent;
import com.sam.server.Application;
import com.sam.server.Enquiry;
import com.sam.server.User;
import com.toedter.calendar.JDateChooser;

import javax.swing.JTextArea;




public class AddApplicationStaff extends JPanel {

	// field variables
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private final JLabel lblNewLabel = new JLabel("New label");
	private User user;
	
	JLabel warningLabel = new JLabel("Please complete all fields.  ");
	private JTextField phoneField;
	private JTextField emailField;
	private JFileChooser fileChooser;
	private boolean fileSelected = false;
	
	// constructor
	public AddApplicationStaff(User user) {
		this.user = user;	
		initialize(user);
		this.frame.setVisible(true);
		
		
		
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
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
	    frame.setVisible(true);
		
		// label for displaying logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(10, 0, 247, 98);
		frame.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// need to set this to NOT CONNECTED when the client/server connection ends
		frame.setTitle("Student Application Management");
		
		
		
		RoundedPanel roundedPanel = new RoundedPanel();
		
		roundedPanel.setForeground(new Color(142, 39, 148));
		roundedPanel.setBackground(Color.WHITE);
		roundedPanel.setBounds(282, 107, 730, 474);
		frame.getContentPane().add(roundedPanel);
		roundedPanel.setLayout(null);
		
		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setBounds(84, 421, 73, 14);
		roundedPanel.add(addressLabel);
		addressLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		addressLabel.setForeground(new Color(142, 39, 148));
		
		JLabel lblAgent = new JLabel("Agent: ");
		lblAgent.setBounds(84, 212, 131, 14);
		roundedPanel.add(lblAgent);
		lblAgent.setFont(new Font("Tahoma", Font.BOLD, 12));
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
		agentCombo.setBounds(201, 206, 169, 20);
		roundedPanel.add(agentCombo);
		
		
		JLabel lblWeeklyPrice = new JLabel("Weekly Price:      \u00A3");
		lblWeeklyPrice.setBounds(395, 392, 151, 14);
		roundedPanel.add(lblWeeklyPrice);
		lblWeeklyPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblWeeklyPrice.setForeground(new Color(142, 39, 148));
		
		
		
		JLabel lblLastContacted = new JLabel("Last Contacted: ");
		lblLastContacted.setBounds(395, 274, 151, 14);
		roundedPanel.add(lblLastContacted);
		lblLastContacted.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLastContacted.setForeground(new Color(142, 39, 148));
		
		JLabel lblEnquiryDate = new JLabel("DOB:");
		lblEnquiryDate.setBounds(395, 242, 151, 14);
		roundedPanel.add(lblEnquiryDate);
		lblEnquiryDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnquiryDate.setForeground(new Color(142, 39, 148));
		
		JComboBox countryCombo = new JComboBox();
		countryCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		countryCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		countryCombo.setBounds(201, 144, 169, 20);
		roundedPanel.add(countryCombo);
		
		JComboBox statusCombo = new JComboBox();
		statusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "New", "Received", "Deposit Received", "Cancelled", "Enrolled"}));
		statusCombo.setBounds(201, 272, 169, 20);
		roundedPanel.add(statusCombo);
		
		JLabel lblNation = new JLabel("Nationality:");
		lblNation.setBounds(84, 181, 131, 14);
		roundedPanel.add(lblNation);
		lblNation.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNation.setForeground(new Color(142, 39, 148));
		
		JLabel lblCountry = new JLabel("Country: ");
		lblCountry.setBounds(84, 146, 131, 14);
		roundedPanel.add(lblCountry);
		lblCountry.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCountry.setForeground(new Color(142, 39, 148));
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(84, 47, 131, 15);
		roundedPanel.add(lblFirstName);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFirstName.setForeground(new Color(142, 39, 148));
		
		JTextField firstNameField = new JTextField();
		firstNameField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		firstNameField.setBounds(202, 42, 168, 25);
		roundedPanel.add(firstNameField);
		firstNameField.setForeground(new Color(142, 39, 148));
		firstNameField.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name: ");
		lblLastName.setBounds(397, 48, 150, 14);
		roundedPanel.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLastName.setForeground(new Color(142, 39, 148));
		
		JTextField lastNameField = new JTextField();
		lastNameField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lastNameField.setBounds(524, 42, 160, 25);
		roundedPanel.add(lastNameField);
		lastNameField.setForeground(new Color(142, 39, 148));
		lastNameField.setColumns(10);
		
		JLabel lblTotalPrice = new JLabel("Total Price: ");
		lblTotalPrice.setBounds(84, 86, 131, 14);
		roundedPanel.add(lblTotalPrice);
		lblTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTotalPrice.setForeground(new Color(142, 39, 148));
		
		JTextField totalPriceField = new JTextField();
		totalPriceField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		totalPriceField.setBounds(202, 78, 168, 25);
		roundedPanel.add(totalPriceField);
		totalPriceField.setForeground(new Color(142, 39, 148));
		totalPriceField.setColumns(10);
		
		JLabel lblSource = new JLabel("Source: ");
		lblSource.setBounds(395, 141, 150, 14);
		roundedPanel.add(lblSource);
		lblSource.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSource.setForeground(new Color(142, 39, 148));
		
		JTextField sourceField = new JTextField();
		sourceField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sourceField.setBounds(524, 135, 160, 25);
		roundedPanel.add(sourceField);
		sourceField.setForeground(new Color(142, 39, 148));
		sourceField.setColumns(10);
		
		JLabel lblStartDate = new JLabel("Arrival Date: ");
		lblStartDate.setBounds(395, 176, 151, 14);
		roundedPanel.add(lblStartDate);
		lblStartDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStartDate.setForeground(new Color(142, 39, 148));
		
		JLabel lblEndDate = new JLabel("Departure Date: ");
		lblEndDate.setBounds(395, 211, 150, 14);
		roundedPanel.add(lblEndDate);
		lblEndDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEndDate.setForeground(new Color(142, 39, 148));
		
		JLabel lblAddNewEnquiry = new JLabel("Add New Application:");
		lblAddNewEnquiry.setForeground(new Color(142, 39, 148));
		lblAddNewEnquiry.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddNewEnquiry.setBounds(22, 5, 181, 31);
		roundedPanel.add(lblAddNewEnquiry);
		
		
		warningLabel.setForeground(Color.RED);
		warningLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel.setBounds(201, 3, 517, 33);
		roundedPanel.add(warningLabel);
		warningLabel.setVisible(false);
		
		JDateChooser arrivalDateField = new JDateChooser();
		
		arrivalDateField.setBounds(525, 170, 160, 25);
		roundedPanel.add(arrivalDateField);
		arrivalDateField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JDateChooser depDateCombo = new JDateChooser();
		depDateCombo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		depDateCombo.setBounds(525, 205, 160, 25);
		roundedPanel.add(depDateCombo);
		
		JDateChooser dobCombo = new JDateChooser();
		dobCombo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		dobCombo.setBounds(524, 236, 160, 25);
		roundedPanel.add(dobCombo);
		
		JDateChooser lastContactedCombo = new JDateChooser();
		lastContactedCombo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lastContactedCombo.setBounds(524, 267, 160, 25);
		roundedPanel.add(lastContactedCombo);
		
		JComboBox genderCombo = new JComboBox();
		genderCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		genderCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "M", "F"}));
		genderCombo.setBounds(200, 237, 170, 20);
		roundedPanel.add(genderCombo);
		
		JComboBox nationCombo = new JComboBox();
		nationCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nationCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		nationCombo.setBounds(201, 175, 169, 20);
		roundedPanel.add(nationCombo);
		
		JComboBox payStatusCombo = new JComboBox();
		payStatusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		payStatusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Not Paid", "Deposit Paid", "Full Fee Paid"}));
		payStatusCombo.setBounds(201, 299, 169, 20);
		roundedPanel.add(payStatusCombo);
		
		JComboBox salesAdvisorCombo = new JComboBox();
		salesAdvisorCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		salesAdvisorCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
		salesAdvisorCombo.setBounds(201, 330, 169, 20);
		roundedPanel.add(salesAdvisorCombo);
		
		JComboBox visaStatusCombo = new JComboBox();
		visaStatusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		visaStatusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "No Visa Required", "Not Applied", "Applied", "Visa Granted", "Visa Refused"}));
		visaStatusCombo.setBounds(522, 302, 161, 20);
		roundedPanel.add(visaStatusCombo);
		
		JComboBox campusCombo = new JComboBox();
		campusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		campusCombo.setModel(new DefaultComboBoxModel(user.getCampuses()));
		campusCombo.setBounds(523, 333, 161, 20);
		roundedPanel.add(campusCombo);
		
		JTextField passportField = new JTextField();
		passportField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		passportField.setForeground(new Color(142, 39, 148));
		passportField.setColumns(10);
		passportField.setBounds(202, 111, 168, 25);
		roundedPanel.add(passportField);
		
		JLabel lblPrice = new JLabel("");
		lblPrice.setForeground(new Color(142, 39, 148));
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPrice.setBounds(525, 392, 95, 14);
		lblPrice.setVisible(true);
		roundedPanel.add(lblPrice);
		
		JLabel lblPassportNo = new JLabel("Passport No:");
		lblPassportNo.setForeground(new Color(142, 39, 148));
		lblPassportNo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassportNo.setBounds(84, 115, 131, 14);
		roundedPanel.add(lblPassportNo);
		
		JLabel lblGender = new JLabel("Gender: ");
		lblGender.setForeground(new Color(142, 39, 148));
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGender.setBounds(82, 243, 133, 14);
		roundedPanel.add(lblGender);
		
		JLabel lblVisaStatus = new JLabel("Visa Status: ");
		lblVisaStatus.setForeground(new Color(142, 39, 148));
		lblVisaStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVisaStatus.setBounds(394, 308, 150, 14);
		roundedPanel.add(lblVisaStatus);
		
		JLabel lblStatus_1 = new JLabel("Status:");
		lblStatus_1.setForeground(new Color(142, 39, 148));
		lblStatus_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStatus_1.setBounds(82, 274, 133, 14);
		roundedPanel.add(lblStatus_1);
		
		JLabel lblCampus = new JLabel("Campus: ");
		lblCampus.setForeground(new Color(142, 39, 148));
		lblCampus.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCampus.setBounds(393, 336, 150, 14);
		roundedPanel.add(lblCampus);
		
		JLabel lblSalesAdvisor = new JLabel("Sales Advisor: ");
		lblSalesAdvisor.setForeground(new Color(142, 39, 148));
		lblSalesAdvisor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSalesAdvisor.setBounds(83, 331, 132, 14);
		roundedPanel.add(lblSalesAdvisor);
		
		JLabel lblPaymentStatus = new JLabel("Pay Status:");
		lblPaymentStatus.setForeground(new Color(142, 39, 148));
		lblPaymentStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPaymentStatus.setBounds(83, 301, 132, 14);
		roundedPanel.add(lblPaymentStatus);
		
		
		
		JLabel lblDuration = new JLabel("Duration (days) : ");
		lblDuration.setForeground(new Color(142, 39, 148));
		lblDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDuration.setBounds(83, 392, 121, 20);
		roundedPanel.add(lblDuration);
		
		
		
		// search text field
				JTextField searchBox = new JTextField();
				searchBox.setBounds(282, 57, 286, 31);
				searchBox.setBorder(new RoundedCornerBorder());
				frame.getContentPane().add(searchBox);
				searchBox.setColumns(10);
				
				JComboBox searchCombo = new JComboBox();
				searchCombo.setModel(new DefaultComboBoxModel(new String[] {"Applications", "Enquiries", "Agents"}));
				searchCombo.setForeground(Color.WHITE);
				searchCombo.setFont(new Font("Tahoma", Font.BOLD, 11));
				searchCombo.setBackground(new Color(142, 39, 148));
				searchCombo.setBounds(661, 60, 102, 25);
				frame.getContentPane().add(searchCombo);
				
				JLabel warningLabelSearch = new JLabel("Please enter a search term");
				warningLabelSearch.setForeground(Color.RED);
				warningLabelSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
				warningLabelSearch.setBounds(297, 34, 211, 20);
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
				searchButton.setBounds(578, 57, 73, 31);
				frame.getContentPane().add(searchButton);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnBack.setForeground(Color.WHITE);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBackground(new Color(142, 39, 148));
		btnBack.setBounds(775, 47, 234, 41);
		frame.getContentPane().add(btnBack);
		
		JLabel courseLabel = new JLabel("Course:");
		courseLabel.setForeground(new Color(142, 39, 148));
		courseLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		courseLabel.setBounds(394, 364, 150, 14);
		roundedPanel.add(courseLabel);
		
		JComboBox courseCombo = new JComboBox();
		courseCombo.setModel(new DefaultComboBoxModel(user.getCourses()));
		courseCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseCombo.setBounds(523, 361, 161, 20);
		roundedPanel.add(courseCombo);
		
		JLabel lblDuration2 = new JLabel("");
		lblDuration2.setForeground(new Color(142, 39, 148));
		lblDuration2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDuration2.setBounds(212, 392, 121, 20);
		roundedPanel.add(lblDuration2);
		
		JTextArea addressField = new JTextArea();
		addressField.setBounds(162, 417, 233, 46);
		roundedPanel.add(addressField);
		addressField.setLineWrap(true);
		addressField.setWrapStyleWord(true);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				if(fileSelected == true){
				user.setFile(fileChooser.getSelectedFile());
				int index = user.getFile().getName().indexOf(".");
				user.setCommand3(user.getFile().getName().substring(index));
				
				Controller.addApplication(frame, user, firstNameField, warningLabel, totalPriceField, passportField, lastNameField,
						countryCombo, nationCombo, agentCombo, genderCombo, statusCombo, payStatusCombo, salesAdvisorCombo, 
						sourceField, arrivalDateField, depDateCombo, dobCombo, lastContactedCombo, visaStatusCombo, campusCombo, 
						lblPrice, emailField, phoneField, courseCombo, lblDuration2, addressField);
				}
				
				else{
					user.setCommand3("false");
					Controller.addApplication(frame, user, firstNameField, warningLabel, totalPriceField, passportField, lastNameField,
							countryCombo, nationCombo, agentCombo, genderCombo, statusCombo, payStatusCombo, salesAdvisorCombo, 
							sourceField, arrivalDateField, depDateCombo, dobCombo, lastContactedCombo, visaStatusCombo, campusCombo, 
							lblPrice, emailField, phoneField, courseCombo, lblDuration2, addressField);
				}
				
			}
		});
		btnConfirm.setBackground(new Color(142, 39, 148));
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConfirm.setBounds(597, 421, 87, 23);
		roundedPanel.add(btnConfirm);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(142, 39, 148));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancel.setBounds(500, 421, 87, 23);
		roundedPanel.add(btnCancel);
		
		phoneField = new JTextField();
		phoneField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		phoneField.setForeground(new Color(142, 39, 148));
		phoneField.setColumns(10);
		phoneField.setBounds(524, 73, 160, 25);
		roundedPanel.add(phoneField);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		phoneLabel.setForeground(new Color(142, 39, 148));
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		phoneLabel.setBounds(395, 78, 150, 14);
		roundedPanel.add(phoneLabel);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		emailField.setForeground(new Color(142, 39, 148));
		emailField.setColumns(10);
		emailField.setBounds(524, 105, 160, 25);
		roundedPanel.add(emailField);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(142, 39, 148));
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmail.setBounds(395, 111, 150, 14);
		roundedPanel.add(lblEmail);
		
		Controller.autoCalculateAppWeeklyPrice(totalPriceField, arrivalDateField, depDateCombo, 
				lblPrice, lblDuration, lblDuration2);
		
		JLabel uploadedLabel = new JLabel("Uploaded");
		uploadedLabel.setForeground(new Color(0, 128, 0));
		uploadedLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		uploadedLabel.setBounds(83, 361, 132, 29);
		uploadedLabel.setVisible(false);
		roundedPanel.add(uploadedLabel);
		
		JButton uploadButton = new JButton("Upload Documents");
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String directory = System.getProperty("user.home");
				fileChooser = new JFileChooser(directory +"/Desktop");
				fileChooser.showOpenDialog(null);
				
				if(fileChooser.getSelectedFile()!=null){
					uploadedLabel.setVisible(true);
					uploadButton.setEnabled(true);
					fileSelected = true;
				}
			}
		});
		uploadButton.setForeground(Color.WHITE);
		uploadButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		uploadButton.setBackground(new Color(142, 39, 148));
		uploadButton.setBounds(201, 361, 169, 20);
		roundedPanel.add(uploadButton);
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setLayout(null);
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(10, 109, 245, 474);
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