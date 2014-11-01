package com.example.wanna;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
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
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.wanna.library.JSONParser;

public class CreateEvent extends Activity {

	JSONParser jsonParser = new JSONParser();
	// url to create new event
	// private static String url_create_product =
	// "http://10.0.2.2/wanna/create_event.php";
	private static String urlCreateEvent = "http://192.168.137.1:80/wanna/DB_CreateEvent.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final int SELECT_PICTURE = 1;
	private Spinner eventTypeSpinner;
	private EditText eventName;
	private EditText eventVenue;
	private static TextView eventDate;
	private static TextView eventTime;
	private EditText eventAddress;
	private EditText eventPriceRange;
	private EditText eventDescription;
	private String selectedImagePath;
	private ImageView img;

	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long arg3) {

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		eventTypeSpinner = (Spinner) findViewById(R.id.spinnerEventType);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.createEventArray,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		eventTypeSpinner.setAdapter(adapter);
		eventTypeSpinner.setOnItemSelectedListener(itemSelectedListener);
		img = (ImageView) findViewById(R.id.imgView);

		((Button) findViewById(R.id.btnSelectImage))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(
								Intent.createChooser(intent, "Select Picture"),
								SELECT_PICTURE);
					}
				});
		eventName = (EditText) findViewById(R.id.eventName);
		eventVenue = (EditText) findViewById(R.id.eventVenue);
		eventAddress = (EditText) findViewById(R.id.editAddress);
		eventPriceRange = (EditText) findViewById(R.id.eventPrice);
		eventDescription = (EditText) findViewById(R.id.eventDescriptionEditText);
		eventTime = (TextView) findViewById(R.id.eventTimeEditText);
		eventDate = (TextView) findViewById(R.id.eventDate);

	}

	public void onCreateEvent(View view) {
		new CreateNewEvent().execute(urlCreateEvent);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				System.out.println("Image Path : " + selectedImagePath);
				img.setImageURI(selectedImageUri);
			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewEvent extends AsyncTask<String, String, String> {

		/**
		 * Creating event
		 * */
		protected String doInBackground(String... args) {
			String eventname = eventName.getText().toString();
			String eventType = eventTypeSpinner.getSelectedItem().toString();
			String eventDateString = eventDate.getText().toString();
			String eventTimeString = eventTime.getText().toString();
			String eventVenueString = eventVenue.getText().toString();
			String eventAddressString = eventAddress.getText().toString();
			String eventPriceRangeString = eventPriceRange.getText().toString();
			String eventDescriptionString = eventDescription.getText()
					.toString();
			System.out.println(eventType);
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
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
			System.out.println(params.toString());
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.getJSONFromUrl(urlCreateEvent, params);
			System.out.println(json.toString());
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Intent i = new Intent(getApplicationContext(),
							CreateProfile.class);
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
			 eventTime.setText(hourOfDay+":"+minute);
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
			eventDate.setText(year+"-"+month+"-"+day);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}
}
