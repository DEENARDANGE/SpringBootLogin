package com.bridgelabz.fundoNoteApp.user.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "note")
public class Note {

	@Id
	private int noteid;
	private String title;
	private String discription;
	private Timestamp creadtedtime;
	private Timestamp updatetime;
	private boolean isarchive;
	private boolean ispinned;
	private boolean intrash;
	private int id;

	public Note() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNoteid() {
		return noteid;
	}

	public void setNoteid(int noteid) {
		this.noteid = noteid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	@Override
	public String toString() {
		return "User [noteid=" + noteid + ", title=" + title + ", discription=" + discription + ", createdtime="
				+ creadtedtime + ", updatetime=" + updatetime + ", isarchive=" + isarchive + ", ispinned=" + ispinned
				+ ",intrash=" + intrash + "]";
	}

}
