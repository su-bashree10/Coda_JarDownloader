package com.presidiojardownloader.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.presidiojardownloader.entity.User;

public interface UserService {

	public ResponseEntity<Object> saveUser(User user);

	public ResponseEntity<Object> getUser(String email);

	public ResponseEntity<Object> loginUser(String email, String password);
	
	public ResponseEntity<Object> getUserById(Long id);

	public ResponseEntity<Object> deleteUserById(Long id);

	public ResponseEntity<Object> updateUserById(Long id, User user);
	
	public ResponseEntity<Object> changePassword(Long id , String password);
	
	public String verifyEmail(String email, String hash);
	
	public ResponseEntity<Object> GetAllSupports(Long userid);

}
