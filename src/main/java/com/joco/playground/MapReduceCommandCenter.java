package com.joco.playground;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.joco.playground.test.tools.Utils;
import com.mongodb.DBCollection;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceCommand.OutputType;

public class MapReduceCommandCenter {
	private Map<String, MapReduceCommand> cache = new HashMap<String, MapReduceCommand>();
	
	public MapReduceCommandCenter() {
	}
	
	public MapReduceCommand getMapReduce(String path, DBCollection collection) throws IOException {
		if(cache.containsKey(path)) {
			return cache.get(path);
		}
		String map = read(path + "/map.js");
		String reduce = read(path + "/reduce.js");
		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce,
				null, OutputType.INLINE, null);
		cache.put(path, cmd);
		return cmd;
	}

	private String read(String file) throws IOException {
		InputStream is = null;
		try {
			is = Utils.getInputStreamFromClassPath(file);
			return Utils.readInputStreamToString(is, "UTF-8");
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
}
