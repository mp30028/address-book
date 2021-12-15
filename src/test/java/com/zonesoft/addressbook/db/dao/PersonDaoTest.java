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
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.entities.Person;
import com.zonesoft.addressbook.testing.data_generator.PersonDataGenerator.Gender;

import static com.zonesoft.addressbook.db.sql.PersonSql.*;
import static com.zonesoft.addressbook.testing.data_generator.PersonDataGenerator.generatePersonData;
import static com.zonesoft.addressbook.testing.data_generator.PersonDataGenerator.generateOtherNamesData;
import static com.zonesoft.addressbook.utils.Utils.convertToLocalDate;


class PersonDaoTest {
	private ConnectionManager mockConnectionManager = Mockito.mock(ConnectionManager.class);
	private Connection mockConnection = Mockito.mock(Connection.class);
	private Statement mockStatement = Mockito.mock(Statement.class);
	private ResultSet mockResultSet = Mockito.mock(ResultSet.class);
	private PersonDao personDao; //Subject under test 
	
	
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
	}
	
	@Test
	void testFetchAll_WHEN_resultsetIsEmpty_THEN_nullIsReturned() throws SQLException {
		when(mockStatement.executeQuery(GET_ALL_SQL)).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(false);
		assertNull(personDao.fetchAll());
	}

	@Test
	void testFetchAll_WHEN_resultsetIsNotEmpty_AND_allPersonsHaveNoOtherNames_THEN_listOfPersonsIsReturned() throws SQLException {
		int expectedResultsSize = 2;
		when(mockStatement.executeQuery(GET_ALL_SQL)).thenReturn(mockResultSet);
			when(mockResultSet.next()).thenReturn(true,true,false);
			Map<String, Object> dataPerson1 = generatePersonData();
			Map<String, Object> dataPerson2 = generatePersonData();
			List<Map<String, Object>> allPersonsData = new ArrayList<Map<String,Object>>();
			allPersonsData.add(dataPerson1);
			allPersonsData.add(dataPerson2);
			when(mockResultSet.getLong(FIELD_PERSON_ID))
				.thenReturn(
						(Long) dataPerson1.get(FIELD_PERSON_ID), 
						(Long) dataPerson2.get(FIELD_PERSON_ID)
			);
			
			when(mockResultSet.getString(FIELD_FIRSTNAME)).
				thenReturn(
						(String) dataPerson1.get(FIELD_FIRSTNAME), 
						(String) dataPerson2.get(FIELD_FIRSTNAME)
			);
			
			when(mockResultSet.getString(FIELD_LASTNAME)).
				thenReturn(
							(String) dataPerson1.get(FIELD_LASTNAME), 
							(String) dataPerson2.get(FIELD_LASTNAME)
			);
			
			when(mockResultSet.getString(FIELD_DATE_OF_BIRTH)).
				thenReturn(
						(String) dataPerson1.get(FIELD_DATE_OF_BIRTH), 
						(String) dataPerson2.get(FIELD_DATE_OF_BIRTH)
			);
			
			PreparedStatement mockOtherNamesStatement = Mockito.mock(PreparedStatement.class);
			ResultSet mockOtherNamesResultset = Mockito.mock(ResultSet.class);
			when(mockConnection.prepareStatement(GET_OTHER_NAMES_SQL)).thenReturn(mockOtherNamesStatement);
			when(mockOtherNamesStatement.executeQuery()).thenReturn(mockOtherNamesResultset);
			when(mockOtherNamesResultset.next()).thenReturn(false);
		List<Person> persons = personDao.fetchAll();
		assertNotNull(persons);
		assertEquals(expectedResultsSize, persons.size());
		for(int j=0; j < expectedResultsSize; j++) {
			Person person = persons.get(j);
			Map<String, Object> data = allPersonsData.get(j);
			assertEquals(data.get(FIELD_PERSON_ID), person.getPersonId());
			assertEquals(data.get(FIELD_FIRSTNAME), person.getFirstname());
			assertEquals(data.get(FIELD_LASTNAME), person.getLastname());
			assertEquals(convertToLocalDate((String)data.get(FIELD_DATE_OF_BIRTH)), person.getDateOfBirth());
		}
	}
	
	@Test
	void testFetchAll_WHEN_resultsetIsNotEmpty_AND_allPersonsHaveOtherNames_THEN_listOfPersonsIsReturned() throws SQLException {
		int expectedResultsSize = 2;
		when(mockStatement.executeQuery(GET_ALL_SQL)).thenReturn(mockResultSet);
			when(mockResultSet.next()).thenReturn(true,true,false);
			Map<String, Object> dataPerson1 = generatePersonData();
			Map<String, Object> dataPerson2 = generatePersonData();
			List<Map<String, Object>> allPersonsData = new ArrayList<Map<String,Object>>();
			allPersonsData.add(dataPerson1);
			allPersonsData.add(dataPerson2);
			
			when(mockResultSet.getLong(FIELD_PERSON_ID))
				.thenReturn(
						(Long) dataPerson1.get(FIELD_PERSON_ID), 
						(Long) dataPerson2.get(FIELD_PERSON_ID)
			);
			
			when(mockResultSet.getString(FIELD_FIRSTNAME)).
				thenReturn(
						(String) dataPerson1.get(FIELD_FIRSTNAME), 
						(String) dataPerson2.get(FIELD_FIRSTNAME)
			);
			
			when(mockResultSet.getString(FIELD_LASTNAME)).
				thenReturn(
							(String) dataPerson1.get(FIELD_LASTNAME), 
							(String) dataPerson2.get(FIELD_LASTNAME)
			);
			
			when(mockResultSet.getString(FIELD_DATE_OF_BIRTH)).
				thenReturn(
						(String) dataPerson1.get(FIELD_DATE_OF_BIRTH), 
						(String) dataPerson2.get(FIELD_DATE_OF_BIRTH)
			);
			
			PreparedStatement mockOtherNamesStatement = Mockito.mock(PreparedStatement.class);
			ResultSet mockOtherNamesResultset = Mockito.mock(ResultSet.class);
			when(mockConnection.prepareStatement(GET_OTHER_NAMES_SQL)).thenReturn(mockOtherNamesStatement);
			when(mockOtherNamesStatement.executeQuery()).thenReturn(mockOtherNamesResultset);
			when(mockOtherNamesResultset.next()).thenReturn(true);

			
		
			List<Map<String, Object>> dataOtherNames1 = generateOtherNamesData((Gender)dataPerson1.get(FIELD_GENDER), 3);
			List<Map<String, Object>> dataOtherNames2 = generateOtherNamesData((Gender)dataPerson2.get(FIELD_GENDER), 1);
			List<List<Map<String, Object>>> allOtherNamesData = new ArrayList<>();
			allOtherNamesData.add(dataOtherNames1);
			allOtherNamesData.add(dataOtherNames2);
			
			when(mockOtherNamesResultset.getLong(FIELD_OTHER_NAME_ID))
				.thenReturn(
						(Long) dataOtherNames1.get(0).get(FIELD_OTHER_NAME_ID),
						(Long) dataOtherNames1.get(1).get(FIELD_OTHER_NAME_ID),
						(Long) dataOtherNames1.get(2).get(FIELD_OTHER_NAME_ID)
			);
			
//			when(mockOtherNamesResultset.getString(FIELD_DATE_OF_BIRTH)).
//			thenReturn(
//					(String) dataOtherNames1.get(FIELD_DATE_OF_BIRTH), 
//					(String) dataOtherNames1.get(FIELD_DATE_OF_BIRTH)
//			);
			
			
			
			
		List<Person> persons = personDao.fetchAll();
		assertNotNull(persons);
		assertEquals(expectedResultsSize, persons.size());
		for(int j=0; j < expectedResultsSize; j++) {
			Person person = persons.get(j);
			Map<String, Object> data = allPersonsData.get(j);
			assertEquals(data.get(FIELD_PERSON_ID), person.getPersonId());
			assertEquals(data.get(FIELD_FIRSTNAME), person.getFirstname());
			assertEquals(data.get(FIELD_LASTNAME), person.getLastname());
			assertEquals(convertToLocalDate((String)data.get(FIELD_DATE_OF_BIRTH)), person.getDateOfBirth());
		}
	}
	
	
}
