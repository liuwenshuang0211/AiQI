package com.example.aiqi;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import fuwuqi.ImageServi;
import fuwuqi.JSONParser;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Baoming extends Activity{
	JSONParser jsonParser = new JSONParser();
	 int  success;
	 private ProgressBar baomingjiazai;
	 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";

	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	    private static final String url_baoming= "http://qq282844655.hk623-hl.6464.cn/baoming.php";
	    private static final String url_baoming1= "http://qq282844655.hk623-hl.6464.cn/baoming1.php";
	private TextView baomingTextView,xingmingTextView;
	private ImageView touxiangImageView;
	private Button baomingButton;
	int number;
	String pid,name,phonenumber;
	  private static  String[] URLS = new String[100];   
	  private static  String[] description = new String[100];   
	  private static  String[] names = new String[100];   
	  private static  String[] pids= new String[100];   
	 @Override
		protected void onCreate(Bundle savedlnstanceState){
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
	      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		        super.onCreate(savedlnstanceState);
		        setContentView(R.layout.baoming);
		        Intent intent1 = getIntent();
		String	       num=intent1.getStringExtra("one");
			       number=Integer.parseInt(num); 
		        baomingTextView=(TextView) findViewById(R.id.baomingxinxi);
		        xingmingTextView=(TextView) findViewById(R.id.xingming111);
		        touxiangImageView=(ImageView) findViewById(R.id.touxiang111);
		        baomingButton=(Button) findViewById(R.id.baoming11);
		        baomingjiazai=(ProgressBar) findViewById(R.id.baomingjiazai);
		        baomingButton.setOnTouchListener(new OnTouchListener() {
		        	
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						// TODO Auto-generated method stub
					    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
		                   arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
		                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
		                }   
		                else if(arg1.getAction() == MotionEvent.ACTION_UP){   
		                    arg0.setBackgroundResource(R.drawable.ee); 
		                    
		                 if(pid!=""&&pid!="null"&&pid!=null){
		                	if(panduan()){
		                  	mybaoming();
					baoming();}else{
						Toast.makeText(Baoming.this,"你已经报名该活动",Toast.LENGTH_SHORT).show();
					}
		                 }   else {
		               	 Toast.makeText(Baoming.this,"请先登录",Toast.LENGTH_SHORT).show();
		   			}
		                } 
						return false;
					}
				});
		      
		        new Thread(){  
			          public void run(){  
			              new AnotherTask().execute("JSON");  
			          }  
			      }.start();  
	 }
	 public boolean panduan(){
			try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("pid",pids[number]));

                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params);
             success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	
                	  JSONArray productObj = json
                              .getJSONArray(TAG_PRODUCT); // JSON Array
                      // get first product object from JSON Array
                      JSONObject product = productObj.getJSONObject(0);
                      if(product.getString("baoming" )!=null){
                String [] temp = null;  
                temp =product.getString("baoming" ).split("#");      
                for(int i=0;i<temp.length;){
                	if(pid.equals(temp[i]))
                	{	return false;}
                	else{
                		i=i+3;
                	}	
              
                }         
                      }
                }	
				} catch (JSONException e) {
                    e.printStackTrace();
				}
		return true;
		 
	 }
	 public void mybaoming(){
		 
		  try {
          	
              List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("pid", pid));  
		   
		            params.add(new BasicNameValuePair("baoming1",pids[number]+"#"));
		            JSONObject json = jsonParser.makeHttpRequest(url_baoming1,
                  "POST", params);
            success = json.getInt(TAG_SUCCESS);
            
              if (success == 1) {
                  // successfully updated
             
                 
              } else {
             
                  // failed to update product
              }
          } catch (JSONException e) {
              e.printStackTrace();
          }
		 
		 
		 
	 }
	 public void baoming(){
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
                      phonenumber=product.getString("dianhua");
                }	
				} catch (JSONException e) {
                    e.printStackTrace();
				}
			if(phonenumber!=""&&phonenumber!="null"&&phonenumber!=null){
			 try { 	
				name=pid+"#"+name+"#"+phonenumber+"#";
	                List<NameValuePair> params = new ArrayList<NameValuePair>();
			        params.add(new BasicNameValuePair("pid", pids[number]));           
			            params.add(new BasicNameValuePair("name",name));
			          
			            JSONObject json = jsonParser.makeHttpRequest(url_baoming,
	                    "POST", params);
	              success = json.getInt(TAG_SUCCESS);
	              
	                if (success == 1) {
	                    // successfully updated
	                	Toast.makeText(Baoming.this,"报名成功",Toast.LENGTH_SHORT).show();
	                    finish();
	                } else {
	                	Toast.makeText(Baoming.this,"报名失败",Toast.LENGTH_SHORT).show();
	                    // failed to update product
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
			}else {
				Toast.makeText(Baoming.this,"请先填写手机号码",Toast.LENGTH_SHORT).show();
			}
           
	 }
	
	 private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
		  baomingTextView.setText(description[number]);
		  xingmingTextView.setText(names[number]);
		  baomingjiazai.setVisibility(View.GONE );
			Picasso.with(Baoming.this)
			.load(URLS[number])
			.error(R.drawable.ic_launcher).into(touxiangImageView);	
//		      try {
//		          Bitmap bitmap = ImageServi.getBitmapFromServer(URLS[number]);
//		          touxiangImageView.setImageBitmap(bitmap);
//		     
//		      } catch (Exception e) {
//		          e.printStackTrace();
//		 
//		      }
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	 final Data urlGeturl = (Data)getApplication();  
			      
			       description=urlGeturl.Getdescription();	
			       URLS   =urlGeturl.geturls1();  
			     names=urlGeturl.getname1();
			     pids=urlGeturl.getpids1();
			     pid=urlGeturl.getpid();
			     name=urlGeturl.getUser();
		        return params[0];  
		    }  
		    }  

}
