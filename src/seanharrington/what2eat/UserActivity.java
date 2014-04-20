package seanharrington.what2eat;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.content.Intent;
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
import java.util.Locale;



public class UserActivity extends Activity implements OnClickListener {
	Boolean initialDisplay = true;
	Boolean initialDisplayFood = true;
	DBHelper dbh;
	
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
			Toast.makeText(getApplicationContext(), "UPDATE is clicked", Toast.LENGTH_SHORT).show();
			
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
        		Toast.makeText(getApplicationContext(), CapEachWord(userName) + " is ok with " + CapEachWord(foodName), Toast.LENGTH_SHORT).show();
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
	
}
