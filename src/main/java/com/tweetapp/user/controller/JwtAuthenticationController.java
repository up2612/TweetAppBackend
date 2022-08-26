package com.tweetapp.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.user.config.JwtTokenUtil;
import com.tweetapp.user.dto.JwtRequest;
import com.tweetapp.user.dto.JwtResponse;
import com.tweetapp.user.exception.AuthorizationException;
import com.tweetapp.user.model.User;
import com.tweetapp.user.service.JwtUserDetailsService;
import com.tweetapp.user.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/api/v1.0/tweets")
@Slf4j
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;


	/**
	 * @param authenticationRequest
	 * @return
	 * @throws AuthorizationException
	 * @throws Exception
	 */
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws AuthorizationException {
		Authentication auth=authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
		//return ResponseEntity.ok(auth);
	}

	private Authentication  authenticate(String userName, String password) throws AuthorizationException {
		try {
			log.info("Inside authenticate Method==================================");
			Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			log.info("Authentication Successful.....");
//			log.info(auth.getCredentials()+"+++++++++++++++++++++++++++++++++");
			return auth;
			
		} catch (DisabledException e) {
			throw new AuthorizationException("USER_DISABLED");
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new AuthorizationException("INVALID_CREDENTIALS");
		}
		
	}

	/**
	 * @param requestTokenHeader
	 * @return
	 */
	@PostMapping(value = "/authorize")
	public boolean authorizeTheRequest(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
		log.info("Inside authorize ==============");
		String jwtToken = null;
		String userName = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			log.info("JWT Tocken =======================");
			try {
				userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException | ExpiredJwtException e) {
				return false;
			}
		}
		return userName != null;

	}
	
	@PostMapping("/register")
	public User registerNewUser(@Valid @RequestBody User user) {
		log.info("register url called");
		return userService.registerNewUser(user);
	}
	
	@PostMapping("/{userName}/forgot")
	public User forgotPassword(@PathVariable String userName,@RequestBody User user){
		log.info("forgot password url called");
		String newPassword=user.getPassword();
		return userService.checkuserName(userName.toLowerCase(),newPassword);
	}
	
	@GetMapping("/users/all")
	public List<String> getAllUsers(@RequestHeader(value = "Authorization", required = true) String token) throws AuthorizationException{
		log.info("get all users url called");
		if(!authorizeTheRequest(token)) {
			log.error("Unauthorized token");
			throw new AuthorizationException("Not Allowed");
		}
		return userService.getAllUsers();
	}
	
	@GetMapping("/user/search/{username}")
	public List<String> getUserNames(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String username) throws AuthorizationException{
		log.info("search user url called");
		if(!authorizeTheRequest(token)) {
			log.error("Unauthorized token");
			throw new AuthorizationException("Not Allowed");
		}
		return userService.getUserNames(username.toLowerCase());
	}
	

	@GetMapping("/health-check")
	public ResponseEntity<String> healthCheck() {
		log.info("health-check");
		return new ResponseEntity<>("auth-Ok", HttpStatus.OK);
	}
	
	
}