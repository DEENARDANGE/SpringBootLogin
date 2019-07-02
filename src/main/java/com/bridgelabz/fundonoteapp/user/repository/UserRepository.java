package com.bridgelabz.fundonoteapp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundonoteapp.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findById(int id);

	boolean deleteById(int varifiedUserId);

	User findByEmail(String email);

	Optional<User> findByEmailAndPassword(String email, String password);
	
	Optional<User> findByEmailAndPasswordAndStatus(String email, String password,String status);

	User getUserByEmail(String email);

}