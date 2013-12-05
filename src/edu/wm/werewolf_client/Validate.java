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
import android.os.SystemClock;
import android.util.Log;
import com.loopj.android.http.*;

import edu.wm.werewolf_client.FindLocation.LocationResult;

public class Validate extends Activity {

	
	Context context;
	static String TAG = "Validate";
	String username = UsernameAndPassword.getUsername();
	String password = UsernameAndPassword.getPassword();
	public double lat = 25;//default
	public double lng = 29;//default
	public boolean gotLocation = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validate);
		
		context = getApplicationContext();
		
		LocationResult locationResult = new LocationResult(){
			@Override
			public void gotLocation(Location location){

				if (location == null){
					Log.w(TAG,"failed to get location!");
				}
				else{
					//Got the location!
					lat = (double) (location.getLatitude());
					lng = (double) (location.getLongitude());

					Log.v(TAG,"latitude is: "+lat);
					Log.v(TAG,"longitude is: "+lng);
					gotLocation = true;
					
					AsyncHttpClient client = new AsyncHttpClient();
					client.setBasicAuth(username,password);
					
					new Thread(new Runnable() {

			            @Override
			            public void run() {
			            	makeRequest("http://powerful-depths-2851.herokuapp.com/users/login?username=",username,password,lat,lng);
			            }
			        }).start();


				}
			}

		};
		FindLocation myLocation = new FindLocation();
		myLocation.getLocation(this, locationResult);

		
		if (!gotLocation){
			//wait here until location is obtained
			SystemClock.sleep(7000);
		}
		
		//Log.i(TAG,"Username is: "+username);
		//Log.i(TAG, "password is: "+password);
		//Log.v(TAG,"latitude is: "+lat);
		//Log.v(TAG,"longitude is: "+lng);
		
		
		
	}
	

	public HttpResponse makeRequest(String uri, final String username, final String password,double lat,double lng) {
	    try {
	    	//tacohen note: login should be something like: /users/login?username=admin&lat=31&lng=30&password=123

	    	HttpClient client = new DefaultHttpClient();
	    	uri = uri+username.trim()+"&lat="+lat+"&lng="+lng+"&password="+password.trim();
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
	        	        Intent intent = new Intent (Validate.this, MainInterface.class);
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
	
	public void MoveToPlayScreen(String username){
		Intent validateIntent = new Intent(Validate.this,MainInterface.class);
		startActivityForResult(validateIntent, 0);
	}

	

}
