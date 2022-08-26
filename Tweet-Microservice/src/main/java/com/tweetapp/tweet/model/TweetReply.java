package com.tweetapp.tweet.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TweetReply {
	
	private String reply;
	private String userName;
	private LocalDateTime date;
	

}
