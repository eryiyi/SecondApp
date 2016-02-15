package com.example.secondapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import com.example.secondapp.activity.AdvertisementDetail;

import java.util.ArrayList;

public class AdvertisingImageadapter extends PagerAdapter{
	private ArrayList<ImageView> viewlist;
	Context context;
	
	public AdvertisingImageadapter(Context context, ArrayList<ImageView> viewlist){
		this.viewlist = viewlist;
		this.context = context;
	}
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		//Warning：不要在这里调用removeView  
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		//对ViewPager页号求模取出View列表中要显示的项 
		position %= viewlist.size();
		switch (position) {
		case 0:
			viewlist.get(0).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context, AdvertisementDetail.class);
					context.startActivity(intent);
				}
			});
			break;
		case 1:
			viewlist.get(1).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context, AdvertisementDetail.class);
					context.startActivity(intent);
				}
			});
			break;
		case 2:
			viewlist.get(2).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context, AdvertisementDetail.class);
					context.startActivity(intent);
				}
			});
			break;
		default:
			break;
		}
		if (position < 0) {
			position = viewlist.size() + position;
		}
		ImageView view = viewlist.get(position);
		//如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException
		ViewParent vp = view.getParent();
		if (vp != null) {
			ViewGroup parent = (ViewGroup)vp;
			parent.removeView(view);
		}
		container.addView(view);
		//add listeners here if necessary  
		return view;
	}

}
