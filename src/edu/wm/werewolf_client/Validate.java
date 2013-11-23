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
            	sendJson(username,password);
            }
        }).start();
		
	}
	
	
	public static void sendJson(String username, String password) {
	    //Map<String, Object> param = new HashMap<String, Object>();
	    //param.put("username", username);
	    //param.put("password", 123);
	    //param.put("lat", 25);
	    //param.put("lng", 26);
	    
	    //String json = new GsonBuilder().create().toJson(param, Map.class);
	    //Log.i(TAG,"JSON is: "+json);
	    makeRequest("http://powerful-depths-2851.herokuapp.com/users/login?username=", username, password);
	}

	public static HttpResponse makeRequest(String uri, String username, String password) {
	    try {
	    	//tacohen note: login should be something like: /users/login?username=admin&lat=31&lng=30&password=123

	    	HttpClient client = new DefaultHttpClient();
	    	uri = uri+username+"&lat="+25+"&lng="+20+"&password="+123;
	        HttpPost httpPost = new HttpPost(uri);
	        //httpPost.setEntity(new StringEntity(json));
	        //String urlJson = URLEncoder.encode(json,"UTF-8");
	        //Log.i(TAG, "urlJson is: "+urlJson);
	        //httpPost.setHeader("Accept-Encoding", "application/json");
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
		/**
		JSONObject jsonParams = new JSONObject();
		try {
			jsonParams.put("username", username);
		} catch (JSONException e) {
			Log.e(TAG,"JSON put error!");
		}
        try {
			jsonParams.put("password", password);
		} catch (JSONException e) {
			Log.e(TAG,"JSON put error!");
		}
        try {
			jsonParams.put("lat", lat);
		} catch (JSONException e) {
			Log.e(TAG,"JSON put error!");;
		}
        try {
			jsonParams.put("lng", lng);
		} catch (JSONException e) {
			Log.e(TAG,"JSON put error!");
		}
		
		params.put("username", username);
		params.put("password", password);
		params.put("lat", lat);
		params.put("lng", lng);
        //StringEntity entity = null;
        //try {
		//	entity = new StringEntity(jsonParams.toString());
		//} catch (UnsupportedEncodingException e) {
		//	Log.e(TAG,"JSON toString() error!");
		//}
        //Log.i(TAG, "Request is: "+client.toString());
		client.addHeader(HTTP.CONTENT_TYPE, "application/json");
		client.post("http://powerful-depths-2851.herokuapp.com/users/login", params, new AsyncHttpResponseHandler() {
        //client.post(context, "http://powerful-depths-2851.herokuapp.com/users/login", entity, "application/json",new AsyncHttpResponseHandler(){   
        	@Override
		    public void onSuccess(String response) {
		        System.out.println("Sent login request, response is: "+response);
		    }
		    @Override
		    public void onFailure(int statusCode,
                    org.apache.http.Header[] headers,
                    byte[] binaryData,
                    java.lang.Throwable error){
		        Log.w(TAG,"HTTP Post failure!");

		    }
				    
		});
	}*/
	/**

		protected void sendJson (final String username, final String password) {
			
			String [] usernameAndPassword = new String [2];
			
			usernameAndPassword[0] = username;
			usernameAndPassword[1] = password;
			
	        Thread t = new Thread() {

	            public void run() {
	                Looper.prepare(); //For Preparing Message Pool for the child Thread
	                HttpClient client = new DefaultHttpClient();
	                //HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
	                HttpResponse response;
	                JSONObject json = new JSONObject();

	                try {
	                	double lat = 30.0;
	                	double lng = 31.0;
	                    HttpPost post = new HttpPost("http://powerful-depths-2851.herokuapp.com/users/login");
	                    //post.setHeader("Content-type", "application/json");
	                    //post.setHeader("Accept", "application/json");
	                    json.accumulate("username", username);
	                    json.accumulate("password", password);
	                    json.accumulate("lat", lat);
	                    json.accumulate("lng", lng);
	                    StringEntity se = new StringEntity(json.toString(), "utf-8");  
	                    Log.i(TAG, "JSON is: "+json.toString());
	                    //se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	                    post.setEntity(se);
	                    //post.setHeader("Accept", "application/json");
	                    post.setHeader("Content-type", "application/json");
	                    response = client.execute(post);
	                    Log.i(TAG, "POST is: "+post.toString());

	                    /*Checking response 
	                    if(response!=null){
	                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
	                        Log.i(TAG, "Response from server was: "+in);
	                        
	                    }

	                } catch(Exception e) {
	                    e.printStackTrace();
	                    Log.e("Validate", "Cannot Estabilish Connection");
	                }

	                Looper.loop(); //Loop in the message queue
	            }
	        };

	        t.start();      
	    }*/
		
		/**
		AsyncHttpClient client = new AsyncHttpClient();
		client.setBasicAuth(username,password);
		client.addHeader("Content-Type","application/json");
		RequestParams params = new RequestParams();
		params.put("username", username);
		params.put("password", password);
		params.put("lat", 30.0);//hardcoded; replace these!
		params.put("lng", 31.0);//hardcoded; replace these!
		client.post("http://powerful-depths-2851.herokuapp.com/users/login",  params,new AsyncHttpResponseHandler() {
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
		        Log.w(TAG,"HTTP Post failure!, Status code is: "+statusCode);
		    	Toast toast = Toast.makeText(context, "Something went wrong, please login again", Toast.LENGTH_LONG);
				toast.show();
				Intent mainIntent = new Intent(Validate.this,MainActivity.class);
				startActivityForResult(mainIntent, 0);
		    }
				    
		});
		*/
	

}
