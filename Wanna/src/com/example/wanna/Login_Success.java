package com.example.wanna;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;
import com.example.wanna.library.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Login_Success extends Activity {
	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	private ProgressDialog pDialog;
	
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	
	//For test purpose!!!
//    EditText etEeventID;    
    TextView tvSessionID;
    TextView tvTextwelcome;
    
	String sessionID;
	String userID;
	String nickName;     
	int success;
    
	UserFunctions userFunctions = new UserFunctions();
	private String urlCheckLogin = userFunctions.URL_ROOT + "DB_LoginSuccess.php";

	private static final String TAG_SUCCESS = "success";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);        

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        
        tvTextwelcome = (TextView) findViewById(R.id.textwelcome);
        tvSessionID = (TextView) findViewById(R.id.textView);
        
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
    
    public void onViewEventClick(View view){
		Intent intent = new Intent(this, ViewEventDetail.class);
		startActivity(intent);    	
    }
    
    public void onViewProfileClick(View view){	
		Intent intent = new Intent(this, ViewProfile.class);
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
			sessionID = sharedpreferences.getString("sessionID", "");
			System.out.println(sessionID);
			userID = sharedpreferences.getString("userID", "");
			nickName = sharedpreferences.getString("nickName", "");
			List<NameValuePair> checkLoginParams = new ArrayList<NameValuePair>();
			checkLoginParams
					.add(new BasicNameValuePair("sessionID", sessionID));
			checkLoginParams.add(new BasicNameValuePair("userID",
					userID));
			JSONObject json = jsonParser.getJSONFromUrl(urlCheckLogin,
					checkLoginParams);
			success = json.optInt(TAG_SUCCESS);
			if (success != 1) {
				Intent intent = new Intent(getApplicationContext(),
						Login_Success.class);
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