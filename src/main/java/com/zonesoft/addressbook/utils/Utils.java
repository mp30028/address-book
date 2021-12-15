package com.zonesoft.addressbook.utils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class Utils {
	
	public static LocalDate convertToLocalDate(String dateAsString) {
		return LocalDate.parse(dateAsString);
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
}
