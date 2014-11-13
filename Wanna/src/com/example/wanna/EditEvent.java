package com.example.wanna;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


public class EditEvent extends Activity {

	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	private ProgressDialog pDialog;
	
	//php file url in the server 
	private String urlEditEvent = userFunctions.URL_ROOT
			+ "DB_UpdateEvent.php";
	private String urlUploadImage = userFunctions.URL_ROOT+"saveImage.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final int SELECT_PICTURE = 1;
	// tag for check whether the photo is uploaded succeed
	private static final String TAG = "upload";
	private Spinner eventTypeSpinner;
	private EditText etEventName;
	private EditText etEventVenue;
	private static TextView tvEventDate;
	private static TextView tvEventTime;
	private EditText etEventAddress;
	private EditText etEventPriceRange;
	private EditText etEventDescription;
	private String eventPictureNameStoredIndatabase;
	private ImageView img;
	String mCurrentPhotoPath;
	
	//session variables 
		public static final String MyPREFERENCES = "Wanna";
		SharedPreferences sharedpreferences;
		String sessionID;
		String userID;
		String eventID;

	static final int REQUEST_TAKE_PHOTO = 1;
	File photoFile = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_event);
		Intent intent = getIntent();
		eventID=intent.getStringExtra("eventID");
		String eventType = intent.getStringExtra("eventType");
		String eventName = intent.getStringExtra("eventName");
		String eventDate = intent.getStringExtra("eventDate");
		String eventTime = intent.getStringExtra("eventTime");
		String eventVenue = intent.getStringExtra("eventVenue");
		String eventLocation = intent.getStringExtra("eventLocation");
		String eventPriceRange = intent.getStringExtra("eventPriceRange");
		String eventDescription = intent.getStringExtra("eventDescription");
		eventTypeSpinner = (Spinner) findViewById(R.id.spinnerEventType);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.createEventArray,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		eventTypeSpinner.setAdapter(adapter);
		int spinnerPosition = adapter.getPosition(eventType);
		eventTypeSpinner.setSelection(spinnerPosition);
		img = (ImageView) findViewById(R.id.imgView);
		etEventName = (EditText) findViewById(R.id.eventName);
		etEventVenue = (EditText) findViewById(R.id.eventVenue);
		etEventAddress = (EditText) findViewById(R.id.editAddress);
		etEventPriceRange = (EditText) findViewById(R.id.eventPrice);
		etEventDescription = (EditText) findViewById(R.id.eventDescriptionEditText);
		tvEventTime = (TextView) findViewById(R.id.eventTimeEditText);
		tvEventDate = (TextView) findViewById(R.id.eventDate);
		//set the value passed from view event detail
		etEventName.setText(eventName);
		etEventVenue.setText(eventVenue);
		etEventAddress.setText(eventLocation);
		etEventPriceRange.setText(eventPriceRange);
		etEventDescription .setText (eventDescription);
		tvEventTime.setText(eventTime);
		tvEventDate.setText(eventDate);
		
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
			tvEventTime.setText(hourOfDay + ":" + minute);
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
			tvEventDate.setText(year + "-" + (month+1) + "-" + day);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}
	 public void onClickPicture(View view){
	    	Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(
					Intent.createChooser(intent, "Select Picture"),
					SELECT_PICTURE);
	    }
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == RESULT_OK) {
				if (requestCode == SELECT_PICTURE) {
					Bitmap bitmap = getPath(data.getData());
		    		img.setImageBitmap(bitmap);
				}
			}
		}
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
			new CreateNewEvent().execute(urlEditEvent);
		}
	 class CreateNewEvent extends AsyncTask<String, String, String> {

			/**
			 * Creating event
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(EditEvent.this);
				pDialog.setTitle("Contacting Servers");
				pDialog.setMessage("Loading ...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			protected String doInBackground(String... args) {
				String eventname = etEventName.getText().toString();
				String eventType = eventTypeSpinner.getSelectedItem().toString();
				String eventDateString = tvEventDate.getText().toString();
				String eventTimeString = tvEventTime.getText().toString();
				String eventVenueString = etEventVenue.getText().toString();
				String eventAddressString = etEventAddress.getText().toString();
				String eventPriceRangeString = etEventPriceRange.getText().toString();
				String eventDescriptionString = etEventDescription.getText().toString();
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("eventID",eventID));
				params.add(new BasicNameValuePair("eventName", eventname));
				params.add(new BasicNameValuePair("eventType", eventType));
				params.add(new BasicNameValuePair("eventDate", eventDateString));
				params.add(new BasicNameValuePair("eventTime", eventTimeString));
				params.add(new BasicNameValuePair("eventVenue", eventVenueString));
				params.add(new BasicNameValuePair("eventAddress",
						eventAddressString));
				params.add(new BasicNameValuePair("eventPriceRange",
						eventPriceRangeString));
				params.add(new BasicNameValuePair("eventDescription",
						eventDescriptionString));
//				 params.add(new
//				 BasicNameValuePair("eventImageURI",eventPictureNameStoredIndatabase));
				 System.out.println(params.toString());
				// getting JSON Object
				// Note that create event url accepts POST method
				JSONObject json = jsonParser.getJSONFromUrl(urlEditEvent, params);
				// check log cat fro response
				Log.d("Create Response", json.toString());
				// check for success tag
				try {
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// successfully created product
						
						Intent intent = new Intent(getApplicationContext(),
								ViewEventDetail.class);
						startActivity(intent);

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
