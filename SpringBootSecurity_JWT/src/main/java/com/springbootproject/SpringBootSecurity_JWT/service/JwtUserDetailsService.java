package com.springbootproject.SpringBootSecurity_JWT.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.context.Theme;

import com.springbootproject.SpringBootSecurity_JWT.dao.UserDAO;
import com.springbootproject.SpringBootSecurity_JWT.model.JwtRequest;
import com.springbootproject.SpringBootSecurity_JWT.model.UserEntity;


//JWTUserDetailsService implements the Spring Security UserDetailsService interface.
//It overrides the loadUserByUsername for fetching user details from the database using the username.
//The Spring Security Authentication Manager calls this method for getting the user details 
//from the database when authenticating the user details provided by the user.
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<UserEntity> userList = userDao.findUserByEmail(email);
		
		if (userList.size()==1) {
			return new User(userList.get(0).getEmail(), userList.get(0).getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
	}
	
	public int registerUser(JwtRequest jwtRequest) {
		List<UserEntity> userList = userDao.findUserByEmail(jwtRequest.getEmail());
		
		if(userList.size()==0) {
			UserEntity newUser = new UserEntity(jwtRequest.getEmail(), bcryptEncoder.encode(jwtRequest.getPassword()), jwtRequest.getFirstname(), jwtRequest.getLastname());
			newUser.setCreatedDateTime(LocalDateTime.now());
			newUser.setModifieDateTime(LocalDateTime.now());
			userDao.save(newUser);
//			successfully saved a new user
			return 1;
		} else {
//			The user already exists.
			return 0;
		}
	}
}