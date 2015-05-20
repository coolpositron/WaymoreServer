package com.cloudcomputing.rest.jersey;

public class KeyPoint {
	private String keyPointId;
	private String routeId;
	private double longitude;
	private double latitude;
	private String title;
	private String notation;
	private String photoUrl;
	
	public String getKeyPointId() {
		return keyPointId;
	}
	public void setKeyPointId(String keyPointId) {
		this.keyPointId = keyPointId;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNotation() {
		return notation;
	}
	public void setNotation(String notation) {
		this.notation = notation;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
