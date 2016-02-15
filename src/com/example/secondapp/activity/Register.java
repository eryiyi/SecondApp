package com.example.secondapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import com.example.secondapp.MainActivity;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.CountDownButtonHelper;
import com.example.secondapp.serviceId.CountDownButtonHelper.OnFinishListener;
import com.example.secondapp.serviceId.ServerId;
import org.json.JSONObject;

public class Register extends BaseActivity implements View.OnClickListener {
	private static EditText password;
	private static EditText confirmpsd;
	private static EditText phonenumber;
	private static EditText verificationcode;
	private static Button getcode;
	private static TextView confirm;
	private static String message;
	private static int code;
	private static int judgeuserexistcode;
	private static int useruid;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.register);
		LinearLayout logonback = (LinearLayout) findViewById(R.id.logonback);
		password = (EditText) findViewById(R.id.password);
		confirmpsd = (EditText) findViewById(R.id.confirmpsd);
		phonenumber = (EditText) findViewById(R.id.phonenumber);
		verificationcode = (EditText) findViewById(R.id.verificationcode);
		getcode = (Button) findViewById(R.id.getcode);
		confirm = (TextView) findViewById(R.id.confirm);
		logonback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		getcode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (phonenumber.getText().length() == 11) {
					if (phonenumber.getText().toString().equals("")){
						
					}else{
						CountDownButtonHelper helper = new CountDownButtonHelper(getcode, "重新发送", 60, 1);
						helper.setOnFinishListener(new OnFinishListener() {
							
							@Override
							public void finish() {
								//Toast.makeText(Register.this, "倒计时结束",Toast.LENGTH_SHORT).show();
								getcode.setText(R.string.sendnum);
								getcode.setBackgroundDrawable(getResources().getDrawable(R.drawable.layout_selectorred));
							}
						});
						judgeUserExist();
						helper.start(Register.this);
					}
				}else{
					Toast.makeText(Register.this, "请输入正确的手机号码！", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (phonenumber.getText().toString().length() != 11) {
					Toast.makeText(Register.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
				}else if (verificationcode.getText().length() == 0) {
					Toast.makeText(Register.this, "请输入验证码", Toast.LENGTH_SHORT).show();
				}else if (password.getText().length() == 0) {
					Toast.makeText(Register.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				}
				else if (!password.getText().toString().equals(confirmpsd.getText().toString())) {
					Toast.makeText(Register.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
				}else {
					confirmRegister();
				}
			}
		});
		this.findViewById(R.id.xieyi).setOnClickListener(this);
	}
	
	public void judgeUserExist(){
		HttpParams params = new HttpParams();
		params.put("mobile", phonenumber.getText().toString());
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getmobile, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("judgeUserExist() = " + jsonObject);
				judgeuserexistcode = jsonObject.optInt("code");
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		});
	}
	
	public void getPhoneCode(){
		HttpParams params = new HttpParams();
		params.put("tel", phonenumber.getText().toString());
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getphonecode, params, new AsyncHttpResponseHandler(){
		});
	}
	
	public void confirmRegister(){
		HttpParams params = new HttpParams();
		params.put("mobile", phonenumber.getText().toString());
		params.put("code", verificationcode.getText().toString());
		params.put("pass", password.getText().toString());
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getregister, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				message = jsonObject.optString("msg");
				code = jsonObject.optInt("code");
				useruid = jsonObject.optInt("data");
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
				System.out.println("code = " + code);
				if (code == 200) {
					Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
					save("uid", useruid);
					save("mobile", phonenumber.getText().toString());
					save("user_name", phonenumber.getText().toString());
					save("password", password.getText().toString());
					Intent intent = new Intent(Register.this, MainActivity.class);
					intent.putExtra("personcenter", 3);
					startActivity(intent);
				}else{
					Toast.makeText(Register.this, "注册失败，验证码不对", Toast.LENGTH_SHORT).show();
				}  
				break;  
			case 1:
				if (judgeuserexistcode == 200) {
					Toast.makeText(Register.this, "该手机号码已经注册，请直接登陆", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Register.this, Logon.class);
					intent.putExtra("accountnumber", phonenumber.getText().toString());
					startActivity(intent);
				}else {
					getPhoneCode();
				}
				break;
			default:
				break;
			}
			return false;
		}
	});

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.xieyi:
				//协议
				Intent xyView = new Intent(Register.this, XieyiActivity.class);
				startActivity(xyView);
				break;
		}
	}
}
