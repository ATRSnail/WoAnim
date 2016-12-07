package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.AtWoBean;
import com.wodm.android.bean.SysMessBean;
import com.wodm.android.ui.newview.SendMsgActivity;
import com.wodm.android.utils.MessageUtils;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2016/12/1.
 */

public class AtWoAdapter extends BaseAdapter implements View.OnClickListener {
    List<AtWoBean.DataBean> list;
    Context mContext;
    MessageUtils utils;
    boolean flag = false;//显示删除选择图标的判断标志
    boolean delete=false;//判断是否全部(批量删除)的标志
    AtyTopLayout set_topbar;
    int num=0;
    List<Long> ids=new ArrayList<>();//消息ID的列表


    public void setSet_topbar(AtyTopLayout set_topbar) {
        this.set_topbar = set_topbar;
    }

    public void setUtils(MessageUtils utils) {
        this.utils = utils;
        this.list =  utils.getReplyMessageList();
    }

    public List<Long> getIds() {
        return ids;
    }

    public boolean getDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    public void setIds(List<Long> ids) {
        this.ids = ids;
    }


    public AtWoAdapter(Context context, boolean mflag) {
        this.mContext = context;
        this.flag = mflag;
    }

    public AtWoAdapter() {
    }

    @Override
    public int getCount() {
        return list.size()>0?list.size():0;
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
            holder.cicle_new = (CircularImage) convertView.findViewById(R.id.cicle_new);
            holder.laizi_atwo = (TextView) convertView.findViewById(R.id.laizi_atwo);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
//        holder.laizi_atwo.setVisibility(View.GONE);//@我去掉来自哪个资讯
        if (flag) {
            holder.reply.setVisibility(View.GONE);
            holder.choice_atwo.setVisibility(View.VISIBLE);
        }
        final AtWoBean.DataBean dataBean = list.get(position);
        final MyHolder finalHolder = holder;
        final boolean[] click = {true};
        holder.choice_atwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id =dataBean.getSendId();
                if (click[0]) {
                    finalHolder.choice_atwo.setImageResource(R.mipmap.up_yes);
                    num++;
                    ids.add(id);
                } else {
                    finalHolder.choice_atwo.setImageResource(R.mipmap.up_no);
                    num--;
                    ids.remove((Object)id);
                }
                click[0] = !click[0];
                if (num> 0) {
                    setIds(ids);
                    if (ids.size()==list.size()){
                        setDelete(true);
                    }else {
                        setDelete(false);
                    }
                    set_topbar.setTvRight("删除");
                }else if (num==0){
                    set_topbar.setTvRight("完成");
                }
            }
        });
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SendMsgActivity.class));
            }
        });
        new AsyncImageLoader(mContext,R.mipmap.moren_header,R.mipmap.moren_header).display(holder.pho,dataBean.getSendPortrait());
//        holder.cicle_new.setVisibility(View.VISIBLE);
        holder.name.setText(dataBean.getSendNickName());
        holder.time.setText(dataBean.getTimes());
        holder.atwo_name.setText(dataBean.getReceiveNickName());
        holder.info_atwo.setText("："+dataBean.getContent());
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
        CircularImage cicle_new;

    }
}
