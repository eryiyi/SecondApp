package com.example.secondapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import org.json.JSONException;
import org.json.JSONObject;

public class ModifyPassword extends BaseActivity implements View.OnClickListener {
	private static EditText originalpassword;
	private static EditText newpassword;
	private static EditText confirmpassword;
	private static TextView confirm;
	public static int getcode;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.changepassword);
		originalpassword = (EditText) findViewById(R.id.originalpassword);
		newpassword = (EditText) findViewById(R.id.newpassword);
		confirmpassword = (EditText) findViewById(R.id.confirmpassword);
		confirm = (TextView) findViewById(R.id.confirm);
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (originalpassword.getText().length() == 0) {
					Toast.makeText(ModifyPassword.this, "原密码不能为空", Toast.LENGTH_SHORT).show();
				}else if (newpassword.getText().length() == 0) {
					Toast.makeText(ModifyPassword.this, "新密码密码不能为空", Toast.LENGTH_SHORT).show();
				}else if (confirmpassword.getText().length() == 0) {
					Toast.makeText(ModifyPassword.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
				}else if (!newpassword.getText().toString().equals(confirmpassword.getText().toString())) {
					Toast.makeText(ModifyPassword.this, "两次输入的新密码不一样", Toast.LENGTH_SHORT).show();
				}else {
					modifyPassword();
				}
			}
		});
		this.findViewById(R.id.logonback).setOnClickListener(this);
	}
	
	public void modifyPassword(){
		HttpParams params = new HttpParams();
		params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
		params.put("pass", originalpassword.getText().toString());
		params.put("password", newpassword.getText().toString());
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.updatePass, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("jsonObject = " + jsonObject);
				try {
					getcode = jsonObject.getInt("code");
				} catch (JSONException e) {
					e.printStackTrace();
				}finally{
					Message message = new Message();
					message.what = 123;
					handler.sendMessage(message);
				}
			}
		});
	}
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				if (getcode == 200) {
					Toast.makeText(ModifyPassword.this, "修改密码成功",
							Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(ModifyPassword.this, "原密码输入不正确",
							Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
			return true;
		}
	});

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.logonback:
				finish();
				break;
		}
	}
}
