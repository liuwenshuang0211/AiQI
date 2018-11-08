package com.example.aiqi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import tupianchuli.UploadUtil;
import tupianchuli.UploadUtil.OnUploadProcessListener;
import fuwuqi.ImageServi;
import fuwuqi.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class Chushou extends Activity {
		private Button chushoubt;
		private Button xzbt;
		private Context mContext;
		   private ImageView imageView1;
		 private TextView uploadImageResult1;
		  private static final String TAG = "uploadImage";
		  protected static final int TO_UPLOAD_FILE = 1; 
		  protected static final int UPLOAD_FILE_DONE = 2; //
		  public static final int TO_SELECT_PHOTO = 3;
		  private static final int UPLOAD_INIT_PROCESS = 4;
		  private static final int UPLOAD_IN_PROCESS = 5;
		  private static String requestURL = "http://qq282844655.hk623-hl.6464.cn/upload.php";
		  private Button uploadButton;
		  private ProgressBar progressBar;
		  private String picPath = null;
		  private ProgressDialog progressDialog;
		  static  String pid,name;
		  static int zhaopian;
		  Bitmap bm1;
		    // JSON parser class
		    JSONParser jsonParser = new JSONParser();
		 int  success;
		    // url to update product
		    private static final String url_update_danche = "http://qq282844655.hk623-hl.6464.cn/danche.php";
		    private static final String TAG_SUCCESS = "success";
		  
	protected void onCreate(Bundle savedlnstanceState){
		
		super.onCreate(savedlnstanceState);
		setContentView(R.layout.chushou);
		 final Data app = (Data)getApplication(); 
		 pid=app.getpid();
		 name=app.getUser();
		 app.setzhanpian(1);
		 zhaopian=app.getzhaopian();
		 mContext=this;	
		 new Thread(){  
	          public void run(){  
	              new AnotherTask().execute("JSON");  
	          }  
	      }.start();  
		xzbt=(Button) findViewById(R.id.tupianbt);
		chushoubt=(Button) findViewById(R.id.chushoubt);
		imageView1=(ImageView) findViewById(R.id.danche);
		uploadImageResult1=(TextView) findViewById(R.id.textView1);
		progressBar=(ProgressBar) findViewById(R.id.progressBar10);
	    progressDialog = new ProgressDialog(this);
	 
	    
		xzbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(mContext,SelectPicActivity.class);
				startActivityForResult(intent, TO_SELECT_PHOTO);
			}
		});
		
		chushoubt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 runOnUiThread(new Runnable() {
					 @Override
					public void run() { 	
				  String price,description1;
			
			     	price=( (EditText) findViewById(R.id.jiage)).getText().toString();
			     	description1=( (EditText) findViewById(R.id.txdes)).getText().toString();
			        try {
		            	
		                List<NameValuePair> params = new ArrayList<NameValuePair>();
				        params.add(new BasicNameValuePair("pid", pid));  
				        params.add(new BasicNameValuePair("price", price));           
				            params.add(new BasicNameValuePair("description1",description1));	        
				            JSONObject json = jsonParser.makeHttpRequest(url_update_danche,
		                    "POST", params);
		              success = json.getInt(TAG_SUCCESS);
		              
		                if (success == 1) {
		                    // successfully updated
		                	sendImage(bm1);
		                	final Data urlGeturl = (Data)getApplication();
							if(urlGeturl.getActivity1()){
							Factivity.instance.finish();
						urlGeturl.setActivityF1();
							}
		                  finish();
		                } else {
		               
		                    // failed to update product
		                }
		            } catch (JSONException e) {
		                e.printStackTrace();
		            }
				
				
			
					 }
				 });
				
			}
		});
	}
	private class AnotherTask extends AsyncTask<String, Void, String>{  
	    @Override  
	        protected void onPostExecute(String result) {  
	             //对UI组件的更新操作  
	    	   showimage();
	    	  
		  
	        }  
	    @Override  
	    protected String doInBackground(String... params) {  
	        //耗时的操作  
	    	
	        return params[0];  
	    }  
	    }  
	public void showimage(){
		 
	      String path ="http://qq282844655.hk156.ip.hl.cn/upload/"+"image.jpg";
	      try {
	          Bitmap bitmap = ImageServi.getBitmapFromServer(path);
	          imageView1.setImageBitmap(bitmap);
	        
	      } catch (Exception e) {
	          e.printStackTrace();
	   Toast.makeText(getApplicationContext(), e.toString(), 1).show();
	      }
		
	  }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if(resultCode==Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
	{
	picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
	Log.i(TAG, "最终选择的图片="+picPath);
	Bitmap bm = BitmapFactory.decodeFile(picPath);
	imageView1.setImageBitmap(bm);
	 bm1=getimage(picPath);
	
	}
	super.onActivityResult(requestCode, resultCode, data);
	}

	private Bitmap getimage(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh = 400f;//这里设置高度为800f  
        float ww = 240f;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
    }  
	private Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    } 


	 private void sendImage(Bitmap bm)
	    {
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
	        byte[] bytes = stream.toByteArray();
	        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

	        AsyncHttpClient client = new AsyncHttpClient();
	        RequestParams params = new RequestParams();
	        params.add("img", img);
	        params.add("pid", pid);
	        client.post("http://qq282844655.hk156.ip.hl.cn/ImgUpload1.php", params, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(int i, Header[] headers, byte[] bytes) {
	                Toast.makeText(Chushou.this, "上架商品成功", Toast.LENGTH_LONG).show();
	            }
	            @Override
	            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
	                Toast.makeText(Chushou.this, "上架商品失败", Toast.LENGTH_LONG).show();
	            }
	        });
	    }
	
}
