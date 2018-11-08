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
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class Mybaoming extends Activity implements OnItemClickListener,OnScrollListener{
	private ListView mybaomingListView;
	  JSONArray products = null;
	private SimpleAdapter sim_adaptertc;
	 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
	 private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	    private static final String TAG_PRODUCTS = "products";
	    private static final String TAG_PID = "pid";
	    JSONParser jParser = new JSONParser();	 
	    private static String url_all_products = "http://qq282844655.hk623-hl.6464.cn/get_all_products.php";
		JSONParser jsonParser = new JSONParser();
	  ArrayList<HashMap<String, String>> productsList;
	  String pid;
	  private static  String[] desStrings = new String[100];
	  private static  String[] pids= new String[100];
	 @Override
		protected void onCreate(Bundle savedlnstanceState){	
		        super.onCreate(savedlnstanceState);	         
		        setContentView(R.layout.mybaoming);	
		        new Thread(){  
		            public void run(){  
		                new AnotherTask().execute("JSON");  
		            }  
		        }.start();  
		        productsList = new ArrayList<HashMap<String, String>>();
		        mybaomingListView = (ListView) findViewById(R.id.baominghuodong);	
    			sim_adaptertc=new SimpleAdapter(this, getData(),R.layout.item,new String[]{"text"},new int[]{R.id.text});  		
		        mybaomingListView .setOnItemClickListener(this);
		        mybaomingListView .setOnScrollListener(this);   
	 }
	  private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
		    	
  		  mybaomingListView.setAdapter(sim_adaptertc);
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	
		    	final   Data getdescription= (Data)getApplication(); 
		  pid=getdescription.getpid();
		  getbaoming();
		  getData();
		        return params[0];  
		    }  
		    }  
	  public void getbaoming(){
			int success;
			int j=0;
			try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("pid",pid));

                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params);
             success = json.getInt(TAG_SUCCESS);
             Log.d(" Product ", json.toString()); 
                if (success == 1) {
                	
                	  JSONArray productObj = json
                              .getJSONArray(TAG_PRODUCT); // JSON Array

                      // get first product object from JSON Array                      	  
                      JSONObject product = productObj.getJSONObject(0);	
                   String [] temp = null;  
                      temp =product.getString("baoming1").split("#");   
                      for(int i=0;i<temp.length;i++)
                    	  pids[i]=temp[i];
                      j=temp.length;
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
			            	 if(pids[k].equals(c.getString(TAG_PID))){
			            		 desStrings[k]=c.getString("description");
			            	 }
			            		
			             }
			             
			                 
			             }
			         } 
			     } catch (JSONException e) {
			         e.printStackTrace();
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
		   Intent intent = new Intent(Mybaoming.this,Mybaoming1.class);  
		  
			  intent.putExtra("one", pids[arg2]);
	        startActivity(intent);  
	}
}
