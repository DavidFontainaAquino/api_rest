package com.dfont.api_rest.service;

import java.util.List;

import com.dfont.api_rest.dto.UserRequestDTO;
import com.dfont.api_rest.dto.UserResponseDTO;
import com.dfont.api_rest.service.exeptions.IdNullExeption;
import com.dfont.api_rest.service.exeptions.IdUnexpectedFormatExeption;
import com.dfont.api_rest.service.exeptions.UserNonExistsExeption;

public interface IUserService {

	UserResponseDTO create(UserRequestDTO request);

	UserResponseDTO getById(Long id) throws IdNullExeption, IdUnexpectedFormatExeption, UserNonExistsExeption;

	List<UserResponseDTO> getAll();

	UserResponseDTO update(Long id, UserRequestDTO requestDTO);

	void delete(Long id);

}