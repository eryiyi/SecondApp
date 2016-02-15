package com.example.secondapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.secondapp.MainActivity;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import org.json.JSONObject;

public class forgetAndModifyPSD extends BaseActivity {
	private static String phone;
	private static EditText newpassword;
	private static EditText confirmpassword;
	private static TextView confirm;
	private static int code;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.modifypassword);
		phone = getIntent().getStringExtra("phonenumber");
		newpassword = (EditText) findViewById(R.id.newpassword);
		confirmpassword = (EditText) findViewById(R.id.confirmpassword);
		confirm = (TextView) findViewById(R.id.confirm);
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (newpassword.getText().toString().length() == 0) {
					Toast.makeText(forgetAndModifyPSD.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				}else if (!newpassword.getText().toString().equals(confirmpassword.getText().toString())) {
					Toast.makeText(forgetAndModifyPSD.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
				}else {
					changePassword();
				}
			}
		});
	}

	public void changePassword() {
		HttpParams params = new HttpParams();
		params.put("pass", newpassword.getText().toString());
		params.put("mobile", phone);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.updatapass, params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonObject) {
						System.out.println("jsonObject = " + jsonObject);
							code = jsonObject.optInt("code");
							Message message = new Message();
							message.what = 123;
							handler.sendMessage(message);
					}
		});
	}

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				if (code == 200) {
					Toast.makeText(forgetAndModifyPSD.this, "修改密码成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(forgetAndModifyPSD.this, MainActivity.class);
					intent.putExtra("personcenter", 3);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(forgetAndModifyPSD.this, "修改密码失败", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
			return true;
		}
	});
}
