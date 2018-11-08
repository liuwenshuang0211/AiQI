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
	             //��UI����ĸ��²���  
	    	   showimage();
	    	  
		  
	        }  
	    @Override  
	    protected String doInBackground(String... params) {  
	        //��ʱ�Ĳ���  
	    	
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
	Log.i(TAG, "����ѡ���ͼƬ="+picPath);
	Bitmap bm = BitmapFactory.decodeFile(picPath);
	imageView1.setImageBitmap(bm);
	 bm1=getimage(picPath);
	
	}
	super.onActivityResult(requestCode, resultCode, data);
	}

	private Bitmap getimage(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//��ʱ����bmΪ��  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
        float hh = 400f;//�������ø߶�Ϊ800f  
        float ww = 240f;//�������ÿ��Ϊ480f  
        //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
        int be = 1;//be=1��ʾ������  
        if (w > h && w > ww) {//�����ȴ�Ļ����ݿ�ȹ̶���С����  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//����߶ȸߵĻ����ݿ�ȹ̶���С����  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//�������ű���  
        //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);//ѹ���ñ�����С���ٽ�������ѹ��  
    }  
	private Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>100) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
            baos.reset();//����baos�����baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
            options -= 10;//ÿ�ζ�����10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ  
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
	                Toast.makeText(Chushou.this, "�ϼ���Ʒ�ɹ�", Toast.LENGTH_LONG).show();
	            }
	            @Override
	            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
	                Toast.makeText(Chushou.this, "�ϼ���Ʒʧ��", Toast.LENGTH_LONG).show();
	            }
	        });
	    }
	
}
