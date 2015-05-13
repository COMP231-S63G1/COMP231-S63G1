package com.wanna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanna.R;
import com.wanna.library.ImageLoader;
import com.wanna.library.JSONParser;
import com.wanna.library.ListViewAdapter;
import com.wanna.library.UserFunctions;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCreatedGroup extends ListActivity {

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
	private String urlViewGroupDetail = UserFunctions.URL_ROOT
			+ "DB_ViewGroup.php";
	private String urlDisplayGroupMember = UserFunctions.URL_ROOT
			+ "DB_DisplayGroupMember.php";

	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_GroupDetail = "groupDetail";
	private static final String TAG_GroupID = "groupID";
	private static final String TAG_GroupType = "groupType";
	private static final String TAG_GroupName = "groupName";
	private static final String TAG_GroupDescription = "groupDescription";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_GroupList = "groupMemberList";
	private static final String TAG_MEMBERNAME = "groupMemberName";
	private static final String TAG_PERSON = "Person";
	private static final String TAG_ORGANIZATION = "Organization";
	private static final String TAG_PICTUREURL = "pictureURL";

	JSONObject groupDetail;

	// event JSONArray
	JSONArray displayGroupMemberList = null;

	// session variables
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	String sessionID;
	String userID;
	String userType;
	String description;
	String name;
	String type;
	String message;
	String memberName;
	String pictureURL;

	ImageView imvUserPicture;

	// group member list
	ArrayList<String[]> groupMemberList = new ArrayList<String[]>();
	ListViewAdapter groupMemberAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_created_group);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");

		Intent intent = getIntent();
		groupID = intent.getStringExtra(TAG_GroupID);
		// Edit Text
		imvUserPicture = (ImageView) findViewById(R.id.userPicture);
		tvGroupTitle = (TextView) findViewById(R.id.tvGroupNameValue);
		tvGroupType = (TextView) findViewById(R.id.tvGroupTypeValue);
		tvGroupDescription = (TextView) findViewById(R.id.tvGroupDescriptionValue);

		// Loading event in Background Thread
		new ViewGroupDetailTask().execute();
		// Display group menbers
		lvGroupMember = (ListView) findViewById(android.R.id.list);
		groupMemberAdapter = new ListViewAdapter(groupMemberList, this);
		new DisplayGroupMemberTask().execute();
	}

	private class ViewGroupDetailTask extends AsyncTask<String, Void, String> {

		int success;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewCreatedGroup.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			// Check for success tag
			// Building Parameters
			List<NameValuePair> viewGroupDatailParams = new ArrayList<NameValuePair>();
			viewGroupDatailParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			viewGroupDatailParams
					.add(new BasicNameValuePair(TAG_USERID, userID));
			viewGroupDatailParams.add(new BasicNameValuePair(TAG_USERTYPE,
					userType));
			viewGroupDatailParams
					.add(new BasicNameValuePair("groupID", groupID));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlViewGroupDetail,
					viewGroupDatailParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				// successfully received event details
				JSONArray groupDetailArray = json.optJSONArray(TAG_GroupDetail); // JSON
																					// Array
				// get first group object from JSON Array
				groupDetail = groupDetailArray.optJSONObject(0);
				// group with this goupID found
				description = groupDetail.optString(TAG_GroupDescription);
				name = groupDetail.optString(TAG_GroupName);
				type = groupDetail.optString(TAG_GroupType);
				pictureURL = "http://wanna.developerdarren.com"
						+ groupDetail.optString(TAG_PICTUREURL);
				// evnet with this eventID found
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			pDialog.dismiss();
			if (success == 1) {
				// display product data in EditText
				tvGroupTitle.setText(groupDetail.optString(TAG_GroupName));
				tvGroupType.setText(groupDetail.optString(TAG_GroupType));
				tvGroupDescription.setText(groupDetail.optString(TAG_GroupDescription));
				// Loader image - will be shown before loading image
				int loader = R.drawable.loader;
				ImageLoader imgLoader = new ImageLoader(getApplicationContext());

				// whenever you want to load an image from url
				// call DisplayImage function
				// url - image url to load
				// loader - loader image, will be displayed before getting image
				// image - ImageView
				imgLoader.DisplayImage(pictureURL, loader, imvUserPicture);
			} else {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class DisplayGroupMemberTask extends
			AsyncTask<String, Void, String> {

		int success;

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> groupMemberParams = new ArrayList<NameValuePair>();
			groupMemberParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			groupMemberParams.add(new BasicNameValuePair(TAG_USERID, userID));
			groupMemberParams
					.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			groupMemberParams.add(new BasicNameValuePair("groupID", groupID));
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
					JSONObject groupMember = displayGroupMemberList
							.optJSONObject(i);
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
			if (success == 1) {
				lvGroupMember.setAdapter(groupMemberAdapter);
			}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void onCancel(View view) {
		if (userType.equals(TAG_PERSON)) {
			Intent intent = new Intent(getApplicationContext(),
					PersonLoginSuccess.class);
			startActivity(intent);
		} else if (userType.equals(TAG_ORGANIZATION)) {
			Intent intent = new Intent(getApplicationContext(),
					OrganizationLoginSuccess.class);
			startActivity(intent);
		}
	}

	public void onEditGroupDetailButtonClick(View view) {
		Intent intent = new Intent(getApplicationContext(), EditGroup.class);
		intent.putExtra("groupID", groupDetail.optString("groupID"));
		intent.putExtra("groupName", tvGroupTitle.getText().toString());
		intent.putExtra("groupType", tvGroupType.getText().toString());
		intent.putExtra("groupDescription", tvGroupDescription.getText()
				.toString());
		intent.putExtra(TAG_PICTUREURL, pictureURL);
		startActivity(intent);
	}
}