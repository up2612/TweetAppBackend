package com.tweetapp.tweet.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.tweet.feign.AuthorisingClient;
import com.tweetapp.tweet.model.Tweet;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TweetControllerTest {

	@Autowired
	private MockMvc mock;

	@MockBean
	private AuthorisingClient authClient;

	private String apiVersion = "/api/v1.0/tweets";

	private static Tweet tweet;

	String username = "user";
	String id = "1";

	@BeforeAll
	static void setTweet() {

		tweet = new Tweet();
		tweet.setTweetId("1");
		tweet.setTweet("tweet");
	}

	@Test
	void testGetAllTweets() throws Exception {
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(true);
		mock.perform(get(apiVersion + "/all").header("Authorization", "Bearer hello")).andExpect(status().isOk());
	}

	@Test
	void testGetAllTweetsUnauthorized() throws Exception {
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(false);
		mock.perform(get(apiVersion + "/all").header("Authorization", "Bearer hello"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void testAddTweet() throws Exception {
		String tw = this.mapToJson(tweet);
		log.info(tw);
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(true);
		MvcResult mvcResult = mock.perform(post(apiVersion + "/" + username + "/add").contentType("application/json")
				.content(tw).header("Authorization", "Bearer hello")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	void testAddTweetUnauthorized() throws Exception {
		String tw = this.mapToJson(tweet);
		log.info(tw);
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(false);
		MvcResult mvcResult = mock.perform(post(apiVersion + "/" + username + "/add").contentType("application/json")
				.content(tw).header("Authorization", "Bearer hello")).andReturn();
		assertEquals(401, mvcResult.getResponse().getStatus());
	}

	@Test

	void testDeleteTweet() throws Exception {
		String id = "2";
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(true);
		MvcResult mvcResult = mock
				.perform(delete(apiVersion + "/" + username + "/delete/" + id).header("Authorization", "Bearer hello"))
				.andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	void testDeleteTweetUnauthorized() throws Exception {
		String id = "2";
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(false);
		MvcResult mvcResult = mock
				.perform(delete(apiVersion + "/" + username + "/delete/" + id).header("Authorization", "Bearer hello"))
				.andReturn();
		assertEquals(401, mvcResult.getResponse().getStatus());
	}

	@Test

	void testUpdateTweet() throws Exception {

		String tw = this.mapToJson(tweet);
		log.info(tw);
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(true);
		MvcResult mvcResult = mock.perform(put(apiVersion + "/" + username + "/update/" + id)
				.contentType("application/json").content(tw).header("Authorization", "Bearer hello")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test

	void testUpdateTweetUnauthorized() throws Exception {

		String tw = this.mapToJson(tweet);
		log.info(tw);
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(false);
		MvcResult mvcResult = mock.perform(put(apiVersion + "/" + username + "/update/" + id)
				.contentType("application/json").content(tw).header("Authorization", "Bearer hello")).andReturn();
		assertEquals(401, mvcResult.getResponse().getStatus());
	}

	@Test
	void testTweetsOfUsername() throws Exception {
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(true);
		MvcResult mvcResult = mock.perform(get(apiVersion + "/" + username).header("Authorization", "Bearer hello"))
				.andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	void testTweetsOfUsernameUnauthorized() throws Exception {
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(false);
		MvcResult mvcResult = mock.perform(get(apiVersion + "/" + username).header("Authorization", "Bearer hello"))
				.andReturn();
		assertEquals(401, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void testReplyTweet() throws Exception {
		String tw = this.mapToJson(tweet);
		log.info(tw);
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(true);
		MvcResult mvcResult = mock.perform(post(apiVersion + "/" + username + "/reply/" + id)
				.contentType("application/json").content(tw).header("Authorization", "Bearer hello")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void testReplyTweetUnauthorized() throws Exception {
		String tw = this.mapToJson(tweet);
		log.info(tw);
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(false);
		MvcResult mvcResult = mock.perform(post(apiVersion + "/" + username + "/reply/" + id)
				.contentType("application/json").content(tw).header("Authorization", "Bearer hello")).andReturn();
		assertEquals(401, mvcResult.getResponse().getStatus());
	}

	@Test
	void testLikeTweet() throws Exception {
		String tw = this.mapToJson(tweet);
		log.info(tw);
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(true);
		MvcResult mvcResult = mock.perform(put(apiVersion + "/" + username + "/like/" + id)
				.contentType("application/json").content(tw).header("Authorization", "Bearer hello")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void testLikeTweetUnauthorized() throws Exception {
		String tw = this.mapToJson(tweet);
		log.info(tw);
		when(authClient.authorizeTheRequest("Bearer hello")).thenReturn(false);
		MvcResult mvcResult = mock.perform(put(apiVersion + "/" + username + "/like/" + id)
				.contentType("application/json").content(tw).header("Authorization", "Bearer hello")).andReturn();
		assertEquals(401, mvcResult.getResponse().getStatus());
	}

	String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper ob = new ObjectMapper();
		return ob.writeValueAsString(object);
	}

}
