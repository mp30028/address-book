package com.zonesoft.addressbook.db.dao.sql.person;

public class FetchAll {
	
	public static final class FIELDS{
		public static final String PERSON_ID = "person_id";
		public static final String FIRSTNAME = "firstname";
		public static final String LASTNAME = "lastname";
		public static final String DATE_OF_BIRTH = "birth_date";
	}
	
	
	public static final String SQL = 
			"SELECT"
			+ 	" person_id,"
			+ 	" firstname,"
			+ 	" lastname,"
			+ 	" birth_date"
			+ " FROM"
			+ 	" t_person"
			+ " ORDER BY"
			+ 	" lastname,"
			+ 	" firstname,"
			+ 	" birth_date";
}
