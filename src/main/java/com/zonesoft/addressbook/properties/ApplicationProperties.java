package com.zonesoft.addressbook.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.zonesoft.addressbook.exceptions.AddressBookException;

import static com.zonesoft.addressbook.constants.ApplicationConstants.*;

public class ApplicationProperties {
	private static final Logger LOGGER = Logger.getLogger(ApplicationProperties.class);
	private final Properties properties = new Properties();

	public ApplicationProperties()  {
		super();
		loadProperties(APPLICATION_PROPERTIES_FILE);
	}

	public ApplicationProperties(String filename) {
		super();
		loadProperties(filename);
	}

	private void loadProperties(String filename) {
		InputStream input = null;
		try {
			input = openPropertiesFile(filename);
			properties.load(input);
			LOGGER.info(DB_HOST + " = " + properties.getProperty(DB_HOST));
			LOGGER.info(DB_PORT + " = " + properties.getProperty(DB_PORT));
			LOGGER.info(DB_SCHEMA + " = " + properties.getProperty(DB_SCHEMA));
			LOGGER.info(DB_DRIVER + " = " + properties.getProperty(DB_DRIVER));
		} catch (IOException e) {
			String message = "Failed to load data from inputstream opened on file " + filename;
			LOGGER.error(message);
			e.printStackTrace();
			throw new AddressBookException(message, e);
		} finally {
			try {
				if (Objects.nonNull(input)) input.close();
			} catch (IOException e) {
				String message = "Failed to close inputstream opened on file " + filename;
				LOGGER.error(message);
				e.printStackTrace();
			}
		}
	}

	private InputStream openPropertiesFile(String propertiesFilename) {
		InputStream inputStream = ApplicationProperties.class.getClassLoader().getResourceAsStream(propertiesFilename);
		if (Objects.isNull(inputStream)) {
			String fileNotFoundErrorMessage = "Error trying to read " + propertiesFilename
					+ ". Check this file is in the classpath";
			LOGGER.error(fileNotFoundErrorMessage);
			FileNotFoundException exception = new FileNotFoundException(fileNotFoundErrorMessage);
			exception.printStackTrace();
			throw new AddressBookException(exception);
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
