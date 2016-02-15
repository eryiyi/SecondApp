package com.example.secondapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.SecondApplication;
import com.example.secondapp.bean.MyOrderBean;
import com.example.secondapp.fragmentdetail.MOFragment1;
import com.example.secondapp.fragmentdetail.MOFragment3;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.view.MyDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MyOrderAdapter3 extends BaseAdapter{
	public static List<MyOrderBean> list;
	LayoutInflater inflater;
	Context context;
	public static String qq;
	private String uid;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

	private OnClickContentItemListener onClickContentItemListener;

	public MyOrderAdapter3(Context context, List<MyOrderBean> list, String uid){
		inflater = LayoutInflater.from(context);
		MyOrderAdapter3.list = list;
		this.uid = uid;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		if(list != null){
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if(list != null && arg0 < list.size()){
			return list.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
	
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.itemmyorder, null);
			holder.orderimg = (ImageView) convertView.findViewById(R.id.comimage);
			holder.ordername = (TextView) convertView.findViewById(R.id.comname);
			holder.orderprice = (TextView) convertView.findViewById(R.id.comprice);
			holder.freightprice = (TextView) convertView.findViewById(R.id.freight);
			holder.paidprice = (TextView) convertView.findViewById(R.id.paid);
			holder.number = (TextView) convertView.findViewById(R.id.number);
			holder.cancelpay = (TextView) convertView.findViewById(R.id.cancelpay);
			holder.checkorder = (TextView) convertView.findViewById(R.id.checkorder);
			holder.payment = (TextView) convertView.findViewById(R.id.payment);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		final MyOrderBean myOrderBean = list.get(position);
		if (position < list.size() && myOrderBean != null) {
			if(myOrderBean.getGoods_data() != null && myOrderBean.getGoods_data().size() >0 ){
				imageLoader.displayImage(ServerId.serveradress + myOrderBean.getGoods_data().get(0).getImg(),
						holder.orderimg, SecondApplication.options, animateFirstListener);
			}
			holder.ordername.setText(list.get(position).getOrder_no()==null?"":list.get(position).getOrder_no());
			holder.orderprice.setText(list.get(position).getPayable_amount()==null?"":list.get(position).getPayable_amount());
			holder.freightprice.setText(list.get(position).getPayable_freight()==null?"":list.get(position).getPayable_freight());
			holder.paidprice.setText(list.get(position).getReal_amount()==null?"0":list.get(position).getReal_amount());
			holder.number.setText((list.get(position).getGoods_nums()==null?"0":list.get(position).getGoods_nums()) + "件");
		}
		CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
		TextView cancelpay = (TextView) convertView.findViewById(R.id.cancelpay);
		TextView payment = (TextView) convertView.findViewById(R.id.payment);
		cancelpay.setText("取消订单");
		payment.setText("联系卖家");
		checkbox.setVisibility(View.GONE);
		cancelpay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showMyDialog(position);
			}
		});

		payment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				contactSeller(position);
			}
		});

//		holder.checkorder.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				onClickContentItemListener.onClickContentItem(position, 1, null);
//			}
//		});
//		holder.cancelpay.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				onClickContentItemListener.onClickContentItem(position, 2, null);
//			}
//		});
//		holder.payment.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				onClickContentItemListener.onClickContentItem(position, 3, null);
//			}
//		});

		return convertView;
	}

	public void contactSeller(final int position){
		HttpParams params = new HttpParams();
		params.put("product_id", list.get(position).getProduct_id());
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getshopqq, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("jsonObject = " + jsonObject);
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				JSONObject object = jsonArray.optJSONObject(0);
				qq = object.optString("qq");
				Message message = new Message();
				message.what = 123;
				handler.sendMessage(message);
			}
		});
	}
	
	class Holder{
		ImageView orderimg;
		TextView ordername;
		TextView orderprice;
		TextView freightprice;
		TextView paidprice;
		TextView number;
		TextView checkorder;
		TextView cancelpay;
		TextView payment;
	}
	
	public void showMyDialog(final int position) {
		MyDialog dialog = new MyDialog(context);
		//dialog.setIcon(R.drawable.a008);
		dialog.setTitle("取消订单");
		dialog.setMessage("确认删除这个订单吗？ ");
		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				HttpParams params = new HttpParams();
				params.put("userId", uid);
				params.put("order_id",  list.get(position).getOrder_id());
				HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.cancelorder, params, new AsyncHttpResponseHandler(){
					 
				});
				list.remove(position);
				MOFragment3.adapter.notifyDataSetChanged();
				if (list.size() == 0) {
					MOFragment3.noorder.setVisibility(View.VISIBLE);
				}
			}
		});
		dialog.show();
	}
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				System.out.println("qq = " + qq);
				break;
			default:
				break;
			}
			return false;
		}
	});
}
