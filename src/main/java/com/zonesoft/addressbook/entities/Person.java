package com.zonesoft.addressbook.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Person {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return json;
	}
}
