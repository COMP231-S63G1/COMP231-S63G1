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
		private String urlViewGroupDetail = UserFunctions.URL_ROOT
				+ "DB_ViewGroup.php";


		// JSON Node names
		private static final String TAG_SESSIONID = "sessionid";
		private static final String TAG_USERID = "userid";
		private static final String TAG_USERTYPE = "userType";
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";
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
		String userType;
		String description;
		String name;
		String type;
		int success;
		String message;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_private_group);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);		
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");	

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
					// Building Parameters
					List<NameValuePair> viewGroupDatailParams = new ArrayList<NameValuePair>();
					viewGroupDatailParams.add(new BasicNameValuePair(TAG_SESSIONID,
							sessionID));
					viewGroupDatailParams.add(new BasicNameValuePair(TAG_USERID,
							userID));
					viewGroupDatailParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
					viewGroupDatailParams.add(new BasicNameValuePair("groupID",groupID));
					// getting event details by making HTTP request
					JSONObject json = jsonParser.getJSONFromUrl(
							urlViewGroupDetail, viewGroupDatailParams);
					// json success tag
					success = json.optInt(TAG_SUCCESS);
					message = json.optString(TAG_MESSAGE);
					if (success == 1) {
						// successfully received event details
						JSONArray groupDetailArray = json.optJSONArray(TAG_GroupDetail); // JSON Array
						// get first group object from JSON Array
						groupDetail = groupDetailArray.optJSONObject(0);
						// group with this goupID found
						description = groupDetail.optString(TAG_GroupDescription);
						System.out.println(description);
						name=groupDetail.optString(TAG_GroupName);
						System.out.println(name);
						type=groupDetail.optString(TAG_GroupType);
						System.out.println(type);
					}else{
					}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			if(success == 1){
			// display product data in EditText
			tvGroupTitle.setText(name);
			tvGroupType.setText(type);
			tvGroupDescription.setText(description);	
		}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public void onCancel(View view){	
		Intent intent = new Intent(this, PersonLoginSuccess.class);
		startActivity(intent);
		}
	
	public void onJoinGroupClick(View view){	
		Toast.makeText(getApplicationContext(),"Your Join Group Request Have Been Send!!",
				Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(getApplicationContext(),ViewPersonProfile.class);
		startActivity(intent);
		}

}
