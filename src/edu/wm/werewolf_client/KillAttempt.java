package edu.wm.werewolf_client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import com.loopj.android.http.*;


public class KillAttempt {
	
	String TAG = "KillAttempt";
	static JSONObject jObj = null;
	ArrayList<Player> playersList;
	
	public KillAttempt(){
		super();
		
	}
	
	public ArrayList<String> FindTargets(){
		ArrayList<String> nearPlayersList = new ArrayList<String>();
		
		String username = UsernameAndPassword.getUsername();
		String password = UsernameAndPassword.getPassword();
		
		try {
	    	//tacohen note: login should be something like: /users/login?username=admin&lat=31&lng=30&password=123
			HttpClient client = new DefaultHttpClient();
			URI website = new URI("http://powerful-depths-2851.herokuapp.com/players/getNear?username="+username);
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
			
			Log.i(TAG, "Server response for getting near players is: "+sb.toString());

			// try parse the string to a JSON object
			try {
				jObj = new JSONObject(sb.toString());
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
			Log.i(TAG, "jObj is: "+jObj);
			JSONArray playerNames = jObj.names();
			if (jObj.length()==0){
				Log.w(TAG, "No players near!");
				return nearPlayersList;
			}
			for (int i = 0; i < playerNames.length(); i++){
				nearPlayersList.add(playerNames.getString(i));
			}


			in.close();

			
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (JSONException e) {
			e.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
	
		return nearPlayersList;
		
	}

	public void MakeKillAttempt(String victimName) {			
			try {
		    	//tacohen note: login should be something like: /users/login?username=admin&lat=31&lng=30&password=123
				String username = UsernameAndPassword.getUsername();
				String password = UsernameAndPassword.getPassword();
				String uri = "http://powerful-depths-2851.herokuapp.com/players/kill?killerId="+username.trim()+"&victimId="+victimName.trim();
				HttpClient client = new DefaultHttpClient();
		        HttpPost httpPost = new HttpPost(uri);
		        httpPost.addHeader(BasicScheme.authenticate(
						new UsernamePasswordCredentials(username, password),
						"UTF-8", false));
		        httpPost.setHeader(new BasicHeader("Content-type", "application/json"));
		        HttpResponse response = client.execute(httpPost);
		        Log.i(TAG, "URI is: "+httpPost.getURI());
		        StatusLine statusLine = response.getStatusLine();
		        Log.i(TAG, "HTTP response code was: "+statusLine.toString());
		        if (statusLine.toString().equals("HTTP/1.1 200 OK")){
		        	Log.i(TAG, "Killed OK");
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

		}
	

}
