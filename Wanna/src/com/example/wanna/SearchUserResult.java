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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class SearchUserResult extends Activity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	private String urlSearchUser = userFunctions.URL_ROOT + "DB_SearchUser.php";
	ListView lvUserItem;

	ArrayList<String[]> userItemsList = new ArrayList<String[]>();
	ListViewAdapter searchUserAdapter;
	String profileID;
	String profileName;
	int success;
	String message;
	String searchType;
	String searchName;
	String searchStatus;
	String searchMinAge;
	String searchMaxAge;
	String searchGender;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_USERLIST = "userList";
	private static final String TAG_PROFILEID = "profileID";
	private static final String TAG_PROFILENAME = "profileName";
	private static final String TAG_SEARCHTYPE = "searchType";
	private static final String TAG_SEARCHTYPENAME = "Name";
	private static final String TAG_SEARCHTYPEFILTRATION = "Filtration";
	private static final String TAG_SEARCHNAME = "searchName";
	private static final String TAG_SEARCHSTATUS = "searchStatus";
	private static final String TAG_SEARCHGENDER = "searchGender";
	private static final String TAG_SEARCHMAXAGE = "searchMaxAge";
	private static final String TAG_SEARCHMINAGE = "searchMinAge";
	// group JSONArray
	JSONArray userList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_user_result);
		Intent intent = getIntent();
		searchType = intent.getStringExtra(TAG_SEARCHTYPE);
		if (searchType.equals( TAG_SEARCHTYPENAME)) {
			searchName = intent.getStringExtra(TAG_SEARCHNAME);
		} else if (searchType.equals(TAG_SEARCHTYPEFILTRATION)) {
			searchStatus = intent.getStringExtra(TAG_SEARCHSTATUS);
			searchMaxAge = intent.getStringExtra(TAG_SEARCHMAXAGE);
			searchMinAge = intent.getStringExtra(TAG_SEARCHMINAGE);
			searchGender = intent.getStringExtra(TAG_SEARCHGENDER);
		}
		new SearchUserTask().execute();
		lvUserItem = (ListView) findViewById(android.R.id.list);
		searchUserAdapter = new ListViewAdapter(userItemsList, this);
		lvUserItem.setAdapter(searchUserAdapter);
	}

	private class SearchUserTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> searchUserParams = new ArrayList<NameValuePair>();
			searchUserParams.add(new BasicNameValuePair(TAG_SEARCHTYPE,
					searchType));
			if (searchType.equals(TAG_SEARCHTYPENAME)) {
				searchUserParams.add(new BasicNameValuePair(TAG_SEARCHNAME,
						searchName));
			} else if (searchType.equals(TAG_SEARCHTYPEFILTRATION)) {
				searchUserParams.add(new BasicNameValuePair(TAG_SEARCHSTATUS,
						searchStatus));		
				searchUserParams.add(new BasicNameValuePair(TAG_SEARCHMINAGE,
						searchMinAge));	
				searchUserParams.add(new BasicNameValuePair(TAG_SEARCHMAXAGE,
						searchMaxAge));		
				searchUserParams.add(new BasicNameValuePair(TAG_SEARCHGENDER,
						searchGender));		
			}			
			JSONObject json = jsonParser.getJSONFromUrl(urlSearchUser,
					searchUserParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				userList = json.optJSONArray(TAG_USERLIST);
				// looping through All groups
				for (int i = 0; i < userList.length(); i++) {
					JSONObject event = userList.optJSONObject(i);
					profileID = event.optString(TAG_PROFILEID);
					profileName = event.optString(TAG_PROFILENAME);
					String[] userItems = { profileID, profileName };
					userItemsList.add(userItems);
				}
			}else {
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
