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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateProfile extends Activity{

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	
	UserFunctions userfunctions = new UserFunctions();
	// url to create user profile
	//private String urlCreateProfile = userfunctions.URL_ROOT + "DB_CreateProfile.php";
	private String urlCreateProfile = "http://192.168.137.1:80/wanna/DB_CreateProfile.php";
	
	// user profile JSONArray
	JSONArray userProfileArray = null;
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	EditText txtUserNickName;
	RadioGroup rgUserGender;
	EditText txtUserAge;
	EditText txtUserDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_form);
		txtUserNickName = (EditText) findViewById(R.id.name);
		rgUserGender = (RadioGroup) findViewById(R.id.userGenderGroup);
		txtUserAge = (EditText) findViewById(R.id.age);
		txtUserDescription = (EditText) findViewById(R.id.description);
	}
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.genderMale:
	            if (checked)
	                // Pirates are the best
	            break;
	        case R.id.genderFemale:
	            if (checked)
	                // Ninjas rule
	            break;
	    }
	}
	
	public void onCreateProfileClick(View view){		
		new DownloadWebpageTask().execute(urlCreateProfile);
	}
	
	public void onCancleCreateProfileClick(View view){
		Intent i = new Intent(getApplicationContext(), Login_Success.class);
		startActivity(i);
	}
	
	private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
        	String userNickName;
    		RadioButton userGenderSelect;
    		String userGender;
    		int userAge;
    		String userDescription;
    		// Building Parameters
    		List<NameValuePair> createProfileParams = new ArrayList<NameValuePair>();
    		try{
    		userNickName = txtUserNickName.getText().toString();
    		userGenderSelect = (RadioButton) findViewById(rgUserGender
                    .getCheckedRadioButtonId());
            userGender = userGenderSelect.getText().toString();
    		userAge = Integer.parseInt(txtUserAge.getText().toString());
    		userDescription = txtUserDescription.getText().toString();
    		if(( !userNickName.equals(""))&&( !userGender.equals(""))&&(userAge >= 18)&&( !userDescription.equals(""))){
    			createProfileParams.add(new BasicNameValuePair("userNickName", userNickName));
    			createProfileParams.add(new BasicNameValuePair("userGender", userGender));
    			createProfileParams.add(new BasicNameValuePair("userAge", Integer.toString(userAge)));
    			createProfileParams.add(new BasicNameValuePair("userDescription", userDescription));
    		}
    		else if(userNickName.equals("")){
    			Toast.makeText(getApplicationContext(),
                        "Name field empty", Toast.LENGTH_SHORT).show();
    		}
    		else if(userAge < 18){
    			Toast.makeText(getApplicationContext(),
                        "Age is less than 18", Toast.LENGTH_SHORT).show();
    		}
    		else if(userDescription.equals("")){
    			Toast.makeText(getApplicationContext(),
                        "Description field empty", Toast.LENGTH_SHORT).show();
    		}
    		}
    		catch(Exception e){
    			Toast.makeText(getApplicationContext(),
                        "invalid profile information", Toast.LENGTH_SHORT).show();
    		}
    		// getting JSON string from URL
    		JSONObject json = jsonParser.getJSONFromUrl(urlCreateProfile, createProfileParams);
    		// check for success tag
    		try {
    			int success = json.getInt(TAG_SUCCESS);

    			if (success == 1) {
    				// successfully created product
    				
    //!!! Should direct to view profile page!!!
    				Intent i = new Intent(getApplicationContext(), Login_Success.class);
    				startActivity(i);
    				
    				// closing this screen
    				finish();
    			} else {
    				// failed to create product
    			}
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
			return null;
        }
    }
}
