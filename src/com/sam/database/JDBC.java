/**
 * This is the JDBC class which contains all of the methods
 * for connecting to the database to access, retrieve, store 
 * and update data. Methods from this class are called 
 * primarily from the server class.
 * 
 * @author Ian Shields, 1506416
 * @version 07/09/2015
 */
package com.sam.database;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import com.sam.server.Agent;
import com.sam.server.AgentNote;
import com.sam.server.Application;
import com.sam.server.Enquiry;
import com.sam.server.Notes;
import com.sam.server.StaffUser;
import com.sam.server.User;

import javax.activation.*;

public class JDBC {

	// field variables for connecting to the database
	private static Connection connection;
	static String password = "_wDqPqGGMTWG8Pe4rbQEfJUcQloOTHcY";

	// Database URL
	private static String URL = "jdbc:postgresql://stampy.db.elephantsql.com:5432/nwqbpirk";

	public static Connection Connect() {
		try {
			if(connection == null){
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(URL, "nwqbpirk", password);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * authorizeLogin method takes a username and password entered by the user
	 * and connects to the database to check whether they are an authorized user
	 * and to identify the type of user that they are (staff, admin or agent)
	 * 
	 * @param - String username and password submitted by user
	 * @returns - a String indicating if the user is in the database and which
	 *          type of user they are
	 */
	public static String authorizeLogin(String username, String password) {
		connection = Connect();
		System.out.println("Successfully connected to Database \n");

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT username FROM public.agent_login WHERE username=? and password=?");
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				System.out
						.println("User has been found in agent_login table and has been authenticated as an agent user: "
								+ resultSet.getString(1) + "\n");
				
				return "agent";
			} else {
				statement = connection
						.prepareStatement("SELECT Username FROM public.staff WHERE Username=? and Password=? and Type='admin'");
				statement.setString(1, username);
				statement.setString(2, password);
				resultSet = statement.executeQuery();

				if (resultSet.next()) {

					System.out
							.println("User has been found in staff table, type identified as admin. User has been authenticated as a staff admin user: "
									+ resultSet.getString(1) + "\n");
					
					return "admin";
				}

				else {
					statement = connection
							.prepareStatement("SELECT Username FROM public.staff WHERE Username=? and Password=? and Type='staff'");
					statement.setString(1, username);
					statement.setString(2, password);
					resultSet = statement.executeQuery();

					if (resultSet.next()) {

						System.out
								.println("User has been found in staff table, type identified as non-admin. User has been authenticated as a staff user: "
										+ resultSet.getString(1) + "\n");
						
						return "staff";
					}

					else {
						System.out
								.println("The user does not exist within the database + \n");
						connection.close();
						return "";
					}
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * This method connects to the database and generates an ArrayList of
	 * application objects for a given user. The prepared statement takes the
	 * given users username, to identify all applications that are assigned to
	 * that particular user. It then creates an ArrayList, and adds a new
	 * application for each record found to the ArrayList, before returning the
	 * ArrayList
	 * 
	 * @param username- a String username for the given user
	 * @return - an ArrayList containing the Application objects relevant to the user
	 */
	public static ArrayList<Application> generateUserApplications(
			String username) {
		ArrayList<Application> appsList = new ArrayList<Application>();
		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.applications_students WHERE sales_advisor=? ORDER BY uid");
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				appsList.add(
						0,
						new Application(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getString(10), resultSet
										.getString(11),
								resultSet.getString(12), resultSet
										.getString(13),
								resultSet.getString(14), resultSet
										.getString(15),
								resultSet.getString(16), resultSet
										.getString(17),
								resultSet.getString(18), resultSet
										.getString(19),
								resultSet.getString(20), resultSet
										.getString(21),
								resultSet.getString(22), resultSet
										.getString(23),
								resultSet.getString(24), resultSet
										.getString(25),
								resultSet.getString(26), null));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return appsList;
	}

	/**
	 * This method connects to the database and inserts and application note
	 * using a prepared statement.
	 * 
	 * @param User
	 *            - the user who is adding the note
	 * @return - true if added successfully, false otherwise
	 */

	public static boolean addAppNote(User user) throws IOException {

		user.getNoteToBeAdded().setNoteNumber(
				generateAppNoteNumber(user.getApplicationToBeAdded().getUid()));
		System.out.println(user.getNoteToBeAdded().getNoteNumber());
		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO public.application_student_notes VALUES(?, ?, ?, ?, ?, ?)");
			statement.setInt(1, user.getNoteToBeAdded().getUid());
			statement.setString(2, user.getNoteToBeAdded().getDateAdded());
			statement.setString(3, user.getNoteToBeAdded().getNote());
			statement.setInt(4, user.getNoteToBeAdded().getNoteNumber());
			statement.setString(5, user.getNoteToBeAdded().getAuthor());
			statement.setString(6, user.getNoteToBeAdded().getTime());
			statement.executeUpdate();
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * This method connects to the database and inserts and application note
	 * using a prepared statement.
	 * 
	 * @param User - the user who is adding the note
	 * @return - true if added successfully, false otherwise
	 */
	public static boolean addEnqNote(User user) throws IOException {

		user.getNoteToBeAdded().setNoteNumber(
				generateEnqNoteNumber(user.getEnquiryToBeAdded().getEid()));
		System.out.println(user.getNoteToBeAdded().getNoteNumber());
		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO public.enquiry_notes VALUES(?, ?, ?, ?, ?, ?)");
			statement.setInt(1, user.getNoteToBeAdded().getUid());
			statement.setString(2, user.getNoteToBeAdded().getDateAdded());
			statement.setString(3, user.getNoteToBeAdded().getNote());
			statement.setInt(4, user.getNoteToBeAdded().getNoteNumber());
			statement.setString(5, user.getNoteToBeAdded().getAuthor());
			statement.setString(6, user.getNoteToBeAdded().getTime());
			statement.executeUpdate();
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	/**
	 * This method connects to the database and inserts and agent note
	 * using a prepared statement.
	 * 
	 * @param User - the user who is adding the note
	 * @return - true if added successfully, false otherwise
	 */

	public static boolean addAgentNote(User user) throws IOException {

		user.getAgentNoteToBeAdded().setNoteNumber(
				generateAgentNoteNumber(user.getCommand2()));

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO public.agent_notes VALUES(?, ?, ?, ?, ?, ?)");
			statement.setString(1, user.getCommand2());
			statement.setString(2, user.getAgentNoteToBeAdded().getDateAdded());
			statement.setString(3, user.getAgentNoteToBeAdded().getNote());
			statement.setInt(4, user.getAgentNoteToBeAdded().getNoteNumber());
			statement.setString(5, user.getAgentNoteToBeAdded().getAuthor());
			statement.setString(6, user.getAgentNoteToBeAdded().getTime());
			statement.executeUpdate();
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * This method takes an ArrayList of applications and populates
	 * an ArrayList of notes for each application object. This is done
	 * by connecting to the database and using a prepared statement 
	 * to get the notes for each application individually using 
	 * an enhanced for loop
	 * 
	 * @param appsList - An ArrayList of applications
	 */
	public static void generateAppNotes(ArrayList<Application> appsList) {

		connection = Connect();

		for (Application a : appsList) {
			a.setApplicationNotes(new ArrayList<Notes>());
			try {
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM public.application_student_notes WHERE uid=? ORDER BY note_number");
				statement.setInt(1, a.getUid());
				ResultSet resultSet = statement.executeQuery();

				while (resultSet.next()) {
					a.getApplicationNotes().add(
							0,
							new Notes(resultSet.getInt(1), resultSet
									.getString(2), resultSet.getString(3),
									resultSet.getInt(4),
									resultSet.getString(5), resultSet
											.getString(6)));

					System.out.println(a.getApplicationNotes().get(0).getUid());

				}

			} catch (Exception e) {
				e.printStackTrace();

			}

		}

	}

	/**
	 * This method takes an individual application and generates 
	 * its application notes using a prepared statement to 
	 * connect to the database and retrieve the data 
	 * 
	 * @param app - the application to generate the notes for 
	 */
	public static void generateIndividualAppNotes(Application app) {

		connection = Connect();

		app.setApplicationNotes(new ArrayList<Notes>());
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.application_student_notes WHERE uid=? ORDER BY note_number");
			statement.setInt(1, app.getUid());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				app.getApplicationNotes()
						.add(0,
								new Notes(resultSet.getInt(1), resultSet
										.getString(2), resultSet.getString(3),
										resultSet.getInt(4), resultSet
												.getString(5), resultSet
												.getString(6)));

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	/**
	 * This method takes an individual enquiry and generates 
	 * its enquiry notes using a prepared statement to 
	 * connect to the database and retrieve the data 
	 * 
	 * @param enq - the enquiry to generate the notes for 
	 */
	public static void generateIndividualEnqNotes(Enquiry enq) {

		connection = Connect();

		enq.setEnquiryNotes(new ArrayList<Notes>());
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.enquiry_notes WHERE eid=? ORDER BY note_number");
			statement.setInt(1, enq.getEid());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				enq.getEnquiryNotes()
						.add(0,
								new Notes(resultSet.getInt(1), resultSet
										.getString(2), resultSet.getString(3),
										resultSet.getInt(4), resultSet
												.getString(5), resultSet
												.getString(6)));

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * This method takes an individual agent and generates 
	 * its agent notes using a prepared statement to 
	 * connect to the database and retrieve the data 
	 * 
	 * @param agent - the agent to generate the notes for 
	 */
	public static void generateIndividualAgentNotes(Agent agent)
			throws SQLException, IOException {

		connection = Connect();

		agent.setNotes(new ArrayList<AgentNote>());
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.agent_notes WHERE company_name=? ORDER BY note_number");
			statement.setString(1, agent.getCompanyName());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				agent.getNotes().add(
						0,
						new AgentNote(resultSet.getString(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getInt(4), resultSet.getString(5),
								resultSet.getString(6)));

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * This method takes an ArrayList of enquiries and populates
	 * an ArrayList of notes for each enquiry object. This is done
	 * by connecting to the database and using a prepared statement 
	 * to get the notes for each enquiry individually using 
	 * an enhanced for loop
	 * 
	 * @param appsList - An ArrayList of enquiries
	 */
	public static void generateEnqNotes(ArrayList<Enquiry> enqsList) {

		connection = Connect();

		for (Enquiry e : enqsList) {
			e.setEnquiryNotes(new ArrayList<Notes>());
			try {
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM public.application_student_notes WHERE uid=? ORDER BY note_number");
				statement.setInt(1, e.getEid());
				ResultSet resultSet = statement.executeQuery();

				while (resultSet.next()) {
					e.getEnquiryNotes().add(
							0,
							new Notes(resultSet.getInt(1), resultSet
									.getString(2), resultSet.getString(3),
									resultSet.getInt(4),
									resultSet.getString(5), resultSet
											.getString(6)));

				}

			} catch (Exception ex) {
				ex.printStackTrace();

			}

		}

	}
	
	/**
	 * This method takes an ArrayList of agents and populates
	 * an ArrayList of notes for each agentn object. This is done
	 * by connecting to the database and using a prepared statement 
	 * to get the notes for each application individually using 
	 * an enhanced for loop
	 * 
	 * @param agentList - An ArrayList of agents
	 */
	public static void generateAgentNotes(ArrayList<Agent> agentList) {

		ArrayList<AgentNote> notes = new ArrayList<AgentNote>();

		connection = Connect();

		for (Agent a : agentList) {

			try {
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM public.agent_notes WHERE company_name=? ORDER BY note_number");
				statement.setString(1, a.getCompanyName());
				ResultSet resultSet = statement.executeQuery();

				while (resultSet.next()) {
					notes.add(0, new AgentNote(resultSet.getString(1),
							resultSet.getString(2), resultSet.getString(3),
							resultSet.getInt(4), resultSet.getString(5),
							resultSet.getString(6)));

				}

			} catch (Exception ex) {
				ex.printStackTrace();

			}

		}

	}
	/**
	 * This method connects to the database and retrieves the relevant 
	 * enquiries for a given user, by using and their username 
	 * to retrieve the relevant data
	 * 
	 * @param username - A String username of the user
	 * @return An ArrayList containing all enquiries linked to the user
	 */
	public static ArrayList<Enquiry> generateUserEnquiries(String username) {
		ArrayList<Enquiry> enqsList = new ArrayList<Enquiry>();
		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.student_enquiry WHERE staff_member=? ORDER BY eid");
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				enqsList.add(
						0,
						new Enquiry(resultSet.getInt(1),
								resultSet.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getString(10), resultSet
										.getString(11),
								resultSet.getString(12), resultSet
										.getString(13),
								resultSet.getString(14), resultSet
										.getString(15),
								resultSet.getString(16), resultSet
										.getString(17), new ArrayList<Notes>()));

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return enqsList;
	}

	/**
	 * This method locates an application record from the database and creates a
	 * new application object for it, which it then returns. It takes an int
	 * UID as a parameter and uses this in a prepared statement to identify the
	 * required application.
	 * 
	 * @param uid - the int uid of the application
	 * @return - An application object
	 * @throws IOException, SQLException
	 */
	public static Application getApplicationByUID(int uid) throws SQLException,
			IOException {

		connection = Connect();

		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM public.applications_students WHERE uid=?");
		statement.setInt(1, uid);
		System.out.println("JDBC Called");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Application app = new Application(resultSet.getInt(1),
					resultSet.getString(2), resultSet.getString(3),
					resultSet.getString(4), resultSet.getString(5),
					resultSet.getString(6), resultSet.getString(7),
					resultSet.getString(8), resultSet.getString(9),
					resultSet.getString(10), resultSet.getString(11),
					resultSet.getString(12), resultSet.getString(13),
					resultSet.getString(14), resultSet.getString(15),
					resultSet.getString(16), resultSet.getString(17),
					resultSet.getString(18), resultSet.getString(19),
					resultSet.getString(20), resultSet.getString(21),
					resultSet.getString(22), resultSet.getString(23),
					resultSet.getString(24), resultSet.getString(25),
					resultSet.getString(26), null);

			generateIndividualAppNotes(app);

			
			return app;

		}

		throw new IOException("The application could not be found");
	}

	/**
	 * This method locates an enquiry record from the database and creates a
	 * new enquiry object for it, which it then returns. It takes an int
	 * EID as a parameter and uses this in a prepared statement to identify the
	 * required application.
	 * 
	 * @param EID, an int eid for the given enquiry
	 * @return - An enquiry object
	 * @throws IOException, SQLException
	 */
	public static Enquiry getEnquiryByEID(int eid) throws SQLException,
			IOException {
		connection = Connect();

		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM public.student_enquiry WHERE eid=?");
		statement.setInt(1, eid);
		System.out.println("JDBC Called");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Enquiry enq = new Enquiry(resultSet.getInt(1),
					resultSet.getString(2), resultSet.getString(3),
					resultSet.getString(4), resultSet.getString(5),
					resultSet.getString(6), resultSet.getString(7),
					resultSet.getString(8), resultSet.getString(9),
					resultSet.getString(10), resultSet.getString(11),
					resultSet.getString(12), resultSet.getString(13),
					resultSet.getString(14), resultSet.getString(15),
					resultSet.getString(16), resultSet.getString(17),
					new ArrayList<Notes>());
			generateIndividualEnqNotes(enq);
			
			return enq;

		}

		throw new IOException("The application could not be found");
	}

	/**
	 * This method locates an agent record from the database and creates a
	 * new agent object for it, which it then returns. It takes a string
	 * agentName as a parameter and uses this in a prepared statement to identify the
	 * required agent.
	 * 
	 * @param agentName - a String agent name for the given agent
	 * @return - An application object
	 * @throws IOException, SQLException
	 */
	public static Agent getAgentByAgentName(String agentName)
			throws SQLException, IOException {

		connection = Connect();

		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM public.agent WHERE company_name=?");
		statement.setString(1, agentName);
		System.out.println("JDBC Called");
		ResultSet resultSet = statement.executeQuery();

		int a = getApps(agentName);
		int b = getPayments(agentName);

		while (resultSet.next()) {
			Agent agent = new Agent(resultSet.getString(1),
					resultSet.getString(2), resultSet.getString(3),
					resultSet.getString(4), resultSet.getString(5),
					resultSet.getString(6), resultSet.getString(7),
					resultSet.getString(8), resultSet.getString(9),
					resultSet.getString(10), a, b, new ArrayList<AgentNote>());

			generateIndividualAgentNotes(agent);
			
			return agent;

		}

		throw new IOException("The application could not be found");

	}

	/**
	 * This method takes an agent name and returns the staff member associated to 
	 * the agent by connecting to the database and using a prepared statement 
	 * to retrieve the required data
	 * 
	 * @param agentName - a String representing the name of the agent
	 * @return - staffMember - String - the name of the staff member linked to the agent
	 * @throws IOException, SQLException
	 */
	public static String getStaffMember(String agentName) throws SQLException,
			IOException {
		connection = Connect();

		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM public.agent WHERE company_name=?");
		statement.setString(1, agentName);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			String staffMember = resultSet.getString(2);
			
			return staffMember;

		}
		return null;

	}
	/**
	 * This method returns the number of applications in the database for 
	 * a given agent. This is done by connecting to the database 
	 * and using a prepared statement with the agents name to get the relevant
	 * data
	 * 
	 * @param agentName - String - the agent 
	 * @return - i - int - the number of applications for the agent
	 */
	public static int getApps(String agentName) {

		connection = Connect();
		int i = 0;

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.applications_students WHERE agent=?");
			statement.setString(1, agentName);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	/**
	 * This method returns the number of paymenys in the database for 
	 * a given agent. This is done by connecting to the database 
	 * and using a prepared statement with the agents name to get the relevant
	 * data
	 * 
	 * @param agentName - String - the agent 
	 * @return - i - int - the number of payments for the agent
	 */
	public static int getPayments(String agentName) {
		connection = Connect();

		String str1 = "Deposit Paid";
		String str2 = "Full Fee Paid";
		String str3 = "Enrolled";
		String str4 = "Deposit Received";
		int i = 0;

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.applications_students WHERE agent = ? "
							+ "AND (payment_status=? OR payment_status=? OR application_status =? OR application_status = ? )");
			statement.setString(1, agentName);
			statement.setString(2, str1);
			statement.setString(3, str2);
			statement.setString(4, str3);
			statement.setString(5, str4);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	/**
	 * This method takes a given users username before connecting 
	 * to the database and using a prepared statement to retrieve 
	 * all of the agents linked to the user
	 * 
	 * @param username - String - the user 
	 * @return an ArrayList containing the agents relevant to the user
	 */
	public static ArrayList<Agent> getAgents(String username) {

		ArrayList<Agent> agents = new ArrayList<Agent>();

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.agent WHERE account_manager=? OR account_manager "
							+ "= 'admin' ORDER BY company_name DESC");
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				agents.add(
						0,
						new Agent(resultSet.getString(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getString(10), 0, 0,
								new ArrayList<AgentNote>()));
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return agents;

	}

	/**
	 * This method connects to the database and retrieves the list of 
	 * campuses within the database
	 *  
	 * @return - a String array containing the list of campuses
	 */
	public static String[] generateCampuses() {
		connection = Connect();
		ArrayList<String> campuses = new ArrayList<String>();
		campuses.add("Select");

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.campus");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				campuses.add(resultSet.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] campusArray = campuses.toArray(new String[campuses.size()]);
		return campusArray;
	}

	/**
	 * This method connects to the database and retrieves the list of 
	 * courses within the database
	 *  
	 * @return - a String array containing the list of courses
	 */
	public static String[] generateCourses() {
		connection = Connect();
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("Select");

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.course");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				courses.add(resultSet.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] courseArray = courses.toArray(new String[courses.size()]);
		return courseArray;
	}

	/**
	 * This method connects to the database and retrieves the list of 
	 * staff members within the database
	 *  
	 * @return - a String array containing the list of staff members
	 */
	public static String[] generateStaffMembers() {
		connection = Connect();
		ArrayList<String> staff = new ArrayList<String>();
		staff.add("Select");

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.staff");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				staff.add(resultSet.getString(3));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] staffArray = staff.toArray(new String[staff.size()]);
		return staffArray;
	}

	/**
	 * This method takes a user object and inserts the application
	 * parameter of the user object into the database using a 
	 * prepared statement
	 *  
	 * @param - user - the user who is adding the application
	 */
	public static void addApplication(User user) {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO applications_students VALUES(?, ?, ?, ?, ?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setInt(1, user.getApplicationToBeAdded().getUid());
			statement.setString(2, user.getApplicationToBeAdded().getFirstName());
			statement.setString(3, user.getApplicationToBeAdded().getLastName());
			statement.setString(4, user.getApplicationToBeAdded().getAgent());
			statement.setString(5, user.getApplicationToBeAdded().getPaymentStatus());
			statement.setString(6, user.getApplicationToBeAdded().getVisaStatus());
			statement.setString(7, user.getApplicationToBeAdded().getNationality());
			statement.setString(8, user.getApplicationToBeAdded().getCountry());
			statement.setString(9, user.getApplicationToBeAdded().getGender());
			statement.setString(10, user.getApplicationToBeAdded().getPassportNo());
			statement.setString(11, user.getApplicationToBeAdded().getAppDate());
			statement.setString(12, user.getApplicationToBeAdded().getCampus());
			statement.setString(13, user.getApplicationToBeAdded().getAppStatus());
			statement.setString(14, user.getApplicationToBeAdded().getSalesAdvisor());
			statement.setString(15, user.getApplicationToBeAdded().getCourse());
			statement.setString(16, user.getApplicationToBeAdded().getAddress());
			statement.setString(17, user.getApplicationToBeAdded().getLastContacted());
			statement.setString(18, user.getApplicationToBeAdded().getArrivalDate());
			statement.setString(19, user.getApplicationToBeAdded().getDepartureDate());
			statement.setString(20, user.getApplicationToBeAdded().getDob());
			statement.setString(21, user.getApplicationToBeAdded().getPhone());
			statement.setString(22, user.getApplicationToBeAdded().getEmail());
			statement.setString(23, user.getApplicationToBeAdded().getSource());
			statement.setString(24, user.getApplicationToBeAdded().getWeeklyPrice());
			statement.setString(25, user.getApplicationToBeAdded().getTotalPrice());
			statement.setString(26, user.getApplicationToBeAdded().getDuration());
			statement.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method takes a user object and inserts the enquiry
	 * parameter of the user object into the database using a 
	 * prepared statement
	 *  
	 * @param - user - the user who is adding the enquiry
	 */
	public static void addEnquiry(User user) {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO public.student_enquiry VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setInt(1, user.getEnquiryToBeAdded().getEid());
			statement.setString(2, user.getEnquiryToBeAdded().getfName());
			statement.setString(3, user.getEnquiryToBeAdded().getlName());
			statement.setString(4, user.getEnquiryToBeAdded().getEnqDate());
			statement.setString(5, user.getEnquiryToBeAdded().getNoWeeks());
			statement.setString(6, user.getEnquiryToBeAdded().getStatus());
			statement.setString(7, user.getEnquiryToBeAdded()
					.getLastContacted());
			statement.setString(8, user.getEnquiryToBeAdded().getSource());
			statement.setString(9, user.getEnquiryToBeAdded().getStaffMember());
			statement.setString(10, user.getEnquiryToBeAdded().getCountry());
			statement.setString(11, user.getEnquiryToBeAdded().getAgent());
			statement.setString(12, user.getEnquiryToBeAdded().getCourse());
			statement.setString(13, user.getEnquiryToBeAdded().getEmail());
			statement.setString(14, user.getEnquiryToBeAdded().getPhone());
			statement.setString(15, user.getEnquiryToBeAdded().getCampus());
			statement
					.setString(16, user.getEnquiryToBeAdded().getWeeklyPrice());
			statement.setString(17, user.getEnquiryToBeAdded().getTotalPrice());
			statement.executeUpdate();

			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method generates an application UID by connecting
	 * to the database to retrieve the highest current application
	 * UID and adding 1 to it, returning this as the new UID generated
	 *  
	 * @param - user - the user who is adding the application
	 * @throws IOException
	 */
	public static int generateAppUid() throws IOException {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT MAX(uid) FROM public.applications_students");

			ResultSet resultSet = statement.executeQuery();

			int temp = 0;
			while (resultSet.next()) {
				temp = (resultSet.getInt(1));
				temp++;

			}

			return temp;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IOException(
				"Unable to generate new UID, something went wrong");
	}

	/**
	 * This method generates an application note number by connecting
	 * to the database to retrieve the highest current note number
	 * for the application and adding 1 to it, returning this as the 
	 * new note number
	 *  
	 * @param - uid - int - the uid of the given application
	 * @throws IOException
	 */
	public static int generateAppNoteNumber(int uid) throws IOException {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT MAX(note_number) FROM public.application_student_notes WHERE uid = ?");
			statement.setInt(1, uid);
			int temp = 0;
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				if (resultSet.getInt(1) == 0) {
					return 1;
				}

				temp = (resultSet.getInt(1));
				temp++;
			}
			return temp;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IOException(
				"Unable to generate new UID, something went wrong");
	}
	
	/**
	 * This method generates an enquiry note number by connecting
	 * to the database to retrieve the highest current note number
	 * for the enquiry and adding 1 to it, returning this as the 
	 * new note number
	 *  
	 * @param - eid - int - the eid of the given application
	 * @throws IOException
	 */
	public static int generateEnqNoteNumber(int eid) throws IOException {

		connection = Connect();
		int temp = 0;

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT MAX(note_number) FROM public.enquiry_notes WHERE eid = ?");
			statement.setInt(1, eid);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				if (resultSet.getInt(1) == 0) {
					return 1;
				}

				temp = (resultSet.getInt(1));
				temp++;

			}

			return temp;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IOException(
				"Unable to generate new UID, something went wrong");
	}

	/**
	 * This method generates an agent note number by connecting
	 * to the database to retrieve the highest current note number
	 * for the agent and adding 1 to it, returning this as the 
	 * new note number
	 *  
	 * @param - companyName - String - the name of the given agent
	 * @throws IOException
	 */
	public static int generateAgentNoteNumber(String companyName)
			throws IOException {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT MAX(note_number) FROM public.agent_notes WHERE company_name = ?");
			statement.setString(1, companyName);
			int temp = 0;
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				if (resultSet.getInt(1) == 0) {
					return 1;
				}

				temp = (resultSet.getInt(1));
				temp++;
			}
			return temp;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IOException(
				"Unable to generate new Note Number, something went wrong");
	}

	/**
	 * This method generates an enquiry EID by connecting
	 * to the database to retrieve the highest current enquiry
	 * EID and adding 1 to it, returning this as the new EID generated
	 *  
	 * @param - user - the user who is adding the enquiry
	 * @throws IOException
	 */
	public static int generateEnqEid() throws IOException {

		connection = Connect();
		int temp = 0;

		try {
			PreparedStatement statement = connection.prepareStatement("SELECT MAX(eid) FROM public.student_enquiry");

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				temp = (resultSet.getInt(1));
				temp++;

			}
			return temp;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		throw new IOException(
				"Unable to generate new UID, something went wrong");

	}

	/**
	 * This methods connects to the database and uses a prepared statement to 
	 * see if the given uid is linked to an application
	 *  
	 * @param - uid - int - the uid of the given application
	 * @return boolean result indicating true if application is in database and false otherwise
	 */
	public static boolean checkApplicationAdded(int uid) {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.applications_students WHERE uid=?");
			statement.setInt(1, uid);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				int temp = resultSet.getInt(1);

				if (temp == uid) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * This methods connects to the database and uses a prepared statement to 
	 * see if the given eid is linked to an enquiry
	 *  
	 * @param - eid - int - the eid of the given enquiry
	 * @return boolean result indicating true if enquiry is in database and false otherwise
	 */
	public static boolean checkEnquiryAdded(int eid) {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.student_enquiry WHERE eid=?");
			statement.setInt(1, eid);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				int temp = resultSet.getInt(1);

				if (temp == eid) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * This methods connects to the database and uses a prepared statement to 
	 * update all fields of the given enquiry attached to the user object passed
	 * into the method.
	 *  
	 * @param - user - the user who is updating the enquiry
	 * @return boolean result indicating true if enquiry updated or false otherwise
	 */
	public static boolean updateEnquiry(User user) {

		connection = Connect();

		try {

			PreparedStatement update = connection
					.prepareStatement("UPDATE public.student_enquiry SET first_name = ?, last_name = ?, "
							+ "enquiry_date = ?, number_of_weeks = ?, status = ?, last_contacted_date = "
							+ "?, source = ?, staff_member = ?, country = ?, agent = ?, course = ?, email = "
							+ "?, phone = ?, campus = ?, weekly_price = ?, total_price = ? WHERE eid = ?");

			update.setString(1, user.getEnquiryToBeAdded().getfName());
			update.setString(2, user.getEnquiryToBeAdded().getlName());
			update.setString(3, user.getEnquiryToBeAdded().getEnqDate());
			update.setString(4, user.getEnquiryToBeAdded().getNoWeeks());
			update.setString(5, user.getEnquiryToBeAdded().getStatus());
			update.setString(6, user.getEnquiryToBeAdded().getLastContacted());
			update.setString(7, user.getEnquiryToBeAdded().getSource());
			update.setString(8, user.getEnquiryToBeAdded().getStaffMember());
			update.setString(9, user.getEnquiryToBeAdded().getCountry());
			update.setString(10, user.getEnquiryToBeAdded().getAgent());
			update.setString(11, user.getEnquiryToBeAdded().getCourse());
			update.setString(12, user.getEnquiryToBeAdded().getEmail());
			update.setString(13, user.getEnquiryToBeAdded().getPhone());
			update.setString(14, user.getEnquiryToBeAdded().getCampus());
			update.setString(15, user.getEnquiryToBeAdded().getWeeklyPrice());
			update.setString(16, user.getEnquiryToBeAdded().getTotalPrice());
			update.setInt(17, user.getEnquiryToBeAdded().getEid());
			update.executeUpdate();

			update.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * This methods connects to the database and uses a prepared statement to 
	 * update all fields of the given agent attached to the user object passed
	 * into the method.
	 *  
	 * @param - user - the user who is updating the agent
	 * @return boolean result indicating true if agent updated or false otherwise
	 */
	public static boolean updateAgent(User user) {

		connection = Connect();

		try {

			PreparedStatement update = connection
					.prepareStatement("UPDATE public.agent SET company_name = ?, account_manager = ?, first_name = ?, "
							+ "last_name = ?, country = ?, status = ?, address = ?, phone = ?, email = ?, last_contacted = "
							+ "? WHERE company_name = ?");

			update.setString(1, user.getAgentToBeAdded().getCompanyName());
			update.setString(2, user.getAgentToBeAdded().getAccountManager());
			update.setString(3, user.getAgentToBeAdded().getFirstName());
			update.setString(4, user.getAgentToBeAdded().getLastName());
			update.setString(5, user.getAgentToBeAdded().getCountry());
			update.setString(6, user.getAgentToBeAdded().getStatus());
			update.setString(7, user.getAgentToBeAdded().getAddress());
			update.setString(8, user.getAgentToBeAdded().getPhone());
			update.setString(9, user.getAgentToBeAdded().getEmail());
			update.setString(10, user.getAgentToBeAdded().getLast_contacted());
			update.setString(11, user.getAgentToBeAdded().getCompanyName());
			update.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * This methods connects to the database and uses a prepared statement to 
	 * update all fields of the given application attached to the user object passed
	 * into the method.
	 *  
	 * @param - user - the user who is updating the application
	 * @return boolean result indicating true if application updated or false otherwise
	 */
	public static boolean updateApplication(User user) {

		connection = Connect();

		try {

			PreparedStatement update = connection
					.prepareStatement("UPDATE public.applications_students SET first_name = ?, last_name = ?, agent = ?, "
							+ "payment_status = ?, visa_status = ?, nationality = ?, country = ?, gender = ?, "
							+ "passport_number = ?, date_of_application = ?, campus = ?, application_status = ?, "
							+ "sales_advisor = ?, course = ?, address = ?, last_contacted = ?, arrival_date = ?, "
							+ "departure_date = ?, dob = ?, phone = ?, email = ?, source = ?, weekly_price = ?, "
							+ "total_price = ?, duration_days = ? WHERE uid = ?");

			update.setString(1, user.getApplicationToBeAdded().getFirstName());
			update.setString(2, user.getApplicationToBeAdded().getLastName());
			update.setString(3, user.getApplicationToBeAdded().getAgent());
			update.setString(4, user.getApplicationToBeAdded().getPaymentStatus());
			update.setString(5, user.getApplicationToBeAdded().getVisaStatus());
			update.setString(6, user.getApplicationToBeAdded().getNationality());
			update.setString(7, user.getApplicationToBeAdded().getCountry());
			update.setString(8, user.getApplicationToBeAdded().getGender());
			update.setString(9, user.getApplicationToBeAdded().getPassportNo());
			update.setString(10, user.getApplicationToBeAdded().getAppDate());
			update.setString(11, user.getApplicationToBeAdded().getCampus());
			update.setString(12, user.getApplicationToBeAdded().getAppStatus());
			update.setString(13, user.getApplicationToBeAdded().getSalesAdvisor());
			update.setString(14, user.getApplicationToBeAdded().getCourse());
			update.setString(15, user.getApplicationToBeAdded().getAddress());
			update.setString(16, user.getApplicationToBeAdded().getLastContacted());
			update.setString(17, user.getApplicationToBeAdded().getArrivalDate());
			update.setString(18, user.getApplicationToBeAdded().getDepartureDate());
			update.setString(19, user.getApplicationToBeAdded().getDob());
			update.setString(20, user.getApplicationToBeAdded().getPhone());
			update.setString(21, user.getApplicationToBeAdded().getEmail());
			update.setString(22, user.getApplicationToBeAdded().getSource());
			update.setString(23, user.getApplicationToBeAdded().getWeeklyPrice());
			update.setString(24, user.getApplicationToBeAdded().getTotalPrice());
			update.setString(25, user.getApplicationToBeAdded().getDuration());
			update.setInt(26, user.getApplicationToBeAdded().getUid());
			update.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * This methods connects to the database and uses a prepared statement to 
	 * insert a new agent record based on the data passed into the method
	 * via the agent parameter
	 *  
	 * @param - agent - the agent to be added
	 * @return boolean result indicating true if agent added or false otherwise
	 */
	public static boolean addAgent(Agent agent) {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO public.agent VALUES(?,?,?,?,?,?,?,?,?,?)");
			statement.setString(1, agent.getCompanyName());
			statement.setString(2, agent.getAccountManager());
			statement.setString(3, agent.getFirstName());
			statement.setString(4, agent.getLastName());
			statement.setString(5, agent.getCountry());
			statement.setString(6, agent.getStatus());
			statement.setString(7, agent.getAddress());
			statement.setString(8, agent.getPhone());
			statement.setString(9, agent.getEmail());
			statement.setString(10, agent.getLast_contacted());

			statement.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * This method generates the recruitment statistics for an 
	 * ArrayList of agents by calling two other methods
	 *  
	 * @param - an ArrayList of agents to set the stats for
	 */
	public static void setAgentStats(ArrayList<Agent> agents) {

		for (Agent a : agents) {

			a.setAppsToDate(getApps(a.getCompanyName()));
			a.setStudentsToDate(getPayments(a.getCompanyName()));

		}

	}

	/**
	 * This method accesses any specified location on the server to retrieve
	 * a list of filenames within that folder, to be displayed to agents 
	 * looking to download promotional files
	 *  
	 * @return a String array of filenames located in the specified folder
	 */
	public static String[] getFileNames() {

		File folder = new File("C:/Users/Ian/Desktop/Server/Downloads");
		File[] listOfFiles = folder.listFiles();
		String[] files = new String[listOfFiles.length];
		int i = 0;
		for (File f : listOfFiles) {
			files[i] = f.getName();
			i++;
		}
		return files;

	}

	/**
	 * This method is called at login and generates the relevant data for a given
	 * staff user for use when using the software by calling the relevant methods. 
	 *  
	 * @param - user - the user to generate the data for
	 */
	public static void generateStaffUserData(User user) {
		user.setAgentsList(getAgents(user.getUsername()));
		user.setUserApplications(generateUserApplications(user.getUsername()));
		user.setCampuses(generateCampuses());
		user.setCourses(generateCourses());
		user.setStaffMembers(generateStaffMembers());
		user.setEnquiries(generateUserEnquiries(user.getUsername()));
		setAgentStats(user.getAgentsList());
		user.setGroupApp(new ArrayList<Application>());
		generateAppNotes(user.getUserApplications());
		generateEnqNotes(user.getEnquiries());
		generateAgentNotes(user.getAgentsList());
		user.setSearchApps(new ArrayList<Application>());
		user.setSearchEnqs(new ArrayList<Enquiry>());
		user.setSearchAgents(new ArrayList<Agent>());
		user.setStaffUsers(generateStaffUsers());
		user.setAgentsWithLogins(new ArrayList<String>());
		generateAgentsWithLogins(user.getAgentsWithLogins());

	}
	/**
	 * This method is called at login and generates the relevant data for a given
	 * agent user for use when using the software by calling the relevant methods. 
	 *  
	 * @param - user - the user to generate the data for
	 */
	public static void generateAgentUserData(User user) {
		user.setAgentsList(new ArrayList<Agent>());
		user.setUserApplications(generateUserApplicationsAgent(user
				.getUsername()));
		user.setCampuses(generateCampuses());
		user.setCourses(generateCourses());
		user.setStaffMembers(generateStaffMembers());
		user.setEnquiries(new ArrayList<Enquiry>());
		user.setGroupApp(new ArrayList<Application>());
		generateAppNotes(user.getUserApplications());
		generateEnqNotes(user.getEnquiries());
		generateAgentNotes(user.getAgentsList());
		user.setSearchApps(new ArrayList<Application>());
		user.setSearchEnqs(new ArrayList<Enquiry>());
		user.setSearchAgents(new ArrayList<Agent>());
		//user.setFileNames(getFileNames()); commented out as file functionality only configured locally
		try {
			user.setStaffMember(getStaffMember(user.getUsername()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method connects to the database and generates an ArrayList of
	 * application objects for a given agent user. The prepared statement takes the
	 * given agent users username, to identify all applications that are assigned to
	 * that particular user. It then creates an ArrayList, and adds a new
	 * application for each record found to the ArrayList, before returning the
	 * ArrayList
	 * 
	 * @param username- a String username for the given agent user
	 * @return - an ArrayList containing the Application objects relevant to the user
	 */
	private static ArrayList<Application> generateUserApplicationsAgent(
			String username) {

		ArrayList<Application> appsList = new ArrayList<Application>();
		connection = Connect();

		try {
			PreparedStatement appStatement = connection
					.prepareStatement("SELECT * FROM public.applications_students WHERE agent=? ORDER BY uid");
			appStatement.setString(1, username);

			ResultSet resultSet = appStatement.executeQuery();

			while (resultSet.next()) {

				appsList.add(
						0,
						new Application(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getString(10), resultSet.getString(11),
								resultSet.getString(12), resultSet.getString(13),
								resultSet.getString(14), resultSet.getString(15),
								resultSet.getString(16), resultSet.getString(17),
								resultSet.getString(18), resultSet.getString(19),
								resultSet.getString(20), resultSet.getString(21),
								resultSet.getString(22), resultSet.getString(23),
								resultSet.getString(24), resultSet.getString(25),
								resultSet.getString(26), new ArrayList<Notes>()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appsList;
	}
	
	public static boolean addUser(StaffUser staffUser) {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO public.staff VALUES(?,?,?,?,?)");
			statement.setString(1, staffUser.getFirstName());
			statement.setString(2, staffUser.getLastName());
			statement.setString(3, staffUser.getUsername());
			statement.setString(4, staffUser.getPassword());
			statement.setString(5, staffUser.getType());
			
			statement.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<StaffUser> generateStaffUsers(){
		
		ArrayList<StaffUser> staffUsers = new ArrayList<StaffUser>();
		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.staff ORDER BY first_name");
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				staffUsers.add(
						0,
						new StaffUser(resultSet.getString(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return staffUsers;
	}
	
	public static boolean removeUser(String staffUser) {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE public.staff SET password = ? WHERE username = ?");
			
			
			statement.setString(1, "userblocked46857");
			statement.setString(2, staffUser);
			
			
			statement.executeUpdate();

			
			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean removeAgent(String agentName) {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM public.agent_login WHERE username = ?");
			
			
			statement.setString(1, agentName);
			statement.executeUpdate();

			
			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean addAgentLogin(String username, String password) {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO public.agent_login VALUES(?,?)");
			statement.setString(1, username);
			statement.setString(2, password);
			
			statement.executeUpdate();

			
			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updatePassword(String username, String password) {

		connection = Connect();

		try {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE public.staff SET password = ? WHERE username = ?");
			
			
			statement.setString(1, password);
			statement.setString(2, username);
			
			
			statement.executeUpdate();

			
			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void generateAgentsWithLogins (ArrayList<String>agents){
		
		connection = Connect();
		
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM public.agent_login");
			
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {

				agents.add(
						0,
						resultSet.getString(1));
			}

			
			
			

		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		
	}
	
	

}
