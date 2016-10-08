package com.wodm.android.ui.newview;

import android.os.Bundle;

import com.wodm.R;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;

/**
 * Created by songchenyu on 16/9/29.
 */
@Layout(R.layout.new_aty_degree)
public class MyDegreeActivity extends AppActivity implements  AtyTopLayout.myTopbarClicklistenter {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void leftClick() {

    }

    @Override
    public void rightClick() {

    }
}
