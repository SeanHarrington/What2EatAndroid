package seanharrington.what2eat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.TextView;


public class OutputActivity extends Activity {
	DBHelper dbh;
	String output_type = "";
	String output_value = "";
	private static String url_return = "http://54.187.104.37/return.php?email=";
	private static String url_send = "http://54.187.104.37/send.php";
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_output);
		dbh = new DBHelper(this); 

		Bundle extras = getIntent().getExtras();
			if (extras != null) {
				output_type = extras.getString("output_type");
			    output_value = extras.getString("output_value");
			}
			
			if (output_type.equals("user")){
				int user_id = 0;
				user_id = dbh.getUserId(output_value);
				Integer[] nArray;
				int nArray_size = 0;
				nArray_size = dbh.getReport_foodCount(user_id);
				if (nArray_size > 0){
					nArray = new Integer[nArray_size*2];
					nArray = dbh.populateUserReport(nArray, user_id);
					String outputString = "";
					TextView txt = (TextView) findViewById(R.id.output_report_text_view);
					for (int i = 0; i < nArray.length; i=i+2) {
						String food_name = "";
						String food_rating = "";
						
						food_name = dbh.GetFoodName(nArray[i]);
						if (nArray[i+1] == 1){
							food_rating = "Hates It!";
						}
						else if (nArray[i+1]== 2){
							food_rating = "It's OK";
						}
						else{
							food_rating = "Loves It!";						
						}
						outputString = outputString + padRight(dbh.CapEachWord(food_name),40) + " -- " + food_rating + "\n";
					}
					txt.setText(outputString);
				}
			}
			else if(output_type.equals("food")){
				int food_id = 0;
				food_id = dbh.GetFoodId(output_value);
				Integer[] nArray;
				int nArray_size = 0;
				nArray_size = dbh.getReport_userCount(food_id);
				if (nArray_size > 0){
					nArray = new Integer[nArray_size*2];
					nArray = dbh.populateFoodReport(nArray, food_id);
					String outputString = "";
					TextView txt = (TextView) findViewById(R.id.output_report_text_view);
					for (int i = 0; i < nArray.length; i=i+2) {
						String user_name = "";
						String food_rating = "";
						user_name = dbh.GetUserName(nArray[i]);
						if (nArray[i+1] == 1){
							food_rating = "Hates It!";
						}
						else if (nArray[i+1]== 2){
							food_rating = "It's OK";
						}
						else{
							food_rating = "Loves It!";						
						}
						outputString = outputString + padRight(dbh.CapEachWord(user_name),40) + " -- " + food_rating + "\n";
					}
					txt.setText(outputString);
				}
			}
			else{
			Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
			}
		}
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
			//Toast.makeText(getApplicationContext(), "About is clicked", Toast.LENGTH_SHORT).show();
			
			Intent myIntent = new Intent(this, AboutActivity.class);
			this.startActivity(myIntent);
			
			return true;
		}
		else if (id == R.id.upload) {
			new getData().execute();	
			new sendData().execute();
			return true;
		}
		
			
		return super.onOptionsItemSelected(item);
	}

	
	public static String padRight(String s, int n) {
	      
	     return String.format("%-22s", s);
	}
	
	

	private class sendData extends AsyncTask<Void, Void, Void> {
		
	     
	    String url_select = url_send;
	

		protected void onPreExecute() {
					
		}

		protected Void doInBackground(Void... params) {
			
			//we need to get a prepared array of values to be uploaded
			String[] nSendArray = dbh.getPreparedUpload();
			
			
			for (int i = 0; i < nSendArray.length; i=i+4){
				
				
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(url_select);

				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("email", nSendArray[i]));
				pairs.add(new BasicNameValuePair("food_name", nSendArray[i+1]));
				pairs.add(new BasicNameValuePair("movement", nSendArray[i+2]));
				pairs.add(new BasicNameValuePair("votes", nSendArray[i+3]));
				
				
				try {
					post.setEntity(new UrlEncodedFormEntity(pairs));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					client.execute(post);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				return null;
		}
		
		protected void onPostExecute(Void donothing) {
			//parse JSON data
	        	//pushToLocal(result);
	        	//here is were we will send the string to the DBHelper to be parsed and updated.
			}		
		
		
	}
	
 	private class getData extends AsyncTask<Void, Void, Void> {
		InputStream inputStream = null;
	    String result = ""; 
	    String url_select = url_return;
	//String FOODN;
	String[] nResponseArray = new String[dbh.getEmailCount()];
		protected void onPreExecute() {
			Log.d("SuggestionAPP ", "Preparing to get Suggestions");		
		}

		protected Void doInBackground(Void... params) {
			
			String concat_string= "";
			nResponseArray = dbh.getEmailList();
			for (int i = 0; i < nResponseArray.length; i=i+1){
				concat_string = url_select + nResponseArray[i];
			try {
				URI uri = new URI(concat_string);
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
		            pushToLocal(result,nResponseArray[i]);
		        } catch (Exception e) {
		            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
		        }
		        
			}
				return null;
		}

		protected void pushToLocal(String unParsed, String email){
			unParsed = unParsed.replace(";",",");
			unParsed = unParsed.replace("\"","");
			unParsed = unParsed.replace("~","");
			unParsed = unParsed.substring(0,unParsed.length()-2);
			String[] parts = unParsed.split(",");
			
			for (int i = 0; i < parts.length; i=i+3){
				String food_name = parts[i];
				Integer sum_rating = Integer.parseInt(parts[i+1]);
				Integer sum_vote = Integer.parseInt(parts[i+2]);
				Integer avg_rating = sum_rating/sum_vote;
				//UPDATE USERS_FOODS SET avg_rating = #{avgr} WHERE user_id = #{name_id} and food_id = #{food_id}"
				if (i == 12){
				//finalResult.setText(food_name);//debug
				}
				//FOODN = food_name + " " + avg_rating;
				
				OutputActivity.this.runOnUiThread(new Runnable() {

			        public void run() {
			        	
			            //Toast.makeText(OutputActivity.this, FOODN, Toast.LENGTH_SHORT).show();
			            
			        }
			    });
				
				dbh.addSoloFood(food_name);
				dbh.updateUser(dbh.getUserIdbyEmail(email), dbh.GetFoodId(food_name), avg_rating);
			}
		}
	
		
		protected void onPostExecute(Void donothing) {
			//parse JSON data
	        	//pushToLocal(result);
			Toast.makeText(getApplicationContext(), "Update Finished", Toast.LENGTH_SHORT).show();
	        	//here is were we will send the string to the DBHelper to be parsed and updated.
			}		
	}
	
	
}
