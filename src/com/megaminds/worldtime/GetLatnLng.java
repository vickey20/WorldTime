package com.megaminds.worldtime;

/*This file is used to handle the XML tags. So we need to extend with DefaultHandler.
we need to override startElement, endElement & characters method .
startElemnt method called when the tag starts.
endElemnt method called when the tag ends
characters method to get characters inside tag.*/

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetLatnLng extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static Location location = null;

	public static Location getSitesList() {
		return location;
	}

	public static void setSitesList(Location location) {
	GetLatnLng.location = location;
	}


	/** Called when tag starts */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	
		currentElement = true;
		
		if (localName.equals("geonames"))
		{
			/** Start */
			location = new Location();
		} 
	
	}
	
	/** Called when tag closing */
	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
	
		currentElement = false;
		
		/** set value */
		
		if (qName.equalsIgnoreCase("lat"))
		
			location.setLat(currentValue);
			
		
		else if (qName.equalsIgnoreCase("lng"))
		
			location.setLng(currentValue);
			
		else if (qName.equalsIgnoreCase("name"))
		
			location.setName(currentValue);
	
	}
	
	
	
	/** Called to get tag characters */
	@Override
	public void characters(char[] ch, int start, int length)
	throws SAXException {
		
		if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}
	
	}

}