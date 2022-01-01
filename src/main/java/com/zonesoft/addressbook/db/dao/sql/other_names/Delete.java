package com.zonesoft.addressbook.db.dao.sql.other_names;

public class Delete {

	public static final class PARAMETERS{
		public static final int OTHER_NAME_ID = 1;
	}
	
	public static final String SQL =
									"DELETE "
									+ " FROM"
									+ 	" t_other_name"
									+ " WHERE"
									+  	" other_name_id = ?";
}