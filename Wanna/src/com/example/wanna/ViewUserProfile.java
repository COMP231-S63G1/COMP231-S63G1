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
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewUserProfile extends Activity {

	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	private ProgressDialog pDialog;

	TextView tvProfileNickName;
	TextView tvProfileGender;
	TextView tvProfileAge;
	TextView tvProfileDescription;
	TextView tvStatus;
	String profileID;
	String sessionID;
	String userID;
	String userType;
	String nickName;
	String age;
	String gender;
	String description;

	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_PROFILEINFORMATION = "profileInformation";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_PROFILEID = "profileID";
	private static final String TAG_ProfileAge = "age";
	private static final String TAG_ProfileGender = "gender";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_NOTIFICATIONTYPE="notificationType";

	private String urlViewUserProfile = UserFunctions.URL_ROOT
			+ "DB_ViewUserProfile.php";
	private String urlSendFriendRequest = UserFunctions.URL_ROOT
			+ "DB_SendFriendRequest.php";
	JSONObject profileInformation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_user_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");

		Intent intent = getIntent();
		profileID = intent.getStringExtra(TAG_PROFILEID);

		tvProfileNickName = (TextView) findViewById(R.id.userNameValue);
		tvProfileGender = (TextView) findViewById(R.id.userGenderValue);
		tvProfileAge = (TextView) findViewById(R.id.userAgeValue);
		tvProfileDescription = (TextView) findViewById(R.id.userDescriptionValue);
		tvStatus = (TextView) findViewById(R.id.userStatusValue);
		new ViewUserProfileTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_user_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void friendRequestBtnOnclick(View view) {
		new SendFriendRequestTask().execute();
		new SendNotificationTask().execute();
	}
	public void backBtnOnclick(View view){
		Intent intent = new Intent(getApplicationContext(), PersonLoginSuccess.class);
		startActivity(intent);
	}

	private class ViewUserProfileTask extends AsyncTask<String, Void, String> {
		int success;
		String message;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewUserProfile.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> ViewProfileParams = new ArrayList<NameValuePair>();
			ViewProfileParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			ViewProfileParams.add(new BasicNameValuePair(TAG_USERID, userID));
			ViewProfileParams
					.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			ViewProfileParams.add(new BasicNameValuePair(TAG_PROFILEID,
					profileID));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlViewUserProfile,
					ViewProfileParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				// successfully received profile info
				JSONArray profileInformationArray = json
						.optJSONArray(TAG_PROFILEINFORMATION); // JSON Array
				// get first profile object from JSON Array
				profileInformation = profileInformationArray.optJSONObject(0);
				nickName = profileInformation.optString(TAG_NICKNAME);
				age = profileInformation.optString(TAG_ProfileAge);
				gender = profileInformation.optString(TAG_ProfileGender);
				description = profileInformation.optString(TAG_DESCRIPTION);
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			pDialog.dismiss();
			if (success == 1) {
				tvProfileNickName.setText(nickName);
				tvProfileGender.setText(gender);
				tvProfileAge.setText(age);
				tvProfileDescription.setText(description);
			}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	
	private class SendNotificationTask extends AsyncTask<String, Void, String> {
		int success;
		String message;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewUserProfile.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Sending Notification...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> SendNotificationParams = new ArrayList<NameValuePair>();
			SendNotificationParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			SendNotificationParams.add(new BasicNameValuePair(TAG_USERID, userID));
			SendNotificationParams
					.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			SendNotificationParams.add(new BasicNameValuePair(TAG_NOTIFICATIONTYPE,"friendRequest"));
			SendNotificationParams.add(new BasicNameValuePair(TAG_PROFILEID,
					profileID));
			
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlViewUserProfile,
					SendNotificationParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				// successfully received profile info
				JSONArray profileInformationArray = json
						.optJSONArray(TAG_PROFILEINFORMATION); // JSON Array
				// get first profile object from JSON Array
				profileInformation = profileInformationArray.optJSONObject(0);
				nickName = profileInformation.optString(TAG_NICKNAME);
				age = profileInformation.optString(TAG_ProfileAge);
				gender = profileInformation.optString(TAG_ProfileGender);
				description = profileInformation.optString(TAG_DESCRIPTION);
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			pDialog.dismiss();
			if (success == 1) {
				tvProfileNickName.setText(nickName);
				tvProfileGender.setText(gender);
				tvProfileAge.setText(age);
				tvProfileDescription.setText(description);
			}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	private class SendFriendRequestTask extends AsyncTask<String, Void, String> {
		int success;
		String message;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewUserProfile.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Sending Request ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> ViewProfileParams = new ArrayList<NameValuePair>();
			ViewProfileParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			ViewProfileParams.add(new BasicNameValuePair(TAG_USERID, userID));
			ViewProfileParams
					.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			ViewProfileParams.add(new BasicNameValuePair(TAG_PROFILEID,
					profileID));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlSendFriendRequest,
					ViewProfileParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
					.show();

		}
	}
}
