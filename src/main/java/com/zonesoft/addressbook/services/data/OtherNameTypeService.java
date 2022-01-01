package com.zonesoft.addressbook.services.data;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.db.dao.OtherNameTypeDao;
import com.zonesoft.addressbook.entities.OtherNameType;
import com.zonesoft.addressbook.properties.ApplicationProperties;

public class OtherNameTypeService {
	private static final ApplicationProperties applicationProperties = new ApplicationProperties();
	private static final ConnectionManager connectionManager = new ConnectionManager(applicationProperties);
    private static final OtherNameTypeDao otherNameTypeDao = new OtherNameTypeDao(connectionManager);
    
	public String fetchAllJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		String json = objectMapper.writeValueAsString(fetchAll());
		return json;
	}

	public List<OtherNameType> fetchAll() {
		return otherNameTypeDao.fetchAll();
	}
	

}
