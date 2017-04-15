/**
 * This class represents the Forgotten Login view for all users. 
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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import com.sam.database.JDBC;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ForgottenLogin {

	// field variables
	private JFrame frame;



	// constructor
	public ForgottenLogin() {
		initialize();
		this.frame.setVisible(true);
	}

	// initialize method for frame creates its components and sets parameters
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 450, 404);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		// title label
		JLabel title = new JLabel("Forgotten Your Login Details?");
		title.setForeground(new Color(142, 39, 148));
		title.setFont(new Font("Tahoma", Font.BOLD, 13));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(78, 96, 274, 50);
		frame.getContentPane().add(title);
		
		// label to display logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(95, 11, 253, 93);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		logoLabel.setIcon(logo);
		frame.getContentPane().add(logoLabel);
		
		// labels to display message instruction content
		JLabel messageLabel1 = new JLabel("Please contact us to reset your login at");
		messageLabel1.setForeground(new Color(142, 39, 148));
		messageLabel1.setBounds(111, 143, 253, 50);
		frame.getContentPane().add(messageLabel1);
		JLabel messageLabel2 = new JLabel("ishields2@gmail.com");
		messageLabel2.setForeground(new Color(142, 39, 148));
		messageLabel2.setBounds(156, 169, 170, 50);
		frame.getContentPane().add(messageLabel2);
		
		JLabel lblEmailSentSuccessfully = new JLabel("Email sent successfully");
		lblEmailSentSuccessfully.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmailSentSuccessfully.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmailSentSuccessfully.setForeground(new Color(142, 39, 148));
		lblEmailSentSuccessfully.setBounds(33, 305, 356, 50);
		frame.getContentPane().add(lblEmailSentSuccessfully);
		lblEmailSentSuccessfully.setVisible(false);
		
		// back button returns to login screen
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBack.setBackground(new Color(142, 39, 148));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginFrame lf = new LoginFrame();
				frame.dispose();
			}
		});
		btnBack.setBounds(163, 271, 89, 23);
		frame.getContentPane().add(btnBack);
		
		
		
	}
}
