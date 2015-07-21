package com.comp231061.project.wanna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.comp231061.project.wanna.library.JSONParser;
import com.comp231061.project.wanna.library.UserFunctions;
import com.comp231061.project.wanna.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewUnacceptableNotification extends Activity {
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	public static final String MyPREFERENCES = "Wanna";
	private String urlViewNotificationInformation = UserFunctions.URL_ROOT + "DB_ViewAcceptableNotification.php";

	SharedPreferences sharedpreferences;
	// JSON Node names
	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_NOTIFICATION = "notification";	
	private static final String TAG_NOTIFICATIONID = "notificationID";	
	private static final String TAG_SENDERNAME = "senderName";	
	private static final String TAG_SENDTIME = "sendTime";	
	private static final String TAG_NOTIFICATIONMESSAGE = "notificationMessage";	

	TextView tvNotificationTitle;
	TextView tvNotificationMessage;
	Button btnNotificationDecline;
	Button btnNotificationAccept;

	String sessionID;
	String userID;
	String userType;
	int success;
	String message;
	String notificationID;
	String senderName;
	String senderTime;
	String notificationMessage;
	
	JSONObject notification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_unacceptable_notification);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		// Edit Text
		tvNotificationTitle = (TextView) findViewById(R.id.tvNotificationTitle);
		tvNotificationMessage = (TextView) findViewById(R.id.tvNotificationMessage);
		btnNotificationDecline=(Button) findViewById(R.id.NotificationDecline);
		btnNotificationAccept=(Button) findViewById(R.id.NotificationAccept);
		Intent intent = getIntent();
		notificationID = intent.getStringExtra(TAG_NOTIFICATIONID);
		new ViewNotificationTask().execute();
	}
	
    public void onBakcClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				ViewNotifications.class);
		startActivity(intent);
	}
	
	private class ViewNotificationTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> viewNotificationParams = new ArrayList<NameValuePair>();
			viewNotificationParams.add(new BasicNameValuePair(TAG_SESSIONID, sessionID));
			viewNotificationParams.add(new BasicNameValuePair(TAG_USERID, userID));
			viewNotificationParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			viewNotificationParams.add(new BasicNameValuePair(TAG_NOTIFICATIONID, notificationID));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlViewNotificationInformation, viewNotificationParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				// successfully received profile info
				JSONArray profileInformationArray = json.optJSONArray(TAG_NOTIFICATION); 
				notification = profileInformationArray.optJSONObject(0);
				senderName = notification.optString(TAG_SENDERNAME);
				senderTime = notification.optString(TAG_SENDTIME);
				notificationMessage = notification.optString(TAG_NOTIFICATIONMESSAGE);
			}
			return null;			
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(success == 1){
				tvNotificationTitle.setText(senderName + " sent you new notification at: " + senderTime);
				tvNotificationMessage.setText(notificationMessage);
			}
			else if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
