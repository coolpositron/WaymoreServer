package com.cloudcomputing.rest.jersey;

public class Route {
	
	private String routeId;
	private String userId;
	private String city;
	private String title;
	private String keywords;
	private boolean sharedFlag;
	private double createdTime;
	private double lastModifiedTime;
	
	public Route(){
		
	}
	
	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public boolean getSharedFlag() {
		return sharedFlag;
	}

	public void setSharedFlag(boolean sharedFlag) {
		this.sharedFlag = sharedFlag;
	}

	public double getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(double createdTime) {
		this.createdTime = createdTime;
	}

	public double getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(double lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

}
