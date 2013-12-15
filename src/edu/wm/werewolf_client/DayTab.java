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

public class DayTab extends Fragment{
	 
	private String TAG = "DayTab";
	private Boolean isAlive;
	private Boolean isNight;
	private Boolean isWerewolf = false;
	public String password;
	public String username;
	public ArrayList<Player> livingPlayers = new ArrayList<Player>();
	public Context context;
	public Vote voteInstance; 
	public boolean voted;

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
		voteInstance = new Vote();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				livingPlayers = getAllAlive.getLivingPlayers(username, password);
				AccessUI();
			}
		}).start();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				isNight = isNightInstance.isNight(username, password);
				if (isNight){
					voted = false;//reset
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

			if ((isNight == null) || (isNight)){
				TextView isDeadText = (TextView) getView().findViewById(R.id.voteText);
				isDeadText.setText("It is night. You cannot vote now");

				Button voteButton = (Button) getView().findViewById(R.id.voteButton);
				voteButton.setVisibility(View.GONE);
			}
			else{
				TextView isDeadText = (TextView) getView().findViewById(R.id.voteText);
				isDeadText.setText("It is daytime, you can vote now");

				if (voted){
					isDeadText.setText("You already voted today");
					Button voteButton = (Button) getView().findViewById(R.id.voteButton);
					voteButton.setVisibility(View.GONE);
				}

				if ((!livingPlayers.isEmpty()) && (!voted)){
					final RadioGroup rg = (RadioGroup) getView().findViewById(R.id.radiogroup);//not this RadioGroup rg = new RadioGroup(this);
					rg.removeAllViews();//prevent duplicates
					rg.setOrientation(RadioGroup.VERTICAL);
					for(int i=0; i<livingPlayers.size(); i++)
					{
						RadioButton rb = new RadioButton(context);
						rg.addView(rb); 
						rb.setText(livingPlayers.get(i).getId());
					}

					Button voteButton = (Button) getView().findViewById(R.id.voteButton);
					voteButton.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							Log.i(TAG, "Vote Button Pressed!");
							Log.i(TAG, "Result from button was: "+rg.getCheckedRadioButtonId());
							// find the radiobutton by returned id
							final Button selectedPlayerButton = (RadioButton) getView().findViewById(rg.getCheckedRadioButtonId());
							final String selectedPlayer = selectedPlayerButton.getText().toString();
							Log.i(TAG, "selected player was: "+selectedPlayer);
							new Thread(new Runnable() {
								@Override
								public void run() {
									voteInstance.vote(selectedPlayer);
									Log.i(TAG, "Voted for "+selectedPlayer);
								}
							}).start();
							Toast.makeText(context, "Thank you for voting! You can only vote once per day", Toast.LENGTH_LONG).show();
							Button voteButton = (Button) getView().findViewById(R.id.voteButton);
							voteButton.setVisibility(View.GONE);
							voted = true;

						}
					});

				}
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
