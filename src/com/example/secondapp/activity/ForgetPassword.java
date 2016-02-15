package com.example.secondapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.CountDownButtonHelper;
import com.example.secondapp.serviceId.CountDownButtonHelper.OnFinishListener;
import com.example.secondapp.serviceId.ServerId;
import org.json.JSONObject;

public class ForgetPassword extends BaseActivity {
	private static EditText phonenumber;
	private static EditText vercode;
	private static Button sendcode;
	private static TextView getpassword;
	private static int judgeuserexistcode;
	private static int code;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.frogetpassword);
		phonenumber = (EditText) findViewById(R.id.phonenumber);
		vercode = (EditText) findViewById(R.id.vercode);
		sendcode = (Button) findViewById(R.id.sendcode);
		getpassword = (TextView) findViewById(R.id.getpassword);
		sendcode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (phonenumber.getText().length() == 11) {
					CountDownButtonHelper helper = new CountDownButtonHelper(sendcode, "重新发送", 60, 1);
					helper.setOnFinishListener(new OnFinishListener() {
						
						@Override
						public void finish() {
							//Toast.makeText(Register.this, "倒计时结束",Toast.LENGTH_SHORT).show();
							sendcode.setText(R.string.sendnum);
							sendcode.setBackgroundDrawable(getResources().getDrawable(R.drawable.layout_selectorred));
						}
					});
					judgeUserExist();
					helper.start(ForgetPassword.this);
				}else{
					Toast.makeText(ForgetPassword.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		getpassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (phonenumber.getText().toString().length() != 11) {
					Toast.makeText(ForgetPassword.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
				}else if (vercode.getText().length() == 0) {
					Toast.makeText(ForgetPassword.this, "请输入验证码", Toast.LENGTH_SHORT).show();
				}else {
					confirmcode();
				}
			}
		});
		
		LinearLayout back = (LinearLayout) findViewById(R.id.logonback);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (judgeuserexistcode == 200) {
					/*Intent intent = new Intent(Register.this, Logon.class);
					intent.putExtra("accountnumber", phonenumber.getText().toString());
					startActivity(intent);*/
					getPhoneCode();
				}else {
					Toast.makeText(ForgetPassword.this, "该手机号还没有注册", Toast.LENGTH_SHORT).show();
				}
				break;
			case 123:
				if (code == 200) {
					Intent intent = new Intent(ForgetPassword.this, forgetAndModifyPSD.class);
					intent.putExtra("phonenumber", phonenumber.getText().toString());
					startActivity(intent);
				}
				break;
			default:
				break;
			}
			return false;
		}
	});
	
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
	
	public void confirmcode(){
		HttpParams params = new HttpParams();
		params.put("mobile", phonenumber.getText().toString());
		params.put("code", vercode.getText().toString());
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getmobilecode, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				code = jsonObject.optInt("code");
				Message message = new Message();
				message.what = 123;
				handler.sendMessage(message);
			}
		});
	}  
	
	
}
