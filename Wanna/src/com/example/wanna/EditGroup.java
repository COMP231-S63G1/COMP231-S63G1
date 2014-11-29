package com.example.wanna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.wanna.EditEvent.CreateNewEvent;
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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class EditGroup extends Activity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	private ProgressDialog pDialog;

	// php file url in the server
	private String urlUpdateGroup = UserFunctions.URL_ROOT + "DB_UpdateGroup.php";
	private String urlUploadImage = UserFunctions.URL_ROOT + "saveImage.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final int SELECT_PICTURE = 1;
	private Spinner groupTypeSpinner;
	private ImageView img;
	EditText etGroupName;
	EditText etGroupDescription;
	
	private static final String TAG_GROUPID = "groupID";

	// session variables
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	String sessionID;
	String userID;
	String groupID;
	String groupPrivacy;
	String groupType;
	String groupName;
	String groupDescription;
	String groupNumber;
	int success;
	String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_group);
		Intent intent = getIntent();
		groupID = intent.getStringExtra(TAG_GROUPID);
		groupType = intent.getStringExtra("groupType");
		groupName = intent.getStringExtra("groupName");
		groupDescription = intent.getStringExtra("groupDescription");

		etGroupName = (EditText) findViewById(R.id.etGroupName);
		etGroupDescription = (EditText) findViewById(R.id.etGroupDescription);
		groupTypeSpinner = (Spinner) findViewById(R.id.groupTypeSpin);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.createGroupArray,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		groupTypeSpinner.setAdapter(adapter);
		int spinnerPosition = adapter.getPosition(groupType);
		groupTypeSpinner.setSelection(spinnerPosition);
		img = (ImageView) findViewById(R.id.imgView);
		// set the value passed from view group detail
		etGroupName.setText(groupName);
		etGroupDescription.setText(groupDescription);
	}
	
	 public void onSaveInformation(View view) {
			new SaveNewInformationTask().execute(urlUpdateGroup);
		}
	 class SaveNewInformationTask extends AsyncTask<String, String, String> {

			/**
			 * Creating event
			 * */

			protected String doInBackground(String... args) {
//				String groupID = intent.getStringExtra(TAG_GroupID);
				String groupName = etGroupName.getText().toString();
				String groupType = groupTypeSpinner.getSelectedItem().toString();
				String groupDescriptionString = etGroupDescription.getText().toString();				
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("groupID",groupID));
				params.add(new BasicNameValuePair("groupName", groupName));
				params.add(new BasicNameValuePair("groupType", groupType));
				params.add(new BasicNameValuePair("groupDescription",
						groupDescriptionString));
				// getting JSON Object
				// Note that create event url accepts POST method
				JSONObject json = jsonParser.getJSONFromUrl(urlUpdateGroup, params);
				success = json.optInt(TAG_SUCCESS);
				message = json.optString(TAG_MESSAGE);				
				return null;
			}
			@Override
			protected void onPostExecute(String result) {
				if (success == 1) {
					Intent intent = new Intent(getApplicationContext(),
							ViewCreatedGroup.class);
					intent.putExtra(TAG_GROUPID, groupID);
					startActivity(intent);
				} 
				if (success != 1) {
					Toast.makeText(getApplicationContext(), message,
							Toast.LENGTH_SHORT).show();
				}
			}

		}

	public void onClickPic(View view) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"),
				SELECT_PICTURE);
	}

	// method when the choose is done and display the photo in the image view
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Bitmap bitmap = getPath(data.getData());
				img.setImageBitmap(bitmap);
			}
		}
	}

	// method to get the bitmap from the gallery according to the uri
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
	/*
	 * public void onSaveInformation(View view){ Intent intent = new
	 * Intent(this, Login_Success.class); startActivity(intent); }
	 */
}
