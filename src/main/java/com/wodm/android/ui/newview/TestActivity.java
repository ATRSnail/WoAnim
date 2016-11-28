package com.wodm.android.ui.newview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

@Layout(R.layout.test)
public class TestActivity extends AppActivity {

//    @ViewIn(R.id.level_2)
//    TextView level_2;
//    @ViewIn(R.id.ll2)
//    LinearLayout ll2;
//    @ViewIn(R.id.level_need)
//    LinearLayout level_need;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        level_2.measure(w, h);
//
//        int width = level_2.getMeasuredWidth();
//        int height = level_2.getMeasuredHeight();
//        Log.e("AAAa", "----------" + width);
//        Log.e("AAAa", "----------" + height);
//        int[] location = new int[2];
//        level_2.getLocationOnScreen(location);
//        Log.e("AAAa", "----------" + width);
//        Log.e("AAAa", "----------" + height);
//
//        float x = location[0] + width / 2;
//        float y = location[1] + height / 2;
//        level_need.setX(300);
//        level_need.setY(215);

    }
}
