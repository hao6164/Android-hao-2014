package com.example.hao_android_class7;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;

public class selectmenu extends Activity {
	private List<Map<String, Object>> File_list=new ArrayList<Map<String, Object>>();
	private String last_fold,now_fold=Environment.getExternalStorageDirectory().toString();
    private Stack<String> path_historyStack=new Stack<String>();
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.selcetmenu);
	set_files_listview(Environment.getExternalStorageDirectory().toString());

}
private void findall(String path ,List<File> list)
{
	  File mFile=new File(path);
	  File[] subFiles=mFile.listFiles();
	  if(subFiles!=null)
	  {
		  for(File subFile:subFiles)
		  {
				list.add(subFile);
		  }	
	  }
}
private void set_files_listview(String root_path)
{ 
	List<File> list=new ArrayList<File>();
	File_list.clear();
	findall(root_path,list);
    Collections.sort(list);
    now_fold=root_path;
    Map<String, Object> map=new HashMap<String, Object>();
    
    if (root_path!=Environment.getExternalStorageDirectory().toString()) {
		map.put("path", "...");
	    File_list.add(map);
	}
	
    for(File file:list)
    {
 	    map=new HashMap<String, Object>();
 	    map.put("type", file.isDirectory());
		map.put("path", file.getName());
	
 	   File_list.add(map);
 	   //list_num++;
    }
	SimpleAdapter listItemAdapter=new SimpleAdapter(this,File_list,R.layout.menutype, new String[]{"path"}, new int[]{R.id.file2});
    ListView FileListView=(ListView)findViewById(R.id.listView_file);
    FileListView.setAdapter(listItemAdapter);
    FileListView.setOnItemClickListener(new OnItemClickListener() {
//	Bundle bundle=new Bundle();
//
		@Override
		public void onItemClick(AdapterView<?> parent,
				View view, int position, long id) {

			if (File_list.get(position).get("path").toString()=="...") {
				String returnpath=path_historyStack.pop();
				set_files_listview(returnpath);
			}
			else{
				if ((Boolean)File_list.get(position).get("type")) {
					
				
			path_historyStack.push(now_fold);
			set_files_listview(now_fold+"/"+File_list.get(position).get("path").toString());
		    // last_fold=now_fold;
			}
				else
				{
					Bundle bundle=new Bundle();
					bundle.putString("path", now_fold+"/"+File_list.get(position).get("path").toString());
					Intent intent=new Intent(selectmenu.this,MainActivity.class);
					intent.putExtras(bundle);
					setResult(android.app.Activity.RESULT_OK,intent);
					finish();
					Log.i("hao2", "file");
				}
		 }
		}
});
	}
}
