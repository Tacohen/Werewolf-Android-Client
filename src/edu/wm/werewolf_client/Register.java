package edu.wm.werewolf_client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity{
	
	Context context;
	String TAG = "Register";
	String username;
	String password;
	String passwordRetyped;
	String email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		context = getApplicationContext();
		
		Button registerButton = (Button)findViewById(R.id.registerButtonRegister);
		registerButton.setOnClickListener(registerListener);
	}
	
	View.OnClickListener registerListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			final EditText usernameText = (EditText) findViewById(R.id.usernameTextEditRegister);
			username = usernameText.getText().toString();
			Log.i(TAG, "Username is : "+username);
			
			final EditText passwordText = (EditText) findViewById(R.id.passwordTextEditRegister);
			password = passwordText.getText().toString();
			Log.i(TAG, "Password is : "+password);
			
			final EditText passwordRetypedText = (EditText) findViewById(R.id.passwordTextEditRetype);
			passwordRetyped = passwordRetypedText.getText().toString();
			Log.i(TAG, "Password Retyped is : "+passwordRetyped);
			
			final EditText emailText = (EditText) findViewById(R.id.emailTextEdit);
			email = emailText.getText().toString();
			Log.i(TAG, "Email is : "+email);
			
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
					if (!passwordRetyped.equals(password)){
						Toast toast = Toast.makeText(context, "Password Fields Must Match", Toast.LENGTH_LONG);
						toast.show();
					}
					else{
						if (email.isEmpty()){
							Toast toast = Toast.makeText(context, "Please Enter an Email Address", Toast.LENGTH_LONG);
							toast.show();
						}
						else{
							Intent registerIntent = new Intent(Register.this,ServerRegistration.class);
							Bundle b = new Bundle();
							b.putString("username", username);
							b.putString("password",password);
							b.putString("email", email);
							registerIntent.putExtras(b);
							startActivityForResult(registerIntent, 0);	
						}
					}
						
				}

			}
			
			
		}
	};

}
