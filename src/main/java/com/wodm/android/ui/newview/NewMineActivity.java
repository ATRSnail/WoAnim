package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.widget.ScrollView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.NewMineAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.NoScrollListView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/9/26.
 */
@Layout(R.layout.new_aty_mine)
public class NewMineActivity extends AppActivity {
    @ViewIn(R.id.lv_noscroll)
    private NoScrollListView noScrollListView;
    @ViewIn(R.id.scrllow_mine)
    private ScrollView scrollow;
    private NewMineAdapter newMineAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      newMineAdapter=new NewMineAdapter(this);
      noScrollListView.setAdapter(newMineAdapter);
      scrollow.setFocusable(true);
      scrollow.setFocusableInTouchMode(true);
      scrollow.requestFocus();
    }


}
