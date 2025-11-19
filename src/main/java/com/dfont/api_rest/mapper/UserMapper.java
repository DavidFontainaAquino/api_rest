package com.dfont.api_rest.mapper;

import com.dfont.api_rest.dto.UserDTO;
import com.dfont.api_rest.model.User;

public class UserMapper {
	public static UserDTO toDTO(User user) {
		UserDTO userDTO = new UserDTO(
				user.getId(),
				user.getName(),
				user.getEmail()
				);
		return userDTO;
	}
	public static User toEntity(UserDTO userDTO) {
		User user = new User(
				userDTO.getId(),
				userDTO.getName(),
				userDTO.getEmail()
				);
		return user;
	}
}
