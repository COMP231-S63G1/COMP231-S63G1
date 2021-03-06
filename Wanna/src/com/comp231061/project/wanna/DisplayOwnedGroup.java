package com.comp231061.project.wanna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.comp231061.project.wanna.library.JSONParser;
import com.comp231061.project.wanna.library.ListViewAdapter;
import com.comp231061.project.wanna.library.UserFunctions;
import com.comp231061.project.wanna.R;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayOwnedGroup extends ListActivity {
	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	private ProgressDialog pDialog;

	private String urlDisplayOwnedGroups = UserFunctions.URL_ROOT
			+ "DB_DisplayOwnedGroups.php";
	ListView lvOwnedGroup;

	ArrayList<String[]> ownedGroupList = new ArrayList<String[]>();
	ListViewAdapter ownedGroupAdapter;
	String sessionID;
	String userID;
	String profileID;
	String groupID;
	String groupName;
	int success;
	String message;

	// group JSONArray
	JSONArray groupList = null;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_GROUPLIST = "groupList";
	private static final String TAG_USERID = "userID";
	private static final String TAG_GROUPID = "groupID";
	private static final String TAG_GROUPNAME = "groupName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_owned_group);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		lvOwnedGroup = (ListView) findViewById(android.R.id.list);
		ownedGroupAdapter = new ListViewAdapter(ownedGroupList, this);
		new DisplayOwnedGroupTask().execute();

	}

	private class DisplayOwnedGroupTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			sessionID = sharedpreferences.getString("sessionID", "");
			userID = sharedpreferences.getString("userID", "");
			profileID = sharedpreferences.getString("profileID", "");

			// Building Parameters
			List<NameValuePair> ownedGroupParams = new ArrayList<NameValuePair>();
			ownedGroupParams
					.add(new BasicNameValuePair("sessionID", sessionID));
			ownedGroupParams.add(new BasicNameValuePair("userID", userID));
			ownedGroupParams
					.add(new BasicNameValuePair("profileID", profileID));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlDisplayOwnedGroups,
					ownedGroupParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				groupList = json.optJSONArray(TAG_GROUPLIST);
				// looping through All Products
				for (int i = 0; i < groupList.length(); i++) {
					JSONObject ownedGroup = groupList.optJSONObject(i);
					groupID = ownedGroup.optString(TAG_GROUPID);
					groupName = ownedGroup.optString(TAG_GROUPNAME);
					String[] ownedGroupInfo = { groupID, groupName };
					ownedGroupList.add(ownedGroupInfo);
				}
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (success == 1) {
				lvOwnedGroup.setAdapter(ownedGroupAdapter);
			}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		groupID = (String) ownedGroupAdapter.getItem(position);
		Intent intent = new Intent(getApplicationContext(),
				ViewCreatedGroup.class);
		intent.putExtra("groupID", groupID);
		startActivity(intent);
	}
}
