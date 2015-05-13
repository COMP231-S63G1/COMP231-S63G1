package com.wannaproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.wannaproject.library.ImageLoader;
import com.wannaproject.library.JSONParser;
import com.wannaproject.library.UserFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPersonProfile extends Activity {

	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	private ProgressDialog pDialog;

	TextView tvProfileNickName;
	TextView tvProfileGender;
	TextView tvProfileAge;
	TextView tvProfileDescription;
	TextView tvStatus;
	ListView createdEventListView;
	ListView joinedEventListView;
	TextView tvGetCreatedEventTextView;
	TextView tvGetJoinedEventTextView;
	TextView tvGetCreatedGroupTextView;
	TextView tvGetJoinedGroupTextView;
	String profileID;
	String sessionID;
	String userID;
	String userType;
	String nickName;
	String age;
	String gender;
	String description;
	String pictureURL;		

	ImageView imvUserPicture;

	// url to view profile info
	private String urlViewProfileInformation = UserFunctions.URL_ROOT
			+ "DB_ViewPersonProfile.php";
	// url to get user created event
	private String urlCountCreatedEvent = UserFunctions.URL_ROOT
			+ "DB_CountCreatedEvent.php";
	// url to get user created event
	private String urlCountJoinedEvent = UserFunctions.URL_ROOT
			+ "DB_CountJoinedEvent.php";
	private String urlCountCreatedGroup = UserFunctions.URL_ROOT
			+ "DB_CountCreatedGroup.php";
	// url to get user created event
	private String urlCountJoinedGroup = UserFunctions.URL_ROOT
			+ "DB_CountJoinedGroup.php";
	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ProfileInformation = "profileInformation";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_ProfileID = "profileID";
	private static final String TAG_ProfileAge = "age";
	private static final String TAG_ProfileGender = "gender";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_Status = "eventName";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_EVENTLIST = "eventList";
	private static final String TAG_GROUPLIST = "groupList";
	private static final String TAG_JOINEDEVENTLIST = "joinedEventList";
	private static final String TAG_EVENTID = "eventID";
	private static final String TAG_EVENTNAME = "eventName";
	private static final String TAG_GROUPID = "groupID";
	private static final String TAG_GROUPNAME = "groupName";
	private static final String TAG_COUNTEVENT = "countEvent";
	private static final String TAG_COUNTGROUP = "countGroup";
	private static final String TAG_PICTUREURL = "pictureURL";
	
	JSONObject profileInformation;
	JSONArray joinedGroupList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_person_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		nickName = sharedpreferences.getString(TAG_NICKNAME, "");

		// Edit Text
		imvUserPicture = (ImageView) findViewById(R.id.userPicture);
		tvProfileNickName = (TextView) findViewById(R.id.tvProfileNickNameValue);
		tvProfileGender = (TextView) findViewById(R.id.tvProfileGenderValue);
		tvProfileAge = (TextView) findViewById(R.id.tvProfileAgeValue);
		tvProfileDescription = (TextView) findViewById(R.id.tvProfileDescriptionValue);
		tvGetCreatedEventTextView = (TextView) findViewById(R.id.tvProfileGetCreateEventValue);
		tvGetJoinedEventTextView = (TextView) findViewById(R.id.tvProfileGetJoinEventValue);
		tvGetCreatedGroupTextView = (TextView) findViewById(R.id.tvProfileGetCreateGroupValue);
		tvGetJoinedGroupTextView = (TextView) findViewById(R.id.tvProfileGetJoinGroupValue);
		new ViewProfileInformationTask().execute();
		new GetCreatedEventTask().execute();
		new GetJoinedEventTask().execute();
		new GetCreatedGroupTask().execute();
		new GetJoinedGroupTask().execute();
	}

	public void onViewProfileInformationBackClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				PersonLoginSuccess.class);
		startActivity(intent);
	}

	public void onEditProfileClick(View view) {
		Intent intent = new Intent(getApplicationContext(), EditProfile.class);
		intent.putExtra(TAG_NICKNAME, nickName);
		intent.putExtra(TAG_DESCRIPTION, description);
		intent.putExtra(TAG_PICTUREURL, pictureURL);
		startActivity(intent);
	}

	private class ViewProfileInformationTask extends
			AsyncTask<String, Void, String> {
		int success;
		String message;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewPersonProfile.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> ViewProfileParams = new ArrayList<NameValuePair>();
			ViewProfileParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			ViewProfileParams.add(new BasicNameValuePair(TAG_USERID, userID));
			ViewProfileParams
					.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(
					urlViewProfileInformation, ViewProfileParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				// successfully received profile info
				JSONArray profileInformationArray = json
						.optJSONArray(TAG_ProfileInformation); // JSON Array
				// get first profile object from JSON Array
				profileInformation = profileInformationArray.optJSONObject(0);
				nickName = profileInformation.optString(TAG_NICKNAME);
				age = profileInformation.optString(TAG_ProfileAge);
				gender = profileInformation.optString(TAG_ProfileGender);
				description = profileInformation.optString(TAG_DESCRIPTION);
				pictureURL = "http://wanna.developerdarren.com"
						+ profileInformation.optString(TAG_PICTUREURL);
				
			} else {
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			pDialog.dismiss();
			if (success == 1) {
				tvProfileNickName.setText(nickName);
				tvProfileGender.setText(gender);
				tvProfileAge.setText(age);
				tvProfileDescription.setText(description);

				// Loader image - will be shown before loading image
		        int loader = R.drawable.loader;
				ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		        
		        // whenever you want to load an image from url
		        // call DisplayImage function
		        // url - image url to load
		        // loader - loader image, will be displayed before getting image
		        // image - ImageView 
		        imgLoader.DisplayImage(pictureURL, loader, imvUserPicture);
			}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	private class GetCreatedEventTask extends AsyncTask<String, Void, String> {
		private int success;
		private String message;
		private int countEvent;

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> getCreatedEventListParams = new ArrayList<NameValuePair>();
			getCreatedEventListParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			getCreatedEventListParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			getCreatedEventListParams.add(new BasicNameValuePair(TAG_USERTYPE,
					userType));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlCountCreatedEvent,
					getCreatedEventListParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if(success == 1){
			countEvent = json.optInt(TAG_COUNTEVENT);
			} else {
			}
			return null;
		}


		@Override
		protected void onPostExecute(String result) {
			if(success == 1){
				tvGetCreatedEventTextView.setText(countEvent + " Created Events");
			}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			} 
		}
	}

	private class GetJoinedEventTask extends AsyncTask<String, Void, String> {
		int success;
		String message;
		private int countEvent;

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> getJoinedEventListParams = new ArrayList<NameValuePair>();
			getJoinedEventListParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			getJoinedEventListParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			getJoinedEventListParams.add(new BasicNameValuePair(TAG_USERTYPE,
					userType));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlCountJoinedEvent,
					getJoinedEventListParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if(success == 1){
				countEvent = json.optInt(TAG_COUNTEVENT);
				}else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(success == 1){
				tvGetJoinedEventTextView.setText(countEvent + " Joined Events");
			}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	private class GetCreatedGroupTask extends AsyncTask<String, Void, String> {
		int success;
		String message;
		private int countGroup;

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> getCreatedGroupListParams = new ArrayList<NameValuePair>();
			getCreatedGroupListParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			getCreatedGroupListParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			getCreatedGroupListParams.add(new BasicNameValuePair(TAG_USERTYPE,
					userType));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlCountCreatedGroup,
					getCreatedGroupListParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if(success == 1){
				countGroup = json.optInt(TAG_COUNTGROUP);
			}else {

			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(success == 1){
				tvGetCreatedGroupTextView.setText(countGroup + " Created Groups");
			}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class GetJoinedGroupTask extends AsyncTask<String, Void, String> {
		int success;
		String message;
		private int countGroup;

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> getJoinedGroupListParams = new ArrayList<NameValuePair>();
			getJoinedGroupListParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			getJoinedGroupListParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			getJoinedGroupListParams.add(new BasicNameValuePair(TAG_USERTYPE,
					userType));
			// getting event details by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlCountJoinedGroup,
					getJoinedGroupListParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if(success == 1){
				countGroup = json.optInt(TAG_COUNTGROUP);
			}else {

			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(success == 1){
				tvGetJoinedGroupTextView.setText(countGroup + " Joined Groups");
			}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void GetJoinEventValueOnClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				GetJoinedEvent.class);
		startActivity(intent);

	}

	public void GetCreateEventValueOnClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				GetCreatedEvent.class);
		startActivity(intent);

	}

	public void GetCreateGroupValueOnClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				GetCreatedGroup.class);
		startActivity(intent);

	}

	public void GetJoinGroupValueOnClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				GetJoinedGroup.class);
		startActivity(intent);

	}
}