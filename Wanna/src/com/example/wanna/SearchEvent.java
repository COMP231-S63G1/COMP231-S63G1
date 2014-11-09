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

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.search_event, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}

