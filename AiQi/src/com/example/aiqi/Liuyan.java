package com.example.aiqi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import fuwuqi.JSONParser;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Liuyan extends Activity {
	private static String liuyansString;
	private Button liuyanbt;
	private static String pid,name,pid1;
	
	JSONParser jsonParser = new JSONParser();
	 int  success;
	    // url to update product
	 private TextView jilutx;
	    private static final String url_liuyan = "http://qq282844655.hk623-hl.6464.cn/liuyan.php";
	    private static final String TAG_SUCCESS = "success";
		 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
		    private static final String TAG_PRODUCT = "product";
		    private static final String TAG_LIUYAN = "liuyan";
		    private static String jilu="";
	 @Override
	protected void onCreate(Bundle savedlnstanceState){
	        super.onCreate(savedlnstanceState);
	        setContentView(R.layout.liuyan);
	        new Thread(){  
	            public void run(){  
	                new AnotherTask().execute("JSON");  
	            }  
	        }.start();
	        final Data app = (Data)getApplication();  
	        liuyanbt=(Button) findViewById(R.id.liuyanbt);
	        Intent intent1 = getIntent();
		       pid=intent1.getStringExtra("pid");
		 jilutx=(TextView) findViewById(R.id.jilu);
		jilutx.setMovementMethod(ScrollingMovementMethod.getInstance());
		       name=app.getUser();
		       pid1=app.getpid();
		   
		       liuyanbt.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						// TODO Auto-generated method stub
					    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
		                   arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
		                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
		                }   
		                else if(arg1.getAction() == MotionEvent.ACTION_UP){   
		                    arg0.setBackgroundResource(R.drawable.ee); 
		                  SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");       
					String    date    =    sDateFormat.format(new    java.util.Date());  
					liuyansString=( (EditText) findViewById(R.id.liuyan)).getText().toString();
					liuyansString=pid1+"#"+name+"#"+liuyansString+"#"+date+"#";
					      Toast.makeText(Liuyan.this,liuyansString,Toast.LENGTH_SHORT).show();
					 try {
			            	
			                List<NameValuePair> params = new ArrayList<NameValuePair>();
					      
					        params.add(new BasicNameValuePair("pid", pid));           
					            params.add(new BasicNameValuePair("liuyan",liuyansString));
					            JSONObject json = jsonParser.makeHttpRequest(url_liuyan,
			                    "POST", params);
			              success = json.getInt(TAG_SUCCESS);
			              
			                if (success == 1) {
			                    // successfully updated
			                	Toast.makeText(Liuyan.this,"留言成功",Toast.LENGTH_SHORT).show();
			                    finish();
			                } else {
			                	Toast.makeText(Liuyan.this,"留言失败",Toast.LENGTH_SHORT).show();
			                    // failed to update product
			                }
			            } catch (JSONException e) {
			                e.printStackTrace();
			            }
		                } 
						return false;
					}
				});
	 }
	 private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
		    	   jilutx.setText(jilu);
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		        getjilu();
		        return params[0];  
		    }  
		    }  
	 private void getjilu() {
		// TODO Auto-generated method stub
		 runOnUiThread(new Runnable() {
			 @Override
			public void run() { 
				 // Building Parameters
				int success;
			try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("pid",pid1));

                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params);
             success = json.getInt(TAG_SUCCESS);
            
                if (success == 1) {
          jilu="";
                	  JSONArray productObj = json
                              .getJSONArray(TAG_PRODUCT); // JSON Array

                      // get first product object from JSON Array
                      JSONObject product = productObj.getJSONObject(0);  
                            
                                String [] temp = null;  
                                temp =product.getString(TAG_LIUYAN ).split("#");  
                                if(temp.length>=4){
                                for(int i=0;i<temp.length;){
                              if(temp[i].equals(pid))
                            	  jilu=jilu+temp[i+3]+"\n"+temp[i+2]+"\n";
                                i=i+4;
                                }
                                }
                             
                }
				
				} catch (JSONException e) {
                    e.printStackTrace();
				}
	
			 }
		 });
	}
}