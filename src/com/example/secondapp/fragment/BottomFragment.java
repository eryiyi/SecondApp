package com.example.secondapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseFragment;

public class BottomFragment extends BaseFragment implements OnClickListener{
	public static interface OnBottomClickListener {
		void onBottomClick(View v, int position);
	}
	public static final int MAINPAGE = 0;
	public static final int RECOMMEND = 1;
	public static final int CLASSIFICATION = 2;
	public static final int PERSONALCENTER = 3;
	LinearLayout mainpage;
	LinearLayout recommend;
	LinearLayout classification;
	LinearLayout personalcenter;
	ImageView mainpage_image;
	ImageView recommend_image;
	ImageView classification_image;
	ImageView personalcenter_image;
	TextView mainpage_text;
	TextView recommend_text;
	TextView classification_text;
	TextView personalcenter_text;
	private View view;
	private OnBottomClickListener bottomClickListener;
	private LinearLayout linearLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.bottombartest, null);
		linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout);
		mainpage = (LinearLayout) view.findViewById(R.id.linear1);
		recommend = (LinearLayout) view.findViewById(R.id.linear2);
		classification = (LinearLayout) view.findViewById(R.id.linear3);
		personalcenter = (LinearLayout) view.findViewById(R.id.linear4);
		
		mainpage_image = (ImageView) view.findViewById(R.id.image1);
		recommend_image = (ImageView) view.findViewById(R.id.image2);
		classification_image = (ImageView) view.findViewById(R.id.image3);
		personalcenter_image = (ImageView) view.findViewById(R.id.image4);
		
		mainpage_text = (TextView) view.findViewById(R.id.text1);
		recommend_text = (TextView) view.findViewById(R.id.text2);
		classification_text = (TextView) view.findViewById(R.id.text3);
		personalcenter_text = (TextView) view.findViewById(R.id.text4);
		
		mainpage.setOnClickListener(this);
		recommend.setOnClickListener(this);
		classification.setOnClickListener(this);
		personalcenter.setOnClickListener(this);
		return view;
		
	}
	
	public void setBottomClickListener(OnBottomClickListener bottomClickListener) {
		this.bottomClickListener = bottomClickListener;
	}
	
	public void setSelected(int position){
		switch (position) {
		case MAINPAGE:
			mainpage.setBackgroundColor(getResources().getColor(R.color.redstandard));
			recommend.setBackgroundColor(getResources().getColor(R.color.common_white));
			classification.setBackgroundColor(getResources().getColor(R.color.common_white));
			personalcenter.setBackgroundColor(getResources().getColor(R.color.common_white));
			
			mainpage_image.setImageResource(R.drawable.mainpagew);
			recommend_image.setImageResource(R.drawable.recommend);
			classification_image.setImageResource(R.drawable.classification);
			personalcenter_image.setImageResource(R.drawable.personalcenter);
			
			mainpage_text.setTextColor(getResources().getColor(R.color.common_white));
			recommend_text.setTextColor(getResources().getColor(R.color.redstandard));
			classification_text.setTextColor(getResources().getColor(R.color.redstandard));
			personalcenter_text.setTextColor(getResources().getColor(R.color.redstandard));
			break;
		case RECOMMEND:
			mainpage.setBackgroundColor(getResources().getColor(R.color.common_white));
			recommend.setBackgroundColor(getResources().getColor(R.color.redstandard));
			classification.setBackgroundColor(getResources().getColor(R.color.common_white));
			personalcenter.setBackgroundColor(getResources().getColor(R.color.common_white));
			
			mainpage_image.setImageResource(R.drawable.mainpage);
			recommend_image.setImageResource(R.drawable.recommendw);
			classification_image.setImageResource(R.drawable.classification);
			personalcenter_image.setImageResource(R.drawable.personalcenter);
			
			mainpage_text.setTextColor(getResources().getColor(R.color.redstandard));
			recommend_text.setTextColor(getResources().getColor(R.color.common_white));
			classification_text.setTextColor(getResources().getColor(R.color.redstandard));
			personalcenter_text.setTextColor(getResources().getColor(R.color.redstandard));
			break;
		case CLASSIFICATION:
			mainpage.setBackgroundColor(getResources().getColor(R.color.common_white));
			recommend.setBackgroundColor(getResources().getColor(R.color.common_white));
			classification.setBackgroundColor(getResources().getColor(R.color.redstandard));
			personalcenter.setBackgroundColor(getResources().getColor(R.color.common_white));
			
			mainpage_image.setImageResource(R.drawable.mainpage);
			recommend_image.setImageResource(R.drawable.recommend);
			classification_image.setImageResource(R.drawable.classificationw);
			personalcenter_image.setImageResource(R.drawable.personalcenter);
			
			mainpage_text.setTextColor(getResources().getColor(R.color.redstandard));
			recommend_text.setTextColor(getResources().getColor(R.color.redstandard));
			classification_text.setTextColor(getResources().getColor(R.color.common_white));
			personalcenter_text.setTextColor(getResources().getColor(R.color.redstandard));
			break;
		case PERSONALCENTER:
			mainpage.setBackgroundColor(getResources().getColor(R.color.common_white));
			recommend.setBackgroundColor(getResources().getColor(R.color.common_white));
			classification.setBackgroundColor(getResources().getColor(R.color.common_white));
			personalcenter.setBackgroundColor(getResources().getColor(R.color.redstandard));
			
			mainpage_image.setImageResource(R.drawable.mainpage);
			recommend_image.setImageResource(R.drawable.recommend);
			classification_image.setImageResource(R.drawable.classification);
			personalcenter_image.setImageResource(R.drawable.personalcenterw);
			
			mainpage_text.setTextColor(getResources().getColor(R.color.redstandard));
			recommend_text.setTextColor(getResources().getColor(R.color.redstandard));
			classification_text.setTextColor(getResources().getColor(R.color.redstandard));
			personalcenter_text.setTextColor(getResources().getColor(R.color.common_white));
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		int position = 0;
		switch (v.getId()) {
		case R.id.linear1:
			position = MAINPAGE;
			break;
		case R.id.linear2:
			position = RECOMMEND;
			break;
		case R.id.linear3:
			position = CLASSIFICATION;
			break;
		case R.id.linear4:
			position = PERSONALCENTER;
			break;
		default:
			break;
		}
		setSelected(position);
		if (bottomClickListener != null) {
			bottomClickListener.onBottomClick(v, position);
		}
	}
}
