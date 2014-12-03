package com.example.wanna;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.LocationTracker;
import com.example.wanna.library.UserFunctions;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class PersonLoginSuccess extends Activity {
	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ORGANIZATION = "Organization";
	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_LONGITUDE = "longitude";
  
    TextView tvSessionID;
    TextView tvTextwelcome;
    
	String sessionID;
	String userID;
	String userType;
	String nickName;     
	int success;
	double latitude;
    double longitude;

	LocationTracker location;
    
	UserFunctions userFunctions = new UserFunctions();
	private String urlCheckLogin = UserFunctions.URL_ROOT + "DB_LoginSuccess.php";


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_login_success);    

		location = new LocationTracker(PersonLoginSuccess.this);   

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		nickName = sharedpreferences.getString(TAG_NICKNAME, "");
		if (userType.equals(TAG_ORGANIZATION)) {
			Intent intent = new Intent(getApplicationContext(),
					OrganizationLoginSuccess.class);
			startActivity(intent);
		}
        tvTextwelcome = (TextView) findViewById(R.id.textwelcome);
        tvSessionID = (TextView) findViewById(R.id.textView);
        if(location.canGetLocation()){            
            latitude = location.getLatitude();
            longitude = location.getLongitude();
           }else{
        	   Toast.makeText(getApplicationContext(), "Cannot get location",
						Toast.LENGTH_SHORT).show();
        }
        new LoginSeccessTask().execute();        
    }
    
    public void onCreateEvent(View view){	
		Intent intent = new Intent(this, CreateEvent.class);
		startActivity(intent);
		}
    
    public void onSearchEvent(View view){	
		Intent intent = new Intent(this, SearchEvent.class);
		startActivity(intent);
		}
    
    public void onViewProfileClick(View view){	
		Intent intent = new Intent(this, ViewPersonProfile.class);
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
    
    public void onSearchGroupClick(View view){	
		Intent intent = new Intent(this, SearchGroup.class);
		startActivity(intent);
		}
    
    public void onCreateGroupClick(View view){	
		Intent intent = new Intent(this, CreateGroup.class);
		startActivity(intent);
		}    

    public void onSearchUserClick(View view){	
		Intent intent = new Intent(this, SearchUserName.class);
		startActivity(intent);
		}

    public void onFiltrationUserClick(View view){	
		Intent intent = new Intent(this, SearchUserFiltration.class);
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
			tvSessionID.setText("userID " + userID);			
		}
    }
}