package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.widget.ScrollView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.BuyingHistoryAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.NoScrollListView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/11/15.
 */
@Layout(R.layout.buying_history)
public class BuyingHistoryActivity extends AppActivity {
    @ViewIn(R.id.lv_noscroll)
    private NoScrollListView lv_noscroll;
    @ViewIn(R.id.scrollView)
    private ScrollView scrollView;
    private BuyingHistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        }
        adapter=new BuyingHistoryAdapter(this);
        lv_noscroll.setAdapter(adapter);
    }

}
