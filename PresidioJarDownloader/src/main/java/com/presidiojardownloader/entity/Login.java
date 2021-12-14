package com.presidiojardownloader.entity;

public class Login {
	private String userEmailId;
	private String userPassword;
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Login(String userEmailId, String userPassword) {
		super();
		this.userEmailId = userEmailId;
		this.userPassword = userPassword;
	}
	
	public Login() {
		
	}
	
	
}
