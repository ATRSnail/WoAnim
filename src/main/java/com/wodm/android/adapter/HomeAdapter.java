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
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.WebViewActivity;
import com.wodm.android.ui.newview.NewMainDetailsActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.ui.widget.NoScrollGridView;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2016/4/26.
 */


@Layout(R.layout.adapter_home_items)
public class HomeAdapter extends HolderAdapter<NewMainBean> {
    private static int type=4;
    private int gettype=5;
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
        if (gettype!=type){
            gettype=type;
            getAdvitisement();
        }
        ViewHolders holders = (ViewHolders) viewHolder;
        NewMainBean newMainBean=mData.get(index);
        final String name=newMainBean.getName();
        final int id=newMainBean.getId();
        holders.tv_title_name.setText(name);
        int width= Tools.getScreenWidth((Activity) mContext);
        int hight= (int) ((width-60)*((float)140/690));
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hight);
        params.setMargins(30,30,30,30);
        holders.img_angle_ads.setLayoutParams(params);
        if (adsList.size()>0){
            for (final AdsBean bean:adsList) {
                if (bean.getSort()==index+1){
                    holders.img_angle_ads.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(bean.getImage()).placeholder(R.mipmap.loading).into(holders.img_angle_ads);
                    holders.img_angle_ads.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(mContext, WebViewActivity.class);
                            intent.putExtra("adsUrl",bean.getAdsUrl());
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                }else {
                    holders.img_angle_ads.setVisibility(View.GONE);
                }
            }
        }
        final int style=newMainBean.getStyle();
        List<NewMainBean.ResourcesBean> resourcesBean=newMainBean.getResources();
        if (style==1){
              holders.grid_new.setAdapter(new NewMain2Adapter(mContext,resourcesBean));
        }else if (style==2){
            holders.grid_new.setAdapter(new NewMain3Adapter(mContext,resourcesBean));
        }else if (style==3){
            holders.grid_new.setAdapter(new NewMainDetailsLVAdapter(mContext,resourcesBean));
        } else if (style==4){
            holders.grid_new.setAdapter(new NewMainAdapter(mContext,resourcesBean,style));
        } else if (style==5){
            holders.grid_new.setAdapter(new NewMainGvAdapter(mContext,resourcesBean));
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
//            View1Holders view1Holders=new View1Holders();
//            View view= LayoutInflater.from(mContext).inflate(R.layout.new_main1,null,false);
//            view1Holders.img_angle= (ImageView) view.findViewById(R.id.img_angle);
//            view1Holders.ll_shadow= (LinearLayout) view.findViewById(R.id.ll_shadow);
//            view1Holders.tv_look_num= (TextView) view.findViewById(R.id.tv_look_num);
//            view1Holders.new_main_image_no_gv= (MyGridView) view.findViewById(R.id.new_main_image_no_gv);
//            int hight= (int) ((width-60)*((float)220/690));
//            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, hight);
//            view1Holders.img_angle.setLayoutParams(params);
//        RelativeLayout.LayoutParams ll_shadowparams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ll_shadow_hight);
//        holders.ll_shadow.setLayoutParams(ll_shadowparams);
//            view1Holders.ll_shadow.getBackground().setAlpha(140);
//            view1Holders.tv_look_num.setTextColor(0x50333333);
//            view1Holders.new_main_image_no_gv.setAdapter(new NewMainAdapter(mContext));
//            holders.ll_view_total.addView(view);
//        }else if (index==1){
//            holders.grid_new.setAdapter(new NewMain3Adapter(mContext));
//            ViewAdsHolders viewAdsHolders=new ViewAdsHolders();
//            View view= LayoutInflater.from(mContext).inflate(R.layout.new_main_ads,null,false);
//            viewAdsHolders.img_angle_ads= (ImageView) view.findViewById(R.id.img_angle_ads);
//            int hight= (int) ((width-60)*((float)140/690));
//            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hight);
//            params.setMargins(30,30,30,30);
//            viewAdsHolders.img_angle_ads.setLayoutParams(params);
////            holders.ll_view_total.addView(view);
//        }else if (index==2){
//            holders.grid_new.setAdapter(new NewMainAdAdapter(mContext));
//            View2Holders view2Holders=new View2Holders();
//            View view= LayoutInflater.from(mContext).inflate(R.layout.new_main2,null,false);
//            view2Holders.new_main2_horscroll= (HorizontalScrollView) view.findViewById(R.id.new_main2_horscroll);
//            view2Holders.ll_add_image= (LinearLayout) view.findViewById(R.id.ll_add_image);
//            for (int i = 0; i <5 ; i++) {
//                RoundAngleImageView imageview=new RoundAngleImageView(mContext);
//                imageview.setBackgroundResource(R.mipmap.login_banner);
//                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(320, 228);
//                params.setMargins(0,0,16,0);
//                imageview.setLayoutParams(params);
//                view2Holders.ll_add_image.addView(imageview);
//            }
//
////            holders.ll_view_total.addView(view);
//        }else if (index==3){
//            holders.grid_new.setAdapter(new NewMainDetailsLVAdapter(mContext));
//            View3Holders view3Holder=new View3Holders();
//            View view= LayoutInflater.from(mContext).inflate(R.layout.new_main3,null,false);
//            view3Holder.new_main3_noscroll_lv= (NoScrollListView) view.findViewById(R.id.new_main3_noscroll_lv);
//            view3Holder.new_main3_noscroll_lv.setAdapter(new NewMain3Adapter(mContext));
////            holders.ll_view_total.addView(view);
//        }else if (index==4){
//            holders.grid_new.setAdapter(new NewMain2Adapter(mContext));
//            View1Holders view1Holders=new View1Holders();
//            View view= LayoutInflater.from(mContext).inflate(R.layout.new_main1,null,false);
//            view1Holders.img_angle= (ImageView) view.findViewById(R.id.img_angle);
//            view1Holders.ll_shadow= (LinearLayout) view.findViewById(R.id.ll_shadow);
//            view1Holders.tv_look_num= (TextView) view.findViewById(R.id.tv_look_num);
//            view1Holders.new_main_image_no_gv= (MyGridView) view.findViewById(R.id.new_main_image_no_gv);
//            int hight= (int) ((width-60)*((float)220/690));
//            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, hight);
//            view1Holders.img_angle.setLayoutParams(params);
////        RelativeLayout.LayoutParams ll_shadowparams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ll_shadow_hight);
////        holders.ll_shadow.setLayoutParams(ll_shadowparams);
//            view1Holders.ll_shadow.getBackground().setAlpha(140);
//            view1Holders.tv_look_num.setTextColor(0x50333333);
//            view1Holders.new_main_image_no_gv.setNumColumns(3);
//            view1Holders.new_main_image_no_gv.setAdapter(new NewMainAdapter(mContext));
////            holders.ll_view_total.addView(view);

//        final ObjectDataBean bean = mData.get(index);
//        ComicAdapter hotAdapter = new ComicAdapter(mContext, bean.getResources());
//        new AsyncImageLoader(mContext, R.mipmap.tubiao, R.mipmap.tubiao).display(holders.icon, bean.getIcon());
//        holders.name.setText(bean.getName());
//        holders.gridView.setAdapter(hotAdapter);
//        holders.more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, CartoonsActivity.class);
//                intent.putExtra("columnId", bean.getId());
//                intent.putExtra("title", bean.getName());
//                mContext.startActivity(intent);
//            }
//        });
    }
//    class ViewAdsHolders{
//        private ImageView img_angle_ads;
//
//    }
//    class View2Holders{
//        private LinearLayout ll_add_image;
//        private HorizontalScrollView new_main2_horscroll;
//    }
//    class View1Holders{
//        private ImageView img_angle;
//        private LinearLayout ll_shadow;
//        private TextView tv_look_num;
//        private MyGridView new_main_image_no_gv;
//    }
//    class View3Holders{
//        private NoScrollListView new_main3_noscroll_lv;
//    }
//    class View3Holders{
//        private ImageView img_angle;
//        private LinearLayout ll_shadow;
//        private TextView tv_look_num;
//        private MyGridView new_main_image_no_gv;
//    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.grid_new)
        private NoScrollGridView grid_new;
        @ViewIn(R.id.tv_title_name)
        private TextView tv_title_name;
        @ViewIn(R.id.img_angle_ads)
        private ImageView img_angle_ads;
        @ViewIn(R.id.img_main_more)
        private ImageView img_main_more;
        public ViewHolders(View view) {
            super(view);
        }
    }
}
