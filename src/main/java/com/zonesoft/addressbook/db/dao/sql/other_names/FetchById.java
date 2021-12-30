package com.zonesoft.addressbook.db.dao.sql.other_names;

public class FetchById {
	public static final class FIELDS{
		public static final String OTHER_NAME_ID = "other_name_id";
		public static final String OTHER_NAME = "other_name";
		public static final String OTHER_NAME_TYPE_ID = "other_name_type_id";
		public static final String OTHER_NAME_TYPE = "other_name_type";
	}
	
	public static final class PARAMETERS{
		public static final int OTHER_NAME_ID = 1;
	}
	
	public static final String SQL = 
			"SELECT"
			+ 	" other_name_id,"
			+ 	" other_name,"
			+ 	" a.other_name_type_id,"
			+ 	" b.other_name_type"
			+ " FROM"
			+ 	" t_other_name as a inner join"
			+ 		" t_other_name_type as b"
			+ 	" on a.other_name_type_id = b.other_name_type_id"
			+ " WHERE"
			+ 	" a.other_name_id = ?";
}
