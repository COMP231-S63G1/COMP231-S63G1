package com.comp231061.project.wanna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.comp231061.project.wanna.library.JSONParser;
import com.comp231061.project.wanna.library.LocationTracker;
import com.comp231061.project.wanna.library.UserFunctions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.comp231061.project.wanna.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OrganizationLoginSuccess extends Activity {
	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_PERSON = "Person";
	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_LONGITUDE = "longitude";
    private static final String TAG_UploadGCMRegid = "gcmRegid";
  
    TextView tvTextwelcome;
    
	String sessionID;
	String userID;
	String userType;
	String nickName;     
	int success;
	String message;
	double latitude;
    double longitude;

	LocationTracker location;	 
	
	/**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "73008187607";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCM Demo";

	GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    Context context;

    String regid;
    
	UserFunctions userFunctions = new UserFunctions();
	private String urlCheckLogin = UserFunctions.URL_ROOT + "DB_LoginSuccess.php";
    private String urlUploadGCMRegid = userFunctions.URL_ROOT
			+ "DB_UploadGCMRegid.php";


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_login_success); 
        
        location = new LocationTracker(OrganizationLoginSuccess.this); 

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		nickName = sharedpreferences.getString(TAG_NICKNAME, "");
        context = getApplicationContext();
		if (userType.equals(TAG_PERSON)) {
			Intent intent = new Intent(getApplicationContext(),
					PersonLoginSuccess.class);
			startActivity(intent);
		} 
        tvTextwelcome = (TextView) findViewById(R.id.textwelcome);
        if(location.canGetLocation()){            
            latitude = location.getLatitude();
            longitude = location.getLongitude();
           }else{
        	   Toast.makeText(getApplicationContext(), "Cannot get location",
						Toast.LENGTH_SHORT).show();
        }
        
     // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);                        

        	//Notice
        	System.out.println("old regid: " + regid);

            if (regid.isEmpty()) {
                registerInBackground();
            }
                        
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
        new LoginSeccessTask().execute();     
        new GCMRegisterTask().execute();          
    }
    
    public void onCreateEvent(View view){	
		Intent intent = new Intent(this, CreateEvent.class);
		startActivity(intent);
		}
    
    public void onViewProfileClick(View view){	
		Intent intent = new Intent(this, ViewOrganizationProfile.class);
		startActivity(intent);
		}
    
    public void onChangePasswordClick(View view){	
		Intent intent = new Intent(this, ChangePassword.class);
		startActivity(intent);    	
    }
    
    public void onLogoutClick(View view){    	
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);    	
    }

    public void onCreateGroupClick(View view){	
		Intent intent = new Intent(this, CreateGroup.class);
		startActivity(intent);
		}    
    public void onViewNotificationClick(View view){
    	Intent intent = new Intent(this,ViewNotifications.class);
    	startActivity(intent);
    }
    
    private class LoginSeccessTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> checkLoginParams = new ArrayList<NameValuePair>();
			checkLoginParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			checkLoginParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			checkLoginParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			checkLoginParams.add(new BasicNameValuePair(TAG_LATITUDE, Double.toString(latitude)));		
			checkLoginParams.add(new BasicNameValuePair(TAG_LONGITUDE, Double.toString(longitude)));	
			System.out.println("latitude: " + latitude);
			System.out.println("longitude: " + longitude);
			JSONObject json = jsonParser.getJSONFromUrl(urlCheckLogin,
					checkLoginParams);
			success = json.optInt(TAG_SUCCESS);
			if (success != 1) {
				Intent intent = new Intent(getApplicationContext(),
						PersonLoginSuccess.class);
				startActivity(intent);					
			}
			return null;			
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText
			tvTextwelcome.setText("Welcome " + nickName);	
		}
    }
    private class GCMRegisterTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String msg = "";
            try {
                Bundle data = new Bundle();
                data.putString("my_message", "Hello World");
                data.putString("my_action", "com.google.android.gcm.demo.app.ECHO_NOW");
                String id = Integer.toString(msgId.incrementAndGet());
                gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
                msg = "Sent message";
            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
            }
            return msg;
        }
    }
    
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }/**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }
    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
            	
            	//Notice
            	System.out.println("gcm: " + gcm);
            	
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);                    

                	//Notice
                	System.out.println("new regid: " + regid);
                    
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }
        }.execute(null, null, null);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(OrganizationLoginSuccess.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend() {
    	new UploadGCMRegid().execute(urlUploadGCMRegid);
    }
    
    class UploadGCMRegid extends AsyncTask<String, String, String> {
		protected String doInBackground(String... args) {
			List<NameValuePair> uploadGCMRegidParams = new ArrayList<NameValuePair>();
			uploadGCMRegidParams.add(new BasicNameValuePair(TAG_SESSIONID,	sessionID));
			uploadGCMRegidParams.add(new BasicNameValuePair(TAG_USERID, userID));
			uploadGCMRegidParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			uploadGCMRegidParams.add(new BasicNameValuePair(TAG_UploadGCMRegid,	regid));
			JSONObject json = jsonParser.getJSONFromUrl(urlUploadGCMRegid, uploadGCMRegidParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);		
			return null;			
		}

		@Override
		protected void onPostExecute(String result) {
			if (success == 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
			if (success == 0) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
    }
}
