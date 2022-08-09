package com.ooredoo.nc.dto;

import java.io.Serializable;

public class DatabaseConfigDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String dbHost;
	private String dbPort;
	private String dbServicename;
	private String user;
	private String password;
	
	public String getDbHost() {
		return dbHost;
	}
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}
	public String getDbPort() {
		return dbPort;
	}
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	public String getDbServicename() {
		return dbServicename;
	}
	public void setDbServicename(String dbServicename) {
		this.dbServicename = dbServicename;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
