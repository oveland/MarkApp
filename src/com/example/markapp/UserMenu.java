package com.example.markapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class UserMenu extends ActionBarActivity{

	// Propiedades de usuario:
	TextView user_data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_menu);
		
		user_data = (TextView) findViewById(R.id.txt_user);
		
		user_data.setText(MainActivity.users.getFirst_name()+MainActivity.users.getLast_name());
		
	}
}
