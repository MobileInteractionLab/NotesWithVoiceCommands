package com.mobileinteractionlabs.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class PicturesHandler {
	
	private static final String TAG = "PicturesHandler";
	
	//Database field titles
	public static final String PICTURE_TABLE = "PICTURE";
	public static final String ID_COLUMN = "_id";
	public static final String ABSOLUTEPATH_COLUMN = "AbsolutePath";
	public static final String NOTE_ID_COLUMN = "Note_Id";
	
	private DatabaseHandler mDatabaseHandler;
	private SQLiteDatabase mDb;
	private Context mContext;
	
	public PicturesHandler(Context context) {
		mContext = context;
		mDatabaseHandler = DatabaseHandler.getInstance(mContext);
		try {
			mDb = mDatabaseHandler.getWritableDatabase();
			Log.i(TAG, "Database opened");
		} 
		catch (SQLiteException SQLiteException) {
			Log.e(TAG, SQLiteException.getLocalizedMessage());
		}
	}
	
	/**
	 * This method returns the first picture for the note
	 * @param id
	 * @return Picture
	 */
	public Picture getPictureByNoteId(long id) {
		Picture picture = null;
		
		Cursor c = mDb.rawQuery(
				"SELECT p._id, p.AbsolutePath " +
				"FROM PICTURE AS p " +
				"WHERE p.Note_id = " + id, null);
		if (c.moveToNext()) {
			Log.d(TAG, "Query");
			long pId = c.getLong(c.getColumnIndex(ID_COLUMN));
			String path = c.getString(c.getColumnIndex(ABSOLUTEPATH_COLUMN));
			picture = new Picture (mContext);
			picture.setId(pId);
			picture.setAbsolutePath(path);
			picture.setNoteId(id);
		}
		
	   return picture;
	}
	
	/**
	 * This function returns a set of pictures related to a note
	 * @param id
	 * @return
	 */
	public Cursor getPicturesByNoteId(long id) {
		Cursor c = mDb.rawQuery(
				"SELECT p._id, p.AbsolutePath " +
				"FROM PICTURE AS p " +
				"WHERE p.Note_Id = " + id, null);
	   return c;
	}
	
	public void insert(Picture picture) {
		Log.d(TAG, "Inserting picture");
		ContentValues values = new ContentValues();
		values.put(ABSOLUTEPATH_COLUMN, picture.getAbsolutePath());
		values.put(NOTE_ID_COLUMN, picture.getNoteId());
		if (mDb.insert(PICTURE_TABLE, null, values) > 0) {
			Log.d(TAG, "Picture inserted");
		}
	}	
	
	public int update(Picture picture) {
		ContentValues values = new ContentValues();
		values.put(ABSOLUTEPATH_COLUMN, picture.getAbsolutePath());
		values.put(NOTE_ID_COLUMN, picture.getNoteId());
		return mDb.update(PICTURE_TABLE, values, ID_COLUMN + " = ?",
				new String[] { String.valueOf(picture.getId()) });
	}
	
	public boolean delete(Picture picture) {
		return mDb.delete(PICTURE_TABLE, ID_COLUMN + "=" + picture.getId(), null) > 0;
	}
	
	public void close() {
		if (mDb != null) {
			mDb.close();
		}
	}

}
