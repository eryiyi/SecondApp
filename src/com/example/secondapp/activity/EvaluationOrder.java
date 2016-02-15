package com.example.secondapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RatingBar.OnRatingBarChangeListener;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.MyOrderBean;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.networkbitmap.BitmapUtil;
import com.example.secondapp.serviceId.ServerId;
import com.ta.utdid2.android.utils.SystemUtils;

import org.json.JSONObject;

public class EvaluationOrder extends BaseActivity implements OnCheckedChangeListener{
	private static MyOrderBean myOrderBean;
	ImageView imageView;
	TextView comname;
	TextView comprice;
	TextView number;
	TextView evtorder;
	EditText evtcontent;
	LinearLayout back;
	private static CheckBox good;
	private static CheckBox normal;
	private static CheckBox bad;
	private static RatingBar ratingbar;
	private static int ratingnumber;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.evaluationorder);
		back = (LinearLayout) findViewById(R.id.back);
		ratingbar = (RatingBar) findViewById(R.id.ratingbar);
		good = (CheckBox) findViewById(R.id.good);
		evtcontent = (EditText) findViewById(R.id.evtcontent);
		normal = (CheckBox) findViewById(R.id.normal);
		bad = (CheckBox) findViewById(R.id.bad);
		evtorder = (TextView) findViewById(R.id.evtorder);
		myOrderBean = (MyOrderBean) getIntent().getSerializableExtra("orderbean");
		imageView = (ImageView) findViewById(R.id.comimage);
		BitmapUtil.getInstance().download(ServerId.serveradress, myOrderBean.getImg(), imageView);
		comname = (TextView) findViewById(R.id.comname);
		comname.setText(myOrderBean.getOrder_no());
		comprice = (TextView) findViewById(R.id.comprice);
		comprice.setText("¥" + myOrderBean.getReal_amount());
		number = (TextView) findViewById(R.id.number);
		number.setText(myOrderBean.getGoods_nums() + "件");
		good.setOnCheckedChangeListener(this);
		normal.setOnCheckedChangeListener(this);
		bad.setOnCheckedChangeListener(this);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		evtorder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (evtcontent.getText().length() == 0) {
					Toast.makeText(EvaluationOrder.this, "尚未填写商品评论哦", Toast.LENGTH_SHORT).show();
				}else if (evtcontent.getText().length() < 5 || evtcontent.getText().length() >= 100) {
					Toast.makeText(EvaluationOrder.this, "评论字数在5-100个字之间哦", Toast.LENGTH_SHORT).show();
				}else if (ratingnumber == 0) {
					Toast.makeText(EvaluationOrder.this, "您还没进行星级评价哦", Toast.LENGTH_SHORT).show();
				}else {
					evtOrder();
				}
			}
		});
		ratingbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,  boolean fromUser) {
				ratingnumber = (int) rating;
				System.out.println("ratingnumber = " + ratingnumber);
			}
		});
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean ischecked) {
		switch (buttonView.getId()) {
		case R.id.good:
			if (ischecked) {
				normal.setChecked(false);
				bad.setChecked(false);
			}
			break;
		case R.id.normal:
			if (ischecked) {
				good.setChecked(false);
				bad.setChecked(false);
			}
			break;
		case R.id.bad:
			if (ischecked) {
				good.setChecked(false);
				normal.setChecked(false);
			}
			break;
		default:
			break;
		}
	}
	
	public void evtOrder(){
		HttpParams params = new HttpParams();
		params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
		params.put("product_id", myOrderBean.getProduct_id());
		params.put("order_id", myOrderBean.getOrder_id());
		params.put("content", evtcontent.getText().toString());
		params.put("grade", ratingnumber);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.toassess, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("jsonObjectorder = " + jsonObject);
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
				
				break;
			default:
				break;
			}
			return false;
		}
	});
}
