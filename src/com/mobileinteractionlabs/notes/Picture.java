package com.mobileinteractionlabs.notes;

import android.content.Context;
import android.util.Log;

public class Picture {
	private static final String TAG = "Picture";
	
	private long mId;
	private String mAbsolutePath;
	private long mNoteId;
	private Context mContext;
	
	public Picture(Context context) {
		mContext = context;	
		mId = 0;
	}
	
	public long getId() {
		return mId;
	}
	
	public String getAbsolutePath() {
		return mAbsolutePath;
	}
	
	public long getNoteId() {
		return mNoteId;
	}

	public void setId(long id) {
		mId = id;
	}
	
	public void setAbsolutePath(String absolutePath) {
		mAbsolutePath = absolutePath;
	}
	
	public void setNoteId(long id) {
		mNoteId = id;
	}
	
	public void save() {
		PicturesHandler ph = new PicturesHandler(mContext);
		
		if (mId == 0) {
			Log.d(TAG,"Requesting Insert");
			ph.insert(this);
		}
		else {
			ph.update(this);
		}
		ph.close();
	}
	
	public void delete() {
		PicturesHandler ph = new PicturesHandler(mContext);
		
		if (mId != 0) {
			ph.delete(this);
		}
		ph.close();
	}
}
