/**
 * This class represents the Individual Application view for staff. 
 * 
 * The various swing components for the view along with their
 * characteristics and attributes. Relevant listeners are added
 * to buttons and their action performed methods defined. 
 * 
 * References:
 * http://stackoverflow.com/questions/10274750/java-swing-setting-margins-on-textarea-with-line-border
 * 
 * @author Ian Shields, 1506416
 * @version 07/09/2015
 */

package com.sam.client;
import java.awt.BorderLayout;
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
import java.io.File;
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

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;


public class IndividualApplicationView extends JFrame {

	// field variables
	private static final long serialVersionUID = 1L;
	
	private final JLabel lblNewLabel = new JLabel("New label");

	
	
	private JTextField firstNameField= new JTextField();
	private JTextField totalPriceField= new JTextField();
	private JTextField passportField= new JTextField();
	private JTextField lastNameField= new JTextField();
	private JTextField sourceField= new JTextField();
	JLabel logoLabel = new JLabel("");
	JLabel label_2 = new JLabel();
	JButton btnBack = new JButton("Back");
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	RoundedPanel generalPanel = new RoundedPanel();
	RoundedPanel contactPanel = new RoundedPanel();
	RoundedPanel notesPanel = new RoundedPanel();
	JLabel totalPriceLabel = new JLabel("Total Price: ");
	JLabel countryLabel = new JLabel("Country: ");
	JComboBox countryCombo = new JComboBox();
	JLabel nationalityLabel = new JLabel("Nationality:");
	JComboBox nationalityCombo = new JComboBox();
	JLabel lblApplication = new JLabel("Application: " );
	JLabel firstNameLabel = new JLabel("First Name:");
	JLabel passportLabel = new JLabel("Passport No:");
	JLabel lastNameLabel = new JLabel("Last Name: ");
	JLabel sourceLabel = new JLabel("Source: ");
	JLabel arrivalDateLabel = new JLabel("Arrival Date: ");
	JDateChooser arrivalDateChooser = new JDateChooser();
	JLabel departureDateLabel = new JLabel("Departure Date: ");
	JDateChooser departureDateChooser = new JDateChooser();
	JLabel dobLabel = new JLabel("DOB:");
	JDateChooser dobDateChooser = new JDateChooser();
	JLabel lastContactedLabel = new JLabel("Last Contacted: ");
	JDateChooser lastContactedDateChooser = new JDateChooser();
	JLabel visaStatusLabel = new JLabel("Visa Status: ");
	JComboBox visaStatusCombo = new JComboBox();
	JLabel campusLabel = new JLabel("Campus: ");
	JComboBox campusCombo = new JComboBox();
	JLabel agentLabel = new JLabel("Agent: ");
	JComboBox agentCombo = new JComboBox();
	JLabel genderLabel = new JLabel("Gender: ");
	JComboBox genderCombo = new JComboBox();
	JLabel statusLabel = new JLabel("Status:");
	JComboBox statusCombo = new JComboBox();
	JLabel payStatusLabel = new JLabel("Pay Status:");
	JComboBox payStatusCombo = new JComboBox();
	JLabel salesAdvisorLabel = new JLabel("Sales Advisor: ");
	JComboBox salesAdvisorCombo = new JComboBox();
	JLabel durationLabel = new JLabel("");
	JLabel weeklyPriceLabel = new JLabel("");
	
	private JTextArea addNoteArea;
	private JTextArea note1Area;
	private JTextArea note2Area;
	private JTextArea note3Area;
	private final JLabel noteAuthor = new JLabel("");
	private final JLabel noteDate = new JLabel("");
	private final JLabel noteTime = new JLabel("");
	private final JLabel noteAuthor2 = new JLabel("");
	private final JLabel noteDate2 = new JLabel("");
	private final JLabel noteTime2 = new JLabel("");
	private final JLabel noteAuthor3 = new JLabel("");
	private final JLabel noteDate3 = new JLabel("");
	private final JLabel noteTime3 = new JLabel("");
	private int l = 0;
	private int m = 1;
	private int r = 2;
	private final JLabel label_7 = new JLabel("Time:");
	private final JLabel label_8 = new JLabel("Date:");
	private final JLabel label_9 = new JLabel("Author:");
	private final JLabel label_10 = new JLabel("Time:");
	private final JLabel label_11 = new JLabel("Date:");
	private final JLabel label_12 = new JLabel("Author:");
	private JFileChooser fileChooser;
	private JTextField phoneField;
	private JTextField emailField;

	
	// constructor
	public IndividualApplicationView(User user, Application app) throws ParseException {
	
		this.setVisible(true);
		System.out.println("Success: " + app.getAddress());
		this.getContentPane().setBackground(Color.WHITE);
		this.setBounds(100, 100, 1050, 630);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		lblNewLabel.setBounds(40, -30, 200, 31);
		this.getContentPane().add(lblNewLabel);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	      
		
		// label for displaying logo
		
		logoLabel.setBounds(10, 0, 244, 93);
		this.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// need to set this to NOT CONNECTED when the client/server connection ends
		this.setTitle("Status: CONNECTED");
		String[]agents = new String[user.getAgentsList().size()];
		for(int i = 0; i < user.getAgentsList().size(); i++){
			agents[i] = user.getAgentsList().get(i).getCompanyName();
		}
		Date date = new SimpleDateFormat("dd/MM/yy").parse(app.getArrivalDate());
		Date date1 = new SimpleDateFormat("dd/MM/yy").parse(app.getDepartureDate());
		Date date2 = new SimpleDateFormat("dd/MM/yy").parse(app.getDob());
		Date date3 = new SimpleDateFormat("dd/MM/yy").parse(app.getLastContacted());;
		
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
		
		tabbedPane.setBounds(284, 94, 730, 474);
		this.getContentPane().add(tabbedPane);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setForeground(new Color(142, 39, 148));
		
		
		generalPanel.setForeground(new Color(142, 39, 148));
		generalPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("General", null, generalPanel, null);
		generalPanel.setLayout(null);
		
		
		
		lblApplication.setText("Application: " + app.getUid());
		lblApplication.setForeground(new Color(142, 39, 148));
		lblApplication.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblApplication.setBounds(20, 11, 254, 17);
		generalPanel.add(lblApplication);
		
	
		firstNameLabel.setForeground(new Color(142, 39, 148));
		firstNameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		firstNameLabel.setBounds(22, 44, 131, 15);
		generalPanel.add(firstNameLabel);
		
		firstNameField.setText(app.getFirstName());
		firstNameField.setForeground(new Color(142, 39, 148));
		firstNameField.setColumns(10);
		firstNameField.setBounds(140, 39, 178, 25);
		firstNameField.setEditable(true);
		generalPanel.add(firstNameField);
		
		
		totalPriceLabel.setForeground(new Color(142, 39, 148));
		totalPriceLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		totalPriceLabel.setBounds(22, 78, 131, 14);
		generalPanel.add(totalPriceLabel);
		
		totalPriceField.setText(app.getTotalPrice());
		totalPriceField.setForeground(new Color(142, 39, 148));
		totalPriceField.setColumns(10);
		totalPriceField.setBounds(140, 70, 178, 25);
		totalPriceField.setEditable(true);
		generalPanel.add(totalPriceField);
		
		
		passportLabel.setForeground(new Color(142, 39, 148));
		passportLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		passportLabel.setBounds(22, 107, 131, 14);
		generalPanel.add(passportLabel);
		
		passportField.setText(app.getPassportNo());
		passportField.setForeground(new Color(142, 39, 148));
		passportField.setColumns(10);
		passportField.setBounds(140, 103, 178, 25);
		passportField.setEditable(true);
		generalPanel.add(passportField);
		
		
		countryLabel.setForeground(new Color(142, 39, 148));
		countryLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		countryLabel.setBounds(22, 138, 131, 18);
		generalPanel.add(countryLabel);
		
		countryCombo.setEditable(true);
		countryCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		countryCombo.setSelectedItem(app.getCountry());
		countryCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		countryCombo.setBounds(139, 136, 179, 20);
		countryCombo.setEditable(true);
		generalPanel.add(countryCombo);
		
		
		nationalityLabel.setForeground(new Color(142, 39, 148));
		nationalityLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		nationalityLabel.setBounds(22, 173, 131, 14);
		generalPanel.add(nationalityLabel);
		
		nationalityCombo.setEditable(true);
		nationalityCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		nationalityCombo.setSelectedItem(app.getNationality());
		nationalityCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nationalityCombo.setBounds(139, 167, 179, 20);
		generalPanel.add(nationalityCombo);
		
		
		lastNameLabel.setForeground(new Color(142, 39, 148));
		lastNameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lastNameLabel.setBounds(394, 49, 150, 14);
		generalPanel.add(lastNameLabel);
		
		lastNameField.setText(app.getLastName());
		lastNameField.setForeground(new Color(142, 39, 148));
		lastNameField.setColumns(10);
		lastNameField.setBounds(523, 44, 179, 25);
		generalPanel.add(lastNameField);
		
		
		sourceLabel.setForeground(new Color(142, 39, 148));
		sourceLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		sourceLabel.setBounds(394, 83, 150, 14);
		generalPanel.add(sourceLabel);
		
		sourceField.setText(app.getSource());
		sourceField.setForeground(new Color(142, 39, 148));
		sourceField.setColumns(10);
		sourceField.setBounds(523, 75, 179, 25);
		generalPanel.add(sourceField);
		
		agentLabel.setForeground(new Color(142, 39, 148));
		agentLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		agentLabel.setBounds(22, 204, 131, 14);
		generalPanel.add(agentLabel);
		agentLabel.setVisible(true);
		
		agentCombo.setEditable(true);
		agentCombo.setModel(new DefaultComboBoxModel(agents));
		agentCombo.setSelectedItem(app.getAgent());
		agentCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		agentCombo.setBounds(139, 198, 179, 20);
		generalPanel.add(agentCombo);
		
		
		genderLabel.setForeground(new Color(142, 39, 148));
		genderLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		genderLabel.setBounds(20, 235, 133, 14);
		generalPanel.add(genderLabel);
		
		genderCombo.setEditable(true);
		genderCombo.setModel(new DefaultComboBoxModel(new String[]{"M", "F"}));
		genderCombo.setSelectedItem(app.getGender());
		genderCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		genderCombo.setBounds(138, 229, 180, 20);
		generalPanel.add(genderCombo);
		
		statusLabel.setForeground(new Color(142, 39, 148));
		statusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		statusLabel.setBounds(20, 266, 133, 14);
		generalPanel.add(statusLabel);
		
		statusCombo.setEditable(true);
		statusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "New", "Received", "Deposit Received", "Cancelled", "Enrolled"}));
		statusCombo.setSelectedItem(app.getAppStatus());
		statusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statusCombo.setBounds(139, 264, 179, 20);
		generalPanel.add(statusCombo);
		
		payStatusLabel.setForeground(new Color(142, 39, 148));
		payStatusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		payStatusLabel.setBounds(21, 293, 132, 14);
		generalPanel.add(payStatusLabel);
		
		payStatusCombo.setEditable(true);
		payStatusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Not Paid", "Desposit Paid", "Full Fee Paid"}));
		payStatusCombo.setSelectedItem(app.getPaymentStatus());
		payStatusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		payStatusCombo.setBounds(139, 291, 179, 20);
		generalPanel.add(payStatusCombo);
		
		salesAdvisorLabel.setForeground(new Color(142, 39, 148));
		salesAdvisorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		salesAdvisorLabel.setBounds(21, 323, 132, 14);
		generalPanel.add(salesAdvisorLabel);
		
		salesAdvisorCombo.setEditable(true);
		salesAdvisorCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
		salesAdvisorCombo.setSelectedItem(app.getSalesAdvisor());
		salesAdvisorCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		salesAdvisorCombo.setBounds(139, 322, 179, 20);
		generalPanel.add(salesAdvisorCombo);
		
		durationLabel.setForeground(new Color(142, 39, 148));
		durationLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		durationLabel.setBounds(311, 14, 91, 14);
		durationLabel.setText(app.getDuration());
		generalPanel.add(durationLabel);
		
		weeklyPriceLabel.setForeground(new Color(142, 39, 148));
		weeklyPriceLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		weeklyPriceLabel.setBounds(588, 14, 210, 14);
		weeklyPriceLabel.setText(app.getWeeklyPrice());
		generalPanel.add(weeklyPriceLabel);
		
		
		arrivalDateLabel.setForeground(new Color(142, 39, 148));
		arrivalDateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		arrivalDateLabel.setBounds(393, 127, 151, 14);
		generalPanel.add(arrivalDateLabel);
		
		
		arrivalDateChooser.setBounds(523, 116, 179, 25);
		arrivalDateChooser.setDate(date);
		generalPanel.add(arrivalDateChooser);
		
		
		departureDateLabel.setForeground(new Color(142, 39, 148));
		departureDateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		departureDateLabel.setBounds(394, 159, 150, 14);
		generalPanel.add(departureDateLabel);
		
	
		departureDateChooser.setBounds(522, 155, 180, 25);
		departureDateChooser.setDate(date1);
		generalPanel.add(departureDateChooser);
		
		
		dobLabel.setForeground(new Color(142, 39, 148));
		dobLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		dobLabel.setBounds(393, 194, 151, 14);
		generalPanel.add(dobLabel);
		
		
		dobDateChooser.setBounds(522, 187, 180, 25);
		dobDateChooser.setDate(date2);
		generalPanel.add(dobDateChooser);
		
		
		lastContactedLabel.setForeground(new Color(142, 39, 148));
		lastContactedLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lastContactedLabel.setBounds(393, 225, 151, 14);
		generalPanel.add(lastContactedLabel);
		
		
		lastContactedDateChooser.setBounds(522, 219, 180, 25);
		lastContactedDateChooser.setDate(date3);
		generalPanel.add(lastContactedDateChooser);
		
		
		visaStatusLabel.setForeground(new Color(142, 39, 148));
		visaStatusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		visaStatusLabel.setBounds(394, 261, 150, 14);
		generalPanel.add(visaStatusLabel);
		
		
		visaStatusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		visaStatusCombo.setBounds(522, 255, 180, 20);
		visaStatusCombo.setEditable(true);
		visaStatusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "No Visa Required", "Not Applied", "Applied", "Visa Granted", "Visa Refused"}));
		visaStatusCombo.setSelectedItem(app.getVisaStatus());
		generalPanel.add(visaStatusCombo);
		
		
		campusLabel.setForeground(new Color(142, 39, 148));
		campusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		campusLabel.setBounds(394, 290, 150, 14);
		generalPanel.add(campusLabel);
		
		
		campusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		campusCombo.setBounds(522, 286, 180, 20);
		campusCombo.setEditable(true);
		campusCombo.setModel(new DefaultComboBoxModel(user.getCampuses()));
		campusCombo.setSelectedItem(app.getCampus());
		generalPanel.add(campusCombo);
		
		JButton button_10 = new JButton("Back");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ViewApplications ve = new ViewApplications(user);
			}
		});
		button_10.setForeground(Color.WHITE);
		button_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_10.setBackground(new Color(142, 39, 148));
		button_10.setBounds(394, 411, 84, 25);
		generalPanel.add(button_10);
		
		JButton btnUndo = new JButton("Reset");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.resetAppFields(user, app, firstNameField, totalPriceField, passportField, 
						countryCombo, nationalityCombo, agentCombo, genderCombo, statusCombo, payStatusCombo, 
						salesAdvisorCombo, lastNameField, sourceField, visaStatusCombo, campusCombo, 
						arrivalDateChooser, departureDateChooser, dobDateChooser, lastContactedDateChooser);
			}
		});
		btnUndo.setForeground(Color.WHITE);
		btnUndo.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUndo.setBackground(new Color(142, 39, 148));
		btnUndo.setBounds(487, 411, 103, 25);
		generalPanel.add(btnUndo);
		
		JLabel warningLabel = new JLabel("Application could not be updated. Please try again later.");
		warningLabel.setForeground(new Color(255, 0, 0));
		warningLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel.setBounds(20, 411, 389, 25);
		warningLabel.setVisible(false);
		generalPanel.add(warningLabel);
		
		JComboBox courseCombo = new JComboBox();
		courseCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseCombo.setEditable(true);
		courseCombo.setModel(new DefaultComboBoxModel(user.getCourses()));
		courseCombo.setSelectedItem(app.getCourse());
		courseCombo.setBounds(522, 315, 180, 20);
		generalPanel.add(courseCombo);
		
		JLabel courseLabel = new JLabel("Campus: ");
		courseLabel.setForeground(new Color(142, 39, 148));
		courseLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		courseLabel.setBounds(394, 319, 150, 14);
		generalPanel.add(courseLabel);
		
		
		JButton btnConfirm = new JButton("Confirm ");
		
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConfirm.setBackground(new Color(142, 39, 148));
		btnConfirm.setBounds(600, 411, 103, 25);
		generalPanel.add(btnConfirm);
		
		JLabel uploadLabel = new JLabel("Documents Attached. Select Confirm to Upload and Replace Existing Documents.");
		uploadLabel.setForeground(new Color(0, 128, 0));
		uploadLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		uploadLabel.setBounds(20, 377, 511, 23);
		uploadLabel.setVisible(false);
		generalPanel.add(uploadLabel);
		
		
		Controller.autoCalculateAppWeeklyPrice2(totalPriceField, arrivalDateChooser, 
				departureDateChooser, 
				weeklyPriceLabel, durationLabel);
		
		JLabel lblDurationdays = new JLabel("Duration (days) : ");
		lblDurationdays.setForeground(new Color(142, 39, 148));
		lblDurationdays.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDurationdays.setBounds(190, 14, 125, 14);
		generalPanel.add(lblDurationdays);
		
		JLabel lblWeeklyprice = new JLabel("Weekly Price :   \u00A3 ");
		lblWeeklyprice.setForeground(new Color(142, 39, 148));
		lblWeeklyprice.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblWeeklyprice.setBounds(455, 14, 150, 14);
		generalPanel.add(lblWeeklyprice);
		
		JButton uploadButton = new JButton("Upload New Documents");
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
						String directory = System.getProperty("user.home");
						fileChooser = new JFileChooser(directory +"/Desktop");
						fileChooser.showOpenDialog(null);
						
						if(fileChooser.getSelectedFile()!=null){
							user.setFile(fileChooser.getSelectedFile());
							user.setCommand2(app.getUid()+"");
							String str = user.getFile().getName();
							int index = str.indexOf(".");
							user.setCommand3(str.substring(index));
							try {
								Controller.uploadFile(user);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							uploadLabel.setVisible(true);
							
						}
					}
				});
		
		uploadButton.setForeground(Color.WHITE);
		uploadButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		uploadButton.setBackground(new Color(142, 39, 148));
		uploadButton.setBounds(524, 376, 178, 25);
		generalPanel.add(uploadButton);
		
		JLabel docsLabel = new JLabel("Documents:");
		docsLabel.setForeground(new Color(142, 39, 148));
		docsLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		docsLabel.setBounds(394, 355, 174, 14);
		generalPanel.add(docsLabel);
		
		JButton btnDownloadDocuments = new JButton("Download Documents");
		btnDownloadDocuments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String directory = System.getProperty("user.home");
				fileChooser = new JFileChooser(directory +"/Desktop");
				String serverLocationPath = "C:/Users/Ian/Desktop/Server/";
				
			//	fileChooser.setSelectedFile(new File("C:/Users/Ian/Desktop/Server/"+app.getUid()+".pdf"));
				int userSelection = fileChooser.showSaveDialog(null);
				System.out.println("Done");
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					System.out.println("Triggered");
				    File fileToSave = fileChooser.getSelectedFile();
				    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				    user.setCommand2(fileToSave.getAbsolutePath()+".pdf");
				    user.setCommand3(serverLocationPath + app.getUid() + ".pdf");
				    try {
						Controller.saveFile(user, uploadLabel);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnDownloadDocuments.setForeground(Color.WHITE);
		btnDownloadDocuments.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDownloadDocuments.setBackground(new Color(142, 39, 148));
		btnDownloadDocuments.setBounds(523, 346, 179, 25);
		generalPanel.add(btnDownloadDocuments);

		contactPanel.setForeground(new Color(142, 39, 148));
		contactPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Contact", null, contactPanel, null);
		contactPanel.setLayout(null);
		
		JLabel phoneLabel = new JLabel("Phone:");
		phoneLabel.setForeground(new Color(142, 39, 148));
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		phoneLabel.setBounds(47, 71, 131, 15);
		contactPanel.add(phoneLabel);
		
		JLabel emailLabel = new JLabel("Email: ");
		emailLabel.setForeground(new Color(142, 39, 148));
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		emailLabel.setBounds(47, 112, 131, 14);
		contactPanel.add(emailLabel);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setForeground(new Color(142, 39, 148));
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAddress.setBounds(47, 165, 131, 14);
		contactPanel.add(lblAddress);
		
		
		
		JLabel label_1 = new JLabel("Application: " + app.getUid());
		label_1.setForeground(new Color(142, 39, 148));
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(47, 21, 254, 17);
		contactPanel.add(label_1);
		
		phoneField = new JTextField();
		phoneField.setForeground(new Color(142, 39, 148));
		phoneField.setText(app.getPhone());
		phoneField.setBounds(113, 70, 228, 30);
		contactPanel.add(phoneField);
		phoneField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setForeground(new Color(142, 39, 148));
		emailField.setText(app.getEmail());
		emailField.setColumns(10);
		emailField.setBounds(113, 105, 228, 30);
		contactPanel.add(emailField);
		
		JTextArea adressBox = new JTextArea();
		adressBox.setForeground(new Color(142, 39, 148));
		adressBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		System.out.println(app.getAddress());
		adressBox.setText(app.getAddress());
		adressBox.setWrapStyleWord(true);
		adressBox.setLineWrap(true);
		adressBox.setBounds(112, 162, 569, 30);
		contactPanel.add(adressBox);
		
		notesPanel.setForeground(new Color(142, 39, 148));
		notesPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Notes", null, notesPanel, null);
		notesPanel.setLayout(null);
		
		JLabel notesLabel = new JLabel("Notes:");
		notesLabel.setForeground(new Color(142, 39, 148));
		notesLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		notesLabel.setBounds(30, 11, 254, 17);
		notesPanel.add(notesLabel);
		
		JLabel lblPreviousNotes = new JLabel("Previous Notes:");
		lblPreviousNotes.setForeground(new Color(142, 39, 148));
		lblPreviousNotes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPreviousNotes.setBounds(30, 157, 131, 15);
		notesPanel.add(lblPreviousNotes);
		
		JButton olderNotes = new JButton(">");
		olderNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(r<app.getApplicationNotes().size()){
				
				l++;
				m++;
				r++;
				
				
				Controller.updateNotesRight(l,r,m, app.getApplicationNotes(), note1Area,note2Area, note3Area, noteAuthor, noteTime, noteDate, 
				noteAuthor2, noteTime2, noteDate2, noteAuthor3, noteTime3, noteDate3);
			}
			}
		});
		olderNotes.setForeground(new Color(142, 39, 148));
		olderNotes.setFont(new Font("Tahoma", Font.BOLD, 14));
		olderNotes.setBackground(Color.WHITE);
		olderNotes.setBounds(652, 278, 43, 84);
		notesPanel.add(olderNotes);
		
		
		RoundedPanel roundedPanel = new RoundedPanel();
		roundedPanel.setLayout(null);
		roundedPanel.setForeground(Color.BLACK);
		roundedPanel.setBackground(new Color(142, 39, 148));
		roundedPanel.setBounds(83, 187, 172, 249);
		notesPanel.add(roundedPanel);
		noteDate.setBounds(60, 177, 209, 26);
		roundedPanel.add(noteDate);
		noteDate.setForeground(Color.WHITE);
		noteDate.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteTime.setBounds(60, 202, 182, 26);
		roundedPanel.add(noteTime);
		noteTime.setForeground(Color.WHITE);
		noteTime.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteAuthor.setBounds(60, 152, 213, 26);
		roundedPanel.add(noteAuthor);
		noteAuthor.setForeground(Color.WHITE);
		noteAuthor.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		note1Area = new JTextArea();
		note1Area.setForeground(Color.BLACK);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		note1Area.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		note1Area.setFont(new Font("Tahoma", Font.PLAIN, 10));
		note1Area.setWrapStyleWord(true);
		note1Area.setLineWrap(true);
		note1Area.setBounds(10, 11, 147, 130);
		roundedPanel.add(note1Area);
		note1Area.setColumns(10);
		
		RoundedPanel roundedPanel_2 = new RoundedPanel();
		roundedPanel_2.setLayout(null);
		roundedPanel_2.setForeground(Color.BLACK);
		roundedPanel_2.setBackground(new Color(142, 39, 148));
		roundedPanel_2.setBounds(275, 187, 172, 249);
		notesPanel.add(roundedPanel_2);
		noteAuthor2.setBounds(61, 152, 149, 26);
		roundedPanel_2.add(noteAuthor2);
		noteAuthor2.setForeground(Color.WHITE);
		noteAuthor2.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteDate2.setBounds(61, 177, 149, 26);
		roundedPanel_2.add(noteDate2);
		noteDate2.setForeground(Color.WHITE);
		noteDate2.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteTime2.setBounds(61, 202, 149, 26);
		roundedPanel_2.add(noteTime2);
		noteTime2.setForeground(Color.WHITE);
		noteTime2.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		note2Area = new JTextArea();
		note2Area.setForeground(Color.BLACK);
		note2Area.setBorder(BorderFactory.createCompoundBorder(border, 
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		note2Area.setLineWrap(true);
		note2Area.setWrapStyleWord(true);
		note2Area.setFont(new Font("Tahoma", Font.PLAIN, 10));
		note2Area.setBounds(10, 11, 147, 130);
		roundedPanel_2.add(note2Area);
		note2Area.setColumns(10);
		
		RoundedPanel roundedPanel_3 = new RoundedPanel();
		roundedPanel_3.setLayout(null);
		roundedPanel_3.setForeground(Color.BLACK);
		roundedPanel_3.setBackground(new Color(142, 39, 148));
		roundedPanel_3.setBounds(470, 187, 172, 249);
		notesPanel.add(roundedPanel_3);
		
		note3Area = new JTextArea();
		note3Area.setForeground(Color.BLACK);
		note3Area.setBorder(BorderFactory.createCompoundBorder(border, 
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		note3Area.setFont(new Font("Tahoma", Font.PLAIN, 10));
		note3Area.setWrapStyleWord(true);
		note3Area.setLineWrap(true);
		note3Area.setBounds(10, 11, 147, 130);
		roundedPanel_3.add(note3Area);
		note3Area.setColumns(10);
		noteTime3.setBounds(60, 202, 131, 26);
		roundedPanel_3.add(noteTime3);
		noteTime3.setForeground(Color.WHITE);
		noteTime3.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteDate3.setBounds(60, 177, 131, 26);
		roundedPanel_3.add(noteDate3);
		noteDate3.setForeground(Color.WHITE);
		noteDate3.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		
		
		
		label_10.setForeground(Color.WHITE);
		label_10.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_10.setBounds(10, 202, 131, 26);
		
		roundedPanel.add(label_10);
		label_11.setForeground(Color.WHITE);
		label_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_11.setBounds(10, 177, 131, 26);
		
		roundedPanel.add(label_11);
		label_12.setForeground(Color.WHITE);
		label_12.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_12.setBounds(10, 152, 131, 26);
		
		roundedPanel.add(label_12);
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_7.setBounds(10, 202, 131, 26);
		
		roundedPanel_2.add(label_7);
		label_8.setForeground(Color.WHITE);
		label_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_8.setBounds(10, 177, 131, 26);
		
		roundedPanel_2.add(label_8);
		label_9.setForeground(Color.WHITE);
		label_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_9.setBounds(10, 152, 131, 26);
		
		roundedPanel_2.add(label_9);
		noteAuthor3.setBounds(60, 152, 131, 26);
		roundedPanel_3.add(noteAuthor3);
		noteAuthor3.setForeground(Color.WHITE);
		noteAuthor3.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		JLabel label_6 = new JLabel("Time:");
		label_6.setBounds(10, 202, 131, 26);
		roundedPanel_3.add(label_6);
		label_6.setForeground(Color.WHITE);
		label_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_5 = new JLabel("Date:");
		label_5.setBounds(10, 177, 131, 26);
		roundedPanel_3.add(label_5);
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_4 = new JLabel("Author:");
		label_4.setBounds(10, 152, 131, 26);
		roundedPanel_3.add(label_4);
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JButton newerNotes = new JButton("<");
		newerNotes.setBounds(30, 278, 43, 84);
		notesPanel.add(newerNotes);
		newerNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(l >0){
				
				l--;
				m--;
				r--;
				

				Controller.updateNotesLeft(l,r,m, app.getApplicationNotes(), note1Area,note2Area, note3Area, noteAuthor, noteTime, noteDate, 
				noteAuthor2, noteTime2, noteDate2, noteAuthor3, noteTime3, noteDate3);
				}
			}
			
		});
		newerNotes.setForeground(new Color(142, 39, 148));
		newerNotes.setFont(new Font("Tahoma", Font.BOLD, 14));
		newerNotes.setBackground(Color.WHITE);
		
		RoundedPanel roundedPanel_4 = new RoundedPanel();
		roundedPanel_4.setLayout(null);
		roundedPanel_4.setForeground(Color.BLACK);
		roundedPanel_4.setBackground(new Color(142, 39, 148));
		roundedPanel_4.setBounds(56, 39, 611, 99);
		notesPanel.add(roundedPanel_4);
		
		JLabel lblAddNewNote = new JLabel("Add New Note:");
		lblAddNewNote.setBounds(10, 11, 131, 15);
		roundedPanel_4.add(lblAddNewNote);
		lblAddNewNote.setForeground(Color.WHITE);
		lblAddNewNote.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel warningLabel5 = new JLabel("Please Enter A Note ");
		warningLabel5.setForeground(Color.RED);
		warningLabel5.setVisible(false);
		warningLabel5.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel5.setBounds(538, 145, 157, 27);
		notesPanel.add(warningLabel5);
		warningLabel5.setVisible(false);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(527, 34, 57, 25);
		roundedPanel_4.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(addNoteArea.getText().equals("")){
					warningLabel5.setVisible(true);
				}
				
				else{
					warningLabel5.setVisible(false);
					
					Controller.addAppNote(user, addNoteArea, app, note1Area, note2Area, note3Area, noteAuthor,
							noteDate, noteTime,noteAuthor2,
							noteDate2, noteTime2,noteAuthor3,
							noteDate3, noteTime3);
				}
			}
		});
		btnAdd.setForeground(new Color(142, 39, 148));
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.setBackground(Color.WHITE);
		
		addNoteArea = new JTextArea();
		addNoteArea.setBorder(BorderFactory.createCompoundBorder(border, 
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		addNoteArea.setForeground(Color.BLACK);
		addNoteArea.setFont(new Font("Tahoma", Font.PLAIN, 10));
		addNoteArea.setWrapStyleWord(true);
		addNoteArea.setLineWrap(true);
		addNoteArea.setBounds(115, 16, 395, 65);
		roundedPanel_4.add(addNoteArea);
		addNoteArea.setColumns(10);
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setLayout(null);
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(9, 104, 245, 474);
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
					AddGroupApplicationStaff a = new AddGroupApplicationStaff(
							user);
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

				EnrolledStudentsViewStaff esv = new EnrolledStudentsViewStaff(
						user);
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
		
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Controller.updateApp (user, app, firstNameField, warningLabel, totalPriceField,
						 passportField,  lastNameField, countryCombo, nationalityCombo,  agentCombo,
						 genderCombo,  statusCombo,  payStatusCombo,  salesAdvisorCombo,  sourceField,
						arrivalDateChooser,  departureDateChooser,  dobDateChooser,  lastContactedDateChooser, 
						 visaStatusCombo,  campusCombo,  weeklyPriceLabel,  emailField,  phoneField,
						 courseCombo,  durationLabel,  adressBox);
			}
		});
		
		System.out.println(app.getApplicationNotes().get(0).getNote());
		
		Controller.generateNotes(app.getApplicationNotes(), note1Area,note2Area, note3Area, noteAuthor, noteTime, noteDate, 
				noteAuthor2, noteTime2, noteDate2, noteAuthor3, noteTime3, noteDate3);
		
		this.setVisible(true);
	}
}