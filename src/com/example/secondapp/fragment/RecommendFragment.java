package com.example.secondapp.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseFragment;
import com.example.secondapp.fragmentdetail.RmdFragment1;
import com.example.secondapp.fragmentdetail.RmdFragment2;
import com.example.secondapp.fragmentdetail.RmdFragment3;
import com.example.secondapp.fragmentdetail.RmdFragment4;
import com.example.secondapp.view.DecoratorViewPager;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends BaseFragment implements OnCheckedChangeListener {
	View view;
	//ViewPager viewPager;
	DecoratorViewPager viewPager;
	List<Fragment> list = new ArrayList<Fragment>();
	Fragment rmd1;
	Fragment rmd2;
	Fragment rmd3;
	Fragment rmd4;
	RadioButton text1;
	RadioButton text2;
	RadioButton text3;
	RadioButton text4;
	private ImageView mIv;// 动画图片
	private static int off = 0;
	private static int ll;
	private static int dhBmpW;
	RadioGroup radiogroup;
	private static int position;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.recommendfragment, null);
		initView(view, inflater);
		viewPager = (DecoratorViewPager) view.findViewById(R.id.recommendvp);
		radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);  
		text1 = (RadioButton) view.findViewById(R.id.text1);
		text2 = (RadioButton) view.findViewById(R.id.text2);
		text3 = (RadioButton) view.findViewById(R.id.text3);
		text4 = (RadioButton) view.findViewById(R.id.text4);
		radiogroup.setOnCheckedChangeListener(this);
		rmd1 = new RmdFragment1();
		rmd2 = new RmdFragment2();
		rmd3 = new RmdFragment3();
		rmd4 = new RmdFragment4();
		list.add(new RmdFragment1()); 
		list.add(new RmdFragment2());
		list.add(new RmdFragment3());
		list.add(new RmdFragment4());
		myViewPagerAdapter adapter = new myViewPagerAdapter(getChildFragmentManager(), list);
		viewPager.setAdapter(adapter);  
		viewPager.setOffscreenPageLimit(3);
		viewPager.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
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
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		return view;
	}

	public void initView(View view,LayoutInflater inflater){
		mIv = (ImageView) view.findViewById(R.id.redmark);
		//取图的宽和高
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.btn_tab_highlight_middle_bg);
		int w = bm.getWidth();
		dhBmpW = bm.getWidth();  
		
		//取屏幕的宽
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
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
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.text1:
			position = 0;
			viewPager.setCurrentItem(0, true);
			break;
		case R.id.text2:
			position = 1;
			viewPager.setCurrentItem(1, true);
			break;
		case R.id.text3:
			position = 2;
			viewPager.setCurrentItem(2, true);
			break;
		case R.id.text4:
			position = 3;
			viewPager.setCurrentItem(3, true);
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
