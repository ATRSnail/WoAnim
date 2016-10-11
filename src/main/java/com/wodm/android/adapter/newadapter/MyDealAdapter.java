package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.view.newview.MyGridView;

/**
 * Created by songchenyu on 16/10/11.
 */

public class MyDealAdapter extends BaseAdapter {
    private Context mContext;
    private String[] personArray = {"漫画", "动画", "日常", "互动"};
    private String[] messageArray = {"宅腐", "达人", "热血", "马猴"};
    private int[] top_colorArray = {R.color.color_89c997, R.color.color_eb6877, R.color.color_facd89, R.color.color_88abda};

    public MyDealAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return personArray.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyDealAdapter.Holder holder = null;
        final int myPosition = position;
        if (convertView == null) {
            holder = new MyDealAdapter.Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.deal_adapter, null, false);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_title);
            holder.gridView = (MyGridView) convertView.findViewById(R.id.gv_mygirdview);
            holder.adapter_view = convertView.findViewById(R.id.adapter_view);
            convertView.setTag(holder);
        } else {
            holder = (MyDealAdapter.Holder) convertView.getTag();
        }
        holder.textView.setText(personArray[position]);
        holder.adapter_view.setBackgroundColor(top_colorArray[position]);
        MyDealAdapter.Myadapter myadapter = new MyDealAdapter.Myadapter(messageArray, top_colorArray);
        holder.gridView.setAdapter(myadapter);

        return convertView;
    }

    private class Holder {
        TextView textView;
        GridView gridView;
        View adapter_view;
    }

    class Myadapter extends BaseAdapter {
        private String mArray[];
        private int miconArray[];

        private Myadapter(String array[], int[] iconArray) {
            this.mArray = array;
            this.miconArray = iconArray;
        }

        @Override
        public int getCount() {
            return mArray.length;
        }

        @Override
        public Object getItem(int position) {
            return mArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyDealAdapter.GvHolder gvHolder = null;
            if (convertView == null) {
                gvHolder = new MyDealAdapter.GvHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_medal, null, false);
                gvHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                gvHolder.img_icon = (ImageView) convertView.findViewById(R.id.gv_img);
                gvHolder.ll_adapter = (LinearLayout) convertView.findViewById(R.id.ll_adapter);
                convertView.setTag(gvHolder);
            } else {
                gvHolder = (MyDealAdapter.GvHolder) convertView.getTag();
            }
            gvHolder.tv_name.setText(mArray[position]);

            gvHolder.img_icon.setBackgroundResource(miconArray[position]);
            return convertView;
        }

    }

    class GvHolder {
        TextView tv_name;
        ImageView img_icon;
        LinearLayout ll_adapter;
    }
}