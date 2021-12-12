package com.zonesoft.addressbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OtherName {
	private long otherNameId;
	private String value;
	private Person person;
	private OtherNameType nameType;

	public long getOtherNameId() {
		return otherNameId;
	}

	public void setOtherNameId(long otherNameId) {
		this.otherNameId = otherNameId;
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

	public OtherNameType getNameType() {
		return nameType;
	}

	public void setNameType(OtherNameType nameType) {
		this.nameType = nameType;
	}
	
}
