package com.example.wanna;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.ListViewAdapter;
import com.example.wanna.library.UserFunctions;

public class CreateEvent extends Activity {
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();
	//php file url in the server 
	private String urlCreateEvent = userFunctions.URL_ROOT
			+ "DB_CreateEvent.php";
	private String urlUploadImage = userFunctions.URL_ROOT+"saveImage.php";
	// JSON Node names
	private static final int SELECT_PICTURE = 1;
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG = "upload";
	private static final String TAG_EVENTNAME = "eventName";
	private static final String TAG_EVENTTYPE = "eventType";
	private static final String TAG_EVENTDATE = "eventDate";
	private static final String TAG_EVENTTIME = "eventTime";
	private static final String TAG_EVENTVENUE = "eventVenue";
	private static final String TAG_EVENTADDRESS = "eventAddress";
	private static final String TAG_PRICERANGE = "eventPriceRange";
	private static final String TAG_DESCRIPTION = "eventDescription";
	
	// tag for check whether the photo is uploaded succeed
	private Spinner eventTypeSpinner;
	private EditText eventName;
	private EditText eventVenue;
	private static TextView eventDate;
	private static TextView eventTime;
	private EditText eventAddress;
	private EditText eventPriceRange;
	private EditText eventDescription;
	private String eventPictureNameStoredIndatabase;
	private ImageView img;
	String mCurrentPhotoPath;

	//session variables 
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	String sessionID;
	String userID;
	String profileID;
	String userType;
	String eventname;
	String eventType;
	String eventDateString;
	String eventTimeString;
	String eventVenueString;
	String eventAddressString;
	String eventPriceRangeString;
	String eventDescriptionString;
	int success;
	String message;
	
	static final int REQUEST_TAKE_PHOTO = 1;
	File photoFile = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		
		eventTypeSpinner = (Spinner) findViewById(R.id.spinnerEventType);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.createEventArray,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		eventTypeSpinner.setAdapter(adapter);
		img = (ImageView) findViewById(R.id.imgView);
		eventName = (EditText) findViewById(R.id.eventName);
		eventVenue = (EditText) findViewById(R.id.eventVenue);
		eventAddress = (EditText) findViewById(R.id.editAddress);
		eventPriceRange = (EditText) findViewById(R.id.eventPrice);
		eventDescription = (EditText) findViewById(R.id.eventDescriptionEditText);
		eventTime = (TextView) findViewById(R.id.eventTimeEditText);
		eventDate = (TextView) findViewById(R.id.eventDate);

	}
	//method for click a button and upload event photo
    public void onClickPicture(View view){
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
	public void onCreateEvent(View view) {

		eventname = eventName.getText().toString();
		eventType = eventTypeSpinner.getSelectedItem().toString();
		eventDateString = eventDate.getText().toString();
		eventTimeString = eventTime.getText().toString();
		eventVenueString = eventVenue.getText().toString();
		eventAddressString = eventAddress.getText().toString();
		eventPriceRangeString = eventPriceRange.getText().toString();
		eventDescriptionString = eventDescription.getText()
				.toString();
		new CreateNewEvent().execute(urlCreateEvent);
	}
	/**
	 * Background Async Task to Create new event
	 * */
	class CreateNewEvent extends AsyncTask<String, String, String> {
		protected String doInBackground(String... args) {
//			try {
//				Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
//				sendPhoto(bitmap);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			// Building Parameters
			List<NameValuePair> createEventParams = new ArrayList<NameValuePair>();
			createEventParams.add(new BasicNameValuePair(TAG_SESSIONID,	sessionID));
			createEventParams.add(new BasicNameValuePair(TAG_USERID, userID));
			createEventParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTNAME, eventname));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTTYPE, eventType));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTDATE, eventDateString));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTTIME, eventTimeString));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTVENUE, eventVenueString));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTADDRESS,
					eventAddressString));
			createEventParams.add(new BasicNameValuePair(TAG_PRICERANGE,
					eventPriceRangeString));
			createEventParams.add(new BasicNameValuePair(TAG_DESCRIPTION,
					eventDescriptionString));

			// getting JSON Object
			// Note that create event url accepts POST method
			JSONObject json = jsonParser.getJSONFromUrl(urlCreateEvent, createEventParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);

			if (success == 1) {
				// successfully created product					
				Intent intent = new Intent(getApplicationContext(),
						ViewPersonProfile.class);
				startActivity(intent);
			} else {
				// failed to create product
			}			
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			if (success == 0) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			eventTime.setText(hourOfDay + ":" + minute);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
//			Calendar my = Calendar.getInstance();
//			my.set(year, month+1, day);
//			if(my.compareTo(Calendar.getInstance())==1)
//				{
//				  eventDate.setText("Please set proper date");
//				}
//			else{
				eventDate.setText(year + "-" + (month+1) + "-" + day);
//			}
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	private void sendPhoto(Bitmap bitmap) throws Exception {
		new UploadTask().execute(bitmap);
	}

	private class UploadTask extends AsyncTask<Bitmap, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CreateEvent.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected Void doInBackground(Bitmap... bitmaps) {
			if (bitmaps[0] == null)
				return null;
			setProgress(0);

			Bitmap bitmap = bitmaps[0];
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); 
			InputStream in = new ByteArrayInputStream(stream.toByteArray()); 
			DefaultHttpClient httpclient = new DefaultHttpClient();
			try {
				HttpPost httppost = new HttpPost(urlUploadImage); 
				MultipartEntity reqEntity = new MultipartEntity();
				eventPictureNameStoredIndatabase=System.currentTimeMillis() + ".jpg";
				reqEntity.addPart("myFile",
						eventPictureNameStoredIndatabase, in);
				httppost.setEntity(reqEntity);

				Log.i(TAG, "request " + httppost.getRequestLine());
				HttpResponse response = null;
				try {
					response = httpclient.execute(httppost);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (response != null)
						Log.i(TAG, "response "
								+ response.getStatusLine().toString());
				} finally {

				}
			} finally {

			}

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;
		}
	}
}
