package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.TypeBean;
import com.wodm.android.ui.newview.MyLevelActivity;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by ATRSnail on 2016/10/11.
 */

public class LevelAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String, String>> mList;

    public LevelAdapter(Context context, List<Map<String, String>> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size() > 0 ? mList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.items_level, null, false);
            holder.btn = (Button) convertView.findViewById(R.id.action_item);
            holder.textView = (TextView) convertView.findViewById(R.id.experience_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map<String, String> map = mList.get(position);
        holder.btn.setText(map.get("actionItem"));
        switch (position) {
            case 3:
            case 4:
            case 5:
                holder.btn.setBackgroundResource(R.drawable.gonglue_rectangle_second);
                break;
            case 6:
            case 7:
                holder.btn.setBackgroundResource(R.drawable.gonglue_rectangle_three);
                break;

        }
        holder.btn.setText(map.get("actionItem"));
        holder.textView.setText(map.get("experience"));
        if (position == 7) {
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        }
        return convertView;
    }

    static class ViewHolder {
        Button btn;
        TextView textView;
    }
}