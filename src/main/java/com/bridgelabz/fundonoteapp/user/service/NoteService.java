package com.bridgelabz.fundonoteapp.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundonoteapp.user.model.Label;
import com.bridgelabz.fundonoteapp.user.model.Note;

public interface NoteService {

	Note updateNote(String header, Note note);

	Note createNote(String header, Note note);

	String deleteNote(int NoteId);

	//Label updateLabel(String header, Label label);

	//Label createLabel(Label label, int noteId);

	//boolean deleteLabel(int noteId, Label label);

	List<Note> getNote(String token);

//	List<Label> getLabel(String token);

//	Label createLabel(int noteId, Label label);

	//Label updateLabel(int noteId, Label label);

	//List<Label> getLabel(int noteId);

	List<Label> getLabel(String token, HttpServletRequest request);

	Object createLabel(String token, Label label, HttpServletRequest request);

	Object updateLabel(String token, Label label, HttpServletRequest request);

	boolean mapNoteToLabel(String token, int noteId, int labelId);

	boolean deleteLabel(String token, String labelName, HttpServletRequest request);

	

}
