package com.presidiojardownloader.repository;

public class OtpVerification {
	private String phone;
	private String otp;
	public OtpVerification() {
		// TODO Auto-generated constructor stub
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public OtpVerification(String phone, String otp) {
		super();
		this.phone = phone;
		this.otp = otp;
	}
	
}
