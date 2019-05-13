package com.bridgelabz.fundoNoteApp.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoNoteApp.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByIdAndPassword(int id, String password);

	Optional<User> findAllById(int id);

	Optional<User> findById(int id);

	boolean deleteById(int varifiedUserId);

	User findByEmail(String email);

}