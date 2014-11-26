package com.example.wanna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateGroup extends Activity {
	Spinner groupTypeSpinner;
	RadioButton rbGroupPrivacySelect;
	RadioGroup rgGroupPrivacy;
	ImageView img;
	EditText etGroupName;
	EditText etGroupDescription;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	//php file url in the server 
	private String urlCreateGroup = userFunctions.URL_ROOT
			+ "DB_CreateGroup.php";
	
	private static final int SELECT_PICTURE = 1;
	private static final String TAG_SUCCESS = "success";	
	private static final String TAG_MESSAGE = "message";

	//session variables 
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	int success;
	String message;
	String sessionID;
	String userID;
	String groupPrivacy;
	String groupType;
	String groupName;
	String groupDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_create_group);	
		rgGroupPrivacy = (RadioGroup) findViewById(R.id.groupPrivacy);
		etGroupName = (EditText) findViewById(R.id.etGroupName);
		etGroupDescription = (EditText) findViewById(R.id.etGroupDescription);
		groupTypeSpinner = (Spinner) findViewById(R.id.groupTypeSpin);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.createGroupArray,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		groupTypeSpinner.setAdapter(adapter);
		img = (ImageView) findViewById(R.id.imgView);
		
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString("sessionID", "");
		userID = sharedpreferences.getString("userID", "");
	}
	
	public void onCreateGroupClick(View view){
		groupType = groupTypeSpinner.getSelectedItem().toString();
		rbGroupPrivacySelect = (RadioButton) findViewById(rgGroupPrivacy
				.getCheckedRadioButtonId());
		groupPrivacy = rbGroupPrivacySelect.getText().toString();
		groupName = etGroupName.getText().toString();
		groupDescription = etGroupDescription.getText().toString();
		if ((!groupName.equals("")) && (!groupDescription.equals(""))) {
			new CreateGroupTask().execute(urlCreateGroup);
		} else if (groupName.equals("")) {
			Toast.makeText(getApplicationContext(), "Name field empty",
					Toast.LENGTH_SHORT).show();
			}else if(groupDescription.equals("")){
				Toast.makeText(getApplicationContext(), "Description field empty",
						Toast.LENGTH_SHORT).show();				
			}
	}
	
	public void onCancelClick(View view){
		Intent intent = new Intent(getApplicationContext(), PersonLoginSuccess.class);
		startActivity(intent);		
	}
	
	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.privacyPublic:
			if (checked)
				// Pirates are the best
				break;
		case R.id.privacyPrivate:
			if (checked)
				// Ninjas rule
				break;
		}
	}
	
	private class CreateGroupTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			List<NameValuePair> createGroupParams = new ArrayList<NameValuePair>();
			createGroupParams.add(new BasicNameValuePair("sessionID", sessionID));
			createGroupParams.add(new BasicNameValuePair("userID", userID));
			createGroupParams.add(new BasicNameValuePair("groupType", groupType));
			createGroupParams.add(new BasicNameValuePair("groupPrivacy", groupPrivacy));
			createGroupParams.add(new BasicNameValuePair("groupName", groupName));
			createGroupParams.add(new BasicNameValuePair("groupDescription", groupDescription));
			
			JSONObject json = jsonParser.getJSONFromUrl(urlCreateGroup,
					createGroupParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			return null;			
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (success == 1) {
				// successfully created profile
				Intent intent = new Intent(getApplicationContext(),
						PersonLoginSuccess.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	 public void onClickPic(View view){
	    	Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(
					Intent.createChooser(intent, "Select Picture"),
					SELECT_PICTURE);
	    }
	    //method when the choose is done and display the photo in the image view 
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == RESULT_OK) {
				if (requestCode == SELECT_PICTURE) {
					Bitmap bitmap = getPath(data.getData());
		    		img.setImageBitmap(bitmap);
				}
			}
		}
	    
	    //method to get the bitmap from the gallery according to the uri
	    private Bitmap getPath(Uri uri) {
			 
			String[] projection = { MediaStore.Images.Media.DATA };
			@SuppressWarnings("deprecation")
			Cursor cursor = managedQuery(uri, projection, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String filePath = cursor.getString(column_index);
			cursor.close();
			// Convert file path into bitmap image using below line.
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			
			return bitmap;
		}
	    
	    public void onClickButtonCancel(View view){	
			Intent intent = new Intent(this, PersonLoginSuccess.class);
			startActivity(intent);
			}
}
