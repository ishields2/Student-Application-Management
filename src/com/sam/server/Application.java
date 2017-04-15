/**
 * This class is used to define Application objects as used by the program.
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

public class Application implements Serializable {

	int uid;
	String firstName;
	String lastName;
	String agent;
	String weeklyPrice;
	String paymentStatus;
	String visaStatus;
	String duration;
	String nationality;
	String country;
	String gender;
	String passportNo;
	String appDate;
	String campus;
	String appStatus;
	String salesAdvisor;
	String course;
	String address;
	String totalPrice;
	String lastContacted;
	String arrivalDate;
	String departureDate;
	String dob;
	String phone;
	String email;
	String source;
	ArrayList<Notes> applicationNotes;

	public Application(int uid, String firstName, String lastName,
			String agent, String paymentStatus, String visaStatus,
			String nationality, String country, String gender,
			String passportNo, String appDate, String campus, String appStatus,
			String salesAdvisor, String course, String address,
			String lastContacted, String arrivalDate, String departureDate,
			String dob, String phone, String email, String source,
			String weeklyPrice, String totalPrice, String duration,
			ArrayList<Notes> applicationNotes) {

		super();
		this.uid = uid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.agent = agent;

		this.paymentStatus = paymentStatus;
		this.visaStatus = visaStatus;

		this.nationality = nationality;
		this.country = country;
		this.gender = gender;
		this.passportNo = passportNo;
		this.appDate = appDate;
		this.campus = campus;
		this.appStatus = appStatus;
		this.salesAdvisor = salesAdvisor;
		this.course = course;
		this.address = address;

		this.lastContacted = lastContacted;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.dob = dob;
		this.phone = phone;
		this.email = email;
		this.source = source;
		this.weeklyPrice = weeklyPrice;
		this.totalPrice = totalPrice;
		this.duration = duration;

		this.applicationNotes = applicationNotes;

	}

	public Application() {

		this.uid = 0;
		this.firstName = null;
		this.lastName = null;
		this.agent = null;
		this.paymentStatus = null;
		this.visaStatus = null;
		this.nationality = null;
		this.country = null;
		this.gender = null;
		this.passportNo = null;
		this.appDate = null;
		this.campus = null;
		this.appStatus = null;
		this.salesAdvisor = null;
		this.course = null;
		this.address = null;
		this.lastContacted = null;
		this.arrivalDate = null;
		this.departureDate = null;
		this.dob = null;
		this.phone = null;
		this.email = null;
		this.source = null;
		this.weeklyPrice = null;
		this.totalPrice = null;
		this.duration = null;
		this.applicationNotes = null;

	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
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

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getWeeklyPrice() {
		return weeklyPrice;
	}

	public void setWeeklyPrice(String weeklyPrice) {
		this.weeklyPrice = weeklyPrice;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getVisaStatus() {
		return visaStatus;
	}

	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getSalesAdvisor() {
		return salesAdvisor;
	}

	public void setSalesAdvisor(String salesAdvisor) {
		this.salesAdvisor = salesAdvisor;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getLastContacted() {
		return lastContacted;
	}

	public void setLastContacted(String lastContacted) {
		this.lastContacted = lastContacted;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
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

	public ArrayList<Notes> getApplicationNotes() {
		return applicationNotes;
	}

	public void setApplicationNotes(ArrayList<Notes> applicationNotes) {
		this.applicationNotes = applicationNotes;
	}

}
