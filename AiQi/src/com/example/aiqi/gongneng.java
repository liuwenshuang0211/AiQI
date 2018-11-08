package com.example.aiqi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import fuwuqi.ImageServi;
import fuwuqi.JSONParser;
import android.os.Handler;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import tupianchuli.UploadUtil;
import tupianchuli.UploadUtil.OnUploadProcessListener;

public class gongneng extends Activity implements OnItemClickListener,OnScrollListener,OnTouchListener, OnGestureListener  {
	private ListView listView;
	private SimpleAdapter sim_adapter;
	private List<Map<String,Object>>dataList;
private Button denglubt;
	private ImageButton bt1;
	   private ImageButton bt2;
	   private ImageButton bt3;
	   private ImageButton bt4; 
	   private ImageView imageView;
	   private TextView textView;
	   static Context mContext4;
	   private GestureDetector      mGestureDetector;  
	  private TextView uploadImageResult;
	  private static final String TAG = "uploadImage";
	  protected static final int TO_UPLOAD_FILE = 1; 
	  protected static final int UPLOAD_FILE_DONE = 2; //
	  public static final int TO_SELECT_PHOTO = 3;
	  private static final int UPLOAD_INIT_PROCESS = 4;
	  private static final int UPLOAD_IN_PROCESS = 5;
	  private static String requestURL = "http://qq282844655.hk623-hl.6464.cn/upload.php";
	  private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
		 private static final String TAG_SUCCESS = "success";
		    private static final String TAG_PRODUCT = "product";
		    private static final String TAG_URL = "url";
		    String path;	  
		    public static gongneng instance = null; 
	  private Button uploadButton;
	  private ProgressBar progressBar;
	  private String picPath ="";
	  private String picPath1 ="";
	  private ProgressDialog progressDialog;
	  
		JSONParser jsonParser = new JSONParser();
	static  String pid,name;
	static int zhaopian1;
	@Override
	protected void onCreate(Bundle saveInstanceState){
	
		super.onCreate(saveInstanceState);
		 setContentView(R.layout.sactivity);
		 SysApplication.getInstance().addActivity(this); 
		 mGestureDetector = new GestureDetector((OnGestureListener) this);    
	        RelativeLayout viewSnsLayout = (RelativeLayout)findViewById(R.id.gongneng123); 
	        viewSnsLayout.setOnTouchListener(this);    
	        viewSnsLayout.setLongClickable(true);  
		  new Thread(){  
	            public void run(){  
	                new AnotherTask().execute("JSON");  
	            }  
	        }.start();   
	     
		 final Data app = (Data)getApplication(); 
		 pid=app.getpid();
		 name=app.getUser();
		 app.setzhanpian(2);
		 zhaopian1=app.getzhaopian();
		 mContext4=this;	
		 instance = this;
		 listView = (ListView) findViewById(R.id.listV3);
		 bt1=(ImageButton) findViewById(R.id.shouye3);	  
		    bt2=(ImageButton) findViewById(R.id.xiaoxi3);
		    bt3=(ImageButton) findViewById(R.id.add3); 
		    bt4=(ImageButton) findViewById(R.id.tongcheng3);  
		    denglubt=(Button) findViewById(R.id.denglubt);
		    imageView=(ImageView) findViewById(R.id.imageV1);
		    textView=(TextView) findViewById(R.id.textV1);
		// picPath1=app.getUrl123();
	
		    imageView.setOnClickListener(new OnClickListener() {		
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(!pid.equals("")){
						Intent intent = new Intent(mContext4,SelectPicActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivityForResult(intent, TO_SELECT_PHOTO);
				         }else{
				        	 Toast.makeText(gongneng.this, "请登录", Toast.LENGTH_SHORT).show();
				         }
				}
			});
		  
		    denglubt.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
				    if(arg1.getAction() == MotionEvent.ACTION_DOWN){   
	                   arg0.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);   
	                    Log.i("TestAndroid Button", "MotionEvent.ACTION_DOWN");
	                }   
	                else if(arg1.getAction() == MotionEvent.ACTION_UP){   
	                    arg0.setBackgroundResource(R.drawable.ee); 
	                    if(pid==""){
	   					 Intent intent = new Intent(mContext4,Denglu.class);    
	   						intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	   	    	         startActivity(intent);
	   	    	         }else {
	   	    	        		Toast.makeText(gongneng.this,"你已经登陆", Toast.LENGTH_SHORT).show();
	   					}
	                    Log.i("TestAndroid Button", "MotionEvent.ACTION_UP");
	                } 
					return false;
				}
			});
		  
		    bt1.setOnClickListener(new OnClickListener(){
		    	  @Override
				public void onClick(View v){
		    	         Intent intent = new Intent(mContext4,Factivity.class);
		    	     	intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	     	intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		    	         startActivity(intent);
		    	    
		    	    	  }

		    		});
		    
		
		    	        
		    	      bt2.setOnClickListener(new OnClickListener(){
		    	    	  @Override
						public void onClick(View v){
		    	         Intent intent = new Intent(mContext4,Xiaoxi.class);	  
		    	     	intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	     	intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		    	         startActivity(intent);
		    	         
		    	    	  }

		    		});
		    	      
		    	      bt4.setOnClickListener(new OnClickListener(){
		    	    	  @Override
						public void onClick(View v){
		    	         Intent intent = new Intent(mContext4,TongCheng.class);
		    	     	intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	     	intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		    	         startActivity(intent);
		    	    	  }

		    		});
		    	      bt3.setOnClickListener(new View.OnClickListener() {
  		@Override
							public void onClick(View agr0) {
		    	    			
		    	    			final String[] items=new String[]{"单车出售","同城消息发布"};
		    	    			android.app.AlertDialog.Builder builder=new  AlertDialog.Builder(gongneng.this);
			builder.setIcon(R.drawable.tubiao);builder.setTitle(" ");
		    	    			
		    	    			builder.setItems(items, new DialogInterface.OnClickListener() {
		    	    				
		    	    				@Override
		    	    				public void onClick(DialogInterface arg0, int arg1) {
		    	    					
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
		    	    		                            		
		    	    							Intent intent = new Intent(mContext4,Chushou.class);
		    	    							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	    					         startActivity(intent);
		    	    		                              }else{
		    	    		                            	  Toast.makeText(gongneng.this, "你已经上架有商品，请先下架", Toast.LENGTH_SHORT).show();
		    	    					         }
		    	    		                        }
		    	    						   
		    	    							} catch (JSONException e) {
		    	    		                        e.printStackTrace();
		    	    							}
		    	    					         }else{
		    	    					        	 Toast.makeText(gongneng.this, "请登陆", Toast.LENGTH_SHORT).show();
		    	    					         }
		    	    						if(arg1==1)
		    	    							if(pid!=""){
		    	       	    					 Intent intent = new Intent(mContext4,TongCheng1.class);   
		    	       	    					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	       			    	         startActivity(intent);
		    	    							}else{
		    	    					        	 Toast.makeText(gongneng.this, "请登陆", Toast.LENGTH_SHORT).show();
		    	    					         }
		    	    				}
		    	    			});
    	    			builder.create().show();
		    	    			}
		    	    	});
		 
		 dataList=new ArrayList<Map<String,Object>>();
		sim_adapter=new SimpleAdapter(this, getData(),R.layout.item3,new String[]{"text"},new int[]{R.id.gongneng});
		   listView.setAdapter(sim_adapter);
		   listView.setOnItemClickListener(new OnItemClickListener(){	   
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if(arg2==0){
					if(!pid.equals("")){
					 Intent intent = new Intent(mContext4,Xiugai.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			         startActivity(intent);
			         }else{
			        	 Toast.makeText(gongneng.this, "请登录", Toast.LENGTH_SHORT).show();
			         }
				}
				
				if(arg2==1){
					if(!pid.equals("")){
						 Intent intent = new Intent(mContext4,Denglu.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				         startActivity(intent);
				         }else{
				        	 Toast.makeText(gongneng.this, "请登录", Toast.LENGTH_SHORT).show();
				         }
				}
				
				if(arg2==6){
				
						Intent intent = new Intent(mContext4,LocationModeSourceActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						  startActivity(intent);
				       
				}
				if(arg2==3){
					if(!pid.equals("")){
						 Intent intent = new Intent(mContext4,Mydanche.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				         startActivity(intent);
				         }else{
				        	 Toast.makeText(gongneng.this, "请登录", Toast.LENGTH_SHORT).show();
				         }
				}
				if(arg2==2){
					if(!pid.equals("")){
						 Intent intent = new Intent(mContext4,Haoyou.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				         startActivity(intent);
				         }else{
				        	 Toast.makeText(gongneng.this, "请登录", Toast.LENGTH_SHORT).show();
				         }
				}
				if(arg2==5){
					if(!pid.equals("")){
						 Intent intent = new Intent(mContext4,Address.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				         startActivity(intent);
				         }else{
				        	 Toast.makeText(gongneng.this, "请登录", Toast.LENGTH_SHORT).show();
				         }
				}
				if(arg2==7){
					android.app.AlertDialog.Builder build=new AlertDialog.Builder(gongneng.this);
					build.setTitle("注意")
							.setMessage("确定要退出吗？")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									
									finish();
									
								}
							})
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
								
									
								}
							})
							.show();
				}
				if(arg2==4){
					if(!pid.equals("")){
						 Intent intent = new Intent(mContext4,Dgshangpin.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				         startActivity(intent);
				         }else{
				        	 Toast.makeText(gongneng.this, "请登录", Toast.LENGTH_SHORT).show();
				         }
				}
			}
		});	
	   
	}		    	   
	  private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  

		    	 if(pid!=""){
		 		    showimage();
		 		    textView.setText(name);
		 		    }
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	int success;
				try {
                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("pid",pid));

                    // getting product details by making HTTP request
                    // Note that product details url will use GET request
                    JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params1);
                 success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {      	
                    	  JSONArray productObj = json
                                  .getJSONArray(TAG_PRODUCT); // JSON Array
                          // get first product object from JSON Array
                          JSONObject product = productObj.getJSONObject(0);               
                  path=product.getString(TAG_URL);
                    }
					} catch (JSONException e) {
                        e.printStackTrace();
					}
		        return params[0];  
		    }  
		    }  
	  
	 /* public boolean fileIsExists(){
		  try{
              File f=new File(picPath1);
              if(!f.exists()){
                      return false;
              }
              
      }catch (Exception e) {
              // TODO: handle exception
              return false;
      }
      return true;
  }*/
	public void showimage(){
		Picasso.with(gongneng.this)
		.load(path)
		.error(R.drawable.ic_launcher).into( imageView);
//	
//	      try {
       //   Bitmap bitmap = ImageServi.getBitmapFromServer(path);
       // imageView.setImageBitmap(bitmap);
//	      } catch (Exception e) {
//	          e.printStackTrace();
//	      }
//		
	  }
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if(resultCode==Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
	{

	picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH); 
	/*final Data app1 = (Data)getApplication(); 
		 app1.seturl123(picPath);*/
	Log.i(TAG, "最终选择的图片="+picPath);
	Bitmap bm = BitmapFactory.decodeFile(picPath);
	imageView.setImageBitmap(bm);
	Bitmap bm1=getimage(picPath);
	sendImage(bm1);
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
        float hh = 200f;//这里设置高度为800f  
        float ww = 120f;//这里设置宽度为480f  
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
	        client.post("http://qq282844655.hk156.ip.hl.cn/ImgUpload.php", params, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(int i, Header[] headers, byte[] bytes) {
	                Toast.makeText(gongneng.this, "更换头像成功！", Toast.LENGTH_LONG).show();
	            }
	            @Override
	            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
	                Toast.makeText(gongneng.this, "更换头像失败！", Toast.LENGTH_LONG).show();
	            }
	        });
	    }

private List<Map<String, Object>>getData(){
		Map<String, Object>map0=new HashMap<String, Object>();
		map0.put("text", "修改密码");
		dataList.add(map0);	
		Map<String, Object>map1=new HashMap<String, Object>();	
		map1.put("text", "切换账户");
		dataList.add(map1);
		Map<String, Object>map2=new HashMap<String, Object>();
		map2.put("text", "我的好友");
		dataList.add(map2);	
		Map<String, Object>map3=new HashMap<String, Object>();
		map3.put("text", "上架单车");
		dataList.add(map3);		
		Map<String, Object>map4=new HashMap<String, Object>();
		map4.put("text", "订购单车");
		dataList.add(map4);
		Map<String, Object>map5=new HashMap<String, Object>();
		map5.put("text", "收货信息");
		dataList.add(map5);
		Map<String, Object>map6=new HashMap<String, Object>();
		map6.put("text", "我的位置");
		dataList.add(map6);
		Map<String, Object>map7=new HashMap<String, Object>();
		map7.put("text", "退出");
		dataList.add(map7);
	return dataList;
}



@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	
	switch (keyCode) {
	case KeyEvent.KEYCODE_BACK:
		AlertDialog.Builder build=new AlertDialog.Builder(this);
		build.setTitle("注意")
				.setMessage("确定要退出吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						SysApplication.getInstance().exit();  
						
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
						
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
public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
	
	
}






@Override
public void onScrollStateChanged(AbsListView arg0, int arg1) {

	
}






@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	
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
      
      
    } else if (e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {  
  
        // 切换Activity  
        Intent intent = new Intent(gongneng.this,Xiaoxi.class);  
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
	
