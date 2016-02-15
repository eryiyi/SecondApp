package com.example.secondapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.secondapp.R;
import com.example.secondapp.adapter.OnClickContentItemListener;
import com.example.secondapp.adapter.ShoppingCartAdapter;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.bean.ShoppingcartBean;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.sharedprefernces.SharedPrefsUtil;
import com.example.secondapp.view.MyDialog;
import com.example.secondapp.view.PullToRefreshLV;
import com.example.secondapp.view.PullToRefreshLV.OnRefreshListener;
import org.json.JSONArray;
import org.json.JSONObject;
import java.math.BigDecimal;
import java.util.*;

public class ShoppingCartList extends BaseActivity implements OnClickListener, OnClickContentItemListener{
	public static PullToRefreshLV listview;
	LinearLayout back;
	double keeptotal;
	public static LinearLayout gopayfor;
	public static ShoppingCartAdapter adapter;
	public static List<ShoppingcartBean> list;
	int count;
	public static TextView gopaynum;
	public static TextView totalprice;
	public static TextView nocom;
	ShoppingcartBean bean;
	SharedPreferences sharedPreferences;
	private static ImageView deleteall;
	double doublePrices;

	@Override
	protected void onCreate(Bundle arg0) {
		if("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))){
			//未登录
			Intent login = new Intent(ShoppingCartList.this, Logon.class);
			startActivity(login);
		}else {
			list = new ArrayList<ShoppingcartBean>();
			getshoppingcart(Integer.parseInt(getGson().fromJson(getSp().getString("uid", ""), String.class)));
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			super.onCreate(arg0);
			setContentView(R.layout.shoppingcart);
			initView();
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					Intent intent = new Intent(ShoppingCartList.this, CommodityDetailTest.class);
					intent.putExtra("id", list.get(position - 1).fruitid);
					startActivity(intent);
				}
			});

			listview.setonRefreshListener(new OnRefreshListener() {

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
							listview.onRefreshComplete();
						};
					}.execute(null, null, null);
				}
			});
		}

	}
	
	//计算金额总的
	public	final void toCalculate(){
			if (list != null){
				doublePrices = 0.0;
				for(int i=0; i<list.size() ;i++){
					ShoppingcartBean shoppingCart = list.get(i);
					doublePrices = doublePrices + Double.parseDouble(shoppingCart.fruitprice==null?"0":shoppingCart.fruitprice) * Double.parseDouble(String.valueOf(shoppingCart.fruitcount)==null?"1":String.valueOf(shoppingCart.fruitcount));
					BigDecimal b = new BigDecimal(doublePrices);  
					doublePrices = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				totalprice.setText(doublePrices + "");
			}
		}
	
	public void initView(){
		ShoppingCartAdapter.totalall = 0.0;
		listview = (PullToRefreshLV) findViewById(R.id.rfslv);
		back = (LinearLayout) findViewById(R.id.back);
		gopaynum = (TextView) findViewById(R.id.gopaynum);
		totalprice = (TextView) findViewById(R.id.totalprice);
		deleteall = (ImageView) findViewById(R.id.deleteall);
		gopayfor = (LinearLayout) findViewById(R.id.gopayfor);
		nocom = (TextView) findViewById(R.id.test);
		back.setOnClickListener(this);
		gopayfor.setOnClickListener(this);
		deleteall.setOnClickListener(this);
		adapter = new ShoppingCartAdapter(this, list ,getGson().fromJson(getSp().getString("uid", ""), String.class));
		adapter.setOnClickContentItemListener(this);
		listview.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.deleteall:
			showMyDialog(0);
			break;
		case R.id.gopayfor:
			ArrayList<FruitBean> arrayList = new ArrayList<FruitBean>();
			for (int i = 0; i < list.size(); i++) {
				ShoppingcartBean cell = list.get(i);
				FruitBean fruitbean = new FruitBean();
				fruitbean.setProduct_id(cell.fruitid);
				fruitbean.setProduct_name(cell.fruitname);
				fruitbean.setCountNum(String.valueOf(cell.fruitcount));
				fruitbean.setPrice_tuangou(cell.fruitprice);
				fruitbean.setPrice(cell.fruitprice);
				fruitbean.setProduct_pic(cell.fruitimage);
				arrayList.add(fruitbean);
			}
			if (arrayList != null && arrayList.size() > 0) {
				Intent orderMakeView = new Intent(ShoppingCartList.this, OrderConfirm.class);
				orderMakeView.putExtra("listsgoods", arrayList);
				startActivity(orderMakeView);
			}
			break;
		default:
			break;
		}
	}
	
	public void showMyDialog(final int position) {
		MyDialog dialog = new MyDialog(this);
		dialog.setTitle("删除商品");
		dialog.setMessage("   确定从购物车删除所有商品吗？   ");
		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				HttpParams params = new HttpParams();
				params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
				HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.deleteallshoppingcart, params, new AsyncHttpResponseHandler(){
				 
				});
				list.clear();
				SharedPrefsUtil.putValue(ShoppingCartList.this, "Setting", 0);
				gopaynum.setText("(" + list.size() + ")");
				totalprice.setText("" + 0.0);
				//gopayfor.setBackgroundColor(getResources().getColor(R.color.common_grey));
				gopayfor.setBackgroundDrawable(getResources().getDrawable(R.drawable.greyframe));
				nocom.setVisibility(View.VISIBLE);
				adapter.notifyDataSetChanged();
			}
		});
		dialog.show();
	}
	
	public void getshoppingcart(int userid){
		HttpParams params = new HttpParams();
		params.put("userId", String.valueOf(userid));
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.checkshoppingcart, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("shoppingcartf = " + jsonObject);
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						ShoppingcartBean bean = new ShoppingcartBean();
						JSONObject object = jsonArray.optJSONObject(i);
						bean.fruitimage = object.optString("product_pic");
						bean.fruitprice = object.optString("price");
						bean.fruitname = object.optString("product_name");
						bean.fruitcount = object.optInt("numbers");
						bean.cartid = object.optInt("cart_id");
						bean.fruitid = object.optInt("product_id");
						list.add(bean);
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
					nocom.setVisibility(View.VISIBLE);
					gopaynum.setText("(" + list.size() + ")");
					totalprice.setText("" + 0.0);
					//gopayfor.setBackgroundColor(getResources().getColor(R.color.common_grey));
					gopayfor.setBackgroundDrawable(getResources().getDrawable(R.drawable.greyframe));
				}else {
					count = list.size();
					gopaynum.setText("(" + list.size() + ")");
					SharedPrefsUtil.putValue(ShoppingCartList.this, "Setting" + getGson().fromJson(getSp().getString("uid", ""), String.class), count);
					adapter.notifyDataSetChanged();
					toCalculate();
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
		switch (flag) {
		case 4:
			int selectNum = list.get(position).fruitcount;
			if (selectNum == 1) {
				return;
			}else {
				list.get(position).fruitcount = list.get(position).fruitcount - 1;
				adapter.notifyDataSetChanged();
			}
			toCalculate();
			break;
		case 5:
			list.get(position).fruitcount = list.get(position).fruitcount + 1;
			adapter.notifyDataSetChanged();
			toCalculate();
			break;
		case 6:
			showMyDialogItem(position);
			break;
		default:
			break;
		}
	}
	
	public void showMyDialogItem(final int position) {
		MyDialog dialog = new MyDialog(this);
		dialog.setTitle("删除商品");
		dialog.setMessage("   确定从购物车删除这个商品吗？   ");
		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("list.size() = " + 	list.size());
			}
		});
		
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				HttpParams params = new HttpParams();
				params.put("userId", getSp().getString("uid", ""));
				params.put("cart_id", list.get(position).cartid);
				HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.deleteshoppingcart, params, new AsyncHttpResponseHandler(){
				});
				list.remove(position);
				SharedPrefsUtil.putValue(ShoppingCartList.this, "Setting", list.size());
				if (list.size() == 0) {
					gopayfor.setBackgroundDrawable(getResources().getDrawable(R.drawable.greyframe));
					listview.setVisibility(View.GONE);
					nocom.setVisibility(View.VISIBLE);
				}else{
					nocom.setVisibility(View.GONE);
					listview.setVisibility(View.VISIBLE);
					adapter.notifyDataSetChanged();
				}
				gopaynum.setText("(" + list.size() + ")");
				toCalculate();
			}
		});
		dialog.show();
	}
}
