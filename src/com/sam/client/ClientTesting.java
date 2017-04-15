/**
 * This class contains the JUnit tests for the Client.
 * 
 * @author Ian Shields, 1506416
 * @version 07-09-2015
 * 
 * References:
 * http://junit.sourceforge.net/javadoc/
 * http://stackoverflow.com/questions/17280120/subtract-four-weeks-from-current-time-stamp
 */

package com.sam.client;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import static org.junit.Assert.*;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.sam.client.Controller;
import com.sam.server.Agent;
import com.sam.server.Application;
import com.sam.server.Enquiry;
import com.sam.server.User;

public class ClientTesting {
	
	/**
	 * Method being Tested: getAppsThisWeek
	 * Should return correct number of applications with dates set within last 7 days
	 * Note: Last Tested on 07/09/2015, adjust dates accordingly to test method
	 */
	@Test
	public void getAppsThisWeekTest() {
		
		Application a1 = new Application();
		Application a2 = new Application();
		Application a3 = new Application();
		Application a4 = new Application();
		Application a5 = new Application();
		
		a1.setAppDate("07/09/15"); // set as date within last week
		a2.setAppDate("06/09/15"); // set as date within last week
		a3.setAppDate("05/09/15"); // set as date within last week
		a4.setAppDate("30/08/15"); // set as date outside of last week
		a5.setAppDate("01/05/15"); // set as date outside of last week
		
		ArrayList<Application> list = new ArrayList<Application>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		
		int expected = 3;
		
		
		assertEquals(expected, Controller.getAppsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getAppsThisWeek
	 * Empty Case: Should return 0
	 * 
	 */
	@Test
	public void getAppsThisWeekTest2() {

		ArrayList<Application> list = new ArrayList<Application>();

		int expected = 0;
		
		
		assertEquals(expected, Controller.getAppsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getAppsThisWeek
	 * All dates outside last week, should return 0
	 * 
	 */
	@Test
	public void getAppsThisWeekTest3() {
		
		Application a1 = new Application();
		Application a2 = new Application();
		Application a3 = new Application();
		Application a4 = new Application();
		Application a5 = new Application();
		
		a1.setAppDate("07/04/01"); 
		a2.setAppDate("06/11/02"); 
		a3.setAppDate("05/06/03"); 
		a4.setAppDate("30/08/04"); 
		a5.setAppDate("01/05/05"); 
		
		ArrayList<Application> list = new ArrayList<Application>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		
		int expected = 0;
		
		
		assertEquals(expected, Controller.getAppsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getAppsThisWeek
	 * Should return correct number of applications with dates set within last 7 days
	 * All applications within past 7 days
	 * Note: Last Tested on 07/09/2015, adjust dates accordingly to test method
	 */
	@Test
	public void getAppsThisWeekTest4() {
		
		Application a1 = new Application();
		Application a2 = new Application();
		Application a3 = new Application();
		Application a4 = new Application();
		Application a5 = new Application();
		
		a1.setAppDate("07/09/15"); // set as date within last week
		a2.setAppDate("06/09/15"); // set as date within last week
		a3.setAppDate("05/09/15"); // set as date within last week
		a4.setAppDate("02/09/15"); // set as date within last week
		a5.setAppDate("05/09/15"); // set as date within last week
		
		ArrayList<Application> list = new ArrayList<Application>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		
		int expected = 5;
		
		assertEquals(expected, Controller.getAppsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getEnqsThisWeek
	 * Should return correct number of applications with dates set within last 7 days
	 * Note: Last Tested on 07/09/2015, adjust dates accordingly to test method
	 */
	@Test
	public void getEnqsThisWeekTest() {
		
		Enquiry e1 = new Enquiry();
		Enquiry e2 = new Enquiry();
		Enquiry e3 = new Enquiry();
		Enquiry e4 = new Enquiry();
		Enquiry e5 = new Enquiry();
		
		e1.setEnqDate("07/09/15"); // set as date within last week
		e2.setEnqDate("06/09/15"); // set as date within last week
		e3.setEnqDate("05/09/15"); // set as date within last week
		e4.setEnqDate("30/08/15"); // set as date outside of last week
		e5.setEnqDate("01/05/15"); // set as date outside of last week
		
		ArrayList<Enquiry> list = new ArrayList<Enquiry>();
		list.add(e1);
		list.add(e2);
		list.add(e3);
		list.add(e4);
		list.add(e5);
		
		int expected = 3;
		
		assertEquals(expected, Controller.getEnqsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getEnqsThisWeek
	 * Empty Case: Should return 0
	 * 
	 */
	@Test
	public void getEnqsThisWeekTest2() {

		ArrayList<Enquiry> list = new ArrayList<Enquiry>();

		int expected = 0;
		
		
		assertEquals(expected, Controller.getEnqsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getEnqsThisWeek
	 * All dates outside last week, should return 0
	 * 
	 */
	@Test
	public void getEnqsThisWeekTest3() {
		
		Enquiry e1 = new Enquiry();
		Enquiry e2 = new Enquiry();
		Enquiry e3 = new Enquiry();
		Enquiry e4 = new Enquiry();
		Enquiry e5 = new Enquiry();
		
		e1.setEnqDate("07/09/11"); 
		e2.setEnqDate("06/09/10"); 
		e3.setEnqDate("05/09/13"); 
		e4.setEnqDate("30/08/14"); 
		e5.setEnqDate("01/05/14"); 
		
		ArrayList<Enquiry> list = new ArrayList<Enquiry>();
		list.add(e1);
		list.add(e2);
		list.add(e3);
		list.add(e4);
		list.add(e5);
		
		int expected = 0;
		
		assertEquals(expected, Controller.getEnqsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getAppsThisWeek
	 * Should return correct number of applications with dates set within last 7 days
	 * All applications within past 7 days
	 * Note: Last Tested on 07/09/2015, adjust dates accordingly to test method
	 */
	@Test
	public void getEnqsThisWeekTest4() {
		
		Enquiry e1 = new Enquiry();
		Enquiry e2 = new Enquiry();
		Enquiry e3 = new Enquiry();
		Enquiry e4 = new Enquiry();
		Enquiry e5 = new Enquiry();
		
		e1.setEnqDate("07/09/15"); // set as date within last week
		e2.setEnqDate("06/09/15"); // set as date within last week
		e3.setEnqDate("05/09/15"); // set as date within last week
		e4.setEnqDate("04/09/15"); // set as date within last week
		e5.setEnqDate("03/09/15"); // set as date within last week
		
		ArrayList<Enquiry> list = new ArrayList<Enquiry>();
		list.add(e1);
		list.add(e2);
		list.add(e3);
		list.add(e4);
		list.add(e5);
		
		int expected = 5;
		
		assertEquals(expected, Controller.getEnqsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getPaymentsThisWeek
	 * Should return correct number of payments with dates set within last 7 days
	 * All applications within past 7 days and all paid
	 * Note: Last Tested on 07/09/2015, adjust dates accordingly to test method
	 */
	@Test
	public void getPaymentsThisWeekTest() {
		
		Application a1 = new Application();
		Application a2 = new Application();
		Application a3 = new Application();
		Application a4 = new Application();
		Application a5 = new Application();
		
		a1.setAppDate("07/09/15"); // set as date within last week
		a1.setPaymentStatus("Deposit Paid");
		a1.setAppStatus("Received");
		
		a2.setAppDate("06/09/15"); // set as date within last week
		a2.setPaymentStatus("Deposit Paid");
		a2.setAppStatus("Received");
		
		a3.setAppDate("05/09/15"); // set as date within last week
		a3.setPaymentStatus("Deposit Paid");
		a3.setAppStatus("Received");
		
		a4.setAppDate("02/09/15"); // set as date within last week
		a4.setPaymentStatus("Deposit Paid");
		a4.setAppStatus("Received");
		
		a5.setAppDate("05/09/15"); // set as date within last week
		a5.setPaymentStatus("Deposit Paid");
		a5.setAppStatus("Received");
		
		
		ArrayList<Application> list = new ArrayList<Application>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		
		int expected = 5;
		
		assertEquals(expected, Controller.getPaymentsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getPaymentsThisWeek
	 * Should return correct number of payments with dates set within last 7 days
	 * 3 Payments within the last 7 days
	 * Note: Last Tested on 07/09/2015, adjust dates accordingly to test method
	 */
	@Test
	public void getPaymentsThisWeekTest2() {
		
		Application a1 = new Application();
		Application a2 = new Application();
		Application a3 = new Application();
		Application a4 = new Application();
		Application a5 = new Application();
		
		a1.setAppDate("07/09/15"); // set as date within last week
		a1.setPaymentStatus("Not Paid");
		a1.setAppStatus("Received");
		
		a2.setAppDate("06/09/15"); // set as date within last week
		a2.setPaymentStatus("Not Paid");
		a2.setAppStatus("Received");
		
		a3.setAppDate("05/09/15"); // set as date within last week
		a3.setPaymentStatus("Deposit Paid");
		a3.setAppStatus("Received");
		
		a4.setAppDate("02/09/15"); // set as date within last week
		a4.setPaymentStatus("Deposit Paid");
		a4.setAppStatus("Received");
		
		a5.setAppDate("05/09/15"); // set as date within last week
		a5.setPaymentStatus("Deposit Paid");
		a5.setAppStatus("Received");
		
		
		ArrayList<Application> list = new ArrayList<Application>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		
		int expected = 3;
		
		assertEquals(expected, Controller.getPaymentsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getPaymentsThisWeek
	 * Should return correct number of payments with dates set within last 7 days
	 * 0 payments within last 7 days, should return 0
	 * Note: Last Tested on 07/09/2015, adjust dates accordingly to test method
	 */
	@Test
	public void getPaymentsThisWeekTest3() {
		
		Application a1 = new Application();
		Application a2 = new Application();
		Application a3 = new Application();
		Application a4 = new Application();
		Application a5 = new Application();
		
		a1.setAppDate("07/09/15"); // set as date within last week
		a1.setPaymentStatus("Not Paid");
		a1.setAppStatus("Received");
		
		a2.setAppDate("06/09/15"); // set as date within last week
		a2.setPaymentStatus("Not Paid");
		a2.setAppStatus("Received");
		
		a3.setAppDate("05/09/15"); // set as date within last week
		a3.setPaymentStatus("Not Paid");
		a3.setAppStatus("Received");
		
		a4.setAppDate("02/09/15"); // set as date within last week
		a4.setPaymentStatus("Not Paid");
		a4.setAppStatus("Received");
		
		a5.setAppDate("05/09/15"); // set as date within last week
		a5.setPaymentStatus("Not Paid");
		a5.setAppStatus("Received");
		
		
		ArrayList<Application> list = new ArrayList<Application>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		
		int expected = 0;
		
		assertEquals(expected, Controller.getPaymentsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getPaymentsThisWeek
	 * Empty Case: Should return 0
	 */
	@Test
	public void getPaymentsThisWeekTest4() {

		ArrayList<Application> list = new ArrayList<Application>();
		
		int expected = 0;
		
		assertEquals(expected, Controller.getPaymentsThisWeek(list));
	}
	
	/**
	 * Method being Tested: getNationsRecruited
	 * Should return correct number of nations recruited from
	 * 3 Different Nations Recruited From - Should return 3
	 * Note: Last Tested on 07/09/2015, adjust dates accordingly to test method
	 */
	@Test
	public void getPaymentsNationsRecruitedTest() {
		
		Application a1 = new Application();
		Application a2 = new Application();
		Application a3 = new Application();
		Application a4 = new Application();
		Application a5 = new Application();
		
		a1.setPaymentStatus("Deposit Paid");
		a1.setAppStatus("Received");
		a1.setCourse("Summer School 2015");
		a1.setCountry("UK");
		
		a2.setPaymentStatus("Deposit Paid");
		a2.setAppStatus("Received");
		a2.setCourse("Summer School 2015");
		a2.setCountry("China");

		a3.setPaymentStatus("Deposit Paid");
		a3.setAppStatus("Received");
		a3.setCourse("Summer School 2015");
		a3.setCountry("Japan");

		a4.setPaymentStatus("Not Paid");
		a4.setAppStatus("Received");
		a4.setCourse("Summer School 2015");
		a4.setCountry("Japan");

		a5.setPaymentStatus("Not Paid");
		a5.setAppStatus("Received");
		a5.setCourse("Summer School 2015");
		a5.setCountry("Japan");
		
		
		ArrayList<Application> list = new ArrayList<Application>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		
		int expected = 3;
		
		assertEquals(expected, Controller.getNationsRecruited(list));
	}
	
	/**
	 * Method being Tested: getNationsRecruited
	 * Should return correct number of nations recruited from
	 * Empty Case - Should Return 0
	 * Note: Last Tested on 07/09/2015, adjust dates accordingly to test method
	 */
	@Test
	public void getPaymentsNationsRecruitedTest2() {
		
		ArrayList<Application> list = new ArrayList<Application>();
		
		int expected = 0;
		
		assertEquals(expected, Controller.getNationsRecruited(list));
	}
	
	/**
	 * Method being Tested: getNationsRecruited
	 * Should return correct number of nations recruited from
	 * 5 Different Nations Recruited From - Should return 5
	 * Note: Last Tested on 07/09/2015, adjust dates accordingly to test method
	 */
	@Test
	public void getPaymentsNationsRecruitedTest3() {
		
		Application a1 = new Application();
		Application a2 = new Application();
		Application a3 = new Application();
		Application a4 = new Application();
		Application a5 = new Application();
		
		a1.setPaymentStatus("Deposit Paid");
		a1.setAppStatus("Received");
		a1.setCourse("Summer School 2015");
		a1.setCountry("UK");
		
		a2.setPaymentStatus("Deposit Paid");
		a2.setAppStatus("Received");
		a2.setCourse("Summer School 2015");
		a2.setCountry("China");

		a3.setPaymentStatus("Deposit Paid");
		a3.setAppStatus("Received");
		a3.setCourse("Summer School 2015");
		a3.setCountry("Japan");

		a4.setPaymentStatus("Not Paid");
		a4.setAppStatus("Received");
		a4.setCourse("Summer School 2015");
		a4.setCountry("Israel");

		a5.setPaymentStatus("Not Paid");
		a5.setAppStatus("Received");
		a5.setCourse("Summer School 2015");
		a5.setCountry("Greece");
		
		
		ArrayList<Application> list = new ArrayList<Application>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		
		int expected = 3;
		
		assertEquals(expected, Controller.getNationsRecruited(list));
	}
	
	/**
	 * Method being Tested: computeWeeklyPriceEnq
	 * Should return correct price
	 */
	@Test
	public void computeWeeklyPriceEnqsTest() {
		
		String totalPrice = "10000";
		String noWeeks = "5";
		
		String expected = "2000.0";
		
		assertEquals(expected, Controller.computeWeeklyPriceEnq(totalPrice, noWeeks));
	}
	
	/**
	 * Method being Tested: computeWeeklyPriceEnq
	 * Should return correct price
	 * 0 - Should return 0
	 */
	@Test
	public void computeWeeklyPriceEnqsTest2() {
		
		String totalPrice = "0";
		String noWeeks = "5";
		
		String expected = "0.0";
		
		assertEquals(expected, Controller.computeWeeklyPriceEnq(totalPrice, noWeeks));
	}
	
	/**
	 * Method being Tested: computeWeeklyPriceEnq
	 * Should return correct price
	 * 0 - Should return 0
	 */
	@Test
	public void computeWeeklyPriceEnqsTest3() {
		
		String totalPrice = "6275";
		String noWeeks = "7";
		
		String expected = "896.4";
		
		assertEquals(expected, Controller.computeWeeklyPriceEnq(totalPrice, noWeeks));
	}
	
	/**
	 * Method being Tested: computeWeeklyPriceEnq
	 * Should return correct price
	 * 0 - Should return 0
	 */
	@Test
	public void getDurationTest() {
		
		Calendar cal = Calendar.getInstance();
		long l1 = cal.getTime().getTime();
		
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DAY_OF_WEEK, + 6);
		long l2 = cal2.getTime().getTime();
		
		int expected = 6;
		
		assertEquals(expected, Controller.getDuration(l1, l2));
	}
	
	/**
	 * Method being Tested: computeWeeklyPriceEnq
	 * Should return correct price
	 * 0 - Should return 0
	 */
	@Test
	public void getDurationTest2() {
		
		Calendar cal = Calendar.getInstance();
		long l1 = cal.getTime().getTime();
		
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.MONTH, + 6);
		long l2 = cal2.getTime().getTime();
		
		int expected = 182;
		
		assertEquals(expected, Controller.getDuration(l1, l2));
	}
	
	/**
	 * Method being Tested: populateSignedAgents
	 * Testing only signed agents
	 */
	@Test
	public void populateSignedAgentsTest() {
		
		Agent a = new Agent();
		Agent a1 = new Agent();
		Agent a2 = new Agent();
		Agent a3 = new Agent();
		Agent a4 = new Agent();
		
		a.setStatus("Signed");
		a.setCompanyName("Agent");
		
		a1.setStatus("Signed");
		a1.setCompanyName("Agent1");
		
		a2.setStatus("Signed");
		a2.setCompanyName("Agent2");
		
		a3.setStatus("Active");
		a3.setCompanyName("Agent3");
		
		a4.setStatus("Active");
		a4.setCompanyName("Agent4");
		
		ArrayList<Agent> list = new ArrayList<Agent>();
		list.add(a);
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		
		boolean bool = false;
		
		User user = new User();
		user.setAgentsList(list);
		
		ArrayList<Agent> emptyList = new ArrayList<Agent>();
		
		
		Controller.populateSignedAgents(emptyList, user);
		
		if(emptyList.size() == 5){ // if it is 5 then all agents have been added
			bool = true;
		}

		assertTrue(bool);
	}
	
	/**
	 * Method being Tested: populateSignedAgents
	 * Testing some signed and some unsigned agents
	 */
	@Test
	public void populateSignedAgentsTest2() {
		
		Agent a = new Agent();
		Agent a1 = new Agent();
		Agent a2 = new Agent();
		Agent a3 = new Agent();
		Agent a4 = new Agent();
		
		a.setStatus("Signed");
		a.setCompanyName("Agent");
		
		a1.setStatus("Signed");
		a1.setCompanyName("Agent1");
		
		a2.setStatus("Signed");
		a2.setCompanyName("Agent2");
		
		a3.setStatus("Cold");
		a3.setCompanyName("Agent3");
		
		a4.setStatus("Cold");
		a4.setCompanyName("Agent4");
		
		ArrayList<Agent> list = new ArrayList<Agent>();
		list.add(a);
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		
		boolean bool = false;
		
		User user = new User();
		user.setAgentsList(list);
		
		ArrayList<Agent> emptyList = new ArrayList<Agent>();
		
		
		Controller.populateSignedAgents(emptyList, user);
		
		if(emptyList.size() == 3){ // if it is 3 then only the three signed agents have been added
			bool = true;
		}
		System.out.println(emptyList.size());
		assertTrue(bool);
	}
	
	/**
	 * Method being Tested: populateSignedAgents
	 * Empty Case - Should return empty ArrayList
	 */
	@Test
	public void populateSignedAgentsTest3() {
		
		
		
		ArrayList<Agent> list = new ArrayList<Agent>();
	
		boolean bool = false;
		
		User user = new User();
		user.setAgentsList(list);
		
		ArrayList<Agent> emptyList = new ArrayList<Agent>();
		
		
		Controller.populateSignedAgents(emptyList, user);
		
		if(emptyList.size() == 0){ 
			bool = true;
		}
	
		assertTrue(bool);
	}
	
	/**
	 * Method being Tested: populateSignedAgents
	 * Testing only Unsigned agents
	 */
	@Test
	public void populateUnsignedAgentsTest() {
		
		Agent a = new Agent();
		Agent a1 = new Agent();
		Agent a2 = new Agent();
		Agent a3 = new Agent();
		Agent a4 = new Agent();
		
		a.setStatus("Cold");
		a.setCompanyName("Agent");
		
		a1.setStatus("Cold");
		a1.setCompanyName("Agent1");
		
		a2.setStatus("Cold");
		a2.setCompanyName("Agent2");
		
		a3.setStatus("Expected");
		a3.setCompanyName("Agent3");
		
		a4.setStatus("Expected");
		a4.setCompanyName("Agent4");
		
		ArrayList<Agent> list = new ArrayList<Agent>();
		list.add(a);
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		
		boolean bool = false;
		
		User user = new User();
		user.setAgentsList(list);
		
		ArrayList<Agent> emptyList = new ArrayList<Agent>();
		
		
		Controller.populateUnsignedAgents(emptyList, user);
		
		if(emptyList.size() == 5){ // if it is 5 then all agents have been added
			bool = true;
		}
		assertTrue(bool);
	}
	
	/**
	 * Method being Tested: populateSignedAgents
	 * Testing some signed and some unsigned agents
	 */
	@Test
	public void populateUnsignedAgentsTest2() {
		
		Agent a = new Agent();
		Agent a1 = new Agent();
		Agent a2 = new Agent();
		Agent a3 = new Agent();
		Agent a4 = new Agent();
		
		a.setStatus("Signed");
		a.setCompanyName("Agent");
		
		a1.setStatus("Signed");
		a1.setCompanyName("Agent1");
		
		a2.setStatus("Cold");
		a2.setCompanyName("Agent2");
		
		a3.setStatus("Cold");
		a3.setCompanyName("Agent3");
		
		a4.setStatus("Cold");
		a4.setCompanyName("Agent4");
		
		ArrayList<Agent> list = new ArrayList<Agent>();
		list.add(a);
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		
		boolean bool = false;
		
		User user = new User();
		user.setAgentsList(list);
		
		ArrayList<Agent> emptyList = new ArrayList<Agent>();
		
		
		Controller.populateUnsignedAgents(emptyList, user);
		
		if(emptyList.size() == 3){ // if it is 3 then only the three unsigned agents have been added
			bool = true;
		}
		assertTrue(bool);
	}
	
	/**
	 * Method being Tested: populateSignedAgents
	 * Empty Case - Should return empty ArrayList
	 */
	@Test
	public void populateUnsignedAgentsTest3() {
		
		
		
		ArrayList<Agent> list = new ArrayList<Agent>();
	
		boolean bool = false;
		
		User user = new User();
		user.setAgentsList(list);
		
		ArrayList<Agent> emptyList = new ArrayList<Agent>();
		
		
		Controller.populateUnsignedAgents(emptyList, user);
		
		if(emptyList.size() == 0){ 
			bool = true;
		}
		System.out.println(emptyList.size());
		assertTrue(bool);
	}
	
	
	
	
}
