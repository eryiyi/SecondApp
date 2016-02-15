package com.example.secondapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.secondapp.R;
import com.example.secondapp.adapter.ImgTextAdapter;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.ImgTextBean;

import java.util.ArrayList;
import java.util.List;

public class ImgTextDetail extends BaseActivity {
	ListView imgtextlv;
	ImgTextAdapter adapter;
	List<ImgTextBean> list;
	LinearLayout back;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.imgtextdetail);
		back = (LinearLayout) findViewById(R.id.back);
		imgtextlv = (ListView) findViewById(R.id.imgtextlv);
		list = new ArrayList<ImgTextBean>();
		adapter = new ImgTextAdapter(this, list);
		imgtextlv.setAdapter(adapter);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
