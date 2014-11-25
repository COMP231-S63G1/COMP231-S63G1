package com.example.wanna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.ListViewAdapter;
import com.example.wanna.library.UserFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GetJoinedGroup extends Activity {

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();
	private ProgressDialog pDialog;

	// url to view event detail
	private String urlJoinedGroupList = userFunctions.URL_ROOT
			+ "DB_GetJoinedGroupList.php";
	
	ListView joinedGroupListView;

	ArrayList<String[]> joinedGroupItemsList = new ArrayList<String[]>();
	ListViewAdapter getJoinedGroupAdapter;
	int success;
	String message;
	String profileID;
	String sessionID;
	String userID;

	// products JSONArray
	JSONArray groupList = null;
	JSONArray joinedGroupList;
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_GROUPLIST = "groupList";
	private static final String TAG_GROUPID = "groupID";
	private static final String TAG_GROUPNAME = "groupName";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_joined_group);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		joinedGroupListView = (ListView) findViewById(android.R.id.list);
		new GetJoinedGroupTask().execute();
	}
	private class GetJoinedGroupTask extends AsyncTask<String, Void, String> {
		private String groupID;
		private String groupName;
		@Override
		protected String doInBackground(String... urls) {
			sessionID = sharedpreferences.getString("sessionID", "");
			userID = sharedpreferences.getString("userID", "");
			profileID = sharedpreferences.getString("profileID","");
			// Building Parameters
			List<NameValuePair> getJoinedGroupListParams = new ArrayList<NameValuePair>();
			getJoinedGroupListParams.add(new BasicNameValuePair("profileID",
					profileID));
			getJoinedGroupListParams.add(new BasicNameValuePair("sessionID",
					sessionID));
			getJoinedGroupListParams.add(new BasicNameValuePair("userID",
					userID));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlJoinedGroupList,
					getJoinedGroupListParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				joinedGroupList = json.optJSONArray(TAG_GROUPLIST);
				// looping through All Products
				for (int i = 0; i < joinedGroupList.length(); i++) {
					JSONObject event = joinedGroupList.optJSONObject(i);
					groupID = event.optString(TAG_GROUPID);
					groupName = event.optString(TAG_GROUPNAME);
					String[] groupItems = { groupID, groupName };
					joinedGroupItemsList.add(groupItems);
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
				getJoinedGroupAdapter = new ListViewAdapter(
						joinedGroupItemsList,GetJoinedGroup.this);
				joinedGroupListView.setAdapter(getJoinedGroupAdapter);
			}
		}
	}
}