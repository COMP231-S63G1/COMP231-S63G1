package com.example.wanna;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wanna.CreatePersonProfile.uploadToServer;
import com.example.wanna.library.AlbumStorageDirFactory;
import com.example.wanna.library.BaseAlbumDirFactory;
import com.example.wanna.library.FroyoAlbumDirFactory;
import com.example.wanna.library.ImageFilePath;
import com.example.wanna.library.JSONParser;
import com.example.wanna.library.ListViewAdapter;
import com.example.wanna.library.UserFunctions;

public class CreateEvent extends Activity {
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();
	// php file url in the server
	private String urlCreateEvent = userFunctions.URL_ROOT
			+ "DB_CreateEvent.php";
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
	private static final String TAG_PERSON = "Person";
	private static final String TAG_ORGANIZATION = "Organization";

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

	// Select images from gallery or take images from camera
	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_TAKE_PHOTO = 1;
	static final int SELECT_FILE = 1;
	private static int RESULT_LOAD_IMG = 1;
	String mCurrentPhotoPath;
	String imgDecodableString;
	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
	private String ChangePicture = "false";
	private String pictureResource;
	ImageButton imbUserPicture;

	// Upload images
	String ba1;
	public static String urlUploadImage = UserFunctions.URL_ROOT
			+ "DB_UploadImages.php";
	String ServerPictureName;
	private static final String TAG_BOOLIMAGECHANGE = "BoolImageChange";
	private static final String TAG_PICTUREURL = "pictureURL";

	// session variables
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
		eventName = (EditText) findViewById(R.id.eventName);
		eventVenue = (EditText) findViewById(R.id.eventVenue);
		eventAddress = (EditText) findViewById(R.id.editAddress);
		eventPriceRange = (EditText) findViewById(R.id.eventPrice);
		eventDescription = (EditText) findViewById(R.id.eventDescriptionEditText);
		eventTime = (TextView) findViewById(R.id.eventTimeEditText);
		eventDate = (TextView) findViewById(R.id.eventDate);

		imbUserPicture = (ImageButton) findViewById(R.id.userPicture);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}

	}

	public void onCreateEvent(View view) {

		eventname = eventName.getText().toString();
		eventType = eventTypeSpinner.getSelectedItem().toString();
		eventDateString = eventDate.getText().toString();
		eventTimeString = eventTime.getText().toString();
		eventVenueString = eventVenue.getText().toString();
		eventAddressString = eventAddress.getText().toString();
		eventPriceRangeString = eventPriceRange.getText().toString();
		eventDescriptionString = eventDescription.getText().toString();
		ServerPictureName = String.valueOf(System.currentTimeMillis());
		if (eventname != null && eventType != null && eventDateString != null
				&& eventTimeString != null && eventVenueString != null
				&& eventAddressString != null && eventPriceRangeString != null
				&& eventDescriptionString != null) {
			if (ChangePicture.equals("true")) {
				upload();
			}
			new CreateNewEvent().execute(urlCreateEvent);
		}
		else{
			Toast.makeText(getApplicationContext(), "All fields are requested",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Background Async Task to Create new event
	 * */
	class CreateNewEvent extends AsyncTask<String, String, String> {
		protected String doInBackground(String... args) {

			List<NameValuePair> createEventParams = new ArrayList<NameValuePair>();
			createEventParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			createEventParams.add(new BasicNameValuePair(TAG_USERID, userID));
			createEventParams
					.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTNAME,
					eventname));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTTYPE,
					eventType));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTDATE,
					eventDateString));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTTIME,
					eventTimeString));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTVENUE,
					eventVenueString));
			createEventParams.add(new BasicNameValuePair(TAG_EVENTADDRESS,
					eventAddressString));
			createEventParams.add(new BasicNameValuePair(TAG_PRICERANGE,
					eventPriceRangeString));
			createEventParams.add(new BasicNameValuePair(TAG_DESCRIPTION,
					eventDescriptionString));
			createEventParams.add(new BasicNameValuePair(TAG_BOOLIMAGECHANGE,
					ChangePicture));
			createEventParams.add(new BasicNameValuePair(TAG_PICTUREURL,
					ServerPictureName));

			// getting JSON Object
			// Note that create event url accepts POST method
			JSONObject json = jsonParser.getJSONFromUrl(urlCreateEvent,
					createEventParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);

			if (success == 1) {
				// successfully created product
				if (userType.equals(TAG_PERSON)) {
					Intent intent = new Intent(getApplicationContext(),
							ViewPersonProfile.class);
					startActivity(intent);
				} else if (userType.equals(TAG_ORGANIZATION)) {
					Intent intent = new Intent(getApplicationContext(),
							ViewOrganizationProfile.class);
					startActivity(intent);
				}
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
			eventDate.setText(year + "-" + (month + 1) + "-" + day);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public void onAddPictureClick(View view) {
		selectImage();
		// dispatchTakePictureIntent();
	}

	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvent.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					pictureResource = "Camera";
					dispatchTakePictureIntent();
				} else if (items[item].equals("Choose from Library")) {
					pictureResource = "Gallery";
					Intent galleryIntent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					// Start the Intent
					startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = setUpPhotoFile();
				mCurrentPhotoPath = photoFile.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
			} catch (IOException e) {
				e.printStackTrace();
				photoFile = null;
				mCurrentPhotoPath = null;
			}
			startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK
					&& pictureResource.equals("Camera")) {
				handleBigCameraPhoto();
				ChangePicture = "true";
			}
			// When an Image is picked
			else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
					&& pictureResource.equals("Gallery") && null != data) {
				// Get the Image from data

				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				// Get the cursor
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				// Move to first row
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				imgDecodableString = cursor.getString(columnIndex);
				cursor.close();
				mCurrentPhotoPath = ImageFilePath.getPath(
						getApplicationContext(), selectedImage);
				handleBigCameraPhoto();
				ChangePicture = "true";

			} else {
				Toast.makeText(this, "You haven't picked Image",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
					.show();
		}
	}

	private void handleBigCameraPhoto() {

		if (mCurrentPhotoPath != null) {
			setPic();
			galleryAddPic();
			// mCurrentPhotoPath = null;
		}

	}

	private File setUpPhotoFile() throws IOException {

		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();

		return f;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
				albumF);
		return imageF;
	}

	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			storageDir = mAlbumStorageDirFactory
					.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}

		} else {
			Log.v(getString(R.string.app_name),
					"External storage is not mounted READ/WRITE.");
		}

		return storageDir;
	}

	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name);
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	private void setPic() {
		// Get the dimensions of the View
		int targetW = imbUserPicture.getWidth();
		int targetH = imbUserPicture.getHeight();

		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		imbUserPicture.setImageBitmap(bitmap);
	}

	private void upload() {
		// Image location URL
		Log.e("path", "----------------" + mCurrentPhotoPath);

		// Image
		final int THUMBSIZE = 64;
		Bitmap bm = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(mCurrentPhotoPath), THUMBSIZE,
				THUMBSIZE);
		// Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
		byte[] ba = bao.toByteArray();
		ba1 = Base64.encodeToString(ba, 0);

		Log.e("base64", "-----" + ba1);

		// Upload image to server
		new uploadToServer().execute();

	}

	public class uploadToServer extends AsyncTask<Void, Void, String> {

		private ProgressDialog pd = new ProgressDialog(CreateEvent.this);

		protected void onPreExecute() {
			super.onPreExecute();
			pd.setMessage("Wait image uploading!");
			pd.show();
		}

		@Override
		protected String doInBackground(Void... params) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("base64", ba1));
			nameValuePairs.add(new BasicNameValuePair("ImageName",
					ServerPictureName + ".jpg"));
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(urlUploadImage);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				String st = EntityUtils.toString(response.getEntity());
				Log.v("log_tag", "In the try Loop" + st);

			} catch (Exception e) {
				Log.v("log_tag", "Error in http connection " + e.toString());
			}
			return "Success";

		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pd.hide();
			pd.dismiss();
			mCurrentPhotoPath = null;
		}
	}
}
