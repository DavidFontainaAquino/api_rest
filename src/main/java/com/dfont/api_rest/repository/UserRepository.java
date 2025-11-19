package com.dfont.api_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dfont.api_rest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
