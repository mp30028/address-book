package com.zonesoft.addressbook.web.api;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.zonesoft.addressbook.services.data.PersonService;


public class PersonApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final PersonService personService = new PersonService();

    public PersonApi() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append(personService.fetchAllJson());
	}
	
}
