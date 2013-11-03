package edu.wm.werewolf_client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.*;

public class Validate extends Activity{
	
	Context context;
	String TAG = "Validate";
	String username = UsernameAndPassword.getUsername();
	String password = UsernameAndPassword.getPassword();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validate);
		
		context = getApplicationContext();
		
		//Bundle b = this.getIntent().getExtras();
		//String username = b.getString("username");
		//String password = b.getString("password");
		
		Log.i(TAG,"Username is: "+username);
		Log.i(TAG, "password is: "+password);
		
		connectToServer();
	}

	private void connectToServer() {
		
		String [] usernameAndPassword = new String [2];
		
		usernameAndPassword[0] = username;
		usernameAndPassword[1] = password;
		
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.setBasicAuth(username,password);
		RequestParams params = new RequestParams();
		params.put("username", username);
		params.put("password", password);
		client.post("http://powerful-depths-2851.herokuapp.com/users/login", params, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		        System.out.println("Response is: "+response);
		        Intent playIntent = new Intent(Validate.this,Play.class);
				Bundle b = new Bundle();
				b.putString("username", username);
				b.putString("password",password);
				playIntent.putExtras(b);
				startActivityForResult(playIntent, 0);
		    }
		    @Override
		    public void onFailure(int statusCode,
                    org.apache.http.Header[] headers,
                    byte[] binaryData,
                    java.lang.Throwable error){
		        Log.w(TAG,"HTTP Post failure!");
		    	Toast toast = Toast.makeText(context, "Something went wrong, please login again", Toast.LENGTH_LONG);
				toast.show();
				Intent mainIntent = new Intent(Validate.this,MainActivity.class);
				startActivityForResult(mainIntent, 0);
		    }
				    
		});
		
	}

}
