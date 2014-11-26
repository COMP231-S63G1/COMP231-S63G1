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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewOrganizationProfile extends Activity {
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	private ProgressDialog pDialog;

	ArrayList<HashMap<String, String>> viewProfileInformationList;
	ArrayList<String[]> createdEventItemsList = new ArrayList<String[]>();
	ArrayList<String[]> createdGroupItemsList = new ArrayList<String[]>();
	TextView tvProfileNickName;
	TextView tvProfileDescription;
	TextView tvStatus;
	ListView createdEventListView;
	TextView tvGetCreatedEventTextView;
	TextView tvGetCreatedGroupTextView;
	String profileID;
	String sessionID;
	String userID;
	String userType;
	String nickName;
	String description;
	String message;
	int success;

	// url to view profile info
	private String urlViewProfileInformation = UserFunctions.URL_ROOT
			+ "DB_ViewOrganizationProfile.php";
	// url to get user created event
	private String urlCreatedEventList = UserFunctions.URL_ROOT
			+ "DB_GetCreatedEventList.php";
	private String urlCreatedGroupList = UserFunctions.URL_ROOT
			+ "DB_GetCreatedGroupList.php";
	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PROFILEINFORMATION = "profileInformation";
	private static final String TAG_PROFILENICKNAME = "nickName";
	private static final String TAG_PROFILEID = "profileID";
	private static final String TAG_PROFILEDESCRIPTION = "description";
	private static final String TAG_STATUS = "eventName";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_EVENTLIST = "eventList";
	private static final String TAG_GROUPLIST = "groupList";
	private static final String TAG_EVENTID = "eventID";
	private static final String TAG_EVENTNAME = "eventName";
	private static final String TAG_GROUPID = "groupID";
	private static final String TAG_GROUPNAME = "groupName";
	JSONObject profileInformation;
	JSONArray createdEventList = null;
	JSONArray createdGroupList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_organization_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		nickName = sharedpreferences.getString(TAG_PROFILENICKNAME, "");
		
		// Edit Text
		tvProfileNickName = (TextView) findViewById(R.id.tvProfileNickNameValue);
		tvProfileDescription = (TextView) findViewById(R.id.tvProfileDescriptionValue);
		tvGetCreatedEventTextView =(TextView) findViewById(R.id.tvProfileGetCreateEventValue);
		tvGetCreatedGroupTextView =(TextView) findViewById(R.id.tvProfileGetCreateGroupValue);
		new ViewProfileInformationTask().execute();
		new GetCreatedEventTask().execute();
		new GetCreatedGroupTask().execute();
	}
	public void onViewProfileInformationBackClick(View view) {
		Intent intent = new Intent(getApplicationContext(), OrganizationLoginSuccess.class);
		startActivity(intent);
	}

	public void onEditProfileClick(View view) {
		Intent intent = new Intent(getApplicationContext(), EditProfile.class);
		intent.putExtra(TAG_PROFILENICKNAME, nickName);
		intent.putExtra(TAG_PROFILEDESCRIPTION, description);
		startActivity(intent);
	}

	private class ViewProfileInformationTask extends
			AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewOrganizationProfile.this);
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
			ViewProfileParams
					.add(new BasicNameValuePair(TAG_SESSIONID, sessionID));
			ViewProfileParams.add(new BasicNameValuePair(TAG_USERID, userID));
			ViewProfileParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(
					urlViewProfileInformation, ViewProfileParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			if (success == 1) {
				// successfully received profile info
				JSONArray profileInformationArray = json
						.optJSONArray(TAG_PROFILEINFORMATION); // JSON Array
				// get first profile object from JSON Array
				profileInformation = profileInformationArray.optJSONObject(0);
				nickName = profileInformation.optString(TAG_PROFILENICKNAME);
				description = profileInformation
						.optString(TAG_PROFILEDESCRIPTION);
				// status = profileInformation.optString(TAG_Status);
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			pDialog.dismiss();
			tvProfileNickName.setText(nickName);
			tvProfileDescription.setText(description);
			// tvStatus.setText(status);

		}
	}

	private class GetCreatedEventTask extends AsyncTask<String, Void, String> {
		private String eventID;
		private String eventName;

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> getCreatedEventListParams = new ArrayList<NameValuePair>();
			getCreatedEventListParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			getCreatedEventListParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			getCreatedEventListParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlCreatedEventList,
					getCreatedEventListParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				createdEventList = json.optJSONArray(TAG_EVENTLIST);
				// looping through All Products
				for (int i = 0; i < createdEventList.length(); i++) {
					JSONObject event = createdEventList.optJSONObject(i);
					eventID = event.optString(TAG_EVENTID);
					eventName = event.optString(TAG_EVENTNAME);
					String[] eventItems = { eventID, eventName };
					createdEventItemsList.add(eventItems);
				}
			} else {
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (success == 0) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
			else{
				String textShowed="";
				for(String[] arr : createdEventItemsList){
					textShowed+=arr[1]+"\n";
					System.out.println(textShowed);
				}
				tvGetCreatedEventTextView.setText(textShowed);
				}
		}
	}
	
	private class GetCreatedGroupTask extends AsyncTask<String, Void, String> {
		private String groupID;
		private String groupName;
		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> getCreatedGroupListParams = new ArrayList<NameValuePair>();
			getCreatedGroupListParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			getCreatedGroupListParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			getCreatedGroupListParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlCreatedGroupList,
					getCreatedGroupListParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				createdGroupList = json.optJSONArray(TAG_GROUPLIST);
				// looping through All Products
				for (int i = 0; i < createdGroupList.length(); i++) {
					JSONObject event = createdGroupList.optJSONObject(i);
					groupID = event.optString(TAG_GROUPID);
					groupName = event.optString(TAG_GROUPNAME);
					String[] groupItems = { groupID, groupName };
					createdGroupItemsList.add(groupItems);
				}
			} else {
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (success == 0) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
			else{

				String textShowed="";
				for(String[] arr : createdGroupItemsList){
					textShowed+=arr[1]+"\n";
					System.out.println(textShowed);
				}
				tvGetCreatedGroupTextView.setText(textShowed);
				}
		}
	}
	
	public void GetCreateEventValueOnClick(View view){
		Intent intent= new Intent(getApplicationContext(),GetCreatedEvent.class);
		startActivity(intent);		
	}
	
	public void GetCreateGroupValueOnClick(View view){
		Intent intent= new Intent(getApplicationContext(),GetCreatedGroup.class);
		startActivity(intent);		
	}
}
