package com.example.aiqi;


import java.io.FileNotFoundException;
import java.io.IOException;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
* @author spring sky<br>
* Email :vipa1888@163.com<br>
* QQ: 840950105<br>
* @version ����ʱ�䣺2012-11-22 ����9:20:03
* ˵������Ҫ����ѡ���ļ�����
*/

@SuppressLint("NewApi") public class SelectPicActivity extends Activity implements OnClickListener{

/***
* ʹ����������ջ�ȡͼƬ
*/
public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
/***
* ʹ������е�ͼƬ
*/
public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

/***
* ��Intent��ȡͼƬ·����KEY
*/
public static final String KEY_PHOTO_PATH = "photo_path";

private final int IMAGE_CODE = 0;
Uri bitmapUri = null;
private final String IMAGE_TYPE = "image/*";

private static final String TAG = "SelectPicActivity";
private static final int SELECT_PIC_KITKAT = 0;
private static final int SELECT_PIC = 1;
private static final int START_ALBUM_CODE = 0;


private LinearLayout dialogLayout;
private Button takePhotoBtn,pickPhotoBtn,cancelBtn;

/**��ȡ����ͼƬ·��*/
private String picPath;
private String path;
private Intent lastIntent ;

private Uri photoUri;
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.tupian);
initView();
}
/**
* ��ʼ������View
*/
private void initView() {
takePhotoBtn = (Button) findViewById(R.id.btn_take_photo);
takePhotoBtn.setOnClickListener(this);
pickPhotoBtn = (Button) findViewById(R.id.btn_pick_photo);
pickPhotoBtn.setOnClickListener(this);
lastIntent = getIntent();
}

@Override
public void onClick(View v) {
switch (v.getId()) {

case R.id.btn_take_photo:
takePhoto();
break;
case R.id.btn_pick_photo:
	selectImage();
break;
default:
finish();
break;
}
}

/**
* ���ջ�ȡͼƬ
*/
private void takePhoto() {
//ִ������ǰ��Ӧ�����ж�SD���Ƿ����
String SDState = Environment.getExternalStorageState();
if(SDState.equals(Environment.MEDIA_MOUNTED))
{

Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
/***
* ��Ҫ˵��һ�£����²���ʹ����������գ����պ��ͼƬ����������е�
* ����ʹ�õ����ַ�ʽ��һ���ô����ǻ�ȡ��ͼƬ�����պ��ԭͼ
* �����ʵ��ContentValues�����Ƭ·���Ļ������պ��ȡ��ͼƬΪ����ͼ������
*/
ContentValues values = new ContentValues(); 
photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); 
intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
/**-----------------*/
startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
}else{
Toast.makeText(this,"�ڴ濨������", Toast.LENGTH_LONG).show();
}
}

/***
* �������ȡͼƬ
*/

private void selectImage() {
	Intent intent = new Intent();
	// Set an explicit MIME data type.
	intent.setType("image/*");
	// Set the general action to be performed.
	intent.setAction(Intent.ACTION_GET_CONTENT);
	// callBack
	startActivityForResult(intent,1);
	
	 
	 }
	 
@Override
protected void onActivityResult(int requestCode,
                                int resultCode, 
                                Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr =this.getContentResolver();
            int sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
			Log.d("sdkVersion:", String.valueOf(sdkVersion));
			Log.d("KITKAT:", String.valueOf(Build.VERSION_CODES.KITKAT));
			if (sdkVersion >= 19) {  // ���� android.os.Build.VERSION_CODES.KITKAT���������ֵ��19
				  path = uri.getPath();//5.0ֱ�ӷ��ص���ͼƬ·�� Uri.getPath is ��  /document/image:46 ��5.0������һ�������ݿ��йص�����ֵ
                  System.out.println("path:" + path);
                  // path_above19:/storage/emulated/0/girl.jpg ������ǻ�ȡ��ͼƬ����ʵ·��
                  path = getPath_above19(SelectPicActivity.this, uri);
                  System.out.println("path_above19:" + path);
                  if(path != null && ( path.endsWith(".png") || path.endsWith(".PNG") ||path.endsWith(".jpg") ||path.endsWith(".JPG") ))
                  {
                  lastIntent.putExtra(KEY_PHOTO_PATH, path);
                  setResult(Activity.RESULT_OK, lastIntent);
                  finish();
                  }else{
                  Toast.makeText(this, "ѡ��ͼƬ�ļ�����ȷ", Toast.LENGTH_LONG).show();
                  }
			} else {
			    path = getFilePath_below19(uri);
			      if(path != null && ( path.endsWith(".png") || path.endsWith(".PNG") ||path.endsWith(".jpg") ||path.endsWith(".JPG") ))
                  {
                  lastIntent.putExtra(KEY_PHOTO_PATH, path);
                  setResult(Activity.RESULT_OK, lastIntent);
                  finish();
                  }else{
                  Toast.makeText(this, "ѡ��ͼƬ�ļ�����ȷ", Toast.LENGTH_LONG).show();
                  }
			}
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    /**
     * API19���»�ȡͼƬ·���ķ���
     * @param uri
     */
    private String getFilePath_below19(Uri uri) {
        //���￪ʼ�ĵڶ����֣���ȡͼƬ��·�����Ͱ汾����û����ģ�����sdk>19���ȡ����
        String[] proj = {MediaStore.Images.Media.DATA};
        //������android��ý�����ݿ�ķ�װ�ӿڣ�����Ŀ�Android�ĵ�
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        //����û�ѡ���ͼƬ������ֵ
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        System.out.println("***************" + column_index);
        //�����������ͷ ���������Ҫ����С�ĺ���������Խ��
        cursor.moveToFirst();
        //����������ֵ��ȡͼƬ·��   ������ƣ�/mnt/sdcard/DCIM/Camera/IMG_20151124_013332.jpg
        String path = cursor.getString(column_index);
        System.out.println("path:" + path);
        return path;
    }


    /**
     * APIlevel 19���ϲ���
     * ������Ŀʱ��������������Ͱ汾API Level�������ҵ���10��
     * ��ˣ�AS����ҵ��õ�API�󣬷��ְ汾�Ų�����Ͱ汾���ݣ�
     * �������õġ�DocumentsContract.isDocumentUri(context, uri)����Level 19 ���ϲ��еģ�
     * ��Ȼ������10��������ʾ����
     * ���    @TargetApi(Build.VERSION_CODES.KITKAT)���ɡ�
     *
     * @param context
     * @param uri
     * @return
     */
   
    @TargetApi(Build.VERSION_CODES.KITKAT) @SuppressLint("NewApi") public  static String getPath_above19(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }



}