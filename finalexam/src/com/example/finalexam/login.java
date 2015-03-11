package com.example.finalexam;

import android.R.string;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends Activity {
	TextView user,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button btn_login=(Button)findViewById(R.id.btn_login);
		user=(TextView)findViewById(R.id.user);
		password=(TextView)findViewById(R.id.password);
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(true)//(user.getText().toString().equals("hao6164")&&password.getText().toString().equals("915433"))	
			{
				Intent intent=new Intent(login.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			else {
				Toast.makeText(login.this, "incorrect", Toast.LENGTH_LONG).show();
			}
			}

		
		});
	}
}
