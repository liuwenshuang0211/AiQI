package com.example.aiqi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import fuwuqi.JSONParser;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TongCheng1 extends Activity {	
	private Button bttongcheng;
    JSONParser jsonParser = new JSONParser();
 int  success;
    // url to update product
    private static final String url_tongcheng = "http://qq282844655.hk623-hl.6464.cn/tongcheng.php";
    // url to delete product
  // JSON Node names
    private static final String TAG_SUCCESS = "success";
	protected void onCreate(Bundle savedlnstanceState){
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.tongchengxx);
        final Data app = (Data)getApplication();  
        bttongcheng=(Button) findViewById(R.id.tongchengbt);
        bttongcheng.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String zhuti,renshu,nian,yue,ri,didian,baoming,TCstring;
				zhuti=( (EditText) findViewById(R.id.zhuti)).getText().toString();
				renshu=( (EditText) findViewById(R.id.renshu)).getText().toString();
				nian=( (EditText) findViewById(R.id.nian)).getText().toString();
				yue=( (EditText) findViewById(R.id.yue)).getText().toString();
				ri=( (EditText) findViewById(R.id.ri)).getText().toString();
				didian=( (EditText) findViewById(R.id.didian)).getText().toString();
			
				
			TCstring="主题:"+zhuti+"\n"+"人数:"+renshu+"\n"+"时间:"+nian+"年 "+yue+"月 "+ri+"日"+
				"\n"+"地点:"+didian;
			  try {
	            	
	                List<NameValuePair> params = new ArrayList<NameValuePair>();
			        params.add(new BasicNameValuePair("description",TCstring));  
			        params.add(new BasicNameValuePair("pid",app.getpid()));  

			            JSONObject json = jsonParser.makeHttpRequest(url_tongcheng,
	                    "POST", params);
	              success = json.getInt(TAG_SUCCESS);
	              
	                if (success == 1) {
	                    // successfully updated
	               Toast.makeText(TongCheng1.this,"同城消息添加成功!",Toast.LENGTH_SHORT).show();
	                    finish();
	                } else {
	               
	                    // failed to update product
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
						
						Toast.makeText(TongCheng1.this,TCstring,Toast.LENGTH_SHORT).show();			
			}
		});
 }

}
