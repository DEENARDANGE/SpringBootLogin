package com.bridgelabz.fundoNoteApp.user.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoNoteApp.user.model.Note;
import com.bridgelabz.fundoNoteApp.user.repository.NoteRepository;
import com.bridgelabz.fundoNoteApp.util.JsonToken;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	public NoteRepository noteRep;
	@Autowired
	public JsonToken jsonToken;

	@Override
	public Note createNote(String token, Note note) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("note creation :" + varifiedUserId);
		note.setUserId(varifiedUserId);
		return noteRep.save(note);
	}

	@Override
	public Note updateNote(String token, Note note) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("varifiedUserId :" + varifiedUserId);
		Optional<Note> maybeNote = noteRep.findByUserIdAndNoteId(varifiedUserId, note.getNoteId());
		System.out.println("maybeNote :" + maybeNote);
		Note presentNote = maybeNote.map(existingNote -> {
			System.out.println("noteee here");
			existingNote.setDiscription(
					note.getDiscription() != null ? note.getDiscription() : maybeNote.get().getDiscription());
			existingNote.setTitle(note.getTitle() != null ? note.getTitle() : maybeNote.get().getTitle());
			return existingNote;
		}).orElseThrow(() -> new RuntimeException("Note Not Found"));

		return noteRep.save(presentNote);
	}

	@Override
	public boolean deleteNote(String token, Note note) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		noteRep.deleteByUserIdAndNoteId(varifiedUserId, note.getNoteId());
		return true;
	}

	@Override
	public List<Note> fetchNote(String token) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("i m in fetch :"+varifiedUserId);
//		public List getAllNote() {
//			return (List) noteRep.findAll();
//		}
		List<Note> notes = (List<Note>) noteRep.findByUserId(varifiedUserId);
	
		return notes;
	}

}
