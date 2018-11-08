package tupianchuli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloader {  
    private static final String TAG = "ImageDownloader";  
    private HashMap<String, MyAsyncTask> map = new HashMap<String, MyAsyncTask>();  
    private Map<String, SoftReference<Bitmap>> imageCaches = new HashMap<String, SoftReference<Bitmap>>();  
    /** 
     *  
     * @param url ��mImageView��Ӧ��url 
     * @param mImageView 
     * @param path �ļ��洢·�� 
     * @param mActivity 
     * @param download OnImageDownload�ص��ӿڣ���onPostExecute()�б����� 
     */  
    public void imageDownload(String url,ImageView mImageView,String path,Activity mActivity,OnImageDownload download){  
        SoftReference<Bitmap> currBitmap = imageCaches.get(url);  
        Bitmap softRefBitmap = null;  
        if(currBitmap != null){  
            softRefBitmap = currBitmap.get();  
        }  
        String imageName = "";  
        if(url != null){  
            imageName = Util.getInstance().getImageName(url);  
        }  
        Bitmap bitmap = getBitmapFromFile(mActivity,imageName,path);  
        //�ȴ���������������   
        if(currBitmap != null && mImageView != null && softRefBitmap != null && url.equals(mImageView.getTag())){  
            mImageView.setImageBitmap(softRefBitmap);  
        }  
        //��������û�У����ļ���������   
        else if(bitmap != null && mImageView != null && url.equals(mImageView.getTag())){  
            mImageView.setImageBitmap(bitmap);  
        }  
        //�ļ���Ҳû�У���ʱ����mImageView��tag����urlȥ�жϸ�url��Ӧ��task�Ƿ��Ѿ���ִ�У������ִ�У����β����������µ��̣߳����򴴽��µ��̡߳�   
        else if(url != null && needCreateNewTask(mImageView)){  
            MyAsyncTask task = new MyAsyncTask(url, mImageView, path,mActivity,download);  
            if(mImageView != null){  
                Log.i(TAG, "ִ��MyAsyncTask --> " + Util.flag);  
                Util.flag ++;  
                task.execute();  
                //����Ӧ��url��Ӧ�����������   
                map.put(url, task);  
            }  
        }  
    }  
      
    /** 
     * �ж��Ƿ���Ҫ���´����߳�����ͼƬ�������Ҫ������ֵΪtrue�� 
     * @param url 
     * @param mImageView 
     * @return 
     */  
    private boolean needCreateNewTask(ImageView mImageView){  
        boolean b = true;  
        if(mImageView != null){  
            String curr_task_url = (String)mImageView.getTag();  
            if(isTasksContains(curr_task_url)){  
                b = false;  
            }  
        }  
        return b;  
    }  
      
    /** 
     * ����url�����շ�ӳ���ǵ�ǰ��ImageView��tag��tag�����position�Ĳ�ͬ����ͬ����Ӧ��task�Ƿ���� 
     * @param url 
     * @return 
     */  
    private boolean isTasksContains(String url){  
        boolean b = false;  
        if(map != null && map.get(url) != null){  
            b = true;  
        }  
        return b;  
    }  
      
    /** 
     * ɾ��map�и�url����Ϣ����һ������Ҫ����ȻMyAsyncTask�����ûᡰһֱ��������map�� 
     * @param url 
     */  
    private void removeTaskFormMap(String url){  
        if(url != null && map != null && map.get(url) != null){  
            map.remove(url);  
            System.out.println("��ǰmap�Ĵ�С=="+map.size());  
        }  
    }  
      
    /** 
     * ���ļ�����ͼƬ 
     * @param mActivity  
     * @param imageName ͼƬ���� 
     * @param path ͼƬ·�� 
     * @return 
     */  
    private Bitmap getBitmapFromFile(Activity mActivity,String imageName,String path){  
        Bitmap bitmap = null;  
        if(imageName != null){  
            File file = null;  
            String real_path = "";  
            try {  
                if(Util.getInstance().hasSDCard()){  
                    real_path = Util.getInstance().getExtPath() + (path != null && path.startsWith("/") ? path : "/" + path);  
                }else{  
                    real_path = Util.getInstance().getPackagePath(mActivity) + (path != null && path.startsWith("/") ? path : "/" + path);  
                }  
                file = new File(real_path, imageName);  
                if(file.exists()) ; 
        //      bitmap = BitmapFactory.decodeStream(new FileInputStream(file));  
            } catch (Exception e) {  
                e.printStackTrace();  
                bitmap = null;  
            }  
        }  
        return bitmap;  
    }  
      
    /** 
     * �����غõ�ͼƬ��ŵ��ļ��� 
     * @param path ͼƬ·�� 
     * @param mActivity 
     * @param imageName ͼƬ���� 
     * @param bitmap ͼƬ 
     * @return 
     */  
    private boolean setBitmapToFile(String path,Activity mActivity,String imageName,Bitmap bitmap){  
        File file = null;  
        String real_path = "";  
        try {  
            if(Util.getInstance().hasSDCard()){  
                real_path = Util.getInstance().getExtPath() + (path != null && path.startsWith("/") ? path : "/" + path);  
            }else{  
                real_path = Util.getInstance().getPackagePath(mActivity) + (path != null && path.startsWith("/") ? path : "/" + path);  
            }  
            file = new File(real_path, imageName);  
            if(!file.exists()){  
                File file2 = new File(real_path + "/");  
                file2.mkdirs();  
            }  
            file.createNewFile();  
            FileOutputStream fos = null;  
            if(Util.getInstance().hasSDCard()){  
                fos = new FileOutputStream(file);  
            }else{  
                fos = mActivity.openFileOutput(imageName, Context.MODE_PRIVATE);  
            }  
              
            if (imageName != null && (imageName.contains(".png") || imageName.contains(".PNG"))){  
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);  
            }  
            else{  
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);  
            }  
            fos.flush();  
            if(fos != null){  
                fos.close();  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * ����������һ�㲻���� 
     * @param path 
     * @param mActivity 
     * @param imageName 
     */  
    private void removeBitmapFromFile(String path,Activity mActivity,String imageName){  
        File file = null;  
        String real_path = "";  
        try {  
            if(Util.getInstance().hasSDCard()){  
                real_path = Util.getInstance().getExtPath() + (path != null && path.startsWith("/") ? path : "/" + path);  
            }else{  
                real_path = Util.getInstance().getPackagePath(mActivity) + (path != null && path.startsWith("/") ? path : "/" + path);  
            }  
            file = new File(real_path, imageName);  
            if(file != null)  
            file.delete();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * �첽����ͼƬ�ķ��� 
     * @author yanbin 
     * 
     */  
    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap>{  
        private ImageView mImageView;  
        private String url;  
        private OnImageDownload download;  
        private String path;  
        private Activity mActivity;  
          
        public MyAsyncTask(String url,ImageView mImageView,String path,Activity mActivity,OnImageDownload download){  
            this.mImageView = mImageView;  
            this.url = url;  
            this.path = path;  
            this.mActivity = mActivity;  
            this.download = download;  
        }  
  
        @Override  
        protected Bitmap doInBackground(String... params) {  
            Bitmap data = null;  
            if(url != null){  
                try {  
                    URL c_url = new URL(url);  
                    InputStream bitmap_data = c_url.openStream();  
                    data = BitmapFactory.decodeStream(bitmap_data);  
                    String imageName = Util.getInstance().getImageName(url);  
                    if(!setBitmapToFile(path,mActivity,imageName, data)){  
                        removeBitmapFromFile(path,mActivity,imageName);  
                    }  
                    imageCaches.put(url, new SoftReference<Bitmap>(data.createScaledBitmap(data, 100, 100, true)));  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            return data;  
        }  
  
        @Override  
        protected void onPreExecute() {  
            super.onPreExecute();  
        }  
  
        @Override  
        protected void onPostExecute(Bitmap result) {  
            //�ص�����ͼƬ   
            if(download != null){  
                download.onDownloadSucc(result,url,mImageView);  
                //��url��Ӧ��task�Ѿ�������ɣ���map�н���ɾ��   
                removeTaskFormMap(url);  
            }  
            super.onPostExecute(result);  
        }  
          
    }  
}  
