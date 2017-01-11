package com.wodm.android.adapter.newadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wodm.R;
import com.wodm.android.bean.NewMainBean;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.home.CarDetailActivity;
import com.wodm.android.ui.newview.DetailActivity;

import java.util.List;

/**
 * Created by songchenyu on 16/11/29.
 */

public class NewMain3Adapter  extends BaseAdapter {
    private Context mContext;
    private List<NewMainBean.ResourcesBean> resourcesBean;
    public NewMain3Adapter(Context context,List<NewMainBean.ResourcesBean> resourcesBean) {
        this.mContext = context;
        this.resourcesBean=resourcesBean;

    }

    @Override
    public int getCount() {
        return resourcesBean.size();
    }

    @Override
    public Object getItem(int position) {
        return resourcesBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holders holders=null;
        final NewMainBean.ResourcesBean bean=resourcesBean.get(position);
        int width= Tools.getScreenWidth((Activity) mContext);
        if (convertView==null){
            holders=new Holders();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.new_main3_adapter,null,false);
            holders.img_angle= (ImageView) convertView.findViewById(R.id.img_angle);
            int hight= (int) ((float)width/750*220);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hight);
            holders.img_angle.setLayoutParams(params);
            holders.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holders.tv_desp= (TextView) convertView.findViewById(R.id.tv_desp);
            holders.ll_new_main3_adapter= (LinearLayout) convertView.findViewById(R.id.ll_new_main3_adapter);
            convertView.setTag(holders);
        }else {
            holders= (Holders) convertView.getTag();
        }
        holders.ll_new_main3_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, bean.getResourceType() == 1 ? AnimDetailActivity.class : CarDetailActivity.class).putExtra("resourceId", bean.getId()));
                mContext.startActivity(new Intent(mContext, DetailActivity.class).putExtra("resourceId", bean.getId()).putExtra("resourceType", bean.getResourceType()));
            }
        });
        Glide.with(mContext).load(bean.getImageUrl()).placeholder(R.mipmap.loading).into(holders.img_angle);
        holders.tv_desp.setText(bean.getDesp());
        holders.tv_name.setText(bean.getName());
        holders.tv_desp.setText(bean.getName());
        return convertView;
    }
    class Holders{
        private ImageView img_angle;
        private TextView tv_name;
        private TextView tv_desp;
        private LinearLayout ll_new_main3_adapter;
    }
}