package edu.wm.werewolf_client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
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
		

		// Making HTTP request
	    try {
	    	
	    	String url = "http://powerful-depths-2851.herokuapp.com/users/login";

	        // defaultHttpClient
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        HttpPost httpPost = new HttpPost(url);
	        //httpPost.setEntity(new UrlEncodedFormEntity(params));

	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        //is = httpEntity.getContent();

	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}

}
