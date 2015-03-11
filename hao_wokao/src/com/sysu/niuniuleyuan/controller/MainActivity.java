package com.sysu.niuniuleyuan.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.sysu.niuniuleyuan.R;
import com.sysu.niuniuleyuan.function.CacheFunction;
import com.sysu.niuniuleyuan.function.DataFunction;
import com.sysu.niuniuleyuan.function.ImageFunction;

import android.R.string;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private ImageButton musicBtn;
	private ImageButton poemBtn;
	private ImageButton storyBtn;
	private ImageButton studyBtn;    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//<<<<<<< .mine
//		setContentView(R.layout.main);
// 
//		///////hao�����Դ���
//		String str = "·��";
//		InputStream   in_withcode=null;
//		InputStream   in_nocode   =   new   ByteArrayInputStream(str.getBytes());   
//		try {
//			in_withcode   =   new   ByteArrayInputStream(str.getBytes("UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//		CacheFunction cacheFunction = new CacheFunction();
//		cacheFunction.store("test2","math",in_withcode);
//		String rawstring=cacheFunction.Stream2String(in_withcode);
//			InputStream inputmusic=cacheFunction.fetch("test2", "math");
//			
//		String fetchstring=cacheFunction.Stream2String(inputmusic);
//		Log.v("hao",fetchstring);
//		cacheFunction.clear(0);
//	    ///////hao�����Դ���
//		
//		
////		startActivity(new Intent(MainActivity.this,MusicActivity.class));
//=======
//		
//		
//>>>>>>> .r29
		setContentView(R.layout.main); 
		bindView();
		bindListener();
		
	}
	
	/*
	 * �󶨿ؼ�
	 */
	private void bindView(){
		studyBtn=(ImageButton) findViewById(R.id.mainToStudyList);
		musicBtn=(ImageButton) findViewById(R.id.mainToMusicList);
		storyBtn=(ImageButton) findViewById(R.id.mainToStoryList);
		poemBtn=(ImageButton) findViewById(R.id.mainToPoemList);
	}

	/*
	 * �󶨼�����
	 */
	private void bindListener(){
		OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it;
				switch(v.getId()){
				case R.id.mainToPoemList:
					it = new Intent(MainActivity.this, PoemList.class);
					startActivity(it);
					break;

				case R.id.mainToStoryList:
					it = new Intent(MainActivity.this,StoryList.class);
					startActivity(it);
					break;

					
				case R.id.mainToMusicList:
				{
					startActivity(new Intent(MainActivity.this,MusicActivity.class));
				}break;
					
				case R.id.mainToStudyList:
					it = new Intent(MainActivity.this, Study.class);
					startActivity(it);
					break;

				}
			}
		};
		
		studyBtn.setOnClickListener(listener);
		musicBtn.setOnClickListener(listener);
		storyBtn.setOnClickListener(listener);
		poemBtn.setOnClickListener(listener);

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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
