/**
 * This class represents the Add Group Application view for staff. 
 * 
 * The various swing components for the view along with their
 * characteristics and attributes. Relevant listeners are added
 * to buttons and their action performed methods defined.
 * 
 * @author Ian Shields, 1506416
 * @version 07/09/2015
 */
package com.sam.client;
//
import java.awt.Dimension;
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
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import com.sam.server.Agent;
import com.sam.server.Application;
import com.sam.server.Enquiry;
import com.sam.server.User;
import com.toedter.calendar.JDateChooser;

import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class AddGroupApplicationStaff extends JFrame {

	// field variables
	private static final long serialVersionUID = 1L;

	private final JLabel lblNewLabel = new JLabel("New label");

	
	JLabel logoLabel = new JLabel("");
	JLabel label_2 = new JLabel();
	JButton btnBack = new JButton("Back");
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	RoundedPanel groupPanel = new RoundedPanel();
	RoundedPanel studentsPanel = new RoundedPanel();
	JLabel agentLabel = new JLabel("Application: ");
	private JTextField sourceField;
	private JTextField totalPriceField;
	private JTextField fname;
	private JTextField lname;
	private JTextField phone;
	private JTextField email;
	private JTextField passport;
	private JTextField address;
	private JTextField phone3;
	private JTextField email3;
	private JTextField passport3;
	private JTextField fname3;
	private JTextField address3;
	private JTextField lname3;
	private JTextField phone4;
	private JTextField email4;
	private JTextField passport4;
	private JTextField fname4;
	private JTextField address4;
	private JTextField lname4;
	private JTextField phone5;
	private JTextField email5;
	private JTextField passport5;
	private JTextField fname5;
	private JTextField address5;
	private JTextField lname5;
	private JTextField phone2;
	private JTextField email2;
	private JTextField passport2;
	private JTextField fname2;
	private JTextField address2;
	private JTextField lname2;
	private JFileChooser chooser;
	private int appTracker;

	

	// constructor
	public AddGroupApplicationStaff(User user) throws ParseException {

		this.setVisible(true);
		this.getContentPane().setBackground(Color.WHITE);
		this.setBounds(100, 100, 1050, 630);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		lblNewLabel.setBounds(40, -30, 200, 31);
		this.getContentPane().add(lblNewLabel);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		// label for displaying logo
		this.setTitle("Student Application Management");
		logoLabel.setBounds(10, 0, 250, 109);
		this.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource(
				"/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnBack.setForeground(Color.WHITE);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBackground(new Color(142, 39, 148));
		btnBack.setBounds(775, 49, 234, 41);
		this.getContentPane().add(btnBack);
		
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

		tabbedPane.setBounds(280, 111, 750, 474);
		this.getContentPane().add(tabbedPane);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setForeground(new Color(142, 39, 148));

		groupPanel.setForeground(new Color(142, 39, 148));
		groupPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Group", null, groupPanel, null);
		groupPanel.setLayout(null);

		JComboBox countryCombo = new JComboBox();
		countryCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		countryCombo.setBounds(159, 109, 180, 20);
		groupPanel.add(countryCombo);

		JLabel label_1 = new JLabel("Country: ");
		label_1.setForeground(new Color(142, 39, 148));
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(42, 111, 131, 14);
		groupPanel.add(label_1);

		JLabel label_4 = new JLabel("Nationality:");
		label_4.setForeground(new Color(142, 39, 148));
		label_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_4.setBounds(42, 193, 131, 14);
		groupPanel.add(label_4);

		JComboBox nationCombo = new JComboBox();
		nationCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nationCombo.setBounds(159, 187, 180, 20);
		groupPanel.add(nationCombo);

		JLabel label_5 = new JLabel("Agent: ");
		label_5.setForeground(new Color(142, 39, 148));
		label_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_5.setBounds(44, 224, 131, 14);
		groupPanel.add(label_5);

		JComboBox agentCombo = new JComboBox();
		agentCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		agentCombo.setBounds(161, 218, 178, 20);
		groupPanel.add(agentCombo);

		JLabel label_7 = new JLabel("Status:");
		label_7.setForeground(new Color(142, 39, 148));
		label_7.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_7.setBounds(42, 255, 133, 14);
		groupPanel.add(label_7);

		JComboBox statusCombo = new JComboBox();
		statusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statusCombo.setBounds(159, 253, 180, 20);
		groupPanel.add(statusCombo);

		JLabel label_8 = new JLabel("Pay Status:");
		label_8.setForeground(new Color(142, 39, 148));
		label_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_8.setBounds(42, 286, 132, 14);
		groupPanel.add(label_8);

		JComboBox payStatusCombo = new JComboBox();
		payStatusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		payStatusCombo.setBounds(160, 284, 179, 20);
		groupPanel.add(payStatusCombo);

		JLabel label_9 = new JLabel("Sales Advisor: ");
		label_9.setForeground(new Color(142, 39, 148));
		label_9.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_9.setBounds(42, 316, 132, 14);
		groupPanel.add(label_9);

		JComboBox salesAdvisorCombo = new JComboBox();
		salesAdvisorCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		salesAdvisorCombo.setBounds(160, 315, 179, 20);
		groupPanel.add(salesAdvisorCombo);

		JLabel label_10 = new JLabel("Duration (days) : ");
		label_10.setForeground(new Color(142, 39, 148));
		label_10.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_10.setBounds(42, 360, 121, 14);
		groupPanel.add(label_10);

		JLabel durationLabel = new JLabel("");
		durationLabel.setForeground(new Color(142, 39, 148));
		durationLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		durationLabel.setBounds(169, 360, 121, 14);
		groupPanel.add(durationLabel);

		JLabel label_12 = new JLabel("Arrival Date: ");
		label_12.setForeground(new Color(142, 39, 148));
		label_12.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_12.setBounds(371, 111, 151, 14);
		groupPanel.add(label_12);

		JDateChooser arrChooser = new JDateChooser();
		arrChooser.setBounds(501, 107, 179, 25);
		groupPanel.add(arrChooser);

		JLabel label_13 = new JLabel("Departure Date: ");
		label_13.setForeground(new Color(142, 39, 148));
		label_13.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_13.setBounds(372, 149, 150, 14);
		groupPanel.add(label_13);

		JDateChooser depChooser = new JDateChooser();
		depChooser.setBounds(500, 145, 180, 25);
		groupPanel.add(depChooser);

		JLabel label_15 = new JLabel("Last Contacted: ");
		label_15.setForeground(new Color(142, 39, 148));
		label_15.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_15.setBounds(371, 191, 151, 14);
		groupPanel.add(label_15);

		JDateChooser lastContactedChooser = new JDateChooser();
		lastContactedChooser.setBounds(501, 185, 179, 25);
		groupPanel.add(lastContactedChooser);

		JComboBox visaStatusCombo = new JComboBox();
		visaStatusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		visaStatusCombo.setBounds(500, 220, 180, 20);
		groupPanel.add(visaStatusCombo);

		JLabel label_16 = new JLabel("Visa Status: ");
		label_16.setForeground(new Color(142, 39, 148));
		label_16.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_16.setBounds(372, 226, 150, 14);
		groupPanel.add(label_16);

		JLabel label_17 = new JLabel("Campus: ");
		label_17.setForeground(new Color(142, 39, 148));
		label_17.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_17.setBounds(372, 255, 150, 14);
		groupPanel.add(label_17);

		JComboBox campusCombo = new JComboBox();
		campusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		campusCombo.setBounds(500, 251, 180, 20);
		groupPanel.add(campusCombo);

		JLabel label_18 = new JLabel("Course:");
		label_18.setForeground(new Color(142, 39, 148));
		label_18.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_18.setBounds(372, 286, 150, 14);
		groupPanel.add(label_18);

		JComboBox courseCombo = new JComboBox();
		courseCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseCombo.setBounds(500, 282, 180, 20);
		groupPanel.add(courseCombo);

		JLabel lblWeeklyPrice = new JLabel("Weekly Price: \u00A3");
		lblWeeklyPrice.setForeground(new Color(142, 39, 148));
		lblWeeklyPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWeeklyPrice.setBounds(371, 358, 151, 14);
		groupPanel.add(lblWeeklyPrice);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		cancelButton.setBackground(new Color(142, 39, 148));
		cancelButton.setBounds(421, 404, 139, 31);
		groupPanel.add(cancelButton);

		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
			}
		});
		nextButton.setForeground(Color.WHITE);
		nextButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		nextButton.setBackground(new Color(142, 39, 148));
		nextButton.setBounds(570, 403, 139, 31);
		groupPanel.add(nextButton);

		JLabel weeklyPrice2 = new JLabel("");
		weeklyPrice2.setForeground(new Color(142, 39, 148));
		weeklyPrice2.setFont(new Font("Tahoma", Font.BOLD, 14));
		weeklyPrice2.setBounds(488, 360, 151, 14);
		groupPanel.add(weeklyPrice2);

		JLabel warningLabel = new JLabel("Please complete all fields.");
		warningLabel.setForeground(Color.RED);
		warningLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		warningLabel.setBounds(42, 59, 503, 20);
		warningLabel.setVisible(false);
		groupPanel.add(warningLabel);

		JLabel lblAddGroupApplication = new JLabel(
				"Add Group Application: Group Information");
		lblAddGroupApplication.setForeground(new Color(142, 39, 148));
		lblAddGroupApplication.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAddGroupApplication.setBounds(42, 23, 450, 25);
		groupPanel.add(lblAddGroupApplication);

		JLabel label_19 = new JLabel("Source: ");
		label_19.setForeground(new Color(142, 39, 148));
		label_19.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_19.setBounds(371, 319, 150, 14);
		groupPanel.add(label_19);

		sourceField = new JTextField();
		sourceField.setForeground(new Color(142, 39, 148));
		sourceField.setColumns(10);
		sourceField.setBounds(501, 312, 179, 25);
		groupPanel.add(sourceField);

		JLabel label_20 = new JLabel("Total Price: ");
		label_20.setForeground(new Color(142, 39, 148));
		label_20.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_20.setBounds(42, 152, 131, 14);
		groupPanel.add(label_20);

		totalPriceField = new JTextField();
		totalPriceField.setForeground(new Color(142, 39, 148));
		totalPriceField.setColumns(10);
		totalPriceField.setBounds(160, 146, 179, 25);
		groupPanel.add(totalPriceField);
		
		String[]agents = new String[user.getAgentsList().size()+1];
		agents[0] = "Select";
		for(int i = 1; i < user.getAgentsList().size(); i++){
			agents[i] = user.getAgentsList().get(i).getCompanyName();
		}	
																																																																				
		agentCombo.setModel(new DefaultComboBoxModel(agents));
		countryCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		statusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "New", "Received", "Deposit Received", "Cancelled", "Enrolled"}));	
		nationCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		payStatusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Not Paid", "Deposit Paid", "Full Fee Paid"}));
		salesAdvisorCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
		visaStatusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "No Visa Required", "Not Applied", "Applied", "Visa Granted", "Visa Refused"}));
		campusCombo.setModel(new DefaultComboBoxModel(user.getCampuses()));
		courseCombo.setModel(new DefaultComboBoxModel(user.getCourses()));
		
		Controller.autoCalculateAppWeeklyPrice2(totalPriceField, arrChooser, depChooser, weeklyPrice2, durationLabel);

		studentsPanel.setForeground(new Color(142, 39, 148));
		studentsPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Students", null, studentsPanel, null);
		studentsPanel.setLayout(null);

		RoundedPanel roundedPanel = new RoundedPanel();
		roundedPanel.setBounds(10, 11, 714, 69);
		roundedPanel.setLayout(null);
		roundedPanel.setForeground(new Color(142, 39, 148));
		roundedPanel.setBackground(new Color(142, 39, 148));
		studentsPanel.add(roundedPanel);

		JLabel lblTel = new JLabel("Tel:");
		lblTel.setBounds(381, 8, 80, 14);
		roundedPanel.add(lblTel);
		lblTel.setForeground(Color.WHITE);
		lblTel.setFont(new Font("Dialog", Font.BOLD, 12));

		phone = new JTextField();
		phone.setFont(new Font("Tahoma", Font.PLAIN, 10));
		phone.setBounds(410, 5, 122, 23);
		roundedPanel.add(phone);
		phone.setForeground(new Color(142, 39, 148));
		phone.setColumns(10);

		JLabel label_24 = new JLabel("Email:");
		label_24.setBounds(542, 8, 62, 14);
		roundedPanel.add(label_24);
		label_24.setForeground(Color.WHITE);
		label_24.setFont(new Font("Dialog", Font.BOLD, 12));

		email = new JTextField();
		email.setFont(new Font("Tahoma", Font.PLAIN, 10));
		email.setBounds(583, 5, 113, 23);
		roundedPanel.add(email);
		email.setForeground(new Color(142, 39, 148));
		email.setColumns(10);

		JLabel lblName_3 = new JLabel("Name:");
		lblName_3.setBounds(10, 8, 80, 15);
		roundedPanel.add(lblName_3);
		lblName_3.setForeground(Color.WHITE);
		lblName_3.setFont(new Font("Dialog", Font.BOLD, 12));

		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setBounds(188, 8, 150, 14);
		roundedPanel.add(lblSurname);
		lblSurname.setForeground(Color.WHITE);
		lblSurname.setFont(new Font("Dialog", Font.BOLD, 12));

		JLabel label_25 = new JLabel("Passport No:");
		label_25.setBounds(10, 38, 131, 14);
		roundedPanel.add(label_25);
		label_25.setForeground(Color.WHITE);
		label_25.setFont(new Font("Dialog", Font.BOLD, 12));

		passport = new JTextField();
		passport.setFont(new Font("Tahoma", Font.PLAIN, 10));
		passport.setBounds(88, 36, 105, 23);
		roundedPanel.add(passport);
		passport.setForeground(new Color(142, 39, 148));
		passport.setColumns(10);

		fname = new JTextField();
		fname.setFont(new Font("Tahoma", Font.PLAIN, 10));
		fname.setBounds(53, 8, 125, 23);
		roundedPanel.add(fname);
		fname.setForeground(new Color(142, 39, 148));
		fname.setColumns(10);

		JLabel label_26 = new JLabel("Address:");
		label_26.setBounds(203, 38, 132, 14);
		roundedPanel.add(label_26);
		label_26.setForeground(Color.WHITE);
		label_26.setFont(new Font("Dialog", Font.BOLD, 12));

		address = new JTextField();
		address.setFont(new Font("Tahoma", Font.PLAIN, 10));
		address.setBounds(260, 35, 226, 23);
		roundedPanel.add(address);
		address.setForeground(new Color(142, 39, 148));
		address.setColumns(10);

		lname = new JTextField();
		lname.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lname.setBounds(248, 5, 123, 23);
		roundedPanel.add(lname);
		lname.setForeground(new Color(142, 39, 148));
		lname.setColumns(10);

		JComboBox genderCombo = new JComboBox();
		genderCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		genderCombo.setBounds(492, 35, 44, 21);
		roundedPanel.add(genderCombo);

		JLabel dob = new JLabel("DOB:");
		dob.setForeground(Color.WHITE);
		dob.setFont(new Font("Dialog", Font.BOLD, 12));
		dob.setBounds(542, 38, 62, 14);
		roundedPanel.add(dob);

		JLabel lblGender_4 = new JLabel("Gender: ");
		lblGender_4.setForeground(Color.WHITE);
		lblGender_4.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGender_4.setBounds(806, 38, 62, 14);
		roundedPanel.add(lblGender_4);

		JDateChooser dobChooser = new JDateChooser();
		dobChooser.setBounds(574, 36, 122, 25);
		roundedPanel.add(dobChooser);

		JButton cancel2Button = new JButton("Cancel");
		cancel2Button.setForeground(Color.WHITE);
		cancel2Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		cancel2Button.setBackground(new Color(142, 39, 148));
		cancel2Button.setBounds(312, 403, 115, 25);
		studentsPanel.add(cancel2Button);

		JButton addAppButton = new JButton("Add Applications");

		addAppButton.setForeground(Color.WHITE);
		addAppButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		addAppButton.setBackground(new Color(142, 39, 148));
		addAppButton.setBounds(572, 403, 152, 25);
		studentsPanel.add(addAppButton);

		JButton btnAddMore = new JButton("Add More");

		btnAddMore.setForeground(Color.WHITE);
		btnAddMore.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAddMore.setBackground(new Color(142, 39, 148));
		btnAddMore.setBounds(440, 403, 122, 25);
		studentsPanel.add(btnAddMore);

		RoundedPanel roundedPanel_3 = new RoundedPanel();
		roundedPanel_3.setLayout(null);
		roundedPanel_3.setForeground(new Color(142, 39, 148));
		roundedPanel_3.setBackground(new Color(142, 39, 148));
		roundedPanel_3.setBounds(10, 91, 714, 69);
		studentsPanel.add(roundedPanel_3);

		JLabel lblTel_1 = new JLabel("Tel:");
		lblTel_1.setForeground(Color.WHITE);
		lblTel_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTel_1.setBounds(381, 8, 80, 14);
		roundedPanel_3.add(lblTel_1);

		JLabel label_34 = new JLabel("Email:");
		label_34.setForeground(Color.WHITE);
		label_34.setFont(new Font("Dialog", Font.BOLD, 12));
		label_34.setBounds(542, 8, 62, 14);
		roundedPanel_3.add(label_34);

		JLabel lblName_2 = new JLabel("Name:");
		lblName_2.setForeground(Color.WHITE);
		lblName_2.setFont(new Font("Dialog", Font.BOLD, 12));
		lblName_2.setBounds(10, 8, 131, 15);
		roundedPanel_3.add(lblName_2);

		JLabel lblSurname_1 = new JLabel("Surname:");
		lblSurname_1.setForeground(Color.WHITE);
		lblSurname_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSurname_1.setBounds(188, 8, 67, 14);
		roundedPanel_3.add(lblSurname_1);

		JLabel label_37 = new JLabel("Passport No:");
		label_37.setForeground(Color.WHITE);
		label_37.setFont(new Font("Dialog", Font.BOLD, 12));
		label_37.setBounds(10, 38, 131, 14);
		roundedPanel_3.add(label_37);

		JLabel label_38 = new JLabel("Address:");
		label_38.setForeground(Color.WHITE);
		label_38.setFont(new Font("Dialog", Font.BOLD, 12));
		label_38.setBounds(203, 38, 132, 14);
		roundedPanel_3.add(label_38);

		JComboBox genderCombo2 = new JComboBox();
		genderCombo2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		genderCombo2.setBounds(492, 35, 44, 21);
		roundedPanel_3.add(genderCombo2);

		JLabel dob2 = new JLabel("DOB:");
		dob2.setForeground(Color.WHITE);
		dob2.setFont(new Font("Dialog", Font.BOLD, 12));
		dob2.setBounds(542, 38, 62, 14);
		roundedPanel_3.add(dob2);

		JLabel lblGender_3 = new JLabel("Gender: ");
		lblGender_3.setForeground(Color.WHITE);
		lblGender_3.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGender_3.setBounds(806, 38, 62, 14);
		roundedPanel_3.add(lblGender_3);

		JDateChooser dobChooser2 = new JDateChooser();
		dobChooser2.setBounds(574, 36, 122, 25);
		roundedPanel_3.add(dobChooser2);
		
		fname2 = new JTextField();
		fname2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		fname2.setBounds(53, 8, 125, 23);
		roundedPanel_3.add(fname2);
		fname2.setForeground(new Color(142, 39, 148));
		fname2.setColumns(10);

		passport2 = new JTextField();
		passport2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		passport2.setBounds(88, 37, 105, 23);
		roundedPanel_3.add(passport2);
		passport2.setForeground(new Color(142, 39, 148));
		passport2.setColumns(10);

		lname2 = new JTextField();
		lname2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lname2.setBounds(248, 5, 123, 23);
		roundedPanel_3.add(lname2);
		lname2.setForeground(new Color(142, 39, 148));
		lname2.setColumns(10);

		phone2 = new JTextField();
		phone2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		phone2.setBounds(410, 5, 122, 23);
		roundedPanel_3.add(phone2);
		phone2.setForeground(new Color(142, 39, 148));
		phone2.setColumns(10);

		email2 = new JTextField();
		email2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		email2.setBounds(583, 5, 113, 23);
		roundedPanel_3.add(email2);
		email2.setForeground(new Color(142, 39, 148));
		email2.setColumns(10);

		address2 = new JTextField();
		address2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		address2.setBounds(260, 35, 226, 23);
		roundedPanel_3.add(address2);
		address2.setForeground(new Color(142, 39, 148));
		address2.setColumns(10);

		RoundedPanel roundedPanel_4 = new RoundedPanel();
		roundedPanel_4.setLayout(null);
		roundedPanel_4.setForeground(new Color(142, 39, 148));
		roundedPanel_4.setBackground(new Color(142, 39, 148));
		roundedPanel_4.setBounds(10, 171, 714, 69);
		studentsPanel.add(roundedPanel_4);

		JLabel lblTel_2 = new JLabel("Tel");
		lblTel_2.setForeground(Color.WHITE);
		lblTel_2.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTel_2.setBounds(381, 8, 80, 14);
		roundedPanel_4.add(lblTel_2);

		JLabel label_40 = new JLabel("Email:");
		label_40.setForeground(Color.WHITE);
		label_40.setFont(new Font("Dialog", Font.BOLD, 12));
		label_40.setBounds(542, 8, 62, 14);
		roundedPanel_4.add(label_40);

		JLabel lblName_1 = new JLabel("Name:");
		lblName_1.setForeground(Color.WHITE);
		lblName_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblName_1.setBounds(10, 8, 131, 15);
		roundedPanel_4.add(lblName_1);

		JLabel lblSurname_2 = new JLabel("Surname:");
		lblSurname_2.setForeground(Color.WHITE);
		lblSurname_2.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSurname_2.setBounds(188, 8, 150, 14);
		roundedPanel_4.add(lblSurname_2);

		JLabel label_43 = new JLabel("Passport No:");
		label_43.setForeground(Color.WHITE);
		label_43.setFont(new Font("Dialog", Font.BOLD, 12));
		label_43.setBounds(10, 38, 131, 14);
		roundedPanel_4.add(label_43);

		JLabel label_44 = new JLabel("Address:");
		label_44.setForeground(Color.WHITE);
		label_44.setFont(new Font("Dialog", Font.BOLD, 12));
		label_44.setBounds(203, 38, 132, 14);
		roundedPanel_4.add(label_44);

		JComboBox genderCombo3 = new JComboBox();
		genderCombo3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		genderCombo3.setBounds(492, 35, 44, 21);
		roundedPanel_4.add(genderCombo3);

		JLabel label_29 = new JLabel("DOB:");
		label_29.setForeground(Color.WHITE);
		label_29.setFont(new Font("Dialog", Font.BOLD, 12));
		label_29.setBounds(542, 38, 62, 14);
		roundedPanel_4.add(label_29);

		JLabel lblGender_2 = new JLabel("Gender:");
		lblGender_2.setForeground(Color.WHITE);
		lblGender_2.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGender_2.setBounds(806, 38, 62, 14);
		roundedPanel_4.add(lblGender_2);

		JDateChooser dobChooser3 = new JDateChooser();
		dobChooser3.setBounds(574, 36, 122, 25);
		roundedPanel_4.add(dobChooser3);
		
		fname3 = new JTextField();
		fname3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		fname3.setBounds(53, 6, 125, 23);
		roundedPanel_4.add(fname3);
		fname3.setForeground(new Color(142, 39, 148));
		fname3.setColumns(10);

		passport3 = new JTextField();
		passport3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		passport3.setBounds(88, 36, 105, 23);
		roundedPanel_4.add(passport3);
		passport3.setForeground(new Color(142, 39, 148));
		passport3.setColumns(10);

		lname3 = new JTextField();
		lname3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lname3.setBounds(248, 5, 123, 23);
		roundedPanel_4.add(lname3);
		lname3.setForeground(new Color(142, 39, 148));
		lname3.setColumns(10);

		phone3 = new JTextField();
		phone3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		phone3.setBounds(410, 5, 122, 23);
		roundedPanel_4.add(phone3);
		phone3.setForeground(new Color(142, 39, 148));
		phone3.setColumns(10);

		email3 = new JTextField();
		email3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		email3.setBounds(583, 5, 113, 23);
		roundedPanel_4.add(email3);
		email3.setForeground(new Color(142, 39, 148));
		email3.setColumns(10);

		address3 = new JTextField();
		address3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		address3.setBounds(260, 35, 226, 23);
		roundedPanel_4.add(address3);
		address3.setForeground(new Color(142, 39, 148));
		address3.setColumns(10);

		RoundedPanel roundedPanel_5 = new RoundedPanel();
		roundedPanel_5.setLayout(null);
		roundedPanel_5.setForeground(new Color(142, 39, 148));
		roundedPanel_5.setBackground(new Color(142, 39, 148));
		roundedPanel_5.setBounds(10, 251, 714, 69);
		studentsPanel.add(roundedPanel_5);

		JLabel lblTel_4 = new JLabel("Tel: ");
		lblTel_4.setForeground(Color.WHITE);
		lblTel_4.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTel_4.setBounds(381, 8, 80, 14);
		roundedPanel_5.add(lblTel_4);

		JLabel label_46 = new JLabel("Email:");
		label_46.setForeground(Color.WHITE);
		label_46.setFont(new Font("Dialog", Font.BOLD, 12));
		label_46.setBounds(542, 8, 62, 14);
		roundedPanel_5.add(label_46);

		JLabel lblName = new JLabel("Name:");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Dialog", Font.BOLD, 12));
		lblName.setBounds(10, 8, 131, 15);
		roundedPanel_5.add(lblName);

		JLabel lblSurname_3 = new JLabel("Surname:");
		lblSurname_3.setForeground(Color.WHITE);
		lblSurname_3.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSurname_3.setBounds(188, 8, 150, 14);
		roundedPanel_5.add(lblSurname_3);

		JLabel label_49 = new JLabel("Passport No:");
		label_49.setForeground(Color.WHITE);
		label_49.setFont(new Font("Dialog", Font.BOLD, 12));
		label_49.setBounds(10, 38, 131, 14);
		roundedPanel_5.add(label_49);

		JLabel label_50 = new JLabel("Address:");
		label_50.setForeground(Color.WHITE);
		label_50.setFont(new Font("Dialog", Font.BOLD, 12));
		label_50.setBounds(203, 38, 132, 14);
		roundedPanel_5.add(label_50);

		JComboBox genderCombo4 = new JComboBox();
		genderCombo4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		genderCombo4.setBounds(492, 35, 44, 21);
		roundedPanel_5.add(genderCombo4);

		JLabel label_27 = new JLabel("DOB:");
		label_27.setForeground(Color.WHITE);
		label_27.setFont(new Font("Dialog", Font.BOLD, 12));
		label_27.setBounds(542, 38, 62, 14);
		roundedPanel_5.add(label_27);

		JLabel lblGender_1 = new JLabel("Gender:");
		lblGender_1.setForeground(Color.WHITE);
		lblGender_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGender_1.setBounds(806, 38, 62, 14);
		roundedPanel_5.add(lblGender_1);

		JDateChooser dobChooser4 = new JDateChooser();
		dobChooser4.setBounds(574, 36, 122, 25);
		roundedPanel_5.add(dobChooser4);
		
		fname4 = new JTextField();
		fname4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		fname4.setBounds(53, 8, 125, 23);
		roundedPanel_5.add(fname4);
		fname4.setForeground(new Color(142, 39, 148));
		fname4.setColumns(10);

		passport4 = new JTextField();
		passport4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		passport4.setBounds(88, 38, 105, 23);
		roundedPanel_5.add(passport4);
		passport4.setForeground(new Color(142, 39, 148));
		passport4.setColumns(10);

		lname4 = new JTextField();
		lname4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lname4.setBounds(248, 5, 124, 23);
		roundedPanel_5.add(lname4);
		lname4.setForeground(new Color(142, 39, 148));
		lname4.setColumns(10);

		phone4 = new JTextField();
		phone4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		phone4.setBounds(410, 5, 122, 23);
		roundedPanel_5.add(phone4);
		phone4.setForeground(new Color(142, 39, 148));
		phone4.setColumns(10);

		email4 = new JTextField();
		email4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		email4.setBounds(583, 5, 113, 23);
		roundedPanel_5.add(email4);
		email4.setForeground(new Color(142, 39, 148));
		email4.setColumns(10);

		address4 = new JTextField();
		address4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		address4.setBounds(260, 35, 226, 23);
		roundedPanel_5.add(address4);
		address4.setForeground(new Color(142, 39, 148));
		address4.setColumns(10);

		RoundedPanel roundedPanel_6 = new RoundedPanel();
		roundedPanel_6.setLayout(null);
		roundedPanel_6.setForeground(new Color(142, 39, 148));
		roundedPanel_6.setBackground(new Color(142, 39, 148));
		roundedPanel_6.setBounds(10, 331, 714, 69);
		studentsPanel.add(roundedPanel_6);

		JLabel lblTel_3 = new JLabel("Tel:");
		lblTel_3.setForeground(Color.WHITE);
		lblTel_3.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTel_3.setBounds(381, 8, 80, 14);
		roundedPanel_6.add(lblTel_3);

		JLabel label_52 = new JLabel("Email:");
		label_52.setForeground(Color.WHITE);
		label_52.setFont(new Font("Dialog", Font.BOLD, 12));
		label_52.setBounds(542, 8, 62, 14);
		roundedPanel_6.add(label_52);

		JLabel lblFName = new JLabel("Name:");
		lblFName.setForeground(Color.WHITE);
		lblFName.setFont(new Font("Dialog", Font.BOLD, 12));
		lblFName.setBounds(10, 8, 131, 15);
		roundedPanel_6.add(lblFName);

		JLabel lblSurname_4 = new JLabel("Surname:");
		lblSurname_4.setForeground(Color.WHITE);
		lblSurname_4.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSurname_4.setBounds(188, 8, 150, 14);
		roundedPanel_6.add(lblSurname_4);

		JLabel label_55 = new JLabel("Passport No:");
		label_55.setForeground(Color.WHITE);
		label_55.setFont(new Font("Dialog", Font.BOLD, 12));
		label_55.setBounds(10, 38, 131, 14);
		roundedPanel_6.add(label_55);

		JLabel label_56 = new JLabel("Address:");
		label_56.setForeground(Color.WHITE);
		label_56.setFont(new Font("Dialog", Font.BOLD, 12));
		label_56.setBounds(203, 38, 132, 14);
		roundedPanel_6.add(label_56);

		JComboBox genderCombo5 = new JComboBox();
		genderCombo5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		genderCombo5.setBounds(492, 35, 44, 21);
		roundedPanel_6.add(genderCombo5);

		JLabel label_28 = new JLabel("DOB:");
		label_28.setForeground(Color.WHITE);
		label_28.setFont(new Font("Dialog", Font.BOLD, 12));
		label_28.setBounds(542, 38, 62, 14);
		roundedPanel_6.add(label_28);

		JLabel lblGender = new JLabel("Gender:");
		lblGender.setForeground(Color.WHITE);
		lblGender.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGender.setBounds(806, 38, 62, 14);
		roundedPanel_6.add(lblGender);

		JDateChooser dobChooser5 = new JDateChooser();
		dobChooser5.setBounds(574, 36, 122, 25);
		roundedPanel_6.add(dobChooser5);
		
		fname5 = new JTextField();
		fname5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		fname5.setBounds(53, 8, 125, 23);
		roundedPanel_6.add(fname5);
		fname5.setForeground(new Color(142, 39, 148));
		fname5.setColumns(10);

		passport5 = new JTextField();
		passport5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		passport5.setBounds(88, 38, 105, 23);
		roundedPanel_6.add(passport5);
		passport5.setForeground(new Color(142, 39, 148));
		passport5.setColumns(10);

		lname5 = new JTextField();
		lname5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lname5.setBounds(248, 5, 125, 23);
		roundedPanel_6.add(lname5);
		lname5.setForeground(new Color(142, 39, 148));
		lname5.setColumns(10);

		phone5 = new JTextField();
		phone5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		phone5.setBounds(410, 5, 122, 23);
		roundedPanel_6.add(phone5);
		phone5.setForeground(new Color(142, 39, 148));
		phone5.setColumns(10);

		email5 = new JTextField();
		email5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		email5.setBounds(583, 5, 113, 23);
		roundedPanel_6.add(email5);
		email5.setForeground(new Color(142, 39, 148));
		email5.setColumns(10);

		address5 = new JTextField();
		address5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		address5.setBounds(260, 35, 226, 23);
		roundedPanel_6.add(address5);
		address5.setForeground(new Color(142, 39, 148));
		address5.setColumns(10);

		JLabel warningLabel2 = new JLabel("Please complete all fields.");
		warningLabel2.setForeground(Color.RED);
		warningLabel2.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel2.setBounds(20, 421, 292, 14);
		warningLabel2.setVisible(false);
		studentsPanel.add(warningLabel2);
		
	
		genderCombo.setModel(new DefaultComboBoxModel(new String[] {"-", "M", "F"}));
		
		genderCombo2.setModel(new DefaultComboBoxModel(new String[] {"-", "M", "F"}));
		genderCombo3.setModel(new DefaultComboBoxModel(new String[] {"-", "M", "F"}));
		genderCombo4.setModel(new DefaultComboBoxModel(new String[] {"-", "M", "F"}));
		genderCombo5.setModel(new DefaultComboBoxModel(new String[] {"-", "M", "F"}));
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setLayout(null);
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(10, 111, 245, 474);
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
		

		addAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				Controller.addGroupApplication(AddGroupApplicationStaff.this, user, countryCombo, totalPriceField,
						nationCombo, agentCombo, genderCombo, statusCombo,
						payStatusCombo, salesAdvisorCombo, arrChooser,
						depChooser, dobChooser, lastContactedChooser,
						visaStatusCombo, campusCombo, courseCombo, sourceField,
						dobChooser2, dobChooser3, dobChooser4, dobChooser5,
						fname, lname, phone, email, passport, address, fname2,
						lname2, phone2, email2, passport2, address2, fname3,
						lname3, phone3, email3, passport3, address3, fname4,
						lname4, phone4, email4, passport4, address4, fname5,
						lname5, phone5, email5, address5, passport5,
						genderCombo2, genderCombo3, genderCombo4, genderCombo5,
						weeklyPrice2, warningLabel, warningLabel2,
						durationLabel);
				
				
			}
		});

		btnAddMore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Controller.addMoreToGroup(user, genderCombo, statusCombo,
						dobChooser, dobChooser2, dobChooser3, dobChooser4,
						dobChooser5, fname, lname, phone, email, passport,
						address, fname2, lname2, phone2, email2, passport2,
						address2, fname3, lname3, phone3, email3, passport3,
						address3, fname4, lname4, phone4, email4, passport4,
						address4, fname5, lname5, phone5, email5, address5,
						passport5, genderCombo2, genderCombo3, genderCombo4,
						genderCombo5, weeklyPrice2, warningLabel,
						warningLabel2, durationLabel);


			}
		});

		this.setVisible(true);
	}
}
