package com.debs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {

	private static final Logger logger = LogManager.getLogger(ConfigReader.class);
	private static Properties prop;

	private static void loadProperties() throws Exception {
		try (InputStream in = ResourceLoader.getResource("config.properties")) {
			prop = new Properties();
			if (in == null) {
				throw new RuntimeException("config.properties not found in the classpath");
			}
			prop.load(in);
			logger.info("Configuration file loaded successfully");
		} catch (IOException e) {
			logger.info("Failed to load configuration file: " + e.getMessage());
			throw new RuntimeException("Failed to load configuation file");
		}

	}

	// initialize method to replace default property present in the config
	// properties with System properties
	public static void initialize()
	{
		try {
			loadProperties();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String key:prop.stringPropertyNames())
			
	
		{
		//override
		if(System.getProperties().containsKey(key))
		{
			prop.setProperty(key, System.getProperty(key));
		}
		
		
		//print
		logger.info("{}={}",key,prop.getProperty(key));
		}
		
	}

	public static String getProperty(String key) {
		String value = System.getProperty(key);
		if (value == null) {
			value = prop.getProperty(key);
		}

		return value;
	}

	public static String getUrl(String project) {
		String env = System.getProperty("test.environment") != null ? System.getProperty("test.environment") : "qa";
		String projectName = System.getProperty("project") != null ? System.getProperty("project") : project;
		System.out.println(projectName);
		return prop.getProperty(projectName + "." + env + ".url");
	}

}
