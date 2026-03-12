package com.debs.testutils;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.debs.utils.ResourceLoader;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestDataLoader {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private TestDataLoader() {
	}

	public static <T> Map<String, List<T>> load(Class<?> testClass, Class<T> recordType) {

		String fileName = "test-data/" + testClass.getSimpleName().toLowerCase() + ".json";

		try (InputStream is = ResourceLoader.getResource(fileName)) {

			if (is == null) {
				throw new RuntimeException("JSON file not found: " + fileName);
			}

			
			//This snippet is building runtime type metadata so Jackson can correctly deserialize nested generic structures.
			//Step 1: Build List<T> Type
			JavaType listType = MAPPER.getTypeFactory().constructCollectionType(List.class, recordType);
            //Step 2: Build Map<String, List<T>>
			JavaType mapType = MAPPER.getTypeFactory().constructMapType(LinkedHashMap.class,
					MAPPER.getTypeFactory().constructType(String.class), listType);

			return MAPPER.readValue(is, mapType);

		} catch (Exception e) {
			throw new RuntimeException("Failed loading JSON data", e);
		}
	}
}
