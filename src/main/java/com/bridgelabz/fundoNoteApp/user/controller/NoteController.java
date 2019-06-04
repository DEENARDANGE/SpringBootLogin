package com.bridgelabz.fundoNoteApp.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoNoteApp.user.model.Note;
import com.bridgelabz.fundoNoteApp.user.service.NoteService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class NoteController {

	@Autowired
	private NoteService noteService;

	// Create
	//@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	@PostMapping(value="/note")
	public ResponseEntity<String> createNote(@RequestBody Note note, HttpServletRequest request,HttpServletResponse response) {

		 noteService.createNote(request.getHeader("token"), note);
		 System.out.println("token : "+request.getHeader("token"));
		 return new ResponseEntity<>("{Note Created}", HttpStatus.OK);
	}

	// update

	//@RequestMapping(value = "/updateNote", method = RequestMethod.PUT)
	@PutMapping(value="/note")
	public Note updateNote(@RequestBody Note note, HttpServletRequest request) {

		return noteService.updateNote(request.getHeader("token"), note);
	}

	// delete

	//@RequestMapping(value = "/deleteNote", method = RequestMethod.DELETE)
	@DeleteMapping(value="note")
	public void deleteNote(@RequestBody Note note, HttpServletRequest request) {
		System.out.println("I am token at delete method :" + request.getHeader("token"));
		noteService.deleteNote(request.getHeader("token"), note);
		// System.out.println("-->" + b);

	}

	// fetch

	//@RequestMapping(value = "/fetchNote", method = RequestMethod.GET)
	@GetMapping(value="/notes")
	public List<Note> getNote(HttpServletRequest request) {
		System.out.println("I am token at get method :" + request.getHeader("token"));
		return noteService.getNote(request.getHeader("token"));
		// System.out.println("-->" + b);

	}

}
