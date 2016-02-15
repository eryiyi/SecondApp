package com.example.secondapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.activity.EditAddress;
import com.example.secondapp.activity.ReceivingAddress;
import com.example.secondapp.bean.AdressBean;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.view.MyDialog;

import java.util.HashMap;
import java.util.List;

public class AdressAdapter extends BaseAdapter {
	List<AdressBean> list;
	LayoutInflater inflater;
	Context context;
	CheckBox checkBox;
	public static LinearLayout layoutitem;
	public static int flag = 0;
	private static HashMap<Integer, Boolean> isSelected;

	private String uid = "";

	public AdressAdapter(Context context, List<AdressBean> list, String uid) {
		inflater = LayoutInflater.from(context);
		this.list = list;
		this.context = context;
		isSelected = new HashMap<Integer, Boolean>();
		initDate();
		this.uid = uid;
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

	private void initDate() {
		
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.itemadress, null);
			layoutitem = (LinearLayout) convertView.findViewById(R.id.layoutitem);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.adress = (TextView) convertView.findViewById(R.id.adress);
			holder.phonenumber = (TextView) convertView.findViewById(R.id.phonenumber);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkboxadress);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if (position < list.size()) {
			holder.name.setText(list.get(position).name);
			holder.adress.setText(list.get(position).adress);
			holder.phonenumber.setText(list.get(position).phonenumber);
			if(getIsSelected() != null){
				holder.checkBox.setChecked(getIsSelected().get(position)==null?false:getIsSelected().get(position));
			}

		}
		
		if (list.get(position).state == 1 && flag == 0) {
			System.out.println("pos = " + position);
			getIsSelected().put(position, true);
		}
		
		holder.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setDefaultAdress(position);
				for (int i = 0; i < list.size(); i++) {
					if (position != i) {
						System.out.println("i = " + i);
						getIsSelected().put(i, false);
					} else {
						System.out.println("ix = " + i);
						getIsSelected().put(i, true);
					}
				}
				ReceivingAddress.adapter.notifyDataSetChanged();
				flag = 1;
			}
		});

		convertView.findViewById(R.id.delete).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						showMyDialog(position);
						// list.remove(position);
						// ReceivingAddress.listView.setAdapter(ReceivingAddress.adapter);
						// ReceivingAddress.adapter.notifyDataSetChanged();
					}
				});

		convertView.findViewById(R.id.edit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(context, EditAddress.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("receiveaddress", list.get(position));
						intent.putExtras(bundle);
						intent.putExtra("pos", position);
						context.startActivity(intent);
					}
				});
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		AdressAdapter.isSelected = isSelected;
	}

	class Holder {
		TextView name;
		TextView adress;
		TextView phonenumber;
		CheckBox checkBox;
	}

	public void showMyDialog(final int position) {
		MyDialog dialog = new MyDialog(context);
		// dialog.setIcon(R.drawable.a008);
		dialog.setTitle("收货地址");
		dialog.setMessage("   确定删除这个地址吗？   ");
		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				list.remove(position);
				ReceivingAddress.listView.setAdapter(ReceivingAddress.adapter);
			}
		});
		dialog.show();
	}
	
	public void setDefaultAdress(int pos){
		HttpParams params = new HttpParams();
		params.put("userId", uid);
		params.put("id", list.get(pos).id);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getdefaultadress, params, new AsyncHttpResponseHandler(){
		});
	}
}
