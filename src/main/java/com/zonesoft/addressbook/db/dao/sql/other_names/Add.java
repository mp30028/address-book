package com.zonesoft.addressbook.db.dao.sql.other_names;

public class Add {

	
	public static final class PARAMETERS{
		public static final int OTHER_NAME = 1;
		public static final int PERSON_ID = 2;
		public static final int OTHER_NAME_TYPE_ID = 3;
	}
	
	public static final String SQL =
					"INSERT INTO "
					+ "	t_other_name("
					+ "	  other_name,"
					+ "   person_id,"
					+ "   other_name_type_id"
					+ "	)"
					+ " VALUES("
					+ "	 ?,"
					+ "  ?,"
					+ "  ?"
					+ " )" ;
}

