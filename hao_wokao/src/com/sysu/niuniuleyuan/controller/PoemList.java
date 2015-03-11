package com.sysu.niuniuleyuan.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.sysu.niuniuleyuan.R;
import com.sysu.niuniuleyuan.function.DataFunction;
import com.sysu.niuniuleyuan.function.ImageFunction;
import com.sysu.niuniuleyuan.function.URLAddress;

public class PoemList extends Activity{

	private ImageButton backBtn;
	private GridView poemList;
	private ProgressBar progressBar;
    private PoemAdapter poemAdapter;
    private ArrayList<String> itemURLs = new ArrayList<String>(); //存储了每个ITEM对应的内容的地址
    									//如:春晓ITEM对应春晓的具体内容
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poem_list);
		bindView();
		initList();
	}
	
	//绑定控件
	private void bindView(){
		backBtn = (ImageButton)findViewById(R.id.poemListToMain);
		poemList = (GridView)findViewById(R.id.poemList);
		progressBar = (ProgressBar)findViewById(R.id.poemListBar);
		//返回键关闭当前界面
		backBtn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				PoemList.this.finish();	
			}
		});
	}
	
	//初始化列表
	private void initList(){
		//初始化内容
		HttpTask ht = new HttpTask();
		ht.execute(URLAddress.MainUrl);

		//添加item点击监听器
		poemList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(PoemList.this,PoemItem.class);
				it.putExtra("url", URLAddress.MainUrl+itemURLs.get(position));
				startActivity(it);
				
			}
		});
	}
	
	//内部类，访问http,更新GridView
	private class HttpTask extends AsyncTask<String, Object, ArrayList<Bitmap>>{

		@Override
		protected ArrayList<Bitmap> doInBackground(String... params) {
			ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
			try{
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				if(conn.getResponseCode()!=200)
					return null;
				InputStream in = conn.getInputStream();
				String jsonString = DataFunction.inputstreamToString(in);
				//Log.v("test", jsonString);
				//把JSON解析出来
				JSONArray jsonArray = new JSONArray(jsonString);
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String imageAdd1 = jsonObject.getString("weizhi_1"); //题目图片地址
					Bitmap bitmap = ImageFunction.getBitmap(URLAddress.MainUrl+imageAdd1);
				    bitmaps.add(bitmap);
				    String imageAdd2 = jsonObject.getString("weizhi_2");  //内容图片地址
					itemURLs.add(imageAdd2);
				}
				return bitmaps;
			}catch(Exception e){
				e.printStackTrace();
				//Log.v("test", e.toString());
			}
			return null;
		}
		@Override
		protected void onPostExecute(ArrayList<Bitmap> result) {
			if(result==null)
				return;
			poemAdapter = new PoemAdapter(PoemList.this, result);
			poemList.setAdapter(poemAdapter);
			progressBar.setVisibility(8);
			super.onPostExecute(result);	
		}
	}
}
