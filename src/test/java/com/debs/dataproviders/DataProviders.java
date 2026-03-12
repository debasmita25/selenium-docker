package com.debs.dataproviders;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.debs.testutils.TestDataLoader;
import com.fasterxml.jackson.core.type.TypeReference;

public class DataProviders {

	

	@DataProvider(name = "jsonData",parallel=true)
	public static Object[][] provide(Method method) {

		String key = method.getName(); // validUser / invalidUser
		Class<?> testClass = method.getDeclaringClass();

		// ✔ Detect record type from test method parameter
		Class<?> recordType = method.getParameterTypes()[0];

		Map<String, ? extends List<?>> data =
		        TestDataLoader.load(testClass, recordType);

		List<?> list = data.get(key);

		if (list == null) {
			throw new RuntimeException("No data found for key: " + key);
		}

		return list.stream().map(row -> new Object[] { row }).toArray(Object[][]::new);
	}
}
