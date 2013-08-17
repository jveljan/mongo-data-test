package com.joco.playground;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;

/**
 * Init mongo collection from json file in class path,
 * WARNING: will remove all existing item in collection and then insert everything from json file
 * 
 * @author joco
 */
public class CollectionFromJson {
	
	public static final String FILE_SUFFIX = "json.js";
	
	public static void init(DB db, String collectionName) throws IOException {
		Jongo jongo = new Jongo(db);
		MongoCollection collection = jongo.getCollection(collectionName);
		collection.remove();
		insertAll(collection, db.getName(), collectionName, FILE_SUFFIX);
	}
	
	private static void insertAll(MongoCollection collection, String dbName,
			String collectionName, String fileSuffix) throws IOException {
		String path = String.format("%s/%s.%s", dbName, collectionName, fileSuffix);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		
		String str = readInputStreamAsString(is, "UTF-8");
		
		ObjectMapper om = new ObjectMapper();
		List<Map<String, Object>> list = om.readValue(str, new TypeReference<List<Map<String, Object>>>(){});
		
		for(Map<String, Object> obj : list) {
			collection.insert(obj);
		}
		
		//collection.insert(str);
		//System.out.println(list);
	}
	
	private static String readInputStreamAsString(InputStream is, String enc) throws IOException {
		StringWriter sw = new StringWriter();
		IOUtils.copy(is, sw, enc);
		return sw.toString();
	}

}
