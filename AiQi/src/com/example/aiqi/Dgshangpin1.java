package com.example.aiqi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;









import com.squareup.picasso.Picasso;

import fuwuqi.ImageServi;
import fuwuqi.JSONParser;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Dgshangpin1  extends Activity{
	 private ImageView imageView;
	 private TextView description;
	 private Button shouhuobt;
	 JSONParser jsonParser = new JSONParser();

	 private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	 private static String num,url,description1,password1,password2,pid;
	  private static final String url_shouhuo= "http://qq282844655.hk623-hl.6464.cn/shouhuo.php";
	 private Context mContext;

	protected void onCreate(Bundle savedlnstanceState){
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.mydgdanche);
        imageView=(ImageView) findViewById(R.id.mydgshangpin1);
       description=(TextView) findViewById(R.id.te1546);
       mContext=this;
       new Thread(){  
           public void run(){  
               new AnotherTask().execute("JSON");  
           }  
       }.start();  
       final Data app = (Data)getApplication();  
       password2=app.getpassword();
       Intent intent1 = getIntent();
       pid=intent1.getStringExtra("pid");
	  url=intent1.getStringExtra("url");
	       description1=intent1.getStringExtra("description");
	  
      shouhuobt=(Button) findViewById(R.id.shouhuobt);
      shouhuobt.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
			    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
                 arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
                  Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
              }   
              else if(arg1.getAction() == MotionEvent.ACTION_UP){   
                  arg0.setBackgroundResource(R.drawable.ee); 
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
					  try {
			            	
			                List<NameValuePair> params = new ArrayList<NameValuePair>();
					        params.add(new BasicNameValuePair("pid", pid));  
					 
					            JSONObject json = jsonParser.makeHttpRequest(url_shouhuo,
			                    "POST", params);
			       int       success = json.getInt(TAG_SUCCESS);
			              
			                if (success == 1) {
			                    // successfully updated
			                	
			                	final Data urlGeturl = (Data)getApplication();
								if(urlGeturl.getActivity1()){
								Factivity.instance.finish();
							urlGeturl.setActivityF1();
								}
			                	finish();
			                	
			                	Toast.makeText(Dgshangpin1.this,"收货成功",Toast.LENGTH_SHORT).show();
			                
			                		
							
			                } else {
			               
			                    // failed to update product
			                }
			            } catch (JSONException e) {
			                e.printStackTrace();
			            }
				}else {
					Toast.makeText(Dgshangpin1.this,"密码错误",Toast.LENGTH_SHORT).show();
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
              } 
				return false;
			}
		});
   
	}
	 private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
		        description.setText(description1);
		        showimage();
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	
		        return params[0];  
		    }  
		    }  
	private void showimage() {
		// TODO Auto-generated method stub
		 Picasso.with(Dgshangpin1.this)
			.load(url)
			.error(R.drawable.ic_launcher).into(imageView);	
	}
}
