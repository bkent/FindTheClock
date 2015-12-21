package com.cytedesign.findtheclock;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
	
	Button simple, advanced;
	ImageButton clock;
	View view;
	Handler randomiseClockDelay;
	MediaPlayer mp;
	Boolean cancelRandomiseClock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view = this.getWindow().getDecorView();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		clock = (ImageButton) findViewById(R.id.bClock);
		clock.setOnClickListener(ClockListener);
		clock.setVisibility(View.INVISIBLE);
		simple = (Button) findViewById(R.id.bSimpleGame);
		simple.setOnClickListener(SimpleListener);
		advanced = (Button) findViewById(R.id.bAdvancedGame);
		advanced.setOnClickListener(AdvancedListener);
		
		randomiseClockDelay = new Handler();
		
		mp = new MediaPlayer();
		mp = MediaPlayer.create(getApplicationContext(), R.raw.clock); 
		
		cancelRandomiseClock = false;
	}

	 public OnClickListener ClockListener = new OnClickListener() {
	        public void onClick(View v) {
	        	clock.setVisibility(View.INVISIBLE);
	        	randomiseClockDelay.postDelayed(randomiseClock, 2000);
	        	try{
	        	    mp.release();
	        	}
	        	catch(Exception Ex){
	        		
	        	}
	        	cancelRandomiseClock = false;
	        }
	 };
	 
	 public OnClickListener SimpleListener = new OnClickListener() {
	        public void onClick(View v) {
	         	simple.setVisibility(View.INVISIBLE);
	        	advanced.setVisibility(View.INVISIBLE);
	        	randomiseClockDelay.postDelayed(randomiseClock, 1000);
	        	clock.setImageResource(R.drawable.large_clock);
	        	cancelRandomiseClock = false;
	        }
	 };
	 
	 public OnClickListener AdvancedListener = new OnClickListener() {
	        public void onClick(View v) {
	         	simple.setVisibility(View.INVISIBLE);
	        	advanced.setVisibility(View.INVISIBLE);
	        	randomiseClockDelay.postDelayed(randomiseClock, 1000);
	        	clock.setImageResource(R.drawable.ic_launcher);
	        	cancelRandomiseClock = false;
	        }
	 };
	  
	 private Runnable randomiseClock = new Runnable()
	 {
	     @Override
	     public void run()
	     {
	    	 if (!cancelRandomiseClock){
		    	 int x = (int) (Math.random() * (view.getWidth() - clock.getWidth())); 
			     int y = (int) (Math.random() * ((view.getHeight() - 100) - clock.getHeight()));
			 	
			     clock.setX(x);        
			     clock.setY(y);
			     clock.setVisibility(View.VISIBLE);
			     
			     try{
		        	    mp.release();
		        	}
		        	catch(Exception Ex){
		        		
		        	}
		         mp = MediaPlayer.create(getApplicationContext(), R.raw.clock);
		         mp.setLooping(true);
		         mp.start();
	    	 }
	     }
	 };
	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	 cancelRandomiseClock = true;
	         mp.release();
	         clock.setImageResource(R.drawable.ic_launcher);
	         clock.setVisibility(View.INVISIBLE);
	         simple.setVisibility(View.VISIBLE);
	         advanced.setVisibility(View.VISIBLE);
	         return true;
	     }

	     return super.onKeyDown(keyCode, event);
	 }
	 
	 @Override
     protected void onStop() 
     {
         super.onStop();
         // Stop the clock sound on home button press (not a home button listener, rather listens for the onStop event.
         mp.release();
     }
}
