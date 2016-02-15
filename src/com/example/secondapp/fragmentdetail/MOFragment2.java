package com.example.secondapp.fragmentdetail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.adapter.MyOrderAdapter2;
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

public class MOFragment2 extends BaseFragment implements OnClickContentItemListener {
	View view;
	PullToRefreshLV lv;
	List<MyOrderBean> list = new ArrayList<MyOrderBean>();;
	public static MyOrderAdapter2 adapter;
	public static int count2;
	public static TextView noorder;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getMOF2Data(Integer.parseInt(getGson().fromJson(getSp().getString("uid", ""), String.class)));
		view = inflater.inflate(R.layout.myorderfragment, null);
		lv = (PullToRefreshLV) view.findViewById(R.id.myorderlv);
		noorder = (TextView) view.findViewById(R.id.noorder);
		list.clear();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				Intent intent = new Intent(getActivity(), ToBeReceive.class);
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("orderbean", list.get(arg2 - 1));
//				intent.putExtras(bundle);
//				intent.putExtra("mof2", (arg2 - 1));
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
	
	public void getMOF2Data(int userid){
		HttpParams params = new HttpParams();
		params.put("uid", String.valueOf(userid));
		params.put("status", "3");
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.get_order_by_type, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				Log.i("villa", "mof2 = " + jsonObject);
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
					adapter = new MyOrderAdapter2(getActivity(), list ,getGson().fromJson(getSp().getString("uid", ""), String.class));
					count2 = adapter.getCount();
					SharedPrefsUtil.putValue(getActivity(), "mo2", count2);
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
}
