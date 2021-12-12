package com.zonesoft.addressbook.entities;

public class OtherNameType {
	private long otherNameTypeId;
	private String value;

	public OtherNameType() {
		super();
	}
	
	public OtherNameType(long id, String value) {
		super();
		this.otherNameTypeId = id;
		this.value = value;
	}
	
	public long getOtherNameTypeId() {
		return this.otherNameTypeId;
	}

	public void setOtherNameTypeId(long otherNameTypeId) {
		this.otherNameTypeId = otherNameTypeId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
