package com.example.wanna;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ViewPrivateGroup extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_private_group);
	}

	public void onCancel(View view){	
		Intent intent = new Intent(this, Login_Success.class);
		startActivity(intent);
		}

}
