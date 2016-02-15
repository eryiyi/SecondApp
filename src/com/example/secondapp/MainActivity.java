package com.example.secondapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.Response;
import com.example.secondapp.fragment.*;
import com.example.secondapp.fragment.BottomFragment.OnBottomClickListener;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.CheckNetwork;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity implements OnBottomClickListener {
	BottomFragment bottomFragment;
	private long mExitTime;
	FragmentManager fManager;
	private MainPageFragment fg1;
	private RecommendFragment fg2;
	private ClassificationFragment fg3;
	private PersonalcenterFragment fg4;
	private FragmentTransaction transaction;
	private static int getskip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY,"FOxtHkxm1PWTOoz0Vt24vowN");
		save("is_login", "0");
		save("uid", "");  
		save("password", "");
		save("user_name", "");
		if(SecondApplication.cityName==null){
			SecondApplication.cityName= getSp().getString("city", "北京市");
			if (!SecondApplication.cityName.equals("北京市")) {
				SecondApplication.cityName = SecondApplication.cityName.substring(1, SecondApplication.cityName.length() -1);
			}
			Log.i("villa", "mainactivity = " + SecondApplication.cityName);
		}
		Fragment mainpagetilte = new PublicTitleFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("changetitle", BottomFragment.MAINPAGE);  
		mainpagetilte.setArguments(bundle);
		// getSupportFragmentManager().beginTransaction().add(R.id.secondappcontent,
		// new MainPageFragment()).commit();
		getSupportFragmentManager().beginTransaction().add(R.id.titlefragment, mainpagetilte).commit();
		fManager = getSupportFragmentManager();
		getskip = getIntent().getIntExtra("personcenter", 0);
		setChioceItem(getskip);
		bottomFragment = (BottomFragment) getSupportFragmentManager().findFragmentById(R.id.bottombar);
		bottomFragment.setBottomClickListener(this);
		bottomFragment.setSelected(0);
	    if (CheckNetwork.isNetworkAvailable(MainActivity.this))
        {
            //Toast.makeText(getApplicationContext(), "当前有可用网络！", Toast.LENGTH_LONG).show();
			if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("password", ""), String.class)) && !StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("mobile", ""), String.class))){
				login(getGson().fromJson(getSp().getString("mobile", ""), String.class), getGson().fromJson(getSp().getString("password", ""), String.class));
			}
        }
        else
        {
            //Toast.makeText(getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
            CheckNetwork.setNetworkMethod(MainActivity.this);
        }
	}

	JSONObject object;
	void login(String mobile, String pwr){
		HttpParams params = new HttpParams();
		params.put("mobile", mobile);
		params.put("password", pwr);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.logonurl, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				try {
					object = jsonObject.getJSONObject("data");
					Response.code = jsonObject.getInt("code");
				} catch (JSONException e) {
					e.printStackTrace();
				}finally{
					Message message = new Message();
					message.what = 123;
					handler.sendMessage(message);
				}
			}
		});
	}

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case 123:
					if(Response.code == 200){
						//-------保存登陆的信息-------
						save("uid", object.optInt("uid"));
						save("user_name", object.optInt("user_name"));
						save("mobile", object.optInt("mobile"));
						save("is_login", "1");
					}
					break;
				default:
					break;
			}
			return true;
		}
	});

	@Override
	public void onBottomClick(View v, int position) {
		switch (position) {
		case BottomFragment.MAINPAGE:
			Fragment mainpagetilte = new PublicTitleFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("changetitle", BottomFragment.MAINPAGE);
			
			mainpagetilte.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, mainpagetilte).commit();
			setChioceItem(0);
			break;
		case BottomFragment.RECOMMEND:
			Fragment recommendtitle = new PublicTitleFragment();
			Bundle bundle1 = new Bundle();
			bundle1.putInt("changetitle", BottomFragment.RECOMMEND);
			recommendtitle.setArguments(bundle1);
			getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, recommendtitle).commit();
			setChioceItem(1);
			break;
		case BottomFragment.CLASSIFICATION:
			Fragment classificationtitle = new PublicTitleFragment();
			Bundle bundle2 = new Bundle();
			bundle2.putInt("changetitle", BottomFragment.CLASSIFICATION);
			classificationtitle.setArguments(bundle2);
			getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, classificationtitle).commit();
			setChioceItem(2);
			break;
		case BottomFragment.PERSONALCENTER:
			Fragment personalcentertitle = new PublicTitleFragment();
			Bundle bundle3 = new Bundle();
			bundle3.putInt("changetitle", BottomFragment.PERSONALCENTER);
			personalcentertitle.setArguments(bundle3);
			getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, personalcentertitle).commit();
			setChioceItem(3);
			break;
		default:
			break;
		}
	}

	// 定义一个选中一个item后的处理
	public void setChioceItem(int index) {
		// 重置选项+隐藏所有Fragment
		transaction = fManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			if (fg1 == null) {
				// 如果fg1为空，则创建一个并添加到界面上
				fg1 = new MainPageFragment();
				transaction.add(R.id.secondappcontent, fg1);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg1);
			}
			break;
		case 1:
			if (fg2 == null) {
				// 如果fg1为空，则创建一个并添加到界面上
				fg2 = new RecommendFragment();
				transaction.add(R.id.secondappcontent, fg2);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg2);
			}
			break;
		case 2:
			if (fg3 == null) {
				// 如果fg1为空，则创建一个并添加到界面上
				fg3 = new ClassificationFragment();
				transaction.add(R.id.secondappcontent, fg3);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg3);
			}
			break;
		case 3:
			if (fg4 == null) {
				// 如果fg1为空，则创建一个并添加到界面上
				fg4 = new PersonalcenterFragment();
				transaction.add(R.id.secondappcontent, fg4);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg4);
			}
			break;
		}
		transaction.commit();
	}

	// 隐藏所有的Fragment,避免fragment混乱
	private void hideFragments(FragmentTransaction transaction) {
		if (fg1 != null) {
			transaction.hide(fg1);
		}
		if (fg2 != null) {
			transaction.hide(fg2);
		}
		if (fg3 != null) {
			transaction.hide(fg3);
		}
		if (fg4 != null) {
			transaction.hide(fg4);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				@SuppressWarnings("unused")
				Object mHelperUtils;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
