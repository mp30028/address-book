package com.zonesoft.addressbook.tryouts;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.db.dao.PersonDao;
import com.zonesoft.addressbook.entities.Person;
import com.zonesoft.addressbook.properties.ApplicationProperties;

class PersonDaoTryout {
	private static final Logger LOGGER = Logger.getLogger(PersonDaoTryout.class);
	
	@Test
	void tryoutPersonDaoFetchAll() {
		ConnectionManager connectionManager = new ConnectionManager(new ApplicationProperties());
		PersonDao personDao = new PersonDao(connectionManager);
		List<Person> persons = personDao.fetchAll();
		LOGGER.debug("persons fetched = " +  persons.size());
		int j = 1;
		for (Person person : persons) {
			LOGGER.debug("person " + j + ": " + person.toJsonString());
			j++;
		}
		
	}

}
