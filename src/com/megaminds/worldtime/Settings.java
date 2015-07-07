
package com.megaminds.worldtime;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;

public class Settings extends PreferenceActivity implements OnPreferenceChangeListener {

	private ListPreference dateFormatPref, timeFormatPref;
	private final String DATE_FORMAT = "Date_Format";
	private final String TIME_FORMAT = "Time_Format";
	
	private final String TIME_24HR = "kk:mm";
	private final String TIME_12HR = "hh:mm a";
	
	public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        
        setPreferenceScreen( createPreferenceHierarchy() );       
        setPrefUIText();
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		setPrefUIText();
	}
	
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	
	private PreferenceScreen createPreferenceHierarchy() {		
		// Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen( this );
        
        // Dialog based preferences
        PreferenceCategory dialogBasedPrefCat = new PreferenceCategory( this );
        dialogBasedPrefCat.setTitle( "Settings" );
        root.addPreference( dialogBasedPrefCat );
        
        timeFormatPref = new ListPreference( this );
        timeFormatPref.setEntries( R.array.time_format );
        timeFormatPref.setEntryValues( R.array.time_format );
        timeFormatPref.setDialogTitle( "Time Format" );
        timeFormatPref.setKey( TIME_FORMAT );
        timeFormatPref.setTitle( "Time Format" );
        timeFormatPref.setPersistent( false );
        timeFormatPref.setDefaultValue( HomeActivity.TIME_FORMAT );       
        timeFormatPref.setOnPreferenceChangeListener( this );
        dialogBasedPrefCat.addPreference( timeFormatPref );
        
        dateFormatPref = new ListPreference( this );
        dateFormatPref.setEntries( R.array.date_format );
        dateFormatPref.setEntryValues( R.array.date_format );
        dateFormatPref.setDialogTitle( "Date Format" );
        dateFormatPref.setKey( DATE_FORMAT );
        dateFormatPref.setTitle( "Date Format" );
        dateFormatPref.setPersistent( false );
        dateFormatPref.setDefaultValue( HomeActivity.DATE_FORMAT );       
        dateFormatPref.setOnPreferenceChangeListener( this );
        dialogBasedPrefCat.addPreference( dateFormatPref );
        
        return root;
	}
	
	
	public boolean onPreferenceChange( Preference preference, Object newValue ) {
		if( preference.getKey().equals( DATE_FORMAT ) ) {
			HomeActivity.DATE_FORMAT_LAYMAN = newValue.toString();
			HomeActivity.DATE_FORMAT = matchDateFormat(newValue.toString());
		}
		if( preference.getKey().equals( TIME_FORMAT ) ) {
			HomeActivity.TIME_FORMAT = matchTimeFormat( newValue.toString() );
		}
		
		setPrefUIText();
		return true;
	}
	
	public String matchTimeFormat( String time ) {
		String timeFormat = "";
		
		if( time.equals("24hr clock") ){
			timeFormat = TIME_24HR;
		}
		else if(time.equals("12hr clock")){
			timeFormat = TIME_12HR;
		}
		
		return timeFormat;
	}
	
	public String matchDateFormat( String date ) {
		String dateFormat = "";
		
		if( date.equals("31-12-2000") ){
			dateFormat = "dd-MM-yyyy";
		}
		else if(date.equals("12-31-2000")){
			dateFormat = "MM-dd-yyyy";
		}
		else if(date.equals("2000-12-31")){
			dateFormat = "yyyy-MM-dd";
		}
		else if(date.equals("31 Dec, 2000")){
			dateFormat = "dd MMM, yyyy";
		}
		else if(date.equals("Dec 31, 2000")){
			dateFormat = "MMM dd, yyyy";
		}
		else if(date.equals("Sun, Dec 31")){
			dateFormat = "EEE, MMM dd";
		}
		else if(date.equals("Sunday, Dec 31")){
			dateFormat = "EEEE, MMM dd";
		}
		
		return dateFormat;
	}
	
	public void setPrefUIText() {
		dateFormatPref.setSummary( HomeActivity.DATE_FORMAT_LAYMAN );
		dateFormatPref.setValue(HomeActivity.DATE_FORMAT_LAYMAN);
		timeFormatPref.setSummary( HomeActivity.TIME_FORMAT_LAYMAN );
		timeFormatPref.setValue(HomeActivity.TIME_FORMAT_LAYMAN);
	}
}