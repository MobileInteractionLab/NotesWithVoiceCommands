package com.mobileinteractionlabs.notes.Activities;

import com.mobileinteractionlabs.notes.DatabaseHandler;
import com.mobileinteractionlabs.notes.NoteAdapter;
import com.mobileinteractionlabs.notes.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	SimpleCursorAdapter cursorAdapter;

	private DatabaseHandler dbh;
	
	GridView gridView;
	Cursor cursor;

	String[] noteText;
	String[] noteDate;
	String[] noteColor;
	
	/*
	 * The following variables are used to build the sample drawer
	 */
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] mTags;    
	private DrawerLayout mDrawerLayout;    
	private ListView mDrawerList;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*
		 * Sample code to build and control the sample drawer
		 */
		mTags = getResources().getStringArray(R.array.sample_tags);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTags));
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);  
		
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
  
		
		dbh = new DatabaseHandler(this);
		populateStrings();
		gridView = (GridView) findViewById(R.id.gvNotes);
	    gridView.setAdapter(new NoteAdapter(this, noteText,noteDate,noteColor));
	        
		//populateGrid();

	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.syncState();
	}
	
	/**
	 * 
	 */
	private void populateStrings() {
		// TODO Auto-generated method stub
		
		cursor = dbh.getAllNotes();
		noteText = new String[cursor.getCount()];
		noteDate = new String[cursor.getCount()];
		noteColor = new String[cursor.getCount()];
		
		int index = 0;
		while (cursor.moveToNext()) {
			noteText[index] = cursor.getString(4);
			noteDate[index] = cursor.getString(3);
			noteColor[index] = cursor.getString(9);
			
			
			
			index++;
		}
		
		for(int i =0; i < noteText.length;i++){
			Log.d("noteText",noteText[i]);
			Log.d("noteDate",noteDate[i]);
			Log.d("noteColor",noteColor[i]);
		}
		
		
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		populateStrings();
		gridView.setAdapter(new NoteAdapter(this, noteText,noteDate,noteColor));
	}

	/**
	 * 
	 */
	private void populateGrid() {
		// TODO Auto-generated method stub
		try {

			cursor = dbh.getAllNotes();

			String[] from = new String[] { dbh.COLUMN_NOTE, dbh.COLUMN_DATE,
					dbh.COLUMN_COLOR };
			
			int[] to = new int[] { R.id.tvGridNoteNote,
					R.id.tvGridNoteDate, R.id.vGridNoteBackground };
			
			cursorAdapter = new SimpleCursorAdapter(this, R.layout.grid_note,
					cursor, from, to);

			cursorAdapter
			.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

				@Override
				public boolean setViewValue(View view, Cursor cursor,
						int columnIndex) {
					// TODO Auto-generated method stub
					if (view.getId() == R.id.vGridNoteBackground) {
						
						int color = cursor
								.getColumnIndex(dbh.COLUMN_COLOR);
						Log.d("COLOR", String.valueOf(color));
						color=getColor(color);
						((TextView) view).setBackgroundColor(color);
						return true;
						
					}else{
						return false;
					}
					
					
				}
				
			});
			
			//gridView.setAdapter(new NoteAdapter(this, numbers));
			gridView.setAdapter(cursorAdapter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param color
	 * @return
	 */
	protected int getColor(int color) {
		// TODO Auto-generated method stub
		int rColor=0;
		switch (color) {
		case 1:
			rColor=getResources().getColor(
					R.color.blue);
			
			break;
		case 2:
			rColor=getResources().getColor(
					R.color.green);
			
			break;
		case 3:
			rColor=getResources().getColor(
					R.color.orange);
			
			break;
		case 4:
			rColor=getResources().getColor(
					R.color.pink);
			
			break;
		case 5:
			rColor=getResources().getColor(
					R.color.purple);
			
			break;
		case 6:
			rColor=getResources().getColor(
					R.color.red);
			
			break;
		case 7:
			rColor=getResources().getColor(
					R.color.sky);
			
			break;
		}
		return rColor;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		switch (item.getItemId()) {
		case R.id.action_new:
			try {
				Intent intnt = new Intent(MainActivity.this,
						NewNoteActivity.class);

				startActivity(intnt);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
