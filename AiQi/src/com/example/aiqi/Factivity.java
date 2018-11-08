package com.example.aiqi;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import fuwuqi.JSONParser;
import tupianchuli.ImageDownloader;
import tupianchuli.Util;
import android.R.integer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Factivity  extends Activity implements OnItemClickListener,OnScrollListener,OnTouchListener, OnGestureListener  {
	static	String username=null;

	  public static Factivity instance = null; 
	 private static final String url_product_detials = "http://qq282844655.hk623-hl.6464.cn/get_product_details.php";
	 private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	    private static final String TAG_PID = "pid";	
	    JSONParser jsonParser = new JSONParser();
	protected static final Message DialogInterface = null;
 // private Button bt2;
   private ImageButton bt2;
   private ImageButton bt3;
   private ImageButton bt4;
  private ImageButton bt5;
  private ImageButton bt7;
  private Gallery G1;
  private ImageButton bt6;
  private GestureDetector      mGestureDetector;  
  
	static Context mContext1;
	ListView mListView;  
    ImageDownloader mDownloader;  
    MyListAdapter myListAdapter;  
    private static final String TAG = "MainActivity";

    int m_flag = 0;  
    private static  String[] URLS = new String[100];   
    private static  String[] description1= new String[100];   
    static  String pid,name;
	private int[] imageld=new int[]{R.drawable.bb,R.drawable.cc,R.drawable.dd,R.drawable.hh,R.drawable.abc,R.drawable.aa,R.drawable.ff,R.drawable.gg};
	@Override
	protected void onCreate(Bundle saveInstanceState){
	
		super.onCreate(saveInstanceState);	
		
	//requestWindowFeature(Window.FEATURE_PROGRESS);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);	
      setContentView(R.layout.factivity);
      setProgressBarIndeterminateVisibility(true);
      SysApplication.getInstance().addActivity(this); 
    mGestureDetector = new GestureDetector((OnGestureListener) this);    
   //   RelativeLayout viewSnsLayout = (RelativeLayout)findViewById(R.id.zhuye123); 
     /// viewSnsLayout.setOnTouchListener(this);    
      //viewSnsLayout.setLongClickable(true);  
      mContext1=this ;
 	 instance = this;
	 final Data urlGeturl = (Data)getApplication();  
	 urlGeturl.setActivityT1();
      for(int i=0;i<URLS.length;i++)
  		URLS[i]="";
      mListView = (ListView) findViewById(R.id.list); 

      new Thread(){  
          public void run(){  
              new AnotherTask().execute("JSON");  
          }  
      }.start();  
    	
      G1=  (Gallery) findViewById(R.id.gallery1);
     bt2=(ImageButton) findViewById(R.id.sousuo);
    bt3=(ImageButton) findViewById(R.id.geren);
    bt4=(ImageButton) findViewById(R.id.paishe);
    bt5=(ImageButton) findViewById(R.id.xiaoxi);
    bt6=(ImageButton) findViewById(R.id.add);
    bt7=(ImageButton) findViewById(R.id.tongcheng);
  

      bt2.setOnClickListener(new  OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext1,sousuoActivity.class); 
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);
			 
		}
	});    
      bt3.setOnClickListener(new OnClickListener(){
    	  @Override
		public void onClick(View v){
         Intent intent = new Intent(mContext1,gongneng.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); 
         intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
         startActivity(intent);
    	  }
	});
      bt4.setOnClickListener(new OnClickListener(){
    	  @Override
		public void onClick(View v){
         Intent intent = new Intent(mContext1,xiangji.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
  
         startActivity(intent);    
    	  }
	});
      bt7.setOnClickListener(new OnClickListener(){
    	  @Override
		public void onClick(View v){
    		//  pDialogstart();
    		 // android.os.SystemClock.sleep(1000); 
         Intent intent = new Intent(mContext1,TongCheng.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
         intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
         startActivity(intent);  
    	  }
	});
      bt5.setOnClickListener(new OnClickListener(){
    	  @Override
		public void onClick(View v){
         Intent intent = new Intent(mContext1,Xiaoxi.class);  
         intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
         intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent); 
    	  } 	  
	});
bt6.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View agr0) {	
		final String[] items=new String[]{"单车出售","同城消息发布"};
		android.app.AlertDialog.Builder builder=new  AlertDialog.Builder(Factivity.this);	
		builder.setIcon(R.drawable.tubiao);builder.setTitle(" ");	
		builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
						 final Data urlGeturl = (Data)getApplication();  
				          pid=urlGeturl.getpid();
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
							Intent intent = new Intent(mContext1,Chushou.class);
							  intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					         startActivity(intent);
		                              }else{
		                            	  Toast.makeText(Factivity.this, "你已经上架有商品，请先下架", Toast.LENGTH_SHORT).show();
					         }
		                        }
						   
							} catch (JSONException e) {
		                        e.printStackTrace();
							}
					         
					         
					         
					         
					         }else{
					        	 Toast.makeText(Factivity.this, "请登陆", Toast.LENGTH_SHORT).show();
					         }
						if(arg1==1)
							if(pid!=""){
   	    					 Intent intent = new Intent(mContext1,TongCheng1.class);   
   	    				  intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
   			    	         startActivity(intent);
							}else{
					        	 Toast.makeText(Factivity.this, "请登陆", Toast.LENGTH_SHORT).show();
					         }
				
			}
		});		
		builder.create().show();	
		}
});	
		BaseAdapter adapter=new BaseAdapter() {
		
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ImageView imageView=new ImageView(mContext1);
			imageView.setBackgroundResource(imageld[arg0%imageld.length]);
			imageView.setLayoutParams(new Gallery.LayoutParams(400,300));
			imageView.setScaleType(ScaleType.FIT_XY);
			return imageView;
		}
		
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}
		
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}
	};
G1.setAdapter(adapter);
G1.setSelection(imageld.length/2);
G1.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(Factivity.this, "你选择了第"+String.valueOf(arg2)+"张图片", Toast.LENGTH_SHORT).show();
	}
	
});



Util.flag = 0;


mListView.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//myListAdapter.setSelectedPosition(arg2);
	myListAdapter.changeState(arg2);
		  Intent intent = new Intent(mContext1,Goumai.class);
		  intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		  String str = String.valueOf(arg2);
		  intent.putExtra("one", str);
	         startActivity(intent);
	}
});
	}
	public boolean isForeground(String PackageName){
		 // Get the Activity Manager
		 ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		 // Get a list of running tasks, we are only interested in the last one,
		 // the top most so we give a 1 as parameter so we only get the topmost.
		 List< ActivityManager.RunningTaskInfo > task = manager.getRunningTasks(1);

		 // Get the info we need for comparison.
		 ComponentName componentInfo = task.get(0).topActivity;

		 // Check if it matches our package name.
		 if(componentInfo.getPackageName().equals(PackageName)) return true;

		 // If not then our app is not on the foreground.
		 return false;
		}
	  private class AnotherTask extends AsyncTask<String, Void, String>{  
		    @Override  
		        protected void onPostExecute(String result) {  
		             //对UI组件的更新操作  
		    	myListAdapter = new MyListAdapter();  
		    	mListView.setAdapter(myListAdapter);  
		    	
		        }  
		    @Override  
		    protected String doInBackground(String... params) {  
		        //耗时的操作  
		    	 final Data app = (Data)getApplication(); 
		    	 URLS   =app.Geturl();
		    	 description1=app.Getdescription1();
		    	 pid=app.getpid();
		        return params[0];  
		    }  
		    }  
	 private class MyListAdapter extends BaseAdapter {  
	        private ViewHolder mHolder;  
	        private int state[];//记录点击的状态，1为选中状态，0为未选中状态
	        private int pre_pos=0;//记录当前点击的位置，供我们在下次onItemClicked的时候还原state数组
	 public void changeState(int position){
	 if(state==null){
	 state=new int[getCount()];
	 state[position]=1;
	 notifyDataSetInvalidated(position);
	 }else {
	 state[pre_pos]=0;
	 state[position]=1;
	 pre_pos=position;
	 notifyDataSetInvalidated(position);
	 }
	 
	 }
	 private void notifyDataSetInvalidated(int position) {
		 super.notifyDataSetInvalidated();
		 state[pre_pos]=0;
		 state[position]=1;
		 pre_pos=position;
		 }
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
	        	LinearLayout layout=new LinearLayout( mContext1);
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
	            
	             mHolder.mTextView.setText(description1[position]);  
	           //  mHolder.mImageView.setTag(URLS[position]);  
	     		Picasso.with(Factivity.this)
				.load(URLS[position])
				.error(R.drawable.ic_launcher).into(mHolder.mImageView);

	     		if(state==null&& position==0){
	     			layout.setBackgroundResource(R.drawable.abc_ab_share_pack_holo_dark);
	     			}else if(state!=null&&state[position]==1) {
	     			layout.setBackgroundResource(R.drawable.abc_ab_bottom_transparent_light_holo);
	     			}
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
	
	
	
	
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
		case SCROLL_STATE_FLING:
			Log.i("Main","用户在手指离开之前，由于用力滑了一下，试图仍依靠惯性继续滑动！");
			/*Map<String, Object>map=new HashMap<String, Object>();
			map.put("pic", R.drawable.bb);
			map.put("text", "增加项");
			dataList.add(map);
			sim_adapter.notifyDataSetChanged();*/
			break;
		case SCROLL_STATE_IDLE:
			Log.i("Main", "试图已经停止滑动！");
		case SCROLL_STATE_TOUCH_SCROLL:
			Log.i("Main", "手指没有离开屏幕，试图正在滑动！");
			break;
		}
	
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			AlertDialog.Builder build=new AlertDialog.Builder(this);
			build.setTitle("注意")
					.setMessage("确定要退出吗？")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							SysApplication.getInstance().exit();  
							
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	private int verticalMinDistance = 150;  
	private int minVelocity         = 0; 
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

	    if (e1.getX() - e2.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {  
	  
	        // 切换Activity  
	         Intent intent = new Intent(Factivity.this, TongCheng.class);  
	         intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	         startActivity(intent);  
	    
	      
	    } else if (e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {  
	  
	        // 切换Activity  
	       
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

	/* @Override
	    public void onResume(){
	       super.onResume();
	   //    Toast.makeText(getApplicationContext(), "123",Toast.LENGTH_LONG).show();
	    }
	 
	   @Override
	    public void onSaveInstanceState(Bundle savedInstanceState){
	       super.onSaveInstanceState(savedInstanceState);
	  ///     savedInstanceState.putString("message",text.getText().toString());
	    }
	 
	   @Override
	    public void onRestoreInstanceState(Bundle savedInstanceState){
	       super.onRestoreInstanceState(savedInstanceState);
	   //    message = savedInstanceState.getString("message");
	    }*/
}