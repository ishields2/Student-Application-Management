/**
 * This class represents the Downloads view for agents. 
 * 
 * The various swing components for the view along with their
 * characteristics and attributes. Relevant listeners are added
 * to buttons and their action performed methods defined.
 * 
 * References:
 * http://stackoverflow.com/questions/15771949/how-do-i-make-jfilechooser-only-accept-txt
 * http://stackoverflow.com/questions/10037644/opening-a-url-from-a-jbutton-in-simple-java-program
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

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JFileChooser;


public class DownloadsAgents extends JPanel {

	// field variables
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private final JLabel lblNewLabel = new JLabel("New label");
	private String chartTitle;
	JFileChooser fileChooser;

	
	// constructor
	public DownloadsAgents(User user) {
		initialize(user);
		this.frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
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
		frame.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
	      frame.setVisible(true);
		frame.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				
			}
			});
		
	
		Controller.updateDownloads(user);
		
		// label for displaying logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(5, 7, 242, 86);
		frame.getContentPane().add(logoLabel);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		
		// need to set this to NOT CONNECTED when the client/server connection ends
		frame.setTitle("Student Application Management");
		
		// logout button
		JButton logoutButton = new JButton("Back");
		logoutButton.setBackground(new Color(142, 39, 148));
		logoutButton.setForeground(Color.WHITE);
		logoutButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		logoutButton.setBounds(775, 49, 234, 41);
		frame.getContentPane().add(logoutButton);
	
		
		
		// welcome user label displays welcome message plus users name
		JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername());
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		welcomeLabel.setForeground(new Color(142, 39, 148));
		welcomeLabel.setBounds(359, 7, 211, 25);
		frame.getContentPane().add(welcomeLabel);
		
		
		JTextField searchBox = new JTextField();
		searchBox.setBounds(284, 59, 401, 31);
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
		searchButton.setBounds(695, 59, 73, 31);
		frame.getContentPane().add(searchButton);
		
		RoundedPanel roundedPanel_1 = new RoundedPanel();
		roundedPanel_1.setForeground(new Color(142, 39, 148));
		roundedPanel_1.setBackground(Color.WHITE);
		roundedPanel_1.setBounds(271, 95, 738, 474);
		frame.getContentPane().add(roundedPanel_1);
		roundedPanel_1.setLayout(null);
		
		JLabel lblManageDocuments = new JLabel("Downloads:");
		lblManageDocuments.setForeground(new Color(142, 39, 148));
		lblManageDocuments.setBounds(52, 22, 214, 20);
		lblManageDocuments.setBackground(new Color(142, 39, 148));
		lblManageDocuments.setFont(new Font("Tahoma", Font.BOLD, 16));
		roundedPanel_1.add(lblManageDocuments);
		
		JLabel uploadLabel = new JLabel("File successfully downloaded");
		uploadLabel.setForeground(new Color(0, 128, 0));
		uploadLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		uploadLabel.setBounds(155, 430, 422, 20);
		uploadLabel.setVisible(false);
		roundedPanel_1.add(uploadLabel);
		
		JComboBox downloadsCombo = new JComboBox();
		downloadsCombo.setBackground(new Color(142, 39, 148));
		downloadsCombo.setBounds(220, 134, 271, 27);
		downloadsCombo.setForeground(Color.WHITE);
		roundedPanel_1.add(downloadsCombo);
		downloadsCombo.setModel(new DefaultComboBoxModel(user.getFileNames()));
		
		JLabel lblSelectAFile = new JLabel("Select a File:");
		lblSelectAFile.setForeground(new Color(142, 39, 148));
		lblSelectAFile.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectAFile.setBackground(new Color(142, 39, 148));
		lblSelectAFile.setBounds(220, 98, 214, 20);
		roundedPanel_1.add(lblSelectAFile);
		
		JLabel fileLabel = new JLabel("File Selected: None");
		fileLabel.setForeground(new Color(142, 39, 148));
		fileLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		fileLabel.setBounds(220, 280, 329, 20);
		roundedPanel_1.add(fileLabel);
		
		JButton btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				String directory = System.getProperty("user.home");
				fileChooser = new JFileChooser(directory +"/Desktop");
				String serverLocationPath = "C:/Users/Ian/Desktop/Server/Downloads/";
				
			
				int userSelection = fileChooser.showSaveDialog(null);
				System.out.println("Done");
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					fileLabel.setText("File Selected: " + downloadsCombo.getSelectedItem().toString());
					System.out.println("Triggered");
				    File fileToSave = fileChooser.getSelectedFile();
				    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				    user.setCommand2(fileToSave.getAbsolutePath()+".pdf");
				    user.setCommand3(serverLocationPath + downloadsCombo.getSelectedItem().toString());
				   
				    System.out.println("The file will be saved as: " +user.getCommand2());
				    System.out.println("The file path and location is: " + user.getCommand3());
				    try {
						Controller.saveFile(user, uploadLabel);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnDownload.setForeground(Color.WHITE);
		btnDownload.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDownload.setBackground(new Color(142, 39, 148));
		btnDownload.setBounds(266, 197, 168, 32);
		roundedPanel_1.add(btnDownload);
		
		
		
		
		ImageIcon itinerary = new ImageIcon(this.getClass().getResource("/Itinerary.jpg"));
		ImageIcon regForm = new ImageIcon(this.getClass().getResource("/Reg Form.jpg"));
		ImageIcon guardian = new ImageIcon(this.getClass().getResource("/Guardian.jpg"));
		ImageIcon brochure = new ImageIcon(this.getClass().getResource("/Brochure2.jpg"));
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(597, 73, 76, 107);
		lblNewLabel_1.setIcon(guardian);
		roundedPanel_1.add(lblNewLabel_1);
		
		JLabel label_2 = new JLabel("New label");
		label_2.setBounds(597, 297, 76, 99);
		label_2.setIcon(regForm);
		roundedPanel_1.add(label_2);
		
		JLabel label_3 = new JLabel("New label");
		label_3.setBounds(52, 293, 76, 99);
		label_3.setIcon(itinerary);
		roundedPanel_1.add(label_3);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(52, 72, 84, 109);
		lblNewLabel_2.setIcon(brochure);
		roundedPanel_1.add(lblNewLabel_2);
		
		JLabel lblBrochure = new JLabel("Brochure 2015");
		lblBrochure.setForeground(new Color(142, 39, 148));
		lblBrochure.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBrochure.setBounds(23, 185, 143, 20);
		roundedPanel_1.add(lblBrochure);
		
		JLabel lblSampleItinerary = new JLabel("Sample Itinerary");
		lblSampleItinerary.setForeground(new Color(142, 39, 148));
		lblSampleItinerary.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSampleItinerary.setBounds(40, 404, 143, 20);
		roundedPanel_1.add(lblSampleItinerary);
		
		JLabel lblRegistrationForm = new JLabel("Registration Form");
		lblRegistrationForm.setForeground(new Color(142, 39, 148));
		lblRegistrationForm.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRegistrationForm.setBounds(580, 404, 143, 20);
		roundedPanel_1.add(lblRegistrationForm);
		
		JLabel lblGuardianshipServices = new JLabel("Guardianship Services");
		lblGuardianshipServices.setForeground(new Color(142, 39, 148));
		lblGuardianshipServices.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGuardianshipServices.setBounds(563, 185, 143, 20);
		roundedPanel_1.add(lblGuardianshipServices);
		
		
		
	
		
		RoundedPanel roundedPanelM = new RoundedPanel();
		roundedPanelM.setLayout(null);
		roundedPanelM.setForeground(new Color(142, 39, 148));
		roundedPanelM.setBackground(Color.WHITE);
		roundedPanelM.setBounds(5, 95, 245, 474);
		frame.getContentPane().add(roundedPanelM);
		
		JLabel label = new JLabel("Dashboard");
		label.setForeground(new Color(142, 39, 148));
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		label.setBounds(75, 11, 98, 17);
		roundedPanelM.add(label);
		
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
				frame.dispose();
			}
		});
		
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddApplicationAgents aaa = new AddApplicationAgents(user);
				frame.dispose();
			}
		});
		
		button_2.addActionListener(new ActionListener() {
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

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewApplications va = new ViewApplications(user);
				frame.dispose();
			}
		});

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnrolledStudentsViewAgents esv = new EnrolledStudentsViewAgents(user);
				frame.dispose();
			}
		});

		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DownloadsAgents da = new DownloadsAgents(user);
				frame.dispose();
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
	
    private CategoryDataset createDataset() {
        String row = "Row";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        dataset.addValue(8, row, "Performance"); // link to JDBC
        dataset.addValue(25, row, "Target");	 // link to JDBC
//        dataset.addValue(510, row, "C");
//        dataset.addValue(570, row, "D");
//        dataset.addValue(180, row, "E");
//        dataset.addValue(504, row, "F");
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