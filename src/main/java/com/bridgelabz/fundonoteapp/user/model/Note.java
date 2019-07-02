package com.bridgelabz.fundonoteapp.user.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "note")
public class Note {

	
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Label.class, cascade = CascadeType.ALL)
	@JoinTable(name = "Note_Label", joinColumns = { @JoinColumn(name = "noteId") }, inverseJoinColumns = {
			@JoinColumn(name = "labelId") })
	private List<Label> labelList;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @Column(name = "noteid")
	private int noteId;
	private String title;
	private String discription;
	private LocalDateTime creadtedtime;
	private LocalDateTime updatetime;
	public boolean isIsarchive() {
		return isarchive;
	}

	public void setIsarchive(boolean isarchive) {
		this.isarchive = isarchive;
	}

	public Timestamp getReminder() {
		return reminder;
	}

	public void setReminder(Timestamp reminder) {
		this.reminder = reminder;
	}

	private boolean isarchive = false;
	private boolean ispinned;
	@Column(name = "intrash")
	private boolean intrash = false;
	@Column(name = "reminder")
	private Timestamp reminder;
	@Column(name = "id")
	private int userId;

	public boolean isIntrash() {
		return intrash;
	}

	public void setIntrash(boolean intrash) {
		this.intrash = intrash;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}


	public List<Label> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}

	public LocalDateTime getCreadtedtime() {
		return creadtedtime;
	}

	public void setCreadtedtime(LocalDateTime creadtedtime) {
		this.creadtedtime = creadtedtime;
	}

	public LocalDateTime getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(LocalDateTime updatetime) {
		this.updatetime = updatetime;
	}

	public boolean isIspinned() {
		return ispinned;
	}

	public void setIspinned(boolean ispinned) {
		this.ispinned = ispinned;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
		return "Note [labelList=" + labelList + ", noteId=" + noteId + ", title=" + title + ", discription="
				+ discription + ", isPinned=" + ispinned + ", inTrash=" + intrash + ", updateTime=" + updatetime
				+ ", createdTime=" + creadtedtime + ", reminder=" + reminder + ", userId=" + userId + ", isArchive="
				+ isarchive + "]";
	}

}
