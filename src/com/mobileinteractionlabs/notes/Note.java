package com.mobileinteractionlabs.notes;

import android.content.Context;
import android.util.Log;

public class Note {
	private static final String TAG = "Note";
	public static final String EDIT_EXTRA = "Edit_Note";
	
	private long mId;
	private String mDate;
	private String mTimestamp;
	private String mText;
	private long mCategoryId;
	private int mPosition;
	private Context mContext;

	public Note(Context context, String text) {
		mContext = context;
		mId = 0;
		mText = text;
	}
	
	public Note(Context context, long id, String timestamp, String text, long categoryId) {
		mContext = context;
		mId = id;
		mTimestamp = timestamp;
		mText = text;
		mCategoryId = categoryId;
	}
	
	public Note(int _position, String _time, String _date, String _note,
			String _picture, String _audio, String _latitude,
			String _longitude, int _color) {
		super();
		this.mPosition = _position;
		this.mTimestamp = _time;
		this.mDate = _date;
		this.mText = _note;
	}
	
	public long getId() {
		return mId;
	}
	
	public String getTimesamp() {
		return mTimestamp;
	}
	
	public String getDate() {
		return mDate;
	}
	
	public String getTime() {
		return mTimestamp;
	}
		
	public String getText() {
		return mText;
	}

	public long getCategoryId() {
		return mCategoryId;
	}
	
	public Category getCategory() {
		//TODO add code to retrieve category
		Category category = new Category();
		return category;
	}
	
	public int getPosition() {
		return mPosition;
	}
	
	public void setId(int id) {
		mId = id;
	}
	
	public void setTimestamp(String timestamp) {
		mTimestamp = timestamp;
	}
	
	public void setText(String text) {
		mText = text;
	}
	
	public void setPosition(int position) {
		mPosition = position;
	}
	
	public void save() {
		NotesHandler nh = new NotesHandler(mContext);
		
		if (mId == 0) {
			nh.insert(this);
		}
		else {
			Log.d(TAG, "Updating note");
			nh.update(this);
		}
		nh.close();
	}
	
	public void delete() {
		NotesHandler nh = new NotesHandler(mContext);
		
		if (mId != 0) {
			nh.delete(this);
		}
		nh.close();
	}
}
