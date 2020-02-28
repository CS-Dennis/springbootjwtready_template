package com.springbootproject.SpringBootSecurity_JWT.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springbootproject.SpringBootSecurity_JWT.model.UserEntity;

public interface UserDAO extends CrudRepository<UserEntity, Long>{
	@Query(value = "SELECT * from user_entity WHERE email = ?1", nativeQuery = true)
	public List<UserEntity> findUserByEmail(String email);
}
