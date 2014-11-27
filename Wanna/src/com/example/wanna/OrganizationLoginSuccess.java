package com.example.wanna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrganizationLoginSuccess extends Activity {
	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_SUCCESS = "success";
  
    TextView tvSessionID;
    TextView tvTextwelcome;
    
	String sessionID;
	String userID;
	String userType;
	String nickName;     
	int success;
    
	UserFunctions userFunctions = new UserFunctions();
	private String urlCheckLogin = UserFunctions.URL_ROOT + "DB_LoginSuccess.php";


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_login_success);        

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		nickName = sharedpreferences.getString(TAG_NICKNAME, "");
		
        tvTextwelcome = (TextView) findViewById(R.id.textwelcome);
        tvSessionID = (TextView) findViewById(R.id.textView);
        
        new LoginSeccessTask().execute();        
    }
    
    public void onCreateEvent(View view){	
		Intent intent = new Intent(this, CreateEvent.class);
		startActivity(intent);
		}

    
    public void onViewEventClick(View view){
		Intent intent = new Intent(this, ViewCreatedEvent.class);
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

    public void onCreateGroupClick(View view){	
		Intent intent = new Intent(this, CreateGroup.class);
		startActivity(intent);
		}    
    
    private class LoginSeccessTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> checkLoginParams = new ArrayList<NameValuePair>();
			checkLoginParams
					.add(new BasicNameValuePair(TAG_SESSIONID, sessionID));
			checkLoginParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
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
