package com.example.wanna;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewNotifications extends Activity {

	final Context context = this;
	private Button button;
 
	public void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_notifications);
	}
	
		public void ChatRequestClick(View view) {
 
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
			alertDialogBuilder.setTitle("Chat Request Notification");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("You have a chat request. If you would like to accept the request" +
						"choose Accept.")
				.setCancelable(false)
				.setPositiveButton("Accept",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						ViewNotifications.this.finish();
					}
				  })
				.setNegativeButton("Decline",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		public void FriendRequestClick(View view) {
			 
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
			alertDialogBuilder.setTitle("Friend Request Notification");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("You have a friend request - if you would like to be friends, click Accept!")
				.setCancelable(false)
				.setPositiveButton("Accept",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						ViewNotifications.this.finish();
					}
				  })
				.setNegativeButton("Decline",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		
		
		
		
	}

	