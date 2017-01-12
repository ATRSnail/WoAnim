package com.wodm.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.ChapterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2016/5/5.
 */
public class SeriesAdapter extends BaseAdapter {

    private Context mContext;
    private List<ChapterBean> data = new ArrayList();
    private Boolean isShowAll = true;
    private int num = 16;


    public void setShowAll() {
        isShowAll = false;
        notifyDataSetChanged();
    }

    public SeriesAdapter(Context context, List<ChapterBean> data, int num) {
        mContext = context;
        this.data = new ArrayList();
        this.data.addAll(data);
        this.num = num << 1;
    }

    public void setData(List<ChapterBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<ChapterBean> getData() {
        return data;
    }

    @Override
    public int getCount() {
        if (isShowAll) {
            if (data.size() > this.num) {
                return this.num;
            } else {
                isShowAll=false;
                return data.size();
            }
        } else {
            return data.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolders holders;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_series, parent, false);
            holders = new ViewHolders();
            holders.button = (TextView) convertView.findViewById(R.id.button);
            holders.img = (ImageView) convertView.findViewById(R.id.img);
            holders.layout = convertView.findViewById(R.id.layout);
            convertView.setTag(holders);
        }
        holders = (ViewHolders) convertView.getTag();

        holders.button.setText((position + 1) + "");

        ChapterBean bean = (ChapterBean) getItem(position);
        if (position + 1 == getCount() && isShowAll) {
            holders.button.setText("更多");
            holders.img.setVisibility(View.INVISIBLE);
        }

        if (bean.IsCheck() == 0) {
            holders.button.setTextColor(Color.BLACK);
            holders.layout.setBackgroundResource(R.drawable.series_anim);
        } else if (bean.IsCheck() == 1) {
            holders.img.setImageResource(R.mipmap.cache_loading);
        } else if (bean.IsCheck() == 2) {
            holders.img.setImageResource(R.mipmap.cache_finish);
        } else if (bean.IsCheck() == 3) {
            holders.button.setTextColor(Color.WHITE);
            holders.layout.setBackgroundResource(R.drawable.series_anim_select);
        }
        return convertView;
    }


    class ViewHolders {
        TextView button;
        ImageView img;
        View layout;
    }
}
