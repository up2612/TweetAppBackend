package com.tweetapp.tweet.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.tweet.model.Tweet;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String>{

	Tweet findByTweetId(String tweetId);
	
	List<Tweet> findByUserName(String userName);
	
}
