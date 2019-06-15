package com.bridgelabz.fundoNoteApp.user.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoNoteApp.user.model.Collaborator;
import com.bridgelabz.fundoNoteApp.user.repository.CollaboratorRepo;
import com.bridgelabz.fundoNoteApp.util.Utility;

@Service
public class CollaboratorServiceImpl implements CollaboratorServiceInf{

	@Autowired
	private Utility jsonToken;
	@Autowired
	private CollaboratorRepo collaboratorRepo;
	
	public boolean addCollaborator(String token,Collaborator collaborator) {
		collaborator.setOwnerId(collaborator.getOwnerId());
		try {
			collaboratorRepo.save(collaborator);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Transactional
	public boolean removeCollaborator(String token, Collaborator colaborater) {
		
		try {
		collaboratorRepo.deleteById(colaborater.getId());
		}catch (Exception e) {
			return false;
		}
		return true;
	}

}