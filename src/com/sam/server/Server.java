
/**
 * This class listens for incoming connections on a specified port. 
 * When a client connection is received, it accepts the connection
 * and ServerThread for each client. 
 * 
 * All client-server communication is achieved by the
 * passing of User objects between the client and server. 
 * 
 * Once a client connects, a ServerThread is created and started,
 * with the inner class containing the run method. This consists
 * of numerous conditional statements based on each User objects
 * command received from the client. Depending on the command,
 * the server performs the required action calling the JDBC
 * class to connect to the database when necessary.  
 * 
 * References: 
 * http://mrbool.com/file-transfer-between-2-computers-with-java/24516
 * http://stackoverflow.com/questions/1105569/java-io-inputstream-blocks-while-reading-standard-output-standard-error-of-an
 * http://www.baeldung.com/convert-input-stream-to-a-file
 * http://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
 * http://stackoverflow.com/questions/10575624/java-string-see-if-a-string-contains-only-numbers-and-not-letters
 * 
 * @author Ian Shields, 1506416
 * @version 21/08/2015
 */
package com.sam.server;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sam.client.LoginFrame;
import com.sam.database.JDBC;

public class Server  {

	// user initially defined by client so set as null here
	User user = null;
	boolean bool = false;
	
	/** 
	* This method starts the server and listens on the specified port. 
	* Once a client connection is received, the method starts a thread
	* for the client, and then the inner class ServerThread executes
	*/
	public void initializeServer(){
		try {
			System.out.println("initializeServer() method called \n");
			ServerSocket socketConnection = new ServerSocket(5432);
			System.out.println("Server is listening on port 5432 \n");
			
			while(true){
				// this method blocks until a connection is made by client
				Socket pipe  = socketConnection.accept();
				
				// start a new thread for each client connection made (see inner thread class below)
				new ServerThread(pipe).start();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[]args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		LoginFrame lf = new LoginFrame();
		Server server = new Server();
		server.initializeServer();
		
	}
	
	/** 
	* This is the inner thread class which defines the run method
	* for the thread, which defines the various actions to take depending 
	* on the user command of the client.
	*/
	public class ServerThread extends Thread{
		
		
		protected Socket pipe;
		
		public ServerThread(Socket pipe){
			this.pipe = pipe;
		}
		
		public void run(){
			
			try{
				System.out.println("Connection made and client thread started: " + " " + this.getName() + "\n");
		
				// initialize input and output streams for server
				ObjectInputStream serverIn = new ObjectInputStream(pipe.getInputStream());
				ObjectOutputStream serverOut = new ObjectOutputStream(pipe.getOutputStream());
				
				System.out.println("Server input and output streams initialized \n");
					
				User user = (User) serverIn.readObject();
				if(user.getCommand().equals("login")){
				
				System.out.println("JDBC.authorizeLogin() method called by server \n");
				
				// call JDBC Login method to authenticate user
				String temp = JDBC.authorizeLogin(user.getUsername(), user.getPassword());
				user.setType(temp);
				
				// user is staff, call JDBC to populate the staff members user data from database
				if(temp.equals("admin") || temp.equals("staff")){
					JDBC.generateStaffUserData(user);
					
				}
				// user is agent, call JDBC to populate the staff members user data from database
				else if(temp.equals("agent")){
					JDBC.generateAgentUserData(user);
					
				}
				// write response back to client 
				serverOut.writeObject(user);
				System.out.println("Server is sending user object back through its output stream to client \n");
				
				}
				
				// if user wants to view an individual application, call JDBC method to retrieve application by UID
				else if(user.getCommand().contains("getuid")){
					// parses UID from user command
					String temp = user.getCommand().substring(12);
					int uid = Integer.parseInt(temp);
					System.out.println(uid);
					Application app = JDBC.getApplicationByUID(uid);
					serverOut.writeObject(app);
				}
				// if user wants to add application, call JDBC methods to add and confirm it has been added
				else if(user.getCommand().equals("addapp")){
					user.getApplicationToBeAdded().setUid(JDBC.generateAppUid());
					JDBC.addApplication(user);
					boolean temp = JDBC.checkApplicationAdded(user.getApplicationToBeAdded().getUid());
					
					if(temp == true){
						user.setCommand("success");
						JDBC.setAgentStats(user.getAgentsList());
						serverOut.writeObject(user);
					}
					
					else{
						user.setCommand("fail");
						serverOut.writeObject(user);
					}
				}
				// if user wants to add enquiry, call JDBC methods to add and confirm it has been added
				else if (user.getCommand().equals("addenq")){
					
					user.getEnquiryToBeAdded().setEid(JDBC.generateEnqEid());
					JDBC.addEnquiry(user);
					boolean temp = JDBC.checkEnquiryAdded(user.getEnquiryToBeAdded().getEid());
					
					if(temp == true){
						user.setCommand("success");
						serverOut.writeObject(user);
					}
					
					else{
						user.setCommand("fail");
						serverOut.writeObject(user);
					}
				}
				// if user wants to view an individual enquiry, call JDBC method to retrieve enquiry by EID
				else if(user.getCommand().contains("geteid")){
					String temp = user.getCommand().substring(12);
					int eid = Integer.parseInt(temp);
					System.out.println(eid);
					Enquiry enq = JDBC.getEnquiryByEID(eid);
					serverOut.writeObject(enq);
				}
				// if user wants to update an enquiry, call the relevant JDBC method
				else if(user.getCommand().equals("updateenq")){
					if(JDBC.updateEnquiry(user) == true){
						user.setCommand("success");
					}
					else{
						user.setCommand("fail");
					}
					serverOut.writeObject(user);
				}
				// if user wants to update application, call the relevant JDBC method
				else if(user.getCommand().equals("updateapp")){
					if(JDBC.updateApplication(user) == true){
						user.setCommand("success");
						
						JDBC.setAgentStats(user.getAgentsList());
					}
					else{
						user.setCommand("fail");
					}
					serverOut.writeObject(user);
				}
				// if user wants to add an agent, call the relevant JDBC method
				else if(user.getCommand().equals("addagent")){
					if(JDBC.addAgent(user.getAgentToBeAdded()) == true){
						user.setCommand("success");
					}
					else{
						user.setCommand("fail");
					}
					serverOut.writeObject(user);
					
				}
				// if user wants to view individual agent, call JDBC to get agent by agent name 
				else if(user.getCommand().contains("getagt")){
					System.out.println("METHOD CALLED");
					String agentName = user.getCommand().substring(21);
					System.out.println(agentName);
					Agent agent = JDBC.getAgentByAgentName(agentName);
					serverOut.writeObject(agent);
				}
				// if user wants to update an agent, call the relevant JDBC method
				else if(user.getCommand().equals("updateagent")){
					if(JDBC.updateAgent(user) == true){
						user.setCommand("success");
					}
					else{
						user.setCommand("fail");
					}
					serverOut.writeObject(user);
				}
				// if user wants to add a group app, call the relevant JDBC method
				else if(user.getCommand().equals("addgroupapp")){

					for(Application a : user.getGroupApp()){
						user.setApplicationToBeAdded(a);
						user.getApplicationToBeAdded().setUid(JDBC.generateAppUid());
						JDBC.addApplication(user);

					}
					try{
						boolean temp2 = JDBC.checkApplicationAdded(user.getApplicationToBeAdded().getUid());
						if(temp2 == true){
							System.out.println("It's true");
							user.setCommand("success");
							serverOut.writeObject(user);
						}
						else{
							user.setCommand("fail");
							serverOut.writeObject(user);
						}
					
						}catch(Exception e){
							System.out.println("Exception caught: No input paramaters entered for application");
							
							serverOut.writeObject(user);
						}	
				}
				// if user wants to add application note, call the relevant JDBC method
				else if(user.getCommand().equals("addappnote")){
					if(JDBC.addAppNote(user) == true){
					user.setCommand("success");
					System.out.println("Command set as: " + user.getCommand());
					serverOut.writeObject(user);
					}
					else{
						user.setCommand("fail");
						serverOut.writeObject(user);
					}
					
					
				}
				// if user wants to add an enquiry note, call the relevant JDBC method
				else if(user.getCommand().equals("addenqnote")){
					if(JDBC.addEnqNote(user) == true){
					user.setCommand("success");
					System.out.println("Command set as: " + user.getCommand());
					serverOut.writeObject(user);
					}
					else{
						user.setCommand("fail");
						serverOut.writeObject(user);
					}
					
					
				}
				// if user wants to add an agent note, call the relevant JDBC method
				else if(user.getCommand().equals("addagentnote")){
					if(JDBC.addAgentNote(user) == true){
					user.setCommand("success");
					serverOut.writeObject(user);
					}
					else{
						user.setCommand("fail");
						serverOut.writeObject(user);
					}
					
					
				}
				// if user wants to upload a file, check if it is an application upload or marketing upload and write file to correct location
				else if(user.getCommand().equals("file")){
					System.out.println("Triggered");
					// if the String contains only numbers it's a UID
					if(user.getCommand2().matches("[0-9]+")){
					File file = new File("C:/Users/Ian/Desktop/Server/"+ user.getCommand2()+user.getCommand3());
					FileOutputStream fos = new FileOutputStream(file);
			        fos.write(user.getFileBytes());
			        fos.flush();
			        fos.close();
					}
					// otherwise it must be a staff upload file for the downloads folder
					else{
						File file = new File("C:/Users/Ian/Desktop/Server/Downloads/"+ user.getCommand2()+user.getCommand3());
						FileOutputStream fos = new FileOutputStream(file);
				        fos.write(user.getFileBytes());
				        fos.flush();
				        fos.close();
					}
			       

				}
				// if user wants to save a file
				else if(user.getCommand().equals("filesave")){
					InputStream inputStream = null;
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
					try{
						
			        user.setFile(new File(user.getCommand3()));
			        System.out.println("File created: "+ user.getFile().getAbsolutePath());
			        
			      
					inputStream = new FileInputStream(user.getFile());

		            byte[] fileBytes = new byte[(int)user.getFile().length()];
		            baos = new ByteArrayOutputStream();

		            int bytesRead;
		            while ((bytesRead = inputStream.read(fileBytes)) != -1) {
		                baos.write(fileBytes, 0, bytesRead);
		            }
		            user.setFileBytes(fileBytes);
		            
		            user.setCommand("success");
		            serverOut.writeObject(user);
		            

		        } catch (FileNotFoundException e) {
		        	System.out.println("Users documents do not exist on server. Returning fail message to client.");
		        	user.setCommand("fail");
		        	serverOut.writeObject(user);
		            
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
				// updates downloads list
				else if(user.getCommand().equals("updatedownloads")){
					user.setFileNames(JDBC.getFileNames());
					user.setCommand("success");
					serverOut.writeObject(user);
				}
				// updates applications when one is added
				else if(user.getCommand().equals("updateagentapps")){
					
					if(user.getType().equals("staff") || user.getType().equals("admin")){
						JDBC.generateStaffUserData(user);
					}
					else if(user.getType().equals("agent")){
					JDBC.generateAgentUserData(user);
					}
					serverOut.writeObject(user);
				}
				
				else if(user.getCommand().equals("adduser")){
					boolean bool = (JDBC.addUser(user.getStaffUser()));
					if(bool == true){
					user.setCommand("success");
					}
					else{
						user.setCommand("fail");
					}
					serverOut.writeObject(user);
				}
				
				else if(user.getCommand().equals("removeuser")){
					boolean bool = (JDBC.removeUser(user.getCommand2()));
					if(bool == true){
						user.setCommand("success");
						}
						else{
							user.setCommand("fail");
						}
						serverOut.writeObject(user);
				}
				
				else if(user.getCommand().equals("addagentlogin")){
					boolean bool = (JDBC.addAgentLogin(user.getCommand2(), user.getCommand3()));
					if(bool == true){
						user.setCommand("success");
						}
						else{
							user.setCommand("fail");
						}
						serverOut.writeObject(user);
				}
				
				else if(user.getCommand().equals("removeagent")){
					boolean bool = (JDBC.removeAgent(user.getCommand2()));
					if(bool == true){
						user.setCommand("success");
						}
						else{
							user.setCommand("fail");
						}
						serverOut.writeObject(user);
				}
				
				else if(user.getCommand().equals("updateagentlogins")){
					
					JDBC.generateAgentsWithLogins(user.getAgentsWithLogins());
						serverOut.writeObject(user);
				}
				else if(user.getCommand().equals("updatepassword")){
					
				if(JDBC.updatePassword(user.getCommand2(), user.getCommand3()) == true);
					user.setCommand("success");
				}
				else{
					user.setCommand("fail");
				}
						serverOut.writeObject(user);

     	
			} 
			catch(Exception e){
				e.printStackTrace();
			}

		}

	}

}