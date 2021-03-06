package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.TabFragmentAdapter;
import com.wodm.android.bean.ProductByTypeBean;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.fragment.newfragment.FragmentVipPager;
import com.wodm.android.fragment.newfragment.VipFragment;
import com.wodm.android.fragment.newfragment.VvipFragment;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.TrackApplication;
import org.eteclab.ui.widget.CircularImage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/11/15.
 */

public class NewVipActivity extends FragmentActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener {
    /**
     * Created by songchenyu on 16/10/21.
     */
    private ViewPager mViewPager;
    private AtyTopLayout set_topbar;
    private CircularImage user_head_imgs;
    private ScrollView scrollView;
    private View tabLine1, tabLine2;
    private TabLayout tabLayout;
    private LinearLayout ll_tiaokuan;
    private Button btn_open_vip;
    private Button open_now;
    private boolean isOpenVip = true;
    private ImageView img_dagou;
    private ImageView img_vip_circle;
    private TextView tv_endof_vip_num;
    private TextView nick_value;
    private TextView nick_name;
    private LinearLayout ll_novip_hint, ll_vip_jiasu, ll_novip_open_vip, ll_vip_time;
    private TextView tv_score;
    private ImageView img_speed;
    private String phone;
    private String VipProductCode;
    private String VVipProductCode;
    private String VipPrice;
    private String VVipPrice;
    private String VipDiscountPrice;
    private String VVipDiscountPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_newvip);

        initView();


    }


    private FragmentVipPager vipPagerFragment;
    private FragmentVipPager vvipPagerFragment;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private TabFragmentAdapter tabFragmentAdapter;
    private ImageView img_vip;

    private void initView() {
        phone = Constants.CURRENT_USER.getData().getAccount().getMobile();
        setPhone(phone);
        img_speed = (ImageView) findViewById(R.id.img_speed);
        tv_score = (TextView) findViewById(R.id.tv_score);
        img_vip = (ImageView) findViewById(R.id.img_vip);
        ll_novip_open_vip = (LinearLayout) findViewById(R.id.ll_novip_open_vip);
        ll_vip_time = (LinearLayout) findViewById(R.id.ll_vip_time);
        ll_novip_hint = (LinearLayout) findViewById(R.id.ll_novip_hint);
        ll_vip_jiasu = (LinearLayout) findViewById(R.id.ll_vip_jiasu);
        nick_value = (TextView) findViewById(R.id.nick_value);
        nick_name = (TextView) findViewById(R.id.nick_name);
        tv_endof_vip_num = (TextView) findViewById(R.id.tv_endof_vip_num);
        img_vip_circle = (ImageView) findViewById(R.id.img_vip_circle);
        set_topbar = (AtyTopLayout) findViewById(R.id.set_topbar);
        vipPagerFragment = new VipFragment();
        vvipPagerFragment = new VvipFragment();
        fragments.add(vvipPagerFragment);
        fragments.add(vipPagerFragment);
        mTitles.add("VIP用户");
        mTitles.add("VVIP用户");
        ll_tiaokuan = (LinearLayout) findViewById(R.id.ll_tiaokuan);
        set_topbar.setOnTopbarClickListenter(this);
        ll_tiaokuan = (LinearLayout) findViewById(R.id.ll_tiaokuan);
        ll_tiaokuan.setOnClickListener(this);
        img_dagou = (ImageView) findViewById(R.id.img_dagou);
        btn_open_vip = (Button) findViewById(R.id.btn_open_vip);
        btn_open_vip.setOnClickListener(this);
        open_now = (Button) findViewById(R.id.open_now);
        open_now.setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        user_head_imgs = (CircularImage) findViewById(R.id.img_user_header);
        if (CURRENT_USER.getData().getAccount().getPortrait() != null)
            new AsyncImageLoader(this, R.mipmap.touxiang_moren, R.mipmap.default_header).display(user_head_imgs, CURRENT_USER.getData().getAccount().getPortrait());
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        mViewPager = (ViewPager) findViewById(R.id.mypage_pager);
        tabFragmentAdapter = new TabFragmentAdapter(fragments, mTitles, getSupportFragmentManager(), getApplicationContext());
        mViewPager.setAdapter(tabFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.size());

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(tabFragmentAdapter);
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        }
        setUserInfo();
    }

    private void setUserInfo() {
        httpGet(Constants.APP_GET_VIP_TIME + CURRENT_USER.getData().getAccount().getId(), new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                String day = obj.optString("data");
                tv_endof_vip_num.setText(day + "");
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
        UserInfoBean.DataBean.AccountBean accountBean = CURRENT_USER.getData().getAccount();
        int vip = accountBean.getVip();
        if (vip == 0) {
            ll_novip_open_vip.setVisibility(View.VISIBLE);
            ll_novip_hint.setVisibility(View.VISIBLE);
            ll_vip_time.setVisibility(View.GONE);
            ll_vip_jiasu.setVisibility(View.GONE);
            setVipOpenButton("开通");
        } else if (vip == 1) {
            ll_novip_open_vip.setVisibility(View.GONE);
            ll_novip_hint.setVisibility(View.GONE);
            ll_vip_time.setVisibility(View.VISIBLE);
            ll_vip_jiasu.setVisibility(View.VISIBLE);
            img_vip.setBackgroundResource(R.mipmap.img_vip_vip);
            img_speed.setBackgroundResource(R.mipmap.vip_speed);
            img_vip_circle.setBackgroundResource(R.mipmap.vip_circle);
            setVipOpenButton("续费");
        } else if (vip == 2) {
            ll_novip_open_vip.setVisibility(View.GONE);
            ll_novip_hint.setVisibility(View.GONE);
            ll_vip_time.setVisibility(View.VISIBLE);
            ll_vip_jiasu.setVisibility(View.VISIBLE);
            img_speed.setBackgroundResource(R.mipmap.vvip_speed);
            img_vip.setBackgroundResource(R.mipmap.img_vip_vvip);
            img_vip_circle.setBackgroundResource(R.mipmap.vvip_circle);
            setVipOpenButton("续费");
        }
        tv_score.setText(accountBean.getScore() + "");
        nick_value.setText("LV." + accountBean.getGradeValue());
        nick_name.setText(accountBean.getNickName());
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(user_head_imgs, CURRENT_USER.getData().getAccount().getPortrait());
    }

    private void setVipOpenButton(final String vip) {

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String str = "VIP";
                if (position == 0) {
                    str = "VIP";
                } else {
                    str = "VVIP";
                }
                btn_open_vip.setText(vip + str);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void httpGet(String url, final HttpCallback callback) {

        try {
            TrackApplication.REQUEST_HEADER.put("Content-Type", "application/json");
            HttpUtil.httpGet(this, url, TrackApplication.REQUEST_HEADER, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void leftClick() {
        finish();

    }

    @Override
    public void rightClick() {

    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.open_now:
            case R.id.btn_open_vip:

                if (isOpenVip) {
                    Intent intent = new Intent();
                    if (getPhone() != null && getPhone().length() > 0) {
                        if (btn_open_vip.getText().toString().contains("VVIP")) {
                            intent.setClass(NewVipActivity.this, VVipPayActivity.class);
                            intent.putExtra("productCode", VVipProductCode);
                            intent.putExtra("VVipPrice", VVipPrice);
                            intent.putExtra("VVipDiscountPrice", VVipDiscountPrice);
                        } else {
                            intent.setClass(NewVipActivity.this, VipPayActivity.class);
                            intent.putExtra("productCode", VipProductCode);
                            intent.putExtra("VipPrice", VipPrice);
                            intent.putExtra("VipDiscountPrice", VipDiscountPrice);
                        }
                        intent.putExtra("phone", phone);
                        intent.putExtra("vip", btn_open_vip.getText());
                    } else {
                        intent.setClass(NewVipActivity.this, BindPhoActivity.class);
                    }
                    startActivity(intent);
                }
                break;
            case R.id.ll_tiaokuan:
                if (isOpenVip) {
                    img_dagou.setBackgroundResource(R.mipmap.vip_quxiaodagou);
                    btn_open_vip.setBackgroundResource(R.drawable.btn_open_vip);
                    isOpenVip = false;
                } else {
                    img_dagou.setBackgroundResource(R.mipmap.vip_dagou);
                    btn_open_vip.setBackgroundResource(R.drawable.btn_open_vip_click);
                    isOpenVip = true;
                }
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getVVipProductCode();
        getVipProductCode();
    }

    private void getVipProductCode() {
        httpGet(Constants.APP_GET_PRODUCT_BY_PRODUCTTYPE + "2", new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                ProductByTypeBean productByTypeBean = new Gson().fromJson(obj.toString(), ProductByTypeBean.class);
                VipProductCode = productByTypeBean.getData().get(0).getProductCode();
                VipPrice =String.valueOf(productByTypeBean.getData().get(0).getPrice());
                VipDiscountPrice=String.valueOf(productByTypeBean.getData().get(0).getDiscountPrice());
            }
        });
    }
    private void getVVipProductCode() {
        httpGet(Constants.APP_GET_PRODUCT_BY_PRODUCTTYPE + "3", new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                ProductByTypeBean productByTypeBean = new Gson().fromJson(obj.toString(), ProductByTypeBean.class);
                VVipProductCode = productByTypeBean.getData().get(0).getProductCode();
                VVipPrice =String.valueOf(productByTypeBean.getData().get(0).getPrice());
                VVipDiscountPrice=String.valueOf(productByTypeBean.getData().get(0).getDiscountPrice());
            }
        });
    }

}
