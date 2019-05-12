package com.bridgelabz.fundoNoteApp.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoNoteApp.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public default  User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}

//    List<User> findByEmailAndPassword(String email, String password);
    List<User> findByidAndPassword(long id, String password);
  

}