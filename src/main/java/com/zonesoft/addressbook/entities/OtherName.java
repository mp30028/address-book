package com.zonesoft.addressbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OtherName {
	private long id;
	private String value;
	private Person person;
	private OtherNameType nameType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@JsonIgnore
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public OtherNameType getOtherNameType() {
		return nameType;
	}

	public void setOtherNameType(OtherNameType nameType) {
		this.nameType = nameType;
	}
	
}
