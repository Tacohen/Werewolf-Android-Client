package edu.wm.werewolf_client;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.*;

public class Validate extends Activity{
	
	Context context;
	static String TAG = "Validate";
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
		
		//sendJson(username,password);
		double lat = 25.0;
    	double lng = 20.0;
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.setBasicAuth(username,password);
		RequestParams params = new RequestParams();
		
		new Thread(new Runnable() {

            @Override
            public void run() {
            	makeRequest("http://powerful-depths-2851.herokuapp.com/users/login?username=",username,password);
            }
        }).start();
		
	}
	

	public static HttpResponse makeRequest(String uri, String username, String password) {
	    try {
	    	//tacohen note: login should be something like: /users/login?username=admin&lat=31&lng=30&password=123

	    	HttpClient client = new DefaultHttpClient();
	    	uri = uri+username+"&lat="+25+"&lng="+20+"&password="+123;
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
