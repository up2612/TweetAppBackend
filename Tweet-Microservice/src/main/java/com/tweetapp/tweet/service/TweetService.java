package com.tweetapp.tweet.service;

import java.util.List;

import com.tweetapp.tweet.model.Tweet;
import com.tweetapp.tweet.model.TweetReply;

public interface TweetService {
	
	public List<Tweet> getAllTweets();
	
	public Tweet addTweet(Tweet tweet, String userName);
	
	public boolean deleteTweet(String userId);
	
	public Tweet updateTweet(String tweetId, Tweet tweet);
	
	public List<Tweet> tweetsOfUsername(String username);
	
	public void reply(String id, TweetReply reply, String userName);
	
	public boolean like(String id, String username);

}
