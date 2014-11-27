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

	private String urlSearchGroup = UserFunctions.URL_ROOT
			+ "DB_SearchGroup.php";
	ListView lvGroupItem;

	ArrayList<String[]> groupItemsList = new ArrayList<String[]>();
	ListViewAdapter searchGroupAdapter;
	String groupID;
	String groupName;
	String groupPrivacy;
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
	private static final String TAG_GROUPPRIVACY = "groupPrivacy";
	private static final String TAG_SEARCHGROUPTYPE = "searchType";
	private static final String TAG_SEARCHGROUPTYPENAME = "Name";
	private static final String TAG_SEARCHGROUPTYPECATEGORY = "Category";
	private static final String TAG_SEARCHGROUPNAME = "searchGroupName";
	private static final String TAG_SEARCHGROUPCATEGORY = "searchGroupCategory";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_group_result);
		Intent intent = getIntent();
		searchType = intent.getStringExtra(TAG_SEARCHGROUPTYPE);
		if (searchType.equals(TAG_SEARCHGROUPTYPENAME)) {
			searchGroupName = intent.getStringExtra(TAG_SEARCHGROUPNAME);
		} else if (searchType.equals(TAG_SEARCHGROUPTYPECATEGORY)) {
			searchGroupCategory = intent
					.getStringExtra(TAG_SEARCHGROUPCATEGORY);
		}
		new SearchGroupTask().execute();
		lvGroupItem = (ListView) findViewById(android.R.id.list);
		searchGroupAdapter = new ListViewAdapter(groupItemsList, this);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		groupID = (String) searchGroupAdapter.getItem(position);
		groupPrivacy = (String) searchGroupAdapter.getItemPrivacy(position);
		if (groupPrivacy.equals("Public")) {
			Intent intent = new Intent(getApplicationContext(),
					ViewPublicGroup.class);
			intent.putExtra(TAG_GROUPID, groupID);
			startActivity(intent);
		}else if(groupPrivacy.equals("Private")){
			Intent intent = new Intent(getApplicationContext(),
					ViewPrivateGroup.class);
			intent.putExtra(TAG_GROUPID, groupID);
			startActivity(intent);
		}
	}
	
	private class SearchGroupTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> searchGroupParams = new ArrayList<NameValuePair>();
			searchGroupParams.add(new BasicNameValuePair(TAG_SEARCHGROUPTYPE,
					searchType));
			if (searchType.equals(TAG_SEARCHGROUPTYPENAME)) {
				searchGroupParams.add(new BasicNameValuePair(TAG_SEARCHGROUPNAME,
						searchGroupName));
			} else if (searchType.equals(TAG_SEARCHGROUPTYPECATEGORY)) {
				searchGroupParams.add(new BasicNameValuePair(
						TAG_SEARCHGROUPCATEGORY, searchGroupCategory));
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
					groupPrivacy = event.optString(TAG_GROUPPRIVACY);
					String[] groupItems = { groupID, groupName, groupPrivacy};
					groupItemsList.add(groupItems);
				}
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(success == 1){
				lvGroupItem.setAdapter(searchGroupAdapter);
			}
			else if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
