package com.wodm.android.ui.newview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.MyDealAdapter;
import com.wodm.android.tools.DisplayUtil;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/10/11.
 */
@Layout(R.layout.aty_my_medal)
public class MyMedalActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener {
    @ViewIn(R.id.back_achieve)
    AtyTopLayout atyTopLayout;
    @ViewIn(R.id.activity_my_medal)
    ScrollView scrollView;
    @ViewIn(R.id.ll_attendance_medal)
    LinearLayout ll_attendance_medal;
    @ViewIn(R.id.ll_comment_medal)
    LinearLayout ll_comment_medal;
    @ViewIn(R.id.ll_register_medal)
    LinearLayout ll_register_medal;
    static final int ATTENDANCE = R.id.ll_attendance_medal;
    static final int COMMENT = R.id.ll_comment_medal;
    static final int REGISTER = R.id.ll_register_medal;

    private int medalImage[] = new int[]{R.mipmap.comer_achieve, R.mipmap.normal_forget_achieve, R.mipmap.violet_forget_achieve, R.mipmap.driver_achieve};
    private int clickedImage[] = new int[]{R.mipmap.click_commer_medal, R.mipmap.click_forget_medal, R.mipmap.click_violet_medal, R.mipmap.click_driver_medal};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        atyTopLayout.setOnTopbarClickListenter(this);
        initLinearLayout(ll_attendance_medal, ATTENDANCE);
        initLinearLayout(ll_comment_medal, COMMENT);
        initLinearLayout(ll_register_medal, REGISTER);

    }

    private void initLinearLayout(LinearLayout linearLayout, int id) {
        for (int i = 0; i < medalImage.length; i++) {
            ImageView image = new ImageView(this);
            int width = (Tools.getScreenWidth(this) - 60) / 12 * 4;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 30, 0);
            image.setImageResource(medalImage[i]);
            image.setLayoutParams(params);
            image.setTag(i);
            image.setId(id);
            linearLayout.addView(image);
            image.setOnClickListener(this);
        }
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        }
    }

    void panduan(LinearLayout linearLayout, int i, int key) {
        ImageView image = (ImageView) linearLayout.getChildAt(i);
        if (key == i) {
            image.setImageResource(clickedImage[i]);
        } else {
            image.setImageResource(medalImage[i]);
        }
    }

    @Override
    public void onClick(View v) {
        int key = (int) v.getTag();
        int id = v.getId();

        for (int i = 0; i < medalImage.length; i++) {

            switch (id) {
                case ATTENDANCE:
                    panduan(ll_attendance_medal, i, key);
                    break;
                case COMMENT:
                    panduan(ll_comment_medal, i, key);
                    break;
                case REGISTER:
                    panduan(ll_register_medal, i, key);
                    break;


            }


        }

    }


}
