package com.bridgelabz.fundoNoteApp.user.service;


import com.bridgelabz.fundoNoteApp.user.model.Note;

public interface NoteService {


	void updateNote(String header, Note note);

	boolean deleteNote(String header);


	Note createNote(String header, Note note);


}
