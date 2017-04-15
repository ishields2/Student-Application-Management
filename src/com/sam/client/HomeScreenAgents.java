/**
 * This class represents the Home Screen view for agents. 
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
import javax.swing.JTextArea;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.SwingConstants;




public class HomeScreenAgents extends JPanel {

	// field variables
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private final JLabel lblNewLabel = new JLabel("New label");
	
	
	

	
	// constructor
	public HomeScreenAgents (User user) {
		initialize(user);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		this.frame.setVisible(true);
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
		logoLabel.setBounds(5, 7, 245, 89);
		frame.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// need to set this to NOT CONNECTED when the client/server connection ends
		frame.setTitle("Student Application Management");
		
		// search text field
		JTextField searchBox = new JTextField();
		searchBox.setBounds(260, 59, 429, 31);
		searchBox.setBorder(new RoundedCornerBorder());
		frame.getContentPane().add(searchBox);
		searchBox.setColumns(10);
		
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
				
						user.setCommand(searchBox.getText());
						user.setSearchApps(new ArrayList<Application>());
						ApplicationSearch as = new ApplicationSearch(user); 
					}
				
			}
		});
		searchButton.setForeground(Color.WHITE);
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		searchButton.setBackground(new Color(142, 39, 148));
		searchButton.setBounds(699, 58, 73, 31);
		frame.getContentPane().add(searchButton);
		
		// recent activity panel
		JPanel recentActivityPanel2 = new RoundedPanel();
		recentActivityPanel2.setForeground(new Color(142, 39, 148));
		recentActivityPanel2.setBackground(Color.WHITE);
		recentActivityPanel2.setBounds(790, 113, 234, 468);
		frame.getContentPane().add(recentActivityPanel2);
		recentActivityPanel2.setLayout(null);
		
		// logout button
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBackground(new Color(142, 39, 148));
		logoutButton.setForeground(Color.WHITE);
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
		
		RoundedPanel roundedPanel = new RoundedPanel();
		roundedPanel.setLayout(null);
		roundedPanel.setForeground(new Color(142, 39, 148));
		roundedPanel.setBackground(new Color(142, 39, 148));
		roundedPanel.setBounds(265, 111, 509, 247);
		frame.getContentPane().add(roundedPanel);
		
		JLabel label = new JLabel("");
		label.setBounds(10, 11, 489, 225);
		roundedPanel.add(label);
		
		ImageIcon picture = new ImageIcon(this.getClass().getResource("/Banner Photo Small.jpg"));
		label.setIcon(picture);
		
		// recruitment stats panel
				JPanel recruitmentStatsPanel2 = new RoundedPanel();
				recruitmentStatsPanel2.setForeground(new Color(142, 39, 148));
				recruitmentStatsPanel2.setBackground(Color.WHITE);
				recruitmentStatsPanel2.setBounds(265, 369, 509, 216);
				frame.getContentPane().add(
						recruitmentStatsPanel2);
				recruitmentStatsPanel2.setLayout(null);

				JLabel lblStudentManagement = new JLabel(
						"Student Application Management System (SAM)");
				lblStudentManagement.setForeground(new Color(142, 39, 148));
				lblStudentManagement.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblStudentManagement.setBounds(10, 5, 580, 25);
				recruitmentStatsPanel2.add(lblStudentManagement);

				JTextArea txtrThankYouFor = new JTextArea();
				txtrThankYouFor.setEditable(false);
				txtrThankYouFor.setFont(new Font("Tahoma", Font.PLAIN, 11));
				txtrThankYouFor
						.setText("Thank you for using the Student Application Management System! \r\n\r\nSAM is a fully functional online management system that will enable both institutions and partners to easily and effectively manage their recruitment activities together.  \r\n\r\nIn addition to providing the ability to Add, View and Update Student Applications, SAM also acts as a database for keeping track of previous or current students that have embarked on an educational journey with you. \r\n\r\nSAM also provides a downloads section where you can find the latest resources. If you have any questions about SAM, please contact us at ishields2@gmail.com");
				txtrThankYouFor.setWrapStyleWord(true);
				txtrThankYouFor.setLineWrap(true);
				txtrThankYouFor.setBounds(10, 38, 489, 152);
				txtrThankYouFor
						.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				recruitmentStatsPanel2.add(txtrThankYouFor);
				
				JLabel lblWhy = new JLabel("About Us");
				lblWhy.setBounds(71, 11, 142, 25);
				recentActivityPanel2.add(lblWhy);
				lblWhy.setForeground(new Color(142, 39, 148));
				lblWhy.setFont(new Font("Tahoma", Font.BOLD, 16));
				
				JTextArea txtrHighQualityTeaching = new JTextArea();
				txtrHighQualityTeaching.setForeground(new Color(142, 39, 148));
				txtrHighQualityTeaching.setFont(new Font("Tahoma", Font.BOLD, 12));
				txtrHighQualityTeaching.setText(" High quality teaching standards\r\n\r\n\r\n  Excellent, modern facilities at a\r\n            truly  traditional \r\n     English Boarding School\r\n\r\n\r\n  24 hour welfare support and\r\n         student care team\r\n\r\n\r\n  Secure, comfortable, modern \r\n             accommodation\r\n\r\n        Organised excursions to \r\n           Top UK Attractions \r\n                  including \r\n     Buckingham Palace and the \r\n        Harry Potter Experience\r\n\r\n\r\n      1 : 8 staff to student ratio, \r\n      enabling unrivalled support \r\n                and service");
				txtrHighQualityTeaching.setBounds(10, 71, 219, 393);
				txtrHighQualityTeaching.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				recentActivityPanel2.add(txtrHighQualityTeaching);
		

		
		RoundedPanel roundedPanelM = new RoundedPanel();
		roundedPanelM.setLayout(null);
		roundedPanelM.setForeground(new Color(142, 39, 148));
		roundedPanelM.setBackground(Color.WHITE);
		roundedPanelM.setBounds(5, 107, 245, 474);
		frame.getContentPane().add(roundedPanelM);
		
		JLabel label2 = new JLabel("Dashboard");
		label2.setForeground(new Color(142, 39, 148));
		label2.setFont(new Font("Tahoma", Font.BOLD, 16));
		label2.setBounds(75, 11, 98, 17);
		roundedPanelM.add(label2);
		
		JButton button = new JButton("Enrolled Students");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 14));
		button.setBackground(new Color(142, 39, 148));
		button.setBounds(10, 248, 220, 39);
		roundedPanelM.add(button);
		
		JButton button_1 = new JButton("View Applications");
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_1.setBackground(new Color(142, 39, 148));
		button_1.setBounds(10, 198, 220, 39);
		roundedPanelM.add(button_1);
		
		JButton button_2 = new JButton("Add Group Application");
	
		button_2.setForeground(Color.WHITE);
		button_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_2.setBackground(new Color(142, 39, 148));
		button_2.setBounds(10, 148, 220, 39);
		roundedPanelM.add(button_2);
		
		JButton button_3 = new JButton("Add Application");
		button_3.setForeground(Color.WHITE);
		button_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_3.setBackground(new Color(142, 39, 148));
		button_3.setBounds(10, 98, 220, 39);
		roundedPanelM.add(button_3);
		
		JButton button_4 = new JButton("Home");
		button_4.setForeground(Color.WHITE);
		button_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_4.setBackground(new Color(142, 39, 148));
		button_4.setBounds(10, 48, 220, 39);
		roundedPanelM.add(button_4);
		
		JButton button_5 = new JButton("Downloads");
		button_5.setForeground(Color.WHITE);
		button_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_5.setBackground(new Color(142, 39, 148));
		button_5.setBounds(10, 298, 220, 39);
		roundedPanelM.add(button_5);
		
		JButton button_6 = new JButton("Website");
		button_6.addActionListener(new ActionListener() {
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
		button_6.setForeground(Color.WHITE);
		button_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_6.setBackground(new Color(142, 39, 148));
		button_6.setBounds(10, 348, 220, 39);
		roundedPanelM.add(button_6);
		
		JButton button_7 = new JButton("Help");
		button_7.setForeground(Color.WHITE);
		button_7.setFont(new Font("Tahoma", Font.BOLD, 14));
		button_7.setBackground(new Color(142, 39, 148));
		button_7.setBounds(10, 398, 220, 39);
		roundedPanelM.add(button_7);
		
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddApplicationAgents aaa = new AddApplicationAgents(user);
				
			}
		});
		
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddGroupApplicationAgents agaa = new AddGroupApplicationAgents(user);
					
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewApplications va = new ViewApplications(user);
				
			}
		});

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnrolledStudentsViewAgents esv = new EnrolledStudentsViewAgents(user);
				
			}
		});

		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DownloadsAgents da = new DownloadsAgents(user);
				
			}
		});

		button_6.addActionListener(new ActionListener() {
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
		


		button_7.addActionListener(new ActionListener() {
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


