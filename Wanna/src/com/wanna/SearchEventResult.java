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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SearchEventResult extends ListActivity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	// url to view event detail
	private String urlSearchEvent = UserFunctions.URL_ROOT
			+ "DB_SearchEvent.php";
	ListView lvEventItem;

	ArrayList<String[]> eventItemsList = new ArrayList<String[]>();
	ListViewAdapter searchEventAdapter;
	String eventID;
	String eventName;
	int success;
	String message;
	String searchType;
	String searchEventName;
	String searchEventCategory;

	// event JSONArray
	JSONArray eventList = null;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_EVENTLIST = "eventList";
	private static final String TAG_EVENTID = "eventID";
	private static final String TAG_EVENTNAME = "eventName";
	private static final String TAG_SEARCHEVENTTYPE = "searchType";
	private static final String TAG_SEARCHEVENTNAME = "searchEventName";
	private static final String TAG_SEARCHEVENTCATEGORY = "searchEventCategory";
	private static final String TAG_SEARCHEVENTTYPENAME = "Name";
	private static final String TAG_SEARCHEVENTTYPECATEGORY = "Category";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_event_result);
		Intent intent = getIntent();
		searchType = intent.getStringExtra(TAG_SEARCHEVENTTYPE);
		if (searchType.equals(TAG_SEARCHEVENTTYPENAME)) {
			searchEventName = intent.getStringExtra(TAG_SEARCHEVENTNAME);
		} else if (searchType.equals(TAG_SEARCHEVENTTYPECATEGORY)) {
			searchEventCategory = intent
					.getStringExtra(TAG_SEARCHEVENTCATEGORY);
		}		
		new SearchEventTask().execute();
		lvEventItem = (ListView) findViewById(android.R.id.list);
		searchEventAdapter = new ListViewAdapter(eventItemsList, this);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		String eventID = (String) searchEventAdapter.getItem(position);
		Intent intent = new Intent(getApplicationContext(),
				ViewAndJoinEvent.class);
		intent.putExtra(TAG_EVENTID, eventID);
		startActivity(intent);		
	}

	private class SearchEventTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> searchEventParams = new ArrayList<NameValuePair>();
			searchEventParams.add(new BasicNameValuePair(TAG_SEARCHEVENTTYPE,
					searchType));
			if (searchType.equals(TAG_SEARCHEVENTTYPENAME)) {
				searchEventParams.add(new BasicNameValuePair(TAG_SEARCHEVENTNAME,
						searchEventName));
			} else if (searchType.equals(TAG_SEARCHEVENTTYPECATEGORY)) {
				searchEventParams.add(new BasicNameValuePair(
						TAG_SEARCHEVENTCATEGORY, searchEventCategory));
			}
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlSearchEvent,
					searchEventParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				eventList = json.optJSONArray(TAG_EVENTLIST);
				// looping through All Products
				for (int i = 0; i < eventList.length(); i++) {
					JSONObject event = eventList.optJSONObject(i);
					eventID = event.optString(TAG_EVENTID);
					eventName = event.optString(TAG_EVENTNAME);
					String[] eventItems = { eventID, eventName };
					eventItemsList.add(eventItems);
				}
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(success == 1){
				lvEventItem.setAdapter(searchEventAdapter);
			}
			else if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
