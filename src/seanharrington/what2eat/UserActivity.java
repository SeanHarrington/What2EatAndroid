package seanharrington.what2eat;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Spinner; 
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.view.View.OnClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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


public class UserActivity extends Activity implements OnClickListener {
	Boolean initialDisplay = true;
	Boolean initialDisplayFood = true;
	DBHelper dbh;
	private static String url_return = "http://54.187.104.37/return.php?email=";
	private static String url_send = "http://54.187.104.37/send.php";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		dbh = new DBHelper(this); 
		
		//create update user button 
		Button buttontest = (Button) findViewById(R.id.button1);
		buttontest.setOnClickListener(this);
		
		//love it
		Button buttontest2 = (Button) findViewById(R.id.button2);
		buttontest2.setOnClickListener(this);
		
		//its ok
		Button buttontest3 = (Button) findViewById(R.id.button3);
		buttontest3.setOnClickListener(this);
		
		//hate it
		Button buttontest4 = (Button) findViewById(R.id.button4);
		buttontest4.setOnClickListener(this);
		
		
		
		Spinner spnLocale;
		spnLocale = (Spinner)findViewById(R.id.spinner_user_name);
		spnLocale.setOnItemSelectedListener(new OnItemSelectedListener() {
			 @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				 Spinner spn1 = (Spinner)findViewById(R.id.spinner_user_name);
				 String Text = spn1.getSelectedItem().toString();
				 if (Text.equals("Select a Friend")){
					 
					 
				 }
				// else if (initialDisplay == true){
			     //   	initialDisplay = false;
			      //  } 
				 else {
			        	EditText txt = (EditText) findViewById(R.id.editText1); 
			        	Text = CapEachWord(Text); 
			        	txt.setText(Text);
			        	
			        	EditText txt2 = (EditText) findViewById(R.id.editText2); 
			        	String Text2 = dbh.GetUserEmail(dbh.getUserId(Text));
			        	
			        	txt2.setText(Text2);
			        }
			    }
			    @Override
			    public void onNothingSelected(AdapterView<?> parentView) {
			    }
		});
		
		Spinner spnLocale2;
		spnLocale2 = (Spinner)findViewById(R.id.spinner_user_food);
		spnLocale2.setOnItemSelectedListener(new OnItemSelectedListener() {
			 @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			  	Spinner spn1 = (Spinner)findViewById(R.id.spinner_user_food);
		       	String Text = spn1.getSelectedItem().toString();
		     	 if (Text.equals("Select a Food")){
					 
					 
				 }	
				 //else if (initialDisplayFood == true){
			       // 	initialDisplayFood = false;
			        //} 
				 else {
			        	EditText txt = (EditText) findViewById(R.id.editText3); 
			        	txt.setText(Text);
			        }
			    }
			    @Override
			    public void onNothingSelected(AdapterView<?> parentView) {
			    	Toast.makeText(getApplicationContext(), "I'm not selecting", Toast.LENGTH_SHORT).show();
			    }
		});
		
		addItemsOnSpinnerFriends();
		addItemsOnSpinnerFoods();
		
	}
	
	public void addItemsOnSpinnerFriends() {
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner_user_name);
		String[] nArray;
		nArray = new String[dbh.GetDBRecordCount("USERS")+1];
		nArray = dbh.populateUserArray(nArray);
		ArrayAdapter<String> adp2=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nArray);
		adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adp2);
		
	 }

	public void addItemsOnSpinnerFoods() {
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner_user_food);
		String[] nArray;
		nArray = new String[dbh.GetDBRecordCount("FOODS")+1];
		//Toast.makeText(getApplicationContext(), "" + dbh.GetDBRecordCount("FOODS"), Toast.LENGTH_SHORT).show();
		nArray = dbh.populateFoodArray(nArray);
		
		ArrayAdapter<String> adp2=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nArray);
		adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adp2);
		
		
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
			//Toast.makeText(getApplicationContext(), "HELP is clicked", Toast.LENGTH_SHORT).show();
			
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
	
	public void onClick(View src) {
		String userName = "";
		String email = "";
		String foodName = "";
		
		EditText txt = (EditText) findViewById(R.id.editText1); 
    	userName = txt.getText().toString().toLowerCase(Locale.ENGLISH);
		EditText txt1 = (EditText) findViewById(R.id.editText2); 
    	email = txt1.getText().toString().toLowerCase(Locale.ENGLISH);
		EditText txt2 = (EditText) findViewById(R.id.editText3); 
    	foodName = txt2.getText().toString().toLowerCase(Locale.ENGLISH);
    	
    	if (userName.equals("friend's name")){
    		Toast.makeText(getApplicationContext(), "Invalid Friend's Name", Toast.LENGTH_SHORT).show();
    	}
    	
    	
    	else{
    	
    		if (email.equals("friend's email")){
    			
    			email = "";
    		}
    		
		switch (src.getId()) {
        	
		case R.id.button1:
        		if (isValidEmail(email) ){
        		dbh.addUser(userName, email);
        		Toast.makeText(getApplicationContext(), "Friend added to Database", Toast.LENGTH_SHORT).show();
        		}
        		else{
        			Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
        		
        		}
        		break;
        	case R.id.button2:
        		dbh.addFood(userName, foodName, 3);
        		Toast.makeText(getApplicationContext(), CapEachWord(userName) + " Loves " + CapEachWord(foodName), Toast.LENGTH_SHORT).show();
        		break;
        	case R.id.button3:
        		dbh.addFood(userName, foodName, 2);
        		Toast.makeText(getApplicationContext(), CapEachWord(userName) + " is OK with " + CapEachWord(foodName), Toast.LENGTH_SHORT).show();
        		break;
        	case R.id.button4:
        		dbh.addFood(userName, foodName, 1);
        		Toast.makeText(getApplicationContext(), CapEachWord(userName) + " Hates " + CapEachWord(foodName), Toast.LENGTH_SHORT).show();
        		break;
		}
		initialDisplay = true;
		initialDisplayFood = true;
		addItemsOnSpinnerFriends();
		addItemsOnSpinnerFoods();
    	}
	}

	public final static boolean isValidEmail(String target) {
	    if (target == null) {
	        return false;
	    } 
	    else if(target.matches("")){
	    	return true;
	    }
	    else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}
	

	public String CapEachWord(String Text){
		
		StringBuilder b = new StringBuilder(Text);
		int i = 0;
		do {
		  b.replace(i, i + 1, b.substring(i,i + 1).toUpperCase(Locale.ENGLISH));
		  i =  b.indexOf(" ", i) + 1;
		} while (i > 0 && i < b.length());
		    
		return b.toString();
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
				
				UserActivity.this.runOnUiThread(new Runnable() {

			        public void run() {
			        	
			            //Toast.makeText(UserActivity.this, FOODN, Toast.LENGTH_SHORT).show();
			            
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
