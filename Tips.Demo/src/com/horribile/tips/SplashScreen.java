package com.horribile.tips;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SplashScreen extends Activity {

	/**
	 * The thread to process splash screen events
	 */
	private Thread mSplashThread;
	private boolean mShouldExit = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		// Splash screen view
		setContentView(R.layout.splash);

		// Start animating the image
		final ImageView splashImageView = (ImageView) 
				findViewById(R.id.SplashImageView);
		
		splashImageView.setBackgroundResource(R.drawable.flag);
		
		final AnimationDrawable frameAnimation = 
				(AnimationDrawable) splashImageView
				.getBackground();
		
		splashImageView.post(new Runnable() {
			public void run() {
				frameAnimation.start();
			}
		});

		final SplashScreen sPlashScreen = this;

		// The thread to wait for splash screen events
		mSplashThread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						// Wait given period of time or exit on touch
						wait(5000);
					}
				} catch (InterruptedException ex) {
				}

				finish();

				if (!mShouldExit) {
					// Run next activity
					Intent intent = new Intent();
					intent.setClass(sPlashScreen, DemoActivity.class);
					startActivity(intent);
				}

				return;

			}
		};

		mSplashThread.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return false;
	}

	@Override
	public void onBackPressed() {

		synchronized (mSplashThread) {
			mShouldExit = true;
			mSplashThread.notifyAll();
		}

		super.onBackPressed();
	}

	/**
	 * Processes splash screen touch events
	 */
	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		if (evt.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (mSplashThread) {
				mSplashThread.notifyAll();
			}
		}
		return true;
	}

}
