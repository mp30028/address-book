package com.zonesoft.addressbook.db.dao.sql.person;

public class Delete {

	public static final class PARAMETERS{
		public static final int PERSON_ID = 1;
	}
	
	public static final String SQL =
									"DELETE "
									+ " FROM"
									+ 	" t_person"
									+ " WHERE"
									+  	" person_id = ?";
}