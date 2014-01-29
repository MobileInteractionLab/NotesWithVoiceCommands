/*
 * Description: DatabaseHandler will manage the database 
 * Created:2012-06-30 by HAMS
 * 
 * All new methods, functions should be added at the bottom starting with a comment indicating the date, rev and developer
 * 
 * Last Modify: 2013-08-21 by HAMS
 * Current Revision: 4
 * All notes for version are at the bottom 
 * 
 * 
 * ALL COPYRIGHT HAMS CORPORATION subdivision HAMS APPS
 */


package com.mobileinteractionlabs.notes;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "notesDB";

	// data table name
	private static final String TABLE_NOTES = "table_notes";
		
	
	//Columns
	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_POSITION = "position";
	public final static String COLUMN_TIME = "time";
	public final static String COLUMN_DATE = "date";
	public final static String COLUMN_NOTE = "note";
	public final static String COLUMN_PICTURE = "picture";
	public final static String COLUMN_AUDIO = "audio";
	public final static String COLUMN_LATITUDE = "latitude";
	public final static String COLUMN_LONGITUDE = "longitude";
	public final static String COLUMN_COLOR = "color";
	
		
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		try{
		String DATABASE_CREATE = "CREATE TABLE " + TABLE_NOTES + " ( "
				+ COLUMN_ID + " INTEGER PRIMARY KEY, "
				+ COLUMN_POSITION + " INTEGER,"
				+ COLUMN_TIME + " TEXT,"
				+ COLUMN_DATE + " TEXT,"
				+ COLUMN_NOTE + " TEXT," 
				+ COLUMN_PICTURE + " TEXT,"
				+ COLUMN_AUDIO + " TEXT,"
				+ COLUMN_LATITUDE + " TEXT,"
				+ COLUMN_LONGITUDE + " TEXT,"
				+ COLUMN_COLOR + " INTEGER"
				+")";
	
	
	
		db.execSQL(DATABASE_CREATE);
		db.execSQL(TABLE_NOTES);   
		
		}catch(Exception e){
			 e.printStackTrace();
		}
			
	}

	

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDIT);
		if (newVersion > oldVersion){
			oldVersion++;
			while(oldVersion <= newVersion){
				switch(oldVersion) {
				   case 2:
					  //upgrades for version 2 here
				  
					   
				   }
				oldVersion++;
			}
			
					
		}
		
	}
	
	//Get all notes for Main Screen
	public Cursor getAllNotes() {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = this.getReadableDatabase();

	   Cursor c = db.query(true, TABLE_NOTES, new String[] { COLUMN_ID,
			   COLUMN_POSITION, COLUMN_TIME, COLUMN_DATE,COLUMN_NOTE,COLUMN_PICTURE,
			   COLUMN_AUDIO, COLUMN_LATITUDE,COLUMN_LONGITUDE,COLUMN_COLOR}
	   			,null, null, null, null, COLUMN_POSITION, null);
	   
	   return c;
	}
	

	//Add new note
	public void addNote(Note note) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_POSITION, note.get_position()); 
		values.put(COLUMN_TIME, note.get_time()); 
		values.put(COLUMN_DATE, note.get_date()); 
		values.put(COLUMN_NOTE, note.get_note()); 
		values.put(COLUMN_PICTURE, note.get_picture()); 
		values.put(COLUMN_AUDIO, note.get_audio()); 
		values.put(COLUMN_LATITUDE, note.get_latitude()); 
		values.put(COLUMN_LONGITUDE, note.get_longitude()); 
		values.put(COLUMN_COLOR, note.get_color()); 

		db.insert(TABLE_NOTES, null, values);
		db.close(); 
	}	
	
	//Delete Note
	public boolean deleteData(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TABLE_NOTES, COLUMN_ID + "=" + id, null) > 0;

	}
	
	//Update Note
	public int updateDatas(Note note) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_POSITION, note.get_position()); 
		values.put(COLUMN_TIME, note.get_time()); 
		values.put(COLUMN_DATE, note.get_date());
		values.put(COLUMN_NOTE, note.get_note()); 
		values.put(COLUMN_PICTURE, note.get_picture());
		values.put(COLUMN_AUDIO, note.get_audio());
		values.put(COLUMN_LATITUDE, note.get_latitude());
		values.put(COLUMN_LONGITUDE, note.get_longitude());
		values.put(COLUMN_COLOR, note.get_color());

		// updating row
		return db.update(TABLE_NOTES, values, COLUMN_ID + " = ?",
				new String[] { String.valueOf(note.get_id()) });
	}
	
	//Get last position
	
	public int getLastPosition() {
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.query(TABLE_NOTES, new String[] { COLUMN_ID},null,null, null, null, null);
		return c.getCount();
	}

}



