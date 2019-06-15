package com.bridgelabz.fundoNoteApp.user.service;

import java.util.List;

import com.bridgelabz.fundoNoteApp.user.model.Label;
import com.bridgelabz.fundoNoteApp.user.model.Note;

public interface NoteService {

	Note updateNote(String header, Note note);

	Note createNote(String header, Note note);

	String deleteNote(int NoteId);

	Label updateLabel(String header, Label label);

	Label createLabel(String header, Label label);

	boolean deleteLabel(String header, Label label);

	List<Note> getNote(String token);

	List<Label> getLabel(String token);

}
