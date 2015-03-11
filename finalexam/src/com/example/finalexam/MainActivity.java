package com.example.finalexam;

import android.R.string;
import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button button1;
	private ListView listView;
	private AlertDialog dialog;
	private MyDB haoDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1 = (Button) findViewById(R.id.btn1);
		listView = (ListView) findViewById(R.id.listView1);
		haoDb = new MyDB(this, "f.db", null, 1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, addActivity.class);
				startActivityForResult(i, 1);
			}
		});
		findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "さよなら", Toast.LENGTH_LONG)
						.show();
				haoDb.clear();
				refresh();

			}
		});
		refresh();
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				LayoutInflater factory = LayoutInflater.from(MainActivity.this);
				// 获得自定义对话框
				View v = factory.inflate(R.layout.alertdialog, null);

				TextView dialogshowTextView = (TextView) v
						.findViewById(R.id.dialogeditname);
				Button dialogbtn = (Button) v.findViewById(R.id.dialogbtn);
				Button btncomfirm = (Button) v.findViewById(R.id.btn_comfirm);
				dialog = new AlertDialog.Builder(MainActivity.this)
						.setIcon(android.R.drawable.btn_star)
						.setTitle("Delete Contrast?").setView(v).create();
				dialog.show();
				final Cursor cursor = haoDb.query();
				cursor.moveToFirst();
				for (int i2 = 0; i2 < position; i2++) {
					cursor.moveToNext();
				}
				dialogshowTextView.setText(cursor.getString(cursor
						.getColumnIndex("name")));
				// 得到的int型
				// setText(String.valueOf(c.getInt(c.getColumnIndex("class"))));
				dialogbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v2) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				btncomfirm.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String[] arg = { cursor.getString(cursor
								.getColumnIndex("_id")) };
						haoDb.delete("_id", arg);
						dialog.dismiss();
						refresh();
					}
				});
				return true;// 屏蔽掉下次单击
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				LayoutInflater factory = LayoutInflater.from(MainActivity.this);
				// 获得自定义对话框
				View v = factory.inflate(R.layout.edit_alertdialog, null);

				final EditText dialogeditname = (EditText) v
						.findViewById(R.id.dialogeditname);
				final EditText dialogedittel = (EditText) v
						.findViewById(R.id.dialogedittel);
				Button dialogbtn = (Button) v.findViewById(R.id.dialogbtn);
				Button btncomfirm = (Button) v.findViewById(R.id.btn_comfirm);
				dialog = new AlertDialog.Builder(MainActivity.this)
						.setIcon(android.R.drawable.btn_plus)
						.setTitle("Update info").setView(v).create();
				dialog.show();
				final Cursor cursor = haoDb.query();
				cursor.moveToFirst();
				for (int i2 = 0; i2 < position; i2++) {
					cursor.moveToNext();
				}
				dialogeditname.setText(cursor.getString(cursor
						.getColumnIndex("name")));
				dialogedittel.setText(cursor.getString(cursor
						.getColumnIndex("tel")));
				// 得到的int型
				// setText(String.valueOf(c.getInt(c.getColumnIndex("class"))));
				dialogbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v2) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				btncomfirm.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v2) {
						// TODO Auto-generated method stub
						String[] arg = { cursor.getString(cursor
								.getColumnIndex("_id")) };
						ContentValues values = new ContentValues();
						values.put("name", dialogeditname.getText().toString());
						values.put("tel", dialogedittel.getText().toString());
						haoDb.update(arg, values);
						dialog.dismiss();
						refresh();
					}
				});

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		refresh();
		Log.v("hao2", "I am back");
	}

	private void refresh() {
		Cursor cursor = (new MyDB(this, "f.db", null, 1)).query();
		SimpleCursorAdapter a = new SimpleCursorAdapter(this, R.layout.item,
				cursor, new String[] { "name", "tel" }, new int[] {
						R.id.item_name, R.id.item_tel });
		listView.setAdapter(a);
	}
}
