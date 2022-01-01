package com.zonesoft.addressbook.web.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
