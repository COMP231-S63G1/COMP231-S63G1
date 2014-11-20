package com.example.wanna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class SearchUserName extends Activity {
	EditText etSearchUserName;
	String searchUserName;
	private static final String TAG_SEARCHUSERNAME = "searchUserName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_user_name);
		etSearchUserName = (EditText)findViewById(R.id.etSearchUserName);
		searchUserName = etSearchUserName.getText().toString();
		if(searchUserName.equals("")){
			Toast.makeText(getApplicationContext(), "User name field empty",
					Toast.LENGTH_SHORT).show();
		}else{
			Intent intent = new Intent(getApplicationContext(),
					SearchUserResult.class);
			intent.putExtra("searchUserName", searchUserName);
			startActivity(intent);
		}
	}
}
