package com.example.wanna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewProfile extends Activity {

	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	private ProgressDialog pDialog;

	ArrayList<HashMap<String, String>> viewProfileInformationList;
	TextView tvProfileNickName;
	TextView tvProfileGender;
	TextView tvProfileAge;
	TextView tvProfileDescription;
	TextView tvStatus;

//	String profileID;
	String sessionID;
	String userID;
	String nickName;
	String age;
	String gender;
	String description;
	String status;
	int success;

	// url to view profile info
	private String urlViewProfileInformation = userFunctions.URL_ROOT
			+ "DB_ViewProfileInformation.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ProfileInformation = "profileInformation";
	private static final String TAG_ProfileNickName = "nickName";
//	private static final String TAG_ProfileID = "profileID";
	private static final String TAG_ProfileAge = "age";
	private static final String TAG_ProfileGender = "gender";
	private static final String TAG_ProfileDescription = "description";
	private static final String TAG_Status = "eventName";

	JSONObject profileInformation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		// Edit Text
		tvProfileNickName = (TextView) findViewById(R.id.tvProfileNickNameValue);
		tvProfileGender = (TextView) findViewById(R.id.tvProfileGenderValue);
		tvProfileAge = (TextView) findViewById(R.id.tvProfileAgeValue);
		tvProfileDescription = (TextView) findViewById(R.id.tvProfileDescriptionValue);
		tvStatus = (TextView) findViewById(R.id.tvProfileStatusValue);
		
		new ViewProfileInformationTask().execute();
	}

	public void onViewProfileInformationBackClick(View view) {
		Intent intent = new Intent(getApplicationContext(), Login_Success.class);
		startActivity(intent);
	}

	public void onEditProfileClick(View view) {
		Intent intent = new Intent(getApplicationContext(), EditProfile.class);
		intent.putExtra("nickName", nickName);
		intent.putExtra("description", description);
		startActivity(intent);
	}

	private class ViewProfileInformationTask extends
			AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewProfile.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... urls) {
			sessionID = sharedpreferences.getString("sessionID", "");
			userID = sharedpreferences.getString("userID", "");
			nickName = sharedpreferences.getString("nickName", "");
			// Building Parameters
			List<NameValuePair> ViewProfileParams = new ArrayList<NameValuePair>();
			ViewProfileParams
					.add(new BasicNameValuePair("sessionID", sessionID));
			ViewProfileParams.add(new BasicNameValuePair("userID", userID));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(
					urlViewProfileInformation, ViewProfileParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			if (success == 1) {
				// successfully received profile info
				JSONArray profileInformationArray = json
						.optJSONArray(TAG_ProfileInformation); // JSON Array
				// get first profile object from JSON Array
				profileInformation = profileInformationArray.optJSONObject(0);
				nickName = profileInformation.optString(TAG_ProfileNickName);
				age = profileInformation.optString(TAG_ProfileAge);
				gender = profileInformation.optString(TAG_ProfileGender);
				description = profileInformation.optString(TAG_ProfileDescription);
				status = profileInformation.optString(TAG_Status);
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			tvProfileNickName.setText(nickName);
			tvProfileGender.setText(gender);
			tvProfileAge.setText(age);
			tvProfileDescription.setText(description);
			tvStatus.setText(status);
			
		}
	}
}
