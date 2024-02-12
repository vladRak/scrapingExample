package scraping;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class App {

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException, MalformedURLException,
			URISyntaxException, InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(2);

		Set<String> sportNames = new HashSet<String>();
		sportNames.add("Football");
		sportNames.add("Ice Hockey");
		sportNames.add("Tennis");
		sportNames.add("Basketball");

		Scraper scraper = new Scraper(executor);

		String result = scraper.scrap(sportNames);

		System.out.println(result);

		executor.shutdown();
		executor.awaitTermination(30, TimeUnit.SECONDS);
	}
}
