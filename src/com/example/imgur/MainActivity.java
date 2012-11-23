package com.example.imgur;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater.Filter;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private static final String IMGUR_GALLERY_URL = "http://imgur.com/gallery.json";
	private int _imageCount = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        gestureDetector = new GestureDetector(new MyGestureDetector(this));
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        
        ImageView imageView = (ImageView)findViewById(R.id.galleryImage);
        imageView.setOnTouchListener(gestureListener);
        imageView.setOnClickListener(MainActivity.this);

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.layout);
        layout.setOnTouchListener(gestureListener);
        layout.setOnClickListener(MainActivity.this);

        showNextImage();
    }

    private void showNextImage() {
    	new GalleryFeedTask(this, _imageCount).execute(IMGUR_GALLERY_URL);
    	_imageCount++;
    }

    private void showPreviousImage() {
    	if (_imageCount == 0) {
    		Toast.makeText(MainActivity.this, "Can't go back", Toast.LENGTH_SHORT).show();
    		return;
    	}

    	_imageCount--;
    	new GalleryFeedTask(this, _imageCount).execute(IMGUR_GALLERY_URL);
    	
    }

    public void showErrorMessage(String errorMessage) { 
 		showTitle("Error: " + errorMessage);
    }
    
    public void showTitle(String title) {
    	TextView textView = (TextView)findViewById(R.id.titleText);
 		textView.setText(title);
    }

    public void showImage(Drawable image) {
    	ImageView galleryImage = (ImageView) findViewById(R.id.galleryImage);
		galleryImage.setImageDrawable(image);
    }

    public void nextImage(View view) {
    	showNextImage();
    }
    
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    class MyGestureDetector extends SimpleOnGestureListener {
    	private MainActivity _mainActivity;
    	
    	public MyGestureDetector(MainActivity mainActivity) {
    		_mainActivity = mainActivity;
    	}

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	_mainActivity.showNextImage();
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	_mainActivity.showPreviousImage();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

    }

	@Override
	public void onClick(View arg) {
		
	}
}