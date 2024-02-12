package scraping.dto.match;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopLeague {

	List<Match> data;

	public List<Match> getData() {
		return data;
	}

	public void setData(List<Match> data) {
		this.data = data;
	}

}
