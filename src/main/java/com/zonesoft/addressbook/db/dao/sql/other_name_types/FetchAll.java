package com.zonesoft.addressbook.db.dao.sql.other_name_types;

public class FetchAll {
	
	public static final class FIELDS{
		public static final String ID = "other_name_type_id";
		public static final String TYPE = "other_name_type";
	}
	
	
	public static final String SQL = 
			"SELECT "
			+ 	" other_name_type_id, "
			+ 	" other_name_type "
			+ " FROM "
			+ 	" t_other_name_type"
			+ " ORDER BY "
			+ 	" other_name_type";
}
