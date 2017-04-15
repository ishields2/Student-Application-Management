/**
 * This class is used to define User objects as used by the program.
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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

// implements serializable so that it can be passed through input and output streams
public class User implements Serializable  {
	
	/**
	 * 
	 */
	
	// field variables
	private String username;
	private String password;
	private String type;
	private String command;
	private String command2;
	private String command3;
	
	
	private Application applicationToBeAdded;
	private ArrayList<Agent> agentsList;
	private String[] campuses;
	private String[] courses;
	private ArrayList<Application> userApplications;
	private String[] staffMembers;
	private ArrayList<Enquiry> enquiries;
	private Enquiry enquiryToBeAdded;
	private Agent agentToBeAdded;
	private ArrayList<Application> groupApp;
	private Notes noteToBeAdded;
	private ArrayList<Notes>userNotes;
	private ArrayList<Notes>userEnqNotes;
	private ArrayList<AgentNote>userAgentNotes;
	private AgentNote agentNoteToBeAdded;
	
	private File file;
	private byte[] fileBytes;
	ArrayList<Application>searchApps;
	ArrayList<Enquiry>searchEnqs;
	ArrayList<Agent>searchAgents;
	private String staffMember;
	private String[] fileNames;
	private StaffUser staffUser;
	private ArrayList<StaffUser> staffUsers;
	private ArrayList<String> agentsWithLogins;
	private ArrayList<String> agentsWithoutLogins;
	

	
	//constructor
	public User(String username, String password, String type, String command, String command2,String command3,
			Application applicationToBeAdded, ArrayList<Agent>agentsList, 
			String[]campuses, String[]courses, ArrayList<Application> userApplications, 
			String[] staffMembers, ArrayList<Enquiry> enquiries, Enquiry enquiryToBeAdded,
			Agent agentToBeAdded, ArrayList<Application> groupApp, Notes noteToBeAdded,
			ArrayList<Notes>userNotes, ArrayList<Notes>userEnqNotes, File file, byte[]fileBytes,
			ArrayList<Application>searchApps, ArrayList<Enquiry>searchEnqs, 
			ArrayList<Agent>searchAgents, String staffMember,  String[]fileNames, ArrayList<AgentNote>userAgentNotes,
			AgentNote agentNoteToBeAdded, StaffUser staffUser, ArrayList<StaffUser> staffUsers,
			ArrayList<String> agentsWithLogins, ArrayList<String> agentsWithoutLogins){
		this.username = username;
		this.password = password;
		this.type = type;
		this.command = command;
		this.command = command2;
		this.command = command3;
		this.applicationToBeAdded = applicationToBeAdded;
		this.agentsList = agentsList;
		this.campuses = campuses;
		this.courses = courses;
		this.userApplications = userApplications;
		this.staffMembers = staffMembers;
		this.enquiries = enquiries;
		this.enquiryToBeAdded = enquiryToBeAdded;
		this.agentToBeAdded = agentToBeAdded;
		this.groupApp = groupApp;
		this.noteToBeAdded = noteToBeAdded;
		this.userNotes = userNotes;
		this.userEnqNotes = userEnqNotes;
		this.file = file;
		this.fileBytes = fileBytes;
		this.searchApps=searchApps;
		this.searchEnqs=searchEnqs;
		this.searchAgents=searchAgents;
		this.staffMember = staffMember;
		this.fileNames = fileNames;
		this.userAgentNotes = userAgentNotes;
		this.agentNoteToBeAdded = agentNoteToBeAdded;
		this.staffUser = staffUser;
		this.staffUsers = staffUsers;
		this.agentsWithLogins = agentsWithLogins;
		this.agentsWithoutLogins = agentsWithoutLogins;
		
	}
	
	public User(){
		this.username = null;
		this.password = null;
		this.type = null;
		this.command = null;
		this.command2 = null;
		this.command3 = null;
		this.applicationToBeAdded = null;
		this.agentsList = null;
		this.campuses = null;
		this.courses = null;
		this.userApplications = null;
		this.staffMembers = null;
		this.enquiries = null;
		this.enquiryToBeAdded = null;
		this.agentToBeAdded = null;
		this.groupApp = null;
		this.noteToBeAdded = null;
		this.userNotes = null;
		this.userEnqNotes = null;
		this.file = null;
		this.fileBytes = null;
		this.searchApps=null;
		this.searchEnqs=null;
		this.searchAgents=null;
		this.staffMember = null;
		this.fileNames = null;
		this.userAgentNotes = null;
		agentNoteToBeAdded = null;
		this.staffUser = null;
		this.staffUsers = null;
		this.agentsWithLogins = null;
		this.agentsWithoutLogins = null;
	}

	// getters and setters 
	
	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

	public ArrayList<Notes> getUserNotes() {
		return userNotes;
	}

	public void setUserNotes(ArrayList<Notes> userNotes) {
		this.userNotes = userNotes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Application getApplicationToBeAdded() {
		return applicationToBeAdded;
	}

	public void setApplicationToBeAdded(Application applicationToBeAdded) {
		this.applicationToBeAdded = applicationToBeAdded;
	}

	public ArrayList<Agent> getAgentsList() {
		return agentsList;
	}

	public void setAgentsList(ArrayList<Agent> agentsList) {
		this.agentsList = agentsList;
	}

	public String[] getCampuses() {
		return campuses;
	}

	public void setCampuses(String[] campuses) {
		this.campuses = campuses;
	}

	public String[] getCourses() {
		return courses;
	}

	public void setCourses(String[] courses) {
		this.courses = courses;
	}


	public ArrayList<Application> getUserApplications() {
		return userApplications;
	}

	public void setUserApplications(ArrayList<Application> userApplications) {
		this.userApplications = userApplications;
	}

	public String[] getStaffMembers() {
		return staffMembers;
	}

	public void setStaffMembers(String[] staffMembers) {
		this.staffMembers = staffMembers;
	}

	public ArrayList<Enquiry> getEnquiries() {
		return enquiries;
	}

	public void setEnquiries(ArrayList<Enquiry> enquiries) {
		this.enquiries = enquiries;
	}

	public Enquiry getEnquiryToBeAdded() {
		return enquiryToBeAdded;
	}

	public void setEnquiryToBeAdded(Enquiry enquiryToBeAdded) {
		this.enquiryToBeAdded = enquiryToBeAdded;
	}

	public Agent getAgentToBeAdded() {
		return agentToBeAdded;
	}

	public void setAgentToBeAdded(Agent agentToBeAdded) {
		this.agentToBeAdded = agentToBeAdded;
	}

	public ArrayList<Application> getGroupApp() {
		return groupApp;
	}

	public void setGroupApp(ArrayList<Application> groupApp) {
		this.groupApp = groupApp;
	}

	public Notes getNoteToBeAdded() {
		return noteToBeAdded;
	}

	public void setNoteToBeAdded(Notes noteToBeAdded) {
		this.noteToBeAdded = noteToBeAdded;
	}

	public ArrayList<Notes> getUserEnqNotes() {
		return userEnqNotes;
	}

	public void setUserEnqNotes(ArrayList<Notes> userEnqNotes) {
		this.userEnqNotes = userEnqNotes;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getCommand2() {
		return command2;
	}

	public void setCommand2(String command2) {
		this.command2 = command2;
	}

	public String getCommand3() {
		return command3;
	}

	public void setCommand3(String command3) {
		this.command3 = command3;
	}

	public ArrayList<Application> getSearchApps() {
		return searchApps;
	}

	public void setSearchApps(ArrayList<Application> searchApps) {
		this.searchApps = searchApps;
	}

	public ArrayList<Enquiry> getSearchEnqs() {
		return searchEnqs;
	}

	public void setSearchEnqs(ArrayList<Enquiry> searchEnqs) {
		this.searchEnqs = searchEnqs;
	}

	public ArrayList<Agent> getSearchAgents() {
		return searchAgents;
	}

	public void setSearchAgents(ArrayList<Agent> searchAgents) {
		this.searchAgents = searchAgents;
	}

	public String getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(String staffMember) {
		this.staffMember = staffMember;
	}



	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public ArrayList<AgentNote> getUserAgentNotes() {
		return userAgentNotes;
	}

	public void setUserAgentNotes(ArrayList<AgentNote> userAgentNotes) {
		this.userAgentNotes = userAgentNotes;
	}

	public AgentNote getAgentNoteToBeAdded() {
		return agentNoteToBeAdded;
	}

	public void setAgentNoteToBeAdded(AgentNote agentNoteToBeAdded) {
		this.agentNoteToBeAdded = agentNoteToBeAdded;
	}

	public StaffUser getStaffUser() {
		return staffUser;
	}

	public void setStaffUser(StaffUser staffUser) {
		this.staffUser = staffUser;
	}

	public ArrayList<StaffUser> getStaffUsers() {
		return staffUsers;
	}

	public void setStaffUsers(ArrayList<StaffUser> staffUsers) {
		this.staffUsers = staffUsers;
	}

	public ArrayList<String> getAgentsWithLogins() {
		return agentsWithLogins;
	}

	public void setAgentsWithLogins(ArrayList<String> agentsWithLogins) {
		this.agentsWithLogins = agentsWithLogins;
	}

	public ArrayList<String> getAgentsWithoutLogins() {
		return agentsWithoutLogins;
	}

	public void setAgentsWithoutLogins(ArrayList<String> agentsWithoutLogins) {
		this.agentsWithoutLogins = agentsWithoutLogins;
	}
	
	
}
