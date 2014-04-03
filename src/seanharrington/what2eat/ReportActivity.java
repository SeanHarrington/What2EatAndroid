package seanharrington.what2eat;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Spinner;


public class ReportActivity extends Activity {

	DBHelper dbh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		
		
		addItemsOnSpinnerFriends();
		addItemsOnSpinnerFoods();
		
		
	}
	public void addItemsOnSpinnerFriends() {
		Spinner spinner = (Spinner) findViewById(R.id.report_spinner_friends);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.name_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	  }

	public void addItemsOnSpinnerFoods() {
		Spinner spinner = (Spinner) findViewById(R.id.report_spinner_foods);
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
			//Toast.makeText(getApplicationContext(), "About is clicked", Toast.LENGTH_SHORT).show();
			
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
