package com.wodm.android.adapter.newadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wodm.R;
import com.wodm.android.bean.NewMainBean;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.home.CarDetailActivity;
import com.wodm.android.view.newview.RoundAngleImageView;

import java.util.List;

/**
 * Created by songchenyu on 16/11/30.
 */

public class NewMainDetailsLVAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewMainBean.ResourcesBean> resourcesBean;
    public NewMainDetailsLVAdapter(Context context,List<NewMainBean.ResourcesBean> resourcesBean) {
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
        Holder holder=null;
        final NewMainBean.ResourcesBean bean=resourcesBean.get(position);
        if (convertView==null){
            int width= Tools.getScreenWidth((Activity) mContext);
            holder=new Holder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.everyday_free,null,false);
            holder.ll_every_free= (LinearLayout) convertView.findViewById(R.id.ll_every_free);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_desp= (TextView) convertView.findViewById(R.id.tv_desp);
            holder.img_angle= (RoundAngleImageView) convertView.findViewById(R.id.img_angle);
            holder.btn_read_now= (Button) convertView.findViewById(R.id.btn_read_now);
            int hight= (int) (width*((float)180/690));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hight);
            params.setMargins(30,0,30,30);
            holder.ll_every_free.setLayoutParams(params);
            LinearLayout.LayoutParams img_params=new LinearLayout.LayoutParams(hight*2, LinearLayout.LayoutParams.MATCH_PARENT);
            holder.img_angle.setLayoutParams(img_params);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        holder.btn_read_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, bean.getResourceType() == 1 ? AnimDetailActivity.class : CarDetailActivity.class).putExtra("resourceId", bean.getId()));
            }
        });
        Glide.with(mContext).load(bean.getImageUrl()).asBitmap().placeholder(R.mipmap.loading).into(holder.img_angle);
        holder.tv_name.setText(bean.getName());
        holder.tv_desp.setText(bean.getDesp());
        return convertView;
    }
    class Holder{
        LinearLayout ll_every_free;
        RoundAngleImageView img_angle;
        TextView tv_name,tv_desp;
        Button btn_read_now;
    }
}
