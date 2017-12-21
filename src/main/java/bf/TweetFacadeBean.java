package main.java.bf;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import main.java.bf.transformer.MapTransformer;
import main.java.bfcl.TweetFacade;
import main.java.bfcl.dto.TweetDTO;
import main.java.bfcl.dto.TwitterRequestDTO;
import main.java.bfcl.dto.TwitterResponseDTO;
import main.java.df.TweetRepo;
import main.java.util.AsyncUtils;
import main.java.util.GeneralConstants;
import main.java.util.StandfordEnum;

/**
 * @author GLK
 */
@Stateless
public class TweetFacadeBean implements TweetFacade {

	private static final Logger log = Logger.getLogger(TweetFacadeBean.class.getName());

	@Inject
	private TweetRepo repo;

	@Override
	public void retrieveTweetsFromApi(TwitterRequestDTO request) {
		TwitterResponseDTO response = MapTransformer.fromTwitterResponseToDTO(
				AsyncUtils.getResultFromAsyncTask(repo.retrieveTweets(MapTransformer.twitterRequestFromDTO(request))));
		if (!response.getTweets().isEmpty()) {
			persistTweets(filterTweets(response.getTweets()));
			// persistTweets(response.getTweets());
		} else {
			log.info("No data from Twitter API");
		}
	}

	private List<TweetDTO> filterTweets(List<TweetDTO> tweets) {
		List<TweetDTO> filteredTweets = new ArrayList<>();
		Properties props = new Properties();
		props.put(StandfordEnum.PROPS_KEY.getCode(), StandfordEnum.PROPS_VALUE.getCode());
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		for (TweetDTO tweet : tweets) {
			Annotation document = new Annotation(tweet.getTweetMessage());
			// run all Annotators on this text
			pipeline.annotate(document);
			if (parseText(document)) {
				filteredTweets.add(tweet);
			}
		}
		return filteredTweets;
	}

	/**
	 * parse every sentence from the tweet and annotate it
	 */
	private boolean parseText(Annotation document) {
		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				// this is the text of the token
				String word = token.get(CoreAnnotations.TextAnnotation.class);
				// this is the POS tag of the token
				String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
				// this is the NER label of the token
				String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

				if (ne.equals(StandfordEnum.LOCATION.getCode())) {
					return true;
				}
				String text = String.format("Print: word: [%s] pos: [%s] ne: [%s]", word, pos, ne);
				log.info(text);
			}
		}
		return false;
	}

	@Override
	public void persistTweets(List<TweetDTO> tweets) {
		repo.insertTweets(MapTransformer.toTweetsFromDTO(tweets));
	}

	@Override
	@Schedule(second = "*", minute = "15", hour = "*", persistent = false)
	public void twitterApiCallScheduled() {
		TwitterRequestDTO request = createRequest();
		retrieveTweetsFromApi(request);
	}

	@Override
	public Long retrieveLastTweetId() {
		List<Long> sinceIds = AsyncUtils.getResultFromAsyncTask(repo.retrieveLastTweetId());
		Long sinceId = GeneralConstants.DEFAULT_SINCE_ID;
		if(sinceIds.get(0) != null) {
			sinceId = sinceIds.get(0);
		}
		return sinceId;
	}

	@Override 
	public List<TweetDTO> retrieveTweetsFromDB() {
		return MapTransformer.fromTweetsToDTO(AsyncUtils.getResultFromAsyncTask(repo.retrieveTweetsFromDB()));
	}

}
