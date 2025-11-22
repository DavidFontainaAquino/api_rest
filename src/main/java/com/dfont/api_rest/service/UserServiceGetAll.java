package com.dfont.api_rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dfont.api_rest.dto.UserRecord;
import com.dfont.api_rest.mapper.UserMapper;
import com.dfont.api_rest.model.User;
import com.dfont.api_rest.repository.UserRepository;

@Service
public class UserServiceGetAll implements IUserGetAll {

	private final UserRepository repository;
	
	public UserServiceGetAll(UserRepository userRepository) {
		this.repository = userRepository;
	}
	
	public List<UserRecord> getAll() {
		List<User> users = repository.findAll();
		List<UserRecord> listUserRecord = new ArrayList<>();
		
		for(User user: users) {
			listUserRecord.add(UserMapper.toRecord(user));
		}
		return listUserRecord;
	}
}
