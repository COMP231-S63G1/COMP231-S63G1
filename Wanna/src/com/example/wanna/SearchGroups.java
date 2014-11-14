package com.example.wanna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchGroups extends Activity {
	private Spinner groupCategorySpinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_groups);
		groupCategorySpinner = (Spinner) findViewById(R.id.spinnerGroupCategories);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.searchGroupCategory,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		groupCategorySpinner.setAdapter(adapter);
	}

	public void onClickCancel(View view){	
		Intent intent = new Intent(this, Login_Success.class);
		startActivity(intent);
		}
	
	public void enterApplication(View view){	
		Intent intent = new Intent(this, SearchGroups.class);
		startActivity(intent);
		}
	
}
