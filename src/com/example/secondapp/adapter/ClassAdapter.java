package com.example.secondapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.bean.ClassBean;
import com.example.secondapp.networkbitmap.BitmapUtil;

import java.util.List;

public class ClassAdapter extends BaseAdapter{
	List<ClassBean> list;
	LayoutInflater inflater;
	Context context;
	
	public ClassAdapter(Context context, List<ClassBean> list){
		inflater = LayoutInflater.from(context);
		this.list = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if (list != null && arg0 < list.size()) {
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
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.itemclass, null);
			holder.classimage = (ImageView) convertView.findViewById(R.id.classimage);
			holder.typename = (TextView) convertView.findViewById(R.id.typename);
			holder.example = (TextView) convertView.findViewById(R.id.example);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			BitmapUtil.getInstance().download("http://115.29.208.113/", list.get(position).typelogo, holder.classimage);
			holder.typename.setText(list.get(position).typename);
			holder.example.setText(list.get(position).example);
		}
		return convertView;
	}

	class Holder{
		ImageView classimage;
		TextView typename;
		TextView example;
	}
}
