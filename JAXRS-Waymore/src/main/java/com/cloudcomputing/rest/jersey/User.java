package com.cloudcomputing.rest.jersey;

import java.util.ArrayList;

public class User {
	private String userId;
	private String userName;
	private ArrayList<String> likeRouteIds;
	
	public User(){
		
	}
	
	public User(String id, String name){
		this.userId = id;
		this.userName = name;
	}
	
	public User(String id, String name, ArrayList<String> likeRouteIds){
		this.userId = id;
		this.userName = name;
		this.likeRouteIds = likeRouteIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<String> getLikeRouteIds() {
		return likeRouteIds;
	}

	public void setLikeRouteIds(ArrayList<String> likeRouteIds) {
		this.likeRouteIds = likeRouteIds;
	}
	
}
