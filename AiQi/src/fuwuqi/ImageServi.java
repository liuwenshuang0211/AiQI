package fuwuqi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageServi {
	  /**
	   * 获取图片
	   * @param path 图片路径
	   * @return
	   */
	public static Bitmap getBitmapFromServer(String imagePath) {

		  HttpGet get = new HttpGet(imagePath);
		  HttpClient client = new DefaultHttpClient();
		  Bitmap pic = null;
		  try {
		   HttpResponse response = client.execute(get);
		   HttpEntity entity = response.getEntity();
		   InputStream is = entity.getContent();

		   pic = BitmapFactory.decodeStream(is);   // 关键是这句代码

		  } catch (ClientProtocolException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  return pic;
		 }

	}