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
import android.widget.Toast;

public class NightTab extends Fragment{
	 

	
	private String TAG = "Play";
	private Boolean isNight = false;
	private Boolean isWerewolf = false;
	public String password;
	public String username;
	public ArrayList<String> targets = new ArrayList<String>();
	public Context context;

	private int kills;
	private final Handler myHandler = new Handler();
	
	public KillAttempt kill;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.activity_night,
			        container, false);
		 
		username = UsernameAndPassword.getUsername();
		password = UsernameAndPassword.getPassword();
		
		final GetAllAlive getAllAlive = new GetAllAlive();
		final IsNight isNightInstance = new IsNight();
		final isWerewolf isWerewolfInstance = new isWerewolf();
		kill = new KillAttempt();
		
		this.context = view.getContext();
		
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
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				targets = kill.FindTargets();
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
		
		return view;
	}
	
	
	private void UpdateUI(){
		
		try{
		
		if ((!isNight) && (isWerewolf)){
			TextView killText = (TextView) getView().findViewById(R.id.killText);
			killText.setText("It is day. You cannot kill now. Pretend to be a townsperson and vote");
			
			final RadioGroup rg = (RadioGroup) getView().findViewById(R.id.killRadiogroup);//not this RadioGroup rg = new RadioGroup(this);
			rg.setVisibility(View.GONE);
			
			Button killButton = (Button) getView().findViewById(R.id.killButton);
			killButton.setVisibility(View.GONE);
		}
		else if ((!isWerewolf) && (isNight)){
			TextView killText = (TextView) getView().findViewById(R.id.killText);
			killText.setText("You are not a Werewolf. You cannot kill, so please be safe until morning");
			
			final RadioGroup rg = (RadioGroup) getView().findViewById(R.id.killRadiogroup);//not this RadioGroup rg = new RadioGroup(this);
			rg.setVisibility(View.GONE);
			Button killButton = (Button) getView().findViewById(R.id.killButton);
			killButton.setVisibility(View.GONE);
			
			
		}else if ((!isWerewolf) && (!isNight)){
			TextView killText = (TextView) getView().findViewById(R.id.killText);
			killText.setText("It is day. Please go vote for whomever you think killed your fellow townspeople!");
			
			final RadioGroup rg = (RadioGroup) getView().findViewById(R.id.killRadiogroup);//not this RadioGroup rg = new RadioGroup(this);
			rg.setVisibility(View.GONE);
			
			Button killButton = (Button) getView().findViewById(R.id.killButton);
			killButton.setVisibility(View.GONE);
			
		}
		else{
			//If they are a werewolf and it's night...
			Button killButton = (Button) getView().findViewById(R.id.killButton);
			
			if (targets.isEmpty()){
				TextView killText = (TextView) getView().findViewById(R.id.killText);
				killText.setText("There are no townspeople near you to kill. Go find some!");
				killButton.setVisibility(View.GONE);
			}
			else{
				//if there are people to kill
				final RadioGroup rg = (RadioGroup) getView().findViewById(R.id.killRadiogroup);//not this RadioGroup rg = new RadioGroup(this);
				rg.removeAllViews();//prevent duplicates
				rg.setOrientation(RadioGroup.VERTICAL);
				for(int i=0; i< targets.size(); i++)
				{
					RadioButton rb = new RadioButton(context);
					rg.addView(rb); 
					rb.setText(targets.get(i));
				}
				killButton.setOnClickListener(new View.OnClickListener() {
		             public void onClick(View v) {
		                 Log.i(TAG, "Kill Button Pressed!");
		                 Log.i(TAG, "Result from button was: "+rg.getCheckedRadioButtonId());
		                 // find the radiobutton by returned id
		                 final Button selectedPlayerButton = (RadioButton) getView().findViewById(rg.getCheckedRadioButtonId());
		                 final String selectedPlayer = selectedPlayerButton.getText().toString();
		                 Log.i(TAG, "selected player was: "+selectedPlayer);
		                 new Thread(new Runnable() {
		         			@Override
		         			public void run() {
		         				kill.MakeKillAttempt(selectedPlayer);
		         				Log.i(TAG, "Voted for "+selectedPlayer);
		         			}
		         		}).start();
		                Toast.makeText(context, "KillMade", Toast.LENGTH_LONG).show();
		                if (rg.getChildCount() == 0){
		                	rg.setVisibility(View.GONE);
		                }
		             }
		         });
				
			}
		}
	}catch(NullPointerException e){
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
	
}
