package com.zonesoft.addressbook.db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.db.dao.sql.other_name_types.FetchAll;
import com.zonesoft.addressbook.entities.OtherNameType;
import com.zonesoft.addressbook.exceptions.AddressBookException;

public class OtherNameTypeDao extends AbstractDao{
	private static final Logger LOGGER = Logger.getLogger(OtherNameTypeDao.class);	
	
	public OtherNameTypeDao(ConnectionManager connectionManager) {
		super(connectionManager);
	}
	
	
	public List<OtherNameType> fetchAll() {
		Connection connection = getCheckedConnection();
		List<OtherNameType> result = null;
		try {
			result = this.fetchAll(connection);
		}finally{
			cleanUpAndCloseConnection(connection);
		}
		return result;
	}
	
	public List<OtherNameType> fetchAll(Connection connection) {
		List<OtherNameType> otherNameTypes = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(FetchAll.SQL);
				while(resultset.next()) {
					if (Objects.isNull(otherNameTypes)) otherNameTypes = new ArrayList<OtherNameType>();
					OtherNameType otherNameType = new OtherNameType();
					otherNameType.setId(resultset.getLong(FetchAll.FIELDS.ID));
					otherNameType.setValue(resultset.getString(FetchAll.FIELDS.TYPE));
					otherNameTypes.add(otherNameType);
				}
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + FetchAll.SQL;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		}		
		return otherNameTypes;
	}
	

}
