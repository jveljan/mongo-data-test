package com.joco.playground;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.joco.playground.model.Book;
import com.joco.playground.model.base.BaseToJsonStringObject;
import com.joco.playground.mongo.MapReduceCommandCenter;
import com.joco.playground.mongo.MapReduceOutputHelper;
import com.joco.playground.test.tools.JongoTestUtils;
import com.joco.playground.test.tools.MongoDbTestRunner;
import com.joco.playground.test.tools.MongoInit;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;

@RunWith(value = MongoDbTestRunner.class)
@MongoInit("test-db.json.js")
public class BooksTest {
	private static final String DB_NAME = "test-db";
	private static final String COLLECTION_NAME = "books";

	private MapReduceCommandCenter mapReduce = new MapReduceCommandCenter();
	
	private MongoCollection booksCollection;

	@Before
	public void setUp() throws Exception {
		booksCollection = JongoTestUtils.getCollection(DB_NAME, COLLECTION_NAME);
	}
	
	private int count(Iterable<?> iterable) {
		int r = 0;
		Iterator<?> it = iterable.iterator();
		while(it.hasNext()) {
			it.next(); r++;
		}
		return r;
	}

	@Test
	public void test() {
		Iterable<Book> books = booksCollection.find().as(Book.class);
		assertEquals(5, count(books));
	}

	

	@Test
	public void test1() {
		Iterable<Book> javaBooks = booksCollection.find("{name:{$regex: 'Java*'}}").as(Book.class);
		assertEquals(1, count(javaBooks));	
	}
	
	public static class BookCount extends BaseToJsonStringObject {
		private int books;
		public int getBooks() {
			return books;
		}
		public void setBooks(int books) {
			this.books = books;
		}
	}
	
	@Test
	public void testMapReduce() throws IOException {
		String path = "test-db/fn/mapreduce/books";
		MapReduceCommand command = mapReduce.getMapReduce(path, booksCollection.getDBCollection());
		MapReduceOutput output = booksCollection.getDBCollection().mapReduce(command);
		
		MapReduceOutputHelper outputHelper = new MapReduceOutputHelper(output);
		Map<String, BookCount> map = outputHelper.toMap(String.class, BookCount.class);
		
		System.out.println(map);
	}
}
