package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.TaskAdapter;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.NoScrollListView;

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
public class TaskActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter ,View.OnClickListener{
    @ViewIn(R.id.scrollView)
    private ScrollView scrollView;
    @ViewIn(R.id.lv_task)
    private NoScrollListView lv_task;
    private TaskAdapter adapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        top_task.setOnTopbarClickListenter(this);
        btn_qiandao.setOnClickListener(this);
        btn_open_vip.setOnClickListener(this);
        adapter=new TaskAdapter(this);
        lv_task.setAdapter(adapter);
        initUserInfo();
        getWeatherQiandao();
    }
    private void initUserInfo(){
        if (CURRENT_USER == null){
            finish();
            return;
        }
        UserInfoBean.DataBean.AccountBean accountBean = CURRENT_USER.getData().getAccount();
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(img_heade, accountBean.getPortrait());
        total_day.setText(accountBean.getMaxCheckinCount()+"");
        lianxu_qiandao.setText(accountBean.getCheckinCount()+"");
        if (accountBean.getVip()!=0){
            rl_open_vip.setVisibility(View.GONE);
        }
    }
    private void getWeatherQiandao(){
        if (CURRENT_USER==null){
            finish();
            return;
        }
        httpGet(Constants.APP_GET_TASKSTATUS+CURRENT_USER.getData().getAccount().getId()+"&taskType=1&taskValue=1", new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    JSONObject jsonObject=new JSONObject(obj.getString("data"));
                    if (jsonObject.optInt("status")== 1){
                       initfinsh();
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
    private void initfinsh(){
        top_line_view.setBackgroundColor(getResources().getColor(R.color.color_ef9429));
        top_task.setBackgroundResource("#ffa031");
        ll_top_task.setBackgroundResource(R.mipmap.qiandao_yellow_bg);
        btn_slow.setBackgroundResource(R.drawable.task_solw_finish);
        btn_slow.setText("快");
        btn_qiandao.setBackgroundResource(R.drawable.btn_task_qiandao_finish);
        btn_qiandao.setText("已签到");
        btn_qiandao.setFocusable(false);
        if (adapter!=null){
            adapter.setQiandaoType();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        }

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
        switch (v.getId()){
            case R.id.btn_qiandao:
                httpGet(Constants.URL_SIGNIN + "?userId=" + Constants.CURRENT_USER.getData().getAccount().getId(), new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        Toast.makeText(getApplicationContext(), "签到成功", Toast.LENGTH_SHORT).show();
                        initfinsh();
                    }
                });
                break;
            case R.id.btn_open_vip:
                startActivity(new Intent(this,VipOpenActivity.class));
                break;
        }
    }
}
