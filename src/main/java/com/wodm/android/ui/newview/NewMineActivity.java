package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.NewMineAdapter;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.user.UserInfoActivity;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.newview.NoScrollListView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.fragment.TrackFragment;
import org.eteclab.ui.widget.CircularImage;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/9/26.
 */
@Layout(R.layout.new_aty_mine)
public class NewMineActivity extends TrackFragment implements View.OnClickListener {
    @ViewIn(R.id.lv_noscroll)
    private NoScrollListView noScrollListView;
    @ViewIn(R.id.scrllow_mine)
    private ScrollView scrollow;
    private NewMineAdapter newMineAdapter;
    @ViewIn(R.id.rl_login)
    private RelativeLayout rl_login;
    @ViewIn(R.id.no_login)
    private RelativeLayout no_login;
    @ViewIn(R.id.user_head_imgs)
    private CircularImage user_head_imgs;
    @ViewIn(R.id.tv_user_name)
    private TextView tv_user_name;
    @ViewIn(R.id.tv_sign)
    private TextView tv_sign;
    @ViewIn(R.id.my_level)
    private TextView my_level;


    @Override
    protected void setDatas(Bundle bundle) {
        newMineAdapter = new NewMineAdapter(getActivity());
        noScrollListView.setAdapter(newMineAdapter);

        no_login.setOnClickListener(this);
        rl_login.setOnClickListener(this);
        my_level.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (scrollow != null) {
            scrollow.scrollTo(0, 0);
            scrollow.setFocusable(true);
            scrollow.setFocusableInTouchMode(true);
            scrollow.requestFocus();
        }
        init();
        getData();
    }

    private void getData() {
        if (CURRENT_USER != null) {
            long userId = CURRENT_USER.getData().getAccount().getId();
            infos.getUserInfo(getActivity(), userId);
        }
    }

    UpdataUserInfo infos = new UpdataUserInfo() {
        @Override
        public void getUserInfo(UserInfoBean bean) {
            Constants.CURRENT_USER = bean;
        }
    };


    private void init() {
        if (CURRENT_USER == null) {
            rl_login.setVisibility(View.GONE);
            no_login.setVisibility(View.VISIBLE);
        } else {
            no_login.setVisibility(View.GONE);
            rl_login.setVisibility(View.VISIBLE);
            tv_user_name.setText(CURRENT_USER.getData().getAccount().getNickName());
            String sign_name = CURRENT_USER.getData().getAccount().getAutograph();
            if (!TextUtils.isEmpty(sign_name)) {
                tv_sign.setText(sign_name);
            }
            new AsyncImageLoader(getActivity(), R.mipmap.default_header, R.mipmap.default_header).display(user_head_imgs, CURRENT_USER.getData().getAccount().getPortrait());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_login:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.no_login:
                Intent intent = new Intent(getActivity(), LgoinActivity.class);
                startActivity(intent);
                break;
            case R.id.my_level:
                Intent intent2 = new Intent(getActivity(), MyLevelActivity.class);
                startActivity(intent2);
        }
    }
}
