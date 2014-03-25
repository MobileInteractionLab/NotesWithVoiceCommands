package com.mobileinteractionlabs.notes;

import android.content.Context;
import android.util.Log;

public class Note {
	private static final String TAG = "Note";
	public static final String EDIT_EXTRA = "Edit_Note";
	
	private long mId;
	private String mDate;
	private String mTitle;
	private String mTimestamp;
	private String mText;
	private long mCategoryId;
	private int mPosition;
	private Context mContext;

	public Note(Context context, String title, String text, long categoryId) {
		mContext = context;
		mId = 0;
		mTitle = title;
		mText = text;
		mCategoryId = categoryId;
	}
	
	public Note(Context context, long id, String timestamp, String title,  String text, long categoryId) {
		mContext = context;
		mId = id;
		mTimestamp = timestamp;
		mText = text;
		mTitle = title;
		mCategoryId = categoryId;
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
	
	public String getTitle() {
		return mTitle;
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
	
	public void setTitle(String title) {
		mTitle = title;
	}
	
	public void setText(String text) {
		mText = text;
	}

	public void setCategoryId(long id) {
		mCategoryId = id;
	}
	
	public long save() {
		NotesHandler nh = new NotesHandler(mContext);
		
		if (mId == 0) {
			mId = nh.insert(this);
		}
		else {
			Log.d(TAG, "Updating note");
			nh.update(this);
		}
		nh.close();
		return mId;
	}
	
	public void delete() {
		NotesHandler nh = new NotesHandler(mContext);
		
		if (mId != 0) {
			nh.delete(this);
		}
		nh.close();
	}
}
