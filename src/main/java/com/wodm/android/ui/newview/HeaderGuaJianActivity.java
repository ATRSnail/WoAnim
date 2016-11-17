package com.wodm.android.ui.newview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.TabFragmentAdapter;
import com.wodm.android.adapter.newadapter.FragmentMyPager;
import com.wodm.android.adapter.newadapter.GuaJianAdapter;
import com.wodm.android.bean.ColumnBean;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.fragment.GuaJianFragment;
import com.wodm.android.fragment.TouXiangFragment;
import com.wodm.android.tools.MallConversionUtil;
import com.wodm.android.utils.DialogUtils;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;
import com.wodm.android.view.newview.OfenUseView;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.TrackApplication;
import org.eteclab.ui.widget.CircularImage;
import org.eteclab.ui.widget.viewpager.BannerView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.wodm.android.Constants.CURRENT_USER;


/**
 * Created by songchenyu on 16/10/21.
 */
public class HeaderGuaJianActivity extends FragmentActivity implements FragmentMyPager.addClickIconListener, AtyTopLayout.myTopbarClicklistenter, View.OnClickListener {
    /**
     * Created by songchenyu on 16/10/21.
     */
    private MyGridView guajian_free;
    private GuaJianAdapter guaJianAdapter;
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;
    private TextView tabTv1, tabTv2;
    private FragmentMyPager frag;
    private AtyTopLayout set_topbar;
    private CircularImage user_head_imgs;
    private ImageView user_guajian;
    private ScrollView scrollView;
    private RelativeLayout ll_open_vip;
    private Button btn_open_vip;
    private View tabLine1, tabLine2, view1, view2;
    private MallGuaJianBean clickBean;
    private int clckIcon;
    private BannerView mBannerView;
    private TabLayout tabLayout;
    private List<ColumnBean> columnBeanList;
    private OfenUseView rl_ofenusertime;
    private TextView tv_user_name;
    private ImageView img_vip_circle;
    private Button btn_buy_now;
    private LinearLayout ll_guajian_of_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_headerguajian);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            clickBean = (MallGuaJianBean) bundle.getSerializable("iconClick");
        }

        initData();
    }

    private void initData(){
        httpGet(Constants.APP_GET_PRODUCT_COLUMNLIST , new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    columnBeanList= new Gson().fromJson(obj.getString("data"), new TypeToken<List<ColumnBean>>() {
                    }.getType());
                    initView();
                    initClickImage(clickBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                Toast.makeText(HeaderGuaJianActivity.this, ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
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
    private void initClickImage(MallGuaJianBean clickBean) {
        if (clickBean == null )
            return;
        try {
            MallConversionUtil.getInstace().dealExpression(this,clickBean.getProductName(),user_guajian,"");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private FragmentMyPager guanJianFrag;
    private FragmentMyPager touXiangFrg;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private TabFragmentAdapter tabFragmentAdapter;

    private void initView() {
        ll_guajian_of_user= (LinearLayout) findViewById(R.id.ll_guajian_of_user);
        ll_guajian_of_user.setOnClickListener(this);
        img_vip_circle= (ImageView) findViewById(R.id.img_vip_circle);
        btn_buy_now= (Button) findViewById(R.id.btn_buy_now);
        btn_buy_now.setOnClickListener(this);
        set_topbar = (AtyTopLayout) findViewById(R.id.set_topbar);
        btn_open_vip = (Button) findViewById(R.id.btn_open_vip);
        btn_open_vip.setOnClickListener(this);
        rl_ofenusertime = (OfenUseView) findViewById(R.id.rl_ofenusertime);
        tv_user_name= (TextView) findViewById(R.id.tv_user_name);
        if (columnBeanList.size()>0){
            String name=columnBeanList.get(0).getName();
            rl_ofenusertime.setTitle(name);
            columnBeanList.remove(0);
            httpGet(Constants.APP_GET_MALL_TOUXIANG +Constants.CURRENT_USER.getData().getAccount().getId()+"&page="+1 , new HttpCallback() {

                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        List<MallGuaJianBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<MallGuaJianBean>>() {
                        }.getType());
                        List<MallGuaJianBean> list=new ArrayList<MallGuaJianBean>();
                        for (int i = 0; i < beanList.size(); i++) {
                            MallGuaJianBean bean=beanList.get(i);
                            list.add(bean);
                            if (i==2){
                                break;
                            }
                        }
                        guaJianAdapter = new GuaJianAdapter(guajian_free, HeaderGuaJianActivity.this, clickBean,list);
                        guaJianAdapter.setAddClickIconListener(HeaderGuaJianActivity.this);
                        guajian_free.setAdapter(guaJianAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    Toast.makeText(HeaderGuaJianActivity.this, ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
                }
            });

        }
        guanJianFrag = new GuaJianFragment();
        touXiangFrg = new TouXiangFragment();
        guanJianFrag.setAddClickIconListener(this);
        touXiangFrg.setAddClickIconListener(this);
        guanJianFrag.setClickImage(clickBean);
        touXiangFrg.setClickImage(clickBean);
        set_topbar.setOnTopbarClickListenter(this);
        fragments.add(guanJianFrag);
        fragments.add(touXiangFrg);
        guanJianFrag.setNumClown(columnBeanList);
        touXiangFrg.setNumClown(columnBeanList);
        mTitles.add("挂件");
        mTitles.add("头像");

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        user_head_imgs = (CircularImage) findViewById(R.id.img_user_header);
        user_guajian = (ImageView) findViewById(R.id.user_head_imgs);

        ll_open_vip = (RelativeLayout) findViewById(R.id.ll_open_vip);
        guajian_free = (MyGridView) findViewById(R.id.guajian_free);
        guajian_free.setSelector(new ColorDrawable(Color.TRANSPARENT));
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        mViewPager = (ViewPager) findViewById(R.id.mypage_pager);
        tabFragmentAdapter = new TabFragmentAdapter(fragments, mTitles, getSupportFragmentManager(), getApplicationContext());
        mViewPager.setAdapter(tabFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.size());

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(tabFragmentAdapter);
//        mViewPager.addOnPageChangeListener(new MyPageChangeListener());
//        frag = FragmentMyPager.newInstance(0, clickImage);
//        frag.setAddClickIconListener(this);
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        }

        initUserInfo();

    }
    private void initUserInfo(){
        if (CURRENT_USER==null){
             return;
        }
        UserInfoBean.DataBean.AccountBean accountBean=CURRENT_USER.getData().getAccount();
        tv_user_name.setText(accountBean.getNickName());
        new AsyncImageLoader(this, R.mipmap.touxiang_moren, R.mipmap.moren_header).display(user_head_imgs, accountBean.getPortrait());
        int vip=accountBean.getVip();
        if (vip==1){
            img_vip_circle.setBackgroundResource(R.mipmap.vip_circle);
        }else if (vip==2){
            img_vip_circle.setBackgroundResource(R.mipmap.vvip_circle);
        }else {
            img_vip_circle.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void addImage(MallGuaJianBean mallGuaJianBean, boolean isVip, int index) {
        if (mallGuaJianBean==null){
            return;
        }
        String name=mallGuaJianBean.getProductName();
        try {
            MallConversionUtil.getInstace().dealExpression(this,name,user_guajian,mallGuaJianBean.getProductImageUrl());
        } catch (Exception e) {
            Glide.with(this).load(name).placeholder(R.mipmap.loading).into(user_guajian);
            e.printStackTrace();
        }
//        Drawable drawable = getResources().getDrawable(imageRescoure);
//        user_guajian.setImageDrawable(drawable);
        if (isVip) {
            if (guanJianFrag != null) guanJianFrag.onUnselect();
            if (touXiangFrg != null) touXiangFrg.onUnselect();

        } else {
            guaJianAdapter.onUnselect();
            if (index == 0){
                if (touXiangFrg != null) touXiangFrg.onUnselect();
            }else {
                if (guanJianFrag != null) guanJianFrag.onUnselect();
            }
        }

//        user_guajian.setBackgroundResource(imageRescoure);
    }

    @Override
    public void leftClick() {
        finish();

    }

    @Override
    public void rightClick() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_vip:
                Intent intent = new Intent(HeaderGuaJianActivity.this, NewVipActivity.class);
                startActivity(intent);
                break;case
            R.id.ll_guajian_of_user:
                Intent intent1 = new Intent(HeaderGuaJianActivity.this, AllOfMineWallActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_buy_now:
//                new DialogUtils.Builder(this)
//                        .setMessage("对不起,您不是联通用户" + "\n" + "无法进行绑定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).create().show();
                httpGet(Constants.APP_GET_PRODUCT_IS_BUY +Constants.CURRENT_USER.getData().getAccount().getId()+"&productCode="+clickBean.getProductCode() , new HttpCallback() {

                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                       if (obj.optInt("data")==1){
                           BuyingGoodsDialog();
                       }else {
                           NoScore();
                       }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        Toast.makeText(HeaderGuaJianActivity.this, ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
//            default:
//                new AlertDialog.Builder(HeaderGuaJianActivity.this)
//                        .setTitle("砖石头像框")
//                        .setMessage("确定使用930积分兑换？")
//                        .create().show();
//                break;
        }

    }
    private void NoScore(){
        new DialogUtils.Builder(this)
                .setMessage("您的积分不足").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
    private void BuyingGoodsDialog(){
        new DialogUtils.Builder(HeaderGuaJianActivity.this)
                .setTitle("砖石头像框")
                .setMessage("确定使用"+clickBean.getNeedScore()+"积分兑换？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        BuyingGoods();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
    private void BuyingGoods(){
        httpGet(Constants.APP_GET_BUY_PRODUCT +Constants.CURRENT_USER.getData().getAccount().getId()+"&productCode="+clickBean.getProductCode()+"&payType=1" , new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                Toast.makeText(HeaderGuaJianActivity.this, ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                Toast.makeText(HeaderGuaJianActivity.this, ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;

        TabInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }
    }

    private class DummyTabFactory implements TabHost.TabContentFactory {
        private Context mContext;

        public DummyTabFactory(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }

    private class TabsAdapter extends FragmentPagerAdapter
            implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
        private Context mContext;
        private TabHost mTabHost;
        private ViewPager mViewPager;
        private ArrayList<TabInfo> mTabs = new ArrayList<>();
        private List<Fragment> fragments = new ArrayList<>();

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onTabChanged(String tabId) {
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
            if (position == 0) {
//                tabTv1.setText("头像框");
//                tabTv1.setTextColor(getResources().getColor(R.color.color_333333));
                tabLine1.setBackgroundColor(getResources().getColor(R.color.color_dd2e5c));

//                tabTv2.setText("挂件");
//                tabTv2.setTextColor(getResources().getColor(R.color.colorAccent));
                tabLine2.setBackgroundColor(Color.TRANSPARENT);
            } else if (position == 1) {
//                tabTv2.setText("头像框");
//                tabTv2.setTextColor(getResources().getColor(R.color.color_333333));
                tabLine2.setBackgroundColor(getResources().getColor(R.color.color_dd2e5c));

//                tabTv1.setText("挂件");
//                tabTv1.setTextColor(getResources().getColor(R.color.colorAccent));
                tabLine1.setBackgroundColor(Color.TRANSPARENT);
            }
//            String tmp=mTabHost.getCurrentTabTag();
//            mTabHost.getCurrentTabView().set
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
//            frag.setCurrentTabNum(position);
//            frag.setAddClickIconListener(HeaderGuaJianActivity.this);
            widget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
