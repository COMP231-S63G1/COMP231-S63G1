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

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GetJoinedEvent extends ListActivity {

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	// url to view event detail
	private String urlJoinedEventList = UserFunctions.URL_ROOT
			+ "DB_GetJoinedEventList.php";
	ListView lvEventItem;
	ListView joinedEventListView;

	ArrayList<String[]> joinedEventItemsList = new ArrayList<String[]>();
	ListViewAdapter searchEventAdapter;
	String eventID;
	String eventName;
	int success;
	String message;
	String searchEventName;
	String profileID;
	String sessionID;
	String userID;
	String userType;

	// products JSONArray
	JSONArray eventList = null;
	JSONArray joinedEventList;
	private ListAdapter getJoinedEventAdapter;
	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_EVENTLIST = "eventList";
	private static final String TAG_EVENTID = "eventID";
	private static final String TAG_EVENTNAME = "eventName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_joined_event);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		
		joinedEventListView = (ListView) findViewById(android.R.id.list);
		new GetJoinedEventTask().execute();
		
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		String eventID = (String) getJoinedEventAdapter.getItem(position);
		Intent intent = new Intent(getApplicationContext(),
				ViewAndJoinEvent.class);
		intent.putExtra("eventID", eventID);
		startActivity(intent);
	}
	
	
	private class GetJoinedEventTask extends AsyncTask<String, Void, String> {
		private String eventID;
		private String eventName;

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> getJoinedEventListParams = new ArrayList<NameValuePair>();
			getJoinedEventListParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			getJoinedEventListParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			getJoinedEventListParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlJoinedEventList,
					getJoinedEventListParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				joinedEventList = json.optJSONArray(TAG_EVENTLIST);
				// looping through All Products
				for (int i = 0; i < joinedEventList.length(); i++) {
					JSONObject event = joinedEventList.optJSONObject(i);
					eventID = event.optString(TAG_EVENTID);
					eventName = event.optString(TAG_EVENTNAME);
					String[] eventItems = { eventID, eventName };
					joinedEventItemsList.add(eventItems);
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
				getJoinedEventAdapter = new ListViewAdapter(
						joinedEventItemsList, GetJoinedEvent.this);
				joinedEventListView.setAdapter(getJoinedEventAdapter);
			}
		}
	}
}
