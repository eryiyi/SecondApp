package com.example.secondapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.sdk.pay.PayUtils;
import com.alipay.sdk.pay.PayUtils.ResponseHandler;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;

public class PayMode extends BaseActivity implements OnClickListener{
	CheckBox checked1;
	CheckBox checked2;
	CheckBox checked3;
	TextView textpm;
	LinearLayout back;
	Button payfor;
	PayUtils payed;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.paymode);
		checked1 = (CheckBox) findViewById(R.id.checked1);
		checked2 = (CheckBox) findViewById(R.id.checked2);
		checked3 = (CheckBox) findViewById(R.id.checked3);
		textpm = (TextView) findViewById(R.id.textpm);
		back = (LinearLayout) findViewById(R.id.back);
		payfor = (Button) findViewById(R.id.payfor);
		checked1.setOnClickListener(this);
		checked2.setOnClickListener(this);
		checked3.setOnClickListener(this);
		payfor.setOnClickListener(this);
		back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.checked1:
			System.out.println("checked1");
			checked1.setChecked(true);
			checked2.setChecked(false);
			checked3.setChecked(false);
			textpm.setText(R.string.Alipay);
			break;
		case R.id.checked2:
			System.out.println("checked2");
			checked1.setChecked(false);
			checked2.setChecked(true);
			checked3.setChecked(false);
			textpm.setText(R.string.pay2);
			break;
		case R.id.checked3:
			System.out.println("checked3");
			checked1.setChecked(false);
			checked2.setChecked(false);
			checked3.setChecked(true);
			textpm.setText(R.string.pay3);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.payfor:
			//payfor.pay(goodsName, goodsInfo, price, orderId)
			break;
		default:
			break;
		}
	}
	
	public void initPayfor(){
		payed = new PayUtils(this, new ResponseHandler() {
			
			@Override
			public void onWebserviceSucceed() {
				
			}
		});
	}
}
