package com.horribile.tips;

import com.horribile.tips.framework.CustomActions;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * The second activity on the screen
 * @author kushnarev
 *
 */
public class Second extends Activity {
	
	BroadcastReceiver mReceiver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		// It's needed for first activity to receive touch events
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

		// The receiver to process messages from the first activity
		IntentFilter filter = new IntentFilter();
		filter.addAction(CustomActions.COMMUNICATION_ACTION);
		
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				
				// The first activity wants to close this one
				String operation = intent.getStringExtra("operation");
				if(operation.equals("hide"))
					finish();
				
			}

		};
				
		this.registerReceiver(mReceiver, filter);

	}
	
	@Override
	public void onDestroy(){
		
		this.unregisterReceiver(mReceiver);

		// Inform the first activity we're closing 
		Intent intent = new Intent();
		intent.setAction(CustomActions.COMMUNICATION_ACTION);
		intent.putExtra("operation", "hide");
		sendBroadcast(intent);
		
		
		super.onDestroy();
	}


}
