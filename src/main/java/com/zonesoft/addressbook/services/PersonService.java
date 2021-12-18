package com.zonesoft.addressbook.services;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.db.dao.PersonDao;
import com.zonesoft.addressbook.entities.Person;
import com.zonesoft.addressbook.properties.ApplicationProperties;

public class PersonService {
	private static final ApplicationProperties applicationProperties = new ApplicationProperties();
	private static final ConnectionManager connectionManager = new ConnectionManager(applicationProperties);
    private static final PersonDao personDao = new PersonDao(connectionManager);
    
	public String fetchAllPersonsJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		String json = objectMapper.writeValueAsString(fetchAllPersons());
		return json;
	}

	public List<Person> fetchAllPersons() {
		return personDao.fetchAll();
	}
	
}
