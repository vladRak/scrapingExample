package scraping;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import scraping.dto.sport.Sport;
import scraping.dto.sport.SportsWrapper;
import scraping.util.RequestHelper;
import scraping.util.ScraperHelper;

public class Scraper implements ScraperConstants {

	private final ExecutorService executor;
	private StringBuffer result = new StringBuffer();

	public Scraper() {
		executor = Executors.newFixedThreadPool(3);
	}

	public Scraper(ExecutorService executor) {
		this.executor = executor;
	}

	public String scrap(Set<String> sportNames) throws JsonMappingException, JsonProcessingException,
			MalformedURLException, URISyntaxException, InterruptedException {
		RequestHelper requestHelper = new RequestHelper(executor);

		String vTag = ScraperHelper.getVTag(requestHelper);
		SportsWrapper<List<Sport>> sports = ScraperHelper.getSports(requestHelper);

		CompletableFuture.supplyAsync(() -> {
			sports.getSports(sportNames).forEach(sport -> {
				String sportName = sport.getName();

				if (sportNames.contains(sportName)) {
					result.append(CompletableFuture.supplyAsync(() -> {
						return new ScraperHelper(vTag, sport).processSport(requestHelper);
					}, executor).join());
				}
			});
			return "DONE";
		}, executor).join();

		return result.toString();
	}
}
