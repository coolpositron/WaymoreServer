package com.cloudcomputing.rest.jersey;

public class Snippet {
	private String routeId;
	private String username;
	private String title;
	private String city;
	private String keywords;
	private int likeNum;
	private double createdTime;
	
	
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(double createdTime) {
		this.createdTime = createdTime;
	}

}
