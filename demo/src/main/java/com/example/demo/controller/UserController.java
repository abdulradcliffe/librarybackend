package com.example.demo.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import com.example.demo.entity.LoginResponse;

@RestController
@RequestMapping(value = "/user")

public class UserController {

	@Autowired
	private UserRepository repository;

	@GetMapping(value = "/getAll")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> allusers = repository.findAll();

		return ResponseEntity.ok(allusers);
	}

	@GetMapping(value = "/add")
	public ResponseEntity<String> adduser(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("role") String role) {
		User u1=repository.findByEmail(email);
		if(u1==null) {
			
		
		User user1 = new User();
		user1.setName(name);
		user1.setEmail(email);
		user1.setPassword(password);
		user1.setRole(role);
		repository.save(user1);
		return ResponseEntity.ok("Added successfully");
		}
		return ResponseEntity.ok("User already exists");

	}

	@GetMapping(value = "/login")
	public ResponseEntity<LoginResponse> checkinguser(@RequestParam("email") String email,
			@RequestParam("password") String password) {
		LoginResponse loginresponse=new LoginResponse();

		User optuser = repository.findByEmailAndPassword(email, password);
		if (optuser == null) {
			loginresponse.setStatus(false);
			return ResponseEntity.ok(loginresponse);//false
		}
		loginresponse.setStatus(true);
		loginresponse.setUser(optuser);//object frm db
		return ResponseEntity.ok(loginresponse);
	}

	@GetMapping(value = "/delete")
	public ResponseEntity<String> deleteuser(@RequestParam("id") Integer id) {
		Optional<User> optuser = repository.findById(id);
		if (optuser.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok("deleted successfully");
		}
		return ResponseEntity.ok("id not found");
	}

	@GetMapping(value = "/update")
	public ResponseEntity<String> updateuser(@RequestParam("id") Integer id, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("role") String role) {
		Optional<User> optuser = repository.findById(id);
		if (optuser.isPresent()) {
			User u = optuser.get();
			u.setName(name);
			u.setEmail(email);
			u.setPassword(password);
			u.setRole(role);
			repository.save(u);
			return ResponseEntity.ok("updated successfully");
		}
		return ResponseEntity.ok("id not found");
	}
	
	@GetMapping(value="/search")
	public ResponseEntity<List<User>> searchUser(@RequestParam("string") String searchString){
		
		List<User> listUser= repository.searchUser(searchString);
		return ResponseEntity.ok(listUser);
		
	}

}
