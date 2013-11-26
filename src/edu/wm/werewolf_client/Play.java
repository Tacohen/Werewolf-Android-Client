package edu.wm.werewolf_client;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Play extends Activity{
	
	Context context;
	Location location;
	ArrayList<Player> livingPlayers;
	String TAG = "Play";
	Boolean isAlive;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		final GetAllAlive getAllAlive = new GetAllAlive();
		
		Bundle b = this.getIntent().getExtras();
		final String username = b.getString("username");
		final String password = b.getString("password");
		
		context = getApplicationContext();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				isAlive = getAllAlive.isSpecificPlayerAlive(username, username, password);
				if (isAlive){
					Log.i(TAG, username+" is alive");
				}
				else{
					Log.i(TAG, username+" is not alive");
				}
			}
		}).start();
		/**
		if (livingPlayers == null) {
			Log.i(TAG, "Living players is null!");
		}
		else if (livingPlayers.isEmpty()){
			Log.i(TAG, "Living players is empty!");
		}
		else if (livingPlayers.contains(username)){
			Log.i(TAG, username+" is in living players!");
		}
		else{
			Log.i(TAG, username+" is not in living players!");
		}
		*/
		
	}
	
	
	

}


