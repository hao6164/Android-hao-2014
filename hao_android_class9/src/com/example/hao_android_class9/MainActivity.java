package com.example.hao_android_class9;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.support.v7.app.ActionBarActivity;
import android.R.string;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.System;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	 private Button button1;
     private EditText editText1;
     private TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=(Button)findViewById(R.id.button1);
        editText1=(EditText)findViewById(R.id.editText1);
        textView1=(TextView)findViewById(R.id.textView1);
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textView1.setText("≤È—Ø÷–°£°£°£°£°£");
				new Thread(new haowebthread(textView1.getText().toString())).start();
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
    class haowebthread implements Runnable{
    	private String qqnumString;
    	private String  uri="http://webservice.webxml.com.cn/webservices/qqOnlineWebService.asmx/qqCheckOnline";
    	public haowebthread(String num){
    	qqnumString=num;
    	}
    	@Override
    	public void run() {
    		// TODO Auto-generated method stub
    		HttpPost httpPost=new HttpPost(uri);
    	    List<NameValuePair> paramsList=new ArrayList<NameValuePair>();
    	    paramsList.add(new BasicNameValuePair("qqCode", qqnumString));
    	    try {
				httpPost.setEntity(new UrlEncodedFormEntity(paramsList,HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
    	    DefaultHttpClient httpClient=new DefaultHttpClient();
    	    try{
    	    	HttpResponse response=httpClient.execute(httpPost);
    	    	HttpEntity resultEntity=response.getEntity();
    	    	String xmlString=EntityUtils.toString(resultEntity);
    	        XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
    	        factory.setNamespaceAware(true);
    	        XmlPullParser xpp=factory.newPullParser();
    	        xpp.setInput(new StringReader(xmlString));
    	        int eventtype=xpp.getEventType();
    	        while (eventtype!=XmlPullParser.END_DOCUMENT) {
					if (eventtype==XmlPullParser.TEXT) {
						Log.v("hao2","Text "+xpp.getText());
					}
					eventtype=xpp.next();
					
				}
    	        Handler handler=new Handler(){
    	        	@Override
    	        public void handleMessage(Message msg) {
    	        	// TODO Auto-generated method stub
    	        	//super.handleMessage(msg);
    	        		Bundle bundle=msg.getData();
    	        		textView1.setText((String)bundle.get("result"));
    	        }};
    	        Message msgMessage=handler.obtainMessage();
    	        Bundle b=new Bundle();
    	        b.putString("result", "hao6164");
    	        msgMessage.setData(b);
    	    }
    	    catch(Exception e){
    	    }
    	}
    }
}
