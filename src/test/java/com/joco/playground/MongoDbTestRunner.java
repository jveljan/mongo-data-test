package com.joco.playground;

import java.io.IOException;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

public class MongoDbTestRunner extends BlockJUnit4ClassRunner {

	public MongoDbTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	public void run(RunNotifier notifier) {
		System.out.println("MongoDbTestRunner.run()");
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

	private void initMongoDb(DbInitConfig cfg) {
		System.out.println("MongoDbTestRunner.initMongoDb()");
	}
	
}
