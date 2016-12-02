package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.ui.newview.ATWoActivity;
import com.wodm.android.ui.newview.CommentActivity;
import com.wodm.android.ui.newview.DianZanActivity;
import com.wodm.android.ui.newview.MessageCenterActivity;
import com.wodm.android.ui.newview.SystemInformActivity;

import org.eteclab.ui.widget.CircularImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2016/12/1.
 */

public class MessageCenterAdapter extends BaseAdapter {
    List<String> list;
    Context mContext;
    int phos[] = new int[]{R.mipmap.system_inform, R.mipmap.comment, R.mipmap.atwo, R.mipmap.officalpush,
            R.mipmap.dianzan};
    String[] names = new String[]{"系统通知", "新的评论", "有人", "官方推送", "有人赞了你"};

    public MessageCenterAdapter(MessageCenterActivity messageCenterActivity) {
        this.mContext = messageCenterActivity;
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("通知" + i);
        }
        notifyDataSetChanged();
    }

    public MessageCenterAdapter() {
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
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        final String name = list.get(position);
        holder.pho.setImageResource(phos[position]);
        if (position == 2) {
            holder.rightname.setVisibility(View.VISIBLE);
            holder.rightname.setText("了你");
            holder.centername.setVisibility(View.VISIBLE);
            holder.centername.setText("@");
        }
        holder.name.setText(names[position]);
        holder.watch_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(mContext, SystemInformActivity.class);
                        break;
                    case 1:
                        intent.setClass(mContext, CommentActivity.class);
                        break;
                    case 2:
                        intent.setClass(mContext, ATWoActivity.class);
                        break;
//                    case 3:
//
//                        break;
                    case 4:
                        intent.setClass(mContext, DianZanActivity.class);
                        break;
                }
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    static class MyHolder {
        CircularImage pho;
        TextView name;
        TextView centername;
        TextView rightname;
        TextView info;
        TextView time;
        TextView watch_message;

    }
}
