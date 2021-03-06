package bimr.rf.twitter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import bimr.rf.transformer.DataTransformer;
import bimr.rf.twitter.wrapper.TweetWrapper;
import bimr.rf.twitter.wrapper.TwitterRequestWrapper;
import bimr.rf.twitter.wrapper.TwitterResponseWrapper;
import bimr.util.GeneralConstants;
import bimr.util.TwitterEnum;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author GLK
 */
public class TwitterServiceClientBean implements TwitterServiceClient {

	private static final Logger log = Logger.getLogger(TwitterServiceClientBean.class.getName());

	@Override
	public TwitterResponseWrapper retrieveTweets(TwitterRequestWrapper request) {
		TwitterResponseWrapper response = new TwitterResponseWrapper();
		List<TweetWrapper> tweets = new ArrayList<>();
		tweets.addAll(DataTransformer.fromTwitterApiResponseToWrapper(
				retrieveTweetsFromApi(request.getHashtag(), request.getLastTweetId())));
		response.setTweets(tweets);
		return response;
	}

	private List<Status> retrieveTweetsFromApi(String hashtag, Long sinceId) {
		List<Status> tweets = new ArrayList<>();
		ConfigurationBuilder configurationBuilder = credentialsSetup();
		Twitter twitter = new TwitterFactory(configurationBuilder.build()).getInstance();

		Query query = new Query(hashtag);
		query.setSinceId(sinceId);
		query.setLang(GeneralConstants.EN_LANGUAGE);
		QueryResult result;
		try {
			result = twitter.search(query);
			tweets.addAll(result.getTweets());
		} catch (TwitterException e) {
			log.severe("Couldn't connect to TwitterAPI: " + e);
		}
		return tweets;
	}

	private ConfigurationBuilder credentialsSetup() {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true).setOAuthConsumerKey(TwitterEnum.CONSUMER_KEY.getCode())
				.setOAuthConsumerSecret(TwitterEnum.CONSUMER_SECRET.getCode())
				.setOAuthAccessToken(TwitterEnum.ACCESS_TOKEN.getCode())
				.setOAuthAccessTokenSecret(TwitterEnum.ACCESS_TOKEN_SECRET.getCode());
		return configurationBuilder;
	}

}
