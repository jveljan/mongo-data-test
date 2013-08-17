package com.joco.playground.test.tools;

import java.util.Map;

public class DbInitConfig {
	private String host;
	private String database;
	private Map<String, String> collections;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public Map<String, String> getCollections() {
		return collections;
	}
	public void setCollections(Map<String, String> collections) {
		this.collections = collections;
	}
}
