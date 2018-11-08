package com.example.aiqi;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fuwuqi.JSONParser;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class Myhuodong extends Activity  implements OnItemClickListener,OnScrollListener{

	JSONParser jsonParser = new JSONParser();
	 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
	 private static final String url_quxiaohuodong = "http://qq282844655.hk623-hl.6464.cn/quxiaohuodong.php";
	 private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
private	 String description1,pid,renshu;
private static  String[] name = new String[100];
private TextView baomingxinxi,renshuTextView;
private Button xiugaibt,quxiaobt;
	   private ListView baominglv;
		private SimpleAdapter sim_adaptertc;
		JSONParser jParser = new JSONParser();
		 
	    ArrayList<HashMap<String, String>> productsList;
	
	 
	    // products JSONArray
	    JSONArray products = null;
	 @Override
	protected void onCreate(Bundle savedlnstanceState){
	        super.onCreate(savedlnstanceState);
	        setContentView(R.layout.myhuodong);
	        baomingxinxi=(TextView) findViewById(R.id.mybaomingxinxi);     
	       baominglv= (ListView) findViewById(R.id.baominglv);	
	       renshuTextView=(TextView) findViewById(R.id.renshu);
	       productsList = new ArrayList<HashMap<String, String>>();
			sim_adaptertc=new SimpleAdapter(this, getData(),R.layout.item,new String[]{"text"},new int[]{R.id.text});
			 baominglv.setOnItemClickListener(this);
		  baominglv.setOnScrollListener(this);  
		xiugaibt=(Button) findViewById(R.id.xiugaibt);
		quxiaobt=(Button) findViewById(R.id.quxiaobt);
		xiugaibt.setOnTouchListener(new OnTouchListener() {
			
			@Override
		
			   public boolean onTouch(View v, MotionEvent event) {
			    if(event.getAction() == MotionEvent.ACTION_DOWN){   
			                    v.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
			                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
			                }   
			                else if(event.getAction() == MotionEvent.ACTION_UP){   
			                    v.setBackgroundResource(R.drawable.ee); 
			                    if(pid!=""){
	       	    					 Intent intent = new Intent(Myhuodong.this,TongCheng1.class);   	 
	       	    					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	       			    	         startActivity(intent);
	    							}else{
	    					        	 Toast.makeText(Myhuodong.this, "请登陆", Toast.LENGTH_SHORT).show();
	    					         }
			                    Log.i("TestAndroid Button", "MotionEvent.ACTION_UP");
			                } 
			   return false;
			   }
		});
		quxiaobt.setOnTouchListener(new OnTouchListener() {
			
			@Override
		
			   public boolean onTouch(View v, MotionEvent event) {
			    if(event.getAction() == MotionEvent.ACTION_DOWN){   
			                    v.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
			                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
			                }   
			                else if(event.getAction() == MotionEvent.ACTION_UP){   
			                    v.setBackgroundResource(R.drawable.ee); 
			                    try {
					            	
					                List<NameValuePair> params = new ArrayList<NameValuePair>();
							        params.add(new BasicNameValuePair("pid", pid));
							 
							            JSONObject json = jsonParser.makeHttpRequest(url_quxiaohuodong,
					                    "POST", params);
					       int       success = json.getInt(TAG_SUCCESS);
					              
					                if (success == 1) {
					                    // successfully updated
					                
					                	Toast.makeText(Myhuodong.this,"取消活动成功",Toast.LENGTH_SHORT).show();
					                   finish();
					                } else {
					                	Toast.makeText(Myhuodong.this,"取消活动失败",Toast.LENGTH_SHORT).show();
					                    // failed to update product
					                }
					            } catch (JSONException e) {
					                e.printStackTrace();
					            }
			                    
			                    
			                    Log.i("TestAndroid Button", "MotionEvent.ACTION_UP");
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
		private List<? extends Map<String, ?>> getData() {
			// TODO Auto-generated method stub
	for(int i=0;i<name.length;i++){
		                   if(name[i]!=""&&name[i]!="null"&&name[i]!=null){ 
		                    	HashMap<String, String> map = new HashMap<String, String>();
		                   map.put("text", name[i]);
		                   
		                    // adding HashList to ArrayList
		                    productsList.add(map);
		                   }
		}
		    	return productsList;
		
		}
	 private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
		 baomingxinxi.setText(description1);
		   baominglv.setAdapter(sim_adaptertc);
		   renshuTextView.setText(renshu);
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	 final Data app = (Data)getApplication(); 
		    pid=app.getpid();		    
		    	gethuodong();
		    	 getData();
		        return params[0];  
		    }
			private void gethuodong() {
				// TODO Auto-generated method stub
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
                       description1=product.getString("description");
                       String [] temp = null; 
                       if(product.getString("baoming" )!=null&&product.getString("baoming" )!=""&&product.getString("baoming" )!="null"){
                       temp =product.getString("baoming" ).split("#");  
                       int j=0;
                      for(int i=0;i<temp.length;i++){
                    	  name[j]=temp[i+1]+"   电话："+temp[i+2];
                    	  j++;
                    	  i=i+2;}
                      renshu = String.valueOf(j)+"人";
                    }	
                    }
					} catch (JSONException e) {
                        e.printStackTrace();
					}
			}  
		    }
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}  
}
