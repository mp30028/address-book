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

import com.zonesoft.addressbook.services.data.OtherNamesService;
import com.zonesoft.addressbook.services.rendering.HtmlView;

import static com.zonesoft.addressbook.constants.ApplicationConstants.*;

public class OtherNamesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(OtherNamesController.class);

	private static final OtherNamesService otherNamesService = new OtherNamesService();

	
	private static final String LIST_TEMPLATE_PATH = "persons/other_names/list.html";
	private static final String UPDATE_TEMPLATE_PATH = "persons/other_names/update.html";
	private static final String HOME_PAGE_PATH = "../../index.html";
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
		long personId =  Long.parseLong(request.getParameter("personId"));
		LOGGER.debug("personId = " + personId);
    	String html = htmlView.create(LIST_TEMPLATE_PATH, getListModel(personId));
    	LOGGER.debug("\n---------------- List Html ---------------------------------");
    	LOGGER.debug(html);
    	LOGGER.debug("------------------------------------------------------------\n");
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
		long otherNameId =  Long.parseLong(request.getParameter("otherNameId"));
		LOGGER.debug("otherNameId = " + otherNameId);
    	String html = htmlView.create(UPDATE_TEMPLATE_PATH, getUpdateModel(otherNameId));
    	LOGGER.debug("\n---------------- Edit Html ---------------------------------");
    	LOGGER.debug(html);
    	LOGGER.debug("------------------------------------------------------------\n");
    	response.getWriter().append(html);		
	}




	private void viewAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		viewHome(request, response);
		
	}

	
	private Map<String, Object> getUpdateModel(long otherNameId) {
		Map<String, Object> model = new HashMap<>();
		model.put("otherName", otherNamesService.fetchById(otherNameId));
		return model;
	}
	
	private Map<String, Object> getListModel(long personId){
		Map<String, Object> model = new HashMap<>();
		model.put("personOtherNames", otherNamesService.fetchByPersonId(personId));
		return model;
	}

}
