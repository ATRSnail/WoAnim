package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wodm.R;

/**
 * Created by songchenyu on 16/10/9.
 */

public class MoneyPackageAdapter extends BaseAdapter {
    private Context mContext;
    public MoneyPackageAdapter(Context context){
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return 4;
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
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_package,null,false);
        return convertView;
    }
}
