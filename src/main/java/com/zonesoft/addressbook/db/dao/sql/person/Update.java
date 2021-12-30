package com.zonesoft.addressbook.db.dao.sql.person;

public class Update {

	
	public static final class PARAMETERS{
		public static final int FIRSTNAME = 1;
		public static final int LASTNAME = 2;
		public static final int BIRTH_DATE = 3;
		public static final int PERSON_ID = 4;
	}
	
	public static final String SQL =
            "UPDATE "
            +    "  t_person AS a"
            + " SET "
            +    " a.firstname  = ?,"
            +    " a.lastname   = ?,"
            +    " a.birth_date = ?"
            + " WHERE"
            +    "  a.person_id = ?";
	
}

