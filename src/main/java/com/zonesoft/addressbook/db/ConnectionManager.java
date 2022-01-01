package com.zonesoft.addressbook.db;

import static com.zonesoft.addressbook.utils.Utils.sqlExceptionAsString;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.zonesoft.addressbook.exceptions.AddressBookException;
import com.zonesoft.addressbook.properties.ApplicationProperties;



public class ConnectionManager {
	private static final Logger LOGGER =  Logger.getLogger(ConnectionManager.class);
	private final ApplicationProperties properties;

	
	public ConnectionManager(ApplicationProperties properties) {
		this.properties = properties;
	}
	
	public Connection getConnection() {
		String connectionUrl = "jdbc:mysql://" + properties.getHost() + ":" + Integer.toString(properties.getPort()) + "/" + properties.getSchema();
		LOGGER.debug("FROM getConnection: connectionUrl = " + connectionUrl);
		return openConnection(connectionUrl, this.properties.getUsername(), this.properties.getPassword());
	}
	
	
	private Connection openConnection(String connectionUrl, String username, String password) {
		Connection connection = null;
		try {
			Class.forName(properties.getJdbcDriver());
			LOGGER.debug("FROM openConnection: connectionUrl = " + connectionUrl);
			connection = DriverManager.getConnection(connectionUrl, username, password);
		} catch (ClassNotFoundException e) {
			String message = "Failed to initialise JDBC driver class=" + properties.getJdbcDriver();
			LOGGER.error(message);
			e.printStackTrace();
			throw new AddressBookException(message, e);
		} catch (SQLException e) {
			String message = "Failed to open connection for connection url=" + connectionUrl + ". Validate the Url and also check the credentials are valid for username=" + username;
			LOGGER.error(message);
			LOGGER.error(sqlExceptionAsString(e));
			e.printStackTrace();
			throw new AddressBookException(message, e);
		} catch(Exception e) {
			String message= "An unexpected Exception occurred trying to open connection with url=" + connectionUrl + ", for username=" + username;
			LOGGER.error(message);
			e.printStackTrace();
			throw new AddressBookException(message, e);
		}
		return connection;
	}
}