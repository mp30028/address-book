package com.zonesoft.addressbook.db;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class AddressBookConnectionTest {
	Properties properties = new Properties();
	AddressBookConnection addressBookConnection = new AddressBookConnection(properties);
	AddressBookConnection spyAddressBookConnection = Mockito.spy(addressBookConnection);
	Connection mockConnection = Mockito.mock(Connection.class);
	
	@Test
	void testGetConnection() throws SQLException, ClassNotFoundException {
		assertNotNull(properties);
		assertNotNull(addressBookConnection);
		assertNotNull(spyAddressBookConnection);
		// Mock the static method and get the connection and check
        try (MockedStatic<DriverManager> mockDriverManager = Mockito.mockStatic(DriverManager.class)) {
            mockDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                   .thenReturn(mockConnection);
            Connection connection = spyAddressBookConnection.getConnection();
            assertTrue(mockConnection.equals(connection));
        }
	}
}
