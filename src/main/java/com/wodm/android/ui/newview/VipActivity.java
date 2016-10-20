package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;
import org.json.JSONObject;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/10/8.
 */
@Layout(R.layout.aty_new_vip)
public class VipActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,View.OnClickListener {
    @ViewIn(R.id.user_head_imgs)
    private CircularImage user_head_imgs;
    @ViewIn(R.id.vip_user_head_imgs)
    private CircularImage vip_user_head_imgs;
    @ViewIn(R.id.tv_user_name)
    private TextView tv_user_name;
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout set_topbar;
    @ViewIn(R.id.btn_open_vip)
    private Button btn_open_vip;
    @ViewIn(R.id.rl_isVip)
    private RelativeLayout rl_isVip;
    @ViewIn(R.id.rl_noVip)
    private RelativeLayout rl_noVip;
    @ViewIn(R.id.tv_no_vip_username)
    private TextView tv_no_vip_username;
    @ViewIn(R.id.tv_speed)
    private TextView tv_speed;
    @ViewIn(R.id.btn_grade_name)
    private TextView btn_grade_name;
    @ViewIn(R.id.tv_endof_vip_num)
    private TextView tv_endof_vip_num;
    @ViewIn(R.id.img_sex)
    private ImageView img_sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CURRENT_USER == null) {
            finish();
            return;
        }
        btn_open_vip.setOnClickListener(this);
        set_topbar.setOnTopbarClickListenter(this);
        setUserInfo();
    }
    private void setUserInfo(){
        httpGet(Constants.APP_GET_VIP_TIME+CURRENT_USER.getData().getAccount().getId(), new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                String day=obj.optString("data");
                tv_endof_vip_num.setText(day+"");
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
        UserInfoBean.DataBean.AccountBean accountBean=CURRENT_USER.getData().getAccount();
        if (accountBean.getVip()==0){
            rl_isVip.setVisibility(View.GONE);
            rl_noVip.setVisibility(View.VISIBLE);
            vip_user_head_imgs.setBackgroundResource(R.mipmap.circle_novip);
        }else{
            rl_isVip.setVisibility(View.VISIBLE);
            rl_noVip.setVisibility(View.GONE);
//            vip_user_head_imgs.setBackgroundResource(R.mipmap.circlr_vip);
        }
        String gradename = accountBean.getGradeName();
        if (!TextUtils.isEmpty(gradename)) {
            btn_grade_name.setText(gradename);
        }
        int sex_value = accountBean.getSex();
        if (sex_value == 0) {
            img_sex.setImageResource(R.drawable.sex_radio_baomi);
        } else if (sex_value == 1) {
            img_sex.setImageResource(R.mipmap.sex_man);
        } else {
            img_sex.setImageResource(R.mipmap.sex_women);
        }
        tv_speed.setText("LV."+accountBean.getGradeValue());
        tv_no_vip_username.setText(accountBean.getNickName());
        tv_user_name.setText(accountBean.getNickName());
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(user_head_imgs, CURRENT_USER.getData().getAccount().getPortrait());
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
            case R.id.btn_open_vip:
                startActivity(new Intent(this,VipOpenActivity.class));
                break;
        }
    }
}
