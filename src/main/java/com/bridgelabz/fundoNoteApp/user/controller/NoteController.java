package com.bridgelabz.fundoNoteApp.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoNoteApp.user.model.Note;

import com.bridgelabz.fundoNoteApp.user.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	NoteService noteService;

	// Create
	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public Note createNote(@RequestBody Note note, HttpServletRequest request) {
		
		return noteService.createNote(request.getHeader("token"), note);
	}

	
	 // update
	  
	  @RequestMapping(value = "/updateNote", method = RequestMethod.PUT) public
	  void updateNote(@RequestBody Note note, HttpServletRequest request) {
	  
	  noteService.updateNote(request.getHeader("token"), note); }
	 

	
	  //delete
	 
	 @RequestMapping(value = "/deleteNote", method = RequestMethod.DELETE) public
	  void deleteNote(HttpServletRequest request) {
	  System.out.println("I am token at delete method :" +
	  request.getHeader("token")); boolean deleteNote =
	  noteService.deleteNote(request.getHeader("token")); //
	 // System.out.println("-->" + b);
	  
	 }
	 

}
