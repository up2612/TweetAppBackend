package com.tweetapp.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tweetapp.user.model.User;
import com.tweetapp.user.service.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ControllerMethodTest {

	
	@InjectMocks
	private JwtAuthenticationController userController;
	
	@MockBean
	private JwtAuthenticationController authorisingClient;
	
	@Mock
	private UserService userService;
	
	
	static User user;
	String apiVersion="/api/v1.0/tweets";
	
	@BeforeAll
	static void setUser(){
		user = new User("user", "pass", "firstname","lastname","a@b.com","0987676543");
	}
	
	@Test
	public void testToken()
	{
		String token = "dummy";
		Mockito.when(authorisingClient.authorizeTheRequest(token)).thenReturn(true);
	}
	
	
	@Test
	public void testRegisterNewUser() {
		String token = "dummy";
		Mockito.when(authorisingClient.authorizeTheRequest(token)).thenReturn(true);
		User user = new User("Parthik199", "parthik", "Parthik", "Shah", "parthik1999@gmail.com","9408587579");
		Mockito.when(userController.registerNewUser(user)).thenReturn(user);
		assertEquals(userController.registerNewUser(user), user);
	}
	
	@Test
	void testForgotPassword() throws Exception{
		String token = "dummy";
		Mockito.when(authorisingClient.authorizeTheRequest(token)).thenReturn(true);
		User user1 = new User("Parthik199", "parthik", "Parthik", "Shah", "parthik1999@gmail.com","9408587579");
		User pwd = new User();
		pwd.setPassword("new");
		Mockito.when(userController.forgotPassword("Parthik199", pwd)).thenReturn(user1);
		assertEquals(userController.forgotPassword("Parthik199", pwd), user1);
		
	}

	@Test
	public void testRegisterNewUserService() {
		String token = "dummy";
		Mockito.when(authorisingClient.authorizeTheRequest(token)).thenReturn(true);
		User user = new User("Parthik199", "parthik", "Parthik", "Shah", "parthik1999@gmail.com","9408587579");
		Mockito.when(userService.registerNewUser(user)).thenReturn(user);
		assertEquals(userService.registerNewUser(user), user);
	}
	
	@Test
	void testForgotPasswordService() throws Exception{
		String token = "dummy";
		Mockito.when(authorisingClient.authorizeTheRequest(token)).thenReturn(true);
		User user1 = new User("Parthik199", "parthik", "Parthik", "Shah", "parthik1999@gmail.com","9408587579");
		User pwd = new User();
		pwd.setPassword("new");
		Mockito.when(userService.checkuserName("Parthik199", pwd.getPassword())).thenReturn(user1);
		assertEquals(userService.checkuserName("Parthik199", pwd.getPassword()), user1);
		
	}
	

}
