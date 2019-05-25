package com.bridgelabz.fundoNoteApp.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoNoteApp.user.model.Label;
import com.bridgelabz.fundoNoteApp.user.service.NoteService;

@RestController
@RequestMapping
public class LabelController {

	@Autowired
	private NoteService noteService;
//	@Autowired
//	private NoteRepository noteRep;

	// Create
	// @RequestMapping(value = "/createLabel", method = RequestMethod.POST)
	@PostMapping(value = "/label")
	public Label createLabel(@RequestBody Label label, HttpServletRequest request) {

		return noteService.createLabel(request.getHeader("token"), label);
	}

	// update

	// @RequestMapping(value = "/updateLabel", method = RequestMethod.PUT)
	@PutMapping(value = "/label")
	public Label updateLabel(@RequestBody Label label, HttpServletRequest request) {

		return noteService.updateLabel(request.getHeader("token"), label);
	}

	// delete

	// @RequestMapping(value = "/deleteLabel", method = RequestMethod.DELETE)
	@DeleteMapping(value = "/label")
	public void deleteLabel(@RequestBody Label label, HttpServletRequest request) {
		System.out.println("I am token at delete method :" + request.getHeader("token"));
		noteService.deleteLabel(request.getHeader("token"), label);
		// System.out.println("-->" + b);

	}

	// fetch

	// @RequestMapping(value = "/fetchLabel", method = RequestMethod.GET)
	@GetMapping(value = "/label")
	public List<Label> getLabel(HttpServletRequest request) {
		System.out.println("I am token at get method :" + request.getHeader("token"));
		return noteService.getLabel(request.getHeader("token"));
		// System.out.println("-->" + b);

	}

}
