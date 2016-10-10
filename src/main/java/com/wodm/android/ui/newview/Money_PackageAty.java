package com.wodm.android.ui.newview;

import android.os.Bundle;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.MoneyPackageAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/10/9.
 */
@Layout(R.layout.aty_package)
public class Money_PackageAty extends AppActivity {
    private MoneyPackageAdapter moneyPackageAdapter;
    @ViewIn(R.id.gv_package)
    private MyGridView gv_package;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moneyPackageAdapter=new MoneyPackageAdapter(this);
        gv_package.setAdapter(moneyPackageAdapter);
    }
}
