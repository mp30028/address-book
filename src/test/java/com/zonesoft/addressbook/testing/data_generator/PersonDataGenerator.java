package com.zonesoft.addressbook.testing.data_generator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import com.zonesoft.addressbook.entities.OtherName;
import com.zonesoft.addressbook.entities.OtherNameType;
import com.zonesoft.addressbook.entities.Person;

public class PersonDataGenerator {
	
	public static final String[] FEMALE_FIRSTNAMES = { "Nicola", "Karen", "Fiona", "Susan", "Claire", "Sharon",
			"Angela", "Gillian", "Julie", "Michelle", "Jacqueline", "Amanda", "Tracy", "Louise", "Jennifer", "Alison",
			"Sarah", "Donna", "Caroline", "Elaine", "Lynn", "Margaret", "Elizabeth", "Lesley", "Deborah", "Pauline",
			"Lorraine", "Laura", "Lisa", "Tracey", "Carol" };
	
	public static final String[] MALE_FIRSTNAMES = { "David", "John", "Paul", "Mark", "James", "Andrew", "Scott",
			"Steven", "Robert", "Stephen", "William", "Craig", "Michael", "Stuart", "Christopher", "Alan", "Colin",
			"Brian", "Kevin", "Gary", "Richard", "Derek", "Martin", "Thomas", "Neil", "Barry", "Ian", "Jason", "Iain",
			"Gordon", "Alexander", };

	
	public static final String[] SURNAMES = { "SMITH", "BROWN", "WILSON", "THOMSON", "STEWART", "ROBERTSON",
			"CAMPBELL", "ANDERSON", "SCOTT", "TAYLOR", "MACDONALD", "CLARK", "MURRAY", "REID", "MORRISON", "YOUNG",
			"WATSON", "WALKER", "MITCHELL", "PATERSON", "ROSS", "GRAHAM", "MARTIN", "MILLER", "KERR", "JOHNSTON",
			"DAVIDSON", "HENDERSON", "HUNTER", "MCDONALD", "BELL", "FRASER", "HAMILTON", "GRAY", "DUNCAN", "FERGUSON",
			"KELLY", "CAMERON", "MACKENZIE", "SIMPSON", "MACLEOD", "ALLAN", "GRANT", "MCLEAN", "BLACK", "RUSSELL",
			"WALLACE", "MACKAY", "WRIGHT", "GIBSON" };
	
	public static final String[] OTHER_NAME_TYPES = { "Nickname", "Middle Name at birth", "Middle Name",
			"Lastname at birth", "Lastname Alias", "Firstname at birth", "Firstname Alias" };
	
	public static final String[] DATES_1937_TO_1994 = { "1937-01-13", "1937-12-02", "1938-09-28", "1938-12-12", "1939-07-18",
			"1939-08-28", "1940-09-21", "1940-12-29", "1941-01-26", "1942-02-03", "1942-09-17", "1943-06-23",
			"1943-10-14", "1943-11-09", "1943-12-05", "1944-11-01", "1944-12-29", "1945-04-07", "1945-06-21",
			"1946-01-08", "1947-01-01", "1947-08-28", "1948-07-14", "1948-12-26", "1949-10-17", "1950-04-29",
			"1950-05-20", "1951-04-08", "1952-05-02", "1952-05-26", "1952-12-02", "1953-11-28", "1954-03-06",
			"1954-09-28", "1955-09-23", "1956-03-31", "1956-07-16", "1957-02-08", "1957-03-09", "1958-01-11",
			"1959-02-05", "1959-03-27", "1959-10-07", "1960-05-08", "1960-05-21", "1960-08-29", "1961-04-17",
			"1961-08-19", "1962-05-12", "1962-11-20", "1963-11-25", "1964-11-24", "1965-11-23", "1966-12-02",
			"1967-06-27", "1968-07-14", "1969-07-03", "1969-10-02", "1970-06-29", "1971-07-16", "1971-12-19",
			"1972-10-08", "1973-08-18", "1974-09-07", "1975-04-23", "1976-05-10", "1976-08-12", "1976-11-18",
			"1977-01-16", "1977-01-26", "1977-07-08", "1978-01-18", "1979-01-15", "1980-02-06", "1980-11-09",
			"1981-04-14", "1982-02-13", "1983-01-17", "1983-04-22", "1983-06-17", "1983-11-30", "1984-02-25",
			"1985-03-30", "1985-07-03", "1985-09-25", "1986-05-29", "1987-02-04", "1987-07-05", "1987-07-29",
			"1987-10-10", "1988-07-29", "1989-04-03", "1990-03-14", "1990-11-20", "1991-03-19", "1991-07-16",
			"1992-08-10", "1993-04-04", "1993-09-23", "1994-08-30" };
	
	public static int randomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static enum Gender{
		MALE,
		FEMALE		
	}
	
	public static Gender generateGender() {
		int outcome = randomInt(0, 1);
		Gender gender = null;
		switch(outcome){
			case 0: gender = Gender.MALE;
			case 1: gender = Gender.FEMALE;
		}
		return gender;
	}
	
	
	public static String generateFirstName(Gender gender) {
		String[] lookupValues;
		if (gender == Gender.MALE) {
			lookupValues = MALE_FIRSTNAMES;
		}else {
			lookupValues = FEMALE_FIRSTNAMES;
		}
		return lookupValues[randomInt(0, lookupValues.length-1)];
	}
	
	public static String generateOtherName(Gender gender) {
		return generateFirstName(gender);
	}
	
	public static String generateLastName() {
		return SURNAMES[randomInt(0, SURNAMES.length-1)];
	}
	
	public static LocalDate generateDateOfBirth() {
		String dateAsString = DATES_1937_TO_1994[randomInt(0, DATES_1937_TO_1994.length-1)];
		return LocalDate.parse(dateAsString);
	}
	
	public static long generateId() {
		long min = 99999L;
		long max = 1000000L;
		return ThreadLocalRandom.current().nextLong(min, max);
	}
	
	public static Person generatePerson(int numberOfOtherNames){
		Person person = new Person();
		Gender gender = generateGender();
		person.setId(generateId());
		person.setFirstname(generateFirstName(gender));
		person.setLastname(generateLastName());
		person.setDateOfBirth(generateDateOfBirth());	
		person.setOtherNames(generateOtherNames(gender, numberOfOtherNames));
		return person;		
	}
	
	public static Person generatePerson(boolean hasOtherNames){
		if (hasOtherNames) {
			int numberOfOtherNames = randomInt(1, 6);
			return generatePerson(numberOfOtherNames);
		}else {
			return generatePerson(0);
		}
	}
	
	public static int generateOtherNameTypeId() {
		return randomInt(0, OTHER_NAME_TYPES.length-1);
	}
	
	public static OtherNameType generateOtherNameType() {		
		int otherNameTypeId = generateOtherNameTypeId();
		return new OtherNameType(otherNameTypeId, OTHER_NAME_TYPES[otherNameTypeId]);		
	}
	
	public static List<OtherName> generateOtherNames(Gender gender){
		int numberOfOtherNames = randomInt(0, 6);
		return generateOtherNames(gender, numberOfOtherNames);
	}

	public static List<OtherName> generateOtherNames(Gender gender, int numberOfOtherNames){
		List<OtherName> otherNames = new ArrayList<>();
		for (int j=0; j < numberOfOtherNames; j++) {
			OtherName otherName = new OtherName();
			otherName.setId(generateId());
			otherName.setValue(generateOtherName(gender));
			otherName.setOtherNameType(generateOtherNameType());
			otherNames.add(otherName);
		}
		return otherNames;
	}
	
	
}
