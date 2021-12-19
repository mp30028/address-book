package com.zonesoft.addressbook.db.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.entities.Person;
import static com.zonesoft.addressbook.db.sql.PersonSql.*;
import static com.zonesoft.addressbook.testing.data_generator.PersonDataGenerator.*;


class PersonDaoFetchByIdTest {
	
	ConnectionManager mockConnectionManager = Mockito.mock(ConnectionManager.class);
	Connection mockConnection = Mockito.mock(Connection.class);
	PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
	ResultSet mockResultSet = Mockito.mock(ResultSet.class);	
	PreparedStatement mockOtherNamesStatement = Mockito.mock(PreparedStatement.class);
	ResultSet mockOtherNamesResultset = Mockito.mock(ResultSet.class);
	PersonDao personDao; //Subject under test 
	
	@BeforeEach
	void beforeEach() throws SQLException {
		assertNotNull(mockConnectionManager);
		assertNotNull(mockConnection);
		assertNotNull(mockStatement);
		assertNotNull(mockResultSet);
		assertNotNull(mockOtherNamesStatement);
		assertNotNull(mockOtherNamesResultset);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(GET_BY_ID_SQL)).thenReturn(mockStatement);
		when(mockConnection.prepareStatement(GET_OTHER_NAMES_SQL)).thenReturn(mockOtherNamesStatement);
	}
	
	@Test
	void testFetchById_WHEN_personWithIdIsNotFound_THEN_returnsNull() throws SQLException {
		personDao = new PersonDao(mockConnectionManager);
		assertNotNull(personDao);	
		when(mockStatement.executeQuery()).thenReturn(null);
		assertNull(personDao.fetchById(100L));
	}

	
	
	private void mockoutResultsetGets(ResultSet mockResultSet, Person generatedPerson) throws SQLException {
		when(mockResultSet.getLong(FIELD_PERSON_ID)).thenReturn(generatedPerson.getPersonId());
		when(mockResultSet.getString(FIELD_FIRSTNAME)).thenReturn(generatedPerson.getFirstname());		
		when(mockResultSet.getString(FIELD_LASTNAME)).thenReturn(generatedPerson.getLastname());		
		when(mockResultSet.getString(FIELD_DATE_OF_BIRTH)).thenReturn(generatedPerson.getDateOfBirth().toString());			
	}

	
	@Test
	void testFetchById_WHEN_personWithIdIsFound_AND_hasNoOtherNames_THEN_returnsPersonWithOtherNamesNull() throws SQLException {
		Person generatedPerson = generatePerson(false);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true, false);
		mockoutResultsetGets(mockResultSet, generatedPerson);
		when(mockOtherNamesStatement.executeQuery()).thenReturn(null);			
		PersonDao personDao = new PersonDao(mockConnectionManager);
		assertNotNull(personDao);	
		Person fetchedPerson = personDao.fetchById(generatedPerson.getPersonId());
		assertNotNull(fetchedPerson);
			assertEquals(generatedPerson.getPersonId(), fetchedPerson.getPersonId());
			assertEquals(generatedPerson.getFirstname(), fetchedPerson.getFirstname());
			assertEquals(generatedPerson.getLastname(), fetchedPerson.getLastname());
			assertEquals(generatedPerson.getDateOfBirth(), fetchedPerson.getDateOfBirth());
			assertNull(fetchedPerson.getOtherNames());
	}
	
	@Test
	void testFetchById_WHEN_personWithIdIsFound_AND_hasOtherNames_THEN_returnsPersonWithOtherNamesNull() throws SQLException {
		Person generatedPerson = generatePerson(true);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true, false);
		mockoutResultsetGets(mockResultSet, generatedPerson);
		when(mockOtherNamesStatement.executeQuery()).thenReturn(mockOtherNamesResultset);
		when(mockOtherNamesResultset.next()).thenReturn(true,false);
		PersonDao personDao = new PersonDao(mockConnectionManager);
		assertNotNull(personDao);	
		Person fetchedPerson = personDao.fetchById(generatedPerson.getPersonId());
		assertNotNull(fetchedPerson);
			assertEquals(generatedPerson.getPersonId(), fetchedPerson.getPersonId());
			assertEquals(generatedPerson.getFirstname(), fetchedPerson.getFirstname());
			assertEquals(generatedPerson.getLastname(), fetchedPerson.getLastname());
			assertEquals(generatedPerson.getDateOfBirth(), fetchedPerson.getDateOfBirth());
			assertNotNull(fetchedPerson.getOtherNames());
	}
}
