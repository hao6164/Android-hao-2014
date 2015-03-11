package com.sysu.niuniuleyuan.function;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;




public class ImageFunction{
	/*
	 * input:String urlString
	 * output:Bitmap bitmap
	 */
	public static Bitmap getBitmap(String urlString){
		Bitmap bitmap;
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			if(conn.getResponseCode()==200){
				InputStream in = conn.getInputStream();
				//Log.v("test", DataFunction.inputstreamToString(in));
				byte[] data = DataFunction.inputstreamToByteArray(in);
				bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				return bitmap;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

