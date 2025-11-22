package com.dfont.api_rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dfont.api_rest.dto.UserRecord;
import com.dfont.api_rest.service.UserServiceGetAll;
import com.dfont.api_rest.service.UserServiceGetById;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserServiceGetById userServiceGetById;
	private UserServiceGetAll userServiceGetAll;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserRecord> getById(@PathVariable Long id){
		
		UserRecord userRecord = userServiceGetById.getById(id);
		return ResponseEntity.ok(userRecord);
	}
	@GetMapping("/")
	public ResponseEntity<List<UserRecord>> get(){
		List<UserRecord> listUserRecord = userServiceGetAll.getAll();
		return ResponseEntity.ok(listUserRecord);
	}

}
