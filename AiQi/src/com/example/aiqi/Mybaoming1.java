package com.example.aiqi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import fuwuqi.ImageServi;
import fuwuqi.JSONParser;

public class Mybaoming1 extends Activity{
	JSONParser jsonParser = new JSONParser();
	 int  success;
	 private ProgressBar huodongjiazai;
	  private static final String url_haoyou = "http://qq282844655.hk623-hl.6464.cn/haoyou.php";
	 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";

	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	    private static final String url_baoming= "http://qq282844655.hk623-hl.6464.cn/baoming.php";
	    private static final String url_baoming1= "http://qq282844655.hk623-hl.6464.cn/baoming1.php";
	private TextView huodongTextView,xingmingTextView,dianhuaTextView;
	private ImageView touxiangImageView;
	private Button haoyou;
	
	String pid,name,phonenumber,pid1,url,description;

	 @Override
		protected void onCreate(Bundle savedlnstanceState){
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
	      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		        super.onCreate(savedlnstanceState);
		        setContentView(R.layout.mybaoming1);
		        Intent intent1 = getIntent();
				 pid1=intent1.getStringExtra("one");
				 huodongTextView=(TextView) findViewById(R.id.huodongxingxi);
				 xingmingTextView=(TextView) findViewById(R.id.xingming123);
				 dianhuaTextView=(TextView) findViewById(R.id.dianhuahaoma);
				 huodongjiazai=(ProgressBar) findViewById(R.id.baomingjiazai1213);
				 touxiangImageView=(ImageView) findViewById(R.id.touxiang123);
				 haoyou=(Button) findViewById(R.id.haoyoutianjia);
				 
				 
				 haoyou.setOnTouchListener(new OnTouchListener() {
						
						@Override
						public boolean onTouch(View arg0, MotionEvent arg1) {
							// TODO Auto-generated method stub
						    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
					           arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
					            Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
					        }   
					        else if(arg1.getAction() == MotionEvent.ACTION_UP){   
					            arg0.setBackgroundResource(R.drawable.ee); 
					          String haoyou = null;
									haoyou=pid1+"#"+name+"#";
									 try {
							            	
							                List<NameValuePair> params = new ArrayList<NameValuePair>();
									        params.add(new BasicNameValuePair("pid", pid));           
									            params.add(new BasicNameValuePair("haoyou",haoyou));
									            JSONObject json = jsonParser.makeHttpRequest(url_haoyou,
							                    "POST", params);
							              success = json.getInt(TAG_SUCCESS);
							              
							                if (success == 1) {
							                    // successfully updated
							                	Toast.makeText(Mybaoming1.this,"添加好友成功",Toast.LENGTH_SHORT).show();
							                   
							                } else {
							                	Toast.makeText(Mybaoming1.this,"添加好友失败",Toast.LENGTH_SHORT).show();
							                    // failed to update product
							                }
							            } catch (JSONException e) {
							                e.printStackTrace();
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
	 private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
	huodongTextView.setText(description);
	xingmingTextView.setText(name);
	dianhuaTextView.setText("手机号码："+phonenumber);
				huodongjiazai.setVisibility(View.GONE );
				Picasso.with(Mybaoming1.this)
				.load(url)
				.error(R.drawable.ic_launcher).into(touxiangImageView);	
//		      try {
//		          Bitmap bitmap = ImageServi.getBitmapFromServer(url);
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
		    	 pid=urlGeturl.getpid();
			   gethuodong();
		        return params[0];  
		    }  
		    }  
	 public void gethuodong(){
		 try {
             List<NameValuePair> params = new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("pid",pid1));

             // getting product details by making HTTP request
             // Note that product details url will use GET request
             JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params);
          success = json.getInt(TAG_SUCCESS);
             if (success == 1) {
             	
             	  JSONArray productObj = json
                           .getJSONArray(TAG_PRODUCT); // JSON Array
                   // get first product object from JSON Array
                   JSONObject product = productObj.getJSONObject(0);
                   description=product.getString("description");
                   name=product.getString("name");
                   phonenumber=product.getString("dianhua");
                   url=product.getString("url");
             }	
				} catch (JSONException e) {
                 e.printStackTrace();
				}
	 }
}
