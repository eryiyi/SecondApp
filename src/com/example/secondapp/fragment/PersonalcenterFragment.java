package com.example.secondapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.example.secondapp.R;
import com.example.secondapp.SecondApplication;
import com.example.secondapp.activity.*;
import com.example.secondapp.adapter.AnimateFirstDisplayListener;
import com.example.secondapp.base.BaseFragment;
import com.example.secondapp.bean.Response;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.http.UploadUtil.OnUploadProcessListener;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.sharedprefernces.SharedPrefsUtil;
import com.example.secondapp.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;

public class PersonalcenterFragment extends BaseFragment implements OnClickListener, OnUploadProcessListener{
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private View view;
	private TextView logon;
	private TextView register;
	private ImageView imagehead;
	private LinearLayout collection;
	private LinearLayout personalshoppingcart;
	private RelativeLayout lin1;
	private RelativeLayout lin2;
	private RelativeLayout lin3;
	private RelativeLayout lin4;
	private RelativeLayout personalsetting;
	private RelativeLayout personalorder;
	private RelativeLayout personalhelpandfeddback;
	private RelativeLayout personalcenter;
	private RelativeLayout personalcenter2;
	private RelativeLayout pushmsg;
	private RelativeLayout saomiao;
	private TextView username;
	int position;
	int flag;
	private FrameLayout frame1;
	private FrameLayout frame2;
	private FrameLayout frame3;
	private FrameLayout frame4;
	private TextView number1;
	private TextView number2;
	private TextView number3;
	private TextView number4;
	private static ImageView logonimage2;
	private LinearLayout statusbar;
	private TextView money;
	private String moneyget;
	private static int myorder1;
	private static int myorder2;
	private static int myorder3;
	private static int myorder4;
	String path;
	private Handler handler = new Handler(Looper.myLooper(), new Handler.Callback(){
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				money.setText(moneyget);
				break;
			default:
				break;
			}
			return false;
		}
	});
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.personalcenterfragment, null);
		registerBoradcastReceiver();
		personalshoppingcart = (LinearLayout) view.findViewById(R.id.personalshoppingcart);
		logonimage2 = (ImageView) view.findViewById(R.id.logonimage2);
		logon = (TextView) view.findViewById(R.id.logon);
		register = (TextView) view.findViewById(R.id.register);
		collection = (LinearLayout) view.findViewById(R.id.collection);
		personalsetting = (RelativeLayout) view.findViewById(R.id.personalsetting);
		personalorder = (RelativeLayout) view.findViewById(R.id.personalorder);
		personalhelpandfeddback = (RelativeLayout) view.findViewById(R.id.personalhelpandfeddback);
		saomiao = (RelativeLayout) view.findViewById(R.id.saomiao);
		personalcenter = (RelativeLayout) view.findViewById(R.id.personalcenter);
		personalcenter2 = (RelativeLayout) view.findViewById(R.id.personalcenter2);
		username = (TextView) view.findViewById(R.id.username);
		imagehead = (ImageView) view.findViewById(R.id.imagehead);
		statusbar = (LinearLayout) view.findViewById(R.id.statusbar);
		lin1 = (RelativeLayout) view.findViewById(R.id.lin1);
		lin2 = (RelativeLayout) view.findViewById(R.id.lin2);
		lin3 = (RelativeLayout) view.findViewById(R.id.lin3);
		lin4 = (RelativeLayout) view.findViewById(R.id.lin4);
		view.findViewById(R.id.saomiao).setOnClickListener(this);
		
		pushmsg = (RelativeLayout) view.findViewById(R.id.pushmsg);
		pushmsg.setOnClickListener(this);
		
		frame1 = (FrameLayout) view.findViewById(R.id.frame1);
		frame2 = (FrameLayout) view.findViewById(R.id.frame2);
		frame3 = (FrameLayout) view.findViewById(R.id.frame3);
		frame4 = (FrameLayout) view.findViewById(R.id.frame4);
		number1 = (TextView) view.findViewById(R.id.number1);
		number2 = (TextView) view.findViewById(R.id.number2);
		number3 = (TextView) view.findViewById(R.id.number3);
		number4 = (TextView) view.findViewById(R.id.number4);
		money = (TextView) view.findViewById(R.id.money);

		logon.setOnClickListener(this);
		register.setOnClickListener(this);   
		personalshoppingcart.setOnClickListener(this);
		collection.setOnClickListener(this);
		personalsetting.setOnClickListener(this);
		personalorder.setOnClickListener(this);
		personalhelpandfeddback.setOnClickListener(this);
		personalcenter.setOnClickListener(this);
		personalcenter2.setOnClickListener(this);
		lin1.setOnClickListener(this);
		lin2.setOnClickListener(this);
		lin3.setOnClickListener(this);
		lin4.setOnClickListener(this);
		saomiao.setOnClickListener(this);
		myorder1 = SharedPrefsUtil.getValue(getActivity(), "mo1", 0);
		if (myorder1 == 0) {
			frame1.setVisibility(View.GONE);
		}else{
			number1.setText(myorder1 + "");
		}
		myorder2 = SharedPrefsUtil.getValue(getActivity(), "mo2", 0);
		if (myorder2 == 0) {
			frame2.setVisibility(View.GONE);
		}else{
			number2.setText(myorder2 + "");
		}
		myorder3 = SharedPrefsUtil.getValue(getActivity(), "mo3", 0);
		if (myorder3 == 0) {
			frame3.setVisibility(View.GONE);
		}else{
			number3.setText(myorder3 + "");
		}
		myorder4 = SharedPrefsUtil.getValue(getActivity(), "mo4", 0);
		if (myorder4 == 0) {
			frame4.setVisibility(View.GONE);
		}else{
			number4.setText(myorder4 + "");
		}
		if ("1".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
			personalcenter.setVisibility(View.GONE);
			personalcenter2.setVisibility(View.VISIBLE);
			statusbar.setVisibility(View.VISIBLE);
			username.setText(getGson().fromJson(getSp().getString("user_name", ""), String.class));
		}
//		String	path = SharedPrefsUtil.getValue(getActivity(), "imagepath", null);
//		if (path != null) {
//			File file1 = new File(path);
//			if (file1.exists()) {
//				Bitmap bm = BitmapFactory.decodeFile(path);
//				imagehead.setImageBitmap(bm);
//			}
//		}
		return view;
	}
	
	public void uploadpic(){
		HttpParams params = new HttpParams();
		params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
		params.put("cover", Environment.getExternalStorageDirectory().toString() + File.separator + "beauty.jpg");
		HttpClientUtils.getInstance().upload(ServerId.serveradress, ServerId.getcover, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) throws JSONException {
				System.out.println("jsonObjectpic = " + jsonObject);
				super.onSuccess(jsonObject);
			}
			
			@Override
			public void onUploadCompleted() {
				System.out.println("上传完成");
			}
					
			@Override
			public void onFailure(String result, int statusCode, String errorResponse) {
				System.out.println("反正是失败了！！！");
				System.out.println("result=" + result + ", statusCode=" + statusCode + ", errorResponse=" + errorResponse);
				super.onFailure(result, statusCode, errorResponse);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.logon:
			Intent intent = new Intent(getActivity(), Logon.class);
			startActivity(intent);
			break;
		case R.id.register:
			Intent intent1 = new Intent(getActivity(), Register.class);
			startActivity(intent1);
			break;
		case R.id.personalshoppingcart:
			if ("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
				Toast.makeText(getActivity(), "请登录后查看购物车", Toast.LENGTH_SHORT).show();
				Intent intent2 = new Intent(getActivity(), Logon.class);
				intent2.putExtra("nozero", 2);
				startActivity(intent2);
			}else{
				Intent intent2 = new Intent(getActivity(), ShoppingCartList.class);
				startActivity(intent2);
			}
			break;
		case R.id.collection:
			if ("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
				Intent nouser = new Intent(getActivity(), Logon.class);
				nouser.putExtra("nozero", 4);
				startActivity(nouser);
			}else{
				Intent intent3 = new Intent(getActivity(), MyCollection.class);
				startActivity(intent3);
			}
			break;
		case R.id.personalsetting:
			Intent intent4 = new Intent(getActivity(), Setting.class);
			startActivity(intent4);
			break;
		case R.id.personalorder:
			if ("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
				Intent nouser = new Intent(getActivity(), Logon.class);
				nouser.putExtra("nozero", 3);
				startActivity(nouser);
			}else{
				Intent intent5 = new Intent(getActivity(), MyOrder.class);
				startActivity(intent5);
			}
			break;
		case R.id.personalhelpandfeddback:
			Intent intent6 = new Intent(getActivity(), HelpAndFeedback.class);
			startActivity(intent6);
			break;
		case R.id.personalcenter2:
			//个人信息
			Intent intent7 = new Intent(getActivity(), PersonalMsg.class);
			startActivity(intent7);
			break;
		case R.id.lin1:
			Intent intent8 = new Intent(getActivity(), MyOrder.class);
			intent8.putExtra("class", 0);
			startActivity(intent8);
			break;
		case R.id.lin2:
			Intent intent9 = new Intent(getActivity(), MyOrder.class);
			intent9.putExtra("class", 1);
			startActivity(intent9);
			break;
		case R.id.lin3:
			Intent intent10 = new Intent(getActivity(), MyOrder.class);
			intent10.putExtra("class", 2);
			startActivity(intent10);
			break;
		case R.id.lin4:
			Intent intent11 = new Intent(getActivity(), MyOrder.class);
			intent11.putExtra("class", 3);
			startActivity(intent11);
			break;
		case R.id.pushmsg:
			Intent intent12 = new Intent(getActivity(), PushMsg.class);
			startActivity(intent12);
			break;
			case R.id.saomiao:
				//
				//扫一扫
			{
				if ("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
					Intent nouser = new Intent(getActivity(), Logon.class);
					nouser.putExtra("nozero", 3);
					startActivity(nouser);
				}else{
					Intent intentsao = new Intent();
					intentsao.setClass(getActivity(), MipcaActivityCapture.class);
					intentsao.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intentsao, SCANNIN_GREQUEST_CODE);
				}

			}

			break;
		default:
			break;
		}
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case SCANNIN_GREQUEST_CODE:
				if(resultCode == getActivity().RESULT_OK){
					Bundle bundle = data.getExtras();
					//显示扫描到的内容
					String result = bundle.getString("result");
					String str = StringUtil.getStrFromJson(result);
					saoyisao(str);
				}
				break;
		}
	}



	void saoyisao(String result){
		HttpParams params = new HttpParams();
		params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
		params.put("order_id", result);
		params.put("status", "4");
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.update_order, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				try {
//					object = jsonObject.getJSONObject("data");
					Response.code = jsonObject.getInt("code");
				} catch (JSONException e) {
					e.printStackTrace();
				}finally{
					Message message = new Message();
					message.what = 123;
					handler1.sendMessage(message);
				}
			}
		});
	}

	Handler handler1 = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case 123:
					if(Response.code == 200 ){
						//
						Toast.makeText(getActivity(), R.string.saosuccess, Toast.LENGTH_SHORT).show();
//						Intent intent = new Intent("address_success");
//						getActivity().sendBroadcast(intent);
					}
					else if(Response.code == -1 ){
						Toast.makeText(getActivity(), R.string.saoerro1r, Toast.LENGTH_SHORT).show();
					}

					else{
						Toast.makeText(getActivity(), R.string.saoerror, Toast.LENGTH_SHORT).show();
					}
					break;
				default:
					break;
			}
			return true;
		}
	});
	
	private void getMoney(){
		HttpParams params = new HttpParams();
		params.put("userId", getSp().getString("uid", ""));
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getmemberInfo, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject s) throws JSONException {
				JSONObject data = s.getJSONObject("data");
				moneyget = data.getString("money");
				Message message = new Message();
				message.what = 123;
				handler.sendMessage(message);
			}
		});
	}
	
	@Override
	public void onResume() {
		if ("1".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
			if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("cover", ""), String.class))){
				imageLoader.displayImage(getGson().fromJson(getSp().getString("cover", ""), String.class), imagehead, SecondApplication.txOptions, animateFirstListener);
				imageLoader.displayImage(getGson().fromJson(getSp().getString("cover", ""), String.class),logonimage2, SecondApplication.txOptions, animateFirstListener);
			}
			personalcenter.setVisibility(View.GONE);
			personalcenter2.setVisibility(View.VISIBLE);
			statusbar.setVisibility(View.VISIBLE);
			getMoney();
			username.setText(getGson().fromJson(getSp().getString("user_name", ""), String.class));
		}
		myorder1 = SharedPrefsUtil.getValue(getActivity(), "mo1", 0);
		if (myorder1 == 0) {
			frame1.setVisibility(View.GONE);
		}else{
			frame1.setVisibility(View.VISIBLE);
			number1.setText(myorder1 + "");
		}
		myorder2 = SharedPrefsUtil.getValue(getActivity(), "mo2", 0);
		if (myorder2 == 0) {
			frame2.setVisibility(View.GONE);
		}else{
			frame2.setVisibility(View.VISIBLE);
			number2.setText(myorder2 + "");
		}
		myorder3 = SharedPrefsUtil.getValue(getActivity(), "mo3", 0);
		if (myorder3 == 0) {
			frame3.setVisibility(View.GONE);
		}else{
			frame3.setVisibility(View.VISIBLE);
			number3.setText(myorder3 + "");
		}
		myorder4 = SharedPrefsUtil.getValue(getActivity(), "mo4", 0);
		if (myorder4 == 0) {
			frame4.setVisibility(View.GONE);
		}else{
			frame4.setVisibility(View.VISIBLE);
			number4.setText(myorder4 + "");
		}
		super.onResume();
	}

	@Override
	public void onUploadDone(int responseCode, String message) {
		
	}

	@Override
	public void onUploadProcess(int uploadSize) {
		
	}

	@Override
	public void initUpload(int fileSize) {
		
	}

	//广播接收动作
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("update_cover_success")) {
				if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("cover", ""), String.class))){
					imageLoader.displayImage(getGson().fromJson(getSp().getString("cover", ""), String.class),imagehead, SecondApplication.txOptions, animateFirstListener);
				}
			}
		}
	};

	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("update_cover_success");//
		//注册广播
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}
}
