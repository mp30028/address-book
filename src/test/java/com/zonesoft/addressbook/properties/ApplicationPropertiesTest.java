package com.zonesoft.addressbook.properties;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.zonesoft.addressbook.exceptions.AddressBookException;

class ApplicationPropertiesTest {

	@Test
	void testAppProps_WHEN_propertiesFileIsInClasspathAndValid_THEN_propertiesAreLoaded()
			throws FileNotFoundException, IOException {
		ApplicationProperties applicationProperties = new ApplicationProperties();
		String expectedHost = "localhost";
		int expectedPort = 3306;
		String expectedSchema = "addressbook";
		String expectedDriver = "com.mysql.cj.jdbc.Driver";

		assertNotNull(applicationProperties);
		assertEquals(expectedHost, applicationProperties.getHost());
		assertEquals(expectedPort, applicationProperties.getPort());
		assertEquals(expectedSchema, applicationProperties.getSchema());
		assertEquals(expectedDriver, applicationProperties.getJdbcDriver());
	}

	@Test
	void testAppProps_WHEN_propertiesFileNotFound_THEN_throwsAddressBookException() {
		String nonExistentFile = "thisfiledoesnotexist.properties";
		assertThrows(AddressBookException.class, () -> new ApplicationProperties(nonExistentFile));
	}

}
