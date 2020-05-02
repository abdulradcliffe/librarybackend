package com.example.demo.repository;

import java.util.List;

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
	
	
	
	@Query(value="Select * from user_dtl where email=:email",nativeQuery=true)
User findByEmail(@Param("email")String email);
	
	@Query(value="SELECT * FROM user_dtl where name like %:string%  OR email like %:string% OR role like %:string%", nativeQuery = true)
	List<User> searchUser(@Param("string") String searchString);
}
