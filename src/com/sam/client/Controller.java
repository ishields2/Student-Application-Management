/**
 * This is the Controller class. The role of this class is to handle
 * interactions between the client and the server. 
 * 
 * The methods of this class generally respond to calls from the client 
 * and connect to the server, receiving a response back from the server 
 * and then updating the client accordingly.
 * 
 * References: 
 * http://stackoverflow.com/questions/31337295/creating-jbuttons-dynamically-with-windowbuilder
 * http://javahonk.com/convert-pdf-to-byte-array/
 * http://toedter.com/jcalendar/
 * https://eclipse.org/windowbuilder/
 * https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/
 * http://www.oracle.com/technetwork/java/javamail/index.html
 * http://docs.oracle.com/javase/7/docs/api/
 * http://stackoverflow.com/questions/8193801/how-to-set-specific-window-frame-size-in-java-swing
 * https://github.com/suni619/P2PChatRoomApplication
 * http://stackoverflow.com/questions/1131116/pdf-to-byte-array-and-vice-versa
 * http://stackoverflow.com/questions/8237193/how-to-convert-currenttimemillis-to-a-date-in-java
 * http://stackoverflow.com/questions/13205060/calculating-difference-in-days-between-dates
 * http://www.mkyong.com/jdbc/how-do-connect-to-postgresql-with-jdbc-driver-java/
 * http://stackoverflow.com/questions/12951505/change-bar-color-in-jfreechart-bar-chart
 * http://www.jfree.org/phpBB2/viewtopic.php?t=20027
 * 
 * 
 * @author Ian Shields, 1506416
 * @version 07/09/2015
 */

package com.sam.client;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sam.server.Agent;
import com.sam.server.AgentNote;
import com.sam.server.Application;
import com.sam.server.Enquiry;
import com.sam.server.Notes;
import com.sam.server.StaffUser;
import com.sam.server.User;
import com.toedter.calendar.JDateChooser;

import org.apache.commons.lang3.*;

public class Controller {

	private static Socket socketConnection;
	private static ObjectOutputStream clientOut;
	private static ObjectInputStream clientIn;

	/**
	 * This method takes the username and password field from the 
	 * login frame and attempts to log users in. If the username and password fields
	 * have both been populated, a user object is created 
	 * and the username and password parameters set, before the user object
	 * is sent to the server. The method then receives a user object back from the 
	 * server and either generates the relevant view for the user, or advises the 
	 * user that their login details are incorrect, based on the response received 
	 * as determined through user type of the user object received back from the server.
	 * 
	 * @param - usernameField, password Field, welcomLabel - the username and password fields 
	 * and the welcome message which is changed to notify the user if login fails
	 * 
	 * @returns - a boolean result indication true if login is successful or false otherwise
	 */
	public static boolean Login(JTextField usernameField,
			JTextField passwordField, JLabel welcomeLabel) {
		// if both fields are empty, prompt user to enter login details
		if (usernameField.getText().equals("")
				&& passwordField.getText().equals("")) {
			welcomeLabel.setText("You have not entered a username or password");
			return false;
		}
		// if username field is empty, prompt user
		else if (usernameField.getText().equals("")) {
			welcomeLabel.setText("You have not entered a username");
			return false;
		}
		// if password field is empty, prompt user
		else if (passwordField.getText().equals("")) {
			welcomeLabel.setText("You have not entered a password");
			return false;
		}
		// otherwise, create a new user object based on the paramaters entered
		// by the user
		else {
			 
			User user = new User();
			user.setUsername(usernameField.getText());
			user.setPassword(passwordField.getText());
			System.out.println("Username submitted was: " + user.getUsername());
	
				try {
					socketConnection = new Socket("127.0.0.1", 5432);
				
				clientOut = new ObjectOutputStream(
						socketConnection.getOutputStream());
				clientIn = new ObjectInputStream(
						socketConnection.getInputStream());

				// set the user command to login for the server to interpret
				user.setCommand("login");

				// write the user object to the output stream to be received by
				// the server
				clientOut.writeObject(user);

				// wait for server response to receive the user object back from
				// server
					try {
						user = (User) clientIn.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					socketConnection.close();
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					// user.getType() is set by the server depending on whether
					// they are valid and which type of user they are
					System.out.println("Client read type as " + user.getType());

					// if user is located and is of type staff or admin create
					// appropriate home screen view
					// change this to the admin screen as well eventually
					if (user.getType().equals("staff")) {

						HomeScreen hs = new HomeScreen(user);
						return true;

					}

					// if user is located and is of type agent then create their
					// appropriate homescreen view
					else if (user.getType().equals("admin")) {

						HomeScreenAdmin hs = new HomeScreenAdmin(user);
						return true;
					}

					// if user is located and is of type agent then create their
					// appropriate homescreen view
					else if (user.getType().equals("agent")) {

						HomeScreenAgents hs = new HomeScreenAgents(user);
						return true;

					}
					// otherwise user was not located so details incorrect
					else {
						welcomeLabel
								.setText("Login details incorrect. Please retry.");
						return false;
				}

		}
	}

	/**
	 * This method takes an ArrayList of applications and returns the total
	 * number of those applications that have an application date within
	 * the past 7 days
	 * 
	 * @param - the ArrayList of applications to be checked
	 * @returns - an int corresponding to the number of applications with an
	 * application date within the past week.
	 */
	public static int getAppsThisWeek(ArrayList<Application> a) {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

		Calendar today = Calendar.getInstance();
		Calendar todayMinusOne = Calendar.getInstance();
		Calendar todayMinusTwo = Calendar.getInstance();
		Calendar todayMinusThree = Calendar.getInstance();
		Calendar todayMinusFour = Calendar.getInstance();
		Calendar todayMinusFive = Calendar.getInstance();
		Calendar todayMinusSix = Calendar.getInstance();
		Calendar todayMinusSeven = Calendar.getInstance();

		todayMinusOne.add(Calendar.DATE, -1);
		todayMinusTwo.add(Calendar.DATE, -2);
		todayMinusThree.add(Calendar.DATE, -3);
		todayMinusFour.add(Calendar.DATE, -4);
		todayMinusFive.add(Calendar.DATE, -5);
		todayMinusSix.add(Calendar.DATE, -6);
		todayMinusSeven.add(Calendar.DATE, -7);

		dateFormat.format(todayMinusOne.getTime());
		dateFormat.format(todayMinusTwo.getTime());
		dateFormat.format(todayMinusThree.getTime());
		dateFormat.format(todayMinusFour.getTime());
		dateFormat.format(todayMinusFive.getTime());
		dateFormat.format(todayMinusSix.getTime());
		dateFormat.format(todayMinusSeven.getTime());

		int b = 0;

		for (Application app : a) {

			if (app.getAppDate().equals(dateFormat.format(today.getTime()))
					|| app.getAppDate().equals(
							dateFormat.format(todayMinusOne.getTime()))
					|| app.getAppDate().equals(
							dateFormat.format(todayMinusTwo.getTime()))
					|| app.getAppDate().equals(
							dateFormat.format(todayMinusThree.getTime()))
					|| app.getAppDate().equals(
							dateFormat.format(todayMinusFour.getTime()))
					|| app.getAppDate().equals(
							dateFormat.format(todayMinusFive.getTime()))
					|| app.getAppDate().equals(
							dateFormat.format(todayMinusSix.getTime()))
					|| app.getAppDate().equals(
							dateFormat.format(todayMinusSeven.getTime()))) {

				b++;
			}
		}
		return b;
	}

	/**
	 * This method takes an ArrayList of enquiries and returns the total
	 * number of those enquiries that have an enquiry date within
	 * the past 7 days
	 * 
	 * @param - the ArrayList of enquiries to be checked
	 * @returns - an int corresponding to the number of enquiries with an
	 * enquiry date within the past week.
	 */
	public static int getEnqsThisWeek(ArrayList<Enquiry> e) {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

		Calendar today = Calendar.getInstance();
		Calendar todayMinusOne = Calendar.getInstance();
		Calendar todayMinusTwo = Calendar.getInstance();
		Calendar todayMinusThree = Calendar.getInstance();
		Calendar todayMinusFour = Calendar.getInstance();
		Calendar todayMinusFive = Calendar.getInstance();
		Calendar todayMinusSix = Calendar.getInstance();
		Calendar todayMinusSeven = Calendar.getInstance();

		todayMinusOne.add(Calendar.DATE, -1);
		todayMinusTwo.add(Calendar.DATE, -2);
		todayMinusThree.add(Calendar.DATE, -3);
		todayMinusFour.add(Calendar.DATE, -4);
		todayMinusFive.add(Calendar.DATE, -5);
		todayMinusSix.add(Calendar.DATE, -6);
		todayMinusSeven.add(Calendar.DATE, -7);

		dateFormat.format(todayMinusOne.getTime());
		dateFormat.format(todayMinusTwo.getTime());
		dateFormat.format(todayMinusThree.getTime());
		dateFormat.format(todayMinusFour.getTime());
		dateFormat.format(todayMinusFive.getTime());
		dateFormat.format(todayMinusSix.getTime());
		dateFormat.format(todayMinusSeven.getTime());

		int b = 0;

		for (Enquiry enq : e) {

			if (enq.getEnqDate().equals(dateFormat.format(today.getTime()))
					|| enq.getEnqDate().equals(
							dateFormat.format(todayMinusOne.getTime()))
					|| enq.getEnqDate().equals(
							dateFormat.format(todayMinusTwo.getTime()))
					|| enq.getEnqDate().equals(
							dateFormat.format(todayMinusThree.getTime()))
					|| enq.getEnqDate().equals(
							dateFormat.format(todayMinusFour.getTime()))
					|| enq.getEnqDate().equals(
							dateFormat.format(todayMinusFive.getTime()))
					|| enq.getEnqDate().equals(
							dateFormat.format(todayMinusSix.getTime()))
					|| enq.getEnqDate().equals(
							dateFormat.format(todayMinusSeven.getTime()))) {

				b++;
			}
		}
		return b;
	}

	/**
	 * This method takes an ArrayList of applications and returns the total
	 * number of those applications that have an application date within
	 * the past 7 days and have a status indicating a payment
	 * 
	 * @param - the ArrayList of applications to be checked
	 * @returns - an int corresponding to the number of applications with an
	 * application date within the past week and a status indicating payment.
	 */
	public static int getPaymentsThisWeek(ArrayList<Application> a) {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

		Calendar today = Calendar.getInstance();
		Calendar todayMinusOne = Calendar.getInstance();
		Calendar todayMinusTwo = Calendar.getInstance();
		Calendar todayMinusThree = Calendar.getInstance();
		Calendar todayMinusFour = Calendar.getInstance();
		Calendar todayMinusFive = Calendar.getInstance();
		Calendar todayMinusSix = Calendar.getInstance();
		Calendar todayMinusSeven = Calendar.getInstance();

		todayMinusOne.add(Calendar.DATE, -1);
		todayMinusTwo.add(Calendar.DATE, -2);
		todayMinusThree.add(Calendar.DATE, -3);
		todayMinusFour.add(Calendar.DATE, -4);
		todayMinusFive.add(Calendar.DATE, -5);
		todayMinusSix.add(Calendar.DATE, -6);
		todayMinusSeven.add(Calendar.DATE, -7);

		dateFormat.format(todayMinusOne.getTime());
		dateFormat.format(todayMinusTwo.getTime());
		dateFormat.format(todayMinusThree.getTime());
		dateFormat.format(todayMinusFour.getTime());
		dateFormat.format(todayMinusFive.getTime());
		dateFormat.format(todayMinusSix.getTime());
		dateFormat.format(todayMinusSeven.getTime());

		int b = 0;

		for (Application app : a) {

			if (app.getAppDate().equals(dateFormat.format(today.getTime()))
					&& app.getPaymentStatus().equals("Deposit Paid")
					|| app.getPaymentStatus().equals("Full Fee Paid")
					|| app.getAppStatus().equals("Deposit Received")
					|| app.getAppStatus().equals("Enrolled")) {
				b++;
			}

			else if (app.getAppDate().equals(
					dateFormat.format(todayMinusOne.getTime()))
					&& app.getPaymentStatus().equals("Deposit Paid")
					|| app.getPaymentStatus().equals("Full Fee Paid")
					|| app.getAppStatus().equals("Deposit Received")
					|| app.getAppStatus().equals("Enrolled")) {
				b++;
			} else if (app.getAppDate().equals(
					dateFormat.format(todayMinusTwo.getTime()))
					&& app.getPaymentStatus().equals("Deposit Paid")
					|| app.getPaymentStatus().equals("Full Fee Paid")
					|| app.getAppStatus().equals("Deposit Received")
					|| app.getAppStatus().equals("Enrolled")) {
				b++;
			} else if (app.getAppDate().equals(
					dateFormat.format(todayMinusThree.getTime()))
					&& app.getPaymentStatus().equals("Deposit Paid")
					|| app.getPaymentStatus().equals("Full Fee Paid")
					|| app.getAppStatus().equals("Deposit Received")
					|| app.getAppStatus().equals("Enrolled")) {
				b++;
			} else if (app.getAppDate().equals(
					dateFormat.format(todayMinusFour.getTime()))
					&& app.getPaymentStatus().equals("Deposit Paid")
					|| app.getPaymentStatus().equals("Full Fee Paid")
					|| app.getAppStatus().equals("Deposit Received")
					|| app.getAppStatus().equals("Enrolled")) {
				b++;
			} else if (app.getAppDate().equals(
					dateFormat.format(todayMinusFive.getTime()))
					&& app.getPaymentStatus().equals("Deposit Paid")
					|| app.getPaymentStatus().equals("Full Fee Paid")
					|| app.getAppStatus().equals("Deposit Received")
					|| app.getAppStatus().equals("Enrolled")) {
				b++;
			} else if (app.getAppDate().equals(
					dateFormat.format(todayMinusSix.getTime()))
					&& app.getPaymentStatus().equals("Deposit Paid")
					|| app.getPaymentStatus().equals("Full Fee Paid")
					|| app.getAppStatus().equals("Deposit Received")
					|| app.getAppStatus().equals("Enrolled")) {
				b++;
			} else if (app.getAppDate().equals(
					dateFormat.format(todayMinusSeven.getTime()))
					&& app.getPaymentStatus().equals("Deposit Paid")
					|| app.getPaymentStatus().equals("Full Fee Paid")
					|| app.getAppStatus().equals("Deposit Received")
					|| app.getAppStatus().equals("Enrolled")) {
				b++;
			}

		}
		return b;
	}

	/**
	 * This method takes an ArrayList of applications and the total number
	 * of countries that payment have been received for from the list of applications
	 * 
	 * To do this it find all of the applications that have a status indicating paid
	 * and then adds the country for each application to an ArrayList of Strings
	 * provided that the ArrayList does not already contain said country.
	 * 
	 * It then returns the size of the arraylist which corresponds to the payments
	 * from different countries.
	 * 
	 * @param - the ArrayList of applications to be checked
	 * @returns - an int corresponding to the number of countries recruited from
	 */
	public static int getNationsRecruited(ArrayList<Application> apps) {

		ArrayList<String> countries = new ArrayList<String>();

		String currentCountry = "";

		if (apps.size() == 0) {
			return 0;
		} else {
			for (Application a : apps) {

				if (a.getPaymentStatus().equals("Deposit Paid")
						|| a.getPaymentStatus().equals("Full Fee Paid")
						|| a.getAppStatus().equals("Deposit Received")
						|| a.getAppStatus().equals("Enrolled")
						&& a.getCourse().equals("Summer School 2015")) {

					if (countries.size() == 0) {
						countries.add(a.getCountry());

					} else {
						currentCountry = a.getCountry();
						if (!countries.contains(currentCountry)) {
							countries.add(currentCountry);

						}
					}

				}

			}
		}
		return countries.size();
	}

	/**
	 * This method takes an ArrayList of applications and returns the total
	 * number of applications from the list whose status indicates a payment
	 * 
	 * @param - the ArrayList of applications to be checked
	 * @returns - an int corresponding to the number of applications with statuses indicating payments
	 */
	public static int getTotalPayments(ArrayList<Application> a) {

		int b = 0;

		for (Application app : a) {

			if (app.getPaymentStatus().equals("Deposit Paid")
					|| app.getPaymentStatus().equals("Full Fee Paid")
					|| app.getAppStatus().equals("Deposit Received")
					|| app.getAppStatus().equals("Enrolled")
					&& app.getCourse().equals("Summer School 2015")) {
				b++;
			}
		}
		return b;
	}

	
	/**
	 * This method takes two String paramaters of total price and 
	 * number of weeks for an enquiry, parses them into doubles, and works out the 
	 * weekly price, which it returns as a String
	 * 
	 * @param - totalPrice, noWeeks
	 * @returns - the weekly price as a String
	 */
	public static String computeWeeklyPriceEnq(String totalPrice, String noWeeks) {
		double a = Integer.parseInt(totalPrice);
		double b = Integer.parseInt(noWeeks);
		String temp = a / b + "";
		int index = temp.indexOf(".");

		String temp2 = temp.substring(0, (index + 2));
		return temp2;

	}

	/**
	 * This method takes two long values, representing dates and returns 
	 * the number of days between those dates. It is used to compute
	 * the duration in days once the user has selected a start and end date
	 * 
	 * @param - long d1, d2 - the long values representing dates
	 * @returns - the weekly price as a String
	 */
	public static int getDuration(long d1, long d2) {
		int days = (int) ((d2 - d1) / (1000 * 60 * 60 * 24));
		return days;
	}

	/**
	 * This method takes a date and returns it in the format
	 * DD/MM/YY
	 * 
	 * @param - date
	 * @returns - the formatted date
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		String dateS = format.format(date);
		return dateS;
	}

	/**
	 * This method takes two dates in the format of long values, as well as 
	 * the total price for a course, and computes the weekly price 
	 * based on these values
	 * 
	 * @param - long d1, d2 - the dates, totalPrice - the price
	 * @returns - the weekly price as a String
	 */
	public static String computeWeeklyPriceApp(long d1, long d2, long totalPrice) {

		double days = (double) ((d2 - d1) / (1000 * 60 * 60 * 24));

		double dailyPrice = totalPrice / days;

		double weeklyPrice = dailyPrice * 7;

		String result = new DecimalFormat("##.##").format(weeklyPrice);

		return result;
	}

	/**
	 * This method takes a long value representing a date and returns the date
	 * value in format DD/MM/YY
	 * 
	 * @param - long date
	 * @returns - the formatted date
	 */
	public static String formatDateLong(long date) {

		String dateFormat = "dd/MM/yy";

		SimpleDateFormat format = new SimpleDateFormat(dateFormat);

		return format.format(date);

	}
	/**
	 * This method takes a user, application and a series of swing components
	 * as paramaters and resets all of the swing component fields to their original values
	 * 
	 * It is used when a user wants to undo any changes they have made when updating an agent
	 * 
	 * @param - user, app, swing components for an application
	 */
	public static void resetAppFields(User user, Application app,
			JTextField firstNameField, JTextField totalPriceField,
			JTextField passportField, JComboBox countryCombo,
			JComboBox nationalityCombo, JComboBox agentCombo,
			JComboBox genderCombo, JComboBox statusCombo,
			JComboBox payStatusCombo, JComboBox salesAdvisorCombo,
			JTextField lastNameField, JTextField sourceField,
			JComboBox visaStatusCombo, JComboBox campusCombo,
			JDateChooser arrivalDateChooser, JDateChooser departureDateChooser,
			JDateChooser dobDateChooser, JDateChooser lastContactedDateChooser) {

				firstNameField.setText(app.getFirstName());
				totalPriceField.setText(app.getTotalPrice());
				passportField.setText(app.getPassportNo());
				countryCombo.setModel(new DefaultComboBoxModel(new String[] { "Select",
				"Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
				"Antigua and Barbuda", "Argentina", "Armenia", "Australia",
				"Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh",
				"Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan",
				"Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil",
				"Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde",
				"Cambodia", "Cameroon", "Canada", "Central African Republic",
				"Chad", "Chile", "China", "Colombia", "Comoros",
				"Congo, Republic of the", "Congo, Democratic Republic of the",
				"Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus",
				"Czech Republic", "Denmark", "Djibouti", "Dominica",
				"Dominican Republic", "Ecuador", "Egypt", "El Salvador",
				"Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji",
				"Finland", "France", "Gabon", "Gambia", "Georgia", "Germany",
				"Ghana", "Greece", "Grenada", "Guatemala", "Guinea",
				"Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary",
				"Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland",
				"Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan",
				"Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos",
				"Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
				"Liechtenstein", "Lithuania", "Luxembourg", "Macedonia",
				"Madagascar", "Malawi", "Malaysia", "Maldives", "Mali",
				"Malta", "Marshall Islands", "Mauritania", "Mauritius",
				"Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia",
				"Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia",
				"Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua",
				"Niger", "Nigeria", "North Korea", "Norway", "Oman",
				"Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea",
				"Paraguay", "Peru", "Philippines", "Poland", "Portugal",
				"Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis",
				"St. Lucia", "St. Vincent and The Grenadines", "Samoa",
				"San Marino", "Sao Tome and Principe", "Saudi Arabia",
				"Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore",
				"Slovakia", "Slovenia", "Solomon Islands", "Somalia",
				"South Africa", "South Korea", "South Sudan", "Spain",
				"Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden",
				"Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania",
				"Thailand", "Timor-Leste", "Togo", "Tonga",
				"Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan",
				"Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK ",
				"USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ",
				"Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe" }));
				countryCombo.setSelectedItem(app.getCountry());
				nationalityCombo.setModel(new DefaultComboBoxModel(new String[] {
				"Select", "Afghanistan", "Albania", "Algeria", "Andorra",
				"Angola", "Antigua and Barbuda", "Argentina", "Armenia",
				"Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain",
				"Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
				"Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina",
				"Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso",
				"Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada",
				"Central African Republic", "Chad", "Chile", "China",
				"Colombia", "Comoros", "Congo, Republic of the",
				"Congo, Democratic Republic of the", "Costa Rica",
				"Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic",
				"Denmark", "Djibouti", "Dominica", "Dominican Republic",
				"Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",
				"Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France",
				"Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece",
				"Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",
				"Haiti", "Honduras", "Hungary", "Iceland", "India",
				"Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy",
				"Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya",
				"Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia",
				"Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",
				"Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi",
				"Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
				"Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova",
				"Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique",
				"Myanmar ", "Namibia", "Nauru", "Nepal", "Netherlands",
				"New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea",
				"Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama",
				"Papua New Guinea", "Paraguay", "Peru", "Philippines",
				"Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda",
				"St. Kitts and Nevis", "St. Lucia",
				"St. Vincent and The Grenadines", "Samoa", "San Marino",
				"Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia",
				"Seychelles", "Sierra Leone", "Singapore", "Slovakia",
				"Slovenia", "Solomon Islands", "Somalia", "South Africa",
				"South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan",
				"Suriname", "Swaziland", "Sweden", "Switzerland", "Syria",
				"Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste",
				"Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
				"Turkmenistan", "Tuvalu", "Uganda", "Ukraine",
				"United Arab Emirates", "UK ", "USA ", "Uruguay", "Uzbekistan",
				"Vanuatu", "Vatican City ", "Venezuela", "Vietnam", "Yemen",
				"Zambia", "Zimbabwe" }));
				nationalityCombo.setSelectedItem(app.getNationality());

				String[] agents = new String[user.getAgentsList().size()];
				for (int i = 0; i < user.getAgentsList().size(); i++) {
					agents[i] = user.getAgentsList().get(i).getCompanyName();
				}
		
				agentCombo.setModel(new DefaultComboBoxModel(agents));
				agentCombo.setSelectedItem(app.getAgent());
				genderCombo.setModel(new DefaultComboBoxModel(new String[] { "M", "F" }));
				genderCombo.setSelectedItem(app.getGender());
				statusCombo.setModel(new DefaultComboBoxModel(
				new String[] { "Select", "New", "Received", "Deposit Received", "Cancelled", "Enrolled" }));
				statusCombo.setSelectedItem(app.getAppStatus());
				payStatusCombo.setModel(new DefaultComboBoxModel(new String[] {
				"Select", "Not Paid", "Deposit Paid", "Full Fee Paid" }));
				payStatusCombo.setSelectedItem(app.getPaymentStatus());
				salesAdvisorCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
				salesAdvisorCombo.setSelectedItem(app.getSalesAdvisor());
				lastNameField.setText(app.getLastName());
				sourceField.setText(app.getSource());
				visaStatusCombo.setModel(new DefaultComboBoxModel(new String[] {
				"Select", "No Visa Required", "Not Applied", "Applied",
				"Visa Granted", "Visa Refused" }));
				visaStatusCombo.setSelectedItem(app.getVisaStatus());
				campusCombo.setModel(new DefaultComboBoxModel(user.getCampuses()));
				campusCombo.setSelectedItem(app.getCampus());

				Date arrival;
				Date departure;
				Date dob;
				Date lastCon;
		
				try {
					arrival = new SimpleDateFormat("dd/MM/yy").parse(app
							.getArrivalDate());
					departure = new SimpleDateFormat("dd/MM/yy").parse(app
							.getDepartureDate());
					dob = new SimpleDateFormat("dd/MM/yy").parse(app.getDob());
					lastCon = new SimpleDateFormat("dd/MM/yy").parse(app
							.getLastContacted());
					arrivalDateChooser.setDate(arrival);
					departureDateChooser.setDate(departure);
					dobDateChooser.setDate(dob);
					lastContactedDateChooser.setDate(lastCon);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	}

	/**
	 * This method takes a user, agent and a series of swing components
	 * as paramaters and resets all of the swing component fields to their original values
	 * 
	 * It is used when a user wants to undo any changes they have made when updating an agent
	 * 
	 * @param - user, agent, swing components for an agent
	 */
	public static void resetAgentFields(Agent agent, User user,
			JTextField firstNameField, JTextField lastNameField,
			JTextField companyfield, JComboBox countryCombo,
			JComboBox statusCombo, JTextArea addressField,
			JTextField phoneField, JTextField emailField,
			JDateChooser lastContactedCombo, JLabel agentLabel,
			JComboBox accManCombo) {

				accManCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
				countryCombo.setModel(new DefaultComboBoxModel(new String[] { "Select",
				"Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
				"Antigua and Barbuda", "Argentina", "Armenia", "Australia",
				"Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh",
				"Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan",
				"Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil",
				"Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde",
				"Cambodia", "Cameroon", "Canada", "Central African Republic",
				"Chad", "Chile", "China", "Colombia", "Comoros",
				"Congo, Republic of the", "Congo, Democratic Republic of the",
				"Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus",
				"Czech Republic", "Denmark", "Djibouti", "Dominica",
				"Dominican Republic", "Ecuador", "Egypt", "El Salvador",
				"Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji",
				"Finland", "France", "Gabon", "Gambia", "Georgia", "Germany",
				"Ghana", "Greece", "Grenada", "Guatemala", "Guinea",
				"Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary",
				"Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland",
				"Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan",
				"Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos",
				"Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
				"Liechtenstein", "Lithuania", "Luxembourg", "Macedonia",
				"Madagascar", "Malawi", "Malaysia", "Maldives", "Mali",
				"Malta", "Marshall Islands", "Mauritania", "Mauritius",
				"Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia",
				"Montenegro", "Morocco", "Mozambique", "Myanmar ", "Namibia",
				"Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua",
				"Niger", "Nigeria", "North Korea", "Norway", "Oman",
				"Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea",
				"Paraguay", "Peru", "Philippines", "Poland", "Portugal",
				"Qatar", "Romania", "Russia", "Rwanda", "St. Kitts and Nevis",
				"St. Lucia", "St. Vincent and The Grenadines", "Samoa",
				"San Marino", "Sao Tome and Principe", "Saudi Arabia",
				"Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore",
				"Slovakia", "Slovenia", "Solomon Islands", "Somalia",
				"South Africa", "South Korea", "South Sudan", "Spain",
				"Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden",
				"Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania",
				"Thailand", "Timor-Leste", "Togo", "Tonga",
				"Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan",
				"Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "UK ",
				"USA ", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City ",
				"Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe" }));
				statusCombo.setModel(new DefaultComboBoxModel(new String[] { "Active",
				"Signed", "Expected", "Cold", "Newly", "Contacted", "Lost" }));
		
				firstNameField.setText(agent.getFirstName());
				lastNameField.setText(agent.getLastName());
				agentLabel.setText("Agent: " + agent.getCompanyName());
				companyfield.setText(agent.getCompanyName());
				countryCombo.setSelectedItem(agent.getCountry());
				statusCombo.setSelectedItem(agent.getStatus());
				addressField.setText(agent.getAddress());
				phoneField.setText(agent.getPhone());
				emailField.setText(agent.getEmail());
				accManCombo.setSelectedItem(agent.getAccountManager());

		Date date3;
		try {
			date3 = new SimpleDateFormat("dd/MM/yy").parse(agent
					.getLast_contacted());
			lastContactedCombo.setDate(date3);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * This method takes a user, enquiry and a series of swing components
	 * as paramaters and resets all of the swing component fields to their original values
	 * 
	 * It is used when a user wants to undo any changes they have made when updating an enquiry
	 * 
	 * @param - user, agent, swing components for an agent
	 */
	public static void resetEnquiryFields(Enquiry enq, User user,
			JTextField firstNameField, JTextField totalPriceField,
			JComboBox countryCombo, JTextField lastNameField,
			JTextField sourceField, JComboBox agentCombo,
			JComboBox statusCombo, JComboBox salesAdvisorCombo,
			JLabel weeklyPriceLabel, JComboBox campusCombo,
			JTextField noWeeksField, JTextField phoneField,
			JTextField emailField, JComboBox courseCombo,
			JDateChooser lastContactedDateChooser) {

			firstNameField.setText(enq.getfName());
			totalPriceField.setText(enq.getTotalPrice());
			countryCombo.setSelectedItem(enq.getCountry());
			lastNameField.setText(enq.getlName());
			sourceField.setText(enq.getSource());
			String[] agents = new String[user.getAgentsList().size()];
			for (int i = 0; i < user.getAgentsList().size(); i++) {
				agents[i] = user.getAgentsList().get(i).getCompanyName();
				}
			agentCombo.setModel(new DefaultComboBoxModel(agents));
			statusCombo.setModel(new DefaultComboBoxModel(new String[] { "Select","A", "B", "C", "D", "E" }));
			salesAdvisorCombo.setModel(new DefaultComboBoxModel(user.getStaffMembers()));
			weeklyPriceLabel.setText(enq.getWeeklyPrice());
			countryCombo.setSelectedItem(enq.getCountry());
			agentCombo.setSelectedItem(enq.getAgent());
			statusCombo.setSelectedItem(enq.getStatus());
			salesAdvisorCombo.setSelectedItem(enq.getStaffMember());
			campusCombo.setSelectedItem(enq.getCampus());
			noWeeksField.setText(enq.getNoWeeks());
			phoneField.setText(enq.getPhone());
			emailField.setText(enq.getEmail());
			courseCombo.setSelectedItem(enq.getCourse());
			Date date3;
	
			try {
				date3 = new SimpleDateFormat("dd/MM/yy").parse(enq
						.getLastContacted());
				lastContactedDateChooser.setDate(date3);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
			campusCombo.setModel(new DefaultComboBoxModel(user.getCampuses()));
			campusCombo.setSelectedItem(enq.getCampus());
			noWeeksField.setText(enq.getNoWeeks());

	}

	/**
	 * This method takes a series of swing components for an application and dynamically 
	 * sets the weekly price and duration fields on the GUI any time 
	 * one of the other components affecting this is changed. 
	 * 
	 * This done by adding property change listeners to those such components
	 * and recalculating and setting the fields when there is a change
	 * 
	 * @param - swing components affecting the weekly price and duration, 
	 * and the weekly price and duration labels themselves 
	 */
	public static void autoCalculateAppWeeklyPrice(JTextField totalPriceField,
			JDateChooser arrivalDateField, JDateChooser depDateCombo,
			JLabel lblPrice, JLabel lblDuration, JLabel lblDuration2) {

		totalPriceField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getSource().equals(totalPriceField)) {

					try {
						lblPrice.setText(""
								+ Controller.computeWeeklyPriceApp(
										arrivalDateField.getDate().getTime(),
										depDateCombo.getDate().getTime(),
										Integer.parseInt(totalPriceField
												.getText())));
					} catch (Exception ex) {

					}
				}

			}
		});

		arrivalDateField
				.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						if (arrivalDateField.getJCalendar() != null) {

							try {
								lblPrice.setText(Controller.computeWeeklyPriceApp(
										arrivalDateField.getDate().getTime(),
										depDateCombo.getDate().getTime(),
										Integer.parseInt(totalPriceField
												.getText())));
								lblDuration.setText(""
										+ Controller.getDuration(
												arrivalDateField.getDate()
														.getTime(),
												depDateCombo.getDate()
														.getTime()));
							} catch (Exception ex) {

							}

						}
					}
				});

		depDateCombo.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {

				if (arrivalDateField.getJCalendar() != null) {

					try {
						lblPrice.setText(Controller.computeWeeklyPriceApp(
								arrivalDateField.getDate().getTime(),
								depDateCombo.getDate().getTime(),
								Integer.parseInt(totalPriceField.getText())));
						lblDuration2.setText(""
								+ Controller.getDuration(arrivalDateField
										.getDate().getTime(), depDateCombo
										.getDate().getTime()));
					} catch (Exception ex) {

					}

				}
			}
		});

	}

	/**
	 * This method takes a series of swing components for an application and dynamically 
	 * sets the weekly price and duration fields on the GUI any time 
	 * one of the other components affecting this is changed. 
	 * 
	 * This done by adding property change listeners to those such components
	 * and recalculating and setting the fields when there is a change
	 * 
	 * @param - swing components affecting the weekly price and duration, 
	 * and the weekly price and duration labels themselves 
	 */
	public static void autoCalculateAppWeeklyPrice2(JTextField totalPriceField,
			JDateChooser arrivalDateChooser, JDateChooser departureDateChooser,
			JLabel weeklyPriceLabel, JLabel durationLabel) {

		totalPriceField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getSource().equals(totalPriceField)) {

					try {
						weeklyPriceLabel.setText(Controller
								.computeWeeklyPriceApp(arrivalDateChooser
										.getDate().getTime(),
										departureDateChooser.getDate()
												.getTime(), Integer
												.parseInt(totalPriceField
														.getText()) * 10));
						System.out.println(Integer.parseInt(totalPriceField
								.getText()));
					} catch (Exception ex) {

					}
				}

			}
		});

		arrivalDateChooser
				.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						if (arrivalDateChooser.getJCalendar() != null) {

							try {
								weeklyPriceLabel.setText(Controller.computeWeeklyPriceApp(
										arrivalDateChooser.getDate().getTime(),
										departureDateChooser.getDate()
												.getTime(), Integer
												.parseInt(totalPriceField
														.getText())));
								durationLabel.setText(Controller.getDuration(
										arrivalDateChooser.getDate().getTime(),
										departureDateChooser.getDate()
												.getTime())
										+ " Days");
							} catch (Exception ex) {

							}

						}
					}
				});

		departureDateChooser
				.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {

						if (arrivalDateChooser.getJCalendar() != null) {

							try {
								weeklyPriceLabel.setText(Controller.computeWeeklyPriceApp(
										arrivalDateChooser.getDate().getTime(),
										departureDateChooser.getDate()
												.getTime(), Integer
												.parseInt(totalPriceField
														.getText())));
								durationLabel.setText(Controller.getDuration(
										arrivalDateChooser.getDate().getTime(),
										departureDateChooser.getDate()
												.getTime())
										+ " Days");
							} catch (Exception ex) {

							}

						}
					}
				});

	}

	/**
	 * This method takes a series of swing components for an enquiry and dynamically 
	 * sets the weekly price and duration fields on the GUI any time 
	 * one of the other components affecting this is changed. 
	 * 
	 * This done by adding documents listeners to those such components
	 * and recalculating and setting the fields when there is a change
	 * 
	 * @param - swing components affecting the weekly price and duration, 
	 * and the weekly price and duration labels themselves 
	 */
	public static void autoCalculateEnqWeeklyPrice(JTextField totalPriceField,
			JTextField noWeeksField, JLabel weeklyPrice2) {

		totalPriceField.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						warn();
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						warn();
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						warn();
					}

					public void warn() {
						if (totalPriceField.getText() != ""
								&& noWeeksField.getText() != "") {
							try {
								weeklyPrice2.setText(Controller
										.computeWeeklyPriceEnq(
												totalPriceField.getText(),
												noWeeksField.getText()));
							} catch (NumberFormatException e) {
								e.printStackTrace();
								System.out
										.println("Exception caught - input String was empty");
							}
						}
					}
				});

		noWeeksField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				if (totalPriceField.getText() != ""
						&& noWeeksField.getText() != "") {
					try {
						weeklyPrice2.setText(Controller.computeWeeklyPriceEnq(
								totalPriceField.getText(),
								noWeeksField.getText()));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	/**
	 * This method takes a series of swing components for an application 
	 * to be added, firstly checking if all the fields are populated, 
	 * and updating the relevant component to display the relevant message 
	 * to the user if they are not. If all fields are populated, an new 
	 * application object is created, set as the users applicatioToBeAdded
	 * parameter, and the user object is sent to the server. 
	 * 
	 * The method then receives a user object back from the server, and 
	 * reads the user command to determine if the transaction was successful. 
	 * 
	 * If it was, then the relevant view is generated, if not then a message is 
	 * provided to the user informing them of this. 
	 * 
	 * @param - swing components representing the different parameters of an application
	 */
	public static void addApplication(JFrame frame, User user,
			JTextField firstNameField, JLabel warningLabel,
			JTextField totalPriceField, JTextField passportField,
			JTextField lastNameField, JComboBox countryCombo,
			JComboBox nationCombo, JComboBox agentCombo, JComboBox genderCombo,
			JComboBox statusCombo, JComboBox payStatusCombo,
			JComboBox salesAdvisorCombo, JTextField sourceField,
			JDateChooser arrivalDateField, JDateChooser depDateCombo,
			JDateChooser dobCombo, JDateChooser lastContactedCombo,
			JComboBox visaStatusCombo, JComboBox campusCombo, JLabel lblPrice,
			JTextField emailField, JTextField phoneField,
			JComboBox courseCombo, JLabel lblDuration2, JTextArea adressBox) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					if (firstNameField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No first name entered.");
						warningLabel.setVisible(true);

					} else if (totalPriceField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No total price entered.");
						warningLabel.setVisible(true);
					} else if (passportField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No passport number entered.");
						warningLabel.setVisible(true);
					} else if (lastNameField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No last name entered.");
						warningLabel.setVisible(true);
					} else if (countryCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No country selected.");
						warningLabel.setVisible(true);
					} else if (nationCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No nationality selected.");
						warningLabel.setVisible(true);
					} else if (agentCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No agent selected.");
						warningLabel.setVisible(true);
					} else if (genderCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No gender selected.");
						warningLabel.setVisible(true);
					} else if (statusCombo.getSelectedItem().equals("select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No status selected.");
						warningLabel.setVisible(true);
					} else if (payStatusCombo.getSelectedItem()
							.equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No pay status selected.");
						warningLabel.setVisible(true);
					} else if (salesAdvisorCombo.getSelectedItem().equals(
							"Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No sales advisor selected.");
						warningLabel.setVisible(true);
					} else if (totalPriceField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No total price entered.");
						warningLabel.setVisible(true);
					} else if (sourceField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No source entered.");
						warningLabel.setVisible(true);
					} else if (arrivalDateField.getDate() == null) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No arrival date selected.");
						warningLabel.setVisible(true);
					} else if (depDateCombo.getDate() == (null)) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No departure date selected.");
						warningLabel.setVisible(true);
					} else if (dobCombo.getDate() == (null)) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No DOB selected.");
						warningLabel.setVisible(true);
					} else if (lastContactedCombo.getDate() == (null)) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No last contacted date selected.");
						warningLabel.setVisible(true);
					} else if (visaStatusCombo.getSelectedItem().equals(
							"Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No visa status selected.");
						warningLabel.setVisible(true);
					}

					else if (campusCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No campus selected.");
						warningLabel.setVisible(true);
					} else if (emailField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No email entered.");
						warningLabel.setVisible(true);
					} else if (phoneField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No phone number entered.");
						warningLabel.setVisible(true);
					} else if (adressBox.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No address entered.");
						warningLabel.setVisible(true);
					} else if (courseCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No course selected.");
						warningLabel.setVisible(true);
					}

					else {

						SimpleDateFormat format = new SimpleDateFormat(
								"dd/MM/yy");
						String currentDate = format.format(new Date(System
								.currentTimeMillis()));

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());

						Application app = new Application(0, firstNameField
								.getText(), lastNameField.getText(), agentCombo
								.getSelectedItem().toString(), payStatusCombo
								.getSelectedItem().toString(), visaStatusCombo
								.getSelectedItem().toString(), nationCombo
								.getSelectedItem().toString(), countryCombo
								.getSelectedItem().toString(), genderCombo
								.getSelectedItem().toString(), passportField
								.getText(), currentDate, campusCombo
								.getSelectedItem().toString(), statusCombo
								.getSelectedItem().toString(),
								salesAdvisorCombo.getSelectedItem().toString(),
								courseCombo.getSelectedItem().toString(),
								adressBox.getText(), Controller
										.formatDate(lastContactedCombo
												.getDate()),
								Controller.formatDate(arrivalDateField
										.getDate()), Controller
										.formatDate(depDateCombo.getDate()),
								Controller.formatDate(dobCombo.getDate()),
								phoneField.getText(), emailField.getText(),
								sourceField.getText(), lblPrice.getText(),
								totalPriceField.getText(), lblDuration2
										.getText(), null);
						user.setApplicationToBeAdded(app);
						user.setCommand("addapp");
						clientOut.writeObject(user);

						User user2 = (User) clientIn.readObject();
						socketConnection.close();

						if (user2.getCommand().equals("success")) {
							System.out.println(user2.getApplicationToBeAdded()
									.getUid() + "");
							user.setCommand2(user2.getApplicationToBeAdded()
									.getUid() + "");
							System.out.println(user.getCommand2());

							if (!user.getCommand3().equals("false")) {
								Controller.uploadFile(user);
							}
							user.getApplicationToBeAdded().setUid(
									user2.getApplicationToBeAdded().getUid());
							user.getUserApplications().add(0,
									user.getApplicationToBeAdded());
							ViewApplications va = new ViewApplications(user);
							frame.dispose();
						}

						else {
							warningLabel
									.setText("Add application failed. Please check fields and try again.");
						}

					}
				} catch (Exception ex) {

				}
			}
		});
	}

	/**
	 * This method takes a series of swing components for an application 
	 * to be added, firstly checking if all the fields are populated, 
	 * and updating the relevant component to display the relevant message 
	 * to the user if they are not. If all fields are populated, an new 
	 * application object is created, set as the users applicatioToBeAdded
	 * parameter, and the user object is sent to the server. 
	 * 
	 * The method then receives a user object back from the server, and 
	 * reads the user command to determine if the transaction was successful. 
	 * 
	 * If it was, then the relevant view is generated, if not then a message is 
	 * provided to the user informing them of this. 
	 * 
	 * This is method is used when an agent wants to add an application, as there 
	 * are less fields to select.
	 * 
	 * @param - swing components representing the different parameters of an application
	 */
	public static void addApplicationAgent(JFrame frame, User user,
			JTextField firstNameField, JLabel warningLabel,
			JTextField passportField, JTextField lastNameField,
			JComboBox countryCombo, JComboBox nationCombo,
			JComboBox genderCombo, JDateChooser arrivalDateField,
			JDateChooser depDateCombo, JDateChooser dobCombo,
			JComboBox campusCombo, JTextField emailField,
			JTextField phoneField, JComboBox courseCombo, JLabel lblDuration2,
			JTextArea adressBox) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					if (firstNameField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No first name entered.");
						warningLabel.setVisible(true);
					}

					else if (passportField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No passport number entered.");
						warningLabel.setVisible(true);
					} else if (lastNameField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No last name entered.");
						warningLabel.setVisible(true);
					} else if (countryCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No country selected.");
						warningLabel.setVisible(true);
					} else if (nationCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No nationality selected.");
						warningLabel.setVisible(true);
					}

					else if (genderCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No gender selected.");
						warningLabel.setVisible(true);
					}

					else if (arrivalDateField.getDate() == null) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No arrival date selected.");
						warningLabel.setVisible(true);
					} else if (depDateCombo.getDate() == (null)) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No departure date selected.");
						warningLabel.setVisible(true);
					} else if (dobCombo.getDate() == (null)) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No DOB selected.");
						warningLabel.setVisible(true);
					}

					else if (campusCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No campus selected.");
						warningLabel.setVisible(true);
					} else if (emailField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No email entered.");
						warningLabel.setVisible(true);
					} else if (phoneField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No phone number entered.");
						warningLabel.setVisible(true);
					} else if (adressBox.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No address entered.");
						warningLabel.setVisible(true);
					} else if (courseCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No course selected.");
						warningLabel.setVisible(true);
					}

					else {
						System.out.println("Else ACTIVATED");
						SimpleDateFormat format = new SimpleDateFormat(
								"dd/MM/yy");
						String currentDate = format.format(new Date(System
								.currentTimeMillis()));
						System.out.println(currentDate);

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());

						System.out.println("Streams established");
						Application app = new Application(0, firstNameField
								.getText(), lastNameField.getText(), user
								.getUsername(), "Not Paid", "-", nationCombo
								.getSelectedItem().toString(), countryCombo
								.getSelectedItem().toString(), genderCombo
								.getSelectedItem().toString(), passportField
								.getText(), currentDate, campusCombo
								.getSelectedItem().toString(), "New", user
								.getStaffMember(), courseCombo
								.getSelectedItem().toString(), adressBox
								.getText(), currentDate, Controller
								.formatDate(arrivalDateField.getDate()),
								Controller.formatDate(depDateCombo.getDate()),
								Controller.formatDate(dobCombo.getDate()),
								phoneField.getText(), emailField.getText(),
								user.getUsername(), "0", "0", "0", null);
						user.setApplicationToBeAdded(app);
						user.setCommand("addapp");
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						if (user2.getCommand().equals("success")) {
							user.setCommand2(user2.getApplicationToBeAdded()
									.getUid() + "");
							if (!user.getCommand3().equals("false")) {
								Controller.uploadFile(user);
							}

							user.getApplicationToBeAdded().setUid(
									user2.getApplicationToBeAdded().getUid());
							user.getUserApplications().add(0,
									user.getApplicationToBeAdded());
							ViewApplications va = new ViewApplications(user);
							frame.dispose();
						}

						else {
							warningLabel
									.setText("Add application failed. Please check fields and try again.");
						}

					}
				} catch (Exception ex) {

				}
			}
		});
	}

	/**
	 * This method updates the GUI to display the next five applications to the
	 * user. An ArrayList of applications is passed into the method, representing a users 
	 * applications, as well an int value i, acting as indexes for keeping track of which five 
	 * applications out of the arraylist are being viewed. 
	 * 
	 * The method takes the first application from position i, and creates a JButton for the application, 
	 * if indeed there is an application to take. It then populates the display fields on the JButton
	 * with the relevant data, before setting the button as visible to the user. If there is no application
	 * left to display, it means that we are at the end of the list of applications and it only displays 
	 * the JButtons for the applications available. int j is used to keep track of the applications
	 * being shown out of the total nummber of applications. 
	 * 
	 * @param - arraylist of applications, the swing components the five applications currently displayed on screen
	 */
	public static void nextApps(ArrayList<Application> apps,
			JButton app1Button, JButton app2Button, JButton app3Button,
			JButton app4Button, JButton app5Button, JLabel lblName,
			JLabel lblUid, JLabel lblAgent, JLabel lblStaff, JLabel lblStatus,
			JLabel lblLastUpdated, JLabel name_2, JLabel uid2, JLabel agent2,
			JLabel staff2, JLabel status2, JLabel lastUpdated2, JLabel name3,
			JLabel uid3, JLabel agent3, JLabel staff3, JLabel status3,
			JLabel lastUpdated3, JLabel name4, JLabel uid4, JLabel agent4,
			JLabel staff4, JLabel status4, JLabel lastUpdated4, JLabel name5,
			JLabel uid5, JLabel agent5, JLabel staff5, JLabel status5,
			JLabel lastUpdated5, JLabel lblOf, int i, int j) {

		// set all app buttons to not visible because we only want to see the
		// ones that have applications related to them
		app1Button.setVisible(false);
		app2Button.setVisible(false);
		app3Button.setVisible(false);
		app4Button.setVisible(false);
		app5Button.setVisible(false);

		// if there is another application and we are not at the end of the
		// appsList
		if (apps.iterator().hasNext() && i < apps.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			app1Button.setVisible(true);
			lblName.setText("Name: " + apps.get(i).getFirstName() + " "
					+ apps.get(i).getLastName());
			lblUid.setText("UID: " + apps.get(i).getUid());
			lblAgent.setText("Agent: " + " " + apps.get(i).getAgent());
			lblStaff.setText("Staff: " + apps.get(i).getSalesAdvisor());
			lblStatus.setText("Status: " + apps.get(i).getAppStatus());
			lblLastUpdated.setText("Last Updated: "
					+ apps.get(i).getLastContacted());
			lblOf.setText(j + " - " + i + 1 + "of" + " " + apps.size());
			i++;
			if (i == apps.size()) {
				lblOf.setText(i + " - " + " " + i + " of " + i);
			}

		}

		if (apps.iterator().hasNext() && i < apps.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			app2Button.setVisible(true);
			name_2.setText("Name: " + apps.get(i).getFirstName() + " "
					+ apps.get(i).getLastName());
			uid2.setText("UID: " + apps.get(i).getUid());
			lastUpdated2.setText("Last Updated: "
					+ apps.get(i).getLastContacted());
			agent2.setText("Agent: " + apps.get(i).getAgent());
			staff2.setText("Staff: " + apps.get(i).getSalesAdvisor());
			status2.setText("Status: " + apps.get(i).getAppStatus());
			lblOf.setText(j + " - " + i + 1 + "of" + " " + apps.size());
			i++;

			if (i == apps.size()) {
				lblOf.setText((i - 1) + " - " + " " + i + " of " + i);
			}

		}

		if (apps.iterator().hasNext() && i < apps.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			app3Button.setVisible(true);
			name3.setText("Name: " + apps.get(i).getFirstName() + " "
					+ apps.get(i).getLastName());
			uid3.setText("UID: " + apps.get(i).getUid());
			lastUpdated3.setText("Last Updated: "
					+ apps.get(i).getLastContacted());
			agent3.setText("Agent: " + apps.get(i).getAgent());
			staff3.setText("Staff: " + apps.get(i).getSalesAdvisor());
			status3.setText("Status: " + apps.get(i).getAppStatus());
			lblOf.setText(j + " - " + (i + 1) + " of " + apps.size());
			i++;
			if (i == apps.size()) {
				lblOf.setText((i - 2) + " - " + " " + i + " of " + i);
			}

		}

		if (apps.iterator().hasNext() && i < apps.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			app4Button.setVisible(true);
			name4.setText("Name: " + apps.get(i).getFirstName() + " "
					+ apps.get(i).getLastName());
			uid4.setText("UID: " + apps.get(i).getUid());
			lastUpdated4.setText("Last Updated: "
					+ apps.get(i).getLastContacted());
			agent4.setText("Agent: " + apps.get(i).getAgent());
			staff4.setText("Staff: " + apps.get(i).getSalesAdvisor());
			status4.setText("Status: " + apps.get(i).getAppStatus());
			lblOf.setText(j + " - " + (i + 1) + " of " + apps.size());
			i++;
			if (i == apps.size()) {
				lblOf.setText((i - 3) + " - " + " " + i + " of " + i);
			}
		}

		if (apps.iterator().hasNext() && i < apps.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			app5Button.setVisible(true);
			name5.setText("Name: " + apps.get(i).getFirstName() + " "
					+ apps.get(i).getLastName());
			uid5.setText("UID: " + apps.get(i).getUid());
			lastUpdated5.setText("Last Updated: "
					+ apps.get(i).getLastContacted());
			agent5.setText("Agent: " + apps.get(i).getAgent());
			staff5.setText("Staff: " + apps.get(i).getSalesAdvisor());
			status5.setText("Status: " + apps.get(i).getAppStatus());
			lblOf.setText(j + " - " + (i + 1) + " of " + apps.size());
			i++;
			if (i == apps.size()) {
				lblOf.setText((i - 4) + " - " + " " + i + " of " + i);
			}
		}
	}

	/**
	 * This method defines the action performed methods for each of the five buttons
	 * that might be selected by a user to view an individual application. 
	 * 
	 * For each button, if it is pressed, the method obtains the uid value for that 
	 * button from its corresponding jlabel. It then sends this information to the server,
	 * and waits for the response back in the form of an application object, which is then
	 * used when opening the next view to display the individual application information. 
	 * 
	 * @param - the JLabels for the application UIDs and the buttons for the applications
	 */
	public static void addButtonListeners(User user, JLabel lblUid,
			JLabel uid2, JLabel uid3, JLabel uid4, JLabel uid5,
			JButton app1Button, JButton app2Button, JButton app3Button,
			JButton app4Button, JButton app5Button) {

		// add action performed method for app1Button
		app1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button pressed");
				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {

							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the uid from the JLabel relating to the
							// application
							user.setCommand("getuid" + " " + lblUid.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant application
							Application app = (Application) clientIn
									.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// application

							if (user.getType().equals("staff")
									|| user.getType().equals("admin")) {
								IndividualApplicationView iav = new IndividualApplicationView(
										user, app);
							} else if (user.getType().equals("agent")) {
								IndividualApplicationViewAgent a = new IndividualApplicationViewAgent(
										user, app);
							}

						} catch (Exception ex) {

						}
					}
				});

			}

		});

		// add action performed method for app2Button
		app2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// creates a thread and adds to EventQueue because work needs to
				// be done before updating GUI so cannot be done from EDT
				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the uid from the JLabel relating to the
							// application
							user.setCommand("getuid" + " " + uid2.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant application
							Application app = (Application) clientIn
									.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// application
							if (user.getType().equals("staff")
									|| user.getType().equals("admin")) {
								IndividualApplicationView iav = new IndividualApplicationView(
										user, app);
							} else if (user.getType().equals("agent")) {
								IndividualApplicationViewAgent a = new IndividualApplicationViewAgent(
										user, app);
							}

						} catch (Exception ex) {

						}
					}
				});
			}
		});

		// add action performed method for app3Button
		app3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the uid from the JLabel relating to the
							// application
							user.setCommand("getuid" + " " + uid3.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant application
							Application app = (Application) clientIn
									.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// application
							if (user.getType().equals("staff")
									|| user.getType().equals("admin")) {
								IndividualApplicationView iav = new IndividualApplicationView(
										user, app);
							} else if (user.getType().equals("agent")) {
								IndividualApplicationViewAgent a = new IndividualApplicationViewAgent(
										user, app);
							}

						} catch (Exception ex) {

						}
					}
				});
			}
		});

		// add action performed method for app4Button
		app4Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the uid from the JLabel relating to the
							// application
							user.setCommand("getuid" + " " + uid4.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant application
							Application app = (Application) clientIn
									.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// application
							if (user.getType().equals("staff")
									|| user.getType().equals("admin")) {
								IndividualApplicationView iav = new IndividualApplicationView(
										user, app);
							} else if (user.getType().equals("agent")) {
								IndividualApplicationViewAgent a = new IndividualApplicationViewAgent(
										user, app);
							}

						} catch (Exception ex) {

						}
					}
				});
			}
		});

		// add action performed method for app5Button
		app5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the uid from the JLabel relating to the
							// application
							user.setCommand("getuid" + " " + uid5.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant application
							Application app = (Application) clientIn
									.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// application
							if (user.getType().equals("staff")
									|| user.getType().equals("admin")) {
								IndividualApplicationView iav = new IndividualApplicationView(
										user, app);
							} else if (user.getType().equals("agent")) {
								IndividualApplicationViewAgent a = new IndividualApplicationViewAgent(
										user, app);
							}

						} catch (Exception ex) {

						}
					}
				});
			}
		});

	}

	/**
	 * This method takes a user, enquiry and a series of swing components as paramaters, 
	 * and creates a new enquiry object based on the swing component values. 
	 * 
	 * It then sends this to the server and waits for a response back in the form of a
	 * user object. It reads the user command to determine if the transaction was successful
	 * and either generates the relevant view if successful, or displays a message informing the 
	 * user of the failure if not. 
	 * 
	 * It is used to update an existing enquiry on the system when in the relevant view
	 * 
	 * @param - user, enq, swing components representing the enquiry data
	 */
	public static void updateEnquiry(User user, Enquiry enq,
			JTextField firstNameField, JTextField lastNameField,
			JTextField noWeeksField, JLabel weeklyPrice2,
			JTextField totalPriceField, JComboBox statusCombo,
			JDateChooser lastContactedDateChooser, JTextField sourceField,
			JComboBox salesAdvisorCombo, JComboBox countryCombo,
			JComboBox agentCombo, JComboBox courseCombo, JTextField emailField,
			JTextField phoneField, JComboBox campusCombo, JLabel warningLabel) {

		try {
			socketConnection = new Socket("127.0.0.1", 5432);
			clientOut = new ObjectOutputStream(
					socketConnection.getOutputStream());
			clientIn = new ObjectInputStream(socketConnection.getInputStream());

			Enquiry updateEnq = new Enquiry(enq.getEid(),
					firstNameField.getText(), lastNameField.getText(),
					enq.getEnqDate(), noWeeksField.getText(), statusCombo
							.getSelectedItem().toString(),
					Controller.formatDate(lastContactedDateChooser.getDate()),
					sourceField.getText(), salesAdvisorCombo.getSelectedItem()
							.toString(), countryCombo.getSelectedItem()
							.toString(), agentCombo.getSelectedItem()
							.toString(), courseCombo.getSelectedItem()
							.toString(), emailField.getText(),
					phoneField.getText(), campusCombo.getSelectedItem()
							.toString(), weeklyPrice2.getText(),
					totalPriceField.getText(), new ArrayList<Notes>());

			user.setEnquiryToBeAdded(updateEnq);
			user.setCommand("updateenq");
			clientOut.writeObject(user);
			try {
				User user2 = (User) clientIn.readObject();
				socketConnection.close();
				if (user2.getCommand().equals("success")) {
					warningLabel.setForeground(new Color(0, 128, 0));
					warningLabel.setText("Enquiry successfully updated.");
					warningLabel.setVisible(true);

					for (Enquiry e : user.getEnquiries()) {
						if (e.getEid() == (updateEnq.getEid())) {
							e.setAgent(updateEnq.getAgent());
							e.setCampus(updateEnq.getCampus());
							e.setCountry(updateEnq.getCountry());
							e.setCourse(updateEnq.getCourse());
							e.setEmail(updateEnq.getEmail());
							e.setEnqDate(updateEnq.getEnqDate());
							e.setfName(updateEnq.getfName());
							e.setLastContacted(updateEnq.getLastContacted());
							e.setlName(updateEnq.getlName());
							e.setNoWeeks(updateEnq.getNoWeeks());
							e.setPhone(updateEnq.getPhone());
							e.setSource(updateEnq.getSource());
							e.setStaffMember(updateEnq.getStaffMember());
							e.setStatus(updateEnq.getStatus());
							e.setTotalPrice(updateEnq.getTotalPrice());
							e.setWeeklyPrice(updateEnq.getWeeklyPrice());
						}
					}

				} else {
					warningLabel.setVisible(true);
				}

			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			socketConnection.close();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	/**
	 * This method takes a user, application and a series of swing components as paramaters, 
	 * and creates a new application object based on the swing component values. 
	 * 
	 * It then sends this to the server and waits for a response back in the form of a
	 * user object. It reads the user command to determine if the transaction was successful
	 * and either generates the relevant view if successful, or displays a message informing the 
	 * user of the failure if not. 
	 * 
	 * It is used to update an existing application on the system when in the relevant view
	 * 
	 * @param - user, app, swing components representing the application data
	 */
	public static void updateApp(User user, Application app,
			JTextField firstNameField, JLabel warningLabel,
			JTextField totalPriceField, JTextField passportField,
			JTextField lastNameField, JComboBox countryCombo,
			JComboBox nationCombo, JComboBox agentCombo, JComboBox genderCombo,
			JComboBox statusCombo, JComboBox payStatusCombo,
			JComboBox salesAdvisorCombo, JTextField sourceField,
			JDateChooser arrivalDateField, JDateChooser depDateCombo,
			JDateChooser dobCombo, JDateChooser lastContactedCombo,
			JComboBox visaStatusCombo, JComboBox campusCombo, JLabel lblPrice,
			JTextField emailField, JTextField phoneField,
			JComboBox courseCombo, JLabel durationLabel, JTextArea adressBox) {

		try {
			socketConnection = new Socket("127.0.0.1", 5432);
			clientOut = new ObjectOutputStream(
					socketConnection.getOutputStream());
			clientIn = new ObjectInputStream(socketConnection.getInputStream());

			Application updateApp = new Application(app.getUid(),
					firstNameField.getText(), lastNameField.getText(),
					agentCombo.getSelectedItem().toString(), payStatusCombo
							.getSelectedItem().toString(), visaStatusCombo
							.getSelectedItem().toString(), nationCombo
							.getSelectedItem().toString(), countryCombo
							.getSelectedItem().toString(), genderCombo
							.getSelectedItem().toString(),
					passportField.getText(),
					Controller.formatDate(lastContactedCombo.getDate()),
					campusCombo.getSelectedItem().toString(), statusCombo
							.getSelectedItem().toString(), salesAdvisorCombo
							.getSelectedItem().toString(), courseCombo
							.getSelectedItem().toString(), adressBox.getText(),
					Controller.formatDate(lastContactedCombo.getDate()),
					Controller.formatDate(arrivalDateField.getDate()),
					Controller.formatDate(depDateCombo.getDate()),
					Controller.formatDate(dobCombo.getDate()),
					phoneField.getText(), emailField.getText(),
					sourceField.getText(), lblPrice.getText(),
					totalPriceField.getText(), durationLabel.getText(), null);

			user.setApplicationToBeAdded(updateApp);
			user.setCommand("updateapp");
			clientOut.writeObject(user);
			try {
				User user2 = (User) clientIn.readObject();
				socketConnection.close();
				if (user2.getCommand().equals("success")) {
					warningLabel.setForeground(new Color(0, 128, 0));
					warningLabel.setText("Application successfully updated.");
					warningLabel.setVisible(true);

					for (Application a : user.getUserApplications()) {

						if (a.getUid() == (updateApp.getUid())) {

							a.setFirstName(updateApp.getFirstName());
							a.setLastName(updateApp.getLastName());
							a.setAgent(updateApp.getAgent());
							a.setPaymentStatus(updateApp.getPaymentStatus());
							a.setVisaStatus(updateApp.getVisaStatus());
							a.setNationality(updateApp.getNationality());
							a.setCountry(updateApp.getCountry());
							a.setGender(updateApp.getGender());
							a.setPassportNo(updateApp.getPassportNo());
							a.setAppDate(updateApp.getAppDate());
							a.setCampus(updateApp.getCampus());
							a.setAppStatus(updateApp.getAppStatus());
							a.setSalesAdvisor(updateApp.getSalesAdvisor());
							a.setCourse(updateApp.getCourse());
							a.setAddress(updateApp.getAddress());
							a.setLastContacted(updateApp.getLastContacted());
							a.setArrivalDate(updateApp.getArrivalDate());
							a.setDepartureDate(updateApp.getDepartureDate());
							a.setDob(updateApp.getDob());
							a.setPhone(updateApp.getPhone());
							a.setEmail(updateApp.getEmail());
							a.setSource(updateApp.getSource());
							a.setWeeklyPrice(updateApp.getWeeklyPrice());
							a.setTotalPrice(updateApp.getTotalPrice());
							a.setDuration(updateApp.getDuration());
						}
					}

				} else {
					warningLabel.setVisible(true);
				}

			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			socketConnection.close();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * This method defines the action performed methods for each of the five buttons
	 * that might be selected by a user to view an individual enquiry. 
	 * 
	 * For each button, if it is pressed, the method obtains the eid value for that 
	 * button from its corresponding jlabel. It then sends this information to the server,
	 * and waits for the response back in the form of an enquiry object, which is then
	 * used when opening the next view to display the individual application information. 
	 * 
	 * @param - the JLabels for the enquiry EIDs and the buttons for the enquiries
	 */
	public static void addViewEnqButtonListeners(User user, JButton enq1Button,
			JButton enq2Button, JButton enq3Button, JButton enq4Button,
			JButton enq5Button, JLabel lbleid, JLabel eid2, JLabel eid3,
			JLabel eid4, JLabel eid5) {

		// add action performed method for app1Button
		enq1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button pressed");
				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {

							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the eid from the JLabel relating to the Enquiry
							user.setCommand("geteid" + " " + lbleid.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant Enquiry
							Enquiry enq = (Enquiry) clientIn.readObject();
							socketConnection.close();

							// open the next frame to display the individual
							// Enquiry
							IndividualEnquiryView iav = new IndividualEnquiryView(
									user, enq);

						} catch (Exception ex) {

						}
					}
				});

			}
		});

		// add action performed method for app2Button
		enq2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// creates a thread and adds to EventQueue because work needs to
				// be done before updating GUI so cannot be done from EDT
				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the eid from the JLabel relating to the Enquiry
							user.setCommand("geteid" + " " + eid2.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant Enquiry
							Enquiry enq = (Enquiry) clientIn.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// Enquiry
							IndividualEnquiryView iav = new IndividualEnquiryView(
									user, enq);

						} catch (Exception ex) {

						}
					}
				});
			}
		});

		// add action performed method for app3Button
		enq3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the eid from the JLabel relating to the Enquiry
							user.setCommand("geteid" + " " + eid3.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant Enquiry
							Enquiry enq = (Enquiry) clientIn.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// Enquiry
							IndividualEnquiryView iav = new IndividualEnquiryView(
									user, enq);

						} catch (Exception ex) {

						}
					}
				});
			}
		});

		// add action performed method for app4Button
		enq4Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the eid from the JLabel relating to the Enquiry
							user.setCommand("geteid" + " " + eid4.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant Enquiry
							Enquiry enq = (Enquiry) clientIn.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// Enquiry
							IndividualEnquiryView iav = new IndividualEnquiryView(
									user, enq);

						} catch (Exception ex) {

						}
					}
				});
			}
		});

		// add action performed method for app5Button
		enq5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the eid from the JLabel relating to the Enquiry
							user.setCommand("geteid" + " " + eid5.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant Enquiry
							Enquiry enq = (Enquiry) clientIn.readObject();
							socketConnection.close();
							
							IndividualEnquiryView iav = new IndividualEnquiryView(
									user, enq);

						} catch (Exception ex) {

						}
					}
				});
			}
		});

	}

	/**
	 * This method defines the action performed methods for each of the five buttons
	 * that might be selected by a user to view an individual agent. 
	 * 
	 * For each button, if it is pressed, the method obtains the agent name value for that 
	 * button from its corresponding jlabel. It then sends this information to the server,
	 * and waits for the response back in the form of an application object, which is then
	 * used when opening the next view to display the individual application information. 
	 * 
	 * @param - the JLabels for the agent name and the buttons for the agents 
	 */
	public static void addViewAgentButtonListeners(User user,
			JButton agent1Button, JButton agent2Button, JButton agent3Button,
			JButton agent4Button, JButton agent5Button, JLabel company,
			JLabel company2, JLabel company3, JLabel company4, JLabel company5) {

		// add action performed method for app1Button
		agent1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button pressed");
				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the eid from the JLabel relating to the Enquiry
							user.setCommand("getagt" + " " + company.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);
							System.out.println("wrote out");

							// receive back the relevant Enquiry
							Agent agent = (Agent) clientIn.readObject();

							socketConnection.close();

							// open the next frame to display the individual
							// Enquiry
							IndividualAgentView iav = new IndividualAgentView(
									user, agent);

						} catch (Exception ex) {

						}
					}
				});

			}
		});

		// add action performed method for app2Button
		agent2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// creates a thread and adds to EventQueue because work needs to
				// be done before updating GUI so cannot be done from EDT
				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the eid from the JLabel relating to the Enquiry
							user.setCommand("getagt" + " " + company2.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant Enquiry
							Agent agent = (Agent) clientIn.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// Enquiry
							IndividualAgentView iav = new IndividualAgentView(
									user, agent);

						} catch (Exception ex) {

						}
					}
				});
			}
		});

		// add action performed method for app3Button
		agent3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the eid from the JLabel relating to the Enquiry
							user.setCommand("getagt" + " " + company3.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant Enquiry
							Agent agent = (Agent) clientIn.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// Enquiry
							IndividualAgentView iav = new IndividualAgentView(
									user, agent);

						} catch (Exception ex) {

						}
					}
				});
			}
		});

		// add action performed method for app4Button
		agent4Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the eid from the JLabel relating to the Enquiry
							user.setCommand("getagt" + " " + company4.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant Enquiry
							Agent agent = (Agent) clientIn.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// Enquiry
							IndividualAgentView iav = new IndividualAgentView(
									user, agent);

						} catch (Exception ex) {

						}
					}
				});
			}
		});

		// add action performed method for app5Button
		agent5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {

						try {
							// open up a socket to connect to the server
							socketConnection = new Socket("127.0.0.1", 5432);
							clientOut = new ObjectOutputStream(socketConnection
									.getOutputStream());
							clientIn = new ObjectInputStream(socketConnection
									.getInputStream());

							// set the user command to be interpreted by server
							// + the eid from the JLabel relating to the Enquiry
							user.setCommand("getagt" + " " + company5.getText());

							// send the user with the relevant user command
							// through to the server
							clientOut.writeObject(user);

							// receive back the relevant Enquiry
							Agent agent = (Agent) clientIn.readObject();
							socketConnection.close();
							// open the next frame to display the individual
							// Enquiry
							IndividualAgentView iav = new IndividualAgentView(
									user, agent);

						} catch (Exception ex) {

						}
					}
				});
			}
		});

	}

	/**
	 * This method updates the GUI to display the next five enquiries to the
	 * user. An ArrayList of enquiries is passed into the method, representing a users 
	 * enquiries, as well an int value i, acting as an index for keeping track of which five 
	 * enquiries out of the arraylist are being viewed. 
	 * 
	 * The method takes the first enquiry from position i, and creates a JButton for the enquiry, 
	 * if indeed there is an enquiry to take. It then populates the display fields on the JButton
	 * with the relevant data, before setting the button as visible to the user. If there is no enquiry
	 * left to display, it means that we are at the end of the list of enquiries and it only displays 
	 * the JButtons for the enquiries available. int j is used to keep track of the enquiries
	 * being shown out of the total nummber of enquiries. 
	 * 
	 * @param - arraylist of enquiries, the swing components the five enquiries currently displayed on screen
	 */
	public static void displayNextEnqs(ArrayList<Enquiry> enqs,
			JButton enq1Button, JButton enq2Button, JButton enq3Button,
			JButton enq4Button, JButton enq5Button, JLabel lblName,
			JLabel lbleid, JLabel lblAgent, JLabel lblStaff, JLabel lblStatus,
			JLabel lblLastUpdated, JLabel lblOf, JLabel name_2, JLabel eid2,
			JLabel agent2, JLabel staff2, JLabel status2, JLabel lastUpdated2,
			JLabel name3, JLabel eid3, JLabel agent3, JLabel staff3,
			JLabel status3, JLabel lastUpdated3, JLabel name4, JLabel eid4,
			JLabel agent4, JLabel staff4, JLabel status4, JLabel lastUpdated4,
			JLabel name5, JLabel eid5, JLabel agent5, JLabel staff5,
			JLabel status5, JLabel lastUpdated5, int i, int j) {

		// set all app buttons to not visible because we only want to see the
		// ones that have Enquirys related to them
		enq1Button.setVisible(false);
		enq2Button.setVisible(false);
		enq3Button.setVisible(false);
		enq4Button.setVisible(false);
		enq5Button.setVisible(false);

		// if there is another Enquiry and we are not at the end of the appsList
		if (enqs.iterator().hasNext() && i < enqs.size()) {

			// set Enquiry button to visible and set JLabels of the button to
			// the relevant parameters of the Enquiry using index i, then
			// increment i
			enq1Button.setVisible(true);
			lblName.setText("Name: " + enqs.get(i).getfName() + " "
					+ enqs.get(i).getlName());
			lbleid.setText("EID: " + enqs.get(i).getEid());
			lblAgent.setText("Agent: " + " " + enqs.get(i).getAgent());
			lblStaff.setText("Staff: " + enqs.get(i).getStaffMember());
			lblStatus.setText("Status: " + enqs.get(i).getStatus());
			lblLastUpdated.setText("Last Updated: "
					+ enqs.get(i).getLastContacted());
			lblOf.setText(j + " - " + (i + 1) + " of" + " " + enqs.size());
			i++;
			if (i == enqs.size()) {
				lblOf.setText(i + " - " + " " + i + " of " + i);
			}

		}

		if (enqs.iterator().hasNext() && i < enqs.size()) {

			// set Enquiry button to visible and set JLabels of the button to
			// the relevant parameters of the Enquiry using index i, then
			// increment i
			enq2Button.setVisible(true);
			name_2.setText("Name: " + enqs.get(i).getfName() + " "
					+ enqs.get(i).getlName());
			eid2.setText("EID: " + enqs.get(i).getEid());
			agent2.setText("Agent: " + " " + enqs.get(i).getAgent());
			staff2.setText("Staff: " + enqs.get(i).getStaffMember());
			status2.setText("Status: " + enqs.get(i).getStatus());
			lastUpdated2.setText("Last Updated: "
					+ enqs.get(i).getLastContacted());
			lblOf.setText(j + " - " + (i + 1) + " of" + " " + enqs.size());
			i++;
			if (i == enqs.size()) {
				lblOf.setText((i - 1) + " - " + " " + i + " of " + i);
			}

		}

		if (enqs.iterator().hasNext() && i < enqs.size()) {

			// set Enquiry button to visible and set JLabels of the button to
			// the relevant parameters of the Enquiry using index i, then
			// increment i
			enq3Button.setVisible(true);
			name3.setText("Name: " + enqs.get(i).getfName() + " "
					+ enqs.get(i).getlName());
			eid3.setText("EID: " + enqs.get(i).getEid());
			agent3.setText("Agent: " + " " + enqs.get(i).getAgent());
			staff3.setText("Staff: " + enqs.get(i).getStaffMember());
			status3.setText("Status: " + enqs.get(i).getStatus());
			lastUpdated3.setText("Last Updated: "
					+ enqs.get(i).getLastContacted());
			lblOf.setText(j + " - " + (i + 1) + " of" + " " + enqs.size());
			i++;
			if (i == enqs.size()) {
				lblOf.setText((i - 2) + " - " + " " + i + " of " + i);
			}

		}

		if (enqs.iterator().hasNext() && i < enqs.size()) {

			// set Enquiry button to visible and set JLabels of the button to
			// the relevant parameters of the Enquiry using index i, then
			// increment i
			enq4Button.setVisible(true);
			name4.setText("Name: " + enqs.get(i).getfName() + " "
					+ enqs.get(i).getlName());
			eid4.setText("EID: " + enqs.get(i).getEid());
			agent4.setText("Agent: " + " " + enqs.get(i).getAgent());
			staff4.setText("Staff: " + enqs.get(i).getStaffMember());
			status4.setText("Status: " + enqs.get(i).getStatus());
			lastUpdated4.setText("Last Updated: "
					+ enqs.get(i).getLastContacted());
			lblOf.setText(j + " - " + (i + 1) + " of" + " " + enqs.size());
			i++;
			if (i == enqs.size()) {
				lblOf.setText((i - 3) + " - " + " " + i + " of " + i);
			}

		}

		if (enqs.iterator().hasNext() && i < enqs.size()) {

			// set Enquiry button to visible and set JLabels of the button to
			// the relevant parameters of the Enquiry using index i, then
			// increment i
			enq5Button.setVisible(true);
			name5.setText("Name: " + enqs.get(i).getfName() + " "
					+ enqs.get(i).getlName());
			eid5.setText("EID: " + enqs.get(i).getEid());
			agent5.setText("Agent: " + " " + enqs.get(i).getAgent());
			staff5.setText("Staff: " + enqs.get(i).getStaffMember());
			status5.setText("Status: " + enqs.get(i).getStatus());
			lastUpdated5.setText("Last Updated: "
					+ enqs.get(i).getLastContacted());
			lblOf.setText(j + " - " + (i + 1) + " of" + " " + enqs.size());
			i++;

			if (i == enqs.size()) {
				lblOf.setText((i - 4) + " - " + " " + i + " of " + i);
			}
		}
	}

	/**
	 * This method updates the GUI to display the next five enrolled students to the
	 * user. An ArrayList of enrolled students is passed into the method, representing a users 
	 * enrolled students, as well an int value i, acting as an index for keeping track of which five 
	 * enrolled students out of the arraylist are being viewed. 
	 * 
	 * The method takes the first enrolled student from position i, and creates a JButton for the enrolled student, 
	 * if indeed there is an enrolled student to take. It then populates the display fields on the JButton
	 * with the relevant data, before setting the button as visible to the user. If there is no enrolled student
	 * left to display, it means that we are at the end of the list of enrolled student and it only displays 
	 * the JButtons for the enrolled student available. int j is used to keep track of the enrolled student
	 * being shown out of the total nummber of enrolled students. 
	 * 
	 * @param - arraylist of enrolled students, the swing components the five enrolled students currently displayed on screen
	 */
	public static void nextEnrolledStudents(
			ArrayList<Application> enrolledStudents, JButton app1Button,
			JButton app2Button, JButton app3Button, JButton app4Button,
			JButton app5Button, JLabel lblName, JLabel lblUid, JLabel lblAgent,
			JLabel lblStaff, JLabel lblStatus, JLabel lblLastUpdated,
			JLabel name_2, JLabel uid2, JLabel agent2, JLabel staff2,
			JLabel status2, JLabel lastUpdated2, JLabel name3, JLabel uid3,
			JLabel agent3, JLabel staff3, JLabel status3, JLabel lastUpdated3,
			JLabel name4, JLabel uid4, JLabel agent4, JLabel staff4,
			JLabel status4, JLabel lastUpdated4, JLabel name5, JLabel uid5,
			JLabel agent5, JLabel staff5, JLabel status5, JLabel lastUpdated5,
			JLabel lblOf, int i, int j) {

		// set all app buttons to not visible because we only want to see the
		// ones that have applications related to them
		app1Button.setVisible(false);
		app2Button.setVisible(false);
		app3Button.setVisible(false);
		app4Button.setVisible(false);
		app5Button.setVisible(false);

		// if there is another application and we are not at the end of the
		// appsList
		if (enrolledStudents.iterator().hasNext()
				&& i < enrolledStudents.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			app1Button.setVisible(true);
			lblName.setText("Name: " + enrolledStudents.get(i).getFirstName()
					+ " " + enrolledStudents.get(i).getLastName());
			lblUid.setText("UID: " + enrolledStudents.get(i).getUid());
			lblAgent.setText("Agent: " + " "
					+ enrolledStudents.get(i).getAgent());
			lblStaff.setText("Staff: "
					+ enrolledStudents.get(i).getSalesAdvisor());
			lblStatus.setText("Status: "
					+ enrolledStudents.get(i).getAppStatus());
			lblLastUpdated.setText("Last Updated: "
					+ enrolledStudents.get(i).getLastContacted());
			lblOf.setText(j + " - " + i + 1 + "of" + " "
					+ enrolledStudents.size());
			i++;
			if (i == enrolledStudents.size()) {
				lblOf.setText(i + " - " + " " + i + " of " + i);
			}

		}

		if (enrolledStudents.iterator().hasNext()
				&& i < enrolledStudents.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			app2Button.setVisible(true);
			name_2.setText("Name: " + enrolledStudents.get(i).getFirstName()
					+ " " + enrolledStudents.get(i).getLastName());
			uid2.setText("UID: " + enrolledStudents.get(i).getUid());
			lastUpdated2.setText("Last Updated: "
					+ enrolledStudents.get(i).getLastContacted());
			agent2.setText("Agent: " + enrolledStudents.get(i).getAgent());
			staff2.setText("Staff: "
					+ enrolledStudents.get(i).getSalesAdvisor());
			status2.setText("Status: " + enrolledStudents.get(i).getAppStatus());
			lblOf.setText(j + " - " + i + 1 + "of" + " "
					+ enrolledStudents.size());
			i++;

			if (i == enrolledStudents.size()) {
				lblOf.setText((i - 1) + " - " + " " + i + " of " + i);
			}

		}

		if (enrolledStudents.iterator().hasNext()
				&& i < enrolledStudents.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			app3Button.setVisible(true);
			name3.setText("Name: " + enrolledStudents.get(i).getFirstName()
					+ " " + enrolledStudents.get(i).getLastName());
			uid3.setText("UID: " + enrolledStudents.get(i).getUid());
			lastUpdated3.setText("Last Updated: "
					+ enrolledStudents.get(i).getLastContacted());
			agent3.setText("Agent: " + enrolledStudents.get(i).getAgent());
			staff3.setText("Staff: "
					+ enrolledStudents.get(i).getSalesAdvisor());
			status3.setText("Status: " + enrolledStudents.get(i).getAppStatus());
			lblOf.setText(j + " - " + (i + 1) + " of "
					+ enrolledStudents.size());
			i++;
			if (i == enrolledStudents.size()) {
				lblOf.setText((i - 2) + " - " + " " + i + " of " + i);
			}

		}

		if (enrolledStudents.iterator().hasNext()
				&& i < enrolledStudents.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			app4Button.setVisible(true);
			name4.setText("Name: " + enrolledStudents.get(i).getFirstName()
					+ " " + enrolledStudents.get(i).getLastName());
			uid4.setText("UID: " + enrolledStudents.get(i).getUid());
			lastUpdated4.setText("Last Updated: "
					+ enrolledStudents.get(i).getLastContacted());
			agent4.setText("Agent: " + enrolledStudents.get(i).getAgent());
			staff4.setText("Staff: "
					+ enrolledStudents.get(i).getSalesAdvisor());
			status4.setText("Status: " + enrolledStudents.get(i).getAppStatus());
			lblOf.setText(j + " - " + (i + 1) + " of "
					+ enrolledStudents.size());
			i++;
			if (i == enrolledStudents.size()) {
				lblOf.setText((i - 3) + " - " + " " + i + " of " + i);
			}
		}

		if (enrolledStudents.iterator().hasNext()
				&& i < enrolledStudents.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			app5Button.setVisible(true);
			name5.setText("Name: " + enrolledStudents.get(i).getFirstName()
					+ " " + enrolledStudents.get(i).getLastName());
			uid5.setText("UID: " + enrolledStudents.get(i).getUid());
			lastUpdated5.setText("Last Updated: "
					+ enrolledStudents.get(i).getLastContacted());
			agent5.setText("Agent: " + enrolledStudents.get(i).getAgent());
			staff5.setText("Staff: "
					+ enrolledStudents.get(i).getSalesAdvisor());
			status5.setText("Status: " + enrolledStudents.get(i).getAppStatus());
			lblOf.setText(j + " - " + (i + 1) + " of "
					+ enrolledStudents.size());
			i++;
			if (i == enrolledStudents.size()) {
				lblOf.setText((i - 4) + " - " + " " + i + " of " + i);
			}
		}
	}

	/**
	 * This method updates the GUI to display the next five agents to the
	 * user. An ArrayList of agents is passed into the method, representing a users 
	 * agents, as well an int value i, acting as an index for keeping track of which five 
	 * agents out of the arraylist are being viewed. 
	 * 
	 * The method takes the first agent from position i, and creates a JButton for the agent, 
	 * if indeed there is an agent to take. It then populates the display fields on the JButton
	 * with the relevant data, before setting the button as visible to the user. If there is no agent
	 * left to display, it means that we are at the end of the list of agent and it only displays 
	 * the JButtons for the agent available. int j is used to keep track of the agent
	 * being shown out of the total nummber of agents. 
	 * 
	 * @param - arraylist of agents, the swing components the five agents currently displayed on screen
	 */
	public static void nextAgents(ArrayList<Agent> agentsList,
			JButton agent1Button, JButton agent2Button, JButton agent3Button,
			JButton agent4Button, JButton agent5Button, JLabel contactName,
			JLabel companyName, JLabel stats, JLabel accManager,
			JLabel lblStatus, JLabel lastCon, JLabel contactName2,
			JLabel companyName2, JLabel stats2, JLabel accMan2, JLabel status2,
			JLabel lastContacted2, JLabel contactName3, JLabel companyName3,
			JLabel stats3, JLabel accMan3, JLabel status3,
			JLabel lastContacted3, JLabel contactName4, JLabel companyName4,
			JLabel stats4, JLabel accMan4, JLabel status4,
			JLabel lastContacted4, JLabel contactName5, JLabel companyName5,
			JLabel stats5, JLabel accMan5, JLabel status5,
			JLabel lastContacted5, JLabel lblOf, int i, int j) {

		// set all app buttons to not visible because we only want to see the
		// ones that have applications related to them
		agent1Button.setVisible(false);
		agent2Button.setVisible(false);
		agent3Button.setVisible(false);
		agent4Button.setVisible(false);
		agent5Button.setVisible(false);

		// if there is another application and we are not at the end of the
		// appsList
		if (agentsList.iterator().hasNext() && i < agentsList.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			agent1Button.setVisible(true);
			contactName.setText("Contact Name: "
					+ agentsList.get(i).getFirstName() + " "
					+ agentsList.get(i).getLastName());
			companyName.setText("Company Name: "
					+ agentsList.get(i).getCompanyName());
			stats.setText("Apps/Payments:" + " "
					+ agentsList.get(i).getAppsToDate() + " / "
					+ agentsList.get(i).getStudentsToDate());
			accManager.setText("Account Manager: "
					+ agentsList.get(i).getAccountManager());
			lblStatus.setText("Status: " + agentsList.get(i).getStatus());
			lastCon.setText("Last Contacted: "
					+ agentsList.get(i).getLast_contacted());
			lblOf.setText(j + " - " + i + 1 + "of" + " " + agentsList.size());
			i++;
			if (i == agentsList.size()) {
				lblOf.setText(i + " - " + " " + i + " of " + i);
			}

		}

		if (agentsList.iterator().hasNext() && i < agentsList.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			agent2Button.setVisible(true);
			contactName2.setText("Contact Name: "
					+ agentsList.get(i).getFirstName() + " "
					+ agentsList.get(i).getLastName());
			companyName2.setText("Company Name: "
					+ agentsList.get(i).getCompanyName());
			lastContacted2.setText("Last Contacted: "
					+ agentsList.get(i).getLast_contacted());
			stats2.setText("Apps/Payments: "
					+ agentsList.get(i).getAppsToDate() + " / "
					+ agentsList.get(i).getStudentsToDate());
			accMan2.setText("Account Manager: "
					+ agentsList.get(i).getAccountManager());
			status2.setText("Status: " + agentsList.get(i).getStatus());
			lblOf.setText(j + " - " + i + 1 + "of" + " " + agentsList.size());
			i++;

			if (i == agentsList.size()) {
				lblOf.setText((i - 1) + " - " + " " + i + " of " + i);
			}

		}

		if (agentsList.iterator().hasNext() && i < agentsList.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			agent3Button.setVisible(true);
			contactName3.setText("Contact Name: "
					+ agentsList.get(i).getFirstName() + " "
					+ agentsList.get(i).getLastName());
			companyName3.setText("Company Name: "
					+ agentsList.get(i).getCompanyName());
			lastContacted3.setText("Last Contacted: "
					+ agentsList.get(i).getLast_contacted());
			stats3.setText("Apps/Payments: "
					+ agentsList.get(i).getAppsToDate() + " / "
					+ agentsList.get(i).getStudentsToDate());
			accMan3.setText("Account Manager: "
					+ agentsList.get(i).getAccountManager());
			status3.setText("Status: " + agentsList.get(i).getStatus());
			lblOf.setText(j + " - " + (i + 1) + " of " + agentsList.size());
			i++;
			if (i == agentsList.size()) {
				lblOf.setText((i - 2) + " - " + " " + i + " of " + i);
			}

		}

		if (agentsList.iterator().hasNext() && i < agentsList.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			agent4Button.setVisible(true);
			contactName4.setText("Contact Name: "
					+ agentsList.get(i).getFirstName() + " "
					+ agentsList.get(i).getLastName());
			companyName4.setText("Company Name: "
					+ agentsList.get(i).getCompanyName());
			lastContacted4.setText("Last Contacted: "
					+ agentsList.get(i).getLast_contacted());
			stats4.setText("Apps/Payments: "
					+ agentsList.get(i).getAppsToDate() + " / "
					+ agentsList.get(i).getStudentsToDate());
			accMan4.setText("Account Manager: "
					+ agentsList.get(i).getAccountManager());
			status4.setText("Status: " + agentsList.get(i).getStatus());
			lblOf.setText(j + " - " + (i + 1) + " of " + agentsList.size());
			i++;
			if (i == agentsList.size()) {
				lblOf.setText((i - 3) + " - " + " " + i + " of " + i);
			}
		}

		if (agentsList.iterator().hasNext() && i < agentsList.size()) {

			// set application button to visible and set JLabels of the button
			// to the relevant parameters of the application using index i, then
			// increment i
			agent5Button.setVisible(true);
			contactName5.setText("Contact Name: "
					+ agentsList.get(i).getFirstName() + " "
					+ agentsList.get(i).getLastName());
			companyName5.setText("Company Name: "
					+ agentsList.get(i).getCompanyName());
			lastContacted5.setText("Last Contacted: "
					+ agentsList.get(i).getLast_contacted());
			stats5.setText("Apps/Payments: "
					+ agentsList.get(i).getAppsToDate() + " / "
					+ agentsList.get(i).getStudentsToDate());
			accMan5.setText("Account Manager: "
					+ agentsList.get(i).getAccountManager());
			status5.setText("Status: " + agentsList.get(i).getStatus());
			lblOf.setText(j + " - " + (i + 1) + " of " + agentsList.size());
			i++;
			if (i == agentsList.size()) {
				lblOf.setText((i - 4) + " - " + " " + i + " of " + i);
			}
		}
	}

	/**
	 * This method takes an empty ArrayList of agents, and adds any agents to it
	 * out of the given users arraylist of agents that have a status indicating that
	 * they are signed
	 * 
	 * @param - the User, the arraylist to add the signed agents to
	 */
	public static void populateSignedAgents(ArrayList<Agent> signed, User user) {

		for (Agent a : user.getAgentsList()) {
			if (a.getStatus().equals("Active")
					|| a.getStatus().equals("Signed")) {
				if (a.getCompanyName().equalsIgnoreCase("DIRECT") == false) {
					signed.add(a);
				}
			}

		}

	}
	/**
	 * This method takes an empty ArrayList of agents, and adds any agents to it
	 * out of the given users arraylist of agents that have a status indicating that
	 * they are not signed
	 * 
	 * @param - the User, the arraylist to add the unsigned agents to
	 */
	public static void populateUnsignedAgents(ArrayList<Agent> signed, User user) {

		for (Agent a : user.getAgentsList()) {
			if (a.getStatus().equals("Expected")
					|| a.getStatus().equals("Cold")
					|| a.getStatus().equals("Newly Contacted")
					|| a.getStatus().equals("Lost")) {
				signed.add(a);

			}
		}

	}

	/**
	 * This method takes a series of swing components for an enquiry 
	 * to be added, firstly checking if all the fields are populated, 
	 * and updating the relevant component to display the relevant message 
	 * to the user if they are not. If all fields are populated, an new 
	 * enquiry object is created, set as the users applicatioToBeAdded
	 * parameter, and the user object is sent to the server. 
	 * 
	 * The method then receives a user object back from the server, and 
	 * reads the user command to determine if the transaction was successful. 
	 * 
	 * If it was, then the relevant view is generated, if not then a message is 
	 * provided to the user informing them of this. 
	 * 
	 * @param - swing components representing the different parameters of an enquiry
	 */
	public static void addEnquiry(JFrame frame, JTextField firstNameField,
			JLabel warningLabel, JTextField totalPriceField,
			JTextField lastNameField, JComboBox countryCombo,
			JComboBox agentCombo, JComboBox statusCombo,
			JComboBox salesAdvisorCombo, JTextField sourceField,
			JDateChooser lastContactedCombo, JComboBox campusCombo,
			JTextField noWeeksField, User user, JComboBox courseCombo,
			JTextField emailField, JLabel weeklyPriceLabel,
			JTextField phoneField) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					if (firstNameField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No first name entered.");
						warningLabel.setVisible(true);

					} else if (totalPriceField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No total price entered.");
						warningLabel.setVisible(true);
					}

					else if (lastNameField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No last name entered.");
						warningLabel.setVisible(true);
					} else if (countryCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No country selected.");
						warningLabel.setVisible(true);
					}

					else if (agentCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No agent selected.");
						warningLabel.setVisible(true);
					}

					else if (statusCombo.getSelectedItem().equals("select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No status selected.");
						warningLabel.setVisible(true);
					}

					else if (salesAdvisorCombo.getSelectedItem().equals(
							"Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No sales advisor selected.");
						warningLabel.setVisible(true);
					} else if (totalPriceField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No total price entered.");
						warningLabel.setVisible(true);
					} else if (sourceField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No source entered.");
						warningLabel.setVisible(true);
					}

					else if (lastContactedCombo.getDate() == (null)) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No last contacted date selected.");
						warningLabel.setVisible(true);
					}

					else if (campusCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No campus selected.");
						warningLabel.setVisible(true);
					}

					else if (noWeeksField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "Number of weeks not entered.");
						warningLabel.setVisible(true);
					}

					else {

						SimpleDateFormat format = new SimpleDateFormat(
								"dd/MM/yy");
						String currentDate = format.format(new Date(System
								.currentTimeMillis()));
						System.out.println(currentDate);

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());

						Enquiry enq = new Enquiry(0, firstNameField.getText(),
								lastNameField.getText(), currentDate,
								noWeeksField.getText(), statusCombo
										.getSelectedItem().toString(),
								Controller.formatDate(lastContactedCombo
										.getDate()), sourceField.getText(),
								salesAdvisorCombo.getSelectedItem().toString(),
								countryCombo.getSelectedItem().toString(),
								agentCombo.getSelectedItem().toString(),
								courseCombo.getSelectedItem().toString(),
								emailField.getText(), phoneField.getText(),
								campusCombo.getSelectedItem().toString(),
								weeklyPriceLabel.getText(), totalPriceField
										.getText(), new ArrayList<Notes>());

						user.setEnquiryToBeAdded(enq);
						user.setCommand("addenq");
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						if (user2.getCommand().equals("success")) {
							user.getEnquiryToBeAdded().setEid(
									user2.getEnquiryToBeAdded().getEid());
							user.getEnquiries().add(0,
									user.getEnquiryToBeAdded());
							ViewEnquiries ve = new ViewEnquiries(user);
							frame.dispose();
						}

						else {
							warningLabel
									.setText("Add application failed. Please check fields and try again.");
						}

					}
				} catch (Exception ex) {

				}
			}
		});

	}
	/**
	 * This method adds document listeners to the components affecting weekly price, 
	 * in order to ensure that if they are changed, the enquiry weekly price 
	 * is recalculated and updated accordingly 
	 * 
	 * @param - the components to add listeners for and the weekly
	 * price label to be changed
	 */
	public static void addWeeklyPriceCalculatorListeners(
			JTextField totalPriceField, JTextField noWeeksField,
			JLabel weeklyPriceLabel) {

		totalPriceField.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						warn();
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						warn();
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						warn();
					}

					public void warn() {
						if (!noWeeksField.getText().equals("")
								&& !totalPriceField.getText().equals("")) {
							weeklyPriceLabel.setText(Controller
									.computeWeeklyPriceEnq(
											totalPriceField.getText(),
											noWeeksField.getText()));
						}
					}
				});

		noWeeksField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				if (!noWeeksField.getText().equals("")
						&& !totalPriceField.getText().equals("")) {
					weeklyPriceLabel.setText(Controller.computeWeeklyPriceEnq(
							totalPriceField.getText(), noWeeksField.getText()));
				}
			}
		});
	}

	/**
	 * This method takes two int values representing an agents 
	 * applications and payments and returns the conversion rate 
	 * 
	 * @param - int apps, payments, used for calculating the conversion rate
	 */
	public static String computeAgentConversionRate(int apps, int payments) {

		double result = ((double) payments) / apps * 100;
		return new DecimalFormat("#.##").format(result);

	}

	/**
	 * This method takes a user, agent and a series of swing components as paramaters, 
	 * and creates a new agent object based on the swing component values. 
	 * 
	 * It then sends this to the server and waits for a response back in the form of a
	 * user object. It reads the user command to determine if the transaction was successful
	 * and either generates the relevant view if successful, or displays a message informing the 
	 * user of the failure if not. 
	 * 
	 * It is used to update an existing agent on the system when in the relevant view
	 * 
	 * @param - user, agent, swing components representing the agent data
	 */
	public static void updateAgent(User user, Agent agent,
			JTextField firstNameField, JTextField lastNameField,
			JComboBox statusCombo, JDateChooser lastContactedDateChooser,
			JComboBox accManCombo, JComboBox countryCombo,
			JTextField emailField, JTextField companyNameField,
			JTextField phoneField, JLabel warningLabel, JTextArea addressField,
			JLabel apps, JLabel payments) {

		try {
			socketConnection = new Socket("127.0.0.1", 5432);
			clientOut = new ObjectOutputStream(
					socketConnection.getOutputStream());
			clientIn = new ObjectInputStream(socketConnection.getInputStream());

			Agent agent2 = new Agent(companyNameField.getText(), accManCombo
					.getSelectedItem().toString(), firstNameField.getText(),
					lastNameField.getText(), countryCombo.getSelectedItem()
							.toString(), statusCombo.getSelectedItem()
							.toString(), addressField.getText(),
					phoneField.getText(), emailField.getText(),
					Controller.formatDate(lastContactedDateChooser.getDate()),
					agent.getAppsToDate(), agent.getStudentsToDate(),
					agent.getNotes());

			user.setAgentToBeAdded(agent2);
			user.setCommand("updateagent");
			clientOut.writeObject(user);
			try {
				User user2 = (User) clientIn.readObject();
				socketConnection.close();
				if (user2.getCommand().equals("success")) {
					warningLabel.setForeground(new Color(0, 128, 0));
					warningLabel.setText("Agent successfully updated.");
					warningLabel.setVisible(true);

					for (Agent a : user.getAgentsList()) {
						if (a.getCompanyName().equals(agent2.getCompanyName())) {
							a.setAccountManager(agent2.getAccountManager());
							a.setAddress(agent2.getAddress());
							a.setAppsToDate(agent2.getAppsToDate());
							a.setCompanyName(agent2.getCompanyName());
							a.setCountry(agent2.getCountry());
							a.setEmail(agent2.getEmail());
							a.setFirstName(agent2.getFirstName());
							a.setLast_contacted(agent2.getLast_contacted());
							a.setLastName(agent2.getLastName());
							a.setPhone(agent2.getPhone());
							a.setStatus(agent2.getStatus());
							a.setStudentsToDate(agent2.getStudentsToDate());

						}
					}

				} else {
					warningLabel.setVisible(true);
					socketConnection.close();
				}

			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			socketConnection.close();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * This method takes a series of swing components for an agent 
	 * to be added, firstly checking if all the fields are populated, 
	 * and updating the relevant component to display the relevant message 
	 * to the user if they are not. If all fields are populated, a new 
	 * agent object is created, set as the users applicatioToBeAdded
	 * parameter, and the user object is sent to the server. 
	 * 
	 * The method then receives a user object back from the server, and 
	 * reads the user command to determine if the transaction was successful. 
	 * 
	 * If it was, then the relevant view is generated, if not then a message is 
	 * provided to the user informing them of this. 
	 * 
	 * @param - swing components representing the different parameters of an agent, user
	 */
	public static void addAgent(JFrame frame, JTextField firstNameField,
			JLabel warningLabel, JTextField lastNameField,
			JTextField companyNameField, JComboBox countryCombo,
			JComboBox statusCombo, JComboBox salesAdvisorCombo,
			JDateChooser lastContactedCombo, JTextField phoneField,
			JTextField emailField, JTextArea addressField, User user) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {
					if (firstNameField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No first name entered.");
						warningLabel.setVisible(true);
					} else if (lastNameField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No last name entered.");
						warningLabel.setVisible(true);
					}

					else if (companyNameField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No company name entered.");
						warningLabel.setVisible(true);
					} else if (countryCombo.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No country selected.");
						warningLabel.setVisible(true);
					}

					else if (statusCombo.getSelectedItem().equals("select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No status selected.");
						warningLabel.setVisible(true);
					}

					else if (salesAdvisorCombo.getSelectedItem().equals(
							"Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No account manager selected.");
						warningLabel.setVisible(true);
					}

					else if (lastContactedCombo.getDate() == (null)) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No last contacted date selected.");
						warningLabel.setVisible(true);
					} else if (phoneField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No phone number entered.");
						warningLabel.setVisible(true);
					} else if (emailField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No email entered.");
						warningLabel.setVisible(true);
					} else if (addressField.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No address entered.");
						warningLabel.setVisible(true);
					}

					else {

						SimpleDateFormat format = new SimpleDateFormat(
								"dd/MM/yy");
						String currentDate = format.format(new Date(System
								.currentTimeMillis()));
						System.out.println(currentDate);

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());

						Agent agent = new Agent(companyNameField.getText(),
								salesAdvisorCombo.getSelectedItem().toString(),
								firstNameField.getText(), lastNameField
										.getText(), countryCombo
										.getSelectedItem().toString(),
								statusCombo.getSelectedItem().toString(),
								addressField.getText(), phoneField.getText(),
								emailField.getText(), Controller
										.formatDate(lastContactedCombo
												.getDate()), 0, 0,
								new ArrayList<AgentNote>());

						user.setAgentToBeAdded(agent);
						user.setCommand("addagent");
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						if (user2.getCommand().equals("success")) {
							user.getAgentsList().add(agent);

							if (agent.getStatus().equals("Signed")
									|| agent.getStatus().equals("Active")) {
								SignedAgentsView sav = new SignedAgentsView(
										user);
								frame.dispose();
							} else {
								UnsignedAgentsView uav = new UnsignedAgentsView(
										user);
								frame.dispose();
							}

						}

						else {
							warningLabel
									.setText("Add agent failed. Please check fields and try again.");
						}

					}
				} catch (Exception ex) {

				}
			}
		});

	}

	/**
	 * This method adds a group application to the system. 
	 * 
	 * Firstly, it checks that all fields for the group, and all fields 
	 * for each individual within the group have been populated, updating the display
	 * to inform the user if they have not been populated.
	 * 
	 * If they have been populated, the method calls another method getRemainingGroupApps,
	 * to get the entire group of applications to be added, before taking each application
	 * in the arraylist of applications to be added and setting their parameters to the 
	 * corresponding group values passed to the method. 
	 * 
	 * The method then sets the user command and writes the user object out to the server.
	 * It then receives a response back from the server in the form of a user object, with a 
	 * command indicating whether the transaction was successful. 
	 * 
	 * If successful, it updates the users application arraylist with the new applications so that
	 * they can be viewed on the system, and then generates the appropiate view to display them.
	 * 
	 * If unnsuccessful, it notifies the user of this by setting a jlabel component to display a 
	 * warning message.
	 * 
	 * @param - swing components representing the group and individual application params, user
	 */
	public static void addGroupApplication(JFrame frame, User user,
			JComboBox countryCombo, JTextField totalPriceField,
			JComboBox nationCombo, JComboBox agentCombo, JComboBox genderCombo,
			JComboBox statusCombo, JComboBox payStatusCombo,
			JComboBox salesAdvisorCombo, JDateChooser arrChooser,
			JDateChooser depChooser, JDateChooser dobChooser,
			JDateChooser lastContactedChooser, JComboBox visaStatusCombo,
			JComboBox campusCombo, JComboBox courseCombo,
			JTextField sourceField, JDateChooser dobChooser2,
			JDateChooser dobChooser3, JDateChooser dobChooser4,
			JDateChooser dobChooser5, JTextField fname, JTextField lname,
			JTextField phone, JTextField email, JTextField passport,
			JTextField address, JTextField fname2, JTextField lname2,
			JTextField phone2, JTextField email2, JTextField passport2,
			JTextField address2, JTextField fname3, JTextField lname3,
			JTextField phone3, JTextField email3, JTextField passport3,
			JTextField address3, JTextField fname4, JTextField lname4,
			JTextField phone4, JTextField email4, JTextField passport4,
			JTextField address4, JTextField fname5, JTextField lname5,
			JTextField phone5, JTextField email5, JTextField address5,
			JTextField passport5, JComboBox genderCombo2,
			JComboBox genderCombo3, JComboBox genderCombo4,
			JComboBox genderCombo5, JLabel weeklyPrice2, JLabel warningLabel,
			JLabel warningLabel2, JLabel durationLabel) {

		if (countryCombo.getSelectedItem().equals("Select")
				|| totalPriceField.getText().equals("")
				|| nationCombo.getSelectedItem().equals("Select")
				|| agentCombo.getSelectedItem().equals("Select")
				|| statusCombo.getSelectedItem().equals("Select")
				|| payStatusCombo.getSelectedItem().equals("Select")
				|| salesAdvisorCombo.getSelectedItem().equals("Select")
				|| arrChooser.getDate() == null || depChooser.getDate() == null
				|| lastContactedChooser.getDate() == null
				|| visaStatusCombo.getSelectedItem().equals("Select")
				|| lastContactedChooser.getDate() == null
				|| visaStatusCombo.getSelectedItem().equals("Select")
				|| campusCombo.getSelectedItem().equals("Select")
				|| courseCombo.getSelectedItem().equals("Select")
				|| sourceField.getText().equals("")) {
			warningLabel2.setText("Please complete all fields on Group Tab");
			warningLabel2.setVisible(true);
		}

		else {

			getRemainingGroupApps(user, genderCombo, statusCombo, dobChooser,
					dobChooser2, dobChooser3, dobChooser4, dobChooser5, fname,
					lname, phone, email, passport, address, fname2, lname2,
					phone2, email2, passport2, address2, fname3, lname3,
					phone3, email3, passport3, address3, fname4, lname4,
					phone4, email4, passport4, address4, fname5, lname5,
					phone5, email5, address5, passport5, genderCombo2,
					genderCombo3, genderCombo4, genderCombo5, weeklyPrice2,
					durationLabel, warningLabel2);

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
			String currentDate = format.format(new Date(System
					.currentTimeMillis()));

			int i = -1;

			for (Application a : user.getGroupApp()) {
				i++;
				a.setAgent(agentCombo.getSelectedItem().toString());
				a.setAppDate(currentDate);
				a.setAppStatus(statusCombo.getSelectedItem().toString());
				a.setArrivalDate(Controller.formatDate(arrChooser.getDate()));
				a.setCampus(campusCombo.getSelectedItem().toString());
				a.setCountry(countryCombo.getSelectedItem().toString());
				a.setCourse(courseCombo.getSelectedItem().toString());
				a.setDepartureDate(Controller.formatDate(depChooser.getDate()));
				a.setDuration(durationLabel.getText().substring(0,durationLabel.getText().indexOf("D") - 1));
				a.setLastContacted(Controller.formatDate(lastContactedChooser.getDate()));
				a.setNationality(nationCombo.getSelectedItem().toString());
				a.setPaymentStatus(payStatusCombo.getSelectedItem().toString());
				a.setSalesAdvisor(salesAdvisorCombo.getSelectedItem().toString());
				a.setSource(sourceField.getText());
				a.setTotalPrice(totalPriceField.getText());
				a.setVisaStatus(visaStatusCombo.getSelectedItem().toString());
				a.setWeeklyPrice(weeklyPrice2.getText());

			}

			EventQueue.invokeLater(new Runnable() {
				public void run() {

					try {

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());
						user.setCommand("addgroupapp");
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						if (user2.getCommand().equals("success")) {

							user.setGroupApp(user2.getGroupApp());

							for (Application a : user.getGroupApp()) {

								user.getUserApplications().add(0, a);
							}
							user.setGroupApp(new ArrayList<Application>());
							ViewApplications va = new ViewApplications(user);
							frame.dispose();
						}

						else {
							warningLabel2
									.setText("Add application failed. Please check fields and try again.");
							user.setGroupApp(new ArrayList<Application>());
						}

					}

					catch (Exception ex) {

					}
				}
			});
		}

	}
	/**
	 * This method is when a user wants to add additional applications to a 
	 * group application. It firstly checks if all fields have been populated, 
	 * informing the user if not, before creating an application object for each 
	 * application within the group. It then adds these to the users arraylist of applications
	 * to be added 
	 * 
	 * @param - swing components representing the group and individual application params, user
	 */
	public static void addMoreToGroup(User user, JComboBox genderCombo,
			JComboBox statusCombo, JDateChooser dobChooser,
			JDateChooser dobChooser2, JDateChooser dobChooser3,
			JDateChooser dobChooser4, JDateChooser dobChooser5,
			JTextField fname, JTextField lname, JTextField phone,
			JTextField email, JTextField passport, JTextField address,
			JTextField fname2, JTextField lname2, JTextField phone2,
			JTextField email2, JTextField passport2, JTextField address2,
			JTextField fname3, JTextField lname3, JTextField phone3,
			JTextField email3, JTextField passport3, JTextField address3,
			JTextField fname4, JTextField lname4, JTextField phone4,
			JTextField email4, JTextField passport4, JTextField address4,
			JTextField fname5, JTextField lname5, JTextField phone5,
			JTextField email5, JTextField address5, JTextField passport5,
			JComboBox genderCombo2, JComboBox genderCombo3,
			JComboBox genderCombo4, JComboBox genderCombo5,
			JLabel weeklyPrice2, JLabel warningLabel, JLabel warningLabel2,
			JLabel durationLabel) {

		if (fname.getText().equals("") || lname.getText().equals("")
				|| phone.getText().equals("") || email.getText().equals("")
				|| passport.getText().equals("")
				|| address.getText().equals("")
				|| genderCombo.getSelectedItem().equals("-")
				|| dobChooser.getDate() == null || fname2.getText().equals("")
				|| lname2.getText().equals("") || phone2.getText().equals("")
				|| email2.getText().equals("")
				|| passport2.getText().equals("")
				|| address2.getText().equals("")
				|| genderCombo2.getSelectedItem().equals("-")
				|| dobChooser2.getDate() == null || fname3.getText().equals("")
				|| lname3.getText().equals("") || phone3.getText().equals("")
				|| email3.getText().equals("")
				|| passport3.getText().equals("")
				|| address3.getText().equals("")
				|| genderCombo3.getSelectedItem().equals("-")
				|| dobChooser3.getDate() == null || fname4.getText().equals("")
				|| lname4.getText().equals("") || phone4.getText().equals("")
				|| email4.getText().equals("")
				|| passport4.getText().equals("")
				|| address4.getText().equals("")
				|| genderCombo4.getSelectedItem().equals("-")
				|| dobChooser4.getDate() == null || fname5.getText().equals("")
				|| lname5.getText().equals("") || phone5.getText().equals("")
				|| email5.getText().equals("")
				|| passport5.getText().equals("")
				|| address5.getText().equals("")
				|| genderCombo5.getSelectedItem().equals("-")
				|| dobChooser5.getDate() == null) {

			warningLabel2
					.setText("Please complete all application fields before adding more");
			warningLabel2.setVisible(true);
		}

		else {

			Application app = new Application(0, fname.getText(),
					lname.getText(), null, null, null, null, null, genderCombo
							.getSelectedItem().toString(), passport.getText(),
					null, null, null, null, null, address.getText(), null,
					null, null, Controller.formatDate(dobChooser.getDate()),
					phone.getText(), email.getText(), null, null, null, null,
					null);

			Application app2 = new Application(0, fname2.getText(),
					lname2.getText(), null, null, null, null, null,
					genderCombo2.getSelectedItem().toString(),
					passport2.getText(), null, null, null, null, null,
					address2.getText(), null, null, null,
					Controller.formatDate(dobChooser2.getDate()),
					phone2.getText(), email2.getText(), null, null, null, null,
					null);

			Application app3 = new Application(0, fname3.getText(),
					lname3.getText(), null, null, null, null, null,
					genderCombo3.getSelectedItem().toString(),
					passport3.getText(), null, null, null, null, null,
					address3.getText(), null, null, null,
					Controller.formatDate(dobChooser3.getDate()),
					phone3.getText(), email3.getText(), null, null, null, null,
					null);

			Application app4 = new Application(0, fname4.getText(),
					lname4.getText(), null, null, null, null, null,
					genderCombo4.getSelectedItem().toString(),
					passport4.getText(), null, null, null, null, null,
					address4.getText(), null, null, null,
					Controller.formatDate(dobChooser4.getDate()),
					phone4.getText(), email4.getText(), null, null, null, null,
					null);

			Application app5 = new Application(0, fname5.getText(),
					lname5.getText(), null, null, null, null, null,
					genderCombo5.getSelectedItem().toString(),
					passport5.getText(), null, null, null, null, null,
					address5.getText(), null, null, null,
					Controller.formatDate(dobChooser5.getDate()),
					phone5.getText(), email5.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app);
			user.getGroupApp().add(app2);
			user.getGroupApp().add(app3);
			user.getGroupApp().add(app4);
			user.getGroupApp().add(app5);

			genderCombo.setSelectedItem("-");
			genderCombo2.setSelectedItem("-");
			genderCombo3.setSelectedItem("-");
			genderCombo4.setSelectedItem("-");
			genderCombo5.setSelectedItem("-");

			dobChooser.setCalendar(null);
			dobChooser2.setCalendar(null);
			dobChooser3.setCalendar(null);
			dobChooser4.setCalendar(null);
			dobChooser5.setCalendar(null);

			fname.setText("");
			lname.setText("");
			phone.setText("");
			email.setText("");
			passport.setText("");
			address.setText("");
			fname2.setText("");
			lname2.setText("");
			phone2.setText("");
			email2.setText("");
			passport2.setText("");
			address2.setText("");
			fname3.setText("");
			lname3.setText("");
			phone3.setText("");
			email3.setText("");
			passport3.setText("");
			address3.setText("");
			fname4.setText("");
			lname4.setText("");
			phone4.setText("");
			email4.setText("");
			passport4.setText("");
			address4.setText("");
			fname5.setText("");
			lname5.setText("");
			phone5.setText("");
			email5.setText("");
			address5.setText("");
			passport5.setText("");

			warningLabel2.setForeground(new Color(0, 128, 0));
			warningLabel2
					.setText("When you have finished adding more, please click Add Applications");

		}
	}

	/**
	 * This method is called by the addGroupApplication method. 
	 * 
	 * The method firstly checks that all fields have been populated for the applications
	 * to be added, and then creates application objects for them based on the data
	 * and the group data, before adding them to the users group applications arraylist
	 * 
	 * @param - swing components representing the group and individual application params, user
	 */
	public static void getRemainingGroupApps(User user, JComboBox genderCombo,
			JComboBox statusCombo, JDateChooser dobChooser,
			JDateChooser dobChooser2, JDateChooser dobChooser3,
			JDateChooser dobChooser4, JDateChooser dobChooser5,
			JTextField fname, JTextField lname, JTextField phone,
			JTextField email, JTextField passport, JTextField address,
			JTextField fname2, JTextField lname2, JTextField phone2,
			JTextField email2, JTextField passport2, JTextField address2,
			JTextField fname3, JTextField lname3, JTextField phone3,
			JTextField email3, JTextField passport3, JTextField address3,
			JTextField fname4, JTextField lname4, JTextField phone4,
			JTextField email4, JTextField passport4, JTextField address4,
			JTextField fname5, JTextField lname5, JTextField phone5,
			JTextField email5, JTextField address5, JTextField passport5,
			JComboBox genderCombo2, JComboBox genderCombo3,
			JComboBox genderCombo4, JComboBox genderCombo5,
			JLabel weeklyPrice2, JLabel durationLabel, JLabel warningLabel2) {

		if (fname.getText().equals("") == false
				&& lname.getText().equals("") == false
				&& phone.getText().equals("") == false
				&& email.getText().equals("") == false
				&& passport.getText().equals("") == false
				&& address.getText().equals("") == false) {

			Application app = new Application(0, fname.getText(),
					lname.getText(), null, null, null, null, null, genderCombo
							.getSelectedItem().toString(), passport.getText(),
					null, null, null, null, null, address.getText(), null,
					null, null, Controller.formatDate(dobChooser.getDate()),
					phone.getText(), email.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app);

		}

		if (fname2.getText().equals("") == false
				&& lname2.getText().equals("") == false
				&& phone2.getText().equals("") == false
				&& email2.getText().equals("") == false
				&& passport2.getText().equals("") == false
				&& address2.getText().equals("") == false) {

			Application app2 = new Application(0, fname2.getText(),
					lname2.getText(), null, null, null, null, null,
					genderCombo2.getSelectedItem().toString(),
					passport2.getText(), null, null, null, null, null,
					address2.getText(), null, null, null,
					Controller.formatDate(dobChooser2.getDate()),
					phone2.getText(), email2.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app2);

		} else {
			warningLabel2.setText("Please complete all fields");
			warningLabel2.setVisible(true);
		}

		if (fname3.getText().equals("") == false
				&& lname3.getText().equals("") == false
				&& phone3.getText().equals("") == false
				&& email3.getText().equals("") == false
				&& passport3.getText().equals("") == false
				&& address3.getText().equals("") == false) {

			Application app3 = new Application(0, fname3.getText(),
					lname3.getText(), null, null, null, null, null,
					genderCombo3.getSelectedItem().toString(),
					passport3.getText(), null, null, null, null, null,
					address3.getText(), null, null, null,
					Controller.formatDate(dobChooser3.getDate()),
					phone3.getText(), email3.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app3);

		} else {
			warningLabel2.setText("Please complete all fields");
			warningLabel2.setVisible(true);
		}

		if (fname4.getText().equals("") == false
				&& lname4.getText().equals("") == false
				&& phone4.getText().equals("") == false
				&& email4.getText().equals("") == false
				&& passport4.getText().equals("") == false
				&& address4.getText().equals("") == false) {

			Application app4 = new Application(0, fname4.getText(),
					lname4.getText(), null, null, null, null, null,
					genderCombo4.getSelectedItem().toString(),
					passport4.getText(), null, null, null, null, null,
					address4.getText(), null, null, null,
					Controller.formatDate(dobChooser.getDate()),
					phone4.getText(), email4.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app4);

		} else {
			warningLabel2.setText("Please complete all fields");
			warningLabel2.setVisible(true);
		}

		if (fname5.getText().equals("") == false
				&& lname5.getText().equals("") == false
				&& phone5.getText().equals("") == false
				&& email5.getText().equals("") == false
				&& passport5.getText().equals("") == false
				&& address5.getText().equals("") == false) {

			Application app5 = new Application(0, fname5.getText(),
					lname5.getText(), null, null, null, null, null,
					genderCombo5.getSelectedItem().toString(),
					passport5.getText(), null, null, null, null, null,
					address5.getText(), null, null, null,
					Controller.formatDate(dobChooser5.getDate()),
					phone5.getText(), email5.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app5);

		} else {
			warningLabel2.setText("Please complete all fields");
			warningLabel2.setVisible(true);
		}

	}

	/**
	 * This method adds a group application to the system for an agent. 
	 * 
	 * Firstly, it checks that all fields for the group, and all fields 
	 * for each individual within the group have been populated, updating the display
	 * to inform the user if they have not been populated.
	 * 
	 * If they have been populated, the method calls another method getRemainingGroupApps,
	 * to get the entire group of applications to be added, before taking each application
	 * in the arraylist of applications to be added and setting their parameters to the 
	 * corresponding group values passed to the method. 
	 * 
	 * The method then sets the user command and writes the user object out to the server.
	 * It then receives a response back from the server in the form of a user object, with a 
	 * command indicating whether the transaction was successful. 
	 * 
	 * If successful, it updates the users application arraylist with the new applications so that
	 * they can be viewed on the system, and then generates the appropiate view to display them.
	 * 
	 * If unnsuccessful, it notifies the user of this by setting a jlabel component to display a 
	 * warning message.
	 * 
	 * @param - swing components representing the group and individual application params, user
	 */
	public static void addGroupApplicationAgent(JFrame frame, User user,
			JComboBox countryCombo, JComboBox nationCombo,
			JComboBox agentCombo, JComboBox genderCombo,
			JDateChooser arrChooser, JDateChooser depChooser,
			JDateChooser dobChooser, JComboBox campusCombo,
			JComboBox courseCombo, JDateChooser dobChooser2,
			JDateChooser dobChooser3, JDateChooser dobChooser4,
			JDateChooser dobChooser5, JTextField fname, JTextField lname,
			JTextField phone, JTextField email, JTextField passport,
			JTextField address, JTextField fname2, JTextField lname2,
			JTextField phone2, JTextField email2, JTextField passport2,
			JTextField address2, JTextField fname3, JTextField lname3,
			JTextField phone3, JTextField email3, JTextField passport3,
			JTextField address3, JTextField fname4, JTextField lname4,
			JTextField phone4, JTextField email4, JTextField passport4,
			JTextField address4, JTextField fname5, JTextField lname5,
			JTextField phone5, JTextField email5, JTextField address5,
			JTextField passport5, JComboBox genderCombo2,
			JComboBox genderCombo3, JComboBox genderCombo4,
			JComboBox genderCombo5, JLabel warningLabel, JLabel warningLabel2,
			JLabel durationLabel) {

		if (countryCombo.getSelectedItem().equals("Select")
				|| nationCombo.getSelectedItem().equals("Select")
				|| arrChooser.getDate() == null || depChooser.getDate() == null
				|| campusCombo.getSelectedItem().equals("Select")
				|| courseCombo.getSelectedItem().equals("Select")) {
			warningLabel2.setText("Please complete all fields on Group Tab");
			warningLabel2.setVisible(true);
		}

		else {

			getRemainingGroupAppsAgent(user, genderCombo, dobChooser,
					dobChooser2, dobChooser3, dobChooser4, dobChooser5, fname,
					lname, phone, email, passport, address, fname2, lname2,
					phone2, email2, passport2, address2, fname3, lname3,
					phone3, email3, passport3, address3, fname4, lname4,
					phone4, email4, passport4, address4, fname5, lname5,
					phone5, email5, address5, passport5, genderCombo2,
					genderCombo3, genderCombo4, genderCombo5, durationLabel,
					warningLabel2);

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
			String currentDate = format.format(new Date(System
					.currentTimeMillis()));

			int i = -1;

			for (Application a : user.getGroupApp()) {
				i++;
				a.setAgent(user.getUsername());
				a.setAppDate(currentDate);
				a.setAppStatus("New");
				a.setArrivalDate(Controller.formatDate(arrChooser.getDate()));
				a.setCampus(campusCombo.getSelectedItem().toString());
				a.setCountry(countryCombo.getSelectedItem().toString());
				a.setCourse(courseCombo.getSelectedItem().toString());
				a.setDepartureDate(Controller.formatDate(depChooser.getDate()));
				a.setDuration(durationLabel.getText().substring(0,
						durationLabel.getText().indexOf("D") - 1));
				a.setLastContacted(currentDate);
				a.setNationality(nationCombo.getSelectedItem().toString());
				a.setPaymentStatus("Not Paid");
				a.setSalesAdvisor(user.getStaffMember());
				a.setSource("agent");
				a.setTotalPrice("0");
				a.setVisaStatus("-");
				a.setWeeklyPrice("0");

			}

			EventQueue.invokeLater(new Runnable() {
				public void run() {

					try {

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());
						user.setCommand("addgroupapp");
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						if (user2.getCommand().equals("success")) {

							user.setGroupApp(user2.getGroupApp());

							for (Application a : user.getGroupApp()) {

								user.getUserApplications().add(0, a);
							}
							user.setGroupApp(new ArrayList<Application>());
							ViewApplications va = new ViewApplications(user);
							frame.dispose();
						}

						else {
							warningLabel2
									.setText("Add application failed. Please check fields and try again.");
							user.setGroupApp(new ArrayList<Application>());
						}

					}

					catch (Exception ex) {

					}
				}
			});
		}

	}

	/**
	 * This method is called by the addGroupApplicationAgent method. 
	 * 
	 * The method firstly checks that all fields have been populated for the applications
	 * to be added, and then creates application objects for them based on the data
	 * and the group data, before adding them to the users group applications arraylist
	 * 
	 * @param - swing components representing the group and individual application params, user
	 */
	public static void getRemainingGroupAppsAgent(User user,
			JComboBox genderCombo, JDateChooser dobChooser,
			JDateChooser dobChooser2, JDateChooser dobChooser3,
			JDateChooser dobChooser4, JDateChooser dobChooser5,
			JTextField fname, JTextField lname, JTextField phone,
			JTextField email, JTextField passport, JTextField address,
			JTextField fname2, JTextField lname2, JTextField phone2,
			JTextField email2, JTextField passport2, JTextField address2,
			JTextField fname3, JTextField lname3, JTextField phone3,
			JTextField email3, JTextField passport3, JTextField address3,
			JTextField fname4, JTextField lname4, JTextField phone4,
			JTextField email4, JTextField passport4, JTextField address4,
			JTextField fname5, JTextField lname5, JTextField phone5,
			JTextField email5, JTextField address5, JTextField passport5,
			JComboBox genderCombo2, JComboBox genderCombo3,
			JComboBox genderCombo4, JComboBox genderCombo5,
			JLabel durationLabel, JLabel warningLabel2) {

		if (fname.getText().equals("") == false
				&& lname.getText().equals("") == false
				&& phone.getText().equals("") == false
				&& email.getText().equals("") == false
				&& passport.getText().equals("") == false
				&& address.getText().equals("") == false) {

			Application app = new Application(0, fname.getText(),
					lname.getText(), null, null, null, null, null, genderCombo
							.getSelectedItem().toString(), passport.getText(),
					null, null, null, null, null, address.getText(), null,
					null, null, Controller.formatDate(dobChooser.getDate()),
					phone.getText(), email.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app);

		}

		if (fname2.getText().equals("") == false
				&& lname2.getText().equals("") == false
				&& phone2.getText().equals("") == false
				&& email2.getText().equals("") == false
				&& passport2.getText().equals("") == false
				&& address2.getText().equals("") == false) {

			Application app2 = new Application(0, fname2.getText(),
					lname2.getText(), null, null, null, null, null,
					genderCombo2.getSelectedItem().toString(),
					passport2.getText(), null, null, null, null, null,
					address2.getText(), null, null, null,
					Controller.formatDate(dobChooser2.getDate()),
					phone2.getText(), email2.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app2);

		} else {
			warningLabel2.setText("Please complete all fields");
			warningLabel2.setVisible(true);
		}

		if (fname3.getText().equals("") == false
				&& lname3.getText().equals("") == false
				&& phone3.getText().equals("") == false
				&& email3.getText().equals("") == false
				&& passport3.getText().equals("") == false
				&& address3.getText().equals("") == false) {

			Application app3 = new Application(0, fname3.getText(),
					lname3.getText(), null, null, null, null, null,
					genderCombo3.getSelectedItem().toString(),
					passport3.getText(), null, null, null, null, null,
					address3.getText(), null, null, null,
					Controller.formatDate(dobChooser3.getDate()),
					phone3.getText(), email3.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app3);

		} else {
			warningLabel2.setText("Please complete all fields");
			warningLabel2.setVisible(true);
		}

		if (fname4.getText().equals("") == false
				&& lname4.getText().equals("") == false
				&& phone4.getText().equals("") == false
				&& email4.getText().equals("") == false
				&& passport4.getText().equals("") == false
				&& address4.getText().equals("") == false) {

			Application app4 = new Application(0, fname4.getText(),
					lname4.getText(), null, null, null, null, null,
					genderCombo4.getSelectedItem().toString(),
					passport4.getText(), null, null, null, null, null,
					address4.getText(), null, null, null,
					Controller.formatDate(dobChooser.getDate()),
					phone4.getText(), email4.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app4);

		} else {
			warningLabel2.setText("Please complete all fields");
			warningLabel2.setVisible(true);
		}

		if (fname5.getText().equals("") == false
				&& lname5.getText().equals("") == false
				&& phone5.getText().equals("") == false
				&& email5.getText().equals("") == false
				&& passport5.getText().equals("") == false
				&& address5.getText().equals("") == false) {

			Application app5 = new Application(0, fname5.getText(),
					lname5.getText(), null, null, null, null, null,
					genderCombo5.getSelectedItem().toString(),
					passport5.getText(), null, null, null, null, null,
					address5.getText(), null, null, null,
					Controller.formatDate(dobChooser5.getDate()),
					phone5.getText(), email5.getText(), null, null, null, null,
					null);

			user.getGroupApp().add(app5);

		} else {
			warningLabel2.setText("Please complete all fields");
			warningLabel2.setVisible(true);
		}

	}

	/**
	 * This method adds a note for a given application
	 * 
	 * Firstly it sets the data and time stamps for the note to be added, creating a 
	 * new note object based on the relevant fields. 
	 * 
	 * After this it sets the user objects note to be added as the newly created note and 
	 * then sets the appropriate user command to be read by the server, before writing 
	 * the user object to the server. 
	 * 
	 * It then receives a response back from the server and reads the relevant command 
	 * in order to determine if the transaction was successful. 
	 * 
	 * If successful it updates the users application notes arraylist, adding the new note, 
	 * and then displays the most recent notes from the arraylist, up to a maximum of three.
	 * @param - user, swing components for the notes, note area for the note to be added, application
	 */
	public static void addAppNote(User user, JTextArea note, Application app,
			JTextArea note1Area, JTextArea note2Area, JTextArea note3Area,
			JLabel noteAuthor, JLabel noteDate, JLabel noteTime,
			JLabel noteAuthor2, JLabel noteDate2, JLabel noteTime2,
			JLabel noteAuthor3, JLabel noteDate3, JLabel noteTime3) {

		DateFormat formatDate = new SimpleDateFormat("dd/MM/yy");
		String currentDate = formatDate.format(new Date(System
				.currentTimeMillis()));
		DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
		String currentTime = formatTime.format(new Date(System
				.currentTimeMillis()));
		user.setNoteToBeAdded(new Notes(app.getUid(), currentDate, note
				.getText(), 0, user.getUsername(), currentTime));
		user.setApplicationToBeAdded(app);

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {

					socketConnection = new Socket("127.0.0.1", 5432);
					clientOut = new ObjectOutputStream(socketConnection
							.getOutputStream());
					clientIn = new ObjectInputStream(socketConnection
							.getInputStream());
					user.setCommand("addappnote");
					clientOut.writeObject(user);
					try {
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						System.out.println("Read In");
						if (user2.getCommand().equals("success")) {
							System.out.println("Command recognized as success");
							user.setNoteToBeAdded(user2.getNoteToBeAdded());
							System.out.println(user2.getNoteToBeAdded()
									.getNote());


							app.getApplicationNotes().add(0,
									user.getNoteToBeAdded());

							note1Area.setText(app.getApplicationNotes().get(0)
									.getNote());
							noteAuthor.setText(app.getApplicationNotes().get(0)
									.getAuthor());
							noteDate.setText(app.getApplicationNotes().get(0)
									.getDateAdded());
							noteTime.setText(app.getApplicationNotes().get(0)
									.getTime());

							if (app.getApplicationNotes().size() > 1) {
								System.out.println("size is bigger than 1");
								note2Area.setText(app.getApplicationNotes()
										.get(1).getNote());
								noteAuthor2.setText(app.getApplicationNotes()
										.get(1).getAuthor());
								noteDate2.setText(app.getApplicationNotes()
										.get(1).getDateAdded());
								noteTime2.setText(app.getApplicationNotes()
										.get(1).getTime());
							}

							if (app.getApplicationNotes().size() > 2) {
								note3Area.setText(app.getApplicationNotes()
										.get(2).getNote());
								noteAuthor3.setText(app.getApplicationNotes()
										.get(2).getAuthor());
								noteDate3.setText(app.getApplicationNotes()
										.get(2).getDateAdded());
								noteTime3.setText(app.getApplicationNotes()
										.get(2).getTime());
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				catch (Exception ex) {

				}
			}
		});

	}
	/**
	 * This method adds a note for a given agent
	 * 
	 * Firstly it sets the data and time stamps for the note to be added, creating a 
	 * new note object based on the relevant fields. 
	 * 
	 * After this it sets the user objects note to be added as the newly created note and 
	 * then sets the appropriate user command to be read by the server, before writing 
	 * the user object to the server. 
	 * 
	 * It then receives a response back from the server and reads the relevant command 
	 * in order to determine if the transaction was successful. 
	 * 
	 * If successful it updates the users agent notes arraylist, adding the new note, 
	 * and then displays the most recent notes from the arraylist, up to a maximum of three.
	 * @param - user, swing components for the notes, note area for the note to be added, agent
	 */
	public static void addAgentNote(User user, JTextArea note, Agent agent,
			JTextArea note1Area, JTextArea note2Area, JTextArea note3Area,
			JLabel noteAuthor, JLabel noteDate, JLabel noteTime,
			JLabel noteAuthor2, JLabel noteDate2, JLabel noteTime2,
			JLabel noteAuthor3, JLabel noteDate3, JLabel noteTime3) {

		DateFormat formatDate = new SimpleDateFormat("dd/MM/yy");
		String currentDate = formatDate.format(new Date(System
				.currentTimeMillis()));
		DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
		String currentTime = formatTime.format(new Date(System
				.currentTimeMillis()));
		user.setAgentNoteToBeAdded(new AgentNote(user.getCommand2(),
				currentDate, note.getText(), 0, user.getUsername(), currentTime));

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {

					socketConnection = new Socket("127.0.0.1", 5432);
					clientOut = new ObjectOutputStream(socketConnection
							.getOutputStream());
					clientIn = new ObjectInputStream(socketConnection
							.getInputStream());
					user.setCommand("addagentnote");
					clientOut.writeObject(user);
					try {
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						System.out.println("Read In");
						if (user2.getCommand().equals("success")) {
							System.out.println("Command recognized as success");
							user.setAgentNoteToBeAdded(user2
									.getAgentNoteToBeAdded());
							System.out.println(user2.getAgentNoteToBeAdded()
									.getNote());

							agent.getNotes().add(0,
									user.getAgentNoteToBeAdded());
							System.out.println("added");

							note1Area
									.setText(agent.getNotes().get(0).getNote());
							noteAuthor.setText(agent.getNotes().get(0)
									.getAuthor());
							noteDate.setText(agent.getNotes().get(0)
									.getDateAdded());
							noteTime.setText(agent.getNotes().get(0).getTime());

							if (agent.getNotes().size() > 1) {
								System.out.println("size is bigger than 1");
								note2Area.setText(agent.getNotes().get(1)
										.getNote());
								noteAuthor2.setText(agent.getNotes().get(1)
										.getAuthor());
								noteDate2.setText(agent.getNotes().get(1)
										.getDateAdded());
								noteTime2.setText(agent.getNotes().get(1)
										.getTime());
							}

							if (agent.getNotes().size() > 2) {
								note3Area.setText(agent.getNotes().get(2)
										.getNote());
								noteAuthor3.setText(agent.getNotes().get(2)
										.getAuthor());
								noteDate3.setText(agent.getNotes().get(2)
										.getDateAdded());
								noteTime3.setText(agent.getNotes().get(2)
										.getTime());
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				catch (Exception ex) {

				}
			}
		});

	}

	/**
	 * This method sets the most recent notes to be displayed when a user is on the relevant screen
	 * up to a maximum of the 3 most recent notes to be displayed 
	 * 
	 * The method checks if there is a note to display, and if so updates the relevant components to 
	 * display it, before repeating this for the next two notes in the list
	 * 
	 * @param - ArrayList of notes to be displayed, the jlabels for displaying the note data
	 */
	public static void generateNotes(ArrayList<Notes> notes,
			JTextArea note1Area, JTextArea note2Area, JTextArea note3Area,
			JLabel noteAuthor, JLabel noteDate, JLabel noteTime,
			JLabel noteAuthor2, JLabel noteDate2, JLabel noteTime2,
			JLabel noteAuthor3, JLabel noteDate3, JLabel noteTime3) {

		System.out.println("Called: " + notes.size());

		if (notes.size() > 0) {
			System.out.println("If > 0 Activated");
			note1Area.setText(notes.get(0).getNote());
			noteAuthor.setText(notes.get(0).getAuthor());
			noteDate.setText(notes.get(0).getDateAdded());
			noteTime.setText(notes.get(0).getTime());
		}

		if (notes.size() > 1) {
			System.out.println("size is bigger than 1");
			note2Area.setText(notes.get(1).getNote());
			noteAuthor2.setText(notes.get(1).getAuthor());
			noteDate2.setText(notes.get(1).getDateAdded());
			noteTime2.setText(notes.get(1).getTime());
		}

		if (notes.size() > 2) {
			note3Area.setText(notes.get(2).getNote());
			noteAuthor3.setText(notes.get(2).getAuthor());
			noteDate3.setText(notes.get(2).getDateAdded());
			noteTime3.setText(notes.get(2).getTime());
		}

	}
	/**
	 * This method sets the most recent agent notes to be displayed when a user is on the relevant screen
	 * up to a maximum of the 3 most recent agent notes to be displayed 
	 * 
	 * The method checks if there is a agent note to display, and if so updates the relevant components to 
	 * display it, before repeating this for the next two notes in the list
	 * 
	 * @param - ArrayList of agent notes to be displayed, the jlabels for displaying the agent note data
	 */
	public static void generateNotesAgent(ArrayList<AgentNote> notes,
			JTextArea note1Area, JTextArea note2Area, JTextArea note3Area,
			JLabel noteAuthor, JLabel noteDate, JLabel noteTime,
			JLabel noteAuthor2, JLabel noteDate2, JLabel noteTime2,
			JLabel noteAuthor3, JLabel noteDate3, JLabel noteTime3) {

		if (notes.size() > 0) {

			note1Area.setText(notes.get(0).getNote());
			noteAuthor.setText(notes.get(0).getAuthor());
			noteDate.setText(notes.get(0).getDateAdded());
			noteTime.setText(notes.get(0).getTime());
		}

		if (notes.size() > 1) {
			System.out.println("size is bigger than 1");
			note2Area.setText(notes.get(1).getNote());
			noteAuthor2.setText(notes.get(1).getAuthor());
			noteDate2.setText(notes.get(1).getDateAdded());
			noteTime2.setText(notes.get(1).getTime());
		}

		if (notes.size() > 2) {
			note3Area.setText(notes.get(2).getNote());
			noteAuthor3.setText(notes.get(2).getAuthor());
			noteDate3.setText(notes.get(2).getDateAdded());
			noteTime3.setText(notes.get(2).getTime());
		}

	}

	/**
	 * This method is called when the users wants to browse through notes. 
	 * 
	 * Indexes are used to keep track of the 3 notes currently being displayed. 
	 * 
	 * The method checks whether there are any more notes, and if so updates 
	 * the relevant components to display the new data
	 * 
	 * @param - ArrayList of notes to be displayed, the jlabels for displaying the note data, indexes for tracking the notes
	 */
	public static void updateNotesRight(int leftIndex, int middleIndex,
			int rightIndex, ArrayList<Notes> notes, JTextArea note1Area,
			JTextArea note2Area, JTextArea note3Area, JLabel noteAuthor,
			JLabel noteDate, JLabel noteTime, JLabel noteAuthor2,
			JLabel noteDate2, JLabel noteTime2, JLabel noteAuthor3,
			JLabel noteDate3, JLabel noteTime3) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (rightIndex < notes.size()) {

					note1Area.setText(notes.get(leftIndex).getNote());
					noteAuthor.setText(notes.get(leftIndex).getAuthor());
					noteDate.setText(notes.get(leftIndex).getDateAdded());
					noteTime.setText(notes.get(leftIndex).getTime());

					note2Area.setText(notes.get(rightIndex).getNote());
					noteAuthor2.setText(notes.get(rightIndex).getAuthor());
					noteDate2.setText(notes.get(rightIndex).getDateAdded());
					noteTime2.setText(notes.get(rightIndex).getTime());

					if (notes.size() > rightIndex + 1) {
						note3Area.setText(notes.get(rightIndex + 1).getNote());
						noteAuthor3.setText(notes.get(rightIndex + 1)
								.getAuthor());
						noteDate3.setText(notes.get(rightIndex + 1)
								.getDateAdded());
						noteTime3.setText(notes.get(rightIndex + 1).getTime());
					}
				}

			}

		});

	}

	/**
	 * This method is called when the users wants to browse through agent notes. 
	 * 
	 * Indexes are used to keep track of the 3 agent notes currently being displayed. 
	 * 
	 * The method checks whether there are any more agent notes, and if so updates 
	 * the relevant components to display the new data
	 * 
	 * @param - ArrayList of notes to be displayed, the jlabels for displaying the note data, indexes for tracking the agent notes
	 */
	public static void updateNotesRightAgent(int leftIndex, int middleIndex,
			int rightIndex, ArrayList<AgentNote> notes, JTextArea note1Area,
			JTextArea note2Area, JTextArea note3Area, JLabel noteAuthor,
			JLabel noteDate, JLabel noteTime, JLabel noteAuthor2,
			JLabel noteDate2, JLabel noteTime2, JLabel noteAuthor3,
			JLabel noteDate3, JLabel noteTime3) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (rightIndex < notes.size()) {

					note1Area.setText(notes.get(leftIndex).getNote());
					noteAuthor.setText(notes.get(leftIndex).getAuthor());
					noteDate.setText(notes.get(leftIndex).getDateAdded());
					noteTime.setText(notes.get(leftIndex).getTime());

					note2Area.setText(notes.get(rightIndex).getNote());
					noteAuthor2.setText(notes.get(rightIndex).getAuthor());
					noteDate2.setText(notes.get(rightIndex).getDateAdded());
					noteTime2.setText(notes.get(rightIndex).getTime());

					if (notes.size() > rightIndex + 1) {
						note3Area.setText(notes.get(rightIndex + 1).getNote());
						noteAuthor3.setText(notes.get(rightIndex + 1)
								.getAuthor());
						noteDate3.setText(notes.get(rightIndex + 1)
								.getDateAdded());
						noteTime3.setText(notes.get(rightIndex + 1).getTime());
					}
				}

			}

		});

	}

	/**
	 * This method is called when the users wants to browse through notes. 
	 * 
	 * Indexes are used to keep track of the 3 notes currently being displayed. 
	 * 
	 * The method checks whether there are any more notes, and if so updates 
	 * the relevant components to display the new data
	 * 
	 * @param - ArrayList of notes to be displayed, the jlabels for displaying the note data, indexes for tracking the notes
	 */
	public static void updateNotesLeft(int leftIndex, int middleIndex,
			int rightIndex, ArrayList<Notes> notes, JTextArea note1Area,
			JTextArea note2Area, JTextArea note3Area, JLabel noteAuthor,
			JLabel noteDate, JLabel noteTime, JLabel noteAuthor2,
			JLabel noteDate2, JLabel noteTime2, JLabel noteAuthor3,
			JLabel noteDate3, JLabel noteTime3) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (leftIndex >= 0) {

					note1Area.setText(notes.get(leftIndex).getNote());
					noteAuthor.setText(notes.get(leftIndex).getAuthor());
					noteDate.setText(notes.get(leftIndex).getDateAdded());
					noteTime.setText(notes.get(leftIndex).getTime());

					note2Area.setText(notes.get(rightIndex).getNote());
					noteAuthor2.setText(notes.get(rightIndex).getAuthor());
					noteDate2.setText(notes.get(rightIndex).getDateAdded());
					noteTime2.setText(notes.get(rightIndex).getTime());

					note3Area.setText(notes.get(middleIndex).getNote());
					noteAuthor3.setText(notes.get(middleIndex).getAuthor());
					noteDate3.setText(notes.get(middleIndex).getDateAdded());
					noteTime3.setText(notes.get(middleIndex).getTime());

				}

			}

		});

	}

	/**
	 * This method is called when the users wants to browse through agent notes. 
	 * 
	 * Indexes are used to keep track of the 3 agent notes currently being displayed. 
	 * 
	 * The method checks whether there are any more agent notes, and if so updates 
	 * the relevant components to display the new data
	 * 
	 * @param - ArrayList of notes to be displayed, the jlabels for displaying the note data, indexes for tracking the agent notes
	 */
	public static void updateNotesLeftAgent(int leftIndex, int middleIndex,
			int rightIndex, ArrayList<AgentNote> notes, JTextArea note1Area,
			JTextArea note2Area, JTextArea note3Area, JLabel noteAuthor,
			JLabel noteDate, JLabel noteTime, JLabel noteAuthor2,
			JLabel noteDate2, JLabel noteTime2, JLabel noteAuthor3,
			JLabel noteDate3, JLabel noteTime3) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (leftIndex >= 0) {

					note1Area.setText(notes.get(leftIndex).getNote());
					noteAuthor.setText(notes.get(leftIndex).getAuthor());
					noteDate.setText(notes.get(leftIndex).getDateAdded());
					noteTime.setText(notes.get(leftIndex).getTime());

					note2Area.setText(notes.get(rightIndex).getNote());
					noteAuthor2.setText(notes.get(rightIndex).getAuthor());
					noteDate2.setText(notes.get(rightIndex).getDateAdded());
					noteTime2.setText(notes.get(rightIndex).getTime());

					note3Area.setText(notes.get(middleIndex).getNote());
					noteAuthor3.setText(notes.get(middleIndex).getAuthor());
					noteDate3.setText(notes.get(middleIndex).getDateAdded());
					noteTime3.setText(notes.get(middleIndex).getTime());

				}

			}

		});

	}

	/**
	 * This method adds a note for a given enquiry
	 * 
	 * Firstly it sets the data and time stamps for the note to be added, creating a 
	 * new note object based on the relevant fields. 
	 * 
	 * After this it sets the user objects note to be added as the newly created note and 
	 * then sets the appropriate user command to be read by the server, before writing 
	 * the user object to the server. 
	 * 
	 * It then receives a response back from the server and reads the relevant command 
	 * in order to determine if the transaction was successful. 
	 * 
	 * If successful it updates the users enquiry notes arraylist, adding the new note, 
	 * and then displays the most recent notes from the arraylist, up to a maximum of three.
	 * 
	 * @param - user, swing components for the notes, note area for the note to be added, enquiry
	 */
	public static void addEnqNote(User user, JTextArea note, Enquiry enq,
			JTextArea note1Area, JTextArea note2Area, JTextArea note3Area,
			JLabel noteAuthor, JLabel noteDate, JLabel noteTime,
			JLabel noteAuthor2, JLabel noteDate2, JLabel noteTime2,
			JLabel noteAuthor3, JLabel noteDate3, JLabel noteTime3) {

		DateFormat formatDate = new SimpleDateFormat("dd/MM/yy");
		String currentDate = formatDate.format(new Date(System
				.currentTimeMillis()));
		DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
		String currentTime = formatTime.format(new Date(System
				.currentTimeMillis()));
		user.setNoteToBeAdded(new Notes(enq.getEid(), currentDate, note
				.getText(), 0, user.getUsername(), currentTime));
		user.setEnquiryToBeAdded(enq);

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {

					socketConnection = new Socket("127.0.0.1", 5432);
					clientOut = new ObjectOutputStream(socketConnection
							.getOutputStream());
					clientIn = new ObjectInputStream(socketConnection
							.getInputStream());
					user.setCommand("addenqnote");
					clientOut.writeObject(user);
					try {
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						System.out.println("Read In");
						if (user2.getCommand().equals("success")) {

							user.setNoteToBeAdded(user2.getNoteToBeAdded());

							enq.getEnquiryNotes().add(0,
									user.getNoteToBeAdded());

							note1Area.setText(enq.getEnquiryNotes().get(0)
									.getNote());
							noteAuthor.setText(enq.getEnquiryNotes().get(0)
									.getAuthor());
							noteDate.setText(enq.getEnquiryNotes().get(0)
									.getDateAdded());
							noteTime.setText(enq.getEnquiryNotes().get(0)
									.getTime());

							if (enq.getEnquiryNotes().size() > 1) {
								System.out.println("size is bigger than 1");
								note2Area.setText(enq.getEnquiryNotes().get(1)
										.getNote());
								noteAuthor2.setText(enq.getEnquiryNotes()
										.get(1).getAuthor());
								noteDate2.setText(enq.getEnquiryNotes().get(1)
										.getDateAdded());
								noteTime2.setText(enq.getEnquiryNotes().get(1)
										.getTime());
							}

							if (enq.getEnquiryNotes().size() > 2) {
								note3Area.setText(enq.getEnquiryNotes().get(2)
										.getNote());
								noteAuthor3.setText(enq.getEnquiryNotes()
										.get(2).getAuthor());
								noteDate3.setText(enq.getEnquiryNotes().get(2)
										.getDateAdded());
								noteTime3.setText(enq.getEnquiryNotes().get(2)
										.getTime());
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				catch (Exception ex) {

				}
			}
		});

	}

	/**
	 * This method is used when uploading a file to the server
	 * 
	 * It creates a ByteArrayOutputStream, before creating an inputstream
	 * corresponding the given users abstract file object. 
	 * 
	 * A byte array is then created and set to the size of the abstract files 
	 * length. A while loop is then used to read the file through the inputstream
	 * into the byte array. Following this the user objects byte array is set as the 
	 * byte array created representing the file, before the appropriate user command 
	 * is set and the user object is written out to the server. 
	 * 
	 * @param - User user
	 * @throws FileNotFoundException
	 */
	public static void uploadFile(User user) throws FileNotFoundException {

		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			socketConnection = new Socket("127.0.0.1", 5432);
			clientOut = new ObjectOutputStream(
					socketConnection.getOutputStream());
			clientIn = new ObjectInputStream(socketConnection.getInputStream());

			inputStream = new FileInputStream(user.getFile());

			byte[] fileBytes = new byte[(int) user.getFile().length()];
			

			int bytesRead;
			while ((bytesRead = inputStream.read(fileBytes)) != -1) {
				baos.write(fileBytes, 0, bytesRead);
			}
			user.setFileBytes(fileBytes);

			user.setCommand("file");
			clientOut.writeObject(user);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 *  This method is used when downloading a file from the server
	 * 
	 *  The relevant user command is set, before the user object is sent
	 *  to the server.
	 *  
	 *  The user object is then received back from the server with the 
	 *  requested file attached as a byte array. 
	 *  
	 *  A FileOutputStream is then used to write the byte array into a file
	 *  at the specified location.
	 * 
	 * @param - User user, Label for a warning
	 * @throws FileNotFoundException
	 */
	public static void saveFile(User user, JLabel warningLabel)
			throws FileNotFoundException {

		try {

			socketConnection = new Socket("127.0.0.1", 5432);
			clientOut = new ObjectOutputStream(
					socketConnection.getOutputStream());
			clientIn = new ObjectInputStream(socketConnection.getInputStream());
			user.setCommand("filesave");
			clientOut.writeObject(user);

			User user2 = (User) clientIn.readObject();
			socketConnection.close();
			System.out.println("Read in, user2 command is: "
					+ user2.getCommand());

			if (user2.getCommand().equals("success")) {
				System.out.println("triggered");
				File file = new File(user.getCommand2());
				System.out.println("Set save path to : " + user.getCommand2());
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(user2.getFileBytes());
				fos.flush();
				fos.close();
				warningLabel
						.setText("Documents successfully downloaded to the specified location.");
				warningLabel.setVisible(true);
			}

			else {
				warningLabel.setForeground(Color.RED);
				warningLabel.setText("No file could be located.");
				warningLabel.setVisible(true);
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is used when a user searches for a particular application. 
	 * 
	 * The method takes the search term entered, and checks each application
	 * of the users application list, to see if any of them contain the 
	 * search term. If they do, they are added to the users search apps 
	 * arraylist. 
	 * 
	 * @param - User user
	 */
	public static void searchApps(User user) {
		String searchTerm = user.getCommand().toLowerCase();

		for (Application a : user.getUserApplications()) {

			String fullName = a.getFirstName() + " " + a.getLastName();

			if (a.getFirstName().toLowerCase().contains(searchTerm)
					|| a.getLastName().toLowerCase().contains(searchTerm)
					|| a.getPassportNo().toLowerCase().contains(searchTerm)
					|| searchTerm.equals(fullName.toLowerCase())) {

				user.getSearchApps().add(a);
			}
		}

	}

	/**
	 * This method is used when a user searches for a particular enquiry. 
	 * 
	 * The method takes the search term entered, and checks each enquiry
	 * of the users enquiry list, to see if any of them contain the 
	 * search term. If they do, they are added to the users search enqs 
	 * arraylist. 
	 * 
	 * @param - User user
	 */
	public static void searchEnqs(User user) {
		String searchTerm = user.getCommand().toLowerCase();

		for (Enquiry e : user.getEnquiries()) {
			String fullName = e.getfName() + " " + e.getlName();

			if (e.getfName().toLowerCase().contains(searchTerm)
					|| e.getlName().toLowerCase().contains(searchTerm)
					|| searchTerm.equals(fullName.toLowerCase())) {

				user.getSearchEnqs().add(e);
			}
		}

	}

	/**
	 * This method is used when a user searches for a particular agent. 
	 * 
	 * The method takes the search term entered, and checks each agent
	 * of the users agents list, to see if any of them contain the 
	 * search term. If they do, they are added to the users search agents 
	 * arraylist. 
	 * 
	 * @param - User user
	 */
	public static void searchAgents(User user) {
		String searchTerm = user.getCommand().toLowerCase();

		for (Agent a : user.getAgentsList()) {

			if (a.getCompanyName().toLowerCase().contains(searchTerm)
					|| a.getFirstName().toLowerCase().contains(searchTerm)
					|| a.getLastName().toLowerCase().contains(searchTerm)) {

				user.getSearchAgents().add(a);
			}
		}

	}

	/**
	 * This method is used to alter the display accordingly if the user is an agent. 
	 * 
	 * The method defines the jcomponents, their positioning and layout accordingly
	 * and adds the appropriate listeners and action performed methods 
	 * 
	 * @param - User user
	 */
	public static void createAgentView(JFrame frame, JTextField searchBox,
			JLabel warningLabelSearch, User user) {
		RoundedPanel roundedPanelM = new RoundedPanel();
		roundedPanelM.setLayout(null);
		roundedPanelM.setForeground(new Color(142, 39, 148));
		roundedPanelM.setBackground(Color.WHITE);
		roundedPanelM.setBounds(5, 107, 245, 474);
		frame.getContentPane().add(roundedPanelM);

		searchBox.setBounds(260, 59, 429, 31);

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
					AddGroupApplicationAgents agaa = new AddGroupApplicationAgents(
							user);
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
				EnrolledStudentsViewAgents esv = new EnrolledStudentsViewAgents(
						user);
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

					String url = "http://www.google.com/";

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

					String url = "http://www.google.com";

					Desktop dt = Desktop.getDesktop();
					URI uri = new URI(url);
					dt.browse(uri.resolve(uri));

				} catch (URISyntaxException ex) {

				} catch (IOException ex) {

				}

			}
		});

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (searchBox.getText().equals("")) {

					warningLabelSearch.setForeground(Color.RED);
					warningLabelSearch
							.setFont(new Font("Tahoma", Font.BOLD, 12));
					warningLabelSearch.setBounds(369, 64, 211, 20);
					warningLabelSearch.setVisible(true);

				}

				else {
					user.setCommand(searchBox.getText());
					user.setSearchApps(new ArrayList<Application>());
					ApplicationSearch as = new ApplicationSearch(user);
					frame.dispose();
				}

			}

		});
		searchButton.setForeground(Color.WHITE);
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		searchButton.setBackground(new Color(142, 39, 148));
		searchButton.setBounds(699, 58, 73, 31);
		frame.getContentPane().add(searchButton);

	}

	/**
	 * This method is used to alter the display accordingly if the user is a staff member . 
	 * 
	 * The method defines the jcomponents, their positioning and layout accordingly
	 * and adds the appropriate listeners and action performed methods 
	 * 
	 * @param - User user
	 */
	public static void createStaffView(JFrame frame, JLabel warningLabelSearch,
			JTextField searchBox, User user) {
		RoundedPanel roundedPanelM = new RoundedPanel();
		roundedPanelM.setLayout(null);
		roundedPanelM.setForeground(new Color(142, 39, 148));
		roundedPanelM.setBackground(Color.WHITE);
		roundedPanelM.setBounds(15, 101, 245, 474);
		frame.getContentPane().add(roundedPanelM);

		JComboBox searchCombo = new JComboBox();
		searchCombo.setModel(new DefaultComboBoxModel(new String[] {
				"Applications", "Enquiries", "Agents" }));
		searchCombo.setForeground(Color.WHITE);
		searchCombo.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchCombo.setBackground(new Color(142, 39, 148));
		searchCombo.setBounds(663, 63, 102, 25);
		frame.getContentPane().add(searchCombo);

		JLabel label = new JLabel("Dashboard");
		label.setForeground(new Color(142, 39, 148));
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(76, 11, 98, 17);
		roundedPanelM.add(label);

		JButton button = new JButton("Unsigned Agents");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		button.setBackground(new Color(142, 39, 148));
		button.setBounds(10, 393, 220, 32);
		roundedPanelM.add(button);

		JButton button_1 = new JButton("Agents");
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_1.setBackground(new Color(142, 39, 148));
		button_1.setBounds(10, 350, 220, 32);
		roundedPanelM.add(button_1);

		JButton button_2 = new JButton("Enrolled Students");
		button_2.setForeground(Color.WHITE);
		button_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_2.setBackground(new Color(142, 39, 148));
		button_2.setBounds(10, 307, 220, 32);
		roundedPanelM.add(button_2);

		JButton button_3 = new JButton("View Applications");
		button_3.setForeground(Color.WHITE);
		button_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_3.setBackground(new Color(142, 39, 148));
		button_3.setBounds(10, 264, 220, 32);
		roundedPanelM.add(button_3);

		JButton button_4 = new JButton("Add Group Application");
		button_4.setForeground(Color.WHITE);
		button_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_4.setBackground(new Color(142, 39, 148));
		button_4.setBounds(10, 221, 220, 32);
		roundedPanelM.add(button_4);

		JButton button_5 = new JButton("Add Application");
		button_5.setForeground(Color.WHITE);
		button_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_5.setBackground(new Color(142, 39, 148));
		button_5.setBounds(10, 178, 220, 32);
		roundedPanelM.add(button_5);

		JButton button_6 = new JButton("View Enquiries");
		button_6.setForeground(Color.WHITE);
		button_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_6.setBackground(new Color(142, 39, 148));
		button_6.setBounds(10, 135, 220, 32);
		roundedPanelM.add(button_6);

		JButton button_7 = new JButton("Add Enquiry");
		button_7.setForeground(Color.WHITE);
		button_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_7.setBackground(new Color(142, 39, 148));
		button_7.setBounds(10, 92, 220, 32);
		roundedPanelM.add(button_7);

		JButton button_8 = new JButton("Home");
		button_8.setForeground(Color.WHITE);
		button_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_8.setBackground(new Color(142, 39, 148));
		button_8.setBounds(10, 49, 220, 32);
		roundedPanelM.add(button_8);

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
					AddGroupApplicationStaff a = new AddGroupApplicationStaff(
							user);
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

				EnrolledStudentsViewStaff esv = new EnrolledStudentsViewStaff(
						user);
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

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (searchBox.getText().equals("")) {

					warningLabelSearch.setForeground(Color.RED);
					warningLabelSearch
							.setFont(new Font("Tahoma", Font.BOLD, 12));
					warningLabelSearch.setBounds(369, 64, 211, 20);
					warningLabelSearch.setVisible(true);

				}

				else {
					if (searchCombo.getSelectedItem().equals("Applications")) {
						user.setCommand(searchBox.getText());
						user.setSearchApps(new ArrayList<Application>());
						ApplicationSearch as = new ApplicationSearch(user);
						frame.dispose();
					}

					else if (searchCombo.getSelectedItem().equals("Enquiries")) {
						user.setCommand(searchBox.getText());
						user.setSearchEnqs(new ArrayList<Enquiry>());
						EnquirySearch es = new EnquirySearch(user);
						frame.dispose();
					} else {
						user.setCommand(searchBox.getText());
						user.setSearchAgents(new ArrayList<Agent>());
						AgentSearch as = new AgentSearch(user);
						frame.dispose();
					}
				}
			}
		});
		searchButton.setForeground(Color.WHITE);
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		searchButton.setBackground(new Color(142, 39, 148));
		searchButton.setBounds(580, 59, 73, 31);
		frame.getContentPane().add(searchButton);
	}

	/**
	 * This method is used to get the latest files available 
	 * for an agent to download. It is called each time an agent
	 * opens the downloads view, and ensures that if a new 
	 * documents has been added that they have the latest document to view. 
	 * 
	 * @param - User user
	 */
	public static void updateDownloads(User user) {

		user.setCommand("updatedownloads");

		try {
			socketConnection = new Socket("127.0.0.1", 5432);
			clientOut = new ObjectOutputStream(
					socketConnection.getOutputStream());
			clientIn = new ObjectInputStream(socketConnection.getInputStream());

			clientOut.writeObject(user);

			try {
				User user2 = (User) clientIn.readObject();
				if (user2.getCommand().equals("success")) {
					user.setFileNames(user2.getFileNames());
				}

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to alter the display accordingly if the user is an admin staff member. 
	 * 
	 * The method defines the jcomponents, their positioning and layout accordingly
	 * and adds the appropriate listeners and action performed methods 
	 * 
	 * @param - User user
	 */
	public static void createAdminView(JFrame frame, JLabel warningLabelSearch,
			JTextField searchBox, User user) {
		RoundedPanel roundedPanelM = new RoundedPanel();
		roundedPanelM.setLayout(null);
		roundedPanelM.setForeground(new Color(142, 39, 148));
		roundedPanelM.setBackground(Color.WHITE);
		roundedPanelM.setBounds(5, 101, 245, 474);
		frame.getContentPane().add(roundedPanelM);

		JComboBox searchCombo = new JComboBox();
		searchCombo.setModel(new DefaultComboBoxModel(new String[] {
				"Applications", "Enquiries", "Agents" }));
		searchCombo.setForeground(Color.WHITE);
		searchCombo.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchCombo.setBackground(new Color(142, 39, 148));
		searchCombo.setBounds(663, 63, 102, 25);
		frame.getContentPane().add(searchCombo);

		JLabel label = new JLabel("Dashboard");
		label.setForeground(new Color(142, 39, 148));
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(76, 11, 98, 17);
		roundedPanelM.add(label);

		JButton button = new JButton("Unsigned Agents");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		button.setBackground(new Color(142, 39, 148));
		button.setBounds(10, 383, 220, 32);
		roundedPanelM.add(button);

		JButton button_1 = new JButton("Agents");
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_1.setBackground(new Color(142, 39, 148));
		button_1.setBounds(10, 340, 220, 32);
		roundedPanelM.add(button_1);

		JButton button_2 = new JButton("Enrolled Students");
		button_2.setForeground(Color.WHITE);
		button_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_2.setBackground(new Color(142, 39, 148));
		button_2.setBounds(10, 297, 220, 32);
		roundedPanelM.add(button_2);

		JButton button_3 = new JButton("View Applications");
		button_3.setForeground(Color.WHITE);
		button_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_3.setBackground(new Color(142, 39, 148));
		button_3.setBounds(10, 254, 220, 32);
		roundedPanelM.add(button_3);

		JButton button_4 = new JButton("Add Group Application");
		button_4.setForeground(Color.WHITE);
		button_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_4.setBackground(new Color(142, 39, 148));
		button_4.setBounds(10, 211, 220, 32);
		roundedPanelM.add(button_4);

		JButton button_5 = new JButton("Add Application");
		button_5.setForeground(Color.WHITE);
		button_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_5.setBackground(new Color(142, 39, 148));
		button_5.setBounds(10, 168, 220, 32);
		roundedPanelM.add(button_5);

		JButton button_6 = new JButton("View Enquiries");
		button_6.setForeground(Color.WHITE);
		button_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_6.setBackground(new Color(142, 39, 148));
		button_6.setBounds(10, 125, 220, 32);
		roundedPanelM.add(button_6);

		JButton button_7 = new JButton("Add Enquiry");
		button_7.setForeground(Color.WHITE);
		button_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_7.setBackground(new Color(142, 39, 148));
		button_7.setBounds(10, 82, 220, 32);
		roundedPanelM.add(button_7);

		JButton button_8 = new JButton("Home");
		button_8.setForeground(Color.WHITE);
		button_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_8.setBackground(new Color(142, 39, 148));
		button_8.setBounds(10, 39, 220, 32);
		roundedPanelM.add(button_8);

		JButton button_9 = new JButton("Documents");
		button_9.setForeground(Color.WHITE);
		button_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_9.setBackground(new Color(142, 39, 148));
		button_9.setBounds(10, 426, 220, 32);
		roundedPanelM.add(button_9);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (searchBox.getText().equals("")) {

					warningLabelSearch.setForeground(Color.RED);
					warningLabelSearch
							.setFont(new Font("Tahoma", Font.BOLD, 12));
					warningLabelSearch.setBounds(369, 64, 211, 20);
					warningLabelSearch.setVisible(true);

				}

				else {
					if (searchCombo.getSelectedItem().equals("Applications")) {
						user.setCommand(searchBox.getText());
						user.setSearchApps(new ArrayList<Application>());
						ApplicationSearch as = new ApplicationSearch(user);
						frame.dispose();
					}

					else if (searchCombo.getSelectedItem().equals("Enquiries")) {
						user.setCommand(searchBox.getText());
						user.setSearchEnqs(new ArrayList<Enquiry>());
						EnquirySearch es = new EnquirySearch(user);
						frame.dispose();
					} else {
						user.setCommand(searchBox.getText());
						user.setSearchAgents(new ArrayList<Agent>());
						AgentSearch as = new AgentSearch(user);
						frame.dispose();
					}
				}
			}
		});
		searchButton.setForeground(Color.WHITE);
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		searchButton.setBackground(new Color(142, 39, 148));
		searchButton.setBounds(580, 59, 73, 31);
		frame.getContentPane().add(searchButton);

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
					AddGroupApplicationStaff a = new AddGroupApplicationStaff(
							user);
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

				EnrolledStudentsViewStaff esv = new EnrolledStudentsViewStaff(
						user);
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
	
	/**
	 * This method is called to update a given users 
	 * application list. It is used when a user wants to view 
	 * applications to ensure that the latest applications are 
	 * displayed. 
	 *
	 * @param - User user
	 */
	public static void updateApps(User user) {

		Socket socketConnection;
		try {
			socketConnection = new Socket("127.0.0.1", 5432);
			clientOut = new ObjectOutputStream(
					socketConnection.getOutputStream());
			clientIn = new ObjectInputStream(socketConnection.getInputStream());

			user.setCommand("updateagentapps");
			clientOut.writeObject(user);
			User user2 = (User) clientIn.readObject();
			user.setUserApplications(user2.getUserApplications());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void removeUser(User user, JLabel warningLabel){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());

						
						user.setCommand("removeuser");
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						if (user2.getCommand().equals("success")) {
							warningLabel.setForeground(new Color(0, 128, 0));
							warningLabel.setText("User has been successfully removed");
							warningLabel.setVisible(true);
						}

						else {
							warningLabel
									.setText("Unable to remove user at this time. Please try again later.");
						}
				} catch (Exception ex) {

				}
			}
		});
		
	}
	
public static void removeAgent(User user, JLabel warningLabel){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());

						
						user.setCommand("removeagent");
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						if (user2.getCommand().equals("success")) {
							warningLabel.setForeground(new Color(0, 128, 0));
							warningLabel.setText("Agent login has been successfully removed");
							warningLabel.setVisible(true);
						}

						else {
							warningLabel
									.setText("Unable to remove agent login at this time. Please try again later.");
						}
				} catch (Exception ex) {

				}
			}
		});
		
	}
	
	public static void addUser(JTextField firstName, JTextField lastName, JTextField username, JTextField password, JComboBox type, JLabel warningLabel, User user){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					if (firstName.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No first name entered.");
						warningLabel.setVisible(true);

					} else if (lastName.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No last name entered.");
						warningLabel.setVisible(true);
					}

					else if (username.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No username entered.");
						warningLabel.setVisible(true);
					} else if (type.getSelectedItem().equals("Select")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No type selected.");
						warningLabel.setVisible(true);
					}

					else if (password.getText().equals("")) {
						warningLabel.setText("Please complete all fields."
								+ " " + "No password entered.");
						warningLabel.setVisible(true);
					}

					else {

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());

						StaffUser staffUser = new StaffUser(firstName.getText(), lastName.getText(), username.getText(), password.getText(), type.getSelectedItem().toString());
						
						user.setStaffUser(staffUser);
						user.setCommand("adduser");
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						if (user2.getCommand().equals("success")) {
							warningLabel.setForeground(new Color(0, 128, 0));
							warningLabel.setText("User added successfully");
							warningLabel.setVisible(true);
						}

						else {
							warningLabel
									.setText("Add user failed. Please check fields and try again.");
						}

					}
				} catch (Exception ex) {

				}
			}
		});
		
	}
	
	public static void generateAgentsWithoutLogins(User user){

		user.setAgentsWithoutLogins(new ArrayList<String>());
		
		ArrayList<String>temp = new ArrayList<String>();
		
		for(Agent a : user.getAgentsList()){
			temp.add(a.getCompanyName());
		}
		
		for(String s : temp){
			if(!user.getAgentsWithLogins().contains(s)){
				user.getAgentsWithoutLogins().add(s);
			}
		}
		
	}
	
	public static void updateAgentsWithLogins(User user){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					
						
						user.setCommand("updateagentlogins");

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());

						
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						
						user.setAgentsWithLogins(user2.getAgentsWithLogins());
						socketConnection.close();
						
					
				} catch (Exception ex) {

				}
			}
		});
		
	}
	
public static void updatePassword(User user, JLabel warningLabel, JTextField textField, JComboBox comboBox_1){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {

						user.setCommand("updatepassword");
						user.setCommand2(comboBox_1.getSelectedItem().toString());
						user.setCommand3(textField.getText());
						

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());

						
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						
						if (user2.getCommand().equals("success")){
							warningLabel.setForeground(new Color(0, 128, 0));
							warningLabel.setText("Password updated successfully");
							warningLabel.setVisible(true);
						}
						
						else{
							warningLabel.setText("Unable to update password. Please try again later");
							warningLabel.setVisible(true);
						}
						
						
						socketConnection.close();
						
					
				} catch (Exception ex) {

				}
			}
		});
		
	}
	
	public static void addAgentLogin(User user, JLabel warningLabel, JTextField passwordField, JComboBox agentCombo){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					if (passwordField.getText().equals("")) {
						warningLabel.setText("Please enter a password.");
						warningLabel.setVisible(true);
					}
					
					else if (agentCombo.getSelectedItem().equals("Select")){
						warningLabel.setText("Please select an agent to generate a login for.");
						warningLabel.setVisible(true);
					}

					else {
						
						user.setCommand2(agentCombo.getSelectedItem().toString());
						user.setCommand3(passwordField.getText());

						socketConnection = new Socket("127.0.0.1", 5432);
						clientOut = new ObjectOutputStream(socketConnection
								.getOutputStream());
						clientIn = new ObjectInputStream(socketConnection
								.getInputStream());

						user.setCommand("addagentlogin");
						clientOut.writeObject(user);
						User user2 = (User) clientIn.readObject();
						socketConnection.close();
						if (user2.getCommand().equals("success")) {
							warningLabel.setForeground(new Color(0, 128, 0));
							warningLabel.setText("Agent login created. Please make record of password now.");
							warningLabel.setVisible(true);
						}

						else {
							warningLabel
									.setText("Activate agent failed. Please check fields and try again.");
						}

					}
				} catch (Exception ex) {

				}
			}
		});
		
	}
	

}
