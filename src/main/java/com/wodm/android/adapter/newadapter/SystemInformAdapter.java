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

public class SystemInformAdapter extends BaseAdapter implements View.OnClickListener {
    List<String> list;
    Context mContext;
    int phos[] = new int[]{R.mipmap.system_inform, R.mipmap.goods_inform};
    String[] names = new String[]{"系统通知", "物流通知"};
    boolean flag = false;
    AtyTopLayout set_topbar;
    int num=0;
    public AtyTopLayout getSet_topbar() {
        return set_topbar;
    }

    public void setSet_topbar(AtyTopLayout set_topbar) {
        this.set_topbar = set_topbar;
    }

    public SystemInformAdapter(Context context, boolean mflag) {
        this.mContext = context;
        this.flag = mflag;
        list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add("通知" + i);
        }

        notifyDataSetChanged();
    }


    public SystemInformAdapter() {

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.messageitem, null);
            holder.pho = (CircularImage) convertView.findViewById(R.id.message_new);
            holder.name = (TextView) convertView.findViewById(R.id.name_message);
            holder.rightname = (TextView) convertView.findViewById(R.id.right_name_message);
            holder.centername = (TextView) convertView.findViewById(R.id.center_name_message);
            holder.info = (TextView) convertView.findViewById(R.id.grade_attention);
            holder.time = (TextView) convertView.findViewById(R.id.time_message);
            holder.watch_message = (TextView) convertView.findViewById(R.id.watch_message);
            holder.line_inform_bottom = (View) convertView.findViewById(R.id.line_inform_bottom);
            holder.line_inform_center = (View) convertView.findViewById(R.id.line_inform_center);
            holder.choice_message = (ImageView) convertView.findViewById(R.id.choice_message);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        final String name = list.get(position);
        holder.pho.setImageResource(phos[position]);
        holder.line_inform_bottom.setVisibility(View.GONE);
        holder.line_inform_center.setVisibility(View.GONE);
        holder.name.setText(names[position]);
        if (flag) {
            holder.time.setVisibility(View.GONE);
            holder.watch_message.setVisibility(View.GONE);
            holder.choice_message.setVisibility(View.VISIBLE);
        }
        final MyHolder finalHolder = holder;
        final boolean[] click = {true};
        holder.choice_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click[0]) {
                    finalHolder.choice_message.setImageResource(R.mipmap.up_yes);
                    num++;
                } else {
                    finalHolder.choice_message.setImageResource(R.mipmap.up_no);
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
        TextView centername;
        TextView rightname;
        TextView info;
        TextView time;
        TextView watch_message;
        View line_inform_center;
        View line_inform_bottom;
        ImageView choice_message;

    }
}
