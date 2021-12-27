package com.zonesoft.addressbook.db.sql;

public class OtherNameSql {
	public static final String FIELD_PERSON_ID = "person_id";
	public static final String FIELD_OTHER_NAME_ID = "other_name_id";
	public static final String FIELD_OTHER_NAME = "other_name";
	public static final String FIELD_OTHER_NAME_TYPE_ID = "other_name_type_id";
	public static final String FIELD_OTHER_NAME_TYPE = "other_name_type";
	public static final int PARAMETER_INDEX_PERSON_ID_FOR_OTHER_NAMES_SQL = 1;
	public static final int PARAMETER_INDEX_OTHER_NAME_ID_FOR_OTHER_NAME_SQL = 1;
	
	
	public static final String GET_OTHER_NAME_SQL = 
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
	
	
	public static final String GET_OTHER_NAMES_SQL = 
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
			+ 	" a.person_id = ?";
	
	public static final String GET_BY_ID_SQL =
			"SELECT"
			+ 	" person_id,"
			+ 	" firstname,"
			+ 	" lastname,"
			+ 	" birth_date"
			+ " FROM"
			+ 	" t_person"
			+ " WHERE"
			+ 	" person_id = ?";
}
