package com.zonesoft.addressbook.entities;

public class OtherNameType {
	private long id;
	private String value;

	public OtherNameType() {
		super();
	}
	
	public OtherNameType(long id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public long getId() {
		return this.id;
	}

	public void setId(long otherNameTypeId) {
		this.id = otherNameTypeId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
