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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.*;

public class Register extends Activity{
	
	Context context;
	static String TAG = "Register";
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
					            	makeRequest("http://powerful-depths-2851.herokuapp.com/users/register?username=",username,password);
					            }
					        }).start();
							
							
						}
						}
					}
						
				}

			}
			
			
		};
		
		
		public static HttpResponse makeRequest(String uri, String username, String password) {
		    try {
		    	//tacohen note: login should be something like: /users/login?username=admin&lat=31&lng=30&password=123

		    	HttpClient client = new DefaultHttpClient();
		    	uri = uri+username+"&lat="+25+"&lng="+20+"&password="+password;
		        HttpPost httpPost = new HttpPost(uri);
		        httpPost.setHeader(new BasicHeader("Content-type", "application/json"));
		        HttpResponse response = client.execute(httpPost);
		        Log.i(TAG, "URI is: "+httpPost.getURI());
		        StatusLine statusLine = response.getStatusLine();
		        Log.i(TAG, "HTTP response code was: "+statusLine.toString());
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
