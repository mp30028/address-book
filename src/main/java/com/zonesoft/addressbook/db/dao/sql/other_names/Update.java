package com.zonesoft.addressbook.db.dao.sql.other_names;

public class Update {

	
	public static final class PARAMETERS{
		public static final int OTHER_NAME = 1;
		public static final int OTHER_NAME_TYPE_ID = 2;
		public static final int OTHER_NAME_ID = 3;
	}
	
	public static final String SQL =
									"UPDATE "
									+ "	t_other_name a"
									+ " SET"
									+ "	a.other_name = ?,"
									+ " a.other_name_type_id = ?"
									+ " WHERE"
									+ "	a.other_name_id = ?";
}

