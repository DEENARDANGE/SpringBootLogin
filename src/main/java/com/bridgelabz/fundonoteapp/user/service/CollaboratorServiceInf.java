package com.bridgelabz.fundonoteapp.user.service;

import com.bridgelabz.fundonoteapp.user.model.Collaborator;

public interface CollaboratorServiceInf {

	boolean addCollaborator(String token,Collaborator collaborator);
	
	boolean removeCollaborator(String token,Collaborator colUser);
	
}
