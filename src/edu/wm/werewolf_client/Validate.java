package edu.wm.werewolf_client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Validate extends Activity{
	
	Context context;
	String TAG = "Validate";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validate);
		
		context = getApplicationContext();
		
		Bundle b = this.getIntent().getExtras();
		String username = b.getString("username");
		String password = b.getString("password");
		
		Log.i(TAG,"Username is: "+username);
		Log.i(TAG, "password is: "+password);
		
		connectToServer(username,password);
	}

	private void connectToServer(String username, String password) {
		
		String [] usernameAndPassword = new String [2];
		
		usernameAndPassword[0] = username;
		usernameAndPassword[1] = password;
		
		new ValidateInBackground().execute(usernameAndPassword);
		
		Intent playIntent = new Intent(Validate.this,Play.class);
		Bundle b = new Bundle();
		b.putString("username", username);
		b.putString("password",password);
		playIntent.putExtras(b);
		startActivityForResult(playIntent, 0);
		
	}

}
