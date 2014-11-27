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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GetCreatedEvent extends ListActivity {

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();
	private ProgressDialog pDialog;

	// url to view event detail
	private String urlCreatedEventList = userFunctions.URL_ROOT
			+ "DB_GetCreatedEventList.php";
	ListView lvEventItem;
	ListView createdEventListView;

	ArrayList<String[]> createdEventItemsList = new ArrayList<String[]>();
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
	JSONArray createdEventList;
	private ListAdapter getCreatedEventAdapter;
	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_EVENTLIST = "eventList";
	private static final String TAG_EVENTID = "eventID";
	private static final String TAG_EVENTNAME = "eventName";
	private static final String TAG_SEARCHEVENTNAME = "searchEventName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_created_event);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		
		createdEventListView = (ListView) findViewById(android.R.id.list);
		new GetCreatedEventTask().execute();
		
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		String eventID = (String) getCreatedEventAdapter.getItem(position);
		Intent intent = new Intent(getApplicationContext(),
				ViewCreatedEvent.class);
		intent.putExtra("eventID", eventID);
		startActivity(intent);
	}
	
	private class GetCreatedEventTask extends AsyncTask<String, Void, String> {
		private String eventID;
		private String eventName;
	    private String profileID;
		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> getCreatedEventListParams = new ArrayList<NameValuePair>();
			getCreatedEventListParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			getCreatedEventListParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			getCreatedEventListParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlCreatedEventList,
					getCreatedEventListParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				createdEventList = json.optJSONArray(TAG_EVENTLIST);
				// looping through All Products
				for (int i = 0; i < createdEventList.length(); i++) {
					JSONObject event = createdEventList.optJSONObject(i);
					eventID = event.optString(TAG_EVENTID);
					eventName = event.optString(TAG_EVENTNAME);
					String[] eventItems = { eventID, eventName };
					createdEventItemsList.add(eventItems);
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
				getCreatedEventAdapter = new ListViewAdapter(
						createdEventItemsList,GetCreatedEvent.this);
				createdEventListView.setAdapter(getCreatedEventAdapter);
			}
		}
	}
}
