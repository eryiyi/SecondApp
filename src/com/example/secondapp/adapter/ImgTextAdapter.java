package com.example.secondapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.secondapp.R;
import com.example.secondapp.bean.ImgTextBean;
import com.example.secondapp.networkbitmap.BitmapUtil;

import java.util.List;

public class ImgTextAdapter extends BaseAdapter {
	List<ImgTextBean> list;
	LayoutInflater inflater;
	Context context;

	public ImgTextAdapter(Context context, List<ImgTextBean> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
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
		// 对象引用保留
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.imageitem, null);
			holder.imgid = (ImageView) convertView.findViewById(R.id.imageviewitem);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if (position < list.size()) {
			BitmapUtil.getInstance().download("http://115.29.208.113/", list.get(position).imgid, holder.imgid);
		}
		return convertView;
	}

	class Holder {
		ImageView imgid;
	}
}
