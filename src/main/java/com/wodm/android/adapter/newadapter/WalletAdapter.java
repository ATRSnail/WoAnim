package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.wodm.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ATRSnail on 2016/10/10.
 */

public class WalletAdapter extends BaseAdapter {


    private List<Map<String, String>> list = new ArrayList<>();
    private Context mContext;

    public WalletAdapter(Context context, List<Map<String, String>> mlist) {
        this.mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.items_grid_wallet, null, false);
            holder.button = (Button) convertView.findViewById(R.id.level_items_wallet);
            holder.textView = (TextView) convertView.findViewById(R.id.rule_items_wallet);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map<String, String> map = list.get(position);
        holder.button.setText(map.get("level"));
        holder.textView.setText(map.get("rule"));
        return convertView;
    }

    static class ViewHolder {
        Button button;
        TextView textView;
    }
}
