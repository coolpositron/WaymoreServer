package com.cloudcomputing.rest.jersey;

public class Like {
	private String userId;
	private String routeId;
	private boolean likeFlag;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public boolean isLikeFlag() {
		return likeFlag;
	}
	public void setLikeFlag(boolean likeFlag) {
		this.likeFlag = likeFlag;
	}
}
