
package com.presidiojardownloader.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.presidiojardownloader.entity.JarFiles;
import com.presidiojardownloader.entity.User;
import com.presidiojardownloader.hashing.AES256;
import com.presidiojardownloader.mail.EmailSenderService;
import com.presidiojardownloader.payment.Order;
import com.presidiojardownloader.payment.OrderRepository;
import com.presidiojardownloader.payment.OrderResponse;
import com.presidiojardownloader.repository.JarFileRepository;
import com.presidiojardownloader.repository.UserRepository;
import com.presidiojardownloader.responseEntity.ResponseHandler;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private JarFileRepository jarFileRespository;
	Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);
	@Override
	public ResponseEntity<Object> saveUser(User user) {
		String password = user.getUserPassword()+"secretekeyofjarfiledownloader";
		String encryptedPassword = AES256.encrypt(password);
		user.setUserPassword(encryptedPassword);
		String hashcode = RandomString.make(64);
		user.setHashCode(hashcode);
		try {
			User savedUser = userRepository.save(user);
			String body = "<h1> Please Verify your email by clicking this link</h1>\n";
			String link = "http://localhost:8080/verifyemail/"+savedUser.getUserEmailId()+"/"+savedUser.getHashCode();
			body+= "<a href="+link+"> Verify Email </a>";
			emailSenderService.sendSimpleEmailWithHtml(savedUser.getUserEmailId(), body, "Verify Your Email");
			return ResponseHandler.generateResponse("User Successfully Added", HttpStatus.OK, savedUser);
		}catch(Exception e)
		{
			return ResponseHandler.generateResponse("User Not Added Please try again", HttpStatus.BAD_REQUEST, null);
		}
		
	}
	@Override
	public ResponseEntity<Object> getUser(String email) {
		User user = userRepository.getUserByEmail(email);
		if(user == null)
		{
			return ResponseHandler.generateResponse("No user With this emailId", HttpStatus.BAD_REQUEST, null);
		}
		return ResponseHandler.generateResponse("User Successfully Found", HttpStatus.OK, user);
	}
	@Override
	public ResponseEntity<Object> loginUser(String email, String password) {
		User user = userRepository.getUserByEmail(email);
		if(user == null)
		{
			return ResponseHandler.generateResponse("No user with this mail", HttpStatus.BAD_REQUEST, null);
		}
		
		password = password + "secretekeyofjarfiledownloader";
		String decryptedPassword = AES256.decrypt(user.getUserPassword());
		if(password.equals(decryptedPassword)) {
			return ResponseHandler.generateResponse("User Successfully Loged in", HttpStatus.OK, user);
		}
		return ResponseHandler.generateResponse("Wrong username or password", HttpStatus.BAD_REQUEST, null);
	}
	@Override
	public ResponseEntity<Object> getUserById(Long id) {
		User user = userRepository.findByUserId(id);
		if(user == null)
		{
			return ResponseHandler.generateResponse("No user With this id", HttpStatus.BAD_REQUEST, null);
		}
		return ResponseHandler.generateResponse("User Successfully Found", HttpStatus.OK, user);
	}
	@Override
	public ResponseEntity<Object> deleteUserById(Long id) {
		int res = userRepository.deleteUserById(id);
		if(res == 1)
		{
			return ResponseHandler.generateResponse("User Successfully Deleted", HttpStatus.OK, null);
		}else {
			return ResponseHandler.generateResponse("No user With this id", HttpStatus.BAD_REQUEST, null);
		}
	}
	@Override
	public ResponseEntity<Object> updateUserById(Long id, User user) {
		User dbUser = userRepository.findByUserId(id);
		if(dbUser == null)
		{
			return ResponseHandler.generateResponse("No user With this id", HttpStatus.BAD_REQUEST, null);
		}
		
		if(Objects.nonNull(user.getUserName()) && !"".equalsIgnoreCase(user.getUserName())) {
			dbUser.setUserName(user.getUserName());
		}
		if(Objects.nonNull(user.getUserEmailId()) && !"".equalsIgnoreCase(user.getUserEmailId())) {
			dbUser.setUserEmailId(user.getUserEmailId());
		}
		if(Objects.nonNull(user.getAccountNumber()) && !"".equalsIgnoreCase(user.getAccountNumber())) {
			dbUser.setAccountNumber(user.getAccountNumber());
		}
		if(Objects.nonNull(user.getUserMobile()) && !"".equalsIgnoreCase(user.getUserMobile())) {
			dbUser.setUserMobile(user.getUserMobile());
		}
		try {
			User updatedUser = userRepository.save(dbUser);
			return ResponseHandler.generateResponse("User Successfully Updated", HttpStatus.OK, updatedUser);
		}catch(Exception e)
		{
			return ResponseHandler.generateResponse("User Not Updated Please try again", HttpStatus.BAD_REQUEST, null);
		}
	}
	@Override
	public ResponseEntity<Object> changePassword(Long id,String password) {
		User user = userRepository.findByUserId(id);
		String Updatedpassword = password+"secretekeyofjarfiledownloader";
		System.out.println(Updatedpassword);
		String encryptedPassword = AES256.encrypt(Updatedpassword);
//		logger.error(encryptedPassword);
		user.setUserPassword(encryptedPassword);
		try {
			User savedUser = userRepository.save(user);
			return ResponseHandler.generateResponse("Password Updated Successfully.....", HttpStatus.OK, savedUser);
		}catch(Exception e)
		{
			return ResponseHandler.generateResponse("There is problem in updating the password..", HttpStatus.BAD_REQUEST, null);
		}
	}
	
	@Override
	public String verifyEmail(String email, String hash) {
		User user = userRepository.findByHashCode(hash).get(0);
		if(user.isVerified() == false) {
			if(email.equals(user.getUserEmailId())) {
				user.setVerified(true);
				try {
				 userRepository.save(user);
				}
				catch (Exception e) {
					logger.error(e.getMessage());
					return "Something went wrong!!!";
				}
				return "Successfully Verified";
			}
			else {
				return "Something Went Wrong";
			}
		} 
		else {
			return "Your mail already Verified....";
		}
				
			
			
		}
	
	@Override
	public ResponseEntity<Object> GetAllSupports(Long userid) {
		List<OrderResponse> response=new ArrayList<OrderResponse>();
		List<Order> user = orderRepository.findByReceiverId(userid);
		for(Order order:user) {
			Long id=order.getSenderId();
			User u=userRepository.findByUserId(id);
			//order.set
			JarFiles jar=jarFileRespository.getById(order.getJarId());
			OrderResponse o=new OrderResponse(u.getUserName(), u.getUserEmailId(), order.getAmount(), jar.getJarName());
			response.add(o);
		}
		return ResponseHandler.generateResponse("Supports Retrieved", HttpStatus.OK, response);
	}
}
