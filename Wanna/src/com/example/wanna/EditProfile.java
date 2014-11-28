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
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends Activity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	private ProgressDialog pDialog;
	
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	private String urlUpdateProfile = UserFunctions.URL_ROOT + "DB_UpdateProfile.php";

	// user profile JSONArray
	JSONArray userProfileArray = null;
	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_MESSAGE = "message";

	EditText txtUserNickName;
	EditText txtUserDescription;

	String sessionID;
	String userID;
	String userType;
	String userNickName;
	String userDescription;
	int success;
	String message;

	JSONObject profileInformation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		Intent intent = getIntent();
		// getting user nick name and description from intent
		userNickName = intent.getStringExtra(TAG_NICKNAME);
		userDescription = intent.getStringExtra(TAG_DESCRIPTION);

		txtUserNickName = (EditText) findViewById(R.id.etName);
		txtUserDescription = (EditText) findViewById(R.id.etDescription);

		txtUserNickName.setText(userNickName);
		txtUserDescription.setText(userDescription);
	}

	public void onUpdateClick(View view) {
		userNickName = txtUserNickName.getText().toString();
		userDescription = txtUserDescription.getText().toString();
		new UploadNewInformationTask().execute(urlUpdateProfile);
	}

	public void onDeleteClick(View view) {
		userNickName = "";
		userDescription = "";
		new UploadNewInformationTask().execute(urlUpdateProfile);
	}

	public void onCancelClick(View view) {
		Intent i = new Intent(getApplicationContext(), PersonLoginSuccess.class);
		startActivity(i);
	}

	private class UploadNewInformationTask extends
			AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditProfile.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> updateProfileParams = new ArrayList<NameValuePair>();
			updateProfileParams.add(new BasicNameValuePair(TAG_SESSIONID, sessionID));
			updateProfileParams.add(new BasicNameValuePair(TAG_USERID, userID));
			updateProfileParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			updateProfileParams.add(new BasicNameValuePair(TAG_NICKNAME, userNickName));
			updateProfileParams.add(new BasicNameValuePair(TAG_DESCRIPTION, userDescription));
			JSONObject json = jsonParser.getJSONFromUrl(urlUpdateProfile, updateProfileParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				Editor editor = sharedpreferences.edit();
				editor.putString(TAG_NICKNAME, json.optString(TAG_NICKNAME));
				editor.commit();
				Intent intent = new Intent(getApplicationContext(),
						ViewPersonProfile.class);
				startActivity(intent);
			} else {
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();				
			}
		}
	}
}