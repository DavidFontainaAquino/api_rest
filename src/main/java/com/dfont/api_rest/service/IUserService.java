package com.dfont.api_rest.service;

import com.dfont.api_rest.dto.UserDTO;

public interface IUserService {
	UserDTO getById(Long id);
}
