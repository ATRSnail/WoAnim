package com.wodm.android.ui.newview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
}
