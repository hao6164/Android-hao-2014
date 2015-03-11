package com.example.hao_experiment_3;

import java.io.IOException;

import junit.framework.Test;

import android.R.bool;
import android.R.string;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class SimpleMusicPlayerService extends Service {
   private final IBinder binder=new SMPlayerBinder();
   MediaPlayer mPlayer=new MediaPlayer();
   private String music_name="Default.whatareyouworrying.mp3";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		//(SMPlayerBinder)binder.getSerivce();
		return binder;
	}
    public class SMPlayerBinder extends Binder{
    	 public SimpleMusicPlayerService getService() {
        	// TODO Auto-generated method stub
    		return SimpleMusicPlayerService.this;
    	}


		
    }
    @Override
    	public void onCreate() {
    		// TODO Auto-generated method stub
    		super.onCreate();

    		  try{ 
    		 	 Log.v("hao", Environment.getExternalStorageDirectory().toString());
    		     mPlayer.setDataSource(Environment.getExternalStorageDirectory().toString()+"/whatareyouworrying.mp3");
    		     Log.v("hao", "23432");
    		     mPlayer.prepare();
    		     mPlayer.setLooping(true);
    		    // mPlayer.start();
    		    }catch(IOException e){
    		 	   Log.v("hao", e.toString());
    		    }
    		  mPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mPlayer.seekTo(0);
					mPlayer.start();
				}
			});
    	}
    @Override
    	public void onDestroy() {
    		// TODO Auto-generated method stub
    		
    		
    		
    		if(mPlayer!=null)
    		{
    			mPlayer.stop();
    			mPlayer.release();
    		}
    		try {
				mPlayer.release();
			} catch (Exception e) {
				// TODO: handle exception
				  e.printStackTrace();
				
			}
    		super.onDestroy();
    	}
    public void play(){
    	if(mPlayer==null){
    		return;
    	}
    	if(mPlayer.isPlaying())
    	{
    		mPlayer.pause();
    	}
    	else{
    		mPlayer.start();
    	}
    }
    public void stop(){
    	if(mPlayer!=null)
    	{
    		mPlayer.stop();
    		try {
				mPlayer.prepare();
			} catch (IOException e) {
				// TODO: handle exception
			    e.printStackTrace();
			}
    	}
    }
    public void change_song(String song_path){
    	mPlayer.stop();
		try {
			mPlayer.prepare();
			mPlayer.reset();
		  mPlayer.setDataSource(song_path);
		     Log.v("hao", "23432");
		     mPlayer.prepare();
		     
		    // mPlayer.start();
		    }catch(IOException e){
		    	e.printStackTrace();
		    }
		mPlayer.start();
		 	  
    }
    public int getDuration(){
//		  if(state==STATE.IDLE)
//		  {
//			  return 0;
//		  }
		  return mPlayer.getDuration();
      }  
    public int getCuttentPosition(){
//    	if(state==STATE.IDLE)
//    		return 0;
    	return mPlayer.getCurrentPosition();
    }
    public String getmusicname(){
//    	if(state==STATE.IDLE)
//    		return 0;
    	return music_name;
    }
    public void setmusicname(String nametemp){
//    	if(state==STATE.IDLE)
//    		return 0;
    	music_name=nametemp;
    }
   public boolean getState(){
   	return mPlayer.isPlaying();
    }
   public void set_song_pos(int pos)
   {
	   mPlayer.seekTo(pos);
   }

}
