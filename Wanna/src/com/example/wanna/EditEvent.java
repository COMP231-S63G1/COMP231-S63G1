package com.example.wanna;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.wanna.EditProfile.uploadToServer;
import com.example.wanna.library.AlbumStorageDirFactory;
import com.example.wanna.library.BaseAlbumDirFactory;
import com.example.wanna.library.FroyoAlbumDirFactory;
import com.example.wanna.library.ImageFilePath;
import com.example.wanna.library.ImageLoader;
import com.example.wanna.library.JSONParser;
import com.example.wanna.library.UserFunctions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class EditEvent extends Activity {

	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	private ProgressDialog pDialog;

	// php file url in the server
	private String urlEditEvent = UserFunctions.URL_ROOT + "DB_UpdateEvent.php";
	private String urlSendNotification = UserFunctions.URL_ROOT
			+ "DB_SendMultipleNotification.php";

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
	String userPictureURL;

	// Upload images
	String ba1;
	public static String urlUploadImage = UserFunctions.URL_ROOT
			+ "DB_UploadImages.php";
	String ServerPictureName;
	private static final String TAG_BOOLIMAGECHANGE = "BoolImageChange";
	private static final String TAG_PICTUREURL = "pictureURL";

	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_PERSON = "Person";
	private static final String TAG_ORGANIZATION = "Organization";
	private static final String TAG_SENDERTYPE = "senderType";
	private static final String TAG_SENDERID = "senderID";
	private static final String TAG_RECEIVERTYPE = "receiverType";
	private static final String TAG_RECEIVERID = "receiverID";
	private static final String TAG_RECEIVERUSERID = "receiverUserID";
	private static final String TAG_ACCEPTALBE = "acceptable";
	private static final String TAG_NOTIFICATIONMASSAGE = "notificationMessage";
	private static final String TAG_SENDTIME = "sendTime";
	private static final int SELECT_PICTURE = 1;
	private Spinner eventTypeSpinner;
	private EditText etEventName;
	private EditText etEventVenue;
	private static TextView tvEventDate;
	private static TextView tvEventTime;
	private EditText etEventAddress;
	private EditText etEventPriceRange;
	private EditText etEventDescription;

	// session variables
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;
	String sessionID;
	String userID;
	String userType;
	String eventID;
	int success;
	String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_event);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		Intent intent = getIntent();
		eventID = intent.getStringExtra("eventID");
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
		etEventName = (EditText) findViewById(R.id.eventName);
		etEventVenue = (EditText) findViewById(R.id.eventVenue);
		etEventAddress = (EditText) findViewById(R.id.editAddress);
		etEventPriceRange = (EditText) findViewById(R.id.eventPrice);
		etEventDescription = (EditText) findViewById(R.id.eventDescriptionEditText);
		tvEventTime = (TextView) findViewById(R.id.eventTimeEditText);
		tvEventDate = (TextView) findViewById(R.id.eventDate);
		// set the value passed from view event detail
		etEventName.setText(eventName);
		etEventVenue.setText(eventVenue);
		etEventAddress.setText(eventLocation);
		etEventPriceRange.setText(eventPriceRange);
		etEventDescription.setText(eventDescription);
		tvEventTime.setText(eventTime);
		tvEventDate.setText(eventDate);

		userPictureURL = intent.getStringExtra(TAG_PICTUREURL);
		
		// Loader image - will be shown before loading image
        int loader = R.drawable.loader;
		imbUserPicture = (ImageButton) findViewById(R.id.userPicture);
		ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView 
        imgLoader.DisplayImage(userPictureURL, loader, imbUserPicture);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
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
			tvEventDate.setText(year + "-" + (month + 1) + "-" + day);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public void onCreateEvent(View view) {
        ServerPictureName = String.valueOf(System.currentTimeMillis());
        if(ChangePicture.equals("true")){
    		upload();        	
        }
		new UpdateEventTask().execute(urlEditEvent);
	}

	private class UpdateEventTask extends AsyncTask<String, String, String> {

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
			String eventPriceRangeString = etEventPriceRange.getText()
					.toString();
			String eventDescriptionString = etEventDescription.getText()
					.toString();
			// Building Parameters
			List<NameValuePair> editEventParams = new ArrayList<NameValuePair>();
			editEventParams.add(new BasicNameValuePair("eventID", eventID));
			editEventParams.add(new BasicNameValuePair("eventName", eventname));
			editEventParams.add(new BasicNameValuePair("eventType", eventType));
			editEventParams.add(new BasicNameValuePair("eventDate", eventDateString));
			editEventParams.add(new BasicNameValuePair("eventTime", eventTimeString));
			editEventParams.add(new BasicNameValuePair("eventVenue", eventVenueString));
			editEventParams.add(new BasicNameValuePair("eventAddress",
					eventAddressString));
			editEventParams.add(new BasicNameValuePair("eventPriceRange",
					eventPriceRangeString));
			editEventParams.add(new BasicNameValuePair("eventDescription",
					eventDescriptionString));
			editEventParams.add(new BasicNameValuePair(TAG_BOOLIMAGECHANGE,
					ChangePicture));
			editEventParams.add(new BasicNameValuePair(TAG_PICTUREURL,
					ServerPictureName));
			// getting JSON Object
			// Note that create event url accepts POST method
			JSONObject json = jsonParser.getJSONFromUrl(urlEditEvent, editEventParams);
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
			if (success != 1) {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			} else {
				new SendNotificationTask().execute();
			}
		}

	}

	private class SendNotificationTask extends AsyncTask<String, Void, String> {
		int success;
		String message;
		String senderType = "Event";
		String senderID = eventID;
		String receiverType = "User";
		String acceptable = "0";
		String notificationMessage = "The event you have joined has been changed";
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String sendTime = sdf.format(c.getTime());

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... urls) {
			// Building Parameters
			System.out.println(sendTime);
			List<NameValuePair> SendNotificationParams = new ArrayList<NameValuePair>();
			SendNotificationParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			SendNotificationParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			SendNotificationParams.add(new BasicNameValuePair(TAG_USERTYPE,
					userType));

			SendNotificationParams.add(new BasicNameValuePair(TAG_SENDERTYPE,
					senderType));
			SendNotificationParams.add(new BasicNameValuePair(TAG_SENDERID,
					senderID));
			SendNotificationParams.add(new BasicNameValuePair(TAG_RECEIVERTYPE,
					receiverType));
			SendNotificationParams.add(new BasicNameValuePair(TAG_ACCEPTALBE,
					acceptable));
			SendNotificationParams.add(new BasicNameValuePair(
					TAG_NOTIFICATIONMASSAGE, notificationMessage));
			SendNotificationParams.add(new BasicNameValuePair(TAG_SENDTIME,
					sendTime));
			// getting profile info by making HTTP request
			JSONObject json = jsonParser.getJSONFromUrl(urlSendNotification,
					SendNotificationParams);
			// json success tag
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// display product data in EditText

			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
					.show();

		}
	}

	public void onAddPictureClick(View view) {
		selectImage();
		// dispatchTakePictureIntent();
	}

	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(EditEvent.this);
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

		private ProgressDialog pd = new ProgressDialog(EditEvent.this);

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
