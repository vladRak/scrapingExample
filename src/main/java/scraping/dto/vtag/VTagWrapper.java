package scraping.dto.vtag;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VTagWrapper {

	boolean enabled;
	String vtag;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getVtag() {
		return vtag;
	}

	public void setVtag(String vtag) {
		this.vtag = vtag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(enabled, vtag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VTagWrapper other = (VTagWrapper) obj;
		return enabled == other.enabled && Objects.equals(vtag, other.vtag);
	}

	@Override
	public String toString() {
		return "VTagWrapper [enabled=" + enabled + ", vtag=" + vtag + "]";
	}

}
