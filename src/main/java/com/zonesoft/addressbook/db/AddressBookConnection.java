package com.zonesoft.addressbook.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;



public class AddressBookConnection {
	private static final Logger LOGGER =  Logger.getLogger(AddressBookConnection.class);
	private final Properties properties;

	
	public AddressBookConnection(Properties properties) {
		this.properties = properties;
	}
	
	public Connection getConnection() {
		try {
			String connectionString = "jdbc:mysql://" + properties.getHost() + ":" + Integer.toString(properties.getPort()) + "/" + properties.getDatabase();
			LOGGER.debug("FROM getConnection: connectionString = " + connectionString);
			return openConnection(connectionString, this.properties.getUsername(), this.properties.getPassword());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	
	private Connection openConnection(String connectionString, String username, String password) throws SQLException, ClassNotFoundException {
		Class.forName(properties.getJdbcDriver());
		LOGGER.debug("FROM openConnection: connectionString = " + connectionString);
		return DriverManager.getConnection(connectionString,username, password);
	}
}