package com.example.wanna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateProfile extends Activity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	private ProgressDialog pDialog;

	UserFunctions userFunctions = new UserFunctions();
	// url to create user profile
	private String urlCreateProfile = userFunctions.URL_ROOT
			+ "DB_CreateProfile.php";
	// private String urlCreateProfile =
	// "http://192.168.137.1:80/wanna/DB_CreateProfile.php";

	// user profile JSONArray
	JSONArray userProfileArray = null;
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_NICKNAME = "nickName";

	EditText etUserNickName;
	RadioGroup rgUserGender;
	EditText etUserAge;
	EditText etUserDescription;

	String userNickName;
	RadioButton userGenderSelect;
	String userGender;
	int userAge;
	String userDescription;
	String userid;
	String sessionid;
	String nickName;
	int success;
	String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		// getting user id from intent
		Intent intent = getIntent();
		// getting user id (userid) from intent
		userid = intent.getStringExtra("userid");
		etUserNickName = (EditText) findViewById(R.id.name);
		rgUserGender = (RadioGroup) findViewById(R.id.userGenderGroup);
		etUserAge = (EditText) findViewById(R.id.age);
		etUserDescription = (EditText) findViewById(R.id.description);
	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.genderMale:
			if (checked)
				// Pirates are the best
				break;
		case R.id.genderFemale:
			if (checked)
				// Ninjas rule
				break;
		}
	}

	public void onCreateProfileClick(View view) {

		userNickName = etUserNickName.getText().toString();
		userGenderSelect = (RadioButton) findViewById(rgUserGender
				.getCheckedRadioButtonId());
		userGender = userGenderSelect.getText().toString();
		userAge = Integer.parseInt(etUserAge.getText().toString());
		userDescription = etUserDescription.getText().toString();
		if ((!userNickName.equals("")) && (!userGender.equals(""))
				&& (userAge >= 18) && (!userDescription.equals(""))) {
			new CreateUserProfileTask().execute(urlCreateProfile);
		} else if (userNickName.equals("")) {
			Toast.makeText(getApplicationContext(), "Name field empty",
					Toast.LENGTH_SHORT).show();
		} else if (userAge < 18) {
			Toast.makeText(getApplicationContext(), "Age is less than 18",
					Toast.LENGTH_SHORT).show();
		} else if (userDescription.equals("")) {
			Toast.makeText(getApplicationContext(), "Description field empty",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void onCancleCreateProfileClick(View view) {
		Intent i = new Intent(getApplicationContext(), Login_Success.class);
		startActivity(i);
	}

	private class CreateUserProfileTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CreateProfile.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... urls) {

			// Building Parameters
			List<NameValuePair> createProfileParams = new ArrayList<NameValuePair>();

			createProfileParams.add(new BasicNameValuePair("userid", userid));
			createProfileParams.add(new BasicNameValuePair("userNickName",
					userNickName));
			createProfileParams.add(new BasicNameValuePair("userGender",
					userGender));
			createProfileParams.add(new BasicNameValuePair("userAge", Integer
					.toString(userAge)));
			createProfileParams.add(new BasicNameValuePair("userDescription",
					userDescription));
			// getting JSON string from URL
			JSONObject json = jsonParser.getJSONFromUrl(urlCreateProfile,
					createProfileParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				Editor editor = sharedpreferences.edit();
				editor.putString("sessionID", json.optString(TAG_SESSIONID));
				editor.putString("userID", json.optString(TAG_USERID));
				editor.putString("nickName", json.optString(TAG_NICKNAME));
				editor.commit();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (success == 1) {
				// successfully created profile
				Intent intent = new Intent(getApplicationContext(),
						ViewProfile.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
