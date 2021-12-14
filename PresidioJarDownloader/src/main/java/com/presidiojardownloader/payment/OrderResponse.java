package com.presidiojardownloader.payment;

public class OrderResponse {
	private String senderName;
	private String senderEmail;
	private String amount;
	private String jarName;
	
	public OrderResponse() {
		
	}
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getJarName() {
		return jarName;
	}
	public void setJarName(String jarName) {
		this.jarName = jarName;
	}
	public OrderResponse(String senderName, String senderEmail, String amount, String jarName) {
		super();
		this.senderName = senderName;
		this.senderEmail = senderEmail;
		this.amount = amount;
		this.jarName = jarName;
	}
	
}
