package com.horribile.tips;

import com.horribile.tips.framework.ErrorDialog;
import com.horribile.tips.framework.ErrorDialog.OnCloseListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DemoActivity extends Activity {
	
	private ListView list;
	private String[] items = new String[] {"Regex demo", "Mask Demo",  "Two activities", "Error Dialog"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        list = (ListView)findViewById(R.id.listView1);
        list.setAdapter(
        		new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        );
        
        list.setOnItemClickListener(
        		new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Intent intent;
						
						switch(position){
						case 0:
							intent = new Intent(DemoActivity.this, RegexFormattingActivity.class);
							startActivity(intent);
							break;
						case 1:
							intent = new Intent(DemoActivity.this, MaskDemoActivity.class);
							startActivity(intent);
							break;
						case 2:
							intent = new Intent(DemoActivity.this, First.class);
							startActivity(intent);
							break;
						case 3:
					    	ErrorDialog.show(DemoActivity.this, "This is the error!",
					    			new OnCloseListener(){

										@Override
										public void OnClose() {
											
											Log.i("DialogsDemo", "Error dialog has been closed!");
											
										}
					    		
					    		}
					    	);
							break;
						default:
							break;
						}
						
					}
        			
        		}
        );
    }
}