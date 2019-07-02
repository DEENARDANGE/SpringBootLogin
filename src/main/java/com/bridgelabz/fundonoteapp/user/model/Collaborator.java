package com.bridgelabz.fundonoteapp.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Collaborator")
public class Collaborator implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	@Column(name="collId")
	private int collId;
	@Column(name = "noteId")
	private int noteId;
	
	@Column(name = "ownerId")
	private int ownerId;
	
	@Column(name = "allocId")
	private int allocId;

	
	public int getCollId() {
		return collId;
	}


	public void setCollId(int collId) {
		this.collId = collId;
	}


	public int getNoteId() {
		return noteId;
	}


	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}


	public int getOwnerId() {
		return ownerId;
	}


	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}


	public int getAllocId() {
		return allocId;
	}


	public void setAllocId(int allocId) {
		this.allocId = allocId;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	
	@Override
	public String toString() {
		return "Collaborator [collId=" + collId + ", allocId=" + allocId + ", noteId=" + noteId + ", ownerId=" + ownerId
				+ "]";
	}
	
	

}