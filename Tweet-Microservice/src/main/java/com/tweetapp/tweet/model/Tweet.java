package com.tweetapp.tweet.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Tweet {
	
	@Id
	private String tweetId;
	
	private String tweet;
	
	private List<TweetReply> reply;
	
	private String userName;
	
	private List<String> likes;
	
	private LocalDateTime date;
	
}
