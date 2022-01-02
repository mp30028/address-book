package com.zonesoft.addressbook.web.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zonesoft.addressbook.entities.OtherName;
import com.zonesoft.addressbook.entities.OtherNameType;
import com.zonesoft.addressbook.entities.Person;
import com.zonesoft.addressbook.services.data.PersonService;
import com.zonesoft.addressbook.services.views.HtmlView;

import static com.zonesoft.addressbook.constants.ApplicationConstants.*;
import static com.zonesoft.addressbook.utils.Utils.*;


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
			case ACTION_SAVE:
				doSave(request);
			case ACTION_CANCEL:
			case ACTION_LIST: 
			default:
				showListView(request, response);
		}
	}
    

	private void doSave(HttpServletRequest request) {
		
		LOGGER.debug(requestParametersToString(request));
		Person person = unpackPerson(request);
		String crudType =  request.getParameter(KEY_CRUD_TYPE);
		switch(crudType){
			case ACTION_EDIT:
				personService.update(person);
				break;
			case ACTION_DELETE:
				personService.delete(person);
				break;
			case ACTION_ADD:
				personService.add(person);
				break;
		}
	}
	
	private Person unpackPerson(HttpServletRequest request) {
		Person person = new Person();
		person.setId(Long.parseLong(request.getParameter("id")));
		person.setFirstname(request.getParameter("firstname"));
		person.setLastname(request.getParameter("lastname"));
		person.setDateOfBirth(convertStringToLocalDate(request.getParameter("dateOfBirth")));
		person.setOtherNames(unpackPersonOtherNames(request, person));
		return person;
	}
	
	
	private List<OtherName> unpackPersonOtherNames(HttpServletRequest request, Person person){
		String[] otherNameIds = request.getParameterValues("otherNameId");
		String[] otherNameValues = request.getParameterValues("otherNameValue");
		String[] otherNameTypeIds = request.getParameterValues("otherNameTypeId");
		List<OtherName> otherNames = null;
		if(Objects.nonNull(otherNameIds)){
			if (otherNameIds.length > 0) otherNames = new ArrayList<OtherName>();
			for(int j=0; j < otherNameIds.length; j++) {
				long otherNameId = Long.parseLong(otherNameIds[j]);
				String otherNameValue = otherNameValues[j];
				long otherNameTypeId = Long.parseLong(otherNameTypeIds[j]);
				OtherName otherName = new OtherName();
				otherName.setId(otherNameId);
				otherName.setValue(otherNameValue);
				OtherNameType otherNameType = new OtherNameType();
				otherName.setPerson(person);
				otherNameType.setId(otherNameTypeId);
				otherName.setOtherNameType(otherNameType);
				otherNames.add(otherName);
			}
		}
		return otherNames;
	}

	private void showListView(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String html = htmlView.create(TEMPLATE_PATH_LIST, modelForList());
    	LOGGER.debug("Showing List View");
    	PrintWriter printWriter = response.getWriter();
    	printWriter.append(html);
    	printWriter.close();
	}


	private void showDeleteView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Objects.nonNull(request.getParameter("id"))){
			long personId =  Long.parseLong(request.getParameter("id"));
	    	String html = htmlView.create(TEMPLATE_PATH_UPDATE, modelForDelete(personId));
	    	LOGGER.debug("Showing Delete View for record with personId=" + personId);
	    	response.getWriter().append(html);
		}else {
			showListView(request, response);
		}
	}





	private void showHomeView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(PATH_HOME_PAGE);
	}


	private void showEditView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Objects.nonNull(request.getParameter("id"))){
			long personId =  Long.parseLong(request.getParameter("id"));
	    	String html = htmlView.create(TEMPLATE_PATH_UPDATE, modelForUpdate(personId));
	    	LOGGER.debug("Showing Edit View for record with personId=" + personId);
	    	response.getWriter().append(html);
		}else {
			showListView(request, response);
		}
	}


	private void showAddView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	String html = htmlView.create(TEMPLATE_PATH_UPDATE, modelForAdd());
	    	response.getWriter().append(html);
	}

	
	private Map<String, Object> modelForUpdate(long personId) {
		Map<String, Object> model = new HashMap<>();
		model.put(KEY_PERSON, personService.fetchById(personId));
		model.put(KEY_CRUD_TYPE, ACTION_EDIT);
		model.put(KEY_IS_READ_ONLY, false);
		return model;
	}
	
	
	private Map<String, Object> modelForList(){
		Map<String, Object> model = new HashMap<>();
		model.put(KEY_All_PERSONS, personService.fetchAll());
		return model;
	}

	private Map<String, Object> modelForDelete(long personId) {
		Map<String, Object> model = new HashMap<>();
		model.put(KEY_PERSON, personService.fetchById(personId));
		model.put(KEY_CRUD_TYPE, ACTION_DELETE);
		model.put(KEY_IS_READ_ONLY, true);
		return model;
	}
	
	private Map<String, Object> modelForAdd() {
		Map<String, Object> model = new HashMap<>();
		Person person = new Person();
		person.setId(-1L);
		model.put(KEY_PERSON, person);
		model.put(KEY_CRUD_TYPE, ACTION_ADD);
		model.put(KEY_IS_READ_ONLY, false);
		return model;
	}
	
}
