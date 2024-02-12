package scraping.dto.sport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import scraping.dto.AbstractScrapedObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Region extends AbstractScrapedObject {

	private List<League> leagues;

	public List<League> getLeagues() {
		return leagues;
	}

	public void setLeagues(List<League> leagues) {
		this.leagues = leagues;
	}
}
