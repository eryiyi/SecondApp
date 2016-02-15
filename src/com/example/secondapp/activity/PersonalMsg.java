package com.example.secondapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.secondapp.MainActivity;
import com.example.secondapp.R;
import com.example.secondapp.adapter.AnimateFirstDisplayListener;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.data.UploadData;
import com.example.secondapp.networkbitmap.BitmapCache;
import com.example.secondapp.sharedprefernces.SharedPrefsUtil;
import com.example.secondapp.upload.CommonUtil;
import com.example.secondapp.util.CompressPhotoUtil;
import com.example.secondapp.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PersonalMsg extends BaseActivity implements OnClickListener {
	RelativeLayout personalsetting1;
	RelativeLayout personalsetting2;
	RelativeLayout personalsetting4;
	RelativeLayout personalsetting5;
	LinearLayout back;
	TextView exitlogon;
	ImageView userimage;
	public BitmapCache cache = new BitmapCache();
	String path;
	private static PopupWindow popupWindow;
	private static View view;
	private static TextView takepic;
	private static TextView fromphotoalbum;
	private static TextView cancel;
	private static TextView cellphonenum;
	public static TextView accountnumber;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	File tempFile = new File(Environment.getExternalStorageDirectory() + "/Postcard", getPhotoFileName());
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private static final File PHOTO_CACHE_DIR = new File(Environment.getExternalStorageDirectory() + "/liangxun/PhotoCache");
	private String txpic = "";
	private String pics = "";

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.personalmsg);
		personalsetting1 = (RelativeLayout) findViewById(R.id.personalsetting1);
		personalsetting2 = (RelativeLayout) findViewById(R.id.personalsetting2);
		personalsetting4 = (RelativeLayout) findViewById(R.id.personalsetting4);
		personalsetting5 = (RelativeLayout) findViewById(R.id.personalsetting5);
		userimage = (ImageView) findViewById(R.id.userimage); 
		exitlogon = (TextView) findViewById(R.id.exitlogon);
		back = (LinearLayout) findViewById(R.id.back);
		cellphonenum = (TextView) findViewById(R.id.cellphonenum);
		accountnumber = (TextView) findViewById(R.id.accountnumber);
		personalsetting1.setOnClickListener(this);
		personalsetting2.setOnClickListener(this);
		personalsetting4.setOnClickListener(this);
		personalsetting5.setOnClickListener(this);
		back.setOnClickListener(this);
		exitlogon.setOnClickListener(this);
		path = SharedPrefsUtil.getValue(this, "imagepath", null);
		System.out.println("--path-- = " + path);
		if (path != null) {
			File file = new File(path);
			if (file.exists()) {
				Bitmap bm = BitmapFactory.decodeFile(path);
				userimage.setImageBitmap(bm);
			}
		}
		cellphonenum.setText(getGson().fromJson(getSp().getString("mobile", ""), String.class));
		accountnumber.setText(getGson().fromJson(getSp().getString("user_name", ""), String.class));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.personalsetting1:
			Intent intentmo = new Intent(PersonalMsg.this, ModifyAccount.class);
			startActivity(intentmo);
			break;
		case R.id.personalsetting2:
			showPopupWindow();
			break;
		case R.id.personalsetting4:
			Intent intent = new Intent(PersonalMsg.this, ModifyPassword.class);
			startActivity(intent);
			break; 
		case R.id.personalsetting5:
			Intent intent1 = new Intent(PersonalMsg.this, ReceivingAddress.class);
			startActivity(intent1);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.takepic:
			// 调用系统的拍照功能
			Intent intenttakepic = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
			// 指定调用相机拍照后照片的储存路径
			intenttakepic.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
			startActivityForResult(intenttakepic, PHOTO_REQUEST_TAKEPHOTO);
			popupWindow.dismiss();
			break;
		case R.id.fromphotoalbum:
			Intent intentfrom = new Intent(Intent.ACTION_PICK, null);
			intentfrom.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intentfrom, PHOTO_REQUEST_GALLERY);
			popupWindow.dismiss();
			break;
		case R.id.cancel:
			popupWindow.dismiss();
			break;
		case R.id.exitlogon:
			save("is_login", "0");
			Toast.makeText(PersonalMsg.this, "已退出当前用户", Toast.LENGTH_SHORT).show();
			Intent intent2 = new Intent(PersonalMsg.this, MainActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}

	}

	public void showPopupWindow(){
		popupWindow = new PopupWindow(PersonalMsg.this);
		popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true); //可聚焦
		popupWindow.setOutsideTouchable(true);//设置外部可点击
		ColorDrawable colorDrawable = new ColorDrawable(0xffffffff);
		popupWindow.setBackgroundDrawable(colorDrawable); //设置背景
		LayoutInflater inflater = LayoutInflater.from(PersonalMsg.this);
		view = inflater.inflate(R.layout.itempopwindow, null);
		//设置PopupWindow的弹出和消失效果
		popupWindow.setAnimationStyle(R.style.popupAnimation);
		takepic = (TextView) view.findViewById(R.id.takepic);
		fromphotoalbum = (TextView) view.findViewById(R.id.fromphotoalbum);
		cancel = (TextView) view.findViewById(R.id.cancel);
		takepic.setOnClickListener(this);
		fromphotoalbum.setOnClickListener(this);
		cancel.setOnClickListener(this);
		popupWindow.setContentView(view);
		popupWindow.showAtLocation(findViewById(R.id.exitlogon), Gravity.BOTTOM, 0, 0);
		backgroundAlpha(0.4f);
		popupWindow.setOnDismissListener(new poponDismissListener());
	}
	
	public void backgroundAlpha(float bgAlpha){
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
	}
	
	public class poponDismissListener implements PopupWindow.OnDismissListener{
		@Override
		public void onDismiss() {
			backgroundAlpha(1f);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
			startPhotoZoom(Uri.fromFile(tempFile), 150);
			break;
		case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
			// 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
			if (data != null){
				Uri originalUri = data.getData();        //获得图片的uri 
				//ContentResolver resolver = getContentResolver();
				//这里开始的第二部分，获取图片的路径：
				String[] proj = {MediaStore.Images.Media.DATA};
				//好像是android多媒体数据库的封装接口，具体的看Android文档
				Cursor cursor = managedQuery(originalUri, proj, null, null, null); 
				//按我个人理解 这个是获得用户选择的图片的索引值
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				//将光标移至开头 ，这个很重要，不小心很容易引起越界
				cursor.moveToFirst();
				//最后根据索引值获取图片路径
				/*path = cursor.getString(column_index);
				System.out.println("~~path~~ = " + path);
				SharedPrefsUtil.putValue(this, "imagepath", path);
				cache.addBitmap(path, photo);
				userimage.setImageBitmap(cache.getBitmap(path));*/
				startPhotoZoom(data.getData(), 150);
			}
			break;
		case PHOTO_REQUEST_CUT:// 返回的结果
			if (data != null){
				setPicToView(data);
			}
			break;
		}
        super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * 保存裁剪之后的图片数据
	 *
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			if (photo != null) {
				pics = CompressPhotoUtil.saveBitmap2file(photo, System.currentTimeMillis() + ".jpg", PHOTO_CACHE_DIR);
				userimage.setImageBitmap(photo);
				sendCover(pics);
			}
		}
	}
	
	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");  
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
	
	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	
	@Override
	public void onPause() {
		//path = SharedPrefsUtil.getValue(this, "imagepath", "sb");
		super.onPause();
	}
	
	@Override
	public void onResume() {
		/*path = SharedPrefsUtil.getValue(this, "imagepath", null);
		if (path != null) {
			File file = new File(path);
			if (file.exists()) {
				Bitmap bm = BitmapFactory.decodeFile(path);
				userimage.setImageBitmap(bm);
			}
		}else{
		}*/
		accountnumber.setText(getGson().fromJson(getSp().getString("user_name", ""), String.class));
		super.onResume();
	}


	public void sendCover(String pics){
		File file = new File(pics);
		Map<String, File> files = new HashMap<String, File>();
		files.put("image", file);
		Map<String, String> params = new HashMap<String, String>();
		CommonUtil.addPutUploadFileRequest(this,"http://115.29.208.113/Home/api/uploadImage",files,params,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code1 = jo.getString("code");
								if (Integer.parseInt(code1) == 200) {
									UploadData data = getGson().fromJson(s, UploadData.class);
									save("cover", data.getData());
									Intent intent = new Intent("update_cover_success");
									sendBroadcast(intent);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
					}
				},
				null);
	}
}
