package com.wodm.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;

import org.eteclab.track.fragment.TrackFragment;

/**
 * Created by ATRSnail on 2016/10/12.
 */

public class LevelFragment extends TrackFragment {
    private UserInfoBean.DataBean dataBean;
    private int gradeValue;
    private String  gradeName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.level_circle_first, container, false);
        ViewHolder holder = new ViewHolder();

        dataBean = Constants.CURRENT_USER.getData();
        UserInfoBean.DataBean.AccountBean accountBean = dataBean.getAccount();
        gradeValue = accountBean.getGradeValue();
        String name = accountBean.getGradeName();
        initView(holder, view);
        if (gradeValue > 1) {
            holder.text_1.setText("LV" + (gradeValue - 1));
            if (!TextUtils.isEmpty(name))
                gradeName="LV"+gradeValue;
                holder.text_2.setText("LV"+ gradeValue+"\n萌呷");
            holder.text_3.setText("LV" + (gradeValue + 1));
            holder.text_4.setText("LV" + (gradeValue + 2));
            holder.text_5.setText("LV" + (gradeValue + 3));
        }
        return view;
    }

    @Override
    protected void setDatas(Bundle bundle) {

    }

//<<<<<<< HEAD
//    @Override
//    protected void setDatas(Bundle bundle) {
//=======
//    private void initView(ViewHolder holder, View view) {
//        holder.text_1 = (TextView) view.findViewById(R.id.leve_1);
//        holder.linearLayout = (LinearLayout) view.findViewById(R.id.ll_level);
//        holder.text_2 = (TextView) view.findViewById(R.id.leve_2);
//        holder.text_3 = (TextView) view.findViewById(R.id.leve_3);
//        holder.text_4 = (TextView) view.findViewById(R.id.leve_4);
//        holder.text_5 = (TextView) view.findViewById(R.id.leve_5);
//        holder.text_need = (TextView) view.findViewById(R.id.level_need);
////        holder.name_need = (TextView) view.findViewById(R.id.levelname_need);
////        holder.exp_need = (TextView) view.findViewById(R.id.levelexp_need);
//        holder.text_need.setText("距离LV" + (gradeValue + 1) + "还有" + dataBean.getNeedEmpirical() + "经验值");
////        holder.name_need.setText("距离LV" + (gradeValue + 1));
////        holder.exp_need.setText("还有" + dataBean.getNeedEmpirical() + "经验值");
//>>>>>>> 95aa3f69c6df6626cab110801f6a4fe4617a4884
//
//    }

    private void initView(ViewHolder holder, View view) {
        holder.ll_circle1 = (LinearLayout) view.findViewById(R.id.ll_circle1);
        holder.ll_circle2 = (LinearLayout) view.findViewById(R.id.ll_circle2);
        holder.ll_circle3 = (LinearLayout) view.findViewById(R.id.ll_circle3);
        holder.ll_circle4 = (LinearLayout) view.findViewById(R.id.ll_circle4);
        holder.ll_circle5 = (LinearLayout) view.findViewById(R.id.ll_circle5);
        holder.text_1 = (TextView) view.findViewById(R.id.tv_lv1);
        holder.text_2 = (TextView) view.findViewById(R.id.tv_lv2);
        holder.text_3 = (TextView) view.findViewById(R.id.tv_lv3);
        holder.text_4 = (TextView) view.findViewById(R.id.tv_lv4);
        holder.text_5 = (TextView) view.findViewById(R.id.tv_lv5);
        holder.tv_jingyanzhi1 = (TextView) view.findViewById(R.id.tv_jingyanzhi1);
        holder.tv_jingyanzhi2 = (TextView) view.findViewById(R.id.tv_jingyanzhi2);
        holder.tv_jingyanzhi3 = (TextView) view.findViewById(R.id.tv_jingyanzhi3);
        holder.tv_jingyanzhi4 = (TextView) view.findViewById(R.id.tv_jingyanzhi4);
        holder.tv_jingyanzhi5 = (TextView) view.findViewById(R.id.tv_jingyanzhi5);
//        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(42,42);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//        RelativeLayout.LayoutParams params_big=new RelativeLayout.LayoutParams(72,72);
//        params_big.addRule(RelativeLayout.CENTER_IN_PARENT);
//        holder.text_1.setLayoutParams(params);
//        holder.text_2.setLayoutParams(params_big);
//        holder.text_3.setLayoutParams(params);
//        holder.text_4.setLayoutParams(params);
//        holder.text_5.setLayoutParams(params);
//        holder.text_1.setGravity(Gravity.CENTER);
//        holder.text_2.setGravity(Gravity.CENTER);
//        holder.text_3.setGravity(Gravity.CENTER);
//        holder.text_4.setGravity(Gravity.CENTER);
//        holder.text_5.setGravity(Gravity.CENTER);
        holder.tv_jingyanzhi2.setVisibility(View.VISIBLE);
        holder.tv_jingyanzhi2.setText("      距离 "+(gradeValue + 1)+" \n 还有 "+dataBean.getNeedEmpirical()+" 经验值");
//        int screen= Tools.getScreenWidth(getActivity());
//        int width=(screen-76*2-72)/4;
//        LinearLayout.LayoutParams ll_params=new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
//        ll_params.setMargins(0,5,0,0);
//        holder.ll_circle1.setLayoutParams(ll_params);
//        holder.ll_circle2.setLayoutParams(ll_params);
//        holder.ll_circle3.setLayoutParams(ll_params);
//        holder.ll_circle4.setLayoutParams(ll_params);
    }

    private class ViewHolder {
        TextView tv_jingyanzhi1;
        TextView tv_jingyanzhi2;
        TextView tv_jingyanzhi3;
        TextView tv_jingyanzhi4;
        TextView tv_jingyanzhi5;
        TextView text_1;
        TextView text_2;
        TextView text_3;
        TextView text_4;
        TextView text_5;
        TextView text_need;
        TextView name_need;
        TextView exp_need;
        LinearLayout linearLayout;
        LinearLayout ll_circle1;
        LinearLayout ll_circle2;
        LinearLayout ll_circle3;
        LinearLayout ll_circle4;
        LinearLayout ll_circle5;
    }
}
