package com.zonesoft.addressbook.db.sql.person;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.zonesoft.addressbook.db.dao.sql.person.Update;

class PersonUpdateSqlTest {
	private static final String ExpectedSql =
            "UPDATE "
            +    "  t_person AS a"
            + " SET "
            +    " a.firstname  = ?,"
            +    " a.lastname   = ?,"
            +    " a.birth_date = ?"
            + " WHERE"
            +    "  a.person_id = ?";
	
	
	private static final int expectedPersonIdIndex = 4;
	private static final int expectedFirstnameIndex = 1;
	
	@Test
	void testGetSql() {
		String sqlStatement = Update.SQL;
		assertEquals(ExpectedSql, sqlStatement);
		
	}
	@Test
	void testGetIndex() {
//		assertEquals(expectedPersonIdIndex, Update.parameterIndex("PERSON_ID"));
//		assertEquals(expectedPersonIdIndex, Update.PERSON_ID);
		assertEquals(expectedPersonIdIndex, Update.PARAMETERS.PERSON_ID);
		assertEquals(expectedFirstnameIndex, Update.PARAMETERS.FIRSTNAME);
	}

}
