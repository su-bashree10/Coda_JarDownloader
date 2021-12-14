package com.presidiojardownloader.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "orders")
public class Order {
	@Column(name = "order_id")
	@Id
	private String id;
	private Long senderId;
	private Long jarId;
	private Long receiverId;
	private String amount;
	@Column(columnDefinition = "integer default 0")
	private int status;
	
	public Order() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getJarId() {
		return jarId;
	}

	public void setJarId(Long jarId) {
		this.jarId = jarId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Order(String id, Long senderId, Long jarId, Long receiverId, String amount) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.jarId = jarId;
		this.receiverId = receiverId;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", senderId=" + senderId + ", jarId=" + jarId + ", receiverId=" + receiverId
				+ ", amount=" + amount + "]";
	}

	public Order(String id, Long senderId, Long jarId, Long receiverId) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.jarId = jarId;
		this.receiverId = receiverId;
	}
	
	
	
}
