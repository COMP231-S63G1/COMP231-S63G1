package com.example.wanna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ViewPublicGroup extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public_group);
	}
	
	public void onCancel(View view){	
		Intent intent = new Intent(this, Login_Success.class);
		startActivity(intent);
		}

	
}
