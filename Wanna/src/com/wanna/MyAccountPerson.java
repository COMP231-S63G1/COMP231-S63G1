package com.wanna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.wanna.R;

public class MyAccountPerson extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account_person);
	}

	public void onViewProfile(View view){	
  		Intent intent = new Intent(this, ViewPersonProfile.class);
  		startActivity(intent);
  		}  
	public void onEditProfile(View view){	
  		Intent intent = new Intent(this, EditProfile.class);
  		startActivity(intent);
  		}  
	public void onChangePassword(View view){	
  		Intent intent = new Intent(this, ChangePassword.class);
  		startActivity(intent);
  		}  
	public void onEditEvents(View view){	
  		Intent intent = new Intent(this, EditEvent.class);
  		startActivity(intent);
  		}  
	public void onEditGroup(View view){	
  		Intent intent = new Intent(this, EditGroup.class);
  		startActivity(intent);
  		}  
	public void onFriendsList(View view){	
  		Intent intent = new Intent(this, ViewFriendList.class);
  		startActivity(intent);
  		}  
	public void onNotifications(View view){	
  		Intent intent = new Intent(this, ViewNotifications.class);
  		startActivity(intent);
  		}  
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_account_person, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
