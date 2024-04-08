package scraping.dto.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import scraping.dto.AbstractScrapedObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Runner extends AbstractScrapedObject {

	private float price;

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "\t\t\t" + getName() + ", " + getPrice() + ", " + getId() + "\n";
	}
}
