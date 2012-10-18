package com.horribile.tips;

import com.horribile.tips.framework.CustomActions;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * The first activity on the screen
 * @author kushnarev
 *
 */
public class First extends Activity {

	// Broadcast receiver to receive messages from the second activity
	BroadcastReceiver mReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);

		((RadioGroup) findViewById(R.id.radioGroup))
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(RadioGroup group, int checkedId) {
						Intent intent;
						switch (checkedId) {
						case R.id.show:
							Intent second = new Intent(First.this, Second.class);
							startActivity(second);
							break;
						case R.id.hide:
							
							intent = new Intent();
							intent.setAction(CustomActions.COMMUNICATION_ACTION);
							intent.putExtra("operation", "hide");
							// Broadcast to the second activity, so it will close itself
							sendBroadcast(intent);
							
							break;
						}
					}

				});

		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				// We received a message that second activity was closed
				((RadioButton) findViewById(R.id.hide)).setChecked(true);

			}

		};

		// Register receiver
		IntentFilter filter = new IntentFilter();
		filter.addAction(CustomActions.COMMUNICATION_ACTION);

		this.registerReceiver(mReceiver, filter);

		// Start second activity
		Intent intent = new Intent(this, Second.class);
		startActivity(intent);

	}
	
	@Override
	public void onDestroy(){
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	
}