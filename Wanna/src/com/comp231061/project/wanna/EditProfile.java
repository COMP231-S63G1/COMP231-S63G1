package com.comp231061.project.wanna;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.json.JSONArray;
import org.json.JSONObject;

import com.comp231061.project.wanna.library.AlbumStorageDirFactory;
import com.comp231061.project.wanna.library.BaseAlbumDirFactory;
import com.comp231061.project.wanna.library.FroyoAlbumDirFactory;
import com.comp231061.project.wanna.library.ImageFilePath;
import com.comp231061.project.wanna.library.ImageLoader;
import com.comp231061.project.wanna.library.JSONParser;
import com.comp231061.project.wanna.library.UserFunctions;
import com.comp231061.project.wanna.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class EditProfile extends Activity {

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	UserFunctions userFunctions = new UserFunctions();

	private ProgressDialog pDialog;

	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	private String urlUpdateProfile = UserFunctions.URL_ROOT
			+ "DB_UpdateProfile.php";
	
	//Select images from gallery or take images from camera
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
	
	//Upload images
    String ba1;
    public static String urlUploadImage = UserFunctions.URL_ROOT + "DB_UploadImages.php";
    String ServerPictureName;
	private static final String TAG_BOOLIMAGECHANGE = "BoolImageChange";

	// user profile JSONArray
	JSONArray userProfileArray = null;
	// JSON Node names
	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_PICTUREURL = "pictureURL";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_PERSON = "Person";
	private static final String TAG_ORGANIZATION = "Organization";

	EditText txtUserNickName;
	EditText txtUserDescription;
	ImageButton imbUserPicture;

	String sessionID;
	String userID;
	String userType;
	String userNickName;
	String userDescription;
	String userPictureURL;
	int success;
	String message;

	JSONObject profileInformation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		Intent intent = getIntent();
		// getting user nick name and description from intent
		userNickName = intent.getStringExtra(TAG_NICKNAME);
		userDescription = intent.getStringExtra(TAG_DESCRIPTION);
		userPictureURL = intent.getStringExtra(TAG_PICTUREURL);

		// Loader image - will be shown before loading image
        int loader = R.drawable.loader;
		txtUserNickName = (EditText) findViewById(R.id.etName);
		txtUserDescription = (EditText) findViewById(R.id.etDescription);
		imbUserPicture = (ImageButton) findViewById(R.id.userPicture);

		txtUserNickName.setText(userNickName);
		txtUserDescription.setText(userDescription);
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
	
	

	public void onUpdateClick(View view) {
		userNickName = txtUserNickName.getText().toString();
		userDescription = txtUserDescription.getText().toString();
        ServerPictureName = String.valueOf(System.currentTimeMillis());
        if(ChangePicture.equals("true")){
    		upload();        	
        }
		new UploadNewInformationTask().execute(urlUpdateProfile);
	}

	public void onDeleteClick(View view) {
		userNickName = "";
		userDescription = "";
        mCurrentPhotoPath = null;
		new UploadNewInformationTask().execute(urlUpdateProfile);
	}

	public void onCancelClick(View view) {
		Intent i = new Intent(getApplicationContext(), PersonLoginSuccess.class);
		startActivity(i);
	}

	private class UploadNewInformationTask extends
			AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditProfile.this);
			pDialog.setTitle("Contacting Servers");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			
			// Building Parameters
			List<NameValuePair> updateProfileParams = new ArrayList<NameValuePair>();
			updateProfileParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			updateProfileParams.add(new BasicNameValuePair(TAG_USERID, userID));
			updateProfileParams.add(new BasicNameValuePair(TAG_USERTYPE,
					userType));
			updateProfileParams.add(new BasicNameValuePair(TAG_NICKNAME,
					userNickName));
			updateProfileParams.add(new BasicNameValuePair(TAG_DESCRIPTION,
					userDescription));
			updateProfileParams.add(new BasicNameValuePair(TAG_BOOLIMAGECHANGE,
					ChangePicture));
			updateProfileParams.add(new BasicNameValuePair(TAG_PICTUREURL,
					ServerPictureName));
			JSONObject json = jsonParser.getJSONFromUrl(urlUpdateProfile,
					updateProfileParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 1) {
				Editor editor = sharedpreferences.edit();
				editor.putString(TAG_NICKNAME, json.optString(TAG_NICKNAME));
				editor.commit();
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

	public void onAddPictureClick(View view) {
		selectImage();
		// dispatchTakePictureIntent();
	}

	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
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
			if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && pictureResource.equals("Camera")) {
				handleBigCameraPhoto();
				ChangePicture = "true";
			}
			// When an Image is picked
			else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && pictureResource.equals("Gallery")
					&& null != data) {
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
				mCurrentPhotoPath = ImageFilePath.getPath(getApplicationContext(), selectedImage);
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
			//mCurrentPhotoPath = null;
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
        Bitmap bm = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mCurrentPhotoPath), 
                THUMBSIZE, THUMBSIZE);
        //Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeToString(ba, 0);
 
        Log.e("base64", "-----" + ba1);
 
        // Upload image to server
        new uploadToServer().execute();
 
    }
	
	public class uploadToServer extends AsyncTask<Void, Void, String> {
		 
        private ProgressDialog pd = new ProgressDialog(EditProfile.this);
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.show();
        }
 
        @Override
        protected String doInBackground(Void... params) {
 
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", ServerPictureName + ".jpg"));
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