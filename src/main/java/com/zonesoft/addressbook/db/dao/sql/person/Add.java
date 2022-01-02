package com.zonesoft.addressbook.db.dao.sql.person;

public class Add {

	
	public static final class PARAMETERS{
		public static final int FIRSTNAME = 1;
		public static final int LASTNAME = 2;
		public static final int BIRTH_DATE = 3;
	}
	
	public static final String SQL = 
			"INSERT INTO"
			+ "	t_person("
			+ 	" firstname,"
			+ 	" lastname,"
			+ 	" birth_date"
			+ "	)"
			+ " VALUES("
			+ 	" ?,"
			+ 	" ?,"
			+ 	" ?"
			+ ")";		
}

