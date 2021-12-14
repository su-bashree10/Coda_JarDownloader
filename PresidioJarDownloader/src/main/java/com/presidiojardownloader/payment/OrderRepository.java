package com.presidiojardownloader.payment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	
	@Transactional
	@Modifying
	@Query(value = "delete from orders where order_id = ?1",nativeQuery = true)
	int deleteOrderById(String id);
	
	@Transactional
	@Modifying
	@Query(value = "update orders set status = 1 where order_id = ?1",nativeQuery = true)
	int updateOrderById(String id);
	
	@Query(value = "select jar_name from jarfiles where jar_file_id = ?1",nativeQuery = true)
	String findJarNameByJarId(Long id);
	
	@Query(value = "select user_name from users where user_id = ?1",nativeQuery = true)
	String findUserNameByUserId(Long id);
	
	Order findOrderById(String id);
	
	@Query(value = "select user_email from users where user_id = ?1",nativeQuery = true)
	String findEmailByUserId(Long id);
	
	List<Order> findByReceiverId(Long receiverId);
	
	
}
