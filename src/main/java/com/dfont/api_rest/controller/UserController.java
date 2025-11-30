package com.dfont.api_rest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dfont.api_rest.dto.UserRequestDTO;
import com.dfont.api_rest.dto.UserResponseDTO;
import com.dfont.api_rest.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private IUserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id){
		
		UserResponseDTO userRecord = userService.getById(id);
		return ResponseEntity.ok(userRecord);
	}
	
	@GetMapping("")
	public ResponseEntity<List<UserResponseDTO>> get(){
		List<UserResponseDTO> listUserRecord = userService.getAll();
		return ResponseEntity.ok(listUserRecord);
	}
	
	@PostMapping("")
	public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO request){
		UserResponseDTO userRecord = userService.create(request);
		return ResponseEntity.created(URI.create("/api/users/"+userRecord.id())).body(userRecord);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserRequestDTO request){
		return ResponseEntity.ok(userService.update(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
