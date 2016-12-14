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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wodm.R;
import com.wodm.android.adapter.NewMain1Adapter;
import com.wodm.android.bean.NewMainBean;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.home.CarDetailActivity;
import com.wodm.android.view.newview.MyGridView;

import java.util.List;

/**
 * Created by songchenyu on 16/11/29.
 */
public class NewMainAdapter extends BaseAdapter {
    private Context mContext;
    private int style;
    private List<NewMainBean.ResourcesBean> resourcesBean;
    private boolean isRemove=false;
    NewMainBean.ResourcesBean positionbean;
    public NewMainAdapter(Context context,List<NewMainBean.ResourcesBean> resourcesBean,int style) {
        this.mContext = context;
        this.resourcesBean=resourcesBean;
        this.style=style;
    }

    @Override
    public int getCount() {
        return 1;
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
        int width= Tools.getScreenWidth((Activity) mContext);
//        final NewMainBean.ResourcesBean bean=resourcesBean.get(0);
        if (convertView==null){
            view1Holders=new View1Holders();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.new_main1,null,false);
            view1Holders.img_angle= (ImageView) convertView.findViewById(R.id.img_angle);
            view1Holders.ll_shadow= (LinearLayout) convertView.findViewById(R.id.ll_shadow);
            view1Holders.ll_new_main_text= (LinearLayout) convertView.findViewById(R.id.ll_new_main_text);
            view1Holders.ll_new_main_image= (LinearLayout) convertView.findViewById(R.id.ll_new_main_image);
            view1Holders.ll_shadow= (LinearLayout) convertView.findViewById(R.id.ll_shadow);
            view1Holders.tv_look_num= (TextView) convertView.findViewById(R.id.tv_look_num);
            view1Holders.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            view1Holders.tv_update= (TextView) convertView.findViewById(R.id.tv_update);
            view1Holders.new_main_image_no_gv= (MyGridView) convertView.findViewById(R.id.new_main_image_no_gv);
            int hight= (int) ((width-60)*((float)220/690));
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, hight);
            view1Holders.img_angle.setLayoutParams(params);
            view1Holders.ll_shadow.getBackground().setAlpha(140);
            view1Holders.tv_look_num.setTextColor(0x50ffffff);
            convertView.setTag(view1Holders);
        }else {
            view1Holders= (View1Holders) convertView.getTag();
        }
        view1Holders.ll_new_main_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, positionbean.getResourceType() == 1 ? AnimDetailActivity.class : CarDetailActivity.class).putExtra("resourceId", positionbean.getId()));
            }
        });
        if (!isRemove&&resourcesBean.size()>4){
            positionbean=resourcesBean.get(0);
            resourcesBean.remove(0);
            isRemove=true;
        }
        if (positionbean==null){
            positionbean=resourcesBean.get(0);
        }
//        if (style==4){
//            view1Holders.ll_new_main_image.setVisibility(View.GONE);
//            view1Holders.ll_new_main_text.setVisibility(View.GONE);
//            view1Holders.new_main_image_no_gv.setNumColumns(3);
//        }else {
            view1Holders.new_main_image_no_gv.setNumColumns(2);
            Glide.with(mContext).load(positionbean.getImageUrl()).placeholder(R.mipmap.loading).into(view1Holders.img_angle);
            view1Holders.tv_look_num.setText(positionbean.getPlayCount()+"");
            view1Holders.tv_name.setText(positionbean.getName());
            view1Holders.tv_update.setText((positionbean.getType() == 1 ? "更新至" : "全") + positionbean.getChapter() + "集");
//        }


        view1Holders.new_main_image_no_gv.setAdapter(new NewMain1Adapter(mContext,resourcesBean));
        return convertView;
    }
    class View1Holders{
        private ImageView img_angle;
        private LinearLayout ll_shadow,ll_new_main_text,ll_new_main_image;
        private TextView tv_look_num,tv_name,tv_update;
        private MyGridView new_main_image_no_gv;
    }
}
