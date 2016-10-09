package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.PersionAdapter;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.user.UserInfoActivity;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;

/**
 * Created by songchenyu on 16/10/8.
 */
@Layout(R.layout.aty_persion)
public class PersionActivity extends AppActivity implements View.OnClickListener {
    @ViewIn(R.id.gv_comments)
    private MyGridView gv_comments;
    private PersionAdapter persionAdapter;
    @ViewIn(R.id.scrllow_mine)
    private ScrollView scrollow;
    @ViewIn(R.id.btn_user_info)
    private Button btn_user_info;
    @ViewIn(R.id.user_head_imgs)
    private ImageView user_head_imgs;
    @ViewIn(R.id.tv_nickname)
    private TextView tv_nickname;
    @ViewIn(R.id.img_sex)
    private ImageView img_sex;
    @ViewIn(R.id.btn_degree)
    private Button btn_degree;
    @ViewIn(R.id.tv_sign)
    private TextView tv_sign;
    @ViewIn(R.id.degree_progress)
    private ProgressBar degree_progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        persionAdapter=new PersionAdapter(this);
        gv_comments.setAdapter(persionAdapter);
        scrollow.setFocusable(true);
        scrollow.setFocusableInTouchMode(true);
        scrollow.requestFocus();
        btn_user_info.setOnClickListener(this);
        btn_degree.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserInfo();
    }
    private void setUserInfo() {
        if (Constants.CURRENT_USER == null) {
            finish();
            return;
        }
        UserInfoBean.DataBean.AccountBean accountBean=Constants.CURRENT_USER.getData().getAccount();
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(user_head_imgs, accountBean.getPortrait());
        tv_nickname.setText(accountBean.getNickName());
        int sex_value=accountBean.getSex();
        if (sex_value == 0) {
            img_sex.setBackgroundResource(R.drawable.sex_radio_baomi);
        } else if (sex_value == 1) {
            img_sex.setBackgroundResource(R.mipmap.sex_man);
        } else {
            img_sex.setBackgroundResource(R.mipmap.sex_women);
        }
        btn_degree.setText("LV"+accountBean.getGradeValue());
        tv_sign.setText(accountBean.getAutograph()+"");
        UserInfoBean.DataBean dataBean=Constants.CURRENT_USER.getData();
        int progress=dataBean.getCurrentEmpirical()/(dataBean.getNextGradeEmpirical()+dataBean.getCurrentEmpirical());
        degree_progress.setProgress(progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_user_info:
                startActivity(new Intent(this, UserInfoActivity.class));
                break;
            case R.id.btn_degree:
                break;
        }
    }
}
