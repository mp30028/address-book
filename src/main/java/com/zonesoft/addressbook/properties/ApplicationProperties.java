package com.zonesoft.addressbook.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import org.apache.log4j.Logger;

import static com.zonesoft.addressbook.constants.ApplicationConstants.APPLICATION_PROPERTIES_FILE;

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
		LOGGER.info("address-book.db.host = " + properties.getProperty("address-book.db.host"));
		LOGGER.info("address-book.db.port = " + properties.getProperty("address-book.db.port"));
		LOGGER.info("address-book.db.schema = " + properties.getProperty("address-book.db.schema"));
		LOGGER.info("address-book.db.driver = " + properties.getProperty("address-book.db.driver"));
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
		return properties.getProperty("address-book.db.driver");
	}

	public String getHost() {
		return properties.getProperty("address-book.db.host");
	}

	public int getPort() {
		return Integer.parseInt(properties.getProperty("address-book.db.port"));
	}

	public String getUsername() {
		return properties.getProperty("address-book.db.username");
	}

	public String getPassword() {
		return properties.getProperty("address-book.db.password");
	}

	public String getSchema() {
		return properties.getProperty("address-book.db.schema");
	}
}
