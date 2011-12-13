package com.horribile.tips;

import com.horribile.tips.framework.MaskedWatcher;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MaskDemoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mask_demo);
        
        EditText phone = (EditText)findViewById(R.id.phone);
        phone.addTextChangedListener(
        		new MaskedWatcher("(###) ###-##-##")
        );
        
        EditText date = (EditText)findViewById(R.id.date);
        date.addTextChangedListener(
        		new MaskedWatcher("####/##/##")
        );
        
        
    }
}