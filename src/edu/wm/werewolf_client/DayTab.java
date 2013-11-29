package edu.wm.werewolf_client;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DayTab extends Fragment{
	 
	private String TAG = "DayTab";
	private Boolean isAlive;
	private Boolean isNight;
	private Boolean isWerewolf = false;
	public String password;
	public String username;
	public ArrayList<Player> livingPlayers = new ArrayList<Player>();
	public Context context;

	private final Handler myHandler = new Handler();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.activity_day,
			        container, false);
		 
		username = UsernameAndPassword.getUsername();
		password = UsernameAndPassword.getPassword();
		
		this.context = view.getContext();
		
		final GetAllAlive getAllAlive = new GetAllAlive();
		final IsNight isNightInstance = new IsNight();
		final isWerewolf isWerewolfInstance = new isWerewolf();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				livingPlayers = getAllAlive.getLivingPlayers(username, password);
				AccessUI();
			}
		}).start();
	     
	    
		/**
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
		*/
		
		
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
		
		if ((isNight == null) || (isNight)){
			TextView isDeadText = (TextView) getView().findViewById(R.id.voteText);
			isDeadText.setText("It is night; You cannot vote now");
			
			//RadioButton optionsRadio = (RadioButton) getView().findViewById(R.id.optionsRadioButton);
			//optionsRadio.setVisibility(View.GONE);
			
			Button voteButton = (Button) getView().findViewById(R.id.voteButton);
			voteButton.setVisibility(View.GONE);
		}
		else{
			TextView isDeadText = (TextView) getView().findViewById(R.id.voteText);
			isDeadText.setText("It is daytime, you can vote now");
			
			if (!livingPlayers.isEmpty()){
				RadioGroup rg = (RadioGroup) getView().findViewById(R.id.radiogroup);//not this RadioGroup rg = new RadioGroup(this);
				rg.setOrientation(RadioGroup.VERTICAL);
				for(int i=0; i<livingPlayers.size(); i++)
				{
					RadioButton rb = new RadioButton(context);
					rg.addView(rb); 
					rb.setText(livingPlayers.get(i).getId());
				}
			}
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
