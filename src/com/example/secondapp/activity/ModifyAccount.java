package com.example.secondapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import org.json.JSONObject;

public class ModifyAccount extends BaseActivity {
	private static LinearLayout back;
	private static TextView preservation;
	public static EditText modifyaccount;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.modifyaccount);
		back = (LinearLayout) findViewById(R.id.back);
		preservation = (TextView) findViewById(R.id.preservation);
		modifyaccount = (EditText) findViewById(R.id.modifyaccount);
		modifyaccount.setText(PersonalMsg.accountnumber.getText());
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		preservation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				save("user_name", modifyaccount.getText().toString());
				HttpParams params = new HttpParams();
				params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
				params.put("name", modifyaccount.getText().toString());
				HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.modifyaccount, params,new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(JSONObject jsonObject) {
						System.out.println("modifyaccountjsonObject = " + jsonObject);
					}
					
					@Override
					public void onFailure(String result, int statusCode, String errorResponse) {
						System.out.println("失败了吗.？");
						super.onFailure(result, statusCode, errorResponse);
					}
				});
			Toast.makeText(ModifyAccount.this, "修改昵称成功", Toast.LENGTH_SHORT).show();
			finish();
			}
		});
	}
}
