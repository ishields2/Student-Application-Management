/**
 * This class represents the Add New Agent view for staff. 
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
import javax.swing.SwingConstants;

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




public class AddNewAgent extends JPanel {

	// field variables
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private final JLabel lblNewLabel = new JLabel("New label");
	private User user;
	
	JLabel warningLabel = new JLabel("Please complete all fields.  ");
	private JTextField phoneField;
	private JTextField emailField;
	private JTextField companyNameField;
	
	
	// constructor
	public AddNewAgent(User user) {
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
		logoLabel.setBounds(10, 0, 245, 103);
		frame.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// need to set this to NOT CONNECTED when the client/server connection ends
		frame.setTitle("Status: CONNECTED");
		
		
		
		RoundedPanel roundedPanel = new RoundedPanel();
		
		roundedPanel.setForeground(new Color(142, 39, 148));
		roundedPanel.setBackground(Color.WHITE);
		roundedPanel.setBounds(284, 102, 726, 474);
		frame.getContentPane().add(roundedPanel);
		roundedPanel.setLayout(null);
		
		String[]agents = new String[user.getAgentsList().size()];
		for(int i = 0; i < user.getAgentsList().size(); i++){
			agents[i] = user.getAgentsList().get(i).getCompanyName();
		}
		
		
		
		JLabel lblLastContacted = new JLabel("Last Contacted: ");
		lblLastContacted.setBounds(381, 216, 151, 14);
		roundedPanel.add(lblLastContacted);
		lblLastContacted.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastContacted.setForeground(new Color(142, 39, 148));
		
		JComboBox countryCombo = new JComboBox();
		countryCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		countryCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		countryCombo.setBounds(180, 181, 179, 20);
		roundedPanel.add(countryCombo);
		
		JComboBox statusCombo = new JComboBox();
		statusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Active", "Signed", "Expected", "Cold", "Newly Contacted", "Lost"}));
		statusCombo.setBounds(180, 211, 179, 20);
		roundedPanel.add(statusCombo);
		
		JLabel lblCountry = new JLabel("Country: ");
		lblCountry.setBounds(38, 183, 131, 14);
		roundedPanel.add(lblCountry);
		lblCountry.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCountry.setForeground(new Color(142, 39, 148));
		
		JLabel lblFirstName = new JLabel("Contact First Name:");
		lblFirstName.setBounds(36, 103, 149, 15);
		roundedPanel.add(lblFirstName);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFirstName.setForeground(new Color(142, 39, 148));
		
		JTextField firstNameField = new JTextField();
		firstNameField.setBounds(179, 98, 180, 25);
		roundedPanel.add(firstNameField);
		firstNameField.setForeground(new Color(142, 39, 148));
		firstNameField.setColumns(10);
		
		JLabel lblLastName = new JLabel("Contact Last Name: ");
		lblLastName.setBounds(381, 108, 150, 14);
		roundedPanel.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastName.setForeground(new Color(142, 39, 148));
		
		JTextField lastNameField = new JTextField();
		lastNameField.setBounds(526, 103, 180, 25);
		roundedPanel.add(lastNameField);
		lastNameField.setForeground(new Color(142, 39, 148));
		lastNameField.setColumns(10);
		
		JLabel companyfield = new JLabel("Company Name:  ");
		companyfield.setBounds(36, 142, 131, 14);
		roundedPanel.add(companyfield);
		companyfield.setFont(new Font("Tahoma", Font.BOLD, 14));
		companyfield.setForeground(new Color(142, 39, 148));
		
		JLabel lblAddNewEnquiry = new JLabel("Add New Agent:");
		lblAddNewEnquiry.setForeground(new Color(142, 39, 148));
		lblAddNewEnquiry.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAddNewEnquiry.setBounds(38, 31, 181, 31);
		roundedPanel.add(lblAddNewEnquiry);
		
		warningLabel.setForeground(Color.RED);
		warningLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		warningLabel.setBounds(38, 388, 500, 33);
		roundedPanel.add(warningLabel);
		warningLabel.setVisible(false);
		
		JDateChooser lastContactedCombo = new JDateChooser();
		lastContactedCombo.setBounds(526, 210, 180, 25);
		roundedPanel.add(lastContactedCombo);
		
		JComboBox salesAdvisorCombo = new JComboBox();
		salesAdvisorCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		salesAdvisorCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
		salesAdvisorCombo.setBounds(180, 246, 179, 20);
		roundedPanel.add(salesAdvisorCombo);
		
		JLabel lblPrice = new JLabel("");
		lblPrice.setForeground(new Color(142, 39, 148));
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrice.setBounds(665, 430, 95, 14);
		lblPrice.setVisible(true);
		roundedPanel.add(lblPrice);
		
		JLabel lblStatus_1 = new JLabel("Status:");
		lblStatus_1.setForeground(new Color(142, 39, 148));
		lblStatus_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStatus_1.setBounds(38, 217, 133, 14);
		roundedPanel.add(lblStatus_1);
		
		JLabel lblSalesAdvisor = new JLabel("Account Manager: ");
		lblSalesAdvisor.setForeground(new Color(142, 39, 148));
		lblSalesAdvisor.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSalesAdvisor.setBounds(38, 252, 132, 14);
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
		
		phoneField = new JTextField();
		phoneField.setForeground(new Color(142, 39, 148));
		phoneField.setColumns(10);
		phoneField.setBounds(526, 139, 180, 25);
		roundedPanel.add(phoneField);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		phoneLabel.setForeground(new Color(142, 39, 148));
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		phoneLabel.setBounds(381, 150, 150, 14);
		roundedPanel.add(phoneLabel);
		
		emailField = new JTextField();
		emailField.setForeground(new Color(142, 39, 148));
		emailField.setColumns(10);
		emailField.setBounds(526, 175, 180, 24);
		roundedPanel.add(emailField);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(142, 39, 148));
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(381, 183, 150, 14);
		roundedPanel.add(lblEmail);
		
		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setForeground(new Color(142, 39, 148));
		addressLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		addressLabel.setBounds(38, 294, 150, 14);
		roundedPanel.add(addressLabel);
		
		JTextArea addressField = new JTextArea();
		addressField.setBounds(180, 291, 272, 86);
		roundedPanel.add(addressField);
		addressField.setLineWrap(true);
		addressField.setWrapStyleWord(true);
		
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Controller.addAgent(frame, firstNameField, warningLabel,  lastNameField, 
					 companyNameField,  countryCombo,  statusCombo,  salesAdvisorCombo, 
					 lastContactedCombo,  phoneField,  emailField, addressField, user);
			}
		});
		btnConfirm.setBackground(new Color(142, 39, 148));
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnConfirm.setBounds(571, 430, 139, 31);
		roundedPanel.add(btnConfirm);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(142, 39, 148));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancel.setBounds(422, 431, 139, 31);
		roundedPanel.add(btnCancel);
		
		companyNameField = new JTextField();
		companyNameField.setForeground(new Color(142, 39, 148));
		companyNameField.setColumns(10);
		companyNameField.setBounds(180, 134, 179, 25);
		roundedPanel.add(companyNameField);
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setLayout(null);
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(10, 102, 245, 474);
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

