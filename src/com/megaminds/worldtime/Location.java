package com.megaminds.worldtime;


/** Contains getter and setter method for variables */
public class Location {

	private String lat = new String();	
	private String lng = new String();
	private String name = new String();
	
	public String getLat() {
		return lat;
	}
	
	public void setLat(String lat) {
		this.lat=lat;
	}
	
	public String getLng() {
		return lng;
	}
	
	public void setLng(String lng) {
		this.lng=lng;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}

}