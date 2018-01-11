package bimr.bf.transformer;

import java.util.ArrayList;
import java.util.List;

import bimr.bfcl.dto.*;
import bimr.df.model.EbirdData;
import bimr.df.model.EbirdRequest;
import bimr.df.model.EbirdResponse;
import bimr.df.model.Tweet;
import bimr.df.model.TwitterRequest;
import bimr.df.model.TwitterResponse;

/**
 * @author GLK
 */
public class MapTransformer {

	private MapTransformer() {
	}

	public static TwitterRequest twitterRequestFromDTO(TwitterRequestDTO requestDTO) {
		return new TwitterRequest(requestDTO.getHashtag(), requestDTO.getLastTweetId(), requestDTO.getUntilDate());
	}

	public static TwitterResponseDTO fromTwitterResponseToDTO(TwitterResponse response) {
		TwitterResponseDTO responseDTO = new TwitterResponseDTO();
		responseDTO.setTweets(fromTweetsToDTO(response.getTweets()));
		return responseDTO;
	}

	public static List<TweetDTO> fromTweetsToDTO(List<Tweet> tweets) {
		List<TweetDTO> tweetsDTO = new ArrayList<>();
		for (Tweet tweet : tweets) {
			TweetDTO tweetDTO = fromTweetToDTO(tweet);
			tweetsDTO.add(tweetDTO);
		}
		return tweetsDTO;
	}

	private static TweetDTO fromTweetToDTO(Tweet tweet) {
		TweetDTO tweetDTO = new TweetDTO();
		tweetDTO.setId(tweet.getId());
		tweetDTO.setTweetId(tweet.getTweetId());
		tweetDTO.setLatitude(tweet.getLatitude());
		tweetDTO.setLongitude(tweet.getLongitude());
		tweetDTO.setObservationDate(tweet.getObservationDate());
		tweetDTO.setTweetMessage(tweet.getTweetMessage());
		return tweetDTO;
	}

	public static List<Tweet> toTweetsFromDTO(List<TweetDTO> tweetsDTO) {
		List<Tweet> tweets = new ArrayList<>();
		for (TweetDTO tweetDTO : tweetsDTO) {
			Tweet tweet = new Tweet();
			tweet.setId(tweetDTO.getId());
			tweet.setTweetId(tweetDTO.getTweetId());
			tweet.setLatitude(tweetDTO.getLatitude());
			tweet.setLongitude(tweetDTO.getLongitude());
			tweet.setObservationDate(tweetDTO.getObservationDate());
			tweet.setTweetMessage(tweetDTO.getTweetMessage());
			tweets.add(tweet);
		}
		return tweets;
	}

	public static EbirdRequest toEbirdRequestFromDTO(EbirdRequestDTO requestDTO) {
		EbirdRequest request = new EbirdRequest();
		request.setRequestUriPattern(requestDTO.getRequestUriPattern());
		return request;
	}

	public static EbirdResponseDTO fromEBirdResponseToDTO(EbirdResponse response) {
		EbirdResponseDTO responseDTO = new EbirdResponseDTO();
		responseDTO.setEbirdData(fromEBirdDataWrapperToDTO(response.getEbirdData()));
		return responseDTO;
	}

	private static List<EbirdDataDTO> fromEBirdDataWrapperToDTO(List<EbirdData> ebirdData) {
		List<EbirdDataDTO> ebirdDataDTO = new ArrayList<>();
		for (EbirdData currentData : ebirdData) {
			EbirdDataDTO ebirdDTO = new EbirdDataDTO();
			ebirdDTO.setCommonName(currentData.getCommonName());
			ebirdDTO.setCountryName(currentData.getCountryName());
			ebirdDTO.setLatitude(currentData.getLatitude());
			ebirdDTO.setLocalityName(currentData.getLocalityName());
			ebirdDTO.setLongitude(currentData.getLongitude());
			ebirdDTO.setObservationDate(currentData.getObservationDate());
			ebirdDTO.setScientificName(currentData.getScientificName());
			ebirdDTO.setStateName(currentData.getStateName());
			ebirdDTO.setUserDisplayName(currentData.getUserDisplayName());
			ebirdDataDTO.add(ebirdDTO);
		}
		return ebirdDataDTO;
	}

	public static EbirdResponse toEbirdResponseFromDTO(EbirdResponseDTO responseDTO) {
		EbirdResponse response = new EbirdResponse();
		List<EbirdData> ebirdDataList = new ArrayList<>();
		for (EbirdDataDTO ebirdDataDTO : responseDTO.getEbirdData()) {
			EbirdData ebirdData = new EbirdData();
			ebirdData.setCommonName(ebirdDataDTO.getCommonName());
			ebirdData.setCountryName(ebirdDataDTO.getCountryName());
			ebirdData.setLatitude(ebirdDataDTO.getLatitude());
			ebirdData.setLocalityName(ebirdDataDTO.getLocalityName());
			ebirdData.setLongitude(ebirdDataDTO.getLongitude());
			ebirdData.setObservationDate(ebirdDataDTO.getObservationDate());
			ebirdData.setScientificName(ebirdDataDTO.getScientificName());
			ebirdData.setStateName(ebirdDataDTO.getStateName());
			ebirdData.setUserDisplayName(ebirdDataDTO.getUserDisplayName());
			ebirdDataList.add(ebirdData);
		}
		response.setEbirdData(ebirdDataList);
		return response;
	}
}
