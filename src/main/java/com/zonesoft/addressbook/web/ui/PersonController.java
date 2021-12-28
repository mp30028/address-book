package com.zonesoft.addressbook.web.ui;

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
import com.zonesoft.addressbook.services.views.HtmlView;

import static com.zonesoft.addressbook.constants.ApplicationConstants.*;


public class PersonController extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(PersonController.class);
	
	private static final long serialVersionUID = 1L;
	
	private static final PersonService personService = new PersonService();
	

	
	private static final String TEMPLATE_PATH_LIST = "persons/list.html";
	private static final String TEMPLATE_PATH_UPDATE = "persons/update.html";
	private static final String PATH_HOME_PAGE = "../index.html";
	
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
				showAddView(request, response);
				break;
			case ACTION_EDIT:
				showEditView(request, response);
				break;
			case ACTION_DELETE:
				showDeleteView(request, response);
				break;
			case ACTION_RETURN_HOME:
				showHomeView(request, response);
				break;
			case ACTION_CANCEL:
			case ACTION_LIST: 
			default:
				showListView(request, response);
		}
	}
    

	private void showListView(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String html = htmlView.create(TEMPLATE_PATH_LIST, modelForList());
    	LOGGER.debug("Showing List View");
    	PrintWriter printWriter = response.getWriter();
    	printWriter.append(html);
    	printWriter.close();
	}


	private void showDeleteView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		showHomeView(request, response);
		
	}


	private void showHomeView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(PATH_HOME_PAGE);
	}


	private void showEditView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long personId =  Long.parseLong(request.getParameter("id"));
    	String html = htmlView.create(TEMPLATE_PATH_UPDATE, modelForUpdate(personId));
    	LOGGER.debug("Showing Edit View for record with personId=" + personId);
    	response.getWriter().append(html);		
	}


	private void showAddView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		showHomeView(request, response);
		
	}

	
	private Map<String, Object> modelForUpdate(long personId) {
		Map<String, Object> model = new HashMap<>();
		model.put(MODEL_KEY_PERSON, personService.fetchById(personId));
		return model;
	}
	
	
	private Map<String, Object> modelForList(){
		Map<String, Object> model = new HashMap<>();
		model.put(MODEL_KEY_PERSONS, personService.fetchAll());
		return model;
	}

}
