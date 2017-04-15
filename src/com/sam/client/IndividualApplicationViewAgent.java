/**
 * This class represents the Individual Application view for agents. 
 * 
 * The various swing components for the view along with their
 * characteristics and attributes. Relevant listeners are added
 * to buttons and their action performed methods defined. 
 * 
 * References:
 * http://www.java2s.com/Tutorial/Java/0240__Swing/AddyourownListCellRenderer.htm
 * http://stackoverflow.com/questions/4827635/better-readability-contrast-in-a-disabled-jcombobox
 * http://stackoverflow.com/questions/10274750/java-swing-setting-margins-on-textarea-with-line-border
 * http://stackoverflow.com/questions/10037644/opening-a-url-from-a-jbutton-in-simple-java-program
 * 
 * @author Ian Shields, 1506416
 * @version 07/09/2015
 */



package com.sam.client;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;


public class IndividualApplicationViewAgent extends JFrame {

	// field variables
	private static final long serialVersionUID = 1L;
	
	private final JLabel lblNewLabel = new JLabel("New label");

	
	
	private JTextField firstNameField= new JTextField();
	private JTextField totalPriceField= new JTextField();
	private JTextField passportField= new JTextField();
	private JTextField lastNameField= new JTextField();
	JLabel logoLabel = new JLabel("");
	JLabel label_2 = new JLabel();
	JButton btnBack = new JButton("Back");
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	RoundedPanel generalPanel = new RoundedPanel();
	RoundedPanel contactPanel = new RoundedPanel();
	JLabel totalPriceLabel = new JLabel("Total Price: ");
	JLabel countryLabel = new JLabel("Country: ");
	JComboBox countryCombo = new JComboBox();
	JLabel nationalityLabel = new JLabel("Nationality:");
	JComboBox nationalityCombo = new JComboBox();
	JLabel lblApplication = new JLabel("Application: " );
	JLabel firstNameLabel = new JLabel("First Name:");
	JLabel passportLabel = new JLabel("Passport No:");
	JLabel lastNameLabel = new JLabel("Last Name: ");
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
	private int l = 0;
	private int m = 1;
	private int r = 2;
	private JFileChooser fileChooser;
	private JTextField phoneField;
	private JTextField emailField;

	
	// constructor
	public IndividualApplicationViewAgent(User user, Application app) throws ParseException {
	
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
		firstNameField.setEnabled(false);
		firstNameField.setEditable(false);
		firstNameField.setDisabledTextColor(Color.BLACK);
		
		firstNameField.setText(app.getFirstName());
		firstNameField.setForeground(new Color(142, 39, 148));
		firstNameField.setColumns(10);
		firstNameField.setBounds(140, 39, 178, 25);
		generalPanel.add(firstNameField);
		
		
		totalPriceLabel.setForeground(new Color(142, 39, 148));
		totalPriceLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		totalPriceLabel.setBounds(22, 78, 131, 14);
		generalPanel.add(totalPriceLabel);
		totalPriceField.setEnabled(false);
		totalPriceField.setDisabledTextColor(Color.BLACK);
		
		totalPriceField.setText(app.getTotalPrice());
		totalPriceField.setForeground(new Color(142, 39, 148));
		totalPriceField.setColumns(10);
		totalPriceField.setBounds(140, 70, 178, 25);
		totalPriceField.setEditable(false);
		generalPanel.add(totalPriceField);
		
		
		passportLabel.setForeground(new Color(142, 39, 148));
		passportLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		passportLabel.setBounds(22, 107, 131, 14);
		generalPanel.add(passportLabel);
		passportField.setEnabled(false);
		
		passportField.setText(app.getPassportNo());
		passportField.setForeground(new Color(142, 39, 148));
		passportField.setColumns(10);
		passportField.setBounds(140, 103, 178, 25);
		passportField.setEditable(false);
		passportField.setDisabledTextColor(Color.BLACK);
		generalPanel.add(passportField);
		
		
		countryLabel.setForeground(new Color(142, 39, 148));
		countryLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		countryLabel.setBounds(22, 138, 131, 18);
		generalPanel.add(countryLabel);
		countryCombo.setEnabled(false);
		
		countryCombo.setEditable(false);
		countryCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		countryCombo.setSelectedItem(app.getCountry());
		countryCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		countryCombo.setBounds(139, 136, 179, 20);
		countryCombo.setEditable(false);
		
		   countryCombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            super.paint(g);
		        }
		    });
		
		generalPanel.add(countryCombo);
		
		
		nationalityLabel.setForeground(new Color(142, 39, 148));
		nationalityLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		nationalityLabel.setBounds(22, 173, 131, 14);
		generalPanel.add(nationalityLabel);
		nationalityCombo.setEnabled(false);
		
		nationalityCombo.setEditable(false);
		nationalityCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		nationalityCombo.setSelectedItem(app.getNationality());
		nationalityCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nationalityCombo.setBounds(139, 167, 179, 20);
		nationalityCombo.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public void paint(Graphics g) {
	            setForeground(Color.BLACK);
	            super.paint(g);
	        }
	    });
		generalPanel.add(nationalityCombo);
		
		
		lastNameLabel.setForeground(new Color(142, 39, 148));
		lastNameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lastNameLabel.setBounds(394, 49, 150, 14);
		generalPanel.add(lastNameLabel);
		lastNameField.setEditable(false);
		lastNameField.setDisabledTextColor(Color.BLACK);
		lastNameField.setEnabled(false);
		
		lastNameField.setText(app.getLastName());
		lastNameField.setForeground(new Color(142, 39, 148));
		lastNameField.setColumns(10);
		lastNameField.setBounds(523, 44, 179, 25);
		generalPanel.add(lastNameField);
		
		agentLabel.setForeground(new Color(142, 39, 148));
		agentLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		agentLabel.setBounds(22, 204, 131, 14);
		generalPanel.add(agentLabel);
		agentLabel.setVisible(true);
		agentCombo.setModel(new DefaultComboBoxModel(new String[] {user.getUsername()}));
		agentCombo.setSelectedItem(app.getAgent());
		agentCombo.setEnabled(false);
		
		agentCombo.setEditable(false);
		
		agentCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		agentCombo.setBounds(139, 198, 179, 20);
		
		  agentCombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            super.paint(g);
		        }
		    });
		
		generalPanel.add(agentCombo);
		
		
		
		
		genderLabel.setForeground(new Color(142, 39, 148));
		genderLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		genderLabel.setBounds(20, 235, 133, 14);
		generalPanel.add(genderLabel);
		genderCombo.setEnabled(false);
		
		genderCombo.setEditable(false);
		genderCombo.setModel(new DefaultComboBoxModel(new String[]{"M", "F"}));
		genderCombo.setSelectedItem(app.getGender());
		genderCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		genderCombo.setBounds(138, 229, 180, 20);
		
		  genderCombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            super.paint(g);
		        }
		    });
		
		generalPanel.add(genderCombo);
		
		statusLabel.setForeground(new Color(142, 39, 148));
		statusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		statusLabel.setBounds(20, 266, 133, 14);
		generalPanel.add(statusLabel);
		statusCombo.setEnabled(false);
		
		statusCombo.setEditable(false);
		statusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "New", "Received", "Deposit Received", "Cancelled", "Enrolled"}));
		statusCombo.setSelectedItem(app.getAppStatus());
		statusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statusCombo.setBounds(139, 264, 179, 20);
		
		statusCombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            super.paint(g);
		        }
		    });
		
		generalPanel.add(statusCombo);
		
		payStatusLabel.setForeground(new Color(142, 39, 148));
		payStatusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		payStatusLabel.setBounds(21, 293, 132, 14);
		generalPanel.add(payStatusLabel);
		payStatusCombo.setEnabled(false);
		
		payStatusCombo.setEditable(false);
		payStatusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Not Paid", "Desposit Paid", "Full Fee Paid"}));
		payStatusCombo.setSelectedItem(app.getPaymentStatus());
		payStatusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		payStatusCombo.setBounds(139, 291, 179, 20);
		
		  payStatusCombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            super.paint(g);
		        }
		    });
		
		generalPanel.add(payStatusCombo);
		
		salesAdvisorLabel.setForeground(new Color(142, 39, 148));
		salesAdvisorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		salesAdvisorLabel.setBounds(21, 323, 132, 14);
		generalPanel.add(salesAdvisorLabel);
		salesAdvisorCombo.setEnabled(false);
		
		salesAdvisorCombo.setEditable(false);
		salesAdvisorCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
		salesAdvisorCombo.setSelectedItem(app.getSalesAdvisor());
		salesAdvisorCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		salesAdvisorCombo.setBounds(139, 322, 179, 20);
		
		salesAdvisorCombo.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public void paint(Graphics g) {
	            setForeground(Color.BLACK);
	            super.paint(g);
	        }
	    });
		
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
		arrivalDateLabel.setBounds(393, 92, 151, 14);
		generalPanel.add(arrivalDateLabel);
		arrivalDateChooser.getCalendarButton().setEnabled(false);
		
		
		arrivalDateChooser.setBounds(523, 81, 179, 25);
		arrivalDateChooser.setDate(date);
		arrivalDateChooser.setEnabled(true);
		
	arrivalDateChooser.setSelectableDateRange(arrivalDateChooser.getDate(), arrivalDateChooser.getDate());
		
		generalPanel.add(arrivalDateChooser);
		
		
		departureDateLabel.setForeground(new Color(142, 39, 148));
		departureDateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		departureDateLabel.setBounds(394, 124, 150, 14);
		departureDateChooser.setSelectableDateRange(departureDateChooser.getDate(), departureDateChooser.getDate());
		generalPanel.add(departureDateLabel);
		
	
		departureDateChooser.setBounds(522, 120, 180, 25);
		departureDateChooser.setDate(date1);
		departureDateChooser.setEnabled(true);
		generalPanel.add(departureDateChooser);
		
		
		dobLabel.setForeground(new Color(142, 39, 148));
		dobLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		dobLabel.setBounds(393, 159, 151, 14);
		generalPanel.add(dobLabel);
		
		
		dobDateChooser.setBounds(522, 152, 180, 25);
		dobDateChooser.setDate(date2);
		dobDateChooser.setSelectableDateRange(dobDateChooser.getDate(), dobDateChooser.getDate());
		dobDateChooser.setEnabled(true);
		generalPanel.add(dobDateChooser);
		
		
		lastContactedLabel.setForeground(new Color(142, 39, 148));
		lastContactedLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lastContactedLabel.setBounds(393, 190, 151, 14);
		generalPanel.add(lastContactedLabel);
		
		
		lastContactedDateChooser.setBounds(522, 184, 180, 25);
		lastContactedDateChooser.setDate(date3);
		lastContactedDateChooser.setEnabled(true);
		lastContactedDateChooser.setSelectableDateRange(lastContactedDateChooser.getDate(), lastContactedDateChooser.getDate());
		generalPanel.add(lastContactedDateChooser);
		
		
		visaStatusLabel.setForeground(new Color(142, 39, 148));
		visaStatusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		visaStatusLabel.setBounds(394, 226, 150, 14);
		generalPanel.add(visaStatusLabel);
		visaStatusCombo.setEnabled(false);
		
		
		visaStatusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		visaStatusCombo.setBounds(522, 220, 180, 20);
		visaStatusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "No Visa Required", "Not Applied", "Applied", "Visa Granted", "Visa Refused"}));
		visaStatusCombo.setSelectedItem(app.getVisaStatus());
		visaStatusCombo.setEditable(false);
		
		  visaStatusCombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            super.paint(g);
		        }
		    });
		generalPanel.add(visaStatusCombo);
		
		
		campusLabel.setForeground(new Color(142, 39, 148));
		campusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		campusLabel.setBounds(394, 255, 150, 14);
		generalPanel.add(campusLabel);
		campusCombo.setEnabled(false);
		
		
		campusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		campusCombo.setBounds(522, 251, 180, 20);
		campusCombo.setModel(new DefaultComboBoxModel(user.getCampuses()));
		campusCombo.setSelectedItem(app.getCampus());
		campusCombo.setEditable(false);
		
		 campusCombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            super.paint(g);
		        }
		    });
		
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
		
//		JButton btnUndo = new JButton("Reset");
//		btnUndo.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ModelM.resetAppFields(user, app, firstNameField, totalPriceField, passportField, 
//						countryCombo, nationalityCombo, agentCombo, genderCombo, statusCombo, payStatusCombo, 
//						salesAdvisorCombo, lastNameField, sourceField, visaStatusCombo, campusCombo, 
//						arrivalDateChooser, departureDateChooser, dobDateChooser, lastContactedDateChooser);
//			}
//		});
//		btnUndo.setForeground(Color.WHITE);
//		btnUndo.setFont(new Font("Tahoma", Font.BOLD, 12));
//		btnUndo.setBackground(new Color(142, 39, 148));
//		btnUndo.setBounds(487, 411, 103, 25);
//		generalPanel.add(btnUndo);
		
		JLabel warningLabel = new JLabel("Application could not be updated. Please try again later.");
		warningLabel.setForeground(new Color(255, 0, 0));
		warningLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel.setBounds(20, 411, 389, 25);
		warningLabel.setVisible(false);
		generalPanel.add(warningLabel);
		
		JComboBox courseCombo = new JComboBox();
		courseCombo.setEnabled(false);
		courseCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseCombo.setModel(new DefaultComboBoxModel(user.getCourses()));
		courseCombo.setSelectedItem(app.getCourse());
		courseCombo.setBounds(522, 280, 180, 20);
		courseCombo.setEditable(false);
		
		  courseCombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            super.paint(g);
		        }
		    });
		
		generalPanel.add(courseCombo);
		
		JLabel courseLabel = new JLabel("Course: ");
		courseLabel.setForeground(new Color(142, 39, 148));
		courseLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		courseLabel.setBounds(394, 284, 150, 14);
		generalPanel.add(courseLabel);
		
		
		JButton btnConfirm = new JButton("Confirm ");
		
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConfirm.setBackground(new Color(142, 39, 148));
		btnConfirm.setBounds(600, 411, 103, 25);
		btnConfirm.setEnabled(false);
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
		uploadButton.setBounds(524, 341, 178, 25);
		uploadButton.setEnabled(false);
		generalPanel.add(uploadButton);
		
		JLabel docsLabel = new JLabel("Documents:");
		docsLabel.setForeground(new Color(142, 39, 148));
		docsLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		docsLabel.setBounds(394, 320, 174, 14);
		generalPanel.add(docsLabel);
		
		JButton btnDownloadDocuments = new JButton("Download Documents");
		btnDownloadDocuments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String directory = System.getProperty("user.home");
				fileChooser = new JFileChooser(directory +"/Desktop");
				String serverLocationPath = "C:/Users/Ian/Desktop/Server/";
				
			
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
		btnDownloadDocuments.setBounds(523, 311, 179, 25);
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
		emailLabel.setBounds(47, 105, 131, 14);
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
		phoneField.setEnabled(false);
		phoneField.setForeground(new Color(142, 39, 148));
		phoneField.setText(app.getPhone());
		phoneField.setBounds(113, 70, 228, 25);
		contactPanel.add(phoneField);
		phoneField.setColumns(10);
		phoneField.setDisabledTextColor(Color.BLACK);
		phoneField.setEditable(false);
		
		emailField = new JTextField();
		emailField.setEnabled(false);
		emailField.setForeground(new Color(142, 39, 148));
		emailField.setText(app.getEmail());
		emailField.setColumns(10);
		emailField.setBounds(113, 104, 228, 25);
		emailField.setEditable(false);
		emailField.setDisabledTextColor(Color.BLACK);
		contactPanel.add(emailField);
		
		JTextArea adressBox = new JTextArea();
		adressBox.setEnabled(false);
		adressBox.setForeground(new Color(142, 39, 148));
		adressBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		System.out.println(app.getAddress());
		adressBox.setText(app.getAddress());
		adressBox.setWrapStyleWord(true);
		adressBox.setLineWrap(true);
		adressBox.setBounds(112, 162, 229, 244);
		adressBox.setEditable(false);
		adressBox.setDisabledTextColor(Color.BLACK);
		contactPanel.add(adressBox);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setLayout(null);
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(10, 104, 245, 474);
		getContentPane().add(roundedPanel_1);
		
		JLabel label = new JLabel("Dashboard");
		label.setForeground(new Color(142, 39, 148));
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		label.setBounds(75, 11, 98, 17);
		roundedPanel_1.add(label);
		
		JButton button_2 = new JButton("Enrolled Students");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnrolledStudentsViewAgents esv = new EnrolledStudentsViewAgents(user);
				dispose();
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
				dispose();
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
					dispose();
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
				dispose();
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
				dispose();
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
		
		
		this.setVisible(true);
	}
}