package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>// id type
																	// clmn>
																	// {//table,
{
	@Query(value = "SELECT * FROM user_dtl where email=:email AND password=:password", nativeQuery = true)
	User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}