package com.debs.testutils;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonDataReaderOld {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();

	private JsonDataReaderOld() {
	}

	public static <T> Map<String, T> read(String fileName, TypeReference<Map<String, T>> typeRef) {

		String cacheKey = fileName + typeRef.getType();

		if (CACHE.containsKey(cacheKey)) {
			return (Map<String, T>) CACHE.get(cacheKey);
		}

		try (InputStream is = JsonDataReaderOld.class.getClassLoader().getResourceAsStream(fileName)) {

			if (is == null) {
				throw new RuntimeException("JSON not found: " + fileName);
			}

			Map<String, T> data = MAPPER.readValue(is, typeRef);

			CACHE.put(cacheKey, data);

			return data;

		} catch (Exception e) {
			throw new RuntimeException("Failed loading JSON: " + fileName, e);
		}
	}
}