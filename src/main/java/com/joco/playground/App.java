package com.joco.playground;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws Exception {
		
		MongoClient mongoClient = new MongoClient("localhost");
		DB db = mongoClient.getDB("books");
		
		Jongo jongo = new Jongo(db);
		
		MongoCollection books = jongo.getCollection("books");
		
//		ResultHandler<String> jsonStringResultHandler = new ResultHandler<String>() {
//			public String map(DBObject result) {
//				return result.toString();
//			}
//		};
//		
//		Iterable<String> arr = books.find().map(jsonStringResultHandler);
//		for(String s : arr) {
//			System.out.println(s);
//		}
//
		
		//insertBook(books, "Joco Kniga", 250);
		
		Iterable<Book> booksResults = books.find().as(Book.class);
		for(Book b : booksResults) {
			System.out.println(b);
		}
		books.remove("{name: 'Joco Kniga'}");
		
		//BasicDBObject joco = new BasicDBObject("name", "joco");
		//BasicDBObject cmd = new BasicDBObject("insert", joco);
		//CommandResult r = db.command(cmd);
		//System.out.println(r);
		
		
		
//		List<String> dbs = mongoClient.getDatabaseNames();
//		for(String s : dbs) {
//			System.out.println(s);
//		}
//		new MapReduceCommand(inputCollection, map, reduce, outputCollection, type, query)
	}

	private static void insertBook(MongoCollection books, String name, int pages) {
		books.insert(new Book(name, pages));
	}
}
