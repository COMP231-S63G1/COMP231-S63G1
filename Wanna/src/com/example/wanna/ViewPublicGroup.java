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
import com.example.wanna.library.ListViewAdapter;
import com.example.wanna.library.UserFunctions;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPublicGroup extends ListActivity {

	// Creating JSON Parser object
			JSONParser jsonParser = new JSONParser();
			UserFunctions userFunctions = new UserFunctions();
			private ProgressDialog pDialog;
			
			ArrayList<HashMap<String, String>> groupDetailList;
			TextView tvGroupTitle;
			TextView tvGroupType;
			TextView tvGroupDescription;
			ListView lvGroupMember;
			
			String groupID;

			// url to view event detail
			private String urlViewGroupDetail = userFunctions.URL_ROOT
					+ "DB_ViewGroup.php";
			private String urlDisplayGroupMember = userFunctions.URL_ROOT
					+ "DB_DisplayGroupMember.php";

			// JSON Node names
			private static final String TAG_SUCCESS = "success";
			private static final String TAG_GroupDetail = "groupDetail";
			private static final String TAG_GroupID = "groupID";
			private static final String TAG_GroupType = "groupType";
			private static final String TAG_GroupName = "groupName";
			private static final String TAG_GroupDescription = "groupDescription";
			private static final String TAG_MESSAGE = "message";
			private static final String TAG_GroupList = "groupMemberList";
			private static final String TAG_MEMBERNAME = "groupMemberName";

			JSONObject groupDetail;
			
			// event JSONArray
			JSONArray displayGroupMemberList = null;
			
			//session variables 
			public static final String MyPREFERENCES = "Wanna";
			SharedPreferences sharedpreferences;			
			String sessionID;
			String userID;
			String description;
			String name;
			String type;
			int success;
			String message;
			String memberName;
			
			//group member list
			ArrayList<String[]> groupMemberList = new ArrayList<String[]>();
			ListViewAdapter groupMemberAdapter;
			
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_view_public_group);
			sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
			// Edit Text
			tvGroupTitle = (TextView) findViewById(R.id.tvGroupNameValue);
			tvGroupType = (TextView) findViewById(R.id.tvGroupTypeValue);
			tvGroupDescription = (TextView) findViewById(R.id.tvGroupDescriptionValue);
			
			// Loading event in Background Thread
					new ViewGroupDetailTask().execute();
			//Display group menbers
					lvGroupMember = (ListView) findViewById(android.R.id.list);
					groupMemberAdapter = new ListViewAdapter(groupMemberList, this);
					new DisplayGroupMemberTask().execute();
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
								name=groupDetail.optString(TAG_GroupName);
								type=groupDetail.optString(TAG_GroupType);
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
		
		private class DisplayGroupMemberTask extends AsyncTask<String, Void, String> {
//			@Override
//			protected void onPreExecute() {
//				super.onPreExecute();
//				pDialog = new ProgressDialog(SearchEventResult.this);
//				pDialog.setTitle("Contacting Servers");
//				pDialog.setMessage("Loading ...");
//				pDialog.setIndeterminate(false);
//				pDialog.setCancelable(true);
//				pDialog.show();
//			}

			@Override
			protected String doInBackground(String... urls) {
				// Building Parameters
				List<NameValuePair> groupMemberParams = new ArrayList<NameValuePair>();
				groupMemberParams.add(new BasicNameValuePair("groupID",
						groupID));
				// getting event details by making HTTP request
				JSONObject json = jsonParser.getJSONFromUrl(urlDisplayGroupMember,
						groupMemberParams);
				// json success tag
				success = json.optInt(TAG_SUCCESS);
				message = json.optString(TAG_MESSAGE);
				if (success == 1) {
					displayGroupMemberList = json.optJSONArray(TAG_GroupList);
					// looping through All Products
					for (int i = 0; i < displayGroupMemberList.length(); i++) {
						JSONObject groupMember = displayGroupMemberList.optJSONObject(i);
						String profileID = groupMember.optString("profileID");
						memberName = groupMember.optString(TAG_MEMBERNAME);
						String[] eventItems = { profileID, memberName };
						groupMemberList.add(eventItems);
					}
				} else {
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				if(success == 1){
					lvGroupMember.setAdapter(groupMemberAdapter);
				}
				if (success != 1) {
					Toast.makeText(getApplicationContext(), message,
							Toast.LENGTH_SHORT).show();
				}
			}
		}
		
		public void onCancel(View view){	
			Intent intent = new Intent(this, Login_Success.class);
			startActivity(intent);
			}
		public void onJoinGrup(View view){
			
		}
}
