package com.example.hao_android_class2;

import java.util.jar.Attributes.Name;

import org.w3c.dom.Text;

import android.support.v7.app.ActionBarActivity;
import android.R.string;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	private Button btn1;
	private EditText  passport;
	private EditText name;
	private ImageButton imgbutton1;
	private TextView hintout;
	private LinearLayout layout2;
	private Context context;
	private TextView motiontext;
	private TextView motiontext2;
	private LinearLayout layout;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(Button)findViewById(R.id.button1 );
        passport=(EditText )findViewById(R.id.editText1  );
        name=(EditText )findViewById(R.id.editText2 );
        hintout=(TextView)findViewById(R.id.textView1);
        imgbutton1=(ImageButton)findViewById(R.id.imageButton1 );
        layout2=(LinearLayout)findViewById(R.id.linearlayout2);
        layout=(LinearLayout)findViewById(R.id.LinearLayout1);
        btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imgbutton1 .setImageResource(R.drawable.confirm);
				passport.setText("");
				name.setText("");
				passport.setVisibility(0);
				name.setVisibility(0);
				layout2.removeAllViews();
				hintout .setText("");
			}
		});
        imgbutton1 .setOnClickListener(new OnClickListener() {
        
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String nametemp=name.getText().toString();
				String passporttemp=passport.getText().toString();
				if(nametemp.equals("Android")&&passporttemp.equals("2014"))
				{
					Log.i("tag","right");
					imgbutton1 .setImageResource(R.drawable.pikachu1);
					passport.setVisibility(8);
					name.setVisibility(8);
					hintout .setText("账号密码正确");
					
				}
				else
				{
					Log.i("tag","wrong");
					imgbutton1 .setImageResource(R.drawable.pikachu2 );
					hintout.setText("用户名或密码错误");
					passport.setText("");
					name.setText("");
				}
			}
		});
        context=this;
        imgbutton1.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				
		        motiontext2=new TextView(context);
		        motiontext2.setText("我是石头里蹦出来的哦");
		        layout2.addView(motiontext2);
				return false;
			}
		});
        
        
        motiontext=new TextView(context);
        motiontext.setText("我是石头里蹦出来的哦");
        layout.addView(motiontext);
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
