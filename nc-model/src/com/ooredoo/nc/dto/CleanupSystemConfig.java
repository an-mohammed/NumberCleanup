package com.ooredoo.nc.dto;

import java.util.List;
import java.util.Map;

public class CleanupSystemConfig {

	private Map<String, DatabaseConfigDetails> dbConfigs;
	private List<String> esmServiceUrl;
	private List<String> pcrfServiceUrl;
	private List<String> hlrServiceUrl;
	private List<String> csServiceUrl;
	private List<String> sprServiceUrl;
	
	public Map<String, DatabaseConfigDetails> getDbConfigs() {
		return dbConfigs;
	}
	public void setDbConfigs(Map<String, DatabaseConfigDetails> dbConfigs) {
		this.dbConfigs = dbConfigs;
	}
	public List<String> getEsmServiceUrl() {
		return esmServiceUrl;
	}
	public void setEsmServiceUrl(List<String> esmServiceUrl) {
		this.esmServiceUrl = esmServiceUrl;
	}
	public List<String> getPcrfServiceUrl() {
		return pcrfServiceUrl;
	}
	public void setPcrfServiceUrl(List<String> pcrfServiceUrl) {
		this.pcrfServiceUrl = pcrfServiceUrl;
	}
	public List<String> getHlrServiceUrl() {
		return hlrServiceUrl;
	}
	public void setHlrServiceUrl(List<String> hlrServiceUrl) {
		this.hlrServiceUrl = hlrServiceUrl;
	}
	public List<String> getCsServiceUrl() {
		return csServiceUrl;
	}
	public void setCsServiceUrl(List<String> csServiceUrl) {
		this.csServiceUrl = csServiceUrl;
	}
	public List<String> getSprServiceUrl() {
		return sprServiceUrl;
	}
	public void setSprServiceUrl(List<String> sprServiceUrl) {
		this.sprServiceUrl = sprServiceUrl;
	}
}
