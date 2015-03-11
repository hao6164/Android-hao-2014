package com.example.hao_android_class10;

import java.io.IOException;

import android.app.Activity;
import android.app.DownloadManager.Request;
import android.app.ProgressDialog;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.ksoap2.*;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class MainActivity extends Activity {

	private EditText editText;
	private Bitmap bitmap;
	private ImageView imageView;
	private ProgressDialog dialog4; 

	 private Handler handler=new Handler()
	    {
	    	@Override
	    	public void handleMessage(Message msg) {
	    		// TODO Auto-generated method stub
	    		
	    		if (msg.what==2) {
					Toast toast=Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);  
		    	//显示toast信息  
		    	toast.show();
				}
	    		else {
				
	    		imageView.setImageBitmap(bitmap);
	    		}
	    		dialog4.cancel();
	    		super.handleMessage(msg);
	    	}
	    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button=(Button)findViewById(R.id.button1);
		editText=(EditText)findViewById(R.id.editText1);
		imageView=(ImageView)findViewById(R.id.imageView1);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog4 = ProgressDialog.show(MainActivity.this, "提示", "查询中。。。",  
			            false, true);
				Thread mThread=new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						haoksoap(editText.getText().toString());
					}
				});
				mThread.start();
				
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
	private void haoksoap(String string) {
		// TODO Auto-generated method stub
		String urlString="http://webservice.webxml.com.cn/WebServices/ValidateCodeWebService.asmx";
		String namespace="http://WebXml.com.cn/";
		String methodname="enValidateByte";
		String SOAPACTION="http://WebXml.com.cn/enValidateByte";
		SoapObject requestObject=new SoapObject(namespace, methodname);
		requestObject.addProperty("byString",string);
		SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER12);
		envelope.dotNet=true;
		envelope.setOutputSoapObject(requestObject);
		HttpTransportSE hTransportSE=new HttpTransportSE(urlString);
	    try {
			hTransportSE.call(SOAPACTION, envelope);
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    SoapObject result=(SoapObject)envelope.bodyIn;
	  
	    if (result==null) {
	    	handler.obtainMessage(2).sendToTarget();
	    	
			return;
		}
	    SoapPrimitive detail=(SoapPrimitive)result.getProperty("enValidateByteResult");
	    byte[] data=Base64.decode(detail.toString(),0);
	    bitmap=BitmapFactory.decodeByteArray(data,0, data.length);
	    handler.obtainMessage(1).sendToTarget();
	}
}
