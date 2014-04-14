package seanharrington.what2eat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper{
	final static String DB_NAME = "what2eat.db";
	final static int DB_VERSION = 1;
	private final String USERS = "USERS";
	//private final String USERS_FOODS = "USERS_FOODS";
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
	public Integer GetUserId(String name) {
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT id FROM USERS WHERE name = '" + name + "'", null);
		c.moveToFirst();
		int user_id= c.getInt(0);
		c.close();
		qdb.close(); 
		return user_id;
	}
	
	public Integer GetFoodId(String name) {
		return 0;
	}
	
	public String GetUserEmail(Integer userId) {
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT email FROM USERS WHERE id = "+userId, null);
		c.moveToFirst();
		String user_email= c.getString(0);
		c.close();
		qdb.close(); 
		return user_email;
	
	}
	
	public Integer GetDBRecordCount(String tableName){
		SQLiteDatabase qdb = this.getWritableDatabase();
		Cursor c = qdb.rawQuery("SELECT count(*) FROM " + tableName, null);
		c.moveToFirst();
		int count= c.getInt(0);
		c.close();
		qdb.close(); 
		
		return count;
	}
	
	public String[] populateUserArray(String[] nArray){
		SQLiteDatabase qdb = this.getReadableDatabase();
		int iCount = 0;
		Cursor c = qdb.rawQuery("SELECT name FROM " +
    			USERS, null);
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
		qdb.close(); 
	return nArray;
	}
	
	public String[] populateFoodArray(String[] nArray){
		SQLiteDatabase qdb = this.getReadableDatabase();
		int iCount = 0;
		Cursor c = qdb.rawQuery("SELECT food_name FROM " +
    			FOODS, null);
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
		qdb.close(); 
	return nArray;
	}
	

	
	public void addUser(String userName, String email) {
		SQLiteDatabase qdb = this.getWritableDatabase();
		//leave these 2 here for wiping the table
		//qdb.execSQL("DROP TABLE USERS;");
		//qdb.execSQL("CREATE TABLE IF NOT EXISTS " + USERS + " (id INTEGER PRIMARY KEY, name VARCHAR NOT NULL, email VARCHAR);");
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
		
		
	}
	
	
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

}