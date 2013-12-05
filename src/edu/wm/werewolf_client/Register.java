package edu.wm.werewolf_client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.*;

import edu.wm.werewolf_client.FindLocation.LocationResult;

public class Register extends Activity{
	
	static Context context;
	static String TAG = "Register";
	String username;
	String password;
	String passwordRetyped;
	String email;
	double lat;
	double lng;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		context = getApplicationContext();
		
		Button registerButton = (Button)findViewById(R.id.registerButtonRegister);
		registerButton.setOnClickListener(registerListener);
		
		LocationResult locationResult = new LocationResult(){
			@Override
			public void gotLocation(Location location){

				if (location == null){
					//If we failed to find the location for some reason, show the user an alert dialog
					Log.w(TAG,"failed to get location!");
				}
				else{
					//Got the location!
					lat = (double) (location.getLatitude());
					lng = (double) (location.getLongitude());

					Log.v(TAG,"latitude is: "+lat);
					Log.v(TAG,"longitude is: "+lng);


				}
			}

		};
		FindLocation myLocation = new FindLocation();
		myLocation.getLocation(this, locationResult);
	}
	
	View.OnClickListener registerListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			final EditText usernameText = (EditText) findViewById(R.id.usernameTextEditRegister);
			username = usernameText.getText().toString();
			UsernameAndPassword.setUsername(username);
			Log.i(TAG, "Username is : "+username);
			
			final EditText passwordText = (EditText) findViewById(R.id.passwordTextEditRegister);
			password = passwordText.getText().toString();
			UsernameAndPassword.setPassword(password);
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
							
							Log.i(TAG, "About to post new registration to server");
							
							new Thread(new Runnable() {

					            @Override
					            public void run() {
					            	makeRequest("http://powerful-depths-2851.herokuapp.com/users/register?username=",username,password,lat,lng);
					            }
					        }).start();
							
							
						}
						}
					}
						
				}

			}
			
			
		};
		
		/**
		public static HttpResponse makeRequest(String uri, final String username, final String password, double lat, double lng) {
		    try {
		    	//tacohen note: login should be something like: /users/login?username=admin&lat=31&lng=30&password=123

		    	HttpClient client = new DefaultHttpClient();
		    	uri = uri+username+"&lat="+lat+"&lng="+lng+"&password="+password;
		        HttpPost httpPost = new HttpPost(uri);
		        httpPost.setHeader(new BasicHeader("Content-type", "application/json"));
		        HttpResponse response = client.execute(httpPost);
		        Log.i(TAG, "URI is: "+httpPost.getURI());
		        StatusLine statusLine = response.getStatusLine();
		        Log.i(TAG, "HTTP response code was: "+statusLine.toString());
		        if (statusLine.toString().equals("HTTP/1.1 200 OK")){
		        	Handler handler = new Handler(Looper.getMainLooper());
		        	handler.post(new Runnable() {
		        	    @Override
		        	    public void run() {
		        	        Intent intent = new Intent (context, MainInterface.class);
		        	        intent.putExtra("username", username);
		        	        intent.putExtra("password", password);
		        	        startActivity(intent);
		        	    }
		        	});
		        }
		        else{
		        	Log.e(TAG, "HTTP problem!");
		        }
		        
		    } catch (UnsupportedEncodingException e) {
		        e.printStackTrace();
		    } catch (ClientProtocolException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return null;
		}*/
		
		public HttpResponse makeRequest(String uri, final String username, final String password,double lat,double lng) {
		    try {
		    	//tacohen note: login should be something like: /users/login?username=admin&lat=31&lng=30&password=123

		    	HttpClient client = new DefaultHttpClient();
		    	uri = uri+username+"&lat="+lat+"&lng="+lng+"&password="+password;
		    	HttpPost httpPost = new HttpPost(uri);
		        httpPost.setHeader(new BasicHeader("Content-type", "application/json"));
		        HttpResponse response = client.execute(httpPost);
		        Log.i(TAG, "URI is: "+httpPost.getURI());
		        StatusLine statusLine = response.getStatusLine();
		        Log.i(TAG, "HTTP response code was: "+statusLine.toString());
		        if (statusLine.toString().equals("HTTP/1.1 200 OK")){
		        	Handler handler = new Handler(Looper.getMainLooper());
		        	handler.post(new Runnable() {
		        	    @Override
		        	    public void run() {
		        	        Intent intent = new Intent (Register.this, MainInterface.class);
		        	        intent.putExtra("username", username);
		        	        intent.putExtra("password", password);
		        	        startActivity(intent);
		        	    }
		        	});
		        	//Validate v = new Validate();
		        	//v.MoveToPlayScreen(username);
		        }
		        else{
		        	Log.e(TAG, "HTTP problem!");
		        }
		    } catch (UnsupportedEncodingException e) {
		        e.printStackTrace();
		    } catch (ClientProtocolException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return null;
		}


}
