package scraping.dto.match;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import scraping.dto.AbstractScrapedObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Match extends AbstractScrapedObject {

	private Date kickoff;
	private List<Market> markets;

	public Date getKickoff() {
		return kickoff;
	}

	public void setKickoff(Long kickoff) {
		this.kickoff = new Date(kickoff);
	}

	public List<Market> getMarkets() {
		return markets;
	}

	public void setMarkets(List<Market> markets) {
		this.markets = markets;
	}
}
