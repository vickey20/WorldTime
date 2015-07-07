package com.megaminds.worldtime;

/*This file is used to handle the XML tags. So we need to extends with DefaultHandler.
we need to override startElement, endElement & characters method .
startElemnt method called when the tag starts.
endElemnt method called when the tag ends
characres method to get characters inside tag.*/

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyXMLHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static SitesList sitesList = null;
	
	public static SitesList getSitesList() {
		return sitesList;
	}
	
	public static void setSitesList(SitesList sitesList) {
		MyXMLHandler.sitesList = sitesList;
	}
	
	
	/** Called when tag starts */
	@Override
	public void startElement(String uri, String localName, String qName,
	Attributes attributes) throws SAXException {
	
		currentElement = true;
		
		if (localName.equals("geonames"))
		{
			/** Start */
			sitesList = new SitesList();
		} 

}

	/** Called when tag closing */
	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
	
		currentElement = false;
		
		/** set value */
		if (localName.equalsIgnoreCase("countryName"))
			sitesList.setCountry(currentValue);
		else if (localName.equalsIgnoreCase("time"))
			sitesList.setTime(currentValue);
	
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