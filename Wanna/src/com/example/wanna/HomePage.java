 package com.example.wanna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
	}

	public void onLoginClick(View view){	
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		}
	
	public void onRegisterClick(View view){	
		Intent intent = new Intent(this, Profile_Form.class);
		startActivity(intent);
		}

}
