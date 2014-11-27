package com.example.wanna;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/RawengulkSans-094.otf");
        TextView tv = (TextView) findViewById(R.id.CustomFontText);
        tv.setTypeface(tf);
	}
	
	public void enterApplication(View view){	
		Intent intent = new Intent(this, SearchUserFiltration.class);
		startActivity(intent);
		}
}

