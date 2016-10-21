package com.wodm.android.adapter.newadapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wodm.R;

import org.eteclab.ui.widget.CircularImage;

import java.util.List;
import java.util.Map;

/**
 * Created by ATRSnail on 2016/10/20.
 */

public class MallAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    List<Map<String, Object>> list;


    public MallAdapter(Context mcontext, List<Map<String, Object>> mlist) {
        this.context = mcontext;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_mall, null, false);
            holder.name = (TextView) convertView.findViewById(R.id.name_item_mall);
            holder.score = (TextView) convertView.findViewById(R.id.score_item_mall);
            holder.icon = (CircularImage) convertView.findViewById(R.id.icon_item_mall);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map<String, Object> map = list.get(position);

        String name = (String) map.get("name");
        String score = (String) map.get("score");
        holder.name.setText(name);
        holder.score.setText(score);
        holder.icon.setImageResource((Integer) map.get("icon"));
        convertView.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        new AlertDialog.Builder(context)
                .setTitle("用户您好：")
                .setMessage("您的积分不足！")
                .create().show();
    }

    static class ViewHolder {
        TextView name;
        TextView score;
        CircularImage icon;
    }

}
