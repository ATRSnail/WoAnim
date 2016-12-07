package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.DianZanBean;
import com.wodm.android.utils.MessageUtils;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2016/12/7.
 */

public class CommentAdapter2 extends BaseAdapter{
    //消息中心的评论的适配

//    List<CommentBean2.DataBean> list=new ArrayList<>();
    List<String> list=new ArrayList<>();
    Context mContext;
    MessageUtils utils;
    boolean flag = false;//显示删除选择图标的判断标志
    AtyTopLayout set_topbar;
    int num=0;
    public static  boolean delete = false;//判断是否全部(批量删除)的标志
    public static  List<Long> ids=new ArrayList<>();//消息ID的列表

    public void setSet_topbar(AtyTopLayout set_topbar) {
        this.set_topbar = set_topbar;
    }

    public CommentAdapter2(Context context, boolean mflag) {
        this.mContext = context;
        this.flag = mflag;
        for (int i = 0; i <2 ; i++) {
            list.add("会飞的鱼"+i);
        }
    }

//    public void setList(List<CommentBean2.DataBean> mlist) {
//        this.list.clear();
//        this.list.addAll(mlist);
//        notifyDataSetChanged();
//    }

    public void setUtils(MessageUtils utils) {
        this.utils = utils;
    }

    public CommentAdapter2() {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, null);
            holder.pho = (CircularImage) convertView.findViewById(R.id.atwo_new);
            holder.name = (TextView) convertView.findViewById(R.id.name_atwo);
            holder.watch = (ImageView) convertView.findViewById(R.id.watch_atwo);
            holder.time = (TextView) convertView.findViewById(R.id.time_atwo);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            holder.choice_atwo = (ImageView) convertView.findViewById(R.id.choice_atwo);
            holder.laizi_comment = (TextView) convertView.findViewById(R.id.laizi_comment);
            holder.info_atwo = (TextView) convertView.findViewById(R.id.info_atwo);
            holder.cicle_new = (CircularImage) convertView.findViewById(R.id.cicle_new);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
       String name =list.get(position);

        if (flag) {
            holder.watch.setVisibility(View.GONE);
            holder.choice_atwo.setVisibility(View.VISIBLE);
        }else {
            holder.watch.setVisibility(View.VISIBLE);
            holder.choice_atwo.setVisibility(View.GONE);
        }

        final MyHolder finalHolder = holder;
        final boolean[] click = {true};
        holder.choice_atwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id =0;
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

//        new AsyncImageLoader(mContext,R.mipmap.moren_header,R.mipmap.moren_header).display(holder.pho,dataBean.getSendPortrait());
//        holder.cicle_new.setVisibility(View.VISIBLE);
        holder.name.setText(name);
        holder.time.setText("2016-11-22");
        holder.tv_comment.setText("这个消息到底有没有？？？？");
        holder.laizi_comment.setText("来自【"+"资讯"+"】");
        holder.info_atwo.setText("："+"上海CP19即将开始门票预售!");
        return convertView;
    }





    static class MyHolder {
        CircularImage pho;
        TextView name;
        ImageView watch;
        TextView time;
        TextView tv_comment;
        TextView info_atwo;
        TextView laizi_comment;
        ImageView choice_atwo;
        CircularImage cicle_new;

    }
}
