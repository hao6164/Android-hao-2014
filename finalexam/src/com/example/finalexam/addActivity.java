package com.example.finalexam;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class addActivity extends Activity {
	private EditText editname,editnum2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		editname = (EditText) findViewById(R.id.user);
		editnum2 = (EditText) findViewById(R.id.editnum);
		findViewById(R.id.button_add).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyDB db = new MyDB(getApplication(), "f.db", null, 1);
				ContentValues values = new ContentValues();
				values.put("name", editname.getText().toString());
				values.put("tel", editnum2.getText().toString());
				db.insert(values);
				setResult(RESULT_OK);
				finish();
			}
		});
	}

}
