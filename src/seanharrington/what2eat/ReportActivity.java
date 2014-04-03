package seanharrington.what2eat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;



public class ReportActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		
		
		
		
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
			Toast.makeText(getApplicationContext(), "HELP is clicked", Toast.LENGTH_SHORT).show();
			
			Intent myIntent = new Intent(this, AboutActivity.class);
			this.startActivity(myIntent);
			
			return true;
		}
		else if (id == R.id.upload) {
			Toast.makeText(getApplicationContext(), "UPDATE is clicked", Toast.LENGTH_SHORT).show();
			
			return true;
		}
		else if (id == R.id.menu_bar_user) {
			Toast.makeText(getApplicationContext(), "User is clicked", Toast.LENGTH_SHORT).show();
			Intent myIntent = new Intent(this, UserActivity.class);
			this.startActivity(myIntent);
			return true;
		}
		else if (id == R.id.menu_bar_report) {
			Toast.makeText(getApplicationContext(), "Report is clicked", Toast.LENGTH_SHORT).show();
			
			return true;
		}
		else if (id == R.id.menu_bar_home) {
			Toast.makeText(getApplicationContext(), "Home is clicked", Toast.LENGTH_SHORT).show();
			Intent myIntent = new Intent(this, MainActivity.class);
			this.startActivity(myIntent);
			return true;
		}
			
		return super.onOptionsItemSelected(item);
	}


}
