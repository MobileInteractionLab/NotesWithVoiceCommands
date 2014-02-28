/**
 * 
 */
package com.mobileinteractionlabs.notes.Activities;

import com.mobileinteractionlabs.notes.Note;
import com.mobileinteractionlabs.notes.NoteAdapter;
import com.mobileinteractionlabs.notes.NotesHandler;
import com.mobileinteractionlabs.notes.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity implements OnItemClickListener {
	private static final String TAG = "MainActivity";
	private GridView mGridView;
	private NoteAdapter mNoteAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGridView = (GridView) findViewById(R.id.gvNotes);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		populateGridView();
	}
	
	private void populateGridView() {
		NotesHandler nh = new NotesHandler(this);
		Cursor cursor = nh.getAllNotes();
		mNoteAdapter = new NoteAdapter(this, cursor);
		mGridView.setAdapter(mNoteAdapter);
		mGridView.setOnItemClickListener(this);
		nh.close();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long rowId) {
		editNote(mNoteAdapter.getItemId(position));		
	}
	
	public void editNote(Long id) {
		Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
		intent.putExtra(Note.EDIT_EXTRA, id);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_new:
					editNote(0L);
				return true;
			case R.id.action_settings:
				return true;
			case R.id.action_help:
				return true;
			case R.id.action_about:
				return true;
			default:
				return false;
		}
	}
}
