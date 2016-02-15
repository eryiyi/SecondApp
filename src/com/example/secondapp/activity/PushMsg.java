package com.example.secondapp.activity;

import org.json.JSONObject;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.TextView;

public class PushMsg extends BaseActivity{
	TextView pushmsg;
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				
				break;
			default:
				break;
			}
			return false;
		}
	});
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.pushmsg);
		pushmsg = (TextView) findViewById(R.id.pushmsg);
		getMsg();
	}
	
	private void getMsg(){
		HttpParams params = new HttpParams();
		//params.put("userId", Integer.parseInt(getGson().fromJson(getSp().getString("uid", "9"), String.class)));
		params.put("userId", "1");
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getPushMsg, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("jsonObject = " + jsonObject);
				Message message = new Message();
				message.what = 123;
				handler.sendMessage(message);
			}
		});
	}
}
