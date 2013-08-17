package com.joco.playground;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@RunWith(value=MongoDbTestRunner.class)
@MongoInit("test-db.json.js")
public class BooksTest {
	private static final String DB_NAME = "db1";
	private static final String COLLECTION_NAME = "books";

	private MongoCollection books;

	@Before
	public void setUp() throws Exception {
		MongoClient mongoClient = new MongoClient("localhost");
		DB db = mongoClient.getDB(DB_NAME);
		//CollectionFromJson.init(db, COLLECTION_NAME);

		Jongo jongo = new Jongo(db);
		books = jongo.getCollection(COLLECTION_NAME);
	}

	ResultHandler<String> jsonStringResultHandler = new ResultHandler<String>() {
		public String map(DBObject result) {
			return result.toString();
		}
	};

	@Test
	public void test() {
		//fail("Not yet implemented");
		Iterable<String> it = books.find().map(jsonStringResultHandler);
		for(String json : it) {
			System.out.println(json);
		}
	}
}
