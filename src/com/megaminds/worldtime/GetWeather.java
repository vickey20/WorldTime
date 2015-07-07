package com.megaminds.worldtime;

/*This file is used to handle the XML tags. So we need to extends with DefaultHandler.
we need to override startElement, endElement & characters method .
startElemnt method called when the tag starts.
endElemnt method called when the tag ends
characters method to get characters inside tag.*/


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetWeather extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static Weather weather = null;
	String att;
	
	public static Weather getWeather() {
		return weather;
	}


	public static void setWeather(Weather weather) {
		GetWeather.weather = weather;
	}
	
	
	/** Called when tag starts */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
	
			
		if (localName.equals("current_conditions"))
		{
			currentElement=true;
			/** Start */
			weather = new Weather();
		}	
	
		else
		{	
			att=atts.getValue("data");
			if (qName.equalsIgnoreCase("temp_c"))
			{		
				if(currentElement)
					weather.setTemp(att);
			}	
	
			else if (qName.equalsIgnoreCase("humidity"))
			{
				if(currentElement)
					weather.setHum(att);
			}
			
			else if (qName.equalsIgnoreCase("icon"))
			{
				if(currentElement)
					weather.setIcon(att);
			}
			
			else if (qName.equalsIgnoreCase("wind_condition"))
			{
				if(currentElement)
					weather.setWind(att);
			}
			
			else if (qName.equalsIgnoreCase("condition"))
			{
				if(currentElement)
					weather.setCondition(att);
			}
		}
	}
	
	/** Called when tag closing ( ex:- <name>AndroidPeople</name>
	* -- </name> )*/
	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
	
		if (localName.equals("current_conditions"))
		{
			currentElement=false;
		}
		
	
	/** set value */
	/*
		if (qName.equalsIgnoreCase("temp_c"))
		{		
			if(currentElement)
				weather.setTemp(att);
		}	
	
		else if (qName.equalsIgnoreCase("humidity"))
		{
			if(currentElement)
				weather.setHum(att);
		}
		else if (qName.equalsIgnoreCase("icon"))
		{
			if(currentElement)
				weather.setIcon(att);
		}
	*/
	}
	
	
	/** Called to get tag characters */
	@Override
	public void characters(char[] ch, int start, int length)
	throws SAXException {
	
		/*if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}*/
		
	}

}