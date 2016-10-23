package com.wodm.android.ui.newview;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.SeacherActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;
import org.json.JSONException;
import org.json.JSONObject;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/10/12.
 */
@Layout(R.layout.activity_task)
public class TaskActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener{
    @ViewIn(R.id.scrollView)
    private ScrollView scrollView;
    @ViewIn(R.id.top_task)
    private AtyTopLayout top_task;
    @ViewIn(R.id.ll_top_task)
    private LinearLayout ll_top_task;
    @ViewIn(R.id.btn_slow)
    private Button btn_slow;
    @ViewIn(R.id.btn_open_vip)
    private Button btn_open_vip;
    @ViewIn(R.id.btn_qiandao)
    private Button btn_qiandao;
    @ViewIn(R.id.top_line_view)
    private View top_line_view;
    @ViewIn(R.id.img_heade)
    private CircularImage img_heade;
    @ViewIn(R.id.total_day)
    private TextView total_day;
    @ViewIn(R.id.lianxu_qiandao)
    private TextView lianxu_qiandao;
    @ViewIn(R.id.rl_open_vip)
    private RelativeLayout rl_open_vip;

    //任务
    @ViewIn(R.id.tv_task_item_value)
    private TextView tv_task_item_value;
    @ViewIn(R.id.img_right)
    private ImageView img_right;
    @ViewIn(R.id.tv_task_item_value1)
    private TextView tv_task_item_value1;
    @ViewIn(R.id.img_right1)
    private ImageView img_right1;
    @ViewIn(R.id.tv_task_item_value2)
    private TextView tv_task_item_value2;
    @ViewIn(R.id.img_right2)
    private ImageView img_right2;
    @ViewIn(R.id.tv_task_item_value3)
    private TextView tv_task_item_value3;
    @ViewIn(R.id.img_right3)
    private ImageView img_right3;
    @ViewIn(R.id.tv_task_item_value4)
    private TextView tv_task_item_value4;
    @ViewIn(R.id.img_right4)
    private ImageView img_right4;
    @ViewIn(R.id.tv_task_item_value5)
    private TextView tv_task_item_value5;
    @ViewIn(R.id.img_right5)
    private ImageView img_right5;
    @ViewIn(R.id.ll_search)
    private LinearLayout ll_search;
    @ViewIn(R.id.ll_complete_info)
    private LinearLayout ll_complete_info;
    @ViewIn(R.id.ll_qiandao)
    private LinearLayout ll_qiandao;
    private int qiandao_day=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        top_task.setOnTopbarClickListenter(this);
        btn_qiandao.setOnClickListener(this);
        btn_open_vip.setOnClickListener(this);
        ll_qiandao.setOnClickListener(this);
        ll_complete_info.setOnClickListener(this);
        ll_search.setOnClickListener(this);
//        adapter = new TaskAdapter(this);
//        lv_task.setAdapter(adapter);

        initUserInfo();
        getWeatherQiandao();
    }


    private void initUserInfo() {
        if (CURRENT_USER == null) {
            finish();
            return;
        }
        UserInfoBean.DataBean dataBean = CURRENT_USER.getData();
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(img_heade, dataBean.getAccount().getPortrait());
        qiandao_day=dataBean.getCheckinCount();
        total_day.setText(qiandao_day+ "");

        lianxu_qiandao.setText(dataBean.getCheckinCount() + "");
        if (dataBean.getAccount().getVip() != 0) {
            rl_open_vip.setVisibility(View.GONE);
        }
    }

    private void getWeatherQiandao() {
        if (CURRENT_USER == null) {
            finish();
            return;
        }
        httpGet(Constants.APP_GET_TASKSTATUS + CURRENT_USER.getData().getAccount().getId() + "&taskType=1&taskValue=1", new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    JSONObject jsonObject = new JSONObject(obj.getString("data"));
                    if (jsonObject.optInt("status") == 1) {
                        initfinsh();
                    }else {
                        img_right1.setVisibility(View.GONE);
                        tv_task_item_value3.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                        tv_task_item_value3.setTextColor(getResources().getColor(R.color.color_ee984a));
                        tv_task_item_value3.setTextSize(14);
                        tv_task_item_value3.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
    }

    private void initfinsh() {
        total_day.setText((qiandao_day+1)+ "");
        top_line_view.setBackgroundColor(getResources().getColor(R.color.color_ef9429));
        top_task.setBackgroundResource("#ffa031");
        ll_top_task.setBackgroundResource(R.mipmap.qiandao_yellow_bg);
        btn_slow.setBackgroundResource(R.drawable.task_solw_finish);
        btn_slow.setText("快");
        btn_qiandao.setBackgroundResource(R.drawable.btn_task_qiandao_finish);
        btn_qiandao.setText("已签到");
        btn_qiandao.setFocusable(false);
        img_right3.setVisibility(View.VISIBLE);
        tv_task_item_value3.setVisibility(View.GONE);
    }
    private String[] personArray = {"完善个人资料", "使用搜索功能", "查看一次资讯", "观看动漫", "发评论"};
    @Override
    protected void onResume() {
        super.onResume();
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        }
        for (int i = 0; i <personArray.length ; i++) {
            getData(personArray[i]);
        }
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    private void QianDao() {
        httpGet(Constants.URL_SIGNIN + "?userId=" + Constants.CURRENT_USER.getData().getAccount().getId() + "&taskType=1&taskValue=1", new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                Toast.makeText(getApplicationContext(), "签到成功", Toast.LENGTH_SHORT).show();
                initfinsh();
            }
        });
    }
    private void getData(final String value) {
        String url = Constants.APP_GET_TASKSTATUS + CURRENT_USER.getData().getAccount().getId();
        String dataurl="";
        if (value.equals("完善个人资料")) {
             dataurl = url + "&taskType=2&taskValue=4";
        } else if (value.equals("使用搜索功能")) {
             dataurl = url + "&taskType=2&taskValue=5";
        }
        else if (value.equals("查看一次资讯")) {
             dataurl = url + "&taskType=2&taskValue=7";
        } else if (value.equals("观看动漫")) {
             dataurl = url + "&taskType=1&taskValue=2";
        } else if (value.equals("发评论")) {
             dataurl = url + "&taskType=1&taskValue=3";
        }
        httpGet(dataurl, new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    JSONObject jsonObject = new JSONObject(obj.getString("data"));
                    int status=jsonObject.optInt("status");
                    if (value.equals("完善个人资料")) {
                        if (status==1){
                            img_right.setVisibility(View.VISIBLE);
                            tv_task_item_value.setVisibility(View.GONE);
                        }else {
                            img_right.setVisibility(View.GONE);
                            tv_task_item_value.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                            tv_task_item_value.setTextColor(getResources().getColor(R.color.color_ee984a));
                            tv_task_item_value.setTextSize(14);
                            tv_task_item_value.setVisibility(View.VISIBLE);
                        }
                    } else if (value.equals("使用搜索功能")) {
                        if (status==1){
                            img_right1.setVisibility(View.VISIBLE);
                            tv_task_item_value1.setVisibility(View.GONE);
                        }else {
                            img_right1.setVisibility(View.GONE);
                            tv_task_item_value1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                            tv_task_item_value1.setTextColor(getResources().getColor(R.color.color_ee984a));
                            tv_task_item_value1.setTextSize(14);
                            tv_task_item_value1.setVisibility(View.VISIBLE);
                        }
                    }
                    else if (value.equals("查看一次资讯")) {
                        if (status==1){
                            img_right2.setVisibility(View.VISIBLE);
                            tv_task_item_value2.setVisibility(View.GONE);
                        }else {
                            img_right2.setVisibility(View.GONE);
                            tv_task_item_value2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                            tv_task_item_value2.setTextColor(getResources().getColor(R.color.color_ee984a));
                            tv_task_item_value2.setTextSize(14);
                            tv_task_item_value2.setVisibility(View.VISIBLE);
                        }

                    } else if (value.equals("观看动漫")) {
                        if (status==1){
                            img_right4.setVisibility(View.VISIBLE);
                            tv_task_item_value4.setVisibility(View.GONE);
                        }else {
                            tv_task_item_value4.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                            tv_task_item_value4.setTextColor(getResources().getColor(R.color.color_ee984a));
                            tv_task_item_value4.setTextSize(14);
                            img_right4.setVisibility(View.GONE);
                            tv_task_item_value4.setVisibility(View.VISIBLE);
                        }
                    } else if (value.equals("发评论")) {
                        if (status==1){
                            img_right5.setVisibility(View.VISIBLE);
                            tv_task_item_value5.setVisibility(View.GONE);
                        }else {
                            tv_task_item_value5.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                            tv_task_item_value5.setTextColor(getResources().getColor(R.color.color_ee984a));
                            tv_task_item_value5.setTextSize(14);
                            img_right5.setVisibility(View.GONE);
                            tv_task_item_value5.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_qiandao:
                QianDao();
                break;
            case R.id.btn_open_vip:
                startActivity(new Intent(this, VipOpenActivity.class));
                break;
            case R.id.ll_search:
                startActivity(new Intent(this, SeacherActivity.class));
                break;
            case R.id.ll_complete_info:
                startActivity(new Intent(this, NewUserInfoActivity.class));
                break;
            case R.id.ll_qiandao:
                QianDao();
                break;
        }
    }


}
