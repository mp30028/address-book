package com.zonesoft.addressbook.webui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zonesoft.addressbook.services.rendering.HtmlView;

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
