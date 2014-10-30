package com.example.wanna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditProfileForm extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile_form);
	}
	
	public void onUpdateClick(View view){	
		EditText name = (EditText) findViewById(R.id.tvName);
		String nameText = name.getText().toString();
		EditText description = (EditText) findViewById(R.id.tvDescription);
		String descriptionText = description.getText().toString();
		}
	
	public void onDeleteClick(View view){	
		EditText name = (EditText) findViewById(R.id.tvName);
		EditText description = (EditText) findViewById(R.id.tvDescription);
		name.setText("");
		description.setText("");
		}
	
	public void onCancelClick(View view){	
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		}
}
