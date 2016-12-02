package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.ui.widget.CircularImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2016/12/1.
 */

public class AtWoAdapter extends BaseAdapter implements View.OnClickListener {
    List<String> list;
    Context mContext;
    boolean flag = false;
    AtyTopLayout set_topbar;
    int num;
    public AtyTopLayout getSet_topbar() {
        return set_topbar;
    }

    public void setSet_topbar(AtyTopLayout set_topbar) {
        this.set_topbar = set_topbar;
    }

    public AtWoAdapter(Context context, boolean mflag) {
        this.mContext = context;
        this.flag = mflag;
        list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add("通知" + i);
        }
        notifyDataSetChanged();
    }

    public AtWoAdapter() {
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyHolder holder = null;
        if (convertView == null) {
            holder = new MyHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.atwoitem, null);
            holder.pho = (CircularImage) convertView.findViewById(R.id.atwo_new);
            holder.name = (TextView) convertView.findViewById(R.id.name_atwo);
            holder.reply = (ImageView) convertView.findViewById(R.id.reply_atwo);
            holder.atwo_name = (TextView) convertView.findViewById(R.id.atwo_name);
            holder.time = (TextView) convertView.findViewById(R.id.time_atwo);
            holder.info_atwo = (TextView) convertView.findViewById(R.id.info_atwo);
            holder.choice_atwo = (ImageView) convertView.findViewById(R.id.choice_atwo);
            holder.laizi_atwo = (TextView) convertView.findViewById(R.id.laizi_atwo);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
//        holder.pho.setImageResource();
        holder.name.setText("会飞的鱼");
        if (flag) {
            holder.reply.setVisibility(View.GONE);
            holder.choice_atwo.setVisibility(View.VISIBLE);
        }
        final MyHolder finalHolder = holder;
        final boolean[] click = {true};

        holder.choice_atwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click[0]) {
                    finalHolder.choice_atwo.setImageResource(R.mipmap.up_yes);
                    num++;
                } else {
                    finalHolder.choice_atwo.setImageResource(R.mipmap.up_no);
                    num--;
                }
                click[0] = !click[0];
                if (num> 0) {
                    set_topbar.setTvRight("删除");
                }else if (num==0){
                    set_topbar.setTvRight("完成");
                }
            }
        });
        return convertView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


    static class MyHolder {
        CircularImage pho;
        TextView name;
        ImageView reply;
        TextView atwo_name;
        TextView time;
        TextView laizi_atwo;
        TextView info_atwo;
        ImageView choice_atwo;

    }
}
