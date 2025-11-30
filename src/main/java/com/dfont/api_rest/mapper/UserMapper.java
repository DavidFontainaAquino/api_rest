package com.dfont.api_rest.mapper;

import com.dfont.api_rest.dto.UserResponseDTO;
import com.dfont.api_rest.model.User;

public class UserMapper {
	public static UserResponseDTO toRecord(User user) {
		UserResponseDTO userRecord = new UserResponseDTO(
				user.getId(),
				user.getName(),
				user.getEmail()
				);
		return userRecord;
	}
	public static User toEntity(UserResponseDTO userRecord) {
		User user = new User(
				userRecord.id(),
				userRecord.name(),
				userRecord.email()
				);
		return user;
	}
}
