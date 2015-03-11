package com.example.hao_android_class8;

import org.w3c.dom.Text;

import android.R.integer;
import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class haosql extends SQLiteOpenHelper {

	private static final String DB_NAME="haoconstacts.db";
	private static final String TABLE_NAME="contacts";
	private static final int DB_VERSION=1;
	public haosql(Context context) {
		super(context,DB_NAME,null,DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
        String SQL_CREATE_TABLE="create table "+TABLE_NAME
        		+"(_id INTEGER primary key autoincrement,"+"stuid INTEGER,"
        		+"name TEXT,"+"class INTEGER,"+"phone TEXT)";
        db.execSQL(SQL_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	public Cursor queue_all()
	{
		SQLiteDatabase db=getReadableDatabase();
		Cursor c=db.rawQuery("select * from "+TABLE_NAME, null);
		return c;
	}
	public Boolean queue_one(Integer stuid)
	{
		SQLiteDatabase db=getReadableDatabase();
		Cursor c=db.rawQuery("select * from "+TABLE_NAME+" where stuid = "+stuid.toString(), null);
		if (c!=null&&c.getCount() >= 1) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void insert(Integer a,String b,Integer c,String d)
	{
		SQLiteDatabase db=getWritableDatabase();
		String sqlcmd="insert into "+TABLE_NAME+" (stuid,name,class,phone) values("+a.toString()+",'"+b+"',"+c.toString()+","+d+")";
		try {
			db.execSQL(sqlcmd);
		} catch (SQLException e) {
			// TODO: handle exception
			Log.v("hao",e.toString());
		}
		
		db.close();
	}
	public void delete(Integer stuid)
	{
		SQLiteDatabase db=getWritableDatabase();
		db.execSQL("delete from "+TABLE_NAME+" where stuid = "+stuid.toString());
		db.close();
	}
	public void update()
	{
		
	}

}
