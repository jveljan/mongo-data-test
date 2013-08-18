package com.joco.playground;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.joco.playground.model.Book;
import com.joco.playground.model.BookCount;
import com.joco.playground.mongo.MapReduceCommandCenter;
import com.joco.playground.mongo.MapReduceOutputHelper;
import com.joco.playground.test.MongoDbTestRunner;
import com.joco.playground.test.MongoInit;
import com.joco.playground.utils.JongoUtils;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;

@RunWith(MongoDbTestRunner.class)
@MongoInit("test-db.json.js")
public class BooksTest {
	private static final String DB_NAME = "test-db";
	private static final String COLLECTION_NAME = "books";

	private MapReduceCommandCenter mapReduce = new MapReduceCommandCenter();
	
	private MongoCollection booksCollection;

	@Before
	public void setUp() throws Exception {
		booksCollection = JongoUtils.getCollection(DB_NAME, COLLECTION_NAME);
	}

	@Test
	public void test() {
		Iterable<Book> books = booksCollection.find().as(Book.class);
		assertEquals(5, JongoUtils.countItems(books));
	}
	
	@Test
	public void test1() {
		Iterable<Book> javaBooks = booksCollection.find("{name:{$regex: 'Java*'}}").as(Book.class);
		assertEquals(1, JongoUtils.countItems(javaBooks));	
	}
	
	@Test
	public void testMapReduce() throws IOException {
		String path = "mapreduce/books";
		MapReduceCommand command = mapReduce.getMapReduce(path, booksCollection.getDBCollection());
		MapReduceOutput output = booksCollection.getDBCollection().mapReduce(command);
		
		MapReduceOutputHelper outputHelper = new MapReduceOutputHelper(output);
		Map<String, BookCount> map = outputHelper.toMap(String.class, BookCount.class);
		
		assertEquals(3, map.get("Small Books").getBooks());
		assertEquals(2, map.get("Big Books").getBooks());
	}
}
