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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class TongCheng extends Activity implements OnItemClickListener,OnScrollListener,OnTouchListener, OnGestureListener  {
	static	String username=null;
	static	String pid="";
	  public static TongCheng instance = null; 
	 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
	 private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	    private static final String TAG_PID = "pid";
		JSONParser jsonParser = new JSONParser();
	private ImageButton bt1;
	   private ImageButton bt2;
	   private ImageButton bt3;
	   private ImageButton bt4;
private Button bt5;
	   private GestureDetector      mGestureDetector;  
	  static Context mContext2;
		private ListView listViewtc;
		 private static  String[] desStrings = new String[100];
		 private static int length;
		//private ArrayAdapter<String>arr_adapter;

		private SimpleAdapter sim_adaptertc;

		
		JSONParser jParser = new JSONParser();
		 
	    ArrayList<HashMap<String, String>> productsList;
	
	 
	    // products JSONArray
	    JSONArray products = null;
	 
	 @Override
	protected void onCreate(Bundle savedlnstanceState){
			
	        super.onCreate(savedlnstanceState);
	         
	        setContentView(R.layout.tongcheng);	
	        SysApplication.getInstance().addActivity(this); 
	        mContext2=this;
	        mGestureDetector = new GestureDetector((OnGestureListener) this);    
	        RelativeLayout viewSnsLayout = (RelativeLayout)findViewById(R.id.tongcheng123); 
	        viewSnsLayout.setOnTouchListener(this);    
	        viewSnsLayout.setLongClickable(true);  
	   	 instance = this;
	        new Thread(){  
	            public void run(){  
	                new AnotherTask().execute("JSON");  
	            }  
	        }.start();   
	        // Hashmap for ListView
	        productsList = new ArrayList<HashMap<String, String>>();
	 
	        // Loading products in Background Thread
	        // Get listview 
			 bt1=(ImageButton) findViewById(R.id.shouye1);
			    bt2=(ImageButton) findViewById(R.id.geren1);
			    bt3=(ImageButton) findViewById(R.id.xiaoxi1); 
			    bt4=(ImageButton) findViewById(R.id.add1); 
			    bt5=(Button) findViewById(R.id.myhuodong);
			   bt5.setOnTouchListener(new OnTouchListener() {
				
				@Override
			
				   public boolean onTouch(View v, MotionEvent event) {
				    if(event.getAction() == MotionEvent.ACTION_DOWN){   
				                    v.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
				                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
				                }   
				                else if(event.getAction() == MotionEvent.ACTION_UP){   
				                    v.setBackgroundResource(R.drawable.ee); 
				                    
				                    
				                    
				           			final String[] items=new String[]{"我发布的活动","我报名的活动"};
			    	    			android.app.AlertDialog.Builder builder=new  AlertDialog.Builder(TongCheng.this);
			    	    			
			    	    		//	builder.setIcon(R.drawable.aa);
			    	    		//	builder.setTitle(" ");
			    	    			
			    	    			builder.setItems(items, new DialogInterface.OnClickListener() {
			    	    				
			    	    				@Override
			    	    				public void onClick(DialogInterface arg0, int arg1) {
			    	    					// TODO Auto-generated method stub
			    	    					 final Data urlGeturl = (Data)getApplication();  
			    	    			          pid=urlGeturl.getpid();
			    	    					if(arg1==0)			    	   									           
							                	if(pid!=""){
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
			    	    		                              if(product.getString("description").length()>10){
			    	    							   Intent intent = new Intent(mContext2,Myhuodong.class);
						    	         intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						    	         startActivity(intent);
			    	    		                              }else{
			    	    		                            	  Toast.makeText(TongCheng.this, "你没有发布活动", Toast.LENGTH_SHORT).show();
			    	    					         }
			    	    		                        }
			    	    						   
			    	    							} catch (JSONException e) {
			    	    		                        e.printStackTrace();
			    	    							}

			   	    							}else{
			   	    					        	 Toast.makeText(TongCheng.this, "请登陆", Toast.LENGTH_SHORT).show();
			   	    					         }
			    	    						if(arg1==1)
			    	    							if(pid!=""){
			    	       	    					 Intent intent = new Intent(mContext2,Mybaoming.class);  
			    	       	    				   intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			    	       			    	         startActivity(intent);
			    	    							}else{
			    	    					        	 Toast.makeText(TongCheng.this, "请登陆", Toast.LENGTH_SHORT).show();
			    	    					         }
			    	    				}
			    	    			});
			    	    			

			    	    				
			    	    			builder.create().show();	                    			                 
				                    Log.i("TestAndroid Button", "MotionEvent.ACTION_UP");
				                } 
				   return false;
				   }
			});
			   
			    bt1.setOnClickListener(new OnClickListener(){
			    	  @Override
					public void onClick(View v){
			    		  qingkong();
			    	         Intent intent = new Intent(mContext2,Factivity.class);
			    	         intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			    	         intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			    	         startActivity(intent);
			    	    	  }

			    		});
			    	 
			    	        
			    	      bt2.setOnClickListener(new OnClickListener(){
			    	    	  @Override
							public void onClick(View v){
			    	    		  qingkong();
			    	         Intent intent = new Intent(mContext2,gongneng.class);
			    	         intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			    	         intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			    	         startActivity(intent);
			    	    	  }

			    		});
			    	      bt3.setOnClickListener(new OnClickListener(){
			    	    	  @Override
							public void onClick(View v){
			    	    		  qingkong();
			    	         Intent intent = new Intent(mContext2,Xiaoxi.class);
			    	         intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			    	         intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			    	         startActivity(intent);
			    	    	  }

			    		});
			    	      bt4.setOnClickListener(new View.OnClickListener() {
			    	    		
			    	    		
			    	    		@Override
								public void onClick(View agr0) {
			    	    			
			    	    			final String[] items=new String[]{"单车出售","同城消息发布"};
			    	    			android.app.AlertDialog.Builder builder=new  AlertDialog.Builder(TongCheng.this);
			    	    			
			    	    			builder.setIcon(R.drawable.tubiao);builder.setTitle(" ");
			    	    			
			    	    			builder.setItems(items, new DialogInterface.OnClickListener() {
			    	    				
			    	    				@Override
			    	    				public void onClick(DialogInterface arg0, int arg1) {
			    	    					// TODO Auto-generated method stub
			    	    					 final Data urlGeturl = (Data)getApplication();  
			    	    			          pid=urlGeturl.getpid();
			    	    					if(arg1==0)
			    	    						if(pid!=""){
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
			    	    		                              if(product.getString("url1").length()<10){
			    	    							Intent intent = new Intent(mContext2,Chushou.class);
			    	    							   intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			    	    					         startActivity(intent);
			    	    		                              }else{
			    	    		                            	  Toast.makeText(TongCheng.this, "你已经上架有商品，请先下架", Toast.LENGTH_SHORT).show();
			    	    					         }
			    	    		                        }
			    	    						   
			    	    							} catch (JSONException e) {
			    	    		                        e.printStackTrace();
			    	    							}
			    	    					         
			    	    					         }else{
			    	    					        	 Toast.makeText(TongCheng.this, "请登陆", Toast.LENGTH_SHORT).show();
			    	    					         }
			    	    						if(arg1==1)
			    	    							if(pid!=""){
			    	       	    					 Intent intent = new Intent(mContext2,TongCheng1.class);  
			    	       	    				   intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			    	       			    	         startActivity(intent);
			    	    							}else{
			    	    					        	 Toast.makeText(TongCheng.this, "请登陆", Toast.LENGTH_SHORT).show();
			    	    					         }
			    	    				}
			    	    			});
			    	    			

			    	    				
			    	    			builder.create().show();
			    	    			
			    	    			}
			    	    	});
			    	      
			    	      
			    	      listViewtc = (ListView) findViewById(R.id.list1);	
			    			sim_adaptertc=new SimpleAdapter(this, getData(),R.layout.item,new String[]{"text"},new int[]{R.id.text});
			    		
			    			  listViewtc.setOnItemClickListener(this);
			    		  listViewtc.setOnScrollListener(this);   
	 }
	
		  
		/** 
		 * 网络操作相关的子线程 
		 */  
	void qingkong(){
		for(int i=0;i<desStrings.length;i++){
			desStrings[i]="";
		}
	}
		  private class AnotherTask extends AsyncTask<String, Void, String>{  
			    @Override  
			        protected void onPostExecute(String result) {  
			             //对UI组件的更新操作  
			    	
	    		  listViewtc.setAdapter(sim_adaptertc);
			        }  
			    @Override  
			    protected String doInBackground(String... params) {  
			        //耗时的操作  
			    	final   Data getdescription= (Data)getApplication(); 
			        desStrings=getdescription.Getdescription();	   
			        pid=getdescription.getpid();
			        getData();
			        return params[0];  
			    }  
			    }  
	private List<? extends Map<String, ?>> getData() {
		// TODO Auto-generated method stub
	for(int i=0;i<desStrings.length;i++){
	                    if(desStrings[i]!=""&&desStrings[i]!="null"&&desStrings[i]!=null&&desStrings[i].length()>10){  
	                    	HashMap<String, String> map = new HashMap<String, String>();
	                    map.put("text", desStrings[i]);
	                    // adding HashList to ArrayList
	                    productsList.add(map);}
	}
	    	return productsList;
	
	}
	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				AlertDialog.Builder build=new AlertDialog.Builder(this);
				build.setTitle("注意")
						.setMessage("确定要退出吗？")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								SysApplication.getInstance().exit();  
								
							}
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						})
						.show();
				break;

			default:
				break;
			}
			return false;
			//return super.onKeyDown(keyCode, event);
			
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
	    Intent intent = new Intent(TongCheng.this,Baoming.class);  
	    String str = String.valueOf(arg2);
		  intent.putExtra("one", str);
        startActivity(intent);  
	}


	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	private int verticalMinDistance =150;  
	private int minVelocity         = 0; 
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

	    if (e1.getX() - e2.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {  
	  
	        // 切换Activity  
	         Intent intent = new Intent(TongCheng.this, Xiaoxi.class);  
	         intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	         startActivity(intent);  
	     
	      
	    } else if (e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {  
	  
	        // 切换Activity  
	        Intent intent = new Intent(TongCheng.this,Factivity.class);  
	        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	         startActivity(intent);  
	     
	    }  
	  
	    return false;  
	}


	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO Auto-generated method stub
		  return mGestureDetector.onTouchEvent(event);  
	}

	  
	@Override  
	   public boolean dispatchTouchEvent(MotionEvent ev) {  
	       mGestureDetector.onTouchEvent(ev);  
	       // scroll.onTouchEvent(ev);  
	       return super.dispatchTouchEvent(ev);  
	   } 

}
