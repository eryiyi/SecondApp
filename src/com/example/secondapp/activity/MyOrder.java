package com.example.secondapp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.fragmentdetail.MOFragment1;
import com.example.secondapp.fragmentdetail.MOFragment2;
import com.example.secondapp.fragmentdetail.MOFragment3;
import com.example.secondapp.fragmentdetail.MOFragment4;

import java.util.ArrayList;
import java.util.List;

public class MyOrder extends BaseActivity implements OnClickListener{
	ViewPager viewPager;
	List<Fragment> list;
	Fragment mo1;
	Fragment mo2;
	Fragment mo3;
	Fragment mo4;
	RadioButton text1;
	RadioButton text2;
	RadioButton text3;
	RadioButton text4;
	LinearLayout back;
	private ImageView mIv;// 动画图片
	private static int off = 0;
	private static int ll;
	private static int dhBmpW;
	RadioGroup radiogroup;
	private static int position;
	private static int flag;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myorder);
		initView();
		viewPager = (ViewPager) findViewById(R.id.myordervp);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		back = (LinearLayout) findViewById(R.id.back);
		text1 = (RadioButton) findViewById(R.id.text1);
		text2 = (RadioButton) findViewById(R.id.text2);
		text3 = (RadioButton) findViewById(R.id.text3);
		text4 = (RadioButton) findViewById(R.id.text4);
		back.setOnClickListener(this);
		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		text3.setOnClickListener(this);
		text4.setOnClickListener(this);
		mo1 = new MOFragment1();
		mo2 = new MOFragment2();
		mo3 = new MOFragment3();
		mo4 = new MOFragment4();
		list = new ArrayList<Fragment>();
		list.add(mo1);
		list.add(mo2);
		list.add(mo3);
		list.add(mo4);
		myViewPagerAdapter adapter = new myViewPagerAdapter(getSupportFragmentManager(), list);
		viewPager.setAdapter(adapter);
		flag = getIntent().getIntExtra("class", 0);
		viewPager.setCurrentItem(flag, true);
		setSelected(flag);
		viewPager.setOffscreenPageLimit(3);
		viewPager.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				return false;
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					text1.performClick();
					break;
				case 1:
					text2.performClick();
					break;
				case 2:
					text3.performClick();
					break;
				case 3:
					text4.performClick();
					break;
				default:
					break;
				}
				setSelected(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				int ox = ll*arg0 + arg2/4;
				Animation am = new TranslateAnimation(off, ox,0,0);
				am.setFillAfter(true);
				am.setDuration(300);
				mIv.startAnimation(am);
				off = ox;
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
	}
	
	private void initView() {
		mIv = (ImageView) findViewById(R.id.redmark);
		//取图的宽和高
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.btn_tab_highlight_middle_bg);
		int w = bm.getWidth();
		dhBmpW = bm.getWidth(); 
		//取屏幕的宽
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		ll = screenW/4;
		//设置图片
		Matrix matrix = new Matrix();
		matrix.postScale(screenW/(4*w)+1, dhBmpW);			
		Bitmap newbm  = Bitmap.createBitmap(bm, 0, 0, w, dhBmpW, matrix, true);
				
		//设置ImageView的宽
		LayoutParams para;
		para = mIv.getLayoutParams();
		para.width = screenW/4;
		mIv.setLayoutParams(para);
		mIv.setImageBitmap(newbm);
		mIv.setColorFilter(getResources().getColor(R.color.redstandard));
	}
	
	class myViewPagerAdapter extends FragmentPagerAdapter {
		List<Fragment> list;

		public myViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			return list.size();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text1:
			position = 0;
			viewPager.setCurrentItem(0);
			break;
		case R.id.text2:
			position = 1;
			viewPager.setCurrentItem(1);
			break;
		case R.id.text3:
			position = 2;
			viewPager.setCurrentItem(2);
			break;
		case R.id.text4:
			position = 3;
			viewPager.setCurrentItem(3);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
		setSelected(position);
	}
	
	public void setSelected(int pos){
		switch (pos) {
		case 0:
			text1.setTextColor(getResources().getColor(R.color.redstandard));
			text2.setTextColor(getResources().getColor(R.color.common_grey));
			text3.setTextColor(getResources().getColor(R.color.common_grey));
			text4.setTextColor(getResources().getColor(R.color.common_grey));
			break;
		case 1:
			text1.setTextColor(getResources().getColor(R.color.common_grey));
			text2.setTextColor(getResources().getColor(R.color.redstandard));
			text3.setTextColor(getResources().getColor(R.color.common_grey));
			text4.setTextColor(getResources().getColor(R.color.common_grey));
			break;
		case 2:
			text1.setTextColor(getResources().getColor(R.color.common_grey));
			text2.setTextColor(getResources().getColor(R.color.common_grey));
			text3.setTextColor(getResources().getColor(R.color.redstandard));
			text4.setTextColor(getResources().getColor(R.color.common_grey));
			break;
		case 3:
			text1.setTextColor(getResources().getColor(R.color.common_grey));
			text2.setTextColor(getResources().getColor(R.color.common_grey));
			text3.setTextColor(getResources().getColor(R.color.common_grey));
			text4.setTextColor(getResources().getColor(R.color.redstandard));
			break;
		default:
			break;
		}
	}
}
