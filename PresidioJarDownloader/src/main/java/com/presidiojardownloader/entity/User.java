package com.presidiojardownloader.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
		name = "users",
		uniqueConstraints = @UniqueConstraint(
				name="emailid_unique",
				columnNames = "user_email"
		)
	)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	@Column(nullable = false)
	private String userName;
	@Column(name = "user_email",nullable = false)
	private String userEmailId;
	@Column(nullable = false)
	private String userPassword;
	@Column(nullable = false)
	private String userMobile;
	private String hashCode;
	@Column(columnDefinition = "boolean default false")
	private boolean isVerified;
	private String accountNumber;
	
	public User() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public User(String userName, String userEmailId, String userPassword, String userMobile) {
		super();
		this.userName = userName;
		this.userEmailId = userEmailId;
		this.userPassword = userPassword;
		this.userMobile = userMobile;
	}
	

	public User(String userName, String userEmailId, String userMobile) {
		super();
		this.userName = userName;
		this.userEmailId = userEmailId;
		this.userMobile = userMobile;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userEmailId=" + userEmailId + ", userPassword="
				+ userPassword + ", userMobile=" + userMobile + "]";
	}

}
