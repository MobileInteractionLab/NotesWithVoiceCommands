/**
 * 
 */
package com.mobileinteractionlabs.notes.Activities;

import com.mobileinteractionlabs.notes.Category;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener {
	private static final String TAG = "MainActivity";
	private GridView mGridView;
	private ImageButton mPadlock;
	private ImageButton mSwheel;
	private ImageButton mBulb;
	private NoteAdapter mNoteAdapter;
	private long mCurrentCategory = Category.SWHEEL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		mGridView = (GridView) findViewById(R.id.gvNotes);
		
		//Category Selector Buttons
		mPadlock = (ImageButton) findViewById(R.id.ibPadlock);
		mSwheel = (ImageButton) findViewById(R.id.ibSWheel);
		mBulb = (ImageButton) findViewById(R.id.ibBulb);
		
		mPadlock.setOnClickListener(this);
		mSwheel.setOnClickListener(this);
		mBulb.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		populateGridView(mCurrentCategory);
	}
	
	private void populateGridView(long category) {
		NotesHandler nh = new NotesHandler(this);
		Cursor cursor = nh.getAllNotesByCategoryId(category);
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

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibPadlock:
			mCurrentCategory = Category.PADLOCK;
			mPadlock.setBackgroundResource(R.drawable.bk_tab_active);
			mSwheel.setBackgroundResource(R.drawable.bk_tab_inactive);
			mBulb.setBackgroundResource(R.drawable.bk_tab_inactive);			
			break;
		case R.id.ibSWheel:
			mCurrentCategory = Category.SWHEEL;
			mPadlock.setBackgroundResource(R.drawable.bk_tab_inactive);
			mSwheel.setBackgroundResource(R.drawable.bk_tab_active);
			mBulb.setBackgroundResource(R.drawable.bk_tab_inactive);	
			break;
		case R.id.ibBulb:
			mCurrentCategory = Category.BULB;
			mPadlock.setBackgroundResource(R.drawable.bk_tab_inactive);
			mSwheel.setBackgroundResource(R.drawable.bk_tab_inactive);
			mBulb.setBackgroundResource(R.drawable.bk_tab_active);	
			break;
		default:
			mCurrentCategory = Category.SWHEEL;
		}
		populateGridView(mCurrentCategory);
	}
}
