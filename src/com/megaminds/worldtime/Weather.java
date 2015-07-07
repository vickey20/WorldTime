package com.megaminds.worldtime;

public class Weather {

	private String temp = new String();	
	private String hum = new String();
	private String icon = new String();
	private String wind = new String();
	private String condition = new String();
	
	public String getTemp() {
		return temp;
	}
	
	public void setTemp(String temp) {
		this.temp=temp;
	}
	
	public String getHum() {
		return hum;
	}
	
	public void setHum(String hum) {
		this.hum=hum;
	}
	
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon=icon;
	}
	
	public String getWind() {
		return wind;
	}
	
	public void setWind(String wind) {
		this.wind=wind;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition=condition;
	}

}