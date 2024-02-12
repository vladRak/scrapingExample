package scraping.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import scraping.ScraperConstants;
import scraping.dto.match.Match;
import scraping.dto.match.TopLeague;
import scraping.dto.sport.Sport;
import scraping.dto.sport.SportsWrapper;
import scraping.dto.vtag.HeadLine;

public class ScraperHelper implements ScraperConstants {

	private String vTag;
	private Sport sport;

	public ScraperHelper(String vTag, Sport sport) {
		this.vTag = vTag;
		this.sport = sport;
	}

	public String processSport(RequestHelper requestHelper) {
		StringBuffer sBuffer = new StringBuffer();
		ObjectMapper mapper = new ObjectMapper();

		String sportName = sport.getName();

		sport.getRegions().forEach(region -> {
			region.getLeagues().forEach(league -> {
				if (league.isTop()) {
					sBuffer.append(sportName + ", " + league.getName() + "\n");

					try {
						String leagueQuery = String.format(LEAGUE_QUERY_TMPL, vTag, league.getId());
						String leagueMatchsJSON = requestHelper
								.requestBodyWithHeaders(getTopLeagueURL(leagueQuery).toString());
						TopLeague topLeague = mapper.readValue(leagueMatchsJSON, TopLeague.class);
						Match match = topLeague.getData().get(0);

						sBuffer.append("\t" + match.getName() + ", " + formatDateToUTC(match.getKickoff()) + ", "
								+ match.getId() + "\n");

						String matchQuery = String.format(MATCH_QUERY_TMPL, match.getId());
						String markersJSON = requestHelper.requestBodyWithHeaders(getMatchURL(matchQuery).toString());
						Match markers = mapper.readValue(markersJSON, Match.class);

						markers.getMarkets().forEach(marker -> {
							sBuffer.append("\t\t" + marker.getName() + "\n");

							marker.getRunners().forEach(runner -> {
								sBuffer.append("\t\t\t" + runner.getName() + ", " + runner.getPrice() + ", "
										+ runner.getId() + "\n");
							});
						});
					} catch (JsonProcessingException | MalformedURLException | URISyntaxException e) {
						e.printStackTrace();
					}
				}
			});
		});
		return sBuffer.toString();
	}

	public static URL getMatchURL(String query) throws MalformedURLException, URISyntaxException {
		return new URLBuilder.Builder(PROTOCOL, HOST).setPath(MATCH_PATH).setQuery(query).build();
	}

	public static URL getTopLeagueURL(String query) throws MalformedURLException, URISyntaxException {
		return new URLBuilder.Builder(PROTOCOL, HOST).setPath(LEAGUE_PATH).setQuery(query).build();
	}

	public static String getVTag(RequestHelper requestHelper) throws MalformedURLException, URISyntaxException {
		return ScraperHelper.getVTagFromJSON(requestHelper.requestBodyWithHeaders(getVTagURL().toString()));
	}

	public static URL getVTagURL() throws MalformedURLException, URISyntaxException {
		return new URLBuilder.Builder(PROTOCOL, HOST).setPath(VTAG_PATH).setQuery(VTAG_QUERY).build();
	}

	public static SportsWrapper<List<Sport>> getSports(RequestHelper requestHelper)
			throws JsonMappingException, JsonProcessingException, MalformedURLException, URISyntaxException {
		String sportsJSON = requestHelper.requestBodyWithHeaders(getSportURL().toString());
		return mappSports(sportsJSON);
	}

	public static SportsWrapper<List<Sport>> mappSports(String sportsJSON)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return new SportsWrapper<List<Sport>>(mapper.readValue(sportsJSON, new TypeReference<List<Sport>>() {
		}));
	}

	public static URL getSportURL() throws MalformedURLException, URISyntaxException {
		return new URLBuilder.Builder(PROTOCOL, HOST).setPath(SPORTS_PATH).setQuery(SPORTS_QUERY).build();
	}

	public static String getVTagFromJSON(String vTagJSON) {
		ObjectMapper mapper = new ObjectMapper();

		HeadLine headLine;
		String vTag = "";
		try {
			headLine = mapper.readValue(vTagJSON, HeadLine.class);
			vTag = headLine.getLive().getVtag();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return vTag;
	}

	public static String formatDateToUTC(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}

}
