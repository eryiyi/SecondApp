package com.example.secondapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.example.secondapp.R;
import com.example.secondapp.adapter.FruitShowListViewAdapter;
import com.example.secondapp.adapter.SearchHistoryAdapter;
import com.example.secondapp.adapter.SearchHistoryAdapter.SearchHistoryBean;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.data.FruitBeanData;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchInterface extends BaseActivity implements OnClickListener{
	ImageView back;
	TextView cleanhistory;
	//TextView searchproduct;
	EditText editText;
	ListView listView;
	ListView lvdetail;
	LinearLayout beforesearch;
	List<SearchHistoryBean> list = new ArrayList<SearchHistoryAdapter.SearchHistoryBean>();
	List<FruitBean> list2 = new ArrayList<FruitBean>();
	SearchHistoryAdapter adapter;
	FruitShowListViewAdapter adapter2;
	SharedPreferences share;
	SearchHistoryBean bean;
	LinearLayout search;
	TextView apple;
	TextView nosearch;
	TextView pear;
	TextView grape;
	TextView kiwi;
	TextView watermelon;
	public static String name;
	public static String input;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.searchinterface);
		nosearch = (TextView) findViewById(R.id.nosearch);
		//searchproduct = (TextView) findViewById(R.id.searchproduct);
		lvdetail = (ListView) findViewById(R.id.searchdetail);
		beforesearch = (LinearLayout) findViewById(R.id.beforesearch);
		back = (ImageView) findViewById(R.id.back);
		cleanhistory = (TextView) findViewById(R.id.cleanhistory);
		listView = (ListView) findViewById(R.id.searchlv);
		editText = (EditText) findViewById(R.id.auto);
		search = (LinearLayout) findViewById(R.id.search);
		share = getSharedPreferences("historyrecord", MODE_PRIVATE);
		apple = (TextView) findViewById(R.id.apple);
		pear = (TextView) findViewById(R.id.pear);
		grape = (TextView) findViewById(R.id.grape);
		kiwi = (TextView) findViewById(R.id.kiwi);
		watermelon = (TextView) findViewById(R.id.watermelon);
		apple.setOnClickListener(this);
		pear.setOnClickListener(this);
		grape.setOnClickListener(this);
		kiwi.setOnClickListener(this);
		watermelon.setOnClickListener(this);
		//searchproduct.setOnClickListener(this);
		name = share.getString("name", "");
		String[] hisArrays = name.split(",");
		if (!"".equals(name)) {
			for (int i = 0; i < hisArrays.length; i++) {
				bean = new SearchHistoryBean();
				bean.searchname = hisArrays[i];
				list.add(bean);
			}
		}
		adapter = new SearchHistoryAdapter(this, list);
		listView.setAdapter(adapter);

		back.setOnClickListener(this);
		cleanhistory.setOnClickListener(this);
		search.setOnClickListener(this);
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				input = editText.getText().toString().trim();
				if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					if (editText.getText().toString().trim() == null || "".equals(editText.getText().toString().trim())) {
						System.out.println("input = " + editText.getText().toString().trim());
						Toast.makeText(SearchInterface.this, "请输入商品信息", Toast.LENGTH_SHORT).show();
						return true;
					} else {
						System.out.println("share.getString.name = " + share.getString("name", ""));
						System.out.println("edittext.getText().toString().trim() = " + editText.getText().toString().trim());
						System.out.println("share.getInt = " + share.getInt("copyname", 0));
						saveHistory("name", editText);
					}
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.cleanhistory:
			share.edit().clear().commit();
			list.clear();
			adapter.notifyDataSetChanged();
			break;
		case R.id.search:
			input = editText.getText().toString().trim();
			if (editText.getText().toString().trim() == null || "".equals(editText.getText().toString().trim())) {
			} else {
				System.out.println("share.getString.name = " + share.getString("name", ""));
				System.out.println("edittext.getText().toString().trim() = " + editText.getText().toString().trim());
				System.out.println("share.getInt = " + share.getInt("copyname", 0));
				saveHistory("name", editText);
			}
			getSearchProduct();
			break;
		case R.id.apple:
			editText.setText(R.string.apple);
			break;
		case R.id.pear:
			editText.setText(R.string.pear);
			break;
		case R.id.grape:
			editText.setText(R.string.grape);
			break;
		case R.id.kiwi:
			editText.setText(R.string.kiwi);
			break;
		case R.id.watermelon:
			editText.setText(R.string.watermelon);
			break;
		/*case R.id.searchproduct:
			getSearchProduct();
			break;*/
		default:
			break;
		}
	}
	
	public void saveHistory(String field, EditText auto){
		input = auto.getText().toString().trim();
		name = share.getString(field, "");
		if (!name.contains(input + ",")) {
			StringBuilder builder = new StringBuilder(name);
			builder.insert(0, input + ",");
			share.edit().putString("name", builder.toString()).commit();
		}
	}
	
	public void getSearchProduct(){
		HttpParams params = new HttpParams();
		params.put("name", editText.getText().toString());
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.likeproduct, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject s) {
				if (StringUtil.isJson(s.toString())) {
					try {
						JSONObject jo = new JSONObject(s.toString());
						String code =  jo.getString("code");
						if(Integer.parseInt(code) == 200){
							FruitBeanData data = getGson().fromJson(s.toString(), FruitBeanData.class);
							list2.addAll(data.getData());
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
				if (list2.size() == 0) {
					nosearch.setVisibility(View.VISIBLE);
					beforesearch.setVisibility(View.GONE);
				}else {
					adapter2 = new FruitShowListViewAdapter(SearchInterface.this, list2);
					beforesearch.setVisibility(View.GONE);
					lvdetail.setVisibility(View.VISIBLE);
					lvdetail.setAdapter(adapter2);
					lvdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
							Intent intent = new Intent(SearchInterface.this, CommodityDetailTest.class);
							intent.putExtra("id", list2.get(i).getProduct_id());
							startActivity(intent);
						}
					});
				}
				break;
			default:
				break;
			}
			return false;
		}
	});
}
