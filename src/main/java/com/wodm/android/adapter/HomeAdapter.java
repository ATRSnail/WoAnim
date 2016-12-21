package com.wodm.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.NewMain2Adapter;
import com.wodm.android.adapter.newadapter.NewMain3Adapter;
import com.wodm.android.adapter.newadapter.NewMainAdapter;
import com.wodm.android.adapter.newadapter.NewMainDetailsLVAdapter;
import com.wodm.android.adapter.newadapter.NewMainGvAdapter;
import com.wodm.android.bean.AdsBean;
import com.wodm.android.bean.NewMainBean;
import com.wodm.android.dbtools.DBTools;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.WebViewActivity;
import com.wodm.android.ui.newview.NewMainDetailsActivity;
import com.wodm.android.view.newview.RoundAngleImageView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.ui.widget.NoScrollGridView;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by json on 2016/4/26.
 */


@Layout(R.layout.adapter_home_items)
public class HomeAdapter extends HolderAdapter<NewMainBean> {
    private static int type=5;
    private int gettype=4;
    private List<AdsBean> adsList=new ArrayList<>();
    public HomeAdapter(Context context, List<NewMainBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }
    public static int getType(int type1){
        type=type1;
        return type;
    }
    private void getAdvitisement(){
        HttpUtil.httpGet(mContext, Constants.APP_GET_MAIN_ADVERTISEMENT +"?location=1&adType="+type, new HttpCallback() {
            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                if (obj != null) {
                    try {
                        if (adsList.size()>0){
                            adsList.clear();
                        }
                        List<AdsBean> adsList1 = new Gson().fromJson(obj.getString("data"), new TypeToken<List<AdsBean>>() {
                        }.getType());
                        adsList.addAll(adsList1);
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, final int index) {
        ViewHolders holders = (ViewHolders) viewHolder;
        if (gettype!=type){
            gettype=type;
            holders.img_angle_ads.setVisibility(View.GONE);
            adsList.clear();
            holders.ll_total.removeAllViews();
            getAdvitisement();
        }
        NewMainBean newMainBean=mData.get(index);
        final String name=newMainBean.getName();
        final int id=newMainBean.getId();
        holders.tv_title_name.setText(name);
        int width= Tools.getScreenWidth((Activity) mContext);
        int hight= (int) (width*((float)140/690));
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hight);
        params.setMargins(30,16,30,16);
        holders.img_angle_ads.setLayoutParams(params);
//        holders.img_angle_ads.setPadding(Tools.dp2px(mContext,8),Tools.dp2px(mContext,8),Tools.dp2px(mContext,8),Tools.dp2px(mContext,8));
        if (adsList.size()>0){
            for (final AdsBean bean:adsList) {
                if (bean.getSort()==index+1){
                    holders.img_angle_ads.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(bean.getImage()).placeholder(R.mipmap.loading).into(holders.img_angle_ads);
                    holders.img_angle_ads.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(bean);
                        }
                    });
                    break;
                }else {
                    holders.img_angle_ads.setVisibility(View.GONE);
                }
            }
            if (getItemCount()==(index+2)){
                int size=adsList.size()-(getItemCount()-1);
                if (size>0){
                    holders.ll_total.removeAllViews();
                    for (int i = 0; i < size; i++) {
                        final AdsBean bean=adsList.get(i);
                        LinearLayout linear=new LinearLayout(mContext);
                        linear.setOrientation(LinearLayout.VERTICAL);
                        linear.setBackgroundColor(mContext.getResources().getColor(R.color.color_efefef));
                        LinearLayout.LayoutParams linear_params=new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        linear.setLayoutParams(linear_params);
                        RoundAngleImageView roundAngleImageview=new RoundAngleImageView(mContext);
                        roundAngleImageview.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(mContext).load(bean.getImage()).placeholder(R.mipmap.loading).into(roundAngleImageview);
                        roundAngleImageview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(bean);
                            }
                        });
                        roundAngleImageview.setLayoutParams(params);
                        linear.addView(roundAngleImageview);
                        holders.ll_total.addView(linear);
                    }
                }
            }else {
                holders.ll_total.removeAllViews();
            }
        }
        final int style=newMainBean.getStyle();
        List<NewMainBean.ResourcesBean> resourcesBean=srotList(newMainBean.getResources());

        if (resourcesBean.size()>=0){
            /*
	          1:表示资源左右滚动的板式
	          2:表示资源上下滚动的板式
	          3:表示3个资源的板式
	          4:表示5个资源的板式
	          5:表示6个资源的板式
	          6:表示2个资源的板式
             */
            if (style==1){
                if (resourcesBean.size()>1){
                    holders.grid_new.setAdapter(new NewMain2Adapter(mContext,resourcesBean));
                }
            }else if (style==2){
                holders.grid_new.setAdapter(new NewMain3Adapter(mContext,resourcesBean));
            }else if (style==3){
                holders.grid_new.setAdapter(new NewMainDetailsLVAdapter(mContext,resourcesBean));
            } else if (style==4){
                holders.grid_new.setAdapter(new NewMainAdapter(mContext,resourcesBean,style));
            } else if (style==5){
                holders.grid_new.setAdapter(new NewMainGvAdapter(mContext,resourcesBean));
            }
        }
        holders.img_main_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext, NewMainDetailsActivity.class);
                intent.putExtra("style",style);
                intent.putExtra("name",name);
                intent.putExtra("id",id);
                mContext.startActivity(intent);
            }
        });
    }
    private List<NewMainBean.ResourcesBean> srotList(List<NewMainBean.ResourcesBean> resourcesBean){
        List<NewMainBean.ResourcesBean> myList=new ArrayList<>();
        if (resourcesBean==null||resourcesBean.size()==0){
            return myList;
        }
        int[] inteds=new int[resourcesBean.size()];
        for (int i = 0; i <resourcesBean.size() ; i++) {
            NewMainBean.ResourcesBean beans=resourcesBean.get(i);
            inteds[i]=beans.getSort();
        }
        Arrays.sort(inteds);//升序排序
        for (int i = inteds.length-1; i >=0 ; i--) {
            for (NewMainBean.ResourcesBean bean:resourcesBean){
                if (bean.getSort()==inteds[i]){
                    myList.add(bean);
                }
            }
        }
        return myList;
    }

    private void startActivity(AdsBean bean){
        DBTools.getInstance(mContext).insertAdsDB(bean.getId());
        Intent intent=new Intent(mContext, WebViewActivity.class);
        intent.putExtra("adsUrl",bean.getAdsUrl());
        mContext.startActivity(intent);
    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.grid_new)
        private NoScrollGridView grid_new;
        @ViewIn(R.id.tv_title_name)
        private TextView tv_title_name;
        @ViewIn(R.id.img_angle_ads)
        private ImageView img_angle_ads;
        @ViewIn(R.id.img_main_more)
        private ImageView img_main_more;
        @ViewIn(R.id.ll_total)
        private LinearLayout ll_total;
        public ViewHolders(View view) {
            super(view);
        }
    }
}
