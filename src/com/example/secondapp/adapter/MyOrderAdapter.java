package com.example.secondapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.secondapp.activity.OrderConfirm;
import com.example.secondapp.bean.MyOrderBean;
import com.example.secondapp.bean.OrderGoods;
import com.example.secondapp.fragmentdetail.MOFragment1;
import com.example.secondapp.serviceId.ServerId;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class MyOrderAdapter extends BaseAdapter{
	public static List<MyOrderBean> list;
	LayoutInflater inflater;
	Context context;
	public static double totalpri;
	List<Integer> listItemID = new ArrayList<Integer>();
	public static CheckBox checkBox;

	private String uid;

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

	private OnClickContentItemListener onClickContentItemListener;


	public MyOrderAdapter(Context context, List<MyOrderBean> list, String uid){
		inflater = LayoutInflater.from(context);
		MyOrderAdapter.list = list;
		this.context = context;
		this.uid = uid;
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

			holder.ordername.setText("订单号:"+(list.get(position).getOrder_no()==null?"":list.get(position).getOrder_no()));
			holder.orderprice.setText(list.get(position).getPayable_amount()==null?"":list.get(position).getPayable_amount());
			holder.freightprice.setText(list.get(position).getPayable_freight()==null?"":list.get(position).getPayable_freight());
			holder.paidprice.setText(list.get(position).getReal_amount()==null?"0":list.get(position).getReal_amount());

			List<OrderGoods>  listgoods =myOrderBean.getGoods_data();
			int countNum = 0;
			if(listgoods != null){
				for(OrderGoods orderGoods:listgoods){
					countNum += Integer.parseInt(orderGoods.getGoods_nums()==null?"0":orderGoods.getGoods_nums());
				}
			}
			holder.number.setText(countNum + "件");
		}
		checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

		holder.cancelpay.setVisibility(View.VISIBLE);
		holder.cancelpay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(context, ViewLogistics.class);
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("mylogistics", list.get(position));
//				intent.putExtras(bundle);
//				context.startActivity(intent);
			}
		});
		
		holder.payment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				showMyDialog(position);
				Intent Ordermake = new Intent(context, OrderConfirm.class);
				Ordermake.putExtra("typePay","1" );
				Ordermake.putExtra("myOrderBean",  myOrderBean);
				context.startActivity(Ordermake);
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

//		public void showMyDialog(final int position) {
//			MyDialog dialog = new MyDialog(context);
//			//dialog.setIcon(R.drawable.a008);
//			dialog.setTitle("确定已收货？");
//			dialog.setMessage("确认后交易金额将支付到商家账户 ");
//			dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//
//				}
//			});
//			dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//
//				}
//			});
//			dialog.show();
//		}
		return convertView;
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

}
