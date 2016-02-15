package com.example.secondapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.example.secondapp.R;
import com.example.secondapp.activity.Logon;
import com.example.secondapp.activity.ShoppingCartList;
import com.example.secondapp.base.BaseFragment;
import com.example.secondapp.city.CityList;
import com.example.secondapp.sharedprefernces.SharedPrefsUtil;
import com.example.secondapp.util.StringUtil;

public class PublicTitleFragment extends BaseFragment{
	private View view;
	TextView title;
	int getchange;
	LinearLayout magnifer;
	RelativeLayout shoppingcart;
	private static int numbercount;
	ImageView numberimage;
	TextView select_city;//城市
	TextView number;
	FrameLayout frameimg;
	//private boolean dingWeiFlag = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.publictitle, null);
		registerBoradcastReceiver();
		title = (TextView) view.findViewById(R.id.title);
		frameimg = (FrameLayout) view.findViewById(R.id.frameimg);
		magnifer = (LinearLayout) view.findViewById(R.id.magnifer);
		shoppingcart = (RelativeLayout) view.findViewById(R.id.shoppingcart);
		numberimage = (ImageView) view.findViewById(R.id.numberimage);
		number = (TextView) view.findViewById(R.id.number);
		getchange = getArguments().getInt("changetitle", 100);
		
		switch (getchange) {
		case BottomFragment.MAINPAGE:
			title.setText(R.string.sellfruit);
			break;
		case BottomFragment.RECOMMEND:
			title.setText(R.string.recommend);
			break;
		case BottomFragment.CLASSIFICATION:
			title.setText(R.string.classification);
			break;
		case BottomFragment.PERSONALCENTER:
			magnifer.setVisibility(View.GONE);
			shoppingcart.setVisibility(View.GONE);
			title.setText(R.string.personalcenter);
			break;
		default:
			break;
		}
		shoppingcart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if ("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
					Toast.makeText(getActivity(), "请登录后查看购物车", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getActivity(), Logon.class);
					intent.putExtra("nozero", 2);
					startActivity(intent);
				}else{
					Intent intent = new Intent(getActivity(), ShoppingCartList.class);
					startActivity(intent);
				}
			}
		});
		
		magnifer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), CityList.class);
				intent.putExtra("city",select_city.getText().toString().trim());
				startActivity(intent);
			}
		});
		if (!"0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
			numbercount = SharedPrefsUtil.getValue(getActivity(), "Setting" + getGson().fromJson(getSp().getString("uid", ""), String.class), 0);
			if (numbercount != 0) {
				frameimg.setVisibility(View.VISIBLE);
				number.setText(numbercount + "");
			}else {
				frameimg.setVisibility(View.GONE);
			}
		}
		select_city = (TextView) view.findViewById(R.id.select_city);
		//select_city.setText(SecondApplication.cityName);
		if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("city", ""), String.class))){
			select_city.setText(getGson().fromJson(getSp().getString("city", ""), String.class));
		}else {
			select_city.setText("重庆");
		}
		/*if(!SecondApplication.dingWeiFlag){
	    	Intent intent = new Intent(getActivity(),CityList.class);
	    	intent.putExtra("city", "北京");
	    	startActivity(intent);
	    }*/
		return view;  
	}
	
	@Override
	public void onResume() {
		if ("1".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
			frameimg.setVisibility(View.VISIBLE);
			numbercount = SharedPrefsUtil.getValue(getActivity(), "Setting" + getGson().fromJson(getSp().getString("uid", ""), String.class), 0);
			number.setText(numbercount + "");
		}
		super.onResume();
	}

	//广播接收动作
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			
			if (action.equals("add_goods_success")) {
				number.setText(String.valueOf((numbercount+1)));
			}
			if (action.equals("select_city_success")) {
				//
				if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("city", ""), String.class))){
					select_city.setText(getGson().fromJson(getSp().getString("city", ""), String.class));
				}else {
					select_city.setText("重庆");
				}
				//select_city.setText(SecondApplication.cityName);
			}
		}
	};

	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("add_goods_success");
		myIntentFilter.addAction("select_city_success");
		//注册广播
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}
}
