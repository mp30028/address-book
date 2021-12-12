package com.zonesoft.addressbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.entities.OtherName;
import com.zonesoft.addressbook.entities.OtherNameType;
import com.zonesoft.addressbook.entities.Person;
import static com.zonesoft.addressbook.dao.sql.PersonSql.*;
import static com.zonesoft.addressbook.utils.Utils.convertToLocalDate;

public class PersonDao {

	private static  ConnectionManager connectionManager;
	
	public PersonDao(ConnectionManager connectionManager) {
		super();
		PersonDao.connectionManager = connectionManager;
	}
	
	public Person getById(long id) {
		// TODO 
		return null;
	}
	
	public List<Person> fetchAll(){
		Connection connection = PersonDao.connectionManager.getConnection();
		List<Person> persons = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(GET_ALL_SQL);
			while(resultset.next()) {
				if (Objects.isNull(persons)) persons = new ArrayList<Person>();
				long id = resultset.getLong(FIELD_PERSON_ID);
				
				Person person = new Person();
				person.sePersonId(id);
				person.setFirstname(resultset.getString(FIELD_FIRSTNAME));
				person.setLastname(resultset.getString(FIELD_LASTNAME));
				person.setDateOfBirth(convertToLocalDate(resultset.getString(FIELD_DATE_OF_BIRTH)));
				List<OtherName> otherNames = fetchOtherNames(connection, person);
				person.setOtherNames(otherNames);
				persons.add(person);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {	
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	private List<OtherName> fetchOtherNames(Connection connection, Person person) throws SQLException{
		PreparedStatement statement = connection.prepareStatement(GET_OTHER_NAMES_SQL);
		statement.setLong(PARAMETER_PERSON_ID_FOR_OTHER_NAMES_SQL, person.getPersonId());
		ResultSet resultset = statement.executeQuery();
		List<OtherName> otherNames = null;
		while(resultset.next()) {
			if (Objects.isNull(otherNames)) otherNames = new ArrayList<OtherName>();
			OtherName otherName = new OtherName();
			otherName.setOtherNameId(resultset.getLong(FIELD_OTHER_NAME_ID));
			otherName.setValue(resultset.getString(FIELD_OTHER_NAME));
			otherName.setNameType(new OtherNameType(resultset.getLong(FIELD_OTHER_NAME_TYPE_ID), resultset.getString(FIELD_OTHER_NAME_TYPE)));
			otherName.setPerson(person);
			otherNames.add(otherName);
		}
		return otherNames;
	}
}
