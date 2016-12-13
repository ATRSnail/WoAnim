package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.XiaoXiBean;
import com.wodm.android.ui.newview.ATWoActivity;
import com.wodm.android.ui.newview.CommentActivity;
import com.wodm.android.ui.newview.DIanZanActivity;
import com.wodm.android.ui.newview.MessageCenterActivity;
import com.wodm.android.ui.newview.SystemInformActivity;

import java.util.List;

/**
 * Created by ATRSnail on 2016/12/1.
 */

public class MessageCenterAdapter extends BaseAdapter {
    List<XiaoXiBean.DataBean> list;
    Context mContext;
    int phos[] = new int[]{R.mipmap.system_inform, R.mipmap.comment, R.mipmap.atwo, R.mipmap.officalpush,
            R.mipmap.dianzan};
    int phos_new[] = new int[]{R.mipmap.sysinfo_new, R.mipmap.comment_new, R.mipmap.atwo_new, R.mipmap.offical_new,
            R.mipmap.dianzan_new};
    String[] names = new String[]{"系统通知", "新的评论", "有人", "官方推送", "有人赞了你"};

    public MessageCenterAdapter(MessageCenterActivity messageCenterActivity) {
        this.mContext = messageCenterActivity;
    }
    public void setList(List<XiaoXiBean.DataBean> mlist) {
        this.list = mlist;
    }

    public List<XiaoXiBean.DataBean> getList() {
        return list;
    }

    public MessageCenterAdapter() {
    }


    @Override
    public int getCount() {
        return getList().size();
    }

    @Override
    public Object getItem(int position) {
        return getList().get(position);
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
            holder.pho = (ImageView) convertView.findViewById(R.id.message_new);
            holder.name = (TextView) convertView.findViewById(R.id.name_message);
            holder.rightname = (TextView) convertView.findViewById(R.id.right_name_message);
            holder.centername = (TextView) convertView.findViewById(R.id.center_name_message);
            holder.info = (TextView) convertView.findViewById(R.id.info_message);
            holder.time = (TextView) convertView.findViewById(R.id.time_message);
            holder.watch_message = (TextView) convertView.findViewById(R.id.watch_message);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
       XiaoXiBean.DataBean dataBean = getList().get(position);
        final int type=dataBean.getType()-1;
        holder.name.setText(names[type]);
        if(dataBean.getStatus()==1){
            holder.pho.setImageResource(phos_new[type]);
        }else {
            holder.pho.setImageResource(phos[type]);
        }
        if (type == 2) {
            holder.rightname.setVisibility(View.VISIBLE);
            holder.rightname.setText("了你");
            holder.centername.setVisibility(View.VISIBLE);
            holder.centername.setText("@");
        }else {
            holder.centername.setVisibility(View.GONE);
            holder.rightname.setVisibility(View.GONE);
        }
        holder.time.setText(dataBean.getTimes());
        String content=dataBean.getContent().toString();
        holder.info.setText(content);
        holder.watch_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent();
                switch (type) {
                    case 0:
                        intent.setClass(mContext, SystemInformActivity.class);
                        break;
                    case 1:
                        intent.setClass(mContext, CommentActivity.class);
                        break;
                    case 2:
                        intent.setClass(mContext, ATWoActivity.class);
                        break;
                    case 3:
                          return;

                    case 4:
                        intent.setClass(mContext, DIanZanActivity.class);
                        break;
                }
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }




    static class MyHolder {
        ImageView pho;
        TextView name;
        TextView centername;
        TextView rightname;
        TextView info;
        TextView time;
        TextView watch_message;

    }
}
