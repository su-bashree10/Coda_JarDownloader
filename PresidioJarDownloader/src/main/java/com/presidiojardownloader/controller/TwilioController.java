package com.presidiojardownloader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.presidiojardownloader.repository.OtpVerification;
import com.presidiojardownloader.responseEntity.ResponseHandler;
import com.presidiojardownloader.service.PhoneverificationService;
import com.presidiojardownloader.service.VerificationResult;


@Controller
public class TwilioController {

	@Autowired
	PhoneverificationService phonesmsservice;
	    
	@RequestMapping("/")
	public String homepage(ModelAndView model)
	{
		return "index";
	}
	
	@PostMapping("/sendotp")
	public ResponseEntity<Object> sendotp(@RequestBody String phone)
	{
	    VerificationResult result=phonesmsservice.startVerification(phone);
	    if(result.isValid())
	    {
	    	
	    	return ResponseHandler.generateResponse("Otp Has Been Sent To your number", HttpStatus.OK, null);
	    }
	    return ResponseHandler.generateResponse("Invalid Phone Number Please Update Your Phone Number", HttpStatus.BAD_REQUEST, null);
	}
	
	@PostMapping("/verifyotp")
	public ResponseEntity<Object> sendotp(@RequestBody OtpVerification otpVerification)
	{
		String phone = otpVerification.getPhone();
		String otp = otpVerification.getOtp();
	    VerificationResult result=phonesmsservice.checkverification(phone,otp);
	    if(result.isValid())
	    {
	    	return ResponseHandler.generateResponse("Your number is Verified",HttpStatus.OK,null);
	    }
		return ResponseHandler.generateResponse("Something wrong/ Otp incorrect",HttpStatus.BAD_REQUEST,null);
	}
	
	
}