package com.zonesoft.addressbook.db.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.db.dao.sql.other_names.Add;
import com.zonesoft.addressbook.db.dao.sql.other_names.Delete;
import com.zonesoft.addressbook.db.dao.sql.other_names.FetchById;
import com.zonesoft.addressbook.db.dao.sql.other_names.FetchByPersonId;
import com.zonesoft.addressbook.db.dao.sql.other_names.Update;
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
			PreparedStatement statement = connection.prepareStatement(FetchByPersonId.SQL);
			statement.setLong(FetchByPersonId.PARAMETERS.PERSON_ID, personId);
			ResultSet resultset = statement.executeQuery();
			if (Objects.nonNull(resultset)) {
				while(resultset.next()) {
					if (Objects.isNull(otherNames)) otherNames = new ArrayList<OtherName>();
					OtherName otherName = new OtherName();
					otherName.setId(resultset.getLong(FetchByPersonId.FIELDS.OTHER_NAME_ID));
					otherName.setValue(resultset.getString(FetchByPersonId.FIELDS.OTHER_NAME));
					otherName.setOtherNameType(new OtherNameType(resultset.getLong(FetchByPersonId.FIELDS.OTHER_NAME_TYPE_ID), resultset.getString(FetchByPersonId.FIELDS.OTHER_NAME_TYPE)));
					otherNames.add(otherName);
				}
			}
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + FetchByPersonId.SQL;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		}		
		return otherNames;
	}
	
	public OtherName fetchById(long otherNameId) {
		Connection connection = getCheckedConnection();
		OtherName otherName = null;
		try {
			PreparedStatement statement = connection.prepareStatement(FetchById.SQL);
			statement.setLong(FetchById.PARAMETERS.OTHER_NAME_ID , otherNameId);
			ResultSet resultset = statement.executeQuery();
			if (Objects.nonNull(resultset)) {
					otherName = new OtherName();
					otherName.setId(resultset.getLong(FetchById.FIELDS.OTHER_NAME_ID));
					otherName.setValue(resultset.getString(FetchById.FIELDS.OTHER_NAME));
					otherName.setOtherNameType(new OtherNameType(resultset.getLong(FetchById.FIELDS.OTHER_NAME_TYPE_ID), resultset.getString(FetchById.FIELDS.OTHER_NAME_TYPE)));
			}
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + FetchById.SQL + " with otherNameId=" + otherNameId;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		}	
		return otherName;
	}


	public void delete(Connection connection, long id) {
		try {
			PreparedStatement statement = connection.prepareStatement(Delete.SQL);
			statement.setLong(Delete.PARAMETERS.OTHER_NAME_ID, id);
			int recordsAffected = statement.executeUpdate();
			if (recordsAffected != 1) LOGGER.warn("Delete of OtherName record resulted in affecting more than one record. OtherName.id=" + id );
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + Delete.SQL + " with OtherName.id=" + id;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		}
	}


	public void update(Connection connection, OtherName otherName) {
		try {
			PreparedStatement statement = connection.prepareStatement(Update.SQL);
			statement.setString(Update.PARAMETERS.OTHER_NAME, otherName.getValue());
			statement.setLong(Update.PARAMETERS.OTHER_NAME_TYPE_ID, otherName.getOtherNameType().getId());
			statement.setLong(Update.PARAMETERS.OTHER_NAME_ID, otherName.getId());
			int recordsAffected = statement.executeUpdate();
			if (recordsAffected != 1) LOGGER.warn("Updated of OtherName record resulted in affecting more than one record. OtherName =  \n " + otherName.toJsonString() + "\n");
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + Update.SQL + " with OtherName=" + otherName.toJsonString();
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		}
	}


	public void add(Connection connection, OtherName otherName) {
		try {
			PreparedStatement statement = connection.prepareStatement(Add.SQL);
			statement.setString(Add.PARAMETERS.OTHER_NAME, otherName.getValue());
			statement.setLong(Add.PARAMETERS.OTHER_NAME_TYPE_ID, otherName.getOtherNameType().getId());
			statement.setLong(Add.PARAMETERS.PERSON_ID, otherName.getPerson().getId());
			int recordsAffected = statement.executeUpdate();
			if (recordsAffected != 1) LOGGER.warn("Adding new OtherName record resulted in affecting more than one record. OtherName =  \n " + otherName.toJsonString() + "\n");
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + Add.SQL + " with OtherName=" + otherName.toJsonString();
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		}
	}
}
