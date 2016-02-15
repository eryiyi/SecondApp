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
import android.webkit.WebView.FindListener;
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

public class RmdFragment1 extends BaseFragment implements OnRefreshListener, OnLoadListener{
	
	View view;
	MyListViewUpDown listView;
	List<FruitBean> list;
	FruitShowListViewAdapter adapter;
	private ProgressDialog progressDialog;
	String rmd1;
	TextView nodata;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		list = new ArrayList<FruitBean>();
		view = inflater.inflate(R.layout.rmdfragment, null);
		nodata = (TextView) view.findViewById(R.id.nodata);
		registerBoradcastReceiver();
		listView = (MyListViewUpDown) view.findViewById(R.id.lv);
		listView.setonRefreshListener(this);
		listView.setOnLoadListener(this);
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
		getRF1Data();
		return view;
	}  
	
	@Override
	public void onDestroyView() {
		getActivity().unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}
	  
	public void getRF1Data(){
		HttpParams params = new HttpParams();
		params.put("city", SecondApplication.cityName);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.popularity, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject s) {
				if(progressDialog != null){
					//progressDialog.dismiss();  
				}
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
		});
	}
	
	Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				if (list.size() == 0){
					Log.i("villa", "list.size() == 0");
					nodata.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					//Toast.makeText(getActivity(), "该城市暂无商品数据", Toast.LENGTH_LONG).show();
				}else {
					Log.i("villa", "list.size() != 0");   
					nodata.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
					adapter = new FruitShowListViewAdapter(getActivity(), list);
					listView.setResultSize(list.size());
					listView.setAdapter(adapter);
				}
				progressDialog.dismiss();  
				//adapter.notifyDataSetChanged();
				break;
			case 0:

				break;
			default:
				break;
			}
			return true;
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
				getRF1Data();
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
				getRF1Data();
			}
		}
		
	};
}
