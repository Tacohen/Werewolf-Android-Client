package edu.wm.werewolf_client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import android.util.Log;

public class Vote {
	
	static String TAG = "Vote";
	String username = UsernameAndPassword.getUsername();
	String password = UsernameAndPassword.getPassword();
	
	public Vote(){
		super();
	}
	
	public void vote(String victim){
	
	try {
    	//tacohen note: login should be something like: /users/login?username=admin&lat=31&lng=30&password=123
		String uri = "http://powerful-depths-2851.herokuapp.com/players/vote?voterId="+username.trim()+"&voteId="+victim.trim();
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
        	Log.i(TAG, "Voted OK");
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

