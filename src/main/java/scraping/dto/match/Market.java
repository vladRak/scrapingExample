package scraping.dto.match;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import scraping.dto.AbstractScrapedObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Market extends AbstractScrapedObject {

	List<Runner> runners;

	public List<Runner> getRunners() {
		return runners;
	}

	public void setRunners(List<Runner> runners) {
		this.runners = runners;
	}

}
