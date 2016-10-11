package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.widget.ListView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.MyDealAdapter;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/10/11.
 */
@Layout(R.layout.aty_my_medal)
public class MyMedalActivity extends AppActivity {
    @ViewIn(R.id.lv_noscroll)
    private ListView lv_noscroll;
    private MyDealAdapter myDealAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDealAdapter=new MyDealAdapter(this);
        lv_noscroll.setAdapter(myDealAdapter);

    }
}
