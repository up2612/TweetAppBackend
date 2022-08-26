package com.tweetapp.tweet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tweetapp.tweet.model.TweetReply;

@SpringBootTest
class TweetApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void main()
	{
		TweetApplication.main(new String[] {});
	}
	
	@Test
	void testNoArgs()
	{
		assertThat(new TweetReply()).isNotNull();
	}
	
	
	@Test
	void testAllArgs()
	{
		TweetReply tweetReply = new TweetReply("Test",null, null);
		assertThat(assertThat(tweetReply).isNotNull());
	}
}
