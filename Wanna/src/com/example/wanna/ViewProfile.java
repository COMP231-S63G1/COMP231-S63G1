package com.example.wanna;

import java.util.ArrayList;
import java.util.HashMap;
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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProfile extends Activity {

	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	private ProgressDialog pDialog;

	ArrayList<HashMap<String, String>> viewProfileInformationList;
	ArrayList<String[]> createdEventItemsList = new ArrayList<String[]>();
	ArrayList<String[]> joinedEventItemsList = new ArrayList<String[]>();
	TextView tvProfileNickName;
	TextView tvProfileGender;
	TextView tvProfileAge;
	TextView tvProfileDescription;
	TextView tvStatus;
	ListView createdEventListView;
	ListView joinedEventListView;
	TextView tvGetCreatedEventTextView;
	TextView tvGetJoinedEventTextView;
	String profileID;
	String sessionID;
	String userID;
	String nickName;
	String age;
	String gender;
	String description;
	String message;
	int success;

	// url to view profile info
	private String urlViewProfileInformation = userFunctions.URL_ROOT
			+ "DB_ViewProfileInformation.php";
	// url to get user created event
	private String urlCreatedEventList = userFunctions.URL_ROOT
			+ "DB_GetCreatedEventList.php";
	// url to get user created event
	private String urlJoinedEventList = userFunctions.URL_ROOT
			+ "DB_GetJoinedEventList.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ProfileInformation = "profileInformation";
	private static final String TAG_ProfileNickName = "nickName";
	private static final String TAG_ProfileID = "profileID";
	private static final String TAG_ProfileAge = "age";
	private static final String TAG_ProfileGender = "gender";
	private static final String TAG_ProfileDescription = "description";
	private static final String TAG_Status = "eventName";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_EVENTLIST = "eventList";
	private static final String TAG_JOINEDEVENTLIST = "joinedEventList";
	private static final String TAG_EVENTID = "eventID";
	private static final String TAG_EVENTNAME = "eventName";

	JSONObject profileInformation;

	JSONArray createdEventList = null;
	JSONArray joinedEventList = null;
	private ListAdapter getCreatedEventAdapter;
	private ListAdapter getJoinedEventAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		// Edit Text
		tvProfileNickName = (TextView) findViewById(R.id.tvProfileNickNameValue);
		tvProfileGender = (TextView) findViewById(R.id.tvProfileGenderValue);
		tvProfileAge = (TextView) findViewById(R.id.tvProfileAgeValue);
		tvProfileDescription = (TextView) findViewById(R.id.tvProfileDescriptionValue);
		tvGetCreatedEventTextView =(TextView) findViewById(R.id.tvProfileGetCreateEventValue);
		tvGetJoinedEventTextView =(TextView) findViewById(R.id.tvProfileGetJoinEventValue);
		new ViewProfileInformationTask().execute();
		new GetCreatedEventTask().execute();
		new GetJoinedEventTask().execute();
		// tvStatus = (TextView) findViewById(R.id.tvProfileStatusValue);
		

		
//		createdEventListView = (ListView) findViewById(R.id.createdEventList);
//		getCreatedEventAdapter = new ListAdapter(createdEventItemsList, this);
//		new GetCreatedEventTask().execute();
//		
//		joinedEventListView = (ListView) findViewById(R.id.joinedEventList);
//		getJoinedEventAdapter = new ListAdapter(joinedEventItemsList, this);
//		new GetJoinedEventTask().execute();

	}
	public void onViewProfileInformationBackClick(View view) {
		Intent intent = new Intent(getApplicationContext(), Login_Success.class);
		startActivity(intent);
	}

	public void onEditProfileClick(View view) {
		Intent intent = new Intent(getApplicationContext(), EditProfile.class);
		intent.putExtra("nickName", nickName);
		intent.putExtra("description", description);
		startActivity(intent);
	}

	private class ViewProfileInformationTask extends
			AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewProfile.this);
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
			nickName = sharedpreferences.getString("nickName", "");
			// Building Parameters
			List<NameValuePair> ViewProfileParams = new ArrayList<NameValuePair>();
			ViewProfileParams
					.add(new BasicNameValuePair("sessionID", sessionID));
			ViewProfileParams.add(new BasicNameValuePair("userID", userID));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(
					urlViewProfileInformation, ViewProfileParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			if (success == 1) {
				// successfully received profile info
				JSONArray profileInformationArray = json
						.optJSONArray(TAG_ProfileInformation); // JSON Array
				// get first profile object from JSON Array
				profileInformation = profileInformationArray.optJSONObject(0);
				nickName = profileInformation.optString(TAG_ProfileNickName);
				age = profileInformation.optString(TAG_ProfileAge);
				gender = profileInformation.optString(TAG_ProfileGender);
				description = profileInformation
						.optString(TAG_ProfileDescription);
				// status = profileInformation.optString(TAG_Status);
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			pDialog.dismiss();
			tvProfileNickName.setText(nickName);
			tvProfileGender.setText(gender);
			tvProfileAge.setText(age);
			tvProfileDescription.setText(description);
			// tvStatus.setText(status);

		}
	}

	private class GetCreatedEventTask extends AsyncTask<String, Void, String> {
		private String eventID;
		private String eventName;

		@Override
		protected String doInBackground(String... urls) {
			sessionID = sharedpreferences.getString("sessionID", "");
			userID = sharedpreferences.getString("userID", "");
			// Building Parameters
			List<NameValuePair> getCreatedEventListParams = new ArrayList<NameValuePair>();
			getCreatedEventListParams.add(new BasicNameValuePair("profileID",
					profileID));
			getCreatedEventListParams.add(new BasicNameValuePair("sessionID",
					sessionID));
			getCreatedEventListParams.add(new BasicNameValuePair("userID",
					userID));
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
			}
			else{
//				getCreatedEventAdapter.setItemList(createdEventItemsList);
//				createdEventListView.setAdapter(getCreatedEventAdapter);
				String textShowed="";
				for(String[] arr : createdEventItemsList){
					textShowed+=arr[1]+"\n";
					System.out.println(textShowed);
				}
				tvGetCreatedEventTextView.setText(textShowed);
				}
		}
	}

	private class GetJoinedEventTask extends AsyncTask<String, Void, String> {
		private String eventID;
		private String eventName;

		@Override
		protected String doInBackground(String... urls) {
			sessionID = sharedpreferences.getString("sessionID", "");
			userID = sharedpreferences.getString("userID", "");
			// Building Parameters
			List<NameValuePair> getJoinedEventListParams = new ArrayList<NameValuePair>();
			getJoinedEventListParams.add(new BasicNameValuePair("profileID",
					profileID));
			getJoinedEventListParams.add(new BasicNameValuePair("sessionID",
					sessionID));
			getJoinedEventListParams.add(new BasicNameValuePair("userID",
					userID));
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
					String[] eventItems =  { eventID, eventName };
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
			}else{
//				System.out.println();
//				getJoinedEventAdapter.setItemList(joinedEventItemsList);
//				joinedEventListView.setAdapter(getJoinedEventAdapter);
				String textShowed="";
				for(String[] arr : joinedEventItemsList){
					textShowed+=arr[1]+"\n";
					System.out.println(textShowed);
				}
				tvGetJoinedEventTextView.setText(textShowed);
			}
			
		}
	}
	private class ListAdapter extends BaseAdapter {

		Context ctxt;
		LayoutInflater listViewInflater;
		ArrayList<String[]> listItemsList;
		
		public ListAdapter(ArrayList<String[]> listItemsList, Context ctxt){
			this.listItemsList = listItemsList;
			this.ctxt = ctxt;
			listViewInflater = (LayoutInflater)ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listItemsList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listItemsList.get(position)[0];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView = listViewInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
				TextView listItem = (TextView)convertView.findViewById(android.R.id.text1);
				listItem.setText(listItemsList.get(position)[1]);
			}
			return convertView;
		}
		public void setItemList(ArrayList<String[]> itemList){
			listItemsList = itemList;
		}

	}
	
	public void GetJoinEventValueOnClick(View view){
		Intent intent= new Intent(getApplicationContext(),GetJoinedEvent.class);
		startActivity(intent);
		
	}
	public void GetCreateEventValueOnClick(View view){
		Intent intent= new Intent(getApplicationContext(),GetCreatedEvent.class);
		startActivity(intent);
		
	}
}
