package com.zonesoft.addressbook.webui;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.zonesoft.addressbook.services.PersonService;


public class PersonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final PersonService personService = new PersonService();
	private static final String ACTION_PARAMTER_NAME = "action";
	private static final String ACTION_LIST = "LIST";
	private static final String ACTION_ADD = "ADD";
	private static final String ACTION_EDIT = "EDIT";
	private static final String ACTION_DELETE = "DELETE";
	private static final String ACTION_RETURN_HOME = "HOME";

    public PersonController() {
        super();
    }



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestedAction = null;
		if (Objects.isNull(request.getParameter(ACTION_PARAMTER_NAME))) { 
			requestedAction = ACTION_LIST;
		}else {
			requestedAction = request.getParameter(ACTION_PARAMTER_NAME);
		}
		switch(requestedAction) {
			case ACTION_ADD:
				viewAdd(request, response);
				break;
			case ACTION_EDIT:
				viewEdit(request, response);
				break;
			case ACTION_DELETE:
				viewDelete(request, response);
				break;
			case ACTION_LIST:
				viewList(request, response);
				break;				
			case ACTION_RETURN_HOME:
				viewHome(request, response);
				break;
			default:
				viewHome(request, response);
		}

	}

    private void viewList(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.getWriter().append(getListTemplate());
	}



	private void viewDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		viewHome(request, response);
		
	}



	private void viewHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/hello");
		requestDispatcher.forward(request, response);
	}



	private void viewEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		viewHome(request, response);
		
	}



	private void viewAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		viewHome(request, response);
		
	}



	private  String getListTemplate() {
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(resolver);
        Context ct = new Context();
        ct.setVariable("persons", personService.fetchAllPersons());
        return templateEngine.process("persons/list.html", ct);
    }
	
}
