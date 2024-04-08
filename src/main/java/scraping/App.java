package scraping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.crypto.Data;

public class App {

	public static void main(String[] args) throws InterruptedException {

		long startTime = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(3);

		Set<String> sportNames = new HashSet<String>();
		sportNames.add("Football");
		sportNames.add("Ice Hockey");
		sportNames.add("Tennis");
		sportNames.add("Basketball");

//		String result = new Scraper(executor).scrap(sportNames);
		
		String result = new Scraper2(executor).scrap(sportNames);

		System.out.println(result);

		executor.shutdown();
		executor.awaitTermination(30, TimeUnit.SECONDS);
		
		long endTime = System.currentTimeMillis();

		System.out.println("### EXEC TIME: " +  (endTime - startTime)/1000);
	}
}
