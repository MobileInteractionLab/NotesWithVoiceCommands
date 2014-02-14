package com.mobileinteractionlabs.notes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter {
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
		
		if (view == null) {
			noteView = new View(mContext);
			noteView = inflater.inflate(R.layout.grid_note, null);
		}
		else {
			noteView = (View) view;
		}
		
		TextView noteText = (TextView) noteView.findViewById(R.id.tvGridNoteNote);
		TextView noteDate = (TextView) noteView.findViewById(R.id.tvGridNoteDate);
		View highlight = (View) noteView.findViewById(R.id.vGridNoteBackground);
		
		mCursor.moveToPosition(position);
		noteText.setText(mCursor.getString(mCursor.getColumnIndex(NotesHandler.TEXT_COLUMN)));
		

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd", Locale.getDefault());
		
		Date d;
		try {
			d = sdf.parse(mCursor.getString(mCursor.getColumnIndex(NotesHandler.TIMESTAMP_COLUMN)));
			noteDate.setText(sdf2.format(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		highlight.setBackgroundColor(0x0C0);

		return noteView;
	}
}
