package edu.wm.werewolf_client;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserTab extends Fragment{
	 

	
	private String TAG = "Play";
	private Boolean isAlive;
	private Boolean isNight;
	private Boolean isWerewolf = false;
	public String password;
	public String username;


	private int kills;
	private final Handler myHandler = new Handler();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.activity_play,
			        container, false);
		 
		username = UsernameAndPassword.getUsername();
		password = UsernameAndPassword.getPassword();
		
		final GetAllAlive getAllAlive = new GetAllAlive();
		final IsNight isNightInstance = new IsNight();
		final isWerewolf isWerewolfInstance = new isWerewolf();
	     
	    
		
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
		
		return view;
	}
	

	private void UpdateUI(){

		try{
			TextView isDeadText = (TextView) getView().findViewById(R.id.isAliveText);
			isDeadText.setText("Is Alive: "+isAlive);

			TextView statsText = (TextView) getView().findViewById(R.id.statsText);
			statsText.setText("Your Current Statistics");

			TextView nightText = (TextView) getView().findViewById(R.id.isNightText);
			nightText.setText("Is It Night?   "+isNight);

			TextView werewolfText = (TextView) getView().findViewById(R.id.typeText);
			werewolfText.setText("Is Werewolf?   "+isWerewolf);


			if (isWerewolf){
				TextView killsText = (TextView) getView().findViewById(R.id.killsText);
				killsText.setText("Number of kills:   "+kills);
			}
			else{
				TextView killsText = (TextView) getView().findViewById(R.id.killsText);
				killsText.setVisibility(View.GONE);
			}
		}catch (NullPointerException e){
			Log.e(TAG, "Null Pointer!");
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
    
	public void setPassword(String password) {
		this.password = password;
	}


	public void setUsername(String username) {
		this.username = username;
	}
}
