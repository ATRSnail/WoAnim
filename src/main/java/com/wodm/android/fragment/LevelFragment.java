package com.wodm.android.fragment;

import android.app.Fragment;
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

import org.eteclab.base.annotation.ViewIn;

/**
 * Created by ATRSnail on 2016/10/12.
 */

public class LevelFragment extends Fragment {
    private UserInfoBean.DataBean dataBean;
    private int gradeValue;

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
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.text_1.setText("LV" + (gradeValue - 1));
            if (!TextUtils.isEmpty(name))
                holder.text_2.setText(name);
            holder.text_3.setText("LV" + (gradeValue + 1));
            holder.text_4.setText("LV" + (gradeValue + 2));
            holder.text_5.setText("LV" + (gradeValue + 3));
        }
        return view;
    }

    private void initView(ViewHolder holder, View view) {
        holder.text_1 = (TextView) view.findViewById(R.id.leve_1);
        holder.linearLayout = (LinearLayout) view.findViewById(R.id.ll_level);
        holder.text_2 = (TextView) view.findViewById(R.id.leve_2);
        holder.text_3 = (TextView) view.findViewById(R.id.leve_3);
        holder.text_4 = (TextView) view.findViewById(R.id.leve_4);
        holder.text_5 = (TextView) view.findViewById(R.id.leve_5);
        holder.text_need = (TextView) view.findViewById(R.id.level_need);
        holder.text_need.setText("距离LV" + gradeValue + "还有" + dataBean.getNeedEmpirical() + "经验值");

    }

    static class ViewHolder {
        TextView text_1;
        TextView text_2;
        TextView text_3;
        TextView text_4;
        TextView text_5;
        TextView text_need;
        LinearLayout linearLayout;
    }
}
