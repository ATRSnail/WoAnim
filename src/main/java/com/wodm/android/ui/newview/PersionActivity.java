package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.http.RequestParams;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.MineCircleAdapter;
import com.wodm.android.adapter.newadapter.PersionAdapter;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;


/**
 * Created by songchenyu on 16/10/8.
 */
@Layout(R.layout.aty_persion)
public class PersionActivity extends AppActivity implements View.OnClickListener, AtyTopLayout.myTopbarClicklistenter {
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout set_topbar;
    @ViewIn(R.id.gv_comments)
    private MyGridView gv_comments;
    @ViewIn(R.id.gv_quanzi)
    private MyGridView gv_quanzi;
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
    private MineCircleAdapter mineCircleAdapter;
    @ViewIn(R.id.tv_num)
    TextView tv_num;
    @ViewIn(R.id.empiral_degree)
    TextView empiral_degree;
    @ViewIn(R.id.grade_name_persion)
    Button grade_name_persion;
    @ViewIn(R.id.my_medal_persion)
    LinearLayout linearLayout;
    @ViewIn(R.id.img_persion_progress)
    ImageView img_persion_progress;

    //    @ViewIn(R.id.btn_degree)
//    private Button btn_degree;
    @ViewIn(R.id.tv_sign)
    private TextView tv_sign;
//    @ViewIn(R.id.degree_progress)
//    private ProgressBar degree_progress;

    int[] medalImage = new int[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        persionAdapter = new PersionAdapter(this);
        gv_comments.setAdapter(persionAdapter);
        mineCircleAdapter = new MineCircleAdapter(this);
        gv_quanzi.setAdapter(mineCircleAdapter);
        scrollow.setFocusable(true);
        scrollow.setFocusableInTouchMode(true);
        scrollow.requestFocus();
        btn_user_info.setOnClickListener(this);
//        btn_degree.setOnClickListener(this);
        set_topbar.setOnTopbarClickListenter(this);

    }

    private void initMyMedal() {

        RequestParams params = new RequestParams();
//        params.addBodyParameter("userId", accountBean.getId() + "");
        params.addBodyParameter("medalType", "0");
        params.addBodyParameter("medalSource", "1");

//        Log.i("AAAA",.getT_user_medal);


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
        UserInfoBean.DataBean.AccountBean accountBean = Constants.CURRENT_USER.getData().getAccount();
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(user_head_imgs, accountBean.getPortrait());
        tv_nickname.setText(accountBean.getNickName());
        int sex_value = accountBean.getSex();
        if (sex_value == 0) {
            img_sex.setBackgroundResource(R.drawable.sex_radio_baomi);
        } else if (sex_value == 1) {
            img_sex.setBackgroundResource(R.mipmap.sex_man);
        } else {
            img_sex.setBackgroundResource(R.mipmap.sex_women);
        }
//        btn_degree.setText("LV"+accountBean.getGradeValue());
        tv_sign.setText(accountBean.getAutograph() + "");
        UserInfoBean.DataBean dataBean = Constants.CURRENT_USER.getData();
        int total = dataBean.getNextGradeEmpirical() + dataBean.getCurrentEmpirical();
        int progress = 0;
        if (total != 0) {
            progress = dataBean.getCurrentEmpirical() / total;
        }
//        degree_progress.setProgress(progress);
        int next_num=dataBean.getNextGradeEmpirical();
        int need_num=dataBean.getNeedEmpirical();
        int num= (int) (110*(1-((float)need_num/next_num)));
        RelativeLayout.LayoutParams img_progress_params=new RelativeLayout.LayoutParams(num,30);
        img_progress_params.setMargins(0,5,0,5);
        img_persion_progress.setLayoutParams(img_progress_params);

        String gradename = accountBean.getGradeName();
        if (!TextUtils.isEmpty(gradename)) {
            grade_name_persion.setText(gradename);
        }

        String grad = String.valueOf(accountBean.getGradeValue());
        if (!TextUtils.isEmpty(grad)) {
            tv_num.setText(grad);
        }
        empiral_degree.setText(dataBean.getCurrentEmpirical()+"/"+dataBean.getNextGradeEmpirical());

//        initMyMedal(accountBean);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user_info:
                startActivity(new Intent(this, NewUserInfoActivity.class));
                break;
        }
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
