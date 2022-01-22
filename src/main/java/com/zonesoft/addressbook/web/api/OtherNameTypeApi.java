package com.zonesoft.addressbook.web.api;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//jakarta.servlet 

import com.zonesoft.addressbook.services.data.OtherNameTypeService;


public class OtherNameTypeApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final OtherNameTypeService otherNameTypeService = new OtherNameTypeService();

    public OtherNameTypeApi() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append(otherNameTypeService.fetchAllJson());
	}
	
}
