package com.zonesoft.addressbook.utils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

public class Utils {
	
	public static LocalDate convertStringToLocalDate(String dateAsString) {
		return LocalDate.parse(dateAsString);
	}
	
	public static String convertLocalDateToString(LocalDate date) {
		return date.toString();
	}
	
	public static String sqlExceptionAsString(SQLException ex) {
		StringBuffer sb = null;
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				if (Objects.isNull(sb)) sb = new StringBuffer();
				sb.append("\n");
				
				sb.append("SQLState: ");
				sb.append(((SQLException) e).getSQLState());
				sb.append("\n");

				sb.append("Error Code: ");
				sb.append(((SQLException) e).getErrorCode());
				sb.append("\n");
				
				sb.append("Message: ");
				sb.append(e.getMessage());
				sb.append("\n");
				
				Throwable t = ex.getCause();
				while (t != null) {
					sb.append("Cause: ");
					sb.append(t);
					t = t.getCause();
				}
			}
		}
		return sb.toString();
	}
	
	
	public static String requestParametersToString(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();		
		@SuppressWarnings("unchecked")
		Enumeration<String> parameterNames = request.getParameterNames();
		sb.append("\n");
		while(parameterNames.hasMoreElements()){
		 String parameterName = parameterNames.nextElement();
		 sb.append(parameterName);
		 sb.append(" = ");
		 String[] parameterValues = request.getParameterValues(parameterName);
		 int j = 0;
		 for(String parameterValue: parameterValues) {
			 sb.append("["); sb.append(j); sb.append("] ");
			 sb.append(parameterValue);
			 sb.append(": ");
			 j++;
		 }
		 sb.append("\n");
		}
		return sb.toString();
	}
	
}
