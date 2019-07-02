package com.bridgelabz.fundonoteapp.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.user.model.Label;
import com.bridgelabz.fundonoteapp.user.service.NoteService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")

@RequestMapping("/user")
public class LabelController {

	@Autowired
	private NoteService noteService;
	@PostMapping("/createlabel")
	public ResponseEntity<?> createLabel(@RequestHeader ("token")String token,@RequestBody Label label, HttpServletRequest request,HttpServletResponse response) {		
		if (noteService.createLabel(token,label, request)!=null)
			return new ResponseEntity<String>("label Succesfully Created",HttpStatus.OK);
		else
			return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}
//	@PostMapping(value = "/createlabel/{noteId}")
//	public Label createLabel(@RequestBody Label label, @PathVariable int noteId) {
//
//		// return noteService.createLabel(request.getHeader("token"), label);
//		return noteService.createLabel(noteId, label);
//	}


	@PutMapping("/updatelabel")
	public ResponseEntity<?> editLabel(@RequestHeader ("token")String token,@RequestBody Label label, HttpServletRequest request,HttpServletResponse response) {		
		if (noteService.updateLabel(token,label, request)!=null)
			return new ResponseEntity<String>("label Succesfully updated",HttpStatus.OK);
		else
			return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}

	@PutMapping("/map-note-label/{noteId:.+}/{labelId:.+}")
	public ResponseEntity<?> mapNoteLabel(@RequestHeader ("token")String token,@PathVariable ("noteId")int noteId,@PathVariable ("labelId")int labelId,HttpServletRequest request) {		

		if(noteService.mapNoteToLabel(token, noteId, labelId))
			return new ResponseEntity<String>("Mapped successfully",HttpStatus.OK);
		else
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}

//	@PutMapping(value = "/updatelabel")
//	public Label updateLabel(@RequestBody Label label, int noteId) {
//
//		return noteService.updateLabel(noteId, label);
//	}

//	@DeleteMapping(value = "/deletelabel")
//	public void deleteLabel(@RequestBody Label label, int noteId) {
//		// System.out.println("I am token at delete method :" +
//		// request.getHeader("token"));
//		noteService.deleteLabel(noteId, label);
//
//	}

	@DeleteMapping("/deletelabel/{token:.+}")
	public ResponseEntity<?> deletelabel(@PathVariable ("token")String token,@RequestParam String labelName,HttpServletRequest request) {		
		if(noteService.deleteLabel(token,labelName, request))
			return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
		else
			return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}

	@GetMapping("/get-label")
	public ResponseEntity<?> retrieveLabel(@RequestHeader ("token")String token, HttpServletRequest request,HttpServletResponse response) {		

		List<Label> labels=noteService.getLabel(token, request);
		if (labels!=null)
			return new ResponseEntity<List<Label>>(labels,HttpStatus.OK);
		else
			return new ResponseEntity<String>("pls provide details correctly",HttpStatus.CONFLICT);
	}



}
