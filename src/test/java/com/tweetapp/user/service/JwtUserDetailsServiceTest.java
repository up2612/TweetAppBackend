package com.tweetapp.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tweetapp.user.model.User;
import com.tweetapp.user.repository.UserRepository;

@SpringBootTest
class JwtUserDetailsServiceTest {
	
	@Mock
	private UserRepository userDao;

	@Mock
	private PasswordEncoder bcryptEncoder;

	@InjectMocks
	private JwtUserDetailsService service;
	
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void loadUserByUserNameShouldThrowExceptionTest() {
		when(userDao.findByUserName("wrongUserName")).thenReturn(null);
		assertThatThrownBy(() -> service.loadUserByUsername("wrongUserName")) 
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessage("User not found with username: wrongUserName");
		verify(userDao, Mockito.times(1)).findByUserName("wrongUserName");
	}
	@Test
	void loadUserByUserNameShouldUserNameTest() {
		when(userDao.findByUserName("Parthik199")).thenReturn(new User("Parthik199", "new", "Parthik", "Shah", "parthik1999@gmail.com","9408587579"));
		assertThat(service.loadUserByUsername("Parthik199")).isNotNull();
		verify(userDao, Mockito.times(1)).findByUserName("Parthik199");
	}
	

}
