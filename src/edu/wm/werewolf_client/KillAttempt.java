package edu.wm.werewolf_client;


import android.util.Log;
import com.loopj.android.http.*;


public class KillAttempt {
	
	String TAG = "KillAttempt";
	
	public KillAttempt(){
		super();
	}

	public void MakeKillAttempt(String victimName) {
		String username = UsernameAndPassword.getUsername();
		String password = UsernameAndPassword.getPassword();
		Log.i(TAG, "About to post new registration to server");
		AsyncHttpClient client = new AsyncHttpClient();
		client.setBasicAuth(username,password);
		RequestParams params = new RequestParams();
		params.put("killerId", username);
		params.put("victimId", victimName);
		client.post("http://powerful-depths-2851.herokuapp.com/players/kill", params, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		        System.out.println("Sent kill request, response is: "+response);
		    }
		    @Override
		    public void onFailure(int statusCode,
                    org.apache.http.Header[] headers,
                    byte[] binaryData,
                    java.lang.Throwable error){
		        Log.w(TAG,"HTTP Post failure!");

		    }
				    
		});
		
	}
	

}
