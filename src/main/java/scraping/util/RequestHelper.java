package scraping.util;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class RequestHelper {

	private ExecutorService executor;

	public RequestHelper(ExecutorService executor) {
		this.executor = executor;
	}

	public String requestBodyWithHeaders(String url) {
		String result = CompletableFuture.supplyAsync(() -> {
			try {
				return this.getConnection(url).header("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36")
						.execute().body();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "ERROR";
		}, this.executor).join();

		return result;
	}

	public Connection getConnection(String url) {
		return Jsoup.connect(url).ignoreContentType(true);
	}
}
