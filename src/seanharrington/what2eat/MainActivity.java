package seanharrington.what2eat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem; 
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button buttontest = (Button) findViewById(R.id.main_user_data);
		buttontest.setOnClickListener(this);
		Button buttontest1 = (Button) findViewById(R.id.main_report);
		buttontest1.setOnClickListener(this);
		
		
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
			Intent myIntent = new Intent(this, ReportActivity.class);
			this.startActivity(myIntent);
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
  
	
	
	
	@Override
	public void onClick(View src) {
		switch (src.getId()) {
        	case R.id.main_user_data:
        		Toast.makeText(getApplicationContext(), "USER DATA is clicked", Toast.LENGTH_SHORT).show();
        		
        		Intent myUserIntent = new Intent(MainActivity.this, UserActivity.class);
    			MainActivity.this.startActivity(myUserIntent);
    			
        		break;
        	case R.id.main_report:
        		Toast.makeText(getApplicationContext(), 
                "REPORT is clicked", Toast.LENGTH_SHORT).show();
        		Intent myReportIntent = new Intent(MainActivity.this, ReportActivity.class);
    			MainActivity.this.startActivity(myReportIntent);
    			
        		break;
		}		
	}
}
