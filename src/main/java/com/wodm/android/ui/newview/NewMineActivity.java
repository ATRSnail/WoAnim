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
import com.wodm.android.ui.user.UserInfoActivity;
import com.wodm.android.view.newview.NoScrollListView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.fragment.TrackFragment;
import org.eteclab.ui.widget.CircularImage;

/**
 * Created by songchenyu on 16/9/26.
 */
@Layout(R.layout.new_aty_mine)
public class NewMineActivity extends TrackFragment implements View.OnClickListener{
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


    @Override
    protected void setDatas(Bundle bundle) {
        newMineAdapter=new NewMineAdapter(getActivity());
        noScrollListView.setAdapter(newMineAdapter);
        scrollow.setFocusable(true);
        scrollow.setFocusableInTouchMode(true);
        scrollow.requestFocus();
        no_login.setOnClickListener(this);
        rl_login.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init(){
        if (Constants.CURRENT_USER == null) {
            rl_login.setVisibility(View.GONE);
            no_login.setVisibility(View.VISIBLE);
        } else {
            no_login.setVisibility(View.GONE);
            rl_login.setVisibility(View.VISIBLE);
            tv_user_name.setText(Constants.CURRENT_USER.getNickName());
            String sign_name=Constants.CURRENT_USER.getAutograph();
            if (!TextUtils.isEmpty(sign_name)){
                tv_sign.setText(sign_name);
            }

            new AsyncImageLoader(getActivity(), R.mipmap.default_header, R.mipmap.default_header).display(user_head_imgs, Constants.CURRENT_USER.getPortrait());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_login:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.no_login:
                Intent intent =new Intent(getActivity(),LgoinActivity.class);
                startActivity(intent);
                break;
        }
    }
}
