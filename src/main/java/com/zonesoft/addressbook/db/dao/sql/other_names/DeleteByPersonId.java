package com.zonesoft.addressbook.db.dao.sql.other_names;

public class DeleteByPersonId {

	public static final class PARAMETERS{
		public static final int PERSON_ID = 1;
	}
	
	public static final String SQL =
									"DELETE "
									+ " FROM"
									+ 	" t_other_name"
									+ " WHERE"
									+  	" person_id = ?";
}