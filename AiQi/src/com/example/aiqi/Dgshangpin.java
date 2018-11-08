package com.example.aiqi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import fuwuqi.JSONParser;
import tupianchuli.ImageDownloader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Dgshangpin  extends Activity{
	private ListView dgsp;
	 private static  String[] URLS = new String[100];
	 private static  String[] description = new String[100];
	 private static  String[] pid = new String[100];
	 private Context mContext;
	    private static String url_all_products = "http://qq282844655.hk623-hl.6464.cn/get_all_products.php";
	    private static final String TAG_DESCRIPTION1 = "description1";
	    JSONParser jParser = new JSONParser();	
	    JSONArray products = null;
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCTS = "products";
	    private static final String TAG_URL = "url1";
	    private static final String TAG_DINGGOU = "dinggou";
	    private static final String TAG_PID = "pid";

	    ImageDownloader mDownloader;  
	    MyListAdapter myListAdapter;  
	    static	String username;
	protected void onCreate(Bundle savedlnstanceState){
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.dgshangpin);  
        mContext=this;
        new Thread(){  
            public void run(){  
                new AnotherTask().execute("JSON");  
            }  
        }.start();  
        final Data app = (Data)getApplication(); 
        username=app.getUser();
       
 
    	
       dgsp=(ListView) findViewById(R.id.dgshangpin);
      	
      
    	dgsp.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				  Intent intent = new Intent(mContext,Dgshangpin1.class);
				  intent.putExtra("url", URLS[arg2]);
				 intent.putExtra("description", description[arg2]);
				 intent.putExtra("pid", pid[arg2]);
				 for(int i=0;i<URLS.length;i++)
					 URLS[i]="";
				  		         startActivity(intent);
			}
		});
	}
	  private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
		   	 myListAdapter = new MyListAdapter();  
      		 dgsp.setAdapter(myListAdapter);  
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	 getdgsp();
		        return params[0];  
		    }  
		    }  
	private void getdgsp() {
		// TODO Auto-generated method stub
	
     	   	 List<NameValuePair> params = new ArrayList<NameValuePair>();
    	     // getting JSON string from URL
    	     JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
    	  // Check your log cat for JSON reponse
    	     Log.d("All Products: ", json.toString());
    	    try {
    	         // Checking for SUCCESS TAG
    	         int success = json.getInt(TAG_SUCCESS);
    	         if (success == 1) {
    	             // products found
    	             // Getting Array of Products
    	             products = json.getJSONArray(TAG_PRODUCTS);
    	             // looping through All Products
    	             int j=0;
    	             for (int i = 0; i < products.length(); i++) {
    	                 JSONObject c = products.getJSONObject(i);
    	                 // Storing each json item in variable
    	                 String dinggouString = c.getString(TAG_DINGGOU);
    	               
                    String [] temp = null;  
                         temp =dinggouString.split("#");  
                         if(temp[0].equals(username)){
                        	 URLS[j]=c.getString(TAG_URL);
                        	 description[j]=c.getString(TAG_DESCRIPTION1 );
                        	 pid[j]=c.getString(TAG_PID);
                        	 j++;
                         }
    	                 // creating new HashMap
    	      // adding each child node to HashMap key => value      
    	             }
    	         } 
    	     } catch (JSONException e) {
    	         e.printStackTrace();
    	     }
     	   		 
     	   		 
     	   		 
     	   		 
     	   
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
                convertView = getLayoutInflater().inflate(R.layout.single_data,  
                        null);  
                 mHolder = new ViewHolder();  
                 mHolder.mImageView = (ImageView) convertView.findViewById(R.id.image_view);  
                 mHolder.mTextView = (TextView) convertView.findViewById(R.id.text_view);  
                
                 convertView.setTag(mHolder);  
            }else {  
             mHolder = (ViewHolder) convertView.getTag();  
             }  
            final String url = URLS[position]; 
          
             mHolder.mTextView.setText(description[position]);  
             Picasso.with(Dgshangpin.this)
 			.load(URLS[position])
 			.error(R.drawable.ic_launcher).into(  mHolder.mImageView);	
//             mHolder.mImageView.setTag(URLS[position]);  
//            if (mDownloader == null) {  
//                mDownloader = new ImageDownloader();  
//            }  
//            //这句代码的作用是为了解决convertView被重用的时候，图片预设的问题   
//            mHolder.mImageView.setImageResource(R.drawable.ic_launcher);  
//            if (mDownloader != null) {  
//                //异步下载图片   
//                mDownloader.imageDownload(url, mHolder.mImageView, "/yanbin",Dgshangpin.this, new tupianchuli.OnImageDownload() {  
//                            @Override  
//                            public void onDownloadSucc(Bitmap bitmap,  
//                                    String c_url,ImageView mimageView) {  
//                                ImageView imageView = (ImageView) dgsp.findViewWithTag(c_url);  
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
