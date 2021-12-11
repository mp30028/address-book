package com.zonesoft.addressbook.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import org.apache.log4j.Logger;

import static com.zonesoft.addressbook.constants.ApplicationConstants.*;

public class ApplicationProperties {
	private static final Logger LOGGER = Logger.getLogger(ApplicationProperties.class);
	private final Properties properties = new Properties();

	public ApplicationProperties() throws IOException, FileNotFoundException {
		super();
		loadProperties(APPLICATION_PROPERTIES_FILE);
	}

	public ApplicationProperties(String filename) throws IOException, FileNotFoundException {
		super();
		loadProperties(filename);
	}

	private void loadProperties(String filename) throws IOException, FileNotFoundException {
		InputStream input = null;
		input = openPropertiesFile(filename);
		properties.load(input);
		LOGGER.info(DB_HOST + " = " + properties.getProperty(DB_HOST));
		LOGGER.info(DB_PORT + " = " + properties.getProperty(DB_PORT));
		LOGGER.info(DB_SCHEMA + " = " + properties.getProperty(DB_SCHEMA));
		LOGGER.info(DB_DRIVER + " = " + properties.getProperty(DB_DRIVER));
		input.close();
	}

	private InputStream openPropertiesFile(String propertiesFilename) throws FileNotFoundException {
		InputStream inputStream = ApplicationProperties.class.getClassLoader().getResourceAsStream(propertiesFilename);
		if (Objects.isNull(inputStream)) {
			String fileNotFoundErrorMessage = "Error trying to read " + propertiesFilename
					+ ". Check this file is in the classpath";
			LOGGER.error(fileNotFoundErrorMessage);
			throw new FileNotFoundException(fileNotFoundErrorMessage);
		}
		return inputStream;
	}

	public String getJdbcDriver() {
		return properties.getProperty(DB_DRIVER);
	}

	public String getHost() {
		return properties.getProperty(DB_HOST);
	}

	public int getPort() {
		return Integer.parseInt(properties.getProperty(DB_PORT));
	}

	public String getUsername() {
		return properties.getProperty(DB_USERNAME);
	}

	public String getPassword() {
		return properties.getProperty(DB_PASSWORD);
	}

	public String getSchema() {
		return properties.getProperty(DB_SCHEMA);
	}
}
