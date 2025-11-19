package com.dfont.api_rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dfont.api_rest.dto.UserDTO;
import com.dfont.api_rest.service.IUserService;
import com.dfont.api_rest.service.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getById(@PathVariable Long id){
		UserDTO userDTO = userService.getById(id);
		return ResponseEntity.ok(userDTO);
	}

}
