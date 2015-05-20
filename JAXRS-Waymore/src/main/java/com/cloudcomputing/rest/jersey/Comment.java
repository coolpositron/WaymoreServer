package com.cloudcomputing.rest.jersey;

public class Comment {
	
	private String commentId;
	private String routeId;
	private String userId;
	private String userName;
	private String content;
	private double createdTime;
	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(double createdTime) {
		this.createdTime = createdTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	 
}
