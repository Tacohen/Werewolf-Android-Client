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
	ArrayList<String> livingPlayers;
	String TAG = "Play";
	Boolean isAlive;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		Bundle b = this.getIntent().getExtras();
		String username = b.getString("username");
		String password = b.getString("password");
		
		context = getApplicationContext();
		
		String [] usernameAndPassword = new String [2];
		
		usernameAndPassword[0] = username;
		usernameAndPassword[1] = password;
		
		try {
			livingPlayers = new GetAllAlive().execute(usernameAndPassword).get();
		} catch (InterruptedException e) {
			Log.e(TAG, "Interrupted in getting all alive players");
		} catch (ExecutionException e) {
			Log.e(TAG, "Interrupted in getting all alive players");
		}
		if (livingPlayers.contains(username)){
			
		}
		
	}
	
	
	

}


