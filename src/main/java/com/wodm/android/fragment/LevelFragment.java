package com.wodm.android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;

/**
 * Created by ATRSnail on 2016/10/12.
 */

public class LevelFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Constants.CURRENT_USER == null)
            return super.onCreateView(inflater, container, savedInstanceState);
        UserInfoBean.DataBean.AccountBean accountBean = Constants.CURRENT_USER.getData().getAccount();
        int gradeValue = accountBean.getGradeValue();
        if (gradeValue != 1) {
            return inflater.inflate(R.layout.level_circle_second, container, false);
        }
        return inflater.inflate(R.layout.level_circle_first, container, false);
    }
}
