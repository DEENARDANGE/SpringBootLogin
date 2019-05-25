package com.bridgelabz.fundoNoteApp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoNoteApp.user.model.LoginRequest;
import com.bridgelabz.fundoNoteApp.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findById(int id);

	boolean deleteById(int varifiedUserId);

	User findByEmail(String email);

	Optional<User> findByEmailAndPassword(String email, String password);

	User getUserByEmail(String email);

}