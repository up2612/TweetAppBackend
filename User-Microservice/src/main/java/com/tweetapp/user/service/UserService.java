package com.tweetapp.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.user.exception.UserAlreadyExistsException;
import com.tweetapp.user.exception.UserDoesntExistsException;
import com.tweetapp.user.model.User;
import com.tweetapp.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtUserDetailsService userDetailsService;
	
	public User registerNewUser(User user) {
		log.info("Inside register new user service");
		user.setUserName(user.getUserName().toLowerCase());
		if(userRepository.existsById(user.getUserName())) {
			log.error("Username already exists");
			throw new UserAlreadyExistsException("Username already exists");
		}
		return userRepository.save(user);
		
	}
	
	public User checkuserName(String userName, String newPassword) {
		log.info("Inside forgot password checking username service");
		if(!userRepository.existsById(userName)) {
			throw new UserDoesntExistsException("Username doesn't exists");
			
		}
		User user=userRepository.findByUserName(userName);
		user.setPassword(newPassword);
		userRepository.save(user);
		log.info("new password saved");
		return user;
	}
	
	
	public List<String> getAllUsers(){
		log.info("Inside get all users service");
		List<User> userList=new ArrayList<User>();
		List<String> usernames= new ArrayList<String>();
		userList=userRepository.findAll();
		for(User u:userList) {
			
			usernames.add(u.getUserName());
		}
		return usernames;
	}
	
	public List<String> getUserNames(String userName){
		log.info("Inside get usernames on basis of search service");
		//log.info(username.toString());
		List<User> userList=new ArrayList<User>();
		List<String> usernames= new ArrayList<String>();
		
		//User user=userRepository.findById(userName).orElseThrow(() -> new UserDoesntExistsException("user not avaliable"));
		userList=userRepository.findByUserNameStartsWith(userName);
		for(User u:userList) {
			log.info(u.getUserName());
			usernames.add(u.getUserName());
		}
		
		return usernames;
	}
	
	
}
