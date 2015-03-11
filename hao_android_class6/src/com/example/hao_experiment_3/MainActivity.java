package com.example.hao_experiment_3;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button confirm_button=(Button)findViewById(R.id.button1);
       
        confirm_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,Music_box.class);
				startActivityForResult(intent, 1);
				TextView textViewshow=(TextView)findViewById(R.id.music_show);
	    		textViewshow.setText("你的手机好卡，笔试你= 。=");
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override 
    protected void onActivityResult(int requestcode,int resultcode,Intent resultdata){
    	if(requestcode==1)
    	{
    		String name=resultdata.getExtras().getString("fruits_name");
    		TextView textViewshow=(TextView)findViewById(R.id.music_show);
    		textViewshow.setText(name);
    	}
    }
}
