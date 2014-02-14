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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	private static final String TAG = "DatabaseHandler";
	private static final String DATABASE_NAME = "notesDB";
	private static final int DATABASE_VERSION = 4;

	// SQL Statements to create database Tables
	private static final String CREATE_TABLE_CATEGORY = 
		"CREATE TABLE [CATEGORY] (" +
		"[_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
		"[Title] TEXT NOT NULL," +
		"[Icon] TEXT NOT NULL," +
		"[Color] INTEGER NOT NULL);";
		
	private static final String CREATE_TABLE_NOTE =
		"CREATE TABLE [NOTE] (" +
		"[_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
		"[Timestamp] DATETIME DEFAULT CURRENT_TIMESTAMP," +
		"[Text] TEXT," +
		"[Category_Id]);"; //INTEGER NOT NULL CONSTRAINT [ID_CATEGORY] REFERENCES [CATEGORY]([_id])
			
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
		
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG,"Creating Database");
		
		try {
			db.execSQL(CREATE_TABLE_CATEGORY);
			db.execSQL(CREATE_TABLE_NOTE);   
		} 
		catch(Exception e) {
			 e.printStackTrace();
		}
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS [NOTE]");
		db.execSQL("DROP TABLE IF EXISTS [CATEGORY]");
		
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
		onCreate(db);
	}
}



