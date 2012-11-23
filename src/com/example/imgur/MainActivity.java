package com.example.imgur;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final String EXTRA_MESSAGE = "com.example.imgur.MESSAGE";
	public static final String SHOW_NEXT = "com.example.imgur.SHOW_NEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //bool showNext = getIntent().getBooleanExtra(MainActivity.SHOW_NEXT, false);

		new GalleryFeedTask(this).execute("http://imgur.com/gallery.json");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void showText(String text) { 
    	TextView textView = new TextView(this);
 		textView.setTextSize(40);
 		textView.setText(text);
 		
 		setContentView(textView);
    }

    public void sendMessage(View view) {
    	//Intent intent = new Intent(this, DisplayMessageActivity.class);
    	
    	//EditText editText = (EditText)findViewById(R.id.edit_message);
    	//String message = editText.getText().toString();
    	//intent.putExtra(EXTRA_MESSAGE, message);
    	
    	//startActivity(intent);
    }
}