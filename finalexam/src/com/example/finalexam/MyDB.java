package com.example.finalexam;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.CursorAdapter;

public class MyDB extends SQLiteOpenHelper {

	public MyDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("Create table fina(_id integer primary key autoincrement,"
				+ "name text,tel text);");
		// +"name TEXT,"+"class INTEGER,"+"phone TEXT)";
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void insert(ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		db.insert("fina", null, values);
		db.close();
	}

	public Cursor query() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query("fina", null, null, null, null, null, null);
		return c;
	}

	public Boolean queue_one(Integer stuid) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery(
				"select * from fina where stuid = " + stuid.toString(), null);
		if (c != null && c.getCount() >= 1) {
			return true;
		} else {
			return false;
		}

	}

	public void delete(String whereClause, String[] whereArgs) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete("fina", "_id=?", whereArgs);
		db.close();
	}

	public void clear() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from fina");
		db.close();
	}

	public void update(String[] whereArgs,ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		db.update("fina", values,"_id=?", whereArgs);
		//db.execSQL("update fina set name = 44 where name = 11");
		db.close();
	}
}
