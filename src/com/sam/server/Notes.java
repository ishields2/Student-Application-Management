/**
 * This class is used to define Notes objects as used by the program.
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
import java.util.Date;

public class Notes implements Serializable {
	
	int uid; // this is eid for enquiries
	String note;
	String author;
	String dateAdded;
	String time;
	int noteNumber;
	
	public Notes(int uid, String dateAdded,String note,  int noteNumber, String author,  String time) {
		
		this.uid = uid;
		this.dateAdded = dateAdded;
		this.note = note;
		this.noteNumber = noteNumber;
		this.author = author;
		this.time = time;
		
		
	}
	
	

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public int getNoteNumber() {
		return noteNumber;
	}



	public void setNoteNumber(int noteNumber) {
		this.noteNumber = noteNumber;
	}



	public int getUid() {
		return uid;
	}



	public void setUid(int uid) {
		this.uid = uid;
	}




	

}
