package com.zonesoft.addressbook.services.data;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.db.dao.OtherNamesDao;
import com.zonesoft.addressbook.entities.OtherName;
import com.zonesoft.addressbook.properties.ApplicationProperties;

public class OtherNamesService {
	private static final ApplicationProperties applicationProperties = new ApplicationProperties();
	private static final ConnectionManager connectionManager = new ConnectionManager(applicationProperties);
    private static final OtherNamesDao otherNamesDao = new OtherNamesDao(connectionManager);
    
	public String fetchByPersonIdJson(long personId) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		String json = objectMapper.writeValueAsString(fetchByPersonId(personId));
		return json;
	}

	public List<OtherName> fetchByPersonId(long personId) {
		return otherNamesDao.fetchByPersonId(personId);
	}

	public Object fetchById(long otherNameId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
