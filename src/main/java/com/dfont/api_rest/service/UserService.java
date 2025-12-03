package com.dfont.api_rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dfont.api_rest.dto.UserRequestDTO;
import com.dfont.api_rest.dto.UserResponseDTO;
import com.dfont.api_rest.mapper.UserMapper;
import com.dfont.api_rest.mapper.UserRequestMapper;
import com.dfont.api_rest.model.User;
import com.dfont.api_rest.repository.UserRepository;
import com.dfont.api_rest.service.exeptions.IdNullExeption;
import com.dfont.api_rest.service.exeptions.IdUnexpectedFormatExeption;
import com.dfont.api_rest.service.exeptions.UserNonExistsExeption;

@Service
public class UserService implements IUserService {
	
	private UserRepository repository;
	
	public UserService(UserRepository repository) {
		this.repository=repository;
	}
	
	@Override
	@CachePut(cacheNames = { "usersCacheRedis", "usersCacheCaffeine" }, key = "#result.id")
	public UserResponseDTO create(UserRequestDTO request) {
		User usuarioRequest = UserRequestMapper.toEntity(request);
		return UserMapper.toRecord(repository.save(usuarioRequest));
	}
	
	@Override
	@Cacheable(cacheNames = { "usersCacheRedis", "usersCacheCaffeine" }, key = "#id")
	public UserResponseDTO getById(Long id) throws IdNullExeption, IdUnexpectedFormatExeption,UserNonExistsExeption {
		Optional<User> optUser = Optional.empty();
		User user;
		
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
		
		return UserMapper.toRecord(user);
	}
	
	@Override
	public List<UserResponseDTO> getAll() {
		List<User> users = repository.findAll();
		List<UserResponseDTO> listUserRecord = new ArrayList<>();
		
		for(User user: users) {
			listUserRecord.add(UserMapper.toRecord(user));
		}
		return listUserRecord;
	}
	
	@Override
	@CachePut(cacheNames = { "usersCacheRedis", "usersCacheCaffeine" }, key = "#id")
	public UserResponseDTO update(Long id, UserRequestDTO requestDTO) {
		Optional<User> optUser = Optional.empty();
		User oldUser;
		User updatedUser;
		
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
		oldUser = optUser.get();
		updatedUser = new User(
				oldUser.getId(), 
				requestDTO.name(), 
				requestDTO.email()
				);
		
		updatedUser= this.repository.save(updatedUser);
		return UserMapper.toRecord(updatedUser);
	}
	
	@Override
	@CacheEvict(cacheNames = { "usersCacheRedis", "usersCacheCaffeine" }, key = "#id")
	public void delete(Long id) {
		repository.deleteById(id);
		return;
	}

}
