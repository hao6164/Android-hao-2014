package com.example.hao_android_class8;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Button btn_confirmButton,btn_confirmButton2;
	private ListView list;
	private Cursor c;
	private haosql haosqlite=new haosql(this);
	private int position2;
	private EditText dia_stuid;
	private EditText dia_name;
	private EditText dia_class;
	private EditText dia_phone;
	private AlertDialog dialog02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=(ListView)findViewById(R.id.listView1);
        c=haosqlite.queue_all();
        SimpleCursorAdapter adapter =new SimpleCursorAdapter(getApplicationContext(), R.layout.item,c,
        		new String[]{"stuid","name","class","phone"}, new int[]{R.id.id_show,R.id.name_show,R.id.class_show,R.id.phone_show}, 0);
        list.setAdapter(adapter);
      
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				position2=position;
				// TODO Auto-generated method stub
				LayoutInflater factory = LayoutInflater.from(MainActivity.this);
				//获得自定义对话框
				View view2 = factory.inflate(R.layout.update_record, null);
				
				dialog02 = new AlertDialog.Builder(MainActivity.this)
		 		 .setIcon(android.R.drawable.btn_star)
		 		 .setTitle("Update info")
		         .setView(view2)
		         .create();
				dialog02.show();
				c.moveToFirst();
				for (int i2 = 0; i2 <position2; i2++) {
					c.moveToNext();
				}
				dia_stuid=(EditText)view2.findViewById(R.id.id_edit2);
				dia_name=(EditText)view2.findViewById(R.id.name_edit2);
				dia_class=(EditText)view2.findViewById(R.id.class_edit2);
				dia_phone=(EditText)view2.findViewById(R.id.phone_edit2);
				dia_stuid.setText(String.valueOf(c.getInt(c.getColumnIndex("stuid"))));
				dia_name.setText(c.getString(c.getColumnIndex("name")));
				dia_class.setText(String.valueOf(c.getInt(c.getColumnIndex("class"))));
				dia_phone.setText(c.getString(c.getColumnIndex("phone")));
				
				btn_confirmButton2=(Button)view2.findViewById(R.id.btn_confirm4);
				btn_confirmButton2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(dia_name.getText().toString().length()==0||dia_class.getText().toString().length()==0||dia_phone.getText().toString().length()==0||dia_stuid.getText().toString().length()==0){
							Toast.makeText(MainActivity.this, "please finish all info",Toast.LENGTH_LONG).show();  
							return;
							}
							
						
						c.moveToFirst();
						for (int i2 = 0; i2 <position2; i2++) {
							c.moveToNext();
						}
		        		   haosqlite.delete(c.getInt(c.getColumnIndex("stuid")));
				        haosqlite.insert(Integer.parseInt(dia_stuid.getText().toString()),
				        		dia_name.getText().toString(),Integer.parseInt(dia_class.getText().toString()),
				        		dia_phone.getText().toString());
				        
				        c=haosqlite.queue_all();
				        SimpleCursorAdapter adapter =new SimpleCursorAdapter(getApplicationContext(), R.layout.item,c,
	        	        		new String[]{"stuid","name","class","phone"}, new int[]{R.id.id_show,R.id.name_show,R.id.class_show,R.id.phone_show}, 0);
	        	        list.setAdapter(adapter);
						dialog02.dismiss();
					}
				});
				
			}
		}) ;
        list.setOnItemLongClickListener(new OnItemLongClickListener() {
        	@Override
        	public boolean onItemLongClick(AdapterView<?> parent, View view,
        			int position, long id) {
                position2=position;
        		new AlertDialog.Builder(MainActivity.this)  
                .setTitle("Delete contract")  
               .setMessage("Are you sure to delete this contract ?")   
               .setPositiveButton("Confirm",   
               new DialogInterface.OnClickListener(){  
                         public void onClick(DialogInterface dialoginterface, int i){   
                                        //按钮事件    
                c.moveToFirst();
        		// TODO Auto-generated method stub
        		for (int i2 = 0; i2 <position2; i2++) {
					c.moveToNext();
				}
        		   haosqlite.delete(c.getInt(c.getColumnIndex("stuid")));
        	        c=haosqlite.queue_all();
        	        SimpleCursorAdapter adapter =new SimpleCursorAdapter(getApplicationContext(), R.layout.item,c,
        	        		new String[]{"stuid","name","class","phone"}, new int[]{R.id.id_show,R.id.name_show,R.id.class_show,R.id.phone_show}, 0);
        	        list.setAdapter(adapter);
                                   Toast.makeText(MainActivity.this, "さよなら",Toast.LENGTH_LONG).show();  
                                     }   
                             }).show();  
        		
        		return true;
        		
        		
        	}
		});
        btn_confirmButton=(Button)findViewById(R.id.button1);
        btn_confirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, record.class);
				startActivity(intent);
				finish();
				
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
    
}
