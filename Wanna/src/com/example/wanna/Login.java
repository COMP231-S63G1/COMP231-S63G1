package com.example.wanna;

//import com.parse.ParseAnalytics;
//import com.parse.ParseObject;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// ParseAnalytics.trackAppOpenedInBackground(getIntent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

	public void loginButton_Onclick(View view) {
		EditText userName = (EditText) findViewById(R.id.username);
		String userNameText = userName.getText().toString();
		EditText password = (EditText) findViewById(R.id.password);
		String passwordText = password.getText().toString();
		TextView loginNotification = (TextView) findViewById(R.id.loginNotification);
		TextView loginUserNameNotification = (TextView) findViewById(R.id.loginUserNameNotification);
		TextView loginPasswordNotification = (TextView) findViewById(R.id.loginPasswordNotification);
		loginNotification.setText("");
		loginUserNameNotification.setText("");
		loginPasswordNotification.setText("");
		if ((!userNameText.matches("")) && (!passwordText.matches(""))) {
			loginNotification.setText("Right.");
		}
		else{
			if (userNameText.matches("")) {
				loginUserNameNotification.setText("Please enter your user name.");
			}
			if (passwordText.matches("")) {
				loginPasswordNotification.setText("Please enter your password.");
			}
		}
		// ParseObject testObject = new ParseObject("User");
		// testObject.put("username", firstName);
		// testObject.put("password", password);
		// testObject.saveInBackground();

		// Intent intent = new Intent(this, MainActivity.class);
		// EditText editText = (EditText) findViewById(R.id.edit_message);
		// String message = editText.getText().toString();
		// intent.putExtra(EXTRA_MESSAGE, firstName);
		// startActivity(intent);
	}
}
