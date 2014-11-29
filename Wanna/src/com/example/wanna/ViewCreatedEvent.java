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
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCreatedEvent extends Activity {

	// Creating JSON Parser object.
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();
	private ProgressDialog pDialog;

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
	private String urlViewEventDetail = UserFunctions.URL_ROOT
			+ "DB_ViewEvent.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
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
	// session variables
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	String sessionID;
	String userID;
	int success;
	String message;

	// // Profile detail JSONArray
	// JSONArray eventDetailArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_created_event);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		// getting event details from intent
		Intent intent = getIntent();

		// getting event id (pid) from intent
		eventID = intent.getStringExtra(TAG_EventID);
		// Edit Text
		tvEventType = (TextView) findViewById(R.id.tvEventTypeValue);
		tvEventName = (TextView) findViewById(R.id.tvEventNameValue);
		tvEventDate = (TextView) findViewById(R.id.tvEventDateValue);
		tvEventTime = (TextView) findViewById(R.id.tvEventTimeValue);
		tvEventVenue = (TextView) findViewById(R.id.tvEventVenueValue);
		tvEventLocation = (TextView) findViewById(R.id.tvEventLocationValue);
		tvEventPriceRange = (TextView) findViewById(R.id.tvEventPriceValue);
		tvEventDescription = (TextView) findViewById(R.id.tvEventDescriptionValue);

		// Loading event in Background Thread
		new ViewEventDetailTask().execute();

	}

	public void onViewEventDetailBackClick(View view) {
		Intent i = new Intent(getApplicationContext(), PersonLoginSuccess.class);
		startActivity(i);
	}

	private class ViewEventDetailTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewCreatedEvent.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> viewEventDatailParams = new ArrayList<NameValuePair>();
			viewEventDatailParams.add(new BasicNameValuePair(TAG_EventID,
					eventID));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlViewEventDetail,
					viewEventDatailParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				// successfully received event details
				JSONArray eventDetailArray = json.optJSONArray(TAG_EventDetail); // JSON
																					// Array
				// get first event object from JSON Array
				eventDetail = eventDetailArray.optJSONObject(0);
				// evnet with this eventID found
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			pDialog.dismiss();
			if (success == 1) {
				tvEventType.setText(eventDetail.optString(TAG_EventType));
				tvEventName.setText(eventDetail.optString(TAG_EventName));
				tvEventDate.setText(eventDetail.optString(TAG_EventDate));
				tvEventTime.setText(eventDetail.optString(TAG_EventTime));
				tvEventVenue.setText(eventDetail.optString(TAG_EventVenue));
				tvEventLocation.setText(eventDetail
						.optString(TAG_EventLocation));
				tvEventPriceRange.setText(eventDetail
						.optString(TAG_EventPriceRange));
				tvEventDescription.setText(eventDetail
						.optString(TAG_EventDescription));
			}
			else{
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();				
			}
		}
	}

	public void onEditEventDetailButtonClick(View view) {
		Intent intent = new Intent(getApplicationContext(), EditEvent.class);
		intent.putExtra("eventID", eventDetail.optString("eventID"));
		intent.putExtra("eventType", tvEventType.getText().toString());
		intent.putExtra("eventName", tvEventName.getText().toString());
		intent.putExtra("eventDate", tvEventDate.getText().toString());
		intent.putExtra("eventTime", tvEventTime.getText().toString());
		intent.putExtra("eventVenue", tvEventVenue.getText().toString());
		intent.putExtra("eventLocation", tvEventLocation.getText().toString());
		intent.putExtra("eventPriceRange", tvEventPriceRange.getText()
				.toString());
		intent.putExtra("eventDescription", tvEventDescription.getText()
				.toString());
		startActivity(intent);
	}
}