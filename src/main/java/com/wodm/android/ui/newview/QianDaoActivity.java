package com.wodm.android.ui.newview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;


public class QianDaoActivity extends Activity {

    RelativeLayout relativeLayout;
    ImageView sign2;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make us non-modal, so that others can receive touch events.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // ...but notify us that it happened.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        setContentView(R.layout.activity_qian_dao);
        int coefficient=getIntent().getIntExtra("coefficient",1);
        int score =getIntent().getIntExtra("score",4);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_qian_dao);
        sign2 = (ImageView) findViewById(R.id.sign2);
        textView = (TextView) findViewById(R.id.score);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.activity_qian_dao)
                finish();
            }
        });
        textView.setText("积分+"+score);
      if (coefficient==3){
          sign2.setVisibility(View.VISIBLE);
      }else {
          sign2.setVisibility(View.GONE);
      }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // If we've received a touch notification that the user has touched
        // outside the app, finish the activity.
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            finish();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
