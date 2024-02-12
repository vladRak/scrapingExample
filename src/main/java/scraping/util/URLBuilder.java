package scraping.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLBuilder {

	private String protocol;
	private String host;

	private String username;
	private int port;
	private String path;
	private String query;
	private String fragment;

	public String getProtocol() {
		return protocol;
	}

	public String getHost() {
		return host;
	}

	public String getUsername() {
		return username;
	}

	public int getPort() {
		return port;
	}

	public String getPath() {
		return path;
	}

	public String getQuery() {
		return query;
	}

	public String getFragment() {
		return fragment;
	}

	private URL toURL() throws MalformedURLException, URISyntaxException {
		if (port == 0)
			port = -1;

		return new URI(protocol, username, host, port, path, query, fragment).toURL();
	}

	private URLBuilder(Builder builder) {
		this.protocol = builder.protocol;
		this.host = builder.host;

		this.username = builder.username;
		this.port = builder.port;
		this.path = builder.path;
		this.query = builder.query;
		this.fragment = builder.fragment;
	}

	public static class Builder {

		private String protocol;
		private String host;

		private String username;
		private int port;
		private String path;
		private String query;
		private String fragment;

		public Builder(String protocol, String host) {
			this.protocol = protocol;
			this.host = host;
		}

		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder setPort(int port) {
			this.port = port;
			return this;
		}

		public Builder setPath(String path) {
			this.path = path;
			return this;
		}

		public Builder setQuery(String query) {
			this.query = query;
			return this;
		}

		public Builder setFragment(String fragment) {
			this.fragment = fragment;
			return this;
		}

		public URL build() throws MalformedURLException, URISyntaxException {
			return new URLBuilder(this).toURL();
		}

	}

}
