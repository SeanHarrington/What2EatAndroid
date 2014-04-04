package seanharrington.what2eat;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;


public class UserActivity extends Activity {
	Boolean initialDisplay = true;
	Boolean initialDisplayFood = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

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
			    }
		});
		
		
	}

	
	public void addItemsOnSpinnerFriends() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner_user_name);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.name_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	  }

	public void addItemsOnSpinnerFoods() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner_user_food);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.food_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
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

}
