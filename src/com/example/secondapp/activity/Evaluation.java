package com.example.secondapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.secondapp.R;
import com.example.secondapp.adapter.EvalutionAdapter;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.model.ReplyObj;
import java.util.ArrayList;
import java.util.List;

public class Evaluation extends BaseActivity {
	ListView evaluationlv;
	LinearLayout back;
	List<ReplyObj> list = new ArrayList<ReplyObj>();
	EvalutionAdapter adapter;
	public static int getid;
	private FruitBean fruitbean;
	public static int evaluatecount;   
	@Override
	protected void onCreate(Bundle arg0) {
		fruitbean = (FruitBean) getIntent().getExtras().get("fruitbean");
		if(fruitbean != null && fruitbean.getReply() != null){
			evaluatecount = fruitbean.getReply().size();
			save("evaluatecount" + fruitbean.getProduct_id(), evaluatecount);
			list.addAll(fruitbean.getReply());
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.evaluation);
		evaluationlv = (ListView) findViewById(R.id.evaluationlv);
		back = (LinearLayout) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		adapter = new EvalutionAdapter(this, list);
		evaluationlv.setAdapter(adapter);
	}
	
	/*public void getEvaluationData(int productid){
		HttpParams params = new HttpParams();
		params.put("product_id", productid);
		HttpClientUtils.getInstance().get(ServerId.serveradress, ServerId.getproductassess, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				Log.i("villa", "evaluation = " + jsonObject);
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						EvaluationBean bean = new EvaluationBean();
						JSONObject object = jsonArray.optJSONObject(i);
						bean.date = object.optString("dateline");
						bean.evaluation = object.optString("content");
						bean.username = object.optString("user_name");
						bean.grade = object.optInt("grade");
						list.add(bean);
					}
				}
				Message message = new Message();
				message.what = 123;
				handler.sendMessage(message);
			}
		});
	}*/
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			return false;
		}
	});
}
