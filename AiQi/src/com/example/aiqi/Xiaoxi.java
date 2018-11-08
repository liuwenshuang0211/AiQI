package com.example.aiqi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import fuwuqi.JSONParser;
import tupianchuli.ImageDownloader;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Xiaoxi extends Activity implements OnItemClickListener,OnScrollListener,OnTouchListener, OnGestureListener  {
	private ListView listView;
	  public static Xiaoxi instance = null;   
	private ImageButton bt1;
	   private ImageButton bt2;
	   private ImageButton bt3;
	   private ImageButton bt4;
	   private GestureDetector      mGestureDetector;  
	    ImageDownloader mDownloader;  
	    MyListAdapter myListAdapter;  
	  static	String username;
		static	String pid;
		JSONParser jsonParser = new JSONParser();
		   JSONArray products = null;
		   static Context mContext3;
			    private static final String TAG_PID = "pid";	

		   private static String url_all_products = "http://qq282844655.hk623-hl.6464.cn/get_all_products.php";
		 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
		 private static final String TAG_SUCCESS = "success";
		 private static final String TAG_URL = "url";
		    private static final String TAG_PRODUCT = "product";
		    private static final String TAG_PRODUCTS = "products";
		    private static final String TAG_LIUYAN = "liuyan";
		private static  String[] names= new String[100];   
		 private static  String[] URLS = new String[100];   
		 private static  String[] liuyans= new String[100];
		 private static  String[] pids= new String[100];
		  JSONParser jParser = new JSONParser();	 
		    ArrayList<HashMap<String, String>> productsList;
		 
	@Override
	protected void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		 setContentView(R.layout.tianjia);
		 SysApplication.getInstance().addActivity(this); 
		 mContext3=this;
		 instance = this;
		 final Data urlGeturl = (Data)getApplication();  
		 urlGeturl.setActivityT();
		 mGestureDetector = new GestureDetector((OnGestureListener) this);    
	        RelativeLayout viewSnsLayout = (RelativeLayout)findViewById(R.id.xiaoxi123); 
	        viewSnsLayout.setOnTouchListener(this);    
	        viewSnsLayout.setLongClickable(true);  
		  new Thread(){  
	            public void run(){  
	                new AnotherTask().execute("JSON");  
	            }  
	        }.start();  
	      
		 
		 bt1=(ImageButton) findViewById(R.id.shouye2);
		  
		    bt2=(ImageButton) findViewById(R.id.geren2);
		    bt3=(ImageButton) findViewById(R.id.add2); 
		    bt4=(ImageButton) findViewById(R.id.tongcheng2);
		  
		    bt1.setOnClickListener(new OnClickListener(){
		    	  @Override
				public void onClick(View v){
		    		  gengxing();
		    	         Intent intent = new Intent(mContext3,Factivity.class);
		    	     	intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	     	intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		    	         startActivity(intent);
		    	    	  }

		    		});
		    	 
		    	        
		    	      bt2.setOnClickListener(new OnClickListener(){
		    	    	  @Override
						public void onClick(View v){
		    	    		  gengxing();
		    	         Intent intent = new Intent(mContext3,gongneng.class);
		    	     	intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	     	intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		    	         startActivity(intent);
		    	    	  }

		    		});
		    	      bt4.setOnClickListener(new OnClickListener(){
		    	    	  @Override
						public void onClick(View v){
		    	    		  gengxing();
		    	         Intent intent = new Intent(mContext3,TongCheng.class);
		    	     	intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	     	intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		    	         startActivity(intent);
		    	    	  }

		    		});
		    	      bt3.setOnClickListener(new View.OnClickListener() {
		    	    		
		    	    		
		    	    		@Override
							public void onClick(View agr0) {
		    	    			
		    	    			final String[] items=new String[]{"单车出售","同城消息发布"};
		    	    			android.app.AlertDialog.Builder builder=new  AlertDialog.Builder(Xiaoxi.this);
		    	    			
		    	    			builder.setIcon(R.drawable.tubiao);builder.setTitle(" ");
		    	    			
		    	    			builder.setItems(items, new DialogInterface.OnClickListener() {
		    	    				
		    	    				@Override
		    	    				public void onClick(DialogInterface arg0, int arg1) {
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
		    	    							Intent intent = new Intent(mContext3,Chushou.class);
		    	    							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	    					         startActivity(intent);
		    	    		                              }else{
		    	    		                            	  Toast.makeText(Xiaoxi.this, "你已经上架有商品，请先下架", Toast.LENGTH_SHORT).show();
		    	    					         }
		    	    		                        }
		    	    						   
		    	    							} catch (JSONException e) {
		    	    		                        e.printStackTrace();
		    	    							}
		    	    					         }else{
		    	    					        	 Toast.makeText(Xiaoxi.this, "请登陆", Toast.LENGTH_SHORT).show();
		    	    					         }
		    	    						if(arg1==1)
		    	    							if(pid!=""){
		    	       	    					 Intent intent = new Intent(mContext3,TongCheng1.class);   	 
		    	       	    					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	       			    	         startActivity(intent);
		    	    							}else{
		    	    					        	 Toast.makeText(Xiaoxi.this, "请登陆", Toast.LENGTH_SHORT).show();
		    	    					         }
		    	    				}
		    	    			});
		    	    			

		    	    				
		    	    			builder.create().show();
		    	    			
		    	    			}
		    	    	});
		    	    		
	 
	   
		 listView = (ListView) findViewById(R.id.listvv);
		
	  
	listView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
		
			  Intent intent = new Intent(mContext3,Liuyan.class);
intent.putExtra("pid", pids[arg2]);
intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		         startActivity(intent);
		}
	});
	}
void	gengxing(){
	 for(int i=0;i<URLS.length;i++)
		 URLS[i]="";
}
	  private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
		    	 myListAdapter = new MyListAdapter();  
				 listView.setAdapter(myListAdapter);  
				
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	final Data urlGeturl = (Data)getApplication();  
		          pid=urlGeturl.getpid();
		    	 liuyan();
		        return params[0];  
		    }  
		    }  
	 private void liuyan() {
		// TODO Auto-generated method stub
		 int j=0;
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
                            
                                String [] temp = null;  
                                temp =product.getString(TAG_LIUYAN ).split("#");                              	
                                int g=0;
                                if(temp.length>=4){
                                for(int i=0;i<temp.length;i++){
                                	g=0;
for(int k=temp.length-3;k>i+1;k=k-4){
	if(temp[i+1].equals(temp[k]))g=1;
}
                                	if(g==0){
                                		
                                pids[j]=temp[i];
                                i++;
                                	  names[j]=temp[i];
                                	 i++;              
                              liuyans[j]=names[j]+"("+temp[i+1]+")"+"\n\n"+temp[i];
                                     i++;
                              
                                j++;	  
                                	}else {
										i=i+3;
									}
                                
                                }
                                }
                }
				} catch (JSONException e) {
                    e.printStackTrace();
				}
			
		    try {
		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		     // getting JSON string from URL
		     JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
		  // Check your log cat for JSON reponse
		     Log.d("All Products: ", json.toString());  // Checking for SUCCESS TAG
		        success = json.getInt(TAG_SUCCESS);
		         if (success == 1) {
		             // products found
		             // Getting Array of Products
		             products = json.getJSONArray(TAG_PRODUCTS);
		             // looping through All Products
		             
		            for(int k=0;k<j;k++){		       
		             for (int i = 0; i < products.length(); i++) {
		                 JSONObject c = products.getJSONObject(i);		            
		            	 if(pids[k].equals(c.getString(TAG_PID)))
		            		 URLS[k]=c.getString(TAG_URL);     
		             }
		             
		                 
		             }
		         } 
		     } catch (JSONException e) {
		         e.printStackTrace();
		     }
		    /*
for(int k=0;k<j;k++)
			try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("pid",pids[k]));
                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params);
             success = json.getInt(TAG_SUCCESS);            
                if (success == 1) {    
                	  JSONArray productObj = json
                              .getJSONArray(TAG_PRODUCT); // JSON Array

                      // get first product object from JSON Array
                      JSONObject product = productObj.getJSONObject(0);  
                              URLS[k]=product.getString(TAG_URL);                
                }
				
				} catch (JSONException e) {
                    e.printStackTrace();
				}
			
			
			*/
			
	}
	private class MyListAdapter extends BaseAdapter {  
	        private ViewHolder mHolder;  
	  
	        @Override  
	        public int getCount() {  
	        	int j=0;
	        	for(int i=0;i<URLS.length;i++){
	        		if(URLS[i]!=null)
	        		if(URLS[i].length()>10)
	        			j++;
	        	}
	        	
	            return j ;  
	        }  
	  
	        @Override  
	        public Object getItem(int position) { 
	        	
	            return URLS[position];  
	        }  
	  
	        @Override  
	        public long getItemId(int position) {  
	        
	         return position;  
	        }  
	  
	        @Override  
	        public View getView(int position, View convertView, ViewGroup parent) {  
	            //只有当convertView不存在的时候才去inflate子元素   
	            if (convertView == null) {  
	                convertView = getLayoutInflater().inflate(R.layout.single_data3,  
	                        null);  
	                 mHolder = new ViewHolder();  
	                 mHolder.mImageView = (ImageView) convertView.findViewById(R.id.image_view);  
	                 mHolder.mTextView = (TextView) convertView.findViewById(R.id.text_view);  
	                
	                 convertView.setTag(mHolder);  
	            }else {  
	             mHolder = (ViewHolder) convertView.getTag();  
	             }  
	            final String url = URLS[position]; 
	          
	             mHolder.mTextView.setText(liuyans[position]);  
	         //    mHolder.mImageView.setTag(URLS[position]);  
	             Picasso.with(Xiaoxi.this)
	     		.load(URLS[position])
	     		.error(R.drawable.ic_launcher).into(mHolder.mImageView);
//	            if (mDownloader == null) {  
//	                mDownloader = new ImageDownloader();  
//	            }  
//	            //这句代码的作用是为了解决convertView被重用的时候，图片预设的问题   
//	            mHolder.mImageView.setImageResource(R.drawable.ic_launcher);  
//	            if (mDownloader != null) {  
//	                //异步下载图片   
//	                mDownloader.imageDownload(url, mHolder.mImageView, "/yanbin",Xiaoxi.this, new tupianchuli.OnImageDownload() {  
//	                            @Override  
//	                            public void onDownloadSucc(Bitmap bitmap,  
//	                                    String c_url,ImageView mimageView) {  
//	                                ImageView imageView = (ImageView) listView.findViewWithTag(c_url);  
//	                                if (imageView != null) {  
//	                                    imageView.setImageBitmap(bitmap);  
//	                                    imageView.setTag("");  
//	                                }   
//	                            }  
//	                        });  
//	            }  
//	          
	        	
	            return convertView;  
	        
	  
	            
	  
	        }  
	  
	        /** 
	         * 使用ViewHolder来优化listview 
	         * @author yanbin 
	         * 
	         */  
	        private class ViewHolder {  
	            ImageView mImageView;  
	            TextView mTextView;  
	           
	        }  
	    }  

@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	Toast.makeText(this, "功能尚未完善！", Toast.LENGTH_SHORT).show();
}
@Override
public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
	// TODO Auto-generated method stub

}


@Override
public void onScrollStateChanged(AbsListView arg0, int arg1) {
	// TODO Auto-generated method stub
	switch (arg1) {
	case SCROLL_STATE_FLING:
		Log.i("Main","用户在手指离开之前，由于用力滑了一下，试图仍依靠惯性继续滑动！");
		break;
	case SCROLL_STATE_IDLE:
		Log.i("Main", "试图已经停止滑动！");
	case SCROLL_STATE_TOUCH_SCROLL:
		Log.i("Main", "手指没有离开屏幕，试图正在滑动！");
		break;
	}
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
         Intent intent = new Intent(Xiaoxi.this, gongneng.class);  
         intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
         startActivity(intent);  
 
      
    } else if (e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {  
  
        // 切换Activity  
        Intent intent = new Intent(Xiaoxi.this,TongCheng.class);  
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
	
