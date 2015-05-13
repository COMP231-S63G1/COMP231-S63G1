package com.example.wanna;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.wanna.library.ImageLoader;
import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewUserProfile extends Activity {

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
	String profileID;
	String sessionID;
	String userID;
	String userType;
	String nickName;
	String age;
	String gender;
	String description;
	String friendUserID;
	String notificationMessage;
	String pictureURL;		

	ImageView imvUserPicture;

	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_PROFILEINFORMATION = "profileInformation";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_PROFILEID = "profileID";
	private static final String TAG_ProfileAge = "age";
	private static final String TAG_ProfileGender = "gender";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_NOTIFICATIONTYPE="notificationType";
	private static final String TAG_FRIENDUSERIDFROMINTENT = "userID";
	private static final String TAG_FRIENDUSERID = "friendUserID";
	private static final String TAG_SENDERTYPE = "senderType";
	private static final String TAG_SENDERID = "senderID";
	private static final String TAG_RECEIVERTYPE = "receiverType";
	private static final String TAG_RECEIVERID = "receiverID";
	private static final String TAG_RECEIVERUSERID = "receiverUserID";
	private static final String TAG_ACCEPTALBE = "acceptable";
	private static final String TAG_NOTIFICATIONMASSAGE = "notificationMessage";
	private static final String TAG_SENDTIME="sendTime";
	private static final String TAG_PICTUREURL = "pictureURL";



	private String urlViewUserProfile = UserFunctions.URL_ROOT
			+ "DB_ViewUserProfile.php";
	private String urlSendFriendRequest = UserFunctions.URL_ROOT
			+ "DB_SendFriendRequest.php";
	private String urlSendNotification = UserFunctions.URL_ROOT
			+ "DB_SendNotification.php";
	JSONObject profileInformation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_user_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");

		Intent intent = getIntent();
		friendUserID = intent.getStringExtra(TAG_FRIENDUSERIDFROMINTENT);

		imvUserPicture = (ImageView) findViewById(R.id.userPicture);
		tvProfileNickName = (TextView) findViewById(R.id.userNameValue);
		tvProfileGender = (TextView) findViewById(R.id.userGenderValue);
		tvProfileAge = (TextView) findViewById(R.id.userAgeValue);
		tvProfileDescription = (TextView) findViewById(R.id.userDescriptionValue);
		tvStatus = (TextView) findViewById(R.id.userStatusValue);
		new ViewUserProfileTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_user_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void friendRequestBtnOnclick(View view) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		 alert.setMessage("Leave Your Message Here");
		final EditText input = new EditText(this);
		 alert.setView(input);
		 alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	             notificationMessage = input.getEditableText().toString();
	                         System.out.println(notificationMessage);
	                         new SendFriendRequestTask().execute();
	            } // End of onClick(DialogInterface dialog, int whichButton)
	        }); //End of alert.setPositiveButton
	            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int whichButton) {
	                // Canceled.
	                  dialog.cancel();
	              }
	        }); //End of alert.setNegativeButton
	            AlertDialog alertDialog = alert.create();
	            alertDialog.show();
		
		
	}
	public void backBtnOnclick(View view){
		Intent intent = new Intent(getApplicationContext(), PersonLoginSuccess.class);
		startActivity(intent);
	}

	private class ViewUserProfileTask extends AsyncTask<String, Void, String> {
		int success;
		String message;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewUserProfile.this);
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
			ViewProfileParams.add(new BasicNameValuePair(TAG_FRIENDUSERID,
					friendUserID));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlViewUserProfile,
					ViewProfileParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				// successfully received profile info
				JSONArray profileInformationArray = json
						.optJSONArray(TAG_PROFILEINFORMATION); // JSON Array
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
				tvProfileDescription.setText(description);// Loader image - will be shown before loading image
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

	
	private class SendNotificationTask extends AsyncTask<String, Void, String> {
		int success;
		String message;
        String senderType = "User";
        String senderID = userID;
        String receiverType = "User";
        String receiverID = friendUserID;
        String acceptable ="1"; 
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendTime = sdf.format(c.getTime());
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			System.out.println(sendTime);
			List<NameValuePair> SendNotificationParams = new ArrayList<NameValuePair>();
			SendNotificationParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			SendNotificationParams.add(new BasicNameValuePair(TAG_USERID, userID));
			SendNotificationParams
					.add(new BasicNameValuePair(TAG_USERTYPE, userType));

			SendNotificationParams.add(new BasicNameValuePair(TAG_SENDERTYPE,
					senderType));
			SendNotificationParams.add(new BasicNameValuePair(TAG_SENDERID,
					senderID));
			SendNotificationParams.add(new BasicNameValuePair(TAG_RECEIVERTYPE,
					receiverType));
			SendNotificationParams.add(new BasicNameValuePair(TAG_RECEIVERID,
					receiverID));
			SendNotificationParams.add(new BasicNameValuePair(TAG_ACCEPTALBE,
					acceptable));
			SendNotificationParams.add(new BasicNameValuePair(TAG_NOTIFICATIONMASSAGE,
					notificationMessage));
			SendNotificationParams.add(new BasicNameValuePair(TAG_SENDTIME,
					sendTime));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlSendNotification,
					SendNotificationParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			
			Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
		}
	}

	private class SendFriendRequestTask extends AsyncTask<String, Void, String> {
		int success;
		String message;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewUserProfile.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Sending Request ...");
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
			ViewProfileParams.add(new BasicNameValuePair(TAG_FRIENDUSERID,
					friendUserID));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlSendFriendRequest,
					ViewProfileParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			pDialog.dismiss();
			if(success == 0){
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
					.show();
			}else if (success==1){
				new SendNotificationTask().execute();
			}

		}
	}
}
