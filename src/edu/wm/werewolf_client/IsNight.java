package edu.wm.werewolf_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class IsNight {
	
	String TAG = "IsNight";
	static JSONObject jObj = null;

	
	public  boolean isNight(String username, String password) {
		try{
			Log.i(TAG, "Username is: "+username);
			Log.i(TAG, "Password is: "+password);


			HttpClient client = new DefaultHttpClient();
			URI website = new URI("http://powerful-depths-2851.herokuapp.com/players/isnight");
			HttpGet request = new HttpGet();
			request.setURI(website);
			request.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(username, password),
					"UTF-8", false));
			HttpResponse response = client.execute(request);
			response.getStatusLine().getStatusCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String l = "";
			String nl = System.getProperty("line.separator");
			while ((l = in.readLine()) !=null){
				sb.append(l + nl);
			}
			
			Log.i(TAG, "response from server was: "+sb);

			// try parse the string to a JSON object
			try {
				jObj = new JSONObject(sb.toString());
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
			Log.i(TAG, "jObj is: "+jObj);
			boolean isNight = jObj.getBoolean("isNight");
			Log.i(TAG, "isNight is: "+isNight);
			in.close();
			
			return isNight;


		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		
		Log.e(TAG, "Could not get Day/Night, assuming day!");
		return false;//Placeholder if we can't actually get the value


	}

}
