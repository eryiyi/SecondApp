package com.example.secondapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.MyOrderBean;
import com.example.secondapp.networkbitmap.BitmapUtil;
import com.example.secondapp.serviceId.ServerId;

public class ViewLogistics extends BaseActivity{
	MyOrderBean bean;
	TextView expressname;
	TextView expressnumber;
	LinearLayout back;
	ImageView commodityimage;
	TextView commodityname;
	TextView commodityprice;
	TextView commoditynumber;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.viewlogistics);
		commodityname = (TextView) findViewById(R.id.commodityname);
		commodityprice = (TextView) findViewById(R.id.commodityprice);
		commoditynumber = (TextView) findViewById(R.id.commoditynumber);
		bean = (MyOrderBean) getIntent().getSerializableExtra("mylogistics");
		expressname = (TextView) findViewById(R.id.expressname);
		expressname.setText(bean.getOrder_no());
		expressnumber = (TextView) findViewById(R.id.expressnumber);
		expressnumber.setText(bean.getGoods_nums());
		back = (LinearLayout) findViewById(R.id.back);
		commodityimage = (ImageView) findViewById(R.id.commodityimage);
		BitmapUtil.getInstance().download(ServerId.serveradress, bean.getImg(), commodityimage);
		commodityname.setText(bean.getOrder_no());
		commodityprice.setText("￥" + bean.getReal_price());
		commoditynumber.setText("×" + bean.getGoods_nums() + "件");
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
