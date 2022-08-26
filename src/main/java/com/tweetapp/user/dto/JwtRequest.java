package com.tweetapp.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class JwtRequest implements Serializable {


	private static final long serialVersionUID = 5926468583005150707L;
	
	private String userName;
	private String password;
	

	public JwtRequest(String userName, String password) {
		this.setUserName(userName);
		this.setPassword(password);
	}
}