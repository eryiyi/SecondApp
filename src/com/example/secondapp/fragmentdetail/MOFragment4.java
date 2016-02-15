package com.example.secondapp.fragmentdetail;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.adapter.MyOrderAdapter4;
import com.example.secondapp.adapter.OnClickContentItemListener;
import com.example.secondapp.base.BaseFragment;
import com.example.secondapp.bean.MyOrderBean;
import com.example.secondapp.data.MyOrderBeanData;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.sharedprefernces.SharedPrefsUtil;
import com.example.secondapp.util.StringUtil;
import com.example.secondapp.view.PullToRefreshLV;
import com.example.secondapp.view.PullToRefreshLV.OnRefreshListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MOFragment4 extends BaseFragment implements OnClickContentItemListener, OnClickListener {
	View view;
	PullToRefreshLV lv;
	List<MyOrderBean> list = new ArrayList<MyOrderBean>();;
	public static MyOrderAdapter4 adapter;
	public static int count4;
	public static TextView noorder;
	private PopupWindow popupWindow;
	private View viewpopup;
	private EditText evaluate;
	private TextView cancel;
	private TextView confirm;
	private static RatingBar ratingbar;
	private TextView tt;
    /**
     * 屏幕的宽度和高度
     */
    protected int mScreenWidth;
    protected int mScreenHeight;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("getSp().getStringuid = " + getSp().getString("uid", ""));
		getMOF4Data(Integer.parseInt(getGson().fromJson(getSp().getString("uid", ""), String.class)));
		view = inflater.inflate(R.layout.myorderfragment, null);
		tt = (TextView) view.findViewById(R.id.tt);
		tt.setOnClickListener(this);
		lv = (PullToRefreshLV) view.findViewById(R.id.myorderlv);
		noorder = (TextView) view.findViewById(R.id.noorder);
		list.clear();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				Intent intent = new Intent(getActivity(), ToBeEvaluation.class);
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("orderbean", list.get(arg2 - 1));
//				intent.putExtras(bundle);
//				intent.putExtra("mof4", (arg2 - 1));
//				startActivity(intent);
			}
			
		});
		lv.setonRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(800);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						for (int i = 0; i < 2; i++) {
							
						}
						return null;
					}

					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
						lv.onRefreshComplete();
					};
				}.execute(null, null, null);
			}
		});
		return view;
	}
	
	private void getMOF4Data(int userid){
		HttpParams params = new HttpParams();
		params.put("uid", String.valueOf(userid));
		params.put("status", "4");
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.get_order_by_type, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				Log.i("villa", "mof4 = " + jsonObject);
				if (StringUtil.isJson(jsonObject.toString())) {
					try {
						JSONObject jo = new JSONObject(jsonObject.toString());
						String code =  jo.getString("code");
						String dataJ =  jo.getString("data");
						if(Integer.parseInt(code) == 200 && !"false".equals(dataJ)){
							MyOrderBeanData data = getGson().fromJson(jsonObject.toString(), MyOrderBeanData.class);
							if(data != null && data.getData() != null){
								list.addAll(data.getData());
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				Message message = new Message();
				message.what = 123;
				handler.sendMessage(message);
			}
		});
	}
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				if (list.size() == 0) {
					noorder.setVisibility(View.VISIBLE);
				}else {
					adapter = new MyOrderAdapter4(getActivity(), list, getGson().fromJson(getSp().getString("uid", ""), String.class));
					count4 = adapter.getCount();
					SharedPrefsUtil.putValue(getActivity(), "mo4", count4);
					lv.setAdapter(adapter);
				}
				break;
			default:
				break;
			}
			return false;
		}
	});

	@Override
	public void onClickContentItem(int position, int flag, Object object) {

	}
	
	private void showPopupWindow() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		viewpopup = inflater.inflate(R.layout.popupevaluate, null);
		evaluate = (EditText) viewpopup.findViewById(R.id.evaluate);
		cancel = (TextView) viewpopup.findViewById(R.id.cancel);
		confirm = (TextView) viewpopup.findViewById(R.id.confirm);
		ratingbar = (RatingBar) viewpopup.findViewById(R.id.ratingbar);
		ratingbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,  boolean fromUser) {
				int ratingnumber = (int) rating;
				System.out.println("ratingnumber = " + ratingnumber);
			}
		});
		cancel.setOnClickListener(this);
		confirm.setOnClickListener(this);
		DisplayMetrics metric = new DisplayMetrics();
	   	getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
	   	mScreenWidth = metric.widthPixels;
	   	mScreenHeight = metric.heightPixels;
		//WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		//int wid = wm.getDefaultDisplay().getWidth();
		popupWindow = new PopupWindow(viewpopup, mScreenWidth-100, LayoutParams.WRAP_CONTENT);
	    /**
	     * 获取屏幕宽度和高度
	     */
		//popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true); // 可聚焦
		popupWindow.setOutsideTouchable(true);// 设置外部可点击
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
		ColorDrawable colorDrawable = new ColorDrawable(0xffffffff);
		popupWindow.setBackgroundDrawable(colorDrawable); // 设置背景
		//设置popupwindow动画效果
		//popupWindow.setAnimationStyle(R.style.AnimationPreview);
		popupWindow.setContentView(viewpopup);
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		//popupWindow.showAtLocation(viewpopup.findViewById(R.id.cancelinvate), Gravity.CENTER, 0, 0);
	}
	
	public void backgroundAlpha(float bgAlpha){
		
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
	}
	
	public class poponDismissListener implements PopupWindow.OnDismissListener{

		@Override
		public void onDismiss() {
			backgroundAlpha(1f);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			popupWindow.dismiss();
			break;
		case R.id.confirm:
			popupWindow.dismiss();
			break;
		case R.id.tt:
			showPopupWindow();
			break;
		default:
			break;
		}
	}
}
