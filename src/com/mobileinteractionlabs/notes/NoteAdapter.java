package com.mobileinteractionlabs.notes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter {
	
	private Context context;
	
	private final String[] nText;
	private final String[] nDate;
	private final String[] nColor;
	
	int color=0;
	
	public NoteAdapter(Context context, String[] nText, String[] nDate, String[] nColor) {
		this.context = context;
		
		this.nText = nText;
		this.nDate = nDate;
		this.nColor = nColor;
	}
	
	@Override
	public int getCount() {
		return nText.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View noteView;
		
		Log.d("position",String.valueOf(position));
		
		if (view == null) {
			noteView = new View(context);
			noteView = inflater.inflate(R.layout.grid_note, null);
		}
		else {
			noteView = (View) view;
		}
		
		TextView noteText = (TextView) noteView.findViewById(R.id.tvGridNoteNote);
		TextView noteDate = (TextView) noteView.findViewById(R.id.tvGridNoteDate);
		View highlight = (View) noteView.findViewById(R.id.vGridNoteBackground);
		
		noteText.setText(nText[position]);
		noteDate.setText(nDate[position]);
		
		color = Integer.valueOf(nColor[position]);
		
		highlight.setBackgroundColor(getColors(color));
		return noteView;
	}

	/**
	 * @param color2
	 * @return
	 */
	private int getColors(int color2) {
		// TODO Auto-generated method stub
		int rColor=0;
		switch (color) {
		case 1:
			rColor=context.getResources().getColor(
					R.color.blue);
			
			break;
		case 2:
			rColor=context.getResources().getColor(
					R.color.green);
			
			break;
		case 3:
			rColor=context.getResources().getColor(
					R.color.orange);
			
			break;
		case 4:
			rColor=context.getResources().getColor(
					R.color.pink);
			
			break;
		case 5:
			rColor=context.getResources().getColor(
					R.color.purple);
			
			break;
		case 6:
			rColor=context.getResources().getColor(
					R.color.red);
			
			break;
		case 7:
			rColor=context.getResources().getColor(
					R.color.sky);
			
			break;
		}
		return rColor;
	}	
}
