package com.example.hao_android_class11;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

	private Button btn1,btn2;
	private TextView showTextView;
	private LocationManager locationManager;
	private double lat,lng;
	Handler handler=new Handler(){
    	@Override
    public void handleMessage(Message msg) {
    	// TODO Auto-generated method stub
    	//super.handleMessage(msg);
    		Bundle bundle=msg.getData();
    		
    		showTextView.setText("position"+bundle.getString("result"));
    }};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(Button)findViewById(R.id.button1);
        btn2=(Button)findViewById(R.id.button2);
        showTextView=(TextView)findViewById(R.id.textView1);
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String provider=LocationManager.GPS_PROVIDER;
				Location location=locationManager.getLastKnownLocation(provider);
				 while(location==null)
				 { 
					 String provider2=LocationManager.GPS_PROVIDER;
				     location=locationManager.getLastKnownLocation(provider2);
					// location=locationManager.getLastKnownLocation(provider);
				 }
				
				if(location==null)
				{	showTextView.setText("位置信息暂时不可用");
					return ;
				}
				lat=location.getLatitude();
				lng=location.getLongitude();
				showTextView.setText("伟度："+String.valueOf(lat)+"\n"+"经度："+String.valueOf(lng));
			}
		});
        btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new haowebthread()).start();
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
    	private String  uri="http://api.map.baidu.com/geocoder?location="+String.valueOf(lat)+",%20"+String.valueOf(lng)+"&key=q5YGYreDRiytpuVkx38wAAE8&output=json";
    	@Override
    	public void run() {
    		// TODO Auto-generated method stub
    		HttpGet httpGet=new HttpGet(uri);
//    	    List<NameValuePair> paramsList=new ArrayList<NameValuePair>();
//    	    paramsList.add(new BasicNameValuePair("location", String.valueOf(lat)+","+String.valueOf(lng)));
//    	    paramsList.add(new BasicNameValuePair("output", "json"));
//    	    paramsList.add(new BasicNameValuePair("key", "q5YGYreDRiytpuVkx38wAAE8"));
//    	    try {
//				httpPost.setEntity(new UrlEncodedFormEntity(paramsList,HTTP.UTF_8));
//			} catch (UnsupportedEncodingException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
    	    Log.v("hao2", httpGet.toString());
    	    HttpClient httpClient=new DefaultHttpClient();
    	    try{
    	    	HttpResponse response=httpClient.execute(httpGet);
    	    	HttpEntity resultEntity=response.getEntity();
    	    	String xmlString=EntityUtils.toString(resultEntity);
    	    	JSONObject jsonObject=new JSONObject(xmlString);
    	    	JSONObject jsoncity=jsonObject.optJSONObject("result");
    	    	Log.v("hao2","sd"+jsoncity.optString("formatted_address"));
//    	        XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
//    	        factory.setNamespaceAware(true);
//    	        XmlPullParser xpp=factory.newPullParser();
//    	        xpp.setInput(new StringReader(xmlString));
//    	        int eventtype=xpp.getEventType();
//    	        while (eventtype!=XmlPullParser.END_DOCUMENT) {
//					if (eventtype==XmlPullParser.TEXT) {
//						Log.v("hao2","Text "+xpp.getText());
//					}
//					eventtype=xpp.next();
//					
//				}
    	        
    	        Message msgMessage=handler.obtainMessage();
    	        Bundle b=new Bundle();
    	        b.putString("result", jsoncity.optString("formatted_address")+" "+jsoncity.optString("business"));
    	        msgMessage.setData(b);
    	        handler.sendMessage(msgMessage);
    	    }
    	    catch(Exception e){
    	    	e.printStackTrace();
    	    }
    	}
    }
}
