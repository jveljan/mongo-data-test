package com.joco.playground;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;

public class MapReduceOutputHelper {

	private MapReduceOutput mapReduceOutput;

	public MapReduceOutputHelper(MapReduceOutput output) {
		this.mapReduceOutput = output;
	}
	
	public <K, V> Map<K, V> toMap(Class<K> keyClass, Class<V> valueClass) {
		Map<K, V> map = new HashMap<K, V>();
		Iterable<DBObject> list = mapReduceOutput.results();
		
		for(DBObject o : list) {	
			Object key = o.get("_id");
			Object value = o.get("value");
			map.put(conv(key, keyClass), conv(value, valueClass));
		}
		return map;
	}
	
	private <T> T conv(Object obj, Class<T> clazz) {
		if (obj instanceof DBObject && !clazz.equals(DBObject.class)) {
			DBObject dbObj = (DBObject) obj;
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(dbObj.toMap(), clazz);
		}
		return clazz.cast(obj);
	}
}

