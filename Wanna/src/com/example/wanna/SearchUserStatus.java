package com.example.wanna;

import com.example.wanna.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SearchUserStatus extends Activity {
	UserFunctions userFunctions = new UserFunctions();

	private static final String TAG_SEARCHTYPE = "searchType";
	private static final String TAG_SEARCHSTATUS = "searchStatus";
	private static final String TAG_SEARCHMINAGE = "searchMinAge";
	private static final String TAG_SEARCHMAXAGE = "searchMaxAge";
	private static final String TAG_SEARCHGENDER = "searchGender";

	EditText etStatus;
	EditText etMinAge;
	EditText etMaxAge;
	RadioButton searchGenderSelect;
	RadioGroup rgFilterGender;

	String searchType;
	String searchStatus;
	int searchMinAge;
	int searchMaxAge;
	String searchGender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_user_status);
		searchType = "Filtration";
		etStatus = (EditText) findViewById(R.id.etStatus);
		etMinAge = (EditText) findViewById(R.id.etMinAge);
		etMaxAge = (EditText) findViewById(R.id.etMaxAge);
		rgFilterGender = (RadioGroup) findViewById(R.id.rgFilterGender);
	}

	public void onSearchClick(View view) {
		searchStatus = etStatus.getText().toString();
		try {
			searchMinAge = Integer.parseInt(etStatus.getText().toString());
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Minimum age invalid",
					Toast.LENGTH_SHORT).show();
		}
		try {
			searchMaxAge = Integer.parseInt(etStatus.getText().toString());
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Maximum age invalid",
					Toast.LENGTH_SHORT).show();
		}
		if (searchMinAge < 18) {
			searchMinAge = 18;
		}
		if (searchMaxAge > 99) {
			searchMaxAge = 99;
		}
		searchGenderSelect = (RadioButton) findViewById(rgFilterGender
				.getCheckedRadioButtonId());
		searchGender = searchGenderSelect.getText().toString();
		if (searchGender.equals("Both")) {
			searchGender = "";
		}
		if (searchMinAge > searchMaxAge) {
			Toast.makeText(getApplicationContext(), "Age range invalid",
					Toast.LENGTH_SHORT).show();
		} else {
			Intent intent = new Intent(getApplicationContext(),
					SearchUserResult.class);
			intent.putExtra(TAG_SEARCHTYPE, searchType);
			intent.putExtra(TAG_SEARCHSTATUS, searchStatus);
			intent.putExtra(TAG_SEARCHMINAGE, searchMinAge);
			intent.putExtra(TAG_SEARCHMAXAGE, searchMaxAge);
			intent.putExtra(TAG_SEARCHGENDER, searchGender);
			startActivity(intent);
		}
	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.rbtnMale:
			if (checked)
				break;
		case R.id.rbtnFemale:
			if (checked)
				break;
		case R.id.rbtnBoth:
			if (checked)
				break;
		}
	}
}
