/**
 * This class represents the AddApplication view for agents. 
 * 
 * The various swing components for the view along with their
 * characteristics and attributes. Relevant listeners are added
 * to buttons and their action performed methods defined.
 * 
 * @author Ian Shields, 1506416
 * @version 07/09/2015
 */

package com.sam.client;
import java.awt.Desktop;
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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
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

public class AddApplicationAgents extends JPanel {

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
	public AddApplicationAgents(User user) {
		this.user = user;	
		initialize(user);
		this.frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		
	}

	// initialize method adds frame components and sets paramaters
	public void initialize(User user) {
		frame = new JFrame();
		frame.setResizable(true);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1050, 630);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		lblNewLabel.setBounds(40, -30, 200, 31);
		frame.getContentPane().add(lblNewLabel);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		// label for displaying logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(10, 0, 249, 93);
		frame.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// need to set this to NOT CONNECTED when the client/server connection ends
		frame.setTitle("Student Application Management");

		RoundedPanel roundedPanel = new RoundedPanel();
		
		roundedPanel.setForeground(new Color(142, 39, 148));
		roundedPanel.setBackground(Color.WHITE);
		roundedPanel.setBounds(278, 104, 746, 474);
		frame.getContentPane().add(roundedPanel);
		roundedPanel.setLayout(null);
		
		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setBounds(42, 291, 132, 14);
		roundedPanel.add(addressLabel);
		addressLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		addressLabel.setForeground(new Color(142, 39, 148));
		
		String[]agents = new String[user.getAgentsList().size()+1];
		agents[0] = "Select";
		for(int i = 1; i < user.getAgentsList().size(); i++){
			agents[i] = user.getAgentsList().get(i).getCompanyName();
		}
		
		JLabel lblEnquiryDate = new JLabel("DOB:");
		lblEnquiryDate.setBounds(375, 224, 151, 14);
		roundedPanel.add(lblEnquiryDate);
		lblEnquiryDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnquiryDate.setForeground(new Color(142, 39, 148));
		
		JComboBox countryCombo = new JComboBox();
		countryCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		countryCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		countryCombo.setBounds(131, 186, 218, 20);
		roundedPanel.add(countryCombo);
		
		JLabel lblNation = new JLabel("Nationality:");
		lblNation.setBounds(44, 223, 131, 14);
		roundedPanel.add(lblNation);
		lblNation.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNation.setForeground(new Color(142, 39, 148));
		
		JLabel lblCountry = new JLabel("Country: ");
		lblCountry.setBounds(44, 188, 131, 14);
		roundedPanel.add(lblCountry);
		lblCountry.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCountry.setForeground(new Color(142, 39, 148));
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(44, 89, 131, 15);
		roundedPanel.add(lblFirstName);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFirstName.setForeground(new Color(142, 39, 148));
		
		JTextField firstNameField = new JTextField();
		firstNameField.setBounds(132, 84, 217, 25);
		roundedPanel.add(firstNameField);
		firstNameField.setForeground(new Color(142, 39, 148));
		firstNameField.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name: ");
		lblLastName.setBounds(375, 89, 150, 14);
		roundedPanel.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLastName.setForeground(new Color(142, 39, 148));
		
		JTextField lastNameField = new JTextField();
		lastNameField.setBounds(482, 84, 199, 25);
		roundedPanel.add(lastNameField);
		lastNameField.setForeground(new Color(142, 39, 148));
		lastNameField.setColumns(10);
		
		JLabel lblStartDate = new JLabel("Arrival Date: ");
		lblStartDate.setBounds(375, 157, 151, 14);
		roundedPanel.add(lblStartDate);
		lblStartDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStartDate.setForeground(new Color(142, 39, 148));
		
		JLabel lblEndDate = new JLabel("Departure Date: ");
		lblEndDate.setBounds(376, 189, 150, 14);
		roundedPanel.add(lblEndDate);
		lblEndDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEndDate.setForeground(new Color(142, 39, 148));
		
		JLabel lblAddNewEnquiry = new JLabel("Add New Application:");
		lblAddNewEnquiry.setForeground(new Color(142, 39, 148));
		lblAddNewEnquiry.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddNewEnquiry.setBounds(42, 23, 181, 31);
		roundedPanel.add(lblAddNewEnquiry);
		
		JLabel uploadedLabel = new JLabel("Attached!");
		uploadedLabel.setForeground(new Color(0, 128, 0));
		uploadedLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		uploadedLabel.setBounds(42, 367, 132, 29);
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
		uploadButton.setBounds(184, 369, 165, 25);
		roundedPanel.add(uploadButton);
		
		warningLabel.setForeground(Color.RED);
		warningLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel.setBounds(48, 430, 652, 33);
		roundedPanel.add(warningLabel);
		warningLabel.setVisible(false);
		
		JDateChooser arrivalDateField = new JDateChooser();
		
		arrivalDateField.setBounds(483, 153, 198, 25);
		roundedPanel.add(arrivalDateField);
		
		
		JDateChooser depDateCombo = new JDateChooser();
		
		depDateCombo.setBounds(482, 185, 199, 25);
		roundedPanel.add(depDateCombo);
		
		JDateChooser dobCombo = new JDateChooser();
		dobCombo.setBounds(482, 217, 199, 25);
		roundedPanel.add(dobCombo);
		
		JComboBox genderCombo = new JComboBox();
		genderCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		genderCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "M", "F"}));
		genderCombo.setBounds(131, 248, 218, 20);
		roundedPanel.add(genderCombo);
		
		JComboBox nationCombo = new JComboBox();
		nationCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nationCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		nationCombo.setBounds(131, 217, 218, 20);
		roundedPanel.add(nationCombo);
		
		JComboBox campusCombo = new JComboBox();
		campusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		campusCombo.setModel(new DefaultComboBoxModel(user.getCampuses()));
		campusCombo.setBounds(481, 253, 200, 20);
		roundedPanel.add(campusCombo);
		
		JTextField passportField = new JTextField();
		passportField.setForeground(new Color(142, 39, 148));
		passportField.setColumns(10);
		passportField.setBounds(132, 153, 217, 25);
		roundedPanel.add(passportField);
		
		JLabel lblPrice = new JLabel("");
		lblPrice.setForeground(new Color(142, 39, 148));
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrice.setBounds(665, 430, 95, 14);
		lblPrice.setVisible(true);
		roundedPanel.add(lblPrice);
		
		JLabel lblPassportNo = new JLabel("Passport No:");
		lblPassportNo.setForeground(new Color(142, 39, 148));
		lblPassportNo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassportNo.setBounds(44, 157, 131, 14);
		roundedPanel.add(lblPassportNo);
		
		JLabel lblGender = new JLabel("Gender: ");
		lblGender.setForeground(new Color(142, 39, 148));
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGender.setBounds(43, 254, 133, 14);
		roundedPanel.add(lblGender);
		
		JLabel lblCampus = new JLabel("Campus: ");
		lblCampus.setForeground(new Color(142, 39, 148));
		lblCampus.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCampus.setBounds(375, 257, 150, 14);
		roundedPanel.add(lblCampus);
		
	
		
		
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setLayout(null);
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(10, 104, 245, 474);
		frame.getContentPane().add(roundedPanel_1);
		
		JLabel label = new JLabel("Dashboard");
		label.setForeground(new Color(142, 39, 148));
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		label.setBounds(75, 11, 98, 17);
		roundedPanel_1.add(label);
		
		JButton button_2 = new JButton("Enrolled Students");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnrolledStudentsViewAgents esv = new EnrolledStudentsViewAgents(user);
				frame.dispose();
			}
		});
		button_2.setForeground(Color.WHITE);
		button_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_2.setBackground(new Color(142, 39, 148));
		button_2.setBounds(10, 248, 220, 39);
		roundedPanel_1.add(button_2);
		
		JButton button_3 = new JButton("View Applications");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewApplications va = new ViewApplications(user);
				frame.dispose();
			}
		});
		button_3.setForeground(Color.WHITE);
		button_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_3.setBackground(new Color(142, 39, 148));
		button_3.setBounds(10, 198, 220, 39);
		roundedPanel_1.add(button_3);
		
		JButton button_4 = new JButton("Add Group Application");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddGroupApplicationAgents agaa = new AddGroupApplicationAgents(user);
					frame.dispose();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_4.setForeground(Color.WHITE);
		button_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_4.setBackground(new Color(142, 39, 148));
		button_4.setBounds(10, 148, 220, 39);
		roundedPanel_1.add(button_4);
		
		JButton button_5 = new JButton("Add Application");
		
		button_5.setForeground(Color.WHITE);
		button_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_5.setBackground(new Color(142, 39, 148));
		button_5.setBounds(10, 98, 220, 39);
		roundedPanel_1.add(button_5);
		
		JButton button_8 = new JButton("Home");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		button_8.setForeground(Color.WHITE);
		button_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_8.setBackground(new Color(142, 39, 148));
		button_8.setBounds(10, 48, 220, 39);
		roundedPanel_1.add(button_8);
		
		JButton button = new JButton("Downloads");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DownloadsAgents da = new DownloadsAgents(user);
				frame.dispose();
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 14));
		button.setBackground(new Color(142, 39, 148));
		button.setBounds(10, 298, 220, 39);
		roundedPanel_1.add(button);
		
		JButton button_1 = new JButton("Website");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {

				     String url ="http://www.google.com/";

				     Desktop dt = Desktop.getDesktop();
				     URI uri = new URI(url);
				     dt.browse(uri.resolve(uri));


				 } catch (URISyntaxException ex) {
				     
				 } catch (IOException ex) {
				     
				 }
			
				
			}
		});
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_1.setBackground(new Color(142, 39, 148));
		button_1.setBounds(10, 348, 220, 39);
		roundedPanel_1.add(button_1);
		
		JButton button_6 = new JButton("Help");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {

				     String url ="http://www.google.com";

				     Desktop dt = Desktop.getDesktop();
				     URI uri = new URI(url);
				     dt.browse(uri.resolve(uri));


				 } catch (URISyntaxException ex) {
				     
				 } catch (IOException ex) {
				     
				 }
			
			}
		});
		button_6.setForeground(Color.WHITE);
		button_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_6.setBackground(new Color(142, 39, 148));
		button_6.setBounds(10, 398, 220, 39);
		roundedPanel_1.add(button_6);
		
		// search text field
		JTextField searchBox = new JTextField();
		searchBox.setBounds(282, 57, 398, 31);
		searchBox.setBorder(new RoundedCornerBorder());
		frame.getContentPane().add(searchBox);
		searchBox.setColumns(10);
		
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
					
						user.setCommand(searchBox.getText());
						user.setSearchApps(new ArrayList<Application>());
						ApplicationSearch as = new ApplicationSearch(user);
					
				}
			}
		});
		searchButton.setForeground(Color.WHITE);
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		searchButton.setBackground(new Color(142, 39, 148));
		searchButton.setBounds(690, 56, 73, 31);
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
		btnBack.setBounds(773, 47, 234, 41);
		frame.getContentPane().add(btnBack);
		
		JLabel courseLabel = new JLabel("Course:");
		courseLabel.setForeground(new Color(142, 39, 148));
		courseLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		courseLabel.setBounds(375, 288, 150, 14);
		roundedPanel.add(courseLabel);
		
		JComboBox courseCombo = new JComboBox();
		courseCombo.setModel(new DefaultComboBoxModel(user.getCourses()));
		courseCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseCombo.setBounds(481, 284, 200, 20);
		roundedPanel.add(courseCombo);
		
		JLabel lblDuration2 = new JLabel("");
		lblDuration2.setForeground(new Color(142, 39, 148));
		lblDuration2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDuration2.setBounds(209, 341, 121, 14);
		roundedPanel.add(lblDuration2);
		
		JTextArea addressField = new JTextArea();
		addressField.setBounds(130, 288, 218, 68);
		roundedPanel.add(addressField);
		addressField.setLineWrap(true);
		addressField.setWrapStyleWord(true);
		
		JTextField totalPriceField = new JTextField("0");
		JComboBox agentCombo = new JComboBox();
		agentCombo.setSelectedItem(user.getUsername());
		
		JComboBox statusCombo = new JComboBox();
		statusCombo.setSelectedItem("New");
		
		JComboBox payStatusCombo = new JComboBox();
		payStatusCombo.setSelectedItem("Not Paid");
		
		JComboBox salesAdvisorCombo = new JComboBox();
		salesAdvisorCombo.setSelectedItem(user.getStaffMember());
		System.out.println(user.getStaffMember());
		
		JTextField sourceField = new JTextField(user.getUsername());
	
		JDateChooser lastContactedCombo = new JDateChooser();
		Date date = new Date();
		lastContactedCombo.setDate(date);
		JComboBox visaStatusCombo = new JComboBox();
		visaStatusCombo.setSelectedItem("-");
		
		phoneField = new JTextField();
		phoneField.setForeground(new Color(142, 39, 148));
		phoneField.setColumns(10);
		phoneField.setBounds(131, 117, 218, 25);
		roundedPanel.add(phoneField);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		phoneLabel.setForeground(new Color(142, 39, 148));
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		phoneLabel.setBounds(43, 123, 150, 14);
		roundedPanel.add(phoneLabel);
		
		emailField = new JTextField();
		emailField.setForeground(new Color(142, 39, 148));
		emailField.setColumns(10);
		emailField.setBounds(482, 118, 199, 24);
		roundedPanel.add(emailField);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(142, 39, 148));
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmail.setBounds(375, 125, 150, 14);
		roundedPanel.add(lblEmail);
		
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(142, 39, 148));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancel.setBounds(425, 395, 139, 31);
		roundedPanel.add(btnCancel);
		
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fileSelected == true){
					user.setFile(fileChooser.getSelectedFile());
					int index = user.getFile().getName().indexOf(".");
					user.setCommand3(user.getFile().getName().substring(index));
				
				Controller.addApplicationAgent(frame, user, firstNameField, warningLabel,
						passportField, lastNameField,countryCombo,  nationCombo, 
						 genderCombo,  arrivalDateField,  depDateCombo,  dobCombo,  
						 campusCombo,  emailField,  phoneField,
						 courseCombo,  lblDuration2,  addressField);
				}
				else{
					user.setCommand3("false");
					Controller.addApplicationAgent(frame, user, firstNameField, warningLabel,
							passportField, lastNameField,countryCombo,  nationCombo, 
							 genderCombo,  arrivalDateField,  depDateCombo,  dobCombo,  
							 campusCombo,  emailField,  phoneField,
							 courseCombo,  lblDuration2,  addressField);
				}
			}
		});
		
		btnConfirm.setBackground(new Color(142, 39, 148));
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConfirm.setBounds(574, 394, 139, 31);
		roundedPanel.add(btnConfirm);
		
		
		
	}
}