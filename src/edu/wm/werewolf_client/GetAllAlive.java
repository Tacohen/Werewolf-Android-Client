package edu.wm.werewolf_client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class GetAllAlive extends AsyncTask<String, Void, ArrayList<String>>{
	
	ArrayList<String> playersList;
	String TAG = "GetAllAlive";
	
	@Override
	protected ArrayList<String> doInBackground(String... usernameAndPassword) {
		String username = usernameAndPassword[0];
		String password = usernameAndPassword[1];

		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://powerful-depths-2851.herokuapp.com/players/alive");
	    Log.i(TAG,"Trying to set up HTTP POST!");
	    httppost.setHeader("Content-Type","application/json");
	    
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("username", username));
	        nameValuePairs.add(new BasicNameValuePair("password", password));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        Log.i(TAG,response.toString());
	        
	    } catch (ClientProtocolException e) {
	        Log.e(TAG,"Client Protocol Exception!");
	    } catch (IOException e) {
	    	Log.e(TAG,"IO Exception!");
	    }
		return playersList;
	    
	}



	
}
