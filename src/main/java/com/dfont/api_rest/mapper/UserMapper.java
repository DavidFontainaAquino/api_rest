package com.dfont.api_rest.mapper;

import com.dfont.api_rest.dto.UserRecord;
import com.dfont.api_rest.model.User;

public class UserMapper {
	public static UserRecord toRecord(User user) {
		UserRecord userRecord = new UserRecord(
				user.getId(),
				user.getName(),
				user.getEmail()
				);
		return userRecord;
	}
	public static User toEntity(UserRecord userRecord) {
		User user = new User(
				userRecord.id(),
				userRecord.name(),
				userRecord.email()
				);
		return user;
	}
}
