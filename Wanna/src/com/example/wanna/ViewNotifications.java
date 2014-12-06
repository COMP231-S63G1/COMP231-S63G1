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

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ViewNotifications extends ListActivity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();
	
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	private String urlViewNotification = UserFunctions.URL_ROOT
			+ "DB_ViewNotifications.php";
	ListView lvNotifications;

	ArrayList<String[]> notificationsList = new ArrayList<String[]>();
	ListViewAdapter notificationsAdapter;
	String sessionID;
	String userID;
	String userType;
	String notificationID;
	int success;
	String message;
	String notificationMessage;
	String acceptable;

	// group JSONArray
	JSONArray notificationJSONList = null;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_NOTIFICATIONJSONLIST = "notificationJSONList";
	private static final String TAG_NOTIFICATIONID = "notificationID";
	private static final String TAG_NOTIFICATIONMESSAGE = "notificationMessage";
	private static final String TAG_ACCEPTABLE = "acceptable";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_notifications);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		lvNotifications = (ListView) findViewById(android.R.id.list);
		notificationsAdapter = new ListViewAdapter(notificationsList, this);
		new DisplayNotificationTask().execute();
	}
	
	private class DisplayNotificationTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			
			// Building Parameters
			List<NameValuePair> notificationsParams = new ArrayList<NameValuePair>();
			notificationsParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			notificationsParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			notificationsParams.add(new BasicNameValuePair(TAG_USERTYPE,
					userType));
			JSONObject json = jsonParser.getJSONFromUrl(urlViewNotification,
					notificationsParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				notificationJSONList = json.optJSONArray(TAG_NOTIFICATIONJSONLIST);
				System.out.println(notificationJSONList);
				// looping through All Products
				for (int i = 0; i < notificationJSONList.length(); i++) {
					JSONObject notifications = notificationJSONList.optJSONObject(i);
					notificationID = notifications.optString(TAG_NOTIFICATIONID);
					notificationMessage = notifications.optString(TAG_NOTIFICATIONMESSAGE);
					acceptable = notifications.optString(TAG_ACCEPTABLE);
					String[] notificationInfo = { notificationID, notificationMessage, acceptable};
					notificationsList.add(notificationInfo);
				}
			} else {
			}
			return null;
		}
		protected void onPostExecute(String result) {
			if (success == 1) {
				lvNotifications.setAdapter(notificationsAdapter);
			}
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	public void onListItemClick(ListView l, View v, int position, long id) {
		notificationID = (String) notificationsAdapter.getItem(position);
		acceptable = (String) notificationsAdapter.getItemPrivacy(position);
		if (acceptable.equals("1")) {
			Intent intent = new Intent(getApplicationContext(),
					ViewAcceptableNotification.class);
			intent.putExtra(TAG_NOTIFICATIONID, notificationID);
			startActivity(intent);
		}else if(acceptable.equals("0")){
			Intent intent = new Intent(getApplicationContext(),
					ViewUnacceptableNotification.class);
			intent.putExtra(TAG_NOTIFICATIONID, notificationID);
			startActivity(intent);
		}
	}
		
		
		
	}

	