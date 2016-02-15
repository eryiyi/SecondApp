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
import com.example.secondapp.bean.AdressBean;

public class EditAddress extends BaseActivity {
	AdressBean adressbean;
	EditText receiver;
	EditText detailedaddress;
	EditText phonenumber;
	TextView save;
	LinearLayout back;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.editadress);
		receiver = (EditText) findViewById(R.id.receiver);
		detailedaddress = (EditText) findViewById(R.id.detailedaddress);
		phonenumber = (EditText) findViewById(R.id.phonenumber);
		back = (LinearLayout) findViewById(R.id.back);
		adressbean = (AdressBean) getIntent().getSerializableExtra("receiveaddress");
		final int pos = getIntent().getIntExtra("pos", 26);
		System.out.println("pos = " + pos);
		save = (TextView) findViewById(R.id.save);
		receiver.setText(adressbean.name);
		detailedaddress.setText(adressbean.adress);
		phonenumber.setText(adressbean.phonenumber);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (phonenumber.getText().length() != 11) {
					Toast.makeText(EditAddress.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
				}else {
					ReceivingAddress.list.get(pos).name = receiver.getText().toString();
					ReceivingAddress.list.get(pos).adress = detailedaddress.getText().toString();
					ReceivingAddress.list.get(pos).phonenumber = phonenumber.getText().toString();
					ReceivingAddress.adapter.notifyDataSetChanged();
					Toast.makeText(EditAddress.this, "修改地址成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
