package com.example.wanna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;

import android.app.Activity;
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

public class CreatePersonProfile extends Activity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	UserFunctions userFunctions = new UserFunctions();
	// url to create user profile
	private String urlCreateProfile = UserFunctions.URL_ROOT
			+ "DB_CreatePersonProfile.php";

	// user profile JSONArray
	JSONArray userProfileArray = null;
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_PROFILEID = "profileid";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_USERGENDER = "userGender";
	private static final String TAG_USERAGE = "userAge";
	private static final String TAG_USERDESCRIPTION = "userDescription";

	EditText etUserNickName;
	RadioButton userGenderSelect;
	RadioGroup rgUserGender;
	EditText etUserAge;
	EditText etUserDescription;

	String userType;
	String userNickName;
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
		setContentView(R.layout.activity_create_person_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		Intent intent = getIntent();
		userid = intent.getStringExtra(TAG_USERID);
		userType = intent.getStringExtra(TAG_USERTYPE);
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
		Intent i = new Intent(getApplicationContext(), PersonLoginSuccess.class);
		startActivity(i);
	}

	private class CreateUserProfileTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... urls) {
			
			// Building Parameters
			List<NameValuePair> createProfileParams = new ArrayList<NameValuePair>();

			createProfileParams.add(new BasicNameValuePair(TAG_USERID, userid));
			createProfileParams.add(new BasicNameValuePair(TAG_USERTYPE,
					userType));
			createProfileParams.add(new BasicNameValuePair(TAG_NICKNAME,
					userNickName));
			createProfileParams.add(new BasicNameValuePair(TAG_USERGENDER,
					userGender));
			createProfileParams.add(new BasicNameValuePair(TAG_USERAGE, Integer
					.toString(userAge)));
			createProfileParams.add(new BasicNameValuePair(TAG_USERDESCRIPTION,
					userDescription));
			// getting JSON string from URL
			JSONObject json = jsonParser.getJSONFromUrl(urlCreateProfile,
					createProfileParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				Editor editor = sharedpreferences.edit();
				editor.putString("TAG_SESSIONID", json.optString(TAG_SESSIONID));
				editor.putString("TAG_USERID", json.optString(TAG_USERID));
				editor.putString(TAG_USERTYPE, userType);
				editor.putString("TAG_NICKNAME", json.optString(TAG_NICKNAME));
				editor.putString("TAG_PROFILEID", json.optString(TAG_PROFILEID));
				editor.commit();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (success == 1) {
				// successfully created profile
				Intent intent = new Intent(getApplicationContext(),
						ViewPersonProfile.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
