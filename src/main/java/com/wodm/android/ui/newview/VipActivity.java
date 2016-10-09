package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/10/8.
 */
@Layout(R.layout.aty_vip)
public class VipActivity extends AppActivity {
    @ViewIn(R.id.user_head_imgs)
    private CircularImage user_head_imgs;
    @ViewIn(R.id.tv_vip_name)
    private TextView tv_vip_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CURRENT_USER == null) {
            finish();
            return;
        }
        UserInfoBean.DataBean.AccountBean accountBean=CURRENT_USER.getData().getAccount();
//        if (accountBean.getVip()==0){
//
//        }
        tv_vip_name.setText(accountBean.getNickName());
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(user_head_imgs, CURRENT_USER.getData().getAccount().getPortrait());
    }
}
