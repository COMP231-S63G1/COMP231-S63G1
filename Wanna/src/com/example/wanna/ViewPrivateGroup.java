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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPrivateGroup extends Activity {
	
	// Creating JSON Parser object
		JSONParser jsonParser = new JSONParser();
		UserFunctions userFunctions = new UserFunctions();
		private ProgressDialog pDialog;
		
		ArrayList<HashMap<String, String>> groupDetailList;
		TextView tvGroupTitle;
		TextView tvGroupType;
		TextView tvGroupDescription;
		//Input a Group ID
		String groupID;

		// url to view event detail
		private String urlViewGroupDetail = userFunctions.URL_ROOT
				+ "DB_ViewGroup.php";
		private String urlJoinGroup = userFunctions.URL_ROOT
				+ "DB_JoinGroup.php";

		// JSON Node names
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_GroupDetail = "groupDetail";
		private static final String TAG_GroupID = "groupID";
		private static final String TAG_GroupType = "groupType";
		private static final String TAG_GroupName = "groupName";
		private static final String TAG_GroupDescription = "groupDescription";

		JSONObject groupDetail;
		//session variables 
		public static final String MyPREFERENCES = "Wanna";
		SharedPreferences sharedpreferences;
		String sessionID;
		String userID;
		String description;
		String name;
		String type;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_private_group);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);		

		Intent intent = getIntent();
		groupID=intent.getStringExtra(TAG_GroupID);
		// Edit Text
		tvGroupTitle = (TextView) findViewById(R.id.tvGroupNameValue);
		tvGroupType = (TextView) findViewById(R.id.tvGroupTypeValue);
		tvGroupDescription = (TextView) findViewById(R.id.tvGroupDescriptionValue);
		
		// Loading event in Background Thread
				new ViewGroupDetailTask().execute();		
	}
	
	private class ViewGroupDetailTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... urls) {
			sessionID = sharedpreferences.getString("sessionID", "");
			userID = sharedpreferences.getString("userID", "");
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> viewGroupDatailParams = new ArrayList<NameValuePair>();
						viewGroupDatailParams
						.add(new BasicNameValuePair("sessionID", sessionID));
						viewGroupDatailParams.add(new BasicNameValuePair("userID", userID));
						viewGroupDatailParams.add(new BasicNameValuePair("groupID",groupID));
						// getting event details by making HTTP request
						JSONObject json = jsonParser.getJSONFromUrl(
								urlViewGroupDetail, viewGroupDatailParams);
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received event details
							JSONArray groupDetailArray = json.optJSONArray(TAG_GroupDetail); // JSON Array
							// get first group object from JSON Array
							groupDetail = groupDetailArray.getJSONObject(0);
							// group with this goupID found
							description = groupDetail.optString(TAG_GroupDescription);
							System.out.println(description);
							name=groupDetail.optString(TAG_GroupName);
							System.out.println(name);
							type=groupDetail.optString(TAG_GroupType);
							System.out.println(type);
						}else{
						}

					} catch (JSONException e) {
						e.printStackTrace();
						return null;
					}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			tvGroupTitle.setText(name);
			tvGroupType.setText(type);
			tvGroupDescription.setText(description);			
		}
	}
	
	public void onCancel(View view){	
		Intent intent = new Intent(this, Login_Success.class);
		startActivity(intent);
		}
	public void onJoinGroupClick(View view){	
		Toast.makeText(getApplicationContext(),"Your Join Group Request Have Been Send!!",
				Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(getApplicationContext(),ViewProfile.class);
		startActivity(intent);
		}
	private class JoinGroupTask extends AsyncTask<String, Void, String> {
		
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(ViewAndJoinEvent.this);
//			pDialog.setTitle("Contacting Servers");
//			pDialog.setMessage("Loading ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
		
		@Override
		protected String doInBackground(String... urls) {
			sessionID = sharedpreferences.getString("sessionID", "");
			userID = sharedpreferences.getString("userID", "");
					int success;
					try {
						// Building Parameters
						List<NameValuePair> joinGroupDatailParams = new ArrayList<NameValuePair>();
						joinGroupDatailParams.add(new BasicNameValuePair("eventID", groupID));
						joinGroupDatailParams.add(new BasicNameValuePair("sessionID", sessionID));
						joinGroupDatailParams.add(new BasicNameValuePair("userID", userID));
						JSONObject json = jsonParser.getJSONFromUrl(
								urlJoinGroup, joinGroupDatailParams);
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
//							Toast.makeText(getApplicationContext(),
//				                    "Join event succeed", Toast.LENGTH_SHORT).show(); 
						}else{
//							Toast.makeText(getApplicationContext(),
//				                    "Join event fail", Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						e.printStackTrace();
						return null;
					}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
			startActivity(intent);
			
		}
	}
}
