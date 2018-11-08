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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Goumai extends Activity {
	JSONParser jsonParser = new JSONParser();
	 int  success;
	    // url to update product
	 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
	    private static final String url_haoyou = "http://qq282844655.hk623-hl.6464.cn/haoyou.php";
	    private static final String url_goumai = "http://qq282844655.hk623-hl.6464.cn/goumai.php";
	    private static final String TAG_DIANHUA= "dianhua";
	    private static final String TAG_ADDRESS = "myaddress";
	    private static final String TAG_DINGGOU = "dinggou";
	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	 private ImageView imageView;
	 private TextView name,description;
	 private ImageButton liuyanbt;
	 private Button haoyoubt;
	 private Button goumai1;
	 private static String num;
	 private Context mContext;
	 private static int number;
	 private ProgressBar danchejiazai;
	  private static  String[] URLS = new String[100];   
	    private static  String[] description1= new String[100]; 
	    private static  String[] description2= new String[100]; 
	    private static  String[] name1= new String[100]; 
	    private static  String[] pid1= new String[100]; 
	    private static String pid,name12,addressString="",dinggou="",password1,password2,dianhuaString="";
	 @Override
	protected void onCreate(Bundle savedlnstanceState){
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
	      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
	        super.onCreate(savedlnstanceState);
	        setContentView(R.layout.chushou1);
	       imageView=(ImageView) findViewById(R.id.danche11);
	   name=(TextView) findViewById(R.id.price1);
	   description=(TextView) findViewById(R.id.descrip1);
	   liuyanbt= (ImageButton) findViewById(R.id.liuyan);
	   haoyoubt=(Button) findViewById(R.id.haoyoubt);
	   goumai1=(Button) findViewById(R.id.goumai);
	   danchejiazai=(ProgressBar) findViewById(R.id.danchejiazai);
	  
	   mContext=this;
	   
	   new Thread(){  
	          public void run(){  
	              new AnotherTask().execute("JSON");  
	          }  
	      }.start();  
	       Intent intent1 = getIntent();
	       num=intent1.getStringExtra("one");
	       number=Integer.parseInt(num); 
	       goumai1.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
				    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
	                   arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
	                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
	                }   
	                else if(arg1.getAction() == MotionEvent.ACTION_UP){   
	                    arg0.setBackgroundResource(R.drawable.ee); 
	                  	
			if(!pid.equals("")){
				if(!pid.equals(pid1[number])){
				
				
				AlertDialog.Builder builder = new Builder(mContext);
				builder.setTitle("请输入密码");	//设置对话框标题
				builder.setIcon(android.R.drawable.btn_star);	//设置对话框标题前的图标
				final EditText edit = new EditText(mContext);	
				builder.setView(edit);	
				
				builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					password1=edit.getText().toString();
				if(password1.equals(password2)){
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
		                              
		                              if(!product.getString(TAG_ADDRESS).equals("null")){
		                            	  addressString=product.getString(TAG_ADDRESS);        	  
		                              }
		                              if(!product.getString(TAG_DIANHUA).equals("null")){
		                            	  dianhuaString=product.getString(TAG_DIANHUA);	  
		                              }
		                        }
								
								} catch (JSONException e) {
			                        e.printStackTrace();
								}	 
							try {
		                        List<NameValuePair> params = new ArrayList<NameValuePair>();
		                        params.add(new BasicNameValuePair("pid",pid1[number]));
		    
		                        // getting product details by making HTTP request
		                        // Note that product details url will use GET request
		                        JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params);
		                     success = json.getInt(TAG_SUCCESS);
		                        if (success == 1) {
		                        	
		                        	  JSONArray productObj = json
		                                      .getJSONArray(TAG_PRODUCT); // JSON Array
		   
		                              // get first product object from JSON Array
		                              JSONObject product = productObj.getJSONObject(0); 
		                              
		                              if(!product.getString(TAG_DINGGOU).equals("null")){
		                            	  dinggou=product.getString(TAG_DINGGOU);
		                              }else {
										dinggou="";
									}
		                        }
								
								} catch (JSONException e) {
			                        e.printStackTrace();
								}	
						
							if(dinggou.equals("")){
							if(!addressString.equals("")&&!dianhuaString.equals("")){
							
				  String description11;
			    description11=description2[number]+"\n\t已被订购";
			        try {
		            	addressString=name12+"#"+addressString+"#"+dianhuaString;
		            	
		                List<NameValuePair> params = new ArrayList<NameValuePair>();
				        params.add(new BasicNameValuePair("pid", pid1[number]));  		       
				            params.add(new BasicNameValuePair("description1",description11));
				            params.add(new BasicNameValuePair("dinggou",addressString));
				            JSONObject json = jsonParser.makeHttpRequest(url_goumai,
		                    "POST", params);
				        
		              success = json.getInt(TAG_SUCCESS);
		              
		                if (success == 1) {
		                    // successfully updated
		                	Toast.makeText(Goumai.this,"购买成功",Toast.LENGTH_SHORT).show();
		                	Factivity.instance.finish();		                	
		                	Intent intent=new Intent();							
							intent.setClass(Goumai.this,Factivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	     	intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(intent); 
		                
		                } else {
		                	Toast.makeText(Goumai.this,"购买失败",Toast.LENGTH_SHORT).show();
		                    // failed to update product
		                }
		            } catch (JSONException e) {
		                e.printStackTrace();
		            }
		        
							}else {
								Toast.makeText(Goumai.this,"请填写收货信息",Toast.LENGTH_SHORT).show();
							}
							}else {
								Toast.makeText(Goumai.this,"该商品已被订购",Toast.LENGTH_SHORT).show();
							}		
					}else {
								Toast.makeText(Goumai.this,"请输入正确密码",Toast.LENGTH_SHORT).show();
							}
			
				}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(mContext, "你点了取消", Toast.LENGTH_SHORT).show();
				}
				});	
				builder.setCancelable(true);	//设置按钮是否可以按返回键取消,false则不可以取消
				AlertDialog dialog = builder.create();	//创建对话框
				dialog.setCanceledOnTouchOutside(true);	//设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
				dialog.show();
				}else{
					 Toast.makeText(Goumai.this,"不能购买自己的商品",Toast.LENGTH_SHORT).show();
				}
				
					 }else {
						 Toast.makeText(Goumai.this,"请登陆",Toast.LENGTH_SHORT).show();
					}
	                } 
					return false;
				}
			});
	     
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
          String haoyou = null;
				haoyou=pid1[number]+"#"+name1[number]+"#";
				 try {
		            	
		                List<NameValuePair> params = new ArrayList<NameValuePair>();
				        params.add(new BasicNameValuePair("pid", pid));           
				            params.add(new BasicNameValuePair("haoyou",haoyou));
				            JSONObject json = jsonParser.makeHttpRequest(url_haoyou,
		                    "POST", params);
		              success = json.getInt(TAG_SUCCESS);
		              
		                if (success == 1) {
		                    // successfully updated
		                	Toast.makeText(Goumai.this,"添加好友成功",Toast.LENGTH_SHORT).show();
		                   
		                } else {
		                	Toast.makeText(Goumai.this,"添加好友失败",Toast.LENGTH_SHORT).show();
		                    // failed to update product
		                }
		            } catch (JSONException e) {
		                e.printStackTrace();
		            }
        } 
		return false;
	}
});
	      
	       liuyanbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(pid!=""){
				  Intent intent = new Intent(mContext,Liuyan.class);
  intent.putExtra("pid", pid1[number]);
			         startActivity(intent);
				}else {
				  	Toast.makeText(Goumai.this,"请登录",Toast.LENGTH_SHORT).show();
				}
			}
		});
	 }
	  private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
		    	   showimage();
		    	   danchejiazai.setVisibility(View.GONE );
		    	   description.setText(description1[number]);
			       name.setText(name1[number]);
			  
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	 final Data urlGeturl = (Data)getApplication();  
			       URLS   =urlGeturl.Geturl();
			       description1=urlGeturl.Getdescription1();
			       description2=urlGeturl.Getdescription2();
			     name1=urlGeturl.Getnames();
			     pid1=urlGeturl.Getpids();
			     pid=urlGeturl.getpid();
			     name12=urlGeturl.getUser();
			     password2=urlGeturl.getpassword();
		        return params[0];  
		    }  
		    }  
	private void showimage() {
		// TODO Auto-generated method stub
		 String path =URLS[number];
         Picasso.with(Goumai.this)
     		.load(path)
     		.error(R.drawable.ic_launcher).into(imageView);
//	      try {
//	          Bitmap bitmap = ImageServi.getBitmapFromServer(path);
//	          imageView.setImageBitmap(bitmap);
//	       //   Toast.makeText(getApplicationContext(), path, 1).show();
//	      } catch (Exception e) {
//	          e.printStackTrace();
//	   Toast.makeText(getApplicationContext(), e.toString(), 1).show();
//	      }
	}

}
