/**
 * This class represents the Home Screen view for staff. 
 * 
 * The various swing components for the view along with their
 * characteristics and attributes. Relevant listeners are added
 * to buttons and their action performed methods defined. 
 * 
 * References:
 * http://www.jfree.org/jfreechart/
 * 
 * @author Ian Shields, 1506416
 * @version 07/09/2015
 */
package com.sam.client;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import com.sam.server.Agent;
import com.sam.server.Application;
import com.sam.server.Enquiry;
import com.sam.server.User;

import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;

import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.SwingConstants;


public class HomeScreen extends JPanel {

	// field variables
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private final JLabel lblNewLabel = new JLabel("New label");
	
	
	

	
	// constructor
	public HomeScreen(User user) {
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
	   
		frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
                e.getWindow().dispose();
            }
        });
		
		
		// label for displaying logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(15, 7, 245, 89);
		frame.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// need to set this to NOT CONNECTED when the client/server connection ends
		frame.setTitle("Student Application Management");
		
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
		
		// logout button
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBackground(new Color(255,255,255));
		logoutButton.setForeground(new Color(142, 39, 148));
		logoutButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				System.exit(0);
			}
		});
		logoutButton.setBounds(790, 49, 234, 41);
		frame.getContentPane().add(logoutButton);
	
		
		
		// welcome user label displays welcome message plus users name
		JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername());
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		welcomeLabel.setForeground(new Color(142, 39, 148));
		welcomeLabel.setBounds(421, 11, 211, 25);
		frame.getContentPane().add(welcomeLabel);
		
		
		
		
		// recent activity panel
		JPanel recentActivityPanel2 = new RoundedPanel();
		recentActivityPanel2.setForeground(new Color(142, 39, 148));
		recentActivityPanel2.setBackground(Color.WHITE);
		recentActivityPanel2.setBounds(790, 101, 234, 258);
		
		frame.getContentPane().add(recentActivityPanel2);
		recentActivityPanel2.setLayout(null);
		
		JLabel lblActivityThisWeek = new JLabel("Activity This Week");
		lblActivityThisWeek.setHorizontalAlignment(SwingConstants.CENTER);
		lblActivityThisWeek.setBounds(20, 11, 185, 17);
		lblActivityThisWeek.setForeground(new Color(142, 39, 148));
		lblActivityThisWeek.setFont(new Font("Tahoma", Font.BOLD, 14));
		recentActivityPanel2.add(lblActivityThisWeek);
		
		JLabel lblFastrecruitmentAddedA = new JLabel("New Applications: " + Controller.getAppsThisWeek(user.getUserApplications()));
		lblFastrecruitmentAddedA.setHorizontalAlignment(SwingConstants.CENTER);
		lblFastrecruitmentAddedA.setForeground(new Color(142, 39, 148));
		lblFastrecruitmentAddedA.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblFastrecruitmentAddedA.setBounds(20, 63, 204, 22);
		recentActivityPanel2.add(lblFastrecruitmentAddedA);
		
		JLabel lblNewEnquiries = new JLabel("New Enquiries: " + Controller.getEnqsThisWeek(user.getEnquiries()));
		lblNewEnquiries.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewEnquiries.setForeground(new Color(142, 39, 148));
		lblNewEnquiries.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblNewEnquiries.setBounds(10, 110, 214, 22);
		recentActivityPanel2.add(lblNewEnquiries);
		
		JLabel lblPaymentReceived = new JLabel("New Payments: " + Controller.getPaymentsThisWeek(user.getUserApplications()));
		lblPaymentReceived.setHorizontalAlignment(SwingConstants.CENTER);
		lblPaymentReceived.setForeground(new Color(142, 39, 148));
		lblPaymentReceived.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblPaymentReceived.setBounds(10, 159, 214, 22);
		recentActivityPanel2.add(lblPaymentReceived);
		
		// recruitment stats panel
		JPanel recruitmentStatsPanel2 = new RoundedPanel();
		recruitmentStatsPanel2.setForeground(new Color(142, 39, 148));
		recruitmentStatsPanel2.setBackground(Color.WHITE);
		recruitmentStatsPanel2.setBounds(790, 370, 234, 211);
		frame.getContentPane().add(recruitmentStatsPanel2);
		recruitmentStatsPanel2.setLayout(null);
		
		// recruitment stats label
		JLabel label_1 = new JLabel("Recruitment Statistics");
		label_1.setBounds(44, 23, 225, 17);
		label_1.setForeground(new Color(142, 39, 148));
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		recruitmentStatsPanel2.add(label_1);
		
		
		
		
		JLabel lblPercentageOf = new JLabel();
		
		if(user.getUserApplications().size()!=0){
		float conversionRate;
		conversionRate = (float) ((Controller.getTotalPayments(user.getUserApplications()) * 100 / user.getUserApplications().size()));
		lblPercentageOf.setText("Conversion Rate: " + conversionRate + " %");
		}
		else{
			lblPercentageOf.setText("Conversion Rate: 0 %");
		}
		lblPercentageOf.setForeground(new Color(142, 39, 148));
		lblPercentageOf.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblPercentageOf.setBounds(44, 72, 274, 22);
		recruitmentStatsPanel2.add(lblPercentageOf);
		
		JLabel lblNumberOf = new JLabel("Nationalities Recruited: 0");
		lblNumberOf.setForeground(new Color(142, 39, 148));
		lblNumberOf.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblNumberOf.setBounds(32, 123, 202, 22);
		recruitmentStatsPanel2.add(lblNumberOf);
		
		ChartPanel panel = new ChartPanel(createChart(createDataset(user)));
		panel.setVisible(true);
		panel.setLocation(294, 116);
		panel.setSize(new Dimension(452, 448));
		frame.getContentPane().add(panel);
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(284, 103, 476, 478);
		frame.getContentPane().add(roundedPanel_1);
		
		RoundedPanel roundedPanel = new RoundedPanel();
		roundedPanel.setLayout(null);
		roundedPanel.setForeground(new Color(142, 39, 148));
		roundedPanel.setBackground(Color.WHITE);
		roundedPanel.setBounds(15, 101, 245, 480);
		frame.getContentPane().add(roundedPanel);
		
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
	
    private CategoryDataset createDataset(User user) {
        String row = "Row";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        dataset.addValue(user.getEnquiries().size(), row, "Enquiries");
        dataset.addValue(user.getUserApplications().size(), row, "Applications"); // link to JDBC
        dataset.addValue(Controller.getTotalPayments(user.getUserApplications()), row, "Payments");	 // link to JDBC

        return dataset;
    }
    
    private JFreeChart createChart(CategoryDataset dataset) {
        CategoryAxis categoryAxis = new CategoryAxis("");
        ValueAxis valueAxis = new NumberAxis("");
        valueAxis.setVisible(false);
        BarRenderer renderer = new BarRenderer() {

            @Override
            public Paint getItemPaint(int row, int column) {
                switch (column) {
                    case 0:
                        return new Color(142, 39, 148);
                    case 1:
                        return new Color(142, 39, 148);
                    case 2:
                        return new Color(142, 39, 148);
                    case 3:
                        return new Color(142, 39, 148);
                    case 4:
                        return new Color(142, 39, 148);
                    case 5:
                        return new Color(142, 39, 148);
                    default:
                        return new Color(142, 39, 148);
                }
            }
        };
        renderer.setDrawBarOutline(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
            ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER));
        renderer.setBaseItemLabelsVisible(Boolean.TRUE);
        renderer.setBarPainter(new StandardBarPainter());
        CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis, renderer);
        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(Color.white);
        return chart;
    }
}
