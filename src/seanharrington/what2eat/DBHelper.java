package seanharrington.what2eat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper{
	final static String DB_NAME = "what2eat.db";
	final static int DB_VERSION = 1;
	private final String USERS = "USERS";
	private final String FOODS = "FOODS";
	Context context;

	public DBHelper(Context context){		
		super(context, DB_NAME, null, DB_VERSION);
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + USERS + " (id INTEGER PRIMARY KEY, name VARCHAR NOT NULL, email VARCHAR);");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + FOODS + " (food_id INTEGER PRIMARY KEY, food_name VARCHAR);");
		db.execSQL("CREATE TABLE IF NOT EXISTS USERS_FOODS(user_food_id INTEGER PRIMARY KEY, user_id INTEGER, food_id INTEGER, rating INTEGER, old_rating INTEGER, updated INTEGER, avg_rating INTEGER, FOREIGN KEY(user_id) REFERENCES USERS(user_id), FOREIGN KEY (food_id) REFERENCES FOODS(food_id));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public String GetSantizedString(String v){
		
		v = v.replace("'","");
		return v;
		
		
	}
	
	/////////////GET FUNCTIONS//////////////////////
	public Integer getUserId(String sname) {
		int user_id = -1;
		sname = GetSantizedString(sname.toLowerCase(Locale.ENGLISH));
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT id FROM USERS WHERE name = '" + sname + "'", null);
		if (c != null ) {
			if (c.moveToFirst()) {
				user_id= c.getInt(0);
			}
		}
		c.close();
		qdb.close(); 
		return user_id;
	}
	
	public Integer getUserIdbyEmail(String sname) {
		int user_id = -1;
		sname = GetSantizedString(sname.toLowerCase(Locale.ENGLISH));
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT id FROM USERS WHERE email = '" + sname + "'", null);
		if (c != null ) {
			if (c.moveToFirst()) {
				user_id= c.getInt(0);
			}
		}
		c.close();
		qdb.close(); 
		return user_id;
	}
	
	
	public Integer GetFoodId(String name) {
		int food_id = -1;
		name = GetSantizedString(name.toLowerCase(Locale.ENGLISH));
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT food_id FROM FOODS WHERE food_name = '" + name + "'", null);
		if (c != null ) {
			if (c.moveToFirst()) {
				food_id = c.getInt(0);
			}
		}
		c.close();
		qdb.close(); 
		return food_id;
	}
	
	public String GetFoodName(int food_id){
		String food_name = "";
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT food_name FROM FOODS WHERE food_id = " + food_id , null);
		if (c != null ) {
			if (c.moveToFirst()) {
				food_name = c.getString(0);
			}
		}
		c.close();
		qdb.close(); 
		
		return food_name;
	}
	
	public String GetUserName(int user_id){
		String user_name = "";
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT name FROM USERS WHERE id = " + user_id , null);
		if (c != null ) {
			if (c.moveToFirst()) {
				user_name = c.getString(0);
			}
		}
		c.close();
		qdb.close(); 
		
		return user_name;
	}
		
	public Integer GetUsersFoodsId(Integer user_id, Integer food_id) {
		int uf_id = -1;
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT user_food_id FROM USERS_FOODS WHERE user_id = " + user_id + " and food_id = " + food_id, null);
		if (c != null ) {
		if (c.moveToFirst()) {
		uf_id= c.getInt(0);
		}
		}
		c.close();
		qdb.close(); 
		return uf_id;
	}
	
	public String GetUserEmail(int userId) {
		String user_email = "";
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT email FROM USERS WHERE id = "+userId, null);
		if (c != null ) {
			if (c.moveToFirst()) {
				user_email= c.getString(0);
			}
		}
		c.close();
		qdb.close(); 
		return user_email;	
	}
	
	public Integer GetDBRecordCount(String tableName){
		int count = 0;
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT count(*) FROM " + tableName, null);
		if (c != null ) {
			if (c.moveToFirst()) {
				count= c.getInt(0);
			}
		}
		c.close();
		qdb.close(); 
		return count;
	}
	
	public Integer getReport_userCount(int food_id){
		int count = 0;
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT count(*) FROM USERS_FOODS WHERE food_id = " + food_id, null);
		if (c != null ) {
			if (c.moveToFirst()) {
					count= c.getInt(0);
			}
		}
		c.close();
		qdb.close(); 
		return count;
	}
		
	public Integer getReport_foodCount(int user_id){
		int count = 0;
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT count(*) FROM USERS_FOODS WHERE user_id = " + user_id, null);
		if (c != null ) {
			if (c.moveToFirst()) {
		count= c.getInt(0);
			}
		}
		c.close();
		qdb.close(); 
		
		
		return count;
	}
	
	public Integer getEmailCount(){
	SQLiteDatabase qdb = this.getReadableDatabase();
	int iCount = 0;
	Cursor c = qdb.rawQuery("SELECT count(*) FROM USERS WHERE email IS NOT '';", null);
	if (c != null ) {
		if  (c.moveToFirst()) {
			iCount= c.getInt(0);
		}
	}
	c.close();
	qdb.close(); 
	return iCount;
}
	
	public String[] getEmailList(){
		SQLiteDatabase qdb = this.getReadableDatabase();
		int iCount = 0;
		String[] nArray;
		nArray = new String[0];
		Cursor c = qdb.rawQuery("SELECT count(*) FROM USERS WHERE email IS NOT '';", null);
		if (c != null ) {
    		if  (c.moveToFirst()) {
    			iCount= c.getInt(0);
    			nArray = new String[iCount];    			    			
    		}
		}
		c.close();
		iCount = 0;
		c = qdb.rawQuery("SELECT email FROM USERS WHERE email IS NOT '';", null);
		if (c != null ) {
    		if  (c.moveToFirst()) {
    			do {
    				String text = c.getString(0);
    				nArray[iCount] = text;
    				iCount = iCount + 1;
    			}
    			while (c.moveToNext());
    		}
		}
		c.close();
		qdb.close(); 
		return nArray;
	}
	
	//////////////GET AND DO SOMETHING FUNCTIONS//////////////
	
	public Integer[] populateUserReport(Integer[] nArray, int userId){
		SQLiteDatabase qdb = this.getReadableDatabase();
		int iCount = 0;
		Cursor c = qdb.rawQuery("SELECT USERS_FOODS.food_id, USERS_FOODS.rating, USERS_FOODS.avg_rating, FOODS.food_name FROM USERS_FOODS, FOODS WHERE USERS_FOODS.food_id = FOODS.food_id and user_id = " + userId + " ORDER BY USERS_FOODS.rating DESC, FOODS.food_name", null);
		if (c != null ) {
    		if  (c.moveToFirst()) {
    			do {
    				Integer food_id = c.getInt(c.getColumnIndex("food_id"));
    				Integer rating = c.getInt(c.getColumnIndex("rating"));
    				Integer avg_rating = c.getInt(c.getColumnIndex("avg_rating"));
    				nArray[iCount] = food_id;
    				iCount = iCount + 1;
    				if (avg_rating > 0){
    					nArray[iCount] = avg_rating;
    				}
    				else{
    					nArray[iCount] = rating;    					
    				}
    				iCount = iCount + 1;
    			}
    			while (c.moveToNext());
    		}
		}
				
		c.close();
		qdb.close();
		
		return nArray;	
	}
	
	public Integer[] populateFoodReport(Integer[] nArray, int foodId){
		SQLiteDatabase qdb = this.getReadableDatabase();
		int iCount = 0;
		Cursor c = qdb.rawQuery("SELECT USERS_FOODS.user_id, USERS_FOODS.rating, USERS_FOODS.avg_rating, USERS.name FROM USERS_FOODS, USERS  WHERE USERS.id = USERS_FOODS.user_id and USERS_FOODS.food_id = " + foodId + " ORDER BY USERS_FOODS.rating DESC, USERS.name", null);
		if (c != null ) {
    		if  (c.moveToFirst()) {
    			do {
    				int user_id = c.getInt(c.getColumnIndex("user_id"));
    				int rating = c.getInt(c.getColumnIndex("rating"));
    				int avg_rating = c.getInt(c.getColumnIndex("avg_rating"));
    				nArray[iCount] = user_id;
    				iCount = iCount + 1;
    				if (avg_rating > 0){
    					nArray[iCount] = avg_rating;
    				}
    				else{
    					nArray[iCount] = rating;    					
    				}
    				iCount = iCount + 1;
    			}
    			while (c.moveToNext());
    		}
		}
				
		c.close();
		qdb.close();
		
		return nArray;	
	}
		
	public String[] populateUserArray(String[] nArray)	
{
		SQLiteDatabase qdb = this.getReadableDatabase();
		nArray[0] = "Select a Friend";
		int iCount = 1;
		Cursor c = qdb.rawQuery("SELECT name FROM USERS ORDER BY name ASC;", null);
		if (c != null ) {
    		if  (c.moveToFirst()) {
    			do {
    				String text = c.getString(0);
    				nArray[iCount] = CapEachWord(text);
    				iCount = iCount + 1;
    			}
    			while (c.moveToNext());
    		}
		}
		qdb.close(); 
	return nArray;
	}
	
	public String[] populateFoodArray(String[] nArray){
		SQLiteDatabase qdb = this.getReadableDatabase();
		nArray[0] = "Select a Food";
		int iCount = 1;
		Cursor c = qdb.rawQuery("SELECT food_name FROM FOODS ORDER BY food_name ASC;", null);
		if (c != null ) {
    		if  (c.moveToFirst()) {
    			do {
    				String text = c.getString(0);
    				nArray[iCount] = CapEachWord(text);
    				iCount = iCount + 1;
    			}
    			while (c.moveToNext());
    		}
		}
		qdb.close(); 
	return nArray;
	}
	
	////////////////SEND FUNCTIONS///////////
	
	public void addUser(String userName, String email) {
		email = email.toLowerCase(Locale.ENGLISH);
		userName = GetSantizedString(userName.toLowerCase(Locale.ENGLISH));
		SQLiteDatabase qdb = this.getWritableDatabase();
		//leave these 6 here for wiping the table
		//qdb.execSQL("DROP TABLE USERS_FOODS;");
		//qdb.execSQL("DROP TABLE USERS;");
		//qdb.execSQL("DROP TABLE FOODS;");
	
		//qdb.execSQL("CREATE TABLE IF NOT EXISTS " + FOODS + " (food_id INTEGER PRIMARY KEY, food_name VARCHAR);");
		//qdb.execSQL("CREATE TABLE IF NOT EXISTS " + USERS + " (id INTEGER PRIMARY KEY, name VARCHAR NOT NULL, email VARCHAR);");
		//qdb.execSQL("CREATE TABLE IF NOT EXISTS USERS_FOODS(user_food_id INTEGER PRIMARY KEY, user_id INTEGER, food_id INTEGER, rating INTEGER, old_rating INTEGER, updated INTEGER, avg_rating INTEGER, FOREIGN KEY(user_id) REFERENCES USERS(user_id), FOREIGN KEY (food_id) REFERENCES FOODS(food_id));");
		
	
		Cursor c = qdb.rawQuery("SELECT * FROM USERS WHERE name = \""+ userName + "\"", null);
		if (c != null ) {
				if  (c.moveToFirst()) {
						qdb.execSQL("UPDATE USERS SET email = \""+ email + "\" WHERE name = \""+ userName + "\";");
				}
				else{
					qdb.execSQL("INSERT INTO USERS(name,email) VALUES (\""+ userName + "\",\""+ email + "\");");
				}
		}
		qdb.close();
	}
	
	public void addFood(String userName, String foodName, Integer rating) {
		
		
		userName = GetSantizedString(userName.toLowerCase(Locale.ENGLISH));
		foodName = GetSantizedString(foodName.toLowerCase(Locale.ENGLISH));
		int food_id = GetFoodId(foodName);
		int user_id = getUserId(userName);
		int users_foods_id = GetUsersFoodsId(user_id,food_id);
		
				
		if (user_id < 0){
			
		}
		//is it a new food?
		
		
		else if (food_id < 0){
			//yes, then double insert			emailemail
			SQLiteDatabase qdb = this.getWritableDatabase();
			qdb.execSQL("INSERT INTO FOODS(food_name) VALUES ('" + foodName +"');");
			qdb.close();
			
			food_id = GetFoodId(foodName);
			
			qdb = this.getWritableDatabase();
			qdb.execSQL("INSERT INTO USERS_FOODS(user_id,food_id, rating, old_rating, updated, avg_rating) VALUES ("+ user_id + ","+ food_id +","+ rating +",0,1," + rating +");");
			qdb.close();
		}
		else if(users_foods_id < 0){
			//is it a new users_foods entry?
			//yes then insert
			SQLiteDatabase qdb = this.getWritableDatabase();
			qdb.execSQL("INSERT INTO USERS_FOODS(user_id,food_id, rating, old_rating, updated, avg_rating) VALUES ("+ user_id + ","+ food_id +","+ rating +",0,1," + rating +");");
			qdb.close();
		}
		else {
			//no then update
			SQLiteDatabase qdb = this.getWritableDatabase();
			int old_rating = 0;
			//SELECT rating from USERS_FOODS where food_id = #{food_id} AND user_id = #{user_id}"
			Cursor c = qdb.rawQuery("SELECT rating from USERS_FOODS where food_id = " + food_id + " and user_id = "+ user_id, null);
			if (c != null ) {
				if (c.moveToFirst()) {
					old_rating= c.getInt(0);
				}
			}
			c.close();
			qdb.execSQL("UPDATE USERS_FOODS SET rating = " + rating + ",old_rating = "+ old_rating + ",updated = 1 WHERE user_id = " + user_id + " and food_id = " + food_id +";");			
			qdb.close();
		}
		
		
			
		
	}
	
	public void addSoloFood(String foodname){
		if (GetFoodId(foodname)<0){
		SQLiteDatabase qdb = this.getWritableDatabase();
		qdb.execSQL("INSERT INTO FOODS(food_name) VALUES ('" + foodname +"');");
		qdb.close();
		}
	}
	
	//unfinished
	public void updateUser(int user_id, int food_id, int avg_rating){
	//similar to addFood but only test for existancce in users_foods	
		
	}
	
	///////////////DEMO FUNCTIONS///////////
	
	public boolean insertText(){
		try{
			//DBHelper appDB = new DBHelper(context);
			SQLiteDatabase qdb = this.getWritableDatabase();
			//Log.d("DB Insert: ", "INSERT OR REPLACE INTO " + USERS + " (text) Values ("+ text + ");");
			qdb.execSQL("INSERT INTO USERS(name,email) VALUES ('Sean','sean@you.com');");
			qdb.close();
		}
		catch(SQLiteException se){
			Log.d("DB Insert Error: ",se.toString());
			return false;
		}
		return true;
	}

	public String getCount(){
		String toReturn = "";
		try{
			//DBHelper appDB = new DBHelper(context);
			SQLiteDatabase qdb = this.getReadableDatabase();
			//qdb.execSQL("CREATE TABLE IF NOT EXISTS " + EXAMPLE_TABLE + " (text VARCHAR);");
			Cursor c = qdb.rawQuery("SELECT * FROM " +
	    			USERS, null);
			if (c != null ) {
	    		if  (c.moveToFirst()) {
	    			do {
	    				String text = c.getString(c.getColumnIndex("email"));
	    				toReturn += text + "\n";
	    				
	    			}
	    			while (c.moveToNext());
	    		}
			}
			qdb.close(); 
		}
		catch(SQLiteException se){
			Log.d("DB Select Error: ",se.toString());
			return "Errord Out";
		}
		return toReturn;
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