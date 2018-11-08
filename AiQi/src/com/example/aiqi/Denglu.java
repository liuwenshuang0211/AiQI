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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.LoginFilter.UsernameFilterGeneric;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;



public class Denglu extends Activity {
	private ProgressDialog pDialog;
	 String pid;
	
	 
	JSONParser jsonParser = new JSONParser();
	 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
	 private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	    private static final String TAG_PID = "pid";
	    private static final String TAG_NAME = "name";
	    private static final String TAG_PRICE = "price";
	    private static final String TAG_DESCRIPTION = "description";
	    private static final String TAG_PASSWORD = "password";
	 
	private Button button;
	private Button zc;
	 @Override
	protected void onCreate(Bundle savedlnstanceState){
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedlnstanceState);
		setContentView(R.layout.denglu);
		final Data app = (Data)getApplication();  
		 button=(Button)findViewById(R.id.denglubt);
		 zc=(Button) findViewById(R.id.dzc);
		 zc.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
				    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
	                   arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
	                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
	                }   
	                else if(arg1.getAction() == MotionEvent.ACTION_UP){   
	                    arg0.setBackgroundResource(R.drawable.ee); 
	                    Intent intent = new Intent(Denglu.this,ZhuCe.class);
				         startActivity(intent); 
	                }
					return false;
				}
				
				});
		
			button.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
				    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
	                   arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
	                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
	                }   
	                else if(arg1.getAction() == MotionEvent.ACTION_UP){   
	                    arg0.setBackgroundResource(R.drawable.ee); 
	                    pid=( (EditText) findViewById(R.id.editText1)).getText().toString();
						String pwd=( (EditText) findViewById(R.id.editText2)).getText().toString();
				
						 // Building Parameters
						int success;
					try {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("pid",pid));
    
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params);
                     success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                        	
                        	  JSONArray productObj = json
                                      .getJSONArray(TAG_PRODUCT); // JSON Array
   
                              // get first product object from JSON Array
                              JSONObject product = productObj.getJSONObject(0);
                            
                            if(product.getString(TAG_PASSWORD).equals(pwd)){
                            	app.setpid(pid);
                            	app.setuser(product.getString(TAG_NAME));
                            	app.setpassword(product.getString(TAG_PASSWORD));     
                            	gongneng.instance.finish();
                            	Toast.makeText(Denglu.this,"µ«¬Ω≥…π¶",Toast.LENGTH_SHORT).show();
                            	Intent intent=new Intent();							
								intent.setClass(Denglu.this,gongneng.class);
					
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			    	     	intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								startActivity(intent); 
								final Data urlGeturl = (Data)getApplication();
								if(urlGeturl.getActivity()){
								Xiaoxi.instance.finish();
							urlGeturl.setActivityF();
								}
								finish();
				
                            }
                            else{
                            	Toast.makeText(Denglu.this,"ƒ„ ‰»Îµƒ√‹¬Î¥ÌŒÛ£°",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                        	Toast.makeText(Denglu.this,"ƒ„ ‰»Îµƒ’À∫≈≤ª¥Ê‘⁄£°",Toast.LENGTH_SHORT).show();
                        }
						
						} catch (JSONException e) {
	                        e.printStackTrace();
						}
			
			
	                } 
					return false;
				}
			});
		
		
	 }
			 
}

