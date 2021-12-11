package com.zonesoft.addressbook.db;

public class Properties {
	private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	private String host = "localhost";
	private int port = 3306;
	private String username = "abuser";
	private String password = "P^ssword*001";
	private String database = "addressbook";
	
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getDatabase() {
		return database;
	}
}
