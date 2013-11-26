package edu.wm.werewolf_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class GetAllAlive {
	
	
	String TAG = "GetAllAlive";
	static JSONObject jObj = null;
	ArrayList<Player> playersList;
	
	public  ArrayList<Player> getLivingPlayers(String username, String password) {
		try{
			Log.i(TAG, "Username is: "+username);
			Log.i(TAG, "Password is: "+password);

			playersList = new ArrayList<Player>();

			HttpClient client = new DefaultHttpClient();
			URI website = new URI("http://powerful-depths-2851.herokuapp.com/players/alive");
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

			// try parse the string to a JSON object
			try {
				jObj = new JSONObject(sb.toString());
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
			Log.i(TAG, "jObj is: "+jObj);
			JSONArray playerNames = jObj.names();
			Log.i(TAG, "playerNames are: "+playerNames.toString(1));
			for (int i=0; i < playerNames.length(); i++ ){
				JSONObject o = jObj.getJSONObject(playerNames.getString(i));
				Player p = new Player(o.getString("id").replaceAll("^\"|\"$", ""),o.getBoolean("dead"),o.getDouble("lat"),o.getDouble("lng"),o.getInt("userID"),o.getBoolean("wereWolf"),o.getInt("voteCount"));
				Log.i(TAG, "New player's name is: "+p.getId());
				playersList.add(p);
			}

			in.close();
			String data = sb.toString();
			Log.i(TAG, "Players/alive result is: "+data);



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
		return playersList;


	}
	
	public boolean isSpecificPlayerAlive(String desiredPlayerName, String username, String password){
		try{
			Log.i(TAG, "Username is: "+username);
			Log.i(TAG, "Password is: "+password);

			playersList = new ArrayList<Player>();

			HttpClient client = new DefaultHttpClient();
			URI website = new URI("http://powerful-depths-2851.herokuapp.com/players/alive");
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

			// try parse the string to a JSON object
			try {
				jObj = new JSONObject(sb.toString());
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
			Log.i(TAG, "jObj is: "+jObj);
			JSONArray playerNames = jObj.names();
			Log.i(TAG, "playerNames are: "+playerNames.toString(0));
			in.close();
			String data = sb.toString();
			Log.i(TAG, "Players/alive result is: "+data);
			
			for (int i=0; i < playerNames.length(); i++){
				if (playerNames.get(i).equals(desiredPlayerName)){
					return true;
				}
			}
			return false;//if we've iterated through the list and can't find the player, they're probably dead


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
		
		return false;//If we reach this point, something went wrong
		
	}
}



	

