package edu.wm.werewolf_client;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Play extends Activity{
	
	
	
	private String TAG = "Play";
	private Boolean isAlive;
	private Boolean isNight;
	private Boolean isWerewolf;
	private int kills;
	private final Handler myHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		
		final GetAllAlive getAllAlive = new GetAllAlive();
		final IsNight isNightInstance = new IsNight();
		final isWerewolf isWerewolfInstance = new isWerewolf();
		
		Bundle b = this.getIntent().getExtras();
		final String username = b.getString("username");
		final String password = b.getString("password");
	     
	    
		
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
				AccessUI();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				isWerewolf = isWerewolfInstance.IsWerewolf(username, password);
				if (isWerewolf){
					Log.i(TAG, username+" is a werewolf");
				}
				else{
					Log.i(TAG, username+" is not a werewolf");
				}
				AccessUI();
			}
		}).start();
		
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				isNight = isNightInstance.isNight(username, password);
				if (isNight){
					Log.i(TAG, "It is Night");
				}
				else{
					Log.i(TAG, "It is Day");
				}
				AccessUI();
			}
		}).start();
		
	}
	

	private void UpdateUI(){
		TextView isDeadText = (TextView) findViewById(R.id.isAliveText);
		isDeadText.setText("Is Alive: "+isAlive);
		
		TextView statsText = (TextView) findViewById(R.id.statsText);
		statsText.setText("Your Current Statistics");
		
		TextView nightText = (TextView) findViewById(R.id.isNightText);
		nightText.setText("Is It Night?   "+isNight);
		
		TextView werewolfText = (TextView) findViewById(R.id.typeText);
		werewolfText.setText("Is Werewolf?   "+isWerewolf);
		
		
		if (isWerewolf){
			TextView killsText = (TextView) findViewById(R.id.killsText);
			killsText.setText("Number of kills:   "+kills);
		}
		else{
			TextView killsText = (TextView) findViewById(R.id.killsText);
			killsText.setVisibility(View.GONE);
		}
		
	}
	
	final Runnable updateRunnable = new Runnable() {
        public void run() {
            //call the activity method that updates the UI
            UpdateUI();
        }
    };
    
    private void AccessUI()
    {
         //update the UI using the handler and the runnable
         myHandler.post(updateRunnable);

    }
    

	

}


