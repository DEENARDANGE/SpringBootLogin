package com.bridgelabz.fundoNoteApp.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoNoteApp.user.model.Label;
import com.bridgelabz.fundoNoteApp.user.model.Note;
import com.bridgelabz.fundoNoteApp.user.repository.LabelRepository;
import com.bridgelabz.fundoNoteApp.user.repository.NoteRepository;
import com.bridgelabz.fundoNoteApp.util.Utility;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRep;
	@Autowired
	private LabelRepository labelRep;
	@Autowired
	private Utility jsonToken;

	// create Note

	@Override
	public Note createNote(String token, Note note) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("note creation :" + varifiedUserId);
		note.setUserId(varifiedUserId);
		LocalDateTime time=LocalDateTime.now();
		note.setCreadtedtime(time);
		return noteRep.save(note);
	}

	// update note
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
		LocalDateTime time=LocalDateTime.now();
		note.setUpdatetime(time);
		return noteRep.save(presentNote);
	}

	// delete note
	@Override
	public String deleteNote( int noteId) {
		//int varifiedUserId = jsonToken.tokenVerification(token);
		//noteRep.deleteByUserIdAndNoteId(varifiedUserId, note.getNoteId());
		noteRep.deleteByNoteId(noteId);
		return "Deleted";
	}

	// fetch note
	@Override
	public List<Note> getNote(String token) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("i m in fetch :" + varifiedUserId);
//		public List getAllNote() {
//			return (List) noteRep.findAll();
//		}
		List<Note> notes = (List<Note>) noteRep.findByUserId(varifiedUserId);

		return notes;
	}

//CREATE label
	@Override
	public Label createLabel(String token, Label label) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("label creation :" + varifiedUserId);
		label.findByUserId(varifiedUserId);
		return labelRep.save(label);
	}

	// update label

	@Override
	public Label updateLabel(String token, Label label) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("varifiedUserId :" + varifiedUserId);
		Optional<Label> maybeLabel = labelRep.findByUserIdAndLabelId(varifiedUserId, label.getLabelId());
		System.out.println("maybeLabel :" + maybeLabel);
		Label presentLabel = maybeLabel.map(existingLabel -> {
			System.out.println("label here");
			existingLabel.setLabelName(
					label.getLabelName() != null ? label.getLabelName() : maybeLabel.get().getLabelName());
			// existingNote.setTitle(note.getTitle() != null ? note.getTitle() :
			// maybeNote.get().getTitle());
			return existingLabel;
		}).orElseThrow(() -> new RuntimeException("Label Not Found"));

		return labelRep.save(presentLabel);
	}

	// delete label

	@Override
	public boolean deleteLabel(String token, Label label) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		labelRep.deleteByUserIdAndLabelId(varifiedUserId, label.getLabelId());
		return true;
	}

	@Override
	public List<Label> getLabel(String token) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("i m in fetch :" + varifiedUserId);
//		public List getAllNote() {
//			return (List) noteRep.findAll();
//		}
		List<Label> labels = labelRep.findByUserId(varifiedUserId);

		return labels;
	}

//	@Override
//	public boolean deleteNote(int NoteId) {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
