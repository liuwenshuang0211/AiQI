package com.example.aiqi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import fuwuqi.JSONParser;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Address extends Activity {
	private Button shouhuobt;
	private EditText addresstv,dianhuaet;
	static String pid,dizhi,dianhua;
    private static final String TAG_PRODUCT = "product";
    JSONParser jsonParser = new JSONParser();
 int  success;
    // url to update product
 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
    private static final String url_address = "http://qq282844655.hk623-hl.6464.cn/address.php";
    // url to delete product
  // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ADDRESS = "myaddress";
    private static final String TAG_DIANHUA = "dianhua";
	 @Override
	protected void onCreate(Bundle savedlnstanceState){
	        super.onCreate(savedlnstanceState);
	        setContentView(R.layout.shouhuo);
	        new Thread(){  
	            public void run(){  
	                new AnotherTask().execute("JSON");  
	            }  
	        }.start(); 
	        final Data app = (Data)getApplication();
	        pid=app.getpid();
	        addresstv=(EditText) findViewById(R.id.addresset);
	        dianhuaet=(EditText) findViewById(R.id.dianhuahaoma11);
	  
	       shouhuobt=(Button) findViewById(R.id.dizhibt);
	       shouhuobt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String shouhuodizhi,dianhuahaoma;
				dianhuahaoma=((EditText)findViewById(R.id.dianhuahaoma11)).getText().toString();
				shouhuodizhi=((EditText) findViewById(R.id.addresset)).getText().toString();
				  try {   	
		                List<NameValuePair> params = new ArrayList<NameValuePair>();
		                params.add(new BasicNameValuePair("dianhua",dianhuahaoma));  
				        params.add(new BasicNameValuePair("myaddress",shouhuodizhi));  
				        params.add(new BasicNameValuePair("pid",pid));  

				            JSONObject json = jsonParser.makeHttpRequest(url_address,
		                    "POST", params);
		              success = json.getInt(TAG_SUCCESS);
		              
		                if (success == 1) {
		                    // successfully updated
		               Toast.makeText(Address.this,"修改信息成功!",Toast.LENGTH_SHORT).show();
		                    finish();
		                } else {
		                	 Toast.makeText(Address.this,"修改信息失败!",Toast.LENGTH_SHORT).show();
		                    // failed to update product
		                }
		            } catch (JSONException e) {
		                e.printStackTrace();
		            }
			}
		});
	 }
	 private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
		    	 if(!dizhi.equals("null"))
		             addresstv.setText(dizhi);
		                      if(!dianhua.equals("null"))
		                          dianhuaet.setText(dianhua);
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	   setaddress();
		        return params[0];  
		    }  
		    }  
	private void setaddress() {
		// TODO Auto-generated method stub
		int success;
		try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("pid",pid));
            JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params);
         success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
            	
            	  JSONArray productObj = json
                          .getJSONArray(TAG_PRODUCT); // JSON Array
  // get first product object from JSON Array
                  JSONObject product = productObj.getJSONObject(0);
                  dizhi=product.getString(TAG_ADDRESS);
                  dianhua=product.getString(TAG_DIANHUA);
                 
            }
        	} catch (JSONException e) {
                e.printStackTrace();
			}
	}

}
