package scraping.dto.vtag;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadLine {

	private VTagWrapper live;
	private VTagWrapper prematch;
	private VTagWrapper events;

	public VTagWrapper getLive() {
		return live;
	}

	public void setLive(VTagWrapper live) {
		this.live = live;
	}

	public VTagWrapper getPrematch() {
		return prematch;
	}

	public void setPrematch(VTagWrapper prematch) {
		this.prematch = prematch;
	}

	public VTagWrapper getEvents() {
		return events;
	}

	public void setEvents(VTagWrapper events) {
		this.events = events;
	}

	@Override
	public int hashCode() {
		return Objects.hash(events, live, prematch);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HeadLine other = (HeadLine) obj;
		return Objects.equals(events, other.events) && Objects.equals(live, other.live)
				&& Objects.equals(prematch, other.prematch);
	}

	@Override
	public String toString() {
		return "HeadLine [live=" + live + ", prematch=" + prematch + ", events=" + events + "]";
	}
}
