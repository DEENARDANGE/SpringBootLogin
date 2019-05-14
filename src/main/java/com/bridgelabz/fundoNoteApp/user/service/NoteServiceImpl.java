package com.bridgelabz.fundoNoteApp.user.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoNoteApp.user.model.Note;
import com.bridgelabz.fundoNoteApp.user.repository.NoteRepository;
import com.bridgelabz.fundoNoteApp.util.JsonToken;
import com.bridgelabz.fundoNoteApp.util.Util;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	public NoteRepository noteRep;
	@Autowired
	public JsonToken jsonToken;
	
	 //Util noteUtil;
	
	
	@Override
	public Note createNote(String token, Note note) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("note creation :"+varifiedUserId);
		/*
		 * noteRep.save(note); Optional<Note> newNote =
		 * noteRep.findById(varifiedUserId); if (newNote != null) {
		 * System.out.println("Sucessfull created"); } else {
		 * System.out.println("Somthing went wrong!!!!!"); } return null;
		 */
		
		note.setId(varifiedUserId);
		return noteRep.save(note);
	}

	@Override
	public void updateNote(String token, Note note) {
		int varifiedUserId = jsonToken.tokenVerification(token);

	}

	@Override
	public boolean deleteNote(String token) {
		int varifiedUserId = jsonToken.tokenVerification(token);
        Optional<Note> maybeNote = noteRep.findById(varifiedUserId);
        return maybeNote.map(existingNote -> {
        	noteRep.delete(existingNote);
            return true;
        }).orElseGet(() -> false);
	}

}
