package com.example.secondapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.secondapp.MainActivity;
import com.example.secondapp.R;
import com.example.secondapp.base.ActivityTack;
import com.example.secondapp.base.BaseActivity;

public class HelpAndFeedback extends BaseActivity implements OnClickListener{
	ImageView back;
	LinearLayout mainpage;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.helpandfeedback);
		back = (ImageView) findViewById(R.id.back);
		mainpage = (LinearLayout) findViewById(R.id.mainpage);
		back.setOnClickListener(this);
		mainpage.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.mainpage:
//			Intent intent = new Intent(this, MainActivity.class);
//			startActivity(intent);
			ActivityTack.getInstanse().popUntilActivity(MainActivity.class);
			break;
		default:
			break;
		}
	}
}
