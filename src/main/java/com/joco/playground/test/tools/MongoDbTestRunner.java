package com.joco.playground.test.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDbTestRunner extends BlockJUnit4ClassRunner {

	public MongoDbTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	public void run(RunNotifier notifier) {
		TestClass clazz = getTestClass();
		MongoInit ann = clazz.getJavaClass().getAnnotation(MongoInit.class);
		if(ann == null) {
			throw new IllegalStateException("Please use " + MongoInit.class + " annotation on your test");
		}
		
		String file = ann.value();
		try {
			DbInitConfig cfg = Utils.readClasspathFiletoJSON(file, DbInitConfig.class);
			initMongoDb(cfg);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		super.run(notifier);
	}

	private void initMongoDb(DbInitConfig cfg) throws IOException {
		MongoClient mongo = new MongoClient(cfg.getHost());
		//DB db = mongo.getDB(cfg.getDatabase());
		//db.dropDatabase();
		
		DB db = mongo.getDB(cfg.getDatabase());
		Jongo jongo = new Jongo(db);
		for(Entry<String, String> c : cfg.getCollections().entrySet()) {
			MongoCollection collection = jongo.getCollection(c.getKey());
			collection.remove(); //clear collection ... 
			insertAll(collection, c.getValue());
		}
	}
	
	private void insertAll(MongoCollection collection, String classPathFile) throws IOException {
		InputStream is = Utils.getInputStreamFromClassPath(classPathFile);
		String str = Utils.readInputStreamToString(is, "UTF-8");
		ObjectMapper om = new ObjectMapper();
		List<Map<String, Object>> list = om.readValue(str, new TypeReference<List<Map<String, Object>>>(){});
		for(Map<String, Object> obj : list) {
			collection.insert(obj);
		}
		IOUtils.closeQuietly(is);
	}
		
}
