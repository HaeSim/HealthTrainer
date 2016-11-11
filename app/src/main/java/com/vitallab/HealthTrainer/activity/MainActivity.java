package com.vitallab.HealthTrainer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vitallab.HealthTrainer.R;
import com.vitallab.HealthTrainer.helper.SQLiteHandler;
import com.vitallab.HealthTrainer.helper.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
	@BindView(R.id.btnProfile) Button btnProfile;
	@BindView(R.id.btnLogout) Button btnLogout;

	@BindView(R.id.name) TextView txtName;
	@BindView(R.id.email) TextView txtEmail;

	private SQLiteHandler db;
	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

		// SqLite database handler
		db = new SQLiteHandler(getApplicationContext());

		// session manager
		session = new SessionManager(getApplicationContext());

		if (!session.isLoggedIn()) {
			logoutUser();
		}

		// Fetching user details from SQLite
		HashMap<String, String> user = db.getUserDetails();

		final String name = user.get("name");
		String email = user.get("email");

		// Displaying the user details on the screen
		txtName.setText(name);
		txtEmail.setText(email);
		//Profile button click event
		btnProfile.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				openProfile(name);
			}
		});
		// Logout button click event
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutUser();
			}
		});
	}
	private  void openProfile(String name){
		Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
		startActivity(intent);
		intent.putExtra("name",name);
	}
	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
	private void logoutUser() {
		session.setLogin(false);

		db.deleteUsers();

		// Launching the login activity
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
