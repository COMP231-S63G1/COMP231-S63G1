package com.example.wanna;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.wanna.library.JSONParser;
import com.example.wanna.library.LocationTracker;
import com.example.wanna.library.UserFunctions;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class PersonLoginSuccess extends Activity {
	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	
	public static final String MyPREFERENCES = "Wanna";
	SharedPreferences sharedpreferences;

	private static final String TAG_SESSIONID = "sessionid";
	private static final String TAG_USERID = "userid";
	private static final String TAG_USERTYPE = "userType";
	private static final String TAG_NICKNAME = "nickName";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_ORGANIZATION = "Organization";
	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_LONGITUDE = "longitude";
  
    TextView tvSessionID;
    TextView tvTextwelcome;
    
	String sessionID;
	String userID;
	String userType;
	String nickName;     
	int success;
	String message;
	double latitude;
    double longitude;

	LocationTracker location;
    
	UserFunctions userFunctions = new UserFunctions();
	private String urlCheckLogin = UserFunctions.URL_ROOT + "DB_LoginSuccess.php";

	String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    ArrayAdapter<String> adapter;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_login_success);    

		location = new LocationTracker(PersonLoginSuccess.this);   

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

		sessionID = sharedpreferences.getString(TAG_SESSIONID, "");
		userID = sharedpreferences.getString(TAG_USERID, "");
		userType = sharedpreferences.getString(TAG_USERTYPE, "");
		nickName = sharedpreferences.getString(TAG_NICKNAME, "");
		if (userType.equals(TAG_ORGANIZATION)) {
			Intent intent = new Intent(getApplicationContext(),
					OrganizationLoginSuccess.class);
			startActivity(intent);
		}
        tvTextwelcome = (TextView) findViewById(R.id.textwelcome);
        tvSessionID = (TextView) findViewById(R.id.textView);
        if(location.canGetLocation()){            
            latitude = location.getLatitude();
            longitude = location.getLongitude();
           }else{
        	   Toast.makeText(getApplicationContext(), "Cannot get location",
						Toast.LENGTH_SHORT).show();
        }
        new LoginSeccessTask().execute();  
        
        menu = new String[]{"MY ACCOUNT","EVENTS","GROUPS","SEARCH USERS","LOGOUT"};
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu);
        
        dList.setAdapter(adapter);
		dList.setSelector(android.R.color.holo_blue_dark);

        dList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

           	 switch( position )
           	 {
           	    case 0:  Intent intent = new Intent(PersonLoginSuccess.this, MyAccountPerson.class);     
  	             			 startActivity(intent);
           	             break;
           	    case 1:  Intent i2 = new Intent(PersonLoginSuccess.this, SearchEvent.class);     
           	             startActivity(i2);
           	             break;
           	    case 2:  Intent i3 = new Intent(PersonLoginSuccess.this, SearchGroup.class);     
           	             startActivity(i3);
           	             break;
           	    case 3:  Intent i4 = new Intent(PersonLoginSuccess.this, SearchUserFiltration.class);     
           	    		startActivity(i4);
           	    		break;
           	    case 5:  Intent i5 = new Intent(PersonLoginSuccess.this, Login.class);     
           	    		startActivity(i5);
           	    		break;
            }
				/*dLayout.closeDrawers();					
				Bundle args = new Bundle();
				args.putString("Menu", menu[position]);
				Fragment detail = new DetailFragment();
				detail.setArguments(args);
			    FragmentManager fragmentManager = getFragmentManager();		    
				fragmentManager.beginTransaction().replace(R.id.content_frame, detail).commit();
				*/
			}
        	
        });
}
    
    
    public void onCreateEvent(View view){	
		Intent intent = new Intent(this, CreateEvent.class);
		startActivity(intent);
		}
    public void onCreateGroup(View view){	
  		Intent intent = new Intent(this, CreateGroup.class);
  		startActivity(intent);
  		}  
    
    public void onSearchUserName(View view){	
  		Intent intent = new Intent(this, SearchUserName.class);
  		startActivity(intent);
  		}  
    
    public void onCreateProfile(View view){	
  		Intent intent = new Intent(this, CreatePersonProfile.class);
  		startActivity(intent);
  		}  
   /* public void onSearchEvent(View view){	
		Intent intent = new Intent(this, SearchEvent.class);
		startActivity(intent);
		}
    
    public void onViewProfileClick(View view){	
		Intent intent = new Intent(this, ViewPersonProfile.class);
		startActivity(intent);
		}
    
    public void onChangePasswordClick(View view){	
		Intent intent = new Intent(this, ChangePassword.class);
		startActivity(intent);    	
    }
    
    public void onLogoutClick(View view){    	
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);    	
    }
    
    public void onSearchGroupClick(View view){	
		Intent intent = new Intent(this, SearchGroup.class);
		startActivity(intent);
		}
    
    

    public void onSearchUserClick(View view){	
		Intent intent = new Intent(this, SearchUserName.class);
		startActivity(intent);
		}

    public void onFiltrationUserClick(View view){	
		Intent intent = new Intent(this, SearchUserFiltration.class);
		startActivity(intent);
		}
    public void onViewFriendClick(View view){
    	Intent intent = new Intent(this,ViewFriendList.class);
    	startActivity(intent);
    }*/
    private class LoginSeccessTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> checkLoginParams = new ArrayList<NameValuePair>();
			checkLoginParams.add(new BasicNameValuePair(TAG_SESSIONID,
					sessionID));
			checkLoginParams.add(new BasicNameValuePair(TAG_USERID,
					userID));
			checkLoginParams.add(new BasicNameValuePair(TAG_USERTYPE, userType));
			checkLoginParams.add(new BasicNameValuePair(TAG_LATITUDE, Double.toString(latitude)));		
			checkLoginParams.add(new BasicNameValuePair(TAG_LONGITUDE, Double.toString(longitude)));	
			System.out.println("latitude: " + latitude);
			System.out.println("longitude: " + longitude);
			JSONObject json = jsonParser.getJSONFromUrl(urlCheckLogin, checkLoginParams);
			success = json.optInt(TAG_SUCCESS);
			message = json.optString(TAG_MESSAGE);
			if (success == 0) {
				Intent intent = new Intent(getApplicationContext(),
						PersonLoginSuccess.class);
				startActivity(intent);					
			}
			return null;			
		}

		@Override
		protected void onPostExecute(String result) {
			if(success != 1){
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
			// display product data in EditText
			tvTextwelcome.setText("Welcome " + nickName);	
			tvSessionID.setText("userID " + userID);			
		}
    }
}