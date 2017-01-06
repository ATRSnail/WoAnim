package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wodm.R;
import com.wodm.android.bean.AtWoBean;
import com.wodm.android.bean.TuiJianBean;
import com.wodm.android.ui.newview.DetailActivity;
import com.wodm.android.utils.MessageUtils;
import com.wodm.android.view.biaoqing.FaceConversionUtil;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2017/1/3.
 */

public class TuiJianAdapter extends BaseAdapter  {
    List<TuiJianBean.DataBean> list=new ArrayList<>();
    Context mContext;
    public TuiJianAdapter(Context context, List<TuiJianBean.DataBean> dataBeen2) {
        this.mContext = context;
        this.list = dataBeen2;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tuijian_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.tuijian_name);
            holder.pho = (ImageButton) convertView.findViewById(R.id.tuijian_pho);
            holder.info = (TextView) convertView.findViewById(R.id.tuijian_info);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        final TuiJianBean.DataBean.ResourceBean resourceBean = list.get(position).getResource();
        Glide.with(mContext).load(resourceBean.getShowImage()).placeholder(R.mipmap.loading).into(holder.pho);
        holder.name.setText(resourceBean.getName());
        String str="更新至"+resourceBean.getChapter()+"集";
        if (resourceBean.getType()==1){
            if ( resourceBean.getResourceType()==2){
                str="更新至"+resourceBean.getChapter()+"话";
            }
        }else   if (resourceBean.getType()==2){
            str=resourceBean.getChapter()+"集";
            if ( resourceBean.getResourceType()==2){
                str=resourceBean.getChapter()+"话";
            }
        }
        holder.info.setText(str);

        holder.pho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext, DetailActivity.class);
                intent.putExtra("resourceId", resourceBean.getId());
                intent.putExtra("resourceType", resourceBean.getResourceType());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }



    public void setList(List<TuiJianBean.DataBean> mlist) {
        this.list.clear();
        this.list.addAll(mlist);
        notifyDataSetChanged();
    }


    static class MyHolder {
        TextView name;
        TextView info;
        ImageButton pho;
    }
}

