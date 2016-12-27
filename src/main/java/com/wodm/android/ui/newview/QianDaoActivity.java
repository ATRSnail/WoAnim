package com.wodm.android.ui.newview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wodm.R;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;


public class QianDaoActivity extends Activity {

    RelativeLayout relativeLayout;
    ImageView sign2;
    int flag=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qian_dao);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_qian_dao);
        sign2 = (ImageView) findViewById(R.id.sign2);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.activity_qian_dao)
                finish();
            }
        });
      if (flag==1){
          sign2.setVisibility(View.VISIBLE);
      }else {
          sign2.setVisibility(View.GONE);
      }
    }
}
