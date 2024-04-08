package scraping;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import scraping.dto.match.Match;
import scraping.dto.match.TopLeague;
import scraping.dto.sport.Sport;
import scraping.dto.vtag.HeadLine;

public class Scraper {

	private final static String SPORTS_URL = "https://leonbets.com/api-2/betline/sports?ctag=en-US";
	private final static String VTAG_URL = "https://leonbets.com/api-2/betline/headline-matches?ctag=en-US";
	private final static String LEAGUE_TMPL = "https://leonbets.com/api-2/betline/changes/all?ctag=en-US&vtag=%s&league_id=%s";
	private final static String MATCH_TMPL = "https://leonbets.com/api-2/betline/event/all?ctag=en-US&eventId=%s";

	private ObjectMapper mapper = new ObjectMapper();

	private final ExecutorService executor;
	
	public Scraper() {
		executor = Executors.newFixedThreadPool(3);
	}

	public Scraper(ExecutorService executor) {
		this.executor = executor;
	}

	public String scrap(Set<String> sportNames)	{

		String vTag = getVTag();
		List<Sport> sports = getSports();

		List<CompletableFuture<String>> sportsResult = new ArrayList<CompletableFuture<String>>();

		sports.stream()
		.filter(sport -> sportNames.contains(sport.getName()))
		.forEach(sport -> {
			CompletableFuture<String> sportFuture = CompletableFuture.supplyAsync(() -> {
				return processSport(sport, vTag);
			}, executor);
			sportsResult.add(sportFuture);
		});
		
		return sportsResult.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.joining(""));		
	}

	private String getVTag() {
		String tag = "";
		try {
			HeadLine headLine = mapper.readValue(request(VTAG_URL), HeadLine.class);
			tag = headLine.getLive().getVtag();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tag;
	}

	private List<Sport> getSports() {
		List<Sport> list = new ArrayList<>();
		try {
			String sportsJSON = request(SPORTS_URL);
			list = mapper.readValue(sportsJSON, new TypeReference<List<Sport>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	private String processSport(Sport sport, String vTag) {

		StringBuffer sBuffer = new StringBuffer();

		String sportName = sport.getName();

		sport.getRegions().forEach(region -> {
			region.getLeagues().stream()
			.filter(league -> league.isTop())
			.forEach(league -> {
				
				sBuffer.append(sportName + ", " + league.getName() + "\n");
				
				try {
					String leagueQuery = String.format(LEAGUE_TMPL, vTag, league.getId());
					String leagueMatchesJSON = request(leagueQuery);
					TopLeague topLeague = mapper.readValue(leagueMatchesJSON, TopLeague.class);
					Match match = topLeague.getData().get(0);

					sBuffer.append(match.toString());

					String matchQuery = String.format(MATCH_TMPL, match.getId());
					String markersJSON = request(matchQuery);
					Match markers = mapper.readValue(markersJSON, Match.class);

					markers.getMarkets()
					.forEach(marker -> {
						sBuffer.append(marker.toString());

						marker.getRunners()
						.forEach(runner -> {
							sBuffer.append(runner.toString());
						});
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		});
		
		return sBuffer.toString();
	}

	private static String request(String url) throws IOException {
		return getConnection(url).header("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36")
				.execute().body();
	}

	private static Connection getConnection(String url) {
		return Jsoup.connect(url).ignoreContentType(true);
	}

}