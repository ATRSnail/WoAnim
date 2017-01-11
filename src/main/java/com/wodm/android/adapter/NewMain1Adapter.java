package com.wodm.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wodm.R;
import com.wodm.android.bean.NewMainBean;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.home.CarDetailActivity;
import com.wodm.android.ui.newview.DetailActivity;
import com.wodm.android.view.newview.MyGridView;

import java.util.List;

import static com.wodm.R.id.ll_new_main1_adapter;

/**
 * Created by songchenyu on 16/11/30.
 */

public class NewMain1Adapter extends BaseAdapter {
    private Context mContext;
    private List<NewMainBean.ResourcesBean> resourcesBean;
    private int type=2;
    public NewMain1Adapter(Context context,List<NewMainBean.ResourcesBean> resourcesBean,int type) {
        this.mContext = context;
        this.resourcesBean=resourcesBean;
        this.type=type;
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
        View1Holders view1Holders=null;
        final NewMainBean.ResourcesBean bean=resourcesBean.get(position);
        int width= Tools.getScreenWidth((Activity) mContext);
        if (convertView==null){
            view1Holders=new View1Holders();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.new_main1_adapter,null,false);
            view1Holders.img_angle= (ImageView) convertView.findViewById(R.id.img_angle);
            view1Holders.ll_shadow= (LinearLayout) convertView.findViewById(R.id.ll_shadow);
            view1Holders.ll_new_main1_adapter= (LinearLayout) convertView.findViewById(ll_new_main1_adapter);
            view1Holders.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            view1Holders.tv_look_num= (TextView) convertView.findViewById(R.id.tv_look_num);
            view1Holders.tv_update= (TextView) convertView.findViewById(R.id.tv_update);
//            int img_hight=LinearLayout.LayoutParams.MATCH_PARENT;
//            int img_width=LinearLayout.LayoutParams.MATCH_PARENT;
            int hight=ViewGroup.LayoutParams.MATCH_PARENT;
            if (type==3){
                 hight= (int) (((width-60-32)/type)*((float)280/220));
//                img_hight= (int) (width/750*280);
//                img_width= (int) (width/750*228);
            }else {
                view1Holders.ll_new_main1_adapter.setPadding(0,48,0,0);
                 hight= (int) (((width-60-32)/type)*((float)220/340));
            }
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, hight);
            view1Holders.img_angle.setLayoutParams(params);
            view1Holders.ll_shadow.getBackground().setAlpha(140);
            view1Holders.tv_look_num.setTextColor(0x50ffffff);
            convertView.setTag(view1Holders);
        }else {
            view1Holders= (View1Holders) convertView.getTag();
        }
        view1Holders.ll_new_main1_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, bean.getResourceType() == 1 ? AnimDetailActivity.class : CarDetailActivity.class).putExtra("resourceId", bean.getId()));
                mContext.startActivity(new Intent(mContext, DetailActivity.class).putExtra("resourceId", bean.getId()).putExtra("resourceType", bean.getResourceType()));
            }
        });
        Glide.with(mContext).load(bean.getImageUrl()).placeholder(R.mipmap.loading).into(view1Holders.img_angle);
        view1Holders.tv_look_num.setText(bean.getPlayCount()+"");
        view1Holders.tv_name.setText(bean.getName());
        view1Holders.tv_update.setText((bean.getType() == 1 ? "更新至" : "全") + bean.getChapter() + "集");
        return convertView;
    }
    class View1Holders{
        private ImageView img_angle;
        private LinearLayout ll_shadow,ll_new_main1_adapter;
        private TextView tv_look_num,tv_name,tv_update;
        private MyGridView new_main_image_no_gv;
    }
}
