package com.example.wanna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchEvent extends Activity {
	

	private static final String TAG_SEARCHTYPE = "searchType";
	private static final String TAG_SEARCHEVENTNAME = "searchEventName";
	private static final String TAG_SEARCHEVENTCATEGORY = "searchEventCategory";
	
	EditText etSearchEventName;
	Spinner eventCategorySpinner;
	
	String searchType;
	String searchEventName;
	String searchEventCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_event);
		eventCategorySpinner = (Spinner) findViewById(R.id.spinnerEventCategories);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.createEventArray,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		eventCategorySpinner.setAdapter(adapter);
		
		etSearchEventName = (EditText) findViewById(R.id.etSearchEventName);
	}
	
	public void onSearchByNameClick(View view){
		searchType = "Name";
		searchEventName = etSearchEventName.getText().toString();	
		Intent intent = new Intent(this, SearchEventResult.class);
		intent.putExtra(TAG_SEARCHTYPE, searchType);
		intent.putExtra(TAG_SEARCHEVENTNAME, searchEventName);
		startActivity(intent);
	}
	
	public void onSearchByCategoryClick(View view){
		searchType = "Category";
		searchEventCategory = eventCategorySpinner.getSelectedItem().toString();	
		Intent intent = new Intent(this, SearchEventResult.class);
		intent.putExtra(TAG_SEARCHTYPE, searchType);
		intent.putExtra(TAG_SEARCHEVENTCATEGORY, searchEventCategory);
		startActivity(intent);		
	}

	public void onCancelClick(View view){	
		Intent intent = new Intent(this, Login_Success.class);
		startActivity(intent);
		}
}

