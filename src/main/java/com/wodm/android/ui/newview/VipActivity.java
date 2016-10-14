package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (CURRENT_USER == null) {
//            finish();
//            return;
//        }
        btn_open_vip.setOnClickListener(this);
        set_topbar.setOnTopbarClickListenter(this);
        setUserInfo();
    }
   private void setUserInfo(){
       UserInfoBean.DataBean.AccountBean accountBean=CURRENT_USER.getData().getAccount();
       if (accountBean.getVip()==0){
           vip_user_head_imgs.setBackgroundResource(R.mipmap.circle_novip);
       }else{
           vip_user_head_imgs.setBackgroundResource(R.mipmap.circlr_vip);
       }
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
