/**
 * This class is used to define Agent objects as used by the program.
 * 
 * The class implements serializable so that instances of the class
 * can be sent accross networks. 
 * 
 *  The class defines the object paramaters as well as the constructors,
 *  getters and setters for the object.
 * 
 * @author Ian Shields, 1506416
 * @version 07/09/2015
 */

package com.sam.server;

import java.io.Serializable;
import java.util.ArrayList;

public class Agent implements Serializable{
	
	String companyName;
	String accountManager;
	String firstName;
	String lastName;
	String country;
	String status;
	String address;
	String phone;
	String email;
	String last_contacted;
	int appsToDate;
	int studentsToDate;
	ArrayList<AgentNote>notes;
	
	
	
	
	public Agent(String companyName, String accountManager, String firstName,
			String lastName, String country, String status, String address,
			String phone, String email, String last_contacted, int appsToDate,
			int studentsToDate, ArrayList<AgentNote>notes) {
		super();
		this.companyName = companyName;
		this.accountManager = accountManager;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.status = status;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.last_contacted = last_contacted;
		this.appsToDate = appsToDate;
		this.studentsToDate = studentsToDate;
		this.notes = notes;
	}
	
	public Agent(){
		this.companyName = null;
		this.accountManager = null;
		this.firstName = null;
		this.lastName = null;
		this.country = null;
		this.status = null;
		this.address = null;
		this.phone = null;
		this.email = null;
		this.last_contacted = null;
		this.appsToDate = 0;
		this.studentsToDate = 0;
		this.notes = null;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLast_contacted() {
		return last_contacted;
	}
	public void setLast_contacted(String last_contacted) {
		this.last_contacted = last_contacted;
	}
	public int getAppsToDate() {
		return appsToDate;
	}
	public void setAppsToDate(int appsToDate) {
		this.appsToDate = appsToDate;
	}
	public int getStudentsToDate() {
		return studentsToDate;
	}
	public void setStudentsToDate(int studentsToDate) {
		this.studentsToDate = studentsToDate;
	}

	public ArrayList<AgentNote> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<AgentNote> notes) {
		this.notes = notes;
	}
	
	

}
