package com.tweetapp.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Document(collection = "user")
public class User {
	
	@Id
	@NotEmpty
	private String userName;

	@NotEmpty(message = "Password cannot be null")
	private String password;
	
	@NotEmpty(message = "First Name cannot be null")
	private String firstName;
	
	@NotEmpty(message = "Last Name cannot be null")
	private String lastName;
	
	@NotEmpty(message = "Email Id cannot be null")
	@Indexed(unique = true)
	@Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$",message = "Email is not valid")
	private String emailId;
	
	@NotEmpty(message = "Contact Number cannot be null")
	private String contactNumber;
	
	
}
