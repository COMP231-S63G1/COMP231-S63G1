package com.example.wanna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchGroup extends Activity {
	EditText etSearchGroupName;
	Spinner groupCategorySpinner;
	
	String searchType;
	String searchGroupName;
	String searchGroupCategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_group);
		groupCategorySpinner = (Spinner) findViewById(R.id.spinnerGroupCategories);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.searchGroupCategory,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		groupCategorySpinner.setAdapter(adapter);
		
		etSearchGroupName = (EditText) findViewById(R.id.searchGroupName);
	}
	
	public void onSearchByNameClick(View view){
		searchType = "Name";
		searchGroupName = etSearchGroupName.getText().toString();	
		Intent intent = new Intent(this, SearchGroupResult.class);
		intent.putExtra("searchType", searchType);
		intent.putExtra("searchGroupName", searchGroupName);
		startActivity(intent);
	}
	
	public void onSearchByCategoryClick(View view){
		searchType = "Category";
		searchGroupCategory = groupCategorySpinner.getSelectedItem().toString();	
		Intent intent = new Intent(this, SearchGroupResult.class);
		intent.putExtra("searchType", searchType);
		intent.putExtra("searchGroupCategory", searchGroupCategory);
		startActivity(intent);		
	}

	public void onCancelClick(View view){	
		Intent intent = new Intent(this, Login_Success.class);
		startActivity(intent);
		}
	
	
}
