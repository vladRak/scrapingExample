package scraping;

public interface ScraperConstants {
	static final String PROTOCOL = "https";
	static final String HOST = "leonbets.com";
	static final String API_PREFIX = "/api-2";
	static final String VTAG_PATH = API_PREFIX + "/betline/headline-matches";
	static final String VTAG_QUERY = "ctag=en-US";
	static final String SPORTS_PATH = API_PREFIX + "/betline/sports";
	static final String SPORTS_QUERY = "ctag=en-US";
	static final String LEAGUE_PATH = API_PREFIX + "/betline/changes/all";
	static final String LEAGUE_QUERY_TMPL = "ctag=en-US&vtag=%s&league_id=%s";
	static final String MATCH_PATH = API_PREFIX + "/betline/event/all";
	static final String MATCH_QUERY_TMPL = "ctag=en-US&eventId=%s";
}
