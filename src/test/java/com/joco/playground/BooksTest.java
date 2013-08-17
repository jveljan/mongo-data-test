package com.joco.playground;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value = MongoDbTestRunner.class)
@MongoInit("test-db.json.js")
public class BooksTest {
	private static final String DB_NAME = "test-db";
	private static final String COLLECTION_NAME = "books";

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
}
