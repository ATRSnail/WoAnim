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
import com.wodm.android.bean.SysMessBean;
import com.wodm.android.ui.newview.GuaJianHeaderImageActivity;
import com.wodm.android.ui.newview.NewVipActivity;
import com.wodm.android.utils.MessageUtils;
import com.wodm.android.view.newview.AtyTopLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2016/12/1.
 */

public class SystemInformAdapter extends BaseAdapter implements View.OnClickListener {
    List<SysMessBean.DataBean> list=new ArrayList<>();
    Context mContext;
    MessageUtils utils;
    int phos[] = new int[]{R.mipmap.system_inform, R.mipmap.goods_inform};
    int phos_new[] = new int[]{R.mipmap.sysinfo_new, R.mipmap.goods_new};
    String[] names = new String[]{"系统通知", "物流通知"};
     boolean flag = false;//显示删除选择图标的判断标志
//    boolean removeAll=false;//判断是否全部删除的标志
    AtyTopLayout set_topbar;
    int num=0;
    public static  boolean delete = false;//判断是否全部(批量删除)的标志
   public static  List<Long> ids=new ArrayList<>();//消息ID的列表


    public void setUtils(MessageUtils utils) {
        this.utils = utils;
    }

    public void setSet_topbar(AtyTopLayout set_topbar) {
        this.set_topbar = set_topbar;
    }

    public SystemInformAdapter(Context context, boolean mflag) {
        this.mContext = context;
        this.flag = mflag;
    }

    public SystemInformAdapter() {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.messageitem, null);
            holder.pho = (ImageView) convertView.findViewById(R.id.message_new);
            holder.name = (TextView) convertView.findViewById(R.id.name_message);
            holder.info = (TextView) convertView.findViewById(R.id.info_message);
            holder.time = (TextView) convertView.findViewById(R.id.time_message);
            holder.watch_message = (TextView) convertView.findViewById(R.id.watch_message);
            holder.choice_message = (ImageView) convertView.findViewById(R.id.choice_message);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        final SysMessBean.DataBean dataBean=  list.get(position);
        if (flag) {
            holder.time.setVisibility(View.GONE);
            holder.watch_message.setVisibility(View.GONE);
            holder.choice_message.setVisibility(View.VISIBLE);
        }
        final MyHolder finalHolder = holder;
        final boolean[] click = {true};//设置删除图标的标识
//        if (removeAll){
//            holder.choice_message.setImageResource(R.mipmap.up_yes);
//            click[0] = false;
//        }else {
//            click[0] = true;
//        }
        holder.choice_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id =dataBean.getId();
                if (click[0]) {
                    finalHolder.choice_message.setImageResource(R.mipmap.up_yes);
                    num++;
                    ids.add(id);
                } else {
                    finalHolder.choice_message.setImageResource(R.mipmap.up_no);
                    num--;
                    ids.remove((Object)id);
                }
                click[0] = !click[0];

                if (num> 0) {
                    set_topbar.setTvRight("删除");
                    if (ids.size()==list.size()){
                        delete=true;
                    }else {
                        delete=false;
                    }
                }else if (num==0){
                    set_topbar.setTvRight("完成");
                }
            }
        });


        if (dataBean.getStatus()==1){
            //现在只有系统通知
//            if(dataBean.getContentType()==6)
//            { holder.pho.setImageResource(phos_new[1]);}
//            else {
                holder.pho.setImageResource(phos_new[0]);
//            }
        }else {
//            if(dataBean.getContentType()==6)
//            { holder.pho.setImageResource(phos[1]);}
//            else {
                holder.pho.setImageResource(phos[0]);
//            }
        }
        if(dataBean.getContentType()==6)
        {   holder.name.setText(names[1]);}
        else {
            holder.name.setText(names[0]);
        }
        holder.time.setText(dataBean.getTimes());
        holder.info.setText(dataBean.getContent().toString());
        holder.watch_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                if (dataBean.getContentType()==4){
                    intent.setClass(mContext,GuaJianHeaderImageActivity.class);
                    intent.putExtra("index",5);
                    mContext.startActivity(intent);
                    utils.readMessage(dataBean.getId());
                }else   if (dataBean.getContentType()==5){
                    intent.setClass(mContext,GuaJianHeaderImageActivity.class);
                    intent.putExtra("index",4);
                    mContext.startActivity(intent);
                    utils.readMessage(dataBean.getId());
                }else   if (dataBean.getContentType()==6){
                    intent.setClass(mContext,NewVipActivity.class);
                    mContext.startActivity(intent);
                    utils.readMessage(dataBean.getId());
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

    public void setList(List<SysMessBean.DataBean> mlist) {
        this.list.clear();
        this.list.addAll(mlist);
        notifyDataSetChanged();
    }


//    public void setRemoveAll(boolean mremoveAll) {
//        this.removeAll = mremoveAll;
//    }


    static class MyHolder {
        ImageView pho;
        TextView name;
        TextView info;
        TextView time;
        TextView watch_message;
        ImageView choice_message;

    }
}
