package com.example.secondapp;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.baidu.android.pushservice.PushMessageReceiver;

public class PushTestReceiver extends PushMessageReceiver{

	@Override
	public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {
		Log.i("villa", "onBind");
	}

	@Override
	public void onDelTags(Context arg0, int arg1, List<String> arg2, List<String> arg3, String arg4) {
		Log.i("villa", "onDelTags");
		
	}

	@Override
	public void onListTags(Context arg0, int arg1, List<String> arg2, String arg3) {
		Log.i("villa", "onListTags");
		
	}

	@Override
	public void onMessage(Context arg0, String arg1, String arg2) {
		Log.i("villa", "onMessage");
		
	}

	@Override
	public void onNotificationArrived(Context arg0, String arg1, String arg2, String arg3) {
		Log.i("villa", "onNotificationArrived");
		
	}

	@Override
	public void onNotificationClicked(Context context, String arg1, String arg2, String arg3) {
		Log.i("villa", "onNotificationClicked");
		Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.example.secondapp");
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
       /* Bundle args = new Bundle();
        args.putString("name", "电饭锅");
        args.putString("price", "58元");
        args.putString("detail", "这是一个好锅, 这是app进程不存在，先启动应用再启动Activity的");
        launchIntent.putExtra(Constants.EXTRA_BUNDLE, context);*/
        context.startActivity(launchIntent);
	}

	@Override
	public void onSetTags(Context arg0, int arg1, List<String> arg2, List<String> arg3, String arg4) {
		Log.i("villa", "onSetTags");
		
	}

	@Override
	public void onUnbind(Context arg0, int arg1, String arg2) {
		Log.i("villa", "onUnbind");
		
	}

}
