package com.example.secondapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.clearcache.DataCleanManager;
import com.example.secondapp.versionupdate.UpdateManager;

public class Setting extends BaseActivity implements OnClickListener{
	LinearLayout back;
	ToggleButton togglebutton1;
	ToggleButton togglebutton2;
	ToggleButton togglebutton3;
	RelativeLayout mysetting2;
	RelativeLayout mysetting3;
	RelativeLayout mysetting4;
	RelativeLayout mysetting5;
	RelativeLayout mysetting6;
	RelativeLayout mysetting7;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.setting);
		back = (LinearLayout) findViewById(R.id.back);
		back.setOnClickListener(this);
		togglebutton1 = (ToggleButton) findViewById(R.id.togglebutton1);
		mysetting2 = (RelativeLayout) findViewById(R.id.myasset2);
		mysetting3 = (RelativeLayout) findViewById(R.id.myasset3);
		mysetting4 = (RelativeLayout) findViewById(R.id.myasset4);
		mysetting5 = (RelativeLayout) findViewById(R.id.myasset5);
		mysetting6 = (RelativeLayout) findViewById(R.id.myasset6);
		mysetting7 = (RelativeLayout) findViewById(R.id.myasset7);
		mysetting4.setOnClickListener(this);
		mysetting5.setOnClickListener(this);
		mysetting6.setOnClickListener(this);
		mysetting7.setOnClickListener(this);
		togglebutton1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mysetting2.setVisibility(View.VISIBLE);
					mysetting3.setVisibility(View.VISIBLE);
				}else{
					mysetting2.setVisibility(View.GONE);
					mysetting3.setVisibility(View.GONE);
				}
			}
	  	});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.myasset4:
			Intent intent = new Intent(this, HelpAndFeedback.class);
			startActivity(intent);
			break;
		case R.id.myasset5:
			UpdateManager manager = new UpdateManager(Setting.this);
			manager.checkUpdate();
			break;
		case R.id.myasset6:
			DataCleanManager.cleanDatabases(this);
			Toast.makeText(Setting.this, "清除缓存成功", Toast.LENGTH_SHORT).show();
			break;
		case R.id.myasset7:
			Intent intent2 = new Intent(this, AboutSelf.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}
}
