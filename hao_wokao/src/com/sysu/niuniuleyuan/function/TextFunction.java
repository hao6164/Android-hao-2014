package com.sysu.niuniuleyuan.function;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TextFunction {

	public static String getText(String urlString){
		try{
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			if(conn.getResponseCode()==200){
				InputStream in = conn.getInputStream();
				String text = DataFunction.inputstreamToChinaString(in);
				return text;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
