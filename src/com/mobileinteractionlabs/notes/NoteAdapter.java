package com.mobileinteractionlabs.notes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.Context;
import android.database.Cursor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.widget.AbsListView.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter {
	private static final String TAG = "NoteAdapter";
	private Context mContext;
	private Cursor mCursor;

	int color=0;
	
	public NoteAdapter(Context context, Cursor cursor) {
		mContext = context;
		mCursor = cursor;
	}
	
	@Override
	public int getCount() {
		return mCursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		long id = 0;
		mCursor.moveToPosition(position);
		id = mCursor.getLong(mCursor.getColumnIndex(NotesHandler.ID_COLUMN));
		return id;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
				
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View noteView;
		TextView noteText;
		
		if (view == null) {
			noteView = new View(mContext);
			noteView = inflater.inflate(R.layout.grid_note, null);
			
			WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			DisplayMetrics dm = new DisplayMetrics();
			display.getMetrics(dm);
			
			int height = (int) dm.widthPixels/2;
			int width = (int) dm.widthPixels/2;
			LinearLayout ll = (LinearLayout) noteView.findViewById(R.id.llGridNote);
			ll.setLayoutParams(new LayoutParams(width,height));			
		}
		else {
			noteView = (View) view;
		}
		
		TextView noteTitle = (TextView) noteView.findViewById(R.id.tvGridNoteTitle);
		TextView noteDate = (TextView) noteView.findViewById(R.id.tvGridNoteDate);
		View highlight = (View) noteView.findViewById(R.id.vGridNoteBackground);
		
		mCursor.moveToPosition(position);
		
		noteTitle.setText(mCursor.getString(mCursor.getColumnIndex(NotesHandler.TITLE_COLUMN)));
		noteText = (TextView) noteView.findViewById(R.id.tvGridNoteNote);
		noteText.setText(mCursor.getString(mCursor.getColumnIndex(NotesHandler.TEXT_COLUMN)));
		

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
		
		Date d;
		try {
			d = sdf.parse(mCursor.getString(mCursor.getColumnIndex(NotesHandler.TIMESTAMP_COLUMN)));	
			noteDate.setText(sdf2.format(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return noteView;
	}
}
