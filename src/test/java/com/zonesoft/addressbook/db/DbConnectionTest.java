package com.zonesoft.addressbook.db;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.zonesoft.addressbook.properties.ApplicationProperties;

class DbConnectionTest {

	@Test
	void testGetConnection_WHEN_propertiesFileInClasspath_THEN_returnsDbConnection()
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		ApplicationProperties properties = new ApplicationProperties();
		DbConnection spyAddressBookConnection = Mockito.spy(new DbConnection(properties));
		Connection mockConnection = Mockito.mock(Connection.class);
		assertNotNull(properties);
		assertNotNull(spyAddressBookConnection);
		// Mock the static method and get the connection and check
		try (MockedStatic<DriverManager> mockedStaticDriverManager = Mockito.mockStatic(DriverManager.class)) {
			mockedStaticDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
					.thenReturn(mockConnection);
			Connection connection = spyAddressBookConnection.getConnection();
			assertTrue(mockConnection.equals(connection));
		}
	}

}
