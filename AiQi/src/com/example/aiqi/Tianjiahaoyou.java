package com.example.aiqi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fuwuqi.JSONParser;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Tianjiahaoyou  extends Activity{
	JSONParser jsonParser = new JSONParser();
	 int  success;
	 private Button haoyoubt;
	 private static String pid;
	    // url to update product
	    private static final String url_haoyou = "http://qq282844655.hk623-hl.6464.cn/haoyou.php";
	    private static final String url_getpid = "http://qq282844655.hk623-hl.6464.cn/getpid.php";
	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	    private static final String TAG_PID= "pid";
	protected void onCreate(Bundle savedlnstanceState){
		
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.tianjiahaoyou);
        final Data app = (Data)getApplication();  
        pid=app.getpid();
      haoyoubt=(Button) findViewById(R.id.haoyoutianjiabt);
      haoyoubt.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
			    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
                 arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
                  Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
              }   
              else if(arg1.getAction() == MotionEvent.ACTION_UP){   
                  arg0.setBackgroundResource(R.drawable.ee); 
                		String haoyou = null ,pid1 = null;
			haoyou=( (EditText) findViewById(R.id.haoyouname)).getText().toString();
			int success;
			try {
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("name",haoyou));

	            // getting product details by making HTTP request
	            // Note that product details url will use GET request
	            JSONObject json = jsonParser.makeHttpRequest(url_getpid, "GET", params);
	         success = json.getInt(TAG_SUCCESS);
	            if (success == 1) {
	            	
	            	  JSONArray productObj = json
	                          .getJSONArray(TAG_PRODUCT); // JSON Array

	                  // get first product object from JSON Array
	                  JSONObject product = productObj.getJSONObject(0);
	                  pid1=product.getString(TAG_PID);
	            }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
			haoyou=pid1+"#"+haoyou+"#";
			if(pid1!=null&&!pid1.equals(pid)){
			 try { 	
					Toast.makeText(Tianjiahaoyou.this,pid,Toast.LENGTH_SHORT).show();
	                List<NameValuePair> params = new ArrayList<NameValuePair>();
			        params.add(new BasicNameValuePair("pid", pid));           
			            params.add(new BasicNameValuePair("haoyou",haoyou));
			            JSONObject json = jsonParser.makeHttpRequest(url_haoyou,
	                    "POST", params);
	              success = json.getInt(TAG_SUCCESS);
	              
	                if (success == 1) {
	                    // successfully updated
	                	Toast.makeText(Tianjiahaoyou.this,"添加好友成功",Toast.LENGTH_SHORT).show();
	                    finish();
	                } else {
	                	Toast.makeText(Tianjiahaoyou.this,"添加好友失败",Toast.LENGTH_SHORT).show();
	                    // failed to update product
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
			}else {
				Toast.makeText(Tianjiahaoyou.this,"你输入的用户不存在",Toast.LENGTH_SHORT).show();
			}
              } 
				return false;
			}
		});

		}

	

	
}
