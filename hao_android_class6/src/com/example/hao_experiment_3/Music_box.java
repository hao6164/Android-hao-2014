package com.example.hao_experiment_3;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.menu;
import android.R.string;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Music_box extends Activity {
	private  ImageButton last_song_btn,next_song_btn,run_song_btn; 
	private  SimpleMusicPlayerService musicService;
	private  Button button_exit;
	private  SeekBar sBar;
	private Handler handler;
	private int song_index=0;
	private Intent indentIntent;
	private TextView music_show;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(Menu.NONE,0,Menu.NONE,"exit");
		
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Exit Completely",
			     Toast.LENGTH_SHORT).show();
		Intent indentIntent=new Intent(Music_box.this,SimpleMusicPlayerService.class);
		try {
			 handler.removeCallbacks(r);
		} catch (Exception e) {
			// TODO: handle exception
		} 
		//musicService.onDestroy();
	    //unbindService(sc); 
		stopService(indentIntent);
	    finish();
	    return true;
		//return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		try {
			 handler.removeCallbacks(r);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		unbindService(sc);
		super.onDestroy();
	} 
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_music);
	        bindbutton();
	        music_show=(TextView)findViewById(R.id.music_show);
	        sBar=(SeekBar)findViewById(R.id.seekBar1);
	        sBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					if (fromUser) {
						musicService.set_song_pos(progress);
					}
				}
			});
	         indentIntent=new Intent(this,SimpleMusicPlayerService.class);
	        startService(indentIntent);
	        Intent bindent=new Intent(this,SimpleMusicPlayerService.class);
	        bindService(bindent, sc,Context.BIND_AUTO_CREATE);
	        generateListView();
	        //musicService.init_song();
	        handler=new Handler();
			handler.post(r);
//            
//          if (musicService.getState()) {
//			Log.v("hao", "msg");
//		}
//	               
//	                               
	  }
	  private void bindbutton() {
		// TODO Auto-generated method stub
		   last_song_btn=(ImageButton)findViewById(R.id.lastsong_btn);
           run_song_btn=(ImageButton)findViewById(R.id.run_btn);
           next_song_btn=(ImageButton)findViewById(R.id.nextsong_btn);
          
		   run_song_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				musicService.play();
				handler=new Handler();
				handler.post(r);
			}
			});
		   last_song_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(song_index<=0)
				{
					Toast.makeText(getApplicationContext(), "there is no last song",
						     Toast.LENGTH_SHORT).show();
				}
				else
				{song_index =song_index-1;
                  musicService.change_song(MdataList.get(song_index).get("path").toString());
                  musicService.setmusicname(MdataList.get(song_index).get("name").toString());
				handler=new Handler();
				handler.post(r);
				}
			}
		});
		   next_song_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(song_index>=MdataList.size()-1)
				{
					Toast.makeText(getApplicationContext(), "there is no next song",
						     Toast.LENGTH_SHORT).show();
				}
				else
				{song_index =song_index+1;
                  musicService.change_song(MdataList.get(song_index).get("path").toString());
                  musicService.setmusicname(MdataList.get(song_index).get("name").toString());
				handler=new Handler();
				handler.post(r);
				}
			}
		});
	}
	  private List<Map<String, Object>> MdataList=new ArrayList<Map<String,Object>>();
	  private void generateListView(){
               List<File> list=new ArrayList<File>();
               int list_num=1;
               findall(Environment.getExternalStorageDirectory().toString(),list);
               Collections.sort(list);
               for(File file:list)
               {
            	   Map<String, Object> map=new HashMap<String, Object>();
            	   map.put("name", list_num+"."+file.getName());
            	   map.put("path", file.getAbsolutePath());
            	   MdataList.add(map);
            	   list_num++;
               }
               SimpleAdapter listItemAdapter=new SimpleAdapter(this, MdataList,R.layout.item, new String[]{"name"}, new int[]{R.id.music_name});
               ListView MusicListView=(ListView)findViewById(R.id.listView1);
               MusicListView.setAdapter(listItemAdapter);
               MusicListView.setOnItemClickListener(new OnItemClickListener() {
//               	Bundle bundle=new Bundle();
//
					@Override
					public void onItemClick(AdapterView<?> parent,
							View view, int position, long id) {

					musicService.change_song(MdataList.get(position).get("path").toString());
					musicService.setmusicname(MdataList.get(position).get("name").toString());
					song_index=position;
					handler=new Handler();
					handler.post(r);
					}
			});
               
	  }
      private void findall(String path ,List<File> list)
      {
    	  File mFile=new File(path);
    	  File[] subFiles=mFile.listFiles();
    	  if(subFiles!=null)
    	  {
    		  for(File subFile:subFiles)
    			  if(subFile.isFile()&&(subFile.getName().endsWith(".mp3")||subFile.getName().endsWith(".aac")||subFile.getName().endsWith(".wma")||subFile.getName().endsWith(".flac")))
    					  {
    						  list.add(subFile);
    					  }
    	  }
      }
	  
	  private ServiceConnection sc=new ServiceConnection(){
		  public void onServiceDisconnected(ComponentName name)
		  {
			  musicService=null;
		  }
		  public void onServiceConnected(ComponentName name,IBinder service)
		  {
			  musicService=((SimpleMusicPlayerService.SMPlayerBinder)(service)).getService();
		  }
	  };
      private Runnable r=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (musicService==null) {
				return;
				
			}
			if(musicService.getState())
			{
				run_song_btn.setBackgroundResource(R.drawable.stopmusic);
			}
			else{
				run_song_btn.setBackgroundResource(R.drawable.playmusic);
			}
			SimpleDateFormat format=new SimpleDateFormat("mm:ss");  
	        Date d1=new Date(musicService.getCuttentPosition());  
	        String t1=format.format(d1);
	        Date d2=new Date(musicService.getDuration());
	        
	        String t2=format.format(d2);
			music_show.setText(musicService.getmusicname()+"   "+t1+"/"+t2);
			sBar.setMax(musicService.getDuration());
			sBar.setProgress(musicService.getCuttentPosition());
			handler.postDelayed(r, 1000);
		}
	};
    	 
}
