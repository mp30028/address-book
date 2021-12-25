package com.zonesoft.addressbook.webui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zonesoft.addressbook.services.data.PersonService;
import com.zonesoft.addressbook.services.rendering.HtmlView;


public class PersonController extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(PersonController.class);
	private static final long serialVersionUID = 1L;
	private static final PersonService personService = new PersonService();
	private static final String ACTION_PARAMTER_NAME = "requestAction";
	private static final String ACTION_LIST = "LIST";
	private static final String ACTION_ADD = "ADD";
	private static final String ACTION_EDIT = "EDIT";
	private static final String ACTION_DELETE = "DELETE";
	private static final String ACTION_RETURN_HOME = "HOME";
	private static final String ACTION_CANCEL = "CANCEL";
	
	private static final String LIST_TEMPLATE_PATH = "persons/list.html";
	private static final String UPDATE_TEMPLATE_PATH = "persons/update.html";
	private static final String HOME_PAGE_PATH = "../index.html";
	private static final HtmlView htmlView = new HtmlView();

    
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestedAction = null;
		if (Objects.isNull(request.getParameter(ACTION_PARAMTER_NAME))) { 
			requestedAction = ACTION_LIST;
		}else {
			requestedAction = request.getParameter(ACTION_PARAMTER_NAME);
		}
		LOGGER.debug("requestedAction = " + requestedAction);
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
			case ACTION_RETURN_HOME:
				viewHome(request, response);
				break;
			case ACTION_CANCEL:
			case ACTION_LIST: 
			default:
				viewList(request, response);
		}
	}

    


	private void viewList(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String html = htmlView.create(LIST_TEMPLATE_PATH, getListModel());
    	LOGGER.debug(html);
    	PrintWriter printWriter = response.getWriter();
    	printWriter.append(html);
    	printWriter.close();
	}



	private void viewDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		viewHome(request, response);
		
	}



	private void viewHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(HOME_PAGE_PATH);
	}



	private void viewEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long personId =  Long.parseLong(request.getParameter("personId"));
		LOGGER.debug("personId = " + personId);
    	String html = htmlView.create(UPDATE_TEMPLATE_PATH, getUpdateModel(personId));
    	response.getWriter().append(html);		
	}




	private void viewAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		viewHome(request, response);
		
	}

	
	private Map<String, Object> getUpdateModel(long personId) {
		Map<String, Object> model = new HashMap<>();
		model.put("person", personService.fetchById(personId));
		return model;
	}
	
	private Map<String, Object> getListModel(){
		Map<String, Object> model = new HashMap<>();
		model.put("persons", personService.fetchAll());
		return model;
	}

	
}
