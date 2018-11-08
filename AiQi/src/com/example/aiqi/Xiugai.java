package com.example.aiqi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import fuwuqi.JSONParser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Xiugai extends Activity{
	private Button btxiugai;
	
	

	
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
 int  success;
    // url to update product
    private static final String url_update_product = "http://qq282844655.hk623-hl.6464.cn/update_product.php";
    private static final String TAG_SUCCESS = "success";

    String pid,username;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
	       StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiugai);
		final Data app = (Data)getApplication();  
	
		btxiugai=(Button) findViewById(R.id.btxiugai);
		btxiugai.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
			    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
                   arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
                }   
                else if(arg1.getAction() == MotionEvent.ACTION_UP){   
                    arg0.setBackgroundResource(R.drawable.ee); 
                        String password1,password2,password3;
	     	password1=( (EditText) findViewById(R.id.editText10)).getText().toString();
	     	password2=( (EditText) findViewById(R.id.editText20)).getText().toString();
	     	password3=( (EditText) findViewById(R.id.editText30)).getText().toString();
	    
	            // Building Parameters
	 		Toast.makeText(Xiugai.this,password1,Toast.LENGTH_SHORT).show();
	           if(password1.equals(app.getpassword())){
	        	   if(!password2.isEmpty()){
	        	if(password2.equals(password3)){
	        		app.setpassword(password2);
	        		  // sending modified data through http request
	            // Notice that update product url accepts POST method
	            try {
	            	
	                List<NameValuePair> params = new ArrayList<NameValuePair>();
			        params.add(new BasicNameValuePair("pid", app.getpid()));  
			        params.add(new BasicNameValuePair("name", app.getUser()));           
			            params.add(new BasicNameValuePair("password",password2));
			            JSONObject json = jsonParser.makeHttpRequest(url_update_product,
	                    "POST", params);
	              success = json.getInt(TAG_SUCCESS);
	              
	                if (success == 1) {
	                    // successfully updated
	                	Toast.makeText(Xiugai.this,"修改密码成功!",Toast.LENGTH_SHORT).show();
	                    finish();
	                } else {
	               
	                    // failed to update product
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	            
	        	}else{
	        		Toast.makeText(Xiugai.this,"你输入的新密码不相同！",Toast.LENGTH_SHORT).show();
	        	}
	        	   }else{
	        		   Toast.makeText(Xiugai.this,"请输入新密码！",Toast.LENGTH_SHORT).show();
	        	   }
	        	   
	         }else{
	        	  Toast.makeText(Xiugai.this,"你输入的原密码错误！",Toast.LENGTH_SHORT).show();
	         }
	             
	            
                } 
				return false;
			}
		});

	}
}
	            // getting updated data from EditTexts
	        
	 

