package com.zonesoft.addressbook.services.data;

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
    
	public String fetchAllJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		String json = objectMapper.writeValueAsString(fetchAll());
		return json;
	}

	public List<Person> fetchAll() {
		return personDao.fetchAll();
	}
	
	public Person fetchById(long id) {
		return personDao.fetchById(id);
	}

	public Person update(Person person) {
		personDao.update(person);
		return person;	
	}
}
