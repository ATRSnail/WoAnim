package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.NewMineAdapter;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.tools.DegreeTools;
import com.wodm.android.tools.MallConversionUtil;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.Main2Activity;
import com.wodm.android.utils.UpdataMedalInfo;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.newview.NoScrollListView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.fragment.TrackFragment;
import org.eteclab.ui.widget.CircularImage;
import org.json.JSONObject;

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
    @ViewIn(R.id.mine_up)
    private RelativeLayout mine_up;
    @ViewIn(R.id.no_login)
    private RelativeLayout no_login;
    @ViewIn(R.id.user_head_imgs)
    private CircularImage user_head_imgs;
    @ViewIn(R.id.user_txk)
    private ImageView user_txk;
    @ViewIn(R.id.user_gj)
    private CircularImage user_gj;
    @ViewIn(R.id.tv_user_name)
    private TextView tv_user_name;
    @ViewIn(R.id.tv_sign)
    private TextView tv_sign;
    //    @ViewIn(R.id.tv_attention)
//    private TextView tv_attention;
//    @ViewIn(R.id.tv_likes)
    private TextView tv_likes;
    @ViewIn(R.id.ll_vip)
    private LinearLayout ll_vip;
    @ViewIn(R.id.ll_degree)
    private LinearLayout ll_degree;
    @ViewIn(R.id.ll_my_wallet)
    private LinearLayout ll_my_wallet;
    @ViewIn(R.id.img_sex)
    private ImageView img_sex;
    @ViewIn(R.id.img_right)
    private ImageView img_right;
    @ViewIn(R.id.grade_name)
    Button grade_name;
    @ViewIn(R.id.tv_num)
    TextView tv_num;
    @ViewIn(R.id.img_progress)
    ImageView img_progress;
    @ViewIn(R.id.img_vip_circle)
    private ImageView img_vip_circle;
    @ViewIn(R.id.ll_head_image)
    private RelativeLayout ll_head_image;
    @Override
    protected void setDatas(Bundle bundle) {
        newMineAdapter = new NewMineAdapter(getActivity());
        noScrollListView.setAdapter(newMineAdapter);
        ll_head_image.setOnClickListener(this);
        img_right.setOnClickListener(this);
        no_login.setOnClickListener(this);
        rl_login.setOnClickListener(this);
        ll_vip.setOnClickListener(this);
        ll_degree.setOnClickListener(this);
        ll_my_wallet.setOnClickListener(this);

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
        getMedalData();
    }


    private void getMedalData() {

        if (Constants.CURRENT_USER != null) {
            UpdataMedalInfo.getMedalInfo(getActivity(), Constants.CURRENT_USER.getData().getAccount().getId());
        }
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mine_up.setLayoutParams(params);
            no_login.setVisibility(View.VISIBLE);
        } else {
            UserInfoBean.DataBean.AccountBean accountBean = CURRENT_USER.getData().getAccount();
            no_login.setVisibility(View.GONE);
            rl_login.setVisibility(View.VISIBLE);
            tv_user_name.setText(accountBean.getNickName());
            String sign_name = accountBean.getAutograph();
            if (!TextUtils.isEmpty(sign_name)) {
                tv_sign.setText(sign_name);
            }
            String gradename = accountBean.getGradeName();
            if (!TextUtils.isEmpty(gradename)) {
                grade_name.setText(gradename);

            }
            DegreeTools.getInstance(getActivity()).getDegree(getActivity(),accountBean.getGradeValue(),grade_name);
            String grad = String.valueOf(accountBean.getGradeValue());
            if (!TextUtils.isEmpty(grad)) {
                tv_num.setText(grad);
            }
            int sex_value = accountBean.getSex();
            if (sex_value == 0) {
                img_sex.setBackgroundResource(R.drawable.sex_radio_baomi);
            } else if (sex_value == 1) {
                img_sex.setBackgroundResource(R.mipmap.sex_man);
            } else {
                img_sex.setBackgroundResource(R.mipmap.sex_women);
            }
            updataDataBean(Constants.CURRENT_USER.getData().getAccount().getId());
        }
    }

    void updataDataBean(long userId) {
        ((AppActivity)getActivity()).httpGet(Constants.APP_GET_USERINFO + userId, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                UserInfoBean bean = new Gson().fromJson(obj.toString(), UserInfoBean.class);
                Constants.CURRENT_USER = bean;
                UserInfoBean.DataBean dataBean=CURRENT_USER.getData();
//                System.out.println("ddii--->"+dataBean.getAccount().toString());
                int next_num=dataBean.getNextGradeEmpirical();
                int need_num=dataBean.getNeedEmpirical();
                int num= (int) (110*(1-((float)need_num/next_num)));
                RelativeLayout.LayoutParams img_progress_params=new RelativeLayout.LayoutParams(num, RelativeLayout.LayoutParams.MATCH_PARENT);
                img_progress_params.setMargins(0,5,0,5);
                img_progress.setLayoutParams(img_progress_params);
                int vip=dataBean.getAccount().getVip();
                if (vip==1){
                    img_vip_circle.setImageResource(R.mipmap.vip_circle);
                }else if (vip==2){
                    img_vip_circle.setImageResource(R.mipmap.vvip_circle);
                }else {
                    img_vip_circle.setVisibility(View.INVISIBLE);
                }
                new AsyncImageLoader(getActivity(), R.mipmap.touxiang_moren, R.mipmap.moren_header).display(user_head_imgs, dataBean.getAccount().getPortrait());
                try {
                    MallConversionUtil.getInstace().dealExpression(getActivity(), dataBean.getPandentDetail().getNameTXK(), user_txk, dataBean.getPandentDetail().getImgUrlTXK());
                } catch (Exception e) {
                    Glide.with(getActivity()).load(dataBean.getPandentDetail().getNameGJ()).placeholder(R.mipmap.loading).into(user_txk);
                    e.printStackTrace();
                }
                try {
                    MallConversionUtil.getInstace().dealExpression(getActivity(), dataBean.getPandentDetail().getNameGJ(), user_gj, dataBean.getPandentDetail().getImgUrlGJ());
                } catch (Exception e) {
                    Glide.with(getActivity()).load(dataBean.getPandentDetail().getNameGJ()).placeholder(R.mipmap.loading).into(user_gj);
                    e.printStackTrace();
                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_right:
                startActivity(new Intent(getActivity(), PersionActivity.class));
                break;
            case R.id.rl_login:
                startActivity(new Intent(getActivity(), PersionActivity.class));
                break;
            case R.id.no_login:
                Intent intent22=new Intent();
                intent22.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent22.setClass(getActivity(), Main2Activity.class);
                Intent intent = new Intent(getActivity(), LgoinActivity.class);
                intent.putExtra("intentclass",intent22);
                startActivity(intent);
                break;
            case R.id.ll_vip:
                Intent i=new Intent();
                i.setClass(getActivity(), NewVipActivity.class);
                if (!UpdataUserInfo.isLogIn(getActivity(), true,i)) {
                Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                return;
                 }
                startActivity(i);
                break;
            case R.id.ll_degree:
                Intent i2=new Intent();
                i2.setClass(getActivity(), MyLevelActivity.class);
                if (!UpdataUserInfo.isLogIn(getActivity(), true, i2)) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(i2);
                break;
            case R.id.ll_my_wallet:
                Intent i3=new Intent();
                i3.setClass(getActivity(), MyWalletActivity.class);
                if (!UpdataUserInfo.isLogIn(getActivity(), true, i3)) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(i3);
                break;     
            case R.id.ll_head_image:
                Intent i4=new Intent();
                i4.setClass(getActivity(), AllOfMineWallActivity.class);
                startActivity(i4);
                break;

        }
    }
}
