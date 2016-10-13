package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.widget.ScrollView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.TaskAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.NoScrollListView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/10/12.
 */
@Layout(R.layout.activity_task)
public class TaskActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter {
    @ViewIn(R.id.scrollView)
    private ScrollView scrollView;
    @ViewIn(R.id.lv_task)
    private NoScrollListView lv_task;
    private TaskAdapter adapter;
    @ViewIn(R.id.top_task)
    private AtyTopLayout top_task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        top_task.setOnTopbarClickListenter(this);
        adapter=new TaskAdapter(this);
        lv_task.setAdapter(adapter);
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
    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
