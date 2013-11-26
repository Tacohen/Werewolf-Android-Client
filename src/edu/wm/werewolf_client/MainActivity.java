package edu.wm.werewolf_client;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	String username;
	String password;
	Context context;
	String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = getApplicationContext();
		
		Button loginButton = (Button)findViewById(R.id.loginButton);
		Button registerButton = (Button)findViewById(R.id.registerButton);
		
		loginButton.setOnClickListener(loginListener);
		registerButton.setOnClickListener(registerListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	View.OnClickListener loginListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			final EditText usernameText = (EditText) findViewById(R.id.usernameTextEdit);
			username = usernameText.getText().toString().trim();
			UsernameAndPassword.setUsername(username);
			Log.i(TAG, "Username is :"+username);
			
			final EditText passwordText = (EditText) findViewById(R.id.passwordTextEdit);
			password = passwordText.getText().toString().trim();
			UsernameAndPassword.setPassword(password);
			Log.i(TAG, "Password is :"+password);
			
			if (username.isEmpty()){
				Toast toast = Toast.makeText(context, "Please Enter a Username", Toast.LENGTH_LONG);
				toast.show();
			}
			else{
				if (password.isEmpty()){
					Toast toast = Toast.makeText(context, "Please Enter a Password", Toast.LENGTH_LONG);
					toast.show();
				}
				else{
					Intent validateIntent = new Intent(MainActivity.this,Validate.class);
					startActivityForResult(validateIntent, 0);
				}

			}

		}
	};
	
	View.OnClickListener registerListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent registerIntent = new Intent(MainActivity.this,Register.class);
        	startActivityForResult(registerIntent, 0);	
		}
	};

}
