
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterOrganization extends Activity {
	// Creating JSON Parser object
		JSONParser jsonParser = new JSONParser();
		
		UserFunctions userFunctions = new UserFunctions();
		
		// url to create user profile
		private String urlRegister = UserFunctions.URL_ROOT + "DB_Register.php";
		
		// user profile JSONArray
		JSONArray RegisterArray = null;
		
		// JSON Node names
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";
		private static final String TAG_USERID = "userid";
		private static final String TAG_USERTYPE = "userType";
		private static final String TAG_USERNAME = "userName";
    	private static final String TAG_EMAIL = "email";
    	private static final String TAG_PASSWORD = "password";

		EditText etOrganizationName;
		EditText etEmail;
		EditText etPassword;
		EditText etConfirmPassword;	
		String userType;
		String userName;
		String email;
		String password;
		String confirmPassword; 
		
		int success;
		String message;
		String userid;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_register_organization);		
			userType = "Organization";
			etOrganizationName = (EditText) findViewById(R.id.oname);
			etEmail = (EditText) findViewById(R.id.email);
			etPassword = (EditText) findViewById(R.id.pword);
			etConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
		}
		
		boolean checkEmail(String mail) {
			String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
			return mail.matches(regex);
			}
		
		public void onRegisterClick(View view){
			userName = etOrganizationName.getText().toString();
			email = etEmail.getText().toString();
			password = etPassword.getText().toString();
			confirmPassword = etConfirmPassword.getText().toString(); 
			
			if((!userName.equals(""))&&(!email.equals(""))&&(!password.equals(""))&&(!confirmPassword.equals(""))&&(password.equals(confirmPassword))){
				if (!checkEmail(email)){
					Toast.makeText(getApplicationContext(), "Invalid Email Type",
							Toast.LENGTH_SHORT).show();
				    }else{
				    	new RegisterTask().execute();
				    }
			}else if(userName.equals("")){
				Toast.makeText(getApplicationContext(),
	                    "Organization name field empty", Toast.LENGTH_SHORT).show(); 
			}else if(email.equals("")){
				Toast.makeText(getApplicationContext(),
	                    "Email field empty", Toast.LENGTH_SHORT).show(); 
			}else if(password.equals("")){
				Toast.makeText(getApplicationContext(),
	                    "Password field empty", Toast.LENGTH_SHORT).show(); 
			}else if(confirmPassword.equals("")){
				Toast.makeText(getApplicationContext(),
	                    "Confirm password field empty", Toast.LENGTH_SHORT).show(); 
			}else if((!password.equals(""))&&(!confirmPassword.equals(""))&&(!password.equals(confirmPassword))){
				Toast.makeText(getApplicationContext(),
	                    "Password and confirm password are different", Toast.LENGTH_SHORT).show(); 
			}		
		}
		
		public void onBackLoginClick(View view){
			Intent intent = new Intent(getApplicationContext(), Login.class);
			startActivity(intent);		
		}
		
		private class RegisterTask extends AsyncTask<String, Void, String> {
			
	        @Override
	        protected String doInBackground(String... urls) {
	    		// Building Parameters
	    		List<NameValuePair> registerParams = new ArrayList<NameValuePair>();  			
				registerParams.add(new BasicNameValuePair(TAG_USERTYPE, userType)); 
				registerParams.add(new BasicNameValuePair(TAG_USERNAME, userName));    			
				registerParams.add(new BasicNameValuePair(TAG_EMAIL, email));    			
				registerParams.add(new BasicNameValuePair(TAG_PASSWORD, password)); 

	    		// getting JSON string from URL
	    		JSONObject json = jsonParser.getJSONFromUrl(urlRegister, registerParams);
	    		success = json.optInt(TAG_SUCCESS);
				message = json.optString(TAG_MESSAGE);
				userid = json.optString(TAG_USERID);   		
				return null;
	        }        

			@Override
			protected void onPostExecute(String result) {
			if(success == 1){
				Intent intent = new Intent(getApplicationContext(), CreateOrganizationProfile.class);
				intent.putExtra(TAG_USERID, userid);
				intent.putExtra(TAG_USERTYPE, userType);
				startActivity(intent);					
			}else{
				Toast.makeText(getApplicationContext(),
						message, Toast.LENGTH_SHORT).show(); 					
			}
			}
		}
}
