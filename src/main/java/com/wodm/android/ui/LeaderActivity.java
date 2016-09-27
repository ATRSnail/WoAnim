package com.wodm.android.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wodm.R;
import com.wodm.android.adapter.TabPagerAdapter;
import com.wodm.android.utils.Preferences;
import com.wodm.android.view.NoScrollViewPager;

import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.util.ArrayList;

@Layout(R.layout.activity_leader)
public class LeaderActivity extends AppActivity {

    @ViewIn(R.id.page_index)
    private NoScrollViewPager pager;

    @InflateView(R.layout.layout_welcome1)
    private View mEndView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ArrayList<View> pagerTopViews = new ArrayList<>();

        int[] imgs = {R.mipmap.lead1, R.mipmap.lead2};
        for (int str : imgs) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(str);
            pagerTopViews.add(iv);
        }
        pagerTopViews.add(mEndView);
        pager.setAdapter(new TabPagerAdapter(pagerTopViews));
        pager.setNoScroll(false);


        mEndView.findViewById(R.id.start_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeaderActivity.this, Main2Activity.class));
                Preferences.getInstance(getApplicationContext()).setPreference("is_first", true);
                finish();
            }
        });

    }
}