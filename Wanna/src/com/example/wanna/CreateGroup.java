package com.example.wanna;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class CreateGroup extends Activity {
	private Spinner groupTypeSpinner;
	private ImageView img;
	
	private static final int SELECT_PICTURE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_group);
		groupTypeSpinner = (Spinner) findViewById(R.id.groupTypeSpin);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.createGroupArray,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		groupTypeSpinner.setAdapter(adapter);
		img = (ImageView) findViewById(R.id.imgView);
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
			Intent intent = new Intent(this, Login_Success.class);
			startActivity(intent);
			}
}
