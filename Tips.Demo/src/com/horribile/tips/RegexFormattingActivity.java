package com.horribile.tips;

import com.horribile.tips.framework.PartialRegexInputFilter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

public class RegexFormattingActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regex_demo);
        
        final EditText txt = (EditText)findViewById(R.id.phone);
        
        final String regex = "\\(\\d{3}\\)\\d{3}\\-\\d{2}\\-\\d{2}";
        
        txt.setFilters(
        		new InputFilter[] {
        				new PartialRegexInputFilter(regex)
        		}
        );
        
        txt.addTextChangedListener(
        		new TextWatcher(){

					public void afterTextChanged(Editable s) {
						String value  = s.toString();
						if(value.matches(regex))
							txt.setTextColor(Color.BLACK);
						else
							txt.setTextColor(Color.RED);
					}

					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
					}

					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
					}
        			
        		}
        );
        
    }
}