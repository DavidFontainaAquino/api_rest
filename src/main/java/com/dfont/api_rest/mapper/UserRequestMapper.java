package com.dfont.api_rest.mapper;

import com.dfont.api_rest.dto.UserRequestDTO;
import com.dfont.api_rest.model.User;

public class UserRequestMapper {
	public static User toEntity(UserRequestDTO userRequest) {
		User user = new User(
				userRequest.name(),
				userRequest.email());
		return user;
	}
}
