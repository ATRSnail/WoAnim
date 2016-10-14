package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wodm.R;

import java.util.List;
import java.util.Map;

/**
 * Created by ATRSnail on 2016/10/13.
 */

public class VipOpenAdapter extends BaseAdapter {
    private Context context;
    List<Map<String, String>> list;

    public VipOpenAdapter(Context mContext, List<Map<String, String>> mlist) {
        this.context = mContext;
        this.list = mlist;
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_vip_open, null, false);
            holder.month = (TextView) convertView.findViewById(R.id.month_vipopen);
            holder.meng_mongey = (TextView) convertView.findViewById(R.id.meng_money_vipopen);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map<String, String> map = list.get(position);
        holder.month.setText(map.get("month"));
        holder.meng_mongey.setText(map.get("meng_money"));
        return convertView;
    }

    static class ViewHolder {
        TextView month;
        TextView meng_mongey;
    }
}
