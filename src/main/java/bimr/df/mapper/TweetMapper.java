package bimr.df.mapper;

import java.util.ArrayList;
import java.util.List;

import bimr.df.model.Tweet;
import bimr.df.model.TwitterRequest;
import bimr.df.model.TwitterResponse;
import bimr.df.model.TwitterUser;
import bimr.rf.twitter.entity.TweetEntity;
import bimr.rf.twitter.wrapper.TweetWrapper;
import bimr.rf.twitter.wrapper.TwitterRequestWrapper;
import bimr.rf.twitter.wrapper.TwitterResponseWrapper;
import bimr.rf.twitter.wrapper.TwitterUserWrapper;

/**
 * @author GLK
 */
public class TweetMapper {

	private TweetMapper() {
	}

	public static TwitterRequestWrapper fromTwitterRequestToWrapper(TwitterRequest request) {
		return new TwitterRequestWrapper(request.getHashtag(), request.getLastTweetId(), request.getUntilDate());
	}

	public static TwitterResponse toTwitterResponseFromWrapper(TwitterResponseWrapper responseWrapper) {
		TwitterResponse response = new TwitterResponse();
		response.setTweets(toTwitterListFromWrapper(responseWrapper.getTweets()));
		return response;
	}

	private static List<Tweet> toTwitterListFromWrapper(List<TweetWrapper> tweetsWrapper) {
		List<Tweet> tweets = new ArrayList<>();
		for (TweetWrapper tweetWrapper : tweetsWrapper) {
			tweets.add(toTwitterFromWrapper(tweetWrapper));
		}
		return tweets;
	}

	private static Tweet toTwitterFromWrapper(TweetWrapper tweetWrapper) {
		Tweet tweet = new Tweet();
		tweet.setTweetId(tweetWrapper.getTweetId());
		tweet.setLatitude(tweetWrapper.getLatitude());
		tweet.setLongitude(tweetWrapper.getLongitude());
		tweet.setObservationDate(tweetWrapper.getObservationDate());
		tweet.setTweetMessage(tweetWrapper.getTweetMessage());
		tweet.setUser(toTwitterUserFromWrapper(tweetWrapper.getUser()));
		return tweet;
	}

	private static TwitterUser toTwitterUserFromWrapper(TwitterUserWrapper userWrapper) {
		TwitterUser user = new TwitterUser();
		user.setEmail(userWrapper.getEmail());
		user.setId(userWrapper.getId());
		user.setIsGeoEnabled(String.valueOf(userWrapper.isGeoEnabled()));
		user.setLocation(userWrapper.getLocation());
		user.setName(userWrapper.getName());
		user.setScreenName(userWrapper.getScreenName());
		return user;
	}

	public static List<TweetEntity> fromTweetListToEntity(List<Tweet> tweets) {
		List<TweetEntity> entityList = new ArrayList<>();
		for (Tweet tweet : tweets) {
			entityList.add(fromTweetToEntity(tweet));
		}
		return entityList;
	}

	public static TweetEntity fromTweetToEntity(Tweet tweet) {
		TweetEntity tweetEntity = new TweetEntity();
		tweetEntity.setId(tweet.getId());
		tweetEntity.setTweetId(tweet.getTweetId());
		tweetEntity.setLatitude(tweet.getLatitude());
		tweetEntity.setLongitude(tweet.getLongitude());
		tweetEntity.setObservationDate(tweet.getObservationDate());
		tweetEntity.setTweetMessage(tweet.getTweetMessage());
		return tweetEntity;
	}

	public static List<Tweet> toTweetListFromEntity(List<TweetEntity> entities) {
		List<Tweet> tweets = new ArrayList<>();
		for (TweetEntity entity : entities) {
			tweets.add(toTweetFromEntity(entity));
		}
		return tweets;
	}

	private static Tweet toTweetFromEntity(TweetEntity tweetEntity) {
		Tweet tweet = new Tweet();
		tweet.setId(tweetEntity.getId());
		tweet.setTweetId(tweetEntity.getTweetId());
		tweet.setLatitude(tweetEntity.getLatitude());
		tweet.setLongitude(tweetEntity.getLongitude());
		tweet.setObservationDate(tweetEntity.getObservationDate());
		tweet.setTweetMessage(tweetEntity.getTweetMessage());
		return tweet;
	}

}
