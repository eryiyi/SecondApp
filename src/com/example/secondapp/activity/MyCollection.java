package com.example.secondapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.adapter.MyCollectionAdapter;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.view.PullToRefreshLV;
import com.example.secondapp.view.PullToRefreshLV.OnRefreshListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyCollection extends BaseActivity {
	PullToRefreshLV lv;
	List<FruitBean> list;
	LinearLayout logonback;
	public static MyCollectionAdapter adapter;
	public static TextView nocollection;
	@Override
	protected void onCreate(Bundle arg0) {
		checkCollection(Integer.parseInt(getGson().fromJson(getSp().getString("uid", ""), String.class)));
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.mycollection);
		lv = (PullToRefreshLV) findViewById(R.id.collectionlv);
		logonback = (LinearLayout) findViewById(R.id.logonback);
		nocollection = (TextView) findViewById(R.id.nocollection);
		list = new ArrayList<FruitBean>();
		list.clear();
		adapter = new MyCollectionAdapter(this, list,getGson().fromJson(getSp().getString("uid", ""), String.class) );
		//lv.setAdapter(adapter);
		logonback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
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
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(MyCollection.this, CommodityDetailTest.class);
				/*Bundle bundle = new Bundle();
				bundle.putSerializable("fruitdetail", list.get(position));
				intent.putExtras(bundle);*/
				intent.putExtra("id", list.get(position - 1).getProduct_id());
				startActivity(intent);
			}
		});

	}
//{"data":[{"uid":"2","delivery_type":null,"product_pic1":null,"product_id":null,"product_name":null,"is_open":null,"dateline":null,"buy_numbers":null,"product_pic2":null,"info":null,"product_pic3":null,"is_num":null,"id":"11","product_pic":null,"pay_type":null,"shop_id":null,"is_discount":null,"is_tuangou":null,"community_id":null,"product_audit":null,"type_id":null,"discount":null,"price_tuangou":null,"unit":null,"price":null,"sale_num":null,"tuangou_numbers":null},{"uid":"2","delivery_type":null,"product_pic1":null,"product_id":null,"product_name":null,"is_open":null,"dateline":null,"buy_numbers":null,"product_pic2":null,"info":null,"product_pic3":null,"is_num":null,"id":"12","product_pic":null,"pay_type":null,"shop_id":null,"is_discount":null,"is_tuangou":null,"community_id":null,"product_audit":null,"type_id":null,"discount":null,"price_tuangou":null,"unit":null,"price":null,"sale_num":null,"tuangou_numbers":null},{"uid":"2","delivery_type":null,"product_pic1":null,"product_id":null,"product_name":null,"is_open":null,"dateline":null,"buy_numbers":null,"product_pic2":null,"info":null,"product_pic3":null,"is_num":null,"id":"14","product_pic":null,"pay_type":null,"shop_id":null,"is_discount":null,"is_tuangou":null,"community_id":null,"product_audit":null,"type_id":null,"discount":null,"price_tuangou":null,"unit":null,"price":null,"sale_num":null,"tuangou_numbers":null},{"uid":"2","delivery_type":null,"product_pic1":null,"product_id":null,"product_name":null,"is_open":null,"dateline":null,"buy_numbers":null,"product_pic2":null,"info":null,"product_pic3":null,"is_num":null,"id":"15","product_pic":null,"pay_type":null,"shop_id":null,"is_discount":null,"is_tuangou":null,"community_id":null,"product_audit":null,"type_id":null,"discount":null,"price_tuangou":null,"unit":null,"price":null,"sale_num":null,"tuangou_numbers":null},{"uid":"2","delivery_type":null,"product_pic1":null,"product_id":null,"product_name":null,"is_open":null,"dateline":null,"buy_numbers":null,"product_pic2":null,"info":null,"product_pic3":null,"is_num":null,"id":"16","product_pic":null,"pay_type":null,"shop_id":null,"is_discount":null,"is_tuangou":null,"community_id":null,"product_audit":null,"type_id":null,"discount":null,"price_tuangou":null,"unit":null,"price":null,"sale_num":null,"tuangou_numbers":null},{"uid":"2","delivery_type":null,"product_pic1":null,"product_id":null,"product_name":null,"is_open":null,"dateline":null,"buy_numbers":null,"product_pic2":null,"info":null,"product_pic3":null,"is_num":null,"id":"17","product_pic":null,"pay_type":null,"shop_id":null,"is_discount":null,"is_tuangou":null,"community_id":null,"product_audit":null,"type_id":null,"discount":null,"price_tuangou":null,"unit":null,"price":null,"sale_num":null,"tuangou_numbers":null},{"uid":"2","delivery_type":null,"product_pic1":null,"product_id":null,"product_name":null,"is_open":null,"dateline":null,"buy_numbers":null,"product_pic2":null,"info":null,"product_pic3":null,"is_num":null,"id":"29","product_pic":null,"pay_type":null,"shop_id":null,"is_discount":null,"is_tuangou":null,"community_id":null,"product_audit":null,"type_id":null,"discount":null,"price_tuangou":null,"unit":null,"price":null,"sale_num":null,"tuangou_numbers":null},{"uid":"2","delivery_type":"3","product_pic1":"\/Uploads\/2015-12-05\/5662291836622.jpg","product_id":"30","product_name":"白菜","is_open":"1","dateline":"1449484347","buy_numbers":"0","product_pic2":"\/Uploads\/2015-12-05\/5662291837148.jpg","info":"<p><span style=\"color: rgb(204, 0, 0); font-family: arial; font-size: 13px; line-height: 20.02px; background-color: rgb(255, 255, 255);\">胡萝卜<\/span><span style=\"color: rgb(51, 51, 51); font-family: arial; font-size: 13px; line-height: 20.02px; background-color: rgb(255, 255, 255);\">，别名<\/span><span style=\"color: rgb(204, 0, 0); font-family: arial; font-size: 13px; line-height: 20.02px; background-color: rgb(255, 255, 255);\">红萝卜<\/span><span style=\"color: rgb(51, 51, 51); font-family: arial; font-size: 13px; line-height: 20.02px; background-color: rgb(255, 255, 255);\">、丁香萝卜、葫芦菔金，又被称为胡芦菔、红菜头、黄萝卜等，有地下“小人参”之称，是伞形二年生草本植物，以呈肉质的根作为蔬菜来食用。<\/span><span style=\"color: rgb(204, 0, 0); font-family: arial; font-size: 13px; line-height: 20.02px; background-color: rgb(255, 255, 255);\">胡萝卜<\/span><span style=\"color: rgb(51, 51, 51); font-family: arial; font-size: 13px; line-height: 20.02px; background-color: rgb(255, 255, 255);\">每100克鲜重含1.67～12.1毫克<\/span><span style=\"color: rgb(204, 0, 0); font-family: arial; font-size: 13px; line-height: 20.02px; background-color: rgb(255, 255, 255);\">胡萝卜<\/span><span style=\"color: rgb(51, 51, 51); font-family: arial; font-size: 13px; line-height: 20.02px; background-color: rgb(255, 255, 255);\">素，含量高于番茄的5～7倍，食用后经肠<\/span><\/p>","product_pic3":null,"is_num":"1000","id":"32","product_pic":"\/Uploads\/2015-12-05\/566229183544a.jpg","pay_type":null,"shop_id":"12","is_discount":null,"is_tuangou":null,"community_id":"0","product_audit":"1","type_id":"1","discount":"绿色无污染","price_tuangou":"1.80","unit":"市斤","price":"2.00","sale_num":null,"tuangou_numbers":null}],"msg":"success","code":200}
	public void checkCollection(int userid) {
		HttpParams params = new HttpParams();
		params.put("userId", String.valueOf(userid));
		HttpClientUtils.getInstance().post(ServerId.serveradress,
				ServerId.getCollection, params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonObject) {
						System.out.println("jsonObject = " + jsonObject);
						JSONArray jsonArray = jsonObject.optJSONArray("data");
						if (jsonArray != null) {
							for (int i = 0; i < jsonArray.length(); i++) {
								FruitBean bean = new FruitBean();
								JSONObject object = jsonArray.optJSONObject(i);
								bean.setProduct_pic(object.optString("product_pic"));
								bean.setPrice(object.optString("price_tuangou"));
								bean.setProduct_name(object.optString("product_name"));
								bean.setPrice(object.optString("price"));
								bean.setProduct_id(object.optInt("product_id"));
								bean.setBuy_numbers(object.optString("buy_numbers"));
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
					nocollection.setVisibility(View.VISIBLE);
				}
				lv.setAdapter(adapter);
				//adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			return false;
		}
	});
}
