package seanharrington.what2eat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.TextView;


public class OutputActivity extends Activity {
	DBHelper dbh;
	String output_type = "";
	String output_value = "";
		
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
			Toast.makeText(getApplicationContext(), "UPDATE is clicked", Toast.LENGTH_SHORT).show();
			
			return true;
		}
		
			
		return super.onOptionsItemSelected(item);
	}

	
	public static String padRight(String s, int n) {
	      
	     return String.format("%-22s", s);
	}
	
}
