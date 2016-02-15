package com.example.secondapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.activity.CommodityDetailTest;
import com.example.secondapp.activity.Evaluation;
import com.example.secondapp.model.ReplyObj;
import com.example.secondapp.sharedprefernces.SharedPrefsUtil;
import com.example.secondapp.util.RelativeDateFormat;

import java.util.List;

public class EvalutionAdapter extends BaseAdapter {
	List<ReplyObj> list;
	LayoutInflater inflater;
	Context context;
	public EvalutionAdapter(Context context, List<ReplyObj> list) {
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
			convertView = inflater.inflate(R.layout.itemevaluation, null);
			holder.username = (TextView) convertView.findViewById(R.id.username);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.evaluation = (TextView) convertView.findViewById(R.id.evaluation);
			holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
//			holder.username.setText(list.get(position).getContent());
			Log.i("villa", position  + " = " + list.get(position).getDateline());
			holder.date.setText(RelativeDateFormat.format(Long.parseLong((list.get(position).getDateline() == null ? "" : list.get(position).getDateline()))));
			holder.evaluation.setText(list.get(position).getContent());
			holder.ratingBar.setRating(Float.valueOf(list.get(position).getGrade()==null?"0":list.get(position).getGrade()));
		}
		SharedPrefsUtil.putValue(context, Evaluation.getid + "" , list.size());
		CommodityDetailTest.evaluationcount.setText("" + list.size());
		return convertView;
	}
	
	class Holder{
		TextView username;
		TextView date;
		TextView evaluation;
		RatingBar ratingBar;
	}
}
