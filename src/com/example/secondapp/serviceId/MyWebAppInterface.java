package com.example.secondapp.serviceId;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class MyWebAppInterface {
	Context mContext;
	
	public MyWebAppInterface(Context mContext){
		this.mContext = mContext;
	}
	
	@JavascriptInterface
	public void showToast(String msg){
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}
	
	@JavascriptInterface
	public void showDialog(String msg){
		Builder builder = new Builder(mContext);
		builder.setTitle("你的用户名");
		builder.setMessage(msg);
		builder.setPositiveButton("确定", null);
		builder.create().show();
	}
	
	@JavascriptInterface
	public void jsInvokeJava(String img){
		Log.i("image","被点击的图片地址为: " + img);//此时拿到了图片的绝对地址下载下来进行放大预览操作都可以
	}
}
