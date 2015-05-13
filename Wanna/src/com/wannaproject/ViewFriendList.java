package com.wannaproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.wannaproject.library.JSONParser;
import com.wannaproject.library.ListViewAdapter;
import com.wannaproject.library.UserFunctions;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewFriendList extends ListActivity {

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	// url to view event detail
	private String urlFriendList = UserFunctions.URL_ROOT
			+ "DB_GetFriendList.php";
	ListView friendListView;

	ArrayList<String[]> friendList = new ArrayList<String[]>();
	int success;
	String message;
	String sessionID;
	String userID;
	String userType;
	String profileID;
	String nickName;
	String friendID;

	JSONArray friendListJsonArray;
	private ListAdapter getFriendListAdapter;
	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_FRIENDLIST = "friendList";
	private static final String TAG_PROFILEID = "profileID";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_FRIENDID = "friendID";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_friend_list);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");

		friendListView = (ListView) findViewById(android.R.id.list);
		new GetFriendListTask().execute();

	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		String friendID = (String) getFriendListAdapter.getItem(position);
		Intent intent = new Intent(getApplicationContext(),
				ViewUserProfile.class);
		intent.putExtra("userID", friendID);
		startActivity(intent);
	}

	private class GetFriendListTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> getFriendListParams = new ArrayList<NameValuePair>();
			getFriendListParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			getFriendListParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			getFriendListParams.add(new BasicNameValuePair(TAG_USERTYPE,
					userType));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlFriendList,
					getFriendListParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				friendListJsonArray = json.optJSONArray(TAG_FRIENDLIST);
				// looping through All Products
				for (int i = 0; i < friendListJsonArray.length(); i++) {
					JSONObject friend = friendListJsonArray.optJSONObject(i);
				    friendID = friend.optString(TAG_FRIENDID);
					nickName = friend.optString(TAG_NICKNAME);
					String[] eventItems = { friendID, nickName };
					friendList.add(eventItems);
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
				getFriendListAdapter = new ListViewAdapter(
						friendList, ViewFriendList.this);
				friendListView.setAdapter(getFriendListAdapter);
			}
		}
	}
}
