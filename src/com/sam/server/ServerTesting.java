/**
 * This class contains the JUnit tests for the Server.
 * There is also testing of the Controller class login method
 * as this is needed in order to test the server. 
 * 
 * @author Ian Shields, 1506416
 * @version 07-09-2015
 * 
 * References:
 * http://junit.sourceforge.net/javadoc/
 */

package com.sam.server;

import org.junit.Test;

import com.sam.client.Controller;

import static org.junit.Assert.*;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ServerTesting {
	
	/**
	 * Method being Tested: login, inititalizeServer
	 * Testing connections established and user can log in
	 */
	@Test
	public void clientServerLoginTest() {
		
		JTextField username = new JTextField("ishields");
		
		JTextField password = new JTextField("password");
		
		JLabel label = new JLabel();

		assertTrue(Controller.Login(username, password, label));
	}
	
	/**
	 * Method being Tested: login, inititalizeServer
	 * Testing an invalid username, should return false
	 */
	@Test
	public void clientServerLoginTest2() {
		
		JTextField username = new JTextField("I'm Not a User");
		
		JTextField password = new JTextField("password");
		
		JLabel label = new JLabel();

		assertFalse(Controller.Login(username, password, label));
	}
	
	/**
	 * Method being Tested: login, inititalizeServer
	 * Testing an invalid password, should return false
	 */
	@Test
	public void clientServerLoginTest3() {
		
		JTextField username = new JTextField("ishields");
		
		JTextField password = new JTextField("I'm Not a Password");
		
		JLabel label = new JLabel();

		assertFalse(Controller.Login(username, password, label));
	}
	
	/**
	 * Method being Tested: login, inititalizeServer
	 * Testing an invalid username and password, should return false
	 */
	@Test
	public void clientServerLoginTest4() {
		
		JTextField username = new JTextField("I'm Not a Username");
		
		JTextField password = new JTextField("I'm Not a Password");
		
		JLabel label = new JLabel();

		assertFalse(Controller.Login(username, password, label));
	}
	
	/**
	 * Method being Tested: login, inititalizeServer
	 * Empty Case - No String set for username or password, should return false
	 */
	@Test
	public void clientServerLoginTest5() {
		
		JTextField username = new JTextField();
		
		JTextField password = new JTextField();
		
		JLabel label = new JLabel();

		assertFalse(Controller.Login(username, password, label));
	}

}
