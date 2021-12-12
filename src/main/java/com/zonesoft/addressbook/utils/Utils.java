package com.zonesoft.addressbook.utils;

import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonesoft.addressbook.entities.Person;

public class Utils {
	
	public static LocalDate convertToLocalDate(String dateAsString) {
		return LocalDate.parse(dateAsString);
	}
	

}
