      
   
package com.megaminds.worldtime;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ProgressDialog;
//import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;
import android.widget.Toast;


public class HomeActivity extends Activity implements TextWatcher{
    // Called when the activity is first created. 
	
	public static String DATE_FORMAT = "MMM dd, yyyy";
	public static String DATE_FORMAT_LAYMAN = "Dec 31, 2000";
	public static String TIME_FORMAT = "kk:mm";
	public static String TIME_FORMAT_LAYMAN = "24hr clock";
	
	SitesList sitesList = null;
	Location location=null;
	Weather weather=null;
	ProgressDialog pd_;
	
	EditText cityName;
    Button buttonGo;
    TextView timeView, dateView, cityView, countryView, tempResult, weatherResult;
    ImageView imageView;
    
    String country,time,lat,lng,name;
    String editTextString;
    
    private String finalDate = "", finalTime = "";
    
    Boolean isInternetAvailable; 
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	outState.putString("CITY_NAME", editTextString);
    	outState.putBoolean("INTERNET", CheckInternet());
    }
    
	//ConnectivityManager connectivity;
	//NetworkInfo wifiInfo, mobileInfo;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
            
        cityName = (EditText)findViewById(R.id.editTextCityName);
        buttonGo = (Button)findViewById(R.id.buttonGo);
        timeView = (TextView)findViewById(R.id.textViewTime);
        dateView = (TextView)findViewById(R.id.textViewDate);
        cityView = (TextView)findViewById(R.id.textViewCity);
        countryView = (TextView)findViewById(R.id.textViewCountry);
        tempResult = (TextView)findViewById(R.id.tempView);
        weatherResult = (TextView)findViewById(R.id.weatherView);
        imageView = (ImageView)findViewById(R.id.weatherImage);
        	
        cityName.addTextChangedListener(this);

        if( cityName.getText().toString().length() > 0 ){
        	buttonGo.setEnabled(true);
        }
        else{
        	buttonGo.setEnabled(false);
        }
        
        	
        if( null != savedInstanceState ){
        	Boolean b = savedInstanceState.getBoolean("INTERNET");
        	if ( b ){
        		isInternetAvailable = b;
        		String str = savedInstanceState.getString("CITY_NAME");
            	if ( str != null && str.length() > 0 ){
            		editTextString = str;
            		new BackgroundTask().execute();
            	}
        	}
        }
        
        isInternetAvailable = CheckInternet();
        Log.d("network", "internet connected :: " + isInternetAvailable );
	    
	    buttonGo.setOnClickListener(new Button.OnClickListener(){
	    	public void onClick(View v){

	    		editTextString = cityName.getText().toString();
	    		
	    		if( CheckInternet() ){
		    		
		    		new BackgroundTask().execute();
	    		}
	    		else{
	    			
	    			Toast.makeText(HomeActivity.this, "Sorry! No network connection", Toast.LENGTH_LONG).show();
	    		}

	    		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
	    		imm.hideSoftInputFromWindow(buttonGo.getWindowToken(), 0);
	    	}
	    });
	    
	}

	public boolean CheckInternet() 
	{
	    ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

	    // Here if condition check for wifi and mobile network is available or not.
	    // If anyone of them is available or connected then it will return true, otherwise false;

	    if (wifi.isConnected()) {
	        return true;
	    } else if (mobile.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	public void getRequest(){
    	
		String cityname = editTextString;
		String newcityname=cityname;
        	
        for(int i=0;i<cityname.length();i++)
        {	
        	if(cityname.charAt(i)==' ')
        	{
        		String cityname1=cityname.substring(0,i);
        		String cityname2=cityname.substring(i+1);
        		newcityname=cityname1+"%20"+cityname2;
        	}
	        		
        }
	        
        try {
        	
           	String url="http://api.geonames.org/search?q="+newcityname+"&maxRows=1&username=vickey";
        	URL newurl = new URL(url);		
        	// Handling XML 
        	SAXParserFactory spf = SAXParserFactory.newInstance();
        	SAXParser sp = spf.newSAXParser();
        	XMLReader xr = sp.getXMLReader();

        	// Send URL to parse XML Tags 
        	
        	// Create handler to handle XML Tags ( extends DefaultHandler ) 
        	GetLatnLng myXMLHandler = new GetLatnLng();
        	xr.setContentHandler(myXMLHandler);
        	xr.parse(new InputSource(newurl.openStream()));
        }
        catch (Exception e) {
        	System.out.println("XML Pasing Excpetion = " + e);
        }

        // Get result from MyXMLHandler Location Object 
        location = GetLatnLng.location;
        
        // Get the latitude and longitude values 
        lat = location.getLat();
        lng = location.getLng();
        name = location.getName();
       // id = location.getId();
        
        try {
        	String url="http://api.geonames.org/timezone?lat="+lat+"&lng="+lng+"&username=vickey";
        	
           	URL newurl = new URL(url);		
        	// Handling XML 
        	SAXParserFactory spf = SAXParserFactory.newInstance();
        	SAXParser sp = spf.newSAXParser();
        	XMLReader xr = sp.getXMLReader();

        	// Send URL to parse XML Tags 
        	
        	// Create handler to handle XML Tags ( extends DefaultHandler ) 
        	MyXMLHandler myXMLHandler = new MyXMLHandler();
        	xr.setContentHandler(myXMLHandler);
        	xr.parse(new InputSource(newurl.openStream()));
        }
        catch (Exception e) {
        	System.out.println("XML Pasing Excpetion = " + e);
        }

        // Get result from MyXMLHandler SitlesList Object 
        sitesList = MyXMLHandler.sitesList;
        
        // Get the country and time values 
        country = sitesList.getCountry();
        time = sitesList.getTime();
    
        try {
        	
           	String url="http://www.google.com/ig/api?weather="+newcityname;
        	URL newurl = new URL(url);		
        	// Handling XML 
        	SAXParserFactory spf = SAXParserFactory.newInstance();
        	SAXParser sp = spf.newSAXParser();
        	XMLReader xr = sp.getXMLReader();

        	// Send URL to parse XML Tags 
        	
        	// Create handler to handle XML Tags ( extends DefaultHandler ) 
        	GetWeather myXMLHandler = new GetWeather();
        	xr.setContentHandler(myXMLHandler);
        	xr.parse(new InputSource(newurl.openStream()));
        }
        catch (Exception e) {
        	System.out.println("XML Pasing Excpetion = " + e);
        }

        // Get result from MyXMLHandler Weather Object 
        weather = GetWeather.weather;

    	// Set the layout view to display 

        }     
        
        private Drawable LoadImageFromWebOperations(String url)
        {
        	try
        	{
        		InputStream is = (InputStream) new URL(url).getContent();
        		Drawable d = Drawable.createFromStream(is, "src name");
        		return d;
        	}
        	catch (Exception e) {
        		System.out.println("Exc="+e);
        		return null;
        	}
        }

        public void setViews() {
        	String date = null, time1 = null;
        	
        	/*if( time != null && time.length() > 0 ){
        		for(int i=0;i<time.length();i++)
                {
                	if(time.charAt(i)==' ')
                	{
                		date=time.substring(0,i);
                		time1=time.substring(i+1);
                   	}
                		
                }
        	}*/
            
        	SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        	SimpleDateFormat dateOutputFormat = new SimpleDateFormat( DATE_FORMAT );
        	SimpleDateFormat timeOutputFormat = new SimpleDateFormat(TIME_FORMAT);
        	
        	Date parsed = new Date();
        	try {
				parsed = inputFormat.parse(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	date = dateOutputFormat.format(parsed);
        	
        	time1 = timeOutputFormat.format(parsed);
        	
        	
            if( time1 != null && time1.length() > 0 ){
            	timeView.setText(time1);
            }
            if( date != null && date.length() > 0 ){
            	dateView.setText(date);
            }
            if( name != null && name.length() > 0 ){
            	cityView.setText(name);
            }
            if( country != null && country.length() > 0 ){
            	countryView.setText(country);
            }
/*
            if( null == weather ){
            	pd_.dismiss();
            	runOnUiThread(new Runnable() {
    				
    				public void run() {

    					Toast.makeText(HomeActivity.this, "Could not fetch weather. Try again later.", Toast.LENGTH_LONG).show();
    				}
    			});
            	return;
            }	
            else{
            	
    	        // Get the temperature and humidity values 
    	        String temp = weather.getTemp();
    	        String hum = weather.getHum();
    	        String condition = weather.getCondition(); 
    	        String wind = weather.getWind();
    	        String icon = weather.getIcon();
    	        
    	        sb3.append(temp+"°C");
    	    	tempResult.setText(sb3);
    	    	
    	    	sb4.append(condition);
    	    	sb4.append("\n");
    	    	sb4.append(hum);
    	    	sb4.append("\n");
    	    	sb4.append(wind);
    	    	weatherResult.setText(sb4);
    	    	
    	    	
    	    	String imageUrl="http://google.com"+icon;
    	    	
    	    	Drawable drawable = LoadImageFromWebOperations(imageUrl);
    	    	imageView.setImageDrawable(drawable);
    	    	
            }*/
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    	super.onCreateOptionsMenu(menu);
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.menu, menu);
	    	return true;
	    }
	
	    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
	    	case R.id.about:
	    		startActivity(new Intent(this, About.class));
	    		break;
	    		
	    	case R.id.settings:
	    		startActivity(new Intent(this, Settings.class));
	    		break;
	    	}
	    	return false;
	    }
	    
    
    class BackgroundTask extends AsyncTask<Void, Void, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd_ = ProgressDialog.show(HomeActivity.this, null , "please wait...", true, false);
		}
    	
		@Override
		protected String doInBackground(Void... params) {
			
			getRequest();
			return null;
		}
    	
		protected void onPostExecute(String result) {
		
			setViews();
			pd_.dismiss();
		
		};
    }


	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		if( cityName.length() > 0 ){
			buttonGo.setEnabled(true);
		}
		else{
			buttonGo.setEnabled(false);
		}
	}
    
}