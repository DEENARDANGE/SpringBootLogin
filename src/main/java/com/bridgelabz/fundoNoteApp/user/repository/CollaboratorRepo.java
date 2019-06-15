package com.bridgelabz.fundoNoteApp.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoNoteApp.user.model.Collaborator;

@Repository
public interface CollaboratorRepo extends JpaRepository<Collaborator,Integer>{
	
	List<Collaborator> findByNoteId(int noteId);
		
	List<Collaborator> findAllByOwnerId(int userId);

}
