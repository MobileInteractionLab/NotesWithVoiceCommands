package com.mobileinteractionlab.noteswithvoicecommands;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NotesDesktop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_desktop);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notes_desktop, menu);
        return true;
    }
    
}
