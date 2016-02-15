package com.example.secondapp.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.networkbitmap.BitmapUtil;
import com.example.secondapp.serviceId.ServerId;

import java.util.List;

public class FruitShowListViewAdapter extends BaseAdapter{
	//适配器对数据源的引用
	List<FruitBean> list;
	//布局加载器，作用是将一个xml的布局文件，转换成java代码能够识别的View对象
	LayoutInflater inflater;
	Context context;
	
	public FruitShowListViewAdapter(Context context, List<FruitBean> list){
		this.list = list;
		inflater = LayoutInflater.from(context);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		//对象引用保留
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.itemfruit, null);
			holder.compic = (ImageView) convertView.findViewById(R.id.comimage);
			holder.comdescribe = (TextView) convertView.findViewById(R.id.comname);
			holder.compri = (TextView) convertView.findViewById(R.id.comprice);
			holder.comsuppri = (TextView) convertView.findViewById(R.id.comsuppri);
			holder.addshopcart = (TextView) convertView.findViewById(R.id.purchasequantity);
			holder.compnum = (TextView) convertView.findViewById(R.id.compnum);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		if(position < list.size()){
			BitmapUtil.getInstance().download(ServerId.serveradress, list.get(position).getProduct_pic(), holder.compic);
			holder.comdescribe.setText(list.get(position).getProduct_name());
			holder.compri.setText("¥" + (list.get(position).getPrice_tuangou()==null?"":list.get(position).getPrice_tuangou()));
			holder.comsuppri.setText("原价:" +  (list.get(position).getPrice()==null?"":list.get(position).getPrice()));
			holder.comsuppri.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
			holder.compnum.setText(list.get(position).getIs_num());
			String numcount =(list.get(position).getBuy_numbers()==null?"0":list.get(position).getBuy_numbers());
			if("".equals(numcount)){
				numcount = "0";
			}
			holder.addshopcart.setText(numcount);
		}
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
		TextView compnum;
	}
}
