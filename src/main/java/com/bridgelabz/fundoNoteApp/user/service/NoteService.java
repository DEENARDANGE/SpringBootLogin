package com.bridgelabz.fundoNoteApp.user.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoNoteApp.user.model.Note;

public interface NoteService {

	Note updateNote(String header, Note note);

	Note createNote(String header, Note note);

	boolean deleteNote(String token, Note note);

	List<Note> fetchNote(String header);

}
