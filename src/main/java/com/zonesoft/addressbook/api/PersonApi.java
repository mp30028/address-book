package com.zonesoft.addressbook.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonesoft.addressbook.db.ConnectionManager;
import com.zonesoft.addressbook.db.dao.PersonDao;
import com.zonesoft.addressbook.properties.ApplicationProperties;


public class PersonApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final ApplicationProperties applicationProperties = new ApplicationProperties();
	private static final ConnectionManager connectionManager = new ConnectionManager(applicationProperties);
    private static final PersonDao personDao = new PersonDao(connectionManager);

    public PersonApi() {
        super();
    }



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		String json = objectMapper.writeValueAsString(personDao.fetchAll());
		response.getWriter().append(json);
	}


	
}
