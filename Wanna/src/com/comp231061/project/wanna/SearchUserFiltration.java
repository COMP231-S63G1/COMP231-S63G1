package com.comp231061.project.wanna;

import com.comp231061.project.wanna.library.LocationTracker;
import com.comp231061.project.wanna.library.UserFunctions;
import com.comp231061.project.wanna.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchUserFiltration extends Activity {
	UserFunctions userFunctions = new UserFunctions();

	private static final String TAG_SEARCHTYPE = "searchType";
	private static final String TAG_SEARCHSTATUS = "searchStatus";
	private static final String TAG_SEARCHMINAGE = "searchMinAge";
	private static final String TAG_SEARCHMAXAGE = "searchMaxAge";
	private static final String TAG_SEARCHGENDER = "searchGender";
	private static final String TAG_SEARCHRANGE = "searchRange";
	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_LONGITUDE = "longitude";

	EditText etStatus;
	EditText etMinAge;
	EditText etMaxAge;
	RadioButton searchGenderSelect;
	RadioButton defaultRadio;
	RadioGroup rgFilterGender;
	Spinner locationRangeSpinner;

	String searchType;
	String searchStatus;
	int searchMinAge;
	int searchMaxAge;
	String searchGender;
	String searchRange;
	double latitude;
    double longitude;
    
	LocationTracker location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_user_filtration);
		
		location = new LocationTracker(SearchUserFiltration.this);
		
		searchType = "Filtration";
		etStatus = (EditText) findViewById(R.id.etStatus);
		etMinAge = (EditText) findViewById(R.id.etMinAge);
		etMaxAge = (EditText) findViewById(R.id.etMaxAge);
		rgFilterGender = (RadioGroup) findViewById(R.id.rgFilterGender);
		defaultRadio = (RadioButton)findViewById(R.id.rbtnBoth);
		defaultRadio.setChecked(true);
		locationRangeSpinner = (Spinner) findViewById(R.id.locationRange);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.locationRangeOptions,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		locationRangeSpinner.setAdapter(adapter);
	}

	public void onSearchClick(View view) {
		searchStatus = etStatus.getText().toString();
		if(searchStatus.equals(""))
		{
			Toast.makeText(getApplicationContext(), "Status is empty",
					Toast.LENGTH_SHORT).show();
		}
		if (etMinAge.getText().toString().equals("")) {
			searchMinAge = 18;
		} else {
			try {
				searchMinAge = Integer.parseInt(etMinAge.getText().toString());
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Minimum age invalid",
						Toast.LENGTH_SHORT).show();
			}
		}
		if (etMaxAge.getText().toString().equals("")) {
			searchMaxAge = 99;
		} else {
			try {
				searchMaxAge = Integer.parseInt(etMaxAge.getText().toString());
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Maximum age invalid",
						Toast.LENGTH_SHORT).show();
			}
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
		searchRange = locationRangeSpinner.getSelectedItem().toString();
		if(searchRange.equals("No limit")){
			searchRange = "40075";
		}
		if(location.canGetLocation()){            
            latitude = location.getLatitude();
            longitude = location.getLongitude();
           }else{
        	   Toast.makeText(getApplicationContext(), "Cannot get location",
						Toast.LENGTH_SHORT).show();
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
			intent.putExtra(TAG_SEARCHRANGE, searchRange);
			intent.putExtra(TAG_LATITUDE, latitude);
			intent.putExtra(TAG_LONGITUDE, longitude);
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
