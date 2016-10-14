package com.wodm.android.ui.newview;

import android.os.Bundle;

import com.wodm.R;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/10/8.
 */
@Layout(R.layout.aty_new_vip)
public class VipActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter {
//    @ViewIn(R.id.user_head_imgs)
//    private CircularImage user_head_imgs;
//    @ViewIn(R.id.vip_user_head_imgs)
//    private CircularImage vip_user_head_imgs;
//    @ViewIn(R.id.tv_vip_name)
//    private TextView tv_vip_name;
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout set_topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (CURRENT_USER == null) {
//            finish();
//            return;
//        }
        set_topbar.setOnTopbarClickListenter(this);

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
}
