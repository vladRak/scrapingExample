package scraping;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(3);

		Set<String> sportNames = new HashSet<String>();
		sportNames.add("Football");
		sportNames.add("Ice Hockey");
		sportNames.add("Tennis");
		sportNames.add("Basketball");

		String result = new Scraper(executor).scrap(sportNames);
		
		System.out.println(result);

		executor.shutdown();
		executor.awaitTermination(30, TimeUnit.SECONDS);
	}
}
