package com.presidiojardownloader.payment;

import org.springframework.beans.factory.annotation.Autowired;



public class OrderController {

	
	private OrderRepository orderRepository;
	

	@Autowired
	public OrderController(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	

	public void saveOrder(String id , Long senderId , Long jarId , Long receiverId , String amount) {
		Order order = new Order(id,senderId,jarId,receiverId,amount);
		orderRepository.save(order);
	}
	
	
	public void deleteOrder(String id) {
		orderRepository.deleteOrderById(id);
	}
	
	public void updateOrder(String id) {
		orderRepository.updateOrderById(id);
	}
	
	
}
