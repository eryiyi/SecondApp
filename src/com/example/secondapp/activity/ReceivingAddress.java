package com.example.secondapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.example.secondapp.R;
import com.example.secondapp.adapter.AdressAdapter;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.AdressBean;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReceivingAddress extends BaseActivity {
	public static List<AdressBean> list = new ArrayList<AdressBean>();
	public static ListView listView;
	RelativeLayout personalsetting;
	LinearLayout back;
	private static int ca;
	public static AdressBean adressBean;
	public static AdressAdapter adapter;
	@Override
	protected void onCreate(Bundle arg0) {
		list.clear();
		getAddressData(Integer.parseInt(getGson().fromJson(getSp().getString("uid", ""), String.class)));
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.receivingaddress);
		ca = getIntent().getIntExtra("changeadress", 0);
		listView = (ListView) findViewById(R.id.addresslv);
		personalsetting = (RelativeLayout) findViewById(R.id.personalsetting);
		back = (LinearLayout) findViewById(R.id.back);
		if (ca == 1) {
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					adressBean = list.get(position);
					finish();
				}
			});
		}
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		personalsetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ReceivingAddress.this, AddAdress.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onPause() {
		AdressAdapter.flag = 0;
		super.onPause();
	}
	
	public void getAddressData(int userid){
		HttpParams params = new HttpParams();
		params.put("userId", String.valueOf(userid));
		HttpClientUtils.getInstance().get(ServerId.serveradress, ServerId.checkaddress, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("jsonObjectaddress = " + jsonObject);
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						AdressBean adressBean = new AdressBean();
						JSONObject object = jsonArray.optJSONObject(i);
						adressBean.name = object.optString("name");
						adressBean.adress = object.optString("address");
						adressBean.phonenumber = object.optString("tel");
						adressBean.id = object.optInt("id");
						adressBean.state = object.optInt("state");
						list.add(adressBean);
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
				adapter = new AdressAdapter(ReceivingAddress.this, list, getGson().fromJson(getSp().getString("uid", ""), String.class));
				listView.setAdapter(adapter);
				//adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			return false;
		}
	});
}
