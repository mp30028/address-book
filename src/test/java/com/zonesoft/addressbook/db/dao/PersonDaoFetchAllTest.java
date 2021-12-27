package com.zonesoft.addressbook.db.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.entities.Person;
import static com.zonesoft.addressbook.db.sql.PersonSql.*;
import static com.zonesoft.addressbook.db.sql.OtherNameSql.FIELD_OTHER_NAME;
import static com.zonesoft.addressbook.db.sql.OtherNameSql.FIELD_OTHER_NAME_ID;
import static com.zonesoft.addressbook.db.sql.OtherNameSql.FIELD_OTHER_NAME_TYPE;
import static com.zonesoft.addressbook.db.sql.OtherNameSql.FIELD_OTHER_NAME_TYPE_ID;
import static com.zonesoft.addressbook.testing.data_generator.PersonDataGenerator.*;


class PersonDaoFetchAllTest {
	private ConnectionManager mockConnectionManager = Mockito.mock(ConnectionManager.class);
	private Connection mockConnection = Mockito.mock(Connection.class);
	private Statement mockStatement = Mockito.mock(Statement.class);
	private ResultSet mockResultSet = Mockito.mock(ResultSet.class);
	private PersonDao personDao; //Subject under test 
	int expectedResultsSize = 2;

	
	PreparedStatement mockOtherNamesStatement = Mockito.mock(PreparedStatement.class);
	ResultSet mockOtherNamesResultset = Mockito.mock(ResultSet.class);
	
	
	@BeforeEach
	void beforeEach() throws SQLException {
		assertNotNull(mockConnectionManager);
		assertNotNull(mockConnection);
		assertNotNull(mockStatement);
		assertNotNull(mockResultSet);
		when(mockConnectionManager.getConnection()).thenReturn(mockConnection);
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		personDao = new PersonDao(mockConnectionManager);
		assertNotNull(personDao);
		when(mockStatement.executeQuery(GET_ALL_SQL)).thenReturn(mockResultSet);
		when(mockConnection.prepareStatement(GET_OTHER_NAMES_SQL)).thenReturn(mockOtherNamesStatement);
		when(mockOtherNamesStatement.executeQuery()).thenReturn(mockOtherNamesResultset);	
	}
	
	@Test
	void testFetchAll_WHEN_resultsetIsEmpty_THEN_nullIsReturned() throws SQLException {
		when(mockResultSet.next()).thenReturn(false);
		assertNull(personDao.fetchAll());
	}

	private void mockoutResultsetGets(Person generatedPerson1, Person generatedPerson2) throws SQLException {
		when(mockResultSet.getLong(FIELD_PERSON_ID))
			.thenReturn(
					generatedPerson1.getPersonId(), 
					generatedPerson2.getPersonId()
		);
		
		when(mockResultSet.getString(FIELD_FIRSTNAME)).
			thenReturn(
					generatedPerson1.getFirstname(), 
					generatedPerson2.getFirstname()
		);
		
		when(mockResultSet.getString(FIELD_LASTNAME)).
			thenReturn(
					generatedPerson1.getLastname(), 
					generatedPerson2.getLastname()
		);
		
		when(mockResultSet.getString(FIELD_DATE_OF_BIRTH)).
			thenReturn(
					generatedPerson1.getDateOfBirth().toString(), 
					generatedPerson2.getDateOfBirth().toString()
		);				
	}

	
	@Test
	void testFetchAll_WHEN_resultsetIsNotEmpty_AND_allPersonsHaveNoOtherNames_THEN_listOfPersonsIsReturnedWithOtherNamesNull() throws SQLException {
		Person generatedPerson1 = generatePerson(false);;
		Person generatedPerson2 = generatePerson(false);;
		mockoutResultsetGets(generatedPerson1,generatedPerson2);
		@SuppressWarnings("serial")
		List<Person> allGeneratedPersons = new ArrayList<Person>() {{add(generatedPerson1);add(generatedPerson2);}};
		when(mockResultSet.next()).thenReturn(true,true,false);
		when(mockOtherNamesResultset.next()).thenReturn(false);
		
		List<Person> fetchedPersons = personDao.fetchAll();
		assertNotNull(fetchedPersons);
		assertEquals(expectedResultsSize, fetchedPersons.size());
		for(int j=0; j < expectedResultsSize; j++) {
			Person fetchedPerson = fetchedPersons.get(j);
			Person generatedPerson = allGeneratedPersons.get(j);
			assertEquals(generatedPerson.getPersonId(), fetchedPerson.getPersonId());
			assertEquals(generatedPerson.getFirstname(), fetchedPerson.getFirstname());
			assertEquals(generatedPerson.getLastname(), fetchedPerson.getLastname());
			assertEquals(generatedPerson.getDateOfBirth(), fetchedPerson.getDateOfBirth());
			assertNull(fetchedPerson.getOtherNames());
		}
	}
	

	@Test
	void testFetchAll_WHEN_resultsetIsNotEmpty_AND_allPersonsHaveOtherNames_THEN_listOfPersonsIsReturnedWithOtherNamesNotNull() throws SQLException {
		Person generatedPerson1 = generatePerson(true);;
		Person generatedPerson2 = generatePerson(true);;
		mockoutResultsetGets(generatedPerson1,generatedPerson2);
		@SuppressWarnings("serial")
		List<Person> allGeneratedPersons = new ArrayList<Person>() {{add(generatedPerson1);add(generatedPerson2);}};
		when(mockResultSet.next()).thenReturn(true,true,false);
		when(mockOtherNamesResultset.next()).thenReturn(true,true,false,true,false);
		when(mockOtherNamesResultset.getLong(FIELD_OTHER_NAME_ID)).thenReturn(generateId());
		when(mockOtherNamesResultset.getString(FIELD_OTHER_NAME)).thenReturn(generateOtherName(generateGender()));
		when(mockOtherNamesResultset.getLong(FIELD_OTHER_NAME_TYPE_ID)).thenReturn((long) generateOtherNameTypeId());
		when(mockOtherNamesResultset.getString(FIELD_OTHER_NAME_TYPE)).thenReturn(generateOtherNameType().toString());
		List<Person> fetchedPersons = personDao.fetchAll();
		assertNotNull(fetchedPersons);
		assertEquals(expectedResultsSize, fetchedPersons.size());
		for(int j=0; j < expectedResultsSize; j++) {
			Person fetchedPerson = fetchedPersons.get(j);
			Person generatedPerson = allGeneratedPersons.get(j);
			assertEquals(generatedPerson.getPersonId(), fetchedPerson.getPersonId());
			assertEquals(generatedPerson.getFirstname(), fetchedPerson.getFirstname());
			assertEquals(generatedPerson.getLastname(), fetchedPerson.getLastname());
			assertEquals(generatedPerson.getDateOfBirth(), fetchedPerson.getDateOfBirth());
			assertNotNull(fetchedPerson.getOtherNames());
		}
	}
}
