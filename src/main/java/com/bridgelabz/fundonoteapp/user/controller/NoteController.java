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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.user.model.Note;
import com.bridgelabz.fundonoteapp.user.service.NoteService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping(value = "/create-note{token}")
	public ResponseEntity<Note> createNote(@PathVariable String token, @RequestBody Note note,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("token : " + request.getHeader("token"));
		return new ResponseEntity<Note>(noteService.createNote(token, note), HttpStatus.OK);
	}

	@PutMapping(value = "/update-note{token}")
	public Note updateNote(@RequestBody Note note, HttpServletRequest request) {

		return noteService.updateNote(request.getHeader("token"), note);
	}

	@DeleteMapping(value = "/delete-note/{noteId}")
	public void deleteNote(@PathVariable int NoteID, HttpServletRequest request) {
		System.out.println("I am token at delete method :" + request.getHeader("token"));
		noteService.deleteNote(NoteID);
	}

	@GetMapping(value = "/get-notes")
	public List<Note> getNote(HttpServletRequest request) {
		System.out.println("I am token at get method :" + request.getHeader("token"));
		return noteService.getNote(request.getHeader("token"));

	}

}
