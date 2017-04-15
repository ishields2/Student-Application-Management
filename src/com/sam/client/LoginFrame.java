/**
 * This class represents the first frame that all users 
 * see. It should be started when the server is started 
 * to login to the program. 
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
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.UIManager;

import com.sam.server.Application;
import com.sam.server.Server;
import com.sam.server.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JPasswordField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class LoginFrame {

	// field variables 
	private JFrame frame;
	private JTextField usernameField;
	JLabel loginPrompt;
	private JPasswordField passwordField;
	
	
	// constructor for initial login frame
	public LoginFrame() {
		initialize();
		this.frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	}

		// initialise method adds components to frame and sets parameters
		private void initialize() {
		
		
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 468, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// label to display logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(112, 0, 253, 122);
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/Logo.jpg"));
		frame.getContentPane().setLayout(null);
		logoLabel.setIcon(logo);
		frame.getContentPane().add(logoLabel);
		
		// welcome label
		JLabel welcomeLabel = new JLabel("Welcome to SAM, please login to use the system");
		welcomeLabel.setBounds(23, 309, 408, 79);
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		welcomeLabel.setForeground(new Color(142, 39, 148));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(welcomeLabel);
		
		// login button and action performed method
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			if(Controller.Login(usernameField, passwordField, welcomeLabel) == true){
				frame.dispose();
			}
			}
		});
				

		loginButton.setBounds(130, 243, 89, 30);
		loginButton.setForeground(new Color(255, 255, 255));
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		loginButton.setBackground(new Color(142, 39, 148));
		frame.getContentPane().add(loginButton);

		// exit button
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(229, 243, 89, 30);
		exitButton.setForeground(new Color(255, 255, 255));
		exitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		exitButton.setBackground(new Color(142, 39, 148));
		frame.getContentPane().add(exitButton);
		
		// username text field
		usernameField = new JTextField();
		usernameField.setBounds(156, 144, 180, 23);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		usernameField.setBorder(new RoundedCornerBorder());
		
		// username label
		JLabel usernameLabel = new JLabel("Username : ");
		usernameLabel.setBounds(68, 128, 213, 50);
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		usernameLabel.setForeground(new Color(142, 39, 148));
		frame.getContentPane().add(usernameLabel);
		
		// password text field
		passwordField = new JPasswordField();
		passwordField.setBounds(156, 176, 180, 23);
		frame.getContentPane().add(passwordField);
		passwordField.setBorder(new RoundedCornerBorder());
		
		// password label
		JLabel passwordLabel = new JLabel("Password : ");
		passwordLabel.setBounds(68, 160, 80, 50);
		passwordLabel.setForeground(new Color(142, 39, 148));
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		frame.getContentPane().add(passwordLabel);
		
		// forgot login button
		JButton forgotLoginButton = new JButton("Forgotten Login Details");
		forgotLoginButton.setBounds(130, 284, 188, 30);
		forgotLoginButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		forgotLoginButton.setForeground(new Color(255, 255, 255));
		forgotLoginButton.setBackground(new Color(142, 39, 148));
		forgotLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ForgottenLogin fl1 = new ForgottenLogin();
				
			}
		});
		frame.getContentPane().add(forgotLoginButton);
	}
		
	
	// start point for system, launches login screen
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
						LoginFrame window = new LoginFrame();
						window.frame.setVisible(true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
	}
}
