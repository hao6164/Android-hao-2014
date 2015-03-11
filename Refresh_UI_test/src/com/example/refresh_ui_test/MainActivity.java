package com.example.refresh_ui_test;

import android.R.integer;
import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	private Button btn_asy,btn_run,btn_dir,btn_handler;
	private TextView textView;
	private Handler h=new Handler();
	private Runnable r=new Runnable() {
		public void run() {
			textView.setText("run");
		}
	};
	private Handler h2=new Handler()
	{
		 @Override
		public void handleMessage(Message msg) {
			if (msg.what==2) {
				textView.setText("handler");
			}
			super.handleMessage(msg);
		}
	};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_asy=(Button)findViewById(R.id.btn_asy);
        btn_run=(Button)findViewById(R.id.btn_run);
        btn_handler=(Button)findViewById(R.id.btn_handler);
        btn_dir=(Button)findViewById(R.id.btn_dir);
        textView=(TextView)findViewById(R.id.textView1);
        btn_run.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread thread=new Thread(new Runnable() {
					public void run() {
						
						

				        h.post(r);
					}
				});
				thread.start();
			}
		});
        btn_dir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread thread=new Thread(new Runnable() {
					public void run() {
						textView.setText("dir");
					}
				});
				thread.start();
			}
		});
 btn_asy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread thread=new Thread(new Runnable() {
					public void run() {
						haoasy hao=new haoasy();
						hao.execute(1,2,4,5);
					}
				});
				thread.start();
			}
		});
        btn_handler.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread thread=new Thread(new Runnable() {
					public void run() {

						h2.obtainMessage(2).sendToTarget();
					}
				});thread.start();
			}
		});
    }

    private class haoasy extends AsyncTask<Integer, Integer, Integer>
    {

		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			Integer a,b,c,d;
			a=params[0];
					b=params[1];
							c=params[2];
							d=		params[3];
			return params[0];
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			if (result==1) {
				textView.setText("asy");
			}
			super.onPostExecute(result);
		}
    	
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
