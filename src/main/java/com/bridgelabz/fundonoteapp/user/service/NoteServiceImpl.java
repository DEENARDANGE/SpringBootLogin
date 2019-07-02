package com.bridgelabz.fundonoteapp.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundonoteapp.user.model.Label;
import com.bridgelabz.fundonoteapp.user.model.Note;
import com.bridgelabz.fundonoteapp.user.repository.LabelRepository;
import com.bridgelabz.fundonoteapp.user.repository.NoteRepository;
import com.bridgelabz.fundonoteapp.util.Utility;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRep;
	@Autowired
	private LabelRepository labelRep;
	@Override
	public Note createNote(String token, Note note) {
		int varifiedUserId = Utility.tokenVerification(token);
		System.out.println("note creation :" + varifiedUserId);
		note.setUserId(varifiedUserId);
		LocalDateTime time = LocalDateTime.now();
		note.setCreadtedtime(time);
		return noteRep.save(note);
	}

	@Override
	public Note updateNote(String token, Note note) {
		int varifiedUserId = Utility.tokenVerification(token);
		System.out.println("varifiedUserId :" + varifiedUserId);
		Optional<Note> maybeNote = noteRep.findByUserIdAndNoteId(varifiedUserId, note.getNoteId());
		System.out.println("maybeNote :" + maybeNote);
		Note presentNote = maybeNote.map(existingNote -> {
			System.out.println("noteee here");
			existingNote.setDiscription(
					note.getDiscription() != null ? note.getDiscription() : maybeNote.get().getDiscription());
			existingNote.setTitle(note.getTitle() != null ? note.getTitle() : maybeNote.get().getTitle());
			existingNote.setIspinned(note.isIspinned());
            existingNote.setIsarchive(note.isIsarchive());
            existingNote.setIntrash(note.isIntrash());
			
			return existingNote;
		}).orElseThrow(() -> new RuntimeException("Note Not Found"));
		LocalDateTime time = LocalDateTime.now();
		note.setUpdatetime(time);
		return noteRep.save(presentNote);
	}

	@Override
	public String deleteNote(int noteId) {
		noteRep.deleteByNoteId(noteId);
		return "Deleted";
	}

	@Override
	public List<Note> getNote(String token) {
		int varifiedUserId = Utility.tokenVerification(token);
		System.out.println("i m in fetch :" + varifiedUserId);
		List<Note> notes = (List<Note>) noteRep.findByUserId(varifiedUserId);

		return notes;
	}
//	@Override
//	public Label updateLabel(int noteId, Label label) {
//		
//		Optional<Label> maybeLabel = labelRep.findByNoteIdAndLabelId(noteId, label.getLabelId());
//		System.out.println("maybeLabel :" + maybeLabel);
//		Label presentLabel = maybeLabel.map(existingLabel -> {
//			System.out.println("label here");
//			existingLabel.setLabelName(
//					label.getLabelName() != null ? label.getLabelName() : maybeLabel.get().getLabelName());
//			return existingLabel;
//		}).orElseThrow(() -> new RuntimeException("Label Not Found"));
//
//		return labelRep.save(presentLabel);
//	}

//	@Override
//	public Label updateLabel(String token, Label label) {
//		int varifiedUserId = Utility.tokenVerification(token);
//		System.out.println("varifiedUserId :" + varifiedUserId);
//		Optional<Label> maybeLabel = labelRep.findByUserIdAndLabelId(varifiedUserId, label.getLabelId());
//		System.out.println("maybeLabel :" + maybeLabel);
//		Label presentLabel = maybeLabel.map(existingLabel -> {
//			System.out.println("label here");
//			existingLabel.setLabelName(
//					label.getLabelName() != null ? label.getLabelName() : maybeLabel.get().getLabelName());
//			return existingLabel;
//		}).orElseThrow(() -> new RuntimeException("Label Not Found"));
//
//		return labelRep.save(presentLabel);
//	}

//	public boolean deleteLabel(int noteId, Label label) {
//		//int varifiedUserId = Utility.tokenVerification(token);
//		labelRep.deleteByNoteIdAndLabelId(noteId, label.getLabelId());
//		return true;
//	}

//	@Override
//	public List<Label> getLabel(int noteId) {
//		//int varifiedUserId = Utility.tokenVerification(token);
//	//	System.out.println("i m in fetch :" + varifiedUserId);
//		List<Label> labels = labelRep.findByNoteId(noteId);
//
//		return labels;
//	}

	@Override
	public List<Label> getLabel(String token, HttpServletRequest request) {
		int userId=Utility.tokenVerification(token);
		List<Label> labels = labelRep.findAllByUserId(userId);
		if (!labels.isEmpty()) 
			return labels;
		else
			return null;
		
	}

@Override
	public Label createLabel(String token,Label label, HttpServletRequest request){
		int userId=Utility.tokenVerification(token);
		if(userId>0) {
			label.setUserId(userId);
			Label newLabel=labelRep.save(label);
			return newLabel;
		}
		return null;
	}
@Override
public boolean mapNoteToLabel(String token, int noteId, int labelId) {
	Note note = noteRep.findById(noteId).get();
	Label label = labelRep.findById(labelId).get();
	if(!note.getLabelList().contains(label)) {
		List<Label> labelAddList=note.getLabelList();
		labelAddList.add(label);
		note.setLabelList(labelAddList);
		noteRep.save(note);
		return true;
	}				
	return false;
}

@Override
public Object updateLabel(String token, Label label, HttpServletRequest request) {
	int userId=Utility.tokenVerification(token);
	List<Label> presentLabel=labelRep.findAllByUserId(userId);
	if(presentLabel!=null) {
		Label existingLabel = presentLabel.stream()
				//.filter(userLabel -> label.getLabelId()==(userLabel.getId()))
				.findAny()
				.orElse(null);
		existingLabel.setLabelName(label.getLabelName());
		assert(existingLabel!=null);
		return labelRep.save(existingLabel);
	}
	else
		return null;

	
}

@Override
public boolean deleteLabel(String token, String labelName, HttpServletRequest request) {
	int userId=Utility.tokenVerification(token);
	List<Label> aliveLabels=labelRep.findAllByUserId(userId);
	if(aliveLabels!=null) {
		labelRep.deleteBylabelName(labelName);
		return true;
	}
	else
		return false;
}

}
