package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
>>>>>>> afd1e829edf4b6b1dd9e7c7614d2c79e4fffe9f2

import com.wodm.R;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/10/8.
 */
<<<<<<< HEAD
@Layout(R.layout.aty_new_vip)
public class VipActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter {
//    @ViewIn(R.id.user_head_imgs)
//    private CircularImage user_head_imgs;
//    @ViewIn(R.id.vip_user_head_imgs)
//    private CircularImage vip_user_head_imgs;
//    @ViewIn(R.id.tv_vip_name)
//    private TextView tv_vip_name;
=======
@Layout(R.layout.aty_vip)
public class VipActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener {
    @ViewIn(R.id.user_head_imgs)
    private CircularImage user_head_imgs;
    @ViewIn(R.id.vip_user_head_imgs)
    private CircularImage vip_user_head_imgs;
    @ViewIn(R.id.tv_vip_name)
    private TextView tv_vip_name;
>>>>>>> afd1e829edf4b6b1dd9e7c7614d2c79e4fffe9f2
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout set_topbar;
    @ViewIn(R.id.btn_open_vip)
    Button btn_open_vip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (CURRENT_USER == null) {
//            finish();
//            return;
//        }
        set_topbar.setOnTopbarClickListenter(this);
<<<<<<< HEAD

=======
        UserInfoBean.DataBean.AccountBean accountBean = CURRENT_USER.getData().getAccount();
        if (accountBean.getVip() == 0) {
            vip_user_head_imgs.setBackgroundResource(R.mipmap.circle_novip);
        } else {
            vip_user_head_imgs.setBackgroundResource(R.mipmap.circlr_vip);
        }
        tv_vip_name.setText(accountBean.getNickName());
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(user_head_imgs, CURRENT_USER.getData().getAccount().getPortrait());

        btn_open_vip.setOnClickListener(this);
>>>>>>> afd1e829edf4b6b1dd9e7c7614d2c79e4fffe9f2
    }
//   private void setUserInfo(){
//       UserInfoBean.DataBean.AccountBean accountBean=CURRENT_USER.getData().getAccount();
//       if (accountBean.getVip()==0){
//           vip_user_head_imgs.setBackgroundResource(R.mipmap.circle_novip);
//       }else{
//           vip_user_head_imgs.setBackgroundResource(R.mipmap.circlr_vip);
//       }
//       tv_vip_name.setText(accountBean.getNickName());
//       new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(user_head_imgs, CURRENT_USER.getData().getAccount().getPortrait());
//    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), VipOpenActivity.class));
    }
}
