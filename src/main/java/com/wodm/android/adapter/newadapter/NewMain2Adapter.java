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
import com.wodm.android.bean.NewMainBean;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.home.CarDetailActivity;
import com.wodm.android.view.newview.RoundAngleImageView;

import java.util.List;

/**
 * Created by songchenyu on 16/11/30.
 */

public class NewMain2Adapter extends BaseAdapter {
    private Context mContext;
    private int width;
    private int hight;
    private int gridWidth;
    private List<NewMainBean.ResourcesBean> resourcesBean;

    public NewMain2Adapter(Context context, List<NewMainBean.ResourcesBean> resourcesBean) {
        this.mContext = context;
        this.resourcesBean = resourcesBean;
        width = Tools.getScreenWidth((Activity) mContext);
        hight = (int) ((width - Tools.dp2px(mContext, 60)) * ((float) 220 / 690));
        gridWidth = (int) (width * ((float) 2 / 5));
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NewMainBean.ResourcesBean bean = resourcesBean.get(0);
        View2Holders view2Holders = null;
        if (convertView == null) {
            view2Holders = new View2Holders();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.new_main2, null, false);
            view2Holders.img_angle = (ImageView) convertView.findViewById(R.id.img_angle);
            view2Holders.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            view2Holders.tv_look_num = (TextView) convertView.findViewById(R.id.tv_look_num);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, hight);
            view2Holders.img_angle.setLayoutParams(params);
            view2Holders.ll_add_image = (LinearLayout) convertView.findViewById(R.id.ll_add_image);
            convertView.setTag(view2Holders);
        } else {
            view2Holders = (View2Holders) convertView.getTag();
        }
        Glide.with(mContext).load(bean.getImageUrl()).asBitmap().placeholder(R.mipmap.loading).into(view2Holders.img_angle);
        view2Holders.tv_name.setText(bean.getName());
        view2Holders.tv_look_num.setText((bean.getType() == 1 ? "更新至" : "全") + bean.getChapter() + "集");
        view2Holders.img_angle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, bean.getResourceType() == 1 ? AnimDetailActivity.class : CarDetailActivity.class).putExtra("resourceId", bean.getId()));
            }
        });
        view2Holders.ll_add_image.removeAllViews();
        for (int i = 1; i < resourcesBean.size(); i++) {
            final NewMainBean.ResourcesBean dataBean = resourcesBean.get(i);
            RoundAngleImageView imageview = new RoundAngleImageView(mContext);
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, hight);
            params.setMargins(0, 0, Tools.dp2px(mContext, 16), 0);
            imageview.setLayoutParams(params);
            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, dataBean.getResourceType() == 1 ? AnimDetailActivity.class : CarDetailActivity.class).putExtra("resourceId", dataBean.getId()));
                }
            });
            Glide.with(mContext).load(dataBean.getImageUrl()).placeholder(R.mipmap.loading).into(imageview);
            view2Holders.ll_add_image.addView(imageview);
        }
        return convertView;
    }

    class View2Holders {
        private ImageView img_angle;
        private TextView tv_name;
        private TextView tv_look_num;
        private LinearLayout ll_add_image;
    }
}