package com.wanna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.wanna.R;

public class SearchUserName extends Activity {
	EditText etSearchUserName;
	String searchUserName;
	String searchType;
	
	private static final String TAG_SEARCHTYPE = "searchType";
	private static final String TAG_SEARCHEMAIL = "searchEmail";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_user_name);
		etSearchUserName = (EditText)findViewById(R.id.etSearchUserName);	
		searchType = "Email";
	}
	
	public void onSearchEventClick(View view){
		searchUserName = etSearchUserName.getText().toString();
		if(searchUserName.equals("")){
			Toast.makeText(getApplicationContext(), "User name field empty",
					Toast.LENGTH_SHORT).show();
		}else{
			Intent intent = new Intent(getApplicationContext(),
					SearchUserResult.class);
			intent.putExtra(TAG_SEARCHTYPE, searchType);
			intent.putExtra(TAG_SEARCHEMAIL, searchUserName);
			startActivity(intent);
		}
	}
}
