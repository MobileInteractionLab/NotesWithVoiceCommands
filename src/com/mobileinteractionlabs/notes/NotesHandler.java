package com.mobileinteractionlabs.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class NotesHandler {
	
	private static final String TAG = "NotesHandler";
	
	//Database field titles
	public static final String NOTE_TABLE = "NOTE";
	public static final String ID_COLUMN = "_id";
	public static final String TIMESTAMP_COLUMN = "Timestamp";
	public static final String TITLE_COLUMN = "Title";
	public static final String TEXT_COLUMN = "Text";
	public static final String CATEGORY_ID_COLUMN = "Category_Id";
	
	private DatabaseHandler mDatabaseHandler;
	private SQLiteDatabase mDb;
	private Context mContext;
		
	public NotesHandler(Context context) {
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
	
	//Get all notes for Main Screen
	public Cursor getAllNotes() {
		Cursor c = mDb.rawQuery(
				"SELECT n._id, datetime(n.Timestamp,'localtime') AS Timestamp, n.Title, n.Text, n.Category_Id " +
				"FROM NOTE AS n " +
				"ORDER BY Timestamp DESC", null);
	   return c;
	}
	
	public Cursor getAllNotesByCategoryId(long categoryId) {
		Cursor c = mDb.rawQuery(
				"SELECT n._id, datetime(n.Timestamp,'localtime') AS Timestamp, n.Title, n.Text, n.Category_Id " +
				"FROM NOTE AS n " +
				"WHERE n.Category_Id = " + categoryId +
				" ORDER BY Timestamp DESC", null);
	   return c;
	}
	
	public Note getNoteById(Long id) {
		long mId = 0;
		String mTimestamp = "";
		String mTitle = "";
		String mText = "";
		long mCategoryId = 0;
		
		Cursor c = mDb.rawQuery(
				"SELECT n._id, datetime(n.Timestamp,'localtime') AS Timestamp, n.Title, n.Text, n.Category_Id " +
				"FROM NOTE AS n " +
				"WHERE n._id = " + id, null);
		
		if (c.moveToFirst()) {
			mId = c.getLong(c.getColumnIndex(ID_COLUMN));
			mTimestamp = c.getString(c.getColumnIndex(TIMESTAMP_COLUMN));
			mTitle =  c.getString(c.getColumnIndex(TITLE_COLUMN));
			mText =  c.getString(c.getColumnIndex(TEXT_COLUMN));
			mCategoryId = c.getLong(c.getColumnIndex(CATEGORY_ID_COLUMN));
		}
		return new Note(mContext, mId, mTimestamp, mTitle, mText, mCategoryId);
	}
	
	public long insert(Note note) {
		ContentValues values = new ContentValues();
		values.put(TITLE_COLUMN, note.getTitle());
		values.put(TEXT_COLUMN, note.getText());
		values.put(CATEGORY_ID_COLUMN, note.getCategoryId());
		return mDb.insert(NOTE_TABLE, null, values);
	}	
	
	public int update(Note note) {
		ContentValues values = new ContentValues();
		values.put(TIMESTAMP_COLUMN, note.getTimesamp());
		values.put(TITLE_COLUMN, note.getTitle());
		values.put(TEXT_COLUMN, note.getText());
		values.put(CATEGORY_ID_COLUMN, note.getCategoryId());
		return mDb.update(NOTE_TABLE, values, ID_COLUMN + " = ?",
				new String[] { String.valueOf(note.getId()) });
	}
	
	public boolean delete(Note note) {
		return mDb.delete(NOTE_TABLE, ID_COLUMN + "=" + note.getId(), null) > 0;
	}
	
	public void close() {
		if (mDb != null) {
			mDb.close();
		}
	}
}
