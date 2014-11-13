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


public class ViewAndJoinEvent extends Activity {
	// Creating JSON Parser object
		JSONParser jsonParser = new JSONParser();
		UserFunctions userFunctions = new UserFunctions();
		
		public static final String MyPREFERENCES = "Wanna";
		SharedPreferences sharedpreferences;
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

		// url to view event detail
		private String urlViewEvent = userFunctions.URL_ROOT
				+ "DB_ViewEvent.php";
		private String urlJoinEvent = userFunctions.URL_ROOT
				+ "DB_JoinEvent.php";
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
		
		//session
		String sessionID;
		String userID;
		String eventID;
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_and_join_event);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

		// getting event details from intent
		Intent i = getIntent();

		// getting event id (pid) from intent
		eventID = i.getStringExtra(TAG_EventID);

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
	
	public void onViewEventDetailBackClick(View view){
		Intent i = new Intent(getApplicationContext(), Login_Success.class);
		startActivity(i);		
	}

	private class ViewEventDetailTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewAndJoinEvent.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... urls) {
					int success;
					try {
						// Building Parameters
						List<NameValuePair> viewEventDatailParams = new ArrayList<NameValuePair>();
						viewEventDatailParams.add(new BasicNameValuePair("eventID", eventID));
						// getting event details by making HTTP request
						JSONObject json = jsonParser.getJSONFromUrl(
								urlViewEvent, viewEventDatailParams);
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received event details
							JSONArray eventDetailArray = json.optJSONArray(TAG_EventDetail); // JSON Array
							// get first event object from JSON Array
							eventDetail = eventDetailArray.getJSONObject(0);
							// evnet with this eventID found
						}else{
						}

					} catch (JSONException e) {
						e.printStackTrace();
						return null;
					}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			tvEventType.setText(eventDetail.optString(TAG_EventType));
			tvEventName.setText(eventDetail.optString(TAG_EventName));
			tvEventDate.setText(eventDetail.optString(TAG_EventDate));
			tvEventTime.setText(eventDetail.optString(TAG_EventTime));
			tvEventVenue.setText(eventDetail.optString(TAG_EventVenue));
			tvEventLocation.setText(eventDetail.optString(TAG_EventLocation));
			tvEventPriceRange.setText(eventDetail.optString(TAG_EventPriceRange));
			tvEventDescription.setText(eventDetail.optString(TAG_EventDescription));
		}
	}
	public void JoinEventonClick(View view){
		new JoinEventTask().execute();
	}
	private class JoinEventTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewAndJoinEvent.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... urls) {
			sessionID = sharedpreferences.getString("sessionID", "");
			userID = sharedpreferences.getString("userID", "");
					int success;
					try {
						// Building Parameters
						List<NameValuePair> joinEventDatailParams = new ArrayList<NameValuePair>();
						joinEventDatailParams.add(new BasicNameValuePair("eventID", eventID));
						joinEventDatailParams.add(new BasicNameValuePair("sessionID", sessionID));
						joinEventDatailParams.add(new BasicNameValuePair("userID", userID));
						JSONObject json = jsonParser.getJSONFromUrl(
								urlJoinEvent, joinEventDatailParams);
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
//							Toast.makeText(getApplicationContext(),
//				                    "Join event succeed", Toast.LENGTH_SHORT).show(); 
						}else{
//							Toast.makeText(getApplicationContext(),
//				                    "Join event fail", Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						e.printStackTrace();
						return null;
					}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
			startActivity(intent);
			
		}
	}
	

}
