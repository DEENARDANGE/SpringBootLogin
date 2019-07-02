package com.bridgelabz.fundonoteapp.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "label")
public class Label {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int labelId;
	private String labelName;
	@Column(name="noteId")
	private int noteId;
	@Column(name="userId")
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public int getLabelId() {
		return labelId;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}


	@Override
	public String toString() {
		return "User [labelId=" + labelId + ", labelName=" + labelName + ", noteId=" + noteId + "]";
	}
}
