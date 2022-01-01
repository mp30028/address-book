package com.zonesoft.addressbook.db.dao;

import static com.zonesoft.addressbook.utils.Utils.sqlExceptionAsString;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.exceptions.AddressBookException;

public class AbstractDao {
	private static final Logger LOGGER = Logger.getLogger(AbstractDao.class);
	private static  ConnectionManager connectionManager;
	
	public AbstractDao(ConnectionManager connectionManager) {
		super();
		AbstractDao.connectionManager = connectionManager;
	}
	
	
	protected Connection getCheckedConnection() {
		Connection connection = AbstractDao.connectionManager.getConnection();
		if (Objects.isNull(connection)) {
			String message = "Connection to database was null. Could not proceed with request";
			ConnectException exception = new ConnectException(message);
			LOGGER.error(message);
			throw new AddressBookException(exception);
		}
		return connection;
	}
	
	protected void cleanUpAndCloseConnection(Connection connection) {
		try {
			if (Objects.nonNull(connection)) {
				if (!connection.isClosed()) {
					connection.close();
					LOGGER.debug("Connection successfully closed");
				}
			}else {
				LOGGER.warn("Connection close abandoned as connection is null");
			}
		} catch (SQLException e) {
			String message = "Failed to close connection to db. Connection is in an unexpected state";
			LOGGER.error(message);
			LOGGER.error(sqlExceptionAsString(e));
			e.printStackTrace();
		}		
	}
	
}
