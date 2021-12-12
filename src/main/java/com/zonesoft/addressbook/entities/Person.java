package com.zonesoft.addressbook.entities;

import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Person {
	private static final Logger LOGGER = Logger.getLogger(Person.class);
	private long personId;
	private String firstname;
	private String lastname;
	private LocalDate dateOfBirth;
	private List<OtherName> otherNames;

	public long getPersonId() {
		return personId;
	}

	public void sePersonId(long id) {
		this.personId = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<OtherName> getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(List<OtherName> otherNames) {
		this.otherNames = otherNames;
	}
	
	@JsonIgnore
	public String toJsonString() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			String message = "<EXCEPTION - whilst writing Person Object to JSON. " + e.getLocalizedMessage() + ">" ;
			LOGGER.error(message);
			return message;
		}
		return json;
	}
}
