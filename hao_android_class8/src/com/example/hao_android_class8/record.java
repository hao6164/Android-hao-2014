package com.example.hao_android_class8;

import android.R.id;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class record extends Activity {
	private Button btn_confirmButton;
	private EditText idtext,phonetext,classtext,nametext;
	private haosql haosqlite=new haosql(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		idtext=(EditText)findViewById(R.id.id_edit);
		nametext=(EditText)findViewById(R.id.name_edit);
		classtext=(EditText)findViewById(R.id.class_edit);
		phonetext=(EditText)findViewById(R.id.phone_edit);
		btn_confirmButton=(Button)findViewById(R.id.btn_confirm2);
		btn_confirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(idtext.getText().toString().length()==0||nametext.getText().toString().length()==0||phonetext.getText().toString().length()==0||classtext.getText().toString().length()==0){
					Toast.makeText(record.this, "please finish all info",Toast.LENGTH_LONG).show();  
					return;
					}
					
				if (haosqlite.queue_one(Integer.parseInt(idtext.getText().toString()))) {
					Toast.makeText(record.this, "this id exists already ",Toast.LENGTH_LONG).show();  
					return;
					}
				
		        haosqlite.insert(Integer.parseInt(idtext.getText().toString()),
		        		nametext.getText().toString(),Integer.parseInt(classtext.getText().toString()),
		        		phonetext.getText().toString());
		        
				
				Intent intent=new Intent(record.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(record.this,MainActivity.class);
		startActivity(intent);
		super.onDestroy();
	}

}
