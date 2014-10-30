package com.example.wanna;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.wanna.library.UserFunctions;
import com.example.wanna.library.DatabaseHandler;

import java.util.HashMap;

public class Login_Success extends Activity {
    Button btnLogout;
    Button changepas;




    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);
        

        changepas = (Button) findViewById(R.id.btchangepass);
        btnLogout = (Button) findViewById(R.id.logout);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        /**
         * Hashmap to load data from the Sqlite database
         **/
         HashMap<String,String> user = new HashMap<String, String>();
         user = db.getUserDetails();


        /**
         * Change Password Activity Started
         **/
        changepas.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){

                Intent chgpass = new Intent(getApplicationContext(), ChangePassword.class);

                startActivity(chgpass);
            }

        });

       /**
        *Logout from the User Panel which clears the data in Sqlite database
        **/
        btnLogout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                UserFunctions logout = new UserFunctions();
                logout.logoutUser(getApplicationContext());
                Intent login = new Intent(getApplicationContext(), Login.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                finish();
            }
        });
        
        
/**
 * Sets user first name and last name in text view.
 **/
        final TextView login = (TextView) findViewById(R.id.textwelcome);
        login.setText("Welcome "+user.get("fname"));


    }
    
    public void onCreateEvent(View view){	
		Intent intent = new Intent(this, CreateEvent.class);
		startActivity(intent);
		}
    
    public void onSearchEvent(View view){	
		Intent intent = new Intent(this, SearchEvent.class);
		startActivity(intent);
		}
    
    public void onCreateProfile(View view){	
		Intent intent = new Intent(this, Profile_Form.class);
		startActivity(intent);
		}
  
}