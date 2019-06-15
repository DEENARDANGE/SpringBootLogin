package com.bridgelabz.fundoNoteApp.user.service;

import com.bridgelabz.fundoNoteApp.user.model.Collaborator;

public interface CollaboratorServiceInf {

	boolean addCollaborator(String token,Collaborator collaborator);
	
	boolean removeCollaborator(String token,Collaborator colUser);
	
}
