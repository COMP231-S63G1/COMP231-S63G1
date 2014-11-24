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

public class GetCreatedGroup extends Activity {
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();
	private ProgressDialog pDialog;

	// url to view event detail
	private String urlCreatedGroupList = userFunctions.URL_ROOT
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

	// products JSONArray
	JSONArray groupList = null;
	JSONArray createdGroupList;
	// JSON Node names
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
		createdGroupListView = (ListView) findViewById(android.R.id.list);
		new GetCreatedGroupTask().execute();
	}
	private class GetCreatedGroupTask extends AsyncTask<String, Void, String> {
		private String groupID;
		private String groupName;
		@Override
		protected String doInBackground(String... urls) {
			sessionID = sharedpreferences.getString("sessionID", "");
			userID = sharedpreferences.getString("userID", "");
			profileID = sharedpreferences.getString("profileID","");
			// Building Parameters
			List<NameValuePair> getCreatedGroupListParams = new ArrayList<NameValuePair>();
			getCreatedGroupListParams.add(new BasicNameValuePair("profileID",
					profileID));
			getCreatedGroupListParams.add(new BasicNameValuePair("sessionID",
					sessionID));
			getCreatedGroupListParams.add(new BasicNameValuePair("userID",
					userID));
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
