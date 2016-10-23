package com.wodm.android.adapter.newadapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    int flag = 0;

    public MallAdapter(Context mcontext, List<Map<String, Object>> mlist, int i) {
        flag = i;
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
            holder.xianshi_mall = (ImageView) convertView.findViewById(R.id.xianshi_mall);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout_mall);
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


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.layout.getLayoutParams();
//        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        holder.layout.measure(w, h);
        int width = params.width;
        params.height = width * 146 / 160;
        holder.layout.setLayoutParams(params);
        convertView.setOnClickListener(this);

        if (flag == 0 && position == 0) {
            holder.xianshi_mall.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        new AlertDialog.Builder(context)
                .setMessage("您的积分不足")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    static class ViewHolder {
        TextView name;
        TextView score;
        CircularImage icon;
        ImageView xianshi_mall;
        LinearLayout layout;
    }

}
