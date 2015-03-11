package com.example.hao_experiment_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class fruits_item extends Activity {
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_fruits);
	        setData();
	        SimpleAdapter listitemadapterAdapter=new SimpleAdapter(
	        		this, MdataList, R.layout.item, 
	        		new String[]{"image","name" },new int[]{R.id.fruit_image,R.id.fruit_name});
	                ListView list_fruitListView=(ListView)findViewById(R.id.listView1);
	                list_fruitListView.setAdapter(listitemadapterAdapter);
	                list_fruitListView.setOnItemClickListener(new OnItemClickListener() {
	                	Bundle bundle=new Bundle();

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
						Bundle bundle=new Bundle();
						Map<String, Object>  map=new HashMap<String, Object>();
						bundle.putString("fruits_name", MdataList.get(position).get("name").toString()+" »¹²»´íÓ´");
						Intent intent=new Intent(fruits_item.this,MainActivity.class);
						intent.putExtras(bundle);
						setResult(1,intent);
						finish();
						}
					});
	  }
	  private List<Map<String, Object>> MdataList=new ArrayList<Map<String,Object>>();
	  private void setData(){
		Map<String, Object>  map=new HashMap<String, Object>();
	    map.put("name","Æ»¹û");
	    map.put("image", R.drawable.apple);
	    MdataList.add(map);
	    map=new HashMap<String, Object>();
	    map.put("name","Ïã½¶");
	    map.put("image", R.drawable.banana);
	    MdataList.add(map);
	    map=new HashMap<String, Object>();
	    map.put("name","³µÀå×Ó");
	    map.put("image", R.drawable.cherry);
	    MdataList.add(map);
	    map=new HashMap<String, Object>();
	    map.put("name","¿É¿É");
	    map.put("image", R.drawable.coco);
	    MdataList.add(map);
	    map=new HashMap<String, Object>();

	    map.put("name","â¨ºïÌÒ");
	    map.put("image", R.drawable.kiwi);
	    MdataList.add(map);
	    
	    map=new HashMap<String, Object>();
	    map.put("name","Ïã³È");
	    map.put("image", R.drawable.orange);
	    MdataList.add(map);
	    map=new HashMap<String, Object>();
	    map.put("name","Ë®¾§Àæ");
	    map.put("image", R.drawable.pear);
	    MdataList.add(map);
	    map=new HashMap<String, Object>();
	    
	    map.put("name","²ÝÝ®");
	    map.put("image", R.drawable.strawberry);
	    MdataList.add(map);
	    map=new HashMap<String, Object>();
	    map.put("name","Î÷¹Ï");
	    map.put("image", R.drawable.watermelon);
	    MdataList.add(map);
	    
	    
	  }

	  

}
