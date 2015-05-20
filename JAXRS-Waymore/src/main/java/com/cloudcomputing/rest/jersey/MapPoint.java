package com.cloudcomputing.rest.jersey;

public class MapPoint {
	
	private String mapPointId;
	private String routeId;
	private double latitude;
	private double longitude;
	private double createdTime;
	
	
	public String getMapPointId() {
		return mapPointId;
	}
	public void setMapPointId(String mapId) {
		this.mapPointId = mapId;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(double createdTime) {
		this.createdTime = createdTime;
	}
}
