package edu.wm.werewolf_client;

import edu.wm.werewolf_client.FindLocation.LocationResult;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class Locate extends Activity {

	public static final String TAG = "DetectLocation";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detect_location);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		LocationResult locationResult = new LocationResult(){
			@Override
			public void gotLocation(Location location){

				if (location == null){
					//If we failed to find the location for some reason, show the user an alert dialog
					Log.w(TAG,"failed to get location!");
				}
				else{
					//Got the location!
					double lat = (double) (location.getLatitude());
					double lng = (double) (location.getLongitude());

					Log.v(TAG,"latitude is: "+lat);
					Log.v(TAG,"longitude is: "+lng);


				}
			}

		};
		FindLocation myLocation = new FindLocation();
		myLocation.getLocation(this, locationResult);

	}

}