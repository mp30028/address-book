package com.zonesoft.addressbook.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.zonesoft.addressbook.properties.ApplicationProperties;



public class ConnectionManager {
	private static final Logger LOGGER =  Logger.getLogger(ConnectionManager.class);
	private final ApplicationProperties properties;

	
	public ConnectionManager(ApplicationProperties properties) {
		this.properties = properties;
	}
	
	public Connection getConnection() {
		try {
			String connectionUrl = "jdbc:mysql://" + properties.getHost() + ":" + Integer.toString(properties.getPort()) + "/" + properties.getSchema();
			LOGGER.debug("FROM getConnection: connectionUrl = " + connectionUrl);
			return openConnection(connectionUrl, this.properties.getUsername(), this.properties.getPassword());
		} catch (SQLException sqlEx) {
			// TODO Auto-generated catch block
			sqlEx.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	private Connection openConnection(String connectionUrl, String username, String password) throws SQLException, ClassNotFoundException {
		Class.forName(properties.getJdbcDriver());
		LOGGER.debug("FROM openConnection: connectionUrl = " + connectionUrl);
		return DriverManager.getConnection(connectionUrl,username, password);
	}
}