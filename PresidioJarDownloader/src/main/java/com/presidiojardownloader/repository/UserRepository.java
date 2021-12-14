package com.presidiojardownloader.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.presidiojardownloader.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 @Query( value =  "select * from users where user_email=:email",nativeQuery = true)
	User getUserByEmail(@Param("email") String email);
	 
	User findByUserId(Long id);
	
	@Transactional
	@Modifying
	
	@Query(value="delete from users where user_id = ?1" , nativeQuery = true)
	int deleteUserById(Long id);
	
	List<User> findByHashCode(String hashCode);
	 
}
