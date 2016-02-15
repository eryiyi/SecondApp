package com.example.secondapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.sharedprefernces.SharedPrefsUtil;
import org.json.JSONObject;

public class CommodityDetail extends BaseActivity implements OnClickListener{
	RelativeLayout evaluation;
	LinearLayout back;
	LinearLayout shoppingcart;
	Button buyrightnow;
	Button addshoppingcart;
	FruitBean fruitbean;
	ImageView test2;
	TextView commodityname;
	TextView commodityprice;
	TextView minus;
	TextView plus;
	TextView count;
	CheckBox collectioncheckbox;
	public static TextView evaluationcount;
	private static int countall = 1;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.commoditydetail);
		fruitbean = (FruitBean) getIntent().getSerializableExtra("fruitdetail");
//		test2 = (ImageView) findViewById(R.id.test2);
//		BitmapUtil.getInstance().download(ServerId.serveradress, fruitbean.fruitimage, test2);
		evaluation = (RelativeLayout) findViewById(R.id.evaluation);
		back = (LinearLayout) findViewById(R.id.back);
		shoppingcart = (LinearLayout) findViewById(R.id.shoppingcart);
		addshoppingcart = (Button) findViewById(R.id.addshoppingcart);
		buyrightnow = (Button) findViewById(R.id.buyrightnow);
		minus = (TextView) findViewById(R.id.minus);
		plus = (TextView) findViewById(R.id.plus);
		count = (TextView) findViewById(R.id.count);
		commodityname = (TextView) findViewById(R.id.commodityname);
		commodityname.setText(fruitbean.getProduct_name());
		commodityprice = (TextView) findViewById(R.id.commodityprice);
		commodityprice.setText("¥" + fruitbean.getPrice());
		evaluationcount = (TextView) findViewById(R.id.evaluationcount);
		int num = SharedPrefsUtil.getValue(this, fruitbean.getProduct_id() + "", 0);
		evaluationcount.setText("" + num);
		collectioncheckbox = (CheckBox) findViewById(R.id.collectioncheckbox);
		collectioncheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					HttpParams params = new HttpParams();
					params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
					params.put("product_id", fruitbean.getProduct_id());
					HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.addproductCollection, params, new AsyncHttpResponseHandler(){
						@Override
						public void onSuccess(JSONObject jsonObject) {
							System.out.println("addshopjsonObject = " + jsonObject.toString());
						}
					});
				}else{
					HttpParams params = new HttpParams();
					params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
					params.put("product_id", fruitbean.getProduct_id());
					HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.deleteCollection, params, new AsyncHttpResponseHandler(){
					});
				}
			}
		});
		minus.setOnClickListener(this);
		plus.setOnClickListener(this);
		evaluation.setOnClickListener(this);
		back.setOnClickListener(this);
		shoppingcart.setOnClickListener(this);
		buyrightnow.setOnClickListener(this);
		addshoppingcart.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.evaluation:
			Intent id = new Intent(CommodityDetail.this, Evaluation.class);
			id.putExtra("productid", fruitbean.getProduct_id());
			startActivity(id);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.shoppingcart:
			Intent id1 = new Intent(CommodityDetail.this, ShoppingCartList.class);
			startActivity(id1);
			break;
		case R.id.addshoppingcart:
			if ("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
				Intent intent = new Intent(CommodityDetail.this, Logon.class);
				intent.putExtra("nozero", 1);
				startActivity(intent);
			}else{
				HttpParams params = new HttpParams();
				params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
				params.put("product_id", fruitbean.getProduct_id());
				params.put("product_name", fruitbean.getProduct_name());
				params.put("user_name", getGson().fromJson(getSp().getString("user_name", ""), String.class));
				params.put("price", fruitbean.getPrice());
				params.put("numbers", "" + countall);
				HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.addshoppingcart, params, new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(JSONObject jsonObject) {
						System.out.println("addshopjsonObject = " + jsonObject.toString());
					}
					
					@Override
					public void onFailure(String result, int statusCode, String errorResponse) {
						super.onFailure(result, statusCode, errorResponse);
					}
				});
				Toast.makeText(CommodityDetail.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.buyrightnow:
			Intent intent = new Intent(CommodityDetail.this, OrderConfirm.class);
			startActivity(intent);
			break;
		case R.id.minus:
			if (countall > 1) {
				count.setText("" + --countall);
			}
			break;
		case R.id.plus:
			count.setText("" + ++countall);
			break;
		default:
			break;
		}
	}
}
