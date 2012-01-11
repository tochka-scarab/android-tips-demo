package com.horribile.tips;

import java.util.HashMap;

import com.horribile.tips.framework.CustomMenu.OnMenuItemSelectedListener;
import com.horribile.tips.framework.ErrorDialog;
import com.horribile.tips.framework.ErrorDialog.OnCloseListener;
import com.horribile.tips.framework.CustomMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DemoActivity extends Activity {

	private ListView list;
	private String[] items = new String[] { "Regex demo", "Mask Demo",
			"Two activities", "Error Dialog" };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		HashMap<Integer, String> menuItems = new HashMap<Integer, String>();
		menuItems.put(0, "Regex Demo");
		menuItems.put(1, "Mask Demo");
		menuItems.put(2, "Two Activities");
		menuItems.put(3, "Error Dialog");

		CustomMenu.show(this, menuItems, new OnMenuItemSelectedListener() {

			@Override
			public void MenuItemSelectedEvent(Integer selection) {

				Intent intent = new Intent();
				switch (selection) {
				case 0:
					intent = new Intent(DemoActivity.this,
							RegexFormattingActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(DemoActivity.this,
							MaskDemoActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(DemoActivity.this, First.class);
					startActivity(intent);
					break;
				case 3:
					ErrorDialog.show(DemoActivity.this, "This is the error!",
							new OnCloseListener() {

								@Override
								public void OnClose() {

									Log.i("DialogsDemo",
											"Error dialog has been closed!");

								}

							});
					break;

				default:
					break;
				}
			}
		});

		return false;
	}
	
	public void onBackPressed(){
		CustomMenu.hide();
		super.onBackPressed();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items));

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent;

				switch (position) {
				case 0:
					intent = new Intent(DemoActivity.this,
							RegexFormattingActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(DemoActivity.this,
							MaskDemoActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(DemoActivity.this, First.class);
					startActivity(intent);
					break;
				case 3:
					ErrorDialog.show(DemoActivity.this, "This is the error!",
							new OnCloseListener() {

								@Override
								public void OnClose() {

									Log.i("DialogsDemo",
											"Error dialog has been closed!");

								}

							});
					break;
				default:
					break;
				}

			}

		});
	}
}