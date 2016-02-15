package com.example.secondapp.fragmentdetail;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.example.secondapp.R;
import com.example.secondapp.SecondApplication;
import com.example.secondapp.activity.CommodityDetailTest;
import com.example.secondapp.adapter.FruitShowListViewAdapter;
import com.example.secondapp.base.BaseFragment;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.data.FruitBeanData;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.util.StringUtil;
import com.example.secondapp.view.MyListViewUpDown;
import com.example.secondapp.view.MyListViewUpDown.OnLoadListener;
import com.example.secondapp.view.MyListViewUpDown.OnRefreshListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RmdFragment3 extends BaseFragment implements OnRefreshListener, OnLoadListener{
	View view;
	MyListViewUpDown listView;
	List<FruitBean> list;
//	List<FruitBean> datas;
	FruitShowListViewAdapter adapter;
	private boolean flag = true;
	String rmd3;
	private ProgressDialog progressDialog;
	TextView noData;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		list = new ArrayList<FruitBean>();
		getRF3Data();
		view = inflater.inflate(R.layout.rmdfragment, null);
		noData = (TextView) view.findViewById(R.id.nodata);
		registerBoradcastReceiver();
		listView = (MyListViewUpDown) view.findViewById(R.id.lv);
		listView.setonRefreshListener(this);
		listView.setOnLoadListener(this);
		list = new ArrayList<FruitBean>();
		adapter = new FruitShowListViewAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(getActivity(), CommodityDetailTest.class);
				intent.putExtra("id", list.get(position - 1).getProduct_id());
				startActivity(intent);
			}
		});
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("请稍后");
		progressDialog.setCancelable(false);
		progressDialog.show(); 
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroy();
	}
	
	public void getRF3Data(){
		HttpParams params = new HttpParams();
		params.put("city", SecondApplication.cityName);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.lastproduct, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject s) {
				if (StringUtil.isJson(s.toString())) {
					try {
						JSONObject jo = new JSONObject(s.toString());
						String code =  jo.getString("code");
						String dataJ =  jo.getString("data");
						if(Integer.parseInt(code) == 200 && !"false".equals(dataJ)){
							FruitBeanData data = getGson().fromJson(s.toString(), FruitBeanData.class);
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
			@Override
			public void onFailure(String result, int statusCode, String errorResponse) {
				super.onFailure(result, statusCode, errorResponse);
			}
		});
	}
	
	Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				if (list.size() == 0){
					if(flag){
						flag = false;
					}else{
						//Toast.makeText(getActivity(), "该城市暂无商品数据", Toast.LENGTH_LONG).show();
						noData.setVisibility(View.VISIBLE);
						listView.setVisibility(View.GONE);
					}
				}else {
					listView.setVisibility(View.VISIBLE);
					noData.setVisibility(View.GONE);
					listView.setResultSize(list.size());
					adapter.notifyDataSetChanged();
					progressDialog.dismiss();  
				}
				break;
			default:
				break;
			}
			return false;
		}
	});
	@Override
	public void onLoad() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//上拉加载的内容
				
				return null;
			}

			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();
				listView.onLoadComplete();
			};
		}.execute(null, null, null);
	}

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
				list.clear();
				getRF3Data();
				return null;
			}

			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();
				listView.onRefreshComplete();
			};
		}.execute(null, null, null);
	}
	
	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("select_city_success");
		//注册广播
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}
			
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("select_city_success")) {
				list.clear();
				getRF3Data();
			}
		}
	};
}
