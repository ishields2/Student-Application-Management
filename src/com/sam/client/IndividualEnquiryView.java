/**
 * This class represents the Individual Enquiry view for staff members. 
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
import java.net.Socket;
import java.net.UnknownHostException;
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
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class IndividualEnquiryView extends JFrame {

	// field variables
	private static final long serialVersionUID = 1L;
	
	private final JLabel lblNewLabel = new JLabel("New label");

	private JTextField textField_3 = new JTextField();
	
	private JTextField firstNameField= new JTextField();
	private JTextField totalPriceField= new JTextField();
	private JTextField passportField= new JTextField();
	private JTextField lastNameField= new JTextField();
	private JTextField sourceField= new JTextField();
	JLabel logoLabel = new JLabel("");
	JLabel label_2 = new JLabel();
	JButton btnBack_1 = new JButton("Back");
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	RoundedPanel generalPanel = new RoundedPanel();
	RoundedPanel notesPanel = new RoundedPanel();
	JLabel totalPriceLabel = new JLabel("Total Price: ");
	JLabel countryLabel = new JLabel("Country: ");
	JComboBox countryCombo = new JComboBox();
	JLabel nationalityLabel = new JLabel("Nationality:");
	JLabel lblEnquiry = new JLabel("Enquiry: " );
	JLabel firstNameLabel = new JLabel("First Name:");
	JLabel lastNameLabel = new JLabel("Last Name: ");
	JLabel sourceLabel = new JLabel("Source: ");
	JLabel lastContactedLabel = new JLabel("Last Contacted: ");
	JDateChooser lastContactedDateChooser = new JDateChooser();
	JLabel campusLabel = new JLabel("Campus: ");
	JComboBox campusCombo = new JComboBox();
	JLabel agentLabel = new JLabel("Agent: ");
	JComboBox agentCombo = new JComboBox();
	JLabel genderLabel = new JLabel("Gender: ");
	JLabel statusLabel = new JLabel("Status:");
	JComboBox statusCombo = new JComboBox();
	JLabel salesAdvisorLabel = new JLabel("Sales Advisor: ");
	JComboBox salesAdvisorCombo = new JComboBox();
	JLabel weeklyPriceLabel = new JLabel("Weekly Price:");
	private JTextField noWeeksField;
	private final JButton btnUndoChanges = new JButton("Undo ");
	private JTextField phoneField;
	private JTextField emailField;
	private final JLabel warningLabel = new JLabel("Enquiry could not be updated. Please try again. ");
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
	private JTextArea addNoteArea;

	
	// constructor
	public IndividualEnquiryView(User user, Enquiry enq) throws ParseException {
	
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
		
		logoLabel.setBounds(10, 0, 240, 93);
		this.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// need to set this to NOT CONNECTED when the client/server connection ends
		this.setTitle("Status: CONNECTED");
		
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
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		btnBack_1.setForeground(Color.WHITE);
		btnBack_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack_1.setBackground(new Color(142, 39, 148));
		btnBack_1.setBounds(775, 47, 234, 41);
		this.getContentPane().add(btnBack_1);
		
		tabbedPane.setBounds(284, 95, 730, 475);
		this.getContentPane().add(tabbedPane);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setForeground(new Color(142, 39, 148));

		generalPanel.setForeground(new Color(142, 39, 148));
		generalPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("General", null, generalPanel, null);
		generalPanel.setLayout(null);
		
		notesPanel.setForeground(new Color(142, 39, 148));
		notesPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Notes", null, notesPanel, null);
		notesPanel.setLayout(null);
		
		JLabel notesLabel = new JLabel("Notes:");
		notesLabel.setForeground(new Color(142, 39, 148));
		notesLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		notesLabel.setBounds(44, 11, 254, 17);
		notesPanel.add(notesLabel);
		
		JLabel lblPreviousNotes = new JLabel("Previous Notes:");
		lblPreviousNotes.setForeground(new Color(142, 39, 148));
		lblPreviousNotes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPreviousNotes.setBounds(44, 161, 131, 15);
		notesPanel.add(lblPreviousNotes);
		
		
		
		JButton olderNotes = new JButton(">");
		olderNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(r<enq.getEnquiryNotes().size()){
				
				l++;
				m++;
				r++;
				
				
				Controller.updateNotesRight(l,r,m, enq.getEnquiryNotes(), note1Area,note2Area, note3Area, noteAuthor, noteTime, noteDate, 
				noteAuthor2, noteTime2, noteDate2, noteAuthor3, noteTime3, noteDate3);
			}
			}
		});
		olderNotes.setForeground(new Color(142, 39, 148));
		olderNotes.setFont(new Font("Tahoma", Font.BOLD, 14));
		olderNotes.setBackground(Color.WHITE);
		olderNotes.setBounds(639, 274, 43, 84);
		notesPanel.add(olderNotes);

		lblEnquiry.setText("Enquiry: " + enq.getEid());
		lblEnquiry.setForeground(new Color(142, 39, 148));
		lblEnquiry.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnquiry.setBounds(54, 27, 254, 17);
		generalPanel.add(lblEnquiry);

		firstNameLabel.setForeground(new Color(142, 39, 148));
		firstNameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		firstNameLabel.setBounds(54, 89, 131, 15);
		generalPanel.add(firstNameLabel);
		
		firstNameField.setText(enq.getfName());
		firstNameField.setForeground(new Color(142, 39, 148));
		firstNameField.setColumns(10);
		firstNameField.setBounds(139, 84, 214, 25);
		generalPanel.add(firstNameField);

		totalPriceLabel.setForeground(new Color(142, 39, 148));
		totalPriceLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		totalPriceLabel.setBounds(54, 132, 131, 14);
		generalPanel.add(totalPriceLabel);
		
		totalPriceField.setText(enq.getTotalPrice());
		totalPriceField.setForeground(new Color(142, 39, 148));
		totalPriceField.setColumns(10);
		totalPriceField.setBounds(139, 124, 214, 25);
		generalPanel.add(totalPriceField);

		countryLabel.setForeground(new Color(142, 39, 148));
		countryLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		countryLabel.setBounds(54, 167, 131, 18);
		generalPanel.add(countryLabel);
		
		countryCombo.setEditable(true);
		countryCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		countryCombo.setSelectedItem(enq.getCountry());
		countryCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		countryCombo.setBounds(138, 165, 215, 20);
		countryCombo.setEditable(true);
		generalPanel.add(countryCombo);

		lastNameLabel.setForeground(new Color(142, 39, 148));
		lastNameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lastNameLabel.setBounds(377, 89, 150, 14);
		generalPanel.add(lastNameLabel);
		
		lastNameField.setText(enq.getlName());
		lastNameField.setForeground(new Color(142, 39, 148));
		lastNameField.setColumns(10);
		lastNameField.setBounds(487, 84, 216, 25);
		generalPanel.add(lastNameField);

		sourceLabel.setForeground(new Color(142, 39, 148));
		sourceLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		sourceLabel.setBounds(377, 132, 150, 14);
		generalPanel.add(sourceLabel);
		
		sourceField.setText(enq.getSource());
		sourceField.setForeground(new Color(142, 39, 148));
		sourceField.setColumns(10);
		sourceField.setBounds(487, 124, 216, 25);
		generalPanel.add(sourceField);
		
		agentLabel.setForeground(new Color(142, 39, 148));
		agentLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		agentLabel.setBounds(54, 208, 131, 14);
		generalPanel.add(agentLabel);
		agentLabel.setVisible(true);
		
		agentCombo.setEditable(true);
		String[]agents = new String[user.getAgentsList().size()];
		for(int i = 0; i < user.getAgentsList().size(); i++){
			agents[i] = user.getAgentsList().get(i).getCompanyName();
		}
		agentCombo.setModel(new DefaultComboBoxModel(agents));
		agentCombo.setSelectedItem(enq.getAgent());
		agentCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		agentCombo.setBounds(138, 202, 215, 20);
		generalPanel.add(agentCombo);

		statusLabel.setForeground(new Color(142, 39, 148));
		statusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		statusLabel.setBounds(54, 241, 133, 14);
		generalPanel.add(statusLabel);
		
		statusCombo.setEditable(true);
		statusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "A", "B", "C", "D", "E"}));
		statusCombo.setSelectedItem(enq.getStatus());
		statusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statusCombo.setBounds(139, 239, 214, 20);
		generalPanel.add(statusCombo);

		salesAdvisorLabel.setForeground(new Color(142, 39, 148));
		salesAdvisorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		salesAdvisorLabel.setBounds(377, 241, 132, 14);
		generalPanel.add(salesAdvisorLabel);
		
		salesAdvisorCombo.setEditable(true);
		salesAdvisorCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
		salesAdvisorCombo.setSelectedItem(enq.getStaffMember());
		salesAdvisorCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		salesAdvisorCombo.setBounds(487, 239, 216, 20);
		generalPanel.add(salesAdvisorCombo);

		weeklyPriceLabel.setForeground(new Color(142, 39, 148));
		weeklyPriceLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		weeklyPriceLabel.setBounds(377, 354, 132, 14);
		weeklyPriceLabel.setText("Weekly Price:     \u00A3");
		generalPanel.add(weeklyPriceLabel);
		
		
		
		
		
		lastContactedLabel.setForeground(new Color(142, 39, 148));
		lastContactedLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lastContactedLabel.setBounds(377, 166, 151, 14);
		generalPanel.add(lastContactedLabel);
		
		lastContactedDateChooser.setBounds(487, 160, 216, 25);
		Date date3 = new SimpleDateFormat("dd/MM/yy").parse(enq.getLastContacted());
		lastContactedDateChooser.setDate(date3);
		generalPanel.add(lastContactedDateChooser);
		
		campusLabel.setForeground(new Color(142, 39, 148));
		campusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		campusLabel.setBounds(377, 206, 150, 14);
		generalPanel.add(campusLabel);
		
		
		campusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		campusCombo.setBounds(487, 202, 216, 20);
		campusCombo.setEditable(true);
		campusCombo.setModel(new DefaultComboBoxModel(user.getCampuses()));
		campusCombo.setSelectedItem(enq.getCampus());
		generalPanel.add(campusCombo);
		
		JLabel noWeeksLabel = new JLabel("No. Weeks");
		noWeeksLabel.setForeground(new Color(142, 39, 148));
		noWeeksLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		noWeeksLabel.setBounds(54, 281, 150, 14);
		generalPanel.add(noWeeksLabel);
		
		noWeeksField = new JTextField();
		noWeeksField.setText(enq.getNoWeeks());
		noWeeksField.setForeground(new Color(142, 39, 148));
		noWeeksField.setColumns(10);
		noWeeksField.setBounds(137, 278, 216, 25);
		generalPanel.add(noWeeksField);
		
		JLabel courseLabel = new JLabel("Course:");
		courseLabel.setForeground(new Color(142, 39, 148));
		courseLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		courseLabel.setBounds(54, 318, 150, 14);
		generalPanel.add(courseLabel);
		
		JComboBox courseCombo = new JComboBox();
		courseCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseCombo.setEditable(true);
		courseCombo.setModel(new DefaultComboBoxModel(user.getCourses()));
		courseCombo.setSelectedItem(enq.getCourse());
		courseCombo.setBounds(139, 316, 214, 20);
		generalPanel.add(courseCombo);
		
		JLabel weeklyPrice2 = new JLabel(enq.getWeeklyPrice());
		weeklyPrice2.setForeground(new Color(142, 39, 148));
		weeklyPrice2.setFont(new Font("Tahoma", Font.BOLD, 12));
		weeklyPrice2.setBounds(505, 354, 210, 14);
		generalPanel.add(weeklyPrice2);
		
		Controller.autoCalculateEnqWeeklyPrice(totalPriceField, noWeeksField, weeklyPrice2);
		
		JButton btnConfirmChanges = new JButton("Confirm ");
		btnConfirmChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Controller.updateEnquiry(user, enq, firstNameField, lastNameField, noWeeksField, 
						weeklyPrice2, totalPriceField, statusCombo, lastContactedDateChooser, sourceField, 
						   salesAdvisorCombo, countryCombo, agentCombo, courseCombo, emailField, 
						   phoneField, campusCombo, warningLabel);	
			}
		});
		btnConfirmChanges.setForeground(Color.WHITE);
		btnConfirmChanges.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConfirmChanges.setBackground(new Color(142, 39, 148));
		btnConfirmChanges.setBounds(642, 398, 84, 25);
		generalPanel.add(btnConfirmChanges);
		btnUndoChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Controller.resetEnquiryFields(enq, user, firstNameField, totalPriceField, courseCombo, lastNameField, 
						sourceField, courseCombo, courseCombo, courseCombo, courseLabel, courseCombo, noWeeksField, 
						phoneField, emailField, courseCombo, lastContactedDateChooser);
			}
		});
		btnUndoChanges.setForeground(Color.WHITE);
		btnUndoChanges.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUndoChanges.setBackground(new Color(142, 39, 148));
		btnUndoChanges.setBounds(548, 398, 84, 25);
		
		generalPanel.add(btnUndoChanges);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		phoneLabel.setForeground(new Color(142, 39, 148));
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		phoneLabel.setBounds(377, 278, 150, 14);
		generalPanel.add(phoneLabel);
		
		phoneField = new JTextField();
		phoneField.setForeground(new Color(142, 39, 148));
		phoneField.setText(enq.getPhone());
		phoneField.setColumns(10);
		phoneField.setBounds(488, 270, 215, 25);
		generalPanel.add(phoneField);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setForeground(new Color(142, 39, 148));
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		emailLabel.setBounds(377, 311, 150, 14);
		generalPanel.add(emailLabel);
		
		emailField = new JTextField();
		emailField.setForeground(new Color(142, 39, 148));
		emailField.setText(enq.getEmail());
		emailField.setColumns(10);
		emailField.setBounds(488, 306, 215, 24);
		generalPanel.add(emailField);
		warningLabel.setForeground(Color.RED);
		warningLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel.setBounds(54, 408, 450, 28);
		warningLabel.setVisible(false);
		
		generalPanel.add(warningLabel);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewEnquiries ve = new ViewEnquiries(user);
				dispose();
				
			}
		});
		btnBack.setForeground(Color.WHITE);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBack.setBackground(new Color(142, 39, 148));
		btnBack.setBounds(455, 398, 84, 25);
		generalPanel.add(btnBack);
		
		
		RoundedPanel roundedPanel = new RoundedPanel();
		roundedPanel.setLayout(null);
		roundedPanel.setForeground(Color.BLACK);
		roundedPanel.setBackground(new Color(142, 39, 148));
		roundedPanel.setBounds(93, 187, 172, 249);
		notesPanel.add(roundedPanel);
		noteDate.setBounds(62, 177, 209, 26);
		roundedPanel.add(noteDate);
		noteDate.setForeground(Color.WHITE);
		noteDate.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteTime.setBounds(62, 202, 182, 26);
		roundedPanel.add(noteTime);
		noteTime.setForeground(Color.WHITE);
		noteTime.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteAuthor.setBounds(62, 152, 213, 26);
		roundedPanel.add(noteAuthor);
		noteAuthor.setForeground(Color.WHITE);
		noteAuthor.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		note1Area = new JTextArea();
		note1Area.setForeground(Color.BLACK);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		note1Area.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		note1Area.setFont(new Font("Tahoma", Font.PLAIN, 11));
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
		noteAuthor2.setBounds(62, 152, 149, 26);
		roundedPanel_2.add(noteAuthor2);
		noteAuthor2.setForeground(Color.WHITE);
		noteAuthor2.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteDate2.setBounds(62, 177, 149, 26);
		roundedPanel_2.add(noteDate2);
		noteDate2.setForeground(Color.WHITE);
		noteDate2.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteTime2.setBounds(62, 202, 149, 26);
		roundedPanel_2.add(noteTime2);
		noteTime2.setForeground(Color.WHITE);
		noteTime2.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		note2Area = new JTextArea();
		note2Area.setForeground(Color.BLACK);
		note2Area.setBorder(BorderFactory.createCompoundBorder(border, 
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		note2Area.setLineWrap(true);
		note2Area.setWrapStyleWord(true);
		note2Area.setFont(new Font("Tahoma", Font.PLAIN, 11));
		note2Area.setBounds(10, 11, 147, 130);
		roundedPanel_2.add(note2Area);
		note2Area.setColumns(10);
		
		RoundedPanel roundedPanel_3 = new RoundedPanel();
		roundedPanel_3.setLayout(null);
		roundedPanel_3.setForeground(Color.BLACK);
		roundedPanel_3.setBackground(new Color(142, 39, 148));
		roundedPanel_3.setBounds(457, 187, 172, 249);
		notesPanel.add(roundedPanel_3);
		
		note3Area = new JTextArea();
		note3Area.setForeground(Color.BLACK);
		note3Area.setBorder(BorderFactory.createCompoundBorder(border, 
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		note3Area.setFont(new Font("Tahoma", Font.PLAIN, 11));
		note3Area.setWrapStyleWord(true);
		note3Area.setLineWrap(true);
		note3Area.setBounds(10, 11, 147, 130);
		roundedPanel_3.add(note3Area);
		note3Area.setColumns(10);
		noteTime3.setBounds(62, 202, 131, 26);
		roundedPanel_3.add(noteTime3);
		noteTime3.setForeground(Color.WHITE);
		noteTime3.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteDate3.setBounds(62, 177, 131, 26);
		roundedPanel_3.add(noteDate3);
		noteDate3.setForeground(Color.WHITE);
		noteDate3.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		
		
		label_10.setForeground(Color.WHITE);
		label_10.setFont(new Font("Tahoma", Font.BOLD, 10));
		label_10.setBounds(10, 202, 131, 26);
		
		roundedPanel.add(label_10);
		label_11.setForeground(Color.WHITE);
		label_11.setFont(new Font("Tahoma", Font.BOLD, 10));
		label_11.setBounds(10, 177, 131, 26);
		
		roundedPanel.add(label_11);
		label_12.setForeground(Color.WHITE);
		label_12.setFont(new Font("Tahoma", Font.BOLD, 10));
		label_12.setBounds(10, 152, 131, 26);

		roundedPanel.add(label_12);
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("Tahoma", Font.BOLD, 10));
		label_7.setBounds(10, 202, 131, 26);

		roundedPanel_2.add(label_7);
		label_8.setForeground(Color.WHITE);
		label_8.setFont(new Font("Tahoma", Font.BOLD, 10));
		label_8.setBounds(10, 177, 131, 26);

		roundedPanel_2.add(label_8);
		label_9.setForeground(Color.WHITE);
		label_9.setFont(new Font("Tahoma", Font.BOLD, 10));
		label_9.setBounds(10, 152, 131, 26);

		roundedPanel_2.add(label_9);
		noteAuthor3.setBounds(62, 152, 131, 26);
		roundedPanel_3.add(noteAuthor3);
		noteAuthor3.setForeground(Color.WHITE);
		noteAuthor3.setFont(new Font("Tahoma", Font.BOLD, 10));

		JLabel label_6 = new JLabel("Time:");
		label_6.setBounds(10, 202, 131, 26);
		roundedPanel_3.add(label_6);
		label_6.setForeground(Color.WHITE);
		label_6.setFont(new Font("Tahoma", Font.BOLD, 10));

		JLabel label_5 = new JLabel("Date:");
		label_5.setBounds(10, 177, 131, 26);
		roundedPanel_3.add(label_5);
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 10));

		JLabel label_4 = new JLabel("Author:");
		label_4.setBounds(10, 152, 131, 26);
		roundedPanel_3.add(label_4);
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 10));

		JButton newerNotes = new JButton("<");
		newerNotes.setBounds(41, 274, 43, 84);
		notesPanel.add(newerNotes);
		newerNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (l > 0) {

					l--;
					m--;
					r--;

					Controller.updateNotesLeft(l, r, m, enq.getEnquiryNotes(),
							note1Area, note2Area, note3Area, noteAuthor,
							noteTime, noteDate, noteAuthor2, noteTime2,
							noteDate2, noteAuthor3, noteTime3, noteDate3);
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
		warningLabel5.setBounds(525, 141, 157, 27);
		notesPanel.add(warningLabel5);
		warningLabel5.setVisible(false);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(527, 34, 57, 25);
		roundedPanel_4.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (addNoteArea.getText().equals("")) {
					warningLabel5.setVisible(true);
				}

				else {
					warningLabel5.setVisible(false);

					Controller.addEnqNote(user, addNoteArea, enq, note1Area,
							note2Area, note3Area, noteAuthor, noteDate,
							noteTime, noteAuthor2, noteDate2, noteTime2,
							noteAuthor3, noteDate3, noteTime3);
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
		addNoteArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		addNoteArea.setWrapStyleWord(true);
		addNoteArea.setLineWrap(true);
		addNoteArea.setBounds(115, 16, 395, 65);
		roundedPanel_4.add(addNoteArea);
		addNoteArea.setColumns(10);

		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setLayout(null);
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(10, 95, 245, 474);
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

		Controller.generateNotes(enq.getEnquiryNotes(), note1Area,note2Area, note3Area, noteAuthor, noteTime, noteDate, 
				noteAuthor2, noteTime2, noteDate2, noteAuthor3, noteTime3, noteDate3);
		
		this.setVisible(true);
	}
}