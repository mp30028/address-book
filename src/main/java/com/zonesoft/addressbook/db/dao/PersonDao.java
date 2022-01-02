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
import com.zonesoft.addressbook.db.dao.sql.person.Add;
import com.zonesoft.addressbook.db.dao.sql.person.Delete;
import com.zonesoft.addressbook.db.dao.sql.person.FetchAll;
import com.zonesoft.addressbook.db.dao.sql.person.FetchById;
import com.zonesoft.addressbook.db.dao.sql.person.Update;
import com.zonesoft.addressbook.entities.OtherName;
import com.zonesoft.addressbook.entities.Person;
import com.zonesoft.addressbook.exceptions.AddressBookException;


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
			PreparedStatement statement = connection.prepareStatement(FetchById.SQL);
			statement.setLong(FetchById.PARAMETERS.PERSON_ID, personId);
			ResultSet resultset = statement.executeQuery();
			if(Objects.nonNull(resultset)) {
				if(resultset.next()) {
					person = unpackResultset(resultset);
					fetchAndUnpackOtherNames(connection, person);
				}
			}
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + FetchById.SQL;
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
			ResultSet resultset = statement.executeQuery(FetchAll.SQL);
			while(resultset.next()) {
				if (Objects.isNull(persons)) persons = new ArrayList<Person>();
				Person person = unpackResultset(resultset);
				fetchAndUnpackOtherNames(connection, person);
				persons.add(person);
			}
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + FetchAll.SQL;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		} finally {
			cleanUpAndCloseConnection(connection);
		}
		return persons;
	}
	
	public Person update(Person person) {
		Connection connection = getCheckedConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(Update.SQL);
			statement.setLong(Update.PARAMETERS.PERSON_ID, person.getId());
			statement.setString(Update.PARAMETERS.FIRSTNAME, person.getFirstname());
			statement.setString(Update.PARAMETERS.LASTNAME, person.getLastname());
			statement.setString(Update.PARAMETERS.BIRTH_DATE, convertLocalDateToString(person.getDateOfBirth()));
			int recordsAffected = statement.executeUpdate();
			if (recordsAffected != 1) LOGGER.warn("Updated of Person record resulted in affecting more than one record. Person =  \n " + person.toJsonString() + "\n");
			updateOtherNames(connection, person);
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + Update.SQL;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		} finally {
			cleanUpAndCloseConnection(connection);
		}
		return person;
	}
	
	private void updateOtherNames(Connection connection, Person person) {
		deleteRemovedOtherNames(connection, person);
		updateChangedOtherNames(connection, person);
		addNewOtherNames(connection,person);
	}

	private void addNewOtherNames(Connection connection, Person person) {
		if (Objects.nonNull(person.getOtherNames())) {
			for(OtherName personsOtherName: person.getOtherNames()) {
				if (personsOtherName.getId() <= 0) {
					otherNamesDao.add(connection, personsOtherName);
				}
			}
		}
	}

	private void updateChangedOtherNames(Connection connection, Person person) {
		if (Objects.nonNull(person.getOtherNames())) {
			for(OtherName personsOtherName: person.getOtherNames()) {
				otherNamesDao.update(connection, personsOtherName);
			}
		}
	}

	private void deleteRemovedOtherNames(Connection connection, Person person) {
		List<OtherName> fetchedOtherNames = otherNamesDao.fetchByPersonId(connection, person.getId());
		if (Objects.nonNull(fetchedOtherNames)) {
			for(OtherName fetchedOtherName: fetchedOtherNames) {
				if (! inPersonsOtherNames(fetchedOtherName, person)) {
					otherNamesDao.delete(connection, fetchedOtherName.getId());
				}
			}
		}
	}
	
	private void deletePersonsAllOtherNames(Connection connection, long personId) {
		otherNamesDao.deleteByPersonId(connection, personId);
	}
	
	private boolean inPersonsOtherNames(OtherName otherName, Person person) {
		if (Objects.nonNull(person.getOtherNames())) {
			for(OtherName personsOtherName: person.getOtherNames()) {
				if (personsOtherName.getId() == otherName.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public Person add(Person person) {
		Connection connection = getCheckedConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(Add.SQL, Statement.RETURN_GENERATED_KEYS);
			statement.setString(Add.PARAMETERS.FIRSTNAME, person.getFirstname());
			statement.setString(Add.PARAMETERS.LASTNAME, person.getLastname());
			statement.setString(Add.PARAMETERS.BIRTH_DATE, convertLocalDateToString(person.getDateOfBirth()));
			int recordsAffected = statement.executeUpdate();
			if (recordsAffected != 1) LOGGER.warn("Adding of Person record resulted in affecting more than one record. Person =  \n " + person.toJsonString() + "\n");
			ResultSet generatedKeys =  statement.getGeneratedKeys();
			if(generatedKeys.next()) {
				long newPersonId = generatedKeys.getLong(1);
				person.setId(newPersonId);
			}else {
				LOGGER.warn("An unexpected condition occurred during adding new Person record as no generated key was returned. Person =  \n " + person.toJsonString() + "\n");
			}
			if(generatedKeys.next()) LOGGER.warn("An unexpected condition occurred during adding new Person record as more than one generated key was returned. Person =  \n " + person.toJsonString() + "\n");
			addNewOtherNames(connection,person);
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + Add.SQL + "\nPerson = " + person.toJsonString() + "\n";
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		} finally {
			cleanUpAndCloseConnection(connection);
		}
		return person;
	}
	
	public Person delete(Person person) {
		Connection connection = getCheckedConnection();
		this.deletePersonsAllOtherNames(connection, person.getId());
		this.delete(connection, person.getId());
		return person;
	}
	
	public void delete(Connection connection, long personId) {
		try {
			PreparedStatement statement = connection.prepareStatement(Delete.SQL);
			statement.setLong(Delete.PARAMETERS.PERSON_ID, personId);
			int recordsAffected = statement.executeUpdate();
			if (recordsAffected != 1) LOGGER.warn("Deleting Person resulted in affecting more than one record where PersonId =  " + personId);
		} catch (SQLException e) {
			String message = "SQL Exception trying to execute SQL=" + Delete.SQL + " with PersonId =  " + personId;
			LOGGER.error(message);
			throw new AddressBookException(message, e);
		}
	}
	
	private void fetchAndUnpackOtherNames(Connection connection, Person person) throws SQLException {
		List<OtherName> otherNames = otherNamesDao.fetchByPersonId(connection, person.getId());
		if (Objects.nonNull(otherNames)) {
			for (OtherName otherName : otherNames) {
				otherName.setPerson(person);
			}
		}
		person.setOtherNames(otherNames);
	}
		
	private Person unpackResultset(ResultSet resultset) throws SQLException {
		Person person = new Person();
		person.setId(resultset.getLong(FetchAll.FIELDS.PERSON_ID));
		person.setFirstname(resultset.getString(FetchAll.FIELDS.FIRSTNAME));
		person.setLastname(resultset.getString(FetchAll.FIELDS.LASTNAME));
		person.setDateOfBirth(convertStringToLocalDate(resultset.getString(FetchAll.FIELDS.DATE_OF_BIRTH)));
		return person;
	}
	
}
