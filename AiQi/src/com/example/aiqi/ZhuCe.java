package com.example.aiqi;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import fuwuqi.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ZhuCe extends Activity{
	EditText txtnum,txtpwd,txtyonghu;
	Button btzhu;
	private ProgressDialog pDialog;
	 JSONParser jsonParser = new JSONParser();
	 private static String url_create_product = "http://qq282844655.hk623-hl.6464.cn/create_product.php";
	 private static final String TAG_SUCCESS = "success";
	@Override
	protected void onCreate(Bundle savedlnstanceState){
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
	       StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
			super.onCreate(savedlnstanceState);
		super.onCreate(savedlnstanceState);
		setContentView(R.layout.zhuce);
		txtyonghu=(EditText) findViewById(R.id.yonghu);
		  txtnum=(EditText) findViewById(R.id.txtnum);
		 txtpwd=(EditText) findViewById(R.id.txtpwd);
		 
		 btzhu=(Button) findViewById(R.id .zhuce);
		 btzhu.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
				    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
	                   arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
	                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
	                }   
	                else if(arg1.getAction() == MotionEvent.ACTION_UP){   
	                    arg0.setBackgroundResource(R.drawable.ee); 
	                 		 String zhanghao = txtnum.getText().toString();
				 String yonghu = txtyonghu.getText().toString(); 
				 String password = txtpwd.getText().toString();
				
		          if(  zhanghao.matches("[0-9]+")&&zhanghao.length()!=0&&yonghu.length()!=0&&password.length()!=0){
		       try {
		            // Building Parameters
		            List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("pid", zhanghao));  
		        params.add(new BasicNameValuePair("name",URLEncoder.encode( yonghu)));           
		            params.add(new BasicNameValuePair("password", password));
		          
		            // getting JSON Object
		            // Note that create product url accepts POST method
		            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
		                    "POST", params);
		 
		            // check log cat fro response
		            Log.d("Create Response", json.toString());	 
		            // check for success tag	      
		                int success = json.getInt(TAG_SUCCESS);
		 
		                if (success == 1) {
		                    // successfully created product
		                    
		                	Toast.makeText(ZhuCe.this,"◊¢≤·≥…π¶",Toast.LENGTH_SHORT).show();
		                	finish();
		                    // closing this scree
		                } else {
		                	Toast.makeText(ZhuCe.this,"◊¢≤· ß∞‹",Toast.LENGTH_SHORT).show();
		                    // failed to create product
		                }
		            } catch (JSONException e) {
		                e.printStackTrace();
		            }
		            }else if(yonghu.length()==0){
		        	  Toast.makeText(ZhuCe.this,"«Î ‰»Î”√ªß√˚£°",Toast.LENGTH_SHORT).show();
		          }else if(zhanghao.length()==0){
		        	  Toast.makeText(ZhuCe.this,"«Î ‰»Î’À∫≈£°",Toast.LENGTH_SHORT).show();
		          }else if( !zhanghao.matches("[0-9]+")){
		        	  Toast.makeText(ZhuCe.this,"ƒ„ ‰»Îµƒ’À∫≈”–ŒÛ£°",Toast.LENGTH_SHORT).show();
		          }else if(password.length()==0){
		        	  Toast.makeText(ZhuCe.this,"«Î ‰»Î√‹¬Î£°",Toast.LENGTH_SHORT).show();
		          }else{
		        	  Toast.makeText(ZhuCe.this,"shenmgui",Toast.LENGTH_SHORT).show();
		          }
		        
	                } 
					return false;
				}
			});
		
}
	
	 
	    
	
}
