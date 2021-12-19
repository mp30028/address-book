package com.zonesoft.addressbook.webapi;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zonesoft.addressbook.services.data.PersonService;


public class PersonApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final PersonService personService = new PersonService();

    public PersonApi() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append(personService.fetchAllPersonsJson());
	}
	
}
