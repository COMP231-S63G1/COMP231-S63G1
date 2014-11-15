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
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SearchGroupResult extends ListActivity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();
	private ProgressDialog pDialog;

	private String urlSearchGroup = userFunctions.URL_ROOT
			+ "DB_SearchGroup.php";
	ListView lvGroupItem;

	ArrayList<String[]> groupItemsList = new ArrayList<String[]>();
	ListViewAdapter searchGroupAdapter;
	String groupID;
	String groupName;
	int success;
	String message;
	String searchType;
	String searchGroupName = null;
	String searchGroupCategory = null;

	// group JSONArray
	JSONArray groupList = null;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_GROUPLIST = "groupList";
	private static final String TAG_GROUPID = "groupID";
	private static final String TAG_GROUPNAME = "groupName";
	private static final String TAG_SEARCHGROUPTYPE = "searchType";
	private static final String TAG_SEARCHGROUPNAME = "searchGroupName";
	private static final String TAG_SEARCHGROUPCATEGORY = "searchGroupCategory";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_group_result);
		Intent intent = getIntent();
		searchType = intent.getStringExtra(TAG_SEARCHGROUPTYPE);
		if (searchType.equals( "Name")) {
			searchGroupName = intent.getStringExtra(TAG_SEARCHGROUPNAME);
		} else if (searchType.equals("Category")) {
			searchGroupCategory = intent
					.getStringExtra(TAG_SEARCHGROUPCATEGORY);
		}
		new SearchGroupTask().execute();
		lvGroupItem = (ListView) findViewById(android.R.id.list);
		searchGroupAdapter = new ListViewAdapter(groupItemsList, this);
		lvGroupItem.setAdapter(searchGroupAdapter);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		String groupID = (String) searchGroupAdapter.getItem(position);
		Intent intent = new Intent(getApplicationContext(),
				ViewAndJoinEvent.class);
		intent.putExtra("groupID", groupID);
		startActivity(intent);
	}

	private class SearchGroupTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> searchGroupParams = new ArrayList<NameValuePair>();
			searchGroupParams.add(new BasicNameValuePair("searchType",
					searchType));
			if (searchType.equals("Name")) {
				searchGroupParams.add(new BasicNameValuePair("searchGroupName",
						searchGroupName));
			} else if (searchType.equals("Category")) {
				searchGroupParams.add(new BasicNameValuePair(
						"searchGroupCategory", searchGroupCategory));
			}
			JSONObject json = jsonParser.getJSONFromUrl(urlSearchGroup,
					searchGroupParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				groupList = json.optJSONArray(TAG_GROUPLIST);
				// looping through All groups
				for (int i = 0; i < groupList.length(); i++) {
					JSONObject event = groupList.optJSONObject(i);
					groupID = event.optString(TAG_GROUPID);
					groupName = event.optString(TAG_GROUPNAME);
					String[] groupItems = { groupID, groupName };
					groupItemsList.add(groupItems);
				}
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
