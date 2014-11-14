package com.example.wanna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
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

public class SearchEventResult extends ListActivity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();
	private ProgressDialog pDialog;
	
	// url to view event detail
	private String urlSearchEvent = userFunctions.URL_ROOT + "DB_SearchEvent.php";
	ListView lvEventItem;
	
	ArrayList<String[]> eventItemsList = new ArrayList<String[]>();
	ListViewAdapter searchEventAdapter;
	String eventID;
	String eventName;
	int success;
	String message;
	String searchEventName;
	
	// products JSONArray
	JSONArray eventList = null;
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_EVENTLIST = "eventList";
	private static final String TAG_EVENTID = "eventID";
	private static final String TAG_EVENTNAME = "eventName";
	private static final String TAG_SEARCHEVENTNAME = "searchEventName";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_event_result);
		Intent intent = getIntent();
		searchEventName = intent.getStringExtra(TAG_SEARCHEVENTNAME);
		new SearchEventTask().execute();
		lvEventItem = (ListView)findViewById(android.R.id.list);
		searchEventAdapter = new ListViewAdapter(eventItemsList, this);
		lvEventItem.setAdapter(searchEventAdapter);
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
	    String eventID = (String) searchEventAdapter.getItem(position);
		Intent intent = new Intent(getApplicationContext(), ViewAndJoinEvent.class);
		intent.putExtra("eventID", eventID);
		startActivity(intent);	
	  }
	
	private class SearchEventTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchEventResult.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> searchEventParams = new ArrayList<NameValuePair>();
			searchEventParams.add(new BasicNameValuePair("searchEventName", searchEventName));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(
					urlSearchEvent, searchEventParams);
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
					String[] eventItems = {eventID, eventName};
					eventItemsList.add(eventItems);
//				// successfully received event details
//				JSONArray searchEventArray = json.optJSONArray(TAG_EVENTLIST);
//				List<String> eventArrayList = new ArrayList<String>();
//				for(int i = 0; i < searchEventArray.length(); i++){
//					try {
//						JSONObject jsonObject = searchEventArray.getJSONObject(i);
//						eventArrayList.add(jsonObject.toString());
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					testEventItems = eventArrayList.toArray(new String[eventArrayList.size()]);
				}
			}else{
			}
			return null;
			}
		
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(),
					message, Toast.LENGTH_SHORT).show(); 
			
		}
		}
}
