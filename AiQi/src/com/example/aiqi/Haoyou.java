package com.example.aiqi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import com.squareup.picasso.Picasso;

import tupianchuli.ImageDownloader;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Haoyou extends Activity {
	private ListView listView;
	JSONParser jsonParser = new JSONParser();
	  private Context mContext;
	  private Button tianjiabt;
	    ImageDownloader mDownloader;  
	    MyListAdapter myListAdapter;  
	 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
	 private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	    private static final String TAG_HAOYOU= "haoyou";
	    private static  String[] names= new String[100];   
		 private static  String[] URLS = new String[100];   
		 private static  String[] haoyous= new String[100];
		 private static  String[] pids= new String[100];
		  static	String username;
			static	String pid;
	protected void onCreate(Bundle savedlnstanceState){
	
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.haoyou);
        mContext=this;
        new Thread(){  
            public void run(){  
                new AnotherTask().execute("JSON");  
            }  
        }.start();   
		 final Data app = (Data)getApplication(); 
		 pid=app.getpid();
      tianjiabt=(Button) findViewById(R.id.tianjiahaoyou11);
      tianjiabt.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
			    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
                 arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
                  Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
              }   
              else if(arg1.getAction() == MotionEvent.ACTION_UP){   
                  arg0.setBackgroundResource(R.drawable.ee); 
                	Intent intent = new Intent(mContext,Tianjiahaoyou.class);
			startActivity(intent);
			finish();
              } 
				return false;
			}
		});
     
   
      
    
    listView=(ListView) findViewById(R.id.haoyoulv);
   	
 
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				  Intent intent = new Intent(mContext,Liuyan.class);
				  intent.putExtra("pid", pids[arg2]);
				  		         startActivity(intent);
			}
		});
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
		        gethaoyou();
		        return params[0];  
		    }  
		    }  
	private void gethaoyou() {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			 @Override
			public void run() { 
				 // Building Parameters
				int success;       int j=0;
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
                               temp =product.getString(TAG_HAOYOU).split("#");  
                           	
                               int g=0;
                               if(temp.length>=2){
                               for(int i=0;i<temp.length;i++){
                               	g=0;
                            
for(int k=temp.length-2;k>i;k=k-2){
	if(temp[i].equals(temp[k]))g=1;
}
                               	if(g==0){
                               		 
                               pids[j]=temp[i];
                               i++;
                               	  haoyous[j]=temp[i];                      
                               j++;	  
                               	}else {
										i=i+1;
									}
                               
                               }
                               }
               }
				
				} catch (JSONException e) {
                   e.printStackTrace();
				}
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
	                              URLS[k]=product.getString("url");                
	                }
					
					} catch (JSONException e) {
	                    e.printStackTrace();
					}
			 }
		 });
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
                convertView = getLayoutInflater().inflate(R.layout.single_data1,  
                        null);  
                 mHolder = new ViewHolder();  
                 mHolder.mImageView = (ImageView) convertView.findViewById(R.id.image_view1);  
                 mHolder.mTextView = (TextView) convertView.findViewById(R.id.text_view1);  
                      convertView.setTag(mHolder);  
            }else {  
             mHolder = (ViewHolder) convertView.getTag();  
             }  
            final String url = URLS[position]; 
          
             mHolder.mTextView.setText(haoyous[position]);  
            // mHolder.mImageView.setTag(URLS[position]);  
             Picasso.with(Haoyou.this)
				.load(URLS[position])
				.error(R.drawable.ic_launcher).into(mHolder.mImageView);	
//            if (mDownloader == null) {  
//                mDownloader = new ImageDownloader();  
//            }  
//            //这句代码的作用是为了解决convertView被重用的时候，图片预设的问题   
//            mHolder.mImageView.setImageResource(R.drawable.ic_launcher);  
//            if (mDownloader != null) {  
//                //异步下载图片   
//                mDownloader.imageDownload(url, mHolder.mImageView, "/yanbin",Haoyou.this, new tupianchuli.OnImageDownload() {  
//                            @Override  
//                            public void onDownloadSucc(Bitmap bitmap,  
//                                    String c_url,ImageView mimageView) {  
//                                ImageView imageView = (ImageView) listView.findViewWithTag(c_url);  
//                                if (imageView != null) {  
//                                    imageView.setImageBitmap(bitmap);  
//                                    imageView.setTag("");  
//                                }   
//                            }  
//                        });  
//            }  
          
        	
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

}
