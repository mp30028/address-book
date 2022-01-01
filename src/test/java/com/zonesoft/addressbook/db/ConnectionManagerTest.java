package com.zonesoft.addressbook.db;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.zonesoft.addressbook.exceptions.AddressBookException;
import com.zonesoft.addressbook.properties.ApplicationProperties;

class ConnectionManagerTest {

	@Test
	void testGetConnection_WHEN_noExceptions_THEN_returnsDbConnection() {
		ApplicationProperties properties = new ApplicationProperties();
		ConnectionManager spiedConnectionManager = Mockito.spy(new ConnectionManager(properties));
		Connection mockConnection = Mockito.mock(Connection.class);
		assertNotNull(properties);
		assertNotNull(spiedConnectionManager);
		// Mock the static method and get the connection and check
		try (MockedStatic<DriverManager> mockedStaticDriverManager = Mockito.mockStatic(DriverManager.class)) {
			mockedStaticDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
					.thenReturn(mockConnection);
			Connection connection = spiedConnectionManager.getConnection();
			assertTrue(mockConnection.equals(connection));
		}
	}

	@Test
	void testGetConnection_WHEN_classNotFoundOccurs_THEN_addressbookExceptionIsThrown() {
		ApplicationProperties mockProperties = Mockito.mock(ApplicationProperties.class);
		ConnectionManager connectionManager = new ConnectionManager(mockProperties);
		assertNotNull(mockProperties);
		assertNotNull(connectionManager);
		when(mockProperties.getJdbcDriver()).thenReturn("thisIsNotAValid.class");
		assertThrows(AddressBookException.class, () -> connectionManager.getConnection());
	}
	
	@Test
	void testGetConnection_WHEN_sqlExceptionOccurs_THEN_addressbookExceptionIsThrown() {
		ApplicationProperties spiedProperties = spy(new ApplicationProperties());
		ConnectionManager connectionManager = new ConnectionManager(spiedProperties);
		assertNotNull(spiedProperties);
		assertNotNull(connectionManager);
		when(spiedProperties.getPassword()).thenReturn("thisIsNotAValidPassword");
		assertThrows(AddressBookException.class, () -> connectionManager.getConnection());
	}
	
	@Test
	void testGetConnection_WHEN_generalExceptionOccurs_THEN_addressbookExceptionIsThrown() {
		ApplicationProperties spiedProperties = spy(new ApplicationProperties());
		ConnectionManager connectionManager = new ConnectionManager(spiedProperties);
		assertNotNull(spiedProperties);
		assertNotNull(connectionManager);
		when(spiedProperties.getJdbcDriver()).thenThrow(new RuntimeException());
		assertThrows(AddressBookException.class, () -> connectionManager.getConnection());
	}
}
