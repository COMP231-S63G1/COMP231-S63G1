package com.example.wanna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewEventDetail extends Activity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	ArrayList<HashMap<String, String>> eventDetailList;
	TextView tvEventType;
	TextView tvEventName;
	TextView tvEventDate;
	TextView tvEventTime;
	TextView tvEventVenue;
	TextView tvEventLocation;
	TextView tvEventPriceRange;
	TextView tvEventDescription;

	String eventID;

	// url to view event detail
	private String urlViewEventDetail = userFunctions.URL_ROOT
			+ "DB_ViewEventDetail.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_EventDetail = "eventDetail";
	private static final String TAG_EventID = "eventID";
	private static final String TAG_EventType = "eventType";
	private static final String TAG_EventName = "eventName";
	private static final String TAG_EventDate = "eventDate";
	private static final String TAG_EventTime = "eventTime";
	private static final String TAG_EventVenue = "eventVenue";
	private static final String TAG_EventLocation = "eventLocation";
	private static final String TAG_EventPriceRange = "eventPriceRange";
	private static final String TAG_EventDescription = "eventDescription";
	
	JSONObject eventDetail;

//	// Profile detail JSONArray
//	JSONArray eventDetailArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_event_detail);

		// getting event details from intent
		Intent i = getIntent();

		// getting event id (pid) from intent
		eventID = i.getStringExtra(TAG_EventID);

		// // Hashmap for ListView
		// eventDetailList = new ArrayList<HashMap<String, String>>();

		// Loading event in Background Thread
		new ViewEventDetailTask().execute();
		
	}
	
	public void onViewEventDetailBackClick(View view){
		Intent i = new Intent(getApplicationContext(), Login_Success.class);
		startActivity(i);		
	}

	private class ViewEventDetailTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			// updating UI from Background Thread
//			runOnUiThread(new Runnable() {
//				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> ViewEventDatailparams = new ArrayList<NameValuePair>();
						ViewEventDatailparams.add(new BasicNameValuePair("eventID", eventID));
						// getting event details by making HTTP request
						JSONObject json = jsonParser.getJSONFromUrl(
								urlViewEventDetail, ViewEventDatailparams);
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received event details
							JSONArray eventDetailArray = json.optJSONArray(TAG_EventDetail); // JSON Array
							// get first event object from JSON Array
							eventDetail = eventDetailArray.getJSONObject(0);
							// evnet with this eventID found
							// Edit Text
							tvEventType = (TextView) findViewById(R.id.tvEventTypeValue);
							tvEventName = (TextView) findViewById(R.id.tvEventNameValue);
							tvEventDate = (TextView) findViewById(R.id.tvEventDateValue);
							tvEventTime = (TextView) findViewById(R.id.tvEventTimeValue);
							tvEventVenue = (TextView) findViewById(R.id.tvEventVenueValue);
							tvEventLocation = (TextView) findViewById(R.id.tvEventLocationValue);
							tvEventPriceRange = (TextView) findViewById(R.id.tvEventPriceValue);
							tvEventDescription = (TextView) findViewById(R.id.tvEventDescriptionValue);


							// display product data in EditText
							tvEventType.setText(eventDetail.optString(TAG_EventType));
							tvEventName.setText(eventDetail.optString(TAG_EventName));
							tvEventDate.setText(eventDetail.optString(TAG_EventDate));
							tvEventTime.setText(eventDetail.optString(TAG_EventTime));
							tvEventVenue.setText(eventDetail.optString(TAG_EventVenue));
							tvEventLocation.setText(eventDetail.optString(TAG_EventLocation));
							tvEventPriceRange.setText(eventDetail.optString(TAG_EventPriceRange));
							tvEventDescription.setText(eventDetail.optString(TAG_EventDescription));
						}else{
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
//				}
//			});

			return null;
		}
	}
}
