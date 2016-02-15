package com.example.secondapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.SecondApplication;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.serviceId.ServerId;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 */
public class ItemGoodsAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<FruitBean> findEmps;
    private Context mContext;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    private OnClickContentItemListener onClickContentItemListener;

    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
    }

    public ItemGoodsAdapter(List<FruitBean> findEmps, Context mContext) {
        this.findEmps = findEmps;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return findEmps.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order_set, null);
            holder.comimage = (ImageView) convertView.findViewById(R.id.comimage);
            holder.comname = (TextView) convertView.findViewById(R.id.comname);
            holder.comprice = (TextView) convertView.findViewById(R.id.comprice);
            holder.freight = (TextView) convertView.findViewById(R.id.freight);
            holder.paid = (TextView) convertView.findViewById(R.id.paid);
            holder.goods_jian = (ImageView) convertView.findViewById(R.id.goods_jian);
            holder.goods_add = (ImageView) convertView.findViewById(R.id.goods_add);
            holder.item_num = (TextView) convertView.findViewById(R.id.item_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FruitBean fiveTmp = findEmps.get(position);
        imageLoader.displayImage(ServerId.serveradress + fiveTmp.getProduct_pic(), holder.comimage, SecondApplication.options, animateFirstListener);
        holder.comname.setText(fiveTmp.getProduct_name());
        holder.comprice.setText("￥"+fiveTmp.getPrice_tuangou()==null?"":fiveTmp.getPrice_tuangou());
        if(fiveTmp.getCountNum()==null){
        	fiveTmp.setCountNum("1");
        }
        holder.freight.setText(fiveTmp.getCountNum()==null?"1":fiveTmp.getCountNum());
        holder.item_num.setText(fiveTmp.getCountNum()==null?"1":fiveTmp.getCountNum());
        holder.goods_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickContentItemListener.onClickContentItem(position, 2, null);
            }
        });
        holder.goods_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickContentItemListener.onClickContentItem(position, 3, null);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView comimage;
        TextView comname;
        TextView comprice;
        TextView freight;
        TextView paid;
        ImageView goods_jian;
        ImageView goods_add;
        TextView item_num;
    }

}