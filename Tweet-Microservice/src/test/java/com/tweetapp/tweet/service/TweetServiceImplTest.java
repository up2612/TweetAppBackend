package com.tweetapp.tweet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tweetapp.tweet.model.Tweet;
import com.tweetapp.tweet.repository.TweetRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TweetServiceImplTest {
	
	@MockBean
	TweetRepository repo;
	
	@Autowired
	TweetService service;
	
	static List<Tweet> tweetList=new ArrayList<Tweet>();
	static Tweet tweet;
	
	@BeforeEach
	void setTweetList() {
		tweet=new Tweet();
		tweet.setTweetId("1");
		tweet.setTweet("tweet");
		tweetList.add(tweet);
	}
	
	@Test
	void testGetAllTweets() throws Exception{
		
		when(repo.findAll()).thenReturn(tweetList);
		assertEquals(service.getAllTweets(),tweetList);
	}
	
	@Test
	void testAddTweet() throws Exception{
		String username="user";
		when(repo.save(tweet)).thenReturn(tweet);
		assertEquals(service.addTweet(tweet,username),tweet);
	}
	
	@Test
	void testDeleteTweet() throws Exception{
		String id="2";
		
		assertEquals(service.deleteTweet(id),true);
	}
	
	@Test
	void testUpdateTweet() throws Exception{
		String username="user";
		when(repo.save(tweet)).thenReturn(tweet);
		assertEquals(service.addTweet(tweet,username),tweet);
	}
	
	
}
