package com.tweetapp.tweet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.tweet.exception.AuthorizationException;
import com.tweetapp.tweet.feign.AuthorisingClient;
import com.tweetapp.tweet.model.Tweet;
import com.tweetapp.tweet.model.TweetReply;
import com.tweetapp.tweet.model.TweetResponse;
import com.tweetapp.tweet.serviceimpl.TweetServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/api/v1.0/tweets")
public class TweetController {

	@Autowired
	AuthorisingClient authClient;

	@Autowired
	TweetServiceImpl service;

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	String topic = "my_logs";

	@GetMapping("/all")
	public List<Tweet> getAllTweet(@RequestHeader(value = "Authorization", required = true) String token)
			throws AuthorizationException {
		log.debug("Insdie getAllTweet() in TweetController");
		if (!authClient.authorizeTheRequest(token)) {
			throw new AuthorizationException("Not allowed");
		}
		log.info("tweets added");
		return service.getAllTweets();
	}

	@PostMapping("/{userName}/add")
	public TweetResponse addTweet(@RequestHeader(value = "Authorization", required = true) String token,
			@RequestBody Tweet tweet, @PathVariable String userName) throws AuthorizationException {
		TweetResponse res = new TweetResponse();
		log.debug("Insdie addTweet() in TweetController");
		if (!authClient.authorizeTheRequest(token)) {
			throw new AuthorizationException("Not allowed");
		}
		service.addTweet(tweet, userName);
		res.setCode(HttpStatus.OK.value());
		res.setMessage("Tweet added");
		return res;
//		return new ResponseEntity<>("Tweet added", HttpStatus.OK);

	}

	@DeleteMapping("/{username}/delete/{id}")
	public TweetResponse deleteTweet(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String id) throws AuthorizationException {
		TweetResponse res = new TweetResponse();
		log.debug("Insdie deleteTweet() in TweetController");
		if (!authClient.authorizeTheRequest(token)) {
			log.error("Unauthorised token");
			throw new AuthorizationException("Not allowed");
		}
		String message = "";
		if(service.deleteTweet(id)) {
			 message = "Tweet Deleted Successfully !!";
		}
		else {
			message = "Trying to delete tweet is not present";
		}
		res.setCode(HttpStatus.OK.value());
		res.setMessage(message);
		return res;

	}

	@PutMapping("/{username}/update/{id}")
	public TweetResponse updateTweet(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String id, @RequestBody Tweet tweet) throws AuthorizationException {
		TweetResponse res = new TweetResponse();
		log.debug("Insdie updateTweet() in TweetController");
		if (!authClient.authorizeTheRequest(token)) {
			log.error("Unauthorised token");
			throw new AuthorizationException("Not allowed");
		}
		service.updateTweet(id, tweet);
		res.setCode(HttpStatus.OK.value());
		res.setMessage("Tweet updated");
		return res;
		
//		return new ResponseEntity<>("Tweet updated", HttpStatus.OK);

	}

	@GetMapping("/{username}")
	public List<Tweet> tweetsOfUsername(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String username) throws AuthorizationException {
		log.debug("Insdie updateTweet() in TweetController");
		if (!authClient.authorizeTheRequest(token)) {
			log.error("Unauthorised token");
			throw new AuthorizationException("Not allowed");
		}
		return service.tweetsOfUsername(username);

	}

	@PostMapping("/{username}/reply/{id}")
	public TweetResponse replyTweet(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String username, @PathVariable String id, @RequestBody TweetReply reply)
			throws AuthorizationException {
		TweetResponse res = new TweetResponse();
		log.debug("Insdie replyTweet() in TweetController");
		if (!authClient.authorizeTheRequest(token)) {
			log.error("Unauthorised token");
			throw new AuthorizationException("Not allowed");
		}
		service.reply(id, reply, username);
		res.setCode(HttpStatus.OK.value());
		res.setMessage("replied to tweet");
		return res;
//		return new ResponseEntity<>("replied to tweet", HttpStatus.OK);
	}

	@PutMapping("/{username}/like/{id}")
	public TweetResponse likeTweet(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String username, @PathVariable String id) throws AuthorizationException {
		log.debug("Insdie likeTweet() in TweetController");
		TweetResponse res = new TweetResponse();
		if (!authClient.authorizeTheRequest(token)) {
			log.error("Unauthorised token");
			throw new AuthorizationException("Not allowed");
		}
		boolean flag = service.like(id, username);
		String message = "";
		if (flag) {
			message = "Liked tweet";
		} else {
			message = "Unliked tweet";
		}
		res.setCode(HttpStatus.OK.value());
		res.setMessage(message);
		return res;
	}
}
