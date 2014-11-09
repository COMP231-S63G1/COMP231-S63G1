package com.example.wanna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
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
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends Activity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	private String urlUploadProfile = userFunctions.URL_ROOT + "DB_UploadProfile.php";

	// user profile JSONArray
	JSONArray userProfileArray = null;
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NickName = "nickName";
	private static final String TAG_ProfileDescription = "description";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_MESSAGE = "message";

	EditText txtUserNickName;
	EditText txtUserDescription;

	String sessionID;
	String userID;
	String userNickName;
	String userDescription;
	int success;
	String message;

	JSONObject profileInformation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		Intent intent = getIntent();
		// getting user nick name and description from intent
		userNickName = intent.getStringExtra(TAG_NickName);
		userDescription = intent.getStringExtra(TAG_ProfileDescription);

		txtUserNickName = (EditText) findViewById(R.id.etName);
		txtUserDescription = (EditText) findViewById(R.id.etDescription);

		txtUserNickName.setText(userNickName);
		txtUserDescription.setText(userDescription);
	}

	public void onUpdateClick(View view) {
		userNickName = txtUserNickName.getText().toString();
		userDescription = txtUserDescription.getText().toString();
		new UploadNewInformationTask().execute(urlUploadProfile);
	}

	public void onDeleteClick(View view) {
		userNickName = "";
		userDescription = "";
		new UploadNewInformationTask().execute(urlUploadProfile);
	}

	public void onCancelClick(View view) {
		Intent i = new Intent(getApplicationContext(), Login_Success.class);
		startActivity(i);
	}

	private class UploadNewInformationTask extends
			AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			sessionID = sharedpreferences.getString("sessionID", "");
			userID = sharedpreferences.getString("userID", "");
			// Building Parameters
			List<NameValuePair> uploadProfileParams = new ArrayList<NameValuePair>();
			uploadProfileParams.add(new BasicNameValuePair("sessionID", sessionID));
			uploadProfileParams.add(new BasicNameValuePair("userID", userID));
			uploadProfileParams.add(new BasicNameValuePair("userNickName", userNickName));
			uploadProfileParams.add(new BasicNameValuePair("userDescription", userDescription));
			JSONObject json = jsonParser.getJSONFromUrl(urlUploadProfile, uploadProfileParams);
			success = json.optInt(TAG_SUCCESS);
			if (success == 1) {
				message = json.optString(TAG_MESSAGE);
				Editor editor = sharedpreferences.edit();
				editor.putString("nickName", json.optString(TAG_NICKNAME));
				editor.commit();
				Intent intent = new Intent(getApplicationContext(),
						ViewProfile.class);
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