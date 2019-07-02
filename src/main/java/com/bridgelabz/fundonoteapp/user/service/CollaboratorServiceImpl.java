package com.bridgelabz.fundonoteapp.user.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundonoteapp.user.model.Collaborator;
import com.bridgelabz.fundonoteapp.user.repository.CollaboratorRepo;

@Service
public class CollaboratorServiceImpl implements CollaboratorServiceInf {
	@Autowired
	private CollaboratorRepo collaboratorRepo;

	public boolean addCollaborator(String token, Collaborator collaborator) {
		//collaborator.setOwnerId(collaborator.getOwnerId());
		try {
			collaboratorRepo.save(collaborator);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Transactional
	public boolean removeCollaborator(String token, Collaborator colaborater) {

		try {
			collaboratorRepo.deleteById(colaborater.getCollId());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}