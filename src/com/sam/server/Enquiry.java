/**
 * This class is used to define Enquiry objects as used by the program.
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
import java.util.Date;

public class Enquiry implements Serializable {

	int eid;
	String fName;
	String lName;
	String enqDate;
	String noWeeks;
	String status;
	String lastContacted;
	String source;
	String staffMember;
	String country;
	String agent;
	String course;
	String email;
	String phone;
	String campus;
	String weeklyPrice;
	String totalPrice;
	ArrayList<Notes> enquiryNotes;
	
	public Enquiry(int eid, String fName, String lName, String enqDate,
			String noWeeks, String status, String lastContacted, String source,
			String staffMember, String country, String agent, String course,
			String email, String phone, String campus, String weeklyPrice,
			String totalPrice, ArrayList<Notes> enquiryNotes) {
		super();
		this.eid = eid;
		this.fName = fName;
		this.lName = lName;
		this.enqDate = enqDate;
		this.noWeeks = noWeeks;
		this.status = status;
		this.lastContacted = lastContacted;
		this.source = source;
		this.staffMember = staffMember;
		this.country = country;
		this.agent = agent;
		this.course = course;
		this.email = email;
		this.phone = phone;
		this.campus = campus;
		this.weeklyPrice = weeklyPrice;
		this.totalPrice = totalPrice;
		this.enquiryNotes = enquiryNotes;
	}
	
	public Enquiry(){
		this.eid = 0;
		this.fName = null;
		this.lName = null;
		this.enqDate = null;
		this.noWeeks = null;
		this.status = null;
		this.lastContacted = null;
		this.source = null;
		this.staffMember = null;
		this.country = null;
		this.agent = null;
		this.course = null;
		this.email = null;
		this.phone = null;
		this.campus = null;
		this.weeklyPrice = null;
		this.totalPrice = null;
		this.enquiryNotes = null;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEnqDate() {
		return enqDate;
	}
	public void setEnqDate(String enqDate) {
		this.enqDate = enqDate;
	}
	public String getNoWeeks() {
		return noWeeks;
	}
	public void setNoWeeks(String noWeeks) {
		this.noWeeks = noWeeks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastContacted() {
		return lastContacted;
	}
	public void setLastContacted(String lastContacted) {
		this.lastContacted = lastContacted;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStaffMember() {
		return staffMember;
	}
	public void setStaffMember(String staffMember) {
		this.staffMember = staffMember;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getWeeklyPrice() {
		return weeklyPrice;
	}
	public void setWeeklyPrice(String weeklyPrice) {
		this.weeklyPrice = weeklyPrice;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public ArrayList<Notes> getEnquiryNotes() {
		return enquiryNotes;
	}
	public void setEnquiryNotes(ArrayList<Notes> enquiryNotes) {
		this.enquiryNotes = enquiryNotes;
	}
	

}