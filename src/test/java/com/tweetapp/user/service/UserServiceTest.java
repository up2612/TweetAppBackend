package com.tweetapp.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.tweetapp.user.controller.JwtAuthenticationController;
import com.tweetapp.user.model.User;
import com.tweetapp.user.repository.UserRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	UserService userService;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	private JwtAuthenticationController authClient;

	
	@Test
	public void testRegisterNewUser()
	{
		User user = new User("Parthik199", "parthik", "Parthik", "Shah", "parthik1999@gmail.com","9408587579");
		
//		String token = "dummy";
//		Mockito.when(authClient.authorizeTheRequest(token)).thenReturn(true);
		Mockito.when(userRepository.save(user)).thenReturn((user));
		assertEquals(userService.registerNewUser(user),user);
	}
	
	
	
}
