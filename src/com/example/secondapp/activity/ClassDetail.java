package com.example.secondapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.secondapp.R;
import com.example.secondapp.adapter.FruitShowListViewAdapter;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.data.FruitBeanData;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.sharedprefernces.SharedPrefsUtil;
import com.example.secondapp.util.StringUtil;
import com.example.secondapp.view.PullToRefreshView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassDetail extends BaseActivity implements OnClickListener {
	PullToRefreshView classptrv;
	ListView classlv;
	TextView classtitle;
	TextView defaults;
	TextView price;
	TextView sellnum;
	TextView number;
	LinearLayout arrowback;
	LinearLayout shoppingcart;
	FrameLayout frameimg;
	private static int numbercount;
	int getkey;
	String title;
	List<FruitBean> list = new ArrayList<FruitBean>();;
	FruitShowListViewAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		Intent intent = getIntent();
		getkey = intent.getIntExtra("key", 0);
		getdata(getkey);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.classdetail);
		frameimg = (FrameLayout) findViewById(R.id.frameimg);
		number = (TextView) findViewById(R.id.number);
		classlv = (ListView) findViewById(R.id.classlv);
		classtitle = (TextView) findViewById(R.id.classtitle);
		defaults = (TextView) findViewById(R.id.defaults);
		price = (TextView) findViewById(R.id.price);
		sellnum = (TextView) findViewById(R.id.sellnum);
		arrowback = (LinearLayout) findViewById(R.id.arrowback);
		shoppingcart = (LinearLayout) findViewById(R.id.shoppingcart_LL);
		defaults.setOnClickListener(this);
		price.setOnClickListener(this);
		sellnum.setOnClickListener(this);
		arrowback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		shoppingcart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ClassDetail.this, ShoppingCartList.class);
				startActivity(intent);
			}
		});
		
		classlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(ClassDetail.this, CommodityDetailTest.class);
				intent.putExtra("id", list.get(position).getProduct_id());
				startActivity(intent);
			}
		});
		adapter = new FruitShowListViewAdapter(this, list);
		classlv.setAdapter(adapter);
		title = intent.getStringExtra("title");
		classtitle.setText(title);
		if ("1".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
			numbercount = SharedPrefsUtil.getValue(this, "Setting" + getGson().fromJson(getSp().getString("uid", ""), String.class), 0);
			System.out.println("商品详情numbercount = " + numbercount);
			if (numbercount != 0) {
				frameimg.setVisibility(View.VISIBLE);
				number.setText(numbercount + "");
			}else {
				frameimg.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		int position = 0;
		switch (v.getId()) {
		case R.id.defaults:
			position = 0;
			list.clear();
			getdata(getkey);
		break;
		case R.id.price:
			position = 1;
			list.clear();
			getPrice();
		break;
		case R.id.sellnum:
			position = 2;
			list.clear();
			getOrder();
		break;
		default:
		break;
		}
		setselected(position);
	}

	@Override
	public void onResume() {
		if ("1".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
			frameimg.setVisibility(View.VISIBLE);
			numbercount = SharedPrefsUtil.getValue(this, "Setting" + getGson().fromJson(getSp().getString("uid", ""), String.class), 0);
			number.setText(numbercount + "");
		}
		super.onResume();
	}
	
	public void getPrice(){
		HttpParams params = new HttpParams();
		params.put("type_id", getkey);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getproducttypeprice, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject s) {
				if (StringUtil.isJson(s.toString())) {
					try {
						JSONObject jo = new JSONObject(s.toString());
						String code =  jo.getString("code");
						String dataJ =  jo.getString("data");
						if(Integer.parseInt(code) == 200 && !"false".equals(dataJ)){
							FruitBeanData data = getGson().fromJson(s.toString(), FruitBeanData.class);
							if(data != null && data.getData() != null){
								list.addAll(data.getData());
							}

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				Message message = new Message();
				message.what = 123;
				handler.sendMessage(message);
			}
		});
	}
	
	public void getOrder(){
		HttpParams params = new HttpParams();
		params.put("type_id", getkey);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getproducttypeorder, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject s) {
				if (StringUtil.isJson(s.toString())) {
					try {
						JSONObject jo = new JSONObject(s.toString());
						String code =  jo.getString("code");
						String dataJ =  jo.getString("data");
						if(Integer.parseInt(code) == 200 && !"false".equals(dataJ)){
							FruitBeanData data = getGson().fromJson(s.toString(), FruitBeanData.class);
							if(data != null && data.getData() != null){
								list.addAll(data.getData());
							}

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				Message message = new Message();
				message.what = 123;
				handler.sendMessage(message);
			}
		});
	}
	
	public void setselected(int position) {
		switch (position) {
		case 0:
			defaults.setTextColor(getResources().getColor(R.color.common_white));
			price.setTextColor(getResources().getColor(R.color.redstandard));
			sellnum.setTextColor(getResources().getColor(R.color.redstandard));
			defaults.setBackgroundDrawable(getResources().getDrawable(R.drawable.defaultsred));
			price.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));
			sellnum.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));
			break;
		case 1:
			defaults.setTextColor(getResources().getColor(R.color.redstandard));
			price.setTextColor(getResources().getColor(R.color.common_white));
			sellnum.setTextColor(getResources().getColor(R.color.redstandard));
			defaults.setBackgroundColor(getResources().getColor(R.color.transparent));
			price.setBackgroundColor(getResources().getColor(R.color.redstandard));
			sellnum.setBackgroundColor(getResources().getColor(R.color.transparent));
			break;
		case 2:
			defaults.setTextColor(getResources().getColor(R.color.redstandard));
			price.setTextColor(getResources().getColor(R.color.redstandard));
			sellnum.setTextColor(getResources().getColor(R.color.common_white));
			defaults.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));
			price.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));
			sellnum.setBackgroundDrawable(getResources().getDrawable(R.drawable.salered));
			break;
		default:
			break;
		}
	}

	public void getdata(int tpyeid){
		HttpParams params = new HttpParams();
		params.put("type_id", tpyeid);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getproducttype, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject s) {
				if (StringUtil.isJson(s.toString())) {
					try {
						JSONObject jo = new JSONObject(s.toString());
						String code =  jo.getString("code");
						String dataJ =  jo.getString("data");
						if(Integer.parseInt(code) == 200 && !"false".equals(dataJ)){
							FruitBeanData data = getGson().fromJson(s.toString(), FruitBeanData.class);
							if(data != null && data.getData()!= null){
								list.addAll(data.getData());
							}

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
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
				classlv.setAdapter(adapter);
				//adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			return false;
		}
	});
	
}
