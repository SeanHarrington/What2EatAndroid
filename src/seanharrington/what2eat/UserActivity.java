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
		
		
		
		addItemsOnSpinnerFriends();
		addItemsOnSpinnerFoods();
		
		Spinner spnLocale;
		spnLocale = (Spinner)findViewById(R.id.spinner_user_name);
		spnLocale.setOnItemSelectedListener(new OnItemSelectedListener() {
			 @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			        if (initialDisplay == true){
			        	initialDisplay = false;
			        } else {
			        	EditText txt = (EditText) findViewById(R.id.editText1); 
			        	Spinner spn1 = (Spinner)findViewById(R.id.spinner_user_name);
			        	String Text = spn1.getSelectedItem().toString();
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
			        if (initialDisplayFood == true){
			        	initialDisplayFood = false;
			        } else {
			        	EditText txt = (EditText) findViewById(R.id.editText3); 
			        	Spinner spn1 = (Spinner)findViewById(R.id.spinner_user_food);
			        	String Text = spn1.getSelectedItem().toString();
			        	txt.setText(Text);
			        }
			    }
			    @Override
			    public void onNothingSelected(AdapterView<?> parentView) {
			    	Toast.makeText(getApplicationContext(), "I'm not selecting", Toast.LENGTH_SHORT).show();
			    }
		});
		
		
	}
	
	public void addItemsOnSpinnerFriends() {
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner_user_name);
		String[] nArray;
		nArray = new String[dbh.GetDBRecordCount("USERS")];
		nArray = dbh.populateUserArray(nArray);
		ArrayAdapter<String> adp2=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nArray);
		adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adp2);
		
	 }

	public void addItemsOnSpinnerFoods() {
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner_user_food);
		String[] nArray;
		nArray = new String[dbh.GetDBRecordCount("FOODS")];
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
    	userName = txt.getText().toString();
		EditText txt1 = (EditText) findViewById(R.id.editText2); 
    	email = txt1.getText().toString();
		EditText txt2 = (EditText) findViewById(R.id.editText3); 
    	foodName = txt2.getText().toString();
    	
		switch (src.getId()) {
        	case R.id.button1:
        		dbh.addUser(userName, email);
        		break;
        	case R.id.button2:
        		dbh.addFood(userName, foodName, 3);
        		Toast.makeText(getApplicationContext(), userName + " Loves " + foodName, Toast.LENGTH_SHORT).show();
        		break;
        	case R.id.button3:
        		dbh.addFood(userName, foodName, 2);
        		Toast.makeText(getApplicationContext(), userName + " is ok with " + foodName, Toast.LENGTH_SHORT).show();
        		break;
        	case R.id.button4:
        		dbh.addFood(userName, foodName, 1);
        		Toast.makeText(getApplicationContext(), userName + " Hates " + foodName, Toast.LENGTH_SHORT).show();
        		break;
		}
		initialDisplay = true;
		initialDisplayFood = true;
		addItemsOnSpinnerFriends();
		addItemsOnSpinnerFoods();
		
	}

}
