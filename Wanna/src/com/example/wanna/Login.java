package com.example.wanna;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;

import java.util.ArrayList;
import java.util.List;

public class Login extends Activity {
	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	public static final String MyPREFERENCES = "Wanna";

	private ProgressDialog pDialog;
	EditText etEmail;
	EditText etPassword;

	String loginEmail;
	String loginPassword;
	int success;
	String message;

	SharedPreferences sharedpreferences;

	UserFunctions userFunctions = new UserFunctions();
	private String urlLogin = userFunctions.URL_ROOT + "DB_Login.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_MESSAGE = "message";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

		etEmail = (EditText) findViewById(R.id.email);
		etPassword = (EditText) findViewById(R.id.pword);
	}

	public void onLoginClick(View view) {
		loginEmail = etEmail.getText().toString();
		loginPassword = etPassword.getText().toString();
		if ((!loginEmail.equals("")) && (!loginPassword.equals(""))) {
			new LoginTask().execute();
		} else if ((!loginEmail.equals(""))) {
			Toast.makeText(getApplicationContext(), "Password field empty",
					Toast.LENGTH_SHORT).show();
		} else if ((!loginPassword.equals(""))) {
			Toast.makeText(getApplicationContext(), "Email field empty",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(),
					"Email and Password field are empty", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void onRegisterClick(View view) {
		Intent intent = new Intent(getApplicationContext(), Register.class);
		startActivity(intent);
	}

	public void onForgetPasswordClick(View view) {
		Intent intent = new Intent(getApplicationContext(), PasswordReset.class);
		startActivity(intent);
	}

	private class LoginTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Logging in ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			loginEmail = etEmail.getText().toString();
			loginPassword = etPassword.getText().toString();
			// loginEmail = "gulang15@gmail.com";
			// loginPassword = "123";
			List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
			loginParams.add(new BasicNameValuePair("loginEmail", loginEmail));
			loginParams.add(new BasicNameValuePair("loginPassword",
					loginPassword));
			JSONObject json = jsonParser.getJSONFromUrl(urlLogin, loginParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				Editor editor = sharedpreferences.edit();
				editor.putString("sessionID", json.optString(TAG_SESSIONID));
				editor.putString("userID", json.optString(TAG_USERID));
				editor.putString("nickName", json.optString(TAG_NICKNAME));
				editor.commit();
				Intent intent = new Intent(getApplicationContext(),
						Login_Success.class);
				startActivity(intent);
			} else {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
