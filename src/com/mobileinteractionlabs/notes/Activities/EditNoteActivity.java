/*
 * 
 */
package com.mobileinteractionlabs.notes.Activities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.mobileinteractionlabs.notes.Note;
import com.mobileinteractionlabs.notes.NotesHandler;
import com.mobileinteractionlabs.notes.Picture;
import com.mobileinteractionlabs.notes.PicturesHandler;
import com.mobileinteractionlabs.notes.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 
 * @author mobile interaction lab
 *
 */
public class EditNoteActivity extends Activity implements OnClickListener {
	private static final String TAG = "EditNoteActivity";
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	private TextView mNoteDate;
	private EditText mNoteTitle;
	private EditText mNoteText;
	private ImageButton mMic;
	private ImageButton mCamera;
	private String mTitle = "";
	private String mText = "";
	private Long mNoteId;
	private Note mNote = null;
	
	private Picture picture;
	private Uri fileUri;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_edit_note);
	
		//Setup the action bar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		mNoteDate = (TextView) findViewById(R.id.tvNoteDate);
		mNoteTitle = (EditText) findViewById(R.id.evNoteTitle);
		mNoteText = (EditText) findViewById(R.id.etNoteText);
		mMic = (ImageButton) findViewById(R.id.ibMic);
		mCamera = (ImageButton) findViewById(R.id.ibCamera);
		
		mMic.setOnClickListener(this);
		mCamera.setOnClickListener(this);
		
		Intent intent =  getIntent();
		mNoteId = intent.getLongExtra(Note.EDIT_EXTRA, 0);
		if (mNoteId > 0) {
			setTitle(R.string.edit_note_activity_title);
			populateNote();
		} else {
			setTitle(R.string.new_note_activity_title);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
			mNoteDate.setText(sdf.format(new Date()));
		}
	}

	private void populateNote() {
		NotesHandler nh = new NotesHandler(this);
		mNote = nh.getNoteById(mNoteId);
		nh.close();
		
		mNoteDate.setText(mNote.getTimesamp());
		mNoteTitle.setText(mNote.getTitle());
		mNoteText.append(mNote.getText());
		
		PicturesHandler ph = new PicturesHandler(this);
		picture = ph.getPictureByNoteId(mNoteId);
		ph.close();
		
		if (picture != null) {
			mCamera.setImageResource(R.drawable.btn_picture_normal);
			mCamera.setId(R.id.ibPicture);
		} else {
			mCamera.setImageResource(R.drawable.btn_camera_normal);
			mCamera.setId(R.id.ibCamera);
		}
		mCamera.invalidate();
	}
	
	private void saveNote() {
		mTitle = mNoteTitle.getText().toString();
		mText = mNoteText.getText().toString();
		
		// Check if Edit text Note have text
		if (mText.length() > 0) {
			try {
				if (mNote == null) {
					mNote = new Note(this, mTitle, mText);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
					sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
					mNote.setTimestamp(sdf.format(new Date()));
					mNote.setText(mText);
				}
				long noteId = mNote.save();
				picture.setNoteId(noteId);
				picture.save();
				
				returnIntent(RESULT_OK);
			} catch (Exception e) {
						returnIntent(RESULT_CANCELED);
			}
		} else {
			returnIntent(RESULT_CANCELED);
		}
	}

	private void deleteNote() {
		if (mNote != null) {
			mNote.delete();
			returnIntent(RESULT_OK);
		}
	}
	
	private void returnIntent(int resultCode) {
		Intent intent = new Intent();
		intent.putExtra("result", true);
		setResult(resultCode, intent);
		finish();
	}
		
	private void takePicture() {
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // create path to store images
	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	private void displayPicture() {
		Uri uri = Uri.parse(picture.getAbsolutePath());
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(uri, "image/*");
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			returnIntent(RESULT_CANCELED);
			return true;
		case R.id.action_save:
			saveNote();
			return true;
		case R.id.action_delete:
			deleteNote();
		}
		return false;
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.ibMic:
			break;
		case R.id.ibCamera:
			takePicture();
			break;
		case R.id.ibPicture:
			displayPicture();
		default:
			break;
		}
	}
	
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.
	
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.
	    Log.d("TXTO", mediaStorageDir.getAbsolutePath());
	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d(TAG, "failed to create directory");
	            return null;
	        }
	    }
	
	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else {
	        return null;
	    }
	
	    return mediaFile;
	}
	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data){
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ) {
	        if (resultCode == RESULT_OK) {
	        	picture = new Picture(this);
	        	picture.setAbsolutePath(fileUri.toString());
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }
	}
}
