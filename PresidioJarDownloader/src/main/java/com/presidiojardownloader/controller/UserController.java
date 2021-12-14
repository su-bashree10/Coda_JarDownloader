package com.presidiojardownloader.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.presidiojardownloader.entity.Login;
import com.presidiojardownloader.entity.User;
import com.presidiojardownloader.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/users")
	public ResponseEntity<Object> saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@GetMapping("/getuser/{email}")
	public ResponseEntity<Object> getUser(@PathVariable("email") @RequestBody String email)
	{
		return userService.getUser(email);
	}
	
	@PostMapping("/loginUser")
	public ResponseEntity<Object> loginUser(@RequestBody Login login)
	{
		String userEmailId = login.getUserEmailId();
		String userPassword = login.getUserPassword();
		System.out.println(userEmailId + " "+ userPassword);
		return userService.loginUser(userEmailId,userPassword);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Object>getUserById( @PathVariable("id")  @RequestBody Long id){
		return userService.getUserById(id);
	}
	
	@GetMapping("/user/delete/{id}")
	public ResponseEntity<Object>deleteUserById(@PathVariable("id") @RequestBody Long id)
	{
		return userService.deleteUserById(id);
	}
	
	@PostMapping("/user/update/{id}")
	public ResponseEntity<Object> updateUserById( @PathVariable("id") Long id ,@RequestBody User user)
	{
		return userService.updateUserById(id,user);
	}
	@PostMapping("/user/changepassword")
	
	public ResponseEntity<Object>changePassword(@RequestParam("id") Long id ,@RequestParam("password") String password){
		return userService.changePassword(id,password);
	}
	@GetMapping("/verifyemail/{email}/{hash}")
	public String verifyEmail(@PathVariable("email")String email , @PathVariable("hash") String hash) {
		return userService.verifyEmail(email,hash);
	}
	
	@GetMapping("/user/support/{userid}")
	public ResponseEntity<Object>GetAllSupports(@PathVariable("userid") Long userid){
		return userService.GetAllSupports(userid);
	}
}
