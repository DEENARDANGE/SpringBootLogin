package com.bridgelabz.fundoNoteApp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoNoteApp.user.model.Note;
@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
	
	public void deleteById(int id);

	public Optional<Note> findById(int id);

}
