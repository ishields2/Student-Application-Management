/**
 * This class contains the JUnit tests for the JDBC class.
 * 
 * @author Ian Shields, 1506416
 * @version 07-09-2015
 * 
 * References:
 * http://junit.sourceforge.net/javadoc/
 */

package com.sam.database;
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.sam.server.Agent;
import com.sam.server.Application;
import com.sam.server.Enquiry;
import com.sam.server.Notes;


public class JDBCTesting {
	
	/**
	 * Method being Tested: authorizeLogin
	 * Testing that method successfully recognises user type as agent
	 */
	@Test
	public void UserTypeTest() {
		
		String username = "Bobs Agency";
		String password = "password";
		String expected = "agent";
		String result = JDBC.authorizeLogin(username, password);
		
		assertEquals(expected, result);
	}
	
	/**
	 * Method being Tested: authorizeLogin
	 * Successfully recognises user type as admin
	 */
	@Test
	public void UserTypeTest2() {
		
		String username = "ishields";
		String password = "password";
		String expected = "admin";
		String result = JDBC.authorizeLogin(username, password);
		
		assertEquals(expected, result);
	}
	
	/**
	 * Method being Tested: authorizeLogin
	 * Successfully recognises user type as staff
	 */
	@Test
	public void UserTypeTest3() {
		
		String username = "ptweedie";
		String password = "password";
		String expected = "staff";
		String result = JDBC.authorizeLogin(username, password);
		
		assertEquals(expected, result);
	}
	
	/**
	 * Method Being Tested: generateUserApplications
	 * Empty Case - User with 0 applications on system
	 * Should return an empty ArrayList of Applications
	 */
	@Test
	public void generateUserAppsTest() {
		
		ArrayList<Application>expected = new ArrayList<Application>();
		
		String username = "ptweedie";

		ArrayList<Application> result = JDBC.generateUserApplications(username);
		
		assertEquals(expected, result);
	}
	
	/**
	 * Method Being Tested: generateUserEnquiries
	 * Empty Case - User with 0 application on system
	 * Should return an empty ArrayList of Applications
	 */
	@Test
	public void generateUserEnqsTest() {
		
		ArrayList<Enquiry>expected = new ArrayList<Enquiry>();
		
		String username = "test";

		ArrayList<Enquiry> result = JDBC.generateUserEnquiries(username);
		
		assertEquals(expected, result);
	}
	
	/**
	 * Method Being Tested: getApplicationByUID
	 * Should retrieve the desired application
	 * 
	 */
	@Test
	public void getAppByUIDTest() {
		
		String expected = "Peter";
		int uid = 69;
		Application result;
		try {
			result = JDBC.getApplicationByUID(uid);
			assertEquals(expected, result.getFirstName());
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	/**
	 * Method Being Tested: getApplicationByUID
	 * No matching UID
	 * Should throw IOException
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	@Test(expected=IOException.class)
	public void getAppByUIDTest2() throws SQLException, IOException {
		
		int uid = 5000;
		Application	result = JDBC.getApplicationByUID(uid);
	
	}
	
	/**
	 * Method Being Tested: getApplicationByEID
	 * Should retrieve the desired enquiry
	 * 
	 */
	@Test
	public void getAppByEIDTest() {
		
		String expected = "Mario";
		int eid = 2;
		Enquiry result;
		try {
			result = JDBC.getEnquiryByEID(eid);
			assertEquals(expected, result.getfName());
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	/**
	 * Method Being Tested: getEnquiryByEID
	 * No matching EID
	 * Should throw IOException
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	@Test(expected=IOException.class)
	public void getAppByEIDTest2() throws SQLException, IOException {
		
		int eid = 5000;
		Enquiry	result = JDBC.getEnquiryByEID(eid);
	
	}
	
	/**
	 * Method Being Tested: getAgentByAgentName
	 * Should retrieve the desired agent
	 * 
	 */
	@Test
	public void getAgentByAgentNameTest() {
		
		String expected = "ishields"; // ishields is the account manager for the expected agent
		String agentName = "Bobs Agency";
		Agent result;
		try {
			result = JDBC.getAgentByAgentName(agentName);
			assertEquals(expected, result.getAccountManager());
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	/**
	 * Method Being Tested: getEnquiryByEID
	 * No matching EID
	 * Should throw IOException
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	@Test(expected=IOException.class)
	public void getAgentByAgentNameTest2() throws SQLException, IOException {
		
		String agentName = "I Don't Exist";
		Agent result = JDBC.getAgentByAgentName(agentName);
	
	}
	
	/**
	 * Method Being Tested: getStaffMember
	 * Should retrieve the desired staff member
	 * 
	 */
	@Test
	public void getStaffMemberTest() {
		
		String expected = "ishields"; // ishields is the account manager for the expected agent
		String agentName = "Bobs Agency";
		String result;
		try {
			result = JDBC.getStaffMember(agentName);
			assertEquals(expected, result);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	/**
	 * Method Being Tested: getStaffMember
	 * Agent name is not in database
	 * Should return null
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	@Test
	public void getStaffMemberTest2() throws SQLException, IOException  {
		
		String expected = null;
		String agentName = "I Don't Exist";
		String result = JDBC.getStaffMember(agentName);
		assertEquals(expected, result);
	}
	
	/**
	 * Method Being Tested: getApps
	 * Should return the correct number of applications for the user
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	@Test
	public void getAppsTest() throws SQLException, IOException  {
		
		int expected = 2;
		String agentName = "Rec4U";
		int result = JDBC.getApps(agentName);
		assertEquals(expected, result);
	}
	
	/**
	 * Method Being Tested: getApps - No Matching Agent
	 * Should return 0
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	@Test
	public void getAppsTest2() throws SQLException   {

		String agentName = "I Don't Exist";
		int expected = 0;
		int result = JDBC.getApps(agentName);
		assertEquals(expected, result);
		
	}
	
	/**
	 * Method Being Tested: getApps
	 * Should return the correct number of applications for the user
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	@Test
	public void getPaymentsTest() throws SQLException, IOException  {
		
		int expected = 8;
		String agentName = "Bobs Agency";
		int result = JDBC.getPayments(agentName);
		assertEquals(expected, result);
	}
	
	/**
	 * Method Being Tested: getApps - No Matching Agent
	 * Should return 0
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	@Test
	public void getPaymentsTest2() throws SQLException   {

		String agentName = "I Don't Exist";
		int expected = 0;
		int result = JDBC.getPayments(agentName);
		assertEquals(expected, result);
		
	}
	
	/**
	 * Method Being Tested: generateUserApplications
	 * Empty Case - User with 0 application on system
	 * Should return an empty ArrayList of Applications
	 */
	@Test
	public void getAgentsTest() {
		
		String username = "test";
		
		String expected = "DIRECT";

		ArrayList<Agent> result = JDBC.getAgents(username);
		
		assertEquals(expected, result.get(0).getCompanyName());
	}
	
	/**
	 * Method Being Tested: generateCourses
	 * Testing that the correct list of courses is returned
	 * Course returned should match
	 */
	@Test
	public void generateCoursesTest() {
		
		String expected = "Summer School 2015";
		String[] result = JDBC.generateCourses();
		
		assertEquals(expected, result[1]);
	}
	
	
	
	/**
	 * Method Being Tested: generateStaffMembers
	 * Testing that the correct list of staff members is returned
	 * Staff member selected should match
	 */
	@Test
	public void generateStaffMembersTest() {
		
		String expected = "ahopkins";
		String[] result = JDBC.generateStaffMembers();
		
		assertEquals(expected, result[6]);
	}
	
	/**
	 * Method Being Tested: checkApplicationAdded
	 * Testing an application that is on the system
	 * Should return true
	 */
	@Test
	public void checkApplicationAddedTest() {
		
		int uid = 30;
		
		assertTrue(JDBC.checkApplicationAdded(uid));
	}
	
	/**
	 * Method Being Tested: checkApplicationAdded
	 * Testing an application that is not on the system
	 * Should return false
	 */
	@Test
	public void checkApplicationAddedTest2() {
		
		int uid = 50000;
		
		assertFalse(JDBC.checkApplicationAdded(uid));
	}
	
	/**
	 * Method Being Tested: checkApplicationAdded
	 * Testing an application that is on the system
	 * Should return true
	 */
	@Test
	public void checkEnquiryAddedTest() {
		
		int eid = 5;
		
		assertTrue(JDBC.checkEnquiryAdded(eid));
	}
	
	/**
	 * Method Being Tested: checkApplicationAdded
	 * Testing an application that is not on the system
	 * Should return false
	 */
	@Test
	public void checkEnquiryAddedTest2() {
		
		int eid = 50000;
		
		assertFalse(JDBC.checkEnquiryAdded(eid));
	}
}
