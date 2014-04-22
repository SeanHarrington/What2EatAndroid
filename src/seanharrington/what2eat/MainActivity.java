package seanharrington.what2eat;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;

import android.os.AsyncTask;
import android.os.Bundle;

import android.preference.PreferenceManager;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem; 
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements OnClickListener{
		
	private static String url_return = "http://54.187.104.37/return.php?email=seanarrington7@mail.csuchico.edu";
	private static String url_send = "http://54.187.104.37/send.php";
	private TextView finalResult;
	
	ArrayAdapter<String> adapter;
	String [] items = {"No Suggestions"};
	String m_Text="";
	
    Integer doOnce = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		finalResult = (TextView) findViewById(R.id.maintitle);
		Button buttontest = (Button) findViewById(R.id.main_user_data);
		buttontest.setOnClickListener(this);
		Button buttontest1 = (Button) findViewById(R.id.main_report);
		buttontest1.setOnClickListener(this);
		
		
		if (doOnce < 1){
			doOnce++;
			Intent myUserIntent = new Intent(MainActivity.this, SplashActivity.class);
			MainActivity.this.startActivity(myUserIntent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent myIntent = new Intent(this, AboutActivity.class);
			this.startActivity(myIntent);
			return true;
		}
		else if (id == R.id.upload) {
			//Toast.makeText(getApplicationContext(), "UPDATE is clicked", Toast.LENGTH_SHORT).show();
			
			new getData().execute();	
			
			
			return true;
		}
		
			
		return super.onOptionsItemSelected(item);
	}
  	
	
	
	@Override
	public void onClick(View src) {
		switch (src.getId()) {
        	case R.id.main_user_data:
        		//Toast.makeText(getApplicationContext(), "USER DATA is clicked", Toast.LENGTH_SHORT).show();
        		
        		Intent myUserIntent = new Intent(MainActivity.this, UserActivity.class);
    			MainActivity.this.startActivity(myUserIntent);
    			
        		break;
        	case R.id.main_report:
        		//Toast.makeText(getApplicationContext(), "REPORT is clicked", Toast.LENGTH_SHORT).show();
        		Intent myReportIntent = new Intent(MainActivity.this, ReportActivity.class);
    			MainActivity.this.startActivity(myReportIntent);
    			
        		break;
		}		
	}
	
	private class getData extends AsyncTask<Void, Void, Void> {
		InputStream inputStream = null;
	    String result = ""; 
	    String url_select = url_return;
	

		protected void onPreExecute() {
			Log.d("SuggestionAPP ", "Preparing to get Suggestions");		
		}

		protected Void doInBackground(Void... params) {
			
			try {
				URI uri = new URI(url_select);
				HttpClient httpclient = new DefaultHttpClient();
	            HttpResponse httpResponse = httpclient.execute(new HttpGet(uri));
	            HttpEntity httpEntity = httpResponse.getEntity();
	            inputStream = httpEntity.getContent(); 		        
			 } catch (UnsupportedEncodingException e1) {
		            Log.e("UnsupportedEncodingException", e1.toString());
		            e1.printStackTrace();
		        } catch (ClientProtocolException e2) {
		            Log.e("ClientProtocolException", e2.toString());
		            e2.printStackTrace();
		        } catch (IllegalStateException e3) {
		            Log.e("IllegalStateException", e3.toString());
		            e3.printStackTrace();
		        } catch (IOException e4) {
		            Log.e("IOException", e4.toString());
		            e4.printStackTrace();
		        } catch (URISyntaxException e) {
		        	Log.e("URISyntaxException ", e.toString());
					e.printStackTrace();
				}
		        // Convert response to string using String Builder
		        try {
		            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
		            StringBuilder sBuilder = new StringBuilder();
		            String line = null;
		            line = bReader.readLine();
		            
		                sBuilder.append(line + "\n");
		            
		            inputStream.close();
		            result = sBuilder.toString();
		        } catch (Exception e) {
		            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
		        }
				return null;
		}

		protected void onPostExecute(Void donothing) {
			//parse JSON data
	        	finalResult.setText(result);
	        	//here is were we will send the string to the DBHelper to be parsed and updated.
			}		
	}
	
	
}
