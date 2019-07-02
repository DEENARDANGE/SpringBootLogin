package com.bridgelabz.fundonoteapp.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundonoteapp.user.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {

	//List<Label> findByUserId(int varifiedUserId);

	//void deleteByUserIdAndLabelId(int varifiedUserId, int labelId);

	//Optional<Label> findByUserIdAndLabelId(int varifiedUserId, int labelId);

	//List<Label> findallByNoteId(int noteId);

	//List<Label> findByUserId(int varifiedUserId);

	Optional<Label> findByNoteIdAndLabelId(int noteId, int labelId);

	//void deleteByNoteIdAndLabelId(int noteId, int labelId);

	List<Label> findByNoteId(int noteId);

	List<Label> findAllByUserId(int userId);

	void deleteBylabelName(String labelName);

	
}
