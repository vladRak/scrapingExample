package scraping.dto.sport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import scraping.dto.AbstractScrapedObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sport extends AbstractScrapedObject {

	private List<Region> regions;

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
}
