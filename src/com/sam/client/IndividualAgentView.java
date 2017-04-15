/**
 * This class represents the Individual Agent view for staff. 
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

import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class IndividualAgentView extends JFrame {

	// field variables
	private static final long serialVersionUID = 1L;
	
	private final JLabel lblNewLabel = new JLabel("New label");


	JLabel logoLabel = new JLabel("");
	JLabel label_2 = new JLabel();
	JButton btnBack = new JButton("Back");
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	RoundedPanel generalPanel = new RoundedPanel();
	RoundedPanel contactPanel = new RoundedPanel();
	RoundedPanel notesPanel = new RoundedPanel();
	JLabel agentLabel = new JLabel("Application: "  );
	private JTextField adressBox;
	private JTextField firstNameField;
	private JTextField companyfield;
	private JTextField emailField;
	private JTextField phoneField;
	private JTextField lastNameField;
	private int l = 0;
	private int m = 1;
	private int r = 2;
	private JTextField textField;
	private JTextField textField_1;

	
	// constructor
	public IndividualAgentView(User user, Agent agent) throws ParseException {
	
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
		
		logoLabel.setBounds(10, 0, 339, 99);
		this.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
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
		
		tabbedPane.setBounds(283, 100, 730, 478);
		this.getContentPane().add(tabbedPane);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setForeground(new Color(142, 39, 148));
		
		
		generalPanel.setForeground(new Color(142, 39, 148));
		generalPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("General", null, generalPanel, null);
		generalPanel.setLayout(null);

		contactPanel.setForeground(new Color(142, 39, 148));
		contactPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Contact", null, contactPanel, null);
		contactPanel.setLayout(null);
		
		JLabel phoneLabel = new JLabel("Phone:");
		phoneLabel.setForeground(new Color(142, 39, 148));
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		phoneLabel.setBounds(47, 65, 131, 15);
		contactPanel.add(phoneLabel);
		
		JLabel emailLabel = new JLabel("Email: ");
		emailLabel.setForeground(new Color(142, 39, 148));
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		emailLabel.setBounds(47, 105, 131, 14);
		contactPanel.add(emailLabel);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setForeground(new Color(142, 39, 148));
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAddress.setBounds(47, 140, 131, 14);
		contactPanel.add(lblAddress);
		
		adressBox = new JTextField();
		adressBox.setHorizontalAlignment(SwingConstants.LEFT);
		adressBox.setText((String) null);
		adressBox.setForeground(new Color(142, 39, 148));
		adressBox.setEditable(true);
		adressBox.setText(agent.getAddress());
		adressBox.setColumns(10);
		adressBox.setBounds(109, 135, 561, 31);
		contactPanel.add(adressBox);
		
		textField = new JTextField();
		textField.setText(agent.getEmail());
		textField.setForeground(new Color(142, 39, 148));
		textField.setColumns(10);
		textField.setBounds(109, 97, 180, 31);
		contactPanel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setText(agent.getPhone());
		textField_1.setForeground(new Color(142, 39, 148));
		textField_1.setColumns(10);
		textField_1.setBounds(109, 58, 180, 31);
		contactPanel.add(textField_1);
		
	
		
		
		
		notesPanel.setForeground(new Color(142, 39, 148));
		notesPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Notes", null, notesPanel, null);
		notesPanel.setLayout(null);
		
		RoundedPanel roundedPanelM = new RoundedPanel();
		roundedPanelM.setLayout(null);
		roundedPanelM.setForeground(Color.BLACK);
		roundedPanelM.setBackground(new Color(142, 39, 148));
		roundedPanelM.setBounds(48, 44, 611, 99);
		notesPanel.add(roundedPanelM);
		
		JLabel label_1 = new JLabel("Add New Note:");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(10, 11, 131, 15);
		roundedPanelM.add(label_1);
		
		JButton addButton = new JButton("Add");
		
		addButton.setForeground(new Color(142, 39, 148));
		addButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		addButton.setBackground(Color.WHITE);
		addButton.setBounds(527, 34, 57, 25);
		roundedPanelM.add(addButton);
		
		JTextArea addNoteArea = new JTextArea();
		addNoteArea.setWrapStyleWord(true);
		addNoteArea.setLineWrap(true);
		addNoteArea.setForeground(Color.BLACK);
		addNoteArea.setFont(new Font("Tahoma", Font.PLAIN, 10));
		addNoteArea.setColumns(10);
		addNoteArea.setBounds(115, 16, 395, 65);
		roundedPanelM.add(addNoteArea);
		
		RoundedPanel roundedPanelM_1 = new RoundedPanel();
		roundedPanelM_1.setLayout(null);
		roundedPanelM_1.setForeground(Color.BLACK);
		roundedPanelM_1.setBackground(new Color(142, 39, 148));
		roundedPanelM_1.setBounds(84, 190, 172, 249);
		notesPanel.add(roundedPanelM_1);
		
		JLabel noteDate = new JLabel("");
		noteDate.setForeground(Color.WHITE);
		noteDate.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteDate.setBounds(60, 177, 209, 26);
		roundedPanelM_1.add(noteDate);
		
		JLabel noteTime = new JLabel("");
		noteTime.setForeground(Color.WHITE);
		noteTime.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteTime.setBounds(60, 202, 182, 26);
		roundedPanelM_1.add(noteTime);
		
		JLabel noteAuthor = new JLabel("");
		noteAuthor.setForeground(Color.WHITE);
		noteAuthor.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteAuthor.setBounds(60, 152, 213, 26);
		roundedPanelM_1.add(noteAuthor);
		
		JTextArea note1Area = new JTextArea();
		note1Area.setWrapStyleWord(true);
		note1Area.setLineWrap(true);
		note1Area.setForeground(Color.BLACK);
		note1Area.setFont(new Font("Tahoma", Font.PLAIN, 10));
		note1Area.setColumns(10);
		note1Area.setBounds(10, 11, 147, 130);
		roundedPanelM_1.add(note1Area);
		
		JLabel labeld = new JLabel("Time:");
		labeld.setForeground(Color.WHITE);
		labeld.setFont(new Font("Tahoma", Font.BOLD, 11));
		labeld.setBounds(10, 202, 131, 26);
		roundedPanelM_1.add(labeld);
		
		JLabel labelc = new JLabel("Date:");
		labelc.setForeground(Color.WHITE);
		labelc.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelc.setBounds(10, 177, 131, 26);
		roundedPanelM_1.add(labelc);
		
		JLabel labela = new JLabel("Author:");
		labela.setForeground(Color.WHITE);
		labela.setFont(new Font("Tahoma", Font.BOLD, 11));
		labela.setBounds(10, 152, 131, 26);
		roundedPanelM_1.add(labela);
		
		RoundedPanel roundedPanelM_2 = new RoundedPanel();
		roundedPanelM_2.setLayout(null);
		roundedPanelM_2.setForeground(Color.BLACK);
		roundedPanelM_2.setBackground(new Color(142, 39, 148));
		roundedPanelM_2.setBounds(276, 190, 172, 249);
		notesPanel.add(roundedPanelM_2);
		
		JLabel noteAuthor2 = new JLabel("");
		noteAuthor2.setForeground(Color.WHITE);
		noteAuthor2.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteAuthor2.setBounds(61, 152, 149, 26);
		roundedPanelM_2.add(noteAuthor2);
		
		JLabel noteDate2 = new JLabel("");
		noteDate2.setForeground(Color.WHITE);
		noteDate2.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteDate2.setBounds(61, 177, 149, 26);
		roundedPanelM_2.add(noteDate2);
		
		JLabel noteTime2 = new JLabel("");
		noteTime2.setForeground(Color.WHITE);
		noteTime2.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteTime2.setBounds(61, 202, 149, 26);
		roundedPanelM_2.add(noteTime2);
		
		JTextArea note2Area = new JTextArea();
		note2Area.setWrapStyleWord(true);
		note2Area.setLineWrap(true);
		note2Area.setForeground(Color.BLACK);
		note2Area.setFont(new Font("Tahoma", Font.PLAIN, 10));
		note2Area.setColumns(10);
		note2Area.setBounds(10, 11, 147, 130);
		roundedPanelM_2.add(note2Area);
		
		JLabel labelg = new JLabel("Time:");
		labelg.setForeground(Color.WHITE);
		labelg.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelg.setBounds(10, 202, 131, 26);
		roundedPanelM_2.add(labelg);
		
		JLabel labelf = new JLabel("Date:");
		labelf.setForeground(Color.WHITE);
		labelf.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelf.setBounds(10, 177, 131, 26);
		roundedPanelM_2.add(labelf);
		
		JLabel labele = new JLabel("Author:");
		labele.setForeground(Color.WHITE);
		labele.setFont(new Font("Tahoma", Font.BOLD, 11));
		labele.setBounds(10, 152, 131, 26);
		roundedPanelM_2.add(labele);
		
		JButton button_13 = new JButton("<");
		button_13.setForeground(new Color(142, 39, 148));
		button_13.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_13.setBackground(Color.WHITE);
		button_13.setBounds(-246, 57, 43, 84);
		roundedPanelM_2.add(button_13);
		
		JButton button_14 = new JButton(">");
		button_14.setForeground(new Color(142, 39, 148));
		button_14.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_14.setBackground(Color.WHITE);
		button_14.setBounds(376, 57, 43, 84);
		roundedPanelM_2.add(button_14);
		
		RoundedPanel roundedPanelM_3 = new RoundedPanel();
		roundedPanelM_3.setLayout(null);
		roundedPanelM_3.setForeground(Color.BLACK);
		roundedPanelM_3.setBackground(new Color(142, 39, 148));
		roundedPanelM_3.setBounds(471, 190, 172, 249);
		notesPanel.add(roundedPanelM_3);
		
		JTextArea note3Area = new JTextArea();
		note3Area.setWrapStyleWord(true);
		note3Area.setLineWrap(true);
		note3Area.setForeground(Color.BLACK);
		note3Area.setFont(new Font("Tahoma", Font.PLAIN, 10));
		note3Area.setColumns(10);
		note3Area.setBounds(10, 11, 147, 130);
		roundedPanelM_3.add(note3Area);
		
		JLabel noteTime3 = new JLabel("");
		noteTime3.setForeground(Color.WHITE);
		noteTime3.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteTime3.setBounds(60, 202, 131, 26);
		roundedPanelM_3.add(noteTime3);
		
		JLabel noteDate3 = new JLabel("");
		noteDate3.setForeground(Color.WHITE);
		noteDate3.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteDate3.setBounds(60, 177, 131, 26);
		roundedPanelM_3.add(noteDate3);
		
		JLabel noteAuthor3 = new JLabel("");
		noteAuthor3.setForeground(Color.WHITE);
		noteAuthor3.setFont(new Font("Tahoma", Font.BOLD, 10));
		noteAuthor3.setBounds(60, 152, 131, 26);
		roundedPanelM_3.add(noteAuthor3);
		
		JLabel labelj = new JLabel("Time:");
		labelj.setForeground(Color.WHITE);
		labelj.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelj.setBounds(10, 202, 131, 26);
		roundedPanelM_3.add(labelj);
		
		JLabel labeli = new JLabel("Date:");
		labeli.setForeground(Color.WHITE);
		labeli.setFont(new Font("Tahoma", Font.BOLD, 11));
		labeli.setBounds(10, 177, 131, 26);
		roundedPanelM_3.add(labeli);
		
		JLabel labelh = new JLabel("Author:");
		labelh.setForeground(Color.WHITE);
		labelh.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelh.setBounds(10, 152, 131, 26);
		roundedPanelM_3.add(labelh);
		
		JButton newerNotes = new JButton("<");
		newerNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(l >0){
				
				l--;
				m--;
				r--;
				

				Controller.updateNotesLeftAgent(l,r,m, agent.getNotes(), note1Area,note2Area, note3Area, noteAuthor, noteDate, noteTime, 
				noteAuthor2, noteDate2, noteTime2, noteAuthor3, noteDate3, noteTime3);
				}
			
				
				
			}
		});
		newerNotes.setForeground(new Color(142, 39, 148));
		newerNotes.setFont(new Font("Tahoma", Font.BOLD, 14));
		newerNotes.setBackground(Color.WHITE);
		newerNotes.setBounds(31, 258, 43, 84);
		notesPanel.add(newerNotes);
		
		JButton olderNotes = new JButton(">");
		olderNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("CALLED");
				if(r<agent.getNotes().size()){
					System.out.println("TRIGGERED");
					l++;
					m++;
					r++;
					
					
					Controller.updateNotesRightAgent(l,r,m, agent.getNotes(), note1Area,note2Area, note3Area, noteAuthor, noteDate, noteTime, 
							noteAuthor2, noteDate2, noteTime2, noteAuthor3, noteDate3, noteTime3);
				}
				
			}
		});
		olderNotes.setForeground(new Color(142, 39, 148));
		olderNotes.setFont(new Font("Tahoma", Font.BOLD, 14));
		olderNotes.setBackground(Color.WHITE);
		olderNotes.setBounds(653, 258, 43, 84);
		notesPanel.add(olderNotes);
		
		JLabel label_30 = new JLabel("Previous Notes:");
		label_30.setForeground(new Color(142, 39, 148));
		label_30.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_30.setBounds(48, 164, 131, 15);
		notesPanel.add(label_30);
		
		JLabel label_31 = new JLabel("Notes:");
		label_31.setForeground(new Color(142, 39, 148));
		label_31.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_31.setBounds(48, 16, 254, 17);
		notesPanel.add(label_31);
		
		JLabel warningLabel5 = new JLabel("Please Enter a Note");
		warningLabel5.setForeground(new Color(255, 0, 0));
		warningLabel5.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel5.setBounds(508, 145, 188, 17);
		notesPanel.add(warningLabel5);
		warningLabel5.setVisible(false);
		
		
		
		agentLabel.setText("Agent Name: " + agent.getCompanyName());
		agentLabel.setForeground(new Color(142, 39, 148));
		agentLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		agentLabel.setBounds(22, 25, 254, 17);
		generalPanel.add(agentLabel);
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(addNoteArea.getText().equals("")){
					warningLabel5.setVisible(true);
				}
				
				else{
					warningLabel5.setVisible(false);
					
					user.setCommand2(agent.getCompanyName());
					
					Controller.addAgentNote(user, addNoteArea, agent, note1Area, note2Area, note3Area, noteAuthor, noteDate, noteTime, 
							noteAuthor2, noteDate2, noteTime2, noteAuthor3, noteDate3, noteTime3);
				}
				
			}
		});
		
		JButton button_10 = new JButton("Back");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		button_10.setForeground(Color.WHITE);
		button_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_10.setBackground(new Color(142, 39, 148));
		button_10.setBounds(236, 414, 84, 25);
		generalPanel.add(button_10);
		
		
		
		JLabel warningLabel = new JLabel("Agent profile could not be updated. Please try again later.");
		warningLabel.setForeground(new Color(255, 0, 0));
		warningLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		warningLabel.setBounds(22, 378, 408, 25);
		warningLabel.setVisible(false);
		generalPanel.add(warningLabel);;
		
		
		JLabel label_4 = new JLabel("Contact First Name:");
		label_4.setForeground(new Color(142, 39, 148));
		label_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_4.setBounds(22, 75, 149, 15);
		generalPanel.add(label_4);
		
		firstNameField = new JTextField();
		firstNameField.setForeground(new Color(142, 39, 148));
		firstNameField.setText(agent.getFirstName());
		firstNameField.setColumns(10);
		firstNameField.setBounds(144, 70, 180, 30);
		generalPanel.add(firstNameField);
		
		JLabel label_5 = new JLabel("Company Name:  ");
		label_5.setForeground(new Color(142, 39, 148));
		label_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_5.setBounds(22, 114, 131, 14);
		generalPanel.add(label_5);
		
		companyfield = new JTextField();
		companyfield.setForeground(new Color(142, 39, 148));
		companyfield.setText(agent.getCompanyName());
		companyfield.setColumns(10);
		companyfield.setBounds(145, 106, 179, 30);
		generalPanel.add(companyfield);
		
		JLabel label_6 = new JLabel("Country: ");
		label_6.setForeground(new Color(142, 39, 148));
		label_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_6.setBounds(24, 155, 131, 14);
		generalPanel.add(label_6);
		
		JComboBox countryCombo = new JComboBox();
		countryCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Republic of the", "Congo, Democratic Republic of the", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis", "St. Lucia", "St. Vincent and The Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK", "USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"}));
		System.out.println((agent.getCountry()));
		countryCombo.setSelectedItem(agent.getCountry());
		countryCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		countryCombo.setBounds(145, 153, 179, 20);
		generalPanel.add(countryCombo);
		
		JLabel label_7 = new JLabel("Status:");
		label_7.setForeground(new Color(142, 39, 148));
		label_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_7.setBounds(24, 189, 133, 14);
		generalPanel.add(label_7);
		
		JComboBox statusCombo = new JComboBox();
		statusCombo.setModel(new DefaultComboBoxModel(new String[] {"Select", "Active", "Signed", "Expected", "Cold", "Newly Contacted", "Lost"}));
		statusCombo.setSelectedItem(agent.getStatus());
		
		
		statusCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statusCombo.setBounds(144, 186, 179, 20);
		generalPanel.add(statusCombo);
		
		JLabel label_8 = new JLabel("Account Manager: ");
		label_8.setForeground(new Color(142, 39, 148));
		label_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_8.setBounds(24, 224, 132, 14);
		generalPanel.add(label_8);
		
		JComboBox accManCombo = new JComboBox();
		accManCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		accManCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
		accManCombo.setSelectedItem(agent.getAccountManager());
		accManCombo.setBounds(145, 218, 179, 20);
		generalPanel.add(accManCombo);
		
		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setForeground(new Color(142, 39, 148));
		addressLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		addressLabel.setBounds(24, 266, 150, 14);
		generalPanel.add(addressLabel);
		
		JTextArea addressField = new JTextArea();
		addressField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		addressField.setWrapStyleWord(true);
		addressField.setLineWrap(true);
		addressField.setText(agent.getAddress());
		addressField.setBounds(145, 263, 179, 81);
		generalPanel.add(addressField);
		
		JLabel label_10 = new JLabel("Last Contacted: ");
		label_10.setForeground(new Color(142, 39, 148));
		label_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_10.setBounds(396, 183, 151, 14);
		generalPanel.add(label_10);
		
		JDateChooser lastContactedChooser = new JDateChooser();
		Date date3 = new SimpleDateFormat("dd/MM/yy").parse(agent.getLast_contacted());
		lastContactedChooser.setDate(date3);
		lastContactedChooser.setBounds(520, 177, 180, 29);
		generalPanel.add(lastContactedChooser);
		
		emailField = new JTextField();
		emailField.setForeground(new Color(142, 39, 148));
		emailField.setText(agent.getEmail());
		emailField.setColumns(10);
		emailField.setBounds(520, 142, 180, 27);
		generalPanel.add(emailField);
		
		JLabel label_11 = new JLabel("Email:");
		label_11.setForeground(new Color(142, 39, 148));
		label_11.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_11.setBounds(396, 150, 150, 14);
		generalPanel.add(label_11);
		
		JLabel label_12 = new JLabel("Phone: ");
		label_12.setForeground(new Color(142, 39, 148));
		label_12.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_12.setBounds(396, 117, 150, 14);
		generalPanel.add(label_12);
		
		phoneField = new JTextField();
		phoneField.setForeground(new Color(142, 39, 148));
		phoneField.setText(agent.getPhone());
		phoneField.setColumns(10);
		phoneField.setBounds(520, 106, 180, 30);
		generalPanel.add(phoneField);
		
		lastNameField = new JTextField();
		lastNameField.setForeground(new Color(142, 39, 148));
		lastNameField.setText(agent.getLastName());
		lastNameField.setColumns(10);
		lastNameField.setBounds(520, 70, 180, 30);
		generalPanel.add(lastNameField);
		
		JLabel label_13 = new JLabel("Contact Last Name: ");
		label_13.setForeground(new Color(142, 39, 148));
		label_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_13.setBounds(396, 75, 150, 14);
		generalPanel.add(label_13);
		
		JLabel appsToDateLabel = new JLabel("");
		appsToDateLabel.setForeground(new Color(142, 39, 148));
		appsToDateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		appsToDateLabel.setBounds(675, 285, 40, 14);
		generalPanel.add(appsToDateLabel);
		
		JLabel payToDateLabel = new JLabel("");
		payToDateLabel.setForeground(new Color(142, 39, 148));
		payToDateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		payToDateLabel.setBounds(684, 330, 31, 14);
		generalPanel.add(payToDateLabel);
		
		JLabel conversionLabel = new JLabel("Conversion Rate:       " + Controller.computeAgentConversionRate(agent.getAppsToDate(), agent.getStudentsToDate()) + "%");
		conversionLabel.setForeground(new Color(142, 39, 148));
		conversionLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		conversionLabel.setBounds(396, 310, 255, 14);
		generalPanel.add(conversionLabel);
		
		JButton button_11 = new JButton("Undo Changes");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.resetAgentFields(agent,
						user, firstNameField, lastNameField,
						 companyfield, countryCombo, statusCombo,
						 addressField,  phoneField,  emailField,
						 lastContactedChooser,  agentLabel, accManCombo);
			}
		});
		button_11.setForeground(Color.WHITE);
		button_11.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_11.setBackground(new Color(142, 39, 148));
		button_11.setBounds(329, 414, 181, 25);
		generalPanel.add(button_11);
		
		
		
		JButton button_12 = new JButton("Confirm Changes");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Controller.updateAgent(user,  agent, firstNameField,  lastNameField,  
						 statusCombo, lastContactedChooser, 
						    accManCombo,  countryCombo,   emailField,  companyfield,
						    phoneField,  warningLabel,  addressField,  appsToDateLabel,  payToDateLabel);
				

			}
		});
		
		button_12.setForeground(Color.WHITE);
		button_12.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_12.setBackground(new Color(142, 39, 148));
		button_12.setBounds(520, 414, 181, 25);
		generalPanel.add(button_12);
		
		JLabel lblApplicationsToDate = new JLabel("Applications to Date: " + agent.getAppsToDate());
		lblApplicationsToDate.setForeground(new Color(142, 39, 148));
		lblApplicationsToDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblApplicationsToDate.setBounds(396, 263, 265, 14);
		generalPanel.add(lblApplicationsToDate);
		
		JLabel lblPaymentsToDate = new JLabel("Payments To Date:   " + agent.getStudentsToDate());
		lblPaymentsToDate.setForeground(new Color(142, 39, 148));
		lblPaymentsToDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPaymentsToDate.setBounds(396, 288, 232, 14);
		generalPanel.add(lblPaymentsToDate);
		
		
		
		
		RoundedPanel roundedPanel = new RoundedPanel();
		roundedPanel.setLayout(null);
		roundedPanel.setForeground(new Color(142, 39, 148));
		roundedPanel.setBackground(Color.WHITE);
		roundedPanel.setBounds(10, 100, 245, 474);
		getContentPane().add(roundedPanel);
		
		JLabel label = new JLabel("Dashboard");
		label.setForeground(new Color(142, 39, 148));
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(76, 11, 98, 17);
		roundedPanel.add(label);
		
		JButton button = new JButton("Unsigned Agents");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		button.setBackground(new Color(142, 39, 148));
		button.setBounds(10, 383, 220, 32);
		roundedPanel.add(button);
		
		JButton button_1 = new JButton("Agents");
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_1.setBackground(new Color(142, 39, 148));
		button_1.setBounds(10, 340, 220, 32);
		roundedPanel.add(button_1);
		
		JButton button_2 = new JButton("Enrolled Students");
		button_2.setForeground(Color.WHITE);
		button_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_2.setBackground(new Color(142, 39, 148));
		button_2.setBounds(10, 297, 220, 32);
		roundedPanel.add(button_2);
		
		JButton button_3 = new JButton("View Applications");
		button_3.setForeground(Color.WHITE);
		button_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_3.setBackground(new Color(142, 39, 148));
		button_3.setBounds(10, 254, 220, 32);
		roundedPanel.add(button_3);
		
		JButton button_4 = new JButton("Add Group Application");
		button_4.setForeground(Color.WHITE);
		button_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_4.setBackground(new Color(142, 39, 148));
		button_4.setBounds(10, 211, 220, 32);
		roundedPanel.add(button_4);
		
		JButton button_5 = new JButton("Add Application");
		button_5.setForeground(Color.WHITE);
		button_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_5.setBackground(new Color(142, 39, 148));
		button_5.setBounds(10, 168, 220, 32);
		roundedPanel.add(button_5);
		
		JButton button_6 = new JButton("View Enquiries");
		button_6.setForeground(Color.WHITE);
		button_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_6.setBackground(new Color(142, 39, 148));
		button_6.setBounds(10, 125, 220, 32);
		roundedPanel.add(button_6);
		
		JButton button_7 = new JButton("Add Enquiry");
		button_7.setForeground(Color.WHITE);
		button_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_7.setBackground(new Color(142, 39, 148));
		button_7.setBounds(10, 82, 220, 32);
		roundedPanel.add(button_7);
		
		JButton button_8 = new JButton("Home");
		button_8.setForeground(Color.WHITE);
		button_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_8.setBackground(new Color(142, 39, 148));
		button_8.setBounds(10, 39, 220, 32);
		roundedPanel.add(button_8);
		
		JButton button_9 = new JButton("Documents");
		button_9.setForeground(Color.WHITE);
		button_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_9.setBackground(new Color(142, 39, 148));
		button_9.setBounds(10, 426, 220, 32);
		roundedPanel.add(button_9);
		
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

Controller.generateNotesAgent(agent.getNotes(), note1Area,note2Area, note3Area, noteAuthor, noteDate, noteTime, 
		noteAuthor2, noteDate2, noteTime2, noteAuthor3, noteDate3, noteTime3);
		
		this.setVisible(true);
	}
}
