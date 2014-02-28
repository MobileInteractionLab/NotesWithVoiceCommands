/*
 * Description: DatabaseHandler will manage the database 
 * Created:2012-06-30 by HAMS
 * 
 */


package com.mobileinteractionlabs.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author Mobile Interaction Lab
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {
	private static final String TAG = "DatabaseHandler";
	
	private static final String DATABASE_NAME = "notesDB";
	private static final int DATABASE_VERSION = 9;

	private static final Object mLock = new Object();
	private static DatabaseHandler mInstance = null;
	
	/* SQL Statements to create database Tables */
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
		"[Title] TEXT," +
		"[Text] TEXT," +
		"[Category_Id]);"; //INTEGER NOT NULL CONSTRAINT [ID_CATEGORY] REFERENCES [CATEGORY]([_id])
	
	private static final String CREATE_TABLE_PICTURE =
			"CREATE TABLE [PICTURE] (" +
			"[_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
			"[AbsolutePath] TEXT NOT NULL,"+
			"[Note_Id] INTEGER NOT NULL CONSTRAINT [ID_NOTE] REFERENCES [NOTE]([_id]));";
	
	public static DatabaseHandler getInstance(Context context) {
		synchronized (mLock) {
			if (mInstance == null) {
				mInstance = new DatabaseHandler(context.getApplicationContext());
			}
		}
		return mInstance;
	}

	private DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG,"Creating Database");
		
		try {
			db.execSQL(CREATE_TABLE_CATEGORY);
			db.execSQL(CREATE_TABLE_NOTE); 
			db.execSQL(CREATE_TABLE_PICTURE);
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
		db.execSQL("DROP TABLE IF EXISTS [PICTURE]");
		
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



