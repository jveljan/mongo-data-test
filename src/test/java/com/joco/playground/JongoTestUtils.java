package com.joco.playground;

import java.io.PrintStream;
import java.net.UnknownHostException;

import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class JongoTestUtils {
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
}
