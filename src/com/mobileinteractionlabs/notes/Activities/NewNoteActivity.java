/**
 * File Created		: 	18/01/2014
 * Author			: 	HAMS
 * Email			:	hamscorporation@gmail.com
 * Document Revision:
 * Last Date Modify :	18/01/2014	
 * Revision			:
 * Description		:
 * COPYRIGHT MOBILE INTERACTION LAB
 *
 */
package com.mobileinteractionlabs.notes.Activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mobileinteractionlabs.notes.DatabaseHandler;
import com.mobileinteractionlabs.notes.Note;
import com.mobileinteractionlabs.notes.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 *
 */
public class NewNoteActivity extends Activity {
	
	
	private DatabaseHandler dbh;
	
	//data for database
	String _time="",
			_date="",
			_note="",
			_picture="",
			_audio="",
			_latitude="",
			_longitude="";
	int _position,_color;
	
	// boolean to check if audio, picture or locations are set
	boolean note = false;
	boolean audio = false;
	boolean picture = false;
	boolean location = false;

	TextView tvNewNoteDate;
	EditText etNewNoteData;
	LinearLayout llNewNoteColor;

	String cTime;
	String cDate;
	String cColor;
	String noteText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.new_note);

		//Setup the action bar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		setTitle(R.string.new_note_activity_title);
		
		tvNewNoteDate = (TextView) findViewById(R.id.tvNewNoteDate);
		etNewNoteData = (EditText) findViewById(R.id.etNewNoteData);
		llNewNoteColor = (LinearLayout) findViewById(R.id.llNewNoteColor);

		dbh = new DatabaseHandler(this);
		
		getCurrentTimeDate();

	}

	/**
	 * 
	 */
	private void getCurrentTimeDate() {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		_color = c.get(Calendar.DAY_OF_WEEK);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss a");
		String cDate = sdf.format(c.getTime());
		
		//data for time
		sdf = new SimpleDateFormat("HH:mm:ss a");
		_time = sdf.format(c.getTime());
		//data for date
		sdf = new SimpleDateFormat("dd/MMMM/yyyy");
		_date = sdf.format(c.getTime());
		

		// cDate =
		// java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

		// show current Date
		tvNewNoteDate.setText(cDate);

		// place Note Color
		switch (_color) {
		case 1:
			llNewNoteColor.setBackgroundColor(getResources().getColor(
					R.color.blue));
			
			break;
		case 2:
			llNewNoteColor.setBackgroundColor(getResources().getColor(
					R.color.green));
			
			break;
		case 3:
			llNewNoteColor.setBackgroundColor(getResources().getColor(
					R.color.orange));
			
			break;
		case 4:
			llNewNoteColor.setBackgroundColor(getResources().getColor(
					R.color.pink));
			
			break;
		case 5:
			llNewNoteColor.setBackgroundColor(getResources().getColor(
					R.color.purple));
			
			break;
		case 6:
			llNewNoteColor.setBackgroundColor(getResources().getColor(
					R.color.red));
			
			break;
		case 7:
			llNewNoteColor.setBackgroundColor(getResources().getColor(
					R.color.sky));
			
			break;
		}

	}
	
	private void saveNote() {
		_note = etNewNoteData.getText().toString();

		_position =dbh.getLastPosition();
		
		// Prepare data to be saved to Database
		// Check if Edit text Note have text
		if (_note.length() > 0) {
			try {
				dbh.addNote(new Note(_position,_time,_date,_note,_picture,_audio,_latitude,_longitude,_color));
				dbh.close();
				Toast.makeText(
						NewNoteActivity.this,
						getString(R.string.NoteAdded),
						Toast.LENGTH_SHORT).show();
				returnIntent(RESULT_OK);
						

			} catch (Exception e) {
				Toast.makeText(
						NewNoteActivity.this,
						getString(R.string.NoteAddedError),
						Toast.LENGTH_SHORT).show();
						returnIntent(RESULT_CANCELED);
			}
		}	
	}

	private void returnIntent(int resultCode) {
		Intent intent = new Intent();
		intent.putExtra("result", true);
		setResult(resultCode, intent);
		finish();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_note, menu);
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
		}
		return false;
	}
}
