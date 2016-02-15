package com.example.secondapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.bean.Response;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class Logon extends BaseActivity implements OnClickListener{
	TextView register;
	TextView forgetpsd;
	TextView logon;
	LinearLayout back;
	EditText logonusername;
	EditText logonpsd;
	JSONObject object;
	FruitBean fruitBean;
	private static int getkey;
	private static String getuser;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.logon);
		fruitBean = (FruitBean) getIntent().getSerializableExtra("fruitdetail");
		register = (TextView) findViewById(R.id.register);
		forgetpsd = (TextView) findViewById(R.id.forgetpsd);
		back = (LinearLayout) findViewById(R.id.logonback);
		logon = (TextView) findViewById(R.id.logon);
		logonusername = (EditText) findViewById(R.id.logonusername);
		logonpsd = (EditText) findViewById(R.id.logonpsd);
		getkey = getIntent().getIntExtra("nozero", 0);
		getuser = getIntent().getStringExtra("accountnumber");
		logonusername.setText(getuser);
		register.setOnClickListener(this);
		forgetpsd.setOnClickListener(this);
		back.setOnClickListener(this);
		logon.setOnClickListener(this);
		if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("mobile", ""), String.class))){
			logonusername.setText(getGson().fromJson(getSp().getString("mobile", ""), String.class));
		}
		if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("password", ""), String.class))){
			logonpsd.setText(getGson().fromJson(getSp().getString("password", ""), String.class));
		}
		if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("password", ""), String.class)) && !StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("mobile", ""), String.class))){
			login(getGson().fromJson(getSp().getString("mobile", ""), String.class), getGson().fromJson(getSp().getString("password", ""), String.class));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:
			Intent intent = new Intent(this, Register.class);
			startActivity(intent);
			break;
		case R.id.forgetpsd:
			Intent intent1 = new Intent(this, ForgetPassword.class);
			startActivity(intent1);
		case R.id.logonback:
			finish();
			break;
		case R.id.logon:
			login(logonusername.getText().toString().trim(), logonpsd.getText().toString().trim());
			break;
		default:
			break;
		}
		
	}

	void login(String mobile, String pwr){
		HttpParams params = new HttpParams();
		params.put("mobile", mobile);
		params.put("password", pwr);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.logonurl, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				try {
					object = jsonObject.getJSONObject("data");
					Response.code = jsonObject.getInt("code");
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
				if(Response.code == 200 && object != null){
					Toast.makeText(Logon.this, "登录成功", Toast.LENGTH_SHORT).show();
					if(object != null){
						//-------保存登陆的信息-------
						save("uid", object.optInt("uid"));
						try {
							save("user_name", object.getString("user_name"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						try {
							save("mobile", object.getString("mobile"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						save("is_login", "1");
						save("password", logonpsd.getText().toString().trim());
					}
					finish();
				}else{
					Toast.makeText(Logon.this, "无法登录（用户名或密码不正确）", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
			return true;
		}
	});
}
