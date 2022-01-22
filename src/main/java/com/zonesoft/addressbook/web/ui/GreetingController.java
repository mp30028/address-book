package com.zonesoft.addressbook.web.ui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.zonesoft.addressbook.services.views.HtmlView;

public class GreetingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEMPLATE_PATH = "greetings/greeting.html"; 
	private static final HtmlView htmlView = new HtmlView();

	public GreetingController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String html = htmlView.create(TEMPLATE_PATH, getModel());
		response.getWriter().append(html);
	}
	
	private Map<String, Object> getModel(){
		Map<String, Object> model = new HashMap<>();
		model.put("name", "Hello User");
		model.put("date", LocalDateTime.now().toString());
		return model;
	}

}
