package com.example.secondapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.activity.MyCollection;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.networkbitmap.BitmapUtil;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.view.MyDialog;

import java.util.List;

public class MyCollectionAdapter extends BaseAdapter{
	//适配器对数据源的引用
	List<FruitBean> list;
	//布局加载器，作用是将一个xml的布局文件，转换成java代码能够识别的View对象
	LayoutInflater inflater;
	Context context;
	private String uid;
	
	public MyCollectionAdapter(Context context, List<FruitBean> list, String uid){
		this.list = list;
		inflater = LayoutInflater.from(context);
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
		//对象引用保留
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.itemmycollection, null);
			holder.compic = (ImageView) convertView.findViewById(R.id.comimage);
			holder.comdescribe = (TextView) convertView.findViewById(R.id.comname);
			holder.compri = (TextView) convertView.findViewById(R.id.comprice);
			holder.comsuppri = (TextView) convertView.findViewById(R.id.comsuppri);
			holder.addshopcart = (TextView) convertView.findViewById(R.id.purchasequantity);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		if(position < list.size()){
			BitmapUtil.getInstance().download(ServerId.serveradress, list.get(position).getProduct_pic(), holder.compic);
			holder.comdescribe.setText(list.get(position).getProduct_name());
			holder.compri.setText(list.get(position).getPrice());
			holder.comsuppri.setText(list.get(position).getPrice_tuangou());
			holder.comsuppri.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			holder.addshopcart.setText(list.get(position).getBuy_numbers() + "人购买");
		}
		
		((ImageView)convertView.findViewById(R.id.delete)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showMyDialog(position);
			}
		});
		
		return convertView;
	}

	
	/**
	 * 对每个数据项里面的控件的引用
	 * 也是每个数据项里面的控件的保存
	 * 使用他可以在getView方法里面当convertView不为空，也就是这个数据项是使用缓存的View的时候，
	 * 他可以快捷的访问到View里面的控件的引用
	 */
	class Holder{
		ImageView compic;
		TextView comdescribe;
		TextView compri;
		TextView comsuppri;
		TextView addshopcart;
	}
	
	public void showMyDialog(final int position) {
		MyDialog dialog = new MyDialog(context);
		dialog.setTitle("删除收藏商品");
		dialog.setMessage("   确定删除这个商品吗？   ");
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
				params.put("product_id", list.get(position).getProduct_id());
				HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.deleteCollection, params, new AsyncHttpResponseHandler(){
				 
				});
				list.remove(position);
				if (list.size() == 0) {
					MyCollection.nocollection.setVisibility(View.VISIBLE);
				}else{
					MyCollection.adapter.notifyDataSetChanged();
				}
			}
		});
		dialog.show();
	}
}
