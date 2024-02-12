package scraping.dto.sport;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class League {

	private String id;
	private String name;
	private boolean top;
	private int topOrder;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public int getTopOrder() {
		return topOrder;
	}

	public void setTopOrder(int topOrder) {
		this.topOrder = topOrder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, top, topOrder);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		League other = (League) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && top == other.top
				&& topOrder == other.topOrder;
	}

	@Override
	public String toString() {
		return "Leagu [id=" + id + ", name=" + name + ", top=" + top + ", topOrder=" + topOrder + "]";
	}
}
