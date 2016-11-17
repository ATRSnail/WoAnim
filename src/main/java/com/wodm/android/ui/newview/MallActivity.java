package com.wodm.android.ui.newview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.MallAdapter;
import com.wodm.android.bean.BannerBean;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.WebViewActivity;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.home.CarDetailActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.viewpager.BannerView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_mall)
public class MallActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener {

//    @ViewIn(R.id.ll_buy_mall)
//    LinearLayout ll_buy_mall;
    @ViewIn(R.id.new_grid_mall)
    MyGridView new_grid_mall;
    @ViewIn(R.id.renqi_grid_mall)
    MyGridView renqi_grid_mall;
    @ViewIn(R.id.back_mall)
    AtyTopLayout back_mall;
//    @ViewIn(R.id.go_btn)
//    ImageButton go_btn;
    @ViewIn(R.id.banner)
    BannerView mBannerView;
    private LinearLayout mall_more1,mall_more;

    MallAdapter adapter;
    private List<MallGuaJianBean> newsbeanList;
    private List<MallGuaJianBean> mansbeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renqi_grid_mall.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mall_more1= (LinearLayout) findViewById(R.id.mall_more1);
        mall_more= (LinearLayout) findViewById(R.id.mall_more);
        mall_more1.setOnClickListener(this);
        mall_more.setOnClickListener(this);
//        initLinearLayout();
        //1 挂件 2 头像
        initData(1);
        initData(2);
        initBars();
        initTapPageView();
//        initList();
        back_mall.setOnTopbarClickListenter(this);
 //       go_btn.setOnClickListener(this);
    }
    private void initTapPageView() {
        mBannerView = (BannerView) findViewById(R.id.banner);
        final String string =Constants.URL_HOME_TOP_LIST + "&type=" + 1;
        HttpUtil.httpGet(MallActivity.this, string, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    ArrayList<BannerBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<ArrayList<BannerBean>>() {
                    }.getType());

                    List<View> bannerViews = new ArrayList<View>();
                    //(list.size() > 5 ? 5 : list.size())
                    for (int i = 0; i < (list.size() > 5 ? 5 : list.size()) ; i++) {
                        ImageView v = new ImageView(MallActivity.this);
                        v.setScaleType(ImageView.ScaleType.FIT_XY);
                        final BannerBean bean = list.get(i);
                        new AsyncImageLoader(MallActivity.this, R.mipmap.loading, R.mipmap.loading).display(v, bean.getImage());
                        final int pos = i;
                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 点击事件
                                try {
                                    Intent intent = new Intent();
                                    intent.putExtra("resourceId", Integer.valueOf(bean.getResourceId()).intValue());
                                    if (bean.getType().equals("1")) {
                                        intent.setClass(MallActivity.this, AnimDetailActivity.class);
                                    } else if (bean.getType().equals("2")) {
                                        intent.setClass(MallActivity.this, CarDetailActivity.class);
                                    } else if (bean.getType().equals("2")) {
                                        intent.setClass(MallActivity.this, CarDetailActivity.class);
                                    }else {
                                        intent.putExtra("adsUrl",bean.getAdsUrl());
                                        intent.setClass(MallActivity.this, WebViewActivity.class);
                                    }
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        bannerViews.add(v);
                    }
                    mBannerView.setViewPagerViews(bannerViews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initData(final int page){
        httpGet(Constants.APP_GET_MALL_TOUXIANG +Constants.CURRENT_USER.getData().getAccount().getId()+"&page="+page , new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    if (page==1){
                        newsbeanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<MallGuaJianBean>>() {
                        }.getType());
                    }else {
                        mansbeanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<MallGuaJianBean>>() {
                        }.getType());
                    }

                    initList(page);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                Toast.makeText(MallActivity.this, ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initList(int page) {
        if (page==2){
            adapter = new MallAdapter(this, mansbeanList);
            new_grid_mall.setAdapter(adapter);
            new_grid_mall.setFocusable(false);
        }else {
            adapter = new MallAdapter(this, newsbeanList);
            renqi_grid_mall.setAdapter(adapter);
            renqi_grid_mall.setFocusable(false);
        }
//        adapter = new MallAdapter(this, list, 1);
//        renqi_grid_mall.setAdapter(adapter);
//        renqi_grid_mall.setFocusable(false);
//        renqi_grid_mall.setOnItemClickListener(this);
    }

//    private void initLinearLayout() {
//        for (int i = 0; i < buyName.length; i++) {
//            LinearLayout view = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.buy_item_mall, null, false);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
//            view.setLayoutParams(params);
//            ImageView buy_colcor_mall;
//            TextView buy_font_mall;
//            buy_colcor_mall = (ImageView) view.findViewById(R.id.buy_colcor_mall);
//            buy_font_mall = (TextView) view.findViewById(R.id.buy_font_mall);
//            buy_font_mall.setText(buyName[i]);
//            buy_colcor_mall.setBackgroundResource(color[i]);
//            view.setClickable(true);
//            view.setFocusable(true);
//            String name = buyName[i];
//            if (name.equals("周边")) {
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new AlertDialog.Builder(MallActivity.this)
//                                .setMessage("正在建设中...")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .create().show();
//                    }
//                });
//            } else {
//                Class atyClass = null;
//                if (name.equals("会员")) {
//                    atyClass = VipOpenActivity.class;
//                } else {
//                    atyClass = HeaderGuaJianActivity.class;
//                }
//                final Class finalAtyClass = atyClass;
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (finalAtyClass == null)
//                            return;
//                        Intent intent = new Intent(MallActivity.this, finalAtyClass);
//                        startActivity(intent);
//                    }
//                });
//            }
//            buy_colcor_mall.setBackgroundResource(colors[i]);
//            view.setId(i);
//            ll_buy_mall.addView(view);
//        }
//    }

    private void initBars(){
        List<View> bannerViews = new ArrayList<View>();
        //(list.size() > 5 ? 5 : list.size())
        for (int i = 0; i < 4 ; i++) {
            ImageView v = new ImageView(this);
            v.setScaleType(ImageView.ScaleType.FIT_XY);
            new AsyncImageLoader(this, R.mipmap.loading, R.mipmap.loading).display(v, "");
            final int pos = i;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击事件

                }
            });
            bannerViews.add(v);
        }
        mBannerView.setViewPagerViews(bannerViews);
    }


    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {
        Intent intent=new Intent();
        intent.setClass(this,BuyingHistoryActivity.class);
        startActivity(intent);

    }

    private void initDialog() {
        new AlertDialog.Builder(MallActivity.this)
                .setTitle("用户您好：")
                .setMessage("您的积分不足！")
                .create().show();
    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(MallActivity.this,"lf,gfllfdfdb,lfbld,fd",Toast.LENGTH_LONG);
        switch (v.getId()) {
            case 0:
                break;
            case 1:
//                startActivity(new Intent(this, HeaderGuaJianActivity.class));
                break;
            case 2:
//                startActivity(new Intent(this, HeaderGuaJianActivity.class));
                break;
            case 3:
                new AlertDialog.Builder(this)
                        .setMessage("正在建设中...")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
                break;
            case R.id.mall_more:
                startGuajian("人气");
                break;
            case R.id.mall_more1:
                startGuajian("最新");
                break;
//            case R.id.go_btn:
//                new AlertDialog.Builder(this)
//                        .setMessage("您的积分不足")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .create().show();
//                break;

        }
    }
    private void startGuajian(String text){
        Intent intent=new Intent();
        intent.putExtra("text",text);
        intent.setClass(this,GuaJianHeaderImageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        mBannerView.start();
        super.onStart();
    }

    @Override
    public void onStop() {
        mBannerView.shutdown();
        super.onStop();
    }
}