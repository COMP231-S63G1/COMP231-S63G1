package com.wanna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.wanna.R;
import com.wanna.library.JSONParser;
import com.wanna.library.ListViewAdapter;
import com.wanna.library.UserFunctions;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class GetCreatedGroup extends ListActivity {
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	// url to view event detail
	private String urlCreatedGroupList = UserFunctions.URL_ROOT
			+ "DB_GetCreatedGroupList.php";
	ListView lvEventItem;
	ListView createdGroupListView;

	ArrayList<String[]> createdGroupItemsList = new ArrayList<String[]>();
	ListViewAdapter getCreatedGroupAdapter;
	int success;
	String message;
	String profileID;
	String sessionID;
	String userID;
	String userType;

	// products JSONArray
	JSONArray groupList = null;
	JSONArray createdGroupList;
	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_GROUPLIST = "groupList";
	private static final String TAG_GROUPID = "groupID";
	private static final String TAG_GROUPNAME = "groupName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_created_group);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		
		createdGroupListView = (ListView) findViewById(android.R.id.list);
		new GetCreatedGroupTask().execute();
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		String groupID = (String) getCreatedGroupAdapter.getItem(position);
		Intent intent = new Intent(getApplicationContext(),
				ViewCreatedGroup.class);
		intent.putExtra(TAG_GROUPID, groupID);
		startActivity(intent);
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
			} else {
				getCreatedGroupAdapter = new ListViewAdapter(
						createdGroupItemsList,GetCreatedGroup.this);
				createdGroupListView.setAdapter(getCreatedGroupAdapter);
			}
		}
	}
}
