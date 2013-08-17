package com.joco.playground;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	private static final ObjectMapper mapper = new ObjectMapper();

	
	public static <T> T readClasspathFiletoJSON(String file, Class<T> clazz) throws IOException {
		InputStream is = getInputStreamFromClassPath(file);
		try {
			String str = readInputStreamToString(is, "UTF-8");
			return mapper.readValue(str, clazz);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	public static InputStream getInputStreamFromClassPath(String file) throws FileNotFoundException {
		file = file.startsWith("/") ? file : "/" + file;
		InputStream is = Utils.class.getResourceAsStream(file);
		if(is == null) {
			throw new FileNotFoundException(file);
		}
		return is;
	}


	public static String readInputStreamToString(InputStream is, String encoding) throws IOException {
		StringWriter sw = new StringWriter();
		IOUtils.copy(is, sw, encoding);
		return sw.toString();
	}

}
