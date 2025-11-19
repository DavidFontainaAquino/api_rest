package com.dfont.api_rest.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dfont.api_rest.dto.UserDTO;
import com.dfont.api_rest.mapper.UserMapper;
import com.dfont.api_rest.model.User;
import com.dfont.api_rest.repository.UserRepository;
import com.dfont.api_rest.service.exeptions.IdNullExeption;
import com.dfont.api_rest.service.exeptions.IdUnexpectedFormatExeption;
import com.dfont.api_rest.service.exeptions.UserNonExistsExeption;

@Service
public class UserServiceImpl implements IUserService {
	
	private final UserRepository repository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.repository = userRepository;
	}

	public UserDTO getById(Long id) throws IdNullExeption, IdUnexpectedFormatExeption,UserNonExistsExeption {
		Optional<User> optUser = Optional.empty();
		User user;
		UserDTO userDTO;
		
		// System.out.println("Id: " + userDTO.getId() + " Name: " + userDTO.getName());
		// Comprobaciones del id
		if(id==null)
			throw new IdNullExeption("El id no puede ser null");
		if(id<0)
			throw new IdUnexpectedFormatExeption("El id no es válido");
		
		//Obtiene el usuario del repositorio
		optUser = this.repository.findById(id);
		
		/*
		// Comprueba la existencia del usuario en el repositorio
		// Si no existe lanza una exepcion
		*/
		if(!optUser.isPresent())
			throw new UserNonExistsExeption("Usuario inexistente");
		user = optUser.get();
		
		return UserMapper.toDTO(user);
	}
}
