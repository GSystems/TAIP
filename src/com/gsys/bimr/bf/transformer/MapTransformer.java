package com.gsys.bimr.bf.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gsys.bimr.bfcl.dto.EBirdDataDTO;
import com.gsys.bimr.bfcl.dto.EBirdRequestDTO;
import com.gsys.bimr.bfcl.dto.EBirdResponseDTO;
import com.gsys.bimr.bfcl.dto.TwitterDataDTO;
import com.gsys.bimr.bfcl.dto.TwitterRequestDTO;
import com.gsys.bimr.bfcl.dto.TwitterResponseDTO;
import com.gsys.bimr.df.model.EBirdData;
import com.gsys.bimr.df.model.EBirdRequest;
import com.gsys.bimr.df.model.EBirdResponse;
import com.gsys.bimr.df.model.TwitterData;
import com.gsys.bimr.df.model.TwitterRequest;
import com.gsys.bimr.df.model.TwitterResponse;

public class MapTransformer {

	private MapTransformer() {
	}

	public static EBirdResponseDTO fromEBirdResponseToDTO(EBirdResponse response) {
		EBirdResponseDTO responseDTO = new EBirdResponseDTO();
		responseDTO.seteBirdData(fromEBirdDataWrapperToDTO(response.geteBirdData()));
		return responseDTO;
	}
	
	public static TwitterResponseDTO fromTwitterResponseToDTO(TwitterResponse response) {
		TwitterResponseDTO responseDTO = new TwitterResponseDTO();
		responseDTO.setTweets(fromTwitterDataWrapperToDTO(response.getTweets()));
		return responseDTO;
	}
	
	private static List<EBirdDataDTO> fromEBirdDataWrapperToDTO(List<EBirdData> ebirdData) {
		List<EBirdDataDTO> ebirdDataDTO = new ArrayList<>();
		for (EBirdData currentData: ebirdData) {
			EBirdDataDTO ebirdDTO = new EBirdDataDTO();
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

	private static Map<String, TwitterDataDTO> fromTwitterDataWrapperToDTO(Map<String, TwitterData> tweets) {
		Map<String, TwitterDataDTO> tweetsDTO = new HashMap<>();
		for (Map.Entry<String, TwitterData> entry : tweets.entrySet()) {
			TwitterData tweet = entry.getValue();
			tweetsDTO.put(entry.getKey(), new TwitterDataDTO(tweet.getUser(), tweet.getLocation()));
		}
		return tweetsDTO;
	}

	public static EBirdRequest ebirdRequestFromDTO(EBirdRequestDTO requestDTO) {
		EBirdRequest request = new EBirdRequest();
		request.setRequestUriPattern(requestDTO.getRequestUriPattern());
		return request;
	}
	
	public static TwitterRequest twitterRequestFromDTO(TwitterRequestDTO requestDTO) {
		TwitterRequest request = new TwitterRequest();
		request.setHashtags(requestDTO.getHashtags());
		return request;
	}

}
