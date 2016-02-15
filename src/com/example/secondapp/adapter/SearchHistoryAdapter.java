package com.example.secondapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.secondapp.R;

import java.util.List;

public class SearchHistoryAdapter extends BaseAdapter{
	List<SearchHistoryBean> list;
	LayoutInflater inflater;
	
	public SearchHistoryAdapter(Context context, List<SearchHistoryBean> list){
		this.list = list;
		inflater = LayoutInflater.from(context);
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
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.itemsearchhistory, null);
			holder.searchname = (TextView) convertView.findViewById(R.id.searchname);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			holder.searchname.setText(list.get(position).searchname);
		}
		return convertView;
	}

	class Holder{
		TextView searchname;
	}
	
	public static class SearchHistoryBean{
		public String searchname;
		public SearchHistoryBean(){
			super();
		}
	}
}
