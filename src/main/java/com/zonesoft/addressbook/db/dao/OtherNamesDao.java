package com.zonesoft.addressbook.db.dao;

import static com.zonesoft.addressbook.db.sql.OtherNameSql.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.entities.OtherName;
import com.zonesoft.addressbook.entities.OtherNameType;
import com.zonesoft.addressbook.exceptions.AddressBookException;

public class OtherNamesDao extends AbstractDao{
	private static final Logger LOGGER = Logger.getLogger(OtherNamesDao.class);	
	public OtherNamesDao(ConnectionManager connectionManager) {
		super(connectionManager);
	}
	
	
	public List<OtherName> fetchByPersonId(long personId) {
		Connection connection = getCheckedConnection();
		List<OtherName> result = null;
		try {
			result = this.fetchByPersonId(connection, personId);
		}finally{
			cleanUpAndCloseConnection(connection);
		}
		return result;
	}
	
	public List<OtherName> fetchByPersonId(Connection connection, long personId) {
		List<OtherName> otherNames = null;
		try {
			PreparedStatement statement = connection.prepareStatement(GET_OTHER_NAMES_SQL);
			statement.setLong(PARAMETER_INDEX_PERSON_ID_FOR_OTHER_NAMES_SQL, personId);
			ResultSet resultset = statement.executeQuery();
			if (Objects.nonNull(resultset)) {
				while(resultset.next()) {
					if (Objects.isNull(otherNames)) otherNames = new ArrayList<OtherName>();
					OtherName otherName = new OtherName();
					otherName.setId(resultset.getLong(FIELD_OTHER_NAME_ID));
					otherName.setValue(resultset.getString(FIELD_OTHER_NAME));
					otherName.setOtherNameType(new OtherNameType(resultset.getLong(FIELD_OTHER_NAME_TYPE_ID), resultset.getString(FIELD_OTHER_NAME_TYPE)));
					otherNames.add(otherName);
				}
			}
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + GET_OTHER_NAMES_SQL;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		}		
		return otherNames;
	}
	
	public OtherName fetchById(long otherNameId) {
		Connection connection = getCheckedConnection();
		OtherName otherName = null;
		try {
			PreparedStatement statement = connection.prepareStatement(GET_OTHER_NAME_SQL);
			statement.setLong(PARAMETER_INDEX_OTHER_NAME_ID_FOR_OTHER_NAME_SQL, otherNameId);
			ResultSet resultset = statement.executeQuery();
			if (Objects.nonNull(resultset)) {
					otherName = new OtherName();
					otherName.setId(resultset.getLong(FIELD_OTHER_NAME_ID));
					otherName.setValue(resultset.getString(FIELD_OTHER_NAME));
					otherName.setOtherNameType(new OtherNameType(resultset.getLong(FIELD_OTHER_NAME_TYPE_ID), resultset.getString(FIELD_OTHER_NAME_TYPE)));
			}
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + GET_OTHER_NAME_SQL + " with otherNameId =" + otherNameId;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		}	
		return otherName;
	}
}
