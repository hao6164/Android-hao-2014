package com.example.hao_android_class7;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes.Name;

import android.R.array;
import android.R.bool;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    private AutoCompleteTextView name;
    private EditText password;
    private TextView show_xml;
    private SharedPreferences sp;
    private Editor editor;
    private int xml_item_max;
    private Button btn_insert,btn_delete,btn_queue,btn_clear,btn_select;
    private ArrayList<String> autoStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setdefault();
        setdefaultEvent();
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode==12) {
			if (resultCode==Activity.RESULT_OK) {
				
				//readfile(data.getExtras().getString("path"));
				haoasynctask readhao=new haoasynctask();
				readhao.execute(data.getExtras().getString("path"));
			}
		}
    }
    private String readfile(String file_path2) {
		// TODO Auto-generated method stub
		StringBuffer buffer=new StringBuffer();
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file_path2)));
			String str;
		    
			try {
				while((str=reader.readLine())!=null)
				{
					buffer.append(str+"\n");
				}
				return buffer.toString();
				//show_xml.setText(buffer.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				return "error";
		}
		
		
	}
	private void showxml() {
		// TODO Auto-generated method stub
		StringBuffer buffer=new StringBuffer();
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("data/data/com.example.hao_android_class7/shared_prefs/hao_database.xml")));
			String str;
			try {
				while((str=reader.readLine())!=null)
				{
					buffer.append(str+"\n");
				}
				show_xml.setText(buffer.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
    private void setdefaultEvent() {
		// TODO Auto-generated method stub
		btn_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			editor.clear();
			editor.commit();
			autoStr.clear();
			setdropdown();
			showxml();
			}
		});
		btn_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(check(name.getText().toString()))
				{
                   editor.remove(name.getText().toString());
                   
           	       autoStr.remove(name.getText().toString());
           	       String xml_users="";
           	       for(int i=0;i<autoStr.size();i++)
           	       {
           	          xml_users+=","+autoStr.get(i); 
           	       }
                   editor.putString("Allusers", xml_users);
				}
				
				editor.commit();
				showxml();
			}
		});
		btn_insert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String test=password.getText().toString();
				if (name.getText().toString().isEmpty()||password.getText().toString().isEmpty()) 
				{
					show_xml.setText("please input the username and password");
					return;
				}
				
				editor.putString(name.getText().toString(),password.getText().toString());
				//editor.putString("password"+xml_item_max,password.getText().toString() );
				//editor.putInt("password",xml_item_max);
				if(!check(name.getText().toString()))
				{
                   editor.putString("Allusers",sp.getString("Allusers", "")+","+name.getText().toString());
                   
                   autoStr.add(name.getText().toString());
                   setdropdown();
              
				}
				
				editor.commit();
			    showxml();
			}

			

		});
		btn_queue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String resultout=sp.getString(name.getText().toString(), "none");
				if(resultout!="none")
				{
					show_xml.setText("key="+name.getText().toString()+",value="+resultout);
				}
				else
				{
					show_xml.setText("no such account");
				}
				
				
			}
		});
		btn_select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intents_select=new Intent();
				intents_select.setClass(MainActivity.this, selectmenu.class);
				startActivityForResult(intents_select,12);
			}
		});
	}

    private boolean check(String checkstr) {
		// TODO Auto-generated method stub

   		String resultout=sp.getString(checkstr, "none");
   		if (resultout=="none") {
			return false;
		}
   		else
   			return true;
//    		sp.get
//			editor.remove(key)

	}
	private void setdefault() {
		// TODO Auto-generated method stub
    	sp=getSharedPreferences("hao_database", Activity.MODE_PRIVATE);
        editor=sp.edit();

        
        btn_select=(Button)findViewById(R.id.btn_select);
        btn_clear=(Button)findViewById(R.id.button_clear);
        btn_insert=(Button)findViewById(R.id.button_insert);
        btn_queue=(Button)findViewById(R.id.button_queue);
        btn_delete=(Button)findViewById(R.id.button_delete);
	    password=(EditText)findViewById(R.id.passwordinput);
	    show_xml=(TextView)findViewById(R.id.showxml);
	   
	    String userslist=sp.getString("Allusers", "");
	    String[] autoStrtemp=userslist.split(",");
	    autoStr=new ArrayList<String>(Arrays.asList(autoStrtemp)); 
	    autoStr.remove("");
	    setdropdown();
	 //   autoStr=(ArrayList<String>) Arrays.asList(listtemp);
	   // xml_item_max=sp.getInt("xml_item_max", 0);
	}


	private void setdropdown() {
		// TODO Auto-generated method stub
		   name=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
	        ArrayAdapter<String> autoadapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,autoStr);
	        name.setAdapter(autoadapter);
	        name.setThreshold(1);
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
  
    class haoasynctask extends AsyncTask<String, Integer, String>
    {
    	private String readbuf;
    	@Override
    	protected String doInBackground(String... params) {
    		// TODO Auto-generated method stub
    		readbuf=readfile(params[0]);
    		return "done";
    	}
    	@Override
    	protected void onPostExecute(String result) {
    		// TODO Auto-generated method stub
    		show_xml.setText(readbuf.substring(1,1000));
    		super.onPostExecute(result);
    	}
    }
    
}
