package com.sam.server;

import java.io.Serializable;

public class StaffUser implements Serializable {
	
	String firstName;
	String lastName;
	String username;
	String password;
	String type;
	
	public StaffUser (String firstName, String lastName, 
			String username, String password, String type){
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.type = type;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
