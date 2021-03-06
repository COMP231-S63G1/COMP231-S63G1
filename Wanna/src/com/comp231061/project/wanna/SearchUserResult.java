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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SearchUserResult extends ListActivity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	private String urlSearchUser = UserFunctions.URL_ROOT + "DB_SearchUser.php";
	ListView lvUserItem;

	ArrayList<String[]> userItemsList = new ArrayList<String[]>();
	ListViewAdapter searchUserAdapter;
	String userID;
	String email;
	int success;
	String message;
	String searchType;
	String searchName;
	String searchStatus;
	String searchMinAge;
	String searchMaxAge;
	String searchGender;
	String searchRange;
	String latitude;
    String longitude;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_USERLIST = "userList";
	private static final String TAG_USERID = "userID";
	private static final String TAG_PROFILEEMAIL = "email";
	private static final String TAG_SEARCHTYPE = "searchType";
	private static final String TAG_SEARCHTYPEEMAIL = "Email";
	private static final String TAG_SEARCHTYPEFILTRATION = "Filtration";
	private static final String TAG_SEARCHEMAIL = "searchEmail";
	private static final String TAG_SEARCHSTATUS = "searchStatus";
	private static final String TAG_SEARCHGENDER = "searchGender";
	private static final String TAG_SEARCHMAXAGE = "searchMaxAge";
	private static final String TAG_SEARCHMINAGE = "searchMinAge";
	private static final String TAG_SEARCHRANGE = "searchRange";
	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_LONGITUDE = "longitude";
	// group JSONArray
	JSONArray userList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_user_result);
		Intent intent = getIntent();
		searchType = intent.getStringExtra(TAG_SEARCHTYPE);
		if (searchType.equals( TAG_SEARCHTYPEEMAIL)) {
			searchName = intent.getStringExtra(TAG_SEARCHEMAIL);
		} else if (searchType.equals(TAG_SEARCHTYPEFILTRATION)) {
			searchStatus = intent.getStringExtra(TAG_SEARCHSTATUS);
			searchMaxAge = String.valueOf(intent.getIntExtra(TAG_SEARCHMAXAGE, 0));
			searchMinAge = String.valueOf(intent.getIntExtra(TAG_SEARCHMINAGE, 0));
			searchGender = intent.getStringExtra(TAG_SEARCHGENDER);
			searchRange = intent.getStringExtra(TAG_SEARCHRANGE);
			latitude = Double.toString(intent.getDoubleExtra(TAG_LATITUDE, 0));
		    longitude = Double.toString(intent.getDoubleExtra(TAG_LONGITUDE, 0));

		}
		new SearchUserTask().execute();
		lvUserItem = (ListView) findViewById(android.R.id.list);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		String userID = (String)searchUserAdapter.getItem(position);
		Intent intent = new Intent(getApplicationContext(),
				ViewUserProfile.class);
		intent.putExtra(TAG_USERID, userID);
		startActivity(intent);
	}
	private class SearchUserTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> searchUserParams = new ArrayList<NameValuePair>();
			searchUserParams.add(new BasicNameValuePair(TAG_SEARCHTYPE,
					searchType));
			if (searchType.equals(TAG_SEARCHTYPEEMAIL)) {
				searchUserParams.add(new BasicNameValuePair(TAG_SEARCHEMAIL,
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
				searchUserParams.add(new BasicNameValuePair(TAG_SEARCHRANGE,
						searchRange));		
				searchUserParams.add(new BasicNameValuePair(TAG_LATITUDE,
						latitude));		
				searchUserParams.add(new BasicNameValuePair(TAG_LONGITUDE,
						longitude));		
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
					userID = event.optString(TAG_USERID);
					email = event.optString(TAG_PROFILEEMAIL);
					String[] userItems = { userID, email };
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
			}else{
				searchUserAdapter = new ListViewAdapter(userItemsList, SearchUserResult.this);
				lvUserItem.setAdapter(searchUserAdapter);
			}
		}
	}
}
