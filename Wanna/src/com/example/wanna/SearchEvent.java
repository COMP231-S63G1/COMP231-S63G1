package com.example.wanna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SearchEvent extends Activity {
	
	EditText etSearchEvent;
	String searchEventName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_event);
		etSearchEvent = (EditText)findViewById(R.id.etSearchEvent);
	}
	
	public void onSearchEventClick(View view){
		searchEventName = etSearchEvent.getText().toString();
		Intent intent = new Intent(getApplicationContext(), SearchEventResult.class);
		intent.putExtra("searchEventName", searchEventName);
		startActivity(intent);	
	}
}

