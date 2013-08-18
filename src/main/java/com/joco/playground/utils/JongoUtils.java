package com.joco.playground.utils;

import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.Iterator;

import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class JongoUtils {
	public static MongoCollection getCollection(String dbName,
			String collectionName) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient("localhost");
		DB db = mongoClient.getDB(dbName);
		Jongo jongo = new Jongo(db);
		return jongo.getCollection(collectionName);
	}
	
	private static ResultHandler<String> jsonStringResultHandler = new ResultHandler<String>() {
		public String map(DBObject result) {
			return result.toString();
		}
	};
	
	public static void printResult(Find find, PrintStream ps) {
		Iterable<String> it = find.map(jsonStringResultHandler);
		for(String json : it) {
			ps.println(json);
		}
	}
	
	public static int countItems(Iterable<?> iterable) {
		int r = 0;
		Iterator<?> it = iterable.iterator();
		while(it.hasNext()) {
			it.next(); r++;
		}
		return r;
	}
}
