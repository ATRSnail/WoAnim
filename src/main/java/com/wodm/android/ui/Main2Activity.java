package com.wodm.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.fragment.HomeFragment;
import com.wodm.android.fragment.RecomFragment;
import com.wodm.android.fragment.TypeFragment;
import com.wodm.android.run.DBService;
import com.wodm.android.service.DownLoadServices;
import com.wodm.android.tools.DegreeTools;
import com.wodm.android.tools.MallConversionUtil;
import com.wodm.android.tools.TimeTools;
import com.wodm.android.ui.newview.NewMineActivity;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.utils.UpdateUtils;
import com.wodm.android.view.biaoqing.FaceConversionUtil;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.ReflectionUtil;
import org.eteclab.track.TrackApplication;
import org.eteclab.track.Tracker;
import org.eteclab.track.annotation.TrackClick;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.wodm.android.Constants.CURRENT_USER;

@Layout(R.layout.activity_main2)
public class Main2Activity extends AppActivity {
    private static final SparseArray<SurfaceParam> mSurfaceParams = new SparseArray<>();
    @ViewIn(R.id.enetic_cartoon)
    private TextView mTab1;
    @ViewIn(R.id.enetic_animation)
    private TextView mTab2;

    @TrackClick(R.id.enetic_animation)
    public void clickAnimation(View v) {
        ((HomeFragment) mSurfaceParams.get(R.id.tab_home).fragment).clickType(v);
        initTapView();
    }

    @TrackClick(R.id.enetic_cartoon)
    public void clickCartoon(View v) {
        ((HomeFragment) mSurfaceParams.get(R.id.tab_home).fragment).clickType(v);
        initTapView();
    }

    private void initTapView() {
//        GetPhoneState.GetNetIp(this);
        if (((HomeFragment) mSurfaceParams.get(R.id.tab_home).fragment).IndexTabId == R.id.enetic_cartoon) {
            mTab2.setBackgroundResource(R.drawable.enetic_home_title_left);
            mTab2.setTextColor(getResources().getColor(android.R.color.white));
            mTab1.setBackgroundDrawable(new BitmapDrawable());
            mTab1.setTextColor(getResources().getColor(R.color.color_f5c92f));
        } else if (((HomeFragment) mSurfaceParams.get(R.id.tab_home).fragment).IndexTabId == R.id.enetic_animation) {
            mTab1.setBackgroundResource(R.drawable.enetic_home_title_right);
            mTab1.setTextColor(getResources().getColor(android.R.color.white));
            mTab2.setBackgroundDrawable(new BitmapDrawable());
            mTab2.setTextColor(getResources().getColor(R.color.color_f5c92f));
        }
    }

    @ViewIn(R.id.tab_home)
    private View mHome;
    @ViewIn(R.id.tab_type)
    private View mType;
    @ViewIn(R.id.tab_tuijian)
    private View mRecom;
    @ViewIn(R.id.tab_us)
    private View mUs;
    @ViewIn(R.id.enter_floating)
    private ImageButton mfloatView;

    private FragmentManager mFragmentManager;

    private int indexId=R.id.tab_home ;
    private long mExitTime;

    static {
        mSurfaceParams.put(R.id.tab_tuijian, new SurfaceParam(RecomFragment.class, R.string.enetic_recom));
        mSurfaceParams.put(R.id.tab_home, new SurfaceParam(HomeFragment.class, R.string.enetic_home));
        mSurfaceParams.put(R.id.tab_type, new SurfaceParam(TypeFragment.class, R.string.enetic_type));
        mSurfaceParams.put(R.id.tab_us, new SurfaceParam(NewMineActivity.class, R.string.enetic_us));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //增加缓存表情字段
        new Thread(new Runnable() {
            @Override
            public void run() {
                FaceConversionUtil.getInstace().getFileText(getApplication());
                MallConversionUtil.getInstace().getFileText(getApplication());
                DegreeTools.getInstance(getApplication());
            }
        }).start();
        pref = Preferences.getInstance(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), DownLoadServices.class);
        startService(intent);
        mFragmentManager = getFragmentManager();
        mHome.setOnClickListener(clickListener);
        mUs.setOnClickListener(clickListener);
        mType.setOnClickListener(clickListener);
        mRecom.setOnClickListener(clickListener);
        mToolbar.setNavigationIcon(null);
        mToolbar.setNavigationOnClickListener(null);
        mToolbar.findViewById(R.id.img_right).setVisibility(View.VISIBLE);
            initTabViews();
            initTapView();

        new UpdateUtils(this).checkUpdate(false);


    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (indexId != v.getId()) {
                indexId = v.getId();
                initTabViews();
            }
        }
    };


    private void initTabViews() {
        setTabViews(mHome, R.string.enetic_home, indexId == R.id.tab_home ? R.mipmap.tab_home_select : R.mipmap.tab_home, indexId == R.id.tab_home);
        setTabViews(mType, R.string.enetic_type, indexId == R.id.tab_type ? R.mipmap.tab_fenlei_select : R.mipmap.tab_fenlei, indexId == R.id.tab_type);
        setTabViews(mUs, R.string.enetic_us, indexId == R.id.tab_us ? R.mipmap.tab_us_select : R.mipmap.tab_us, indexId == R.id.tab_us);
        setTabViews(mRecom, R.string.enetic_recom, indexId == R.id.tab_tuijian ? R.mipmap.tab_guangchang_select : R.mipmap.tab_guangchang, indexId == R.id.tab_tuijian);
        Tracker.getInstance(getApplicationContext()).trackMethodInvoke("", "跳转到" + setTabSelection(indexId) + "页");
        setCustomTitle(indexId == R.id.tab_home ? "" : setTabSelection(indexId));
        showOrGoneCheckButton(indexId == R.id.tab_home ? View.VISIBLE : View.GONE);
    }

    private void setTabViews(View layout, int strRes, int imgRes, Boolean isSelect) {
        TextView tv = (TextView) layout.findViewById(R.id.tab_text);
        ImageView iv = (ImageView) layout.findViewById(R.id.tab_img);
        tv.setTextColor(isSelect ? Color.rgb(0x8A, 0xCE, 0xE8) : Color.rgb(0x92, 0x92, 0x92));
        iv.setImageResource(imgRes);
        tv.setText(strRes);
    }

    private String setTabSelection(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        int size = mSurfaceParams.size();
        for (int i = 0; i < size; i++) {
            Fragment f = mSurfaceParams.valueAt(i).fragment;
            if (f != null) {
                transaction.hide(f);
            }
        }
        if (mSurfaceParams.get(index).fragment == null) {
            mSurfaceParams.get(index).fragment = (Fragment) ReflectionUtil.generateObject(mSurfaceParams.get(index).clazz);
            transaction.add(R.id.content_view, mSurfaceParams.get(index).fragment);
        } else {
            transaction.show(mSurfaceParams.get(index).fragment);
        }
        transaction.commit();
        return getString(mSurfaceParams.get(index).titleId);
    }

    private static class SurfaceParam {
        private Class clazz;
        private int titleId;
        private Fragment fragment;

        public SurfaceParam(Class clazz, int titleId) {
            this.clazz = clazz;
            this.titleId = titleId;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < mSurfaceParams.size(); i++) {
            mSurfaceParams.valueAt(i).fragment = null;
        }
    }

    @Override
    public void onBackPressed() {
//        upData();
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            JSONObject jsonObject=new JSONObject();
            int id=Preferences.getInstance(this).getPreference("userBehavier", 0);
            long initStartTime=Preferences.getInstance(this).getPreference("initStartTime", System.currentTimeMillis());
//                jsonObject.put("id",id);
//                jsonObject.put("times", TimeTools.getNianTime());
            long timelong=(System.currentTimeMillis()-initStartTime)/1000;
//                jsonObject.put("timeLong", timelong);
            try {
                httpGet(Constants.APP_GET_MALL_OF_UPDATEUSERBEHAVIOURINFO+"?id="+id+"&times="+ URLEncoder.encode(TimeTools.getNianTime(),"UTF-8")+"&timeLong="+timelong, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ((TrackApplication) getApplication()).exitApp();
        }
    }

    Preferences pref;

    @TrackClick(value = R.id.img_right, location = "", eventName = "搜索界面")
    private void clickSeach(View v) {
        startActivity(new Intent(this, SeacherActivity.class));
    }

    @TrackClick(value = R.id.enter_floating, location = "", eventName = "签到")
    private void clickSignin(View v) {
        if (!UpdataUserInfo.isLogIn(this, true,null)) {
            return;
        }
        httpGet(Constants.URL_SIGNIN + "?userId=" + Constants.CURRENT_USER.getData().getAccount().getId() + "&taskType=1&taskValue=1", new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                Toast.makeText(getApplicationContext(), "签到成功", Toast.LENGTH_SHORT).show();
                mfloatView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                mfloatView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSgin();
    }
    private void upData(){
        Intent serviceIntent=new Intent(this, DBService.class);
        serviceIntent.putExtra("type","updataall");
        startService(serviceIntent);
    }


    private void checkSgin() {
//        httpGet("http://172.16.3.49:8080/api/v1//newuser/test?userId=198", new HttpCallback() {
//
//            @Override
//            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthSuccess(result, obj);
//            }
//
//            @Override
//            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthFailure(result, obj);
//            }
//        });
        if (Constants.CURRENT_USER != null) {
            httpGet(Constants.APP_GET_TASKSTATUS + CURRENT_USER.getData().getAccount().getId() + "&taskType=1&taskValue=1", new HttpCallback() {

                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        JSONObject jsonObject = new JSONObject(obj.getString("data"));
                        if (jsonObject.optInt("status") == 1) {
                            mfloatView.setVisibility(View.INVISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    mfloatView.setVisibility(View.VISIBLE);
                }
            });
//            httpGet(Constants.URL_CHECK_SIGNIN + Constants.CURRENT_USER.getData().getAccount().getId(), new HttpCallback() {
//                @Override
//                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
//                    super.doAuthSuccess(result, obj);
//                    mfloatView.setVisibility(View.INVISIBLE);
//                }
//
//                @Override
//                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
//                    super.doAuthFailure(result, obj);
//                    mfloatView.setVisibility(View.VISIBLE);
//                }
//            });
            UserInfoBean.DataBean.AccountBean accountBean = CURRENT_USER.getData().getAccount();
            if (!accountBean.getPortrait().equals("")) {
                if (!accountBean.getNickName().equals("")) {
                    if (accountBean.getSex() < 3) {
                        if (!accountBean.getBirthday().equals("")) {
                            if (!accountBean.getAutograph().equals("")) {
                                httpGet(Constants.APP_PERFECT_USERINFO + accountBean.getId() + "&taskType=2&taskValue=4", new HttpCallback() {
                                    @Override
                                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                        super.doAuthSuccess(result, obj);
                                    }

                                    @Override
                                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                        super.doAuthFailure(result, obj);
                                    }
                                });
                            }
                        }
                    }
                }

            }
        }


    }


}