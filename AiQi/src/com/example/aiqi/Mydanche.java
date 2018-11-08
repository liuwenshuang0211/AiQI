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
import android.app.Activity;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Mydanche extends Activity{
	 private ImageView imageView;
	 private TextView description,dinggou;
	 private Button xiajia;
	 JSONParser jsonParser = new JSONParser();
	private    String [] temp = null;  
	 private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	    private static final String TAG_DESCRIPTION1= "description1";
	    private static final String TAG_DINGGOU= "dinggou";
	    private static final String TAG_URL = "url1";
	    private static String pid,url="",dgsString="",description1="";
	    private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
	    private static final String url_xiajia = "http://qq282844655.hk623-hl.6464.cn/xiajia.php";
	 private Context mContext;	

	 @Override
	protected void onCreate(Bundle savedlnstanceState){
	        super.onCreate(savedlnstanceState);
	        setContentView(R.layout.mythings);
	        mContext=this;
	        new Thread(){  
	            public void run(){  
	                new AnotherTask().execute("JSON");  
	            }  
	        }.start();  
	        description=(TextView) findViewById(R.id.descrip11);
	        dinggou=(TextView) findViewById(R.id.dingou);
	        imageView=(ImageView) findViewById(R.id.danche111);
	        xiajia=(Button) findViewById(R.id.xiajiabt);
	        final Data app = (Data)getApplication();  
	        pid=app.getpid();
	      xiajia.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
				    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
	                   arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
	                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
	                }   
	                else if(arg1.getAction() == MotionEvent.ACTION_UP){   
	                    arg0.setBackgroundResource(R.drawable.ee); 
	                  	 if(!description1.equals("")){
						 if(dgsString.equals("")){
			     try {
		            	
		                List<NameValuePair> params = new ArrayList<NameValuePair>();
				        params.add(new BasicNameValuePair("pid", app.getpid()));  
				 
				            JSONObject json = jsonParser.makeHttpRequest(url_xiajia,
		                    "POST", params);
		       int       success = json.getInt(TAG_SUCCESS);
		              
		                if (success == 1) {
		                    // successfully updated
		                	if(url.length()>10)
		                	{	Toast.makeText(Mydanche.this,"下架单车成功",Toast.LENGTH_SHORT).show();
		                	final Data urlGeturl = (Data)getApplication();
							if(urlGeturl.getActivity1()){
							Factivity.instance.finish();
						urlGeturl.setActivityF1();
							}
		                    finish();}
		                	else {
		                		Toast.makeText(Mydanche.this,"下架单车失败",Toast.LENGTH_SHORT).show();
							}
		                } else {
		               
		                    // failed to update product
		                }
		            } catch (JSONException e) {
		                e.printStackTrace();
		            }
						 }else {
							 Toast.makeText(Mydanche.this,"该商品已被订购，请发货",Toast.LENGTH_SHORT).show();
						} 
						 }else {
							 Toast.makeText(Mydanche.this,"您无上架的单车",Toast.LENGTH_SHORT).show();
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
		        description.setText(description1);
		    	   if(temp.length>=3)
                       dinggou.setText("订购人:"+temp[0]+"\n地址:"+temp[1]+"\n电话:"+temp[2]);
		    	 showimage1();
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	getxiaoxi();
		   	
		        return params[0];  
		    }  
		    }  
	private void getxiaoxi() {
		// TODO Auto-generated method stub
		
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
                      
                             description1=product.getString(TAG_DESCRIPTION1);
                         
                             temp =product.getString(TAG_DINGGOU).split("#");  
                             dgsString=product.getString(TAG_DINGGOU);
                          
                           	url=product.getString(TAG_URL);
                    
                }
				
				} catch (JSONException e) {
                    e.printStackTrace();
				}
	}
	private void showimage1() {
		// TODO Auto-generated method stub
	
		  Picasso.with(Mydanche.this)
			.load(url)
			.error(R.drawable.ic_launcher).into(imageView);	
	   if(url=="")
	          description.setText("您暂无上架商品");
	 
	}
}
