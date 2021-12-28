package com.zonesoft.addressbook.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.entities.OtherName;
import com.zonesoft.addressbook.entities.Person;
import com.zonesoft.addressbook.exceptions.AddressBookException;

import static com.zonesoft.addressbook.db.sql.PersonSql.*;
import static com.zonesoft.addressbook.utils.Utils.*;

public class PersonDao extends AbstractDao {
	private static final Logger LOGGER = Logger.getLogger(PersonDao.class);
	private static OtherNamesDao otherNamesDao;
	
	public PersonDao(ConnectionManager connectionManager) {
		super(connectionManager);
		otherNamesDao  = new OtherNamesDao(connectionManager);
	}
	
	public Person fetchById(long personId) {
		Connection connection = getCheckedConnection();
		Person person = null;
		try {
			PreparedStatement statement = connection.prepareStatement(GET_BY_ID_SQL);
			statement.setLong(PARAMETER_INDEX_PERSON_ID_FOR_GET_BY_ID_SQL, personId);
			ResultSet resultset = statement.executeQuery();
			if(Objects.nonNull(resultset)) {
				if(resultset.next()) {
					person = unmarshallResultset(resultset);
					fetchAndUpdateOtherNames(connection, person);
				}
			}
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + GET_BY_ID_SQL;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		} finally {
			cleanUpAndCloseConnection(connection);
		}
		return person;
	}

	public List<Person> fetchAll(){
		Connection connection = getCheckedConnection();
		List<Person> persons = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(GET_ALL_SQL);
			while(resultset.next()) {
				if (Objects.isNull(persons)) persons = new ArrayList<Person>();
				Person person = unmarshallResultset(resultset);
				fetchAndUpdateOtherNames(connection, person);
				persons.add(person);
			}
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + GET_ALL_SQL;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		} finally {
			cleanUpAndCloseConnection(connection);
		}
		return persons;
	}
	
	public Person update(Person person) {
		// TODO 
		return null;
	}
	
	public Person add(Person person) {
		// TODO 
		return null;
	}
	
	public Person delete(Person person) {
		// TODO 
		return null;
	}
	
	public Person deleteById(long id) {
		// TODO 
		return null;
	}
	
	private void fetchAndUpdateOtherNames(Connection connection, Person person) throws SQLException {
		List<OtherName> otherNames = otherNamesDao.fetchByPersonId(connection, person.getId());
		if (Objects.nonNull(otherNames)) {
			for (OtherName otherName : otherNames) {
				otherName.setPerson(person);
			}
		}
		person.setOtherNames(otherNames);
	}
		
	private Person unmarshallResultset(ResultSet resultset) throws SQLException {
		Person person = new Person();
		person.setId(resultset.getLong(FIELD_PERSON_ID));
		person.setFirstname(resultset.getString(FIELD_FIRSTNAME));
		person.setLastname(resultset.getString(FIELD_LASTNAME));
		person.setDateOfBirth(convertToLocalDate(resultset.getString(FIELD_DATE_OF_BIRTH)));
		return person;
	}
	
}
